package com.example.skcc_client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.skcc_client.ui.nfc.NfcTagDiscoverDialog;

@SuppressLint("ValidFragment")
public class NFCTab extends Fragment {
	private Context mContext;
	
	public NFCTab(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_nfc, null);
		Log.d("NFC", "NFC CREATE");
		
		Log.d("NFC", "end");
		Button button = (Button) view.findViewById(R.id.nfcFindButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				NfcTagDiscoverDialog dialog = NfcTagDiscoverDialog.newInstance();
//				dialog.show(getFragmentManager(), "TAG2");
				Intent targetIntent = new Intent(getActivity(), NFCActivity.class);
				startActivity(targetIntent);
			}
		});
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
	
}