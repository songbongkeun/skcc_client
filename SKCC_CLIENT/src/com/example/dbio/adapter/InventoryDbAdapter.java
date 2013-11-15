package com.example.dbio.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.skcc_client.common.Global;
import com.example.dbio.assist.InventoryDbHelper;
import com.example.dbio.data.InventoryInfo;

public class InventoryDbAdapter {
	
	private static final String TAG = InventoryDbAdapter.class.getSimpleName();
	
	// declare database fields
	public static final String TBL_INFO = "inventory";
	public static final String COL_USER_ID = "user_id";
	public static final String COL_ITEM_ID = "item_id";
	public static final String COL_QUANTITY = "quantity";
	
	// projection on all columns
	private static final String[] PROJECTION_ALL = new String[] {
		COL_USER_ID, COL_ITEM_ID,COL_QUANTITY
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
	
	private boolean isExistingCount()
	{
		boolean isDataExists = false;
		String strColumn[] = { COL_USER_ID }; 
		
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
	
	private boolean isExistingItem(String userid, int itemId)
	{
		String strColumn[] = { COL_USER_ID, COL_ITEM_ID }; 
		String strSelection = COL_ITEM_ID + "=" + itemId +" AND " + COL_USER_ID + "='" + userid + "'"; 
		
		Cursor queryCursor = mDb.query(TBL_INFO, strColumn, strSelection, null, null, null, null );
		
		if(queryCursor == null) {
			Log.d(TAG, "fetchAllUsers(): queryCursor = null "); 
			return true;
		}
		try
		{
			if( queryCursor.getCount() == 1) return true;
		} 
		finally
		{
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
	public long insertItem2Inventory(String userid, int itemId, int quantity) {
		
		if(isExistingItem(userid, itemId)) return -1;
		
		long ret = mDb.insert(TBL_INFO, null, createContentValues(userid, itemId, quantity));
		
		if(ret == -1) Log.d(TAG,"Failed...user[" + userid + "]");
		
		return ret;
	}
	
	public long insertInitItem2Inventory(String userid, int itemId, int quantity) {
		
		if(isExistingItem(userid, itemId) || isExistingCount()) return -1;
		
		long ret = mDb.insert(TBL_INFO, null, createContentValues(userid, itemId, quantity));
		
		if(ret == -1) Log.d(TAG,"Failed...user[" + userid + "]");
		
		return ret;
	}
	
	public long updateItem2Inventory(String userId, int itemId, int quantity) {
		String whereClause = COL_ITEM_ID + "=" + itemId +" AND " + COL_USER_ID + "='" + userId + "'"; 
		long ret = mDb.update(TBL_INFO, createContentValues(userId, itemId, quantity), whereClause, null);
		
		if(ret == -1) Log.d(TAG,"Failed...user[" + userId + "]");
		
		return ret;
	}
	
	/*
	 * delete a record from db
	 */
	public long deleteItem2Inventory(String userId, int itemId) {
		String strSelection = COL_ITEM_ID + "=" + itemId +" AND " + COL_USER_ID + "='" + userId + "'"; 
		return mDb.delete(TBL_INFO, strSelection, null);
	}
	
	/*
	 * query all records
	 */
	public List<InventoryInfo> fetchAllItemList() {
		// get query cursor
		Cursor queryCursor = mDb.query(TBL_INFO, PROJECTION_ALL, null, null, null, null, null);
		// just return null if cursor null
		if(queryCursor == null) {
			Log.d(TAG, "fetchAllItemList(): queryCursor = null "); 
			return null;
		}
		// init list to hold user info
		List<InventoryInfo> listItemList = new ArrayList<InventoryInfo>();
		// set cursor to the first element
		queryCursor.moveToFirst();
		// if cursor is not the last element
		while(queryCursor.isAfterLast() == false) {
			// add new user info
			listItemList.add(new InventoryInfo(
					// get user id from cursor
					queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_USER_ID)),
					// get item id from cursor
					queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_ITEM_ID)),
					// get item quantity from cursor
					queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_QUANTITY))
				)
			);
			// move cursor to next item
			queryCursor.moveToNext();
		}
		// check if cursor is still opened and not null
		if(queryCursor != null && !queryCursor.isClosed()) {
			// close it to avoid memory leak
			queryCursor.close();
		}
		Log.d(TAG, "fetchAllItemList(): listItemList.size() = " + listItemList.size());
		// return user list
		return listItemList;
	}
	
	/*
	 * query one record
	 */
	public Object fetchSingleItemList(String userId, int itemId, int type) {
		String strSelection = COL_ITEM_ID + "=" + itemId +" AND " + COL_USER_ID + "='" + userId + "'"; 
		// query a cursor on identified user
		Cursor c = mDb.query(true, TBL_INFO, PROJECTION_ALL, strSelection, null, null, null, null, null);
		// return null if no record avaiable
		if(c == null) {
			return null;
		}
		
		Object objOut = null;
		
		if(type == QUERY_TYPE_STRING_ARRAY) {
			// create array to hold user info
			String[] user_info = new String[3];
			user_info[0] = userId;
			user_info[2] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_ITEM_ID)));
			user_info[1] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_QUANTITY)));
			
			objOut = user_info;
		} else {
			// create UserInfo object
			InventoryInfo item_info = new InventoryInfo(
					userId,
					c.getInt(c.getColumnIndexOrThrow(COL_ITEM_ID)),
					c.getInt(c.getColumnIndexOrThrow(COL_QUANTITY))
			);
			objOut = item_info;
		}
		// close cursor 
		c.close();
		
		// return user info
		return objOut;		
	}
	
	
	
	/*
	 * create ContentValues object to use for db transaction
	 */
	private ContentValues createContentValues(String usrid, int itemid, int quantity) {
		// init a ContentValues object
		ContentValues cv = new ContentValues();
		// put data
		cv.put(COL_USER_ID, usrid);
		cv.put(COL_ITEM_ID, itemid);
		cv.put(COL_QUANTITY, quantity);
		// return object
		return cv;
	}
}
