package com.example.dbio.data;

public class InventoryInfo {
	
    // fields 
	private String mUsrId;
	private int mItemId;
	private int mQuantity;
  
    /* 
     * constructor 
     */ 
    public InventoryInfo(String userid, int itemid, int quantity) { 
    	mUsrId = userid; 
    	mItemId = itemid;
    	mQuantity = quantity;
    } 
  
    /* 
     * set user id 
     */ 
    public void setUserID(String id) { 
    	mUsrId = id; 
    } 
  
    /* 
     * get user id 
     */ 
    public String getUserID() { 
        return mUsrId; 
    } 
    
    /* 
     * set item id 
     */ 
    public void setItemID(int cid) { 
    	mItemId = cid; 
    } 
  
    /* 
     * get item id 
     */ 
    public int getItemID() { 
        return mItemId; 
    }
    
    /* 
     * set user id 
     */ 
    public void setQuantity(int quantity) { 
    	mQuantity = quantity; 
    } 
  
    /* 
     * get user id 
     */ 
    public int getQuantity() { 
        return mQuantity; 
    }
}
