package com.ruyicai.action.cooperation;

public enum enum_ActivityType {

	TYPE_FIRST_REGISTR_GIFT_3("1","活动首次绑定手机送3元");
	
	public String value;
	
	public String memo;
	
	enum_ActivityType(String value, String memo) {
		
		this.value = value;
		this.memo = memo;
	}
	
	public static String getMemo(String value){
		for(enum_ActivityType errorcode :enum_ActivityType.values()){
			if(errorcode.value.equals(value)){
				return errorcode.memo;
			}
		}
		return "";
		
	}
}

