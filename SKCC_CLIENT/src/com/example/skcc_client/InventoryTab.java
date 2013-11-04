package com.example.skcc_client;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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