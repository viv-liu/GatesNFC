package com.example.gatesnfc.existing;

import java.util.Calendar;

import com.example.gatesnfc.NFC_write;
import com.example.gatesnfc.R;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;

import com.example.gatesnfc.Patient;

public class ExistingActivity extends FragmentActivity implements OnClickListener, DatePickerDialog.OnDateSetListener{
	private final int DEMO = 0, IMMUNE = 1, CHANGE = 2;
	
	public SectionsPagerAdapter mSectionsPagerAdapter;
	
	static ViewPager mViewPager;
	public static Patient p_existing;
	public static Patient p_reset;

	protected static Object c_existing;	
	protected static Object c_reset;
	
	static final int NUM_STEPS = 3;
	
	public static final String DATEFORMAT = "MMM dd, yyyy";
	
	public String patientName;
	public String momName;
	public String dadName;
	public String notes;
	public String address;
	public String unique_id;
	
	private String getStringData;
	private String getCodeData;

	private String mMessage;
	private String mStatus;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_view_pager);
			
		Intent intent = getIntent();
		getStringData = intent.getStringExtra("SentData"); //Data in string form
		getCodeData = intent.getStringExtra("SentCode"); 

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		p_existing = new Patient();
		p_existing.decryptPatientString(getStringData);
		p_existing.setCode(getCodeData);
		Log.d("ExistingActivity readMessage", getStringData);
		p_reset = new Patient();
		p_reset.decryptPatientString(getStringData);
		p_reset.setCode(getCodeData);
		
		Class<?> c_existing = p_existing.getClass();
		Class<?> c_reset = p_reset.getClass();
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
			
			if(position < NUM_STEPS){
				switch(position) {
				case DEMO: fragment = PatientSummaryFragment.newInstance(position); break;
				case IMMUNE: fragment = immune_sum.newInstance(position); break;
				case CHANGE: fragment = change_Log.newInstance(position); break;
				}
			} else {
			}
			return fragment;			
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return NUM_STEPS;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case DEMO:	return "Demographics";
			case IMMUNE:return "Immunizations";
			case CHANGE:return "Change Log";
			}
			return null;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button_id:
			showIDDialog();
			break;
		case R.id.button_name:
			mStatus = "Patient's Name";
			showNameDialog();
			break;
		case R.id.button_birthdate:
			showDateofBirthDialog();
			break;
		case R.id.button_mom:
			mStatus = "Mom's Name";
			showNameDialog();
			break;
		case R.id.button_dad:
			mStatus = "Dad's Name";
			showNameDialog();
			break;
		case R.id.button_address:
			showEditAddressDialog();
			break;
		case R.id.button_notes:
			//TODO:Inflate a notes dialog
			break;
		}
	}
	
	private void showIDDialog() {
        EditIDDialog dialog = new EditIDDialog();
        dialog.show(getSupportFragmentManager(), "dialog");
    }
	
	private void showNameDialog(){
        EditNameDialog dialog = new EditNameDialog();
        dialog.show(getSupportFragmentManager(), "dialog");
	}
	
	private void showDateofBirthDialog(){
		Calendar cal = p_existing.birthday;
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH); 
		int day = cal.get(Calendar.DATE); 
		DatePicker_Fix newFragment = DatePicker_Fix.newInstance(year, month, day);
        newFragment.show(getSupportFragmentManager(), "DatePicker");
	}
	
	@Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		p_existing.birthday = cal;
		updateView();
    }
	 
	 private void showEditAddressDialog(){
	    EditAddressDialog dialog = new EditAddressDialog();
	    dialog.show(getFragmentManager(), "dialog");
	 }
	
	public String getStatus() {
		return mStatus;
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
		//TODO: get ID matching working
		mMessage = p_existing.constructPatientString();
		String ID = p_existing.getCode();
		Intent i = new Intent(this, NFC_write.class);
		i.putExtra("SendData", mMessage);
		i.putExtra("ID", ID);
		startActivityForResult(i, 1);
		
	}
	
	
	/**reset_AllData()
	 * Resets all p_existing data to the scanned NFC data
	 */
	
	public void reset_AllData(){
		//TODO: For some odd reason this crashes..
		//seems to be trying to parse the First Name to the Calendar for some reason?
		String reset = p_reset.constructPatientString();
		Log.d("p_reset string", reset);
		p_existing.decryptPatientString(reset);
		updateView();
		
		//TODO: force the other two fragments to update their views
	}
	
	/**reset_ImmuneData(String immune)
	 * Reset's the date and the immunization type
	 * to previous values
	 * @param immune is the name of the immunization you want to reset
	 */
	
	public void reset_ImmuneData(String immune){
		//TODO: force update views
		//TODO: Check if I'm getting the get and set values correct, I'm assuming they get null if not there
		try {
			if(p_reset.getImmunization(immune) != null)
			{	//If there originally was an immunization then get the date of that
				Calendar iDate = p_reset.getImmunizationDate(immune);
				if (p_existing.getImmunization(immune) != null)
				{	//If immunization exists then just set the date
					p_existing.setImmunizationDate(immune, iDate);
				}
				else
				{	//If immunization doesn't exist anymore, set it to true then set the Date
					p_existing.setImmunization(immune);
					p_existing.setImmunizationDate(immune, iDate);
				}
			}
			else //When there originally wasn't an immunization
			{
				if (p_existing.getImmunization(immune) != null)
				{
					//Set the immunization to false
					p_existing.setImmunization(immune);
				}
				else
				{
					//do nothing for both are already set to false
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void updateView() {
		// TODO Also force the Immunizations to updateView
		PatientSummaryFragment.code.setText (ExistingActivity.p_existing.getCode());
		PatientSummaryFragment.name.setText (ExistingActivity.p_existing.firstName + " " + ExistingActivity.p_existing.lastName);
		PatientSummaryFragment.birthdate.setText(DateFormat.format(DATEFORMAT, ExistingActivity.p_existing.birthday).toString());
		PatientSummaryFragment.mom.setText (ExistingActivity.p_existing.mom_firstName + " " + ExistingActivity.p_existing.mom_lastName);
		PatientSummaryFragment.dad.setText (ExistingActivity.p_existing.dad_firstName + " " + ExistingActivity.p_existing.dad_lastName);
		PatientSummaryFragment.address.setText (ExistingActivity.p_existing.getAddressString());
		PatientSummaryFragment.notes.setText (ExistingActivity.p_existing.notes);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlertDialog.Builder dlgAlert= new AlertDialog.Builder(this)
	        .setTitle("Are you sure you want to exit?")
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.existing, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.reset_all:
	        	reset_AllData();
	            return true;
	        case R.id.help:
	            //TODO: Create Help Function
	            return true;
	     }
		return false;
	}

}

