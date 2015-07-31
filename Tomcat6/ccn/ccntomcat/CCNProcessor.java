package ccntomcat;

import java.io.File;
import java.io.IOException;

import org.apache.coyote.ActionCode;
import org.apache.coyote.ActionHook;
import org.apache.coyote.Adapter;
import org.apache.coyote.Request;
import org.apache.coyote.RequestInfo;
import org.apache.coyote.Response;
import org.apache.tomcat.util.buf.ByteChunk;
import org.ccnx.ccn.protocol.Interest;

public class CCNProcessor implements ActionHook {

	protected CCNEndpoint endpoint = null;
	/**
	 * Associated adapter.
	 */
	protected Adapter adapter = null;
	/**
	 * Request object.
	 */

	protected Request request = null;

	/**
	 * Response object.
	 */

	protected Response response = null;

	/**
	 * input
	 */

	protected InternalCCNInputBuffer inputBuffer = null;

	/**
	 * output
	 */
	
	protected InternalCCNOutputBuffer outputBuffer = null;

	// protected Interest interest = null;

	/**
	 * Error flag.
	 */
	protected boolean error = false;

	protected int ccnBufferSize = -1;

	public int getCcnBufferSize() {
		return ccnBufferSize;
	}

	public void setCcnBufferSize(int ccnBufferSize) {
		this.ccnBufferSize = ccnBufferSize;
		outputBuffer.setCCNBufferSize(ccnBufferSize);
	}

	public CCNProcessor(int headerBufferSize, CCNEndpoint endpoint) {
		// TODO Abnprsuto-generated constructor stub
		this.endpoint = endpoint;
		request = new Request();
		inputBuffer = new InternalCCNInputBuffer(request, headerBufferSize);
		response = new Response();
		response.setHook(this);

		outputBuffer = new InternalCCNOutputBuffer(response, headerBufferSize);
		response.setOutputBuffer(outputBuffer);
		request.setResponse(response);

		// nan initializeFilters();

		// Cause loading of HexUtils
		// nan HexUtils.getDec('0');

	}

	@Override
	public void action(ActionCode actionCode, Object param) {
		// TODO Auto-generated method stub
		if (actionCode == ActionCode.ACTION_COMMIT) {
			// Commit current response

			if (response.isCommitted())
				return;

			// Validate and write response headers
			prepareResponse();
			try {
				outputBuffer.commit();
			} catch (IOException e) {
				// Set error flag
				error = true;
			}
		} else if (actionCode == ActionCode.ACTION_CLOSE) {
			// Close

			// End the processing of the current request, and stop any further
			// transactions with the client

			try {
				outputBuffer.endRequest();
			} catch (IOException e) {
				// Set error flag
				error = true;
			}
		}

	}

	// ------------------------------------------------------ Connector Methods

	/**
	 * After reading the request headers, we have to setup the request filters.
	 */
	protected void prepareRequest() {

	}

	/**	
	 * When committing the response, we have to validate the set of headers, as
	 * well as setup the response filters.
	 */
	protected void prepareResponse() {

		/**
		 * nan
		 * 
		 * copy header info into InternCCNOutputBuffer.
		 * 
		 * create CCNOutputStream ???
		 * 
		 */		
		String uri = inputBuffer.getUri();// get the file name;
		int index = uri.indexOf(".");
		
		String fileInfo = null;
		String rootPath = endpoint.getCCNConnector().getRootPath();
		String filepath = rootPath+uri;
		File file = new File(filepath);
		file.length();
		//注意：当response.getContentLenthlog()为负数时，会有意向不到的问题。
		/*	
		if(index !=-1){
			 fileInfo = "Type:" + MapFileFormat(uri.substring(index + 1))
					+ "\nLength:" + response.getContentLengthLong() + "\r\n";
			
		}else{ // no format
			 fileInfo = "Type:" + "text/html"
					+ "\nLength:" + response.getContentLengthLong() + "\r\n";			
		}
		*/
	
		if(index !=-1){
			 fileInfo = "Type:" + MapFileFormat(uri.substring(index + 1))
					+ "\nLength:" + file.length() + "\r\n";
			
		}else{ // no format
			 fileInfo = "Type:" + "text/html"
					+ "\nLength:" + file.length() + "\r\n";			
		}
		
		//System.out.println("Before set Pos, Buffer pos value is: "+String.valueOf(outputBuffer.getPos()));
		outputBuffer.setPos(0);
		//System.out.println("After set Pos, Buffer pos value is: "+String.valueOf(outputBuffer.getPos()));
		outputBuffer.write(fileInfo.getBytes());
		outputBuffer.addActiveFilter();
		 
	}

	public static String MapFileFormat(String fileformat) {
		if (fileformat.equals("html")||fileformat.equals("jsp")||fileformat.equals("JSP")) {
			return "text/html";
		} else if (fileformat.equals("xml")) {
			return "text/xml";
		} else if (fileformat.equals("xhtml")) {
			return "xhtml application/xhtml+xml";
		} else if (fileformat.equals("rtf")) {
			return "appliction/rtf";
		} else if (fileformat.equals("pdf")) {
			return "appliction/pdf";
		} else if (fileformat.equals("doc")) {
			return "appliction/msword";
		} else if (fileformat.equals("png")) {
			return "image/png";
		} else if (fileformat.equals("gif")) {
			return "image/gif";
		} else if (fileformat.equals("bmp")) {
			return "image/bmp";
		} else if (fileformat.equals("jpg") || fileformat.equals("jpeg")) {
			return "image/jpeg";
		} else if (fileformat.equals("au")) {
			return "audio/basic";
		} else if (fileformat.equals("mid") || fileformat.equals("midi")) {
			return "audio/midi,audio/x-mid";
		} else if (fileformat.equals("ra") || fileformat.equals("ram")) {
			return "audio/x-pn-realaudio";
		} else if (fileformat.equals("mpg") || fileformat.equals("mpeg")) {
			return "video/mpeg";
		}else if(fileformat.endsWith("mp4")){
			return "video/mp4";
		}else if(fileformat.endsWith("mp3")){
			return "audio/mpeg";
		}else if (fileformat.equals("ogg")) {
			return "video/ogg";
		} else if (fileformat.equals("avi")) {
			return "video/x-msvideo";
		} else if (fileformat.equals("gz")) {
			return "appliction/x-gzip";
		} else if (fileformat.equals("tar")) {
			return "appliction/x-tar";
		} else if (fileformat.equals("txt")) {
			return "text/plain";
		} else if (fileformat.equals("js")) {
			return "application/x-javascript";
		} else if (fileformat.equals("css")) {
			return "text/css";
		} else {
			return "text/html";
			//return "application/octet-stream";
		}

	}

	/**
	 * Set the associated adapter.
	 * 
	 * @param adapter
	 *            the new adapter
	 */
	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}

	/**
	 * Get the associated adapter.
	 * 
	 * @return the associated adapter
	 */
	public Adapter getAdapter() {
		return adapter;
	}
	
	boolean keepAlive = true;

	public void process(Interest interest) {

		RequestInfo rp = request.getRequestProcessor();
		rp.setStage(org.apache.coyote.Constants.STAGE_PARSE);
		error = false;
		//keepAlive = true;
		// setting up the I/O
		// this.interest = interest;
		// outputBuffer.recycle();
		inputBuffer.setInterest(interest);
		inputBuffer.setCCNConnector(endpoint.getCCNConnector());
		outputBuffer.setInterest(interest);
		outputBuffer.setCCNConnector(endpoint.getCCNConnector());
		outputBuffer.setUriHashMap(endpoint.getCCNConnector().getUriHashMap());
		// outputBuffer.setCcnhandle(endpoint.getCcnhandle());
		// step1: parse CCN header
		// step2: put the header content into Request
		// while (!error && !endpoint.isPaused()&&keepAlive) {
		if (!error) {
			rp.setStage(org.apache.coyote.Constants.STAGE_PREPARE);
			inputBuffer.parseRequet();
			// outputBuffer.setUri(inputBuffer.getOriginalUri());
			outputBuffer.setUri(inputBuffer.getUri());
			outputBuffer.initCCNOutputStream();
			request.setStartTime(System.currentTimeMillis());
			// step3: call service(request,response)
			rp.setStage(org.apache.coyote.Constants.STAGE_SERVICE);
			try {
				adapter.service(request, response);
				//keepAlive = false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				error = true;
				e.printStackTrace();
			}

			// step4: Finish the handling of the request
			rp.setStage(org.apache.coyote.Constants.STAGE_ENDINPUT);
			inputBuffer.endRequest();
			// step5: next request
			//inputBuffer.nextRequest();
			//outputBuffer.nextRequest();

		}
		rp.setStage(org.apache.coyote.Constants.STAGE_ENDED);
		// Recycle
		inputBuffer.recycle();
		outputBuffer.recycle();
		// this.interest = null;

	}

	public Request getRequest() {
		// TODO Auto-generated method stub
		return request;
	}

}
