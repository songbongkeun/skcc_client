package com.example.skcc_client;

import java.util.Date;

import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.ImageHelper;
import com.example.skcc_client.gameObject.ProductionItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductionGridItem extends LinearLayout {
	
	protected ProductionGridItem(Context context) {
		
		super(context);
	}
	
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
		else if(item.getState() == Constants.code.ITEM_STATE_NOTHING) {
			stateColor = context.getResources().getColor(R.color.item_nothing);
		}

		// Calculate progress rate
		int progressRate = 0;
		
		if(item.getState() == Constants.code.ITEM_STATE_PRODUCING) {
			Date date = new Date();
			long completeTime = date.getTime() - item.getStartTime().getTime();
			long totalTime = item.getEndTime().getTime() - item.getStartTime().getTime();
			progressRate = (int) ((100 * completeTime) / totalTime);
		}
		else if(item.getState() == Constants.code.ITEM_STATE_FINISHED) {
			Date date = new Date();
			long finishTime = item.getEndTime().getTime() - date.getTime();
			long expireTime = item.getExpireTime().getTime() - item.getEndTime().getTime();
			progressRate = (int) ((100 * finishTime) / expireTime);
		}
		else if(item.getState() == Constants.code.ITEM_STATE_ROTTEN) {
			progressRate = Constants.code.ITEM_PROGRESS_ROTTEN;
		}
		else if(item.getState() == Constants.code.ITEM_STATE_NOTHING) {
			progressRate = Constants.code.ITEM_PROGRESS_NOTHING;
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
		iconImage = ImageHelper.getProductionItemIcon(context, iconImage, 30, 15, item.getState(), progressRate);
		icon.setImageBitmap(iconImage);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item name to TextView
		TextView name = new TextView(context);
		name.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventory_item_name_height));
		
		if(0 != item.getId()) {
			
			name.setTypeface(name.getTypeface(), Typeface.BOLD);
			name.setText(item.getName());
			name.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventory_item_name_textSize));
			name.setTextColor(stateColor);
			name.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set remain time to TextView
		TextView remains = new TextView(context);
		remains.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventory_item_name_height));
		
		if(0 != item.getId()) {
			
			remains.setTypeface(remains.getTypeface(), Typeface.BOLD);
			remains.setText(item.getRemainTime());
			remains.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventory_item_name_textSize));
			remains.setTextColor(stateColor);
			remains.setGravity(Gravity.LEFT | Gravity.TOP);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set layout
		this.setOrientation(LinearLayout.VERTICAL);
		this.addView(name);
		this.addView(icon);
		this.addView(remains);
	}
	
}
