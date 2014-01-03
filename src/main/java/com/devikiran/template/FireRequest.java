package com.devikiran.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class FireRequest {
	StringBuilder output = new StringBuilder();
	CloseableHttpResponse response = null;
	CloseableHttpClient httpClient = null;
	HttpRequestBase reqBase = null;

	public FireRequest(HttpRequestBase requestType) {
		this.reqBase = requestType;
	}

	public StringBuilder executeRequest() {
		try {
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(reqBase);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String line;

			line = br.readLine();

			while (line != null) {
				output.append(line);
				output.append('\n');
				line = br.readLine();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

}
