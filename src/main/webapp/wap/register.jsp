<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Cache-control" content="no-cache">
<meta name="Keywords" content="如意彩，双色球,彩票,福彩中心,体彩中心,中奖号码,开奖公告,手机彩票，手机购彩，手机投注，3d，福彩3d，彩票开奖，彩票合买,福利彩票">
<meta name="Description" content="如意彩-让手机彩票更精彩。拥有专业手机投注服务平台，全手机彩票牌照运营，手机客户端购彩，彩票送礼服务，中奖通知等多种彩票特色产品服务。如意彩还提供，彩票合买,手机彩票资讯，号码走势，开奖查询等增值服务，充分满足广大彩民的需求。">
<title>如意彩-注册页面</title>
<link href="<%=request.getContextPath() %>/wap/css/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="top">
	<img src="<%=request.getContextPath() %>/wap/images/LOGOEN.png">
</div>
<div class="logo_box">
		<a href="http://ruyicai.com/index">
	<img class="logo" alt="" src="<%=request.getContextPath() %>/wap/images/ruiyicai.png"></a>
</div>
<%
    String username = (String)request.getAttribute("userName") == null?"":(String)request.getAttribute("userName");
    String error =(String) request.getAttribute("error");   
 %>
新用户注册<br />
请填写手机号并设置一个密码<br />
<form method="post" action="<%=request.getContextPath()%>/function/registerAction!wapRegister">
	<input id="reqUrl" type="hidden" value="<%=request.getParameter("reqUrl")==null?"":request.getParameter("reqUrl") %>" name="reqUrl" />
	<c:if test='${error!=null && error!=""}'>
	<span style="color: blue;"> <%= error%></span> <br/>
	</c:if>
	用户名:(4-16 个字符，可以使用字母、汉字、数字、下划线)<br />
	<input name="username" type="text" maxlength="16" size="16"/><br />
	设置密码:(6-16 个字符，建议使用字母、数字组合、混合大小写)<br />
	<input name="password" type="password" maxlength="16" size="16"/><br />
	重复一次密码:<br />
	<input name="check" type="password" maxlength="16" size="16" /><br />
	<input name="type" type="hidden" value="wap">
	<input 	type="submit" value="确定" />
</form>
<br />
<div class="contant">
	<a href="http://users.ruyicai.com/wap/login.jsp?reqUrl=http://ruyicai.com/index">我已注册</a><br />
	<a	href="<%=request.getContextPath()%>/itemShow">用户协议</a><br />
	<a href="http://ruyicai.com/index">返回上一页</a>
	</div>
</body></html>