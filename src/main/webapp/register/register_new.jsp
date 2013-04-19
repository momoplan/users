<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder" %><%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="/common/ruyicai_include_common_top_http.jsp"></jsp:include>
<base href="<%=basePath%>">
<meta http-equiv="keywords" content="如意彩，用户注册，购彩服务"/>
<meta http-equiv="description" content="现在免费注册成为如意彩用户，便能立刻享受便捷的购彩服务"/>
<title>用户注册</title>
<link type="text/css" href="/css/util.css" rel="stylesheet"/>
<link type="text/css" href="/css/ruserAll.css" rel="stylesheet"/>

<script type="text/javascript" src="/js/jqueryJS/jquery-1.5.min.js"></script>
<script type="text/javascript" src="/js/util.js"></script>
<script type="text/javascript" src="/js/register_new.js"></script>
<script type="text/javascript">
//如意点卡验证 	^([0-9]{16,21})
function checkCardNo(){
	var cardNo = $("#card_id").val();
	if(cardNo.trim()==""){
		$("#card_idTip").text("卡号不能为空！");
		return false;
	}else if(cardNo.trim().length!=16 ){
		$("#card_idTip").text("如意卡号格式必须是16位数字。");
		return false;
	}else if(cardNo.trim().length==16){
		var re = /^[\d]+$/;
		var ret = re.test(cardNo);
		if(!ret){
			$("#card_idTip").text("如意卡号格式必须是16位数字。");
			return false;
		}
		$("#card_idTip").text("卡号格式正确。");
		return true;
	}
	$("#card_idTip").text("如意卡号格式必须是16位数字。");
	return false;
}

//如意彩点卡密码验证
function checkCardPwd(){
	var cardpwd = $("#card_pwd").val();
	if(cardpwd.trim()==""){
		$("#card_pwdTip").html("密码不能为空！");
		return false;
	}else if(cardpwd.trim().length!=6 ){
		$("#card_pwdTip").html("密码长度范围为6位数字。");
		return false;
	}else if(cardpwd.trim().length==6){
		var re = /^[\d]+$/;
		var ret = re.test(cardpwd);
		if(!ret){
			$("#card_pwdTip").html("密码只由数字组成。");
			return false;
		};
		$("#card_pwdTip").html("密码格式正确。");
		return true;
	};
	$("#card_pwdTip").html("密码长度范围为6位数字。");
	return false;

}
</script>
<script type="text/javascript">
var a=0 ;
function send()
{	   
		var rclass = $("#a2").attr("class");//phone_sendMessageBtn
		$("#a2").removeClass(rclass);
		$("#a2").addClass("phone_sendMessageBtnHide");
		$("#a1").removeClass("phone_sendMessageBtnHide");
		$("#a1").addClass(rclass);
		$("#a2").attr("disabled", true);
		phoneBandCheck(51);
}
function resend()
{
	phoneBandCheck(51);
}
function oncl(){
	$('#count').countdown({seconds: 60,callback: 'countadd()'}); 
	}
function countadd()
{a=1;}
</script>
</head>
<body>
<div class="ZhuCe">
	<jsp:include page="/common/ruyicai_include_common_new_top.jsp"></jsp:include>
	<div class="ZhuCe_body" id="registerbody">
		<div class="ZhuCe_titleBg">
			<span class="ZhuCe_titleLeft">快速注册</span><span class="ZhuCe_titleRight">我已经注册，现在就<a href="http://users.ruyicai.com/login.jsp" title="登录">登录</a></span>
		</div>
		<form name="userPhoneRegisterForm" id="userPhoneRegisterForm" method="post">
			<table border="0" cellspacing="0" cellpadding="0" class="ZhuCe_table table_space">
				<thead><tr>
					<th>用 户 名：</th>
					<th width="120" colspan="2"><input name="username" id="username" type="text" class="ZhuCe_input" onblur="checkUserName()"/></th>
					<td width="500"><span id="usernameImage"></span><span id="usernameTip">4-16个字符，可以使用字母、汉字、数字、下划线</span></td>
				</tr></thead>
				<tbody><tr>
					<th>登录密码：</th>
					<th colspan="2"><input name="password" id="password" type="password" class="ZhuCe_input" onblur="checkPassWord()" onkeydown="passStrength()"/></th>
					<td width="500"><span id="passwordImage"></span><span id="passwordTip">6-16个字符，建议使用字母、数字组合、混合大小写</span></td>
				</tr>
				<tr>
					<th></th>
					<th colspan="2">
					<dl>
						<dd class="basicInfor_leve">密码强度</dd>
						<dd class="basicInfor_level" id="tips_data">弱</dd>
						<dd class="basicInfor_bar" id="line">
						</dd>
					</dl></th>
					<td></td>
				</tr>
				</tbody>
				<tfoot><tr>
					<th>确认密码：</th>
					<th colspan="2"><input name="realPass" id="realPass" type="password" class="ZhuCe_input" onblur="checkRealPass()"/></th>
					<td ><span id="realPassImage"></span><span id="realpasswordTip">请再次输入密码</span></td>
				</tr>
				<tr style="display: none;">
					<th>&#160;</th>
					<td colspan="3">
						<dl class="CheckBox RuyikaRegisterCheckBox">
							<dt id="checkCard" class="RuyikaRegister" onmouseover="HoverOver($(this))" onmouseout="HoverOut($(this))" onclick="Switch($(this))" ControlTarget=".RuyikaRegister" ></dt>
							<dd>使用如意彩票卡</dd>
						</dl>
					</td>
				</tr>
				<tr class="RuyikaRegister Switch" style="display: none;"><th>如意卡号：</th><th colspan="2"><input name="cardid" id="card_id" type="text" onfocus="registerFocus(6);" class="registerinput" onblur="checkCardNo()"/></th><td><span id="card_idTip">请输入如意卡号。</span></td></tr>
				<tr class="RuyikaRegister Switch" style="display: none;"><th>密　　码：</th><th colspan="2"><input name="cardpwd" id="card_pwd" type="password" onfocus="registerFocus(7);" class="registerinput" onblur="checkCardPwd()"/></th><td><span id="card_pwdTip">请输入如意卡密码。</span></td></tr>
				
				<tr>
					<th >验 证 码：</th>
					<th><input name="passRegister" id="passRegister" type="text" onKeyDown="if (event.keyCode==13)register();" class="ZhuCe_smallInput"/></th>
					<th width="76"><img src="/common/image.jsp" id="magPassRegister"/></th>
					<td>看不清？<a href="javascript:change('magPassRegister');" title="换一张">换一张</a></td>
				</tr>
				<tr>
					<th>&#160;</th>
					<td colspan="3" class="special_td"><input type="checkbox" checked="checked" id="registerXieyi"/>我已满十八周岁且已阅读并同意<a href="http://www.ruyicai.com/rules/userProtocol.html" target="_blank" title="用户服务协议" >《用户服务协议》</a></td>
				</tr>
				<tr>
					<th>&#160;</th>
					<th colspan="3" class="special_td">
					<input type="hidden" id="userid" name="userid" value='<s:property value="#parameters.userid"/>'>
					<input type="hidden" id="flag" name="flag" value='<s:property value="#parameters.flag"/>'>
					<input type="button" class="ZhuCe_btn light" id="phoneregister_submit" onclick="register()" value="立即注册" /></th>
				</tr></tfoot>
			</table>
		</form>
	</div>
	<jsp:include page="/common/ruyicai_include_common_footer_litter.jsp"></jsp:include>
</div>
 <input type="hidden" value="" name="tishi" id="tishi"/>
<jsp:include page="/common/setBody.jsp"></jsp:include>

<div id="login_pop" class="WindowCenter" style="display:none;" ></div>
<div class="BodyBG">&#160;</div>
<div class="AlertWindow BalanceWindow WindowCenter" id="Shouye_Alert" style="display:none">
<div class="WindowTittle"><h3>温馨提示</h3><span class="Alertclose" onclick='loginShow("Shouye_Alert",false);'>&#160;</span></div>
	<div class="InsideBorder">
		<table class="BalanceTable">
			<tbody>
				<tr>
					<th id="AlertMsg">您好，红球最多只能定5个胆码，红球（6）是否作为杀号选择？</th>
				</tr>
				<tr>
						<th class="space10">&#160;</th>
					</tr>
				<tr>
					<th><input type="button" value="确　定" class="ZhuCe_btn"  onclick='loginShow("Shouye_Alert",false);'/></th>
				</tr>
			</tbody>
		</table>
	</div>
	</div>
</body>
</html>