package com.example.dbio; 

import java.util.List;

import android.content.Context;

import com.example.dbio.adapter.UserDbAdapter;
import com.example.dbio.data.UserInfo;
  
public class UserInfoCreator { 
    // db adapter 
    private UserDbAdapter mDbAdapter; 
  
    /* 
     * constructor 
     */ 
    public UserInfoCreator(Context c) { 
        mDbAdapter = new UserDbAdapter(c); 
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
    	mDbAdapter.insertInitUser("cheonjoy", 700, 10, "천기쁨", 50000, "천기쁨입니다.");
    	mDbAdapter.insertInitUser("tommybee", 700, 10, "백영곤", 50000, "백영곤입니다.");
    	mDbAdapter.insertInitUser("freepsw", 700, 10, "박상원", 50000, "박상원입니다.");
    	mDbAdapter.insertInitUser("jongkilpark", 700, 10, "박종길", 50000, "박종길입니다.");
    	mDbAdapter.insertInitUser("bongkeonsong", 700, 10, "송봉근", 50000, "송봉근입니다.");
    } 
    
    public UserInfo queryUser(String userid)
    {
    	return (UserInfo)mDbAdapter.fetchSingleUser(userid, UserDbAdapter.QUERY_TYPE_USERINFO_OBJ);
    }
    
    /* 
     * query all user info from db 
     */ 
    public List<UserInfo> queryAll() { 
        return mDbAdapter.fetchAllUsers(); 
    } 
  
    /* 
     * close connection 
     */
    public void close() { 
        mDbAdapter.close(); 
    } 
}
