package com.example.dbio.data;

public class ProductionInfo {
	
	private int mItemId;
	private String mUserId;
	private String mStartTime;
	private String mEndTime;
	private String mExpireTime;
	
	/**
	 * Constructor : Item�� �Է� �޾� ����
	 * @param item			Item
	 * @param startTime		���� ���� �ð�
	 * @param endTime		���� ���� �ð�
	 * @param expireTime	������� �ð�
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
