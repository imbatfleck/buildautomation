package com.yodlee.buildautomation.BuildAutomation.comparebatchdetails;

public class BatchResult {
	private String status1;
	private String status2;
	private String htmlDumpFileName1;
	private String htmlDumpFileName2;
	private String itemId;
	private String itemTypeId;
	private String responseType;
	private String sumInfoId;
	private String latency1;
	private String latency2;
	private String numAccts1;
	private String numAccts2;
	private String numNav1;
	private String numNav2;
	private String batchReqDetailsId1;
	private String batchReqDetailsId2;
	
	private XMLDifference xmlDiff;
	
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemTypeId() {
		return itemTypeId;
	}
	public void setItemTypeId(String itemTypeId) {
		this.itemTypeId = itemTypeId;
	}
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getSumInfoId() {
		return sumInfoId;
	}
	public void setSumInfoId(String sumInfoId) {
		this.sumInfoId = sumInfoId;
	}
	public String getLatency1() {
		return latency1;
	}
	public void setLatency1(String latency1) {
		this.latency1 = latency1;
	}
	public String getLatency2() {
		return latency2;
	}
	public void setLatency2(String latency2) {
		this.latency2 = latency2;
	}
	public String getNumAccts1() {
		return numAccts1;
	}
	public void setNumAccts1(String numAccts1) {
		this.numAccts1 = numAccts1;
	}
	public String getNumAccts2() {
		return numAccts2;
	}
	public void setNumAccts2(String numAccts2) {
		this.numAccts2 = numAccts2;
	}
	public String getNumNav1() {
		return numNav1;
	}
	public void setNumNav1(String numNav1) {
		this.numNav1 = numNav1;
	}
	public String getNumNav2() {
		return numNav2;
	}
	public void setNumNav2(String numNav2) {
		this.numNav2 = numNav2;
	}
	public String getBatchReqDetailsId1() {
		return batchReqDetailsId1;
	}
	public void setBatchReqDetailsId1(String batchReqDetailsId1) {
		this.batchReqDetailsId1 = batchReqDetailsId1;
	}
	public String getBatchReqDetailsId2() {
		return batchReqDetailsId2;
	}
	public void setBatchReqDetailsId2(String batchReqDetailsId2) {
		this.batchReqDetailsId2 = batchReqDetailsId2;
	}
	public XMLDifference getXmlDiff() {
		return xmlDiff;
	}
	public void setXmlDiff(XMLDifference xmlDiff) {
		this.xmlDiff = xmlDiff;
	}
	public String getStatus1() {
		return status1;
	}
	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	public String getStatus2() {
		return status2;
	}
	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	public String getHtmlDumpFileName1() {
		return htmlDumpFileName1;
	}
	public void setHtmlDumpFileName1(String htmlDumpFileName1) {
		this.htmlDumpFileName1 = htmlDumpFileName1;
	}
	public String getHtmlDumpFileName2() {
		return htmlDumpFileName2;
	}
	public void setHtmlDumpFileName2(String htmlDumpFileName2) {
		this.htmlDumpFileName2 = htmlDumpFileName2;
	}
	public BatchResult() {
		super();
	}
	
	
}
