package com.example.skcc_client;

import com.example.skcc_client.common.Constants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

@SuppressLint("ValidFragment")
public class ProductionTab extends Fragment {
	
	Context context;
	Handler refreshHandler;
	Runnable runnable;
	
	public ProductionTab(Context context) {
		
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		View view = inflater.inflate(R.layout.tab_production, null);
		
		Log.d("PRODUCTION", "View created.");
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		Log.d("PRODUCTION", "Activity created.");
		
		initGrid();
	}
	
	/**
	 * Initialize grid
	 */
	private void initGrid() {
		
		GridView gridView = (GridView) getActivity().findViewById(R.id.productionGrid);
		
		if(null == gridView.getAdapter()) {

			ProductionGridAdapter adapter = new ProductionGridAdapter(getActivity());
			
			if(null != adapter) {
				
				gridView.setAdapter(adapter);
				Log.d("PRODUCTION", "Grid init");

				// Create refresh handler
				refreshHandler = new Handler();
				runnable = new Runnable() {
					
				    public void run()  {
				    	
				    	refreshGrid();
				    	refreshHandler.postDelayed(this, Constants.system.GRID_REFRESH_MILLISECOND);
				    }
				};
				
				refreshHandler.postDelayed(runnable, Constants.system.GRID_REFRESH_MILLISECOND);
			}
		}
	}
	
	/**
	 * Refresh grid
	 */
	private void refreshGrid() {
		
		GridView gridView = (GridView) getActivity().findViewById(R.id.productionGrid);
		ProductionGridAdapter adapter = new ProductionGridAdapter(getActivity());
		gridView.setAdapter(adapter);
		
		Log.d("PRODUCTION", "Grid refresh");
	}
	
	@Override
	public void onDestroyView() {
		
		super.onDestroyView();
		
		refreshHandler.removeCallbacks(runnable); // *IMPORTANT* Remove refresh handler.
		Log.d("PRODUCTION", "Remove refresh handler");
	}
}