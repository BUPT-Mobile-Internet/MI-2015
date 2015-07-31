package ccntomcat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.util.logging.Level;

import net.sf.json.JSONObject;

import org.apache.coyote.Response;
import org.apache.tomcat.util.http.mapper.Mapper;
import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.CCNInterestHandler;
import org.ccnx.ccn.config.ConfigurationException;
import org.ccnx.ccn.impl.testHash;
import org.ccnx.ccn.impl.support.Log;
import org.ccnx.ccn.io.CCNFileOutputStream;
import org.ccnx.ccn.io.CCNOutputStream;
import org.ccnx.ccn.io.RepositoryFileOutputStream;
import org.ccnx.ccn.io.RepositoryOutputStream;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.Interest;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;
import org.ccnx.ccn.utils.CommonParameters;

public class CCNConnector implements CCNInterestHandler {

	protected CCNHandle ccnhandle;//, ccnhandle1, ccnhandle2, ccnhandle3,ccnhandle4, ccnhandle5;
	protected ContentName ccnprefix;//, ccnprefix1, ccnprefix2, ccnprefix3,ccnprefix4, ccnprefix5;

	protected CCNOutputStream ccnostream;
	
	protected CCNConcurrentHashMap<String, Interest> uriHashMap;

	protected CCNConcurrentHashMap<String,Long> repoHashMap;

	protected testHash testhash = testHash.getTestHash();
	
	protected int ccncount = 0;
	
	protected Mapper mapper;
	protected boolean running;
	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}
	protected String waitForMatch = null;
	
	protected String workPath = null;

	public boolean isPreLoad = false;
	
	public CCNPreLoad ccnPreLoad = null;
	
	public CCNStaticContentWatch ccnContentWatch = null;
	//protected String workPath = null;
	protected String rootPath;
	public String getRootPath() {
		return rootPath;
	}


	protected File rootFilePath;
	public CCNPreLoad getCcnPreLoad() {
		return ccnPreLoad;
	}

	public CCNConnector() {
		this.uriHashMap = new CCNConcurrentHashMap<String,Interest>(1024,0.75f,1024);
		this.repoHashMap = new CCNConcurrentHashMap<String,Long>(1024,0.75f,1024);
		
		String dirPath = System.getProperty("user.dir");
		/*
		System.out.println("-----------------1th workPath----------------------");

		System.out.println("workPath is "+dirPath);

		System.out.println("-----------------1th workPath----------------------");
		*/
		workPath = dirPath.replaceAll("bin", "webapps");
		/*
		System.out.println("-----------------2th workPath----------------------");

		System.out.println("workPath is "+workPath);

		System.out.println("-----------------2th workPath----------------------");
		*/
		
		//rootPath = workPath + "/ROOT";
		
		rootPath = workPath + "/webapps/ROOT";

		System.out.println("-----------------rootPath----------------------");

		System.out.println("rootPath is "+rootPath);
		this.running = true;
		rootFilePath = new File(rootPath);
	}
	public void destroy(){
		this.running = false;
		this.ccnhandle.close();
	}
	/*
	public CCNConnector() {
		this.uriHashMap = new CCNConcurrentHashMap<String,Interest>(1024,0.75f,1024);
		this.repoHashMap = new CCNConcurrentHashMap<String,Long>(1024,0.75f,1024);
		
		String dirPath = System.getProperty("user.dir");
		System.out.println("-----------------1th workPath----------------------");

		System.out.println("workPath is "+dirPath);

		System.out.println("-----------------1th workPath----------------------");

		workPath = dirPath.replaceAll("bin", "webapps");
		
		System.out.println("-----------------2th workPath----------------------");

		System.out.println("workPath is "+workPath);

		System.out.println("-----------------2th workPath----------------------");
		rootPath = workPath + "/ROOT";
		rootFilePath = new File(rootPath);
	}
	*/
	public CCNConcurrentHashMap<String, Long> getRepoHashMap() {
		return repoHashMap;
	}

	public void setRepoHashMap(CCNConcurrentHashMap<String, Long> repoHashMap) {
		this.repoHashMap = repoHashMap;
	}
	
	public CCNConcurrentHashMap<String, Interest> getUriHashMap() {
		return uriHashMap;
	}
	public void setUriHashMap(CCNConcurrentHashMap<String, Interest> uriHashMap) {
		this.uriHashMap = uriHashMap;
	}
	
	
	protected CCNEndpoint ccnendpoint;

	public void setCcnendpoint(CCNEndpoint ccnendpoint) {
		this.ccnendpoint = ccnendpoint;
	}

	public boolean openCcnHandle() {
		try {
			ccnhandle = CCNHandle.open();
			ccnhandle.setTesthash(this.testhash);
			// ccnhandle1 = CCNHandle.open();
			/*
			 * ccnhandle2 = CCNHandle.open(); ccnhandle3 = CCNHandle.open();
			 * ccnhandle4 = CCNHandle.open(); ccnhandle5 = CCNHandle.open();
			 */
			ccnprefix = ContentName.fromURI(Constants.DEFAULT_CCN_URI);
			// ccnprefix1 = ContentName.fromURI("Constants.DEFAULT_CCN_URI");
			/*
			 * ccnprefix2 = ContentName.fromURI("ccnx:/app2"); ccnprefix3 =
			 * ContentName.fromURI("ccnx:/app3"); ccnprefix4 =
			 * ContentName.fromURI("ccnx:/app4"); ccnprefix5 =
			 * ContentName.fromURI("ccnx:/app5");
			 */
			ccnhandle.registerFilter(ccnprefix, this);
			// ccnhandle1.registerFilter(ccnprefix1, this);
			/*1;fps
			 * ccnhandle2.registerFilter(ccnprefix2, this);
			 * ccnhandle3.registerFilter(ccnprefix3, this);
			 * ccnhandle4.registerFilter(ccnprefix4, this);
			 * ccnhandle5.registerFilter(ccnprefix5, this);
			 */
			ccnPreLoad = new CCNPreLoad(ccnhandle);
			
			ccnContentWatch = new CCNStaticContentWatch(ccnPreLoad,rootFilePath,this);

			ccnContentWatch.startContentWatch();	
			
			return true;
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedContentNameStringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/*catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/ catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public CCNHandle getCcnhandle() {
		return ccnhandle;
	}

	public ContentName getCcnPrefix() {
		try {
			ccnprefix = ContentName.fromURI(Constants.DEFAULT_CCN_URI);
		} catch (MalformedContentNameStringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ccnprefix;
	}

	public String getOriginalUri(Interest interest) {
		ContentName contentName = interest.getContentName();

		ContentName fileNamePostfix = contentName.postfix(getCcnPrefix());

		String originalUri = fileNamePostfix.toString();

		return originalUri;
	}

	public String getUri(Interest interest) {
		String uri = getOriginalUri(interest);
		// int index1 = uri.indexOf("-00"); //nan 2013-11-17 update
		int index1 = uri.indexOf("=00");
		int index2 = uri.indexOf("=FD");
		// int index3 = uri.ind
		int index = 0;
		if (index1 != -1 && index2 != -1) {
			index = (index1 < index2 ? index1 : index2);
		} else if (index1 != -1 && index2 == -1) {
			index = index1;
		} else if (index1 == -1 && index2 != -1) {
			index = index2;
		} else if (index1 == -1 && index2 == -1) {
			index = -1;
		}
		if (index != -1) {
			uri = uri.substring(0, index - 1);
		}
		return uri;
	}

	public CCNOutputStream createCCNOutputStream(Interest interest,
			Response response) {
		CCNOutputStream ccnostream = null;
		ContentName contentname;
		try {
			contentname = ContentName.fromURI(getUri(interest));
			try {
				ccnostream = new CCNFileOutputStream(contentname, ccnhandle);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ccnostream.addOutstandingInterest(interest);
		} catch (MalformedContentNameStringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ccnostream;

	}

	/*
	 * public CCNOutputStream createCCNOutputStream(Interest interest, Response
	 * response) { CCNOutputStream ccnostream = null; try { ContentName
	 * contentname = ContentName.fromURI(getUri(interest));
	 * 
	 * if (response.isDefaultServlet()) { ccncount=(ccncount++)%5;
	 * switch(ccncount){
	 * 
	 * case 1: ccnostream = new RepositoryFileOutputStream(contentname,
	 * ccnhandle1, true); break; case 2: ccnostream = new
	 * RepositoryFileOutputStream(contentname, ccnhandle2, true); break; case 3:
	 * ccnostream = new RepositoryFileOutputStream(contentname, ccnhandle3,
	 * true); break; case 4: ccnostream = new
	 * RepositoryFileOutputStream(contentname, ccnhandle4, true); break;
	 * default: ccnostream = new RepositoryFileOutputStream(contentname,
	 * ccnhandle5, true); break; }
	 * 
	 * } else { ccnostream = new CCNFileOutputStream(contentname, ccnhandle1);
	 * ccnostream.addOutstandingInterest(interest); } } catch
	 * (MalformedContentNameStringException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return ccnostream; }
	 */
	public boolean flush(byte[] buf, int off, int len) {
		try {
			System.out.println("ccnconncector 337 flush()");
			ccnostream.write(buf, off, len);
			if (len < Constants.BLOCK_SIZE) {
				ccnostream.flush();
				ccnostream.close();
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean checkUri(String uri){
		
		if (uri.indexOf("%FD") != -1 || uri.indexOf("%00") != -1
				|| uri.indexOf("%C1") != -1 || uri.indexOf("%EA") != -1
				|| uri.indexOf("%DA") != -1 || uri.equals("/") ) {
			return false;
		}
		return true;

	}

	@Override
	public boolean handleInterest(Interest interest){
		// TODO Auto-generated method stub
		System.out.println(interest.toString());
		//Yukai Tu Modify
		if (isPreLoad) {
			if(getUri(interest).indexOf("fanyi")!=-1){
				System.out.println("in the fanyi");
				String httpUrl = "http://apis.baidu.com/apistore/tranlateservice/dictionary";
				String httpArg = "query="+getUri(interest).substring(getUri(interest).indexOf("fanyi")+6)+"&from=en&to=zh";
				String jsonResult = request(httpUrl, httpArg);
				JSONObject json= JSONObject.fromObject(JSONObject.fromObject(JSONObject.fromObject(jsonResult).get("retData")).get("dict_result"));
				CCNOutputStream ostream;
				try {
					ostream = new CCNOutputStream(interest.getContentName(), this.ccnhandle);
				
				InputStream is = new ByteArrayInputStream(jsonResult.getBytes());
				
				long time = System.currentTimeMillis();
				int size = CommonParameters.BLOCK_SIZE;
				int readLen = 0;
				byte [] buffer = new byte[CommonParameters.BLOCK_SIZE];
				if( Log.isLoggable(Level.FINER)) {
					Log.finer("do_write: " + is.available() + " bytes left.");
					while ((readLen = is.read(buffer, 0, size)) != -1){
						ostream.write(buffer, 0, readLen);
						Log.finer("do_write: wrote " + size + " bytes.");
						Log.finer("do_write: " + is.available() + " bytes left.");
					}
				} else {
					while ((readLen = is.read(buffer, 0, size)) != -1){
						ostream.write(buffer, 0, readLen);
					}
				}
				ostream.close();
				Log.fine("finished write: {0}", System.currentTimeMillis() - time);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(json.get("retData"));
				System.out.println(jsonResult);
			}
			
		}
		return false;
	}
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey","df47a536311e7de89867b38f0ba21925");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	public String getFileFormat(String fileName){
		
		int index = fileName.indexOf(".");
		return fileName.substring(index + 1);
		
	}
	
	public String removeVersion(String str){
		//ccnx:/index.jsp/%FD%DS%DSF/%00%01
		int begin = str.indexOf("%FD");
		if(begin==-1){
			return str;
		}
		int last = begin + str.substring(begin).indexOf('/');
		//System.out.println(str.substring(begin));
		//System.out.println(str.substring(begin,last));
		if(last == begin-1)
			return str;
		return str.replaceAll(str.substring(begin-1,last+1),"/");
	}
	
	class CCNPreLoad {


		protected CCNOutputStream ccnostream;
		protected CCNHandle handle;
		FileWriter writer = null;

		public CCNPreLoad(CCNHandle handle) {
			this.handle = handle;
			CommonParameters.unversioned = false;			
			CommonParameters.rawMode = false;
			
			//long startTime = System.currentTimeMillis();
			/*
			try {
				writer = new FileWriter("/usr/test/tomcatlog");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			*/
			try {
				showAllFiles(rootFilePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//writeLog(startTime);
			
			isPreLoad = true;
		}

		public void writeLog(long startTime){
			
			long completeTime = System.currentTimeMillis();
			long responseTime = completeTime - startTime;
			try {
				writer.write(responseTime+"\n");
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void showAllFiles(File dir) throws Exception {
			File[] fs = dir.listFiles();
			for (int i = 0; i < fs.length; i++) {
				System.out.println(fs[i].getAbsolutePath());
				// System.out.println(fs[i].getParent());

				if (fs[i].isDirectory()) {
					if(false == fs[i].getName().equals("WEB-INF") ){
						showAllFiles(fs[i]);						
					}
				} else {// if this is a static file			
				//avoid to write dynamic file to repo
					if(false == getFileFormat(fs[i].getName()).equals("class")){
					String format = getFileFormat(fs[i].getName());
					if(false == format.equals("jsp") && false == format.equals("JSP")){
						preWrite(fs[i]);	
					}
				}
			}
			
			}
		}
		
		public void preWrite(File file) throws MalformedContentNameStringException{
			//step1:  get dabsolute path, eg: /usr/workspace/Tomcat6/webapps/ROOT/app1/file1
			String absPath = file.getAbsolutePath();

			
			//setp2: get relative path, eg: /app1/file1
			String relaPath = absPath.substring(rootPath.length());
			//step3: write file to repository;
			ContentName contentName = ContentName.fromURI("ccnx:/"
					+ relaPath);
			writeToRepo(file.getAbsolutePath(), contentName);
			recordAdd("ccnx:/"+relaPath,System.currentTimeMillis());
		}
		
		public void watchWrite(String file) throws MalformedContentNameStringException{
			//step1:  get dabsolute path, eg: /usr/workspace/Tomcat6/webapps/ROOT/app1/file1
			//file is the absolute
			//setp2: get relative path, eg: /app1/file1
			//System.out.println("workpath= "+workPath);
			String relaPath = file.substring(rootPath.length());
			//step3: write file to repository;
			ContentName contentName = ContentName.fromURI("ccnx:/"
					+ relaPath);
			writeToRepo(file, contentName);
			recordAdd("ccnx:/"+relaPath,System.currentTimeMillis());
		}
		
		public String getFileInfo(File file){
			
			String fileName = file.getName();//get file Name
			int index = fileName.lastIndexOf(".");
			String fileInfo = null;
			if(index !=-1){
				 fileInfo = "Type:" + CCNProcessor.MapFileFormat(fileName.substring(index + 1))
						+ "\nLength:" + file.length() + "\r\n";				
			}else{ // no format
				 fileInfo = "Type:" + "text/html"
						+ "\nLength:" + file.length() + "\r\n";			
			}
			return fileInfo;
		}
		
		public void recordAdd(String str, Long time){
			
			repoHashMap.addObject(str, time);
		
		}
		
		public void writeToRepo(String fileName,
				ContentName contentName) {			
			try {
				
				doPut(handle, fileName, contentName);
				
			} catch (InvalidKeyException e) {
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

		protected void doPut(CCNHandle handle, String fileName,
				ContentName nodeName) throws IOException, InvalidKeyException,
				ConfigurationException {
			InputStream is;

			File theFile = new File(fileName);
			if (!theFile.exists()) {
				System.out.println("No such file: " + theFile.getName());
			}
			is = new FileInputStream(theFile);

			CCNOutputStream ostream;

			if (CommonParameters.rawMode) {
				if (CommonParameters.unversioned)
					ostream = new CCNOutputStream(nodeName, handle);
				else
					ostream = new CCNFileOutputStream(nodeName, handle);
			} else {
				if (CommonParameters.unversioned)
					ostream = new RepositoryOutputStream(nodeName, handle,
							CommonParameters.local);
				else
					ostream = new RepositoryFileOutputStream(nodeName, handle,
							CommonParameters.local);
			}
			/*if (CommonParameters.timeout != null)
				ostream.setTimeout(CommonParameters.timeout);
			*/
			do_write(ostream, is, "");

			return;
		}

		private void do_write(CCNOutputStream ostream, InputStream is,
				String fileInfo) throws IOException {
			int size = CommonParameters.BLOCK_SIZE;
			int readLen = 0;
			byte[] buffer = new byte[CommonParameters.BLOCK_SIZE];
			byte[] fileInfoByte = fileInfo.getBytes();
			//add file info to buffer, and the write it to ostream
			System.arraycopy(fileInfoByte, 0, buffer, 0, fileInfoByte.length);
			ostream.write(buffer, 0, fileInfoByte.length);
			while ((readLen = is.read(buffer, 0, size)) != -1) {
				ostream.write(buffer, 0, readLen);
			}
			ostream.close();
		}
	}
}
