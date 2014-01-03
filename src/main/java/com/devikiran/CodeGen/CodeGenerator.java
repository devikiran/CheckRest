package com.devikiran.CodeGen;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.devikiran.template.Request;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JTryBlock;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

public class CodeGenerator {
	

	public void writeCodeModel(String factroyPackage) {
		try {

			/* Creating java code model classes */
			JCodeModel jCodeModel = new JCodeModel();

			/* Adding packages here */
			JPackage jp = jCodeModel._package(factroyPackage);
					/* Giving Class Name to Generate */
			JDefinedClass jc = jp._class("HttpTest");
			JFieldVar ReqField = jc.field(JMod.PRIVATE, com.devikiran.template.Request.class,"datamerger");
			JFieldVar httpBuilder = jc.field(JMod.PRIVATE,HttpClientBuilder.class, "builder");
			JFieldVar CloseableHttpClient = jc.field(JMod.PRIVATE,CloseableHttpClient.class, "httpClient");
			JFieldVar CloseableHttpResponse = jc.field(JMod.PRIVATE,CloseableHttpResponse.class, "response");
			JFieldVar HttpClients = jc.field(JMod.PRIVATE,HttpClients.class, "httpclients");
		

			JMethod constructorMethod = jc.constructor(JMod.PUBLIC);
			String paramName = "requestobject";
			constructorMethod.param(jCodeModel.directClass("com.devikiran.template.Request"),
					paramName);
			JBlock jBlock = constructorMethod.body();
			JClass jconstructor = jCodeModel.ref(Request.class);

			jBlock.assign(JExpr._this().ref("datamerger"), JExpr.ref(paramName));

			/* Adding class level coment */
			JDocComment jDocComment = jc.javadoc();
			jDocComment.add("Class Level Java Docs");

			JClass rawInterface = jCodeModel.ref(Request.class);
			/*
			 * Adding method to the Class which is public static and returns
			 * com.somclass.AnyXYZ.class
			 */
			String mehtodName = "buildRequest";
			JMethod jmbuildCreate = jc.method(JMod.PUBLIC,jCodeModel.VOID,
					 mehtodName);

						
			/* Addign java doc for method */
			//jmCreate.javadoc().add("Method Level Java Docs");

			/* Adding method body */
			jBlock = jmbuildCreate.body();
			JClass expection =jCodeModel.ref(IOException.class);
			JTryBlock jb=jBlock._try();
			jb.body().directStatement("httpClient = httpclients.createDefault();");
			jb._catch(expection);
			//body().directStatement("httpClient = httpclients.createDefault();");
			
		
			/* Adding some direct statement */
			
			/* Building class at given location */
			jCodeModel.build(new File("E:\\tmp\\CheckRest\\src\\main\\java"));
			// jCodeModel.build(new File("E:\\Tesco"));
		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		CodeGenerator cf = new CodeGenerator();
		cf.writeCodeModel("com.devikiran.TestChecks");
	}

}
