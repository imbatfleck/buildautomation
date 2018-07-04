package com.yodlee.buildautomation.BuildAutomation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yodlee.buildautomation.BuildAutomation.authdetails.AuthenticationDeatils;
import com.yodlee.buildautomation.BuildAutomation.authdetails.LoginResponseEntity;
import com.yodlee.buildautomation.BuildAutomation.batchreqid.BatchReqDetailListID;
import com.yodlee.buildautomation.BuildAutomation.batchstatuschecking.BatchReqDetailList;
import com.yodlee.buildautomation.BuildAutomation.comparebatchdetails.AfterCompareResponse;
import com.yodlee.buildautomation.BuildAutomation.controller.BuildOperation;
import com.yodlee.buildautomation.BuildAutomation.serverurl.URLFactory;
import com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails.TriggerBatchResponse;
import com.yodlee.buildautomation.BuildAutomation.utilities.Utility;

@SpringBootApplication
public class BuildAutomationApplication{
	
	public static final Logger logger=LoggerFactory.getLogger(BuildAutomationApplication.class);
	public static ConcurrentHashMap<String,String> requestMapper=new ConcurrentHashMap();
	public static HashMap<String, String> requestInitTimeMapper=new HashMap<>();
	private static final String REQ_STATUS_COMPLETED="completed";
	private static final String REQ_STATUS_PENDING="pending";
	private static Stack<AuthenticationDeatils> authStack=new Stack<>();
	private static Stack<AuthenticationDeatils> reservedAuthStack=new Stack<>();
	private static List<AuthenticationDeatils> authList=new ArrayList<>();
	private static final ArrayList<String> custRouteList=new ArrayList<>();
	private static final HashMap<String,String> requestStatusMap=new HashMap<>();
	private static Properties emailProps = ReadPropertiesFile.getProperties("email.properties");
	private static CopyOnWriteArrayList<String> requestList=new CopyOnWriteArrayList<>();
	private static long latestRequestStartTime=0;
	private static HashSet<String> processMap=new HashSet<>();
	public static HashMap<String, String> statusMap=new HashMap<>();
	public static int batchProcessCount=0;
	
	
	public static void setBatchParams(String batchName,String batchID,String[] args) throws IOException, InterruptedException, MessagingException, ParseException
	{
		ConfigurableApplicationContext applicationContext=SpringApplication.run(BuildAutomationApplication.class, args);
		logger.info("Build regression application has started");
		BuildOperation buildOperation=applicationContext.getBean(BuildOperation.class);
		TriggerBatchResponse triggerBatchResponse=null;
		BatchReqDetailListID batchReqDetailListID=null;
		String userName=null;
		
	
		for(String custRoute: custRouteList)
		{
			logger.info("Batch Name - {}",batchName);
			logger.info("Environment - {}",custRoute);
			boolean isBatchTriggered=false;
			while(!isBatchTriggered)
			{
				boolean isPicked=false;
				if(authStack.isEmpty())
				{
					logger.info("Auth pool is empty",authStack);
					long startTime=latestRequestStartTime;
					long endTime=startTime+(60000*30);
					int i=0;
					while(!(startTime>=endTime)){
						logger.info("Polling auth pool for credentials - {}",i);
						int poolSize=checkPoolForCreds(buildOperation);
						logger.info("Pool Size - {}",poolSize);
						if(poolSize>0)
						{
							isPicked=true;
							logger.info("Succeful polling");
							logger.info("Pool Size - {}",poolSize);
							logger.info("Username picked - {}",authStack.peek().getUsername());
							break;
						}
						logger.info("Auth pool is busy, sleeping for 1 minute");
						startTime=startTime+60000;
						Thread.sleep(60000);
						i++;
					}
					if(!isPicked)
					{
						logger.info("Auth is busy so batch could not picked- Hence checking time initiated");
						for(String req:requestList)
						{
							long initTime=Long.valueOf(requestInitTimeMapper.get(req));
							long targetTime=Utility.getCurrentTImeStamp();
							long checkTime=targetTime-initTime;
							double minCheckTime=((checkTime/1000)/60.00);
							logger.info("Minute time - {}",minCheckTime);
							
							if(checkTime>30)
							{
								try
								{
									BatchReqDetailList batchReqDetail=buildOperation.getStatusBatch(URLFactory.BATCH_STATUS_URL,req);
									userName=batchReqDetail.getUserName();
								}
								catch(HttpClientErrorException he)
								{
									if(he.getMessage().contains("408"))
									{
										buildOperation.getToken(URLFactory.LOGIN_URL, reservedAuthStack.peek());
										BatchReqDetailList batchReqDetail=buildOperation.getStatusBatch(URLFactory.BATCH_STATUS_URL,req);
										userName=batchReqDetail.getUserName();
									}
								}
								for(AuthenticationDeatils authenticationDeatils: authList)
								{
									if(authenticationDeatils.getUsername().equals(userName))
									{
											authStack.push(authenticationDeatils);
											requestList.remove(req);
											break;
									}
								}
							}
						}
					}
				}
				else
				{
					
					AuthenticationDeatils authenticationDeatils=authStack.peek();
					logger.info("picked up credential from pool - {}",authenticationDeatils.getUsername());
					LoginResponseEntity loginResponseEntity=buildOperation.getToken(URLFactory.LOGIN_URL,authenticationDeatils);
					logger.info("Token Created - {}",loginResponseEntity.getToken());
					
					try
					{
						 triggerBatchResponse=buildOperation.triggerBatch(URLFactory.TRIGGER_BATCH_URL, batchID,custRoute);
					}
					catch(HttpClientErrorException he)
					{
						if(he.getMessage().contains("408"))
						{
							buildOperation.getToken(URLFactory.LOGIN_URL, reservedAuthStack.peek());
							triggerBatchResponse=buildOperation.triggerBatch(URLFactory.TRIGGER_BATCH_URL, batchID,custRoute);
						}
					}
					logger.info("User is triggering batch - {}",batchName);
					logger.info("Batch trigger status message",triggerBatchResponse.getStatusMsg());
					String appReqID=triggerBatchResponse.getAppRequestId();
					logger.info("Batch status id - {}",appReqID);
					if(appReqID!=null && !appReqID.equals("REJECTED"))
					{
						logger.info("Batch successfully triggered");
						try
						{
							 batchReqDetailListID=buildOperation.getBatchRequestId(URLFactory.BATCH_GETREQID_URL,batchName,appReqID);
						}
						catch(HttpClientErrorException he)
						{
							if(he.getMessage().contains("408"))
							{
								buildOperation.getToken(URLFactory.LOGIN_URL, reservedAuthStack.peek());
								batchReqDetailListID=buildOperation.getBatchRequestId(URLFactory.BATCH_GETREQID_URL,batchName,appReqID);
							}
						}
						if(custRoute.equals("C"))
						{	
							logger.info("Batch Trigger Environment - {}","Developer");
							requestMapper.put(batchReqDetailListID.getBatchReqDetailsId(), batchName+"_dev");
							processMap.add(batchName);
						}
						else if(custRoute.equals("R"))
						{
							logger.info("Batch Trigger Environment - {}","Regression");
							requestMapper.put(batchReqDetailListID.getBatchReqDetailsId(), batchName+"_reg");
							processMap.add(batchName);
						}
						logger.info("Batch Request ID - {}",batchReqDetailListID.getBatchReqDetailsId());
						requestList.add(batchReqDetailListID.getBatchReqDetailsId());
						requestInitTimeMapper.put(batchReqDetailListID.getBatchReqDetailsId(), batchReqDetailListID.getReqInitiated());
						logger.info("Batch Request List - {}",requestList);
						isBatchTriggered=true;
						latestRequestStartTime=Long.valueOf(batchReqDetailListID.getReqInitiated());
						AuthenticationDeatils authenticationDeatils2=authStack.pop();
						logger.info("popped one credential set from auth stack - {}",authenticationDeatils2.getUsername());
					}
					else if(appReqID.equals("REJECTED"))
					{
						logger.info("Batch request has been rejected for the user- {}",authenticationDeatils.getUsername());
						long startTime=latestRequestStartTime;
						long endTime=startTime+(60000*5);
						while(!(startTime>=endTime)){
							logger.info("Polling pool for independent user");
							int poolSize=checkPoolForCreds(buildOperation);
							if(poolSize>0)
							{
								logger.info("User got picked with username - {}",authStack.peek().getUsername());
								logger.info("Pool Size - {}",poolSize);
								break;
							}
							logger.info("Pool is busy. Sleeping for 1 minute");
							startTime=startTime+60000;
							Thread.sleep(60000);
						}
					}
				}
			}
		}
		for(String reqID1:requestMapper.keySet())
		{
			String statusID=statusMap.get(reqID1);
			if(statusID==null)
			{
				try
				{
					BatchReqDetailList batchReqDetail=buildOperation.getStatusBatch(URLFactory.BATCH_STATUS_URL,reqID1);
				}
				catch(HttpClientErrorException he)
				{
					if(he.getMessage().contains("408"))
					{
						buildOperation.getToken(URLFactory.LOGIN_URL, reservedAuthStack.peek());
						BatchReqDetailList batchReqDetail=buildOperation.getStatusBatch(URLFactory.BATCH_STATUS_URL,reqID1);
					}
				}
			}
		}
		logger.info("Status Map - {}",statusMap);
		logger.info("Total Batch Request Size - {}",processMap.size());
		logger.info("Total Batch Size - {}",batchProcessCount);
		
		if(processMap.size()==batchProcessCount)
		{
			long startTime=1530540164000l;
			//long endTime=startTime+(60000*25);
			long endTime=startTime+(60000*25);
			while(!(startTime>=endTime)){
				if(requestMapper.size()==0)
				{
					break;
				}
				for(String reqID:requestMapper.keySet())
				{
					logger.info("Request Nav - {}",reqID);
					try
					{
						BatchReqDetailList batchReqDetail=buildOperation.getStatusBatch(URLFactory.BATCH_STATUS_URL,reqID);
					}
					catch(HttpClientErrorException he)
					{
						if(he.getMessage().contains("408"))
						{
							if(authStack.size()==0)
							{
								createAuthPool();
							}
							buildOperation.getToken(URLFactory.LOGIN_URL, authStack.peek());
							BatchReqDetailList batchReqDetail=buildOperation.getStatusBatch(URLFactory.BATCH_STATUS_URL,reqID);
						}
					}
				}
				if(!compareBatches(buildOperation,true))
				{
					logger.info("waiting for batch request completion");
					startTime=startTime+60000;
					Thread.sleep(60000);
				}
				else
				{
					logger.info("Batch comparision is done for the batch");
				}
			}
		}
		if(processMap.size()==batchProcessCount 
				&& requestMapper.size()!=0)
		{
			for(String request:requestMapper.keySet())
			{
				logger.info("Pending Request - {}",request);
				compareBatches(buildOperation,false);
			}
		}
		
	}
	
	private static boolean compareBatches(BuildOperation buildOperation,boolean checkStatus) throws IOException, MessagingException
	{
		boolean isCompared=false;
		AfterCompareResponse afterCompareResponse=null;
		for(String reqID1:requestMapper.keySet())
		{
			logger.info("Request Mapper - {}",requestMapper);
			logger.info("Request ID - {}",reqID1);
			String statID=statusMap.get(reqID1);
			
			if(checkStatus && !statID.equals("5"))
			{
				continue;
			}
			boolean isBatDev=false;
			boolean isBatReg=false;
			String batchReqDev=null;
			String batchReqReg=null;
			String batchDev=null;
			String batchReg=null;
			String batch=requestMapper.get(reqID1);
			if(batch.contains("_dev"))
			{
				batchDev=batch;
				batchDev=batchDev.replace("_dev", "").trim();
				batchReqDev=reqID1;
				isBatDev=true;
			}
			else if(batch.contains("_reg"))
			{
				batchReg=batch;
				batchReg=batchReg.replace("_reg", "").trim();
				batchReqReg=reqID1;
				isBatReg=true;
			}
			for(String reqID2:requestMapper.keySet())
			{
				String statID2=statusMap.get(reqID2);
				if(checkStatus && !statID2.equals("5"))
				{
					continue;
				}
				String bat=requestMapper.get(reqID2);
				if(isBatDev)
				{
					if(bat.contains("_reg"))
					{
						bat=bat.replace("_reg", "");
						if(batchDev.equals(bat))
						{
							batchReg=bat;
							batchReqReg=reqID2;
							break;
						}
					}
				}
				else if(isBatReg)
				{
					if(bat.contains("_dev"))
					{
						bat=bat.replace("_dev", "");
						if(batchReg.equals(bat))
						{
							batchDev=bat;
							batchReqDev=reqID2;
							break;
						}
					}
				}
			}
			if(batchReqDev!=null && batchReqReg!=null)
			{
				try
				{
					 	afterCompareResponse=buildOperation.doCompareBatches(URLFactory.COMPARE_BATCH_URL, batch, batchReqDev, batchReqReg);
				}
				catch(HttpClientErrorException he)
				{
					if(he.getMessage().contains("408"))
					{
						buildOperation.getToken(URLFactory.LOGIN_URL, reservedAuthStack.peek());
						afterCompareResponse=buildOperation.doCompareBatches(URLFactory.COMPARE_BATCH_URL, batch, batchReqDev, batchReqReg);
					}
				}
				logger.info("Request processed");
				sendMail(afterCompareResponse.getFinalHTML(),batch,afterCompareResponse.isWrote());
				logger.info("Mail Sent");
				logger.info("Removing requests from requestmapper");
				requestMapper.remove(batchReqDev);
				logger.info("Removed Request ID - {}",batchReqDev);
				requestMapper.remove(batchReqReg);
				logger.info("Removed Request ID - {}",batchReqReg);
				requestStatusMap.put(batchReqDev, REQ_STATUS_COMPLETED);
				requestStatusMap.put(batchReqReg, REQ_STATUS_COMPLETED);
				isCompared=true;
				break;
			}
		}
		return isCompared;
	}
	
	private static void sendMail(String finalHTML, String batchName,boolean wrote) throws MessagingException {
		Properties properties=new Properties();
		properties.put(emailProps.getProperty("mailType"), emailProps.getProperty("hostIp"));
		Session session=Session.getInstance(properties,null);

		   String[] aliasHolderTo =emailProps.getProperty("aliasHolderTo").split(",");
		   String[] aliasHolderCC= null;
		   if(emailProps.getProperty("aliasHolderCC").length()>0)
				aliasHolderCC = emailProps.getProperty("aliasHolderCC").split(",");


			InternetAddress fromAddress = null;
			MimeMessage msg = new MimeMessage(session);
			for (int i = 0; i < aliasHolderTo.length; i++) {
				// System.out.println("2^^^^^^^aliasHolder:" + aliasHolderTo[i]);
				String add = aliasHolderTo[i];
				InternetAddress to = new InternetAddress(add);
				msg.addRecipient(Message.RecipientType.TO, to);
			}
			if(aliasHolderCC!=null){
				for (int i = 0; i < aliasHolderCC.length; i++) {
					// System.out.println("2^^^^^^^aliasHolderCC:" + aliasHolderCC[i]);
					String add = aliasHolderCC[i];
					InternetAddress cc = new InternetAddress(add);
					msg.addRecipient(Message.RecipientType.CC, cc);
				}
			}

			msg.setFrom();
			fromAddress = new InternetAddress(emailProps.getProperty("from"));
			msg.setFrom(fromAddress);
			msg.addRecipient(Message.RecipientType.CC, fromAddress);
			if(batchName.contains("_dev"))
			{
				msg.setSubject("Build Regression : "+batchName.replace("_dev", ""));
			}
			else if(batchName.contains("_reg"))
			{
				msg.setSubject("Build Regression : "+batchName.replace("_reg", ""));
			}
			msg.setSentDate(new Date());
			msg.setText("Attachment");
			/*
			 * ************************Test for attachment*************************
			 */
			
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(finalHTML, "text/html");
			
			

			// Part two is attachment
			
			DataSource source = new FileDataSource(batchName+".html");
			attachmentBodyPart.setDataHandler(new DataHandler(source));
			attachmentBodyPart.setFileName(batchName+".html");

			// Put parts in message
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachmentBodyPart);
			msg.setContent(multipart);
			 //******************************************************************** 
			Transport.send(msg);


		
	}

	private static int checkPoolForCreds(BuildOperation buildOperation) throws JsonProcessingException {
		BatchReqDetailList batchReqDetail=null;
		for(String reqID:requestList)
		{
			try
			{
				 batchReqDetail=buildOperation.getStatusBatch(URLFactory.BATCH_STATUS_URL,reqID);
			}
			catch(HttpClientErrorException he)
			{
				if(he.getMessage().contains("408"))
				{
					buildOperation.getToken(URLFactory.LOGIN_URL, reservedAuthStack.peek());
					batchReqDetail=buildOperation.getStatusBatch(URLFactory.BATCH_STATUS_URL,reqID);
				}
			}
			if(batchReqDetail.getBatchStatusId().equals("5"))
			{
				for(AuthenticationDeatils authenticationDeatils: authList)
				{
					if(authenticationDeatils.getUsername().equals(batchReqDetail.getUserName()))
					{
							authStack.push(authenticationDeatils);
							requestList.remove(reqID);
							break;
					}
				}
			}
		}
		
		return authStack.size();
	}

		
	public static void createAuthPool() throws IOException
	{
		
		try {
			FileInputStream fileInputStream=new FileInputStream(new File("authdetails.xlsx"));
			XSSFWorkbook workbook=new XSSFWorkbook(fileInputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			 
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int j=0;
            while (rowIterator.hasNext())
            {
	            	AuthenticationDeatils authenticationDeatils=new AuthenticationDeatils();
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	                Iterator<Cell> cellIterator = row.cellIterator();
	                int i=0;
	                while (cellIterator.hasNext())
	                {
	                    Cell cell = cellIterator.next();
	                    //Check the cell type and format accordingly
	                    switch (cell.getCellType())
	                    {
	                    
	                        case Cell.CELL_TYPE_NUMERIC:
	                            break;
	                        case Cell.CELL_TYPE_STRING:
	                            String cellValue=cell.getStringCellValue();
	                            if(j!=0)
	                            {
		                            if(i==0)
		                            {
		                            	authenticationDeatils.setUsername(cell.getStringCellValue());
		                            }
		                            else
		                            {
		                            	authenticationDeatils.setPassword(cell.getStringCellValue());
		                            }
	                            }
	                            break;
	                    }
	                    i++;
	                }
	                authList.add(authenticationDeatils);
	                reservedAuthStack.add(authenticationDeatils);
            	
                j++;
            }
            fileInputStream.close();

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(AuthenticationDeatils authDet:authList)
		{
			if(authDet.getUsername()==null)
			{
				authList.remove(authDet);
				break;
			}
			
		}
		
		for(AuthenticationDeatils authDet:authList)
		{
			logger.info("Pushing credetial in pool");
			logger.info("Username - {}",authDet.getUsername());
			authStack.push(authDet);
			
		}
	}
	
	public static void createRouteList()
	{
		custRouteList.add("C");
		custRouteList.add("R");
	}
	
	public static void createBatch() throws IOException
	{
		BuildOperation buildOperation=new BuildOperation();
		buildOperation.getToken(URLFactory.LOGIN_URL, authStack.peek());
		buildOperation.doCreateBatch(URLFactory.CREATE_BATCH_URL,"DagBase", "736BuildShelv", "736BuildShelv", "itemlist5.txt");
	}
}
