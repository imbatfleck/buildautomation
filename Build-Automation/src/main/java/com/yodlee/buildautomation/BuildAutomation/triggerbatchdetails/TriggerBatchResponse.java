package com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails;

public class TriggerBatchResponse {
	
	private String statusMsg;
	private String appRequestId;
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public String getAppRequestId() {
		return appRequestId;
	}
	public void setAppRequestId(String appRequestId) {
		this.appRequestId = appRequestId;
	}
	public TriggerBatchResponse() {
		super();
	}
	

}
