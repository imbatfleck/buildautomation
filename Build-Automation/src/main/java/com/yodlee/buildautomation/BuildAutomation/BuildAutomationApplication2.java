package com.yodlee.buildautomation.BuildAutomation;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.yodlee.buildautomation.BuildAutomation.controller.BuildOperation;
import com.yodlee.buildautomation.BuildAutomation.serverurl.URLFactory;

@SpringBootApplication
public class BuildAutomationApplication2 {
	
	public static void setBatchParams(String batchName,String batchID,String[] args) throws IOException, InterruptedException
	{
		int batchCount=1;
		ConfigurableApplicationContext applicationContext=SpringApplication.run(BuildAutomationApplication2.class, args);
		System.out.println("Spring Boot application started");
		BuildOperation buildOperation=applicationContext.getBean(BuildOperation.class);
		
		/**
		 * USER LOGIN AND GETTING TOKEN
		 */
		System.out.println(buildOperation.getToken(URLFactory.LOGIN_URL));
		
		
		/**
		 * LOOP TO TRIGGER BATCH TWO TIMES. ONCE IN DEVELOPER AND AGAIN IN PLATFORM
		 */
		while(batchCount<=2)
		{
			long startTime=0;
			long endTime=0;
			
			/**
			 * TRIGGER BATCH
			 */
			buildOperation.triggerBatch(URLFactory.TRIGGER_BATCH_URL,batchID,batchCount);
			
			/**
			 * GETTING REQUEST ID FOR THE TRIGGERED BATCH
			 */
			
			String reqInitTime=buildOperation.getBatchRequestId(URLFactory.BATCH_GETREQID_URL,batchName,batchCount);
			
			/**
			 * POLLING EVERY 5 MINS TO GET THE TRIGGERED BATCH STATUS
			 */
			startTime=Long.valueOf(reqInitTime);
			endTime=startTime+60000;
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
		
		/**
		 * COMPARAING BATCH REQUEST OF DEVELOPER AND PLATFORM
		 */
		
		buildOperation.doCompareBatches(URLFactory.COMPARE_BATCH_URL);
		
		/**
		 * Destroying all the objects and closing all the networks
		 */
		
		
		applicationContext.getBean(TerminateBean.class);
		applicationContext.close();
	}
	
	
	
}
