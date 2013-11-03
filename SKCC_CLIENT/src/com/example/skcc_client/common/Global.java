package com.example.skcc_client.common;

import java.util.ArrayList;

import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.ProductionItem;

/**
 * ���� ���� ���� Ŭ����
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
	 * Global�� instance�� ��� ���������� ����Ѵ�.
	 * * ���� : Globa.getInstance().����������
	 */
	public static Global getInstance() {
		
		if (instance == null) instance = new Global();
		return instance;
	}
}
