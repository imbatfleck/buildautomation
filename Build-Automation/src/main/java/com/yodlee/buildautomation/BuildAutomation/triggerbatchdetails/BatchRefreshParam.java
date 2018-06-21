package com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails;

public class BatchRefreshParam {
	
	private String serverType;
	private String[] requestTypes;
	private String customrefreshRoute;
	private String agentFileType;
	private String refreshRoute;
	private boolean ignoreWarnings;
	private IAVRequest iavRequest;
	private DocDownloadRequest docDownloadRequest;
	public String getServerType() {
		return serverType;
	}
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	public String[] getRequestTypes() {
		return requestTypes;
	}
	public void setRequestTypes(String[] requestTypes) {
		this.requestTypes = requestTypes;
	}
	public String getCustomrefreshRoute() {
		return customrefreshRoute;
	}
	public void setCustomrefreshRoute(String customrefreshRoute) {
		this.customrefreshRoute = customrefreshRoute;
	}
	public String getAgentFileType() {
		return agentFileType;
	}
	public void setAgentFileType(String agentFileType) {
		this.agentFileType = agentFileType;
	}
	public String getRefreshRoute() {
		return refreshRoute;
	}
	public void setRefreshRoute(String refreshRoute) {
		this.refreshRoute = refreshRoute;
	}
	public boolean isIgnoreWarnings() {
		return ignoreWarnings;
	}
	public void setIgnoreWarnings(boolean ignoreWarnings) {
		this.ignoreWarnings = ignoreWarnings;
	}
	public IAVRequest getIavRequest() {
		return iavRequest;
	}
	public void setIavRequest(IAVRequest iavRequest) {
		this.iavRequest = iavRequest;
	}
	public DocDownloadRequest getDocDownloadRequest() {
		return docDownloadRequest;
	}
	public void setDocDownloadRequest(DocDownloadRequest docDownloadRequest) {
		this.docDownloadRequest = docDownloadRequest;
	}
	public BatchRefreshParam() {
		super();
	}
	
	

}
