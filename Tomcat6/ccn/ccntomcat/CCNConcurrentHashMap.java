package ccntomcat;

/**
 * Hashmap that maintains request info
 */
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;

import org.ccnx.ccn.protocol.Interest;

public class CCNConcurrentHashMap<K, V> {
	int capacity;
	int concurrentlevel;
	float loadfactor;
	
	protected ConcurrentHashMap<K, V> hm = null;
	//protected ConcurrentHashMap<>
	protected Queue<Interest> requestqu = null;
	protected Queue<Interest> processqu = null;

	public Queue<Interest> getRequestqu() {
		return requestqu;
	}

	public Queue<Interest> getProcessqu() {
		return processqu;
	}

	public CCNConcurrentHashMap(int capacity, float loadfactor, int concurrentlevel){
		
		this.capacity = capacity;
		this.loadfactor = loadfactor;
		this.concurrentlevel = concurrentlevel;
		hm = new ConcurrentHashMap<K, V>(capacity,loadfactor,concurrentlevel);	
		//hm = new ConcurrentHashMap<String, String>(capacity,loadfactor,concurrentlevel);	
		//requestqu = new ConcurrentLinkedQueue<Interest>();
		//processqu = new ConcurrentLinkedQueue<Interest>();
//		List list = Collections.synchronizedList(qu);
	}
	
	public void createHashMap(){
		hm = new ConcurrentHashMap<K, V>(capacity,loadfactor,concurrentlevel);
	}
	
	public boolean addObject(K k, V v) {
		/**
		 * to avoid ccnx:/
		 */
		if (null == getObject(k)){
			if (null == hm.putIfAbsent(k, v)) {
				//requestqu.offer(interest);//add the uri to the queue
				return true;
			}
		//	return true;
		}
		return false;
	}
	
	public void printUri(){
		
		System.out.println("UriHasMap is:" + hm.toString());

	}
	
	public Object getObject(K k){
		return hm.get(k);
	}
	
	public Object remove(K k) {
		//qu.poll();
		//decrease();
		return hm.remove(k);
		
	}
	
	public boolean update(K k, V v){
		if(null == remove(k)){
			return false;
		}
		if(addObject(k, v)){
			return true;
		}
		return false;
	}
}
