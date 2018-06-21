package com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails;

public class IAVRequest {
	
	private String accountNumberMatchPrefix;
	private String accountNumberMatchSuffix;
	private ParamKeyValues paramKeyValues;
	private IAVPlusRequest iavPlusRequest;
	public String getAccountNumberMatchPrefix() {
		return accountNumberMatchPrefix;
	}
	public void setAccountNumberMatchPrefix(String accountNumberMatchPrefix) {
		this.accountNumberMatchPrefix = accountNumberMatchPrefix;
	}
	public String getAccountNumberMatchSuffix() {
		return accountNumberMatchSuffix;
	}
	public void setAccountNumberMatchSuffix(String accountNumberMatchSuffix) {
		this.accountNumberMatchSuffix = accountNumberMatchSuffix;
	}
	public ParamKeyValues getParamKeyValues() {
		return paramKeyValues;
	}
	public void setParamKeyValues(ParamKeyValues paramKeyValues) {
		this.paramKeyValues = paramKeyValues;
	}
	public IAVPlusRequest getIavPlusRequest() {
		return iavPlusRequest;
	}
	public void setIavPlusRequest(IAVPlusRequest iavPlusRequest) {
		this.iavPlusRequest = iavPlusRequest;
	}
	public IAVRequest() {
		super();
	}
	
	

}
