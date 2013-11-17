package com.example.skcc_client.gameObject;

import java.sql.Timestamp;
import java.util.Date;

import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.TextHelper;

public class ProductionItem extends Item {
	
	private int position;
	private Timestamp startTime;
	private Timestamp endTime;
	private Timestamp expireTime;
	
	private int state;
	
	/**
	 * Constructor : Item�� �Է� �޾� ����
	 * @param position		���� ��ġ
	 * @param item			Item
	 * @param startTime		���� ���� �ð�
	 * @param endTime		���� ���� �ð�
	 * @param expireTime	������� �ð�
	 */
	public ProductionItem(int position, Item item, Timestamp startTime, Timestamp endTime, Timestamp expireTime) {
		
		super(item);
		this.position = position;
		this.startTime = startTime;
		this.endTime = endTime;
		this.expireTime = expireTime;
	}
	
	/**
	 * Constructor : �Ķ���͸� �Է¹޾� ����
	 * @param position		���� ��ġ
	 * @param id			������ id
	 * @param companyId		ȸ�� id
	 * @param itemType		������ Ÿ��
	 * @param name			�̸�
	 * @param description	����
	 * @param startTime		���� ���� �ð�
	 * @param endTime		���� ���� �ð�
	 * @param expireTime	������� �ð�
	 */
	public ProductionItem(int position, int id, int companyId, int itemType, String name, String description,
			Timestamp startTime, Timestamp endTime, Timestamp expireTime) {
		
		super(id, companyId, itemType, name, description);
		this.position = position;
		this.startTime = startTime;
		this.endTime = endTime;
		this.expireTime = expireTime;
		this.state = getState();
	}

	public int getPosition() {
		return position;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public Timestamp getExpireTime() {
		return expireTime;
	}
	
	public String getRemainTime() {
		
		long timeMSec = 0;
		Date date = new Date();
		Timestamp now = new Timestamp(date.getTime());

		if(getState() == Constants.code.ITEM_STATE_ROTTEN) {
			
			return "Rotten";
		}
		else if(getState() == Constants.code.ITEM_STATE_PRODUCING) {
			
			date = new Date(endTime.getTime() - now.getTime());
		}
		else if(getState() == Constants.code.ITEM_STATE_FINISHED) {
			
			date = new Date(expireTime.getTime() - now.getTime());
		}
		
		timeMSec = date.getTime(); 
		
		return TextHelper.remainTime(timeMSec);
	}

	public int getState() {
		
		if(0 == getId()) {
			return Constants.code.ITEM_STATE_NOTHING;
		}
		
		Date date = new Date();
		Timestamp now = new Timestamp(date.getTime());
		
		if(now.compareTo(endTime) < 0) {
			
			state = Constants.code.ITEM_STATE_PRODUCING;
		}
		else {
			
			if(now.compareTo(expireTime) < 0) {
				
				state = Constants.code.ITEM_STATE_FINISHED;
			}
			else {
				
				state = Constants.code.ITEM_STATE_ROTTEN;
			}
		}
		
		return state;
	}

	@Override
	public String toString() {
		
		String parent = super.toString();
		
		return "ProductionItem ["
				+ ", position=" + position
				+ ", startTime=" + startTime
				+ ", endTime=" + endTime
				+ ", expireTime=" + expireTime
				+ ", state=" + state
				+  " " + parent + "]";
	}
}