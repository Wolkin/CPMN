<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.db.access.DBConnection"%>
<%@page import="java.sql.SQLException"%>
<%
	/* 投注纪录展示 */
	ResultSet rs = null;
	Connection conn = new DBConnection().getConnection();
	Statement stat;
%>
<html>
	<head>
		<meta charset="utf-8"> 
		<title>彩票模拟竞猜游戏</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">  
		<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
		<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</head>
	<body>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">
					<div class="carousel slide" id="carousel-970874">
						<ol class="carousel-indicators">
							<li data-slide-to="0" data-target="#carousel-970874">
							</li>
							<li data-slide-to="1" data-target="#carousel-970874">
							</li>
							<li data-slide-to="2" data-target="#carousel-970874" class="active">
							</li>
						</ol>
						<div class="carousel-inner">
							<div class="item">
								<img alt="First slide" src="../img/title_1.jpg" />
								<div class="carousel-caption">
									<h4>
										勾丝
									</h4>
									<p>
										勾丝是一个吹牛逼的老中医。
									</p>
								</div>
							</div>
							<div class="item">
								<img alt="Second slide" src="../img/title_2.jpg" />
								<div class="carousel-caption">
									<h4>
										勾丝
									</h4>
									<p>
										勾丝是一个吹牛逼的老中医。
									</p>
								</div>
							</div>
							<div class="item active">
								<img alt="Third slide" src="../img/title_3.jpg" />
								<div class="carousel-caption">
									<h4>
										勾丝
									</h4>
									<p>
										勾丝是一个吹牛逼的老中医。
									</p>
								</div>
							</div>
						</div>
						<a data-slide="prev" href="#carousel-970874" class="left carousel-control">
							<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
							<span class="sr-only">Previous</span>
						</a>
						<a data-slide="next" href="#carousel-970874" class="right carousel-control">
							<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
							<span class="sr-only">Next</span>
						</a>
					</div>
					<ul class="nav nav-tabs">
						<li>
							<a href="../cpmnmain.html"><b>彩票竞猜</b></a>
						</li>
						<li>
							<a href="../History/historymain.jsp"><b>投注记录</b></a>
						</li>
						<li class="active">
							<a href="../Mine/minemain.jsp"><b>我的收益</b></a>
						</li>
					</ul>
					<div class="row-fluid">
						<!-- Table Striped -->
		                <div class="block-area" id="tableStriped">
		                    <h3 class="block-title">My income</h3>
		                    <div class="table-responsive overflow">
		                        <table class="tile table table-bordered table-striped">
		                            <thead>
		                                <tr>
		                                    <th>期次</th>
		                                    <th>投入</th>
		                                    <th>收益</th>
		                                    <th>盈亏</th>
		                                </tr>
		                            </thead>
		                            <tbody>
		    <%
            try {
        		stat = conn.createStatement();
        		String sSql = " select expect,user,sum(investment) as investment,sum(profit) as profit " + 
        					  " from Betting_Record " + 
		        			  " where 1 = 1 " + 
        					  " group by expect,user " + 
		        			  " order by expect desc ";
        		rs = stat.executeQuery(sSql);
        		while (rs.next()) {
            %>
		                                <tr>
		                                    <td><%=rs.getString("expect") %></td>
		                                    <td><%=rs.getString("user") %> </td>
		                                    <td><%=rs.getInt("investment") %></td>
		                                    <td><%=rs.getDouble("profit") %></td>
		                                </tr>
		    <%
		    	}
				rs.getStatement().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			%>
		                            </tbody>
		                        </table>
		                    </div>
		                </div>
		                <hr width=98%>
		                <br>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>