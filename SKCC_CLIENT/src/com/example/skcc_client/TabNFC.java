package com.example.skcc_client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class TabNFC extends Fragment {
		Context mContext;
		
		public TabNFC(Context context) {
			mContext = context;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			
			View view = inflater.inflate(R.layout.tab_nfc, null);
			
	    	return view;
		}

}