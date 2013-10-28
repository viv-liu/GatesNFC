package com.example.gatesnfc.existing;

import com.example.gatesnfc.DeepCopy;
import com.example.gatesnfc.NFC_write;
import com.example.gatesnfc.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.gatesnfc.Patient;

public class ExistingActivity extends FragmentActivity implements OnClickListener{
	
	public SectionsPagerAdapter mSectionsPagerAdapter;

	
	static ViewPager mViewPager;
	public static Patient p_existing;
	public static Patient p_reset;

	protected static Object c_existing;	
	protected static Object c_reset;
	
	static final int NUM_STEPS = 3;
	
	public String patientName;
	public String momName;
	public String dadName;
	public String notes;
	public String address;
	public String unique_id;
	
	private String getStringData;
	private String getCodeData;


	private String mMessage;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_view_pager);
		
		Intent intent = getIntent();
		getStringData = intent.getStringExtra("SentData"); //Data in string form
		getCodeData = intent.getStringExtra("SentCode"); 
		// TODO: populate a patient class with string data

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		p_existing = new Patient();
		p_reset = new Patient();
		
		Class<?> c_existing = p_existing.getClass();
		Class<?> c_reset = p_reset.getClass();
		
		//Dummy Info
		//TODO: Create a backup so on reset (in menu options) goes back to the original default
		unique_id = getCodeData;
		patientName = getStringData;
		momName ="Wanda";
		dadName = "Cosmo";
		notes = "This is Notes";
		address = "16 My house";

		
		p_existing.firstName = (patientName);
		p_existing.dad_firstName = (dadName);
		p_existing.mom_firstName = (momName);
		p_existing.setNotes(notes);
		p_existing.street = (address);
		p_existing.setCode(unique_id);
		// Initializing a RESET
		p_reset.firstName = (patientName);
		p_reset.dad_firstName = (dadName);
		p_reset.mom_firstName = (momName);
		p_reset.setNotes(notes);
		p_reset.street = (address);
		p_reset.setCode(unique_id);
		
		try {
			p_existing.setImmunization("DTP1");
			p_existing.setImmunization("DTP2");
			p_existing.setImmunization("HepB1");
			p_existing.setImmunization("HepA1");
			p_reset.setImmunization("DTP1");
			p_reset.setImmunization("DTP2");
			p_reset.setImmunization("HepB1");
			p_reset.setImmunization("HepA1");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
//		p_existing = (Patient) DeepCopy.copy(p_reset);
		
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
				if(position == 0) {
					fragment = PatientSummaryFragment.newInstance(position);
				}
				else 
					fragment = immune_sum.newInstance(position);
			} else {
				fragment = change_Log.newInstance(position);
				position = 0;
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
			switch (position) {
			case 0: return "Demographics";
			case 1: return "Immunizations";
			case 2:	return "Change Log";
			}
			return null;
		}
	}
	
	//TODO: how did you get the NewActivity onClick Listeners to happen in NewActivity?
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
//		case R.id.confirm_log:
//			Log.d("Confirm", "Clicked");
//			store_Data();
//			break;
//		case R.id.reset_log:
//			reset_Data();
//			break;
		}
	}
	
	/**
	 * store_Data()
	 * 
	 * Starts NFC_write activity which will allow the user to write to the NFC
	 * Set up mMessage and pass it an ID of "None" so it can overwrite anything
	 * for it is a new patient.
	 * In other types of writes would need to pass it the Unique ID code of the
	 * read NFC tag
	 */

	private void store_Data() {
		//TODO: put the real data to be stored here
		mMessage = "ABCDEFG";
		String ID = "AD4503E0";
		Intent i = new Intent(this, NFC_write.class);
		i.putExtra("SendData", mMessage);
		i.putExtra("ID", ID);
		startActivityForResult(i, 1);
		
	}
	
	private void reset_Data(){
		//Changes all values of p_existing to p_reset
		//TODO: make it so you can deepcopy a Patient Class
		//OR JUST USE OUR METHOD OF CONSTRUCTING THE CLASS. get the string of the p_reset out
		//Then laod the string into p_existing
//		ExistingActivity.p_existing = (Patient) DeepCopy.copy(ExistingActivity.p_reset);
		PatientSummaryFragment.updateView();
		
		//TODO: force the other two fragments to update their views
	}

}

