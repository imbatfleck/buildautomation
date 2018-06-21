package com.yodlee.buildautomation.BuildAutomation.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.yodlee.buildautomation.BuildAutomation.authdetails.AuthenticationDeatils;
import com.yodlee.buildautomation.BuildAutomation.authdetails.LoginResponseEntity;
import com.yodlee.buildautomation.BuildAutomation.batchreqid.BatchReqDetailListID;
import com.yodlee.buildautomation.BuildAutomation.batchreqid.BatchRequestID;
import com.yodlee.buildautomation.BuildAutomation.batchreqid.BatchRequestIDResponse;
import com.yodlee.buildautomation.BuildAutomation.batchstatuschecking.BatchReqDetailList;
import com.yodlee.buildautomation.BuildAutomation.batchstatuschecking.BatchStatusResponse;
import com.yodlee.buildautomation.BuildAutomation.comparebatchdetails.BatchResult;
import com.yodlee.buildautomation.BuildAutomation.comparebatchdetails.CompareBatch;
import com.yodlee.buildautomation.BuildAutomation.comparebatchdetails.CompareBatchHTML;
import com.yodlee.buildautomation.BuildAutomation.comparebatchdetails.CompareBatchResponse;
import com.yodlee.buildautomation.BuildAutomation.restoperation.RestOperation;
import com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails.BatchRefreshParam;
import com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails.DocDownloadRequest;
import com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails.IAVPlusRequest;
import com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails.IAVRequest;
import com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails.ParamKeyValues;
import com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails.StatementRequest;
import com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails.TriggerBatchResponse;
import com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails.TriggerRequest;
import com.yodlee.buildautomation.BuildAutomation.utilities.Utility;

@Component
public class BuildOperation {
	public static final String tokenAdder="Bearer ";
	public static final String BATCH_ONE="BATCH1";
	public static final String BATCH_TWO="BATCH2";
	public static HashMap<Integer, String> batchAppIDMap=new HashMap<>();
	public static HashMap<String, String> batchReqIDMap=new HashMap<>();
	public static String tokeValue=null;
	public static String appRequestId=null;
	public static String statusMsg=null;
	public static RestTemplate restTemplate=new RestTemplate();
	public static HttpHeaders httpHeaders=new HttpHeaders();
	
	
	RestOperation restOperation=new RestOperation();
	LoginResponseEntity loginResponseEntity=new LoginResponseEntity();
	CompareBatchResponse compareBatchResponse=new CompareBatchResponse();
	TriggerBatchResponse triggerBatchResponse=new TriggerBatchResponse();
	BatchStatusResponse batchStatusResponse=new BatchStatusResponse();
	BatchRequestIDResponse batchRequestIDResponse=new BatchRequestIDResponse();
	//BatchReqDetailList[] batchReqDetailList=new BatchReqDetailList();
	
	public String getToken(String url)
	{
		AuthenticationDeatils authenticationDeatils=AuthenticationDeatils.setAuthDetails();
		loginResponseEntity=restOperation.getPostResponseEntity(url,authenticationDeatils, loginResponseEntity, restTemplate);
		String token=loginResponseEntity.getToken();
		tokeValue=tokenAdder+token;
		return tokeValue;
	}
	
	public String getBatchRequestId(String url,String batchName,int batchCount)
	{
		String requestID=null;
		String batchReqID=null;
		String reqInitTime=null;
		BatchRequestID batchRequestID=BatchRequestID.setBatchRequestIDParam(batchName);
		batchRequestIDResponse=restOperation.getPostResponseExchange(url, httpHeaders, batchRequestID, batchRequestIDResponse, restTemplate);
		BatchReqDetailListID[] batchReqDetailListIDs=batchRequestIDResponse.getBatchReqDetailList();
		for(BatchReqDetailListID bID:batchReqDetailListIDs)
		{
			requestID=bID.getAppReqId();
			reqInitTime=bID.getReqInitiated();
			if(requestID.equals(batchAppIDMap.get(batchCount)))
			{
				batchReqID=bID.getBatchReqDetailsId();
				batchReqIDMap.put("BATCH"+batchCount, batchReqID);
				break;
			}
		}
		return reqInitTime;
	}
	
	public void triggerBatch(String url,String batchID,int batchCount)
	{
		TriggerRequest triggerRequest=TriggerRequest.setTriggerRequestParams(batchID);
		restOperation.setHeaders(httpHeaders, tokeValue);
		triggerBatchResponse=restOperation.getPostResponseExchange(url, httpHeaders, triggerRequest, triggerBatchResponse, restTemplate);
		String statusMsg=triggerBatchResponse.getStatusMsg();
		String appRequestId=triggerBatchResponse.getAppRequestId();
		batchAppIDMap.put(batchCount, appRequestId);
		System.out.println("+++++++++++++statusMsg="+statusMsg+"++++++++++++++++++appRequestId="+appRequestId);
	}
	
	public String getStatusBatch(String url,int batchCount)
	{
		String batchReqID=batchReqIDMap.get("BATCH"+batchCount);
		String batchStatusId=null;
		restOperation.setHeaders(httpHeaders, tokeValue);
		batchStatusResponse=restOperation.getPostResponseExchange(url, httpHeaders,batchReqID, batchStatusResponse, restTemplate);
		BatchReqDetailList[] batchReqDetailList=batchStatusResponse.getBatchReqDetailList();
		for(BatchReqDetailList bList:batchReqDetailList)
		{
			batchStatusId=bList.getBatchStatusId();
			System.out.println("++++++++++batch status id="+batchStatusId);
		}
		return batchStatusId;
	}
	
	public void doCompareBatches(String url) throws IOException
	{
	
		String batchReq1=batchReqIDMap.get(BATCH_ONE);
		String batchReq2=batchReqIDMap.get(BATCH_TWO);
		CompareBatch compareBatch=CompareBatch.setCompareBatchDetails(batchReq1,batchReq2);
		restOperation.setHeaders(httpHeaders, tokeValue);
		compareBatchResponse=restOperation.getPostResponseExchange(url,httpHeaders, compareBatch, compareBatchResponse, restTemplate);
		BatchResult[] batchResult=compareBatchResponse.getBcResult();
		String headerTable=CompareBatchHTML.createHeaderTable(batchResult);
		String bodyTable=CompareBatchHTML.createBodyTable(batchResult);
		String cssStyles=Utility.readFromFile("csstyles.txt");
		String javaScript=Utility.readFromFile("javascript.txt");
		String finalHTML=CompareBatchHTML.createFinalHTML(cssStyles,headerTable,bodyTable,javaScript);
		boolean isWrote=Utility.writeToFile("index", "html", finalHTML);
		
	}

	

	private void printProps(BatchResult bc) {
		// TODO Auto-generated method stub
		System.out.println("==============================================================");
		System.out.println("++++++++++++++++++++item id="+bc.getItemId());
		System.out.println("+++++++++++++++++++status1="+bc.getStatus1());
		System.out.println("+++++++++++++++++++status2="+bc.getStatus2());
		System.out.println("+++++++++++++++++++htmlDumpFileName1="+bc.getHtmlDumpFileName1());
		System.out.println("+++++++++++++++++++htmlDumpFileName1="+bc.getHtmlDumpFileName2());
		System.out.println("+++++++++++++++++++sumInfoId="+bc.getSumInfoId());
		System.out.println("+++++++++++++++++++response="+bc.getResponseType());
		System.out.println("+++++++++++++++++++item type="+bc.getItemTypeId());
		System.out.println("+++++++++++++++++++site xml 1="+bc.getXmlDiff().getXmlOneString());
		System.out.println("+++++++++++++++++++site xml 2="+bc.getXmlDiff().getXmlTwoString());
		System.out.println("+++++++++++++++++++missing fields1="+bc.getXmlDiff().getXmlOneMismatchFieldList());
		System.out.println("+++++++++++++++++++missing fields1="+bc.getXmlDiff().getXmlTwoMismatchFieldList());
		System.out.println("+++++++++++++++++++missing fields count1="+bc.getXmlDiff().getXmlOneMissingCount());
		System.out.println("+++++++++++++++++++missing fields count2="+bc.getXmlDiff().getXmlTwoMissingCount());
		System.out.println("+++++++++++++++++++mismatch fields1="+bc.getXmlDiff().getXmlOneMismatchFieldList());
		System.out.println("+++++++++++++++++++mismatch fields2="+bc.getXmlDiff().getXmlTwoMismatchFieldList());
		System.out.println("+++++++++++++++++++mismatch fields count1="+bc.getXmlDiff().getXmlOneMismatchCount());
		System.out.println("+++++++++++++++++++mismatch fields count2="+bc.getXmlDiff().getXmlTwoMismatchCount());
		System.out.println("+++++++++++++++++++src id mismatched count1="+bc.getXmlDiff().getXmlOneSrcCount());
		System.out.println("+++++++++++++++++++src id mismatched count2="+bc.getXmlDiff().getXmlTwoSrcCount());
		System.out.println("+++++++++++++++++++nav1="+bc.getNumNav1());
		System.out.println("+++++++++++++++++++nav2="+bc.getNumNav2());
		System.out.println("+++++++++++++++++++noa1="+bc.getNumAccts1());
		System.out.println("+++++++++++++++++++noa1="+bc.getNumAccts2());
		System.out.println("+++++++++++++++++++lat1="+bc.getLatency1());
		System.out.println("+++++++++++++++++++lat2="+bc.getLatency2());
		System.out.println("+++++++++++++++++++batch det1="+bc.getBatchReqDetailsId1());
		System.out.println("+++++++++++++++++++batch det2="+bc.getBatchReqDetailsId2());
		
		System.out.println("==============================================================");
	
	}
}
