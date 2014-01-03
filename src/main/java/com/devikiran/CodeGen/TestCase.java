package com.devikiran.CodeGen;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;


import org.testng.annotations.Test;

import com.devikiran.entities.Parameter;
import com.devikiran.template.FireRequest;
import com.devikiran.template.Request;
//import com.devikiran.utils.JsonParser;

public class TestCase {
	HttpRequestBase request = null;
	String fileLocation=".\\src\\test\\resources\\TestData.json";
	Request inputRequest =null;

@Test
	public void basicTest() {
		try {
			//inputRequest = new JsonParser().getRequest(fileLocation);
			request = inputRequest.getVerb().getRequest();
			request.setURI(new URI(inputRequest.getUrl()));
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			
			for (Parameter param : inputRequest.getParameters()) 
			{
				formparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
			URI uri = new URIBuilder(request.getURI())
					.addParameters(formparams).build();
			(request).setURI(uri);

			System.out.println(new FireRequest(request).executeRequest());

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}
}
