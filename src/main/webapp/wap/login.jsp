<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Cache-control" content="no-cache">
<meta name="Keywords" content="如意彩，双色球,彩票,福彩中心,体彩中心,中奖号码,开奖公告,手机彩票，手机购彩，手机投注，3d，福彩3d，彩票开奖，彩票合买,福利彩票">
<meta name="Description" content="如意彩-让手机彩票更精彩。拥有专业手机投注服务平台，全手机彩票牌照运营，手机客户端购彩，彩票送礼服务，中奖通知等多种彩票特色产品服务。如意彩还提供，彩票合买,手机彩票资讯，号码走势，开奖查询等增值服务，充分满足广大彩民的需求。">
<title>如意彩-登录页面</title>
<link href="/wap/css/index.css" rel="stylesheet" type="text/css">

</head>
<body>
<div class="top">
	<img src="/wap/images/LOGOEN.png">
</div>
<div class="logo_box">
	<a href="/wap/index"><img src="/wap/images/ruiyicai.png" alt="" class="logo"></a>
</div>
<%
    String username = (String)request.getAttribute("userName") == null?"":(String)request.getAttribute("userName");
    String error =(String) request.getAttribute("error");   
 %>
登录<br>
<form method="post" action="<%=request.getContextPath() %>/user/center!loginForm">
<input id="reqUrl" type="hidden" value="<%=request.getParameter("reqUrl")==null?"":request.getParameter("reqUrl") %>" name="reqUrl" />
	<c:if test='${error!=null && error!=""}'>
	<span style="color: blue;"> <%= error%></span> <br/>
	</c:if>
	<input name="sign" value="wap" type="hidden"/>
	用户名:(4-16 个字符，可以使用字母、汉字、数字、下划线)<br>
	<input type="text" value="<%=username%>" size="16" maxlength="16" name="userName"><br>
	密码:(6-16 个字符，建议使用字母、数字组合、混合大小写)<br>
	<input type="password" value="" size="16" maxlength="16" name="password"> <br>
	<input type="submit" value="登录">
</form>
<br>
<a href="http://192.168.0.183:8080/wap/forgetworld">忘记密码</a>
<a href="http://192.168.0.183:8080/wap/register">免费注册</a><br>
<a href="http://192.168.0.183:8080/wap/index">返回上一页</a>


	
<div class="contant">
	<a href="/w/wap/11select5/11select5Index.jsp">11选5</a>·<a href="/w/wap/ElevenDuoJin/index.jsp">十一运夺金</a>·<a href="/w/wap/DoubleBall/DoubleBallIndex.jsp">双色球</a>·<a href="/w/wap/3D/3Dindex.jsp">3D</a>·<a href="/w/wap/daletou/DaletouIndex.jsp">大乐透</a>·<a href="/w/wap/qilecai/QilecaiIndex.jsp">七乐彩</a>·<a href="/w/wap/array3/ArrayIndex.jsp">排列三</a>·<a href="/w/wap/array5/array5Index.jsp">排列五</a>·<a href="/w/wap/cqshishicai/sscIndex.jsp">时时彩</a>·<a href="/w/wap/sevenStar/7StarIndex.jsp">七星彩</a>·<a href="/w/wap/zucai/ZucaiIndex.jsp">足彩</a>·<a href="/w/wap/buyLottery.jsp">购彩大厅</a>·<a href="/w/wap/charge/chargeIndex.jsp">账户充值</a>·<a href="/w/wap/userinfo/userCenter.jsp">用户中心</a>·<a href="/w/wap/help.jsp">帮助中心</a>
</div>




</body></html>