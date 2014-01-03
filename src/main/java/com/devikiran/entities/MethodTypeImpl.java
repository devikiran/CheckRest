package com.devikiran.entities;

import org.apache.http.client.methods.*;

public class MethodTypeImpl {
		public MethodTypeImpl(String methodValue) {
		super();
		this.methodValue = methodValue;
	}

		Verb methodType;

	String methodValue;

	public String getMethodValue() {
		return methodValue;
	}


	/*public MethodType methodValue() {
		if (methodValue.equals("GET")) {
			methodType = methodType.GET;
		} else if (methodValue.equals("POST")) {
			methodType = methodType.POST;
		}
		return methodType;
	}*/

	/*public HttpRequestBase MethodType() {
		HttpRequestBase req = null;
		switch (methodValue) {
		
		case "GET":
			System.out.println(" in the GET");
			req=new HttpGet();
			break;
			//return req;
		case "POST":
			System.out.println("in the POST");
			req= new HttpPost();
			break;
		case "DELETE":
			System.out.println("in the DELETE");
			req=new HttpDelete();
			break;
		case "OPTIONS":
			System.out.println("OPTIONS");
			req= new HttpOptions();
			break;
		case "PUT":
			System.out.println("PUT");
			req= new HttpPut();
			break;
		case "PATCH":
			System.out.println("PATCH");
			req= new HttpPatch();
			break;
		case "HEAD":
			System.out.println("Head");
			req= new HttpHead();
			break;

		}
		return req;
	}*/
	
}
