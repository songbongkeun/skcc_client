package com.example.dbio; 

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

import com.example.dbio.adapter.ProductionDbAdapter;
import com.example.skcc_client.gameObject.ProductionItem;

public class ProductionCreator {
	
	// db adapter 
    private ProductionDbAdapter mDbAdapter; 
  
    /* 
     * constructor 
     */ 
    public ProductionCreator(Context c) { 
    	
        mDbAdapter = new ProductionDbAdapter(c); 
    } 
  
    /* 
     * open DBAdapter connection 
     */ 
    public void open() { 
    	
        mDbAdapter.open(); 
    } 
  
	
    public void insertInit(String playerId) { 
    	mDbAdapter.deleteAllProduct();
    	
    	Date date = new Date();
    	// 1 Producing
   	 	Timestamp start1	= new Timestamp(date.getTime() - 40000);
   	 	Timestamp end1		= new Timestamp(date.getTime() + 20000);
   	 	Timestamp expire1	= new Timestamp(date.getTime() + 40000);
   	 	// 2 Finished
   	 	Timestamp start2	= new Timestamp(date.getTime() - 40000);
   	 	Timestamp end2		= new Timestamp(date.getTime() - 20000);
   	 	Timestamp expire2	= new Timestamp(date.getTime() + 20000);
   	 	// 3 Rotten
   	 	Timestamp start3	= new Timestamp(date.getTime() - 40000);
   	 	Timestamp end3		= new Timestamp(date.getTime() - 20000);
   	 	Timestamp expire3	= new Timestamp(date.getTime() - 10000);
   	 	// 4 Producing
   	 	Timestamp start4	= new Timestamp(date.getTime() - 400000);
   	 	Timestamp end4		= new Timestamp(date.getTime() + 200000);
   	 	Timestamp expire4	= new Timestamp(date.getTime() + 400000);
   	 	// 4 Producing
   	 	Timestamp start5	= new Timestamp(date.getTime() - 40000000);
   	 	Timestamp end5		= new Timestamp(date.getTime() + 20000000);
   	 	Timestamp expire5	= new Timestamp(date.getTime() + 40000000);
   	 	
        mDbAdapter.insertInitProduct(playerId, 0, 2002, start1.getTime(), end1.getTime(), expire1.getTime()); 
        mDbAdapter.insertInitProduct(playerId, 4, 3001, start2.getTime(), end2.getTime(), expire2.getTime()); 
        mDbAdapter.insertInitProduct(playerId, 5, 3001, start2.getTime(), end2.getTime(), expire2.getTime()); 
        mDbAdapter.insertInitProduct(playerId, 6, 3001, start2.getTime(), end2.getTime(), expire2.getTime()); 
        mDbAdapter.insertInitProduct(playerId, 7, 3001, start2.getTime(), end2.getTime(), expire2.getTime()); 
        mDbAdapter.insertInitProduct(playerId, 8, 1004, start3.getTime(), end3.getTime(), expire3.getTime()); 
        mDbAdapter.insertInitProduct(playerId, 9, 3004, start4.getTime(), end4.getTime(), expire4.getTime()); 
        mDbAdapter.insertInitProduct(playerId, 10, 8001, start5.getTime(), end5.getTime(), expire5.getTime());
    } 
    
    public void remove(int position) {
    	
    	mDbAdapter.deleteProduct(position);
    } 
  
    /* 
     * query all user info from db 
     */ 
    public ArrayList<ProductionItem> queryAll() { 
    	
        return mDbAdapter.fetchAllProducts(); 
    } 
  
    /* 
     * close connection 
     */
    public void close() { 
    	
        mDbAdapter.close(); 
    } 
}
