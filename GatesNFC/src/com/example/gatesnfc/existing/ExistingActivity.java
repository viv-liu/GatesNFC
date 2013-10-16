package com.example.gatesnfc.existing;

import java.util.Locale;

import com.example.gatesnfc.R;
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


	protected static Object c_existing;	
	
	static final int NUM_STEPS = 3;
	
	public String patientName;
	public String momName;
	public String dadName;
	public String notes;
	public String address;
	public String unique_id;
	

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
		
		p_existing = new Patient();
		
		Class<?> c_existing = p_existing.getClass();
		
		//Dummy Info
		//TODO: Create a backup so on reset (in menu options) goes back to the original default
		patientName = "Timmy";
		momName ="Wanda";
		dadName = "Cosmo";
		notes = "This is Notes";
		address = "16 My house";
		unique_id = "007";
		p_existing.setName(patientName);
		p_existing.set_dadName(dadName);
		p_existing.set_momName(momName);
		p_existing.setNotes(notes);
		p_existing.setAdd(address);
		p_existing.setCode(unique_id);
		
		try {
			p_existing.setImmunization("DTP1");
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
		try {
			p_existing.setImmunization("DTP2");
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
		try {
			p_existing.setImmunization("HepB1");
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			p_existing.setImmunization("HepA1");
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
}

