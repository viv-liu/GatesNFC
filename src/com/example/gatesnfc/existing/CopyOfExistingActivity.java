//package com.example.gatesnfc.existing;
//
//import java.util.Calendar;
//
//import com.example.gatesnfc.NFC_write;
//import com.example.gatesnfc.R;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//
//import com.example.gatesnfc.Patient;
//import com.example.gatesnfc.New.NameEntryFragment;
//
//public class CopyOfExistingActivity extends FragmentActivity implements OnClickListener{
//	private final int DEMO = 0, IMMUNE = 1, CHANGE = 2;
//	
//	public SectionsPagerAdapter mSectionsPagerAdapter;
//
//	
//	static ViewPager mViewPager;
//	public static Patient p_existing;
//	public static Patient p_reset;
//
//	protected static Object c_existing;	
//	protected static Object c_reset;
//	
//	static final int NUM_STEPS = 3;
//	
//	public String patientName;
//	public String momName;
//	public String dadName;
//	public String notes;
//	public String address;
//	public String unique_id;
//	
//	private String getStringData;
//	private String getCodeData;
//
//
//	private String mMessage;
//	
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.fragment_view_pager);
//		
//		Intent intent = getIntent();
//		getStringData = intent.getStringExtra("SentData"); //Data in string form
//		getCodeData = intent.getStringExtra("SentCode"); 
//
//		// Create the adapter that will return a fragment for each of the three
//		// primary sections of the app.
//		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//		
//		// Set up the ViewPager with the sections adapter.
//		mViewPager = (ViewPager) findViewById(R.id.pager);
//		mViewPager.setAdapter(mSectionsPagerAdapter);
//		
//		p_existing = new Patient();
//		p_existing.decryptPatientString(getStringData);
//		p_existing.setCode(getCodeData);
//		Log.d("ExistingActivity readMessage", getStringData);
//		p_reset = new Patient();
//		p_reset.decryptPatientString(getStringData);
//		p_reset.setCode(getCodeData);
//		
//		Class<?> c_existing = p_existing.getClass();
//		Class<?> c_reset = p_reset.getClass();
//		
//	}	
//
//
//	/**
//	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
//	 * one of the sections/tabs/pages.
//	 */
//	public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//		public SectionsPagerAdapter(FragmentManager fm) {
//			super(fm);
//		}
//
//		@Override
//		public Fragment getItem(int position) {
//
//			Fragment fragment = null;
//			
//			if(position < NUM_STEPS){
//				switch(position) {
//				case DEMO: fragment = PatientSummaryFragment.newInstance(position); break;
//				case IMMUNE: fragment = immune_sum.newInstance(position); break;
//				case CHANGE: fragment = change_Log.newInstance(position); break;
//				}
//			} else {
//			}
//			return fragment;			
//		}
//
//		@Override
//		public int getCount() {
//			// Show 3 total pages.
//			return NUM_STEPS;
//		}
//
//		@Override
//		public CharSequence getPageTitle(int position) {
//			switch (position) {
//			case DEMO:	return "Demographics";
//			case IMMUNE:return "Immunizations";
//			case CHANGE:return "Change Log";
//			}
//			return null;
//		}
//	}
//	
//	//TODO: how did you get the NewActivity onClick Listeners to happen in NewActivity?
//	@Override
//	public void onClick(View v) {
//		switch(v.getId()) {
////		case R.id.confirm_log:
////			Log.d("Confirm", "Clicked");
////			store_Data();
////			break;
////		case R.id.reset_log:
////			reset_Data();
////			break;
//		}
//	}
//	
//	/**
//	 * store_Data()
//	 * 
//	 * Starts NFC_write activity which will allow the user to write to the NFC
//	 * Set up mMessage and pass it an ID of "None" so it can overwrite anything
//	 * for it is a new patient.
//	 * In other types of writes would need to pass it the Unique ID code of the
//	 * read NFC tag
//	 */
//
//	private void store_Data() {
//		//TODO: put the real data to be stored here
//		mMessage = p_existing.constructPatientString();
//		String ID = p_existing.getCode();
//		Intent i = new Intent(this, NFC_write.class);
//		i.putExtra("SendData", mMessage);
//		i.putExtra("ID", ID);
//		startActivityForResult(i, 1);
//		
//	}
//	
//	
//	/**reset_AllData()
//	 * Resets all p_existing data to the scanned NFC data
//	 */
//	
//	public void reset_AllData(){
//		String reset = p_reset.constructPatientString();
//		p_existing.decryptPatientString(reset);
//		PatientSummaryFragment.updateView();
//		
//		//TODO: force the other two fragments to update their views
//	}
//	
//	/**reset_ImmuneData(String immune)
//	 * Reset's the date and the immunization type
//	 * to previous values
//	 * @param immune is the name of the immunization you want to reset
//	 */
//	
//	public void reset_ImmuneData(String immune){
//		//TODO: force update views
//		//TODO: Check if I'm getting the get and set values correct, I'm assuming they get null if not there
//		try {
//			if(p_reset.getImmunization(immune) != null)
//			{	//If there originally was an immunization then get the date of that
//				Calendar iDate = p_reset.getImmunizationDate(immune);
//				if (p_existing.getImmunization(immune) != null)
//				{	//If immunization exists then just set the date
//					p_existing.setImmunizationDate(immune, iDate);
//				}
//				else
//				{	//If immunization doesn't exist anymore, set it to true then set the Date
//					p_existing.setImmunization(immune);
//					p_existing.setImmunizationDate(immune, iDate);
//				}
//			}
//			else //When there originally wasn't an immunization
//			{
//				if (p_existing.getImmunization(immune) != null)
//				{
//					//Set the immunization to false
//					p_existing.setImmunization(immune);
//				}
//				else
//				{
//					//do nothing for both are already set to false
//				}
//			}
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchFieldException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//}
//
