package com.example.skcc_client.common;

import java.util.ArrayList;

import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.ProductionItem;

/**
 * 전역 변수 관리 클래스
 * @author Jongkil Park
 */
public class Global {
	
	public ArrayList<InventoryItem> inventoryList;
	public ArrayList<ProductionItem> productionList;

	/**
	 * Constructor
	 */
	private Global() {
		
		inventoryList = new ArrayList<InventoryItem>();
		productionList = new ArrayList<ProductionItem>();
	}
	
	private static Global instance;

	/**
	 * Global의 instance를 얻어 전역변수를 사용한다.
	 * * 사용법 : Globa.getInstance().전역변수명
	 */
	public static Global getInstance() {
		
		if (instance == null) instance = new Global();
		return instance;
	}
}
