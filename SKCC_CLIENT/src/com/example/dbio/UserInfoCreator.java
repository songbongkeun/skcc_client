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
    	mDbAdapter.insertInitUser("cheonjoy", 700, 10, "õ���", 50000, "õ����Դϴ�.");
    	mDbAdapter.insertInitUser("tommybee", 700, 10, "�鿵��", 50000, "�鿵���Դϴ�.");
    	mDbAdapter.insertInitUser("freepsw", 700, 10, "�ڻ��", 50000, "�ڻ���Դϴ�.");
    	mDbAdapter.insertInitUser("jongkilpark", 700, 10, "������", 50000, "�������Դϴ�.");
    	mDbAdapter.insertInitUser("bongkeonsong", 700, 10, "�ۺ���", 50000, "�ۺ����Դϴ�.");
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
