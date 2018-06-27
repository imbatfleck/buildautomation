package com.yodlee.buildautomation.BuildAutomation.batchcreation;

public class CBResponse {
	
	private String batchName;
	private String statusMsg;
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public CBResponse() {
		super();
	}
	

}
