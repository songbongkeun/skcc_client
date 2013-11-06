package com.example.skcc_client.ui.production;

import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.Item;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ProductionListAdapter extends BaseAdapter {
	
	private Context context;

	public ProductionListAdapter(Context context) {

		this.context = context;
	}

	@Override
	public int getCount() {
		return Global.getInstance().productionRule.getProductionRuleList().size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			
			// List item �� �����´�.
			Item item = Global.getInstance().productionRule.getProductionRuleList().get(position);
			ProductionListItem listItem = new ProductionListItem(context, item);
			
			return listItem;
		}
		else {
			
			return convertView;
		}
	}
}