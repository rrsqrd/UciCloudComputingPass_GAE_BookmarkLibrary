package com.rrsqrd.uci.bookmarkAppEngine.model;

public class BookmarkPost 
{
	private  String topic;
	private  String urlName;
	private  String urlStr;
	private  String category;	
	private  String modifyDate;	
	private  Long   entityKeyID;
	
	public static final String TOPIC          	= "topic";
	public static final String CATEGORY    	  	= "category";
	public static final String URL_NAME    		= "urlName";	
	public static final String URL_STR     		= "urlStr";
	public static final String MODIFY_DATE  	= "modifyDate";	
	public static final String ENTITY_KEY_ID  	= "entityKeyID";
		
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getUrlName() {return urlName;}
	public void   setUrlName(String urlName) {this.urlName = urlName;}
	
	public String getUrlStr() {return urlStr;}
	public void   setUrlStr(String urlStr) {this.urlStr = urlStr;}	

	public String getCategory() {return category;}
	public void   setCategory(String category) {this.category = category;}
	
	public String getModifyDate() {return modifyDate;}
	public void   setModifyDate(String modifyDate) {this.modifyDate = modifyDate;}
	
	public Long   getEntityKeyID() {return entityKeyID;}
	public void   setEntityKeyID(Long entityKeyID) {this.entityKeyID = entityKeyID;}
	

	@Override
	public String toString() {
	    return
	        "Topic: " + topic + ", Modify date: " + modifyDate +
	        ", Category: " + category +
	        "urlName: " + urlName + "urlStr: " + urlStr;
	}	

}
