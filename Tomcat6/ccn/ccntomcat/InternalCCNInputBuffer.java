package ccntomcat;

import java.io.EOFException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.coyote.InputBuffer;
import org.apache.coyote.Request;
import org.apache.tomcat.util.buf.ByteChunk;
import org.ccnx.ccn.protocol.Interest;

/**
 * Parse CCN Interest Header
 * 
 * @author Nan GuoShun
 * 
 */

public class InternalCCNInputBuffer implements InputBuffer {

	protected Interest interest = null;
	protected Request request;
	protected String uri;
	protected String originalUri;
	protected CCNConnector ccnConnector;

	public void setCCNConnector(CCNConnector ccnInternalHandler) {
		this.ccnConnector = ccnInternalHandler;
	}

	public String getOriginalUri() {
		return originalUri;
	}

	public void setOriginalUri(String originalUri) {
		this.originalUri = originalUri;
	}

	/**
	 * Pointer to the current read buffer.
	 */
	protected byte[] buf;

	public InternalCCNInputBuffer(Request request, int headerBufferSize) {

		this.request = request;
		buf = new byte[headerBufferSize];

		/*
		 * try { ccnprefix = ContentName.fromURI(Constants.DEFAULT_CCN_URI); }
		 * catch (MalformedContentNameStringException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		/*
		 * headers = request.getMimeHeaders();
		 * 
		 * buf = new byte[headerBufferSize];
		 * 
		 * inputStreamInputBuffer = new InputStreamInputBuffer();
		 * 
		 * filterLibrary = new InputFilter[0]; activeFilters = new
		 * InputFilter[0]; lastActiveFilter = -1;
		 * 
		 * parsingHeader = true; swallowInput = true;
		 */
	}

	@Override
	public int doRead(ByteChunk chunk, Request request) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setInterest(Interest interest) {
		// TODO Auto-generated method stub
		this.interest = interest;
	}

	public void parseRequet() {
		// TODO Auto-generated method stub

		/**
		 * set the method to adapt with Tomcat container
		 */
		byte[] methodB = new byte[Constants.METHOD_LENGHT];
		methodB = Constants.REQUEST_METHOD.getBytes();

		request.method().setBytes(methodB, 0, methodB.length);

		/**
		 * parse uri
		 * 
		 */
		// int index =0;
		int length = buf.length;
		for (int i = 0; i < length; i++) {
			buf[i] = 0;
		}
		uri = ccnConnector.getUri(interest);

		// step 1 : calculate ? position.
		// step 2 : copy uri to uriMB and param to queryMB
		int questionPos = uri.indexOf(Constants.QUESTION);
		String realUri = null;
		if(questionPos >= 1){
			realUri = uri.substring(0, questionPos - 1);			
		}else{
			realUri = uri;
		}

		System.out.println("realUri is : "+realUri);
		byte[] realUribuf = realUri.getBytes();
		String paramUri = null;
		if (questionPos >= 0) {
			
			if(uri.length()==questionPos+Constants.QUESTION.length()){
				paramUri = uri.substring(questionPos+Constants.QUESTION.length());
			}else{
				paramUri = uri.substring(questionPos+Constants.QUESTION.length()+1);				
			}
			
			System.out.println("before decode, paramUri is : "+paramUri);

			try {
				
			paramUri = java.net.URLDecoder.decode(paramUri, "UTF-8");
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			
			System.out.println("after decode, paramUri is : "+paramUri);
			
			byte[] paramUribuf = paramUri.getBytes();
			System.arraycopy(realUribuf, 0, buf, 0, realUribuf.length);
			if(paramUri.endsWith("...")==false){
				request.queryString().setBytes(paramUribuf, 0, paramUribuf.length);				
			}
			request.requestURI().setBytes(realUribuf, 0, realUribuf.length);

		} else {
			/**
			 * nan: security check and sometimes it is useless ?
			 */
			System.out.println("realUri is : "+realUri);

			for (int i = 0; i < realUribuf.length; i++) {
				if (realUribuf[i] == 0) {
					System.arraycopy(realUribuf, 0, buf, 0, i + 1);
					request.requestURI().setBytes(buf, 0, i + 1);
					return;
				}
			}
			// end

			System.arraycopy(realUribuf, 0, buf, 0, realUribuf.length);
			request.requestURI().setBytes(buf, 0, realUribuf.length);
		}

	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void endRequest() {
		// TODO Auto-generated method stub
		ccnConnector.getUriHashMap().remove(uri);
	}

	public void nextRequest() {
		// TODO Auto-generated method stub
		request.recycle();
		
	}

	public void recycle() {
        // Recycle Request object
        request.recycle();

     
		
	}

}
