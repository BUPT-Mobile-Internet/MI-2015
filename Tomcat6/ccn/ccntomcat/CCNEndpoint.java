package ccntomcat;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Executor;

import org.apache.catalina.connector.Connector;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.apache.tomcat.util.res.StringManager;
import org.ccnx.ccn.protocol.Interest;


public class CCNEndpoint extends AbstractEndpoint {
	  // CCN
   // protected CCNHandle ccnhandle;
   // protected  ContentName ccnprefix; 
	
	FileWriter writer = null;
	
    public boolean ishandle = false;
	
    Thread ccnconnectorthread = null;
    
	protected static Log log = LogFactory.getLog(CCNEndpoint.class);
	
    protected StringManager sm = 
            StringManager.getManager("org.apache.tomcat.util.net.res");
	
    protected int acceptorThreadCount = 0;
    /**
     * Track the initialization state of the endpoint.
     */
    protected boolean initialized = false;
    /**
     * Available workers.
     */
    protected WorkerStack workers = null;

    protected int threadPriority = Thread.NORM_PRIORITY;
    
    /**
     * Running state of the endpoint.
     */
    protected volatile boolean running = false;
    
    
    /**
     * Will be set to true whenever the endpoint is paused.
     */
    protected volatile boolean paused = false;

    
    /**
     * External Executor based thread pool.
     */
    protected Executor executor = null;
    public void setExecutor(Executor executor) { this.executor = executor; }
    public Executor getExecutor() { return executor; }

    /**
     * Maximum amount of worker threads.
     */
    protected int maxThreads = 200;
    
    protected Connector connector;
    
    
    public Connector getConnector() {
		return connector;
	}
	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	/**
     * The default is true - the created threads will be
     *  in daemon mode. If set to false, the control thread
     *  will not be daemon - and will keep the process alive.
     */
    protected boolean daemon = true;
    public void setDaemon(boolean b) { daemon = b; }
    public boolean getDaemon() { return daemon; }


    /**
     * Name of the thread pool, which will be used for naming child threads.
     */
    protected String name = "TP";
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    
    /**
     * Current worker threads busy count.
     */
    protected int curThreadsBusy = 0;


    /**
     * Current worker threads count.
     */
    protected int curThreads = 0;


    /**
     * Sequence number used to generate thread names.
     */
    protected int sequence = 0;
    
	protected CCNConnector ccnconnector;
	
    public CCNConnector getCCNConnector() {
		return ccnconnector;
	}

	/**
     * Server socket port.
     */
    protected int port;
    public int getPort() { return port; }
    public void setPort(int port ) { this.port=port; }


    /**
     * Address for the server socket.
     */
    protected InetAddress address;
    public InetAddress getAddress() { return address; }
    public void setAddress(InetAddress address) { this.address = address; }

    
    public interface Handler {
        public boolean process(Interest interest);
    }
    /**
     * Handling of accepted sockets.
     */
    protected Handler handler = null;
    public void setHandler(Handler handler ) { this.handler = handler; }
    public Handler getHandler() { return handler; }
    
    public void init()
            throws Exception {

            if (initialized)
                return;
            
            // Initialize thread count defaults for acceptor
            if (acceptorThreadCount == 0) {
                acceptorThreadCount = 1;
            }
            
            initialized = true;
    }
    
    
    
    public void start()
            throws Exception {
            // Initialize socket if not done before
            if (!initialized) {
                init();
            }
            if (!running) {
                running = true;
                paused = false;

                // Create worker collection
                if (executor == null) {
                 workers = new WorkerStack(maxThreads);
                	//workers = new WorkerStack(1);
                }
  
        	ccnconnector = new CCNConnector();
        	//ccnconnectorthread = new Thread(ccnconnector);     	
        	ccnconnector.setCcnendpoint(this);
        	ccnconnector.setMapper(this.connector.getMapper());
           // ccnconnector.setUriHashMap(uriHashMap);
        //	ccnconnectorthread.start();
        	
        	writer = new FileWriter("/usr/test/mul/writerlog");
        	
                // Start acceptor threads
                for (int i = 0; i < acceptorThreadCount; i++) {
                	//nan: acceptor run() method will be called
                    Thread acceptorThread = new Thread(new CCNAcceptor(), getName() + "-Acceptor-" + i);
                    acceptorThread.setPriority(threadPriority);
                    acceptorThread.setDaemon(daemon);
                    acceptorThread.start();
                }
 
            }
        }
    
 /*   
    public CCNHandle getCcnhandle() {
		return ccnhandle;
	}
	public void setCcnhandle(CCNHandle ccnhandle) {
		this.ccnhandle = ccnhandle;
	}*/
	/**
     * Process given interest.
     */
    protected boolean processInterest(Interest interest) {
        try {
        	System.out.println("Process the Interest and ContentName is "+interest.getContentName().toString());
            if (executor == null) {
                getWorkerThread().assign(interest);
            } else {
               // executor.execute(new SocketProcessor(socket));
            }
        } catch (Throwable t) {
            // This means we got an OOM or similar creating a thread, or that
            // the pool and its queue are full
            log.error(sm.getString("endpoint.process.fail"), t);
            return false;
        }
        return true;
    }
    
    /**
     * Return a new worker thread, and block while to worker is available.
     */
    protected CCNWorker getWorkerThread() {
        // Allocate a new worker thread
        synchronized (workers) {
            CCNWorker workerThread;
            while ((workerThread = createWorkerThread()) == null) {
                try {
                    workers.wait();
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
            return workerThread;
        }
    }
    /**
     * Create (or allocate) and return an available processor for use in
     * processing a specific HTTP request, if possible.  If the maximum
     * allowed processors have already been created and are in use, return
     * <code>null</code> instead.
     */
    protected CCNWorker createWorkerThread() {

        synchronized (workers) {
            if (workers.size() > 0) {// nan: The worker instance available.
                curThreadsBusy++;
                
                return workers.pop();
            }
            if ((maxThreads > 0) && (curThreads < maxThreads)) {
                curThreadsBusy++;
                
                System.out.println("new Thread is created");
                
                if (curThreadsBusy == maxThreads) {
                    log.info(sm.getString("endpoint.info.maxThreads",
                            Integer.toString(maxThreads), address,
                            Integer.toString(port)));
                }
                return (newWorkerThread());
            } else {
                if (maxThreads < 0) {
                    curThreadsBusy++;
                    return (newWorkerThread());
                } else {
                    return (null);
                }
            }
        }

    }


    /**
     * Create and return a new processor suitable for processing HTTP
     * requests and returning the corresponding responses.
     */
    protected CCNWorker newWorkerThread() {

        CCNWorker workerThread = new CCNWorker();
        workerThread.start();
        return (workerThread);

    }




    /**
     * Recycle the specified Processor so that it can be used again.
     *
     * @param workerThread The processor to be recycled
     */
    protected void recycleWorkerThread(CCNWorker workerThread) {
        synchronized (workers) {
            workers.push(workerThread);
            curThreadsBusy--;
            workers.notify();
        }
    }

    
    

    /**
     * Server Interest acceptor thread.
     */
    protected class CCNAcceptor implements Runnable{
    	
       protected boolean initialize = false;

         /**
         * The background thread that listens for incoming TCP/IP connections and
         * hands them off to an appropriate processor.
         */
        public void run() {
        	
        	if(!initialize){
        		ccnconnector.openCcnHandle();
/*        	     try {
        				ccnhandle = CCNHandle.open();
        				ccnprefix = ContentName.fromURI(Constants.DEFAULT_CCN_URI);
        				ccnhandle.registerFilter(ccnprefix, this);
        			} catch (ConfigurationException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			} catch (MalformedContentNameStringException e) {	      
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}*/
        	     initialize = true;
        	     
        	}
            // Loop until we receive a shutdown command
          /*  while (running) {

                // Loop if endpoint is paused
                while (paused) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                }*/

                // Accept the next incoming connection from the server socket
             /*   try {
                	 
                    Socket socket = serverSocketFactory.acceptSocket(serverSocket);
                    serverSocketFactory.initSocket(socket);
                    // Hand this socket off to an appropriate processor
                    if (!processInterest(socket)) {
                        // Close socket right away
                        try {
                            socket.close();
                        } catch (IOException e) {
                            // Ignore
                        }
                    }
                }catch (Throwable t) {
                    log.error(sm.getString("endpoint.accept.fail"), t);
                }
              */
                // The processor will recycle itself when it finishes

           // }

        }

		//@Override
	/*	public boolean handleInterest(Interest interest) {
			
			// TODO Auto-generated method stub
			String uri = ccnInternalHandler.getUri(interest);
			
			if(uriHashMap.insertUri(uri, "CCNRequest:"+uri)){				
				processInterest(interest);
				return true;
			//	ishandle =true;
			}
			
			return false;
		}*/

    }
/**
 * 
 * Worker handle the specified request
 * 
 * @author Nan GuoShun
 * 
 * -------------------------------------Inner Class---------------------------------------------------------------
 */
  
    // ----------------------------------------------------- Worker Inner Class


    protected class CCNWorker implements Runnable {

        protected Thread thread = null;
        protected boolean available = false;
 //       protected Socket socket = null;
        protected Interest interest = null;
        
        /**
         * Process an incoming TCP/IP connection on the specified socket.  Any
         * exception that occurs during processing must be logged and swallowed.
         * <b>NOTE</b>:  This method is called from our Connector's thread.  We
         * must assign it to our own thread so that multiple simultaneous
         * requests can be handled.
         *
         * @param socket TCP socket to process
         */

        synchronized void assign(Interest interest) {
            // Wait for the Processor to get the previous Socket
            while (available) {
                try {
                    wait();

                } catch (InterruptedException e) {
                }
            }
            // Store the newly available Socket and notify our thread
            this.interest = interest;
            available = true;
            notifyAll();
            try {
				writer.write("assign a thread\n");
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
        
        /**
         * Await a newly assigned Socket from our Connector, or <code>null</code>
         * if we are supposed to shut down.
         */
        private synchronized Interest await() {
            // Wait for the Connector to provide a new Socket
            while (!available) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            // Notify the Connector that we have received this Socket
            Interest interest = this.interest;
            available = false;
            notifyAll();
            return (interest);

        }
        /**
         * The background thread that listens for incoming TCP/IP connections and
         * hands them off to an appropriate processor.
         */
        public void run() {

            // Process requests until we receive a shutdown signal
            while (running) {

                // Wait for the next socket to be assigned
                Interest interest = await();
                if (interest  == null)
                    continue;
                // Process the request from this socket
                if (handler.process(interest)) {
                }

                // Finish up this request
                interest = null;
                recycleWorkerThread(this);

            }

        }


        /**
         * Start the background processing thread.
         */
        public void start() {
            thread = new Thread(this);
            thread.setName(getName() + "-" + (++curThreads));
            thread.setDaemon(true);
            thread.start();
        }


    }
    
 /**
  * WorkerStack manage work thread.
  *   
  * @author Nan GuoShun
  *-------------------------------------Inner Class---------------------------------------------------------------
  */
    //nan: WorkStact manage work thread.
    public class WorkerStack {
        
        protected CCNWorker[] workers = null;
        protected int end = 0;
        
        public WorkerStack(int size) {
            workers = new CCNWorker[size];
        }
        
        /** 
         * Put the object into the queue. If the queue is full (for example if
         * the queue has been reduced in size) the object will be dropped.
         * 
         * @param   object  the object to be appended to the queue (first
         *                  element).
         */
        public void push(CCNWorker worker) {
        	
            if (end < workers.length) {
                workers[end++] = worker;
            } else {
                curThreads--;
            }
            try {
            	writer.write("Totoally "+end+" CCNWorker Threads\n");
				writer.write("push a CCNWorker thread\n");
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        /**
         * Get the first object out of the queue. Return null if the queue
         * is empty. 
         */
        public CCNWorker pop() {
            if (end > 0) {
                System.out.println("Pop thread and there are "+end+" Threads in the pool");
                return workers[--end];
            }
            try {
            	
				writer.write("pop a CCNWorker thread\n");
				writer.flush();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return null;
        }
        
        /**
         * Get the first object out of the queue, Return null if the queue
         * is empty.
         */
        public CCNWorker peek() {
            return workers[end];
        }
        
        /**
         * Is the queue empty?
         */
        public boolean isEmpty() {
            return (end == 0);
        }
        
        /**
         * How many elements are there in this queue?
         */
        public int size() {
            return (end);
        }
        
        /**
         * Resize the queue. If there are too many objects in the queue for the
         * new size, drop the excess.
         * 
         * @param newSize
         */
        public void resize(int newSize) {
            CCNWorker[] newWorkers = new CCNWorker[newSize];
            int len = workers.length;
            if (newSize < len) {
                len = newSize;
            }
            System.arraycopy(workers, 0, newWorkers, 0, len);
            workers = newWorkers;
        }
    }
    /*
	 * Tu20140901
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		 if (running) {
	            stop();
	        }
	        this.ccnconnector.destroy();
	        initialized = false ;
		
	}
	private void stop() {
		if (running) {
            running = false;
           // unlockAccept();
        }
		
	}

public boolean isPaused() {
	// TODO Auto-generated method stub
	return paused;
}
public void pause() {
    if (running && !paused) {
        paused = true;
        //unlockAccept();
    }
}
}
