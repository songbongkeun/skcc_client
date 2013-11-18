package com.example.skcc_client.common;

import java.util.ArrayList;
import java.util.Hashtable;

import android.graphics.Bitmap;
import android.util.SparseArray;

import com.example.skcc_client.gameObject.GamePlaying;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.Item;
import com.example.skcc_client.gameObject.Player;
import com.example.skcc_client.gameObject.ProductionItem;
import com.example.skcc_client.gameRule.LevelRule;
import com.example.skcc_client.gameRule.ProductionRule;
import com.example.skcc_client.ui.player.PlayerInfoLayout;

/**
 * 전역 변수 관리 클래스
 * @author Jongkil Park
 */
public class Global {
	public static final String THIS_USER = "park108";
	public static final int USER_INIT_COUNT = 6;
	public static final int ITEM_INIT_COUNT = 13;
	public static final int INVENTORY_INIT_COUNT = 12;
	public static final int PRODUCTION_INIT_COUNT = 8;
	
	public static final boolean USE_CACHE = true;
	
	public Hashtable<Integer, Item> itemList;
	public ArrayList<InventoryItem> inventoryList;
	public ArrayList<ProductionItem> productionList;
	
	public LevelRule levelRule;
	public ProductionRule productionRule;
	
	public Player player;
	public GamePlaying playing;
	public PlayerInfoLayout playerInfoLayout;
	private BitMapCache bitMapCache;
	
	/**
	 * Constructor
	 */
	private Global() {
		itemList = new Hashtable<Integer, Item>();
		inventoryList = new ArrayList<InventoryItem>();
		productionList = new ArrayList<ProductionItem>(Constants.rule.PRODUCTION_MAX_COUNT);
		levelRule = new LevelRule();
		productionRule = new ProductionRule();
		
		bitMapCache = new BitMapCache();
	}
	
	public BitMapCache getBitMapCache() {
		return bitMapCache;
	}

	/**
	 * Global의 instance를 얻어 전역변수를 사용한다.
	 * * 사용법 : Globa.getInstance().전역변수명
	 */
	public static Global getInstance() {
		return SingletonHolder.instance;
	}
	
	public void addInventoryItem(InventoryItem inventoryItem) {
		for(InventoryItem existItem : inventoryList) {
			if(existItem.getId() == inventoryItem.getId()) {
				existItem.setQunatity(existItem.getQuantity() + inventoryItem.getQuantity());
				return;
			}
		}
		inventoryList.add(inventoryItem);
	}
	
	/** Using Singleton Holder Pattern */
	private static class SingletonHolder {
		private static final Global instance = new Global();
	}
	
	public class BitMapCache {
		private SparseArray<Bitmap> cache = new SparseArray<Bitmap>();
		
		private BitMapCache(){}//외부에서 생성하는 실수를 방지하기 위해
		
		public void putBitmap(int imageId, Bitmap bitmap) {
			cache.put(imageId, bitmap);
		}
		
		public boolean isExistsBitmap(int imageId) {
			if(USE_CACHE) {
				return false;
			}
			if(cache.get(imageId) == null) {
				return false;
			} else {
				return true;
			}
		}
		
		public Bitmap getBitmap(int imageId) {
			return cache.get(imageId);
		}
		
		public void cleanAll() {
			cache.clear();
		}
	}
}
