package com.example.skcc_client.common;

public final class Constants {

    public static final class code {

        public static final int ITEM_TYPE_NOTHING			= 0;
        public static final int ITEM_TYPE_MATERIAL			= 1;
        public static final int ITEM_TYPE_COUPON			= 2;
    	
        public static final int ITEM_STATE_NOTHING			= 0;
        public static final int ITEM_STATE_PRODUCING		= 1;
        public static final int ITEM_STATE_FINISHED			= 2;
        public static final int ITEM_STATE_ROTTEN			= 3;
        
        public static final int ITEM_PROGRESS_NOTHING		=  100;
        public static final int ITEM_PROGRESS_ROTTEN		= -100;
    }
    
    public static final class rule {
    	
    	public static final int PRODUCTION_MAX_COUNT		= 16;
    }

    public static final class system {
        
        public static final int GRID_REFRESH_MILLISECOND	= 1000;
    }
}