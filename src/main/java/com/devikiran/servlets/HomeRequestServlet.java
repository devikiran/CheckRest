package com.devikiran.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devikiran.CodeGen.NewCodeGenerator;
import com.devikiran.entities.Header;
import com.devikiran.template.Request;
import com.devikiran.template.RequestBuild;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class HomeRequestServlet extends HttpServlet {
	Request req = new Request();
	String jsonData = null;
	StringBuilder res=null;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String json = "";
		if (br != null) {
			json = br.readLine();
		}
		jsonData = json;
		Gson gson = new Gson();
		Request req = gson.fromJson(json, Request.class);
		new NewCodeGenerator().setInputReq(req);
		res=new RequestBuild(req).buildRequest().executeRequest();
		System.out.println("response  "+res);
	}
/*	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("in the doGet");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			
			out.write(res.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
	}*/

	

	public List<Header> listToHeader(List<String> values) {
		List<Header> headers = new ArrayList<Header>();

		if (values.size() >= 0) {

			for (int i = 1; i < values.size(); i++) {

				for (int j = 0; j < i; j++) {
					//headers.add(new Header(values.get(i), values.get(j)));
				}

			}
		}
				return headers;
	}

}
