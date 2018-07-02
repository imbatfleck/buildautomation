package com.yodlee.buildautomation.BuildAutomation.comparebatchdetails;

import com.yodlee.buildautomation.BuildAutomation.BuildAutomationApplication;

public class CompareBatchHTML {
	
	public static String createBodyTable(BatchResult[] batchResult) {
		String tdTag="";
		
		for(BatchResult bc:batchResult)
		{
			//printProps(bc);
			String response=null;
			String csid=null;
			String codeDiff=null;
			String dump1=null;
			String dump2=null;
			String siteXML1=null;
			String siteXML2=null;
			String missingFieldCount=null;
			String mismatchCount=null;
			String srcIDCount=null;
			String navCount=null;
			String latencyDiff=null;
			String accountDiff=null;
			String batchDetail1=null;
			String batchDetail2=null;
			String batchComp=null;
			String cacheItemID=null;
			String itemType=null;

			if(bc!=null)
			{
				response=bc.getResponseType();
				csid=bc.getSumInfoId();
				codeDiff=bc.getStatus1()+"/"+bc.getStatus2();
				dump1=bc.getHtmlDumpFileName1();
				dump2=bc.getHtmlDumpFileName2();
				if(bc.getXmlDiff()!=null)
				{
					siteXML1=bc.getXmlDiff().getXmlOneString();
					siteXML2=bc.getXmlDiff().getXmlTwoString();
					missingFieldCount=bc.getXmlDiff().getXmlOneMissingCount()+"/"+bc.getXmlDiff().getXmlTwoMissingCount();
					mismatchCount=bc.getXmlDiff().getXmlOneMismatchCount()+"/"+bc.getXmlDiff().getXmlTwoMismatchCount();
					srcIDCount=bc.getXmlDiff().getXmlOneSrcCount()+"/"+bc.getXmlDiff().getXmlTwoSrcCount();
				}
				navCount=bc.getNumNav1()+"/"+bc.getNumNav2();
				latencyDiff=bc.getLatency1()+"/"+bc.getLatency2();
				accountDiff=bc.getNumAccts1()+"/"+bc.getNumAccts2();
				batchDetail1=bc.getBatchReqDetailsId1();
				batchDetail2=bc.getBatchReqDetailsId2();
				batchComp=batchDetail1+"/"+batchDetail2;
				
				cacheItemID=bc.getItemId();
				itemType=bc.getItemTypeId();
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
						"<td>"+"<a href="+dump1+" target=_blank"+">Dump</a>"+"</td>"+
						"<td>"+"<a href="+dump2+" target=_blank"+">Dump</a>"+"</td>"+
						"<td>"+"<a href=\"javascript:;\" onclick=\"myFunction(this,this)\" data-xml1-type="+"\""+siteXML1+"\""+" data-xml2-type="+"\""+siteXML2+"\""+">XML Diff</a>"+"</td>"+
						"<td>"+missingFieldCount+"</td>"+
						"<td>"+mismatchCount+"</td>"+
						"<td>"+srcIDCount+"</td>"+
						"<td>"+navCount+"</td>"+
						"<td>"+latencyDiff+"</td>"+
						"<td>"+accountDiff+"</td>"+
						"</tr>";
				
		
			}
		}
		return tdTag;
	}

	public static String createHeaderTable(BatchResult[] batchResult) {
		// TODO Auto-generated method stub
		String headerTable="";
		for(BatchResult bc:batchResult)
		{
			if(bc!=null)
			{
			String batchDetail1=bc.getBatchReqDetailsId1();
			String batchDetail2=bc.getBatchReqDetailsId2();
			String env1=BuildAutomationApplication.requestMapper.get(batchDetail1);
			String env2=BuildAutomationApplication.requestMapper.get(batchDetail2);
			if(env1.contains("dev"))
			{
				env1="Dev";
			}
			else
			{
				env1="Reg";
			}
			if(env2.contains("reg"))
			{
				env2="Reg";
			}
			else
			{
				env2="Dev";
			}
			String batchComp=env1+"/"+env2;
			headerTable="<body>\r\n" + 
					"	<table>\r\n" + 
					"		<tr class=header>\r\n" + 
					"    <th>Item Id</th>\r\n" + 
					"    <th>Item Type</th> \r\n" + 
					"    <th>Response</th>\r\n" + 
					"    <th>CSID</th>\r\n" + 
					"    <th>Code<br /><hr /><span class=sp>"+batchComp+"</span></th>\r\n" + 
					"    <th>Dump1<br /><hr /><span class=sp>"+env1+"</span></th>\r\n" + 
					"    <th>Dump2<br /><hr /><span class=sp>"+env2+"</span></th>\r\n" + 
					"    <th>Site Xml diff<br /><hr /><span class=sp>"+batchComp+"</span></th>\r\n" + 
					"    <th>Missing field<br /><hr /><span class=sp>"+batchComp+"</span></th>\r\n" +
					"    <th>Mismatch values<br /><hr /><span class=sp>"+batchComp+"</span></th>\r\n" +
					"    <th>Src-Id mismatched<br /><hr /><span class=sp>"+batchComp+"</span></th>\r\n" + 
					"    <th>Navigations<br /><hr /><span class=sp>"+batchComp+"</span></th>\r\n" + 
					"    <th>Latency Diff<br /><hr /><span class=sp>"+batchComp+"</span></th>\r\n" + 
					"    <th>Accounts Diff<br /><hr /><span class=sp>"+batchComp+"</span></th>\r\n" + 
					"\r\n" + 			
					"  </tr>";
			
			}
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
