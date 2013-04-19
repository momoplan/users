function Top(){
	var Base = { name:"public_top_", imgUrl:"http:\/\/img0.91huo.cn\/sj\/images\/index\/110105\/", font:"宋体", target:"_blank" }, TopGG,
	Logo = { name:"91手机娱乐门户", url:"http://sj.91.com/", target:"_self", img:Base.imgUrl + "logo_07.jpg" },
	ListItems = [
		{ name:"首页", url:"http://sj.91.com/", title:"iphone专区", width:210, target:Base.target, items:[
			{ name:"iPhone ", url:"http://iphone.91.com/", title:"iPhone专区", target:Base.target },
			{ name:"iPad ", url:"http://ipad.91.com/", title:"ipad专区", target:Base.target },
			{ name:"Android ", url:"http://android.91.com/", title:"Android专区", target:Base.target },
			{ name:"WP7 ", url:"http://wm.sj.91.com/", title:"windows phone专区", target:Base.target }
		]},
		{ name:"下载",url:"http://sj.91.com/download/",  title:"手机软件主题壁纸下载", target:Base.target, width:270, items:[
			{ name:"苹果", url:"http://app.91.com/", title:"iPhone与iPad软件游戏下载", target:Base.target },
			{ name:"安卓", url:"http://apk.91.com", title:"安卓软件游戏下载", target:Base.target },
			{ name:"游戏", url:"http://iphone.91.com/game/", title:"iPhone与iPad游戏", target:Base.target },
			{ name:"小说", url:"http://ibbs.91.com/forum-47-1.html", title:"手机小说", target:Base.target },
            { name:"壁纸", url:"http://mobile.91.com/Pic/index_1_0.html", title:"iOS壁纸专区", target:Base.target },
            { name:"主题", url:"http://mobile.91.com/Theme/iPhone.html", title:"iPhone主题", target:Base.target },
            { name:"铃声", url:"http://mobile.91.com/ring/", title:"手机铃声", target:Base.target }
								]},
		{ name:"资讯", url:"http://zx.sj.91.com/", title:"智能手机资讯", target:Base.target, width:195, items:[
			{ name:"行业", url:"http://zx.sj.91.com/industry-list/", title:"手机行业资讯", target:Base.target },
			{ name:"趣闻", url:"http://zx.sj.91.com/lm/", title:"手机趣闻", target:Base.target },
			{ name:"评测", url:"http://lib.91.com/", title:"91评测室", target:Base.target },
			{ name:"视频", url:"http://zx.sj.91.com/video/", title:"精彩视频", target:Base.target },			
			{ name:"知道", url:"http://zhidao.91.com/", title:"91知道", target:Base.target }
		]},
		{ name:"产品", url:"http://soft.sj.91.com", title:"91软件产品", target:Base.target, width:170, noborder:true, items:[

			{ name:"助手", url:"http://zs.91.com/", title:"91手机助手", target:Base.target },
			{ name:"看书", url:"http://ks.sj.91.com/", title:"91熊猫看书", target:Base.target },
			{ name:"桌面", url:"http://home.sj.91.com/", title:"91熊猫桌面", target:Base.target },
			{ name:"发布", url:"http://market.sj.91.com", title:"91软件市场发布平台", target:Base.target }
		]},
		{ name:"新手", url:"http://sj.91.com/guide/", title:"91iphone新手专区", target:Base.target, width:210, items:[
			{ name:"越狱", url:"http://iphone.91.com/jailbreak/", title:"iphone越狱教程", target:Base.target },
			{ name:"固件", url:"http://iphone.91.com/fw/", title:"固件下载", target:Base.target },
			{ name:"刷机", url:"http://android.91.com/rom/", title:"安卓刷机教程", target:Base.target },
                                                  { name:"应用导航", url:"http://hao.91.com/", title:"应用下载导航", target:Base.target }
		]},
		{ name:"生活", url:"http://sj.91.com/shopping/", title:"91移动生活指南", target:Base.target, width:270, items:[
            { name:"配件", url:"http://pj.91.com", title:"手机周边配件推荐", target:Base.target },
            { name:"商城", url:"http://buy.91.com/", title:"91商城", target:Base.target },
      { name:"科技", url:"http://pj.91.com/tech/", title:"91科技", target:Base.target },
            { name:"活动", url:"http://ibbs.91.com/forum-127-1.html", title:"论坛社区活动", target:Base.target },
			{ name:"新机", url:"http://zx.sj.91.com/list/machine-list.shtml", title:"新手机与平板终端", target:Base.target },
			{ name:"热图", url:"http://mt.91.com/", title:"美女热图", target:Base.target },
            { name:"手机壳", url:"http://buy.91.com/goods/goodslist_23.html", title:"91手机壳", target:Base.target }
		]},
		{ name:"论坛", url:"http://ibbs.91.com/", title:"91手机论坛", target:Base.target, width:195, items:[
			{ name:"影视", url:"http://ibbs.91.com/forum-46-1.html", title:"影视", target:Base.target },
            { name:"补丁", url:"http://ibbs.91.com/forum-141-1.html", title:"补丁", target:Base.target },
			{ name:"二手", url:"http://ibbs.91.com/forum-125-1.html", title:"二手交易", target:Base.target },
			{ name:"精华", url:"http://ibbs.91.com/portal.php?mod=topic&topicid=2", title:"91手机论坛精华贴", target:Base.target },
            { name:"商家", url:"http://ibbs.91.com/forum.php?gid=45", title:"认证商家", target:Base.target }
		]},
		{ name:"游戏", url:"http://game.91.com",title:"91手机游戏", target:Base.target, width:170, noborder:true, items:[
			{ name:"充值", url:"https://mpay.91.com", title:"91豆充值", target:Base.target },
			{ name:"农场", url:"http://nc.sj.91.com", title:"91农场", target:Base.target },
			{ name:"部落", url:"http://bl.91.com/", title:"91部落", target:Base.target },
			{ name:"页游", url:"http://g2.v3.91.com/Hit.aspx?Id=146745", title:"页游", target:Base.target }
		]}
	];
	this.$link = function(txt, url, target, title, style){ return (url) ? "<a href=\"" + url + "\" " + ((style) ? "style=\"" + style + "\"" : "") + ((target) ? " target=\"" + target + "\"" : "") + ((title) ? " title=\"" + title + "\"" : " title=\"" + txt + "\"") + ">" + txt + "<\/a>" : "<a " + ((title) ? " title=\"" + title + "\"" : " title=\"" + txt + "\"") + ">" + txt + "<\/a>"; }
	
	var style = "<style type=\"text\/css\">body{ margin:0; }\n ." + Base.name + ", ." + Base.name + " *{ position:static; float:none; margin:0; padding:0; font:12px \"宋体\"; line-height:20px; list-style-type:none; border:0; overflow:hidden; background-color:transparent; }\n ." + Base.name + "{ height:65px; background-image:url(" + Base.imgUrl + "top_bg.jpg); text-align:center; border-bottom:1px solid #dcdcdc; }\n ." + Base.name + " a{ color:#555; text-decoration:none; }\n ." + Base.name + " a:hover{ color:#1f8bbb; text-decoration:underline; }\n ." + Base.name + "con{ width:1000px; float:none; margin:0 auto; }\n ." + Base.name + "logo{ float:left; cursor:pointer; background:url(" + Logo.img + ") no-repeat; width:130px; display:inline; margin:7px auto 0; padding-right:10px; }\n ." + Base.name + "logo a{ display:block; text-indent:-999em; font-size:0; line-height:0; height:55px; }\n ." + Base.name + "links{ margin-top:8px; width: }\n ." + Base.name + "links dl, ." + Base.name + "links dt, ." + Base.name + "links dd{ float:left; display:inline; white-space:nowrap; }\n ." + Base.name + "links dd a{ line-height:20px; }\n ." + Base.name + "links dl{ width:210px;height:20px; border-right:1px dotted #dcdcdc; padding:4px 0 2px;overflow:hidden}\n ." + Base.name + "links dt{ width:45px; }\n ." + Base.name + "links dt, ." + Base.name + "links dt a{ font-weight:bold; }<\/style>",
	info = "<div class=\"" + Base.name + "\"><div class=\"" + Base.name + "con\"><div class=\"" + Base.name + "logo\"><a href=\"http:\/\/sj.91.com\/\" title=\"91手机娱乐\"><\/a><\/div><div class=\"" + Base.name + "links\">";
	for(var i = 0; i < ListItems.length; i++){
		info += "<dl style=\"width:" + ListItems[i].width + "px; " + ((ListItems[i].noborder) ? "border-right:0;" : "") + "\"><dt>" + this.$link(ListItems[i].name, ListItems[i].url, ListItems[i].target, ListItems[i].title) + "<\/dt><dd>";
		if(ListItems[i].items) for(var k = 0; k < ListItems[i].items.length; k++) info += " " + this.$link(ListItems[i].items[k].name, ListItems[i].items[k].url, ListItems[i].items[k].target, ListItems[i].items[k].title);
		info += "<\/dd><\/dl>";
	}
	document.writeln(style + info + "<\/div><\/div><\/div>");
}
Top.GetPublicTop = function(){ Top(); }

//底部
function Footer(public_footer_id){
	Footer.Font = "宋体";
	Footer.ImgUrl = "http://img8.91huo.cn/sj/images/index/090915/";
	Footer.BaseTarget = "_blank";
	Footer.PublicName = (public_footer_id) ? public_footer_id : "public_footer";
                //Footer.cnzzUrl = "http://w.cnzz.com/c.php?id=30048851";
	Footer.Nav = [
		{ name:"关于我们", url:"http://sj.91.com/about/", target:Footer.BaseTarget, splitIcon:" ┊ "},
		{ name:"网站合作", url:"http://sj.91.com/about/business.html", target:Footer.BaseTarget, splitIcon:" ┊ ", style:"color:#c30"},
		{ name:"诚聘英才", url:"http://hr.nd.com.cn/SocietyJobs.aspx", target:Footer.BaseTarget, splitIcon:" ┊ " },
		{ name:"版权声明", url:"http://sj.91.com/about/copyright.html", target:Footer.BaseTarget, splitIcon:" ┊ " },
		{ name:"网站地图", url:"http://sj.91.com/about/map.html", target:Footer.BaseTarget, splitIcon:" ┊ " },
		{ name:"友情链接", url:"http://sj.91.com/about/link.shtml", target:Footer.BaseTarget, splitIcon:" ┊ " },
                                { name:"软件版权举报", url:"http://ibbs.91.com/forum-126-1.html", target:Footer.BaseTarget},
		{},
		{ name:"增值电信业务经营许可证闽B2-20050038", url:"http://www.91.com/about/licence.shtml", target:Footer.BaseTarget, splitIcon:"　" },
		{ name:"闽网文『2011』0566-021号", url:"http://www.91.com/about/telecom.shtml", target:Footer.BaseTarget },
		{name:"客服热线: 0591-87085788",splitIcon:" " },
                                {},

		{ name:"Copyright 1999-2012 &copy;", splitIcon:" " },
		{ name:"91.com", url:"http://www.91.com/", target:Footer.BaseTarget, splitIcon:" "  },
		{ name:"All rights reserved" },
                                {},
                                { name:"福建网龙计算机网络信息技术有限公司" }
	];
	Footer.CA = { name:"闽B2-20050038", url:"http://www.miitbeian.gov.cn/", target:Footer.BaseTarget, img:Footer.ImgUrl + "icp.gif" };
	Footer.PO = { name:"报警岗亭", url:"http://www.fuzhou.cyberpolice.cn/", target:Footer.BaseTarget, img:Footer.ImgUrl + "pl.gif" };

	document.writeln("<style type=\"text\/css\">\n ." + Footer.PublicName + "_content{ width:100%; background-color:transparent; } \n ." + Footer.PublicName + "{ width:980px; float:none; margin:0 auto; position:relative; padding:0;overflow:hidden; padding:10px 0; }\n ." + Footer.PublicName + " *{ font:normal 12px \"" + Footer.Font + "\"; color:#555; text-align:center; line-height:20px; padding:0; margin:0; position:static; float:none; border:0; background:none; list-style-type:none; overflow:hidden; }\n ." + Footer.PublicName + "_lcon{ width:550px; float:left; }\n ." + Footer.PublicName + "_rcon{ width:160px; margin-left:10px; float:left; }<\/style><div class=\"" + Footer.PublicName + "_content\"><div class=\"" + Footer.PublicName + "\"><div class=\"" + Footer.PublicName + "_lcon\">");
	for(var i = 0; i < Footer.Nav.length; i++) document.writeln((Footer.Nav[i].name) ? Fun.$link(Footer.Nav[i].name, Footer.Nav[i].url, Footer.Nav[i].target, Footer.Nav[i].style) + ((Footer.Nav[i].splitIcon) ? Footer.Nav[i].splitIcon : "") : "<br \/>");
	document.writeln("<\/div><div class=\"" + Footer.PublicName + "_rcon\">" + (((Footer.CA.img) ? Fun.$link("<img src=\"" + Footer.CA.img + "\" alt=\"" + Footer.CA.name + "\" title=\"" + Footer.CA.name + "\" \/>", Footer.CA.url, Footer.CA.target) : "") + "<br \/>" + Fun.$link(Footer.CA.name, Footer.CA.url, Footer.CA.target, Footer.CA.style)) + "<\/div><div class=\"" + Footer.PublicName + "_rcon\">" + (((Footer.PO.img) ? Fun.$link("<img src=\"" + Footer.PO.img + "\" alt=\"" + Footer.PO.name + "\" title=\"" + Footer.PO.name + "\" \/>", Footer.PO.url, Footer.PO.target) : "") + "<br \/>" + Fun.$link(Footer.PO.name, Footer.PO.url, Footer.PO.target, Footer.PO.style)) + "<\/div><\/div><\/div>");
	//document.writeln("<script src=\"" + Footer.cnzzUrl + "\" language=\"JavaScript\"><\/script>");
}


Footer.GetPublicFooter = function(url){
	url = (url) ? url : Fun.$domain(window.location.href);
	switch(url){
		case "zt.sj.91.com": Footer(); break;
		default: Footer();
	}
}

//BUG反馈
function win_open_bug(errType,width,height,siteUrl){
	var err = null;
	var url = (siteUrl != "" && siteUrl != null) ? encodeURIComponent(siteUrl) : encodeURIComponent(document.location.toString());
	try{errType = (errType != null) ? parseInt(errType) : 1;}catch(err){errType = 1;}
	try{width = (width != null) ? parseInt(width) : 800;}catch(err){width = 800;}
	try{height = (height != null) ? parseInt(height) : 720;}catch(err){height = 720;}
	window.open("http://zc.91.com/?controller=user&action=showaddstep1&i_url="+url,"errPage","fullscreen=no,channelmode=no,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=" + width.toString() + ",height=" + height.toString());
}

//建议提交
function win_open_advice(errType,width,height,siteUrl){
	var err = null;
	var url = (siteUrl != "" && siteUrl != null) ? encodeURIComponent(siteUrl) : encodeURIComponent(document.location.toString());
	try{errType = (errType != null) ? parseInt(errType) : 1;}catch(err){errType = 1;}
	try{width = (width != null) ? parseInt(width) : 800;}catch(err){width = 800;}
	try{height = (height != null) ? parseInt(height) : 720;}catch(err){height = 720;}
	window.open("http://zc.91.com/?controller=user&action=showaddadvicestep1&i_url="+url,"errPage","fullscreen=no,channelmode=no,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=" + width.toString() + ",height=" + height.toString());
}

//公共功能模块
function Fun(){
	Fun.IEVerPattern = /MSIE( )(\d+(\.\d+)?)/;
	Fun.$ie = (document.all) ? true : false; //判断是否IE
	Fun.$IEVer = (Fun.IEVerPattern.test(window.navigator.userAgent)) ? parseFloat(RegExp.$2) : null; //获取IE版本号
	Fun.$id = function(id){ return document.getElementById(id); } //获取ID对象值：（对象ID名）
	//获取tag对象名集合：（对象Tag标签名）[父对象限定]
	Fun.$tag = function(tagName, parent){ return (parent) ? parent.getElementsByTagName(tagName) : document.getElementsByTagName(tagName) ; }
	//获取Class样式名对象集合：（class样式名），[Tag标签限定]，[父对象限定]
	Fun.$cl = function(cl, objName, parentNodeObj){
		var retnode = [], myclass = new RegExp('\\b' + cl + '\\b'), elem = (parentNodeObj == null) ? document.getElementsByTagName(objName) : parentNodeObj.getElementsByTagName(objName), classes;
		for (var j = 0; j < elem.length; j++) { classes = elem[j].className; if (myclass.test(classes)) retnode.push(elem[j]); }
		return retnode;
	}
	//设置链接文本：（待设置的文本），[链接目标地址]，[链接目标窗口]，[样式]
	Fun.$link = function(txt, url, target, style){ return (url) ? "<a href=\"" + url + "\" " + ((style) ? "style=\"" + style + "\"" : "") + ((target) ? " target=\"" + target + "\"" : "") + ">" + txt + "<\/a>" : txt; }
	//设置对象集合事件：（待设置的对象集合），[鼠标覆盖事件]，[鼠标移开事件]，[鼠标点击事件]，
	Fun.$action = function(objArray, mouseoverHandle, mouseoutHandle, mouseclickHandle){
		if(!objArray || objArray.length < 1) return;
		for(var i = 0; i < objArray.length; i++){
			if(mouseoverHandle) objArray[i].onmouseover = mouseoverHandle;
			if(mouseoutHandle) objArray[i].onmouseout = mouseoutHandle;
			if(mouseclickHandle) objArray[i].onclick = mouseclickHandle;
		}
	}
	//设置目标相对浮动：（对比源对象），（待设置目标对象），[左偏移量]，[上偏移量]
	Fun.$position = function(obj, targetObj, leftOffset, topOffset){
		var temp = obj.offsetParent, left = obj.offsetLeft, top = obj.offsetTop;
		while(temp){ left += temp.offsetLeft; top += temp.offsetTop; temp = temp.offsetParent; }
		targetObj.style.left = (left + leftOffset) + "px";targetObj.style.top = (top + topOffset) + "px";
	}
	//获取URL参数函数（参数名）
	Fun.$url = function(name){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"), r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	}
	//获取URL参数函数（参数位置）
	Fun.$staticurl = function(pos){
		    var pathArray = window.location.href.split("/"),url = pathArray[pathArray.length - 1], arr = url.split(',');
            if(arr.length-1 >= pos) return arr[pos];
            return null;
	}
	//设置添加收藏效果：（收藏地址），（收藏标题）
	Fun.$fav = function(url, title){ try{ (Fun.$ie) ? window.external.addFavorite(url, title) : window.sidebar.addPanel(url, title); }catch(e){ alert("收藏失败！请手动操作"); } }
	//设置设为首页效果：（触发源对象），（首页地址）
	Fun.$home = function(obj, url){ try{ obj.style.behavior = "url(#default#homepage)"; obj.setHomePage(url); }catch(e){ if(window.netscape) try { netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); }catch(e){ alert("您的浏览器不支持此功能！"); } } }
	//获取地址栏中域名：（域名参数）
	Fun.$domain = function(url){ return (url.indexOf("http") > -1) ? url.split("/")[2] : url.split("/")[0]; }
	//ajax处理：（ajax地址），（获取方式），（回调函数），[编码方式]
	Fun.$ajax = function(url, type, ajaxHandle, charset){
		if(type){
			var xmlHttp;
			try{ xmlHttp = new XMLHttpRequest(); }catch (e){ try{ xmlHttp = new ActiveXObject("Msxml2.XMLHTTP"); }catch (e){ try{ xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); }catch (e){ alert("您的浏览器不支持AJAX！"); return false; } } }
			if(Fun.$ie) xmlHttp.onreadystatechange = ajaxHandle;
			if(!Fun.$ie) xmlHttp.onload = ajaxHandle;
			xmlHttp.open(type, url, false);
			xmlHttp.setRequestHeader( "Content-Type", "text/html;charset=" + charset );
			xmlHttp.send(null);
		}else{
			var xmlHttp = document.createElement("script"); xmlHttp.type = "text\/javascript";
			xmlHttp.charset = charset; xmlHttp.src = url;
			(Fun.$ie) ? xmlHttp.onreadystatechange = ajaxHandle : xmlHttp.onload = ajaxHandle;
			Fun.$tag("head")[0].appendChild(xmlHttp);
			setTimeout(function(){ Fun.$tag("head")[0].removeChild(xmlHttp); }, 5000);
		}
	}
	Fun.$sajax = function(url, method, callbackHandle, charset){
		var xmlHttp = {
			_objPool:[],
			_getInstance:function (){
				for (var i = 0; i < this._objPool.length; i ++){
					if (this._objPool[i].readyState == 0 || this._objPool[i].readyState == 4)
					return this._objPool[i];
				}
				this._objPool[this._objPool.length] = this._createObj();
				return this._objPool[this._objPool.length - 1];
			},
			_createObj:function(){
				if (window.XMLHttpRequest){ var objXMLHttp = new XMLHttpRequest(); }else{
					var MSXML = ['MSXML2.XMLHTTP.8.0', 'MSXML2.XMLHTTP.7.0', 'MSXML2.XMLHTTP.6.0', 'MSXML2.XMLHTTP', 'Microsoft.XMLHTTP'];
					for(var n = 0; n < MSXML.length; n ++){ try{  var objXMLHttp = new ActiveXObject(MSXML[n]); break; }catch(e){}}
				}
				if (objXMLHttp.readyState == null){
					objXMLHttp.readyState = 0;
					objXMLHttp.addEventListener("load", function (){
						objXMLHttp.readyState = 4;
						if (typeof objXMLHttp.onreadystatechange == "function"){ objXMLHttp.onreadystatechange(); }
					}, false);
				}
				return objXMLHttp;
			},
			sendReq: function (method, url, charset, callback){
				var objXMLHttp = this._getInstance();
				with(objXMLHttp){
					try{
						url += ((url.indexOf("?") > 0)) ? "&randnum=" + Math.random() : "?randnum=" + Math.random(); 
              		open(method, url, true);
						setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=' + charset);
						send(null);
						onreadystatechange = function (){ if (objXMLHttp.readyState == 4 && (objXMLHttp.status == 200 || objXMLHttp.status == 304)) callback(objXMLHttp.responseText); }
					}catch(e){ alert(e); }
				}
			}
		};
		xmlHttp.sendReq(method, url, (charset) ? charset : "utf-8", callbackHandle);
	}
	//取不重复随机数：（数据集合），（取的长度）
	Fun.$getRandom = function(array, len){ for (var r = [], i = 0; i < len; i++){ r.push(array.splice(Math.floor(Math.random() * array.length), 1)); } return r; }
}Fun();

//设置Cookie
function Cookie(){
	Cookie.GetCookie = function(name){   
		var arg = name + "=", alen = arg.length, clen = document.cookie.length, i = 0, j = 0;
		while(i < clen){


			j = i + alen;
			if(document.cookie.substring(i, j) == arg) return Cookie.GetCookieVal(j);
			i = document.cookie.indexOf(" ", i) + 1;
			if(i == 0) break;
		}
		return null;
	}
	Cookie.GetCookieVal = function(offset) {   
		var endstr = document.cookie.indexOf (";", offset);
		if(endstr == -1) endstr = document.cookie.length;
		return unescape(document.cookie.substring(offset, endstr));
	}
	Cookie.DeleteCookie = function(name) {   
		var exp = new Date(), cval = Cookie.GetCookie(name);
		exp.setTime(exp.getTime() - 1);
		document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
	}
	Cookie.SetCookie = function(name, value, days) {
		var path = "/", domain = null, secure = false, expires = new Date();
		Cookie.DeleteCookie(name);
		expires.setTime(expires.getTime() + (days * 24 * 60 * 60 * 1000));
		document.cookie = name + "=" + escape(value) + "; expires=" + expires.toGMTString() + ((domain == null) ? "" : ("; domain=" + domain)) + ((path == null) ? "" : ("; path=" + path)) +  ((secure == true) ? "; secure" : "") + "; domain=91.com";
	}
}
Cookie();// JavaScript Document