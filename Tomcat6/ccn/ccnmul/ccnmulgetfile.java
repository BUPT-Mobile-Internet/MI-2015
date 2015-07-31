package ccnmul;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.io.CCNFileInputStream;
import org.ccnx.ccn.io.CCNInputStream;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;

public class ccnmulgetfile {
	public static void main(String[] args) throws IOException, InterruptedException {
		String ccnName, fileName;
		FileWriter writer = null;
		try {
			writer = new FileWriter("/usr/test/mul/logfile");
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write("filesize time"+"\n");
		writer.flush();
		// create file
		int threadNum = Integer.valueOf(args[2]).intValue();
		ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
		for (int i = 0; i < threadNum; i++) {
			if(i<40 && i%40 == 0){
				Thread.sleep(10000);
			}else if(i>40 && i%10 == 0){			
				Thread.sleep(10000);		
			}
		}
		for (int i = 0; i < threadNum; i++) {
			ccnName = "ccnx:/app1/"+args[0] + i;
			fileName ="/usr/test/mul/file/"+args[1] + i;			
			executorService.execute(new Worker(ccnName,fileName,writer));
		}		
		executorService.shutdown();
		executorService.awaitTermination(500,TimeUnit.SECONDS);
	}
	
}
class Worker implements Runnable {

		String reqFileName, newFileName;
		FileWriter writer;

		public Worker(String reqFileName, String newFileName, FileWriter writer) {
			this.reqFileName = reqFileName;
			this.newFileName = newFileName;
			this.writer = writer;
		}
		public void run() {
				try {
					execute(reqFileName, newFileName);
				} catch (MalformedContentNameStringException e) {	
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
		}
		public void execute(String reqccnname, String newFileName)
				throws MalformedContentNameStringException, ConfigurationException,
				IOException {
			int readsize = 4096; 
			ContentName ccnname = ContentName.fromURI(reqccnname);
			CCNHandle handle = CCNHandle.open();
			FileOutputStream output = new FileOutputStream(new File(newFileName));		
			FileWriter wr = new FileWriter(newFileName);
			CCNInputStream input;
			input = new CCNFileInputStream(ccnname, handle);
			byte[] buffer = new byte[readsize];
			int readcount = 0;
			long readtotal = 0;
			long completeTime = 0;
			long startTime = System.currentTimeMillis();
			while ((readcount = input.read(buffer)) != -1) {	
				readtotal += readcount;
				output.write(buffer, 0, readcount);
				output.flush();	
			}
			completeTime = System.currentTimeMillis();
			input.close();

			long responseTime = completeTime - startTime;
			writeFile((int)responseTime,(int)readtotal,writer);
			System.out.println("CompleteTime is: " + responseTime);
			System.out.println("Retrieved content " + newFileName + " got "
					+ readtotal + " bytes.");					
		}
		public synchronized void writeFile(int responseTime, int fileSize, FileWriter writer){
			
			try {
				String str =String.valueOf(fileSize)+" "+String.valueOf(responseTime);
				writer.write(str+"\n");
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}

}


