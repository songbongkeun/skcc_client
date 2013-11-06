package com.example.skcc_client.gameRule;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.example.skcc_client.common.Global;
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
	
	public ProductionRule() {
		
		super();
		costTable = new LinkedHashMap<Integer, info>();
	}
	
	/**
	 * ���� ��Ģ�� �߰��Ѵ�.
	 * @param id				������ id
	 * @param productionExp		���� ����ġ
	 * @param canUse			��� ���� ����
	 * @param useAP				��� �ൿ��
	 * @param useExp			��� ����ġ
	 * @param limitLevel		���� ���� ����
	 * @param costTime			���� �ҿ� �ð�
	 * @param costAP			���� �ҿ� �ൿ��
	 * @param costMoney			���� �ҿ� �Ӵ�
	 * @param costItem1			���� �ҿ� ������1
	 * @param costItem1Quantity	���� �ҿ� ������1 ��
	 * @param costItem2			���� �ҿ� ������2
	 * @param costItem2Quantity	���� �ҿ� ������2 ��
	 * @param costItem3			���� �ҿ� ������3
	 * @param costItem3Quantity	���� �ҿ� ������3 ��
	 * @param costItem4			���� �ҿ� ������4
	 * @param costItem4Quantity	���� �ҿ� ������4 ��
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
		
		ArrayList<Item> list = new ArrayList<Item>();
		
		list.add(Global.getInstance().itemList.get(1001));
		list.add(Global.getInstance().itemList.get(1002));
		list.add(Global.getInstance().itemList.get(2002));
		
		return list;
	}
	
	/** Generate level rule for test [Level 1 ~ 8] */
	public void generateTestRule() {
		
		costTable.clear();
		
		long min = 1000 * 60;
		
		addRule(1001,	100,	false,	0,	0,	1,	4 * 60 * min,	1,	 50,    0, 0,    0, 0,    0, 0, 0, 0); // Ŀ�Ǻ�
		addRule(1002,	 50,	true,	2,	10, 1,	2 * 60 * min,	1,	 10,    0, 0,    0, 0,    0, 0, 0, 0); // ����
		addRule(1003,	100,	true,	6,	10, 1,	3 * 60 * min,	1,	 50,    0, 0,    0, 0,    0, 0, 0, 0); // �а���
		addRule(1004,	120,	true,	5,	10, 2,	5 * 60 * min,	1,	 80,    0, 0,    0, 0,    0, 0, 0, 0); // ���ڷ�
		addRule(2001,	100,	true,	3,	40, 1,	1 * 20 * min,	1,	100, 1001, 1,    0, 0,    0, 0, 0, 0); // �Ƹ޸�ī��
		addRule(2002,	150,	true,	4,	50, 2,	1 * 30 * min,	1,	150, 1001, 1, 1002, 1,    0, 0, 0, 0); // ī���
		addRule(3001,	 80,	true,	3,	10, 2,	1 * 60 * min,	1,	 50, 1003, 1,    0, 0,    0, 0, 0, 0); // ��
		addRule(3002,	150,	true,	6,	15, 2,	1 * 15 * min,	1,	100, 1004, 1, 1002, 1,    0, 0, 0, 0); // ���ڿ���
		addRule(3003,	175,	true,	6,	20, 3,	3 * 15 * min,	1,	100, 1001, 2, 1003, 1,    0, 0, 0, 0); // ��ī��
		addRule(3004,	190,	true,	8,	18, 3,	3 * 30 * min,	1,	 75, 1004, 2, 1003, 1,    0, 0, 0, 0); // ���ڸ���
		addRule(8001,	200,	true,	10,	50, 2,	1 * 30 * min,	1,	200, 1001, 1, 1002, 1, 9001, 1, 0, 0); // Cafe4U ī���
	}
}
