package com.example.skcc_client.ui.production;

import java.util.ArrayList;

import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.Item;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ProductionListAdapter extends BaseAdapter {

	private static final String TAG = "UI";
	
	private Context context;
	private ArrayList<ProductionListItem> viewList;
 
	public ProductionListAdapter(Context context) {
		
		this.context = context;
		viewList = new ArrayList<ProductionListItem>();
		ArrayList<Item> productionItemList = Global.getInstance().productionRule.getProductionRuleList();
		
		for(int i = 0; i < productionItemList.size(); i++) {
			
			viewList.add(new ProductionListItem(context, productionItemList.get(i)));
		}
	}

	@Override
	public int getCount() {
		return viewList.size();
	}

	@Override
	public Object getItem(int position) {
		
		return viewList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Log.d(TAG, "New production item list position = " + position);
		
		return viewList.get(position);
	}
}