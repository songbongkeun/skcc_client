package com.example.preprocess;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.example.dbio.*;
import com.example.dbio.data.*;
import com.example.skcc_client.common.Global;
import com.example.skcc_client.gameObject.InventoryItem;
import com.example.skcc_client.gameObject.Item;
import com.example.skcc_client.gameObject.Player;
import com.example.skcc_client.gameObject.ProductionItem;
import com.example.skcc_client.test.TestData;

import android.content.Context;
import android.database.SQLException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

public class LoadingTask extends AsyncTask<String, Integer, Integer> {

	private static final String TAG = "LOADING";
	
	private final Context mAppContext;
	
	public interface LoadingTaskFinishedListener {
		
		void onTaskFinished(); // If you want to pass something back to the listener add a param to this method
	}

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
		TestData testData = new TestData();
		
		UserInfoCreator usercreator = new UserInfoCreator(mAppContext);
		ItemCreator itemcreator = new ItemCreator(mAppContext);
		InventoryCreator ivencreator = new InventoryCreator(mAppContext);
		ProductionCreator productcreator = new ProductionCreator(mAppContext);
		
		testData.generatorTestRuleData();
		
		try{
			usercreator.open();
			usercreator.insert();
			itemcreator.open();
			itemcreator.insert();
		}
		catch(SQLException se) {
			
			se.printStackTrace();
		}
		finally {
			
			usercreator.close();
			itemcreator.close();
			usercreator = null;
			itemcreator = null;
		}
		
		usercreator = new UserInfoCreator(mAppContext);
        
		usercreator.open();
		List<UserInfo> users = usercreator.queryAll();
		
		for( int i = 0; i < users.size(); i++) {
			
			UserInfo usrInfo = users.get(i);
			
			if(usrInfo.getID().equals(Global.THIS_USER)) {
				
				Global.getInstance().player = new Player(usrInfo.getName(), usrInfo.getID(), usrInfo.getExperience(), usrInfo.getActionPoint(), usrInfo.getMoney());
				break;
			}
			usrInfo = null;
				
		}
		
		usercreator.close();
		usercreator = null;
		
		
		Hashtable<Integer, Item> itemList = Global.getInstance().itemList;
		
		itemcreator = new ItemCreator(mAppContext);
        
		itemcreator.open();
		List<ItemInfo> items = itemcreator.queryAll();
		
		for( int i = 0; i < items.size(); i++) {
			
			ItemInfo item = items.get(i);
			Item it = new Item(item.getID(),item.getCompanyID(), 
				item.getItemType(),item.getName(), item.getDescription());
			itemList.put(item.getID(), it);
		}
		
		//testData.generateTestItems();
		/////////////////// inventory create ////////////////////////////
		ivencreator.open();
		ivencreator.insertInit(Global.THIS_USER, items, 10);
		ivencreator.close();
		/////////////////// inventory create done ///////////////////////
		
		/////////////////// inventory list create ////////////////////////////
		//testData.generateTestInventory(itemList);
		ivencreator = new InventoryCreator(mAppContext);
		ivencreator.open();
		
		ArrayList<InventoryItem> inventoryList = Global.getInstance().inventoryList;
		List<InventoryInfo> inventories = ivencreator.queryAll();
		
		for( int i = 0; i < inventories.size(); i++) {
			
			InventoryInfo inventory = inventories.get(i);
			InventoryItem invntry = new InventoryItem(itemList.get(inventory.getItemID()), inventory.getQuantity());
			inventoryList.add(invntry);
			
			inventory = null;
			invntry = null;
		}
		
		ivencreator.close();
		/////////////////// inventory list create done ///////////////////////
		
		/////////////////// production create ////////////////////////////
		productcreator.open();
		productcreator.insertInit(Global.THIS_USER);
		productcreator.close();
		/////////////////// production create done ////////////////////////////
		
		/////////////////// production list create ////////////////////////////
		//testData.generateTestProduction(itemList);
		productcreator = new ProductionCreator(mAppContext);
		productcreator.open();
		List<ProductionInfo> productions = productcreator.queryAll();
		ArrayList<ProductionItem> productionList = Global.getInstance().productionList;
		
		for( int i = 0; i < productions.size(); i++) {
			
			ProductionInfo production = productions.get(i);

			Timestamp stime = new Timestamp(Long.valueOf(production.getStartTime()));
			Timestamp etime = new Timestamp(Long.valueOf(production.getEndTime()));
			Timestamp exptime = new Timestamp(Long.valueOf(production.getExpireTime()));
			
			ProductionItem prductitem =  new ProductionItem(itemList.get(production.getItemID()), stime, etime, exptime);
			productionList.add(prductitem);
			
			stime = etime = exptime = null;
			production = null;
			prductitem = null;
		}
		
		productcreator.close();
		/////////////////// production list create done ///////////////////////
		
		itemcreator.close();
		itemcreator = null;
		
		int count = 10;
		for (int i = 0; i < count; i++) {

			// Update the progress bar after every step
			int progress = (int) ((i / (float) count) * 100);
			publishProgress(progress);

			// Do some long loading things
			try { Thread.sleep(200); } catch (InterruptedException ignore) {}
		}
	}
}
