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
import com.example.dbio.assist.UserDbHelper;
import com.example.dbio.data.ProductionInfo;

public class ProductionDbAdapter {
	private static final String TAG = ProductionDbAdapter.class.getSimpleName();
	
	public static final String TBL_INFO = "production";
	public static final String COL_ID = "id";
	public static final String COL_STIME = "s_time";
	public static final String COL_ETIME = "e_time";
	public static final String COL_EXP_TIME = "exp_time";
	public static final String COL_USER_ID = "user_id";
	public static final String COL_ITEM_ID = "item_id";
	
	// projection on all columns
	private static final String[] PROJECTION_ALL = new String[] {
		COL_ID, COL_STIME, COL_ETIME, COL_EXP_TIME, COL_USER_ID, COL_ITEM_ID
	};
	
	// query output type
	public static final int QUERY_TYPE_STRING_ARRAY = 0x01;
	public static final int QUERY_TYPE_PRODUCTION_OBJ = 0x02;
	
	// declared fields
	private Context mContext;
	private SQLiteDatabase mDb;
	private UserDbHelper mDbHelper;
	
	/*
	 * constructor
	 */
	public ProductionDbAdapter(Context c) {
		mContext = c;
	}
	
	/*
	 * open database connection
	 */
	public ProductionDbAdapter open() throws SQLException {
		mDbHelper = new UserDbHelper(mContext);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	/*
	 * close database connection
	 */
	public void close() {
		mDbHelper.close();
	}
	
	/*
	 * insert a record to db
	 */
	public long insertProduct(ProductionInfo pInfo) {
		return mDb.insert(TBL_INFO, null, 
			createContentValues(
					pInfo.getItemID(), 
					pInfo.getUserID(), 
					pInfo.getStartTime(), 
					pInfo.getEndTime(), 
					pInfo.getExpireTime()));
	}
	
	private boolean isExistingCount()
	{
		boolean isDataExists = false;
		String strColumn[] = { COL_ID }; 
		
		Cursor queryCursor = mDb.query(TBL_INFO, strColumn, null, null, null, null, null );
		
		try
		{
			if(queryCursor.getCount() > Global.PRODUCTION_INIT_COUNT) {
				isDataExists = true;
				Log.d(TAG, "Count exists.....!!!!!!!!!!!!!!!!!!!!!");
			}
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
	
	/*
	 * insert a record to db
	 */
	public long insertInitProduct(int itemId, String userId, String startTime, String endTime, String expireTime) {
		if(isExistingCount()) return -1;
		Log.d(TAG, itemId + " " + userId + " " + startTime + " " + endTime + " " + expireTime);
		return mDb.insert(TBL_INFO, null, createContentValues(itemId, userId, startTime, endTime, expireTime));
	}
	
	public long insertProduct(int itemId, String userId, String startTime, String endTime, String expireTime) {
		Log.d(TAG, "[insert] " + itemId + " " + userId + " " + startTime + " " + endTime + " " + expireTime);
		return mDb.insert(TBL_INFO, null, createContentValues(itemId, userId, startTime, endTime, expireTime));
	}
	
	/*
	 * update a record to db
	 */
	public long updateProduct(int itemId, String userId, String startTime, String endTime, String expireTime) {
		Log.d(TAG, "[update] " + itemId + " " + userId + " " + startTime + " " + endTime + " " + expireTime);
		return mDb.update(TBL_INFO, createContentValues(itemId, userId, startTime, endTime, expireTime), COL_ID + "=" + itemId, null);
	}
	
	/*
	 * delete a record from db
	 */
	public long deleteProduct(int itemid, String userId) {
		String strSelection = COL_ITEM_ID + "=" + itemid +" AND " + COL_USER_ID + "='" + userId + "'"; 
		return mDb.delete(TBL_INFO, strSelection, null);
	}
	
	/*
	 * delete a record from db
	 */
	public long deleteAllProduct() {
		return mDb.delete(TBL_INFO, null, null);
	}
	
	
	/*
	 * query all records
	 */
	public List<ProductionInfo> fetchAllProducts() {
		// get query cursor
		Cursor queryCursor = mDb.query(TBL_INFO, PROJECTION_ALL, null, null, null, null, null);
		// just return null if cursor null
		if(queryCursor == null) {
			Log.d(TAG, "UserDbAdapter.fetchAllUsers(): queryCursor = null "); 
			return null;
		}
		// init list to hold user info
		List<ProductionInfo> listProduct = new ArrayList<ProductionInfo>();
		// set cursor to the first element
		queryCursor.moveToFirst();
		// if cursor is not the last element
		while(queryCursor.isAfterLast() == false) {
			int itemId = queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_ITEM_ID));
			String userId = queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_USER_ID));
			String startTime = queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_STIME));
			String endTime = queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_ETIME));
			String expireTime = queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_EXP_TIME));
			//Log.d(TAG, "[fetchAllProducts] " + itemId + " " + userId + " " + startTime + " " + endTime + " " + expireTime);
			
			// add new user info
			listProduct.add(new ProductionInfo(itemId,userId,startTime,endTime,expireTime));
			
			// move cursor to next item
			queryCursor.moveToNext();
			
			userId = startTime = endTime = expireTime = null;		
		}
		// check if cursor is still opened and not null
		if(queryCursor != null && !queryCursor.isClosed()) {
			// close it to avoid memory leak
			queryCursor.close();
		}
		Log.d(TAG, "fetchAllProducts(): listProduct.size() = " + listProduct.size());
		// return user list
		return listProduct;
	}
	
	/*
	 * query one record
	 */
	public Object fetchSingleProduct(int id, String userid, int type) {
		String strSelection = COL_ITEM_ID + "=" + id +" AND " + COL_USER_ID + "='" + userid + "'"; 
		// query a cursor on identified user
		Cursor c = mDb.query(true, TBL_INFO, PROJECTION_ALL, strSelection, null, null, null, null, null);
		// return null if no record avaiable
		if(c == null) {
			return null;
		}
		
		Object objOut = null;
		
		if(type == QUERY_TYPE_STRING_ARRAY) {
			// create array to hold user info
			String[] product_info = new String[5];
			product_info[0] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_ITEM_ID)));
			product_info[1] = c.getString(c.getColumnIndexOrThrow(COL_USER_ID));
			product_info[2] = c.getString(c.getColumnIndexOrThrow(COL_STIME));
			product_info[3] = c.getString(c.getColumnIndexOrThrow(COL_ETIME));
			product_info[4]= c.getString(c.getColumnIndexOrThrow(COL_EXP_TIME));

			objOut = product_info;
		} else {
			
			// create UserInfo object
			ProductionInfo product_info = new ProductionInfo(
					c.getInt(c.getColumnIndexOrThrow(COL_ITEM_ID)),
					c.getString(c.getColumnIndexOrThrow(COL_USER_ID)),
					c.getString(c.getColumnIndexOrThrow(COL_STIME)),
					c.getString(c.getColumnIndexOrThrow(COL_ETIME)),
					c.getString(c.getColumnIndexOrThrow(COL_EXP_TIME))
			);
			objOut = product_info;
		}
		// close cursor 
		c.close();
		
		// return user info
		return objOut;		
	}
	
	/*
	 * create ContentValues object to use for db transaction
	 */
	private ContentValues createContentValues(
		int itemId, String userid, String startTime, String endTime, String expireTime) {
		
		// init a ContentValues object
		ContentValues cv = new ContentValues();
		// put data
		cv.put(COL_ITEM_ID, itemId);
		cv.put(COL_USER_ID, userid);
		cv.put(COL_STIME, startTime);
		cv.put(COL_ETIME, endTime);
		cv.put(COL_EXP_TIME, expireTime);

		// return object
		return cv;
	}
}

