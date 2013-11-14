package com.example.skcc_client.common;

public class CodeHelper {

	public CodeHelper() {
		super();
	}
	
	public static String getItemTypeName(int itemType) {
		
		String name = null;
		
		if(Constants.code.ITEM_TYPE_NOTHING == itemType) name = "����";
		else if(Constants.code.ITEM_TYPE_MATERIAL == itemType) name = "���";
		else if(Constants.code.ITEM_TYPE_DRINK == itemType) name = "����";
		else if(Constants.code.ITEM_TYPE_BREAD == itemType) name = "��";
		else if(Constants.code.ITEM_TYPE_COUPON == itemType) name = "����";
		else if(Constants.code.ITEM_TYPE_RECEIPE == itemType) name = "������";
		
		return name;
	}
}
