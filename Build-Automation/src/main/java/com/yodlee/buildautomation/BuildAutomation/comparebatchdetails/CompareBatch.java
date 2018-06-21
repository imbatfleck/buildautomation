package com.yodlee.buildautomation.BuildAutomation.comparebatchdetails;

public class CompareBatch {
	    private static CompareBatch compareBatch=new CompareBatch();
		private String batchReqDetailsId1;
		private String batchReqDetailsId2;
		private String batchName;
		public String getBatchReqDetailsId1() {
			return batchReqDetailsId1;
		}
		public void setBatchReqDetailsId1(String batchReqDetailsId1) {
			this.batchReqDetailsId1 = batchReqDetailsId1;
		}
		public String getBatchReqDetailsId2() {
			return batchReqDetailsId2;
		}
		public void setBatchReqDetailsId2(String batchReqDetailsId2) {
			this.batchReqDetailsId2 = batchReqDetailsId2;
		}
		public String getBatchName() {
			return batchName;
		}
		public void setBatchName(String batchName) {
			this.batchName = batchName;
		}
		public CompareBatch() {
			super();
		}
		public static CompareBatch setCompareBatchDetails(String batchReq1,String batchReq2)
		{
			compareBatch.setBatchReqDetailsId1(batchReq1);
			compareBatch.setBatchReqDetailsId2(batchReq2);
			compareBatch.setBatchName("");
			return compareBatch;
		}
}
