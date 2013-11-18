package com.example.skcc_client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("ValidFragment")
public class QuestTab extends Fragment {

	private static final String TAG = "UI";

	Context context;
	
	public QuestTab(Context context) {
		
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		View view = inflater.inflate(R.layout.tab_quest, null);
		
		Log.d(TAG, "QuestTab created.");
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		ExpandableListView listView = (ExpandableListView) this.getActivity().findViewById(R.id.questList);
		
		if(null != listView) {
			
			QuestListAdapter adapter = new QuestListAdapter(this.getActivity());
			
			if(null != adapter) {
				
				listView.setAdapter(adapter);
				
				Log.d(TAG, "Quest HO!!");
			}
		}
		
		Log.d(TAG, "Quest Activity created.");
	}

}