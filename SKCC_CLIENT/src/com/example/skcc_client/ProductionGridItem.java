package com.example.skcc_client;

import java.util.Date;

import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.ImageHelper;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.ProductionItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductionGridItem extends LinearLayout {

	public ProductionGridItem(Context context, ProductionItem item) {
		
		super(context);
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set state values
		// Set state color
		int stateColor = 0;
		
		if(item.getState() == Constants.code.ITEM_STATE_PRODUCING) {
			stateColor = context.getResources().getColor(R.color.item_producing);
		}
		else if(item.getState() == Constants.code.ITEM_STATE_FINISHED) {
			stateColor = context.getResources().getColor(R.color.item_finished);
		}
		else if(item.getState() == Constants.code.ITEM_STATE_ROTTEN) {
			stateColor = context.getResources().getColor(R.color.item_rotten);
		}

		// Calculate progress rate
		int progressRate = 0;
		
		if(item.getState() == Constants.code.ITEM_STATE_PRODUCING) {
			Date date = new Date();
			long completeTime = item.getEndTime().getTime() - date.getTime();
			long totalTime = item.getEndTime().getTime() - item.getStartTime().getTime();
			progressRate = (int) ((100 * completeTime) / totalTime);
		}
		else if(item.getState() == Constants.code.ITEM_STATE_FINISHED) {
			progressRate = Constants.code.ITEM_PROGRESS_FINISHED;
		}
		else if(item.getState() == Constants.code.ITEM_STATE_ROTTEN) {
			progressRate = Constants.code.ITEM_PROGRESS_ROTTEN;
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item image to ImageView
		ImageView icon;
		
		icon = new ImageView(context);
		icon.setLayoutParams(new GridView.LayoutParams(150, 150));
		icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		icon.setPadding(0, 0, 0, 0);
		
		int imageId = context.getResources().getIdentifier(item.getImageName(), "drawable", context.getPackageName());
		Bitmap iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
		iconImage = ImageHelper.getItemIcon(context, iconImage, 30, 15, item.getState(), progressRate);
		icon.setImageBitmap(iconImage);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item name to TextView
		TextView name = new TextView(context);
		name.setTypeface(name.getTypeface(), Typeface.BOLD);
		name.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_height));
		name.setText(item.getName());
		name.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_textSize));
		name.setTextColor(stateColor);
		name.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item state to TextView
		String stateText = "";
		if(item.getState() == Constants.code.ITEM_STATE_PRODUCING) stateText = context.getResources().getString(R.string.state_producing);
		else if(item.getState() == Constants.code.ITEM_STATE_FINISHED) stateText = context.getResources().getString(R.string.state_finished);
		else if(item.getState() == Constants.code.ITEM_STATE_ROTTEN) stateText = context.getResources().getString(R.string.state_rotten);
		
		TextView state = new TextView(context);
		state.setTypeface(name.getTypeface(), Typeface.BOLD);
		state.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_height));
		state.setText(stateText);
		state.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_textSize));
		state.setTextColor(stateColor);
		state.setGravity(Gravity.LEFT | Gravity.TOP);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set layout
		this.setOrientation(LinearLayout.VERTICAL);
		this.addView(name);
		this.addView(icon);
		this.addView(state);
	}
}
