package com.build.dapmaster.DAPMASTER;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.build.dapmaster.DAPMASTER.batchcreateiondetails.BatchCreationDetails;
import com.build.dapmaster.DAPMASTER.batchcreateiondetails.Items;
import com.build.dapmaster.DAPMASTER.batchdetails.BatchDetails;
import com.build.dapmaster.DAPMASTER.batchgroup.DAPMaster;
import com.yodlee.buildautomation.BuildAutomation.BuildAutomationApplication;
import com.yodlee.buildautomation.BuildAutomation.controller.BuildOperation;
import com.yodlee.buildautomation.BuildAutomation.serverurl.URLFactory;
import com.yodlee.buildautomation.BuildAutomation.utilities.Utility;

@SpringBootApplication
public class DapMasterApplication{
	
	public static void main(String[] args) throws IOException, InterruptedException, MessagingException, ParseException {
			
			BuildAutomationApplication.createAuthPool();
			BuildAutomationApplication.createRouteList();
			
			DAPMaster dapMaster=new DAPMaster();
			LinkedHashSet<BatchDetails> batchList=dapMaster.getBatchDetailsInfo();
			BuildAutomationApplication.batchProcessCount=batchList.size();
			for(BatchDetails bdetails:batchList)
			{
				BuildAutomationApplication.setBatchParams(bdetails.getBatchName(), bdetails.getBatchID(), args);
				Thread.sleep(2000);
			}
		
		/*BuildAutomationApplication.createAuthPool();
		BuildAutomationApplication.createBatch();*/
		//getCurrentTimeUsingCalendar();
		/* Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		 String str=timestamp.toString();
		 System.out.println("++++++++++++++++str="+str);
	     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	     Date date = df.parse(str);
	     long epoch = date.getTime();
	     System.out.println(epoch);
	     long a1=1530617222000l;
	     long a2=1530618162000l;
	     System.out.println("+++++++++time diff="+(a2-a1));
	     double minTime=(((a2-a1)/1000)/60.00);
	     System.out.println("+++++++++time diff="+minTime);
	     if(minTime>15)
	     {
	    	 System.out.println("true");
	     }
	     else
	     {
	    	 System.out.println("false");
	     }*/
		
	}
	
	

	
	
    
}
