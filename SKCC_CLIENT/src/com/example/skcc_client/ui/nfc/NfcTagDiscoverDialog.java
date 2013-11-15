package com.example.skcc_client.ui.nfc;

import com.example.skcc_client.R;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.ui.inventory.ItemDetailDialog;

import android.app.Dialog;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

public class NfcTagDiscoverDialog extends DialogFragment {
	public static NfcTagDiscoverDialog newInstance() {
		NfcTagDiscoverDialog dialog = new NfcTagDiscoverDialog();
		return dialog;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.popup_nfc_discover, container, false);
		return v;
	}
	
}
