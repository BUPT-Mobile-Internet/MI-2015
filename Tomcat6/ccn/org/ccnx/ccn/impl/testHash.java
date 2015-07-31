package org.ccnx.ccn.impl;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import org.ccnx.ccn.impl.CCNTrieTree.Vertex;
import org.ccnx.ccn.io.content.Collection.CollectionObject;
import org.ccnx.ccn.protocol.ContentObject;

/**
 * This is a hashtable, which uses component as key and uses Trietree as the value.
 *
 * @author Yukai Tu
 */

public class testHash {
	
	public testHash(){
		
	}
	protected static testHash testHash = new testHash();
	public static testHash getTestHash() {
		return testHash;
	}

	public static void setTestHash(testHash testHash) {
		org.ccnx.ccn.impl.testHash.testHash = testHash;
	}
	final static int HASH_NAME = 0;
	final static int TRIETREE_NAME = 1;
	final static int JUMPLIST_NAME = 2;
	final static int PROCESS_NAME = 3;
	static Date date = new Date();
	protected Hashtable<String, CCNTrieTree> hashtable = new Hashtable<String,CCNTrieTree>();
	//protected Hashtable<String, Integer> inttable = new Hashtable<String,Integer>();
	public static void main(String args[])
	{
		
		String str = "ccnx:/index.jsp/%FD%DS%DSF/%00%01";
		testHash.removeVersion(str);
		
		
		//System.out.println("insert node num:"+insertmaxnode+" distance:"+distance+"\ninserting uses " +(new Date().getTime()-t1)+"ms");
		/*for(int i=0;i<30;i++){
			String path = "/baidu/movie/Zh/jianxiaqingyuan/S1/"+i;
			String HostName=parsePath(path,HASH_NAME);
			String TrieTreeName = parsePath(path,TRIETREE_NAME);
			String JumpListName = parsePath(path,JUMPLIST_NAME);
			addPath(HostName,TrieTreeName,JumpListName,hashtable);
		}
		for(int i=0;i<30;i++){
			String path = "/baidu/story/Zh/jianxiaqingyuan/S1/"+i;
			String HostName=parsePath(path,HASH_NAME);
			String TrieTreeName = parsePath(path,TRIETREE_NAME);
			String JumpListName = parsePath(path,JUMPLIST_NAME);
			addPath(HostName,TrieTreeName,JumpListName,hashtable);
		}
		testhash.PrintPath("/baidu/movie/","Zh/guzhuang/jianxiaqingyuan/S1");
		
		int searchmaxnode = 30000;
		t1 = new Date().getTime();
		for(int i=0;i<searchmaxnode;i++){
			
			int randomint = (int) (Math.random()*(insertmaxnode+20000));
			String path = "/baidu/movie/Zh/guzhuang/jianxiaqingyuan/S1/"+randomint;
			String HostName=testhash.parsePath(path,HASH_NAME);
			String TrieTreeName = testhash.parsePath(path,TRIETREE_NAME);
			//String JumpListName = parsePath(path,JUMPLIST_NAME);
			String numofnode = randomint+"";
			//System.out.println(numofnode);
			Vertex vertex = testhash.FindPath(HostName,TrieTreeName);
			//CCNFileNode filenode = jumplist.findFileNode(numofnode);
			//if(filenode!=null){
				//filenode.print();
			//}
			randomint = (int) (Math.random()*(insertmaxnode+20000));
			numofnode = randomint+"";
			vertex.getVertexJumpList().deleteNode(numofnode);
			randomint = (int) (Math.random()*(insertmaxnode+20000));
			numofnode = randomint+"";
			//jumplist.insertNode(new CCNFileNode("Root/xiaoxia",numofnode,date.getTime()));
		
			
		}
		System.out.println("search+delete+insert node num:"+searchmaxnode+" distance:"+distance+"\ntime: " +(new Date().getTime()-t1)+"ms");
		
		//JumpList test = FindPath("/baidu/movie/","Zh/guzhuang/jianxiaqingyuan/S1",hashtable);
		//t1 = new Date().getTime();
		//test.findFileNode("23").print();
		//System.out.println("searching uses "+(new Date().getTime()-t1)+"ms");
		//DeletePath
		//System.out.println("\ndelete\n");
		//DeletePath("/baidu/movie/","Zh/guzhuang/jianxiaqingyuan/S1",hashtable);
		//PrintPath("/baidu/movie/","Zh/guzhuang/jianxiaqingyuan/S1",hashtable);
		//PrintPath(hashtable);
		
		*/
	}
	
	public String parsePath(String path,int type){
		int indexOfSlave = 0;
		if(path.startsWith("ccnx:"))
			path = path.substring(5);
		
		String HashPath,firstLeftString,TrieTreePath,JumpListPath;
		while(true){
			int lastSlave = path.lastIndexOf('/');
			String t = path.substring(lastSlave+1, path.length());		
			if(t.startsWith("%")||t.startsWith(".")){
				path = path.substring(0, lastSlave);
			}
			else{
				break;
			}
			
			
		}
		if(type==PROCESS_NAME){
			return path;
		}
		String tempString=path;
		indexOfSlave+= (tempString.indexOf('/')+1);
		if(indexOfSlave==0){
			System.out.println("wrong path to parse");
			return "";
		}
			
		tempString = tempString.substring(tempString.indexOf('/')+1);
		if(tempString==""){
			System.out.println("wrong path to parse");
			return "";
		}
		indexOfSlave+= (tempString.indexOf('/')+1);
	
		tempString = tempString.substring(tempString.indexOf('/')+1);
		if(tempString==""){
			System.out.println("wrong path to parse");
			return "";
		}
		indexOfSlave+= (tempString.indexOf('/')+1);
		
		tempString = tempString.substring(tempString.indexOf('/')+1);
		if(tempString==""){
			System.out.println("wrong path to parse");
			return "";
		}
		indexOfSlave+= (tempString.indexOf('/')+1);
		tempString = tempString.substring(tempString.indexOf('/')+1);
		if(tempString==""){
			System.out.println("wrong path to parse");
			return "";
		}
		//indexOfSlave+= (tempString.indexOf('/')+1);
		
		indexOfSlave+= (tempString.indexOf('/')+1);
		/*tempString = tempString.substring(tempString.indexOf('/')+1);
		if(tempString==""){
			System.out.println("wrong path to parse");
			return "";
		}
		indexOfSlave+= (tempString.indexOf('/')+1);*/
		//System.out.println(indexOfSlave);
		HashPath = path.substring(0, indexOfSlave);
		//System.out.println(HashPath);
		if(type == HASH_NAME){
			return HashPath;
		}
		firstLeftString = path.substring(indexOfSlave);
		//System.out.println(firstLeftString);
		
		/*indexOfSlave = 0;
		tempString = firstLeftString;
		indexOfSlave+= (tempString.indexOf('/')+1);
		tempString = tempString.substring(tempString.indexOf('/')+1);
		indexOfSlave+= (tempString.indexOf('/')+1);*/
		indexOfSlave = firstLeftString.lastIndexOf('/');
		//if(indexOfSlave==-1){
			//System.out.println("wrong path to parse");
			//return "";
		//}
		tempString = firstLeftString.substring(indexOfSlave+1);
		//System.out.println(tempString);
		if(tempString.startsWith("%")){
			firstLeftString = firstLeftString.substring(0,indexOfSlave);
		}
		JumpListPath = firstLeftString.substring(firstLeftString.lastIndexOf('/')+1);
	//	TrieTreePath = firstLeftString.substring(0,firstLeftString.lastIndexOf('/'));
		//System.out.println(HashPath);
		if(type == TRIETREE_NAME){
			return firstLeftString;
		}
		
		
		
		//System.out.println(firstLeftString);
		//System.out.println(JumpListPath);
		return JumpListPath;
	}
	
	public String removeVersion(String str){
		//ccnx:/index.jsp/%FD%DS%DSF/%00%01
		int begin = str.indexOf("%FD");
		int last = begin + str.substring(begin).indexOf('/');
		//System.out.println(str.substring(begin));
		//System.out.println(str.substring(begin,last));
		return str.replaceAll(str.substring(begin-1,last+1),"/");
	}
	
	public Vertex addPath(String HashName,String TrieTreeName,String JumpListName){
		
		//System.out.println(firstLeftString.substring(0,firstLeftString.lastIndexOf('/')));
		//long t1 = date.getTime();
		synchronized(this){
			if(hashtable.containsKey(HashName)){
				return hashtable.get(HashName).addPath(TrieTreeName, null);
			}
			else{
				hashtable.put(HashName, new CCNTrieTree());
				return hashtable.get(HashName).addPath(TrieTreeName, null);
			}
		}
		//System.out.println("�˴β����ʱ��"+(date.getTime()-t1));
	}
	public Vertex addPath(int jumpDistance,String HashName,String TrieTreeName){
		
		//System.out.println(firstLeftString.substring(0,firstLeftString.lastIndexOf('/')));
		//long t1 = date.getTime();
		synchronized(this){
			if(hashtable.containsKey(HashName)){
				return hashtable.get(HashName).addPath(TrieTreeName, null,jumpDistance);
			}
			else{
				hashtable.put(HashName, new CCNTrieTree());
				return hashtable.get(HashName).addPath(TrieTreeName, null,jumpDistance);
			}
		}
		//System.out.println("�˴β����ʱ��"+(date.getTime()-t1));
	}
	public Vertex addPath(){
		
		//System.out.println(firstLeftString.substring(0,firstLeftString.lastIndexOf('/')));
		//long t1 = date.getTime();
		String HashName="ccnx:/ccntomcat/";
		String TrieTreeName="default";
		int jumpDistance = 3;
		synchronized(this){
			if(hashtable.containsKey(HashName)){
				return hashtable.get(HashName).addPath(TrieTreeName, null,jumpDistance);
			}
			else{
				hashtable.put(HashName, new CCNTrieTree());
				return hashtable.get(HashName).addPath(TrieTreeName, null,jumpDistance);
			}
		}
		//System.out.println("�˴β����ʱ��"+(date.getTime()-t1));
	}
public Vertex addPath(String path){
		
		//System.out.println(firstLeftString.substring(0,firstLeftString.lastIndexOf('/')));
		//long t1 = date.getTime();
		String HashName="ccnx:/ccntomcat/";
		String TrieTreeName=this.parsePath(path, TRIETREE_NAME);
		int jumpDistance = 2;
		synchronized(this){
			if(hashtable.containsKey(HashName)){
				return hashtable.get(HashName).addPath(TrieTreeName, null,jumpDistance);
			}
			else{
				hashtable.put(HashName, new CCNTrieTree());
				return hashtable.get(HashName).addPath(TrieTreeName, null,jumpDistance);
			}
		}
		//System.out.println("�˴β����ʱ��"+(date.getTime()-t1));
	}
	
	
	
	public void PrintPath(String HashName,String TrieTreeName){
		synchronized(this){
			if(hashtable.containsKey(HashName)){
				hashtable.get(HashName).print(TrieTreeName);
			}
			else{
				return;
			}
		}
	}
	public void PrintPath(){
		synchronized(this){
			if(hashtable.size()==0){
				System.out.println("hash表为空");
				return;
			}
			Collection<CCNTrieTree> Collection = hashtable.values();
			Iterator<CCNTrieTree> it = Collection.iterator();
			System.out.println("print beginʼ");
			do{
				System.out.println(it.toString());
				CCNTrieTree trietree = it.next();
				trietree.print();
			}while(it.hasNext());
		}
		
	}
	/*
	 * 调用此方法的地方需要控制线程
	 */
	public Vertex FindPath(String HashName,String TrieTreeName){
		synchronized(this){
			if(hashtable.containsKey(HashName)){
				return hashtable.get(HashName).findPath(TrieTreeName);
			}
			else{
				return null;
			}
		}
	}
	public Vertex FindPath(String path){
		String hashName = testHash.parsePath(path, 0);
		String trieTreeName = testHash.parsePath(path, 1);
		synchronized(this){
			if(hashtable.containsKey(hashName)){
				return hashtable.get(hashName).findPath(trieTreeName);
			}
			else{
				return null;
			}
		}
	}
	public ContentObject FindPath(String HashName,String TrieTreeName,String NodeName){
		synchronized(this){
			if(hashtable.containsKey(HashName)){
				Vertex vertex = hashtable.get(HashName).findPath(TrieTreeName);
				if(vertex==null)
					return null;
				if(vertex.isExist()==true){
					CCNFileNode filenode = vertex.getVertexJumpList().findFileNode(NodeName);
					if(filenode!=null){
						return filenode.getCob();
					}
				}
				return null;
			}
			else{
				return null;
			}
		}
	}
	
	public void DeletePath(String HashName,String TrieTreeName){
		synchronized(this){
			if(hashtable.containsKey(HashName)){
				hashtable.get(HashName).deletePath(TrieTreeName);
				if(hashtable.get(HashName).getRoot().getNumOfChild()==0){
					hashtable.remove(HashName);
				}
			}
			else{
				
				return;
			}
		}
	}
	
	
}
