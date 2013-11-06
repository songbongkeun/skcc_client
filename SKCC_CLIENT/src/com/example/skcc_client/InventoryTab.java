package com.example.skcc_client;


import junit.framework.Assert;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.ui.inventory.ItemDetailDialog;

@SuppressLint("ValidFragment")
public class InventoryTab extends Fragment {
	
	Context mContext;
	
	public InventoryTab(Context context) {
		
		mContext = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_inventory, null);
		Log.d("INVENTORY", "View created.");
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		GridView gridView = (GridView) this.getActivity().findViewById(R.id.inventoryGrid);
		if(null != gridView) {
			InventoryGridAdapter adapter = new InventoryGridAdapter(this.getActivity());
			if(null != adapter) {
				gridView.setAdapter(adapter);
			}
		}
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				int listPosition = arg2 + (int)arg3;
				InventoryItem item = Global.getInstance().inventoryList.get(listPosition);
				Assert.assertNotNull("Oppss?", item);
				Log.d("INVENTORY", "selected Item : " + item);
				ItemDetailDialog dialog = ItemDetailDialog.newInstance(item);
				dialog.show(getFragmentManager(), "TAG");
			}
		});
		Log.d("INVENTORY", "Activity created.");
	}
	
	/**
	 * Refresh grid
	 */
	public void refreshGrid() {
		
		GridView gridView = (GridView) getActivity().findViewById(R.id.inventoryGrid);
		InventoryGridAdapter adapter = new InventoryGridAdapter(getActivity());
		gridView.setAdapter(adapter);
		
		Log.d("INVENTORY", "Grid refresh");
	}
}