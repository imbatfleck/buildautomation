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


public class RestOperation implements ClientHttpRequestInterceptor{
	
	public void setHeaders(HttpHeaders httpHeaders,String token)
	{
		httpHeaders.set("Authorization",token);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	}

	public <T,M> M getPostResponseExchange(String url,HttpHeaders httpHeaders,T requestObject,M responseObject,RestTemplate restTemplate) throws JsonProcessingException
	{
		HttpEntity<T> entity = new HttpEntity<>(requestObject, httpHeaders);
		ObjectMapper objectMapper=new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String cbProp2=objectMapper.writeValueAsString(requestObject);
		System.out.println(cbProp2);
		
		ResponseEntity<M> responseData=(ResponseEntity<M>) restTemplate.exchange(url, HttpMethod.POST, entity,responseObject.getClass());
		return responseData.getBody();
	}
	
	public <T,M> M getPostResponseEntity(String url,T requestObject,M responseObject,RestTemplate restTemplate)
	{
		ResponseEntity<M> responseData=(ResponseEntity<M>) restTemplate.postForEntity(url, requestObject, responseObject.getClass());
		return responseData.getBody();
	}
	
	 private void logRequest(HttpRequest request, byte[] body) throws IOException {
	        
	            System.out.println("===========================request begin================================================");
	            
	            System.out.println("Headers     : {}"+request.getHeaders());
	            System.out.println("Request body: {}"+new String(body, "UTF-8"));
	            System.out.println("==========================request end================================================");
	        
	
	
	 }

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		// TODO Auto-generated method stub
		logRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		return response;
	}
}
