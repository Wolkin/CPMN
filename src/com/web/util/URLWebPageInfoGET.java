package com.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

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
			httpUrl.setRequestProperty("Content-type", "text/html");
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
	
	public static String urlEncode(String url) {
		if(url == null) {
            return null;
        }
        
        final String reserved_char = ";/?:@=&";
        String ret = "";
        for(int i=0; i < url.length(); i++) {
            String cs = String.valueOf( url.charAt(i) );
            if(reserved_char.contains(cs)){
                ret += cs;
            }else{
                try {
					ret += URLEncoder.encode(cs, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        return ret.replace("+", "%20");
	}
	
	public static void main(String[] args) {
		System.out.println(urlEncode("http://www.geekdev.club/CPMN/cpmnmain.html"));
	}
}
