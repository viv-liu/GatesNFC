package com.example.gatesnfc.existing;

import java.util.Locale;

import com.example.gatesnfc.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.gatesnfc.Patient;

public class ExistingActivity extends FragmentActivity {
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
				fragment = PatientSummaryFragment.newInstance(position);
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
			Locale l = Locale.getDefault();
			switch (position) {
			case 0: return "Demographics";
			case 1: return "Immunizations";
			case 2:	return "Change Log";
//			case 3:	return getString(R.string.tutorial_title_section4).toUpperCase(l);
//			case 4:	return getString(R.string.tutorial_title_section5).toUpperCase(l);
//			case 5:	return getString(R.string.tutorial_title_section6).toUpperCase(l);
//			case 6:	return getString(R.string.tutorial_title_section7).toUpperCase(l);
//			case 7:	return getString(R.string.tutorial_title_section8).toUpperCase(l);
			}
			return null;
		}
	}
}

