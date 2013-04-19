package com.ruyicai.action;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ruyicai.util.BaseAction;
import com.ruyicai.util.CommonUtil;
import com.ruyicai.util.JSONReslutUtil;
import com.ruyicai.util.NameUtil;

@SuppressWarnings("serial")
public class SelectAllAction extends BaseAction {
	private static Logger logger = Logger.getLogger(SelectAllAction.class);
	/**
	 * 
	 * 查询账号余额 原findBalance方法 
	 * 
	 */
	public String ajaxFindAccount(){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			JSONObject user = JSONReslutUtil.getUserInfo(request);
			if(user!=null){
			 user =user.getJSONObject("value");
			 JSONObject obj = CommonUtil.getBalance(user.getString("userno"));
				double deposit_amount = obj.getDouble("balance")-obj.getDouble("freezebalance");
				double valid_amount = obj.getDouble("balance")-obj.getDouble("freezebalance")>=obj.getDouble("drawbalance")?obj.getDouble("drawbalance"):obj.getDouble("balance")-obj.getDouble("freezebalance");
				JSONObject reobj  = new JSONObject();
				reobj.put("deposit_amount", deposit_amount/100);
				reobj.put("valid_amount", valid_amount/100);
				reobj.put("freeze_amout", obj.getDouble("freezebalance")/100);
				reobj.put("userName", user.getString("userName"));
				reobj.put("nickName",NameUtil.getNameUtilJson(user));
				response.getWriter().print(reobj.toString());
				return null; 
			}else{
				logger.error("------------Not fond user's  infromation!----------------");
				return "error";
			}
		} catch (Exception e) {
			logger.error("查询用户余额出异常Exception(Check user balances abnormal):"+e);
			return "error";
		}
	}
}
