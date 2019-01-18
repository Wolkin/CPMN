package com.servlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class URLTest {
	private static StringBuffer mStringBuffer = new StringBuffer();
	
    public static void main(String[] args) {
    	URLTest test = new URLTest();
    	ArrayList<LotteryRecord> list = test.getLotteryList("http://kaijiang.zhcw.com/zhcw/html/ssq/list_1.html");
    	for (LotteryRecord lotteryRecord: list) {
    		System.out.println(lotteryRecord.toString());
    	}

    }

    public ArrayList<LotteryRecord> getLotteryList(String urlInfo) {
    	String content;
		try {
			content = getURLInfoString(urlInfo);
			if (content != null && !content.equals("")) {
	    		return getOneTermContent(content);
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public String getURLInfoString(String urlInfo) throws Exception {
        //读取目的网页URL地址，获取网页源码
        URL url = new URL(urlInfo);
        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
        httpUrl.setRequestMethod("POST");
        httpUrl.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)");
        httpUrl.setRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
        httpUrl.setRequestProperty("Accept-Language", "zh-cn");
        httpUrl.setRequestProperty("UA-CPU", "x86");	
        // 为什么没有deflate呢
        httpUrl.setRequestProperty("Accept-Encoding", "gzip");
        httpUrl.setRequestProperty("Content-type", "text/html");
        // keep-Alive，有什么用呢，你不是在访问网站，你是在采集。嘿嘿。减轻别人的压力，也是减轻自己。
        httpUrl.setRequestProperty("Connection", "close");
        // 不要用cache，用了也没有什么用，因为我们不会经常对一个链接频繁访问。（针对程序）
        httpUrl.setUseCaches(false);
        httpUrl.setConnectTimeout(6 * 1000);
        httpUrl.setReadTimeout(6 * 1000);
        httpUrl.setDoOutput(true);
        httpUrl.setDoInput(true);
        httpUrl.setRequestProperty("Charset", "utf-8");
        httpUrl.connect();

        if (httpUrl.getResponseCode() == 200) {
        	InputStream inputStream = null;
        	if (httpUrl.getContentEncoding() != null && !httpUrl.getContentEncoding().equals("")) {
        		String encode = httpUrl.getContentEncoding().toLowerCase();
        		if (encode != null && !encode.equals("") && encode.indexOf("gzip") >= 0) {
        			inputStream = new GZIPInputStream(httpUrl.getInputStream());
        		}
        	}
        	if (null == inputStream) {
        		inputStream = httpUrl.getInputStream();
        	}
        	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        	StringBuilder builder = new StringBuilder();
        	String line = null;
        	while ((line = reader.readLine()) != null) {
//        		System.out.println(line);
        		builder.append(line).append("\n");
        	}
        	inputStream.close();
        	reader.close();
        	return builder.toString();
        }
        return null;
    }

    /**
     * 获取一期开奖结果HTML
     * @param pageContent
     * @return
     */
    private ArrayList<LotteryRecord> getOneTermContent(String pageContent) {
    	ArrayList<LotteryRecord> lotteryRecordList = new ArrayList<LotteryRecord>();
    	String regex = "<td align=\"center\">\\d{4}-\\d{2}-\\d{2}</td>[\\s]+?<td align=\"center\">[\\s\\S]+?</em></td>";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(pageContent);
    	while (matcher.find()) {
    		String oneTermContent = matcher.group();
    		lotteryRecordList.add(getOneTermNumbers(oneTermContent));
    	}
    	return lotteryRecordList;
    }

    /**
     * 从一期开奖结果HTML中解析出开奖记录
     * @param oneTermContent
     * @return
     */
    private LotteryRecord getOneTermNumbers(String oneTermContent) {
    	LotteryRecord lotteryRecord = new LotteryRecord();
    	/**
    	 * 开奖日期
    	 */
    	String ballDateRegex = ">\\d{4}-\\d{2}-\\d{2}<";
    	Pattern pattern = Pattern.compile(ballDateRegex);
    	Matcher matcher = pattern.matcher(oneTermContent);
    	if (matcher.find()) {
    		String ballDate = matcher.group();
    		ballDate = ballDate.substring(1, ballDate.length() - 1);
    		mStringBuffer.append(ballDate).append("|");
        	lotteryRecord.setRrecordDate(ballDate);
    	}

    	/**
    	 * 期次
    	 */
    	String ballTermRegex = ">\\d{7}<";
    	pattern = Pattern.compile(ballTermRegex);
    	matcher = pattern.matcher(oneTermContent);
    	if (matcher.find()) {
    		String ballTerm = matcher.group();
    		ballTerm = ballTerm.substring(1, ballTerm.length() - 1);
    		mStringBuffer.append(ballTerm).append("|");
        	lotteryRecord.setLotteryTerm(ballTerm);
    	}

    	/**
    	 * 中奖号码
    	 */
    	String regex = ">\\d{2}<";
    	pattern = Pattern.compile(regex);
    	matcher = pattern.matcher(oneTermContent);
    	String[] lotteryNumbers = new String[7];
    	int i = 0;
    	while (matcher.find()) {
    		String content = matcher.group();
    		String ballNumber = content.substring(1, content.length() - 1);
    		mStringBuffer.append(ballNumber).append(" ");
    		lotteryNumbers[i] = ballNumber;
    		i++;
    	}
    	lotteryRecord.setLotteryNumbers(lotteryNumbers);
    	mStringBuffer.append("\r\n");
    	return lotteryRecord;
    }
}

class LotteryRecord {
    private String recordDate;	//开奖日期
    private String lotteryTerm;	//期次
    private String[] lotteryNumbers;	//开奖号码
    public String getRecordDate() {
        return recordDate;
    }
    public void setRrecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
    public String getLotteryTerm() {
        return lotteryTerm;
    }
    public void setLotteryTerm(String lotteryTerm) {
        this.lotteryTerm = lotteryTerm;
    }
    
    public String[] getLotteryNumbers() {
        return lotteryNumbers;
    }
    public void setLotteryNumbers(String[] lotteryNumbers) {
        this.lotteryNumbers = lotteryNumbers;
    }
    @Override
    public String toString() {
    	String numbers = "";
    	for (String number : lotteryNumbers) {
    		numbers += number + " ";
    	}
        return "LotteryRecord [recordDate=" + recordDate + ", lotteryTerm=" + lotteryTerm
                + ", lotteryNumbers=" + numbers + "]";
    }
}