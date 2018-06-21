package com.build.dapmaster.DAPMASTER;

import java.io.IOException;
import java.util.LinkedHashSet;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.build.dapmaster.DAPMASTER.batchdetails.BatchDetails;
import com.build.dapmaster.DAPMASTER.batchgroup.DAPMaster;
import com.yodlee.buildautomation.BuildAutomation.BuildAutomationApplication2;

@SpringBootApplication
public class DapMasterApplication{
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
			
			DAPMaster dapMaster=new DAPMaster();
			LinkedHashSet<BatchDetails> batchList=dapMaster.getBatchDetailsInfo();
			for(BatchDetails bdetails:batchList)
			{
				System.out.println(bdetails.getBatchName()+" has started");
				BuildAutomationApplication2.setBatchParams(bdetails.getBatchName(), bdetails.getBatchID(), args);
				System.out.println(bdetails.getBatchName()+" has finished");
				Thread.sleep(5000);
			}
			
		
	}

	

	
	
    
}