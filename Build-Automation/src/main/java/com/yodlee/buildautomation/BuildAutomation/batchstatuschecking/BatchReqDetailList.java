package com.yodlee.buildautomation.BuildAutomation.batchstatuschecking;

public class BatchReqDetailList {
	private String reqInitiated;
	private String reqCompleted;
	private String batchStatusId;
	private String appReqId;
	private String userName;
	
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAppReqId() {
		return appReqId;
	}
	public void setAppReqId(String appReqId) {
		this.appReqId = appReqId;
	}
	public String getReqInitiated() {
		return reqInitiated;
	}
	public void setReqInitiated(String reqInitiated) {
		this.reqInitiated = reqInitiated;
	}
	public String getReqCompleted() {
		return reqCompleted;
	}
	public void setReqCompleted(String reqCompleted) {
		this.reqCompleted = reqCompleted;
	}
	public String getBatchStatusId() {
		return batchStatusId;
	}
	public void setBatchStatusId(String batchStatusId) {
		this.batchStatusId = batchStatusId;
	}
	public BatchReqDetailList() {
		super();
	}
	
	
}
