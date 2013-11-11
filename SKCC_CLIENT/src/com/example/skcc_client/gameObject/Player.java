package com.example.skcc_client.gameObject;

import com.example.skcc_client.common.Global;

/**
 * Player
 * @author Jongkil Park
 */
public class Player {
	
	private String id;
	private String name;
	private int level;
	private long exp;
	private int actionPoint;
	private long money;
	
	/**
	 * Constructor : 파라미터를 입력받아 생성한다.
	 * @param id			사용자 id
	 * @param name			이름
	 * @param level			레벨
	 * @param exp			경험치
	 * @param actionPoint	행동력
	 * @param money			머니
	 */
	public Player(String id, String name, long exp, int actionPoint, long money) {
		
		super();
		
		this.id = id;
		this.name = name;
		this.level = Global.getInstance().levelRule.getLevel(exp);
		this.exp = exp;
		this.actionPoint = actionPoint;
		this.money = money;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public long getExp() {
		return exp;
	}

	public int getActionPoint() {
		return actionPoint;
	}

	public void addExp(long exp) {
		this.exp += exp;
	}

	public void addActionPoint(int actionPoint) {
		this.actionPoint += actionPoint;
	}

	public void removeActionPoint(int actionPoint) {
		this.actionPoint -= actionPoint;
	}

	public long getMoney() {
		return money;
	}

	public void addMoney(long money) {
		this.money += money;
	}

	public void removeMoney(long money) {
		this.money -= money;
	}
}
