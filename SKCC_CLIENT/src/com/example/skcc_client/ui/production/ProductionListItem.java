package com.example.skcc_client.ui.production;

import com.example.skcc_client.R;
import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.common.ImageHelper;
import com.example.skcc_client.common.TextHelper;
import com.example.skcc_client.gameObject.Item;
import com.example.skcc_client.gameRule.ProductionRule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProductionListItem extends RelativeLayout {
	
	Item item;
	
	private ProductionListItem(Context context) {
		
		super(context);
	}

	public ProductionListItem(Context context, Item item) {
		
		super(context);
		
		this.item = item;

		///////////////////////////////////////////////////////////////////////////////////////
		// Set params
		ProductionRule rule = Global.getInstance().productionRule;
		int canProduce = rule.canProduce(item.getId());
		boolean hasEnoughItem = false;
		int itemState = (canProduce == Constants.rule.NEW_PRODUCTION_OK)
				? Constants.code.ITEM_STATE_PRODUCING
				: Constants.code.ITEM_STATE_ROTTEN;
		int textColor = (canProduce == Constants.rule.NEW_PRODUCTION_OK)
				? context.getResources().getColor(R.color.item_finished)
				: context.getResources().getColor(R.color.item_rotten);
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item name to TextView
		TextView name = new TextView(context);
		name.setTypeface(name.getTypeface(), Typeface.BOLD);
		name.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_height));
		name.setText(item.getName());
		name.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_textSize));
		name.setTextColor(textColor);
		name.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		name.setId(1);
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set limit level to TextView
		TextView limitLevel = new TextView(context);
		String limitLevelMsg = "";
		if(rule.hasEnoughLevel(item.getId()) == Constants.rule.NEW_PRODUCTION_NOT_ENOUGH_LEVEL) {
			limitLevelMsg = "레벨 " + rule.getLimitLevel(item.getId()) + " 이상만 생산할 수 있습니다!";
		}
		
		limitLevel.setTypeface(name.getTypeface(), Typeface.BOLD);
		limitLevel.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_height));
		limitLevel.setText(limitLevelMsg);
		limitLevel.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_textSize));
		limitLevel.setTextColor(context.getResources().getColor(R.color.item_rotten));
		limitLevel.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
		limitLevel.setId(2);
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item image to ImageView
		ImageView icon;
		
		icon = new ImageView(context);
		icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		icon.setPadding(5, 5, 5, 5);
		
		int imageId = context.getResources().getIdentifier(item.getImageName(), "drawable", context.getPackageName());
		Bitmap iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
		iconImage = ImageHelper.getItemIcon(context, iconImage, 30, 15, itemState, Constants.code.ITEM_PROGRESS_NOTHING);
		icon.setImageBitmap(iconImage);
		icon.setId(3);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost time to TextView
		long t = Global.getInstance().productionRule.getCostTime(item.getId());
		
		TextView costTime = new TextView(context);
		costTime.setTypeface(name.getTypeface(), Typeface.BOLD);
		costTime.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_height));
		costTime.setText(TextHelper.remainTime(t));
		costTime.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_textSize));
		costTime.setTextColor(textColor);
		costTime.setGravity(Gravity.LEFT | Gravity.TOP);
		costTime.setId(4);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost money to TextView
		long m = Global.getInstance().productionRule.getCostMoney(item.getId());
		
		boolean hasEnoughMoney = (rule.hasEnoughMoney(item.getId()) == Constants.rule.NEW_PRODUCTION_OK);
		textColor = hasEnoughMoney
				? context.getResources().getColor(R.color.item_finished)
				: context.getResources().getColor(R.color.item_rotten);
		
		TextView costMoney = new TextView(context);
		costMoney.setTypeface(name.getTypeface(), Typeface.BOLD);
		costMoney.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_height));
		costMoney.setText("￦" + m);
		costMoney.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_textSize));
		costMoney.setTextColor(textColor);
		costMoney.setGravity(Gravity.LEFT | Gravity.TOP);
		costMoney.setId(5);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost AP to TextView
		int ap = Global.getInstance().productionRule.getCostAP(item.getId());
		
		boolean hasEnoughAP = (rule.hasEnoughAP(item.getId()) == Constants.rule.NEW_PRODUCTION_OK);
		textColor = hasEnoughAP
				? context.getResources().getColor(R.color.item_finished)
				: context.getResources().getColor(R.color.item_rotten);
		
		TextView costAP = new TextView(context);
		costAP.setTypeface(name.getTypeface(), Typeface.BOLD);
		costAP.setHeight(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_height));
		costAP.setText(ap + " AP");
		costAP.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.inventoryItem_name_textSize));
		costAP.setTextColor(textColor);
		costAP.setGravity(Gravity.RIGHT | Gravity.TOP);
		costAP.setId(10);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost item1 to ImageView
		int costItemId1 = Global.getInstance().productionRule.getCostItem1(item.getId());
		ImageView costItem1 = new ImageView(context);
		
		if(costItemId1 != 0) {

			hasEnoughItem = (rule.hasEnoughItem(1, item.getId()) == Constants.rule.NEW_PRODUCTION_OK);
			itemState = hasEnoughItem ? Constants.code.ITEM_STATE_PRODUCING : Constants.code.ITEM_STATE_ROTTEN;
		
			costItem1.setScaleType(ImageView.ScaleType.CENTER_CROP);
			costItem1.setPadding(15, 5, 10, 5);
			
			Item costItem = Global.getInstance().itemList.get(costItemId1);
			Log.d("PRODUCTION", "ITEM IMAGE NAME = " + costItem.getImageName());
			imageId = context.getResources().getIdentifier(costItem.getImageName(), "drawable", context.getPackageName());
			iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
			iconImage = ImageHelper.getItemIcon(context, iconImage, 30, 15, itemState, Constants.code.ITEM_PROGRESS_NOTHING);
			
			costItem1.setImageBitmap(iconImage);
			costItem1.setId(6);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost item2 to ImageView
		int costItemId2 = Global.getInstance().productionRule.getCostItem2(item.getId());
		ImageView costItem2 = new ImageView(context);
		
		if(costItemId2 != 0) {

			hasEnoughItem = (rule.hasEnoughItem(2, item.getId()) == Constants.rule.NEW_PRODUCTION_OK);
			itemState = hasEnoughItem ? Constants.code.ITEM_STATE_PRODUCING : Constants.code.ITEM_STATE_ROTTEN;
			
			costItem2.setScaleType(ImageView.ScaleType.CENTER_CROP);
			costItem2.setPadding(15, 5, 10, 5);
			
			Item costItem = Global.getInstance().itemList.get(costItemId2);
			Log.d("PRODUCTION", "ITEM IMAGE NAME = " + costItem.getImageName());
			imageId = context.getResources().getIdentifier(costItem.getImageName(), "drawable", context.getPackageName());
			iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
			iconImage = ImageHelper.getItemIcon(context, iconImage, 30, 15, itemState, Constants.code.ITEM_PROGRESS_NOTHING);
			
			costItem2.setImageBitmap(iconImage);
			costItem2.setId(7);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost item3 to ImageView
		int costItemId3 = Global.getInstance().productionRule.getCostItem3(item.getId());
		ImageView costItem3 = new ImageView(context);
		
		if(costItemId3 != 0) {

			hasEnoughItem = (rule.hasEnoughItem(3, item.getId()) == Constants.rule.NEW_PRODUCTION_OK);;
			itemState = hasEnoughItem ? Constants.code.ITEM_STATE_PRODUCING : Constants.code.ITEM_STATE_ROTTEN;
		
			costItem3.setScaleType(ImageView.ScaleType.CENTER_CROP);
			costItem3.setPadding(15, 5, 10, 5);
			
			Item costItem = Global.getInstance().itemList.get(costItemId3);
			Log.d("PRODUCTION", "ITEM IMAGE NAME = " + costItem.getImageName());
			imageId = context.getResources().getIdentifier(costItem.getImageName(), "drawable", context.getPackageName());
			iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
			iconImage = ImageHelper.getItemIcon(context, iconImage, 30, 15, itemState, Constants.code.ITEM_PROGRESS_NOTHING);
			
			costItem3.setImageBitmap(iconImage);
			costItem3.setId(8);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost item4 to ImageView
		int costItemId4 = Global.getInstance().productionRule.getCostItem4(item.getId());
		ImageView costItem4 = new ImageView(context);
		
		if(costItemId4 != 0) {

			hasEnoughItem = (rule.hasEnoughItem(4, item.getId()) == Constants.rule.NEW_PRODUCTION_OK);;
			itemState = hasEnoughItem ? Constants.code.ITEM_STATE_PRODUCING : Constants.code.ITEM_STATE_ROTTEN;
		
			costItem4.setScaleType(ImageView.ScaleType.CENTER_CROP);
			costItem4.setPadding(15, 5, 10, 5);
			
			Item costItem = Global.getInstance().itemList.get(costItemId4);
			Log.d("PRODUCTION", "ITEM IMAGE NAME = " + costItem.getImageName());
			imageId = context.getResources().getIdentifier(costItem.getImageName(), "drawable", context.getPackageName());
			iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
			iconImage = ImageHelper.getItemIcon(context, iconImage, 30, 15, itemState, Constants.code.ITEM_PROGRESS_NOTHING);
			
			costItem4.setImageBitmap(iconImage);
			costItem4.setId(9);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set layout
		// name
		RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams(150, 50);
		name.setLayoutParams(nameParams);
		this.addView(name);
		
		// limit level
		RelativeLayout.LayoutParams limitLevelParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 50);
		limitLevelParams.addRule(RelativeLayout.RIGHT_OF, 1);
		limitLevel.setLayoutParams(limitLevelParams);
		this.addView(limitLevel);
		
		// icon
		RelativeLayout.LayoutParams iconParams = new RelativeLayout.LayoutParams(150, 150);
		iconParams.addRule(RelativeLayout.BELOW, 1);
		icon.setLayoutParams(iconParams);
		this.addView(icon);
		
		// cost time
		RelativeLayout.LayoutParams costTimeParams = new RelativeLayout.LayoutParams(150, 50);
		costTimeParams.addRule(RelativeLayout.BELOW, 2);
		costTimeParams.addRule(RelativeLayout.RIGHT_OF, 3);
		costTime.setLayoutParams(costTimeParams);
		this.addView(costTime);
		
		// cost money
		RelativeLayout.LayoutParams costMoneyParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 50);
		costMoneyParams.addRule(RelativeLayout.BELOW, 2);
		costMoneyParams.addRule(RelativeLayout.RIGHT_OF, 4);
		costMoney.setLayoutParams(costMoneyParams);
		this.addView(costMoney);
		
		// cost money
		RelativeLayout.LayoutParams costAPParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 50);
		costAPParams.addRule(RelativeLayout.BELOW, 2);
		costAPParams.addRule(RelativeLayout.RIGHT_OF, 5);
		costAP.setLayoutParams(costAPParams);
		this.addView(costAP);

		// cost item 1
		if(costItemId1 != 0) {
			RelativeLayout.LayoutParams costItem1Params = new RelativeLayout.LayoutParams(100, 100);
			costItem1Params.addRule(RelativeLayout.RIGHT_OF, 3);
			costItem1Params.addRule(RelativeLayout.BELOW, 4);
			costItem1Params.addRule(RelativeLayout.BELOW, 5);
			costItem1Params.addRule(RelativeLayout.BELOW, 10);
			costItem1.setLayoutParams(costItem1Params);
			this.addView(costItem1);
		}

		// cost item 2
		if(costItemId2 != 0) {
			RelativeLayout.LayoutParams costItem2Params = new RelativeLayout.LayoutParams(100, 100);
			costItem2Params.addRule(RelativeLayout.RIGHT_OF, 3);
			costItem2Params.addRule(RelativeLayout.BELOW, 4);
			costItem2Params.addRule(RelativeLayout.BELOW, 5);
			costItem2Params.addRule(RelativeLayout.BELOW, 10);
			costItem2Params.addRule(RelativeLayout.RIGHT_OF, 6);
			costItem2.setLayoutParams(costItem2Params);
			this.addView(costItem2);
		}

		// cost item 3
		if(costItemId3 != 0) {
			RelativeLayout.LayoutParams costItem3Params = new RelativeLayout.LayoutParams(100, 100);
			costItem3Params.addRule(RelativeLayout.RIGHT_OF, 3);
			costItem3Params.addRule(RelativeLayout.BELOW, 4);
			costItem3Params.addRule(RelativeLayout.BELOW, 5);
			costItem3Params.addRule(RelativeLayout.BELOW, 10);
			costItem3Params.addRule(RelativeLayout.RIGHT_OF, 7);
			costItem3.setLayoutParams(costItem3Params);
			this.addView(costItem3);
		}

		// cost item 4
		if(costItemId4 != 0) {
			RelativeLayout.LayoutParams costItem4Params = new RelativeLayout.LayoutParams(100, 100);
			costItem4Params.addRule(RelativeLayout.RIGHT_OF, 3);
			costItem4Params.addRule(RelativeLayout.BELOW, 4);
			costItem4Params.addRule(RelativeLayout.BELOW, 5);
			costItem4Params.addRule(RelativeLayout.BELOW, 10);
			costItem4Params.addRule(RelativeLayout.RIGHT_OF, 8);
			costItem4.setLayoutParams(costItem4Params);
			this.addView(costItem4);
		}
	}
}
