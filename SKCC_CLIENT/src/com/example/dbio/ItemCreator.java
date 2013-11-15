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
        mDbAdapter.insertInitItem(1001, 10, Constants.code.ITEM_TYPE_MATERIAL, "Ŀ�Ǻ�", "���� Ŀ���� ����ᰡ �Ǵ� Ŀ�ǳ��� ������ ��"); 
        mDbAdapter.insertInitItem(1002, 16, Constants.code.ITEM_TYPE_MATERIAL, "����", "���� ��. ������� ����, �ܹ���, Į��, ��Ÿ���� ǳ���ϰ� �����Ǿ� �־� ���簡�� ����."); 
        mDbAdapter.insertInitItem(1003, 14, Constants.code.ITEM_TYPE_MATERIAL, "�а���", "���� ���� ���� ����"); 
        mDbAdapter.insertInitItem(1004, 15, Constants.code.ITEM_TYPE_MATERIAL, "���ڷ�", "����� ���� Ȱ���� �Ҿ� �־� �� ... ���ڷ� ... ��.. �޴�~~"); 
        mDbAdapter.insertInitItem(2001, 16, Constants.code.ITEM_TYPE_DRINK, "�Ƹ޸�ī��", "���������ҿ� �߰ſ� ���� ���Ͽ� �Դ� Ŀ��");
        
        mDbAdapter.insertInitItem(2002, 13, Constants.code.ITEM_TYPE_DRINK, "ī���", "�ε巯�� ������ ���յ� �޴��� ī���");
        mDbAdapter.insertInitItem(3001, 15, Constants.code.ITEM_TYPE_BREAD, "��", "���� �ִ� �а���� ���� ����� ���� ���� ����?");
        mDbAdapter.insertInitItem(3002, 12, Constants.code.ITEM_TYPE_DRINK, "���ڿ���", "���ڷ��� ������ Ȳ�� ����");
        mDbAdapter.insertInitItem(3003, 14, Constants.code.ITEM_TYPE_BREAD, "��ī��", "Ŀ�Ǻ�� ���� ��ȭ~~ �ѹ� ��ź�����.");
        mDbAdapter.insertInitItem(3004, 13, Constants.code.ITEM_TYPE_BREAD, "���ڸ���", "�׳� ���� ����... ���ڷ��� ��� ��� ����");
        mDbAdapter.insertInitItem(8001, 15, Constants.code.ITEM_TYPE_COUPON, "Cafe4U ī���", "Cafe4U �����Ǹ� ������ ����� ȯ���� Ŀ�ǿ���~ ���մϴ�. ����");
        mDbAdapter.insertInitItem(9001, 16, Constants.code.ITEM_TYPE_RECEIPE, "Cafe4U ī��� ������", "Cafe4U ������");
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
