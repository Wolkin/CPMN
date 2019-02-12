<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.web.util.URLWebPageInfoGET"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<html>
	<head>
		<meta charset="utf-8"> 
		<title>彩票模拟竞猜游戏</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">  
		<script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
		<script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</head>
	<%
		//定义授权变量
		String isSuccess = "false";
		String access_token = "";
		String refresh_token = "";
		String expires_date = "";
		
		//定义用户信息
		JSONObject jsonUserD = null;
		String uuid = "";
		String nickName = "";
		int memberNumber = 0;
		int founder = 0;
		long registerDate = 0;
		boolean isKyc = false;
		boolean isTwoElement = false;
		String heads = "";
		
		String code = request.getParameter("code");
		System.out.println("用户授权代码code【" + code + "】！");
		String accessTokenUrl = "https://sandbox.blockcity.gxb.io/auth/oauth/access_token?client_id=rp66crdix9vncse7&client_secret=08yofenh1fobt7f004twbeigei3ir5ej&code=" + code;
		
		if(code == null || "".equals(code)) {
			System.out.println("非授权登录或已登录小应用，无需重复授权！");
		}else {
			String accessToken = URLWebPageInfoGET.getURLPageInfo(accessTokenUrl);
			System.out.println("accessToken:" + accessToken);
			JSONObject jsonObj = new JSONObject(accessToken);
			isSuccess = jsonObj.get("success").toString();
			if("true".equals(isSuccess)) {
				JSONObject jsonData = new JSONObject(jsonObj.get("data").toString());
				access_token = jsonData.get("access_token").toString();
				refresh_token = jsonData.get("refresh_token").toString();
				expires_date = jsonData.get("expires_date").toString();
				String timestamp = String.valueOf(System.currentTimeMillis());
				//定义参数
				Map params = new HashMap();
				params.put("client_id","rp66crdix9vncse7");
				params.put("method","user.baseinfo");
				params.put("access_token",access_token);
				params.put("timestamp",timestamp);
				params.put("client_secret","08yofenh1fobt7f004twbeigei3ir5ej");
				
				String sign = URLWebPageInfoGET.signRequest(params);
				
				String userDataUrl = "https://sandbox.blockcity.gxb.io/openapi/user/baseinfo?client_id=rp66crdix9vncse7&method=user.baseinfo&access_token=" + access_token + "&timestamp=" + timestamp + "&sign=" + sign;
				String userData = URLWebPageInfoGET.getURLPageInfo(userDataUrl);
				
				//用户数据解析
				JSONObject jsonUser = new JSONObject(userData);
				String ucode = jsonUser.get("code").toString();
				if("0".equals(ucode)) {
					System.out.println("-----------------初始化用户信息-----------------");
					jsonUserD = new JSONObject(jsonUser.get("data").toString());
					uuid = jsonUserD.get("uuid").toString();
					nickName = jsonUserD.get("nickName").toString();
					memberNumber = jsonUserD.getInt("memberNumber");
					founder = jsonUserD.getInt("founder");
					registerDate = jsonUserD.getLong("registerDate");
					isKyc = jsonUserD.getBoolean("isKyc");
					isTwoElement = jsonUserD.getBoolean("isTwoElement");
					
					session.setAttribute("jsonUserD", jsonUserD);
					session.setAttribute("uuid", uuid);
					session.setAttribute("nickName", nickName);
					session.setAttribute("memberNumber", memberNumber);
					session.setAttribute("founder", founder);
					session.setAttribute("registerDate", registerDate);
					session.setAttribute("isKyc", isKyc);
					session.setAttribute("isTwoElement", isTwoElement);
				}
			}
		}
		
		
		uuid = (session.getAttribute("uuid")==null?"":session.getAttribute("uuid")).toString();
		nickName = (session.getAttribute("nickName")==null?"":session.getAttribute("nickName")).toString();
		memberNumber = Integer.parseInt(session.getAttribute("memberNumber")==null?"0":session.getAttribute("memberNumber").toString());
		founder = Integer.parseInt(session.getAttribute("founder")==null?"0":session.getAttribute("founder").toString());
		registerDate = Long.parseLong(session.getAttribute("registerDate")==null?"0":session.getAttribute("registerDate").toString());
		isKyc = Boolean.parseBoolean(session.getAttribute("isKyc")==null?"false":session.getAttribute("isKyc").toString());
		isTwoElement = Boolean.parseBoolean(session.getAttribute("isTwoElement")==null?"false":session.getAttribute("isTwoElement").toString());
		
		System.out.println("-----------" + uuid);
		
	%>
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
								<img alt="First slide" src="img/title_1.jpg" />
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
								<img alt="Second slide" src="img/title_2.jpg" />
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
								<img alt="Third slide" src="img/title_3.jpg" />
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
						<li class="active">
							<a href="cpmnmain.jsp"><b>彩票竞猜</b></a>
						</li>
						<li>
							<a href="History/historymain.jsp"><b>投注记录</b></a>
						</li>
						<li>
							<a href="History/lotterymain.jsp"><b>开奖信息</b></a>
						</li>
						<li>
							<a href="Mine/minemain.jsp"><b>我的收益</b></a>
						</li>
					</ul>
					<div class="row-fluid">
						<div class="span12">
							<form id="bettingForm">
								<fieldset>
									 <legend>选择彩票号</legend>
									 <div class="row">
									 	<table>
									 		<tr id="tr0" >
									 			<td>
							                        <div class="col-xs-2">
							                            <input type="text" id="n00" style="border-color:red;padding:6px 0px;text-align:center;border-radius:20px" class="form-control" placeholder="红球">
							                        </div>
							                        <div class="col-xs-2">
							                            <input type="text" id="n10" style="border-color:red;padding:6px 0px;text-align:center;border-radius:20px" class="form-control" placeholder="红球">
							                        </div>
							                        <div class="col-xs-2">
							                            <input type="text" id="n20" style="border-color:red;padding:6px 0px;text-align:center;border-radius:20px" class="form-control" placeholder="红球">
							                        </div>
							                        <div class="col-xs-2">
							                            <input type="text" id="n30" style="border-color:red;padding:6px 0px;text-align:center;border-radius:20px" class="form-control" placeholder="红球">
							                        </div>
							                        <div class="col-xs-2">
							                            <input type="text" id="n40" style="border-color:red;padding:6px 0px;text-align:center;border-radius:20px" class="form-control" placeholder="红球">
							                        </div>
							                        <div class="col-xs-2">
							                            <input type="text" id="n50" style="border-color:red;padding:6px 0px;text-align:center;border-radius:20px" class="form-control" placeholder="红球">
							                        </div>
							                        <div class="col-xs-2">
							                            <input type="text" id="n60" style="border-color:blue;padding:6px 0px;text-align:center;border-radius:20px" class="form-control" placeholder="蓝球">
							                        </div>
							                        <div class="col-xs-2">
							                            <button type="button" id="add0" class="btn btn-sm"  onclick="addOne()">添加</button>
							                        </div>
							                        <div class="col-xs-2">
							                        	<button type="button" id="del0" class="btn btn-sm" style="visibility:hidden" onclick="delOne()">删减</button>
							                        </div>
							                        <div class="clearfix"></div>
							                        <hr width=98%>
						                        </td>
					                        </tr>
				                        </table>
				                     </div>
									 <button type="button" class="btn" onclick="rdmNum()">机选</button>
									 <button type="button" class="btn" onclick="doSubMit()">提交</button>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script language="javascript">
		var iCount = 0;   //定义全局变量
		
		//添加一注彩票，当前彩票下增加一行
		function addOne() {
			if(iCount >= 9){
				alert("单次最多投注10注！");
				return;
			}
			var dtr = document.getElementById("tr" + iCount);
			var dd = dtr.parentNode;
			var newNodeTR = document.createElement("tr");
			iCount = iCount + 1;
			newNodeTR.setAttribute("id", "tr" + iCount);
			
			var newNodeTD = document.createElement("td");
			newNodeTD.innerHTML = "<div class='col-xs-2'>" + 
								  "		<input type='text' id='n0" + iCount + "' style='border-color:red;padding:6px 0px;text-align:center;border-radius:20px' class='form-control' placeholder='红球'>" + 
								  "</div>" + 
								  "<div class='col-xs-2'>" + 
								  "		<input type='text' id='n1" + iCount + "' style='border-color:red;padding:6px 0px;text-align:center;border-radius:20px' class='form-control' placeholder='红球'>" + 
								  "</div>" + 
								  "<div class='col-xs-2'>" + 
								  "		<input type='text' id='n2" + iCount + "' style='border-color:red;padding:6px 0px;text-align:center;border-radius:20px' class='form-control' placeholder='红球'>" + 
								  "</div>" + 
								  "<div class='col-xs-2'>" + 
								  "		<input type='text' id='n3" + iCount + "' style='border-color:red;padding:6px 0px;text-align:center;border-radius:20px' class='form-control' placeholder='红球'>" + 
								  "</div>" + 
								  "<div class='col-xs-2'>" + 
								  "		<input type='text' id='n4" + iCount + "' style='border-color:red;padding:6px 0px;text-align:center;border-radius:20px' class='form-control' placeholder='红球'>" + 
								  "</div>" + 
								  "<div class='col-xs-2'>" + 
								  "		<input type='text' id='n5" + iCount + "' style='border-color:red;padding:6px 0px;text-align:center;border-radius:20px' class='form-control' placeholder='红球'>" + 
								  "</div>" + 
								  "<div class='col-xs-2'>" + 
								  "		<input type='text' id='n6" + iCount + "' style='border-color:blue;padding:6px 0px;text-align:center;border-radius:20px' class='form-control' placeholder='蓝球'>" + 
								  "</div>" + 
								  "<div class='col-xs-2'>" + 
								  "		<button type='button' id='add" + iCount + "' class='btn btn-sm'  onclick='addOne()'>添加</button>" + 
								  "</div>" + 
								  "<div class='col-xs-2'>" + 
								  "		<button type='button' id='del" + iCount + "' class='btn btn-sm'  onclick='delOne()'>删减</button>" + 
								  "</div>" + 
								  "<div class='clearfix'></div>" + 
								  "<hr width=98%>";
			newNodeTR.appendChild(newNodeTD);
			dd.appendChild(newNodeTR);
			
			document.getElementById("add" + (iCount - 1)).style.visibility = "hidden";
			document.getElementById("del" + (iCount - 1)).style.visibility = "hidden";
		}
		
		//删除当前彩票，当前行删除
		function delOne() {
			var dtr = document.getElementById("tr" + iCount);
			dtr.parentNode.removeChild(dtr);
			iCount = iCount - 1;
			document.getElementById("add" + iCount).style.visibility = "visible";
			if(iCount > 0) {
				document.getElementById("del" + iCount).style.visibility = "visible";
			}
		}
		
		//随机产生投注号
		function rdmNum() {
			
			for(var r = 0 ; r <= iCount ; r++) {
				var numArray = "";
				var flag,num;
				for(var c = 0 ; c <= 6 ; c ++) {
					do{
						//产生红色球号码
						num = Math.round(Math.random()*33 + 0.5);
						//校验不产生重复红色球号码
						flag = numArray.indexOf("'" + num + "'");
						
						
						if(flag == -1) {
							document.getElementById("n" + c + r).value = num;
							numArray = numArray + ",'" + num + "'";
						}
					}while(flag != -1)
					
					//产生蓝色球号码
					if(c == 6) {
						document.getElementById("n" + c + r).value = Math.round(Math.random()*16 + 0.5);
					}
				}
			}
		}
		
		//提交投注彩票
		function doSubMit() {
			var red = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33";
			var blue = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16";
			var bettingRecord = new Array();
			for(var r = 0 ; r <= iCount ; r++) {
				var temp = new Array();
				for(var c = 0 ; c <= 6 ; c ++) {
					temp[c] = document.getElementById("n" + c + r).value;
					var isRight;
					if(c == 6) {
						isRight = blue.indexOf(temp[c]);
						if(isRight == -1 || temp[c] == 0) {
							alert("第 " + (r + 1) + " 注蓝色球号码异常!");
							return false;
						}
					}else {
						isRight = red.indexOf(temp[c]);
						if(isRight == -1 || temp[c] == 0) {
							alert("第 " + (r + 1) + " 注，第 " + (c + 1) + " 个红色球异常!");
							return false;
						}
					}
				}
				bettingRecord[r] = temp + ";";
			}
			if(confirm('你确定提交押注吗？\n需支付【' + (iCount + 1) + '个GXC】！')) {
				var passWord = prompt("请输入密码进行支付...");
				if(typeof(passWord) == "undefined" || passWord == null || passWord == "") {
					alert("支付密码不合法！");
					return false;
				}
				
				var a = document.createElement("a");
				a.setAttribute("href", "DataDO/BettingRecordDO.jsp?bettingRecord=" + bettingRecord + "&passWord=" + passWord + "&money=" + (iCount + 1) + "&rand=" + Math.abs(Math.sin(new Date().getTime())).toString().substr(2));
				a.setAttribute("id", "betting");
				a.setAttribute("target", "_blank");
				document.body.appendChild(a);
				a.click(); //执行当前对象
			}
			window.location.reload();
			alert("完成押注，等待开奖...");
		}
	</script>
</html>