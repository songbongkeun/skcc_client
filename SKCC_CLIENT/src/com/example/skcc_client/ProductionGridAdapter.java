package com.example.skcc_client;

import java.util.ArrayList;

import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.ProductionItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ProductionGridAdapter extends BaseAdapter {
	
	private Context context;

	public ProductionGridAdapter(Context context) {

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
			ProductionItem item = Global.getInstance().productionList.get(position);
			ProductionGridItem gridItem = new ProductionGridItem(context, item);
			
			return gridItem;
		}
		else {
			
			return convertView;
		}
	}
}