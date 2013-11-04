package com.example.skcc_client.gameRule;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class LevelRule {
	
	private class info {
		
		long nextLevelExp;
		int maxActionPoint;
		long actionPointRecoveryDelay;
	}
	
	private LinkedHashMap<Integer, info> levelTable;
	
	public LevelRule() {
		
		super();
		levelTable = new LinkedHashMap<Integer, info>();
	}
	
	/**
	 * Level ��Ģ�� �߰��Ѵ�.
	 * @param level						����
	 * @param nextLevelExp				���� ���������� ����ġ
	 * @param maxActionPoint			�ִ� �ൿ��
	 * @param actionPointRecoverDelay	�ൿ�� 1 ȸ���ϴµ� �ɸ��� �ð�(millisecond)
	 */
	public void addRule(int level, long nextLevelExp, int maxActionPoint, long actionPointRecoveryDelay) {
		
		info i = new info();
		i.nextLevelExp = nextLevelExp;
		i.maxActionPoint = maxActionPoint;
		i.actionPointRecoveryDelay = actionPointRecoveryDelay;
		
		levelTable.put(level, i);
	}
	
	public long getNextLevelExp(int level) {
		
		return levelTable.get(level).nextLevelExp;
	}
	
	public int getMaxActionPoint(int level) {
		
		return levelTable.get(level).maxActionPoint;
	}
	
	public long getActionPointRecoveryDelay(int level) {
		
		return levelTable.get(level).actionPointRecoveryDelay;
	}
	
	public int getLevel(long exp) {
		
		int returnLevel = 0;
		
		if(levelTable.isEmpty()) return returnLevel;
		
		Iterator<Integer> keyData = levelTable.keySet().iterator();
		Integer key;
		
		while(keyData.hasNext()) {
			
			key = (Integer)keyData.next();
			info i = levelTable.get(key);
			
			if(exp < i.nextLevelExp) {
				
				returnLevel = key.intValue();
			}
			else {
				
				break;
			}
		}
		
		return returnLevel;
	}
	
	/** Generate level rule for test [Level 1 ~ 8] */
	public void generateTestRule() {
		
		levelTable.clear();
		
		long hour = 1000 * 60 * 60;
		
		addRule(1, 1000, 10, hour);
		addRule(2, 2000, 15, hour);
		addRule(3, 4000, 20, hour);
		addRule(4, 8000, 25, hour);
		addRule(5, 16000, 30, hour);
		addRule(6, 32000, 35, hour);
		addRule(7, 64000, 40, hour);
		addRule(8, 128000, 45, hour);
	}
}
