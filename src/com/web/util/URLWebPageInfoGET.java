package com.web.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

public class URLWebPageInfoGET {
	
	public static String getURLPageInfo(String urlStr) {
		String returnStr = "";
		
		try {
			URL url = new URL(urlStr);
			HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
			
			/**
			 * 设定Http连接属性参数
			 */
			httpUrl.setRequestMethod("POST");
			httpUrl.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)");
			httpUrl.setRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
			//httpUrl.setRequestProperty("Accept-Language", "zh-cn");
			//httpUrl.setRequestProperty("UA-CPU", "x86");
			//httpUrl.setRequestProperty("Accept-Encoding", "gzip");
			httpUrl.setRequestProperty("Content-type", "application/json");
			//httpUrl.setRequestProperty("Connection", "close");
			httpUrl.setUseCaches(false);
	        httpUrl.setConnectTimeout(6 * 1000);
	        httpUrl.setReadTimeout(6 * 1000);
	        httpUrl.setDoOutput(true);
	        httpUrl.setDoInput(true);
	        httpUrl.setRequestProperty("Charset", "utf-8");
	        
	        //建立连接
	        httpUrl.connect();
	        
	        if (httpUrl.getResponseCode() == 200) {
	        	InputStream inputStream = null;
	        	/*
	        	if (httpUrl.getContentEncoding() != null && !httpUrl.getContentEncoding().equals("")) {
	        		String encode = httpUrl.getContentEncoding().toLowerCase();
	        		if (encode != null && !encode.equals("") && encode.indexOf("gzip") >= 0) {
	        			inputStream = new GZIPInputStream(httpUrl.getInputStream());
	        		}
	        	}
	        	*/
	        	if (null == inputStream) {
	        		inputStream = httpUrl.getInputStream();
	        	}
	        	
	        	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
	        	StringBuilder builder = new StringBuilder();
	        	String line = null;
	        	while ((line = reader.readLine()) != null) {
	        		builder.append(line).append("\n");
	        	}
	        	returnStr = builder.toString();
	        	
	        	//释放资源
	        	inputStream.close();
	        	reader.close();
	        }
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnStr;
	}
	
	public static String urlHttpRequestDO(String urlStr,String params) {
		HttpURLConnection connection = null;
		StringBuffer buffer = new StringBuffer();
		
		String returnStr = "";
		
		try {
			URL url = new URL(urlStr);
			//使用URL打开一个链接
			connection = (HttpURLConnection) url.openConnection();
			
			//允许输出流，即允许上传
			connection.setDoOutput(true);
			//允许输入流，即允许下载
			connection.setDoInput(true);
			//使用post请求
			connection.setRequestMethod("POST");
			//不使用缓冲
			connection.setUseCaches(false);
			connection.setConnectTimeout(5000);
			//自动执行HTTP重定向 
			//this.connection.setInstanceFollowRedirects(false);
			//application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据 application/json;charset=utf-8 -> json数据
			//设定 请求格式 json，也可以设定xml格式的
			connection.setRequestProperty("Content-Type","application/json");
			connection.connect();
			
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(params);
            out.flush();
            out.close();
			
			if(connection.getResponseCode() == 200) {
				InputStream inputStream = connection.getInputStream();
				
		        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		    
		        String str = "";
		        while ((str = bufferedReader.readLine()) != null) {
		        	buffer.append(str);
		        }
		        // 释放资源    
		        returnStr = buffer.toString();
		        bufferedReader.close();
		        inputStreamReader.close();
		        
		        inputStream.close();
		        inputStream = null;
			}
			
			connection.disconnect();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnStr;
	}
	
	public static String signRequest(Map params){
		// 第一步：对参数进行ASCII排序
		String[] keys = (String[]) params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		// 第二步：把所有参数名和参数值串在一起
		StringBuilder query = new StringBuilder();
		for (String key : keys) {
			String value = params.get(key).toString();
	        query.append(key).append(value);
		}
		// 第三步：md5加密
		return DigestUtils.md5Hex(query.toString());
	}
	
	public static void main(String[] args) {
		
	}
}
