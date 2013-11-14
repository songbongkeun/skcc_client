package com.example.skcc_client.common;

import java.util.ArrayList;
import java.util.Hashtable;

import com.example.skcc_client.gameObject.GamePlaying;
import com.example.skcc_client.gameObject.Item;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.ProductionItem;
import com.example.skcc_client.gameObject.Player;
import com.example.skcc_client.gameRule.LevelRule;
import com.example.skcc_client.gameRule.ProductionRule;
import com.example.skcc_client.ui.player.PlayerInfoLayout;

/**
 * ���� ���� ���� Ŭ����
 * @author Jongkil Park
 */
public class Global {
	
	public Hashtable<Integer, Item> itemList;
	public ArrayList<InventoryItem> inventoryList;
	public ArrayList<ProductionItem> productionList;
	
	public LevelRule levelRule;
	public ProductionRule productionRule;
	
	public Player player;
	
	public GamePlaying playing;
	
	public PlayerInfoLayout playerInfoLayout;

	/**
	 * Constructor
	 */
	private Global() {
		
		itemList = new Hashtable<Integer, Item>();
		inventoryList = new ArrayList<InventoryItem>();
		productionList = new ArrayList<ProductionItem>(Constants.rule.PRODUCTION_MAX_COUNT);
		levelRule = new LevelRule();
		productionRule = new ProductionRule();
		playing = new GamePlaying();
	}
	
	private static Global instance;

	/**
	 * Global�� instance�� ��� ���������� ����Ѵ�.
	 * * ���� : Globa.getInstance().����������
	 */
	public static Global getInstance() {
		
		if (instance == null) instance = new Global();
		return instance;
	}
}
