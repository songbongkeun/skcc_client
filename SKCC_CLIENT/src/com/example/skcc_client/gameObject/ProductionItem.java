package com.example.skcc_client.gameObject;

import java.sql.Timestamp;
import java.util.Date;

import com.example.skcc_client.common.Constants;

public class ProductionItem extends Item {
	
	private Timestamp startTime;
	private Timestamp endTime;
	private Timestamp expireTime;
	
	private int state;

	public ProductionItem(int id, int companyId, int itemType, String name, String description,
			Timestamp startTime, Timestamp endTime, Timestamp expireTime, int state) {
		
		super(id, companyId, itemType, name, description);
		this.startTime = startTime;
		this.endTime = endTime;
		this.expireTime = expireTime;
		this.state = state;
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

	public int getState() {
		
		Date date = new Date();
		Timestamp now = new Timestamp(date.getTime());
		
		if(now.compareTo(endTime) > 0) {
			
			state = Constants.code.ITEM_STATE_PRODUCING;
		}
		else {
			
			if(now.compareTo(expireTime) > 0) {
				
				state = Constants.code.ITEM_STATE_FINISHED;
			}
			else {
				
				state = Constants.code.ITEM_STATE_ROTTEN;
			}
		}
		
		return state;
	}
}