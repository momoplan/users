function LTrim(str){
    var i;
    for(i=0;i<str.length;i++)
    {
        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break;
    }
    str=str.substring(i,str.length);
    return str;
}
function RTrim(str){
    var i;
    for(i=str.length-1;i>=0;i--)
    {
        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break;
    }
    str=str.substring(0,i+1);
    return str;
}
function Trim(str){
    return LTrim(RTrim(str));
}

function Length(str) {  
    var len = 0;  
    for (var i=0; i<str.length; i++) {
        if (str.charCodeAt(i)>127 || str.charCodeAt(i)==94) {  
            len += 2;  
        } else {  
            len ++;  
        }  
    }  
    return len;  
}  
//---------------主要是集合登陆 注册 找回密码三个功能模块-----------------
//验证用户名是否可用
  var nameflag=0;
  //bl 为true  表示需要用户存在    为false 表示不需要这个用户名存在
	function checkName(id,bl){
		var patt=/^(([a-zA-Z0-9_]|[0-9]|[\u4e00-\u9fa5]){2,16})+$/;
		var getusername=$("#"+id).val();
		if(getusername!=Trim(getusername)){
			addWrongImages(id,id+'Image',id+'Tip','用户不支持输入空格，请重新输入！');
			nameflag=0;
			return false;
		}
		if(Length(getusername)<4 || Length(getusername)>16){
			addWrongImages(id,id+'Image',id+'Tip','用户名长度至少为4个，最多16个字符！');
			nameflag=0;
			return false;
		}
		if(patt.test(getusername)==false){
			addWrongImages(id,id+'Image',id+'Tip','用户名仅支持4-16位字母、汉字、数字、下划线！');
			nameflag=0;
			return false;
		}
		var params = "username="+getusername+"&isIe=中文";
		for(b in $.browser) if(b=='msie'){params=params+"&msie=1";};
		$.ajax({
			url:'/function/validateAction!queryUserName',
			type:"POST",
			data:params,
			async:false,
			dataType:'text',
			error: function(data){
					openAlert("网络出现异常");
					addWrongImages(id,id+'Image',id+'Tip','网络出现异常');
					nameflag=0;
					return false;},
			success:function(data){
				if(data=='0'){  
					//用户不存在
					//查询昵称是否存在
					var para = "nickname="+getusername+"&isIe=中文";
					for(b in $.browser) if(b=='msie'){para=para+"&msie=1";};
					$.ajax({
						url:'/function/validateAction!queryNickName',
						type:"POST",
						data:para,
						async: false,
						dataType:'text',
						error:function(){openAlert("网络出现异常");
								addWrongImages(id,id+'Image',id+'Tip','网络出现异常');
								nameflag=0;
								return false;},
						success:function(data){
							if(data =="1"){
								if(bl=="false"){
									addWrongImages(id,id+'Image',id+'Tip','用户名已存在，请更换用户名！');
									nameflag=0;
									return false;
								}else if(bl=="true"){
									addRightImages(id+'Image',id+'Tip');
									nameflag=1;
									return true;
								}
								
							}else if(data=='0'){
								if(bl=="false"){
									addRightImages(id+'Image',id+'Tip');
									nameflag=1;
									return true;
								}else if(bl=="true"){
									addWrongImages(id,id+'Image',id+'Tip','用户名不存在！');
									nameflag=0;
									return false;
								}
							}
					
						}
					});
					
				}else if(data =='1'){   //用户存在
					if(bl=="false"){
						addWrongImages(id,id+'Image',id+'Tip','用户名已存在，请更换用户名！');
						nameflag=0;
						return false;
					}else if(bl=="true"){
						addRightImages(id+'Image',id+'Tip');
						nameflag=1;
						return true;
					}
					
				}else{
					addWrongImages(id,id+'Image',id+'Tip',data);
					nameflag=0;
					return false;
				}
			}		
		});
		

	}
var passflag =0;
function checkPassword(uid,pid){
	var username = $("#"+uid).val();
	var password = $("#"+pid).val();
	if(username==null || username == "undefind" || username ==""){
		addWrongImages(pid,pid+'Image',pid+'Tip','请先输入用户名！');
		return false;
	}else{
		$.ajax({
			type:"get",
			url:"/function/validateAction!getPasswordByUserName?username="+username+"&reg_password="+password,
			datatype:'html',
			success:function(data){
				if(data=="true"){
					addRightImages(pid+'Image',pid+'Tip');
					passflag=1;
					return true;
				}else{
					addWrongImages(pid,pid+'Image',pid+'Tip','输入密码有误，请重新输入！');
				return false;
				}
			}
		});
	}
}

function checkNewPassword(uid,pid){
	var password = $("#"+pid).val();
	if(password!= Trim(password)){
		addWrongImages(pid,pid+'Image',pid+'Tip','空格不可做为密码输入！');
		passflag=0;
		return false;
	}
	if(Length(password)<6 || Length(password)>16){
		addWrongImages(pid,pid+'Image',pid+'Tip','密码长度必须是6-16个字符！');
		passflag=0;
		
		return false;
	}
	
	addRightImages(pid+'Image',pid+'Tip');
	passflag=1;
	return true;
}

function selectToAction(){
	var radioOne = $("#radioNumone").attr("checked");
	var radiotwo = $("#radioNumtwo").attr("checked");
	var inputyzm = $("#inputyzm").val();
	var name = $("#name").val();//新建的用户名
	var password = $("#password").val();
	var username = $("#username").val(); //原有账户的用户名
	if(radiotwo == true){
		//已有如意彩账户
		if(passflag==1){
			$.ajax({
				type:'post',
				url:'/function/unitedLogin!userInfoBind?username='+username+"&password="+password,
				async:false,
				success:function (data){
					window.location.href=data;
				}
			});
		}else{
			openAlert(decodeURI(EncodeUtf8("信息有误，请根据提示修改信息！")));
			return;
		}
		
	}else if(radioOne == true){
		//新创建账户
		if(passflag==0){
			openAlert(decodeURI(EncodeUtf8("请根据错误提示修改用户信息！")));
			return ;
		}
		if ($("#registerXieyi").attr("checked")!=true) {
			openAlert('您没有同意《如意彩服务协议》！');
			return;
		}
		if(name=="" || name=="undefined"){
			openAlert(decodeURI(EncodeUtf8("用户名不可为空！")));
			return;
		};
		registerUser(name,password,inputyzm);
		

	}

}

function registerUser(name,password,inputyzm){
	var parameters ="username="+name+"&inputyzm="+inputyzm+"&password="+password+"&isIe="+decodeURI(EncodeUtf8("中"));
	for(b in $.browser) if(b=='msie'){parameters=encodeURI(parameters)+"&msie=1";};
	$.ajax({
		type:'post',
		url:'/function/unitedLogin!registerUser',
		async:false,
		data:parameters,
		success:function (data){
			if(data=="success"){
				//注册成功，绑定用户
				$.ajax({
				type:'get',
				url:'/function/unitedLogin!userBindInfo',
				async:false,
				error:function(data){alert("绑定用户出现异常");},
				success:function(data){
					if(data=="success"){
						window.location.href='http://www.ruyicai.com/index.html';
					}else if(data=="yzmwrong"){
						window.location.href='/unitedLogin/setNickName_logistics.jsp';
					}else{
						window.location.href=data;
					}
				}
			
				});
			}else if(data=="inforegistered"){
				alert("您的支付宝信息在本站已被注册！");
				window.location.href='/unitedLogin/setNickName_logistics.jsp';
			}else if(data=="loginfailed"){
				alert("登录失败,请重新登录！");
				window.location.href='/unitedLogin/setNickName_logistics.jsp';
			}else{
				alert("注册用户失败，请重新填写信息！");
				window.location.href='/unitedLogin/setNickName_logistics.jsp';
			}
		}
	});
	
}

function setNickName(){
	var nickname = $("#nickname").val();
	var parameters ="nickname="+nickname+"&isIe="+decodeURI(EncodeUtf8("中"));
		for(b in $.browser) if(b=='msie'){parameters=encodeURI(parameters)+"&msie=1";};
	$.ajax({
		type:'post',
		url:'/function/validateAction!appsetNickName',
		async:false,
		data:parameters,//参数
		success:function (data){
			if(data=="success")
				window.location.href="http://www.ruyicai.com/index.html";
		}
	});

}
//------------------------- register.js开始---------------------

//登录验证
function loginFormValidator(){
	$.formValidator.initConfig({
		formid:"userLoginForm",
		onError:function(){
			alert(decodeURI(EncodeUtf8("校验没有通过，具体错误请看错误提示。")));
			return false;
		}
	});
	
	//用户名验证
	$("#username").formValidator({
		onshow : decodeURI(EncodeUtf8("请输入邮箱或手机号！")),
		onfocus : decodeURI(EncodeUtf8("请输入你的用户名，不可为空！")),
		oncorrect : decodeURI(EncodeUtf8("该用户名格式正确。"))
	}).regexValidator({
		regexp : "(^(13[0-9]|15[0-9]|18[0-9])\\d{8})|(^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?))$",
		onerror : decodeURI(EncodeUtf8("你输入的用户名格式不正确！"))
		
	});

	//密码验证
	$("#pwd").formValidator({
		onshow : decodeURI(EncodeUtf8("请输入密码！")),
		onfocus : decodeURI(EncodeUtf8("密码不能为空，密码长度 6- 16位。")),
		oncorrect : decodeURI(EncodeUtf8("密码合法。"))
	}).inputValidator({
			min : 6,
			max : 16,
			empty : {
				leftempty : false,
				rightempty : false,
				emptyerror : decodeURI(EncodeUtf8("密码两边不能有空符号！"))
			},
			onerror :decodeURI(EncodeUtf8("密码长度范围为6-16。"))
		}).regexValidator({
			regexp : "^[a-z0-9_]+$",
			onerror : decodeURI(EncodeUtf8("密码只由小写英文字母、阿拉伯数字、下划线混合。"))
		});	
}
//实名制认证验证
function realFormValidator(){
	$.formValidator.initConfig({
		formid:"realForm",
		onError:function(){
		alert(decodeURI(EncodeUtf8("校验没有通过，具体错误请看错误提示。")));
			return false;
		}
	});
	
	//真实姓名验证
	$("#name").formValidator({
		onshow : decodeURI(EncodeUtf8("4-12位汉字、字母、数字组合，不区分大小写！")),
		onfocus : decodeURI(EncodeUtf8("请输入你的真实姓名，可为空！")),
		oncorrect : decodeURI(EncodeUtf8("该用户名格式正确！"))
	}).regexValidator({
		regexp : "^[a-z]{1}[a-z0-9_]{3,20}$",
		onerror : decodeURI(EncodeUtf8("你输入的真实姓名格式不正确！"))
		
	});
	
	//身份证验证
	$("#userID").formValidator({
		onshow : decodeURI(EncodeUtf8("领奖取现的重要凭证，请认真填写，或在以后完善信息！")),
		onfocus : decodeURI(EncodeUtf8("输入 15或 18位的身份证。")) ,
		oncorrect : decodeURI(EncodeUtf8("输入正确。"))
	}).functionValidator({
		fun : isCardID
	});
	
	//邮箱验证
	$("#email").formValidator({
		empty : true,
		onshow : decodeURI(EncodeUtf8("请输入你的邮箱，可以为空！")),
		onfocus : decodeURI(EncodeUtf8("邮箱至少 6个字符，最多 100个字符。")),
		oncorrect : decodeURI(EncodeUtf8("邮箱格式输入正确。"))
		}).inputValidator({
				min : 6,
				max : 100,
				onerror : decodeURI(EncodeUtf8("你输入的邮箱长度非法，请确认！"))
			}).regexValidator({
		regexp : "^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",
		onerror : decodeURI(EncodeUtf8("你输入的邮箱格式不正确！"))
	});
}
//验证用户名
function checkUsername(){
	var username = $("#username").val();
	//var userflage =0;
	if(username.indexOf("@")==-1){
		var telePattern=/^(13[0-9]|15[0-9]|18[0-9])\d{8}$/;
		if(!telePattern.test(username)){
			return false;
		}
	}else{
		var telePattern=/(\S)+[@]{1}(\S)+[.]{1}(\w)+/;
		if(!telePattern.test(username)){
			return false;
		}
		
	}
	if(username==null || username==""){
		return false;
	};
	return true;
}

// 验证密码
function checkPWD(){
	var pass = $("#pwd").val();
	//var username = $("#username").val();
	//var passflage = 0;
	if(pass==null || pass==""){
		return false;
	};
	return true;

}

//验证验证码
function checkYZ(){
	if($("#rank_value").val()!=0){
		var pass1 = $("#password1").val();
		if(pass1==null || pass1==""){
			return false;
		}
	}
	return true;
}

// 表单提交验证手机号码和密码
function checkloginformsub(formName){
	var a=true;
	var usernameOk = checkUsername();
	if(!usernameOk){
		openAlert(decodeURI(EncodeUtf8("请输入正确的用户名！")));
		$("#rank_id").css("display","");
		$("#rank_value").val("1"); 
		return false;
	};
	var passOk = checkPWD();
	if(!passOk){
		openAlert(decodeURI(EncodeUtf8("请输入正确的密码！")));
		$("#rank_id").css("display","");
		$("#rank_value").val("1"); 
		return false;
	};
	var yzOk = checkYZ();
	if(!yzOk){
		openAlert(decodeURI(EncodeUtf8("请输入正确的验证码！")));
		$("#rank_id").css("display","");
		$("#rank_value").val("1"); 
		return false;
	};
	if(a == usernameOk && a == passOk && a == yzOk){
		return true;
	};
}

//修改密码页面的显示强弱的函数
 function passStrong(){
	 var num = new RegExp('[0-9]');
	 var pinyin = new RegExp('[A-Za-z]');
	 var teshu = new RegExp('[_]');
	 var tiao = '';
	 var countT = 0;
	 
	 var pwd =$("#newpassWord").val();
	 if(pwd!=null){
	 	if(num.test(pwd)||pinyin.test(pwd)){
		 $("#showStrong").text(decodeURI(EncodeUtf8("弱")));
		 countT = countT + 3;
		 	if(num.test(pwd)&&pinyin.test(pwd)){
		 		$("#showStrong").text(decodeURI(EncodeUtf8("中")));
				countT = countT + 3;
		 			if(teshu.test(pwd)){
		 				$("#showStrong").text(decodeURI(EncodeUtf8("强")));
						countT = countT + 4;
			 			}
			 	}
		 }
		 
	for ( var i = 0; i < 10; i++) {
		if (i < countT) {
			tiao = tiao + '<li class="ziliao_hong">&nbsp;</li>';
		} else {
			tiao = tiao + '<li class="ziliao_hui">&nbsp;</li>';
		}
		$("#tiao").html(tiao);
	}
		 
	}else{
			 $("#showStrong").text("");
			 $("#tiao").html('<li class="re_pass_hui">&nbsp;</li>');
			 }


 }

	//打开邮箱内的链接
function openEmail(url){

    window.location.href="http://"+url;
}

//联合登录页面的密码强弱的函数

function passStrength(){
	var num = new RegExp('[0-9]');
	var pinyin = new RegExp('[A-Za-z]');
	var teshu = new RegExp('[_]');
	var password =$("#password").val();
	if(password!=null || !password==""){
	if(num.test(password)||pinyin.test(password)){
	$("#tips_data").text("弱");
	$("#line").html('<ul><li>&#160;</li><li>&#160;</li></ul>');
	if(num.test(password)&&pinyin.test(password)){
	$("#tips_data").text("中");
	$("#line").html('<ul><li>&#160;</li>'+
		'<li>&#160;</li><li>&#160;</li>'+
		'<li>&#160;</li><li>&#160;</li></ul>');
		if(teshu.test(password)){
			$("#tips_data").text("强");
			$("#line").html(
		'<ul><li>&#160;</li><li>&#160;</li>'+
		'<li>&#160;</li><li>&#160;</li>'+
		'<li>&#160;</li><li>&#160;</li>'+
		'<li>&#160;</li><li>&#160;</li>'+
		'<li>&#160;</li><li>&#160;</li></ul>');
			}
	}
	}
	}
}
 
 
