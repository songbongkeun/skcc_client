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
		Item item1001 = new Item(1001, 1, Constants.code.ITEM_TYPE_MATERIAL, "커피빈", "각종 커피의 원재료가 되는 커피나무 열매의 씨");
		Item item1002 = new Item(1002, 1, Constants.code.ITEM_TYPE_MATERIAL, "우유", "소의 젖. 백색으로 지방, 단백질, 칼슘, 비타민이 풍부하게 함유되어 있어 영양가가 높다.");
		Item item1003 = new Item(1003, 1, Constants.code.ITEM_TYPE_MATERIAL, "밀가루", "밀을 빻아 만든 가루");
		Item item1004 = new Item(1004, 1, Constants.code.ITEM_TYPE_MATERIAL, "초코렛", "당산의 뇌에 활력을 불어 넣어 줄 ... 초코렛 ... 아.. 달다~~");
		Item item2001 = new Item(2001, 1, Constants.code.ITEM_TYPE_DRINK, "아메리카노", "에스프레소에 뜨거운 물을 더하여 먹는 커피");
		Item item2002 = new Item(2002, 1, Constants.code.ITEM_TYPE_DRINK, "카페라떼", "부드러운 우유와 조합된 달달한 카페라떼");
		Item item3001 = new Item(3001, 1, Constants.code.ITEM_TYPE_BREAD, "빵", "남아 있는 밀가루로 빵을 만들어 보는 것은 어떨까요?");
		Item item3002 = new Item(3002, 1, Constants.code.ITEM_TYPE_DRINK, "초코우유", "초코렛과 우유의 황금 비율");
		Item item3003 = new Item(3003, 1, Constants.code.ITEM_TYPE_BREAD, "모카번", "커피빈과 빵의 조화~~ 한번 드셔보세요.");
		Item item3004 = new Item(3004, 1, Constants.code.ITEM_TYPE_BREAD, "초코머핀", "그냥 빵은 가라... 초코렛이 듬뿍 담긴 머핀");
		Item item8001 = new Item(8001, 1, Constants.code.ITEM_TYPE_COUPON, "Cafe4U 카페라떼", "Cafe4U 레시피를 가지고 만드는 환상의 커피우유~ 반합니다. 강추");
		Item item9001 = new Item(9001, 1, Constants.code.ITEM_TYPE_RECEIPE, "Cafe4U 카페라떼 레시피", "Cafe4U 레시피");
		
		Hashtable<Integer, Item> itemList = Global.getInstance().itemList;
		
		if(0 == itemList.size()) {
			
			itemList.put(item0000.getId(), item0000);
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
		}
		
		// Player
		if(null == Global.getInstance().player) {
			
			Global.getInstance().player = new Player("힘내자!", "skcnc", 63800, 10, 1000);
		}
		
		// Inventory Item
		if(0 == Global.getInstance().inventoryList.size()) {
			
			ArrayList<InventoryItem> inventoryList = Global.getInstance().inventoryList;
			inventoryList.add(new InventoryItem(itemList.get(1001), 10));
			inventoryList.add(new InventoryItem(itemList.get(1002), 10));
			inventoryList.add(new InventoryItem(itemList.get(1003), 10));
			inventoryList.add(new InventoryItem(itemList.get(1004), 10));
			inventoryList.add(new InventoryItem(itemList.get(2001), 10));
			inventoryList.add(new InventoryItem(itemList.get(2002), 10));
			inventoryList.add(new InventoryItem(itemList.get(3001), 10));
			inventoryList.add(new InventoryItem(itemList.get(3002), 10));
			inventoryList.add(new InventoryItem(itemList.get(3003), 10));
			inventoryList.add(new InventoryItem(itemList.get(3004), 10));
			inventoryList.add(new InventoryItem(itemList.get(8001), 10));
			inventoryList.add(new InventoryItem(itemList.get(9001), 10));
		}

		// Production Item
		if(0 == Global.getInstance().productionList.size()) {
			
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
}
