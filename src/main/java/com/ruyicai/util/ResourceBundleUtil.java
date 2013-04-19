package com.ruyicai.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 
 * 读取如意彩配置文件
 * 
*/
public class ResourceBundleUtil {
	private static ResourceBundle rbint = ResourceBundle.getBundle("ruyicai") ;
	//新的链接地址
	public static final String LINKURL = rbint.getString("linkURL");
	//如意彩注册手机发送短信内容
	public static final String RYC_PHONEBANDSENDMSG = rbint.getString("ryc_phonebandsendMsg");
	//短信计数的链接地址
	public static final String LINKSMSCOUNTURL = rbint.getString("linksmsCount");
	//设置开关短信地址
	public static String LINKSMGURL = rbint.getString("linkSMGUrl");
	//身份证号码
	public static final String CARDID = rbint.getString("cardId");
	//获取默认的渠道号
	public static String DEFALUT_SUBCHANNEL  = rbint.getString("defalut_subchannel");
	//民生银行接口地址
	public static final String MSBANKURL = rbint.getString("msbankUrl");
	//注册送彩金的开关
	public static final String SEND_REGISTER = rbint.getString("sendRegister");
	//注册送彩金的金额
	public static final String PRESENTATION_MONEY = rbint.getString("presentationMoney");
	//发送手机短信的链接地址
	public static final String SENDURL = rbint.getString("sendUrl");
	//从配置文件中获取返回地址传给后台
	public static Map<String, String> mapAlipayLoginUrl = ResourceBundleUtil.getPropertiesList("alipayLoginUrl");
	/**
	 * 支付宝快捷登录所需参数
	 */
	public static String ALIPAY_ID = rbint.getString("alipay_id");//partner
	public static String ALIPAY_KEY = rbint.getString("alipay_key");//key
	public static String ALIPAY_URL = rbint.getString("alipay_url");//请求支付宝的地址
	public static String ALIPAY_CHANNEL = rbint.getString("alipay_channel");//支付宝的subchannel
	public static String ALIPAY_TYPE = rbint.getString("alipay_type");//支付宝的type
	
	/**
	 * QQ登录所需要的参数
	 */
	public static String QQ_CHANNEL = rbint.getString("qq_channel");
	public static String QQ_TYPE = rbint.getString("qq_type");
	public static String QQ_AUTHORIZATION_CODE_URL = rbint.getString("qq_Authorization_Code_url");
	public static String QQ_ACCESS_TOKEN_URL = rbint.getString("qq_Access_Token_url");
	public static String QQ_GETUSERID_URL = rbint.getString("qq_getUserId_url");
	public static String QQ_GETUSERINFO_URL = rbint.getString("qq_getUserInfo_url");
	public static Map<String, String> MAPQQLOGINURL = ResourceBundleUtil.getPropertiesList("qqLoginUrl");
	public static Map<String, String> QQ_APP_ID = ResourceBundleUtil.getPropertiesList("qq_app_id");
	public static Map<String, String> QQ_APP_KEY = ResourceBundleUtil.getPropertiesList("qq_app_key");
	
	
	
	/**
	 * 返回对应网站所需内容在配置文件获取的方法
	 * 包括渠道号、Channel、充值返回地址
	 * @return
	 */
	public static Map<String, String> getPropertiesList(String prorertiesName){
		//读取配置文件中的内容
        String[] prorertiesNames = rbint.getString(prorertiesName).split(",");
		Map<String, String> map = new HashMap<String, String>();
		//循环存入map中
		for(String prorName : prorertiesNames){
			String[] retUrl = prorName.split("-");
		    map.put(retUrl[0], retUrl[1]);
		}
		return map;
	}
}
