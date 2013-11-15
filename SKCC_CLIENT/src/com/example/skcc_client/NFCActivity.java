package com.example.skcc_client;

import com.example.skcc_client.common.CodeHelper;
import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.Item;

import android.nfc.NfcAdapter;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NFCActivity extends Activity {
	private NfcAdapter mAdapter;
	private PendingIntent mPendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;
	private TextView name;
	private TextView quantity;
	private TextView itemType;
	private TextView description;
	private ImageView itemImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc);
		mAdapter = NfcAdapter.getDefaultAdapter(this);
		if (mAdapter == null) {
			Toast.makeText(this, "NFC 기능을 활성화 해 주세요", Toast.LENGTH_SHORT)
					.show();
			Log.d("NFC", "NFC 기능을 활성화 해 주세요");
		}

		Intent targetIntent = new Intent(this, NFCActivity.class);
		targetIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		mPendingIntent = PendingIntent.getActivity(this, 0, targetIntent, 0);

		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
		try {
			ndef.addDataType("*/*");
		} catch (MalformedMimeTypeException e) {
			throw new RuntimeException("fail", e);
		}

		mFilters = new IntentFilter[] { ndef, };
		mTechLists = new String[][] { new String[] { NfcF.class.getName() } };
		
		name = (TextView) findViewById(R.id.inventoryPopupName);
		itemImage = (ImageView) findViewById(R.id.inventoryPopupitemImage);
		quantity = (TextView) findViewById(R.id.inventoryPopupQuantity);
		itemType = (TextView) findViewById(R.id.inventoryPopupType);
		description = (TextView) findViewById(R.id.inventoryPopupDescription);
		
		waitNfcTagDiscovered();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.nfc, menu);

		return true;
	}

	private void waitNfcTagDiscovered() {
		Log.d("NFC", "waitNfcTagDiscovered");
		Intent passedIntent = getIntent();
		if (passedIntent != null) {
			String action = passedIntent.getAction();
			if (NfcAdapter.ACTION_ADAPTER_STATE_CHANGED.equals(action)) {
				Log.d("NFC", "ACTION_ADAPTER_STATE_CHANGED");
				procesNfc();
			} else if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
				Log.d("NFC", "ACTION_NDEF_DISCOVERED");
				procesNfc();
			} else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
				Log.d("NFC", "ACTION_TAG_DISCOVERED");
				procesNfc();
			} else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
				Log.d("NFC", "ACTION_TECH_DISCOVERED");
				procesNfc();
			} 
		}
	}

	private void procesNfc() {
		Log.d("NFC", "NFC Discovered.");
		Item tem = new Item(9001, 1, Constants.code.ITEM_TYPE_RECEIPE, "Cafe4U 카페라떼 레시피", "Cafe4U 레시피");
		setItemInfo(tem);
		InventoryItem itemInventory = new InventoryItem(tem, 1);
		Global.getInstance().addInventoryItem(itemInventory);
	}
	
	private void setItemInfo(Item item) {
		itemType.setText(CodeHelper.getItemTypeName(item.getItemType()), TextView.BufferType.NORMAL);
		name.setText(String.valueOf(item.getName()), TextView.BufferType.NORMAL);
		description.setText(String.valueOf(item.getDescription()), TextView.BufferType.NORMAL);
		quantity.setText(String.valueOf(1));
		int imageId = getResources().getIdentifier(item.getImageName(), "drawable", getPackageName());
		Bitmap image = BitmapFactory.decodeResource(getResources(), imageId);
		itemImage.setImageBitmap(image);
	}
}
