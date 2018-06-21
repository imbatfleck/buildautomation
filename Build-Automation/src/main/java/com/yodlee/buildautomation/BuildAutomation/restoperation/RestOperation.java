package com.yodlee.buildautomation.BuildAutomation.restoperation;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class RestOperation {
	
	public void setHeaders(HttpHeaders httpHeaders,String token)
	{
		httpHeaders.set("Authorization",token);
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	}

	public <T,M> M getPostResponseExchange(String url,HttpHeaders httpHeaders,T requestObject,M responseObject,RestTemplate restTemplate)
	{
		HttpEntity<T> entity = new HttpEntity<>(requestObject, httpHeaders);
		ResponseEntity<M> responseData=(ResponseEntity<M>) restTemplate.exchange(url, HttpMethod.POST, entity,responseObject.getClass());
		return responseData.getBody();
	}
	
	public <T,M> M getPostResponseEntity(String url,T requestObject,M responseObject,RestTemplate restTemplate)
	{
		ResponseEntity<M> responseData=(ResponseEntity<M>) restTemplate.postForEntity(url, requestObject, responseObject.getClass());
		return responseData.getBody();
	}
	
	
}
