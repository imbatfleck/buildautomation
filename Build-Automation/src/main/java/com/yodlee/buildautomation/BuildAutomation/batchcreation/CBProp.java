package com.yodlee.buildautomation.BuildAutomation.batchcreation;

import java.util.ArrayList;
import java.util.List;

public class CBProp {
	
	private static CBProp cbProp=new CBProp();
	private static CBItemProp cbItemProp=new CBItemProp();
	
	
	private String agentName;
	private String description;
	private String nickName;
	private CBItemProp items;
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
	public CBItemProp getItems() {
		return items;
	}
	public void setItems(CBItemProp items) {
		this.items = items;
	}
	public CBProp() {
		super();
	}
	
	public static CBProp setBatchValues(String agentName,String description,String nickName,List<CBAddItemProps> itemList)
	{
		
		List<CBAddItemProps> listcii=new ArrayList<>();
		
		for(CBAddItemProps item: itemList)
		{
			CBAddItemProps cbAddItemProps=new CBAddItemProps();
			System.out.println("+++++++++++get cii:"+item.getItemId());
			System.out.println("+++++++++++get db:"+item.getDbName());
			System.out.println("+++++++++++get desc:"+item.getDescription());
			System.out.println("+++++++++++get type:"+item.getItemType());
			cbAddItemProps.setItemId(item.getItemId());
			cbAddItemProps.setDbName(item.getDbName());
			cbAddItemProps.setDescription(item.getDescription());
			cbAddItemProps.setItemType(item.getItemType());
			listcii.add(cbAddItemProps);
		}
		cbItemProp.setAdd(listcii);
		cbProp.setAgentName(agentName);
		cbProp.setDescription(description);
		cbProp.setNickName(nickName);
		cbProp.setItems(cbItemProp);
		return cbProp;
	}
	/*@Override
	public String toString() {
		return "{agentName:" + agentName + ", description:" + description + ", nickName:" + nickName + ", items:"
				+ items + "}";
	}*/
	
	

}
