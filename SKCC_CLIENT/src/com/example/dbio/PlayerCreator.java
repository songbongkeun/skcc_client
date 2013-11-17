package com.example.dbio; 

import java.util.ArrayList;

import android.content.Context;

import com.example.dbio.adapter.PlayerDbAdapter;
import com.example.skcc_client.gameObject.Player;
  
public class PlayerCreator { 
	
    // db adapter 
    private PlayerDbAdapter mDbAdapter; 
  
    /* 
     * constructor 
     */ 
    public PlayerCreator(Context c) { 
    	
        mDbAdapter = new PlayerDbAdapter(c); 
    } 
  
    /* 
     * open DBAdapter connection 
     */ 
    public void open() { 
    	
        mDbAdapter.open(); 
    } 
  
    /* 
     * insert random data 
     * COL_ID, COL_EXP, COL_ACT_PNT, COL_NAME, COL_MONEY, COL_DESC
     * String id, String name, long exp, int actionPoint, long money
     */ 
    public void insert() { 
    	
    	mDbAdapter.insertInitUser("cheonjoy", "Ãµ±â»Ý", 700, 10, 5000);
    	mDbAdapter.insertInitUser("tommybee", "¹é¿µ°ï", 700, 10, 5000);
    	mDbAdapter.insertInitUser("freepsw", "¹Ú»ó¿ø", 700, 10, 5000);
    	mDbAdapter.insertInitUser("park108", "¹ÚÁ¾±æ", 700, 10, 5000);
    	mDbAdapter.insertInitUser("bongkeonsong", "¼ÛºÀ±Ù", 700, 10, 5000);
    }
    
    public Player queryPlayers(String userid) {
    	
    	return (Player) mDbAdapter.fetchSingleUser(userid, PlayerDbAdapter.QUERY_TYPE_USERINFO_OBJ);
    }
    
    /* 
     * query all user info from db 
     */ 
    public ArrayList<Player> queryAll() {
    	
        return mDbAdapter.fetchAllPlayers(); 
    } 
  
    /* 
     * close connection 
     */
    public void close() {
    	
        mDbAdapter.close(); 
    } 
}
