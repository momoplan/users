package com.ruyicai.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class JSONReslutUtil {
	private static Logger logger = Logger.getLogger(JSONReslutUtil.class);
	
	/**
	 * @param url 请求的新接口路径
	 * @return GET方式
	 * @throws IOException 
	 */
	public static String getResultMessage(String url) throws IOException{
		try {
			IHttp http = new IHttp();
			logger.info("get方式:url=" + url);
			String re = http.getViaHttpConnection(url);
			return re;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	

	/**
	 * @param url 请求的新接口路径
	 * @param method 请求方式（post）
	 * @return
	 * @throws IOException 
	 */
	public static String getResultMessage(String url, String params, String method) throws IOException{
		String re ="";// http.getViaHttpConnection(url);
		 URL sendurl = new URL(url);
         HttpURLConnection httpUrl=(HttpURLConnection)sendurl.openConnection();
         httpUrl.setDoInput(true);
         httpUrl.setDoOutput(true);
         httpUrl.setUseCaches(false);
         httpUrl.setInstanceFollowRedirects(true);
         httpUrl.setRequestMethod(method);//POST||GET
         logger.info("put-url:"+url+"\n"+httpUrl.getURL()+params+"\n");
        httpUrl.connect();
        httpUrl.getOutputStream().write(params.getBytes("UTF-8"));
        BufferedReader in = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), "UTF-8"));
        while(in.ready())
         re = re+in.readLine();
         httpUrl.disconnect();
         logger.debug("POST SUBMIT TO SERVICE reString: "+ re);
		return re;
	}
	/**
	 * 此方法 用来调用users项目中方法 实现修改缓存中的属性值
	 * 
	 * JSONObject.fromObject(getResultMessage(
	 * usersCenterUrl+"user/center!getUserInfo;jsessionid="+jsessionid+"?","a="+Math.random(),"POST"));
	 */
	public static String changeUserInfo(HttpServletRequest request){
		try {
			String jsessionid = getJsessionIdToCookies(request);
			if(jsessionid != null){
				JSONObject userinfo = (JSONObject) request.getAttribute("userinfo");
				JSONObject js = CommonUtil.editUserInfo(request,userinfo);
			   if(js.getString("errorCode").equals("0")){
				   logger.info("修改用的缓存信息成功");
				   return js.getString("errorCode");   
			   }
			}
		} catch (Exception e) {
            logger.info("修改用的缓存信息失败");
		}
		
		return null;
	}
	
	/**
	 * 获取用户的userInfo 
	 * @param request
	 * @return 发生异常将返回 null  正确取值 将返回一个json对象 用户已经登录 在errorCode 会返回0
	 */
	public static JSONObject getUserInfo(HttpServletRequest request){
		
		String jsessionid = getJsessionIdToCookies(request);
		if(jsessionid != null){
			try {
				return CommonUtil.getUserInfo(request.getSession(true));
			} catch (Exception e) {
				logger.info("登录信息查询出现异常");
			}
		}
		return null;
	}
	/**
	 * 获取验证码
	 * @return
	 */
	public static String rand (HttpServletRequest request){
		String randCode = "";
		JSONObject userInfoObj = JSONReslutUtil.getUserInfo(request);
		if(userInfoObj.getString("errorCode").equals("0")){
		   randCode = (String) request.getSession().getAttribute("randYZM" + userInfoObj.getJSONObject("value").getString("userName"));
		}
		return randCode;
	}
	/**
	 * 获取cookie中的用户的jsessionid 编号
	 * @param request
	 * @return
	 */
	public static String getJsessionIdToCookies(HttpServletRequest request){
		String cookieName = "userInfoId";
		//判断cookie是否已经存在
		Cookie[] userCookies = request.getCookies();
		if(userCookies.length>1){
			for(int i = 0 ; i < userCookies.length ; i++){
				if(userCookies[i].getName().equals(cookieName)){
					return userCookies[i].getValue();
				}
			}
		}
		return null;
	}
}
