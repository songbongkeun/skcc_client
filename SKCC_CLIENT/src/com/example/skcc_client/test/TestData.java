package com.example.skcc_client.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Global;

import com.example.skcc_client.gameObject.Item;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.Player;
import com.example.skcc_client.gameObject.ProductionItem;

public class TestData {
	
	public TestData() {
		super();
	}
	
	public void generateTestData() {
		
		// Rules
		Global.getInstance().levelRule.generateTestRule(); // Level
		Global.getInstance().productionRule.generateTestRule(); // Production
		
		// Items
		Item item0000 = new Item(   0, 1, Constants.code.ITEM_TYPE_NOTHING, "Vacant", "Vacant");
		Item item1001 = new Item(1001, 1, Constants.code.ITEM_TYPE_MATERIAL, "Ŀ�Ǻ�", "Ŀ�Ǻ�");
		Item item1002 = new Item(1002, 1, Constants.code.ITEM_TYPE_MATERIAL, "����", "����");
		Item item1003 = new Item(1003, 1, Constants.code.ITEM_TYPE_MATERIAL, "�а���", "�а���");
		Item item1004 = new Item(1004, 1, Constants.code.ITEM_TYPE_MATERIAL, "���ڷ�", "����� ���� Ȱ���� �Ҿ� �־� �� ... ���ڷ� ... ��.. �޴�~~");
		Item item2001 = new Item(2001, 1, Constants.code.ITEM_TYPE_DRINK, "�Ƹ޸�ī��", "�Ƹ޸�ī��");
		Item item2002 = new Item(2002, 1, Constants.code.ITEM_TYPE_DRINK, "ī���", "�ε巯�� ������ ���յ� �޴��� ī���");
		Item item3001 = new Item(3001, 1, Constants.code.ITEM_TYPE_BREAD, "��", "���� �ִ� �а���� ���� ����� ���� ���� ����?");
		Item item3002 = new Item(3002, 1, Constants.code.ITEM_TYPE_DRINK, "���ڿ���", "���ڷ��� ������ Ȳ�� ����");
		Item item3003 = new Item(3003, 1, Constants.code.ITEM_TYPE_BREAD, "��ī��", "Ŀ�Ǻ�� ���� ��ȭ~~ �ѹ� ��ź�����.");
		Item item3004 = new Item(3004, 1, Constants.code.ITEM_TYPE_BREAD, "���ڸ���", "�׳� ���� ����... ���ڷ��� ��� ��� ����");
		Item item8001 = new Item(8001, 1, Constants.code.ITEM_TYPE_COUPON, "Cafe4U ī���", "Cafe4U �����Ǹ� ������ ����� ȯ���� Ŀ�ǿ���~ ���մϴ�. ����");
		Item item9001 = new Item(9001, 1, Constants.code.ITEM_TYPE_RECEIPE, "Cafe4U ī��� ������", "Cafe4U ������");
		
		Hashtable<Integer, Item> itemList = Global.getInstance().itemList;
		itemList.put(item0000.getId(), item1001);
		itemList.put(item1001.getId(), item1001);
		itemList.put(item1002.getId(), item1002);
		itemList.put(item1003.getId(), item1003);
		itemList.put(item1004.getId(), item1004);
		itemList.put(item2001.getId(), item2001);
		itemList.put(item2002.getId(), item2002);
		itemList.put(item3001.getId(), item3001);
		itemList.put(item3002.getId(), item3002);
		itemList.put(item3003.getId(), item3003);
		itemList.put(item3004.getId(), item3004);
		itemList.put(item8001.getId(), item8001);
		itemList.put(item9001.getId(), item9001);
		
		// Player
		Global.getInstance().player = new Player("park108", "����", 64000, 100, 1000);
		
		// Inventory Item
		ArrayList<InventoryItem> inventoryList = Global.getInstance().inventoryList;
		inventoryList.add(new InventoryItem(itemList.get(1001), 10));
		inventoryList.add(new InventoryItem(itemList.get(1002), 20));
		inventoryList.add(new InventoryItem(itemList.get(3002), 30));

		// Production Item
		ArrayList<ProductionItem> productionList = Global.getInstance().productionList;
		Date date = new Date(); 
		// 1 Producing
		Timestamp start1	= new Timestamp(date.getTime() - 40000);
		Timestamp end1		= new Timestamp(date.getTime() + 20000);
		Timestamp expire1	= new Timestamp(date.getTime() + 40000);
		// 2 Finished
		Timestamp start2	= new Timestamp(date.getTime() - 40000);
		Timestamp end2		= new Timestamp(date.getTime() - 20000);
		Timestamp expire2	= new Timestamp(date.getTime() + 20000);
		// 3 Rotten
		Timestamp start3	= new Timestamp(date.getTime() - 40000);
		Timestamp end3		= new Timestamp(date.getTime() - 20000);
		Timestamp expire3	= new Timestamp(date.getTime() - 10000);
		// 4 Producing
		Timestamp start4	= new Timestamp(date.getTime() - 400000);
		Timestamp end4		= new Timestamp(date.getTime() + 200000);
		Timestamp expire4	= new Timestamp(date.getTime() + 400000);
		// 4 Producing
		Timestamp start5	= new Timestamp(date.getTime() - 40000000);
		Timestamp end5		= new Timestamp(date.getTime() + 20000000);
		Timestamp expire5	= new Timestamp(date.getTime() + 40000000);
		
		
		productionList.add(new ProductionItem(itemList.get(2002), start1, end1, expire1));
		productionList.add(new ProductionItem(itemList.get(3001), start2, end2, expire2));
		productionList.add(new ProductionItem(itemList.get(3001), start2, end2, expire2));
		productionList.add(new ProductionItem(itemList.get(3001), start2, end2, expire2));
		productionList.add(new ProductionItem(itemList.get(3001), start2, end2, expire2));
		productionList.add(new ProductionItem(itemList.get(1004), start3, end3, expire3));
		productionList.add(new ProductionItem(itemList.get(3004), start4, end4, expire4));
		productionList.add(new ProductionItem(itemList.get(8001), start5, end5, expire5));
	}
}
