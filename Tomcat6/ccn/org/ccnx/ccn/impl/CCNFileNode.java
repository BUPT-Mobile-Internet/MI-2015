package org.ccnx.ccn.impl;

import org.ccnx.ccn.profiles.SegmentationProfile;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.ContentObject;

public class CCNFileNode {
	private String filePath;
	private String fileName;
	private long fileID;
	private ContentName contentName;
	private ContentObject Cob;
	//private SegmentationProfile segprofile = new SegmentationProfile();
	
	public ContentObject getCob() {
		return Cob;
	}
	public void setCob(ContentObject cob) {
		Cob = cob;
	}
	/*CCNFileNode(){
		this("/Root","newFile",0);
	}
	CCNFileNode(String path,String name,long id){
		this.filePath = path;
		this.fileName = name;
		this.fileID = id;
		
	}
	CCNFileNode(CCNFileNode node){
		this.fileID = node.getFileID();
		this.fileName = node.getFileName();
		this.filePath = node.getFilePath();
	}*/
	CCNFileNode(ContentObject cob){
		//this.filePath = path;
		this.contentName = cob.getContentName();
		this.fileName = this.removeVersion(this.contentName.toString());
		this.fileID = SegmentationProfile.getSegmentNumber(this.contentName);
		this.Cob = cob;
	}
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	
	public long getFileID() {
		return fileID;
	}
	public void setFileID(long fileID) {
		this.fileID = fileID;
	}
	
	/*public int compare(FileNode node){
		if(this.fileID>node.getFileID()){
			return 1;
		}
		if(this.fileID<node.getFileID()){
			return -1;
		}
		if(this.fileID==node.getFileID())
			return 0;
		return 0;
	}*/
	public String getIdentify(){
		return this.fileName;
	}
	public int compare(CCNFileNode node){
		return this.fileName.compareTo(node.getFileName());
	}
	public int compare(String name){
		return this.fileName.compareTo(name);
	}
	public int compare(long id){
		if(this.fileID>id){
			return 1;
		}
		if(this.fileID<id){
			return -1;
		}
		if(this.fileID==id)
			return 0;
		return 0;
	}
	public void print(){
		//if()
		System.out.println("file name is:"+this.fileName);
	}
	public String removeVersion(String str){
		//ccnx:/index.jsp/%FD%DS%DSF/%00%01
		int begin = str.indexOf("%FD");
		if(begin==-1){
			return str;
		}
		int last = begin + str.substring(begin).indexOf('/');
		if(last == begin-1)
			return str;
		//System.out.println(str.substring(begin));
		//System.out.println(str.substring(begin,last));
		return str.replaceAll(str.substring(begin-1,last+1),"/");
	}
}
