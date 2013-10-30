package com.example.skcc_client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.util.Log;
 
public class MainActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    
		findViewById(R.id.btn_inventory).setOnClickListener(mClickListener);
		findViewById(R.id.btn_production).setOnClickListener(mClickListener);
		findViewById(R.id.btn_quest).setOnClickListener(mClickListener);
		findViewById(R.id.btn_nfc).setOnClickListener(mClickListener);
	}
	
	Button.OnClickListener mClickListener = new View.OnClickListener() {
		
		public void onClick(View v) {
			
			View tabInventory = (View) findViewById(R.id.include_inventory);
			View tabProduction= (View) findViewById(R.id.include_production);
			View tabQuest = (View) findViewById(R.id.include_quest);
			View tabNfc = (View) findViewById(R.id.include_nfc);
			
			tabInventory.setVisibility(View.GONE);
			tabProduction.setVisibility(View.GONE);
			tabQuest.setVisibility(View.GONE);
			tabNfc.setVisibility(View.GONE);
			
			switch (v.getId()) {
			
				case R.id.btn_inventory:
					tabInventory.setVisibility(LinearLayout.VISIBLE);
					Log.i("DEBUG", "btn_inventory - Click");
					break;
				case R.id.btn_production:
					tabProduction.setVisibility(LinearLayout.VISIBLE);
					Log.i("DEBUG", "btn_production - Click");
					break;
				case R.id.btn_quest:
					tabQuest.setVisibility(LinearLayout.VISIBLE);
					Log.i("DEBUG", "btn_quest - Click");
					break;
				case R.id.btn_nfc:
					tabNfc.setVisibility(LinearLayout.VISIBLE);
					Log.i("DEBUG", "btn_nfc - Click");
					break;
			}
		}
	};
}