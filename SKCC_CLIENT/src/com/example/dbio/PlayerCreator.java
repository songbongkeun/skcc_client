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
    	
    	mDbAdapter.insertInitUser("cheonjoy", "õ���", 700, 10, 50000);
    	mDbAdapter.insertInitUser("tommybee", "�鿵��", 700, 10, 50000);
    	mDbAdapter.insertInitUser("freepsw", "�ڻ��", 700, 10, 50000);
    	mDbAdapter.insertInitUser("jongkilpark", "������", 700, 10, 50000);
    	mDbAdapter.insertInitUser("bongkeonsong", "�ۺ���", 700, 10, 50000);
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
