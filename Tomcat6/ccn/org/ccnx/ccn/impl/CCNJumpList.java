package org.ccnx.ccn.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.ccnx.ccn.protocol.ContentObject;


public class CCNJumpList {
	
	/*
	 * Ĭ�ϵ���Ծ���
	 */
	final static int DEFAULT_DISTANCE = 2;

	/*
	 * ��������
	 */
	final double FLOW_RATIO = 0.10;
	
	/*
	 * ��Ծ���jumpDistance�Լ�setter��getter
	 */
	private int jumpDistance;
	public int getJumpDistance() {
		return jumpDistance;
	}

	public void setJumpDistance(int jumpDistance) {
		this.jumpDistance = jumpDistance;
	}
	
	/*
	 * ratio:���²����
	 * numOfLayer:��Ծ����Ĳ���
	 * compareTime:�Ƚϴ���
	 * eachLayerNodeNum ÿ�������нڵ�ĸ���
	 * headNodeList ���ÿ������ͷ���
	 */
	private double ratio;
	private int numOfLayer;
	//public int compareTime = 0;
	private List<Integer> eachLayerNodeNum = new ArrayList<>();
	private List<JumpListNode > headNodeList = new ArrayList<>();
	
	/*
	 * ���캯��
	 */
	public CCNJumpList(){
		this(DEFAULT_DISTANCE);
		
	}
	public CCNJumpList(int distance){
		this.jumpDistance = distance;
		this.ratio = 1.0/jumpDistance;
		this.numOfLayer = 0;
	}
	public boolean isEmpty(){
		if(this.numOfLayer==0)
			return true;
		else
			return false;
	}
	
	public int size(){
		if(numOfLayer!=0){
			return eachLayerNodeNum.get(0);
		}
		return 0;
	}
	
	public void clear(){
		if(numOfLayer!=0){
			removeLayer(numOfLayer);
		}
	}
	
	public JumpListNode getHead(){
		if(numOfLayer!=0){
			return headNodeList.get(0);
		}
		return null;
	}
	
	public CCNJumpList(CCNFileNode headElement){
		List<CCNFileNode> list = new ArrayList<CCNFileNode>();
		list.add(headElement);
		initList(DEFAULT_DISTANCE,list);
	}
	
	public CCNJumpList(int distance,CCNFileNode headElement){
		List<CCNFileNode> list = new ArrayList<CCNFileNode>();
		list.add(headElement);
		initList(distance,list);
	}
	
	/*public JumpList(FileNode[] elementList){
		initList(DEFAULT_DISTANCE,elementList);
		
	}
	
	public JumpList(int distance,FileNode[] elementList){
		initList(distance,elementList);
		
	}*/
	
	public CCNJumpList(List<CCNFileNode> nodeList){
		initList(DEFAULT_DISTANCE,nodeList);
		
	}
	public CCNJumpList(int distance ,List<CCNFileNode> nodeList){
		initList(distance,nodeList);
		
	}
	
	
	/*
	 * �����ʼ����ͬʱ������Ծ����
	 */
	/*private void initList(int distance,FileNode[] elementList){
		this.jumpDistance = distance;
		this.ratio = 1.0/jumpDistance;
		this.numOfLayer = 0;
		if(elementList!=null)
		{
			JumpListNode jumplistnode = buildNodeList(elementList);
			if(jumplistnode != null){
			this.headNodeList.add(jumplistnode);
			this.numOfLayer++;
			buildJumpList(this.numOfLayer);
			//this.eachLayerNodeNum.add(elementList.length);
			}
			
		}
	}*/
	private void initList(int distance,List<CCNFileNode> nodeList){
		this.jumpDistance = distance;
		this.ratio = 1.0/jumpDistance;
		this.numOfLayer = 0;
		if(nodeList!=null){
			JumpListNode jumplistnode = buildNodeList(nodeList);
			if(jumplistnode != null){
				this.headNodeList.add(jumplistnode);
				this.numOfLayer++;
				buildJumpList(this.numOfLayer);
			}
			//this.eachLayerNodeNum.add(elementList.length);
			
		}
		
	}
	private void initList(CCNFileNode nodeList){
		if(nodeList!=null){
			JumpListNode jumplistnode = new JumpListNode(nodeList);
			if(jumplistnode != null){
				this.headNodeList.add(jumplistnode);
				this.numOfLayer++;
				eachLayerNodeNum.add(1);
				//buildJumpList(this.numOfLayer);
			}
			//this.eachLayerNodeNum.add(elementList.length);
			
		}
		
	}
	
	
	/*public void sortNodeById(List<FileNode> fileNodeList){
		
		
	}
	
	public void sortNodeByName(List<FileNode> fileNodeList){
		
		
	}*/
	
	/*
	 * ��ʼ���ײ�����
	 * 
	 */
	/*private JumpListNode buildNodeList(FileNode[] element){
		JumpListNode headNode = null;
		if(element.length>0){
			headNode = new JumpListNode(element[0],1);
			JumpListNode tempNode = headNode;
			JumpListNode nextNode = headNode;
			for(int i=1;i<element.length;i++){
				nextNode = new JumpListNode(element[i],1);
				tempNode.addNode(nextNode);
				tempNode = nextNode;
			}
		}
		this.eachLayerNodeNum.add(element.length);
		return headNode;
	}*/
	private JumpListNode buildNodeList(List<CCNFileNode> fileNodeList){
		JumpListNode headNode = null;
		int num = 0;
		if(!fileNodeList.isEmpty()){
			Iterator<CCNFileNode> it = fileNodeList.iterator();
			//FileNode node = (FileNode)it.next();
			headNode = new JumpListNode((CCNFileNode)(it.next()));
			JumpListNode tempNode = headNode;
			JumpListNode nextNode = headNode;
			num++;
			while(it.hasNext()){
				nextNode = new JumpListNode((CCNFileNode)(it.next()));
				tempNode.addNextNode(nextNode,1);
				tempNode = nextNode;
				num++;
			}
		}
		if(headNode!=null)
			this.eachLayerNodeNum.add(num);
		return headNode;
	}
	
	/*
	 * ������Ծ����
	 */
	public boolean buildJumpList(int layer){
		if(layer<1&&numOfLayer<layer)
			return false;
		
		if(numOfLayer>layer){
			removeLayer(numOfLayer-layer);
			return buildJumpList(layer);
		}
		int preLayerNodeNum = eachLayerNodeNum.get(layer-1);
		if(preLayerNodeNum < jumpDistance+1){
			return true;
		}
		JumpListNode newHeadNode = headNodeList.get(layer-1);
		//JumpListNode newHeadNode =new JumpListNode(oldHeadNode.getFilenode(),layer+1);
		headNodeList.add(newHeadNode);
		//newHeadNode.addDownNode(newHeadNode,layer+1);
		int nodeNum = 1;
		int tempNum = 0;
		JumpListNode tempNode;
		tempNode = newHeadNode;
		JumpListNode tempNewListNode = newHeadNode;
		//Random random = new Random(new Date().getTime());
		for(int i=1;i<preLayerNodeNum;i++){
			tempNode = tempNode.getNextNode(layer);
			//System.out.println("radio:"+(double)nodeNum/i+" layer:"+layer);
			tempNum++;
			if(tempNum<this.jumpDistance){
				continue;
			}
			tempNum = 0;
			//double randnum = random.nextDouble();
			//System.out.println(randnum+"  "+ratio);
			//if(randnum<this.ratio||(((double)(nodeNum/i))>(ratio-FLOW_RATIO))){
				//JumpListNode newNextNode =tempNode;
			tempNewListNode.addNextNode(tempNode,layer+1);
				//tempNewListNode.addlayer(1);
			tempNewListNode = tempNewListNode.getNextNode(layer+1);
				//newNextNode.addDownNode(tempNode,layer+1);
			nodeNum++;
				
			//}
			
		}
		if(nodeNum == preLayerNodeNum)
		{
			headNodeList.remove(layer);
			return buildJumpList(layer);
		}
		if(nodeNum == 1){
			//headNodeList.get(layer).removeThisLayer(layer);
			headNodeList.remove(layer);
			return true;
		}
		eachLayerNodeNum.add(nodeNum);
		numOfLayer++;
		return buildJumpList(layer+1);
		
	}
	
	public boolean removeLayer(int num){
		if(num<0&&((numOfLayer-num)<0))
			return false;
		int toLayer = numOfLayer-num;
		for(int i=numOfLayer;i>toLayer;i--){
			eachLayerNodeNum.remove(i-1);
			JumpListNode jumplistnode = headNodeList.get(i-1);
			JumpListNode templistnode = jumplistnode;
			while(jumplistnode!=null){
				templistnode = jumplistnode;
				jumplistnode = jumplistnode.getNextNode(i);
				templistnode.removeThisLayer(i);
			}
			headNodeList.remove(i-1);
		}
		numOfLayer = toLayer;
		return true;
	}
	
	/*
	 * ��Ծ�����ӡ����
	 */
	public void printJumpList(){
		if(headNodeList==null){
			return;
		}
		System.out.println("total layer is"+numOfLayer);
		for(int i=0;i<numOfLayer;i++){
			System.out.println("Layer:"+(i+1)+"  node number:"+eachLayerNodeNum.get(i));
			printList(headNodeList.get(i),i+1);
		}
		
	}
	protected void printList(final JumpListNode headNode,int layer){
		if(headNode==null)
			return;
		//headNode.printNode();
		JumpListNode tempNode = headNode;
		while(tempNode!=null){
			tempNode.printNode();
			tempNode = tempNode.getNextNode(layer);
		}
	}
	
	/*
	 * ��Ծ�������ɾ�Ĳ�
	 */
	
	public void addToListNode(CCNFileNode node){
		if(this.numOfLayer==0)
		{
			initList(node);
			return;
		}
		JumpListNode jumplistnode = findNode(node.getIdentify(),headNodeList.get(numOfLayer-1),true,numOfLayer);
		if(jumplistnode==null){
			//jumplistnode = new JumpListNode(node,1);
			initList(node);
			return;
		}
		if(jumplistnode.getFilenode().compare(node)==0){
			return;
		}
		JumpListNode toadd = new JumpListNode(node);
		if(jumplistnode.getFilenode().compare(node)>0){
			if(jumplistnode.getPreNode(1)==null){
				headNodeList.set(0, toadd);
				toadd.addNextNode(jumplistnode,1);
			}
			else{
				jumplistnode.getPreNode(1).addNextNode(toadd,1);
				toadd.addNextNode(jumplistnode,1);
			}
			
		}else{
			if(jumplistnode.getNextNode(1)==null){
				jumplistnode.addNextNode(toadd,1);
			}
			else{
				toadd.addNextNode(jumplistnode.getNextNode(1),1);
				jumplistnode.addNextNode(toadd,1);
			}
		}
			
		eachLayerNodeNum.set(0,eachLayerNodeNum.get(0)+1);
		
		
	}
	
	public void add(ContentObject cob){
		insertNode(new CCNFileNode(cob));
	}
	
	public void toArray(){
		
	}
	
	public void insertNode(CCNFileNode node){
		if(this.numOfLayer==0)
		{
			initList(node);
			return;
		}
		//if(layer>this.)
		JumpListNode jumplistnode = findNode(node.getIdentify(),headNodeList.get(numOfLayer-1),true,numOfLayer);
		if(jumplistnode==null){
			//jumplistnode = new JumpListNode(node,1);
			initList(node);
			return;
		}
		if(jumplistnode.getFilenode().compare(node)==0){
			return;
		}
		JumpListNode toadd = new JumpListNode(node);
		if(jumplistnode.getFilenode().compare(node)>0){
			if(jumplistnode.getPreNode(1)==null){
				headNodeList.set(0, toadd);
				toadd.addNextNode(jumplistnode, 1);
			}
			else{
				jumplistnode.getPreNode(1).addNextNode(toadd,1);
				toadd.addNextNode(jumplistnode,1);
			}
			
		}else{
			if(jumplistnode.getNextNode(1)==null){
				jumplistnode.addNextNode(toadd,1);
			}
			else{
				toadd.addNextNode(jumplistnode.getNextNode(1),1);
				jumplistnode.addNextNode(toadd,1);
			}
		}
			
		eachLayerNodeNum.set(0,eachLayerNodeNum.get(0)+1);
		checkJumpList();
		//this.printJumpList();
		
	}
	
	/*public void deleteNode(long fileid){
		if(numOfLayer==0){
			return;
		}
		JumpListNode deletenode = findNode(fileid,headNodeList.get(numOfLayer-1),true);
		if(deletenode==null){
			return;
		}
		deleteNode(deletenode);
		//checkJumpList();
		
	}*/
	public void deleteNode(String filename){
		if(numOfLayer==0){
			return;
		}
		JumpListNode deletenode = findNode(filename,headNodeList.get(numOfLayer-1),true,numOfLayer);
		if(deletenode==null){
			return;
		}
		deleteNode(deletenode,deletenode.getLayer());
		checkJumpList();
		
	}
	public void deleteNode(JumpListNode node,int layer){
		if(node==null)
			return;
		if(node.getPreNode(layer)==null&&node.getNextNode(layer)==null){
			removeLayer(numOfLayer-layer+1);
			return;
		}
		if(node.getPreNode(layer)==null){
			headNodeList.set(layer-1, node.getNextNode(layer));
			node.getNextNode(layer).addPreNode(null, layer);
		}
		else if(node.getNextNode(layer)==null){
			node.getPreNode(layer).addNextNode(null, layer);
		}
		else{
			node.getPreNode(layer).addNextNode(node.getNextNode(layer),layer);
			node.getNextNode(layer).addPreNode(node.getPreNode(layer),layer);
		}
		
		node.removeThisLayer(layer);
		eachLayerNodeNum.set(layer-1, eachLayerNodeNum.get(layer-1)-1);
		
		if(eachLayerNodeNum.get(layer-1)<2){
			removeLayer(numOfLayer-layer+1);
		}
		//node.sublayer(1);
		deleteNode(layer==1?null:node,layer-1);
		
		
	}
	public void checkJumpList(){
		if(numOfLayer==0)
			return;
		if(numOfLayer==1&&eachLayerNodeNum.get(0)<this.jumpDistance+1)
			return;
		else if(numOfLayer==1){
			buildJumpList(1);
			return;
		}
		double deleteRatio;
		//int tempLayer;
		for(int i=0;i<numOfLayer-1;i++){
			//tempLayer = numOfLayer - i;
			deleteRatio = ((double)eachLayerNodeNum.get(i+1))/(eachLayerNodeNum.get(i));
			if(deleteRatio>ratio+FLOW_RATIO||deleteRatio<ratio-FLOW_RATIO){
				buildJumpList(i+1);
				return;
			}
		}
		//deleteRatio = ((double)eachLayerNodeNum.get(layer-1))/(eachLayerNodeNum.get(layer-2));
		
	}
	
	public JumpListNode findNode(String filename){
		if(filename.isEmpty()){
			return null;
		}
		JumpListNode node = findNode(filename,headNodeList.get(numOfLayer-1),true,numOfLayer);
		if(node.getFilenode().compare(filename)==0){
			return node;
		}else{
			return null;
		}
		
	}
	
	public CCNFileNode findFileNode(String filename){
		if(filename.isEmpty()){
			return null;
		}
		JumpListNode jumplistnode = findNode(filename,headNodeList.get(numOfLayer-1),true,numOfLayer);
		if(jumplistnode == null)
			return null;
		CCNFileNode node = jumplistnode.getFilenode();
		if(node.compare(filename)==0){
			return node;
		}else{
			return null;
		}
	}
	
	public JumpListNode findNode(String filename,JumpListNode node,boolean isBack,int layer){
		if(node == null){
			return null;
		}
		JumpListNode tempNode = node;
		JumpListNode tempPBNode = node;
		//int numOfThisLayer = eachLayerNodeNum.get(layer);
		if(isBack){
			while(tempNode!=null){
				//compareTime++;
				//System.out.println("filename:"+tempNode.getFilenode().getFileName()+"\ncaompere to"+tempNode.getFilenode().getFileName().compareTo(filename));
				if(tempNode.getFilenode().compare(filename)<0){
					tempPBNode = tempNode;
					tempNode = tempNode.getNextNode(layer);
					continue;
				}
				if(tempNode.getFilenode().compare(filename)>0){
					if((tempNode = findNode(filename,layer==1?null:tempNode,false,layer-1))==null){
						return tempPBNode;
					}else{
						return tempNode;
					}
				}
				if(tempNode.getFilenode().compare(filename)==0){
					return tempNode;
				}
				//tempNode = tempNode.getNextNode();
				
			}
			//if(tempNode.getFilenode().getFileName().compareTo(filename)<0)
			if((tempNode = findNode(filename,layer==1?null:tempPBNode,true,layer-1))==null){
				return tempPBNode;
			}else{
				return tempNode;
			}
		
		}else{
			while(tempNode!=null){
				//System.out.println("filename:"+tempNode.getFilenode().getFileName()+"\ncaompere to"+tempNode.getFilenode().getFileName().compareTo(filename));
				if(tempNode.getFilenode().compare(filename)>0){
					tempPBNode = tempNode;
					tempNode = tempNode.getPreNode(layer);
					continue;
				}
				if(tempNode.getFilenode().compare(filename)<0){
					if((tempNode=findNode(filename,layer==1?null:tempNode,true,layer-1))==null){
						return tempPBNode;
					}else{
						return tempNode;
					}
				}
				if(tempNode.getFilenode().compare(filename)==0){
					return tempNode;
				}
				
			}
			//if(tempNode.getFilenode().getFileName().compareTo(filename)>0)
				if((tempNode=findNode(filename,layer==1?null:tempPBNode,false,layer-1))==null){
					return tempPBNode;
				}else{
					return tempNode;
				}
		}
		
		
		
		
	}
	
	/*public JumpListNode findNode(long fileid){
		if(fileid < 0){
			return null;
		}
		JumpListNode node = findNode(fileid,headNodeList.get(numOfLayer-1),true);
		if(node==null)
			return null;
		if(node.getFilenode().compare(fileid)==0){
			return node;
		}else{
			return null;
		}
		
	}
	public FileNode findFileNode(long fileid){
		if(fileid < 0){
			return null;
		}
		compareTime=0;
		JumpListNode jumplistnode = findNode(fileid,headNodeList.get(numOfLayer-1),true);
		if(jumplistnode == null)
			return null;
		FileNode node = jumplistnode.getFilenode();
		if(node.compare(fileid)==0){
			return node;
		}else{
			return null;
		}
	}
	
	public JumpListNode findNode(long fileid,JumpListNode node,boolean isBack){
		if(node == null){
			return null;
		}
		JumpListNode tempNode = node;
		JumpListNode tempPBNode = node;
		//int compareTime = 0;
		//System.out.println("������һ��");
		//int numOfThisLayer = eachLayerNodeNum.get(layer);
		if(isBack){
			while(tempNode!=null){
				compareTime++;
				//System.out.println("filename:"+tempNode.getFilenode().getFileName()+"\ncaompere to"+tempNode.getFilenode().getFileName().compareTo(filename));
				if(tempNode.getFilenode().compare(fileid)<0){
					tempPBNode = tempNode;
					tempNode = tempNode.getNextNode();
					continue;
				}
				if(tempNode.getFilenode().compare(fileid)>0){
					if((tempNode = findNode(fileid,tempNode.getDownNode(),false))==null){
						return tempPBNode;
					}else{
						return tempNode;
					}
				}
				if(tempNode.getFilenode().compare(fileid)==0){
					return tempNode;
				}
				//tempNode = tempNode.getNextNode();
				
			}
			//if(tempNode.getFilenode().getFileName().compareTo(filename)<0)
			 if((tempNode = findNode(fileid,tempPBNode.getDownNode(),true))==null){
				 return tempPBNode;
			 }else{
				 return tempNode;
			 }
		
		}else{
			while(tempNode!=null){
				compareTime++;
				//System.out.println("filename:"+tempNode.getFilenode().getFileName()+"\ncaompere to"+tempNode.getFilenode().getFileName().compareTo(filename));
				if(tempNode.getFilenode().compare(fileid)>0){
					tempPBNode = tempNode;
					tempNode = tempNode.getPreNode();
					continue;
				}
				if(tempNode.getFilenode().compare(fileid)<0){
					if((tempNode = findNode(fileid,tempNode.getDownNode(),true))==null){
						return tempPBNode;
					}else{
						return tempNode;
					}
				}
				if(tempNode.getFilenode().compare(fileid)==0){
					return tempNode;
				}
				//tempNode = tempNode.getNextNode();
				
			}
			//if(tempNode.getFilenode().getFileName().compareTo(filename)>0)
				if((tempNode = findNode(fileid,tempPBNode.getDownNode(),false))==null)
					return tempPBNode;
				else
					return tempNode;
		}*/
		//return tempPBNode;
		
		
		
		
	
	
	
	@SuppressWarnings("hiding")
	/*
	 * ��Ծ����Ľڵ�
	 */
	 public class JumpListNode{
		public JumpListNode(){
			//FileNode node = null;
			this(null);
		}
		/*public JumpListNode(FileNode element){
			this.filenode = element;
			this.setDownNode(null);
			this.setNextNode(null);
			this.setPreNode(null);
		}*/
		public JumpListNode(CCNFileNode element){
			this.filenode = element;
			//this.setDownNode(null);
			//this.setNextNode(null);
			//this.setPreNode(null);
			this.layer = 1;
		}
		private CCNFileNode filenode;
		private int layer;
		public void addlayer(int addnum){
			this.layer+=addnum;
		}
		public void sublayer(int subnum){
			this.layer-=subnum;
			if(this.layer<0){
				layer = 0;
			}
		}
		public void setLayer(int layer) {
			this.layer = layer;
		}
		public int getLayer() {
			return layer;
		}
		public CCNFileNode getFilenode() {
			return filenode;
		}
		private List<JumpListNode> nextNodeList = new ArrayList<JumpListNode>();
		public List<JumpListNode> getNextNodeList() {
			return this.nextNodeList;
		}
		public void setNextNodeList(List<JumpListNode> nextNodeList) {
			this.nextNodeList = nextNodeList;
		}
		public List<JumpListNode> getPreNodeList() {
			return this.preNodeList;
		}
		public void setPreNodeList(List<JumpListNode> preNodeList) {
			this.preNodeList = preNodeList;
		}
		
		private List<JumpListNode> preNodeList = new ArrayList<JumpListNode>();
		//private List<JumpListNode> downNodeList = new ArrayList<JumpListNode>(); 
		
		/*public List<JumpListNode> getDownNodeList() {
			return this.downNodeList;
		}
		public void setDownNodeList(List<JumpListNode> downNodeList) {
			this.downNodeList = downNodeList;
		}*/
		public void setNextNode(JumpListNode node,int layer){
			if(layer>this.layer){
				if(layer==(this.layer+1)){
					this.layer++;
				}
				else{
					System.out.print("error JumpListNode.addNode outofbounds!");
					return;
				}
			}
			if(this.nextNodeList.size()<layer)
				this.nextNodeList.add(node);
			else
				this.nextNodeList.set(layer-1, node);
			//return;
			if(node==null)
				return;
			//node.setPreNode(this, layer);
			
		}
		public void addNextNode(JumpListNode node,int layer){
			if(layer>this.layer){
				if(layer==(this.layer+1)){
					this.layer++;
				}
				else{
					System.out.print("error JumpListNode.addNode outofbounds!");
					return;
				}
			}
			if(this.nextNodeList.size()<layer)
				this.nextNodeList.add(node);
			else
				this.nextNodeList.set(layer-1, node);
			//return;
			if(node==null)
				return;
			node.setPreNode(this, layer);
			
		}
		public void removeThisLayer(int layer){
			if(layer == this.layer){
				if(layer<1){
					return;
				}
				if(this.nextNodeList.size()==layer){
					this.nextNodeList.remove(layer-1);
				}
				if(this.preNodeList.size()==layer){
					this.preNodeList.remove(layer-1);
					/*if(layer>1){
						this.downNodeList.remove(layer-2);
					}*/
					
				}
				this.layer--;
				return;
			}
			
		}
		
		/*public void addDownNode(JumpListNode node,int layer){
			if(layer==1){
				System.out.println("bottom node can't have down node!");
				return;
			}
			if(layer>(this.layer)){
				if(layer==(this.layer+1)){
					this.layer++;
				}
				else{
					System.out.print("error JumpListNode.addNode outofbounds!");
					return;
				}
			}
			if(this.downNodeList.size()<layer-1)
				this.downNodeList.add(node);
			else
				this.downNodeList.set(layer-2, node);
			//node.addPreNode(this, layer);
			
		}*/
		
		public JumpListNode getNextNode(int layer){
				if(layer>this.layer){
					System.out.print("error JumpListNode.getNextNode outOfBounds");
					return null;
				}
				while(this.nextNodeList.size()<layer){
					nextNodeList.add(null);
				}
				
				return this.nextNodeList.get(layer-1);
			
		}
		/*public JumpListNode getDownNode(int layer){
			if(layer<2){
				System.out.print("error JumpListNode.getNextNode outOfBounds");
				return null;
			}
			if(layer>this.layer){
				System.out.print("error JumpListNode.getNextNode outOfBounds");
				return null;
			}
			if(downNodeList.size()<layer-1){
				downNodeList.add(null);
			}
			return this.downNodeList.get(layer-2);
		
	}*/
		public JumpListNode getPreNode(int layer){
			if(layer>this.layer){
				System.out.print("error JumpListNode.getNextNode outOfBounds");
				return null;
			}
			while(preNodeList.size()<layer){
				preNodeList.add(null);
			}
				return this.preNodeList.get(layer-1);
		
	}
		public void setPreNode(JumpListNode node,int layer){
			if(layer>this.layer){
				if(layer==(this.layer+1)){
					this.layer++;
				}
				else{
					System.out.print("error JumpListNode.addPreNode outofbounds!");
					return;
				}
			}
			if(this.preNodeList.size()<layer)
				this.preNodeList.add(node);
			else
				this.preNodeList.set(layer-1, node);
			//return;
			//node.setPreNode(this);
			
		}
		
		public void addPreNode(JumpListNode node,int layer){
			if(layer>this.layer){
				if(layer==(this.layer+1)){
					this.layer++;
				}
				else{
					System.out.print("error JumpListNode.addPreNode outofbounds!");
					return;
				}
			}
			if(this.preNodeList.size()<layer)
				this.preNodeList.add(node);
			else
				this.preNodeList.set(layer-1, node);
			//return;
			//node.setPreNode(this);
			if(node==null)
				return;
			node.setNextNode(this,layer);
			
		}
		
		public void printNode(){
			System.out.println("file name is:"+filenode.getFileName());
		}
		
	}
	
	/*public static void main(String args[]){
		//FileNode[] fileNodeList;
		List<FileNode> fileNodeList = new ArrayList<FileNode>();
		JumpList jumplist = new JumpList(3);
		for(int i=0;i<60;i++){
			String name = "xianjian"+i;
			String path = "Root/xiaoxia/";
			long id = 1221201+i;
			FileNode node = new FileNode(path,name,id);
			//fileNodeList.add(node);
			jumplist.insertNode(node);
		}
		
		//jumplist.buildJumpList(1);
		jumplist.printJumpList();
		System.out.println("Delete begin");
		/*
		 * insert test
		 
		jumplist.deleteNode("xianjian9");
		jumplist.deleteNode("xianjian0");
		FileNode insertTestNode1 = new FileNode("Root/xiaoxia/","xianjian30",1221233);
		
		jumplist.insertNode(insertTestNode1);
		for(int i=0;i<30;i++){
			String name = "xianjianjiang"+i;
			String path = "Root/xiaoxia/";
			long id = 1221231+i;
			FileNode node = new FileNode(path,name,id);
			//fileNodeList.add(node);
			jumplist.insertNode(node);
		}
		jumplist.printJumpList();
		for(int i=0;i<30;i++){
			
			String name = "xianjianjiang"+i;
			//fileNodeList.add(node);
			jumplist.deleteNode(name);
		}
		jumplist.printJumpList();
		//int a= 0;
		System.out.println("xianjian8".compareTo("xianjian10"));
		/*FileNode node = jumplist.findFileNode(1221220);
		if(node!=null){
			System.out.println("�Ƚϴ���"+jumplist.compareTime);
			node.print();
		}
		else
		{
			System.out.println("file doesn't exsit");
		}
		node = jumplist.findFileNode("xianjian08");
		if(node!=null){
			System.out.println("�Ƚϴ���"+jumplist.compareTime);
			node.print();
		}
		else
		{
			System.out.println("file doesn't exsit");
		}
		*/
		
		
		
		//String a = "xiaoxia0";
		//String b = "xiaoxia1";
	
		//System.out.println(b.compareTo(a));
	//}*/
	
	
		
	
	
}



