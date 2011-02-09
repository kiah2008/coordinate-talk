/**
 * 此程序为开源程序，遵循GPLv3版本发布，并受其保护。
 * (GPLv3 http://www.gnu.org/licenses/gpl.html)
 * Copyright 2011 by H!Guo
 */
package org.guohai.android.cta.bll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.guohai.android.cta.model.MessageInfo;
import org.guohai.android.cta.model.ParseGpsInfo;

/**
 * Http Post 
 * @author H!Guo 
 */
public class HttpRest {

	/** 反向解析 地址  */
	public String GetParse(ParseGpsInfo parm){
		//create http client
		HttpClient client = new DefaultHttpClient();
		//create post request
		HttpPost httpPost = new HttpPost("http://android.guohai.org/api/?fun=parse");
		
		List <NameValuePair> dataList = new ArrayList <NameValuePair>();
		dataList.add(new BasicNameValuePair("SendAccount",parm.SendAccount));
		dataList.add(new BasicNameValuePair("Latitude", Double.toString(parm.Latitude)));
		dataList.add(new BasicNameValuePair("Longitude",Double.toString(parm.Longitude)));
		HttpEntity entity;
		try {
			entity = new UrlEncodedFormEntity(dataList,HTTP.UTF_8);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "UnsupportedEncodingException";
		}
		httpPost.setEntity(entity);
		
		//向服务器POST数据 
		try {
			HttpResponse response = client.execute(httpPost);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entityHtml = response.getEntity();
				BufferedReader  reader = new BufferedReader(new InputStreamReader(entityHtml.getContent(),"UTF-8"));
				String line = null;
				String reString = "";
				while((line = reader.readLine())!=null){
					reString += line;
				}
				if(entityHtml!=null){
					entityHtml.consumeContent();
				}
				return reString;
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "失败了IOException";
		}
		return "失败了over";
	}
	
	/** 增加一   */
	public String AddMessage(MessageInfo message)
	{
		//create http client
		HttpClient client = new DefaultHttpClient();
		//create post request
		HttpPost httpPost = new HttpPost("http://android.guohai.org/api/?fun=add");
		
		List <NameValuePair> dataList = new ArrayList <NameValuePair>();
		dataList.add(new BasicNameValuePair("Note",message.Note));
		dataList.add(new BasicNameValuePair("SendAccount",message.SendAccount));
		dataList.add(new BasicNameValuePair("Latitude", Double.toString(message.Latitude)));
		dataList.add(new BasicNameValuePair("Longitude",Double.toString(message.Longitude)));
		dataList.add(new BasicNameValuePair("Altitude",Double.toString(message.Altitude)));
		HttpEntity entity;
		try {
			entity = new UrlEncodedFormEntity(dataList,HTTP.UTF_8);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "UnsupportedEncodingException";
		}
		httpPost.setEntity(entity);
		
		//向服务器POST数据 
		try {
			HttpResponse response = client.execute(httpPost);
			return response.getStatusLine().getStatusCode()+"";
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "失败了IOException";
		}
		return "失败了over";
	}
}