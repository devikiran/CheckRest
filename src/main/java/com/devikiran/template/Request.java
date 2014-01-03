package com.devikiran.template;

import java.util.List;

import com.devikiran.entities.Body;
import com.devikiran.entities.Header;
import com.devikiran.entities.Headers;
import com.devikiran.entities.Parameter;
import com.devikiran.entities.Parameters;
import com.devikiran.entities.URL;
import com.devikiran.entities.Verb;

public class Request {

	private String url;// = new URL();
	private List<Header> headers;// = new Headers();
	
	private List<Parameter> parameters;// = new Parameters();
	private Verb verb ;
	private String body;//=new Body();
	
	public Request()
	{
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Header> getHeaders() {
		return headers;
	}

	public void setHeaders(List<Header> headers) {
		this.headers = headers;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public Verb getVerb() {
		return verb;
	}

	public void setVerb(Verb verb) {
		this.verb = verb;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	/*public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public Headers getHeaders() {
		return headers;
	}

	public void setHeaders(Headers headers) {
		this.headers = headers;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	public Verb getVerb() {
		return verb;
	}
	
	
	
	public void setVerb(Verb verb) {
		this.verb = verb;
	}
*/
}
