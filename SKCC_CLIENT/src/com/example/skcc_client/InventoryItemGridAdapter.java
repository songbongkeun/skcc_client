package com.example.skcc_client;

import java.util.ArrayList;

import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class InventoryItemGridAdapter extends BaseAdapter {
	
	private Context context;

	public InventoryItemGridAdapter(Context context) {

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

			ImageView imageView;
			
			imageView = new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(0, 0, 0, 0);
			
			ArrayList<InventoryItem> list = Global.getInstance().inventoryList;
			int imageId = context.getResources().getIdentifier(list.get(position).getImageName(), "drawable", context.getPackageName());
			imageView.setImageResource(imageId);
			
			return imageView;
		}
		else {
			
			return convertView;
		}
	}
}