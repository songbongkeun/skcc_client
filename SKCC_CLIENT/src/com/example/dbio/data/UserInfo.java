package com.example.dbio.data;

public class UserInfo { 
    // fields 
	private String mId; 
	private int mExperience;
	private int mActionPoint;
    private int mMoney;
    private String mName;
    private String mDescription; 
    
    /* 
     * constructor 
     */ 
    public UserInfo(String id, int experience, int actionPoint, String name, int money, String desc) { 
    	mId = id; 
    	mExperience = experience; 
    	mActionPoint = actionPoint; 
        mName = name; 
        mMoney = money;
        mDescription = desc; 
    } 
    
    /* 
     * set user id 
     */ 
    public void setID(String id) { 
        mId = id; 
    } 
  
    /* 
     * get user id 
     */ 
    public String getID() { 
        return mId; 
    } 
    
    /* 
     * set user action point 
     */ 
    public void setActionPoint(int actionPoint) { 
    	mActionPoint = actionPoint; 
    } 
  
    /* 
     * get action point
     */ 
    public int getActionPoint() { 
        return mActionPoint; 
    }
    
    /* 
     * set user experience 
     */ 
    public void setExperience(int experience) { 
    	mExperience = experience; 
    } 
  
    /* 
     * get user experience 
     */ 
    public int getExperience() { 
        return mExperience; 
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
     * set user money 
     */ 
    public void setMoney(int money) { 
        mMoney = money; 
    } 
  
    /* 
     * get user money 
     */ 
    public int getMoney() { 
        return mMoney; 
    } 
    
    /* 
     * set user description
     */ 
    public void setDescription(String desc) { 
    	mDescription = desc; 
    } 
  
    /* 
     * get user description 
     */
    public String getDescription() { 
        return mDescription; 
    } 
}
