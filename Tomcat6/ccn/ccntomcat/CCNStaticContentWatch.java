package ccntomcat;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.ccnx.ccn.protocol.MalformedContentNameStringException;

import ccntomcat.CCNConnector.CCNPreLoad;

public class CCNStaticContentWatch {

	CCNPreLoad ccnPreLoad;
	File rootPath;
	ExecutorService executorService = null;
	CCNConnector ccnconnector;
	
	public CCNStaticContentWatch(CCNPreLoad ccnPreLoad, File rootPath,CCNConnector ccnconnector) {
		this.ccnPreLoad = ccnPreLoad;
		this.rootPath = rootPath;
		this.ccnconnector = ccnconnector;
	}

	// WatchService 是线程安全的，跟踪文件事件的服务，一般是用独立线程启动跟踪
	public void startContentWatch() throws IOException, InterruptedException {
		File[] fs = rootPath.listFiles();
		WatchService[] watchService = new WatchService[fs.length];
		File[] filePath = new File[fs.length];

		int watchCount = 0;
		for (int i = 0; i < fs.length; i++) {
			System.out.println(fs[i].getAbsolutePath());
			if (fs[i].isDirectory()) {
				// servlet文件保存在WEB-INFO文件夹下
				if (false == fs[i].getName().equals("WEB-INF")) {
					// filePath保存当前目录的绝对路径，用于定位文件的位置。
					filePath[watchCount] = fs[i];
					watchService[watchCount] = FileSystems.getDefault()
							.newWatchService();
					Path dirPath = Paths.get(fs[i].getAbsolutePath());
					dirPath.register(watchService[watchCount],
							StandardWatchEventKinds.ENTRY_CREATE,
							StandardWatchEventKinds.ENTRY_MODIFY,
							StandardWatchEventKinds.ENTRY_DELETE);
					watchCount++;
				}

			}
		}
		WatchThreadExecutor(watchService, filePath, watchCount);

	}

	public void WatchThreadExecutor(WatchService[] watchService,
			File[] filePath, int watchServiceNum) {
		executorService = Executors
				.newFixedThreadPool(watchServiceNum);
		for (int k = 0; k < watchServiceNum; k++) {
			executorService.execute(new watchThread(watchService[k],
					filePath[k],this.ccnconnector));
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(500, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFileFormat(String fileName) {

		int index = fileName.indexOf(".");
		return fileName.substring(index + 1);

	}

	class watchThread implements Runnable {

		WatchService watchservice;
		int count = 0;
		File filePath = null;
		CCNConnector ccnconnector;
		public watchThread(WatchService watchservice, File file,CCNConnector ccnconnector) {
			this.watchservice = watchservice;
			this.filePath = file;
			this.ccnconnector = ccnconnector;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (this.ccnconnector.running = true) {
				watch();
			}
		}

		public void watch() {
			// retrieve and remove the next watch key
			// get list of pending events for the watch key
			WatchKey watchkey;
			try {
				// 当没有响应时，take操作阻塞，所以对于多个文件夹需要用多线程进行监听。
				// modify操作，Linux下会产生两次EVEN_MODIFY事件，所以在程序层面要进行控制
				watchkey = watchservice.take();
				AtomicBoolean modifyEventFired = new AtomicBoolean();
				modifyEventFired.set(false);
				for (WatchEvent<?> watchEvent : watchkey.pollEvents()) {
					// get the kind of event (create, modify, delete)
					Kind<?> kind = watchEvent.kind();
					//
					WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
					Path filename = watchEventPath.context();
					String format = getFileFormat(filename.toString());
					if (false == format.equals("jsp")
							&& false == format.equals("JSP")) {
						switch (kind.name()) {
						case "ENTRY_CREATE":
							if (filename.toString().indexOf("swp") == -1) {
								// filename.toAbsolutePath返回的是当前的工作目录 +
								// fileName，而非文件的实际目录，所有要
								// 将之转换为绝对目录
								if (!modifyEventFired.get()) {
									System.out.println(kind + " -> "
											+ filePath.toString() + "/"
											+ filename.toString());
									ccnPreLoad.watchWrite(filePath.toString()
											+ "/" + filename.toString());
									modifyEventFired.set(true);
								}
							}
							break;
						case "ENTRY_MODIFY":
							if (filename.toString().indexOf("swp") == -1) {
								if (!modifyEventFired.get()) {
									if (count % 2 == 0) {
										System.out.println(kind + " -> "
												+ filePath.toString() + "/"
												+ filename.toString());
										ccnPreLoad.watchWrite(filePath
												.toString()
												+ "/"
												+ filename.toString());
										count = 0;
									}
									modifyEventFired.set(true);
									count++;
								}
							}
							break;
						case "ENTRY_DELETE":
							break;

						}
					}
				}
				modifyEventFired.set(false);
				boolean valid = watchkey.reset();
				if (!valid) {
					return;
				}
			} catch (InterruptedException | MalformedContentNameStringException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
