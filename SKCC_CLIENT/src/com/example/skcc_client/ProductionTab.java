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
public class ProductionTab extends Fragment {
	Context mContext;
	
	public ProductionTab(Context context) {
		
		mContext = context;
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

		GridView gridView = (GridView) this.getActivity().findViewById(R.id.productionGrid);
		
		if(null != gridView) {

			ProductionItemGridAdapter adapter = new ProductionItemGridAdapter(this.getActivity());
			
			if(null != adapter) {
				
				gridView.setAdapter(adapter);
			}
		}
		
		Log.d("PRODUCTION", "Activity created.");
	}

}