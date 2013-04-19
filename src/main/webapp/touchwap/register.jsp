<!DOCTYPE HTML>
<html>
<head>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<meta
	content="如意彩，双色球,彩票,福彩中心,体彩中心,中奖号码,开奖公告,手机彩票，手机购彩，手机投注，3d，福彩3d，彩票开奖，彩票合买,福利彩票"
	name="Keywords">
<meta
	content="如意彩-让手机彩票更精彩。拥有专业手机投注服务平台，全手机彩票牌照运营，手机客户端购彩，彩票送礼服务，中奖通知等多种彩票特色产品服务。如意彩还提供，彩票合买,手机彩票资讯，号码走势，开奖查询等增值服务，充分满足广大彩民的需求。"
	name="Description">
<title>如意彩-用户登录</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/touchwap/resources/jquery-1.7.1.js"></script>
<script type="text/javascript">
$(document).bind("mobileinit", function(){
    $.mobile.ajaxEnabled = false;
});
</script>
<script type="text/javascript" src="<%=request.getContextPath() %>/touchwap/resources/jquery.mobile-1.1.0-rc.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/touchwap/resources/public-ruyicai-0.0.1.js"></script>
<link href="<%=request.getContextPath() %>/touchwap/resources/jquery.mobile-1.1.0-rc.1.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath() %>/touchwap/resources/jquery.mobile.structure-1.1.0-rc.1.css" rel="stylesheet" type="text/css">
<style type="text/css">
html, body, div, span, applet, object, iframe, table, caption, tbody, tfoot, thead, tr, th, td, del, dfn, em, font, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, dl, dt, dd, ol, ul, li, fieldset, form, label, legend { vertical-align: baseline; font-family: inherit; font-weight: inherit; font-style: inherit; outline: 0; padding: 0; margin: 0; border: 0; }
ol, ul, li { list-style: none; }
.clear { content: "."; height:0px; line-height:0px; overflow:hidden; display:none; visibility:hidden; clear:both }
a:link,a:visited{text-decoration:none;
}
</style>
<script type="text/javascript">
//注册页面 默认		
function hideCentent() {
	   var bindcardid = $("input[name='bindcardid']").attr('checked');
	   if(bindcardid != "checked"){
		   $("input[name='cardid']").attr("disabled","disabled");
		   $("input[name='realname']").attr("disabled","disabled");
	   }else{
		   $("input[name='cardid']").removeAttr("disabled");
		   $("input[name='realname']").removeAttr("disabled");
	   }
}
</script>
</head>
<body>
<!-- 用户注册 -->
<div id="userregister" data-role="page" class="type-index" data-add-back-btn="false">
	<div data-role="header" class="ui-header ui-bar-a ui-header-fixed"> 
		<a href="http://users.ruyicai.com/touchwap/login.jsp?reqUrl=http://touch.ruyicai.com:8000/touchWap/index" data-role="button" data-icon="arrow-l" data-iconpos="left">返回登录</a>
		<h1 class="ui-title" tabindex="0">用户注册</h1>
	</div>
	<div class="jg_50"></div>
	<div class="ui-body">
	<div class="error" id="error">${error}</div>
	<form action="<%=request.getContextPath() %>/function/registerAction!wapRegister" method="post">
		<div data-role="fieldcontain">
			<label for="name">用户名：</label>
			<input name="username" type="text" id="username" value="请输入用户名" onClick="if(this.value=='请输入用户名'){this.value=''}" onBlur="if(this.value==''){this.value='请输入用户名'}" />
		</div>
		<div data-role="fieldcontain">
			<label for="name">密&nbsp;码：</label>
			<input type="password" name="password" id="password" value=""  />
		</div>
		<div data-role="fieldcontain">
			<label for="name">确认密码：</label>
			<input type="password" name="check" id="check" value=""  />
		</div>
		<div data-role="fieldcontain">
			<fieldset data-role="controlgroup">
				<legend></legend>
				<input type="checkbox" name="bindcardid" id="checkbox-1a" class="custom" onclick="hideCentent()"/>
				<label for="checkbox-1a">绑定身份证信息</label>
			</fieldset>
		</div>
		<div data-role="fieldcontain">
			<label for="name">身份证号：</label>
			<input type="text" name="cardid" id="cardid" disabled="disabled"  value="请填写身份证号码" onClick="if(this.value=='请填写身份证号码'){this.value=''}" onBlur="if(this.value==''){this.value='请填写身份证号码'}" />
		</div>
		<div id="namehiden" data-role="fieldcontain">
			<label for="name">真实姓名：</label>
			<input type="text" name="realname" id="name" disabled="disabled" value="请填写身份证上的真实姓名" onClick="if(this.value=='请填写身份证上的真实姓名'){this.value=''}" onBlur="if(this.value==''){this.value='请填写身份证上的真实姓名'}"  />
		</div>
		<div data-role="fieldcontain">
			<fieldset data-role="controlgroup">
				<legend></legend>
				<input type="checkbox" name="disagree" id="checkbox-1a" class="custom" checked="checked"/>
				<label for="checkbox-1a">遵守并同意《用户服务协议》</label>
			</fieldset>
		</div>
		<input name="type" type="hidden" value="isTouch">
		<input id="reqUrl" type="hidden" value="<%=request.getParameter("reqUrl")==null?"":request.getParameter("reqUrl") %>" name="reqUrl" />
		<div class="ui-body">
			<fieldset class="ui-grid-a">
				<div class="ui-block-a">
					<a class="btn-link" href="http://users.ruyicai.com/touchwap/login.jsp?reqUrl=http://touch.ruyicai.com:8000/touchWap/index"><button type="button" data-theme="a">返回登陆</button></a>
				</div>
				<div class="ui-block-b">
					<button type="submit" data-theme="a">提交注册</button>
				</div>
			</fieldset>
		</div>
		</form>
	</div>
	<div data-role="footer"  data-theme="c"><span class="ui-body">
		<h6>手机号码、真实姓名和身份证号码是彩票兑奖的重要依据，为了保证您的合法权益，建议您注册时绑定身份证信</h6>
		息！</span></div>
</div>
<!-- 用户注册 --> 
</body>
</html>


