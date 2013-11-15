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
	protected static final String DB_USER_CREATE = 
		"CREATE TABLE IF NOT EXISTS user ( " +
		"usr_id text PRIMARY KEY, " +
		"experience integer NOT NULL, " + 
		"act_point integer NOT NULL, " + 
		"money integer NOT NULL, " + 
		"name text NOT NULL, " + 
		"description text" + 
		");";
	
	// declared constant SQL Expression  
	protected static final String DB_ITEM_CREATE = 
		"CREATE TABLE IF NOT EXISTS item ( "
		+ "item_id integer PRIMARY KEY, " 
		+ "company_id integer NOT NULL, "
		+ "item_type integer NOT NULL, " 
		+ "name text NOT NULL,"
		+ "description text" 
		+ ");";
	
	// declared constant SQL Expression  
	protected static final String DB_PRODUCT_CREATE = 
		"CREATE TABLE IF NOT EXISTS production ( " +
		"id integer PRIMARY KEY AUTOINCREMENT, " 
		+ "s_time text,"
		+ "e_time text,"
		+ "exp_time text,"
		+ "user_id text, "
		+ "item_id integer, "
		+ "FOREIGN KEY(user_id) REFERENCES user(usr_id), "
		+ "FOREIGN KEY(item_id) REFERENCES item(item_id)"
		+ ");";
	
	// declared constant SQL Expression 
	protected static final String DB_INVENTORY_CREATE = 
		"CREATE TABLE IF NOT EXISTS inventory ( "
		+ "_id integer PRIMARY KEY AUTOINCREMENT, "
		+ "quantity integer NOT NULL, "
		+ "user_id text, "
		+ "item_id integer, "
		+ "FOREIGN KEY(user_id) REFERENCES user(usr_id), "
		+ "FOREIGN KEY(item_id) REFERENCES item(item_id)"
		+ ");";
	

	protected static final String DB_USER_DESTROY = "DROP TABLE IF EXISTS user";
	protected static final String DB_ITEM_DESTROY = "DROP TABLE IF EXISTS item";
	protected static final String DB_PRODUCT_DESTROY = "DROP TABLE IF EXISTS product";
	protected static final String DB_INVENTORY_DESTROY = "DROP TABLE IF EXISTS inventory";
	
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
		
		db.execSQL(DB_USER_CREATE);
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
