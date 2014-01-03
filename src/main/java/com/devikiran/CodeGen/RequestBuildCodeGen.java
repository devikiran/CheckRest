package com.devikiran.CodeGen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.devikiran.template.Request;

public class RequestBuildCodeGen {
	Request datamerger;

	HttpClientBuilder builder = null;
	CloseableHttpClient httpClient = null;
	CloseableHttpResponse response = null;

	public RequestBuildCodeGen(Request data) {
		this.datamerger = data;
	}

	public void buildRequest() {
		httpClient = HttpClients.createDefault();
			try {
			HttpRequestBase reqBase = datamerger.getVerb().getRequest();
			reqBase.setURI(new URI(datamerger.getUrl()));

			for(com.devikiran.entities.Header header : datamerger.getHeaders())
			{
				reqBase.setHeader(header.getKey(), header.getValue());
			}
			if (datamerger.getBody().length() > 0) {
				if (reqBase instanceof HttpPost) {
					reqBase = (HttpPost) reqBase;
					((HttpPost) reqBase).setEntity(new StringEntity(
							datamerger.getBody()));
					System.out.println(datamerger.getBody() + "body");

				}

			} 
			response = httpClient.execute(reqBase);

			
			for (Header header : response.getAllHeaders()) {
				System.out.println(header.getName() + " headers "
						+ header.getValue());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
		} catch (IOException  e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		;
	}
}
