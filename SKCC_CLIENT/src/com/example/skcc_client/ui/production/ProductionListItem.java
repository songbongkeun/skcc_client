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
	
	static private int ID_ITEM_ICON = 1;
	static private int ID_ITEM_NAME = 2;
	static private int ID_COST_TIME_ICON = 33;
	static private int ID_COST_TIME = 3;
	static private int ID_COST_MONEY_ICON = 44;
	static private int ID_COST_MONEY = 4;
	static private int ID_COST_AP_ICON = 55;
	static private int ID_COST_AP = 5;
	static private int ID_COST_ITEM1 = 6;
	static private int ID_COST_ITEM2 = 7;
	static private int ID_COST_ITEM3 = 8;
	static private int ID_COST_ITEM4 = 9;
	
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
		boolean newProductinoOk = (canProduce == Constants.rule.NEW_PRODUCTION_OK);
		boolean hasEnoughItem = false;
		int itemState = newProductinoOk
				? Constants.code.NEW_PRODUCTION_STATE_OK
				: Constants.code.NEW_PRODUCTION_STATE_NOT_OK;
		int textColor = newProductinoOk
				? context.getResources().getColor(R.color.new_production_ok_color)
				: context.getResources().getColor(R.color.new_production_not_ok_color);
				
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item image to ImageView
		ImageView icon;
		
		icon = new ImageView(context);
		icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		icon.setPadding(5, 5, 5, 5);
		
		String itemImageName = newProductinoOk ? item.getImageName() : item.getImageName() + "x";
		int imageId = context.getResources().getIdentifier(itemImageName, "drawable", context.getPackageName());
		Bitmap iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
		iconImage = ImageHelper.getProductionItemIcon(context, iconImage, 30, 15, itemState, Constants.code.ITEM_PROGRESS_NOTHING);
		icon.setImageBitmap(iconImage);
		icon.setId(ID_ITEM_ICON);
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set item name to TextView
		TextView name = new TextView(context);
		name.setTypeface(name.getTypeface(), Typeface.BOLD);
		name.setHeight(context.getResources().getDimensionPixelSize(R.dimen.new_production_popup_text_height));
		name.setText(item.getName());
		name.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.new_production_popup_text_size_S));
		name.setTextColor(textColor);
		name.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		name.setId(ID_ITEM_NAME);


		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost time icon to ImageView
		ImageView costTimeIcon;
		
		costTimeIcon = new ImageView(context);
		costTimeIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		costTimeIcon.setPadding(3, 3, 3, 3);
		costTimeIcon.setImageResource(R.drawable.icon_clock);
		costTimeIcon.setId(ID_COST_TIME_ICON);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost time to TextView
		long t = Global.getInstance().productionRule.getCostTime(item.getId());
		
		TextView costTime = new TextView(context);
		costTime.setTypeface(name.getTypeface(), Typeface.BOLD);
		costTime.setHeight(context.getResources().getDimensionPixelSize(R.dimen.new_production_popup_text_height));
		costTime.setText(TextHelper.remainTime(t));
		costTime.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.new_production_popup_text_size_S));
		costTime.setTextColor(textColor);
		costTime.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		costTime.setId(ID_COST_TIME);


		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost money icon to ImageView
		ImageView costMoneyIcon;
		
		costMoneyIcon = new ImageView(context);
		costMoneyIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		costMoneyIcon.setPadding(8, 8, 8, 8);
		costMoneyIcon.setImageResource(R.drawable.icon_money);
		costMoneyIcon.setId(ID_COST_MONEY_ICON);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost money to TextView
		long m = Global.getInstance().productionRule.getCostMoney(item.getId());
		
		boolean hasEnoughMoney = (rule.hasEnoughMoney(item.getId()) == Constants.rule.NEW_PRODUCTION_OK);
		textColor = hasEnoughMoney
				? context.getResources().getColor(R.color.new_production_ok_color)
				: context.getResources().getColor(R.color.new_production_not_enough_color);
		
		TextView costMoney = new TextView(context);
		costMoney.setTypeface(name.getTypeface(), Typeface.BOLD);
		costMoney.setHeight(context.getResources().getDimensionPixelSize(R.dimen.new_production_popup_text_height));
		costMoney.setText(Long.toString(m));
		costMoney.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.new_production_popup_text_size_S));
		costMoney.setTextColor(textColor);
		costMoney.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		costMoney.setId(ID_COST_MONEY);


		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost AP icon to ImageView
		ImageView costAPIcon;
		
		costAPIcon = new ImageView(context);
		costAPIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
		costAPIcon.setPadding(8, 8, 8, 8);
		costAPIcon.setImageResource(R.drawable.icon_ap_inverse);
		costAPIcon.setId(ID_COST_AP_ICON);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost AP to TextView
		int ap = Global.getInstance().productionRule.getCostAP(item.getId());
		
		boolean hasEnoughAP = (rule.hasEnoughAP(item.getId()) == Constants.rule.NEW_PRODUCTION_OK);
		textColor = hasEnoughAP
				? context.getResources().getColor(R.color.new_production_ok_color)
				: context.getResources().getColor(R.color.new_production_not_enough_color);
		
		TextView costAP = new TextView(context);
		costAP.setTypeface(name.getTypeface(), Typeface.BOLD);
		costAP.setHeight(context.getResources().getDimensionPixelSize(R.dimen.new_production_popup_text_height));
		costAP.setText(Integer.toString(ap));
		costAP.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.new_production_popup_text_size_S));
		costAP.setTextColor(textColor);
		costAP.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		costAP.setId(ID_COST_AP);
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost item1 to ImageView
		int costItemId1 = Global.getInstance().productionRule.getCostItem1(item.getId());
		ImageView costItem1 = new ImageView(context);
		
		if(costItemId1 != 0) {

			hasEnoughItem = (rule.hasEnoughItem(1, item.getId()) == Constants.rule.NEW_PRODUCTION_OK);
			itemState = hasEnoughItem ? Constants.code.ITEM_STATE_PRODUCING : Constants.code.ITEM_STATE_ROTTEN;
		
			costItem1.setScaleType(ImageView.ScaleType.CENTER_CROP);
			costItem1.setPadding(5, 5, 5, 5);
			
			Item costItem = Global.getInstance().itemList.get(costItemId1);
			Log.d("PRODUCTION", "ITEM IMAGE NAME = " + costItem.getImageName());
			imageId = context.getResources().getIdentifier(costItem.getImageName(), "drawable", context.getPackageName());
			iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
			iconImage = ImageHelper.getProductionItemIcon(context, iconImage, 30, 15, itemState, Constants.code.ITEM_PROGRESS_NOTHING);
			
			costItem1.setImageBitmap(iconImage);
			costItem1.setId(ID_COST_ITEM1);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost item2 to ImageView
		int costItemId2 = Global.getInstance().productionRule.getCostItem2(item.getId());
		ImageView costItem2 = new ImageView(context);
		
		if(costItemId2 != 0) {

			hasEnoughItem = (rule.hasEnoughItem(2, item.getId()) == Constants.rule.NEW_PRODUCTION_OK);
			itemState = hasEnoughItem ? Constants.code.ITEM_STATE_PRODUCING : Constants.code.ITEM_STATE_ROTTEN;
			
			costItem2.setScaleType(ImageView.ScaleType.CENTER_CROP);
			costItem2.setPadding(5, 5, 5, 5);
			
			Item costItem = Global.getInstance().itemList.get(costItemId2);
			Log.d("PRODUCTION", "ITEM IMAGE NAME = " + costItem.getImageName());
			imageId = context.getResources().getIdentifier(costItem.getImageName(), "drawable", context.getPackageName());
			iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
			iconImage = ImageHelper.getProductionItemIcon(context, iconImage, 30, 15, itemState, Constants.code.ITEM_PROGRESS_NOTHING);
			
			costItem2.setImageBitmap(iconImage);
			costItem2.setId(ID_COST_ITEM2);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost item3 to ImageView
		int costItemId3 = Global.getInstance().productionRule.getCostItem3(item.getId());
		ImageView costItem3 = new ImageView(context);
		
		if(costItemId3 != 0) {

			hasEnoughItem = (rule.hasEnoughItem(3, item.getId()) == Constants.rule.NEW_PRODUCTION_OK);;
			itemState = hasEnoughItem ? Constants.code.ITEM_STATE_PRODUCING : Constants.code.ITEM_STATE_ROTTEN;
		
			costItem3.setScaleType(ImageView.ScaleType.CENTER_CROP);
			costItem3.setPadding(5, 5, 5, 5);
			
			Item costItem = Global.getInstance().itemList.get(costItemId3);
			Log.d("PRODUCTION", "ITEM IMAGE NAME = " + costItem.getImageName());
			imageId = context.getResources().getIdentifier(costItem.getImageName(), "drawable", context.getPackageName());
			iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
			iconImage = ImageHelper.getProductionItemIcon(context, iconImage, 30, 15, itemState, Constants.code.ITEM_PROGRESS_NOTHING);
			
			costItem3.setImageBitmap(iconImage);
			costItem3.setId(ID_COST_ITEM3);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set cost item4 to ImageView
		int costItemId4 = Global.getInstance().productionRule.getCostItem4(item.getId());
		ImageView costItem4 = new ImageView(context);
		
		if(costItemId4 != 0) {

			hasEnoughItem = (rule.hasEnoughItem(4, item.getId()) == Constants.rule.NEW_PRODUCTION_OK);;
			itemState = hasEnoughItem ? Constants.code.ITEM_STATE_PRODUCING : Constants.code.ITEM_STATE_ROTTEN;
		
			costItem4.setScaleType(ImageView.ScaleType.CENTER_CROP);
			costItem4.setPadding(5, 5, 5, 5);
			
			Item costItem = Global.getInstance().itemList.get(costItemId4);
			Log.d("PRODUCTION", "ITEM IMAGE NAME = " + costItem.getImageName());
			imageId = context.getResources().getIdentifier(costItem.getImageName(), "drawable", context.getPackageName());
			iconImage = BitmapFactory.decodeResource(context.getResources(), imageId);
			iconImage = ImageHelper.getProductionItemIcon(context, iconImage, 30, 15, itemState, Constants.code.ITEM_PROGRESS_NOTHING);
			
			costItem4.setImageBitmap(iconImage);
			costItem4.setId(ID_COST_ITEM4);
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////////
		// Set layout
		
		// icon
		RelativeLayout.LayoutParams iconParams = new RelativeLayout.LayoutParams(200, 200);
		icon.setLayoutParams(iconParams);
		this.addView(icon);
		
		// name
		RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams(200, 50);
		nameParams.addRule(RelativeLayout.RIGHT_OF, ID_ITEM_ICON);
		name.setLayoutParams(nameParams);
		this.addView(name);
		
		// cost time icon
		RelativeLayout.LayoutParams costTimeIconParams = new RelativeLayout.LayoutParams(50, 50);
		costTimeIconParams.addRule(RelativeLayout.RIGHT_OF, ID_ITEM_ICON);
		costTimeIconParams.addRule(RelativeLayout.BELOW, ID_ITEM_NAME);
		costTimeIcon.setLayoutParams(costTimeIconParams);
		this.addView(costTimeIcon);
		
		// cost time
		RelativeLayout.LayoutParams costTimeParams = new RelativeLayout.LayoutParams(150, 50);
		costTimeParams.addRule(RelativeLayout.RIGHT_OF, ID_COST_TIME_ICON);
		costTimeParams.addRule(RelativeLayout.BELOW, ID_ITEM_NAME);
		costTime.setLayoutParams(costTimeParams);
		this.addView(costTime);
		
		// cost money icon
		RelativeLayout.LayoutParams costMoneyIconParams = new RelativeLayout.LayoutParams(50, 50);
		costMoneyIconParams.addRule(RelativeLayout.RIGHT_OF, ID_ITEM_ICON);
		costMoneyIconParams.addRule(RelativeLayout.BELOW, ID_COST_TIME_ICON);
		costMoneyIcon.setLayoutParams(costMoneyIconParams);
		this.addView(costMoneyIcon);
		
		// cost money
		RelativeLayout.LayoutParams costMoneyParams = new RelativeLayout.LayoutParams(150, 50);
		costMoneyParams.addRule(RelativeLayout.RIGHT_OF, ID_COST_MONEY_ICON);
		costMoneyParams.addRule(RelativeLayout.BELOW, ID_COST_TIME);
		costMoney.setLayoutParams(costMoneyParams);
		this.addView(costMoney);
		
		// cost AP icon
		RelativeLayout.LayoutParams costAPIconParams = new RelativeLayout.LayoutParams(50, 50);
		costAPIconParams.addRule(RelativeLayout.RIGHT_OF, ID_ITEM_ICON);
		costAPIconParams.addRule(RelativeLayout.BELOW, ID_COST_MONEY_ICON);
		costAPIcon.setLayoutParams(costAPIconParams);
		this.addView(costAPIcon);
		
		// cost AP
		RelativeLayout.LayoutParams costAPParams = new RelativeLayout.LayoutParams(150, 50);
		costAPParams.addRule(RelativeLayout.RIGHT_OF, ID_COST_AP_ICON);
		costAPParams.addRule(RelativeLayout.BELOW, ID_COST_MONEY);
		costAP.setLayoutParams(costAPParams);
		this.addView(costAP);

		// cost item 1
		if(costItemId1 != 0) {
			RelativeLayout.LayoutParams costItem1Params = new RelativeLayout.LayoutParams(90, 90);
			costItem1Params.addRule(RelativeLayout.RIGHT_OF, ID_ITEM_NAME);
			costItem1Params.addRule(RelativeLayout.RIGHT_OF, ID_COST_TIME);
			costItem1Params.addRule(RelativeLayout.RIGHT_OF, ID_COST_MONEY);
			costItem1Params.addRule(RelativeLayout.RIGHT_OF, ID_COST_AP);
			costItem1.setLayoutParams(costItem1Params);
			this.addView(costItem1);
		}

		// cost item 2
		if(costItemId2 != 0) {
			RelativeLayout.LayoutParams costItem2Params = new RelativeLayout.LayoutParams(90, 90);
			costItem2Params.addRule(RelativeLayout.RIGHT_OF, ID_COST_ITEM1);
			costItem2.setLayoutParams(costItem2Params);
			this.addView(costItem2);
		}

		// cost item 3
		if(costItemId3 != 0) {
			RelativeLayout.LayoutParams costItem3Params = new RelativeLayout.LayoutParams(90, 90);
			costItem3Params.addRule(RelativeLayout.RIGHT_OF, ID_ITEM_NAME);
			costItem3Params.addRule(RelativeLayout.RIGHT_OF, ID_COST_TIME);
			costItem3Params.addRule(RelativeLayout.RIGHT_OF, ID_COST_MONEY);
			costItem3Params.addRule(RelativeLayout.RIGHT_OF, ID_COST_AP);
			costItem3Params.addRule(RelativeLayout.BELOW, ID_COST_ITEM1);
			costItem3.setLayoutParams(costItem3Params);
			this.addView(costItem3);
		}

		// cost item 4
		if(costItemId4 != 0) {
			RelativeLayout.LayoutParams costItem4Params = new RelativeLayout.LayoutParams(90, 90);
			costItem4Params.addRule(RelativeLayout.RIGHT_OF, ID_COST_ITEM3);
			costItem4Params.addRule(RelativeLayout.BELOW, ID_COST_ITEM2);
			costItem4.setLayoutParams(costItem4Params);
			this.addView(costItem4);
		}
	}
}
