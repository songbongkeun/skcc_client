package com.example.dbio.assist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public abstract class CafeDbHelper extends SQLiteOpenHelper {
	
	private static final String TAG = CafeDbHelper.class.getSimpleName();
	
	// declare constants fields
	protected static final String DB_NAME = "cafe.db";
	protected static final int DB_VERSION = 2;

	
	// declared constant SQL Expression
	protected static final String DB_PLAYER_CREATE = 
		"CREATE TABLE IF NOT EXISTS player ( "
				 + "id text PRIMARY KEY"
				 + ", name text NOT NULL"
				 + ", exp integer NOT NULL"
				 + ", action_point integer NOT NULL"
				 + ", money integer NOT NULL"
				 + ");";
	
	// declared constant SQL Expression  
	protected static final String DB_ITEM_CREATE = 
		"CREATE TABLE IF NOT EXISTS item ( "
				+ "id integer PRIMARY KEY" 
				+ ", company_id integer NOT NULL"
				+ ", item_type integer NOT NULL" 
				+ ", name text NOT NULL"
				+ ", description text" 
				+ ");";
	
	// declared constant SQL Expression 
	protected static final String DB_INVENTORY_CREATE = 
		"CREATE TABLE IF NOT EXISTS inventory ( "
				+ "player_id text"
				+ ", item_id integer"
				+ ", quantity integer NOT NULL"
				+ ", PRIMARY KEY(player_id, item_id)"
				+ ", FOREIGN KEY(player_id) REFERENCES player(id)"
				+ ", FOREIGN KEY(item_id) REFERENCES item(id)"
				+ ");";
	
	// declared constant SQL Expression  
	protected static final String DB_PRODUCT_CREATE = 
		"CREATE TABLE IF NOT EXISTS production ( "
				+ "player_id text"
				+ ", position integer"
				+ ", item_id integer"
				+ ", start_time integer"
				+ ", end_time integer"
				+ ", expire_time integer"
				+ ", PRIMARY KEY(player_id, position)"
				+ ", FOREIGN KEY(player_id) REFERENCES player(id)"
				+ ", FOREIGN KEY(item_id) REFERENCES item(id)"
				+ ");";
	

	protected static final String DB_USER_DESTROY = "DROP TABLE IF EXISTS player";
	protected static final String DB_ITEM_DESTROY = "DROP TABLE IF EXISTS item";
	protected static final String DB_INVENTORY_DESTROY = "DROP TABLE IF EXISTS inventory";
	protected static final String DB_PRODUCT_DESTROY = "DROP TABLE IF EXISTS product";
	
	/*
	 * constructor
	 */
	public CafeDbHelper(Context context) {
		
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	/*@Override
	public void onOpen(SQLiteDatabase db)
	{
		Log.d(TAG, "[onCreate] SQLiteDatabase opened...");
	}*/
	
	/*
	 * (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		Log.d(TAG, "[onCreate] SQLiteDatabase created...");
		
		db.execSQL(DB_PLAYER_CREATE);
		Log.d(TAG, "[onCreate] User table created...");
		
		db.execSQL(DB_ITEM_CREATE);
		Log.d(TAG, "[onCreate] Item table created...");
		
		db.execSQL(DB_INVENTORY_CREATE);
		Log.d(TAG, "[onCreate] Inventory table created...");
		
		db.execSQL(DB_PRODUCT_CREATE);
		Log.d(TAG, "[onCreate] Production table created...");
	}

	/*
	 * (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL(DB_USER_DESTROY);
		Log.d(TAG, "[onUpgrade] User table droped!");
		
		db.execSQL(DB_ITEM_DESTROY);
		Log.d(TAG, "[onUpgrade] Item table droped!");
		
		db.execSQL(DB_INVENTORY_DESTROY);
		Log.d(TAG, "[onUpgrade] ItemList table droped!");
		
		db.execSQL(DB_PRODUCT_DESTROY);
		Log.d(TAG, "[onUpgrade] Production table droped!");
		
		onCreate(db);
		Log.d(TAG, "[onUpgrade] SQLiteDatabase upgraded...");
	}
}
