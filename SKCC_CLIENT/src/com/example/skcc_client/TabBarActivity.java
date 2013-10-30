package com.example.skcc_client;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class TabBarActivity extends LinearLayout {
	
	Context context;
	
	public TabBarActivity(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.tabbar, this, true);
	}
}