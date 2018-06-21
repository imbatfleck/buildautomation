package com.build.dapmaster.DAPMASTER.batchdetails;

public class BatchDetails {
	private static BatchDetails batchDetails=new BatchDetails();
	private String batchName;
	private String batchID;
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getBatchID() {
		return batchID;
	}
	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}
	public BatchDetails() {
		super();
	}
	
	public BatchDetails setBatchDetails(String batchName,String batchID)
	{
		batchDetails.setBatchName(batchName);
		batchDetails.setBatchID(batchID);
		return batchDetails;
	}
	

}
