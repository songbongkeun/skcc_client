package com.example.skcc_client;

import com.example.skcc_client.common.Constants;
import com.example.skcc_client.gameObject.Quest;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuestListGroup extends LinearLayout {
	
	protected QuestListGroup(Context context) {
		
		super(context);
	}
	
	public QuestListGroup(Context context, Quest quest) {
		
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
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set quest title to TextView
		TextView title = new TextView(context);
		title.setHeight(context.getResources().getDimensionPixelSize(R.dimen.quest_title_height));
		
		if(0 != quest.getId()) {
			
			title.setTypeface(title.getTypeface(), Typeface.BOLD);
			title.setText(quest.getTitle());
			title.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.quest_title_textSize));
			title.setTextColor(stateColor);
			title.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set layout
		this.setOrientation(LinearLayout.VERTICAL);
		this.addView(title);
	}
	
}
