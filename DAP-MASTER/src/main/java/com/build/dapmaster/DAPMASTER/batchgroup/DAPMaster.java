package com.build.dapmaster.DAPMASTER.batchgroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.build.dapmaster.DAPMASTER.batchdetails.BatchDetails;
import com.yodlee.buildautomation.BuildAutomation.ReadPropertiesFile;


public class DAPMaster{
	
	private static final String BUILD_DETAIL_KEY="build.detail";
	private static final String BUILD_DETAIL_PROP_FILE="batchdetails.properties";
	
	private static Properties buildProps = ReadPropertiesFile.getProperties(BUILD_DETAIL_PROP_FILE);
	private static String buildDetails=buildProps.getProperty(BUILD_DETAIL_KEY);
	
	private static LinkedHashSet<BatchDetails> batchDeatilSet=new LinkedHashSet<>();
	
	
	public LinkedHashSet<BatchDetails> getBatchDetailsInfo() {
		
		
		String[] batchList=buildDetails.split(",");
		for(String bat:batchList)
		{
			BatchDetails batchDetails=new BatchDetails();
			String[] batchDet=bat.split("\\|");
			batchDetails.setBatchName(batchDet[0]);
			batchDetails.setBatchID(batchDet[1]);
			batchDeatilSet.add(batchDetails);
		}
		
		return batchDeatilSet;
	}

	
	
	

}
