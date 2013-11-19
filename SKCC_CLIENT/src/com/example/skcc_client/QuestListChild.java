package com.example.skcc_client;

import java.util.Date;

import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.TextHelper;
import com.example.skcc_client.gameObject.Quest;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestListChild extends LinearLayout {
	
	protected QuestListChild(Context context) {
		
		super(context);
	}
	
	public QuestListChild(Context context, Quest quest) {
		
		super(context);
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set state values
		// Set state color
		int stateColor = 0;
		
		if(quest.getState() == Constants.code.QUEST_STATE_NOTHING) {
			stateColor = context.getResources().getColor(R.color.item_nothing);
		}
		else if(quest.getState() == Constants.code.QUEST_STATE_ONGOING) {
			stateColor = context.getResources().getColor(R.color.item_producing);
		}
		else if(quest.getState() == Constants.code.QUEST_STATE_COMPLETE) {
			stateColor = context.getResources().getColor(R.color.item_finished);
		}
		else if(quest.getState() == Constants.code.QUEST_STATE_EXPIRED) {
			stateColor = context.getResources().getColor(R.color.item_rotten);
		}
		
		// Set text color
		int descriptionColor = context.getResources().getColor(R.color.quest_list_description_color);
		int termColor = context.getResources().getColor(R.color.quest_list_term_color);
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set quest description to TextView
		TextView description = new TextView(context);
		description.setHeight(300);
		
		if(0 != quest.getId()) {
			
			description.setTypeface(description.getTypeface(), Typeface.BOLD);
			description.setText(quest.getDescription());
			description.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.quest_title_textSize));
			description.setTextColor(descriptionColor);
			description.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		}
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set quest term to TextView
		TextView term = new TextView(context);
		term.setHeight(100);
		
		if(0 != quest.getId()) {
			
			Date startDate = new Date(quest.getStartTime().getTime());
			Date endDate = new Date(quest.getEndTime().getTime());
			
			term.setTypeface(description.getTypeface(), Typeface.BOLD);
			term.setText("±â°£ : " + TextHelper.getFormattedDate(startDate) + " ~ " + TextHelper.getFormattedDate(endDate));
			term.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.quest_title_textSize));
			term.setTextColor(termColor);
			term.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set layout
		this.setOrientation(LinearLayout.VERTICAL);
		this.setBackgroundResource(R.color.quest_list_background_color);
		this.addView(description);
		this.addView(term);
	}
	
}
