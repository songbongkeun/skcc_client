package com.example.preprocess;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import com.example.dbio.*;
import com.example.skcc_client.common.Constants;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.Item;
import com.example.skcc_client.gameObject.Player;
import com.example.skcc_client.gameObject.ProductionItem;
import com.example.skcc_client.gameObject.GamePlaying;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

public class LoadingTask extends AsyncTask<String, Integer, Integer> {

	private static final String TAG = "LOADING";
	
	private final Context mAppContext;

	// This is the progress bar you want to update while the task is in progress
	private final ProgressBar progressBar;
	
	// This is the listener that will be told when this task is finished
	private final LoadingTaskFinishedListener finishedListener;

	/**
	 * A Loading task that will load some resources that are necessary for the app to start
	 * @param progressBar - the progress bar you want to update while the task is in progress
	 * @param finishedListener - the listener that will be told when this task is finished
	 */
	public LoadingTask(ProgressBar progressBar, LoadingTaskFinishedListener finishedListener, final Context appContext) {
		
		this.progressBar = progressBar;
		this.finishedListener = finishedListener;
		this.mAppContext = appContext;
	}
	
	public interface LoadingTaskFinishedListener {
		
		void onTaskFinished(); // If you want to pass something back to the listener add a param to this method
	}

	@Override
	protected Integer doInBackground(String... params) {
		
		Log.d(TAG, "Starting task with url: " + params[0]);
		
		if(resourcesDontAlreadyExist()) {
			
			downloadResources();
		}
		
		// Perhaps you want to return something to your post execute
		return 1234;
	}

	private boolean resourcesDontAlreadyExist() {
		// Here you would query your app's internal state to see if this download had been performed before
		// Perhaps once checked save this in a shared preference for speed of access next time
		return true; // returning true so we show the splash every time
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		
		Log.d(TAG, "onProgressUpdate");
		
		super.onProgressUpdate(values);
		
		progressBar.setProgress(values[0]); // This is ran on the UI thread so it is ok to update our progress bar ( a UI view ) here

	}

	@Override
	protected void onPostExecute(Integer result) {
		
		Log.d(TAG, "onPostExecute");
		
		super.onPostExecute(result);
		
		finishedListener.onTaskFinished(); // Tell whoever was listening we have finished
	}
	
	private void downloadResources() {
		
		Log.d(TAG, "downloadResources");
		
		
		///////////////////////////////////////////////////////////////////////////////////////////
		// Load rules
		///////////////////////////////////////////////////////////////////////////////////////////

		// Rules
		Global.getInstance().levelRule.generateTestRule(); // Level
		Global.getInstance().productionRule.generateTestRule(); // Production
		Global.getInstance().playing = new GamePlaying(mAppContext); // Production
		
		// Update progress bar
		updateProgressBar(1);

		PlayerCreator playerCreator = new PlayerCreator(mAppContext);
		ItemCreator itemCreator = new ItemCreator(mAppContext);
		InventoryCreator inventoryCreator = new InventoryCreator(mAppContext);
		ProductionCreator productionCreator = new ProductionCreator(mAppContext);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////
		// Create players
		///////////////////////////////////////////////////////////////////////////////////////////
		
		// If has no player in DB, create new players.
		playerCreator.open();
		
		if(0 == playerCreator.queryAll().size()) {
			
			playerCreator.insert();
		}
		
		ArrayList<Player> players = playerCreator.queryAll();
		
		if(null != players) {
			
			for(int i = 0; i < players.size(); i++) {
				
				Player player = players.get(i);
				
				if(player.getId().equals(Global.THIS_USER)) {
					
					Global.getInstance().player = new Player(player.getId(), player.getName(), player.getExp(), player.getActionPoint(), player.getMoney());
					break;
				}
				
				player = null;
			}
		}
		
		playerCreator.close();
		
		// Update progress bar
		updateProgressBar(2);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////
		// Create items
		///////////////////////////////////////////////////////////////////////////////////////////
		
		// If has no item in DB, create new items.
		itemCreator.open();
		
		if(0 == itemCreator.queryAll().size()) {
			
			itemCreator.insert();
		}
		
		Hashtable<Integer, Item> itemList = Global.getInstance().itemList;
        
		ArrayList<Item> items = itemCreator.queryAll();
		
		if(null != items) {
			
			for(int i = 0; i < items.size(); i++) {
				
				Item item = items.get(i);
				itemList.put(item.getId(), item);
			}
		}
		
		itemCreator.close();
		
		// Update progress bar
		updateProgressBar(3);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////
		// Create inventory
		///////////////////////////////////////////////////////////////////////////////////////////

		// If has no inventory item in DB, create new inventory.
		inventoryCreator.open();
			
		if(0 == inventoryCreator.queryAll().size()) {
			
			inventoryCreator.insertInit(Global.THIS_USER);
		}
		
		ArrayList<InventoryItem> inventoryList = Global.getInstance().inventoryList;
        
		ArrayList<InventoryItem> inventoryItems = inventoryCreator.queryAll();
		
		if(null != inventoryItems) {
			
			for(int i = 0; i < inventoryItems.size(); i++) {
				
				InventoryItem item = inventoryItems.get(i);
				inventoryList.add(i, item);
			}
		}

		inventoryCreator.close();
		
		// Update progress bar
		updateProgressBar(4);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////
		// Create production
		///////////////////////////////////////////////////////////////////////////////////////////

		// If has no production item in DB, create new production.
		productionCreator.open();
			
		if(0 == productionCreator.queryAll().size()) {
			
			productionCreator.insertInit(Global.THIS_USER);
		}
		
		ArrayList<ProductionItem> productionList = Global.getInstance().productionList;
        
		ArrayList<ProductionItem> productionItems = productionCreator.queryAll();
		
		ProductionItem item = null;
		
		// Set null
		for(int i = 0; i < Constants.rule.PRODUCTION_MAX_COUNT; i++) {
			
			productionList.add(null);
		}
		
		// Add queried items to list
		for(int i = 0; i < productionItems.size(); i++) {
			
			item = productionItems.get(i);
			productionList.set(item.getPosition(), item);
		}
		
		// Fill vacant
		for(int i = 0; i < Constants.rule.PRODUCTION_MAX_COUNT; i++) {
			
			if(null == productionList.get(i)) {

				Timestamp now = new Timestamp(new Date().getTime());
				ProductionItem vacant = new ProductionItem(
						i, 0, 0, Constants.code.ITEM_TYPE_NOTHING
						, "Vacant", "Vacant",  now, now, now);
				productionList.set(i, vacant);
			}
		}

		productionCreator.close();
		
		// Update progress bar
		updateProgressBar(5);
	}
	
	private void updateProgressBar(int step) {
		
		// Animate progress bar
		int totalSteps = 5;

		// Update the progress bar after every step
		int progress = (int) ((step / (float) totalSteps) * 100);
		publishProgress(progress);

		// Do some long loading things
		try { Thread.sleep(50); } catch (InterruptedException ignore) {}
	}
}
