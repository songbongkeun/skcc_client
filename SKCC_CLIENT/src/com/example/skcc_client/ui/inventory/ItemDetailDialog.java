package com.example.skcc_client.ui.inventory;

import java.util.List;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skcc_client.InventoryTab;
import com.example.skcc_client.R;
import com.example.skcc_client.common.CodeHelper;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;

public class ItemDetailDialog extends DialogFragment {

	private TextView name;
	private TextView quantity;
	private TextView itemType;
	private TextView description;
	private TextView ap;
	private TextView exp;
	private Button btn;
	
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
		
		View v = inflater.inflate(R.layout.popup_inventory_item_detail, container, false);

		name = (TextView) v.findViewById(R.id.inventoryPopupName);
		itemImage = (ImageView) v.findViewById(R.id.inventoryPopupitemImage);
		quantity = (TextView) v.findViewById(R.id.inventoryPopupQuantity);
		itemType = (TextView) v.findViewById(R.id.inventoryPopupType);
		description = (TextView) v.findViewById(R.id.inventoryPopupDescription);
		ap = (TextView) v.findViewById(R.id.inventoryPopupAP);
		exp = (TextView) v.findViewById(R.id.inventoryPopupExp);
		btn = (Button) v.findViewById(R.id.button_use);
		
		btn.setOnClickListener(mClickListener); // 사용 버튼 OnClick 이벤트 리스너 등록
		
		setItemInformation();
		
		// 사용할 수 없는 아이템이면 사용정보와 사용 버튼 보여주지 않음
		if(!Global.getInstance().productionRule.canUse(item.getId())) {
			
			v.findViewById(R.id.layout_use_text).setVisibility(View.GONE);
			v.findViewById(R.id.button_use).setVisibility(View.GONE);
		}
		
		Log.d("INVENTORY", "ItemDetailDialog Create");
		
		return v;
	}
	
	// OnClick Event Listener of Use Button
	Button.OnClickListener mClickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			
			// Use inventory item
			Global.getInstance().playing.useInventoryItem(item);
			
			// Refresh InventoryTab
			List<Fragment> tabs = getFragmentManager().getFragments();
			
			if(0 < tabs.size()) {
				InventoryTab tab = (InventoryTab) tabs.get(0);
				tab.refreshGrid();
			}
			
			// Close popup
			onStop();
		}
	};
	
	@Override
	public void onStart() {
		
		super.onStart();
	}
	@Override
	public void onStop() {
		
		super.onStop();
		super.dismiss();
	}

	public void setItemInformation() {
		
		int useAP = Global.getInstance().productionRule.getUseAP(item.getId());
		long useExp = Global.getInstance().productionRule.getUseExp(item.getId());
		
		itemType.setText(CodeHelper.getItemTypeName(item.getItemType()), TextView.BufferType.NORMAL);
		name.setText(String.valueOf(item.getName()), TextView.BufferType.NORMAL);
		description.setText(String.valueOf(item.getDescription()), TextView.BufferType.NORMAL);
		quantity.setText(String.valueOf(item.getQuantity()), TextView.BufferType.NORMAL);
		ap.setText(String.valueOf(useAP), TextView.BufferType.NORMAL);
		exp.setText(String.valueOf(useExp), TextView.BufferType.NORMAL);

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
		
		Log.d("INVENTORY", "itemType:"+itemType.getText().toString());
		Log.d("INVENTORY", "name:"+name.getText().toString());
		Log.d("INVENTORY", "description:"+description.getText().toString());
		Log.d("INVENTORY", "quantity:"+quantity.getText().toString());
		
	}

	public void setItem(InventoryItem item) {
		
		this.item = item;
	}

}