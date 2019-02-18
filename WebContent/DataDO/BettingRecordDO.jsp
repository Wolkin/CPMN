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
	String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDmJHujwNl8iVyG" + 
						"/yWNBP2oOD7Qx/igbQsaQ0T7soyUObeKjH8r5NtYN0lw2RFiAAwXJshaEPY6giMO" + 
						"kZylxd0b+lDPqvaQTo9WZ2FvkcL3g2Jz5UFeER1Ny58p8kCK2caAnusK9ZPmEkGW" + 
						"rU5QeELYReKY4uXWRBc6L3P88quXMh3drF+RplDd9ib1/H/QMdK/+7LkLHYwljcx" + 
						"sj1LiHZLj5pZk50HWzgZ1XO4tOEZ+SI2GiZJT2BYkH+D+rlxPtZpENMPypk8+fgv" + 
						"JvvMBRjD7739BlzJwrrK0Kj0BfhQ4gYKvuGlc71zld7+NG2KM4SvRqqlg8gZrLeC" + 
						"INPCF9iJAgMBAAECggEBAMpJxReRmlkZz+ek+1exzFgBYE1ZiBNS5ZBnEop+TYy4" + 
						"ErlIdzuYxKgMqJP1aI8QZZAv+akZAaU54CCdIuifO5Os+T0gE+uR8fHLmtxbbdjr" + 
						"di1SlJtfRbesxDE1iz/A3fcOOKEAbMiswVP070JSAsdg3iTuQ4GLhjMRpMzwbJzO" + 
						"uHr5bySNptgZaGZmXEpWUfNTtTQLVAbyGwkC6jc4GN0ML03t2BJI+xH5BYqew2RX" + 
						"8nG65mSykvWCPZA+PUor/SqOtfIDPEMNvNnb6mtJiOj4lW9r5v+2dEYtb3QIn/pV" + 
						"8cwE1XahbpofF7jVtey6vGOxaUIYpmskMk19n/8SB2UCgYEA+ug0UtkVKL3OGbOS" + 
						"KvKdj39d0kBq1mpxuKhHj1y67fMi4BfD0wD1kZt7HfcGPQFabuJErWuHWxJPg1K6" + 
						"urx41rahMw+ikLHHfN8ajCyGHOwTAwHlyd81mwOoI4jGlxcLz1YSarlJT2B8BDu0" + 
						"xuGHy/LpsxkjZGlXDStrhKv+Xr8CgYEA6tBhE+zCSv2IG5+c3udfA0FNeAKGBkeJ" + 
						"AXHlAEoeNInouoOq4rziGspT/bQeGRJ8UVindlXDHarhOWcXM1gdEv7BIHY4i9i7" + 
						"kWfo5mzWog/lKdVjBQPCqWl/IO2EE3R/qkGB7skdrKvRZ1tVE/epNvMY2UKERLk5" + 
						"dh9V6R1DYrcCgYAjiDjItsdhUqfaSezcOimIBdCCku7OWJqsPOCNc+NhCTqaI6Nu" + 
						"wUcFjNA9qRrwDr4Az/hL2tt7UTeDcHbTNRejyI9BjOhHt3V5wJqg2TlQ5Tm4Bk/F" + 
						"a9/KiRUJmzgMc44Pma/X/09bd114t//c3ll9z3O7EkdJ1AUo70o1qqnGPwKBgQDj" + 
						"DL7xzztH28MiQsIi30KGGTzZUEdcBwu5M/Ikx4ZRcxBwWSSBvfY7xWxVozZ/M9q1" + 
						"pa23xNG3/CVvpflPEmJ6nY8M3oNq5hToUrtnI9KePhRu0QDANpShz3q30jKlxT40" + 
						"2MbUp+9jqXgAItYvSoh6s7FRUvomoZuoVkBx+nPQHQKBgQCY6foNgx6XPQMMCsMe" + 
						"lKk+jS4vGnEPpMwkP5w3MlivP6MC+zdi3yxC3cNdkJpAR6nJeeTqaFV/bp7HyozB" + 
						"Q9UKS+n2HcaY1QFRZibAcvOT4jiZkS9g4peQBt0AawM+OrsToET1Q1r6MRebnXvI" + 
						"2FtbL+rgKMqlflNfR1TgS+1NUA==";
	
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