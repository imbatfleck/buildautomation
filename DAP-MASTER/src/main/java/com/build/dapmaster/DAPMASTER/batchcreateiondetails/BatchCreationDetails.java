package com.build.dapmaster.DAPMASTER.batchcreateiondetails;

import java.util.List;

public class BatchCreationDetails {
	private String agentName;
	private String description;
	private String nickName;
	private List<Items> itemList;
	
	
	public List<Items> getItemList() {
		return itemList;
	}
	public void setItemList(List<Items> itemList) {
		this.itemList = itemList;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public BatchCreationDetails() {
		super();
	}
	
	

}
