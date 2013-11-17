package com.example.skcc_client;

import java.sql.Timestamp;
import java.util.Date;

import com.example.skcc_client.common.Constants;
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
		return Constants.rule.PRODUCTION_MAX_COUNT;
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
			
			ProductionItem item = Global.getInstance().productionList.get(position);
			
			if(null == item) {

				Timestamp now = new Timestamp(new Date().getTime());
				item = new ProductionItem(position, 0, 0, Constants.code.ITEM_TYPE_NOTHING, "Vacant", "Vacant",  now, now, now);
				Global.getInstance().productionList.set(position, item);
			}
			
			ProductionGridItem gridItem = new ProductionGridItem(context, item);
			
			return gridItem;
		}
		else {
			
			return convertView;
		}
	}
}