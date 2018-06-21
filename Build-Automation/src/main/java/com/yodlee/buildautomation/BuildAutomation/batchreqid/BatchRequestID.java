package com.yodlee.buildautomation.BuildAutomation.batchreqid;

public class BatchRequestID {
	private static BatchRequestID batchRequestID=new BatchRequestID();
	private String batchName;

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public BatchRequestID() {
		super();
	}
	
	public static BatchRequestID setBatchRequestIDParam(String batchName)
	{
		batchRequestID.setBatchName(batchName);
		return batchRequestID;
	}

}
