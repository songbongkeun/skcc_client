package com.example.dbio;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import android.content.Context;
import android.util.Log;

import com.example.dbio.adapter.InventoryDbAdapter;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.Item;
import com.example.skcc_client.gameObject.InventoryItem;

public class InventoryCreator {
	
	private static final String TAG = "INVENTORY";
	
	// db adapter 
    private InventoryDbAdapter mDbAdapter; 
  
    /* 
     * constructor 
     */ 
    public InventoryCreator(Context c) { 
        mDbAdapter = new InventoryDbAdapter(c); 
    } 
  
    /* 
     * open DBAdapter connection 
     */ 
    public void open() { 
        mDbAdapter.open(); 
    } 
    
    /*
     *  inventoryList.add(new InventoryItem(itemList.get(1001), 10));
	 *  inventoryList.add(new InventoryItem(itemList.get(1002), 10));
	 * 	inventoryList.add(new InventoryItem(itemList.get(1003), 10));
	 * 	inventoryList.add(new InventoryItem(itemList.get(1004), 10));
	 * 	inventoryList.add(new InventoryItem(itemList.get(2001), 10));
	 * 	inventoryList.add(new InventoryItem(itemList.get(2002), 10));
	 * 	inventoryList.add(new InventoryItem(itemList.get(3001), 10));
	 * 	inventoryList.add(new InventoryItem(itemList.get(3002), 10));
	 * 	inventoryList.add(new InventoryItem(itemList.get(3003), 10));
	 * 	inventoryList.add(new InventoryItem(itemList.get(3004), 10));
	 * 	inventoryList.add(new InventoryItem(itemList.get(8001), 10));
	 * 	inventoryList.add(new InventoryItem(itemList.get(9001), 10));
	 * 	
     */
    public void insertInit(String playerId)  {
    	
    	// 최초일 경우 테스트 데이터라도 집어넣는다.
    	if(0 == queryAll().size()) {
    		
    		Hashtable<Integer, Item> itemTable = Global.getInstance().itemList;

    		if(!itemTable.isEmpty()) {
    			
    			int itemId = 0;
	    		Iterator<Integer> keyData = itemTable.keySet().iterator();
	    		
		    	while(keyData.hasNext()) {
		    		
		    		itemId = keyData.next();
		    		Item item = itemTable.get(itemId);
		    		if(item.getId() == 0) continue;
		    		
		    		Log.d(TAG, "InventoryCreator.insertInit() : Create test data = " + playerId + " / " + item.getId());
		    		
		    		mDbAdapter.insertInitItem2Inventory(playerId, item.getId(), 10);
		    	}
	    	}
    	}
    }
    
    public void insert(String userid, Item item, int quantity)  { 
    	
    	mDbAdapter.insertItem2Inventory(userid, item.getId(), quantity);
    }
    
    public void update(String userid, Item item, int quantity) { 
    	
    	mDbAdapter.insertItem2Inventory(userid, item.getId(), quantity);
    }
  
    /* 
     * query all user info from db 
     */ 
    public ArrayList<InventoryItem> queryAll() {
    	
        return mDbAdapter.fetchAllItemList(); 
    } 
  
    /* 
     * close connection 
     */
    public void close() { 
        mDbAdapter.close(); 
    } 
}
