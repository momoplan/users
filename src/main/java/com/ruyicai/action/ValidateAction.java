package com.ruyicai.action;

import java.io.IOException;
import java.io.PrintWriter;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ruyicai.bean.Tuserinfo;
import com.ruyicai.util.BaseAction;
import com.ruyicai.util.CommonUtil;
import com.ruyicai.util.JSONReslutUtil;
import com.ruyicai.util.LotErrorCode;
import com.ruyicai.util.ResourceBundleUtil;
import com.ruyicai.util.URLEncoder;
import com.ruyicai.util.MD5.PaySign;

/**
 * 
 * 验证用户信息是否已经存在
 *
 */
@SuppressWarnings("serial")
public class ValidateAction extends BaseAction {
	private static Logger logger = Logger.getLogger(ValidateAction.class);
	private Tuserinfo tuserinfo = new Tuserinfo();//用户
	/**
	 * 查询用户名是否存在
	 */
	
	public String changeUserName(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			String username="";
			String isIe=request.getParameter("isIe");
			if(request.getParameter("msie")!=null){
				if(request.getParameter("msie").equals("1") && !"中文".equals(isIe)){
					username = URLEncoder.decode(request.getParameter("username").trim()=="undefined"?"":request.getParameter("username"));
				}else{
					username = request.getParameter("username");
				}
			}else{
				if("中文".equals(isIe)){
					username = request.getParameter("username");
				}else{
					username = URLEncoder.decode(request.getParameter("username").trim()=="undefined"?"":request.getParameter("username"));
				}
			}
			if(username!=username.trim()){
				response.getWriter().print("用户名不可以输入空格，请重新输入！");
				return null;
			}
			String user = JSONReslutUtil.getResultMessage(ResourceBundleUtil.LINKURL + "/tuserinfoes?","json&find=ByUserName&userName="+username+"&state=1","POST"); 
			JSONObject userinfo =  JSONObject.fromObject(user).getJSONObject("value");
			logger.info("根据用户名查到的用户信息是："+userinfo);
			if(userinfo.isEmpty()||userinfo.getInt("state")!=1){
				//用户名不存在
				session.setAttribute("register_username", username);
				response.getWriter().print("0");
				return null;
			}else{
				//用户名存在
				response.getWriter().print("1");
				return null;
			}
		}catch (Exception e) {
			logger.info("查询用户名是否存在出现异常："+e.toString());
			return "error";
		}
	}
	
	//查询昵称是否被占用
	//JSONObject nick = JSONObject.fromObject
	//(JSONReslutUtil.getResultMessage(ResourceBundleUtil.LINKURL + "/tuserinfoes?json&find=ByNickname&","nickname=" + tuserinfo.getNICKNAME(),"POST"));
	public void queryNickName(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try{
			PrintWriter out = response.getWriter();
			//调用接口查询昵称是否被占用,占用则返回1,未被使用则返回0
			String isIe = request.getParameter("isIe");
			String nickname ="";
			if(request.getParameter("msie")!=null){
				if(request.getParameter("msie").equals("1") && !"中文".equals(isIe)){
					nickname = URLEncoder.decode((request.getParameter("nickname")==""||request.getParameter("nickname")=="undefined")?tuserinfo.getNICKNAME():request.getParameter("nickname"));
				}else{
					nickname = request.getParameter("nickname")==""||request.getParameter("nickname")=="undefined"?tuserinfo.getNICKNAME():request.getParameter("nickname");
				}
			}else{
				if("中文".equals(isIe)){
					nickname = request.getParameter("nickname")==""||request.getParameter("nickname")=="undefined"?tuserinfo.getNICKNAME():request.getParameter("nickname");
				}else{
					nickname = URLEncoder.decode((request.getParameter("nickname")==""||request.getParameter("nickname")=="undefined")?tuserinfo.getNICKNAME():request.getParameter("nickname"));
				}
			}
			logger.info("昵称是："+nickname);

			JSONObject jsonObject =  JSONObject.fromObject(JSONReslutUtil.getResultMessage(ResourceBundleUtil.LINKURL + "/tuserinfoes?json&find=ByNickname&","nickname=" +nickname.trim() ,"POST"));
			logger.info("根据昵称查询到的用户信息是："+jsonObject);
			String errorcode=jsonObject.getString("errorCode");
			if(errorcode.equals(LotErrorCode.NEW_OK)){
				Tuserinfo tuser = Tuserinfo.setJson(jsonObject.getJSONObject("value"));
				session.setAttribute("tuserinfo", tuser);
				out.print("1");
			}else{
				 out.print("0");
			}

		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("message", "服务器内部发生故障!");
			logger.error("查询昵称发生错误！"+e.toString());
		}
	}
	
	/**
	 * 注册修改用户信息
	 */
	public String modifyUserInfo(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			Tuserinfo user = getUserInfo();
			logger.info("获取到的用户信息是："+user);
			String cardid = (request.getParameter("cardid")==null || request.getParameter("cardid")=="")?" ":request.getParameter("cardid");
			String verifyCode = (request.getParameter("verifyCode")==null || request.getParameter("verifyCode")=="")?" ":request.getParameter("verifyCode");
			String tel = (request.getParameter("tel")==null || request.getParameter("tel")=="")?" ":request.getParameter("tel");
			String email = (request.getParameter("email")==null || request.getParameter("email")=="")?" ":request.getParameter("email");
			String nickname = "";
			String name = "";
			String randcode = JSONReslutUtil.rand(request);
			logger.info("用户完善用户信息"+user.getUSERNO()+"用户输入的验证码为："+verifyCode
					+"系统短信发送的验证码："+randcode
					);
			if(StringUtils.isBlank(verifyCode)){
				response.getWriter().print("您填写的验证码验证不能为空！");
				return null;
			}
			if(randcode==null||!randcode.equals(verifyCode)){
				response.getWriter().print("您填写的验证码验证不通过，请重试！");
				return null;
			}
			
			String isIe=request.getParameter("isIe");
			if(request.getParameter("msie")!=null){
				if(request.getParameter("msie").equals("1") && !"中文".equals(isIe)){
					nickname = URLEncoder.decode((request.getParameter("nickname").trim()=="undefined"||request.getParameter("nickname").trim()=="")?" ":request.getParameter("nickname"));
					name = URLEncoder.decode((request.getParameter("name").trim()=="undefined" || request.getParameter("name").trim()=="")?" ":request.getParameter("name"));
					
				}else{
					nickname = (request.getParameter("nickname").trim()=="undefined" || request.getParameter("nickname").trim()=="")?" ":request.getParameter("nickname");
					name = (request.getParameter("name").trim()=="undefined" || request.getParameter("name").trim()=="")?" ":request.getParameter("name");
				}
			}else{
				if("中文".equals(isIe)){
					nickname = (request.getParameter("nickname").trim()=="undefined" || request.getParameter("nickname").trim()=="")?" ":request.getParameter("nickname");
					name = (request.getParameter("name").trim()=="undefined" || request.getParameter("name").trim()=="")?" ":request.getParameter("name");
				}else{
					nickname = URLEncoder.decode(request.getParameter("nickname").trim()=="undefined"?"":request.getParameter("nickname"));
					name = URLEncoder.decode((request.getParameter("name").trim()=="undefined" || request.getParameter("name").trim()=="")?" ":request.getParameter("name"));
				}
			}
			
			if("userinfo".equals(request.getParameter("flag"))){
				if(name.indexOf("*")>-1){
					name = user.getNAME();
				}
				if(cardid.indexOf("*")>-1){
					cardid = user.getCERTID();
				}
				if(tel.indexOf("*")>-1){
					tel = user.getMOBILEID();
				}
				if(email.indexOf("*")>-1){
					email = user.getEMAIL();
				}
			}
		
			JSONObject re=JSONObject.fromObject(JSONReslutUtil.getResultMessage( ResourceBundleUtil.LINKURL +"/tuserinfoes/modify?", "name="+name+"&nickname="+nickname
    			+"&certid="+cardid+"&email="+email+"&mobileid="+tel+"&userno="+user.getUSERNO(), "POST"));
			
			logger.info("注册修改用户信息时返回数据是:"+re);
			if(CommonUtil.getBackValue("errorCode",re).equals(LotErrorCode.NEW_OK)){
				//赠送积分
				UserCenter.addUserScore(user.getUSERNO(),null,1,null,null,null);
				JSONObject userinfo = re.getJSONObject("value");
				request.setAttribute("userinfo",userinfo);
				String reerrcode = JSONReslutUtil.changeUserInfo(request);
				if("0".equals(reerrcode)){
					logger.info("修改缓存中的用户成功");
				}else{
					logger.info("修改缓存中用户信息失败");
				}
				response.getWriter().print("success");
				return null;
			}else if(CommonUtil.getBackValue("errorCode",re).equals(LotErrorCode.SJYBD)){
				response.getWriter().print("您填写的手机号已绑定，请更换别的手机号绑定！");
				return null;
			}else if(CommonUtil.getBackValue("errorCode",re).equals(LotErrorCode.YXYBD)){
				response.getWriter().print("邮箱已经绑定！");
				return null;
			}else if(CommonUtil.getBackValue("errorCode",re).equals(LotErrorCode.EMAIL_ERROR)){
				response.getWriter().print("请填写正确的邮箱！");
				return null;
			}else{
				response.getWriter().print("failed");
				return null;
			}
			
		} catch (Exception e) {
			logger.info("注册修改用户信息出现异常"+e.toString());
			try {
				response.getWriter().print("账户信息完善不成功 ！");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "error";
		}
	}
	
	//根据用户名查用户信息
		public void queryUserName(){
			try{
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				//调用接口查询用户是否存在着，存在返回1不存在着返回0
				String isIe = request.getParameter("isIe");
				String username ="";
				if(request.getParameter("msie")!=null){
					if(request.getParameter("msie").equals("1") && !"中文".equals(isIe)){
						username = URLEncoder.decode((request.getParameter("username")==""||request.getParameter("username")=="undefined")?tuserinfo.getNICKNAME():request.getParameter("username"));
					}else{
						username = request.getParameter("username")==""||request.getParameter("username")=="undefined"?tuserinfo.getUSERNAME():request.getParameter("username");
					}
				}else{
					if("中文".equals(isIe)){
						username = request.getParameter("username")==""||request.getParameter("username")=="undefined"?tuserinfo.getUSERNAME():request.getParameter("username");
					}else{
						username = URLEncoder.decode((request.getParameter("username")==""||request.getParameter("username")=="undefined")?tuserinfo.getNICKNAME():request.getParameter("username"));
					}
				}
				JSONObject jsonObject =  JSONObject.fromObject(
						JSONReslutUtil.getResultMessage(
								ResourceBundleUtil.LINKURL + "/tuserinfoes?","json&find=ByUserName&userName=" + username,"POST"));
				logger.info("根据用户名查询到的用户信息是："+jsonObject);
				String errorcode=jsonObject.getString("errorCode");
				if(errorcode.equals(LotErrorCode.NEW_OK)){
					Tuserinfo tuser = Tuserinfo.setJson(jsonObject.getJSONObject("value"));
					session.setAttribute("tuserinfo", tuser);
					 out.print("1");
				}else{
					 out.print("0");
					}			
				
			}catch(Exception e){
//				e.printStackTrace();
				request.setAttribute("message", "服务器内部发生故障!");
				logger.error("查询昵称发生错误！"+e.toString());
			}
		}
	
		/**
		 * 根据用户名查询密码
		 */
		public void getPasswordByUserName(){		
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");

			try {
				String username = URLEncoder.decode((request.getParameter("username")==""||request.getParameter("username")=="undefined")?"":request.getParameter("username"));
				String old_password = request.getParameter("reg_password")==""?"":request.getParameter("reg_password").trim();
				
				if(username!="" ){
					Tuserinfo tuser = (Tuserinfo) session.getAttribute("tuserinfo");
					
					//将输入密码进行md5转换，并与用户密码进行比较
					String old_pass = PaySign.EncoderByMd5(old_password);
					String oldpass = tuser.getPASSWORD();
					logger.info("输入的密码："+old_pass+"^^^^^^^"+"查到用户的密码："+oldpass);
					if(old_pass.equals(oldpass)){
						response.getWriter().print("true");
					}else{
						response.getWriter().print("false");
					}
				};
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		//支付宝用户设置昵称
		public String appsetNickName(){
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			try{
				String isIe = request.getParameter("isIe");
				String nickname ="";
				if(request.getParameter("msie")!=null){
					if(request.getParameter("msie").equals("1") && !"中".equals(isIe)){
						nickname =URLEncoder.decode(request.getParameter("nickname"));
					}else{
						nickname =request.getParameter("nickname");
					}
				}else{
					if("中".equals(isIe)){
						nickname =request.getParameter("nickname");
					}else{
						nickname =URLEncoder.decode(request.getParameter("nickname"));
					}
				}
				logger.info("昵称是：：：："+nickname);
				Tuserinfo user = getUserInfo();
				
				//调用新接口修改昵称
		    	String re= JSONReslutUtil.getResultMessage( ResourceBundleUtil.LINKURL +"/tuserinfoes/modify?", "nickname="+nickname
		    			+"&userno="+user.getUSERNO(), "POST");
		    	
		    		user = Tuserinfo.setJson(JSONObject.fromObject(CommonUtil.getBackValue("value", JSONObject.fromObject(re))));
		    		logger.info("修改昵称后的用户信息是：：："+user);
		    	//session.setAttribute("user", user);
		    	//修改完昵称后，提取出用户名，以便在网站上显示
		    	//String tuser_name = NameUtil.getNameUtil(user);
		    	//session.setAttribute("tuser_name",tuser_name);
		    	//修改成功后，修改缓存中的用户信息
		    	request.setAttribute("userinfo", JSONObject.fromObject(CommonUtil.getBackValue("value", JSONObject.fromObject(re))));
		    	JSONReslutUtil.changeUserInfo(request);
		    	response.getWriter().print("success");
			}catch(Exception e){
//				e.printStackTrace();
				request.setAttribute("messge", "昵称设置失败，请稍候再试！");
				logger.error("设置昵称失败"+e.toString());
				
			}
			return null;
		}
		
	/**
	 * 调用工具方法来获取cookie里的用户信息
	 * @return
	 */
	public Tuserinfo getUserInfo(){
		JSONObject userinfojson = JSONReslutUtil.getUserInfo(request);
		if(!userinfojson.isNullObject() && "0".equals(userinfojson.getString("errorCode"))){
			Tuserinfo userInfo = Tuserinfo.setJson(JSONObject.fromObject(userinfojson.getString("value")));
			logger.info("获取到的用户信息是 is:"+userInfo);
			return userInfo;
		}else{
			logger.info("未获取到用户信息");
			return null;
		}
	}
}
