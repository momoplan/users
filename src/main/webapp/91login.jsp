<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder" %><%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="/common/ruyicai_include_common_top_http.jsp"></jsp:include>
<base href="<%=basePath%>">
<title>91如意彩会员登录，您可以使用QQ、支付宝快捷登陆</title>
<meta http-equiv="keywords" content="会员登录，QQ、支付宝快捷登陆"/>
<meta http-equiv="description" content="91如意彩会员快捷登录使用QQ、支付宝登陆让您购彩更快捷！"/>
<link type="text/css" href="/css/util.css" rel="stylesheet"/>
<link type="text/css" href="/css/ruserAll.css" rel="stylesheet"/>
<%-- <script type="text/javascript" src="<%=basePath%>/js/jqueryJS/jquery-1.5.min.js" ></script> --%>
<script type="text/javascript" src="/js/jqueryJS/jquery-1.5.min.js" ></script>
<script type="text/javascript" src="/js/top.js" ></script>
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
<jsp:include page="/common/ruyicai_include_common_new_91_top.jsp"></jsp:include>
<div id="indexLogin">
  <div id="login_body">
	<div class="user_loginTitle">用户登录</div>
	<input id="msg" value="${msg}" type="hidden"/>
	<div class="user_loginContent">
		<form action="<%=request.getContextPath() %>/user/center!loginForm" method="post" id="userLoginForm" name="userLoginForm" onSubmit="" >
		<input id="reqUrl" type="hidden" value="<%=request.getParameter("reqUrl")==null?"":request.getParameter("reqUrl") %>" name="reqUrl" />
		<input name="sign" type="hidden"  value="91ruyicai"/>
		<div class="user_loginLeft">
			<s:if test='#request.error!=null && #request.error!=""'><div class="user_loginPrompt" id="error"><s:property value="#request.error"/></div></s:if>
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
			<s:if test="#request.error!=null&&#request.error.length()>0">
				<tr id="rank_id">
					<th>验证码：</th>
					<th><input id="password1" name="password1" type="text" class="login_inputSmall" tabindex="3"/></th>
					<td><img src="image.jsp" id="magLogin" width="60" height="20" class="login_img" /></td>
					<td><a href="javascript:change('magLogin');" title="换一张">换一张</a></td>
				</tr>
			</s:if>
				<tr>
					<td colspan="4"><input id="login_submit" type="submit" value="登录" class="login_inpuBtn"  />
					<a href="http://91.ruyicai.com/rules/findPwd_new.html" title="忘记密码">忘记密码?</a></td>
				</tr>
			</table>
		</div>
		</form>
		<div class="user_loginRight">
			<h2>还不是91如意彩用户？</h2>
			<p>现在免费注册成为91如意彩用户，便能立刻享受便捷的购彩服务。</p>
			<div class="login_footerLine"><a href="/register/91register.jsp" class="login_registration">注册新用户</a></div>
			<div class="space15">&#160;</div>
			<p>使用合作网站账号登录91如意彩：</p>
			<p><a href="/function/unitedLogin!qqUnitedHandlyLogin" title="QQ" class="login_QQ">QQ</a><a href="/function/unitedLogin!alipayHandyLogin" title="支付宝" class="login_zhiFuBao">支付宝</a></p>
		</div>
	</div>
	<jsp:include page="/common/ruyicai_include_common_footer_91_litter.jsp"></jsp:include>
</div>
</div>
<script>
if($("#msg").val()=="登陆成功！"){
	 alert($("#msg").val());
	   window.close();
}
</script>
<jsp:include page="/common/setBody.jsp"></jsp:include>