package com.example.dbio; 

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

import com.example.dbio.adapter.QuestDbAdapter;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.ProductionItem;
import com.example.skcc_client.gameObject.Quest;

public class QuestCreator {
	
	// db adapter 
    private QuestDbAdapter mDbAdapter; 
  
    /* 
     * constructor 
     */ 
    public QuestCreator(Context c) { 
    	
        mDbAdapter = new QuestDbAdapter(c); 
    } 
  
    /* 
     * open DBAdapter connection 
     */ 
    public void open() { 
    	
        mDbAdapter.open(); 
    } 
  
	
    public void insertInit(String playerId) { 
    	mDbAdapter.deleteAllQuests();
    	
    	Date date = new Date();
    	
    	// 1 Quest
   	 	Timestamp start1	= new Timestamp(date.getTime() - 40000);
   	 	Timestamp end1		= new Timestamp(date.getTime() + (1000 * 60 * 60 * 24 * 5));
   	 	Timestamp complete1	= new Timestamp(0);

   	 	Timestamp start2	= new Timestamp(date.getTime() - 40000);
   	 	Timestamp end2		= new Timestamp(date.getTime() + (1000 * 60 * 60 * 24 * 5));
   	 	Timestamp complete2	= new Timestamp(0);
   	 	
        mDbAdapter.insertInitQuest(Global.THIS_USER, 1
        		, "1회 5잔 이상 구매시 추가 아이템 획득!"
        		, "Cafe4U 카페라떼 30잔을 만들어 납품해 주세요."
        			+ "\n납품해 주시면 할인쿠폰을 드립니다."
					+ "\n\n퀘스트 보상:"
					+ "\n1000 게임머니"
	        		+ "\n200 경험치"
	        		+ "\nCafe4U 할인쿠폰"
        		, start1.getTime(), end1.getTime(), complete1.getTime());
   	 	
        mDbAdapter.insertInitQuest(Global.THIS_USER, 2
        		, "Cafe4U 9층을 방문해 주세요."
        		, "매장에서 음료를 구매하시면"
        			+ "\n카페라떼 레시피와"
					+ "\n커피콩 10개,"
					+ "\n우유 10개를 드립니다!"
        		, start2.getTime(), end2.getTime(), complete2.getTime());
    } 
    
    public void remove(Quest quest) {
    	
    	mDbAdapter.deleteQuest(Global.THIS_USER, quest);
    } 
  
    /* 
     * query all user info from db 
     */ 
    public ArrayList<Quest> queryAll() { 
    	
        return mDbAdapter.fetchAllQuests(); 
    } 
  
    /* 
     * close connection 
     */
    public void close() { 
    	
        mDbAdapter.close(); 
    } 
}
