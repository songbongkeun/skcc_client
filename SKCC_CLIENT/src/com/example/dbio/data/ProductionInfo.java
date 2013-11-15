package com.example.dbio.data;

public class ProductionInfo {
	
	private int mItemId;
	private String mUserId;
	private String mStartTime;
	private String mEndTime;
	private String mExpireTime;
	
	/**
	 * Constructor : Item을 입력 받아 생성
	 * @param item			Item
	 * @param startTime		생산 시작 시각
	 * @param endTime		생산 종료 시각
	 * @param expireTime	유통기한 시각
	 */
	public ProductionInfo(int itemid, String userId, String startTime, String endTime, String expireTime) {
		this.mItemId = itemid;
		this.mUserId = userId;
		this.mStartTime = startTime;
		this.mEndTime = endTime;
		this.mExpireTime = expireTime;
	}
	
	/* 
     * set item id 
     */ 
    public void setItemID(int id) { 
    	mItemId = id; 
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
    public void setUserID(String usrid) { 
    	mUserId = usrid; 
    } 
  
    /* 
     * get user id
     */ 
    public String getUserID() { 
        return mUserId; 
    }
    
    /* 
     * set start time 
     */ 
    public void setStartTime(String stime) { 
    	mStartTime = stime; 
    } 
  
    /* 
     * get start time
     */ 
    public String getStartTime() { 
        return mStartTime;
    }
    
    /* 
     * set end time 
     */ 
    public void setEndTime(String etime) { 
    	mEndTime = etime; 
    } 
  
    /* 
     * get end time 
     */ 
    public String getEndTime() { 
    	return mEndTime;
    }
    
    /* 
     * set user id 
     */ 
    public void setExpireTime(String exptime) { 
    	mExpireTime = exptime; 
    } 
  
    /* 
     * get user id 
     */ 
    public String getExpireTime() { 
    	return mExpireTime;
    }

 	@Override
	public String toString() {
		return "Item [id=" + mItemId + ", companyId=" + mStartTime + ", itemType="
				+ mEndTime + ", name=" + mExpireTime + "]";
	}
}
