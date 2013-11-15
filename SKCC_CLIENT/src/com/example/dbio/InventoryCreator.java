package com.example.dbio;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.example.dbio.adapter.InventoryDbAdapter;
import com.example.dbio.data.InventoryInfo;
import com.example.dbio.data.ItemInfo;

public class InventoryCreator {
	
	private static final String TAG = InventoryCreator.class.getSimpleName();
	
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
    public void insertInit(String userid, List<ItemInfo> itemList, int quantity) 
    { 
    	int size = itemList.size();
    	Log.d(TAG, "[inventory insert]" + size);
    	
    	for( int i = 0; i < size; i++)
    	{
    		ItemInfo item = itemList.get(i);
    		if(item.getID() == 0) continue;
    		Log.d(TAG, "[inventory insert]" + userid + ":" + item.getID());
    		mDbAdapter.insertInitItem2Inventory(userid, item.getID(), quantity);
    	}
    }
    
    public void insert(String userid, ItemInfo item, int quantity) 
    { 
    	mDbAdapter.insertItem2Inventory(userid, item.getID(), quantity);
    }
    
    public void update(String userid, ItemInfo item, int quantity) 
    { 
    	mDbAdapter.insertItem2Inventory(userid, item.getID(), quantity);
    }
  
    /* 
     * query all user info from db 
     */ 
    public List<InventoryInfo> queryAll() { 
        return mDbAdapter.fetchAllItemList(); 
    } 
  
    /* 
     * close connection 
     */
    public void close() { 
        mDbAdapter.close(); 
    } 
}
