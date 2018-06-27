package com.yodlee.buildautomation.BuildAutomation.batchcreation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CBItemProp {
	
	
	private List<CBAddItemProps> Add;

	
	





	@JsonProperty("Add")
	public List<CBAddItemProps> getAdd() {
		return Add;
	}








	public void setAdd(List<CBAddItemProps> Add) {
		this.Add = Add;
	}








	public CBItemProp() {
		super();
	}




	/*@Override
	public String toString() {
		return "[Add:" + Add + "]";
	}
	
	*/

}
