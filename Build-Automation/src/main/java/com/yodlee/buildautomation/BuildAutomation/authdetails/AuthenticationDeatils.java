package com.yodlee.buildautomation.BuildAutomation.authdetails;

public class AuthenticationDeatils {
	
	private String username;
	private String password;
	public AuthenticationDeatils(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public AuthenticationDeatils() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
