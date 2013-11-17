package com.example.skcc_client.gameObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.content.Context;
import android.util.Log;

import com.example.dbio.adapter.InventoryDbAdapter;
import com.example.dbio.adapter.PlayerDbAdapter;
import com.example.dbio.adapter.ProductionDbAdapter;
import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameRule.ProductionRule;

public class GamePlaying {
	
	private static final String TAG = "PLAYING";

	private Context mContext;
	private PlayerDbAdapter playerDbAdapter;
	private InventoryDbAdapter inventoryDbAdapter;
	private ProductionDbAdapter productionDbAdapter;
	
	public GamePlaying(Context context) {
		
		super();
		
		mContext = context;
		playerDbAdapter = new PlayerDbAdapter(mContext);
		inventoryDbAdapter = new InventoryDbAdapter(mContext);
		productionDbAdapter = new ProductionDbAdapter(mContext);
	}
	
	private void addItemToInventory(Item item, int quantity) {
		
		if(item.getId() == 0) return;
		
		// Check inventory has same item
		boolean hasSameItem = false;
		ArrayList<InventoryItem> list = Global.getInstance().inventoryList;
		Iterator<InventoryItem> iterator = list.iterator();
		InventoryItem inventoryItem = null;
		
		while(iterator.hasNext()) {
			
			inventoryItem = iterator.next();
			
			if(item.getId() == inventoryItem.getId()) {
				
				hasSameItem = true;
				break;
			}
		}
		
		// If has same item, update its quantity.
		if(hasSameItem) {

			inventoryItem.addQuantity(quantity);
			
			// Update DB
			inventoryDbAdapter.open();
			inventoryDbAdapter.updateItem2Inventory(Global.THIS_USER, inventoryItem.getId(), inventoryItem.getQuantity());
			inventoryDbAdapter.close();
			
			Log.d(TAG, "____ addItemToInventory[update] : " + item.getId() + " - " + item.getName() + " / " + quantity);
		}
		
		// else, insert new item to inventory
		else {

			// Add to inventory list 
			InventoryItem producedItem = new InventoryItem(item, 1);
			Global.getInstance().inventoryList.add(producedItem);
			
			// Insert DB
			inventoryDbAdapter.open();
			inventoryDbAdapter.insertItem2Inventory(Global.THIS_USER, producedItem.getId(), producedItem.getQuantity());
			inventoryDbAdapter.close();
			
			Log.d(TAG, "____ addItemToInventory[insert] : " + item.getId() + " - " + item.getName() + " / " + quantity);
		}
	}
	
	private void removeItemFromInventory(Item item, int quantity) {
		
		if(item.getId() == 0) return;
		
		// Check inventory has item
		ArrayList<InventoryItem> list = Global.getInstance().inventoryList;
		Iterator<InventoryItem> iterator = list.iterator();
		InventoryItem inventoryItem = null;
		
		while(iterator.hasNext()) {
			
			inventoryItem = iterator.next();
			if(item.getId() == inventoryItem.getId()) {
				
				inventoryItem.removeQuantity(quantity);
				break;
			}
		}
		
		// If has item at inventory
		if(inventoryItem.getQuantity() > 0) {
		
			// Update DB
			inventoryDbAdapter.open();
			inventoryDbAdapter.updateItem2Inventory(Global.THIS_USER, inventoryItem.getId(), inventoryItem.getQuantity());
			inventoryDbAdapter.close();
			
			Log.d(TAG, "____ removeItemFromInventory[update] : " + item.getId() + " - " + item.getName() + " / -" + quantity);
		}
		// If has no item at inventory
		else {
		
			// Delete DB
			inventoryDbAdapter.open();
			inventoryDbAdapter.deleteItem2Inventory(Global.THIS_USER, inventoryItem.getId());
			inventoryDbAdapter.close();
			
			// Remove from inventory list 
			for(int i = 0; i < Global.getInstance().inventoryList.size(); i++) {
				
				if(item.getId() == Global.getInstance().inventoryList.get(i).getId()) {
					
					Global.getInstance().inventoryList.remove(i);
					break;
				}
			}
			
			Log.d(TAG, "____ removeItemFromInventory[delete] : " + item.getId() + " - " + item.getName() + " / -" + quantity);
		}
	}
	
	private void addItemToProduction(int position, Item item) {
		
		ProductionRule rule = Global.getInstance().productionRule;
		
		Timestamp now = new Timestamp(new Date().getTime());
		Timestamp end = new Timestamp(now.getTime() + rule.getCostTime(item.getId()));
		Timestamp exp = new Timestamp(end.getTime() + 5 * (end.getTime() - now.getTime()));
		
		// Create production item
		ProductionItem newProduction = new ProductionItem(position, item, now, end, exp);
		
		// Add to production item to list
		Global.getInstance().productionList.set(position, newProduction);
		
		// Insert DB
		productionDbAdapter.open();
		productionDbAdapter.insertProduct(newProduction);
		productionDbAdapter.close();
		
		Log.d(TAG, "____ addItemToProduction : " + position + " / " + item.getId() + " - " + item.getName());
	}
	
	private void removeItemFromProduction(int position, Item item) {

		// Delete DB
		productionDbAdapter.open();
		productionDbAdapter.deleteProduct(Global.getInstance().productionList.get(position).getPosition());
		productionDbAdapter.close();

		// Remove from production item to list
		Global.getInstance().productionList.set(position, null);
		
		Log.d(TAG, "____ removeItemFromProduction : " + position + " / " + item.getId() + " - " + item.getName());
	}
	
	private void getExpFromFinishedItem(Item item) {
		
		Player player = Global.getInstance().player;
		
		// Get exp
		long getExp = Global.getInstance().productionRule.getProductionExp(item.getId());
		player.addExp(getExp);
		
		// Update DB
		playerDbAdapter.open();
		playerDbAdapter.updatePlayer(player);
		playerDbAdapter.close();
		
		Log.d(TAG, "____ getExpFromFinishedItem : " + getExp + " / " + item.getId() + " - " + item.getName());
	}
	
	private void getExpFromUseItem(Item item) {
		
		Player player = Global.getInstance().player;
		
		// Get exp
		long getExp = Global.getInstance().productionRule.getUseExp(item.getId());
		player.addExp(getExp);
		
		// Update DB
		playerDbAdapter.open();
		playerDbAdapter.updatePlayer(player);
		playerDbAdapter.close();
		
		Log.d(TAG, "____ getExpFromUseItem : " + getExp + " / " + item.getId() + " - " + item.getName());
	}
	
	private void getMoneyFromItem(Item item) {
		
		// TODO: 현재 아이템에서 돈을 얻는 방법이 없음. 새로운 설계 필요.
	}
	
	private void removeMoneyFromItem(Item item) {
		
		Player player = Global.getInstance().player;
		
		// Get cost ap
		long costMoney = Global.getInstance().productionRule.getCostMoney(item.getId());
		player.removeMoney(costMoney);
		
		// Update DB
		playerDbAdapter.open();
		playerDbAdapter.updatePlayer(player);
		playerDbAdapter.close();
		
		Log.d(TAG, "____ removeMoneyFromItem : " + costMoney + " / " + item.getId() + " - " + item.getName());
	}
	
	private void getAPFromItem(Item item) {
		
		Player player = Global.getInstance().player;
		
		// Get ap
		int currentAP = player.getActionPoint();
		int maxAP = Global.getInstance().levelRule.getMaxActionPoint(player.getLevel());
		int getAP = Global.getInstance().productionRule.getUseAP(item.getId());
		
		// Calculate getting ap
		if(maxAP < currentAP + getAP) {
			player.addActionPoint(maxAP - currentAP);
		}
		else {
			player.addActionPoint(getAP);
		}
		
		// Update DB
		playerDbAdapter.open();
		playerDbAdapter.updatePlayer(player);
		playerDbAdapter.close();
		
		Log.d(TAG, "____ getAPFromItem : " + getAP + " / " + item.getId() + " - " + item.getName());
	}
	
	private void removeAPFromItem(Item item) {
		
		Player player = Global.getInstance().player;
		
		// Get cost ap
		int costAP = Global.getInstance().productionRule.getCostAP(item.getId());
		player.removeActionPoint(costAP);
		
		// Update DB
		playerDbAdapter.open();
		playerDbAdapter.updatePlayer(player);
		playerDbAdapter.close();
		
		Log.d(TAG, "____ removeAPFromItem : " + costAP + " / " + item.getId() + " - " + item.getName());
	}
	
	/**
	 * 인벤토리의 아이템을 사용하여 행동력과 경험치를 얻는다.
	 * @param item	사용할 아이템
	 */
	public void useInventoryItem(Item item) {

		// Remove use item
		removeItemFromInventory(item, 1);
		
		// Get exp from finished item
		getExpFromUseItem(item);
		
		// Get AP from finished item
		getAPFromItem(item);

		// Refresh player info
		Global.getInstance().playerInfoLayout.refreshInfo();
		
		Log.d(TAG, "Use Inventory Item : " + item.getId());
	}
	
	/**
	 * 생산의 썩은 아이템을 치운다
	 * @param item		썩은 아이템
	 * @param position	썩은 아이템의 위치
	 */
	public void cleanRottenItem(Item item, int position) {

		// Clean rotten item at position
		removeItemFromProduction(position, item);

		// Refresh player info
		Global.getInstance().playerInfoLayout.refreshInfo();
		
		Log.d(TAG, "Rotten item removed : " + position);
	}
	
	/**
	 * 생산의 완료된 아이템을 인벤토리에 넣고, 경험치를 얻는다.
	 * @param item		생산 완료된 아이템
	 * @param position	생산 완료된 아이템의 위치
	 */
	public void getFinishedItem(int position, Item item) {

		// Clean got item at position
		removeItemFromProduction(position, item);
		
		// Get exp from finished item
		getExpFromFinishedItem(item);
		
		// Add 1 to inventory
		addItemToInventory(item, 1);
		
		// Refresh player info
		Global.getInstance().playerInfoLayout.refreshInfo();

		Log.d(TAG, "Finished item get : " + position);
	}
	
	/**
	 * 새로운 생산을 시작한다.
	 * @param item		생산할 아이템
	 * @param position	아이템을 생산할 위치
	 * @return
	 */
	public int startNewProduction(Item item, int position) {
		
		ProductionRule rule = Global.getInstance().productionRule;
		int returnCode = rule.canProduce(item.getId());
		
		if(returnCode == Constants.rule.NEW_PRODUCTION_OK) {
		
			int itemId = item.getId();
			int costItem1 = rule.getCostItem1(itemId);
			int costItem2 = rule.getCostItem2(itemId);
			int costItem3 = rule.getCostItem3(itemId);
			int costItem4 = rule.getCostItem4(itemId);
			int costItem1Qty = rule.getCostItem1Quantity(itemId);
			int costItem2Qty = rule.getCostItem2Quantity(itemId);
			int costItem3Qty = rule.getCostItem3Quantity(itemId);
			int costItem4Qty = rule.getCostItem4Quantity(itemId);
	
			// Costs money
			removeMoneyFromItem(item);
			
			// Costs ap
			removeAPFromItem(item);
			
			// Costs items
			removeItemFromInventory(Global.getInstance().itemList.get(costItem1), costItem1Qty);
			removeItemFromInventory(Global.getInstance().itemList.get(costItem2), costItem2Qty);
			removeItemFromInventory(Global.getInstance().itemList.get(costItem3), costItem3Qty);
			removeItemFromInventory(Global.getInstance().itemList.get(costItem4), costItem4Qty);
			
			// Add new production to list
			addItemToProduction(position, item);
			
			// Refresh player info
			Global.getInstance().playerInfoLayout.refreshInfo();

			Log.d(TAG, "Start new production : " + position + " / " + item.getId() + " - " + item.getName());
		}
		
		return returnCode;
	}
}
