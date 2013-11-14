package com.example.skcc_client.gameRule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import android.util.Log;

import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.Item;

public class ProductionRule {
	
	private class info {
		
		long productionExp;
		boolean canUse;
		int useAP;
		long useExp;
		int limitLevel;
		long costTime;
		int costAP;
		long costMoney;
		int costItem1;
		int costItem1Quantity;
		int costItem2;
		int costItem2Quantity;
		int costItem3;
		int costItem3Quantity;
		int costItem4;
		int costItem4Quantity;
	}
	
	private LinkedHashMap<Integer, info> costTable;
	
	private int currentListLevel;
	private ArrayList<Item> list;
	
	public ProductionRule() {
		
		super();
		costTable = new LinkedHashMap<Integer, info>();
		list = new ArrayList<Item>();
		currentListLevel = 0;
	}
	
	/**
	 * 생산 규칙을 추가한다.
	 * @param id				아이템 id
	 * @param productionExp		생산 경험치
	 * @param canUse			사용 가능 여부
	 * @param useAP				사용 행동력
	 * @param useExp			사용 경험치
	 * @param limitLevel		생산 가능 레벨
	 * @param costTime			생산 소요 시간
	 * @param costAP			생산 소요 행동력
	 * @param costMoney			생산 소요 머니
	 * @param costItem1			생산 소요 아이템1
	 * @param costItem1Quantity	생산 소요 아이템1 수
	 * @param costItem2			생산 소요 아이템2
	 * @param costItem2Quantity	생산 소요 아이템2 수
	 * @param costItem3			생산 소요 아이템3
	 * @param costItem3Quantity	생산 소요 아이템3 수
	 * @param costItem4			생산 소요 아이템4
	 * @param costItem4Quantity	생산 소요 아이템4 수
	 */
	public void addRule(int id, long productionExp, boolean canUse, int useAP, long useExp, int limitLevel,
		long costTime, int costAP, long costMoney,
		int costItem1, int costItem1Quantity, int costItem2, int costItem2Quantity,
		int costItem3, int costItem3Quantity, int costItem4, int costItem4Quantity) {
		
		info i = new info();
		i.productionExp = productionExp;
		i.canUse = canUse;
		i.useAP = useAP;
		i.useExp = useExp;
		i.limitLevel = limitLevel;
		i.costTime = costTime;
		i.costAP = costAP;
		i.costMoney = costMoney;
		i.costItem1 = costItem1;
		i.costItem1Quantity = costItem1Quantity;
		i.costItem2 = costItem2;
		i.costItem2Quantity = costItem2Quantity;
		i.costItem3 = costItem3;
		i.costItem3Quantity = costItem3Quantity;
		i.costItem4 = costItem4;
		i.costItem4Quantity = costItem4Quantity;
		
		costTable.put(id, i);
	}
	
	public long getProductionExp(int id) {
		
		return costTable.get(id).productionExp;
	}
	
	public boolean canUse(int id) {
		
		return costTable.get(id).canUse;
	}
	
	public int getUseAP(int id) {

		return costTable.get(id).useAP;
	}
	
	public long getUseExp(int id) {

		return costTable.get(id).useExp;
	}
	
	public int getLimitLevel(int id) {

		return costTable.get(id).limitLevel;
	}
	
	public long getCostTime(int id) {

		return costTable.get(id).costTime;
	}
	
	public int getCostAP(int id) {

		return costTable.get(id).costAP;
	}
	
	public long getCostMoney(int id) {

		return costTable.get(id).costMoney;
	}
	
	public int getCostItem1(int id) {

		return costTable.get(id).costItem1;
	}
	
	public int getCostItem1Quantity(int id) {

		return costTable.get(id).costItem1Quantity;
	}
	
	public int getCostItem2(int id) {

		return costTable.get(id).costItem2;
	}
	
	public int getCostItem2Quantity(int id) {

		return costTable.get(id).costItem2Quantity;
	}
	
	public int getCostItem3(int id) {

		return costTable.get(id).costItem3;
	}
	
	public int getCostItem3Quantity(int id) {

		return costTable.get(id).costItem3Quantity;
	}
	
	public int getCostItem4(int id) {

		return costTable.get(id).costItem4;
	}
	
	public int getCostItem4Quantity(int id) {

		return costTable.get(id).costItem4Quantity;
	}
	
	public ArrayList<Item> getProductionRuleList() {
		
		int currentPlayerLevel = Global.getInstance().player.getLevel();
		
		// 기존 리스트의 레벨과 현재 플레이어 레벨이 다르면 생산 리스트를 재생성한다.
		if(currentListLevel != Global.getInstance().player.getLevel()) {
			
			list.clear();
			
			currentListLevel = currentPlayerLevel;
			
			Iterator<Integer> keyData = costTable.keySet().iterator();
			Integer itemId = 0;
			
			// 현재 레벨에서 생산 가능한 아이템만 추려서 리턴한다.
			while(keyData.hasNext()) {
				
				itemId = keyData.next();
			
				if(Constants.rule.NEW_PRODUCTION_OK == hasEnoughLevel(itemId.intValue())) {
					
					this.list.add(Global.getInstance().itemList.get(itemId.intValue()));
					
					Log.d("PRODUCTION", "Can make items = " + itemId);
				}
			}
		}
		
		return this.list;
	}
	
	/** Generate production rule for test */
	public void generateTestRule() {
		
		costTable.clear();
		
		long min = 60 * 1000;

		//      id    pExp   canUse   uAP  uEx Lv   costTime      cAP cMney item1    item2    item3    item4
		addRule(1001,	100,	false,	 0,	 0,	 1,	4 * 60 * min,	1,	 50,    0, 0,    0, 0,    0, 0,    0, 0); // 커피빈
		addRule(1002,	 50,	 true,	 2,	10,  1,	2 * 60 * min,	1,	 10,    0, 0,    0, 0,    0, 0,    0, 0); // 우유
		addRule(1003,	100,	 true,	 6,	10,  1,	3 * 60 * min,	1,	 50,    0, 0,    0, 0,    0, 0,    0, 0); // 밀가루
		addRule(1004,	120,	 true,	 5,	10,  2,	5 * 60 * min,	1,	 80,    0, 0,    0, 0,    0, 0,    0, 0); // 초코렛
		addRule(2001,	100,	 true,	 3,	40,  1,	1 * 20 * min,	1,	100, 1001, 1,    0, 0,    0, 0,    0, 0); // 아메리카노
		addRule(2002,	150,	 true,	 4,	50,  2,	1 * 30 * min,	1,	150, 1001, 1, 1002, 1,    0, 0,    0, 0); // 카페라떼
		addRule(3001,	 80,	 true,	 3,	10,  2,	1 * 60 * min,	1,	 50, 1003, 1,    0, 0,    0, 0,    0, 0); // 빵
		addRule(3002,	150,	 true,	 6,	15,  2,	1 * 15 * min,	1,	100, 1004, 1, 1002, 1,    0, 0,    0, 0); // 초코우유
		addRule(3003,	175,	 true,	 6,	20,  3,	3 * 15 * min,	1,	100, 1001, 2, 1003, 1,    0, 0,    0, 0); // 모카번
		addRule(3004,	190,	 true,	 8,	18,  3,	3 * 30 * min,	1,	 75, 1004, 2, 1003, 1,    0, 0,    0, 0); // 초코머핀
		addRule(8001,	200,	 true,	10,	50,  2,	1 * 30 * min,	1,	200, 1001, 1, 1002, 1, 9001, 1,    0, 0); // Cafe4U 카페라떼
		addRule(9001,	  0,	false,	 0,	 0, 99,	           0,	0,	  0,    0, 0,    0, 0,    0, 0,    0, 0); // Cafe4U 카페라떼 레시피
	}
	
	/**
	 * 아이템을 생산할 수 있는지 리턴
	 * @param	id	아이템id
	 * @return	아이템 생산 가능 여부
	 */
	public int canProduce(int id) {
		
		int code = Constants.rule.NEW_PRODUCTION_OK;
		
		code = hasEnoughLevel(id);
		if(code != Constants.rule.NEW_PRODUCTION_OK) return code;
		code = hasEnoughAP(id);
		if(code != Constants.rule.NEW_PRODUCTION_OK) return code;
		code = hasEnoughMoney(id);
		if(code != Constants.rule.NEW_PRODUCTION_OK) return code;
		
		code = hasEnoughItem(1, id);
		if(code != Constants.rule.NEW_PRODUCTION_OK) return code;
		code = hasEnoughItem(2, id);
		if(code != Constants.rule.NEW_PRODUCTION_OK) return code;
		code = hasEnoughItem(3, id);
		if(code != Constants.rule.NEW_PRODUCTION_OK) return code;
		code = hasEnoughItem(4, id);
		if(code != Constants.rule.NEW_PRODUCTION_OK) return code;
		
		return code;
	}
	
	/**
	 * 아이템을 생산할 수 있는 레벨인지 리턴
	 * @param	id 아이템id
	 * @return	해당 아이템을 생산할 수 있는 레벨인지 여부
	 */
	public int hasEnoughLevel(int id) {
		
		int level = Global.getInstance().player.getLevel();
		
		if(this.getLimitLevel(id) > level) {
			return Constants.rule.NEW_PRODUCTION_NOT_ENOUGH_LEVEL;
		}
		
		return Constants.rule.NEW_PRODUCTION_OK;
	}
	
	/**
	 * 아이템을 생산에 필요한 행동력이 충분한지 리턴
	 * @param	id 아이템id
	 * @return	해당 아이템 생산에 필요한 행동력이 충분한지 여부
	 */
	public int hasEnoughAP(int id) {
		
		int ap = Global.getInstance().player.getActionPoint();
		
		if(this.getCostAP(id) > ap) {
			return Constants.rule.NEW_PRODUCTION_NOT_ENOUGH_AP;
		}
		
		return Constants.rule.NEW_PRODUCTION_OK;
	}
	
	/**
	 * 아이템을 생산에 필요한 돈이 충분한지 리턴
	 * @param	id 아이템id
	 * @return	해당 아이템 생산에 필요한 돈이 충분한지 여부
	 */
	public int hasEnoughMoney(int id) {
		
		long owned = Global.getInstance().player.getMoney();
		
		if(this.getCostMoney(id) > owned) {
			return Constants.rule.NEW_PRODUCTION_NOT_ENOUGH_MONEY;
		}
		
		return Constants.rule.NEW_PRODUCTION_OK;
	}
	
	/**
	 * 아이템을 생산에 필요한 재료 아이템이 충분한지 리턴
	 * @param	itemNo	아이템 번호(1 ~ 4)
	 * @param	id		아이템id
	 * @return	해당 아이템 생산에 필요한 재료 아이템이 충분한지 여부
	 */
	public int hasEnoughItem(int itemNo, int id) {
		
		// 아이템 번호 체크
		if(itemNo < 0 || itemNo > 4) return Constants.rule.NEW_PRODUCTION_ERROR;
		
		// 해당 아이템에 대한 필요 아이템id(재료)가져오기
		int costItem = 0;
		if(1 == itemNo) costItem = getCostItem1(id);
		if(2 == itemNo) costItem = getCostItem2(id);
		if(3 == itemNo) costItem = getCostItem3(id);
		if(4 == itemNo) costItem = getCostItem4(id); 
		
		// 재료가 존재하지 않을 경우 true 리턴
		if(0 == costItem) return Constants.rule.NEW_PRODUCTION_OK;
		
		// 재료의 보유개수 가져오기
		int owned = 0;
		
		Iterator<InventoryItem> iter = Global.getInstance().inventoryList.iterator();
		InventoryItem item;
		
		while(iter.hasNext()) {
			
			item = iter.next();
			
			if(costItem == item.getId()) {
				
				owned = item.getQuantity();
				break;
			}
		}
		
		// 재료 보유량이 충분한지 여부 리턴 
		if(1 == itemNo && this.getCostItem1Quantity(id) <= owned) return Constants.rule.NEW_PRODUCTION_OK;
		else if(2 == itemNo && this.getCostItem2Quantity(id) <= owned) return Constants.rule.NEW_PRODUCTION_OK;
		else if(3 == itemNo && this.getCostItem3Quantity(id) <= owned) return Constants.rule.NEW_PRODUCTION_OK;
		else if(4 == itemNo && this.getCostItem4Quantity(id) <= owned) return Constants.rule.NEW_PRODUCTION_OK;
		
		if(1 == itemNo) return Constants.rule.NEW_PRODUCTION_NOT_ENOUGH_ITEM1;
		else if(2 == itemNo) return Constants.rule.NEW_PRODUCTION_NOT_ENOUGH_ITEM2;
		else if(3 == itemNo) return Constants.rule.NEW_PRODUCTION_NOT_ENOUGH_ITEM3;
		else if(4 == itemNo) return Constants.rule.NEW_PRODUCTION_NOT_ENOUGH_ITEM4;
		
		return Constants.rule.NEW_PRODUCTION_ERROR;
	}
}
