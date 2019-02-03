package com.web.util;

import java.io.BufferedReader;
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
			 * �趨Http�������Բ���
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
	        
	        //��������
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
	        	
	        	//�ͷ���Դ
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
	
	public static String signRequest(Map params){
		// ��һ�����Բ�������ASCII����
		String[] keys = (String[]) params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		// �ڶ����������в������Ͳ���ֵ����һ��
		StringBuilder query = new StringBuilder();
		for (String key : keys) {
			String value = params.get(key).toString();
	        query.append(key).append(value);
		}
		// ��������md5����
		return DigestUtils.md5Hex(query.toString());
	}
	
	public static void main(String[] args) {
		
	}
}