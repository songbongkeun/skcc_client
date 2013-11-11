package com.example.skcc_client.common;

public final class Constants {

    public static final class code {

        public static final int ITEM_TYPE_NOTHING			= 0;
        public static final int ITEM_TYPE_MATERIAL			= 1;
        public static final int ITEM_TYPE_DRINK				= 2;
        public static final int ITEM_TYPE_BREAD				= 3;
        public static final int ITEM_TYPE_COUPON			= 8;
        public static final int ITEM_TYPE_RECEIPE			= 9;
    	
        public static final int ITEM_STATE_NOTHING			= 0;
        public static final int ITEM_STATE_PRODUCING		= 1;
        public static final int ITEM_STATE_FINISHED			= 2;
        public static final int ITEM_STATE_ROTTEN			= 3;
        
        public static final int ITEM_PROGRESS_NOTHING		=  100;
        public static final int ITEM_PROGRESS_ROTTEN		= -100;
    }
    
    public static final class rule {
    	
    	public static final int PRODUCTION_MAX_COUNT			= 16;

    	public static final int NEW_PRODUCTION_ERROR			= -1;
    	public static final int NEW_PRODUCTION_OK				=  0;
    	public static final int NEW_PRODUCTION_NOT_ENOUGH_LEVEL	= 11;
    	public static final int NEW_PRODUCTION_NOT_ENOUGH_AP	= 21;
    	public static final int NEW_PRODUCTION_NOT_ENOUGH_MONEY	= 31;
    	public static final int NEW_PRODUCTION_NOT_ENOUGH_ITEM1	= 41;
    	public static final int NEW_PRODUCTION_NOT_ENOUGH_ITEM2	= 42;
    	public static final int NEW_PRODUCTION_NOT_ENOUGH_ITEM3	= 43;
    	public static final int NEW_PRODUCTION_NOT_ENOUGH_ITEM4	= 44;
    }

    public static final class system {
        
        public static final int GRID_REFRESH_MILLISECOND	= 1000;
    }
}