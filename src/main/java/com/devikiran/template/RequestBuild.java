package com.devikiran.template;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import com.devikiran.entities.Parameter;

public class RequestBuild {
	Request datamerger;
	HttpClientBuilder builder = null;
	StringBuilder output = new StringBuilder();
	HttpRequestBase reqBase =null;

	public RequestBuild(Request data) {
		this.datamerger = data;
	}

	public FireRequest buildRequest() {
		try {
			reqBase= datamerger.getVerb().getRequest();
			reqBase.setURI(new URI(datamerger.getUrl()));

			for (com.devikiran.entities.Header header : datamerger.getHeaders()) {
				reqBase.setHeader(header.getKey(), header.getValue());
			}
			if (datamerger.getBody().length() > 0) {
				if (reqBase instanceof HttpPost) {
					reqBase = (HttpPost) reqBase;
					((HttpPost) reqBase).setEntity(new StringEntity(datamerger
							.getBody()));
				}
			}
			if (datamerger.getParameters().size() > 0)
			{
				List<NameValuePair> formparams = new ArrayList<NameValuePair>();
				for (Parameter param : datamerger.getParameters()) {
					formparams.add(new BasicNameValuePair(param.getKey(), param
							.getValue()));
				}

				URI uri = new URIBuilder(reqBase.getURI()).addParameters(
						formparams).build();
				(reqBase).setURI(uri);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return new FireRequest(reqBase);
	}
}
