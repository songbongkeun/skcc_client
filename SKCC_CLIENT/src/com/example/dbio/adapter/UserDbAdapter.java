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
import com.example.dbio.data.UserInfo;

public class UserDbAdapter {
	private static final String TAG = UserDbAdapter.class.getSimpleName();
	private static final boolean USE_DEBUG = false;
	// declare database fields
	public static final String TBL_INFO = "user";
	public static final String COL_ID = "usr_id";
	public static final String COL_EXP = "experience";
	public static final String COL_ACT_PNT = "act_point";
	public static final String COL_MONEY = "money";
	public static final String COL_NAME = "name";
	public static final String COL_DESC = "description";
	
	// projection on all columns
	private static final String[] PROJECTION_ALL = new String[] {
		COL_ID, COL_EXP, COL_ACT_PNT, COL_NAME, COL_MONEY, COL_DESC
	};
	
	// query output type
	public static final int QUERY_TYPE_STRING_ARRAY = 0x01;
	public static final int QUERY_TYPE_USERINFO_OBJ = 0x02;
	
	// declared fields
	private Context mContext;
	private SQLiteDatabase mDb;
	private UserDbHelper mDbHelper;
	
	/*
	 * constructor
	 */
	public UserDbAdapter(Context c) {
		mContext = c;
	}
	
	/*
	 * open database connection
	 */
	public UserDbAdapter open() throws SQLException {
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
	
	private boolean isExistingCount()
	{
		boolean isDataExists = false;
		String strColumn[] = { COL_ID }; 
		
		Cursor queryCursor = mDb.query(TBL_INFO, strColumn, null, null, null, null, null );
		
		
		try
		{
			if(queryCursor.getCount() > Global.USER_INIT_COUNT) isDataExists = true;
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
	
	public long insertInitUser(String id, int experience, int actionPoint, String name, int money, String desc) {
		if(isExistingUser(id) || isExistingCount())
		{
			if(USE_DEBUG) 
				Log.d(TAG, "id = " + id + " exists!!");
			
			return -1;
		}
		
		return mDb.insert(TBL_INFO, null, createContentValues(id, experience, actionPoint, name, money, desc));
	}
	
	/*
	 * insert a record to db
	 */
	public long insertUser(String id, int experience, int actionPoint, String name, int money, String desc) {
		
		if(isExistingUser(id))
		{
			if(USE_DEBUG) 
				Log.d(TAG, "id = " + id + " exists!!");
			
			return -1;
		}
		
		return mDb.insert(TBL_INFO, null, createContentValues(id, experience, actionPoint, name, money, desc));
	}
	
	/*
	 * update a record to db
	 */
	public long updateUser(String id,  int experience, int actionPoint, String name, int money, String desc) {
		if(!isExistingUser(id)) return -1;
		
		return mDb.update(TBL_INFO, createContentValues(id, experience, actionPoint,  name, money, desc), COL_ID + "=" + id, null);
	}
	
	/*
	 * delete a record from db
	 */
	public long deleteUser(String id) {
		if(!isExistingUser(id)) return -1;
		
		return mDb.delete(TBL_INFO, COL_ID + "=" + id, null);
	}
	
	private boolean isExistingUser(String id)
	{
		String strColumn[] = { COL_NAME, COL_ID }; 
		String strSelection = COL_ID + "='" + id +"'"; 
		
		Cursor queryCursor = mDb.query(TBL_INFO, strColumn, strSelection, null, null, null, null );
		
		if(queryCursor == null) {
			Log.d(TAG, "UserDbAdapter.fetchAllUsers(): queryCursor = null "); 
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
	 * query all records
	 */
	public List<UserInfo> fetchAllUsers() {
		// get query cursor
		Cursor queryCursor = mDb.query(TBL_INFO, PROJECTION_ALL, null, null, null, null, null);
		// just return null if cursor null
		if(queryCursor == null) {
			Log.d(TAG, "UserDbAdapter.fetchAllUsers(): queryCursor = null "); 
			return null;
		}
		// init list to hold user info
		List<UserInfo> listUsers = new ArrayList<UserInfo>();
		// set cursor to the first element
		queryCursor.moveToFirst();
		// if cursor is not the last element
		while(queryCursor.isAfterLast() == false) {
			// add new user info
			listUsers.add(new UserInfo(
					// get user id from cursor
					queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_ID)),
					// get user id from cursor
					queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_EXP)),
					// get user id from cursor
					queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_ACT_PNT)),
					// get user name from cursor
					queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_NAME)),
					// get user money from cursor
					queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_MONEY)),
					// get user city from cursor
					queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_DESC))					
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
		Log.d(TAG, "fetchAllUsers(): listUsers.size() = " + listUsers.size());
		// return user list
		return listUsers;
	}
	
	/*
	 * query one record
	 */
	public Object fetchSingleUser(String id, int type) {
		String strSelection = COL_ID + "='" + id +"'"; 
		// query a cursor on identified user
		Cursor c = mDb.query(true, TBL_INFO, PROJECTION_ALL, strSelection, null, null, null, null, null);
		// return null if no record avaiable
		if(c == null) {
			return null;
		}
		
		Object objOut = null;
		
		if(type == QUERY_TYPE_STRING_ARRAY) {
			// create array to hold user info
			String[] user_info = new String[6];
			user_info[0] = id;
			user_info[1] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_EXP)));
			user_info[2] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_ACT_PNT)));
			user_info[3] = c.getString(c.getColumnIndexOrThrow(COL_NAME));
			user_info[4] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_MONEY)));
			user_info[5] = c.getString(c.getColumnIndexOrThrow(COL_DESC));
			objOut = user_info;
		} else {
			// create UserInfo object
			UserInfo user_info = new UserInfo(
					id,
					c.getInt(c.getColumnIndexOrThrow(COL_EXP)),
					c.getInt(c.getColumnIndexOrThrow(COL_ACT_PNT)),
					c.getString(c.getColumnIndexOrThrow(COL_NAME)),
					c.getInt(c.getColumnIndexOrThrow(COL_MONEY)),
					c.getString(c.getColumnIndexOrThrow(COL_DESC))					
			);
			objOut = user_info;
		}
		// close cursor 
		c.close();
		
		// return user info
		return objOut;		
	}
	
	
	
	/*
	 * create ContentValues object to use for db transaction
	 */
	private ContentValues createContentValues(String id, int experience, int actionPoint, String name, int money, String desc) {
		// init a ContentValues object
		ContentValues cv = new ContentValues();
		// put data
		cv.put(COL_ID, id);
		cv.put(COL_EXP, experience);
		cv.put(COL_ACT_PNT, actionPoint);
		cv.put(COL_NAME, name);
		cv.put(COL_MONEY, money);
		cv.put(COL_DESC, desc);
		// return object
		return cv;
	}
	
	

}

