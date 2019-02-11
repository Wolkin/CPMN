package com.web.lottery.batch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class URLLotteryBatch {
	private static StringBuffer mStringBuffer = new StringBuffer();
	
    public static void main(String[] args) {
    	String expect = "";
    	String redball1 = "";
    	String redball2 = "";
    	String redball3 = "";
    	String redball4 = "";
    	String redball5 = "";
    	String redball6 = "";
    	String blueball = "";
    	String opentime = "";
    	String opentimestamp = "";
    	
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	Connection conn = new DBConnection().getConnection();
    	Statement stat = null;
    	
    	LotteryRecord temp = null;
    	
    	URLLotteryBatch test = new URLLotteryBatch();
    	ArrayList<LotteryRecord> list = test.getLotteryList("http://kaijiang.zhcw.com/zhcw/html/ssq/list_1.html");
    	
    	System.out.println("-------福利彩票网站获取的开奖结果记录-------");
    	for (LotteryRecord lotteryRecord: list) {
    		if(temp == null) {
    			temp = lotteryRecord;
    		}else {
    			if(lotteryRecord.getLotteryTerm().compareTo(temp.getLotteryTerm()) > 0) {
        			temp = lotteryRecord;
        		}
    		}
    		
    		System.out.println(lotteryRecord.toString());
    	}
    	System.out.println("-------<<<<<<<<<<<<<>>>>>>>>>>>>>>>-------");
    	
    	System.out.println("最新一期开奖结果：" + temp.toString());
    	expect = temp.getLotteryTerm();
    	redball1 = temp.getLotteryNumbers()[0];
    	redball2 = temp.getLotteryNumbers()[1];
    	redball3 = temp.getLotteryNumbers()[2];
    	redball4 = temp.getLotteryNumbers()[3];
    	redball5 = temp.getLotteryNumbers()[4];
    	redball6 = temp.getLotteryNumbers()[5];
    	blueball = temp.getLotteryNumbers()[6];
    	opentime = temp.getRecordDate();
    	opentimestamp = df.format(new Date());
    	
    	String sSql = " insert into Lottery_Result(expect,redball1,redball2,redball3,redball4,redball5,redball6,blueball,opentime,opentimestamp) " + 
				      " values ( " + 
				      " '" + expect + "'," + 
				      " '" + redball1 + "'," + 
				      " '" + redball2 + "'," + 
				      " '" + redball3 + "'," + 
				      " '" + redball4 + "'," + 
				      " '" + redball5 + "'," + 
				      " '" + redball6 + "'," + 
				      " '" + blueball + "'," + 
				      " '" + opentime + "'," +
				      " '" + opentimestamp + "'" + 
				      ")";
    	
    	try {
			stat = conn.createStatement();
			Statement stat1 = conn.createStatement();
			System.out.println(sSql);
			ResultSet rs = stat1.executeQuery("select count(*) as num from Lottery_Result where expect = '" + expect + "' ");
			if(rs.next()) {
				if(rs.getInt("num") > 0) {
					System.out.println("已经存在该期记录！");
				}else {
					stat.execute(sSql);
				}
			}
			rs.getStatement().close();
			
			stat.close();
			conn.close();
		} catch (SQLException e) {
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
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
        //读取目的网页URL地址，获取网页源
        URL url = new URL(urlInfo);
        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
        httpUrl.setRequestMethod("POST");
        httpUrl.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows 7)");
        httpUrl.setRequestProperty("Accept", "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
        httpUrl.setRequestProperty("Accept-Language", "zh-cn");
        httpUrl.setRequestProperty("UA-CPU", "x86");	
        // 为什么没有deflate
        httpUrl.setRequestProperty("Accept-Encoding", "gzip");
        httpUrl.setRequestProperty("Content-type", "text/html");
        // keep-Alive，有么用呢，你不是在访问网站，你是在采集。嘿嘿减轻别人的压力，也是减轻自己
        httpUrl.setRequestProperty("Connection", "close");
        // 不要用cache，用了也没有么用，因为我们不会经常对个链接频繁访问
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
     * 获取期开奖结果HTML
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
     * 从一期开奖结果HTML中解析出奖记
     * @param oneTermContent
     * @return
     */
    private LotteryRecord getOneTermNumbers(String oneTermContent) {
    	LotteryRecord lotteryRecord = new LotteryRecord();
    	/**
    	 * 奖日
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
    private String recordDate;	//奖日
    private String lotteryTerm;	//期次
    private String[] lotteryNumbers;	//奖号
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

class DBConnection {

	private final String driver = "com.hxtt.sql.access.AccessDriver";
	String path = "";
	private String url = "";
	private final String user = "";
    private final String password = "";
    private Connection conn = null;

    public DBConnection() {
    	init();
		try {
			Class.forName(driver);	//JDBC-ODBC桥接器
			this.conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return conn;
	}

	private Statement createStatement() throws SQLException {
		return conn.createStatement();
	}

	private void init() {
		String path = this.getClass().getResource("/").getPath();
		if(!path.contains("classes")) {
			path = "/C:/WebApp/apache-tomcat-7.0.82/webapps/CPMN/WEB-INF/classes/";
		}
		System.out.println("path:" + path);
		this.path = path.substring(1, path.indexOf("classes"));
		//String classPath = DBConnection.class.getClassLoader().getResource("").getPath();
		this.url = "jdbc:Access:///" + this.path + "db/gxbdb.accdb";
		System.out.println("数据库url:" + this.url);
	}
	
	public void close() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}