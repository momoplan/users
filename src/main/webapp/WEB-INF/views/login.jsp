<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.net.URLDecoder" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>如意彩会员登录，您可以使用QQ、支付宝快捷登陆</title>
<meta http-equiv="keywords" content="会员登录，QQ、支付宝快捷登陆"/>
<meta http-equiv="description" content="如意彩会员快捷登录使用QQ、支付宝登陆让您购彩更快捷！"/>
<link type="text/css" href="http://www.ruyicai.com/css/util.css" rel="stylesheet"/>
<link type="text/css" href="http://www.ruyicai.com/css/ruserAll.css" rel="stylesheet"/>
<script type="text/javascript" src="<%=basePath%>/js/jqueryJS/jquery-1.5.min.js" ></script>
<script type="text/javascript">
<!--
function change(mag){
	var d=new Date();
	var imageUrl = "image.jsp?id="+d.getDate()+Math.random();
	$("#"+mag).attr('src',imageUrl);
}
//-->

</script>
</head>

<body>
<div id="indexLogin">
  <div id="login_body">
	<div class="user_login">
		<a class="user_logo" href="http://www.ruyicai.com/index.html" title="首页"><img src="http://www.ruyicai.com/images/HomeLogo.gif" width="138" height="64" /></a>
		<div class="ZhuCe_hotLine">客服热线：400-665-1000<a href="http://www.ruyicai.com/rules/customMessage.html" title="留言反馈">留言反馈</a>|<a href="http://www.ruyicai.com/bangzhuzhongxin.html" title="帮助中心">帮助中心</a>|<a href="http://www.ruyicai.com/index.html" title="返回首页">返回首页</a></div>
	</div>
	<div class="user_loginTitle">用户登录</div>
	<input id="msg" value="${msg}" type="hidden"/>
	<div class="user_loginContent">
		<form action="<%=request.getContextPath() %>/user/center!loginForm" method="post" id="userLoginForm" name="userLoginForm" onSubmit="" >
		<input id="reqUrl" type="hidden" value="<%=request.getParameter("reqUrl")==null?"":request.getParameter("reqUrl") %>" name="reqUrl" />
		<div class="user_loginLeft">
			<table  cellspacing="0" cellpadding="0" >
				<tr>
					<th width="60">用户名：</th>
					<th colspan="2" width="170"><input name="userName" id="username" type="text" tabindex="1" class="loginInput" /></th>
					<td width="230" id="usernameTip"></td>
				</tr>
				<tr>
					<th>密　码：</th>
					<th colspan="2" ><input name="password"  id="pwd" type="password" tabindex="2" class="loginInput" /></th>
					<td id="pwdTip"></td>
				</tr>
				<tr>
					<td colspan="4"><input id="login_submit" type="submit" value="登录" class="login_inpuBtn"  />
					<a href="http://www.ruyicai.com/rules/findPwd_new.html" title="忘记密码">忘记密码?</a></td>
				</tr>
			</table>
		</div>
		</form>
		<div class="user_loginRight">
			<h2>还不是如意彩用户？</h2>
			<p>现在免费注册成为如意彩用户，便能立刻享受便捷的购彩服务。</p>
			<div class="login_footerLine"><a href="/register/register_new.jsp" class="login_registration">注册新用户</a></div>
			<div class="space15">&#160;</div>
			<p>使用合作网站账号登录如意彩：</p>
			<p><a href="http://www.ruyicai.com/chlw/function/unitedLogin!qqUnitedHandlyLogin" title="QQ" class="login_QQ">QQ</a><a href="http://www.ruyicai.com/chlw/function/unitedLogin!alipayHandyLogin" title="支付宝" class="login_zhiFuBao">支付宝</a></p>
		</div>
	</div>
	<div id="footer">
		<div class="copyright">Copyright© 2009-2014如意彩彩票网 All rights reserved  增值电信业务经营许可证（B2-20100007）　京ICP备10007936号</div>
	    <div class="lianxi">客服热线：400-665-1000　 客服邮箱：<a href="mailto:service@ruyicai.com">service@ruyicai.com</a></div>
		<div class="lianxi">如意彩温馨提示：彩票乃公益事业，投注请量力而行，不向未满18周岁的青少年出售彩票！</div>
	</div>
</div>
</div>
<script>
if($("#msg").val()=="登陆成功！"){
	 alert($("#msg").val());
	   window.close();
}

</script>
<!-- 百度统计代码 开始 -->
<div style="display: none">
<script type="text/javascript">
	var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
	document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F8333ff4357591825f74461a26584cc2f' type='text/javascript'%3E%3C/script%3E"));
</script>
</div>
<!-- 百度统计代码 结束 -->
</body>
</html>