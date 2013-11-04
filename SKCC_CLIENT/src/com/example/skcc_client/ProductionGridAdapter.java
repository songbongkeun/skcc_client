package com.example.skcc_client;

import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.ProductionItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ProductionGridAdapter extends BaseAdapter {
	
	private Context context;

	public ProductionGridAdapter(Context context) {

		this.context = context;
	}

	@Override
	public int getCount() {
		return Global.getInstance().productionList.size();
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
			
			// Grid item �� �����´�.
			ProductionItem item = Global.getInstance().productionList.get(position);
			ProductionGridItem gridItem = new ProductionGridItem(context, item);
			
			return gridItem;
		}
		else {
			
			return convertView;
		}
	}
}