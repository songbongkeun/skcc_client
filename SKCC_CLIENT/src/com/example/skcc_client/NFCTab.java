package com.example.skcc_client;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class NFCTab extends Fragment {
	private Context mContext;
	private NfcAdapter mAdapter;
	
	public NFCTab(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_nfc, null);
		mAdapter = NfcAdapter.getDefaultAdapter(getActivity());
		startNfcEvent();
		Log.d("NFC", "NFC CREATE");
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("NFC", "NFC Start.");
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("NFC", "NFC End.");
	}
	
	public void startNfcEvent() {
	    IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
	    IntentFilter[] mWriteTagFilters = new IntentFilter[] { tagDetected };
	    Intent targetIntent = new Intent(getActivity(), NFCActivity.class);
	    targetIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
	    PendingIntent mPendingIntent = PendingIntent.getActivity(getActivity(), 0, targetIntent, 0);
	    String[][] mTechLists = new String[][] { new String[] { NfcF.class.getName() } };
		
	    mAdapter.enableForegroundDispatch(getActivity(), mPendingIntent, mWriteTagFilters, mTechLists);
	}
	
	public void endNfcEvent() {
		mAdapter.disableForegroundDispatch(getActivity());
	}
}