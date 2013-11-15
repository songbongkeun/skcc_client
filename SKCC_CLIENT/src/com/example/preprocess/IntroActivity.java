package com.example.preprocess;

import com.example.preprocess.LoadingTask.LoadingTaskFinishedListener;
import com.example.skcc_client.MainActivity;
import com.example.skcc_client.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class IntroActivity extends Activity implements LoadingTaskFinishedListener {

	// TAG for LogCat     
	public static final String TAG = IntroActivity.class.getSimpleName();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        
        // Hide action bar
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        
        // Show the splash screen
        setContentView(R.layout.splash_activity);
        
        // Find the progress bar
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.activity_splash_progress_bar);
        
        // Start your loading
        new LoadingTask(progressBar, this, getApplicationContext()).execute("www.google.co.uk"); // Pass in whatever you need a url is just an example we don't use it in this tutorial
    }

    // This is the callback for when your async task has finished
    @Override
	public void onTaskFinished() {
    	
    	Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
		finish(); // Don't forget to finish this Splash Activity so the user can't return to it!
	}
}
