package com.example.skcc_client.gameObject;

import java.sql.Timestamp;
import java.util.Date;

import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Constants.code;
import com.example.skcc_client.common.Global;

/**
 * Player
 * @author Jongkil Park
 */
public class Quest {
	
	private int id;
	private String title;
	private String description;
	private Timestamp startTime;
	private Timestamp endTime;
	private Timestamp completeTime;

	/**
	 * Constructor : 파라미터를 입력받아 생성한다.
	 * @param id			퀘스트 id
	 * @param title			이름
	 * @param description	설명
	 * @param startTime		시작시각
	 * @param endTime		종료시각
	 * @param completeTime	완료시각
	 */
	public Quest(int id, String title, String description, Timestamp startTime, Timestamp endTime, Timestamp completeTime) {
		
		super();
		
		this.id = id;
		this.title = title;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.completeTime = completeTime;
	}
	
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public Timestamp getCompleteTime() {
		return completeTime;
	}
	
	/**
	 * 퀘스트 상태를 리턴한다.
	 * @return 퀘스트 상태
	 * <br /> - Constants.code.QUEST_STATE_ONGOING
	 * <br /> - Constants.code.QUEST_STATE_EXPIRED
	 * <br /> - Constants.code.QUEST_STATE_COMPLETE
	 */
	public int getState() {
		
		int state = Constants.code.QUEST_STATE_NOTHING;
		
		if(null == this.completeTime) {
			
			if(this.endTime.compareTo(new Date()) > 0 ) {
				
				state = Constants.code.QUEST_STATE_EXPIRED;
			}
			else {
				
				state = Constants.code.QUEST_STATE_ONGOING;
			}
		}
		else {
			
			state = Constants.code.QUEST_STATE_COMPLETE;
		}
		
		return state; 
	}
	
	/**
	 * 퀘스트를 완료한다.
	 */
	public void completeQuest() {
		
		this.completeTime = new Timestamp(new Date().getTime());
	}
}
