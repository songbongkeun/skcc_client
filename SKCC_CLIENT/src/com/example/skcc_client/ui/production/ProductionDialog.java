package com.example.skcc_client.ui.production;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.example.skcc_client.R;

public class ProductionDialog extends DialogFragment {
	
	private int position;
	
	public static ProductionDialog newInstance(int position) {
		
		ProductionDialog dialog = new ProductionDialog();
		
		dialog.setPosition(position);
		
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
		
		ListView listView = (ListView) view.findViewById(R.id.newProductionList);
		ProductionListAdapter adapter = new ProductionListAdapter(getActivity());
		listView.setAdapter(adapter);

		Log.d("PRODUCTION", "Production Dialog View created.");
		
		return view;
	}
	
	@Override
	public void onStop() {
		
		super.onStop();
	}

	public void setPosition(int position) {
		
		this.position = position;
	}
	
}
