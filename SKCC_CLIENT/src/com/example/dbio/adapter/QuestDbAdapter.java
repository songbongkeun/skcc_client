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
import com.example.skcc_client.gameObject.Quest;
import com.example.dbio.assist.ItemDbHelper;

public class QuestDbAdapter {
	private static final String TAG = QuestDbAdapter.class.getSimpleName();
	
	// declare database fields
	public static final String TBL_INFO = "quest";
	public static final String COL_PLAYER_ID = "player_id";
	public static final String COL_QUEST_ID = "quest_id";
	public static final String COL_TITLE = "title";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_START_TIME = "start_time";
	public static final String COL_END_TIME = "end_time";
	public static final String COL_COMPLETE_TIME = "complete_time";
	
	// projection on all columns
	private static final String[] PROJECTION_ALL = new String[] {
		COL_PLAYER_ID, COL_QUEST_ID, COL_TITLE, COL_DESCRIPTION, COL_START_TIME, COL_END_TIME, COL_COMPLETE_TIME
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
	public QuestDbAdapter(Context c) {
		mContext = c;
	}
	
	/*
	 * open database connection
	 */
	public QuestDbAdapter open() throws SQLException {
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
	
	private boolean isExistingCount() {
		
		boolean isDataExists = false;
		String strColumn[] = { COL_PLAYER_ID, COL_QUEST_ID }; 
		
		Cursor queryCursor = mDb.query(TBL_INFO, strColumn, null, null, null, null, null );
		
		
		try {
			
			if(queryCursor.getCount() > Global.ITEM_INIT_COUNT) isDataExists = true;
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
	
	private boolean isExistingQuest(String playerId, int questId) {
		
		String strColumn[] = { COL_PLAYER_ID, COL_QUEST_ID }; 
		String strSelection = COL_PLAYER_ID + "='" + playerId + "'"
				+ " AND " + COL_QUEST_ID + "=" + questId; 
		
		Cursor queryCursor = mDb.query(TBL_INFO, strColumn, strSelection, null, null, null, null );
		
		if(queryCursor == null) {

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
	
	public long insertInitQuest(String playerId, int questId, String title, String description, long startTime, long endTime, long completeTime) {
		
		if(isExistingQuest(playerId, questId) || isExistingCount()) return -1;

		long ret = mDb.insert(TBL_INFO, null, createContentValues(playerId, questId, title, description, startTime, endTime, completeTime));
		
		if(ret == -1) Log.d(TAG,"Failed...item[" + questId + "]");
		
		return ret;
	}
	
	/**
	 * insert a record to db
	 * @param playerId	플레이어 id
	 * @param quest		퀘스트
	 */
	public long insertQuest(String playerId, Quest quest) {
		
		if(isExistingQuest(playerId, quest.getId())) return -1;

		long ret = mDb.insert(TBL_INFO, null, createContentValues(playerId, quest));
		
		if(ret == -1) Log.d(TAG, "Failed...item[" + quest.getId() + "]");
		
		Log.d(TAG, "QuestDbAdapter.insertItem() : insert quest = " + quest.getId() + " / " + quest.getTitle());
		
		return ret;
	}
	
	/**
	 * update a record to db
	 * @param playerId	플레이어 id
	 * @param quest		퀘스트
	 */
	public long updateQuest(String playerId, Quest quest) {
		
		String whereClause = COL_PLAYER_ID + "='" + playerId + "'"
				+" AND " + COL_QUEST_ID + "=" + quest.getId();
		
		long ret = mDb.update(TBL_INFO, createContentValues(playerId, quest), whereClause, null);

		if(ret == -1) {
			
			Log.d(TAG,"Update failed... quest[" + quest.getId() + "]");
		}
		
		Log.d(TAG, "QuestDbAdapter.updateItem() : update quest = " + quest.getId() + " / " + quest.getTitle());
		
		return ret;
	}
	
	/**
	 * delete a record from db
	 * @param item	생산 아이템
	 */
	public long deleteQuest(String playerId, Quest quest) {
		
		String whereClause = COL_PLAYER_ID + "='" + playerId + "'"
				+" AND " + COL_QUEST_ID + "=" + quest.getId();
		
		long ret = mDb.delete(TBL_INFO, whereClause, null);
		
		if(ret == -1) {
			
			Log.d(TAG,"Delete failed... quest[" + quest.getId() + "]");
		}
		
		Log.d(TAG, "QuestDbAdapter.deleteProduct() at " + quest.getId());
		
		return ret; 
	}
	
	/**
	 * delete all record from db
	 */
	public long deleteAllQuests() {
		
		return mDb.delete(TBL_INFO, null, null);
	}
	
	/**
	 * query all records
	 * @return	ArrayList<Quest>
	 */
	public ArrayList<Quest> fetchAllQuests() {
		
		// get query cursor
		Cursor queryCursor = mDb.query(TBL_INFO, PROJECTION_ALL, null, null, null, null, null);
		
		// just return null if cursor null
		if(queryCursor == null) {
			
			Log.d(TAG, "QuestDbAdapter.fetchAllUsers(): queryCursor = null "); 
			return null;
		}
		
		// init list to hold user info
		ArrayList<Quest> listQuests = new ArrayList<Quest>();
		
		// set cursor to the first element
		queryCursor.moveToFirst();
		
		// if cursor is not the last element
		int questId;
		String title, description;
		long startTime, endTime, completeTime;
		
		while(queryCursor.isAfterLast() == false) {

			questId = queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_QUEST_ID));
			title = queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_TITLE));
			description = queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_DESCRIPTION));
			startTime = queryCursor.getLong(queryCursor.getColumnIndexOrThrow(COL_START_TIME));
			endTime = queryCursor.getLong(queryCursor.getColumnIndexOrThrow(COL_END_TIME));
			completeTime = queryCursor.getLong(queryCursor.getColumnIndexOrThrow(COL_COMPLETE_TIME));

			Timestamp stt = new Timestamp(startTime);
			Timestamp end = new Timestamp(endTime);
			Timestamp cpl = new Timestamp(completeTime);
			
			Quest quest = new Quest(questId, title, description, stt, end, cpl);
			
			// add new quest info
			listQuests.add(quest);
			
			// move cursor to next item
			queryCursor.moveToNext();
		}
		
		// check if cursor is still opened and not null
		if(queryCursor != null && !queryCursor.isClosed()) {
			
			// close it to avoid memory leak
			queryCursor.close();
		}
		
		Log.d(TAG, "QuestDbAdapter.fetchAllItems(): listItems.size() = " + listQuests.size());
		
		// return user list
		return listQuests;
	}
	
	/**
	 * query one record
	 */
	public Object fetchSingleQuest(String playerId, int questId, int type) {
		
		String whereClause = COL_PLAYER_ID + "='" + playerId + "'"
				+" AND " + COL_QUEST_ID + "=" + questId;
		
		// query a cursor on identified user
		Cursor c = mDb.query(true, TBL_INFO, PROJECTION_ALL, whereClause, null, null, null, null, null);
		
		// return null if no record avaiable
		if(c == null) {
			
			return null;
		}
		
		Object objOut = null;

		// create array to hold user info
		if(type == QUERY_TYPE_STRING_ARRAY) {
			
			String[] quest_info = new String[5];
			
			quest_info[0] = String.valueOf(questId);
			quest_info[1] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_TITLE)));
			quest_info[2] = String.valueOf(c.getInt(c.getColumnIndexOrThrow(COL_DESCRIPTION)));
			quest_info[3] = c.getString(c.getColumnIndexOrThrow(COL_START_TIME));
			quest_info[4] = c.getString(c.getColumnIndexOrThrow(COL_END_TIME));
			quest_info[5] = c.getString(c.getColumnIndexOrThrow(COL_COMPLETE_TIME));
			
			objOut = quest_info;
		}

		// create UserInfo object
		else {

			String title = c.getString(c.getColumnIndexOrThrow(COL_TITLE));
			String description = c.getString(c.getColumnIndexOrThrow(COL_DESCRIPTION));
			long startTime = c.getLong(c.getColumnIndexOrThrow(COL_START_TIME));
			long endTime = c.getLong(c.getColumnIndexOrThrow(COL_END_TIME));
			long completeTime = c.getLong(c.getColumnIndexOrThrow(COL_COMPLETE_TIME));

			Timestamp stt = new Timestamp(startTime);
			Timestamp end = new Timestamp(endTime);
			Timestamp cpl = new Timestamp(completeTime);
			
			Quest quest = new Quest(questId, title, description, stt, end, cpl);
			
			objOut = quest;
		}
		
		// close cursor 
		c.close();
		
		return objOut;		
	}
	
	/**
	 * create ContentValues object to use for db transaction
	 * @param playeId	플레이어 id
	 * @param quest		퀘스트
	 * @return
	 */
	private ContentValues createContentValues(String playeId, Quest quest) {
		
		// init a ContentValues object
		ContentValues cv = new ContentValues();
		
		// put data
		cv.put(COL_PLAYER_ID, playeId);
		cv.put(COL_QUEST_ID, quest.getId());
		cv.put(COL_TITLE, quest.getTitle());
		cv.put(COL_DESCRIPTION, quest.getDescription());
		cv.put(COL_START_TIME, quest.getStartTime().getTime());
		cv.put(COL_END_TIME, quest.getEndTime().getTime());
		cv.put(COL_COMPLETE_TIME, quest.getCompleteTime().getTime());
		
		// return object
		return cv;
	}
	
	/**
	 * create ContentValues object to use for db transaction
	 * @param playeId		플레이어 id
	 * @param questId		퀘스트 id
	 * @param title			제목
	 * @param description	설명
	 * @param startTime		시작시각
	 * @param endTime		종료시각
	 * @param completeTime	완료시각
	 * @return
	 */
	private ContentValues createContentValues(String playeId, int questId, String title, String description, long startTime, long endTime, long completeTime) {
		
		// init a ContentValues object
		ContentValues cv = new ContentValues();
		
		// put data
		cv.put(COL_PLAYER_ID, playeId);
		cv.put(COL_QUEST_ID, questId);
		cv.put(COL_TITLE, title);
		cv.put(COL_DESCRIPTION, description);
		cv.put(COL_START_TIME, startTime);
		cv.put(COL_END_TIME, endTime);
		cv.put(COL_COMPLETE_TIME, completeTime);
		
		// return object
		return cv;
	}
}
