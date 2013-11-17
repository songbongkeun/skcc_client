package com.example.skcc_client;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.ProductionItem;
import com.example.skcc_client.ui.production.ProductionDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class ProductionTab extends Fragment {

	private static final String TAG = "UI";
	
	Context context;
	Handler refreshHandler;
	Runnable runnable;
	
	public ProductionTab(Context context) {
		
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		View view = inflater.inflate(R.layout.tab_production, null);
		
		Log.d(TAG, "ProductionTab created.");
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		Log.d(TAG, "Production Activity created.");
		
		// Create runnable
		runnable = new Runnable() {
			
		    public void run()  {
		    	
		    	refreshGrid();
		    	refreshHandler.postDelayed(this, Constants.system.GRID_REFRESH_MILLISECOND);
		    }
		};
		
		Log.d(TAG, "Production runnable created.");
		
		// Create refresh handler
		refreshHandler = new Handler();
		
		Log.d(TAG, "Production refresh handler created.");
		
		initGrid();
	}
	
	/**
	 * Initialize grid
	 */
	private void initGrid() {
		
		GridView gridView = (GridView) getActivity().findViewById(R.id.productionGrid);
		
		// Initialize GridView
		if(null == gridView.getAdapter()) {

			ProductionGridAdapter adapter = new ProductionGridAdapter(getActivity());
			
			if(null != adapter) {
				
				gridView.setAdapter(adapter);
				
				Log.d(TAG, "Production grid init.");
				
				
				// Set refresh handler
				refreshHandler.postDelayed(runnable, Constants.system.GRID_REFRESH_MILLISECOND);
				
				Log.d(TAG, "Set production refresh handler.");
			}
		}
		
		// Set GridView event : ItemClick
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View gridView, int position, long arg3) {
				
				Log.d(TAG, "Production grid item click : " + position);
				
				ProductionItem item = Global.getInstance().productionList.get(position); // clicked item
				
				///////////////////////////////////////////////////////////////////////////////////
				// 1. Rotten item -> Clean
				if(item.getState() == Constants.code.ITEM_STATE_ROTTEN) {
					
					// Clean rotten item
					Global.getInstance().playing.cleanRottenItem(item, position);

					// Show toast
					CharSequence msg = "썩은 " + item.getName() + "을(를) 치웠습니다!";
					Toast.makeText(gridView.getContext(), msg, Toast.LENGTH_SHORT).show();
					
					// Refresh
					refreshGrid();
				}

				///////////////////////////////////////////////////////////////////////////////////
				// 2. Finished item -> Add inventory and Clean
				else if(item.getState() == Constants.code.ITEM_STATE_FINISHED) {
					
					// Get finished item
					Global.getInstance().playing.getFinishedItem(position, item);

					// Show toast
					CharSequence msg = item.getName() + " 1개를 얻었습니다!";
					Toast.makeText(gridView.getContext(), msg, Toast.LENGTH_SHORT).show();
					
					// Refresh
					refreshGrid();
					
					// Refresh InventoryTab
					List<Fragment> tabs = getFragmentManager().getFragments();
					
					if(0 < tabs.size()) {
						InventoryTab tab = (InventoryTab) tabs.get(0);
						tab.refreshGrid();
					}
				}

				///////////////////////////////////////////////////////////////////////////////////
				// 3. Producing item -> Ask cancel to user
				else if(item.getState() == Constants.code.ITEM_STATE_PRODUCING) {
					
					// TODO: Make it!
				}

				///////////////////////////////////////////////////////////////////////////////////
				// 4. Vacant -> Start new producing
				else if(item.getState() == Constants.code.ITEM_STATE_NOTHING) {
					
					// New production popup
					DialogFragment newProductionPopup = ProductionDialog.newInstance(position);
					newProductionPopup.show(getFragmentManager(), "dialog");
				}
			}
		});
	}
	
	/**
	 * Refresh grid
	 */
	public void refreshGrid() {
		
		GridView gridView = (GridView) getActivity().findViewById(R.id.productionGrid);
		ProductionGridAdapter adapter = new ProductionGridAdapter(getActivity());
		
		gridView.setAdapter(adapter);
	}
	
	@Override
	public void onDestroyView() {
		
		super.onDestroyView();
		
		refreshHandler.removeCallbacksAndMessages(null); // *IMPORTANT* Remove refresh handler.
		Log.d(TAG, "Remove production refresh handler call backs");
	}
}