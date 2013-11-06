package com.example.skcc_client.ui.production;

import java.sql.Timestamp;

import com.example.skcc_client.R;
import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.common.ImageHelper;
import com.example.skcc_client.common.TextHelper;
import com.example.skcc_client.gameObject.Item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProductionListItem extends RelativeLayout {
	
	private ProductionListItem(Context context) {
		
		super(context);
	}

	public ProductionListItem(Context context, Item item) {
		
		super(context);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item name to TextView
		TextView name = new TextView(context);
		name.setTypeface(name.getTypeface(), Typeface.BOLD);
		name.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_height));
		name.setText(item.getName());
		name.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_textSize));
		name.setTextColor(context.getResources().getColor(R.color.item_finished));
		name.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		name.setId(1);
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item image to ImageView
		ImageView icon;
		
		icon = new ImageView(context);
		icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		icon.setPadding(5, 5, 5, 5);
		
		int imageId = context.getResources().getIdentifier(item.getImageName(), "drawable", context.getPackageName());
		Bitmap iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
		iconImage = ImageHelper.getItemIcon(context, iconImage, 30, 15, Constants.code.ITEM_STATE_FINISHED, 0);
		icon.setImageBitmap(iconImage);
		icon.setId(2);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost time to TextView
		long t = Global.getInstance().productionRule.getCostTime(item.getId());
		
		TextView costTime = new TextView(context);
		costTime.setTypeface(name.getTypeface(), Typeface.BOLD);
		costTime.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_height));
		costTime.setText(TextHelper.remainTime(t));
		costTime.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_textSize));
		costTime.setTextColor(context.getResources().getColor(R.color.item_finished));
		costTime.setGravity(Gravity.LEFT | Gravity.TOP);
		costTime.setId(3);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost money to TextView
		long m = Global.getInstance().productionRule.getCostMoney(item.getId());
		
		TextView costMoney = new TextView(context);
		costMoney.setTypeface(name.getTypeface(), Typeface.BOLD);
		costMoney.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_height));
		costMoney.setText("£Ü" + m);
		costMoney.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_textSize));
		costMoney.setTextColor(context.getResources().getColor(R.color.item_finished));
		costMoney.setGravity(Gravity.LEFT | Gravity.TOP);
		costMoney.setId(4);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost time to ImageView
		int costItemId1 = Global.getInstance().productionRule.getCostItem1(item.getId());
		ImageView costItem1 = new ImageView(context);
		
		if(costItemId1 != 0) {
		
			costItem1.setScaleType(ImageView.ScaleType.CENTER_CROP);
			costItem1.setPadding(15, 5, 5, 5);
			
			Item costItem = Global.getInstance().itemList.get(costItemId1);
			imageId = context.getResources().getIdentifier(costItem.getImageName(), "drawable", context.getPackageName());
			iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
			iconImage = ImageHelper.getItemIcon(context, iconImage, 30, 15, Constants.code.ITEM_STATE_FINISHED, 0);
			
			costItem1.setImageBitmap(iconImage);
			costItem1.setId(5);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost time to ImageView
		int costItemId2 = Global.getInstance().productionRule.getCostItem2(item.getId());
		ImageView costItem2 = new ImageView(context);
		
		if(costItemId2 != 0) {
		
			costItem2.setScaleType(ImageView.ScaleType.CENTER_CROP);
			costItem2.setPadding(15, 5, 5, 5);
			
			Item costItem = Global.getInstance().itemList.get(costItemId2);
			imageId = context.getResources().getIdentifier(costItem.getImageName(), "drawable", context.getPackageName());
			iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
			iconImage = ImageHelper.getItemIcon(context, iconImage, 30, 15, Constants.code.ITEM_STATE_FINISHED, 0);
			
			costItem2.setImageBitmap(iconImage);
			costItem2.setId(6);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set layout
		RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 50);
		name.setLayoutParams(nameParams);
		this.addView(name);
		
		RelativeLayout.LayoutParams iconParams = new RelativeLayout.LayoutParams(150, 150);
		iconParams.addRule(RelativeLayout.BELOW, 1);
		icon.setLayoutParams(iconParams);
		this.addView(icon);
		
		RelativeLayout.LayoutParams costTimeParams = new RelativeLayout.LayoutParams(150, 50);
		costTimeParams.addRule(RelativeLayout.BELOW, 1);
		costTimeParams.addRule(RelativeLayout.RIGHT_OF, 2);
		costTime.setLayoutParams(costTimeParams);
		this.addView(costTime);
		
		RelativeLayout.LayoutParams costMoneyParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 50);
		costMoneyParams.addRule(RelativeLayout.BELOW, 1);
		costMoneyParams.addRule(RelativeLayout.RIGHT_OF, 3);
		costMoney.setLayoutParams(costMoneyParams);
		this.addView(costMoney);

		
		if(costItemId1 != 0) {
			RelativeLayout.LayoutParams costItem1Params = new RelativeLayout.LayoutParams(100, 100);
			costItem1Params.addRule(RelativeLayout.RIGHT_OF, 2);
			costItem1Params.addRule(RelativeLayout.BELOW, 3);
			costItem1Params.addRule(RelativeLayout.BELOW, 4);
			costItem1.setLayoutParams(costItem1Params);
			this.addView(costItem1);
		}

		
		if(costItemId2 != 0) {
			RelativeLayout.LayoutParams costItem2Params = new RelativeLayout.LayoutParams(100, 100);
			costItem2Params.addRule(RelativeLayout.RIGHT_OF, 2);
			costItem2Params.addRule(RelativeLayout.BELOW, 3);
			costItem2Params.addRule(RelativeLayout.BELOW, 4);
			costItem2Params.addRule(RelativeLayout.RIGHT_OF, 5);
			costItem2.setLayoutParams(costItem2Params);
			this.addView(costItem2);
		}
	}
}
