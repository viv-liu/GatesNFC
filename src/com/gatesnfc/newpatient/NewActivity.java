package com.gatesnfc.newpatient;

import java.util.Calendar;

import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;

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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

import com.gatesnfc.NFC_write;
import com.gatesnfc.Patient;
import com.gatesnfc.R;

public class NewActivity extends FragmentActivity implements OnClickListener, DatePickerDialog.OnDateSetListener{
	private final int DEMO = 0, IMMUNE = 1;
	public SectionsPagerAdapter mSectionsPagerAdapter;
	
	static ViewPager mViewPager;
	public static Patient p_new;

	protected static Object c_existing;	
	protected static Object c_reset;
	
	static final int NUM_STEPS = 2;
	
	public static final String DATEFORMAT = "MMM dd, yyyy";
	
	public String patientName;
	public String momName;
	public String dadName;
	public String notes;
	public String address;
	public String unique_id;

	private String mMessage;
	private String mStatus;
	

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

		p_new = new Patient();
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
				case IMMUNE: fragment = ImmuneListFragment.newInstance(position); break;
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
			}
			return null;
		}
	}
	
	@Override
	public void onClick(final View v) {
		switch(v.getId()) {
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
			showAddressDialog();
			break;
		case R.id.button_notes:
			showNotesDialog();
			break;
		case R.id.button_country:
			// Open up CountryPicker 
			final CountryPicker picker = CountryPicker.newInstance("Select Country");
			picker.setListener(new CountryPickerListener() {

				@Override
				public void onSelectCountry(String name, String code) {
					// Leave this blurb here as a reference
					/*Toast.makeText(
							NewActivity.this,
							"Country Name: " + name + " - Code: " + code
									+ " - Currency: "
									+ CountryPicker.getCurrencyCode(code),
							Toast.LENGTH_SHORT).show();*/
					// Update the button
					((Button)v).setText(name);
					picker.dismiss();
				}
			});
			picker.show(this.getSupportFragmentManager(), "COUNTRY_PICKER");
			break;
		}
		PatientSummaryFragment.updateButtonTexts();
	}
	
	private void showNameDialog(){
        EditNameDialog dialog = new EditNameDialog();
        dialog.show(getSupportFragmentManager(), "dialog");
	}
	
	private void showDateofBirthDialog(){
		Calendar cal = Calendar.getInstance();
		if(p_new.birthday != null) {
			cal = p_new.birthday;
		}
		 
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
		p_new.birthday = cal;
		PatientSummaryFragment.updateButtonTexts();
    }
	 
	 private void showAddressDialog(){
	    EditAddressDialog dialog = new EditAddressDialog();
	    dialog.show(getFragmentManager(), "dialog");
	 }
	 
	 private void showNotesDialog() {
		 EditNotesDialog dialog = new EditNotesDialog();
		 dialog.show(getSupportFragmentManager(), "dialog");
	 }
	
	public String getStatus() {
		return mStatus;
	}
	
	/**
	 * storeData()
	 * 
	 * Starts NFC_write activity which will allow the user to write to the NFC
	 * Set up mMessage and pass it an ID of "None" so it can overwrite anything
	 * for it is a new patient.
	 * In other types of writes would need to pass it the Unique ID code of the
	 * read NFC tag
	 * 
	 * Expects mMessage to be loaded.
	 */

	private void storeData() {
		String ID = "None";
		Intent i = new Intent(this, NFC_write.class);
		mMessage = p_new.constructPatientString();
		i.putExtra("SendData", mMessage);
		i.putExtra("ID", ID);
		startActivityForResult(i, 1);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

		     if(resultCode == RESULT_OK){    
		    	 
				 String result=data.getStringExtra("result"); 
		    	 
		    	 AlertDialog.Builder dlgAlert= new AlertDialog.Builder(this)
		        .setTitle(result)
		        .setPositiveButton("Back to menu", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		            	finish();
		            }
		        })
		        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		            }
		        });
				AlertDialog box=dlgAlert.create();
		        box.show();         
		     }
		     if (resultCode == RESULT_CANCELED) { 
		    	 String result=data.getStringExtra("result"); 
		    	 AlertDialog.Builder dlgAlert= new AlertDialog.Builder(this)
			        .setTitle(result)
			        .setCancelable(true)
			        .setPositiveButton("Write Again", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int whichButton) {
			            	storeData();
			            }
			        })
			        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int whichButton) {
			            }
			        });
					AlertDialog box=dlgAlert.create();
			        box.show();
		     }
		  }
		  
		}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlertDialog.Builder dlgAlert= new AlertDialog.Builder(this)
	        .setTitle("Are you sure you want to exit?")
	        .setCancelable(true)
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	finish();
	            }
	        })
	        .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
		getMenuInflater().inflate(R.menu.newpatient, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.done:
	        	storeData();
	            return true;
	        case R.id.help:
	            //TODO: Create Help Function
	            return true;
	     }
		return false;
	}
	public static void updateAllViews() {
		PatientSummaryFragment.updateButtonTexts();
	}

}

