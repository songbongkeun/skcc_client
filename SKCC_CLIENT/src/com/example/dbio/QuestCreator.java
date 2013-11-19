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
        		, "1ȸ 5�� �̻� ���Ž� �߰� ������ ȹ��!"
        		, "Cafe4U ī��� 30���� ����� ��ǰ�� �ּ���."
        			+ "\n��ǰ�� �ֽø� ���������� �帳�ϴ�."
					+ "\n\n����Ʈ ����:"
					+ "\n1000 ���ӸӴ�"
	        		+ "\n200 ����ġ"
	        		+ "\nCafe4U ��������"
        		, start1.getTime(), end1.getTime(), complete1.getTime());
   	 	
        mDbAdapter.insertInitQuest(Global.THIS_USER, 2
        		, "Cafe4U 9���� �湮�� �ּ���."
        		, "���忡�� ���Ḧ �����Ͻø�"
        			+ "\nī��� �����ǿ�"
					+ "\nĿ���� 10��,"
					+ "\n���� 10���� �帳�ϴ�!"
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
