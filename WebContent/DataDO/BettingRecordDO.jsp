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
	String rand = request.getParameter("rand");
	
	String nickName = session.getAttribute("nickName").toString();
	
	/**
	 * 以下实现转账交易
	 */
	String transferUrl = "https://sandbox.blockcity.gxb.io/api/blockpay/api/gateway?app_id=rp66crdix9vncse7&method=blockpay.trade.transfer&timestamp=&version=1.0&notify_url=&biz_content=&sign=";
	
	
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
	record.insertRecord(expect,nickName,tempRecord,rand);
%>
<body>
	
</body>
<script type="text/javascript">
	self.close();
</script>
</html>