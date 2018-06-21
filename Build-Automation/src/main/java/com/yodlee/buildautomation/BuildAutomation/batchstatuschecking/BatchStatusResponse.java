package com.yodlee.buildautomation.BuildAutomation.batchstatuschecking;

public class BatchStatusResponse {
	private BatchReqDetailList[] batchReqDetailList;

	public BatchReqDetailList[] getBatchReqDetailList() {
		return batchReqDetailList;
	}

	public void setBatchReqDetailList(BatchReqDetailList[] batchReqDetailList) {
		this.batchReqDetailList = batchReqDetailList;
	}

	public BatchStatusResponse() {
		super();
	}
	
	
}
