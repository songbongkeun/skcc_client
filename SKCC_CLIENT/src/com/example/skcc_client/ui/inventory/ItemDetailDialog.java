package com.example.skcc_client.ui.inventory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skcc_client.R;
import com.example.skcc_client.gameObject.InventoryItem;

public class ItemDetailDialog extends DialogFragment {
	private TextView id;
	private TextView companyId;
	private TextView itemType;
	private TextView name;
	private TextView description;
	private TextView quantity;
	private ImageView itemImage;
	
	private InventoryItem item;
	
	public static ItemDetailDialog newInstance(InventoryItem item) {
		ItemDetailDialog dialog = new ItemDetailDialog();
		dialog.setItem(item);
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
		View v = inflater.inflate(R.layout.tab_inventory_item_detail, container, false);
		id = (TextView) v.findViewById(R.id.inventoryPopupitemId);
		companyId = (TextView) v.findViewById(R.id.inventoryPopupCompanyId);
		itemType = (TextView) v.findViewById(R.id.inventoryPopupType);
		name = (TextView) v.findViewById(R.id.inventoryPopupName);
		description = (TextView) v.findViewById(R.id.inventoryPopupDescription);
		quantity = (TextView) v.findViewById(R.id.inventoryPopupQuantity);
		itemImage = (ImageView) v.findViewById(R.id.inventoryPopupitemImage);
		
		setItemInformation();
		Log.d("INVENTORY", "ItemDetailDialog Create");
		return v;
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	@Override
	public void onStop() {
		super.onStop();
	}

	public void setItemInformation() {
		id.setText(String.valueOf(item.getId()), TextView.BufferType.NORMAL);
		companyId.setText(String.valueOf(item.getCompanyId()), TextView.BufferType.NORMAL);
		itemType.setText(String.valueOf(item.getItemType()), TextView.BufferType.NORMAL);
		name.setText(String.valueOf(item.getName()), TextView.BufferType.NORMAL);
		description.setText(String.valueOf(item.getDescription()), TextView.BufferType.NORMAL);
		quantity.setText(String.valueOf(item.getQuantity()), TextView.BufferType.NORMAL);

		int imageId = getResources().getIdentifier(item.getImageName(), "drawable", getActivity().getPackageName());
		Bitmap image = BitmapFactory.decodeResource(getResources(), imageId);
		itemImage.setImageBitmap(image);
		
		Log.d("INVENTORY", "id:"+id.getText().toString());
		Log.d("INVENTORY", "companyId:"+companyId.getText().toString());
		Log.d("INVENTORY", "itemType:"+itemType.getText().toString());
		Log.d("INVENTORY", "name:"+name.getText().toString());
		Log.d("INVENTORY", "description:"+description.getText().toString());
		Log.d("INVENTORY", "quantity:"+quantity.getText().toString());
		
	}

	public void setItem(InventoryItem item) {
		this.item = item;
	}

}