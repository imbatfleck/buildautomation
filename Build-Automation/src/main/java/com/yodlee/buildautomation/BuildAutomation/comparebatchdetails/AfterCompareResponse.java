package com.yodlee.buildautomation.BuildAutomation.comparebatchdetails;

public class AfterCompareResponse {
	
	private String finalHTML;
	private boolean isWrote;
	public String getFinalHTML() {
		return finalHTML;
	}
	public void setFinalHTML(String finalHTML) {
		this.finalHTML = finalHTML;
	}
	public boolean isWrote() {
		return isWrote;
	}
	public void setWrote(boolean isWrote) {
		this.isWrote = isWrote;
	}
	public AfterCompareResponse() {
		super();
	}
	
	

}
