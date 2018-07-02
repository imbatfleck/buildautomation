package com.yodlee.buildautomation.BuildAutomation.restoperation;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yodlee.buildautomation.BuildAutomation.BuildAutomationApplication;


public class RestOperation{
	public void setHeaders(HttpHeaders httpHeaders,String token)
	{
		BuildAutomationApplication.logger.info("Settinh headers");
		httpHeaders.set("Authorization",token);
		BuildAutomationApplication.logger.info("Authorization - {}",token);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		BuildAutomationApplication.logger.info("Content-Type - {}",MediaType.APPLICATION_JSON);
	}

	public <T,M> M getPostResponseExchange(String url,HttpHeaders httpHeaders,T requestObject,M responseObject,RestTemplate restTemplate) throws JsonProcessingException
	{
		BuildAutomationApplication.logger.info("Post Request Making");
		BuildAutomationApplication.logger.info("Posting URL - {}",url);
		HttpEntity<T> entity = new HttpEntity<>(requestObject, httpHeaders);
		ObjectMapper objectMapper=new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String cbProp2=objectMapper.writeValueAsString(requestObject);
		BuildAutomationApplication.logger.info("Posting Data - {}",cbProp2);
		ResponseEntity<M> responseData=(ResponseEntity<M>) restTemplate.exchange(url, HttpMethod.POST, entity,responseObject.getClass());
		BuildAutomationApplication.logger.info("Data Posted");
		BuildAutomationApplication.logger.info("Status - {}",responseData.getStatusCode());
		return responseData.getBody();
	}
	
	public <T,M> M getPostResponseEntity(String url,T requestObject,M responseObject,RestTemplate restTemplate) throws JsonProcessingException
	{
		BuildAutomationApplication.logger.info("Post Request Making");
		BuildAutomationApplication.logger.info("Posting URL - {}",url);
		ObjectMapper objectMapper=new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String cbProp2=objectMapper.writeValueAsString(requestObject);
		BuildAutomationApplication.logger.info("Posting Data - {}",cbProp2);
		ResponseEntity<M> responseData=(ResponseEntity<M>) restTemplate.postForEntity(url, requestObject, responseObject.getClass());
		BuildAutomationApplication.logger.info("Data Posted");
		BuildAutomationApplication.logger.info("Status - {}",responseData.getStatusCode());
		return responseData.getBody();
	}
	

	
}
