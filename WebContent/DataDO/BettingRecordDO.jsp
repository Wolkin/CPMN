<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.db.access.operate.BettingRecord"%>
<%@page import="java.util.Arrays"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%
	String bettingRecord = request.getParameter("bettingRecord").replace(";,", ";");
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
	
	//数据插入数据库记录
	BettingRecord record = new BettingRecord();
	record.insertRecord("20190116","勾丝",tempRecord);
	
%>
<body>
	
</body>
<script type="text/javascript">
	self.close();
</script>
</html>