package com.ruyicai.action;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.meetup.memcached.MemCacheInvoke;
import com.ruyicai.action.cooperation.dto_Activity;
import com.ruyicai.pojo.PhoneInfo;
import com.ruyicai.util.BaseAction;
import com.ruyicai.util.CommonUtil;
import com.ruyicai.util.ErrorCode;
import com.ruyicai.util.FinalVar;
import com.ruyicai.util.JSONReslutUtil;
import com.ruyicai.util.MessageUtil;
import com.ruyicai.util.ResourceBundleUtil;
import com.ruyicai.util.URLEncoder;
import com.ruyicai.util.MD5.PaySign;
import com.ruyicai.web.service.PhoneInfoService;

@SuppressWarnings("serial")
// http://192.168.99.122:8080/user/phonebind!sendMsgForWapPhoneBind?new_phone=15210900137
public class PhoneBindAction extends BaseAction {
	private static Logger logger = Logger.getLogger(PhoneBindAction.class);
	private static ResourceBundle rbint = ResourceBundle.getBundle("ruyicai");
	// 新的链接地址
	public static final String USER_LINKURL = rbint.getString("linkURL");
	public static final String INTERFACE_KEY = "ruyicaiwebgood";
	
	
	private PhoneInfoService phoneInfoService;
	public PhoneInfoService getPhoneInfoService() {
		return phoneInfoService;
	}

	public void setPhoneInfoService(PhoneInfoService phoneInfoService) {
		this.phoneInfoService = phoneInfoService;
	}

	public void userBindPhone() {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject reJsonObj = new JSONObject();
		try {
			String mobileid = request.getParameter("mobileid");
			String userno = request.getParameter("userno");
			String username = request.getParameter("username");
			String rand = request.getParameter("rand");
			String oldrand = (String) request.getSession().getAttribute(
					"randYZM" + username);

			if (rand == null) {
				reJsonObj.put("errorCode", ErrorCode.UserRand_Error.value);
				reJsonObj.put("message", ErrorCode.UserRand_Error.memo);
				reJsonObj.put("value", "{}");
				response.getWriter().print(reJsonObj.toString());
			} else if (!rand.equals(oldrand)) {
				reJsonObj.put("errorCode", ErrorCode.UserRand_WriteError.value);
				reJsonObj.put("message", ErrorCode.UserRand_WriteError.memo);
				reJsonObj.put("value", "{}");
				response.getWriter().print(reJsonObj.toString());
			} else {
				// 调用新接口修改用户信息
				String re = JSONReslutUtil.getResultMessage(
						ResourceBundleUtil.LINKURL + "/tuserinfoes/modify?",
						"mobileid=" + mobileid + "&userno="+userno, "POST");
				JSONObject update = JSONObject.fromObject(re);

				if (!update.isNullObject()
						&& CommonUtil.getBackValue("errorCode", update).equals(
								"0")) {

					// 调用绑定手机号码成功赠送3元彩金
					dto_Activity.inDataIsFirstRegistrGift(userno, mobileid);

					// 修改成功后,更新缓存中的用户信息
					JSONObject userinfo = update.getJSONObject("value");

					reJsonObj.put("errorCode", "0");
					reJsonObj
							.put("message", MessageUtil.TIAW_phoneBand_Success);
					reJsonObj.put("value", userinfo);
					response.getWriter().print(reJsonObj.toString());
				} else if (!update.isNullObject()
						&& CommonUtil.getBackValue("errorCode", update).equals(
								"100003")) {
					reJsonObj.put("errorCode",
							ErrorCode.UserMod_MobileidBind.value);
					reJsonObj.put("message",
							ErrorCode.UserMod_MobileidBind.memo);
					reJsonObj.put("value", "{}");
					response.getWriter().print(reJsonObj.toString());
				} else {
					reJsonObj.put("errorCode",
							ErrorCode.UserMod_MobileidBindError.value);
					reJsonObj.put("message",
							ErrorCode.UserMod_MobileidBindError.memo);
					reJsonObj.put("value", "{}");
					response.getWriter().print(reJsonObj.toString());
				}
			}
		} catch (Exception e) {// 异常则返回500
			reJsonObj.put("errorCode", ErrorCode.ERROR.value);
			reJsonObj.put("message", ErrorCode.ERROR.memo);
			reJsonObj.put("value", "{}");
			try {
				response.getWriter().print(reJsonObj.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 手机绑定验证功能
	 * @return 手机绑定成功页面
	 */
	
	public void phoneBandCheck(){
		try{
			logger.info("====如意彩手机绑定验证开始====");
			// 1.得到用户信息
			String sessionid = session.getId();
			String usermobile=request.getParameter("usermobile");
			logger.info("发送给用户的相关信息是jsessionid:"+sessionid+";手机号码："+usermobile);
			if(usermobile==null||usermobile.equals("")){
				response.getWriter().print(MessageUtil.TIAW_changeUserinfo_mobile_NULL);
			}else if(CommonUtil.verifyMobileId(usermobile)!=true){
				response.getWriter().print(MessageUtil.TIAW_login_MobilePatternError);
			}else{
				JSONObject object = sendMsgToPhoneBind(usermobile);
				String errorCode = object.getString("errorCode"); 
				logger.info("请求users中的sendMsgForPhoneBind返回的errorCode是："+errorCode);
				if(errorCode.equals("0")||errorCode=="0"){
					String sendnum =  object.getString("value");
					if(Integer.parseInt(sendnum)>=3){
						response.getWriter().print("抱歉 ！每天一个手机号只发送3次短信，已经向该手机号发送3次短信！");
			    		logger.debug("抱歉 ！每天一个手机号只发送3次短信，已经向该手机号发送3次短信！");
					}else{
					response.getWriter().print("isok");}
				}else{
				response.getWriter().print("抱歉 ！验证码发送失败，请您重新发送！");}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("手机绑定验证出异常exception(E-mail to send verification code to bind abnormal):"+e.toString());
		}
	}
	/**
     * 发送短信验证码到手机
     */
	public JSONObject sendMsgToPhoneBind(String usermobile) {

		JSONObject reJsonObj = new JSONObject();
		JSONObject userInfoObj = JSONReslutUtil.getUserInfo(request);
		if(!userInfoObj.getString("errorCode").equals("0")){
			return userInfoObj;
		}
		try {
			String rand = CommonUtil.getRandYZM();
			// 3.得到发送到用户的手机短信内容
			String mes = new String(
					ResourceBundleUtil.RYC_PHONEBANDSENDMSG
							.getBytes("ISO-8859-1"),
					"utf-8");
			String newMes = mes.replace("{mes}", rand);

			// 4.设置请求的mac值，若mac中存在有“+”将其改成“_”
			String mac1;
			try {
				mac1 = PaySign.EncoderByMd5(usermobile + "3gcaipiao");
				if (mac1.indexOf("+") > -1) {
					mac1 = mac1.replaceAll("\\+", "_");
				}
				String mac = URLEncoder.encode(mac1);
				// 5.调用jrtcms中的基数接口得到当前这个手机号码一共发送短信多少次
				int count = this.create(usermobile, mac, "0");
				int countToInt = 0;
				if (count != 0 && !"0".equals(count)) {
					countToInt = count;
				}
				// 6.若发送次数少于三次调用发送短信的接口发送短信并将发送次数返回
				if (countToInt < 3) {
					// 得到手机发送信息接口的信息
					String md51 = PaySign.EncoderByMd5(userInfoObj.getJSONObject("value")
							.getString("mobileid") + INTERFACE_KEY);
					if (md51.indexOf("+") > -1) {
						md51 = md51.replaceAll("\\+", "_");
					}
					JSONObject phoneMessage = JSONObject
							.fromObject(JSONReslutUtil
									.getResultMessage(ResourceBundleUtil.LINKSMGURL
													+ "sms/send?", "mobileIds="
													+ usermobile + "&text="
													+ URLEncoder.encode(newMes)
													+ "&channelName=", "POST"));
				}
				request.getSession().setAttribute(
						"randYZM" + userInfoObj.getJSONObject("value").getString("userName"), rand);
				CommonUtil.pageCookie(request.getSession(true).getId(), request,
						response);
				reJsonObj.put("errorCode", "0");
				reJsonObj.put("value", countToInt);
				reJsonObj.put("randCode", rand);
				return reJsonObj;
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {// 异常则返回500
			reJsonObj.put("errorCode", ErrorCode.ERROR.value);
			reJsonObj.put("value", "{}");
			logger.error("手机短信发送产生异常(SMS send an exception):"
					+ e.toString());
		    return reJsonObj;
		}
		return reJsonObj;
	}
	
    /**
     * 发送短信验证码到手机
     */
	public void sendMsgForPhoneBind() {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject reJsonObj = new JSONObject();
		try {
			Object userInfoObj = MemCacheInvoke.mcc.get("Tuserinfo_"+session.getAttribute(
					"userno").toString());
			JSONObject userInfoStr = new JSONObject();
			if (userInfoObj != null) {
				userInfoStr = JSONObject.fromObject(userInfoObj);// 从系统缓存中获取用户信息
			} else {
				userInfoStr = JSONObject.fromObject(
						JSONReslutUtil.getResultMessage(USER_LINKURL
								+ "/tuserinfoes?json&find=ByUserno&userno="
								+ session.getAttribute("userno").toString()))
						.getJSONObject("value");
				try {
					MemCacheInvoke.mcc.add("Tuserinfo_"+session.getAttribute("userno")
							.toString(), userInfoStr, (24 * 3600));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			String usermobile = request.getParameter("new_phone");
			String rand = (String) request.getSession().getAttribute(
					"randYZM" + userInfoStr.getString("userName"));
			// 2.如果session中不存在验证码就生成一次并存入session中
			if (rand == null || rand.equals("")) {
				rand = CommonUtil.getRandYZM();
				request.getSession().setAttribute(
						"randYZM" + userInfoStr.getString("userName"), rand);
				CommonUtil.pageCookie(request.getSession(true).getId(), request,
						response);

			}
			// 3.得到发送到用户的手机短信内容
			String mes = new String(
					ResourceBundleUtil.RYC_PHONEBANDSENDMSG
							.getBytes("ISO-8859-1"),
					"utf-8");
			String newMes = mes.replace("{mes}", rand);

			// 4.设置请求的mac值，若mac中存在有“+”将其改成“_”
			String mac1;
			try {
				mac1 = PaySign.EncoderByMd5(usermobile + "3gcaipiao");

				if (mac1.indexOf("+") > -1) {
					mac1 = mac1.replaceAll("\\+", "_");
				}
				String mac = URLEncoder.encode(mac1);
				// 5.调用jrtcms中的基数接口得到当前这个手机号码一共发送短信多少次
				int count = this.create(usermobile, mac, "0");
				int countToInt = 0;
				if (count != 0 && !"0".equals(count)) {
					countToInt = count;
				}
				// 6.若发送次数少于三次调用发送短信的接口发送短信并将发送次数返回
				if (countToInt < 3) {
					// 得到手机发送信息接口的信息
					String md51;
					md51 = PaySign.EncoderByMd5(userInfoStr
							.getString("mobileid") + INTERFACE_KEY);

					if (md51.indexOf("+") > -1) {
						md51 = md51.replaceAll("\\+", "_");
					}
					JSONObject phoneMessage = JSONObject
							.fromObject(JSONReslutUtil
									.getResultMessage(
											ResourceBundleUtil.LINKSMGURL
													+ "sms/send?", "mobileIds="
													+ usermobile + "&text="
													+ URLEncoder.encode(newMes)
													+ "&channelName=", "POST"));
					request.getSession().setAttribute("updateMobileInfo",
							usermobile);
					CommonUtil.pageCookie(request.getSession(true).getId(),
							request, response);
				}
				request.getSession().setAttribute("sendNum_phone", countToInt);
				CommonUtil.pageCookie(request.getSession(true).getId(), request,
						response);

				reJsonObj.put("errorCode", "0");
				reJsonObj.put("value", countToInt);
				reJsonObj.put("randCode", rand);
				response.getWriter().print(reJsonObj.toString());
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {// 异常则返回500
			reJsonObj.put("errorCode", ErrorCode.ERROR.value);
			reJsonObj.put("value", "{}");
			try {
				response.getWriter().print(reJsonObj.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
				logger.error("手机短信发送产生异常(SMS send an exception):"
						+ e.toString());
			}
		}
	}

	/**
	 * 手机绑定发送验证码，验证发送次数
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public int create(String usermobile, String mac, String Rules)
			throws NoSuchAlgorithmException, IOException {
		String macFrom = URLEncoder.decode(mac);
		String phoneCode = usermobile;
		Integer rules = Integer.valueOf(Rules == "" ? "0" : Rules);
		int flag = 3;
		if (macFrom != null && !"".equals(macFrom) && phoneCode != null
				&& !"".equals(phoneCode)) {
			String phonecode = phoneCode + "3gcaipiao";
			String macChack = PaySign.EncoderByMd5(phonecode);
			if (macChack.indexOf("+") > -1) {
				macChack = macChack.replaceAll("\\+", "_");
			}
			if (macChack.equals(macFrom)) {
				logger.info("mac一致开始传送");
				PhoneInfo phoneInfo = new PhoneInfo();
				phoneInfo.setPhoneCode(Long.valueOf(phoneCode));
				phoneInfo.setRules(rules);
				flag = phoneInfoService.queryPhoneInfoCount(phoneInfo);
				if (flag < 3) {
					String code = phoneInfoService.add(phoneInfo);
					if (FinalVar.INSERT_SUCCESS.equals(code)) {
						request.setAttribute("msg", "手机信息添加成功");
						logger.info("手机计数添加成功");
					} else {
						logger.info("手机计数添加失败");
					}
				} else {
					logger.info("手机信息每天超过3次则发送失败");
				}
			} else {
				logger.info("mac不一致传送失败");
			}
		} else {
			logger.info("mac地址非法");
		}
		return flag;
	}

	public void sendMsgForWapPhoneBind() {
		response.setCharacterEncoding("utf-8");
		response.setContentType("textml;charset=utf-8");
		JSONObject reJsonObj = new JSONObject();
		try {
			String usermobile = request.getParameter("new_phone");
			String rand = CommonUtil.getRandYZM();
			String mes = new String(
					ResourceBundleUtil.RYC_PHONEBANDSENDMSG
							.getBytes("ISO-8859-1"),
					"utf-8");
			String newMes = mes.replace("{mes}", rand);
			JSONObject phoneMessage = JSONObject.fromObject(JSONReslutUtil
					.getResultMessage(ResourceBundleUtil.LINKSMGURL
							+ "sms/send?", "mobileIds=" + usermobile + "&text="
							+ URLEncoder.encode(newMes) + "&channelName=",
							"POST"));
			logger.info(phoneMessage);
			if (ErrorCode.OK.equals(phoneMessage.getString("errorCode"))) {
				reJsonObj.put("errorCode", "0");
				reJsonObj.put("value", rand);
			} else {
				reJsonObj.put("errorCode", phoneMessage.getString("errorCode"));
				reJsonObj.put("value", rand);
			}
			response.getWriter().print(reJsonObj.toString());

		} catch (IOException e) {// 异常则返回500
			reJsonObj.put("errorCode", ErrorCode.ERROR.value);
			reJsonObj.put("value", "{}");
			try {
				response.getWriter().print(reJsonObj.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
				logger.error("手机短信发送产生异常(SMS send an exception):"
						+ e.toString());
			}
		}
	}

	public void sendMsgForWap() {
		response.setCharacterEncoding("utf-8");
		response.setContentType("textml;charset=utf-8");
		JSONObject reJsonObj = new JSONObject();
		try {

			String usermobile = request.getParameter("usermobile");
			String text = request.getParameter("text");
			JSONObject phoneMessage = JSONObject
					.fromObject(JSONReslutUtil
							.getResultMessage(ResourceBundleUtil.LINKSMGURL
									+ "sms/send?", "mobileIds=" + usermobile
									+ "&text=" + URLEncoder.encode(text)
									+ "&channelName=", "POST"));

			reJsonObj.put("errorCode", "0");
			response.getWriter().print(reJsonObj.toString());

		} catch (IOException e) {// 异常则返回500
			reJsonObj.put("errorCode", ErrorCode.ERROR.value);
			try {
				response.getWriter().print(reJsonObj.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
				logger.error("手机短信发送产生异常:" + e.toString());
			}
		}
	}

}
