package com.cjm.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jdk.internal.org.xml.sax.InputSource;

import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

/**
 * @time 2016年4月14日
 * @author CHEN
 * @param 
 * @about 获得json返回值
 */

public class MyHttpClient {
	/**
	 * @about 通过url获得json返回内容
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getJsonDate(String url) throws ClientProtocolException, IOException {
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost(url);
		//获得响应对象
		HttpResponse response = client.execute(post);
		//响应状态
		Integer statusCode=response.getStatusLine().getStatusCode();
		
		if(statusCode!=HttpStatus.SC_OK) {
			throw new HttpClientError("http status is ERROR");
		}
		
		HttpEntity entityRsp=response.getEntity();
		StringBuffer result=new StringBuffer();
		BufferedReader rd=new BufferedReader(new InputStreamReader(
				entityRsp.getContent(),HTTP.UTF_8));
		
		String tempLine=rd.readLine();
		while(tempLine!=null) {
			result.append(tempLine);
			tempLine=rd.readLine();
		}
		if(entityRsp!=null) {
			entityRsp.consumeContent();
		}
		return result.toString();
	}
}
