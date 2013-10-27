package com.example.gatesnfc.New;

import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;
import com.example.gatesnfc.Patient;
import com.example.gatesnfc.PrefUtils;
import com.example.gatesnfc.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class NewActivity extends FragmentActivity implements OnClickListener{
	private final int NAME = 0, BIRTHDATE = 1, PARENTSNAME = 2, ADDRESS = 3, VACCINE = 4, NOTES = 5, SUMMARY = 6;
	private static final int NUM_PAGES = 7;
	SectionsPagerAdapter mSectionsPagerAdapter;
	static ViewPager mViewPager;	
	public static Patient patient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_view_pager);

		patient = new Patient();
		try {
			patient.getImmunization("HepA1");
			patient.getPackagedImmuneString();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
	    
			@Override
			public void onPageScrolled(int start, float offset, int offsetPixels) {
				switch(start) {
				case NAME: patient.firstName = NameEntryFragment.et_firstname.getText().toString(); 
						   patient.lastName = NameEntryFragment.et_lastname.getText().toString();
					break;
				case BIRTHDATE: patient.setBirthday(DateEntryFragment.cal);
					break;
				case PARENTSNAME: patient.mom_firstName = ParentsNameEntryFragment.et_momfirstname.getText().toString();
								  patient.mom_lastName = ParentsNameEntryFragment.et_momlastname.getText().toString();
								  patient.dad_firstName = ParentsNameEntryFragment.et_dadfirstname.getText().toString();
								  patient.dad_lastName = ParentsNameEntryFragment.et_dadlastname.getText().toString();
					break;
				case ADDRESS: if(AddressEntryFragment.et_number.getText().toString() != null && 
								!AddressEntryFragment.et_number.getText().toString().isEmpty()) {
									patient.number = Integer.valueOf(AddressEntryFragment.et_number.getText().toString());
								}
							  patient.street = AddressEntryFragment.et_street.getText().toString();
							  patient.optional = AddressEntryFragment.et_optional.getText().toString();
							  patient.region = AddressEntryFragment.et_region.getText().toString();
							  patient.country = AddressEntryFragment.country;
							  patient.postal = AddressEntryFragment.et_postal.getText().toString();
					break;
				case VACCINE:
					//TODO: Shen lol
					break;
				case NOTES:	patient.notes = NotesEntryFragment.et_notes.getText().toString();
					break;
				}
			}
			
			@Override
		    public void onPageScrollStateChanged (int arg0){/*Nothing*/}
			@Override
			public void onPageSelected(int arg0) {/*Nothing*/}
	    });
		patient = new Patient();
	}	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO: confirm onBackPressed: Your unsaved info will die. 
		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlertDialog.Builder dlgAlert= new AlertDialog.Builder(this)
	        .setTitle("Are you sure?")
	        .setCancelable(true)
	        .setPositiveButton("Yes, leave.", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	finish();
	            }
	        })
	        .setNegativeButton("No, stay.", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	// Nada
	            }
	        });
			AlertDialog box=dlgAlert.create();
            box.show();
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

			Fragment fragment = null;
			
			if(position < NUM_PAGES){
				switch(position) {
				case NAME: fragment = NameEntryFragment.newInstance(position); break;
				case BIRTHDATE: fragment = DateEntryFragment.newInstance(position); break;
				case PARENTSNAME: fragment = ParentsNameEntryFragment.newInstance(position); break;
				case ADDRESS: fragment = AddressEntryFragment.newInstance(position); break;
				case VACCINE: fragment = VaccineEntryFragment.newInstance(position); break;
				case NOTES: fragment = NotesEntryFragment.newInstance(position); break;
				case SUMMARY: fragment = PatientSummaryFragment.newInstance(position, patient); break;
				}
			} else {
				fragment = SingleTextEntryFragment.newInstance(position);
			}
			return fragment;			
		}

		@Override
		public int getCount() {
			// Show SUMMARY total pages.
			return NUM_PAGES;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case NAME: return "NAME";
			case BIRTHDATE: return "BIRTHDATE";
			case PARENTSNAME:	return "PARENTS";
			case ADDRESS:	return "ADDRESS";
			case VACCINE:	return "VACCINES";
			case NOTES:	return "NOTES";
			case SUMMARY:	return "SUMMARY";
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

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button_country:
			// Open up CountryPicker 
			final CountryPicker picker = CountryPicker.newInstance("Select Country");
			picker.setListener(new CountryPickerListener() {

				@Override
				public void onSelectCountry(String name, String code) {
					Toast.makeText(
							NewActivity.this,
							"Country Name: " + name + " - Code: " + code
									+ " - Currency: "
									+ CountryPicker.getCurrencyCode(code),
							Toast.LENGTH_SHORT).show();
					PrefUtils.setStringPreference(NewActivity.this, PrefUtils.COUNTRY_KEY, name);
					picker.dismiss();
				}
			});
			picker.show(this.getSupportFragmentManager(), "COUNTRY_PICKER");
			break;
		case R.id.button_complete:
			// TODO: NFC storing here.
			Toast.makeText(this, "Do some NFC storing...", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case R.id.button_name:
			mViewPager.setCurrentItem(NAME);
			break;
		case R.id.button_birthdate:
			mViewPager.setCurrentItem(BIRTHDATE);
			break;
		case R.id.button_mom:
			mViewPager.setCurrentItem(PARENTSNAME);
			break;
		case R.id.button_dad:
			mViewPager.setCurrentItem(PARENTSNAME);
			break;
		case R.id.button_address:
			mViewPager.setCurrentItem(ADDRESS);
			break;
		case R.id.button_notes:
			mViewPager.setCurrentItem(NOTES);
			break;
		}
	}
}
