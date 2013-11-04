package com.example.skcc_client.gameObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.skcc_client.common.Constants;

public class ProductionItem extends Item {
	
	private Timestamp startTime;
	private Timestamp endTime;
	private Timestamp expireTime;
	
	private int state;

	public ProductionItem(int id, int companyId, int itemType, String name, String description,
			Timestamp startTime, Timestamp endTime, Timestamp expireTime) {
		
		super(id, companyId, itemType, name, description);
		this.startTime = startTime;
		this.endTime = endTime;
		this.expireTime = expireTime;
		this.state = getState();
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
		
		String time = "";
		long timeMSec = 0;
		Date date = new Date();
		Timestamp now = new Timestamp(date.getTime());

		if(getState() == Constants.code.ITEM_STATE_ROTTEN) {
			
			time = "Rotten";
			return time;
		}
		else if(getState() == Constants.code.ITEM_STATE_PRODUCING) {
			
			date = new Date(endTime.getTime() - now.getTime());
		}
		else if(getState() == Constants.code.ITEM_STATE_FINISHED) {
			
			date = new Date(expireTime.getTime() - now.getTime());
		}
		
		timeMSec = date.getTime(); 
		
		// mm:ss
		if(timeMSec < 60 * 60 * 1000) {
			
			SimpleDateFormat mmss = new SimpleDateFormat("mm:ss", Locale.KOREA);
			time = mmss.format(date);
		}
		// hh:mm:ss
		else if(timeMSec < 24 * 60 * 60 * 1000) {
			
			SimpleDateFormat hhmmss = new SimpleDateFormat("hh:mm:ss", Locale.KOREA);
			time = hhmmss.format(date);
		}
		// dd hh
		else if(timeMSec < 24 * 60 * 60 * 1000) {
			
			SimpleDateFormat ddhh = new SimpleDateFormat("dd hh", Locale.KOREA);
			time = ddhh.format(date);
		}
		
		return time;
	}

	public int getState() {
		
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
}