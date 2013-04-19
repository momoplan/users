package com.ruyicai.action;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ruyicai.action.cooperation.dto_Activity;
import com.ruyicai.bean.Tuserinfo;
import com.ruyicai.util.BaseAction;
import com.ruyicai.util.CommonUtil;
import com.ruyicai.util.Constant;
import com.ruyicai.util.DES;
import com.ruyicai.util.JSONReslutUtil;
import com.ruyicai.util.LotErrorCode;
import com.ruyicai.util.ResourceBundleUtil;
import com.ruyicai.util.URLEncoder;

@SuppressWarnings("serial")
public class RegisterAction extends BaseAction {
	private static ResourceBundle rbint = ResourceBundle.getBundle("ruyicai") ;
	private static Logger logger = Logger.getLogger(RegisterAction.class);
	private static String agencyUrl = rbint.getString("agencyUrl");
	//新的链接地址
	public static final String USER_LINKURL = rbint.getString("linkURL");
	public static final String COOKIES_DOMAIN = rbint.getString("cookiesDomain");
	
	private JSONObject jsonRoot = new JSONObject();
	
	public RegisterAction(){
		super();
	}
	
	/**
	 * 新版本用户注册功能
	 * @return
	 * @throws IOException 
	 */
	public String new_register() throws IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			String username = session.getAttribute("register_username").toString();
			String password = request.getParameter("password");
			String realPass = request.getParameter("realPass");
			String yzm = session.getAttribute("rand").toString();
			String getyzm = request.getParameter("passRegister").trim().toString();
			String sign = request.getParameter("sign")==null?"":request.getParameter("sign");
			String channel = "2";
				if(sign.equals("91ruyicai")){
					channel = "750";
	            }
			/*获取 推广注册参数 这个参数先从用户的请求参数中获取 如果为null，
			 * 说明用户不是用推广连接过来的，于是要从cookie中读取，cookie中如果有userid的值，
			 * 那么是用户二次以上注册账户，怎么默认注册为userid这个推广人员下的 2级推广人员。
			*/
			String userid = request.getParameter("userid").trim().toString();
			if(userid==null||"".equals(userid)||"null".equals(userid)){
				userid = getuseridToCookies(request);
			}
			if(password.trim().equals("") || !password.trim().equals(realPass) || !password.equals(realPass)){
				response.getWriter().print("MMXW");
				return null;
			}
			if(!yzm.equals(getyzm)){
				response.getWriter().print("验证码输入错误，请重新输入！");
				return null;
			}
			String agencyNo = (String)session.getAttribute("agencyNo");
			Tuserinfo user = new Tuserinfo();
				// 执行新接口注册
			String reUser = JSONReslutUtil.getResultMessage(ResourceBundleUtil.LINKURL + "/tuserinfoes?","json&find=ByUserName&userName="+username,"POST");
			String re = "";
			boolean isRegister = true; //用于判断是激活用户还是重新注册用户
			if(reUser!=null){
				JSONObject jsonReUser = JSONObject.fromObject(reUser);
				if(jsonReUser!=null&&!jsonReUser.isNullObject()){
					if("0".equals(jsonReUser.getString("errorCode"))){
						//判断用户是否是关闭用户 0 2 为关闭用户   3 锁定
						if(jsonReUser.getJSONObject("value").getInt("state")==0||jsonReUser.getJSONObject("value").getInt("state")==2){
							Tuserinfo userinfo = Tuserinfo.setJson(jsonReUser.getJSONObject("value"));
							re = JSONReslutUtil.getResultMessage( ResourceBundleUtil.LINKURL +"/tuserinfoes/modify?",
									"userno="+userinfo.getUSERNO()
									+"&accesstype="+Constant.WEB_AGENCYNO 
									+"&agencyno="+CommonUtil.processChannelId(agencyNo==null?"000000":agencyNo)
									+"&certid="+ResourceBundleUtil.CARDID
									+"&state=1"
									+"&info=ruyicai&leave=1"
									+"&nickname="+" "+"&password="+URLEncoder.encode(password)
									+"&type=1", "POST");
							isRegister = false;
						}
						
					}
				}
			}
				if(isRegister){
						re = JSONReslutUtil.getResultMessage(ResourceBundleUtil.LINKURL +"/tuserinfoes/register?",
							"accesstype="+Constant.WEB_AGENCYNO 
							+"&agencyno="+CommonUtil.processChannelId(agencyNo==null?"000000":agencyNo)
							+"&certid="+ResourceBundleUtil.CARDID
							+"&channel="+channel
							+"&info=ruyicai&leave=1"
							+"&nickname="+" "+"&password="+URLEncoder.encode(password)
							+"&userName="+username+"&type=1","POST");
				}
				logger.debug("得到注册接口的信息是："+re);
				JSONObject obj=JSONObject.fromObject(re);
				if(obj.getString("errorCode").equals(LotErrorCode.NEW_OK)){
					//调用积分接口
					user = Tuserinfo.setJson(obj.getJSONObject("value"));
					UserCenter.addUserScore(user.getUSERNO(),null,8,null,null,null);
					JSONObject loginfo = CommonUtil.loginForUserno(user.getUSERNO(), request, response);
					//调用赠送彩金的方法给用户送彩金
					// 赠送彩金接口
					dto_Activity.inDataIsFirstRegistrGift(user.getUSERNO(), user.getUSERNAME());
					if(userid != null){
						//注册成功后 创建代理用户
						//解密 
						DES des = new DES("0123456789ABCDEFG");//自定义密钥
						String parentUserno = des.decrypt(userid);
						String reagencyUrl= JSONReslutUtil.getResultMessage(agencyUrl +"/createUserAgency?","parentUserno="+parentUserno+"&userno="+user.getUSERNO(),"POST");
						JSONObject agencyobj = JSONObject.fromObject(reagencyUrl);
						if(!agencyobj.getString("errorCode").equals("0")){
							  logger.info("注册创建代理用户失败：{}。申请人的userno="+user.getUSERNO()+"上级代理用户编号:"+parentUserno);
						}else{
							//代理成功之后，把链接保存到cookies
							agencyCookie(userid);
						}
					}
					//判断是否输入了如意彩点卡信息
					if(session.getAttribute("cardpwd")!=null){
						this.rycCardCharge(user);
					}
					if("0".equals(loginfo.getString("errorCode"))){
						//让投注金额显示或者隐藏 show为显示
						session.setAttribute("moneyShowType", "hide");
						String jessionid = loginfo.getString("jsessionid");
						pageCookie(jessionid);
						response.getWriter().print("cerSuccess");
						return null;
					}else{
						if(sign.equals("91ruyicai")){
							response.sendRedirect("http://users.ruyicai.com/91login.jsp");
						}
						response.sendRedirect("http://users.ruyicai.com/login.jsp");
						return null;
					}
				}else if( obj.getString("errorCode").equals(LotErrorCode.YHYZC)){
					logger.info("Method:register,"+ user.toString()+ ",注册失败，返回码："+obj.getString("errorCode"));
					response.getWriter().print("用户名已经注册！");
					return null;
				}else{//新接口注册失败
					logger.info("Method:register,"+ user.toString()+ ",注册失败，返回码："+obj.getString("errorCode"));
					response.getWriter().print("网络异常，注册失败，请联系客服！");
					return null;
				}
		}catch (Exception e) {
			logger.info("新注册出现异常："+e.toString());
			response.getWriter().print("网络异常，注册失败，请联系客服！");
			return "error";
		}
	}
	
	
	
	/**
	 * 手机移动WAP用户注册（彩板 and 触屏版）
	 * @return
	 * @throws IOException 
	 */
	public String wapRegister(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repwd = request.getParameter("check");
		String reqUrl = request.getParameter("reqUrl");
		String channel = request.getParameter("channel")==null||request.getParameter("channel").equals("")?"1":request.getParameter("channel");//channel is 1
		String disagree = request.getParameter("disagree")==null?"": request.getParameter("disagree");
		String bindcardid = request.getParameter("bindcardid")==null?"":request.getParameter("bindcardid");
		String type = request.getParameter("type");
		String cardid="" ;String realname="";String goerror = "cerResult";
		if(type.equals("isTouch")){
			goerror = "touchcerResult";
		}
		if(!disagree.equals("on")){
			request.setAttribute("error", "请遵守并同意《用户服务协议》");
			return goerror;
		}
		if(bindcardid.equals("on")){
			 cardid = request.getParameter("cardid");
			 realname = request.getParameter("realname");
		}
		if(isEmpty(reqUrl)){
			reqUrl = "http://wap.ruyicai.com";
		}
		String reResult = VerRegisterParam(username, password,repwd);
		if (isEmpty(reResult) == false) {
			request.setAttribute("error", reResult);
			return goerror;
		}
		Tuserinfo user = new Tuserinfo();
		//注册
		String re ="";
		boolean isRegister = true; //用于判断是激活用户还是重新注册用户
		try {
		   //查询用户是否为关闭用户 state 为 0 或者 2
			String reUser = JSONReslutUtil.getResultMessage(ResourceBundleUtil.LINKURL + "/tuserinfoes?","json&find=ByUserName&userName="+username,"POST");
			if(reUser!=null){
				JSONObject jsonReUser = JSONObject.fromObject(reUser);
				if(jsonReUser!=null&&!jsonReUser.isNullObject()){
					if("0".equals(jsonReUser.getString("errorCode"))){
						//判断用户是否是关闭用户 0 2 为关闭用户
						if(jsonReUser.getJSONObject("value").getInt("state")==0||jsonReUser.getJSONObject("value").getInt("state")==2){
							Tuserinfo userinfo = Tuserinfo.setJson(jsonReUser.getJSONObject("value"));
							re = JSONReslutUtil.getResultMessage( ResourceBundleUtil.LINKURL +"/tuserinfoes/modify?",
									"userno="+userinfo.getUSERNO()
									+"&accesstype=W" 
									+"&certid="+ResourceBundleUtil.CARDID
									+"&state=1"
									+"&info=wap&leave=1"
									+"&nickname="+" "+"&password="+URLEncoder.encode(password)
									+"&type=1", "POST");
							isRegister = false;
						}
					}
				}
			}
			if(isRegister){
				String parameter;
				if(type.equals("isTouch")){
					parameter = "userName=" + username+ "&password=" + password + "&channel="	+ channel;
					if(!cardid.equals("")){
						parameter += "&certid="+cardid	;	
					}
					if(!realname.equals("")){
						parameter += "&name="+realname;
					}
				}else{
				 parameter = "userName=" + username+ "&password=" + password + "&channel="
						+ channel;}
				re = JSONReslutUtil.getResultMessage(ResourceBundleUtil.LINKURL +"/tuserinfoes/register?",
						parameter,"POST");
			}
	    JSONObject obj=JSONObject.fromObject(re);
		if(obj.getString("errorCode").equals(LotErrorCode.NEW_OK)){
			user = Tuserinfo.setJson(obj.getJSONObject("value"));
			JSONObject loginfo = CommonUtil.loginForUserno(user.getUSERNO(), request, response);
			//调用积分接口
			UserCenter.addUserScore(user.getUSERNO(),null,8,null,null,null);
			if("0".equals(loginfo.getString("errorCode"))){
				String jessionid = loginfo.getString("jsessionid");
				pageCookie(jessionid);
				response.sendRedirect(reqUrl);
				return null;
			}else{
				if(type.equals("isTouch")){
					response.sendRedirect("http://users.ruyicai.com/touchwap/login.jsp?reqUrl=http://ruyicai.com/wap/index");
				}
				response.sendRedirect("http://users.ruyicai.com/wap/login.jsp?reqUrl=http://ruyicai.com/wap/index");
				return null;
			}
		}else if(obj.getString("errorCode").equals(LotErrorCode.YHYZC)){
			logger.info("Method:register,"+ user.toString()+ ",注册失败，返回码："+obj.getString("errorCode"));
			request.setAttribute("error", "用户名已经注册！");
			return goerror;
		}else{//新接口注册失败
			logger.info("Method:register,"+ user.toString()+ ",注册失败，返回码："+obj.getString("errorCode"));
			request.setAttribute("error","网络异常，注册失败，请联系客服！");
			return goerror;
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	   public  String VerRegisterParam(String username ,String password ,String rePwd){
	    	 if(isEmpty(username)){
	    		 return "用户名长度至少为4个，最多16个字符！";
	    	 }else if(isEmpty(password)){
	    		 return "密码长度必须是6-16个字符！";
	    	 }else if(!password.equals(rePwd)){
	    		 return  "两次输入密码不相同。";
	    	 }else if(verNameLeanth(username) == false){
	    		 return  "用户名长度不正确,请按提示填写！";
	    	 }else if(verPasswordLeanth(password) == false){
	    		 return  "密码长度不正确,请按提示填写！";
	    	 }
	    	 return "";
	     }
	   public  boolean isEmpty(String str) {
			if (StringUtils.isEmpty(str))
				return true;
			if ("".equals(str.trim()))
				return true;
			if ("null".equals(str.trim()))
				return true;
			return false;
		}
	
	   public  boolean verNameLeanth(String param){
	       int m = param.length();
	       if(m<4 || m >16){
	            return false;
	       }
	       return true;
	     } 
	   /**
	      * 验证密码输入字符的长度判断
	      * @param parm
	      * @return
	      */
	     public  boolean verPasswordLeanth(String param){
	    	 int m = param.length();
	    	 if(6<4 || m >16){
	    		 return false;
	    	 }
	    	 return true;
	     } 
	
	
	
	
	/**
	 * 
	 * 如意彩点卡充值
	 * @return  json
	 * 
	 */
	public String rycCardCharge(Tuserinfo user){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			logger.info("=====如意彩点卡充值开始====="); 
			
			//从页面得到用户输入的卡号和密码
			String cardid = (String) session.getAttribute("cardno");
			String cardpwd = (String) session.getAttribute("cardpwd");
			
			// 3.调用jrtLot接口执行查询
			JSONObject paras = new JSONObject();
			
			//如意彩点卡充值需要的参数是：userno chargetype cardno cardpwd channel subchannel bankid paytype accesstype agencyno
			paras.put("userno", user.getUSERNO());
			paras.put("cardno", cardid);
			paras.put("chargetype", "2");
			paras.put("cardpwd", cardpwd);
			paras.put("channel", (String)request.getSession().getAttribute("CHANNEL"));
			paras.put("subchannel", ResourceBundleUtil.DEFALUT_SUBCHANNEL);
			paras.put("bankid", "ryc001");
			paras.put("paytype", "0300");
			paras.put("accesstype", Constant.WEB_AGENCYNO);
			paras.put("agencyno", "");
			
			
			//调用新接口如意彩点卡充值接口
			
			logger.info("如意彩点卡充值接口地址>>>"+ResourceBundleUtil.MSBANKURL+ "/ruyicaicardcharge!ruyicaiCardCharge?jsonString="+paras.toString());
			
			JSONObject obj = JSONObject.fromObject(JSONReslutUtil.getResultMessage(ResourceBundleUtil.MSBANKURL 
					+ "/ruyicaicardcharge!ruyicaiCardCharge?","jsonString="+URLEncoder.encode(paras.toString()),"POST"));
			
			logger.info("如意彩点卡充值接口返回值>>>"+obj);
			
			if (obj != null) {
				 logger.info("如意彩点卡充值jrtLot返回(如意彩点卡 recharge jrtLot Back):"+obj.toString());
				 jsonRoot.put("flag", true);
				 jsonRoot.put("jsonValue", obj);
				 response.getWriter().print(jsonRoot.toString());
			}
			
		} catch (Exception e) {
			logger.error("如意彩点卡账户充值异常Exception(Account recharge exception):"+e.toString());
			e.printStackTrace();
			return "error";
		}
		return null;
	}
	
	
	/**
	 * 给用户创建一个cookie 如果cookie存在，则修改当前cookie
	 * cookie中存储当前用户的jsessionid 
	 * @param sessionId
	 */
	private void pageCookie(String sessionId){
		String cookieName = "userInfoId";
		Cookie userCookie = new Cookie(cookieName, sessionId);
		userCookie.setPath("/");
		userCookie.setDomain(COOKIES_DOMAIN);
		userCookie.setMaxAge(365*24*60*60);
		response.addCookie(userCookie);
	}
	/**
	 * 给用户创建一个cookie 如果cookie存在，则修改当前cookie
	 * cookie中存储当前用户的jsessionid 
	 * @param sessionId
	 */
	private void agencyCookie(String userid){
		String cookieName = "useragencyId";
		Cookie agencyCookie = new Cookie(cookieName, userid);
		agencyCookie.setPath("/");
		agencyCookie.setDomain(COOKIES_DOMAIN);
		agencyCookie.setMaxAge(30*24*60*60);
		response.addCookie(agencyCookie);
	}
	//h获取cookies中的userid  用于处理只要cookies中有userid , 注册时的账号就成为这个用户的下级用户，
	//如果要是用户的链接中带着新的userid 则覆盖上次保存的id
	public  String getuseridToCookies(HttpServletRequest request){
		String cookieName = "useragencyId";
		//判断cookie是否已经存在
		Cookie[] agencyCookie = request.getCookies();
		if(agencyCookie!=null && agencyCookie.length>0){
			for(int i = 0 ; i < agencyCookie.length ; i++){
				if(agencyCookie[i].getName().equals(cookieName)){
					return agencyCookie[i].getValue();
				}
			}
		}
		return null;
	}
}
