package com.example.dbio.adapter;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.Item;
import com.example.dbio.assist.ItemDbHelper;

public class ItemDbAdapter {
	private static final String TAG = ItemDbAdapter.class.getSimpleName();
	
	// declare database fields
	public static final String TBL_INFO = "item";
	public static final String COL_ID = "id";
	public static final String COL_COMPANY_ID = "company_id";
	public static final String COL_TYPE = "item_type";
	public static final String COL_NAME = "name";
	public static final String COL_DESC = "description";
	
	// projection on all columns
	private static final String[] PROJECTION_ALL = new String[] {
		COL_ID, COL_COMPANY_ID, COL_TYPE, COL_NAME, COL_DESC
	};
	
	// query output type
	public static final int QUERY_TYPE_STRING_ARRAY = 0x01;
	public static final int QUERY_TYPE_ITEM_OBJ = 0x02;
	
	// declared fields
	private Context mContext;
	private SQLiteDatabase mDb;
	private ItemDbHelper mDbHelper;
	
	/*
	 * constructor
	 */
	public ItemDbAdapter(Context c) {
		mContext = c;
	}
	
	/*
	 * open database connection
	 */
	public ItemDbAdapter open() throws SQLException {
		mDbHelper = new ItemDbHelper(mContext);
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
		String strColumn[] = { COL_ID }; 
		
		Cursor queryCursor = mDb.query(TBL_INFO, strColumn, null, null, null, null, null );
		
		
		try
		{
			if(queryCursor.getCount() > Global.ITEM_INIT_COUNT) isDataExists = true;
		} 
		finally
		{
			// check if cursor is still opened and not null
			if(queryCursor != null && !queryCursor.isClosed()) {
				// close it to avoid memory leak
				queryCursor.close();
			}
		}
		
		return isDataExists;
	}
	
	private boolean isExistingItem(int id)
	{
		String strColumn[] = { COL_NAME, COL_ID }; 
		String strSelection = COL_ID + "=" + id; 
		
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
	
	public long insertInitItem(int id, int companyId, int itemType, String name, String desc) {
		
		if(isExistingItem(id) || isExistingCount()) return -1;

		long ret = mDb.insert(TBL_INFO, null, createContentValues(id, companyId, itemType, name, desc));
		
		if(ret == -1) Log.d(TAG,"Failed...item[" + id + "]");
		
		return ret;
	}
	
	/*
	 * insert a record to db
	 */
	public long insertItem(Item item) {
		
		if(isExistingItem(item.getId())) return -1;

		long ret = mDb.insert(TBL_INFO, null, createContentValues(item.getId(), item.getCompanyId(), item.getItemType(), item.getName(), item.getDescription()));
		
		if(ret == -1) Log.d(TAG, "Failed...item[" + item.getId() + "]");
		
		return ret;
	}
	
	/*
	 * update a record to db
	 */
	public long updateItem(int id, int companyId, int itemType, String name, String desc) {
		
		if(!isExistingItem(id)) return -1;
		
		return mDb.update(TBL_INFO, createContentValues(id, companyId, itemType, name, desc), COL_ID + "=" + id, null);
	}
	
	/*
	 * delete a record from db
	 */
	public long deleteItem(int id) {
		
		if(!isExistingItem(id)) return -1;
		
		return mDb.delete(TBL_INFO, COL_ID + "=" + id, null);
	}
	
	/*
	 * query all records
	 */
	public ArrayList<Item> fetchAllItems() {
		
		// get query cursor
		Cursor queryCursor = mDb.query(TBL_INFO, PROJECTION_ALL, null, null, null, null, null);
		
		// just return null if cursor null
		if(queryCursor == null) {
			
			Log.d(TAG, "fetchAllItems(): queryCursor = null "); 
			return null;
		}
		
		// init list to hold user info
		ArrayList<Item> listItems = new ArrayList<Item>();
		
		// set cursor to the first element
		queryCursor.moveToFirst();
		
		// if cursor is not the last element
		while(queryCursor.isAfterLast() == false) {
			
			// add new user info
			listItems.add(new Item(
					queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_ID)),
					queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_COMPANY_ID)),
					queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_TYPE)),
					queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_NAME)),
					queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_DESC))					
				));
			
			// move cursor to next item
			queryCursor.moveToNext();
		}
		
		// check if cursor is still opened and not null
		if(queryCursor != null && !queryCursor.isClosed()) {
			
			// close it to avoid memory leak
			queryCursor.close();
		}
		
		Log.d(TAG, "fetchAllItems(): listItems.size() = " + listItems.size());
		
		// return user list
		return listItems;
	}
	
	/*
	 * query one record
	 */
	public Object fetchSingleItem(int id, int type) {
		
		// query a cursor on identified user
		Cursor c = mDb.query(true, TBL_INFO, PROJECTION_ALL, COL_ID + "=" + id, null, null, null, null, null);
		
		// return null if no record avaiable
		if(c == null) {
			
			return null;
		}
		
		Object objOut = null;

		// create array to hold user info
		if(type == QUERY_TYPE_STRING_ARRAY) {
			
			String[] user_info = new String[5];
			
			user_info[0] = String.valueOf(id);
			user_info[1] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_COMPANY_ID)));
			user_info[2] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_TYPE)));
			user_info[3] = c.getString(c.getColumnIndexOrThrow(COL_NAME));
			user_info[4] = c.getString(c.getColumnIndexOrThrow(COL_DESC));
			
			objOut = user_info;
		}

		// create UserInfo object
		else {
			
			Item item_info = new Item (
					id,
					c.getInt(c.getColumnIndexOrThrow(COL_COMPANY_ID)),
					c.getInt(c.getColumnIndexOrThrow(COL_TYPE)),
					c.getString(c.getColumnIndexOrThrow(COL_NAME)),
					c.getString(c.getColumnIndexOrThrow(COL_DESC))					
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
	private ContentValues createContentValues(int id, int companyid, int itemType, String name, String desc) {
		
		// init a ContentValues object
		ContentValues cv = new ContentValues();
		
		// put data
		cv.put(COL_ID, id);
		cv.put(COL_COMPANY_ID, companyid);
		cv.put(COL_TYPE, itemType);
		cv.put(COL_NAME, name);
		cv.put(COL_DESC, desc);
		
		// return object
		return cv;
	}
}
