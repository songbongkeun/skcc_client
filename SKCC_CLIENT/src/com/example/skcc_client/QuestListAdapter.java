package com.example.skcc_client;

import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.Quest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class QuestListAdapter extends BaseExpandableListAdapter {
	
	private Context context;

	public QuestListAdapter(Context context) {

		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		
		return new QuestListChild(context, Global.getInstance().questList.get(groupPosition));
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Quest getGroup(int groupPosition) {
		
		return Global.getInstance().questList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		
		return Global.getInstance().questList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		
		return new QuestListGroup(context, Global.getInstance().questList.get(groupPosition));
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
}