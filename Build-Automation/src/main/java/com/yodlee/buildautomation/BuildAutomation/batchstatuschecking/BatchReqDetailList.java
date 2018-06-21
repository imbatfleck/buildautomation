package com.yodlee.buildautomation.BuildAutomation.batchstatuschecking;

public class BatchReqDetailList {
	private String reqInitiated;
	private String reqCompleted;
	private String batchStatusId;
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
