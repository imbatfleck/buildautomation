package com.yodlee.buildautomation.BuildAutomation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;

import com.yodlee.buildautomation.BuildAutomation.authdetails.AuthenticationDeatils;
import com.yodlee.buildautomation.BuildAutomation.authdetails.LoginResponseEntity;
import com.yodlee.buildautomation.BuildAutomation.batchreqid.BatchReqDetailListID;
import com.yodlee.buildautomation.BuildAutomation.batchstatuschecking.BatchReqDetailList;
import com.yodlee.buildautomation.BuildAutomation.controller.BuildOperation;
import com.yodlee.buildautomation.BuildAutomation.exception.ExceptionConstants;
import com.yodlee.buildautomation.BuildAutomation.exception.LoginException;
import com.yodlee.buildautomation.BuildAutomation.restoperation.RestOperation;
import com.yodlee.buildautomation.BuildAutomation.serverurl.URLFactory;
import com.yodlee.buildautomation.BuildAutomation.triggerbatchdetails.TriggerBatchResponse;

@SpringBootApplication
public class BuildAutomationApplication{
	static LinkedHashSet<String> reqIDList=new LinkedHashSet<>();
	static HashMap<String,String> requestMapper=new HashMap<>();
	static Stack<AuthenticationDeatils> authStack=new Stack<>();
	private static final ArrayList<String> custRouteList=new ArrayList<>();
	public static void setBatchParams(String batchName,String batchID,String[] args) throws IOException, InterruptedException
	{
		custRouteList.add("C");
		custRouteList.add("R");
		boolean isBatchTriggered=false;
		int batchCount=1;
		boolean isCurrentBatchTriggered=false;
		ConfigurableApplicationContext applicationContext=SpringApplication.run(BuildAutomationApplication.class, args);
		System.out.println("Spring Boot application started");
		BuildOperation buildOperation=applicationContext.getBean(BuildOperation.class);
		List<AuthenticationDeatils> authList=createAuthPool();
		
		for(AuthenticationDeatils authenticationDeatils: authList)
		{
			authStack.push(authenticationDeatils);
		}
		
		for(String custRoute: custRouteList)
		{
			while(!isBatchTriggered)
			{
				if(authStack.isEmpty())
				{
					checkPoolForCreds();
				}
				else
				{
					AuthenticationDeatils authenticationDeatils=authStack.peek();
					LoginResponseEntity loginResponseEntity=buildOperation.getToken(URLFactory.LOGIN_URL);
					TriggerBatchResponse triggerBatchResponse=buildOperation.triggerBatch(URLFactory.TRIGGER_BATCH_URL, batchID, batchCount,custRoute);
					String appReqID=triggerBatchResponse.getAppRequestId();
					if(appReqID!=null)
					{
						requestMapper.put(appReqID, batchName);
						isBatchTriggered=true;
						authStack.pop();
					}
				}
			}
			batchCount++;
		}
		
		/**
		 * USER LOGIN AND GETTING TOKEN
		 */
		
		
		try
		{
			System.out.println(buildOperation.getToken(URLFactory.LOGIN_URL));
		}
		catch (HttpClientErrorException e) {
			if(e.getMessage().equals("401 Unauthorized"))
			{
				throw new LoginException(ExceptionConstants.WRONG_CREDS);
			}
		}
		
		
		
		/**
		 * LOOP TO TRIGGER BATCH TWO TIMES. ONCE IN DEVELOPER AND AGAIN IN PLATFORM
		 */
		
		
		
		/*while(batchCount<=2)
		{
			long startTime=0;
			long endTime=0;
			
			*//**
			 * TRIGGER BATCH
			 *//*
			String appReqID=buildOperation.triggerBatch(URLFactory.TRIGGER_BATCH_URL,batchID,batchCount);
			
			*//**
			 * GETTING REQUEST ID FOR THE TRIGGERED BATCH
			 *//*
			
			String reqInitTime=buildOperation.getBatchRequestId(URLFactory.BATCH_GETREQID_URL,batchName,batchCount);
			
			*//**
			 * POLLING EVERY 5 MINS TO GET THE TRIGGERED BATCH STATUS
			 *//*
			startTime=Long.valueOf(reqInitTime);
			endTime=startTime+(60000*3);
			while(!(startTime>=endTime)){
				String batchStatusID=buildOperation.getStatusBatch(URLFactory.BATCH_STATUS_URL,batchCount);
				if(!batchStatusID.equals("5"))
				{
					System.out.println("+++++++++++++waiting for batch completion");
					startTime=startTime+5000;
					Thread.sleep(5000);
				}
				else
				{
					System.out.println("+++++++++++++batch triggered successfully");
					break;
				}
				
			}
			batchCount++;
		}
		
		*//**
		 * COMPARAING BATCH REQUEST OF DEVELOPER AND PLATFORM
		 *//*
		
		buildOperation.doCompareBatches(URLFactory.COMPARE_BATCH_URL,batchName);
*/		
		/**
		 * Destroying all the objects and closing all the networks
		 */
		
		
		//applicationContext.getBean(TerminateBean.class);
		//applicationContext.close();
	}
	
	private static void checkPoolForCreds() {
		// TODO Auto-generated method stub
		
		
	}

	public static void testAuth(String[] args) throws IOException
	{
		ConfigurableApplicationContext applicationContext=SpringApplication.run(BuildAutomationApplication.class, args);
		System.out.println("Spring Boot application started");
		BuildOperation buildOperation=applicationContext.getBean(BuildOperation.class);
		
		/**
		 * USER LOGIN AND GETTING TOKEN
		 */
		LoginResponseEntity loginResponseEntity=buildOperation.getToken(URLFactory.LOGIN_URL);
		System.out.println(loginResponseEntity.getToken());
		BatchReqDetailListID reqInitTime=buildOperation.getBatchRequestId(URLFactory.BATCH_GETREQID_URL,"DagBase_API_18_Jun_2018_01_45_15",0);
		System.out.println("++++++++++batch request id:"+reqInitTime.getBatchReqDetailsId());
	}
	
	public static void createBatch(String batchName,String batchDesc,String batchNickName,String fileName,String[] args) throws IOException
	{
		ConfigurableApplicationContext applicationContext=SpringApplication.run(BuildAutomationApplication.class, args);
		System.out.println("Spring Boot application started");
		BuildOperation buildOperation=applicationContext.getBean(BuildOperation.class);
		RestOperation restOperation=new RestOperation();
		HttpHeaders httpHeaders=new HttpHeaders();
		
		/**
		 * USER LOGIN AND GETTING TOKEN
		 */
		System.out.println(buildOperation.getToken(URLFactory.LOGIN_URL));
		buildOperation.doCreateBatch(URLFactory.CREATE_BATCH_URL,batchName,batchDesc,batchNickName,fileName);
		
		
	}
	
	public static List<AuthenticationDeatils> createAuthPool() throws IOException
	{
		List<AuthenticationDeatils> authList=new ArrayList<>();
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
	                            System.out.println(cell.getNumericCellValue());
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
			System.out.println("Cred details");
			System.out.println("=============");
			
			System.out.println("+++++++username="+authDet.getUsername());
			System.out.println("+++++++password="+authDet.getPassword());
		}
		return authList;
	}
	
	
}
