package com.example.skcc_client;

import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.ImageHelper;
import com.example.skcc_client.gameObject.InventoryItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InventoryGridItem extends LinearLayout {
	
	private InventoryGridItem(Context context) {
		
		super(context);
	}

	public InventoryGridItem(Context context, InventoryItem item) {
		
		super(context);
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item image to ImageView
		ImageView icon;
		
		icon = new ImageView(context);
		icon.setLayoutParams(new GridView.LayoutParams(150, 150));
		icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		icon.setPadding(0, 0, 0, 0);
		
		int imageId = context.getResources().getIdentifier(item.getImageName(), "drawable", context.getPackageName());
		Bitmap iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
		iconImage = ImageHelper.getProductionItemIcon(context, iconImage, 30, 15, Constants.code.ITEM_STATE_FINISHED, 0);
		icon.setImageBitmap(iconImage);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item name to TextView
		TextView name = new TextView(context);
		name.setTypeface(name.getTypeface(), Typeface.BOLD);
		name.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventory_item_name_height));
		name.setText(item.getName());
		name.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventory_item_name_textSize));
		name.setTextColor(context.getResources().getColor(R.color.item_finished));
		name.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item qunatity to TextView
		TextView qunatity = new TextView(context);
		qunatity.setTypeface(name.getTypeface(), Typeface.BOLD);
		qunatity.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventory_item_name_height));
		qunatity.setText("" + item.getQuantity());
		qunatity.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventory_item_name_textSize));
		qunatity.setTextColor(context.getResources().getColor(R.color.item_finished));
		qunatity.setGravity(Gravity.RIGHT | Gravity.TOP);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set layout
		this.setOrientation(LinearLayout.VERTICAL);
		this.addView(name);
		this.addView(icon);
		this.addView(qunatity);
	}
}
