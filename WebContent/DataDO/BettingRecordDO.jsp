<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.db.access.operate.BettingRecord"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.db.access.DBConnection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.web.util.URLWebPageInfoGET"%>
<%@page import="com.web.util.RsaSignature"%>
<%@page import="com.gxs.block.pay.InitPayParam"%>
<%@page import="com.gxs.block.pay.PayCommonParam"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.alibaba.fastjson.JSON"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
</head>
<%

	/* 获取数据库连接 */
	ResultSet rs = null;
	Connection conn = new DBConnection().getConnection();
	Statement stat;

	String bettingRecord = request.getParameter("bettingRecord").replace(";,", ";");
	String passWord = request.getParameter("passWord").trim();
	int money = Integer.parseInt(request.getParameter("money").trim());
	String rand = request.getParameter("rand");
	
	String uuid = session.getAttribute("uuid").toString();
	String nickName = session.getAttribute("nickName").toString();
	
	/**
	 * 以下实现转账交易
	 */
	String privateKey = "MIIFDjBABgkqhkiG9w0BBQ0wMzAbBgkqhkiG9w0BBQwwDgQIjirsBI2/spECAggAMBQGCCqGSIb3DQMHBAjM6OMSS1IRJgSCBMidSmWuic4H84BFNWzBxvLWiD9muu4PQSGcyfpQvZZyFU2QmbzwFctR2Pa3TQM1i8CMOS37qgv9StBH069xKMmVMhFT9z4G1zbT6ckeJa+TaON0CPD3jkma/OBiqd0lNBSUnFa9EbeJ+YP+Q+Au01k7+/iWqam1HaD4UNeeK0qXzLAg7Ua7zD+8nmjwyUe+i0jo9/1AH4sgytyRorBbxaNDV2L6Uui3SQW3VvqCQQBwZuN8vkpBmzLT8HZDyV7b8Q9e6LIdEyWILimPKXMr2X/HnB4BhYZsuuq9NV8bqDCQGpUdgOxaYihXW9Z0RBxS/VhtEBu+os81AsYFhk3MgZnaU/g2w3vQJmYpR1E7poIpUxXE8cO/+WP6tSewFWfFk82tSwJypOvP8vWbW1tikO5FkP330G0Dj2mVfX1Y1BXunKH418heA6WZoX3TWLBzKLCiJtY57PYOpseh+TpPqrxek8jSt7gKg4EBO/xQqR5FBq0kNeJ+jDv+5qSxQGjsDTfTbA8XHzFB7lcSzRHT39SR4Wavwq/UqYcVUXt9ey8lkiX9vWFMKN4+DfdSO9ib3wy8VsieTq1uw+FpN9HKAZjftF0gVMPqLsjoVbpOcf70Fq7mz+4sPrZDLbHbhEYhnCqVItXdYObSt26+Lxad9j1wXTYEztKu+mffl8GxmzxBkp/hGweCX2vDfB3cj73uR9sK369ltFEeeQpHpktPQwLtO1U5rHl5j7E/QaYRM0Q2xPYT4Tpx5nM23zA2VIcnl/8yN9P/ZANiAUfJEO+g/xVPHQ03wU7CjoUy8OMhZEgsnc6f7knz5pVFg2uBAZLs5niM7gX9xhfb91Ke25qXDdX0FgLZ02CU7kXQnLHcR54tHG7plyYnZ9B4A/o3uErVQcfC+pBEFiTGsGDuanE2oOddmu7aaWK948fpJ8pePcVA1mLuY/5rQHcPAB7UZ0j0CiYgGL3TcyJ0a4uqwHJC01iW5yz0uDpUQsz9/KlVGB1bq2x+uIqX5+i4xqw1ffyV9CN0z777FF0hGHRD+pjhWYJoWKsjy+vX9TC8IuoZFSWMIbdv3TMa8SN/3ynxTarhaW0ORE60Y+wWaKA59ddN+Yq4EG2OZTHa8eCGwbitQGZyiLffrV5ZZ6KJPqBq6i636WDZgelIz52E1aJz8rg3u68M2oBOp+7Fx9tGqKfO1TLzCu+VLr3Ya2IB01qAost5DXdSIr9uU+QGvnqXHY7CHMsysaD1SLY24w8xcIBMsOZ0LujDLgcfsDyUt4IVHHzgqKK6/4vtX63DHV5+FGlRPg1rUIRp4H0VJYj8Xyp6Rt1/xhswj8I/hwDfQ0gisqDcl1z0Tsf634+A9EFa4nlffZskWwYej4J4MS4Bb04PaZjEscirKqY15LEOVi0zPPQWSuDKt0Wmn07mL1oA3uZ2wNXqFQYOlswNrE/nAmluqd5d84/FALJkkkiXj41GnHm9hzloZTvhH7yz6dSP+cK38qYJVKIJtaV0oko7zlP6qHiYw5yEe/H7JWZe/gNmK1xCcnZUZSG327ohoKiyWA6SYy9JSV0WVFZOrX1OJDhV+neRCp0jAuprehj7L7QlwBZUNjI2AICKHrvB/WIUTbidtP+j2wycZNytNyk=";
	
	String biz_content = "\"{" + 
							"\\\"to_user\\\":\\\"osXdYcoFl4gOGTXHR2v08862028\\\"," + 
							"\\\"amount\\\":" + money + "," + 
							"\\\"currency\\\":\\\"GXC\\\"," + 
							"\\\"remark\\\":\\\"\\\"," + 
							"\\\"pswd\\\":\\\"" + passWord + "\\\"," + 
							"\\\"outTransferNo\\\":\\\"" + uuid + "\\\"," + 
							"\\\"subject\\\":\\\"CPMN\\\"" + 
				         "}\"";
	long timestamp = System.currentTimeMillis();
	System.out.println("biz_content:" + biz_content);
	
	/**
	 * 官方签名方法
	**/
	/*
	PayCommonParam param = new PayCommonParam();
	param.setApp_id("rp66crdix9vncse7");
	param.setMethod("blockpay.trade.transfer");
	param.setNotify_url("");
	param.setTimestamp(System.currentTimeMillis());
	param.setVersion("1.0.0");
	InitPayParam initPayParam = new InitPayParam();
	initPayParam.setCurrency("GXS");
	initPayParam.setOut_trade_no(uuid);
	initPayParam.setSubject("CPMN");
	initPayParam.setTotal_amount(new BigDecimal(money));
	param.setBiz_content(JSON.toJSONString(initPayParam));
	param.setSign(RsaSignature.rsaSign(param.getBiz_content()+param.getTimestamp(), privateKey));
	System.out.println(JSON.toJSONString(param));
	String sign = param.getSign();
	*/
	/**官方签名方法**/
	
	//POST方式发起HTTP请求，获取服务端响应--公共类
	String sign = RsaSignature.rsaSign(biz_content+timestamp, privateKey);
	    
    String params = "{" + 
						"\"app_id\":\"rp66crdix9vncse7\"," + 
						"\"method\":\"blockpay.trade.transfer\"," + 
						"\"timestamp\":" + timestamp + "," + 
						"\"version\":\"1.0\"," + 
						"\"notify_url\":\"\"," + 
						"\"biz_content\":" + biz_content + "," + 
						"\"sign\":\"" + sign + "\"" + 
					"}";
					
	System.out.println("params:" + params);
					
	String transferUrl = "https://sandbox.blockcity.gxb.io/api/blockpay/api/gateway";
	
	String payData = URLWebPageInfoGET.urlHttpRequestDO(transferUrl,params);
	
	System.out.println("转账响应信息：" + payData);
	
	/*完成转账交易*/
	
	int n = 0;
	String[][] dataRecord = new String[10][7];
	String rRecord[] = bettingRecord.split(";");
	for(n = 0 ; n < rRecord.length ; n++) {
		String temp[] = rRecord[n].split(",");
		for(int m = 0 ; m < temp.length ; m++) {
			dataRecord[n][m] = temp[m];
		}
	}
	
	String[][] tempRecord = new String[n][7];
	tempRecord = Arrays.copyOfRange(dataRecord, 0, n);
	
	String expect = "";
	//获取最大一期已开奖记录
	try {
       	stat = conn.createStatement();
       	String sSql = " select max(expect) as expect " + 
      				  " from Lottery_Result " + 
        			  " where 1 = 1 " + 
        			  " order by expect desc ";
       	rs = stat.executeQuery(sSql);
     	if(rs.next()) {
     		expect = String.valueOf(Integer.parseInt(rs.getString("expect")) + 1);
     	}
     	rs.getStatement().close();
     	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//控制台输出押注彩票日志记录
	System.out.println("彩票押注数据信息日志：");
	for(int m = 0 ; m < tempRecord.length ; m ++) {
		for(int l = 0 ; l < tempRecord[m].length ; l++) {
			System.out.print(tempRecord[m][l] + " ");
		}
		System.out.print("\n");
	}
	
	//数据插入数据库记录
	BettingRecord record = new BettingRecord();
	record.insertRecord(expect,uuid,nickName,tempRecord,rand);
%>
<body>
	
</body>
<script type="text/javascript">
	self.close();
</script>
</html>