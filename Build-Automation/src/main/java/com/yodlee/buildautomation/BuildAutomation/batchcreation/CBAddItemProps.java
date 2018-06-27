package com.yodlee.buildautomation.BuildAutomation.batchcreation;

public class CBAddItemProps {
	private String itemId;
	private String dbName;
	private String description;
	private String itemType;
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public CBAddItemProps() {
		super();
	}
	/*@Override
	public String toString() {
		return "{itemId:" + itemId + ", dbName:" + dbName + ", description:" + description
				+ ", itemType:" + itemType + "}";
	}
	*/
	
	
}
