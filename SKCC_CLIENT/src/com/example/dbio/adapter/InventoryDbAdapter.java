package com.example.dbio.adapter;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.Item;
import com.example.dbio.assist.InventoryDbHelper;

public class InventoryDbAdapter {
	
	private static final String TAG = "INVENTORY";
	
	// declare database fields
	public static final String TBL_INFO = "inventory";
	public static final String COL_PLAYER_ID = "player_id";
	public static final String COL_ITEM_ID = "item_id";
	public static final String COL_QUANTITY = "quantity";
	
	// projection on all columns
	private static final String[] PROJECTION_ALL = new String[] {
		COL_PLAYER_ID, COL_ITEM_ID, COL_QUANTITY
	};
	
	// query output type
	public static final int QUERY_TYPE_STRING_ARRAY = 0x01;
	public static final int QUERY_TYPE_INVENTORY_OBJ = 0x02;
	
	// declared fields
	private Context mContext;
	private SQLiteDatabase mDb;
	private InventoryDbHelper mDbHelper;
	
	/*
	 * constructor
	 */
	public InventoryDbAdapter(Context c) {
		
		mContext = c;
	}
	
	/*
	 * open database connection
	 */
	public InventoryDbAdapter open() throws SQLException {
		
		mDbHelper = new InventoryDbHelper(mContext);
		mDb = mDbHelper.getWritableDatabase();

		return this;
	}
	
	/*
	 * close database connection
	 */
	public void close() {
		
		mDbHelper.close();
	}
	
	private boolean isExistingCount() {
		
		boolean isDataExists = false;
		String strColumn[] = { COL_PLAYER_ID }; 
		
		Cursor queryCursor = mDb.query(TBL_INFO, strColumn, null, null, null, null, null );
		
		try {
			
			if(queryCursor.getCount() > Global.INVENTORY_INIT_COUNT) isDataExists = true;
		}
		finally {
			
			// check if cursor is still opened and not null
			if(queryCursor != null && !queryCursor.isClosed()) {
				
				// close it to avoid memory leak
				queryCursor.close();
			}
		}
		
		return isDataExists;
	}
	
	private boolean isExistingItem(String playerId, int itemId) {
		
		String strColumn[] = { COL_PLAYER_ID, COL_ITEM_ID }; 
		String strSelection = COL_ITEM_ID + "=" + itemId +" AND " + COL_PLAYER_ID + "='" + playerId + "'"; 
		
		Cursor queryCursor = mDb.query(TBL_INFO, strColumn, strSelection, null, null, null, null );
		
		if(queryCursor == null) {
			
			Log.d(TAG, "fetchAllUsers(): queryCursor = null "); 
			return true;
		}
		try {
			
			if( queryCursor.getCount() == 1) return true;
		} 
		finally {
			
			// check if cursor is still opened and not null
			if(queryCursor != null && !queryCursor.isClosed()) {
				// close it to avoid memory leak
				queryCursor.close();
			}
		}
		
		return false;
	}
	
	
	/*
	 * insert a record to db
	 */
	public long insertItem2Inventory(String playerId, int itemId, int quantity) {
		
		if(isExistingItem(playerId, itemId)) return -1;
		
		long ret = mDb.insert(TBL_INFO, null, createContentValues(playerId, itemId, quantity));
		
		if(ret == -1) {
			
			Log.d(TAG,"Failed...user[" + playerId + "]");
		}
		
		return ret;
	}
	
	public long insertInitItem2Inventory(String userid, int itemId, int quantity) {
		
		if(isExistingItem(userid, itemId) || isExistingCount()) return -1;
		
		long ret = mDb.insert(TBL_INFO, null, createContentValues(userid, itemId, quantity));
		
		if(ret == -1) {
			
			Log.d(TAG,"Failed...user[" + userid + "]");
		}
		
		Log.d(TAG, "InventoryDbAdapter.insertInitItem2Inventory() : insert item = " + itemId + " / " + quantity);
		
		return ret;
	}
	
	public long updateItem2Inventory(String playerId, int itemId, int quantity) {
		
		String whereClause = COL_ITEM_ID + "=" + itemId
				+" AND " + COL_PLAYER_ID + "='" + playerId + "'"; 
		
		long ret = mDb.update(TBL_INFO, createContentValues(playerId, itemId, quantity), whereClause, null);
		
		if(ret == -1) {
			
			Log.d(TAG,"Failed...user[" + playerId + "]");
		}
		
		Log.d(TAG, "InventoryDbAdapter.updateItem2Inventory() : update item = " + itemId + " / " + quantity);
		
		return ret;
	}
	
	/*
	 * delete a record from db
	 */
	public long deleteItem2Inventory(String playerId, int itemId) {
		
		String whereClause = COL_ITEM_ID + "=" + itemId
				+" AND " + COL_PLAYER_ID + "='" + playerId + "'"; 
		
		long ret = mDb.delete(TBL_INFO, whereClause, null);
		
		if(ret == -1) {
			
			Log.d(TAG,"Failed...item[" + itemId + "]");
		}
		
		Log.d(TAG, "InventoryDbAdapter.deleteItem2Inventory() : delete item = " + itemId);
		
		return ret;
	}
	
	/**
	 * query all records
	 * @return	ArrayList<InventoryItem>
	 */
	public ArrayList<InventoryItem> fetchAllItemList() {
		
		// get query cursor
		Cursor queryCursor = mDb.query(TBL_INFO, PROJECTION_ALL, null, null, null, null, null);
		
		// just return null if cursor null
		if(queryCursor == null) {
			
			Log.d(TAG, "InventoryDbAdapter.fetchAllItemList(): queryCursor = null "); 
			return null;
		}
		
		// init list to hold user info
		ArrayList<InventoryItem> listItemList = new ArrayList<InventoryItem>();
		
		// set cursor to the first element
		queryCursor.moveToFirst();
		
		// if cursor is not the last element
		int itemId = 0;
		int quantity = 0;
		
		while(queryCursor.isAfterLast() == false) {
			
			quantity = queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_QUANTITY));
			
			if(0 < quantity) {
				
				itemId = queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_ITEM_ID));
				Item item = Global.getInstance().itemList.get(itemId);
				
				// add new inventory item
				listItemList.add(new InventoryItem(item, quantity));
			}
			
			// move cursor to next item
			queryCursor.moveToNext();
		}
		
		// check if cursor is still opened and not null
		if(queryCursor != null && !queryCursor.isClosed()) {
			
			// close it to avoid memory leak
			queryCursor.close();
		}
		
		Log.d(TAG, "InventoryDbAdapter.fetchAllItemList().size() = " + listItemList.size());
		
		return listItemList;
	}
	
	/**
	 * query one record
	 * @param playerId	플레이어 id
	 * @param itemId	아이템 id
	 * @param type		리턴 타입 = QUERY_TYPE_STRING_ARRAY, QUERY_TYPE_INVENTORY_OBJ
	 * @return
	 */
	public Object fetchSingleItemList(String playerId, int itemId, int type) {
		
		String strSelection = COL_ITEM_ID + "=" + itemId +" AND " + COL_PLAYER_ID + "='" + playerId + "'"; 
		
		// query a cursor on identified user
		Cursor c = mDb.query(true, TBL_INFO, PROJECTION_ALL, strSelection, null, null, null, null, null);
		
		// return null if no record avaiable
		if(c == null) {
			
			return null;
		}
		
		Object objOut = null;
		
		if(type == QUERY_TYPE_STRING_ARRAY) {
			
			// create array to hold user info
			String[] playerInfo = new String[3];
			playerInfo[0] = playerId;
			playerInfo[2] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_ITEM_ID)));
			playerInfo[1] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_QUANTITY)));
			
			objOut = playerInfo;
		}
		else {
			
			// create InventoryItem object
			Item item = Global.getInstance().itemList.get(itemId);
			int quantity = c.getInt(c.getColumnIndexOrThrow(COL_QUANTITY));
			InventoryItem inventoryItem = new InventoryItem(item, quantity);
			
			objOut = inventoryItem;
		}
		
		// close cursor 
		c.close();
		
		return objOut;		
	}
	
	
	/**
	 * create ContentValues object to use for db transaction
	 * @param playerId	플레이어 id
	 * @param itemId	아이템 id
	 * @param quantity	수량
	 * @return
	 */
	private ContentValues createContentValues(String playerId, int itemId, int quantity) {
		
		// init a ContentValues object
		ContentValues cv = new ContentValues();
		
		// put data
		cv.put(COL_PLAYER_ID, playerId);
		cv.put(COL_ITEM_ID, itemId);
		cv.put(COL_QUANTITY, quantity);
		
		// return object
		return cv;
	}
}
