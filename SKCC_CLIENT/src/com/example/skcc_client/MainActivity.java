package com.example.skcc_client;

import java.util.Locale;

import com.example.skcc_client.test.TestData;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setLogo(R.drawable.title_padding);
		actionBar.setCustomView(R.layout.titlebar_user_info);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
		
		ImageView a = new ImageView(getApplicationContext());
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(
			new ViewPager.SimpleOnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					actionBar.setSelectedNavigationItem(position);
				}
			});
		
		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
		
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			
			Tab newTab = actionBar.newTab().setTabListener(this);
			if(0 == i) {
				newTab.setCustomView(mSectionsPagerAdapter.getBackground(true, i));
			}
			else {
				newTab.setCustomView(mSectionsPagerAdapter.getBackground(false, i));
			}
			actionBar.addTab(newTab);
		}
		
		
		// TODO: Test Code
		TestData test = new TestData();
		test.generateTestData();
	}
	
	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
		tab.setCustomView(null);
		tab.setCustomView(mSectionsPagerAdapter.getBackground(true, tab.getPosition()));
	}
	
	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

		tab.setCustomView(null);
		tab.setCustomView(mSectionsPagerAdapter.getBackground(false, tab.getPosition()));
	}
	
	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}
		
	/**
	* A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	* one of the sections/tabs/pages.
	*/
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		Context mContext;
		
		public SectionsPagerAdapter(FragmentManager fm) {
		
			super(fm);
		}
	
		@Override
		public Fragment getItem(int position) {
		
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			switch(position) {
			
				case 0:
					return new InventoryTab(mContext);
				case 1:
					return new ProductionTab(mContext);
				case 2:
					return new QuestTab(mContext);
				case 3:
					return new NFCTab(mContext);
			}
			
			return null;
		}
	
		@Override
		public int getCount() {
		
			// Show 4 total pages.
			return 4;
		}
	
		public int getBackground(boolean isOn, int position) {
			
			switch (position) {
			
				case 0:
					return isOn ? R.layout.tabstrip_inventory : R.layout.tabstrip_inventory_off;
				case 1:
					return isOn ? R.layout.tabstrip_production : R.layout.tabstrip_production_off;
				case 2:
					return isOn ? R.layout.tabstrip_quest : R.layout.tabstrip_quest_off;
				case 3:
					return isOn ? R.layout.tabstrip_nfc : R.layout.tabstrip_nfc_off;
			}
			
			return R.layout.tabstrip_inventory;
		}
	
		@Override
		public CharSequence getPageTitle(int position) {
		
			Locale l = Locale.getDefault();
			
			switch (position) {
			
				case 0:
					return getString(R.string.label_inventory).toUpperCase(l);
				case 1:
					return getString(R.string.label_production).toUpperCase(l);
				case 2:
					return getString(R.string.label_quest).toUpperCase(l);
				case 3:
					return getString(R.string.label_nfc).toUpperCase(l);
			}
			
			return null;
		}
	}
	
	/**
	* A dummy fragment representing a section of the app, but that simply
	* displays dummy text.
	*/
	public static class DummySectionFragment extends Fragment {
		
		/**
		* The fragment argument representing the section number for this
		* fragment.
		*/
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		public DummySectionFragment() {
			
		}
	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			
			View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
			TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
}