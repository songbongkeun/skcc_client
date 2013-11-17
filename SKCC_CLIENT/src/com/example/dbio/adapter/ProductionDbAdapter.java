package com.example.dbio.adapter;

import java.sql.Timestamp;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.Item;
import com.example.skcc_client.gameObject.ProductionItem;
import com.example.dbio.assist.PlayerDbHelper;

public class ProductionDbAdapter {
	
	private static final String TAG = "PRODUCTION";
	
	public static final String TBL_INFO = "production";
	public static final String COL_PLAYER_ID = "player_id";
	public static final String COL_POSITION = "position";
	public static final String COL_ITEM_ID = "item_id";
	public static final String COL_START_TIME = "start_time";
	public static final String COL_END_TIME = "end_time";
	public static final String COL_EXPIRE_TIME = "expire_time";
	
	// projection on all columns
	private static final String[] PROJECTION_ALL = new String[] {
		COL_PLAYER_ID, COL_POSITION, COL_ITEM_ID, COL_START_TIME, COL_END_TIME, COL_EXPIRE_TIME 
	};
	
	// query output type
	public static final int QUERY_TYPE_STRING_ARRAY = 0x01;
	public static final int QUERY_TYPE_PRODUCTION_OBJ = 0x02;
	
	// declared fields
	private Context mContext;
	private SQLiteDatabase mDb;
	private PlayerDbHelper mDbHelper;
	
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
		
		mDbHelper = new PlayerDbHelper(mContext);
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
			
			if(queryCursor.getCount() > Global.PRODUCTION_INIT_COUNT) {
				
				isDataExists = true;
			}
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

	
	/*
	 * insert a record to db
	 */
	public long insertInitProduct(String playerId, int position, int itemId, long startTime, long endTime, long expireTime) {
		
		if(isExistingCount()) return -1;
		
		long ret = mDb.insert(TBL_INFO, null, createContentValues(playerId, position, itemId, startTime, endTime, expireTime));
		
		return ret; 
	}
	
	/*
	 * insert a record to db
	 */
	public long insertProduct(ProductionItem item) {
		
		long ret = mDb.insert(TBL_INFO, null, createContentValues(item));
		
		if(ret == -1) {
			
			Log.d(TAG,"Failed...user[" + Global.THIS_USER + "]");
		}
		
		Log.d(TAG, "ProductionDbAdapter.insertProduct() : insert item = " + item.getPosition() + " / " + item.getId() + " - " + item.getName());
		
		return ret;
	}
	
	public long insertProduct(String playerId, int position, int itemId, long startTime, long endTime, long expireTime) {

		long ret = mDb.insert(TBL_INFO, null, createContentValues(playerId, position, itemId, startTime, endTime, expireTime));
		
		if(ret == -1) {
			
			Log.d(TAG,"Failed...user[" + Global.THIS_USER + "]");
		}
		
		Log.d(TAG, "ProductionDbAdapter.insertProduct() : insert item = " + position + " / " + itemId);
		
		return ret;
	}
	
	/**
	 * update a record to db
	 * @param item	생산 아이템
	 */
	public long updateProduct(ProductionItem item) {
		
		String whereClause = COL_PLAYER_ID + "='" + Global.THIS_USER + "'"
				+" AND " + COL_POSITION + "=" + item.getPosition();
		
		long ret = mDb.update(TBL_INFO, createContentValues(item), whereClause, null);
		
		if(ret == -1) {
			
			Log.d(TAG,"Update failed... position[" + item.getPosition() + "]");
		}
		
		Log.d(TAG, "ProductionDbAdapter.updateProduct() : update item = " + item.getPosition() + " / " + item.getId());
		
		return ret;
	}
	
	/**
	 * delete a record from db
	 * @param item	생산 아이템
	 */
	public long deleteProduct(int position) {

		String whereClause = COL_PLAYER_ID + "='" + Global.THIS_USER + "'"
				+" AND " + COL_POSITION + "=" + position;
		
		long ret = mDb.delete(TBL_INFO, whereClause, null);
		
		if(ret == -1) {
			
			Log.d(TAG,"Delete failed... position[" + position + "]");
		}
		
		Log.d(TAG, "ProductionDbAdapter.deleteProduct() at " + position);
		
		return ret; 
	}
	
	/**
	 * delete all record from db
	 */
	public long deleteAllProduct() {
		
		return mDb.delete(TBL_INFO, null, null);
	}

	
	/**
	 * query all records
	 * @return	ArrayList<ProductionItem>
	 */
	public ArrayList<ProductionItem> fetchAllProducts() {
		
		// get query cursor
		Cursor queryCursor = mDb.query(TBL_INFO, PROJECTION_ALL, null, null, null, null, null);
		
		// just return null if cursor null
		if(queryCursor == null) {
			
			Log.d(TAG, "ProductionDbAdapter.fetchAllUsers(): queryCursor = null "); 
			return null;
		}
		
		// init list to hold user info
		ArrayList<ProductionItem> listProduct = new ArrayList<ProductionItem>();
		
		// set cursor to the first element
		queryCursor.moveToFirst();
		
		// if cursor is not the last element
		int itemId, position;
		long startTime, endTime, expireTime;
		
		while(queryCursor.isAfterLast() == false) {

			position = queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_POSITION));
			itemId = queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_ITEM_ID));
			startTime = queryCursor.getLong(queryCursor.getColumnIndexOrThrow(COL_START_TIME));
			endTime = queryCursor.getLong(queryCursor.getColumnIndexOrThrow(COL_END_TIME));
			expireTime = queryCursor.getLong(queryCursor.getColumnIndexOrThrow(COL_EXPIRE_TIME));
			
			Item item = Global.getInstance().itemList.get(itemId);
			Timestamp stt = new Timestamp(startTime);
			Timestamp end = new Timestamp(endTime);
			Timestamp exp = new Timestamp(expireTime);
			
			// add new user info
			listProduct.add(new ProductionItem(position, item, stt, end, exp));
			
			// move cursor to next item
			queryCursor.moveToNext();
		}
		
		// check if cursor is still opened and not null
		if(queryCursor != null && !queryCursor.isClosed()) {
			
			// close it to avoid memory leak
			queryCursor.close();
		}
		
		Log.d(TAG, "ProductionDbAdapter.fetchAllUsers().size() = " + listProduct.size());
		
		return listProduct;
	}
	
	/**
	 * query one record
	 * @param playerId	플레이어 id
	 * @param itemId	아이템 id
	 * @param position	생산 위치
	 * @param type		리턴 타입 = QUERY_TYPE_STRING_ARRAY, QUERY_TYPE_PRODUCTION_OBJ
	 * @return
	 */
	public Object fetchSingleProduct(String playerId, int position, int type) {

		String strSelection = COL_PLAYER_ID + "='" + Global.THIS_USER + "'"
				+" AND " + COL_POSITION + "=" + position;
		
		// query a cursor on identified user
		Cursor c = mDb.query(true, TBL_INFO, PROJECTION_ALL, strSelection, null, null, null, null, null);
		
		// return null if no record avaiable
		if(c == null) {
			
			return null;
		}
		
		Object objOut = null;
		
		if(type == QUERY_TYPE_STRING_ARRAY) {
			
			// create array to hold user info
			String[] product_info = new String[6];
			product_info[0] = c.getString(c.getColumnIndexOrThrow(COL_PLAYER_ID));
			product_info[1] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_POSITION)));
			product_info[2] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_ITEM_ID)));
			product_info[3] = c.getString(c.getColumnIndexOrThrow(COL_START_TIME));
			product_info[4] = c.getString(c.getColumnIndexOrThrow(COL_END_TIME));
			product_info[5]= c.getString(c.getColumnIndexOrThrow(COL_EXPIRE_TIME));

			objOut = product_info;
		}
		else {
			
			// create ProductionItem object
			Item item = Global.getInstance().itemList.get(c.getInt(c.getColumnIndexOrThrow(COL_ITEM_ID)));
			Timestamp startTime = new Timestamp(c.getLong(c.getColumnIndexOrThrow(COL_START_TIME)));
			Timestamp endTime = new Timestamp(c.getLong(c.getColumnIndexOrThrow(COL_END_TIME)));
			Timestamp expireTime = new Timestamp(c.getLong(c.getColumnIndexOrThrow(COL_EXPIRE_TIME)));
			
			ProductionItem productionItem = new ProductionItem(position, item, startTime, endTime, expireTime);
					
			objOut = productionItem;
		}
		
		// close cursor 
		c.close();
		
		return objOut;		
	}
	
	/**
	 * create ContentValues object to use for db transaction
	 * @param item	생산 아이템
	 * @return
	 */
	private ContentValues createContentValues(ProductionItem item) {
		
		// init a ContentValues object
		ContentValues cv = new ContentValues();
		
		// put data
		cv.put(COL_PLAYER_ID, Global.THIS_USER);
		cv.put(COL_POSITION, item.getPosition());
		cv.put(COL_ITEM_ID, item.getId());
		cv.put(COL_START_TIME, item.getStartTime().getTime());
		cv.put(COL_END_TIME, item.getEndTime().getTime());
		cv.put(COL_EXPIRE_TIME, item.getExpireTime().getTime());

		// return object
		return cv;
	}
	
	/**
	 * create ContentValues object to use for db transaction
	 * @param playerId		플레이어 id
	 * @param itemId		아이템 id
	 * @param position		생산 위치
	 * @param startTime		생산 시작 시각
	 * @param endTime		생산 종료 시각
	 * @param expireTime	유통기한
	 * @return
	 */
	private ContentValues createContentValues(String playerId, int position, int itemId, long startTime, long endTime, long expireTime) {
		
		// init a ContentValues object
		ContentValues cv = new ContentValues();
		
		// put data
		cv.put(COL_PLAYER_ID, playerId);
		cv.put(COL_POSITION, position);
		cv.put(COL_ITEM_ID, itemId);
		cv.put(COL_START_TIME, startTime);
		cv.put(COL_END_TIME, endTime);
		cv.put(COL_EXPIRE_TIME, expireTime);

		// return object
		return cv;
	}
}

