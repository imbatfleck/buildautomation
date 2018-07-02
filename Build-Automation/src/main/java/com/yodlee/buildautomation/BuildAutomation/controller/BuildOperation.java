package com.yodlee.buildautomation.BuildAutomation.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yodlee.buildautomation.BuildAutomation.BuildAutomationApplication;
import com.yodlee.buildautomation.BuildAutomation.authdetails.AuthenticationDeatils;
import com.yodlee.buildautomation.BuildAutomation.authdetails.LoginResponseEntity;
import com.yodlee.buildautomation.BuildAutomation.batchcreation.CBAddItemProps;
import com.yodlee.buildautomation.BuildAutomation.batchcreation.CBProp;
import com.yodlee.buildautomation.BuildAutomation.batchcreation.CBResponse;
import com.yodlee.buildautomation.BuildAutomation.batchreqid.BatchReqDetailListID;
import com.yodlee.buildautomation.BuildAutomation.batchreqid.BatchRequestID;
import com.yodlee.buildautomation.BuildAutomation.batchreqid.BatchRequestIDResponse;
import com.yodlee.buildautomation.BuildAutomation.batchstatuschecking.BatchReqDetailList;
import com.yodlee.buildautomation.BuildAutomation.batchstatuschecking.BatchStatusResponse;
import com.yodlee.buildautomation.BuildAutomation.comparebatchdetails.AfterCompareResponse;
import com.yodlee.buildautomation.BuildAutomation.comparebatchdetails.BatchResult;
import com.yodlee.buildautomation.BuildAutomation.comparebatchdetails.CompareBatch;
import com.yodlee.buildautomation.BuildAutomation.comparebatchdetails.CompareBatchHTML;
import com.yodlee.buildautomation.BuildAutomation.comparebatchdetails.CompareBatchResponse;
import com.yodlee.buildautomation.BuildAutomation.restoperation.RestOperation;
import com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails.TriggerBatchResponse;
import com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails.TriggerRequest;
import com.yodlee.buildautomation.BuildAutomation.utilities.Utility;

@Component
public class BuildOperation {
	private static final String tokenAdder="Bearer ";
	private static String tokeValue=null;
	
	private static RestTemplate restTemplate=new RestTemplate();
	private static HttpHeaders httpHeaders=new HttpHeaders();
	
	
	private static RestOperation restOperation=new RestOperation();
	private static LoginResponseEntity loginResponseEntity=new LoginResponseEntity();
	private static CompareBatchResponse compareBatchResponse=new CompareBatchResponse();
	private static TriggerBatchResponse triggerBatchResponse=new TriggerBatchResponse();
	private static BatchStatusResponse batchStatusResponse=new BatchStatusResponse();
	private static BatchRequestIDResponse batchRequestIDResponse=new BatchRequestIDResponse();
	private static CBResponse cbResponse=new CBResponse();
	
	
	public LoginResponseEntity getToken(String url,AuthenticationDeatils authenticationDeatils) throws JsonProcessingException
	{
		loginResponseEntity=restOperation.getPostResponseEntity(url,authenticationDeatils, loginResponseEntity, restTemplate);
		String token=loginResponseEntity.getToken();
		tokeValue=tokenAdder+token;
		loginResponseEntity.setToken(tokeValue);
		return loginResponseEntity;
	}
	
	public BatchReqDetailListID getBatchRequestId(String url,String batchName,String appReqID) throws JsonProcessingException
	{
		BatchReqDetailListID batchReqDetailList=null;
		String requestID=null;
		
		BatchRequestID batchRequestID=BatchRequestID.setBatchRequestIDParam(batchName);
		
		restOperation.setHeaders(httpHeaders, tokeValue);
		batchRequestIDResponse=restOperation.getPostResponseExchange(url, httpHeaders, batchRequestID, batchRequestIDResponse, restTemplate);
		
		BatchReqDetailListID[] batchReqDetailListIDs=batchRequestIDResponse.getBatchReqDetailList();
		for(BatchReqDetailListID bID:batchReqDetailListIDs)
		{
			requestID=bID.getAppReqId();
			if(requestID.equals(appReqID))
			{
				batchReqDetailList=bID;
				break;
			}
		}
		return batchReqDetailList;
	}
	
	public TriggerBatchResponse triggerBatch(String url,String batchID,String custRoute) throws JsonProcessingException
	{
		TriggerRequest triggerRequest=TriggerRequest.setTriggerRequestParams(batchID,custRoute);
		restOperation.setHeaders(httpHeaders, tokeValue);
		triggerBatchResponse=restOperation.getPostResponseExchange(url, httpHeaders, triggerRequest, triggerBatchResponse, restTemplate);
		return triggerBatchResponse;
	}
	
	public BatchReqDetailList getStatusBatch(String url,String batchReqID) throws JsonProcessingException
	{
		BatchReqDetailList batchReqDetails=null;
		restOperation.setHeaders(httpHeaders, tokeValue);
		batchStatusResponse=restOperation.getPostResponseExchange(url, httpHeaders,batchReqID, batchStatusResponse, restTemplate);
		BatchReqDetailList[] batchReqDetailList=batchStatusResponse.getBatchReqDetailList();
		for(BatchReqDetailList bList:batchReqDetailList)
		{
			batchReqDetails=bList;
		}
		BuildAutomationApplication.statusMap.put(batchReqID,batchReqDetails.getBatchStatusId());
		return batchReqDetails;
	}
	
	public AfterCompareResponse doCompareBatches(String url,String batchName,String batchReqDev,String batchReqRegression) throws IOException
	{
	
		AfterCompareResponse afterCompareResponse=new AfterCompareResponse();
		CompareBatch compareBatch=CompareBatch.setCompareBatchDetails(batchReqDev,batchReqRegression);
		restOperation.setHeaders(httpHeaders, tokeValue);
		compareBatchResponse=restOperation.getPostResponseExchange(url,httpHeaders, compareBatch, compareBatchResponse, restTemplate);
		BatchResult[] batchResult=compareBatchResponse.getBcResult();
		String headerTable=CompareBatchHTML.createHeaderTable(batchResult);
		String bodyTable=CompareBatchHTML.createBodyTable(batchResult);
		String cssStyles=Utility.readFromFile("csstyles.txt");
		String javaScript=Utility.readFromFile("javascript.txt");
		String finalHTML=CompareBatchHTML.createFinalHTML(cssStyles,headerTable,bodyTable,javaScript);
		boolean isWrote=Utility.writeToFile(batchName, "html", finalHTML);
		afterCompareResponse.setFinalHTML(finalHTML);
		afterCompareResponse.setWrote(isWrote);
		return afterCompareResponse;
	}

	public void doCreateBatch(String url,String batchName,String batchDesc,String batchNickName,String fileName) throws IOException
	{
		CBProp cbProp=CBProp.setBatchValues(batchName,batchDesc,batchNickName, createItemList(fileName));
		restOperation.setHeaders(httpHeaders, tokeValue);
		cbResponse=restOperation.getPostResponseExchange(url, httpHeaders, cbProp, cbResponse, restTemplate);
		
	}
	
	
	public static List<CBAddItemProps> createItemList(String fileName) throws IOException
	{
		List<CBAddItemProps> itemList=new ArrayList<>();
		ArrayList<String> listItems=Utility.readFromFileList(fileName);
		int i=1;
		for(String item:listItems)
		{
			CBAddItemProps items=new CBAddItemProps();
			String[] splitItem=item.split("/");
			String itemId=splitItem[0];
			String itemDb=splitItem[1];
			
			items.setItemId(itemId);
			items.setDbName(itemDb);
			items.setItemType("Cache Item Id");
			items.setDescription("adding item "+i);
			itemList.add(items);
			i++;
		}
		return itemList;
	}
	

	/*private void printProps(BatchResult bc) {
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
	
	}*/
}
