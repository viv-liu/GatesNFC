package com.example.gatesnfc.New;

import java.util.Locale;

import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;
import com.example.gatesnfc.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;

public class NewActivity extends FragmentActivity {
	
	SectionsPagerAdapter mSectionsPagerAdapter;

	static ViewPager mViewPager;	
	static final int NUM_STEPS = 8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_view_pager);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Toast.makeText(this, "Please finish the tutorial", Toast.LENGTH_SHORT).show();
			return false;
		} 
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragment;
			
			if(position+1 < NUM_STEPS){
				fragment = SingleTextEntryFragment.newInstance(position);
			} else {
				fragment = null;
			}
			return fragment;			
		}

		@Override
		public int getCount() {
			// Show 8 total pages.
			return NUM_STEPS;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0: return "Child's name";
			case 1: return "Child's age";
//			case 2:	return getString(R.string.tutorial_title_section3).toUpperCase(l);
//			case 3:	return getString(R.string.tutorial_title_section4).toUpperCase(l);
//			case 4:	return getString(R.string.tutorial_title_section5).toUpperCase(l);
//			case 5:	return getString(R.string.tutorial_title_section6).toUpperCase(l);
//			case 6:	return getString(R.string.tutorial_title_section7).toUpperCase(l);
//			case 7:	return getString(R.string.tutorial_title_section8).toUpperCase(l);
			}
			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.show_dialog);
		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				CountryPicker picker = CountryPicker.newInstance("Select Country");
				picker.setListener(new CountryPickerListener() {

					@Override
					public void onSelectCountry(String name, String code) {
						Toast.makeText(
								NewActivity.this,
								"Country Name: " + name + " - Code: " + code
										+ " - Currency: "
										+ CountryPicker.getCurrencyCode(code),
								Toast.LENGTH_SHORT).show();
					}
				});
				
				picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
				return false;
			}
		});
		return true;
	}
}
