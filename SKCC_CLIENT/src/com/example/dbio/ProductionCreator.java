package com.example.dbio; 

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.example.dbio.adapter.ProductionDbAdapter;
import com.example.dbio.data.ItemInfo;
import com.example.dbio.data.ProductionInfo;

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
  
	
    public void insertInit(String userId) { 
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
   	 	
        mDbAdapter.insertInitProduct(2002, userId, Long.valueOf(start1.getTime()).toString(),  Long.valueOf(end1.getTime()).toString(), Long.valueOf(expire1.getTime()).toString()); 
        mDbAdapter.insertInitProduct(3001, userId, Long.valueOf(start2.getTime()).toString(),  Long.valueOf(end2.getTime()).toString(), Long.valueOf(expire2.getTime()).toString()); 
        mDbAdapter.insertInitProduct(3001, userId, Long.valueOf(start2.getTime()).toString(),  Long.valueOf(end2.getTime()).toString(), Long.valueOf(expire2.getTime()).toString()); 
        mDbAdapter.insertInitProduct(3001, userId, Long.valueOf(start2.getTime()).toString(),  Long.valueOf(end2.getTime()).toString(), Long.valueOf(expire2.getTime()).toString()); 
        mDbAdapter.insertInitProduct(3001, userId, Long.valueOf(start2.getTime()).toString(),  Long.valueOf(end2.getTime()).toString(), Long.valueOf(expire2.getTime()).toString()); 
        mDbAdapter.insertInitProduct(1004, userId, Long.valueOf(start3.getTime()).toString(),  Long.valueOf(end3.getTime()).toString(), Long.valueOf(expire3.getTime()).toString());
        mDbAdapter.insertInitProduct(3004, userId, Long.valueOf(start4.getTime()).toString(),  Long.valueOf(end4.getTime()).toString(), Long.valueOf(expire4.getTime()).toString());
        mDbAdapter.insertInitProduct(8001, userId, Long.valueOf(start5.getTime()).toString(),  Long.valueOf(end5.getTime()).toString(), Long.valueOf(expire5.getTime()).toString());
    } 
    
    public void remove(String userid, ItemInfo item) { 
    	mDbAdapter.deleteProduct(item.getID(),userid);
    } 
  
    /* 
     * query all user info from db 
     */ 
    public List<ProductionInfo> queryAll() { 
        return mDbAdapter.fetchAllProducts(); 
    } 
  
    /* 
     * close connection 
     */
    public void close() { 
        mDbAdapter.close(); 
    } 
}
