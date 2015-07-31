package org.ccnx.ccn.impl;
import java.util.ArrayList;
import java.util.List;


public class CCNTrieTree {
	
	
	final int DEFAULT_DISTANCE = 2;
	final int PATH_EXISTED = 2;
	final int PATH_CREATE_SUCCEED = 1;
	final int PATH_CREATE_FAIL = 0;
	final int CAN_DELETE = 1;
	final int CANNOT_DELETE = 0;
	final int PATH_NO_EXIST = -1;
	private int JumpDistance;
	private Vertex root;
	public Vertex getRoot() {
		return root;
	}

	public void setRoot(Vertex root) {
		this.root = root;
	}




	public class Vertex{
		private int numOfChild;
		public int getNumOfChild() {
			return numOfChild;
		}
		public void setNumOfChild(int numOfChild) {
			this.numOfChild = numOfChild;
		}
		private boolean isExist;
		private CCNJumpList vertexJumpList;
	
		public CCNJumpList getVertexJumpList() {
			return vertexJumpList;
		}
		public void setVertexJumpList(CCNJumpList vertexJumpList) {
			this.vertexJumpList = vertexJumpList;
		}
		public boolean isExist() {
			return isExist;
		}
		public void setExist(boolean isExist) {
			this.isExist = isExist;
		}
		protected Vertex[] edges;
		Vertex(){
			//this.vertexJumpList = new CCNJumpList();
			isExist = false;
			numOfChild = 0;
			vertexJumpList=null;
			edges = new Vertex[123];
			for(int i=0;i<edges.length;i++){
				edges[i] = null;
			}
		}
		Vertex(CCNFileNode node){
			isExist = false;
			numOfChild = 0;
			vertexJumpList=null;
			//this.vertexJumpList = new CCNJumpList(node);
			edges = new Vertex[123];
			for(int i=0;i<edges.length;i++){
				edges[i] = null;
			}
		}
		
	}
	
	public CCNTrieTree(){
		root = new Vertex();
		this.JumpDistance = DEFAULT_DISTANCE;
	}
	public CCNTrieTree(int jumpDistance){
		root = new Vertex();
		this.JumpDistance = jumpDistance;
	}
		
	public void checkTree(){
		if(root == null){
			root = new Vertex();
		}
	}
	
	public void deletePath(String path){
		checkTree();
		Vertex vertex = findPath(path);
		if(vertex == null){
			return;
		}
		else{
			deletePath(root,path);
		}
	}
	
	public int deletePath(Vertex vertex,String path){
		System.out.println(path);
		if(path.length()==0){
			if(vertex.isExist()){
				System.out.println("pathExist");
				if(vertex.getNumOfChild()==1){
					vertex.setVertexJumpList(null);
					vertex.setExist(false);
					return CAN_DELETE;
				}
				vertex.setExist(false);
				return CANNOT_DELETE;
			}
			//vertex.setExist(true);
			//if(filenode!=null)
			//	vertex.setVertexJumpList(new CCNJumpList(filenode));
			//else
			//	vertex.setVertexJumpList(new CCNJumpList());
			//return PATH_CREATE_SUCCEED;
		}
		char c = path.charAt(0);
		System.out.println("c"+c);
		if(vertex.edges[c] == null){
			return PATH_NO_EXIST;
		}
		int type = deletePath(vertex.edges[c], path.substring(1));
		if(type == CAN_DELETE){
			vertex.edges[c] = null;
			vertex.setNumOfChild(vertex.getNumOfChild()-1);
			if(vertex.getNumOfChild()==0){
				vertex.setVertexJumpList(null);
				vertex.setExist(false);
				return CAN_DELETE;
			}
		}
		if(type == PATH_NO_EXIST){
			return PATH_NO_EXIST;
		}
		return CANNOT_DELETE;
	}
	
	public Vertex addPath(String path,int distance){
		this.JumpDistance = distance;
		checkTree();
		return addPath(root,path,null);
	}
	
	
	public Vertex addPath(String path,CCNFileNode filenode,int distance){
		this.JumpDistance = distance;
		checkTree();
		return addPath(root,path,filenode);
	}
	public Vertex addPath(String path,CCNFileNode filenode){
		this.JumpDistance = DEFAULT_DISTANCE;
		checkTree();
		return addPath(root,path,filenode);
	}
	
	public Vertex addPath(Vertex vertex,String path,CCNFileNode filenode){
		//System.out.println(path);
		if(path.length()==0){
			if(vertex.isExist()){
				//System.out.println("pathExist");
				//filenode.print();
				if(filenode!=null){
					vertex.getVertexJumpList().insertNode(filenode);
					return vertex;
				}
				return vertex;
			}
			vertex.setExist(true);
			if(filenode!=null)
				vertex.setVertexJumpList(new CCNJumpList(this.JumpDistance,filenode));
			else
				vertex.setVertexJumpList(new CCNJumpList());
			return vertex;
		}
		char c = path.charAt(0);
		//System.out.println("c"+c);
		if(vertex.edges[c] == null){
			vertex.edges[c] = new Vertex();
			vertex.setNumOfChild(vertex.getNumOfChild()+1);
		}
		return addPath(vertex.edges[c], path.substring(1),filenode);
	}
	
	public void print(String path){
		Vertex vertex = findPath(path);
		if(vertex!=null){
			vertex.getVertexJumpList().printJumpList();
			//List<String> printTreeList = this.listAllPaths();
			//System.out.println(printTreeList.toString());
			
		}
		else{
			System.out.println("List don't exist");
		}
		
	}
	public void print(){
		List<String> printTreeList = this.listAllPaths();
		//System.out.println(printTreeList.toString());
		for(int i=0;i<printTreeList.size();i++){
			Vertex vertex = findPath(printTreeList.get(i));
			if(vertex!=null){
				System.out.println("·��"+printTreeList.get(i)+":");
				vertex.getVertexJumpList().printJumpList();
			}
		}
		
	}
	
	public List< String> listAllPaths(){
		List< String> paths = new ArrayList< String>();
		checkTree();
		Vertex[] edges = root.edges;
		//System.out.println(edges.length);
		for(int i=0;i<edges.length;i++){
			if(edges[i]!=null){
				String pathName = ""+(char)i;
				//System.out.println("pathName"+pathName);
				depthFirstSearch(paths,edges[i],pathName);
			}
		}
		return paths;
	}
	private void depthFirstSearch(List<String> paths,Vertex edge,String pathName){
		
		Vertex[] edges = edge.edges;
		boolean hasChild = false;
		for(int i=0; i<edges.length;i++){
			if(edges[i]!=null){
				hasChild = true;
				String pathn = pathName;
				pathn+=(char)i;
				depthFirstSearch(paths,edges[i],pathn);
			}
		}
		if(!hasChild){
			//System.out.println("pathName"+pathName);
			paths.add(pathName);
		}
		
	}
	
	
	public Vertex findNodeByPath(String path){
		checkTree();
		
		return findPath(path,root); 
		
		
	}
	public Vertex findPath(String path){
		checkTree();
		
		return findPath(path,root); 
		
		
	}
	
	private Vertex findPath(String path,Vertex vertex){
		if(path.length()==0){
			if(vertex.isExist==true)
				return vertex;
			else
				return null;
		}
		char c = path.charAt(0);
		Vertex[] edges = vertex.edges;
		if(edges[c]!=null){
			return findPath(path.substring(1),edges[c]);
		}
		else{
			return null;
		}
	}
	
	
	/*public static void main(String args[]){
		TrieTree trietree = new TrieTree();
		trietree.addPath("/Zh/S1");
		trietree.addPath("/Zh/S2");
		trietree.addPath("/Zh/S2");
		trietree.addPath("/Zh/guzhuang/S1");
		trietree.addPath("/Zh/guzhuang/S2");
		List<String> show = trietree.listAllPaths();
		System.out.println(show.toString());
		System.out.println(trietree.findPath("/Zh/xiaoxia"));
		System.out.println(trietree.findPath("/Zh/S1"));
		System.out.println(trietree.findPath("/Zh/guzhuang/S1"));
		System.out.println(trietree.findPath("/Zh/guzhuang/S5"));
		System.out.println(trietree.findPath("/Zh/guzhuang/"));
		
		
	}*/
}
