package com.yodlee.buildautomation.BuildAutomation.comparebatchdetails;

public class CompareBatchHTML {
	
	public static String createBodyTable(BatchResult[] batchResult) {
		String tdTag="";
		for(BatchResult bc:batchResult)
		{
			//printProps(bc);
			String response=bc.getResponseType();
			String csid=bc.getSumInfoId();
			String codeDiff=bc.getStatus1()+"/"+bc.getStatus2();
			String dump1=bc.getHtmlDumpFileName1();
			String dump2=bc.getHtmlDumpFileName2();
			String siteXML1=bc.getXmlDiff().getXmlOneString();
			String siteXML2=bc.getXmlDiff().getXmlTwoString();
			String missingFieldCount=bc.getXmlDiff().getXmlOneMissingCount()+"/"+bc.getXmlDiff().getXmlTwoMissingCount();
			String mismatchCount=bc.getXmlDiff().getXmlOneMismatchCount()+"/"+bc.getXmlDiff().getXmlTwoMismatchCount();
			String srcIDCount=bc.getXmlDiff().getXmlOneSrcCount()+"/"+bc.getXmlDiff().getXmlTwoSrcCount();
			String navCount=bc.getNumNav1()+"/"+bc.getNumNav2();
			String latencyDiff=bc.getLatency1()+"/"+bc.getLatency2();
			String accountDiff=bc.getNumAccts1()+"/"+bc.getNumAccts2();
			String batchDetail1=bc.getBatchReqDetailsId1();
			String batchDetail2=bc.getBatchReqDetailsId2();
			String batchComp=batchDetail1+"/"+batchDetail2;
			
			String cacheItemID=bc.getItemId();
			String itemType=bc.getItemTypeId();
			if(itemType.equals("2"))
			{
				itemType="CII";
			}
			tdTag+="<tr>"+
					"<td>"+cacheItemID+"</td>"+
					"<td>"+itemType+"</td>"+
					"<td>"+response+"</td>"+
					"<td>"+csid+"</td>"+
					"<td>"+codeDiff+"</td>"+
					"<td>"+dump1+"</td>"+
					"<td>"+dump2+"</td>"+
					"<td>"+"<a href=\"javascript:;\" onclick=\"myFunction(this,this)\" data-xml1-type="+"\""+siteXML1+"\""+" data-xml2-type="+"\""+siteXML2+"\""+">SiteXML Diff</a>"+"</td>"+
					"<td>"+missingFieldCount+"</td>"+
					"<td>"+mismatchCount+"</td>"+
					"<td>"+srcIDCount+"</td>"+
					"<td>"+navCount+"</td>"+
					"<td>"+latencyDiff+"</td>"+
					"<td>"+accountDiff+"</td>"+
					"</tr>";
			
		}
		return tdTag;
	}

	public static String createHeaderTable(BatchResult[] batchResult) {
		// TODO Auto-generated method stub
		String headerTable="";
		for(BatchResult bc:batchResult)
		{
			String batchDetail1=bc.getBatchReqDetailsId1();
			String batchDetail2=bc.getBatchReqDetailsId2();
			String batchComp=batchDetail1+"/"+batchDetail2;
			headerTable="<body>\r\n" + 
					"	<table>\r\n" + 
					"		<tr>\r\n" + 
					"    <th>Item Id</th>\r\n" + 
					"    <th>Item Type</th> \r\n" + 
					"    <th>Response</th>\r\n" + 
					"    <th>CSID</th>\r\n" + 
					"    <th>Code-"+batchComp+"</th>\r\n" + 
					"    <th>Dump1-"+batchDetail1+"</th>\r\n" + 
					"    <th>Dump2-"+batchDetail2+"</th>\r\n" + 
					"    <th>Site Xml diff-"+batchComp+"</th>\r\n" + 
					"    <th>Missing field-"+batchComp+"</th>\r\n" +
					"    <th>Mismatch values-"+batchComp+"</th>\r\n" +
					"    <th>Src-Id mismatched-"+batchComp+"</th>\r\n" + 
					"    <th>Navigations-"+batchComp+"</th>\r\n" + 
					"    <th>Latency Diff-"+batchComp+"</th>\r\n" + 
					"    <th>Accounts Diff-"+batchComp+"</th>\r\n" + 
					"\r\n" + 
					"  </tr>";
			break;
		}
		return headerTable;
	}
	
	public static String createFinalHTML(String... files)
	{
		String htmlContent="";
		for(String tempFile:files)
		{
			htmlContent+=tempFile;
		}
		return htmlContent;
	}
}
