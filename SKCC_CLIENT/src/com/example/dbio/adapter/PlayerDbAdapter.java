package com.example.dbio.adapter;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.Player;
import com.example.dbio.assist.PlayerDbHelper;

public class PlayerDbAdapter {
	
	private static final String TAG = "PLAYER";
	private static final boolean USE_DEBUG = false;
	
	// declare database fields
	public static final String TBL_INFO = "player";
	
	public static final String COL_ID = "id";
	public static final String COL_EXP = "exp";
	public static final String COL_ACTION_POINT = "action_point";
	public static final String COL_MONEY = "money";
	public static final String COL_NAME = "name";
	
	// projection on all columns
	private static final String[] PROJECTION_ALL = new String[] {
		COL_ID, COL_NAME, COL_EXP, COL_ACTION_POINT, COL_MONEY
	};
	
	// query output type
	public static final int QUERY_TYPE_STRING_ARRAY = 0x01;
	public static final int QUERY_TYPE_USERINFO_OBJ = 0x02;
	
	// declared fields
	private Context mContext;
	private SQLiteDatabase mDb;
	private PlayerDbHelper mDbHelper;
	
	/*
	 * constructor
	 */
	public PlayerDbAdapter(Context c) {
		
		mContext = c;
	}
	
	/*
	 * open database connection
	 */
	public PlayerDbAdapter open() throws SQLException {
		
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
	
	private boolean isDataExists() {
		
		boolean isDataExists = false;
		
		String strColumn[] = { COL_ID }; 
		
		Cursor queryCursor = mDb.query(TBL_INFO, strColumn, null, null, null, null, null );
		
		
		try {
			
			if(queryCursor.getCount() > Global.USER_INIT_COUNT) isDataExists = true;
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
	
	public long insertInitUser(String id, String name, long experience, int actionPoint, long money) {
		
		if(isExistingUser(id) || isDataExists()) {
			
			if(USE_DEBUG) {
				
				Log.d(TAG, "id = " + id + " exists!!");
			}
			
			return -1;
		}
		
		return mDb.insert(TBL_INFO, null, createContentValues(id, experience, actionPoint, name, money));
	}
	
	/*
	 * insert a record to db
	 */
	public long insertUser(String id, String name, long experience, int actionPoint, long money) {
		
		if(isExistingUser(id)) {
			
			if(USE_DEBUG) { 
				
				Log.d(TAG, "id = " + id + " exists!!");
			}
			
			return -1;
		}
		
		return mDb.insert(TBL_INFO, null, createContentValues(id, experience, actionPoint, name, money));
	}
	
	/*
	 * update a record to db
	 */
	public long updatePlayer(Player player) {
		
		String whereClause = COL_ID + "='" + player.getId() + "'"; 

		if(!isExistingUser(player.getId())) {
			
			return -1;
		}
		
		long ret = mDb.update(TBL_INFO, createContentValues(player), whereClause, null);
		
		if(ret == -1) {
			
			Log.d(TAG, "Failed...player[" + player.getId() + "]");
		}
		
		Log.d(TAG, "PlayerDbAdapter.updatePlayer() : update player = " + player.getId());
		
		return ret;
	}
	
	/*
	 * delete a record from db
	 */
	public long deleteUser(String id) {
		
		if(!isExistingUser(id)) {
			
			return -1;
		}
		
		return mDb.delete(TBL_INFO, COL_ID + "=" + id, null);
	}
	
	private boolean isExistingUser(String id) {
		
		String strColumn[] = { COL_NAME, COL_ID }; 
		String strSelection = COL_ID + "='" + id +"'"; 
		
		Cursor queryCursor = mDb.query(TBL_INFO, strColumn, strSelection, null, null, null, null );
		
		if(queryCursor == null) {
			
			Log.d(TAG, "PlayerDbAdapter.isExistingUser(): queryCursor not initialized!!"); 
			
			return true;
		}
		try {
			
			if( queryCursor.getCount() == 1) {
				
				return true;
			}
		} 
		finally {
			
			// check if cursor is still opened and not null
			if(queryCursor != null && !queryCursor.isClosed()) {
				
				// close it to avoid memory leak
				queryCursor.close();
			}
		}
		
		Log.d(TAG, "PlayerDbAdapter.isExistingUser() : " + id + " is not a player!!");
		
		return false;
	}
	
	/*
	 * query all records
	 */
	public ArrayList<Player> fetchAllPlayers() {
		
		// get query cursor
		Cursor queryCursor = mDb.query(TBL_INFO, PROJECTION_ALL, null, null, null, null, null);
		
		// just return null if cursor null
		if(queryCursor == null) {
			
			Log.d(TAG, "UserDbAdapter.fetchAllUsers(): queryCursor = null "); 
			return null;
		}
		
		// init list to hold user info
		ArrayList<Player> list = new ArrayList<Player>();
		
		// set cursor to the first element
		queryCursor.moveToFirst();
		
		// if cursor is not the last element
		while(queryCursor.isAfterLast() == false) {
			
			// add new user info
			list.add(new Player(
						queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_ID)),
						queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_NAME)),
						queryCursor.getLong(queryCursor.getColumnIndexOrThrow(COL_EXP)),
						queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_ACTION_POINT)),
						queryCursor.getLong(queryCursor.getColumnIndexOrThrow(COL_MONEY))
					));
			
			// move cursor to next item
			queryCursor.moveToNext();
		}
		
		// check if cursor is still opened and not null
		if(queryCursor != null && !queryCursor.isClosed()) {
			
			// close it to avoid memory leak
			queryCursor.close();
		}
		
		Log.d(TAG, "fetchAllUsers(): listUsers.size() = " + list.size());
		
		// return user list
		return list;
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

		// create array to hold user info
		if(type == QUERY_TYPE_STRING_ARRAY) {
			
			String[] playerInfo = new String[5];
			
			playerInfo[0] = id;
			playerInfo[1] = c.getString(c.getColumnIndexOrThrow(COL_NAME));
			playerInfo[2] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_EXP)));
			playerInfo[3] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_ACTION_POINT)));
			playerInfo[4] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_MONEY)));
			
			objOut = playerInfo;
		}
		
		// create UserInfo object
		else {
			
			Player player = new Player(
					id,
					c.getString(c.getColumnIndexOrThrow(COL_NAME)),
					c.getLong(c.getColumnIndexOrThrow(COL_EXP)),
					c.getInt(c.getColumnIndexOrThrow(COL_ACTION_POINT)),
					c.getLong(c.getColumnIndexOrThrow(COL_MONEY))
					);
			objOut = player;
		}
		
		// close cursor 
		c.close();
		
		// return user info
		return objOut;
	}
	
	
	/**
	 * Create ContentValues object to use for db transaction
	 * @param id			Player id
	 * @param exp			Exp
	 * @param actionPoint	Action point
	 * @param name			Name
	 * @param money			Money
	 * @return
	 */
	private ContentValues createContentValues(String id, long exp, int actionPoint, String name, long money) {
		
		// init a ContentValues object
		ContentValues cv = new ContentValues();
		
		// put data
		cv.put(COL_ID, id);
		cv.put(COL_NAME, name);
		cv.put(COL_EXP, exp);
		cv.put(COL_ACTION_POINT, actionPoint);
		cv.put(COL_MONEY, money);
		
		// return object
		return cv;
	}
	
	/**
	 * Create ContentValues object to use for db transaction
	 * @param player	Player object
	 * @return
	 */
	private ContentValues createContentValues(Player player) {
		
		// init a ContentValues object
		ContentValues cv = new ContentValues();
		
		// put data
		cv.put(COL_ID, player.getId());
		cv.put(COL_NAME, player.getName());
		cv.put(COL_EXP, player.getExp());
		cv.put(COL_ACTION_POINT, player.getActionPoint());
		cv.put(COL_MONEY, player.getMoney());
		
		// return object
		return cv;
	}
}

