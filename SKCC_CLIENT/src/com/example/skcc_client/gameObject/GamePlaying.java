package com.example.skcc_client.gameObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.content.Context;
import android.util.Log;

import com.example.dbio.adapter.InventoryDbAdapter;
import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameRule.ProductionRule;

public class GamePlaying {

	private Context mContext;
	
	public GamePlaying(Context context) {
		
		super();
		mContext = context;
	}
	
	private void removeItemFromProduction(Item item, int position) {

		Timestamp now = new Timestamp(new Date().getTime());
		ProductionItem vacant = new ProductionItem(0, 1, Constants.code.ITEM_TYPE_NOTHING
				, "Vacant", "Vacant", now, now, now);
		Global.getInstance().productionList.set(position, vacant);
	}
	
	private void addItemToInventory(Item item, int quantity) {
		
		if(item.getId() == 0) return;
		
		// Check inventory has same item
		boolean hasSameItem = false;
		ArrayList<InventoryItem> list = Global.getInstance().inventoryList;
		Iterator<InventoryItem> iterator = list.iterator();
		
		while(iterator.hasNext()) {
			
			InventoryItem inven = iterator.next();
			if(item.getId() == inven.getId()) {
				
				inven.addQuantity(quantity);
				hasSameItem = true;
				break;
			}
		}
		
		// If no have same item, add new item
		if(!hasSameItem) {
			
			InventoryItem producedItem = new InventoryItem(item, 1);
			Global.getInstance().inventoryList.add(producedItem);
		}
		
		InventoryDbAdapter inventoryDB = new InventoryDbAdapter(mContext);
	}
	
	private void removeItemFromInventory(Item item, int quantity) {
		
		if(item.getId() == 0) return;
		
		// Check inventory has item
		ArrayList<InventoryItem> list = Global.getInstance().inventoryList;
		Iterator<InventoryItem> iterator = list.iterator();
		
		while(iterator.hasNext()) {
			
			InventoryItem inven = iterator.next();
			if(item.getId() == inven.getId()) {
				
				inven.removeQuantity(quantity);
				break;
			}
		}
	}
	
	private void getExpFromFinishedItem(Item item) {
		
		long getExp = Global.getInstance().productionRule.getProductionExp(item.getId());
		Global.getInstance().player.addExp(getExp);
	}
	
	private void getExpFromUseItem(Item item) {
		
		long getExp = Global.getInstance().productionRule.getUseExp(item.getId());
		Global.getInstance().player.addExp(getExp);
	}
	
	private void getAPFromUseItem(Item item) {
		
		Player player = Global.getInstance().player;
		
		int currentAP = player.getActionPoint();
		int maxAP = Global.getInstance().levelRule.getMaxActionPoint(player.getLevel());
		int getAP = Global.getInstance().productionRule.getUseAP(item.getId());
		
		if(maxAP < currentAP + getAP) {
			player.addActionPoint(maxAP - currentAP);
		}
		else {
			player.addActionPoint(getAP);
		}
	}
	
	/**
	 * �κ��丮�� �������� ����Ͽ� �ൿ�°� ����ġ�� ��´�.
	 * @param item	����� ������
	 */
	public void useInventoryItem(Item item) {

		// Remove use item
		removeItemFromInventory(item, 1);
		
		// Get exp from finished item
		getExpFromUseItem(item);
		
		// Get AP from finished item
		getAPFromUseItem(item);

		// Refresh player info
		Global.getInstance().playerInfoLayout.refreshInfo();
		
		Log.d("PLAYING", "Use Inventory Item : " + item.getId());
	}
	
	/**
	 * ������ ���� �������� ġ���
	 * @param item		���� ������
	 * @param position	���� �������� ��ġ
	 */
	public void cleanRottenItem(Item item, int position) {

		// Clean rotten item
		removeItemFromProduction(item, position);

		// Refresh player info
		Global.getInstance().playerInfoLayout.refreshInfo();
		
		Log.d("PLAYING", "Rotten item removed : " + position);
	}
	
	/**
	 * ������ �Ϸ�� �������� �κ��丮�� �ְ�, ����ġ�� ��´�.
	 * @param item		���� �Ϸ�� ������
	 * @param position	���� �Ϸ�� �������� ��ġ
	 */
	public void getFinishedItem(Item item, int position) {
		
		// Add item to inventory
		addItemToInventory(item, 1);
		
		// Get exp from finished item
		getExpFromFinishedItem(item);
		
		// Clean got item
		removeItemFromProduction(item, position);
		
		// Refresh player info
		Global.getInstance().playerInfoLayout.refreshInfo();

		Log.d("PLAYING", "Finished item get : " + position);
	}
	
	/**
	 * ���ο� ������ �����Ѵ�.
	 * @param item		������ ������
	 * @param position	�������� ������ ��ġ
	 * @return
	 */
	public int startNewProduction(Item item, int position) {
		
		ProductionRule rule = Global.getInstance().productionRule;
		int returnCode = rule.canProduce(item.getId());
		
		if(returnCode == Constants.rule.NEW_PRODUCTION_OK) {
		
			Player player = Global.getInstance().player;
			
			int itemId = item.getId();
			int costItem1 = rule.getCostItem1(itemId);
			int costItem2 = rule.getCostItem2(itemId);
			int costItem3 = rule.getCostItem3(itemId);
			int costItem4 = rule.getCostItem4(itemId);
			int costItem1Qty = rule.getCostItem1Quantity(itemId);
			int costItem2Qty = rule.getCostItem2Quantity(itemId);
			int costItem3Qty = rule.getCostItem3Quantity(itemId);
			int costItem4Qty = rule.getCostItem4Quantity(itemId);
	
			// Costs
			player.removeActionPoint(rule.getCostAP(itemId));
			player.removeMoney(rule.getCostMoney(itemId));
			
			removeItemFromInventory(Global.getInstance().itemList.get(costItem1), costItem1Qty);
			removeItemFromInventory(Global.getInstance().itemList.get(costItem2), costItem2Qty);
			removeItemFromInventory(Global.getInstance().itemList.get(costItem3), costItem3Qty);
			removeItemFromInventory(Global.getInstance().itemList.get(costItem4), costItem4Qty);
			
			// Create ProductionItem
			Timestamp now = new Timestamp(new Date().getTime());
			Timestamp end = new Timestamp(now.getTime() + rule.getCostTime(itemId));
			Timestamp expire = new Timestamp(end.getTime() + 5 * (end.getTime() - now.getTime()));
			
			ProductionItem productionItem = new ProductionItem(item, now, end, expire);
			
			// Add new production
			ArrayList<ProductionItem> list = Global.getInstance().productionList;
			list.set(position, productionItem);
			
			// Refresh player info
			Global.getInstance().playerInfoLayout.refreshInfo();
		}
		
		return returnCode;
	}
}
