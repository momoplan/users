package com.ruyicai.action;

import java.io.IOException;
import java.util.ResourceBundle;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.meetup.memcached.MemCacheInvoke;
import com.ruyicai.util.BaseAction;
import com.ruyicai.util.CommonUtil;
import com.ruyicai.util.ErrorCode;
import com.ruyicai.util.JSONReslutUtil;
import com.ruyicai.util.MessageUtil;
import com.ruyicai.util.MD5.PaySign;

public class UserCenter extends BaseAction {
	private static Logger logger = Logger.getLogger(UserCenter.class);
	private static ResourceBundle rbint = ResourceBundle.getBundle("ruyicai") ;
	//新的链接地址
	public static final String USER_LINKURL = rbint.getString("linkURL");
	public static final String LINKSCOREURL = rbint.getString("linkSCOREUrl");
	public static final String COOKIES_DOMAIN = rbint.getString("cookiesDomain");
	
	
	/**
	 *  用户通过提交表单提交登录
	 *  @return formError
	 */
	public String loginForm(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String sign = request.getParameter("sign")==null?"":request.getParameter("sign");
		String gotoValue = "loginError"; // 用户登录后要跳转的地址，如果中间出现错误，则跳转到错误的地址。
		if(sign.equals("91ruyicai")){
			gotoValue = "91loginError";
		}
		//获取用户名的登录名和密码
		if(request.getMethod().equals("GET")){
			request.setAttribute("error", MessageUtil.TIAW_login_Fail);
			return gotoValue;
		}
		
		String userName = StringUtils.isBlank(request.getParameter("userName"))?"":request.getParameter("userName");
		String pass = StringUtils.isBlank(request.getParameter("password"))?"":request.getParameter("password");
		request.setAttribute("userName", userName);
		logger.info("用户登录：username="+userName+"pssword="+pass);
		JSONObject user = new JSONObject();
		//验证用户信息输入是否正确
		if(userName.isEmpty()||	pass.isEmpty()){
			request.setAttribute("error", MessageUtil.TIAW_login_Fail);
			return gotoValue;
		}
		
		//获取验证码并比对
		String input = request.getParameter("password1");
		String rand = (String) request.getSession().getAttribute("rand");//session中的验证码
		if(input!=null){
			if(!rand.equals(input)){
				request.setAttribute("error", MessageUtil.TIAW_login_RandCodeError);
				return gotoValue;
			}
		}
		request.getSession().removeAttribute("rand");
		try {
			//根据用户名查询用户信息
			user = JSONObject.fromObject(JSONReslutUtil.getResultMessage(USER_LINKURL + "/tuserinfoes?","json&find=ByUserName&userName=" + userName,"POST"));
			//判断是否通过用户名能够找到这个用户对象，如果没有找到，则通过mobileid或者Email来找到这个用户，此处用于处理老用户的数据兼容
			logger.info("登录查询用户信息返回结果："+user);
			if(!user.isNullObject()&&ErrorCode.OK.value.equals(user.getString("errorCode"))){
				if(PaySign.EncoderByMd5(pass).equals(user.getJSONObject("value").getString("password"))){
				}else{
					request.setAttribute("error",MessageUtil.TIAW_login_Fail);
					return gotoValue;
				}
			}
			user = user.getJSONObject("value");
			//判断用户的状态，给予不同的提示，如果state为1的时候，为正常用户
			if(user.getInt("state")==0){
				request.setAttribute("error", MessageUtil.TIAW_login_ClosedUser);
				return gotoValue;
			}else if(user.getInt("state")==2){
				request.setAttribute("error", MessageUtil.TIAW_login_WaitActivationUser);
				return gotoValue;
			}
			request.getSession().setAttribute("userno", user.getString("userno"));
			//将查到的用户信息存入缓存
			try {
				MemCacheInvoke.mcc.set("Tuserinfo_"+user.getString("userno"), user.toString(), (24*3600));
			} catch (Exception e) {
				logger.info("MemCache connection refused: connect");
			}
			String sessionid =request.getSession().getId();
			CommonUtil.pageCookie(sessionid,request,response);
			logger.info("比较userno与userinfoid的关系："+user.getString("userno")+"========="+sessionid);
			//调用积分接口
			addUserScore(user.getString("userno"),null,8,null,null,null);
			//获取用户从哪个页面传入的地址
			String reqUrl = request.getParameter("reqUrl");
			if(reqUrl!=null && !reqUrl.equals("")){
				if("/login.jsp".equals(reqUrl)){
					request.setAttribute("message", "登陆成功！");
					return gotoValue;
				}else{
					response.sendRedirect(reqUrl);
					return null;
				}
			} else{
				response.sendRedirect("http://www.ruyicai.com/");
				return null;
			}
			
		} catch (Exception e) {
			request.setAttribute("error", MessageUtil.ERROR_Message);
			e.printStackTrace();
			return gotoValue;
		}
	}
	/**
	 *  用户通过提交表单提交登录
	 *  @return formError
	 */
	public String loginTouch(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//获取用户名的登录名和密码
		try {
		if(request.getMethod().equals("GET")){
			request.setAttribute("message",MessageUtil.TIAW_login_Fail);
			return "touchloginError";
		}
		String userName = StringUtils.isBlank(request.getParameter("username"))?"":request.getParameter("username");
		String pass = StringUtils.isBlank(request.getParameter("password"))?"":request.getParameter("password");
		JSONObject user = new JSONObject();
		//验证用户信息输入是否正确
		if(userName.isEmpty()||	pass.isEmpty()){
			request.setAttribute("message",MessageUtil.TIAW_login_Fail);
			return "touchloginError";
		}
			//根据用户名查询用户信息
			user = JSONObject.fromObject(JSONReslutUtil.getResultMessage(USER_LINKURL + "/tuserinfoes?","json&find=ByUserName&userName=" + userName,"POST"));
			//判断是否通过用户名能够找到这个用户对象，如果没有找到，则通过mobileid或者Email来找到这个用户，此处用于处理老用户的数据兼容
			if(!user.isNullObject()&&ErrorCode.OK.value.equals(user.getString("errorCode"))){
				if(PaySign.EncoderByMd5(pass).equals(user.getJSONObject("value").getString("password"))){
					user = user.getJSONObject("value");
				}
			}
			if(user.isNullObject()==true){
				request.setAttribute("message","用户不存在，请先注册");
				return "touchloginError";
			}
			//判断用户的状态，给予不同的提示，如果state为1的时候，为正常用户
			if(user.getInt("state")==0){
				request.setAttribute("message", MessageUtil.TIAW_login_ClosedUser);
				return "touchloginError";
			}else if(user.getInt("state")==2){
				request.setAttribute("message",MessageUtil.TIAW_login_WaitActivationUser);
				return "touchloginError";
			}
			request.getSession().setAttribute("userno", user.getString("userno"));
			//将查到的用户信息存入缓存
			try {
				MemCacheInvoke.mcc.set("Tuserinfo_"+user.getString("userno"), user.toString(), (24*3600));
			} catch (Exception e) {
				logger.info("MemCache connection refused: connect");
			}
			CommonUtil.pageCookie(request.getSession(true).getId(),request,response);
			
			//调用积分接口
			addUserScore(user.getString("userno"),null,8,null,null,null);
			//获取用户从哪个页面传入的地址
			String reqUrl = request.getParameter("reqUrl");
			if(reqUrl!=null && !reqUrl.equals("")){
				response.sendRedirect(reqUrl);
				return null;
			} else{
				response.sendRedirect("http://wap.ruyicai.com/");
				return null;
			}
			
		} catch (Exception e) {
			request.setAttribute("message",MessageUtil.ERROR_Message);
			e.printStackTrace();
			return "touchloginError";
		}
		
	}
	
	
	/**
	 *  用户通注册时，使用userno 进行USERCENTER的登记注册
	 *  POST 方式提交请求 http://users.ruyicai.com/user/center!loginForUserno   参数为userno
	 *  @return JsonObj
	 */
	public void loginForUserno(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject reJsonObj = new JSONObject();
		try {
			if(request.getMethod().equals("GET")){//如果用户通过get方式请求此接口，则返回503没有权限
				reJsonObj.put("errorCode", "503");
				response.getWriter().print(reJsonObj.toString());
			}
				//获取参数userno
				String userno = request.getParameter("userno")==null?"":request.getParameter("userno");
				if(userno.isEmpty()){//如果userno为空，则返回501
					reJsonObj.put("errorCode", "501");
					response.getWriter().print(reJsonObj.toString());
					return;
				}
				//获取用户在库中的数据 并保存到session memCache 和cookie
				String userInfoStr = JSONObject.fromObject(
						JSONReslutUtil.getResultMessage(USER_LINKURL + 
								"/tuserinfoes?","json&find=ByUserno&userno="+userno,"POST"))
								.getJSONObject("value").toString();
				request.getSession().setAttribute("userno", userno);
				try{
					MemCacheInvoke.mcc.set("Tuserinfo_"+userno, userInfoStr, (24*3600));
				} catch (Exception e) {
					e.printStackTrace();
				}
				CommonUtil.pageCookie(request.getSession(true).getId(),request,response);
				reJsonObj.put("errorCode", "0");
				reJsonObj.put("jsessionid", request.getSession(true).getId());
				response.getWriter().print(reJsonObj.toString());
		} catch (IOException e) {
			try {
				reJsonObj.put("errorCode", "500");
				response.getWriter().print(reJsonObj.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过http请求可以获得当前用户的登录信息 只能获得userno
	 * 例子：http://users.ruyicai.com/user/center!getUserNo;jsessionid=｛cookie中存储的值｝?a=随机数
	 * http://users.ruyicai.com/user/center!getUserCookie;jsessionid=D051904206F696879C5C507444217FFD?a=3493537239827349
	 * @param 返回值为JSONObject对象{errorCode:"0",value:{用户编号}} 如果失败则返回100002 或者500
	 */
	
	public void getUserNo(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject reJsonObj = new JSONObject();
		try {
			if(session.getAttribute("userno")!=null){//判断session中是否存在当前用户的对象
				reJsonObj.put("errorCode", "0");
				reJsonObj.put("value", session.getAttribute("userno").toString());
				response.getWriter().print(reJsonObj.toString());
			}else{//如果不存在则返回100002告知用户不存在并需要登录
				reJsonObj.put("errorCode", ErrorCode.UserMod_UserNotExists.value);
				reJsonObj.put("value", "{}");
				response.getWriter().print(reJsonObj.toString());
			}
		} catch (IOException e) {//异常则返回500
			reJsonObj.put("errorCode", ErrorCode.ERROR.value);
			reJsonObj.put("value", "{}");
			try {
				response.getWriter().print(reJsonObj.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * 通过http请求可以获得当前用户的登录信息 只能获得user详情
	 * 例子：http://192.168.122:8080/user/center!getUserInfo;jsessionid=｛cookie中存储的值｝?a=随机数
	 * http://users.ruyicai.com/user/center!getUserCookie;jsessionid=D051904206F696879C5C507444217FFD?a=3493537239827349
	 * @param 返回值为JSONObject对象{errorCode:"0",value:{用户对象}} 如果失败则返回100002 或者500
	 */
	public void getUserInfo(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject reJsonObj = new JSONObject();
		String userno = (String) session.getAttribute("userno");
		try {
			if(userno!=null){//判断session中是否存在当前用户的对象
				reJsonObj.put("errorCode", "0");
				Object userInfoObj = null;
				try{
					userInfoObj = MemCacheInvoke.mcc.get("Tuserinfo_"+userno);
				}catch (Exception e) {
					logger.info("MemCache connection refused: connect");
				}
				String userInfoStr = "";
				if(userInfoObj!=null){
					userInfoStr = userInfoObj.toString();//从系统缓存中获取用户信息 
				}else{
					userInfoStr = JSONObject.fromObject(
							JSONReslutUtil.getResultMessage(USER_LINKURL + 
									"/tuserinfoes?json&find=ByUserno&userno="+userno))
									.getJSONObject("value").toString();
					try{
						MemCacheInvoke.mcc.add("Tuserinfo_"+userno, userInfoStr, (24*3600));
					} catch (Exception e) {
						logger.info("MemCache connection refused: connect");
					}
				}
				reJsonObj.put("value", JSONObject.fromObject(userInfoStr));
				
				response.getWriter().print(reJsonObj.toString());
			}else{//如果不存在则返回100002告知用户不存在并需要登录
				reJsonObj.put("errorCode", ErrorCode.UserMod_UserNotExists.value);
				reJsonObj.put("value", "{}");
				response.getWriter().print(reJsonObj.toString());
			}
		} catch (IOException e) {//异常则返回500
			reJsonObj.put("errorCode", ErrorCode.ERROR.value);
			reJsonObj.put("value", "{}");
			try {
				response.getWriter().print(reJsonObj.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 通过http请求可以修改当前用户的信息
	 * 例子：http://users.ruyicai.com/user/center!editUserInfo;jsessionid=｛cookie中存储的值｝?a=随机数
	 * http://users.ruyicai.com/user/center!getUserCookie;jsessionid=D051904206F696879C5C507444217FFD?a=3493537239827349
	 * @param 返回值为JSONObject对象{errorCode:"0",value:{用户对象}} 如果失败则返回100002 或者500
	 */
	public void editUserInfo(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String JsonUserInfo = request.getParameter("userinfo")==null?"":request.getParameter("userinfo");
		JSONObject reJsonObj = null;
		if(!JsonUserInfo.isEmpty()){
			try {
				reJsonObj = JSONObject.fromObject(JsonUserInfo);
				//将查到呃用户信息存入缓存
				try{
					MemCacheInvoke.mcc.set("Tuserinfo_"+reJsonObj.getString("userno"), reJsonObj.toString(), (24*3600));
				} catch (Exception e) {
					e.printStackTrace();
				}
				reJsonObj.put("errorCode", "0");
				reJsonObj.put("value", "{}");
				response.getWriter().print(reJsonObj.toString());
			} catch (IOException e) {//异常则返回500
				reJsonObj.put("errorCode", ErrorCode.ERROR.value);
				reJsonObj.put("value", "{}");
				try {
					response.getWriter().print(reJsonObj.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	/**
	 * 通过http请求可以修改当前用户的信息
	 * 例子：http://users.ruyicai.com/user/center!logout;jsessionid=｛cookie中存储的值｝?a=随机数
	 * http://users.ruyicai.com/user/center!logout;jsessionid=D051904206F696879C5C507444217FFD?a=3493537239827349
	 */
	public void logout(){
		session.removeAttribute("userInfoId");
		session.invalidate();
	}
	/**
	 * 用户积分兑换
	 * @return 
	 * @throws IOException 
	 */
	
	public static void addUserScore(String userno, String  bussinessId,
			Integer  scoreType, String buyAmt,String totalAmt, String giveScore ) {
		try{
				String url ="userno=" + userno+"&scoreType="+scoreType;
				if(bussinessId!=null){
					url+="&bussinessId"+bussinessId;
				}else if(buyAmt!=null){
					url+="&buyAmt"+buyAmt;
				}else if(totalAmt!=null){
					url+="&totalAmt"+totalAmt;
				}else if(giveScore!=null){
					url+="&giveScore"+giveScore;
				}
				JSONObject obj=JSONObject.fromObject(JSONReslutUtil.getResultMessage(LINKSCOREURL + "/addTuserinfoScore?",url,"POST"));
				logger.info("recivePresent 返回结果"+obj);
		}catch(Exception e){
			logger.error("获取用户积分出先异常exception(get user score return excpetion):"+e.toString());
	}
	}
}
