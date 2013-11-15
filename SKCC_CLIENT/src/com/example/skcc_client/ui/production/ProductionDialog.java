package com.example.skcc_client.ui.production;

import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.skcc_client.InventoryTab;
import com.example.skcc_client.ProductionTab;
import com.example.skcc_client.R;
import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.Item;

public class ProductionDialog extends DialogFragment {
	
	private int productionPosition;
	
	public static ProductionDialog newInstance(int productionPosition) {
		
		ProductionDialog dialog = new ProductionDialog();
		
		dialog.setPosition(productionPosition);
		
		return dialog;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.popup_new_production, container, false);
		
		// List
		int listViewId = R.id.newProductionList;
		ListView listView = (ListView) view.findViewById(listViewId);
		ProductionListAdapter adapter = new ProductionListAdapter(getActivity());
		listView.setAdapter(adapter);

		Log.d("PRODUCTION", "Production Dialog View created.");
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View listView, int position, long id) {
				
				Log.d("PRODUCTION", "New production list item click : " + position);
				
				// Get clicked item
				Item item = Global.getInstance().productionRule.getProductionRuleList().get(position);
				
				// Start new production
				int returnCode = Global.getInstance().playing.startNewProduction(item, productionPosition);
				
				// Success new production
				if(Constants.rule.NEW_PRODUCTION_OK == returnCode) {
				
					// Refresh grid
					List<Fragment> tabs = getFragmentManager().getFragments();
					
					if(0 < tabs.size()) {

						((InventoryTab) tabs.get(0)).refreshGrid(); // Refresh InventoryTab
						((ProductionTab) tabs.get(1)).refreshGrid(); // Refresh ProductionTab
					}
					
					// Close popup
					onStop();
				}
			}
		});
		
		
		return view;
	}
	
	@Override
	public void onStop() {
		
		Log.d("PRODUCTION", "Close new production popup");
		
		super.onStop();
		super.dismiss();
	}

	public void setPosition(int position) {
		
		this.productionPosition = position;
	}
	
}
