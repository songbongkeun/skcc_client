package com.example.skcc_client.ui.nfc;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skcc_client.R;
import com.example.skcc_client.common.CodeHelper;
import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.Item;

public class NfcTagDiscoverDialog extends DialogFragment {
	private InventoryItem item;
	
	private TextView name;
	private TextView quantity;
	private TextView itemType;
	private TextView description;
	private ImageView itemImage;
	
	public static NfcTagDiscoverDialog newInstance(Intent intent) {
		NfcTagDiscoverDialog dialog = new NfcTagDiscoverDialog();
		InventoryItem item = parseNfcEvent(intent);
		dialog.setItem(item);
		return dialog;
	}
	

	private static InventoryItem parseNfcEvent(Intent intent) {
		Item tem = new Item(9001, 1, Constants.code.ITEM_TYPE_RECEIPE, "Cafe4U 카페라떼 레시피", "Cafe4U 레시피");
		InventoryItem itemInventory = new InventoryItem(tem, 1);
		return itemInventory;
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_nfc, container, false);
		name = (TextView) v.findViewById(R.id.inventoryPopupName);
		itemImage = (ImageView) v.findViewById(R.id.inventoryPopupitemImage);
		quantity = (TextView) v.findViewById(R.id.inventoryPopupQuantity);
		itemType = (TextView) v.findViewById(R.id.inventoryPopupType);
		description = (TextView) v.findViewById(R.id.inventoryPopupDescription);
		setItemInformation();
		Global.getInstance().addInventoryItem(item);
		return v;
	}
	
	public void setItem(InventoryItem item) {
		this.item = item;
	}
	
	public void setItemInformation() {
		itemType.setText(CodeHelper.getItemTypeName(item.getItemType()), TextView.BufferType.NORMAL);
		name.setText(String.valueOf(item.getName()), TextView.BufferType.NORMAL);
		description.setText(String.valueOf(item.getDescription()), TextView.BufferType.NORMAL);
		quantity.setText(String.valueOf(item.getQuantity()), TextView.BufferType.NORMAL);

		int imageId = getResources().getIdentifier(item.getImageName(), "drawable", getActivity().getPackageName());
		Bitmap image = null;
		
		// Create BitMap or load from BitMapCache
		if(Global.getInstance().getBitMapCache().isExistsBitmap(imageId)) {
			image = Global.getInstance().getBitMapCache().getBitmap(imageId);
		}
		else {
			image = BitmapFactory.decodeResource(getResources(), imageId);
			Global.getInstance().getBitMapCache().putBitmap(imageId, image);
		}
		
		itemImage.setImageBitmap(image);
	}

}
