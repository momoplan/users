package com.ruyicai.action.cooperation;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ruyicai.util.JSONReslutUtil;
import com.ruyicai.util.LotErrorCode;
import com.ruyicai.util.ResourceBundleUtil;
import com.ruyicai.util.URLEncoder;
import com.ruyicai.util.MD5.PaySign;

/**
 * 活动相关的功能
 * @author 鞠牧
 *
 */
public class dto_Activity {
	private Logger log = Logger.getLogger(dto_Activity.class);
	//接口交互密钥
	public static final String INTERFACE_KEY="ruyicaiwebgood";
	
	private String userNo;//用户编号
	private String isKey;//活动的唯一标识
	private Long reward;//奖励
	private Integer type;//活动类型，1第一次绑定手机赠送彩金
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getIsKey() {
		return isKey;
	}
	public void setIsKey(String isKey) {
		this.isKey = isKey;
	}
	
	public Long getReward() {
		return reward;
	}
	public void setReward(Long reward) {
		this.reward = reward;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	public static dto_Activity getActivityType(
			String userNo,String isKey,Long reward,Integer type) {
		dto_Activity dat = new dto_Activity();
		dat.setUserNo(userNo);
		dat.setIsKey(isKey);
		dat.setReward(reward);
		dat.setType(type);
		return dat;
	}
	public String toString() {
		return "dto_ActivityType [userNo=" + userNo + ", isKey=" + isKey
				+ ", reward=" + reward + ", type="+ type + "]";
	}
	
	/**
	 * 第一次绑定手机验证通过后送彩金
	 * @param userNo 用户编号
	 * @param isKey 手机号
	 * @return
	 */
	public static void inDataIsFirstRegistrGift(String userNo,String isKey){
		try {
			//活动开关 非0为开启
			if(ResourceBundleUtil.SEND_REGISTER.equals("0")){
				return;
			}
			
			Long reward = Long.parseLong(ResourceBundleUtil.PRESENTATION_MONEY);//分
			Integer type = Integer.parseInt(enum_ActivityType.TYPE_FIRST_REGISTR_GIFT_3.value);
			//调用jrtcms接口 组成json 并获得加密mac 请求jrtcms
			JSONObject obj = JSONObject.fromObject(dto_Activity.getActivityType(userNo, isKey, reward, type));
			
			String md5code = userNo + INTERFACE_KEY;
			String md5 = PaySign.EncoderByMd5(md5code);
			
			if(md5.indexOf("+")>-1){
				md5 = md5.replaceAll("\\+", "_");
			}
			md5 = URLEncoder.encode(md5);
			
			JSONObject reObj = JSONObject.fromObject(JSONReslutUtil.getResultMessage(
					ResourceBundleUtil.SENDURL + "/activity!getActivityByUserNo?",
					"activity="+obj+"&userno=" + userNo + "&interfaceType=002&Mac=" + md5, "POST"));
			if(reObj.get("errorCode")!=null
					&&reObj.getString("errorCode").equals(LotErrorCode.NEW_OK)
					&&reObj.getInt("value")<1){
			
				reObj = JSONObject.fromObject(JSONReslutUtil.getResultMessage(
					ResourceBundleUtil.SENDURL + "/activity!create?",
					"activity="+obj+"&userno=" + userNo + "&interfaceType=002&Mac=" + md5, "POST"));
				if(reObj.getString("errorCode").equals(LotErrorCode.NEW_OK)){
					JSONObject.fromObject(JSONReslutUtil.getResultMessage(
							ResourceBundleUtil.LINKURL + "/taccounts/doDirectChargeProcess?",
							"userno="+userNo
							+"&amt="+reward
							+"&accesstype=B"
							+"&subchannel="+ ResourceBundleUtil.DEFALUT_SUBCHANNEL
							+"&channel=2", "POST"));
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

