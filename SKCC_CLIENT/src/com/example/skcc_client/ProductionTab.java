package com.example.skcc_client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.ProductionItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
		
		// Create runnable
		runnable = new Runnable() {
			
		    public void run()  {
		    	
		    	refreshGrid();
		    	refreshHandler.postDelayed(this, Constants.system.GRID_REFRESH_MILLISECOND);
		    }
		};
		
		Log.d("PRODUCTION", "Create runnable.");
		
		// Create refresh handler
		refreshHandler = new Handler();
		
		Log.d("PRODUCTION", "Create refresh handler.");
		
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
				
				Log.d("PRODUCTION", "Grid init");
				
				
				// Set refresh handler
				refreshHandler.postDelayed(runnable, Constants.system.GRID_REFRESH_MILLISECOND);
				
				Log.d("PRODUCTION", "Set refresh handler.");
			}
		}
		
		// Set GridView event : ItemClick
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View gridView, int position, long arg3) {
				
				Log.d("PRODUCTION", "Grid item click : " + position);
				
				ProductionItem item = Global.getInstance().productionList.get(position); // clicked item
				
				///////////////////////////////////////////////////////////////////////////////////
				// 1. Rotten item -> Clean
				if(item.getState() == Constants.code.ITEM_STATE_ROTTEN) {
					
					// Clean rotten item
					itemToVacant(position);
					Log.d("PRODUCTION", "Rotten item removed : " + position);

					// Show toast
					CharSequence msg = "썩은 " + item.getName() + "을(를) 치웠습니다!";
					Toast.makeText(gridView.getContext(), msg, Toast.LENGTH_SHORT).show();
				}

				///////////////////////////////////////////////////////////////////////////////////
				// 2. Finished item -> Add inventory and Clean
				else if(item.getState() == Constants.code.ITEM_STATE_FINISHED) {
					
					// Check inventory has same item
					boolean hasSameItem = false;
					ArrayList<InventoryItem> list = Global.getInstance().inventoryList;
					Iterator<InventoryItem> iterator = list.iterator();
					
					while(iterator.hasNext()) {
						
						InventoryItem inven = iterator.next();
						if(item.getId() == inven.getId()) {
							
							inven.addQuantity(1);
							hasSameItem = true;
							break;
						}
					}
					
					// If no have same item, add new item
					if(!hasSameItem) {
						
						InventoryItem producedItem = new InventoryItem(item, 1);
						Global.getInstance().inventoryList.add(producedItem);
					}
					
					// Clean got item
					itemToVacant(position);
					
					// Show toast
					CharSequence msg = item.getName() + " 1개를 얻었습니다!";
					Toast.makeText(gridView.getContext(), msg, Toast.LENGTH_SHORT).show();

					Log.d("PRODUCTION", "Finished item get : " + position);
				}

				///////////////////////////////////////////////////////////////////////////////////
				// 3. Producing item -> Ask cancel to user
				else if(item.getState() == Constants.code.ITEM_STATE_PRODUCING) {
					
					// TODO: Make it!
				}

				///////////////////////////////////////////////////////////////////////////////////
				// 4. Vacant -> Start new producing
				else if(item.getState() == Constants.code.ITEM_STATE_NOTHING) {
					
					// TODO: Make it!
				}
			}
		});
	}
	
	/**
	 * Refresh grid
	 */
	protected void refreshGrid() {
		
		// Fill empty lot
		GridView gridView = (GridView) getActivity().findViewById(R.id.productionGrid);
		ProductionGridAdapter adapter = new ProductionGridAdapter(getActivity());
		
		while(Global.getInstance().productionList.size() < Constants.rule.PRODUCTION_MAX_COUNT) {
			
			Timestamp now = new Timestamp(new Date().getTime());
			ProductionItem vacant = new ProductionItem(0, 1, Constants.code.ITEM_TYPE_NOTHING
					, "Vacant", "Vacant", now, now, now);
			
			Global.getInstance().productionList.add(vacant);
		}
		
		gridView.setAdapter(adapter);
		
		Log.d("PRODUCTION", "Grid refresh");
	}
	
	/**
	 * 특정 위치를 공터로 바꾼다.
	 * @param position	공터로 바꿀 위치	
	 */
	public void itemToVacant(int position) {

		Timestamp now = new Timestamp(new Date().getTime());
		ProductionItem vacant = new ProductionItem(0, 1, Constants.code.ITEM_TYPE_NOTHING
				, "Vacant", "Vacant", now, now, now);
		Global.getInstance().productionList.set(position, vacant);
		refreshGrid();
	}
	
	
	/**
	 * 살아있는 아이템의 개수를 리턴한다.
	 * @return	Producing, Finished 상태의 아이템 개수
	 */
	public int liveItemCount() {
		
		int totalCount = 0;
		
		ArrayList<ProductionItem> list = Global.getInstance().productionList;
		Iterator<ProductionItem> iterator = list.iterator();
		
		while(iterator.hasNext()) {
			
			ProductionItem item = iterator.next();
			if(item.getState() == Constants.code.ITEM_STATE_PRODUCING
				|| item.getState() == Constants.code.ITEM_STATE_FINISHED) {
				
				++totalCount;
			}
		}
		
		return totalCount;
	}
	
	@Override
	public void onDestroyView() {
		
		super.onDestroyView();
		
		refreshHandler.removeCallbacksAndMessages(null); // *IMPORTANT* Remove refresh handler.
		Log.d("PRODUCTION", "Remove refresh handler call backs");
	}
}