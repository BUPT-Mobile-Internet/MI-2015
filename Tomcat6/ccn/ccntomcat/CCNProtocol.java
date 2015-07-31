package ccntomcat;

import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.catalina.connector.CoyoteAdapter;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ActionCode;
import org.apache.coyote.ActionHook;
import org.apache.coyote.Adapter;
import org.apache.coyote.RequestGroupInfo;
import org.apache.coyote.RequestInfo;
import org.apache.tomcat.util.modeler.Registry;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.apache.tomcat.util.net.SSLImplementation;
import org.apache.tomcat.util.net.ServerSocketFactory;
import org.apache.tomcat.util.res.StringManager;
import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.Interest;

import ccntomcat.CCNEndpoint.Handler;

public class CCNProtocol extends AbstractProtocol implements MBeanRegistration {

	/**
	 * Maximum size of the CCN message header.
	 */
	protected int maxCCNHeaderSize = 8 * 1024;

	public int getMaxCCNHeaderSize() {
		return maxCCNHeaderSize;
	}

	public void setMaxCCNSize(int valueI) {
		maxCCNHeaderSize = valueI;
	}

	/**
	 * Processor cache.
	 */
	protected int processorCache = -1;

	public int getProcessorCache() {
		return this.processorCache;
	}

	public void setProcessorCache(int processorCache) {
		this.processorCache = processorCache;
	}

	protected int ccnBufferSize = Constants.BUFFER_SIZE;

	public int getCCNBufferSize() {
		return ccnBufferSize;
	}

	public void setCCNBufferSize(int bufferSize) {
		this.ccnBufferSize = bufferSize;
	}

	protected CCNEndpoint endpoint = new CCNEndpoint();

	protected static org.apache.juli.logging.Log log = org.apache.juli.logging.LogFactory
			.getLog(CCNProtocol.class);

	/**
	 * The string manager for this package.
	 */
	/*
	 * protected static StringManager sm =
	 * StringManager.getManager(Constants.Package);
	 */
	protected String domain;
	/**
	 * The adapter, used to call the connector.
	 */
	protected Adapter adapter;

	protected CCNConnectionHandler cHandler = new CCNConnectionHandler(this);

	// *
	protected ObjectName tpOname = null;
	// *
	protected ObjectName rgOname = null;

	public int getPort() {
		return endpoint.getPort();
	}

	public void setPort(int port) {
		endpoint.setPort(port);
	}

	/**
	 * The adapter, used to call the connector.
	 */

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

		endpoint.setName(getName());
		endpoint.setHandler(cHandler);

		// Verify the validity of the configured socket factory
		/*
		 * nan try { if (isSSLEnabled()) { sslImplementation =
		 * SSLImplementation.getInstance(sslImplementationName); socketFactory =
		 * sslImplementation.getServerSocketFactory();
		 * endpoint.setServerSocketFactory(socketFactory); } else if
		 * (socketFactoryName != null) { socketFactory = (ServerSocketFactory)
		 * Class.forName(socketFactoryName).newInstance();
		 * endpoint.setServerSocketFactory(socketFactory); } } catch (Exception
		 * ex) { log.error(sm.getString("ccnprotocol.socketfactory.initerror"),
		 * ex); throw ex; }
		 * 
		 * if (socketFactory!=null) { Iterator<String> attE =
		 * attributes.keySet().iterator(); while( attE.hasNext() ) { String key
		 * = attE.next(); Object v=attributes.get(key);
		 * socketFactory.setAttribute(key, v); } }
		 */
		try {
			endpoint.init();
			if(adapter instanceof CoyoteAdapter)
            {
            		endpoint.setConnector(((CoyoteAdapter)adapter).getConnector());
            }
		} catch (Exception ex) {
			// log.error(sm.getString("ccnprotocol.endpoint.initerror"), ex);
			throw ex;
		}
		// if (log.isInfoEnabled())
		// log.info(sm.getString("ccnprotocol.init", getName()));

	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		if (this.domain != null) {
			try {
				tpOname = new ObjectName(domain + ":" + "type=ThreadPool,name="
						+ getName());
				Registry.getRegistry(null, null).registerComponent(endpoint,
						tpOname, null);
			} catch (Exception e) {
				log.error("Can't register endpoint");
			}
			rgOname = new ObjectName(domain
					+ ":type=GlobalRequestProcessor,name=" + getName());
			Registry.getRegistry(null, null).registerComponent(cHandler.global,
					rgOname, null);
		}

		try {
			endpoint.start();
		} catch (Exception ex) {
			// log.error(sm.getString("http11protocol.endpoint.starterror"),
			// ex);
			throw ex;
		}

		// if (log.isInfoEnabled())
		// log.info(sm.getString("http11protocol.start", getName()));

	}

	public String getDomain() {
		return domain;
	}

	public InetAddress getAddress() {
		return endpoint.getAddress();
	}

	public void setAddress(InetAddress ia) {
		endpoint.setAddress(ia);
	}

	public String getName() {
		String encodedAddr = "";
		if (getAddress() != null) {
			encodedAddr = "" + getAddress();
			if (encodedAddr.startsWith("/"))
				encodedAddr = encodedAddr.substring(1);
			encodedAddr = URLEncoder.encode(encodedAddr) + "-";
		}
		return ("CCN-" + encodedAddr + endpoint.getPort());
	}

	@Override
	public void setAttribute(String name, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * setAdpater was called at connector
	 */
	@Override
	public void setAdapter(Adapter adapter) {
		// TODO Auto-generated method stub
		this.adapter = adapter;

	}

	@Override
	public Adapter getAdapter() {
		// TODO Auto-generated method stub
		return adapter;
	}

	@Override
	public void pause() throws Exception {
		// TODO Auto-generated method stub
		try {
			endpoint.pause();
		} catch (Exception ex) {
			// log.error(sm.getString("http11protocol.endpoint.pauseerror"),
			// ex);
			throw ex;
		}
		// if (log.isInfoEnabled())
		// log.info(sm.getString("http11protocol.pause", getName()));
		// }

	}

	@Override
	public void resume() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() throws Exception {
		// Tu 20140901
		endpoint.destroy();
		if (tpOname!=null)
            Registry.getRegistry(null, null).unregisterComponent(tpOname);
        if (rgOname != null)
            Registry.getRegistry(null, null).unregisterComponent(rgOname);

	}

	@Override
	public ObjectName preRegister(MBeanServer server, ObjectName name)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postRegister(Boolean registrationDone) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preDeregister() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postDeregister() {
		// TODO Auto-generated method stub

	}

	@Override
	protected AbstractEndpoint getEndpoint() {
		// TODO Auto-generated method stub
		return null;
	}

	protected static class CCNConnectionHandler implements Handler {

		protected CCNProtocol proto;
		protected AtomicLong registerCount = new AtomicLong(0);
		protected RequestGroupInfo global = new RequestGroupInfo();
		//匿名类
		protected ConcurrentLinkedQueue<CCNProcessor> recycledProcessors = new ConcurrentLinkedQueue<CCNProcessor>() {
			protected AtomicInteger size = new AtomicInteger(0);

			// nan: add the specified node the queue tail
			public boolean offer(CCNProcessor processor) {
				boolean offer = (proto.processorCache == -1) ? true : (size
						.get() < proto.processorCache);
				// avoid over growing our cache or add after we have stopped
				boolean result = false;
				if (offer) {
					result = super.offer(processor);
					if (result) {
						size.incrementAndGet();
					}
				}
				if (!result)
					unregister(processor);
				return result;
			}

			// nan: get and remove the head of the queue.
			public CCNProcessor poll() {
				CCNProcessor result = super.poll();
				if (result != null) {
					size.decrementAndGet();
				}
				return result;
			}

			public void clear() {
				CCNProcessor next = poll();
				while (next != null) {
					unregister(next);
					next = poll();
				}
				super.clear();
				size.set(0);
			}
		};

		public CCNConnectionHandler(CCNProtocol ccnProtocol) {
			// TODO Auto-generated constructor stub
			this.proto = ccnProtocol;
		}

		@Override
		public boolean process(Interest interest) {
			// TODO Auto-generated method stub
			CCNProcessor processor = recycledProcessors.poll();
			try {

				if (processor == null) {
					System.out.println("Create Processor");
					processor = createProcessor();
				}else{
					System.out.println("Pop Processor");
				}
				
				/*
				 * nan if (processor instanceof ActionHook){ ((ActionHook)
				 * processor).action(ActionCode.ACTION_START, null); }
				 * 
				 * if (proto.isSSLEnabled() && (proto.sslImplementation !=
				 * null)) { processor.setSSLSupport
				 * (proto.sslImplementation.getSSLSupport(socket)); } else {
				 * processor.setSSLSupport(null); }
				 */
				processor.process(interest);
				
				return false;

			} // Future developers: if you discover any other
				// rare-but-nonfatal exceptions, catch them here, and log as
				// above.
			catch (Throwable e) {
				// any other exception or error is odd. Here we log it
				// with "ERROR" level, so it will show up even on
				// less-than-verbose logs.
				/*
				 * CCNProtocol.log.error
				 * (sm.getString("CCNProtocol.proto.error"), e);
				 */} finally {
				// if(proto.adapter != null) proto.adapter.recycle();
				// processor.recycle();

				if (processor instanceof ActionHook) {
					((ActionHook) processor).action(ActionCode.ACTION_STOP,
							null);
				}
				recycledProcessors.offer(processor);
			}

			return false;
		}

		protected CCNProcessor createProcessor() {
			CCNProcessor processor = new CCNProcessor(proto.maxCCNHeaderSize,
					proto.endpoint);

			processor.setAdapter(proto.adapter);
			processor.setCcnBufferSize(proto.ccnBufferSize);
			/*
			 * processor.setMaxKeepAliveRequests(proto.maxKeepAliveRequests);
			 * processor.setKeepAliveTimeout(proto.keepAliveTimeout);
			 * processor.setTimeout(proto.timeout);
			 * processor.setDisableUploadTimeout(proto.disableUploadTimeout);
			 * processor.setCompressionMinSize(proto.compressionMinSize);
			 * processor.setCompression(proto.compression);
			 * processor.setNoCompressionUserAgents
			 * (proto.noCompressionUserAgents);
			 * processor.setCompressableMimeTypes(proto.compressableMimeTypes);
			 * processor.setRestrictedUserAgents(proto.restrictedUserAgents);
			 * processor.setSocketBuffer(proto.socketBuffer);
			 * processor.setMaxSavePostSize(proto.maxSavePostSize);
			 * 
			 * processor.setServer(proto.server);
			 */
			register(processor);
			return processor;
		}

		protected void register(CCNProcessor processor) {
			if (proto.getDomain() != null) {
				synchronized (this) {
					try {
						long count = registerCount.incrementAndGet();
						RequestInfo rp = processor.getRequest()
								.getRequestProcessor();
						rp.setGlobalProcessor(global);
						ObjectName rpName = new ObjectName(proto.getDomain()
								+ ":type=RequestProcessor,worker="
								+ proto.getName() + ",name=CCNRequest" + count);
						if (log.isDebugEnabled()) {
							log.debug("Register " + rpName);
						}
						Registry.getRegistry(null, null).registerComponent(rp,
								rpName, null);
						rp.setRpName(rpName);
					} catch (Exception e) {
						log.warn("Error registering request");
					}
				}
			}
		}

		protected void unregister(CCNProcessor processor) {
			if (proto.getDomain() != null) {
				synchronized (this) {
					try {
						RequestInfo rp = processor.getRequest()
								.getRequestProcessor();
						rp.setGlobalProcessor(null);
						ObjectName rpName = rp.getRpName();
						if (log.isDebugEnabled()) {
							log.debug("Unregister " + rpName);
						}
						Registry.getRegistry(null, null).unregisterComponent(
								rpName);
						rp.setRpName(null);
					} catch (Exception e) {
						log.warn("Error unregistering request", e);
					}
				}
			}
		}
	}

}
