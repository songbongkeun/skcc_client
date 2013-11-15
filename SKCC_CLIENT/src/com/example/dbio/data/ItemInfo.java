package com.example.dbio.data;

import java.util.Locale;

public class ItemInfo { 
	
	private int mId;
	private int mCompanyId;
	private int mItemType;
    private String mName; 
    private String mDescription; 
    
    public ItemInfo(final ItemInfo item) {
		this.mId = item.getID();
		this.mCompanyId = item.getCompanyID();
		this.mItemType = item.getItemType();
		this.mName = item.getName();
		this.mDescription = item.getDescription();
	}
    
    /* 
     * constructor 
     */ 
    public ItemInfo(int id, int companyId, int itemType, String name, String desc) { 
        mId = id; 
        mCompanyId = companyId; 
        mItemType = itemType; 
        mName = name; 
        mDescription = desc; 
    } 
  
    /* 
     * set user id 
     */ 
    public void setID(int id) { 
        mId = id; 
    } 
  
    /* 
     * get user id 
     */ 
    public int getID() { 
        return mId; 
    } 
    
    /* 
     * set user id 
     */ 
    public void setCompanyID(int cid) { 
    	mCompanyId = cid; 
    } 
  
    /* 
     * get user id 
     */ 
    public int getCompanyID() { 
        return mCompanyId; 
    }
    
    /* 
     * set user id 
     */ 
    public void setItemType(int type) { 
    	mItemType = type; 
    } 
  
    /* 
     * get user id 
     */ 
    public int getItemType() { 
        return mItemType; 
    }
  
    /* 
     * set user name 
     */ 
    public void setName(String name) { 
        mName = name; 
    } 
  
    /* 
     * get user name 
     */ 
    public String getName() { 
        return mName; 
    } 
  
    /* 
     * set user city 
     */ 
    public void setDescription(String desc) { 
    	mDescription = desc; 
    } 
  
    /* 
     * get user city 
     */
    public String getDescription() { 
        return mDescription; 
    } 
    
    public String getImageName() {
		return "item" + String.format(Locale.KOREA, "%04d", this.mId);
	}

	@Override
	public String toString() {
		return "Item [id=" + mId + ", companyId=" + mCompanyId + ", itemType="
				+ mItemType + ", name=" + mName + ", description=" + mDescription
				+ "]";
	}
}
