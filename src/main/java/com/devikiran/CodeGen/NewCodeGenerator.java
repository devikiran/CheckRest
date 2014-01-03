package com.devikiran.CodeGen;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.apache.http.client.utils.URIBuilder;
import org.testng.annotations.Test;

import com.devikiran.template.Request;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JTryBlock;
import com.sun.codemodel.JTypeVar;
import com.sun.codemodel.JVar;

public class NewCodeGenerator {
	public static Request Req;
public Request getInputReq() {
		return Req;
	}

	public void setInputReq(Request inputReq/*,String factroyPackage*/) {
		this.Req = inputReq;
		System.out.println(Req.getUrl());
		new NewCodeGenerator().writeCode("com.devikiran.testcases");
		
	}

public void writeCode(String factroyPackage)

{
	JDefinedClass jc=null;
	try {
	JCodeModel jCodeModel = new JCodeModel();
	JPackage jp = jCodeModel._package(factroyPackage);
	jc = jp._class("HttpTest");
	
	JFieldVar ReqField = jc.field(JMod.PRIVATE, com.devikiran.template.Request.class,"inputRequest");
	JFieldVar reqBase=jc.field(JMod.PRIVATE, org.apache.http.client.methods.HttpRequestBase.class,"requestBase");
	JFieldVar uri=jc.field(JMod.PRIVATE, java.net.URI.class,"uri");
	System.out.println(Req.getUrl());
	
	JVar jvar = (JFieldVar) jc.field(17,URIBuilder.class, "urib").init(JExpr._null());
	
	
	String mehtodName = "buildRequest";
	JMethod basicTest = jc.method(JMod.PUBLIC,jCodeModel.VOID,mehtodName);
	JBlock jBlock = basicTest.body();
	//basicTest.annotate(Test.class);
	
	jBlock.directStatement("requestBase=inputRequest.getVerb().getRequest();");
	JClass expection =jCodeModel.ref(URISyntaxException.class);
	JTryBlock jb=jBlock._try();
	jb.body().directStatement("requestBase.setURI(new URI(inputRequest.getUrl()));");
	
	jb._catch(expection);
	if(Req.getParameters().size()>0)
	{
		jb.body().directStatement("com.devikiran.utils.TestcaseUtils.getParameters(inputRequest.getParameters());");
		//jb.body().directStatement("requestBase.setURI(new URI(inputRequest.getUrl()));");
		
	}
	JDefinedClass exampleClass = jCodeModel._class( "org.apache.http.client.utils.URIBuilder" );
	Iterator<JMethod> con=exampleClass.constructors();
	
	jb.body().assign(jvar, JExpr._new(exampleClass));
	/* JClass jClassavpImpl = jCodeModel.ref(URI.class);
	  JVar jvarAvpImpl = jBlock.decl(jClassavpImpl, "varName");
	  jvarAvpImpl.init(JExpr._new(jClassavpImpl));
       */
	
/*	JClass uribuilder =jCodeModel.ref(URIBuilder.class);
	JInvocation invocation = uribuilder.staticInvoke("getRoles");*/
	//basicTest.body().assign(JExpr.ref("uri"), JExpr._new(uriclass));
	
	
//	basicTest.body().assign(JExpr.ref("uri"), JExpr._new(exampleClass));
	jCodeModel.build(new File("E:\\TestCases"));
	
	} catch (JClassAlreadyExistsException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

public static void main(String[] args) {
	
	
}
}

