package com.yodlee.buildautomation.BuildAutomation.authdetails;

import java.util.Properties;

import com.yodlee.buildautomation.BuildAutomation.ReadPropertiesFile;

public class AuthenticationDeatils {
	private static AuthenticationDeatils authenticationDeatils=new AuthenticationDeatils();
	private static final String AUTH_DETAIL_USERNAME_KEY="username";
	private static final String AUTH_DETAIL_PASSWORD_KEY="password";
	
	private static final String AUTH_DETAIL_PROP_FILE="authdetails.properties";
	
	private static Properties buildProps = ReadPropertiesFile.getProperties(AUTH_DETAIL_PROP_FILE);
	private static String userDetail=buildProps.getProperty(AUTH_DETAIL_USERNAME_KEY);
	private static String passDetail=buildProps.getProperty(AUTH_DETAIL_PASSWORD_KEY);
	
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
	
	public static AuthenticationDeatils setAuthDetails()
	{
		authenticationDeatils.setUsername(userDetail);
		authenticationDeatils.setPassword(passDetail);
		return authenticationDeatils;
	}
	
	
}
