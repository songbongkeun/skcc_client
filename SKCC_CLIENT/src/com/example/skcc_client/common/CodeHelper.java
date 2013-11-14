package com.example.skcc_client.common;

public class CodeHelper {

	public CodeHelper() {
		super();
	}
	
	public static String getItemTypeName(int itemType) {
		
		String name = null;
		
		if(Constants.code.ITEM_TYPE_NOTHING == itemType) name = "없음";
		else if(Constants.code.ITEM_TYPE_MATERIAL == itemType) name = "재료";
		else if(Constants.code.ITEM_TYPE_DRINK == itemType) name = "음료";
		else if(Constants.code.ITEM_TYPE_BREAD == itemType) name = "빵";
		else if(Constants.code.ITEM_TYPE_COUPON == itemType) name = "쿠폰";
		else if(Constants.code.ITEM_TYPE_RECEIPE == itemType) name = "레시피";
		
		return name;
	}
}
