package com.example.skcc_client.ui.inventory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.skcc_client.R;
import com.example.skcc_client.gameObject.InventoryItem;

public class ItemDetailDialog extends DialogFragment {
	private EditText id;
	private EditText companyId;
	private EditText itemType;
	private EditText name;
	private EditText description;
	private EditText quantity;
	private ImageView itemImage;
	
	private InventoryItem item;
	
	public static ItemDetailDialog newInstance(InventoryItem item) {
		ItemDetailDialog dialog = new ItemDetailDialog();
		//set
		dialog.setItem(item);
		return dialog;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
		LayoutInflater mLayoutInflater = getActivity().getLayoutInflater();
		mBuilder.setView(mLayoutInflater.inflate(R.layout.tab_inventory_item_detail, null));
		mBuilder.setTitle("Item Detail");
		mBuilder.setMessage("Test");
		return mBuilder.create();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tab_inventory_item_detail, container, false);
		id = (EditText) v.findViewById(R.id.inventoryPopupitemId);
		companyId = (EditText) v.findViewById(R.id.inventoryPopupCompanyId);
		itemType = (EditText) v.findViewById(R.id.inventoryPopupType);
		name = (EditText) v.findViewById(R.id.inventoryPopupName);
		description = (EditText) v.findViewById(R.id.inventoryPopupDescription);
		quantity = (EditText) v.findViewById(R.id.inventoryPopupQuantity);
		itemImage = (ImageView) v.findViewById(R.id.inventoryPopupitemImage);
		setItemInformation();
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}

	public void setItemInformation() {
		id.setText(item.getId());
		companyId.setText(item.getCompanyId());
		itemType.setText(item.getItemType());
		name.setText(item.getName());
		description.setText(item.getDescription());
		quantity.setText(item.getQuantity());
		
		int imageId = getResources().getIdentifier(item.getImageName(), "drawable", "test");
		Bitmap image = BitmapFactory.decodeResource(getResources(), imageId);
		itemImage.setImageBitmap(image);
	}

	public void setItem(InventoryItem item) {
		this.item = item;
	}
	
}
