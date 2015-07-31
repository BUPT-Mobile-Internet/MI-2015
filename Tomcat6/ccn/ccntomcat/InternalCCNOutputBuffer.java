package ccntomcat;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Servlet;

import org.apache.coyote.ActionCode;
import org.apache.coyote.OutputBuffer;
import org.apache.coyote.Response;
import org.apache.coyote.http11.InternalOutputBuffer;
import org.apache.coyote.http11.OutputFilter;
import org.apache.coyote.http11.filters.IdentityOutputFilter;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.ByteChunk.ByteOutputChannel;
import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.io.CCNOutputStream;
import org.ccnx.ccn.protocol.Interest;

public class InternalCCNOutputBuffer implements OutputBuffer, ByteOutputChannel {

	/**
	 * Committed flag for Header.
	 * 
	 * if header info has already been copied into ccnsockbuffer, then this flag
	 * is true
	 * 
	 */
	protected boolean committed;

	protected Interest interest = null;

	protected CCNHandle ccnhandle;

	public CCNHandle getCcnhandle() {
		return ccnhandle;
	}

	public void setCcnhandle(CCNHandle ccnhandle) {
		this.ccnhandle = ccnhandle;
	}

	/**
	 * Associated Coyote response.
	 * 
	 */
	protected Response response;

	/**
	 * Position in the buffer.
	 */
	protected int pos;

	/**
	 * Socket buffer (extra buffering to reduce number of packets sent).
	 */
	// protected boolean useSocketBuffer = false;

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	/**
	 * The buffer used for header composition.
	 */
	protected byte[] buf;

	/**
	 * CCN output stream
	 * 
	 * 
	 */

	/**
	 * Underlying output stream.
	 */
	protected OutputStream outputStream;

	/**
	 * Underlying output buffer.
	 */
	protected OutputBuffer outputStreamOutputBuffer;

	//protected InternalOutputBuffer internaloutputbuffer;

	protected ByteChunk ccnByteChunkBuffer;
	/**
	 * Finished flag.
	 */
	protected boolean finished;

	protected CCNOutputStream ccnOutputStream;

	protected CCNOutputStream ccnostream;
	/**
	 * Active filter (which is actually the top of the pipeline).
	 */
	protected OutputFilter[] activeFilters;

	/**
	 * Index of the last active filter.
	 */
	protected int lastActiveFilter;

	protected String uri;

	protected int blockNum = 0;

	protected int flushCounter = 0;

	protected Servlet servlet;

	protected boolean ccnStreamInitialized;

	protected CCNConcurrentHashMap<String, Interest> uriHashMap;

	public void setUriHashMap(CCNConcurrentHashMap<String, Interest> uriHashMap) {
		this.uriHashMap = uriHashMap;
	}

	protected CCNConnector ccnConnector;

	public void setCCNConnector(CCNConnector ccnConnector) {
		this.ccnConnector = ccnConnector;
	}

	public Servlet getServlet() {
		return servlet;
	}

	public void setServlet(Servlet servlet) {
		this.servlet = servlet;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public InternalCCNOutputBuffer(Response response, int headerBufferSize) {
		// TODO Auto-generated constructor stub

		this.response = response;
		ccnByteChunkBuffer = new ByteChunk();
		ccnByteChunkBuffer.setByteOutputChannel(this);
		buf = new byte[headerBufferSize];
		outputStreamOutputBuffer = new OutputStreamOutputBuffer();
		activeFilters = new OutputFilter[4];
		activeFilters[0] = new IdentityOutputFilter();
		lastActiveFilter = 0;
		ccnStreamInitialized = false;
		/*
		 * nan headers = response.getMimeHeaders();
		 * 
		 * buf = new byte[headerBufferSize];
		 * 
		 * outputStreamOutputBuffer = new OutputStreamOutputBuffer();
		 * 
		 * filterLibrary = new OutputFilter[0]; activeFilters = new
		 * OutputFilter[0]; lastActiveFilter = -1;
		 * 
		 * socketBuffer = new ByteChunk();
		 * socketBuffer.setByteOutputChannel(this);
		 * 
		 * committed = false; finished = false;
		 */
	}

	public void initCCNOutputStream() {
		ccnStreamInitialized = false;
	}

	@Override
	public void realWriteBytes(byte[] cbuf, int off, int len)
			throws IOException {
		// TODO Auto-generated method stub
		if (len > 0) {
			/**
			 * add ccn
			 */

			/**
			 * copy cbuf content to CCNOutputstream ???
			 * 
			 * step 1: CCNFileoutputstream = new
			 * 
			 * step 2:
			 * 
			 */

			if (!ccnStreamInitialized) {
				ccnostream = ccnConnector.createCCNOutputStream(interest,
						response);
				ccnStreamInitialized = true;
			}
			blockNum++;
			flushCounter++;
			ccnostream.write(cbuf, off, len);
			/*System.out.println(interest.toString());
			System.out.println("CCNOutputstream write " + blockNum
					+ " blocks into network, size is " + len + "bytes");
			System.out.print("Output Content is: " + new String(cbuf, "UTF-8"));
			*/
			//ccnostream.flush();
			if (len < Constants.BLOCK_SIZE) {
				ccnostream.flush();
				ccnostream.close();
				ccnStreamInitialized = false;
				uriHashMap.remove(uri);
				// uriHashMap.getProcessqu().poll();
				System.out
						.println("CCN Final block small than BLOCK_SIZE is execute");
			}
		} else {
			System.out
			.println("CCN BLOCK_SIZE 零 is execute");

			uriHashMap.remove(uri);

		}
	}

	@Override
	public int doWrite(ByteChunk chunk, Response response) throws IOException {

		if (!committed) {
			//缓存拷贝
			response.action(ActionCode.ACTION_COMMIT, null);

		}

		if (lastActiveFilter == -1)
			return outputStreamOutputBuffer.doWrite(chunk, response);
		else

			return activeFilters[lastActiveFilter].doWrite(chunk, response);

	}

	public void setInterest(Interest interest) {
		// TODO Auto-generated method stub
		this.interest = interest;
	}

	public void nextRequest() {
		// TODO Auto-generated method stub
		// uriHashMap.printUri();
		// Recycle Request object
		response.recycle();

		// Recycle filters
		for (int i = 0; i <= lastActiveFilter; i++) {
			activeFilters[i].recycle();
		}

		// Reset pointers
		pos = 0;
		lastActiveFilter = -1;
		committed = false;
		finished = false;
	}

	public void recycle() {
		// TODO Auto-generated method stub
		response.recycle();
		// Recycle filters
		for (int i = 0; i <= lastActiveFilter; i++) {
			activeFilters[i].recycle();
		}
		outputStream = null;
		pos = 0;
		lastActiveFilter = -1;
		committed = false;
		finished = false;

		ccnStreamInitialized = false;
		ccnByteChunkBuffer.reset();
		ccnByteChunkBuffer.recycle();
	}

	protected void commit() throws IOException {

		// The response is now committed
		committed = true;
		response.setCommitted(true);

		if (pos > 0) {
			// Sending the response header buffer
			// if (useSocketBuffer) {
			ccnByteChunkBuffer.append(buf, 0, pos);
			// } else {
			// outputStream.write(buf, 0, pos);
			// }
		}

	}

	/**
	 * This method will write the contents of the specyfied byte buffer to the
	 * output stream, without filtering. This method is meant to be used to
	 * write the response header.
	 * 
	 * @param b
	 *            data to be written
	 */
	public void write(byte[] b) {

		// Writing the byte chunk to the output buffer
		System.arraycopy(b, 0, buf, pos, b.length);
		pos = pos + b.length;

	}

	public void endRequest() throws IOException {

		if (!committed) {

			// Send the connector a request for commit. The connector should
			// then validate the headers, send them (using sendHeader) and
			// set the filters accordingly.
			response.action(ActionCode.ACTION_COMMIT, null);
		}
		
		//ccnOutputBuffer.recycle();	
		
		if (finished)
			return;
		/*
		 * nan if (lastActiveFilter != -1)
		 * activeFilters[lastActiveFilter].end();
		 */

		if (lastActiveFilter != -1)
			activeFilters[lastActiveFilter].end();
		// if (useSocketBuffer) {
		ccnByteChunkBuffer.flushBuffer();
		// }
		// /
		finished = true;		
	}

	/**
	 * Add an output filter to the filter library.
	 */
	// public void addActiveFilter(OutputFilter filter) {
	public void addActiveFilter() {

		/*
		 * if (lastActiveFilter == -1) {
		 * filter.setBuffer(outputStreamOutputBuffer); } else { for (int i = 0;
		 * i <= lastActiveFilter; i++) { if (activeFilters[i] == filter) return;
		 * } filter.setBuffer(activeFilters[lastActiveFilter]); }
		 * 
		 * activeFilters[0] = filter;
		 */
		activeFilters[0].setBuffer(outputStreamOutputBuffer);
		activeFilters[0].setResponse(response);

	}

	/**
	 * Set the socket buffer size.
	 */

	public void setCCNBufferSize(int ccnBufferSize) {

		if (ccnBufferSize > 500) {
			// useSocketBuffer = true;
			ccnByteChunkBuffer.allocate(ccnBufferSize, ccnBufferSize);
		} else {
			// useSocketBuffer = false;
		}

	}

	/**
	 * This class is an output buffer which will write data to an output stream.
	 */
	protected class OutputStreamOutputBuffer implements OutputBuffer {

		@Override
		public int doWrite(ByteChunk chunk, Response response)
				throws IOException {
			int length = chunk.getLength();
			// if (useSocketBuffer) {
			/**
			 * nan: c content data into buf if filesize > 8092, then
			 * ByteChunk.append() will call it's flushBuffer method (Line 363)
			 * to clear the buffer
			 * 
			 * if filesise < 8092, then ByeChunk.apend() will execute Line
			 * 344~348 and then return.
			 * 
			 */
			ccnByteChunkBuffer.append(chunk.getBuffer(), chunk.getStart(), length);
			return length;
			/*
			 * } else { outputStream.write(chunk.getBuffer(), chunk.getStart(),
			 * length); }
			 */

		}
	}
}
