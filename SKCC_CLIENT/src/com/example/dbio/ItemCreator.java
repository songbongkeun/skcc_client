package com.example.dbio;

import java.util.List;

import android.content.Context;

import com.example.skcc_client.common.Constants;
import com.example.dbio.adapter.ItemDbAdapter;
import com.example.dbio.data.ItemInfo;

public class ItemCreator {
	
	// db adapter 
    private ItemDbAdapter mDbAdapter; 
  
    /* 
     * constructor 
     */ 
    public ItemCreator(Context c) { 
        mDbAdapter = new ItemDbAdapter(c); 
    } 
  
    /* 
     * open DBAdapter connection 
     */ 
    public void open() { 
        mDbAdapter.open(); 
    } 
  
    /* 
     * insert random data 
     */ 
    public void insert() { 
    	mDbAdapter.insertInitItem(0,    1,  Constants.code.ITEM_TYPE_NOTHING, "Vacant", "Vacant"); 
        mDbAdapter.insertInitItem(1001, 10, Constants.code.ITEM_TYPE_MATERIAL, "커피빈", "각종 커피의 원재료가 되는 커피나무 열매의 씨"); 
        mDbAdapter.insertInitItem(1002, 16, Constants.code.ITEM_TYPE_MATERIAL, "우유", "소의 젖. 백색으로 지방, 단백질, 칼슘, 비타민이 풍부하게 함유되어 있어 영양가가 높다."); 
        mDbAdapter.insertInitItem(1003, 14, Constants.code.ITEM_TYPE_MATERIAL, "밀가루", "밀을 빻아 만든 가루"); 
        mDbAdapter.insertInitItem(1004, 15, Constants.code.ITEM_TYPE_MATERIAL, "초코렛", "당산의 뇌에 활력을 불어 넣어 줄 ... 초코렛 ... 아.. 달다~~"); 
        mDbAdapter.insertInitItem(2001, 16, Constants.code.ITEM_TYPE_DRINK, "아메리카노", "에스프레소에 뜨거운 물을 더하여 먹는 커피");
        
        mDbAdapter.insertInitItem(2002, 13, Constants.code.ITEM_TYPE_DRINK, "카페라떼", "부드러운 우유와 조합된 달달한 카페라떼");
        mDbAdapter.insertInitItem(3001, 15, Constants.code.ITEM_TYPE_BREAD, "빵", "남아 있는 밀가루로 빵을 만들어 보는 것은 어떨까요?");
        mDbAdapter.insertInitItem(3002, 12, Constants.code.ITEM_TYPE_DRINK, "초코우유", "초코렛과 우유의 황금 비율");
        mDbAdapter.insertInitItem(3003, 14, Constants.code.ITEM_TYPE_BREAD, "모카번", "커피빈과 빵의 조화~~ 한번 드셔보세요.");
        mDbAdapter.insertInitItem(3004, 13, Constants.code.ITEM_TYPE_BREAD, "초코머핀", "그냥 빵은 가라... 초코렛이 듬뿍 담긴 머핀");
        mDbAdapter.insertInitItem(8001, 15, Constants.code.ITEM_TYPE_COUPON, "Cafe4U 카페라떼", "Cafe4U 레시피를 가지고 만드는 환상의 커피우유~ 반합니다. 강추");
        mDbAdapter.insertInitItem(9001, 16, Constants.code.ITEM_TYPE_RECEIPE, "Cafe4U 카페라떼 레시피", "Cafe4U 레시피");
    } 
    
    public void insert(int id, int companyId, int itemType, String name, String desc) { 
    	mDbAdapter.insertItem(id, companyId, itemType, name, desc);
    }
    
    public void insert(ItemInfo item) { 
    	mDbAdapter.insertItem(item);
    }
    
    public void remove(ItemInfo item) { 
    	mDbAdapter.deleteItem(item.getID());
    }
    
    public ItemInfo getItem(int itemId) { 
    	return (ItemInfo)mDbAdapter.fetchSingleItem(itemId, ItemDbAdapter.QUERY_TYPE_ITEM_OBJ);
    }
  
    /* 
     * query all user info from db 
     */ 
    public List<ItemInfo> queryAll() { 
        return mDbAdapter.fetchAllItems(); 
    } 
  
    /* 
     * close connection 
     */
    public void close() { 
        mDbAdapter.close(); 
    } 
}
