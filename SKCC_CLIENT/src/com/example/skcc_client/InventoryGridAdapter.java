package com.example.skcc_client;

import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class InventoryGridAdapter extends BaseAdapter {
	
	private Context context;

	public InventoryGridAdapter(Context context) {

		this.context = context;
	}

	@Override
	public int getCount() {
		return Global.getInstance().inventoryList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			
			// Grid item 을 가져온다.
			InventoryItem item = Global.getInstance().inventoryList.get(position);
			InventoryGridItem gridItem = new InventoryGridItem(context, item);
			
			return gridItem;
		}
		else {
			
			return convertView;
		}
	}
}