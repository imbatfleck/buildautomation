package com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails;

public class DocDownloadRequest {
	private String durationType;
	private String taxDurationType;
	private boolean pfm;
	private boolean latest;
	private boolean allAccounts;
	private boolean docDownloadRequest;
	private StatementRequest statementRequest;
	public String getDurationType() {
		return durationType;
	}
	public void setDurationType(String durationType) {
		this.durationType = durationType;
	}
	public String getTaxDurationType() {
		return taxDurationType;
	}
	public void setTaxDurationType(String taxDurationType) {
		this.taxDurationType = taxDurationType;
	}
	public boolean isPfm() {
		return pfm;
	}
	public void setPfm(boolean pfm) {
		this.pfm = pfm;
	}
	public boolean isLatest() {
		return latest;
	}
	public void setLatest(boolean latest) {
		this.latest = latest;
	}
	public boolean isAllAccounts() {
		return allAccounts;
	}
	public void setAllAccounts(boolean allAccounts) {
		this.allAccounts = allAccounts;
	}
	public boolean isDocDownloadRequest() {
		return docDownloadRequest;
	}
	public void setDocDownloadRequest(boolean docDownloadRequest) {
		this.docDownloadRequest = docDownloadRequest;
	}
	public StatementRequest getStatementRequest() {
		return statementRequest;
	}
	public void setStatementRequest(StatementRequest statementRequest) {
		this.statementRequest = statementRequest;
	}
	public DocDownloadRequest() {
		super();
	}
	
	
	
	
	
}
