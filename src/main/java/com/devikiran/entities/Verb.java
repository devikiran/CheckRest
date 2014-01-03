package com.devikiran.entities;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

public enum Verb {
	
	GET(new HttpGet()),POST(new HttpPost());
	private HttpRequestBase request;
	private  Verb(HttpGet get)
	{
		this.request = get;
	}
	private  Verb(HttpPost post)
	{
		this.request = post;
	}
	public HttpRequestBase getRequest()
	{
		return request;
	}
	
}	

