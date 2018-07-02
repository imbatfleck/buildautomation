package com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails;

public class TriggerRequest {
	
	private static TriggerRequest triggerRequest=new TriggerRequest();
	private static BatchRefreshParam batchRefreshParam=new BatchRefreshParam();
	private static IAVRequest iavRequest=new IAVRequest();
	private static DocDownloadRequest docDownloadRequest=new DocDownloadRequest();
	private static IAVPlusRequest iavPlusRequest=new IAVPlusRequest();
	private static StatementRequest statementRequest=new StatementRequest();
	
	
	private String userName;
	private String agentName;
	private String batchDetailsId;
	private BatchRefreshParam batchRefreshParams;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getBatchDetailsId() {
		return batchDetailsId;
	}
	public void setBatchDetailsId(String batchDetailsId) {
		this.batchDetailsId = batchDetailsId;
	}
	public BatchRefreshParam getBatchRefreshParams() {
		return batchRefreshParams;
	}
	public void setBatchRefreshParams(BatchRefreshParam batchRefreshParams) {
		this.batchRefreshParams = batchRefreshParams;
	}
	public TriggerRequest() {
		super();
	}
	
	public static TriggerRequest setTriggerRequestParams(String batchID,String custRoute)
	{
		statementRequest.setStmtDurationType("");
		statementRequest.setTaxDurationType("");
		
		docDownloadRequest.setPfm(false);
		docDownloadRequest.setLatest(false);
		docDownloadRequest.setAllAccounts(false);
		docDownloadRequest.setDocDownloadRequest(false);
		docDownloadRequest.setDurationType("");
		docDownloadRequest.setTaxDurationType("");
		docDownloadRequest.setStatementRequest(statementRequest);
		
		iavPlusRequest.setIavPlus(false);
		
		iavRequest.setAccountNumberMatchPrefix("");
		iavRequest.setAccountNumberMatchSuffix("");
		iavRequest.setIavPlusRequest(iavPlusRequest);
		
		batchRefreshParam.setRequestTypes(new String[]{});
		batchRefreshParam.setServerType("I");
		batchRefreshParam.setCustomrefreshRoute(custRoute);
		batchRefreshParam.setAgentFileType("JAVA");
		batchRefreshParam.setRefreshRoute("D");
		batchRefreshParam.setIgnoreWarnings(true);
		batchRefreshParam.setIavRequest(iavRequest);
		batchRefreshParam.setDocDownloadRequest(docDownloadRequest);
		
		triggerRequest.setAgentName("");
		triggerRequest.setBatchRefreshParams(batchRefreshParam);
		triggerRequest.setBatchDetailsId(batchID);
		
		return triggerRequest;
	}
	

}
