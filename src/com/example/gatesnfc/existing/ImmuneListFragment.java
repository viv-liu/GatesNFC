package com.example.gatesnfc.existing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import com.add_immunepicker.*;
import com.example.gatesnfc.R;
import com.immunepicker.*;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class ImmuneListFragment extends Fragment implements OnClickListener,
Comparator<Immunization>{
	private static InputFilter[] nameFilter;
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static View rootView;
	private static Calendar cal;
	
	private static Immunization selectedImmunization;
	
	/**
	 * View components
	 */
	private EditText searchEditText;
	private ListView ImmunizationListView;

	/**
	 * Adapter for the listview
	 */
	private static ImmunizationListAdapter adapter;

	/**
	 * Immunization Lists used in this file
	 */
	private List<Immunization>justChanged;	// holds recent changes (adds and removes), is cleared before use
	private static List<Immunization> originalImmunizationList; // holds original (old stuff)
	private static List<Immunization> completeImmunizationList; // holds orignal + just changed, updated in update view

	/**
	 * Hold Immunizations that matched user query
	 */
	//private List<Immunization> originalImmunizationList;

	public EditText getSearchEditText() {
		return searchEditText;
	}
	
	public static Fragment newInstance(int position) {
		ImmuneListFragment myFragment = new ImmuneListFragment();
		
		Bundle args = new Bundle();
		args.putInt(ImmuneListFragment.ARG_SECTION_NUMBER, position);
		
		myFragment.setArguments(args);
		nameFilter = new InputFilter[1];
		nameFilter[0] = new InputFilter.LengthFilter(10);

	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_immunization_sum, container, false);
		//setValuesList = new ArrayList<String>();
		justChanged = new ArrayList<Immunization>();
		originalImmunizationList = new ArrayList<Immunization>();
		originalImmunizationList.addAll(getOriginalImmunizationList());
		completeImmunizationList = new ArrayList<Immunization>();
		completeImmunizationList.addAll(originalImmunizationList);

 		ImmunizationListView = (ListView) rootView
 				.findViewById(R.id.immunization_picker_listview);
 		// Init adapter
 		adapter = new ImmunizationListAdapter(getActivity(), completeImmunizationList);
 		ImmunizationListView.setAdapter(adapter);

 		// Inform listener
 		ImmunizationListView.setOnItemClickListener(new OnItemClickListener() {

 			@Override
 			public void onItemClick(AdapterView<?> parent, View view,
 					int position, long id) {
 					selectedImmunization = completeImmunizationList.get(position);
 					showDatePickerDialog();
 					adapter.notifyDataSetChanged();
 			}
 		});
 		ImmunizationListView.setLongClickable(true);
 		ImmunizationListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
					Immunization im = completeImmunizationList.remove(position);
			    	try {
						ExistingActivity.p_existing.setImmunizationDate(im.getName(), null);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					adapter.notifyDataSetChanged();
				return true;
			}
 		});
		Button immunization_button = (Button) rootView.findViewById(R.id.add_immunization);
	    immunization_button.setOnClickListener(this);
	    
	    updateView();
	    
		return rootView;
	}
	
	/**
	 * Search originalImmunizationList contains text and put result into
	 * originalImmunizationList
	 * 
	 * @param text
	 */
	@SuppressLint("DefaultLocale")
	private void search(String text) {
		completeImmunizationList.clear();

		for (Immunization immunization : completeImmunizationList) {
			if (immunization.getName().toLowerCase(Locale.ENGLISH)
					.contains(text.toLowerCase())) {
				completeImmunizationList.add(immunization);
			}
		}

		adapter.notifyDataSetChanged();
	}

	/**
	 * Support sorting the Immunizations list
	 */
	@Override
	public int compare(Immunization lhs, Immunization rhs) {
		return lhs.getDate().compareTo(rhs.getDate());
	}

	/**
	 * Handles the click buttons that launch a immunization adapter to add or remove immunizations
	 * which defaults to the current date when added to the patient.
	 */

	@Override
	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.add_immunization:
			add_immunization();
		break;
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 1) //make sure fragment codes match up
	    {
	    	Log.d("Status", data.getStringExtra("Status"));
	       if (!data.getStringExtra("Status").equals("F"))
	       {
	    	   for (int i = 0; i < justChanged.size(); i++)
	    	   {
	    		   try {
	    			    Calendar cal = Calendar.getInstance();
						//Set the selected Immunization to True, by default the date is set to today
	    			    //User can change date once it appears in list
	    			    if (data.getStringExtra("Status").equals("Add"))
	    			    	ExistingActivity.p_existing.setImmunizationDate(justChanged.get(i).getName(), cal);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
	    	   }
	       }
	    }
	    updateView(); 
	    //Always repopulate view for items have changed and to add or remove view items it 
	    //requires repopulation
	}
	
	private void add_immunization() {
		
		Add_ImmunizationPicker picker = new Add_ImmunizationPicker();
		Add_ImmunizationPicker.newInstance("Existing");
		picker.setTargetFragment(this, 1);
		//setValuesList.clear();
		justChanged.clear();
		picker.setListener(new Add_ImmunizationPickerListener() {
			@Override
			public void onSelectImmunization(String name) {
				Calendar c = Calendar.getInstance();
				Immunization i = new Immunization();
				i.setDate(c);
				i.setName(name);
				if(justChanged.contains(i))
				{
					justChanged.remove(i);
				}
				else
				{
					justChanged.add(i);
				}
			}
		});
		
		picker.show(getFragmentManager(), "Immunization_PICKER");
	}

	public void updateView(){
 		completeImmunizationList = getCompleteImmunizationList(); 		
 		adapter.notifyDataSetChanged();
	}
	
	private List<Immunization> getCompleteImmunizationList() {
		Collections.sort(justChanged, this);
		completeImmunizationList.addAll(justChanged);
		adapter.notifyDataSetChanged();
		return completeImmunizationList;
	}
	/**
	 * Get all Immunizations with code and name from res/raw/Immunizations.json
	 * 
	 * @return
	 */
	private List<Immunization> getOriginalImmunizationList() {
		try {
			List<Immunization> originalShots = new ArrayList<Immunization>();

			// Read from local file
			String allImmunizationsString = readFileAsString(getActivity());
			JSONObject jsonObject = new JSONObject(allImmunizationsString);
			Iterator<?> keys = jsonObject.keys();
			// Add original data to all Immunizations list
			while (keys.hasNext()) {
			String key = (String) keys.next();
				if(ExistingActivity.p_reset.getImmunization(jsonObject.getString(key)))
				{
					//If the patient has the immunization then set as below
					Immunization immunization = new Immunization();
					immunization.setGreyed(true);
					immunization.setName(jsonObject.getString(key));
					immunization.setDate(ExistingActivity.p_existing.getImmunizationDate(jsonObject.getString(key)));
					originalShots.add(immunization);
				}
				//else don't display
			}
			//TODO: sort by calendar
			Collections.sort(originalShots, this);
			return originalShots;

			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	/**readFileAsString
	 * 
	 * Convenient function to read from raw file
	 * 
	 * @param context
	 * @return a local variable
	 * @throws java.io.IOException
	 */
	private static String readFileAsString(Context context)
			throws java.io.IOException {
		InputStream inputStream = context.getResources().openRawResource(
				R.raw.list_immunes);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuffer result = new StringBuffer();
		String line;
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		reader.close();
		return result.toString();
	}
	
	public static void enableGodMode() {
		for(int i = 0; i < completeImmunizationList.size(); i++) {
			if(completeImmunizationList.get(i).isGreyed()) {
				completeImmunizationList.get(i).setGreyed(false);
			}
		}
		adapter.notifyDataSetChanged();
	}
	public static void resetImmuneChanges() {
		Log.d("original", originalImmunizationList.toString());
		Log.d("complete", completeImmunizationList.toString());
		completeImmunizationList.clear();
		completeImmunizationList.addAll(originalImmunizationList);
		adapter.notifyDataSetChanged();
	}
	/**DatePickerDialogFragment
	 * 
	 * Functions that displays Date on Click of Adapter item and edits that adapter's
	 * item's date of immunization.
	 * 
	 * @field: cal 
	 * is used to store the calendar object. This requires that the calendar be initialized
	 * in fragment and then passed to dialogfragment. No problems to occur as calendar is 
	 * then reset in dialogfragment
	 * 
	 * @field: selectedImmunization
	 * the selectedImmunization field is obtained from the listadapter onClick event and is 
	 * then used to get the date of the selectedImmunization to change
	 * 
	 */
	
	public void showDatePickerDialog() {
		cal = Calendar.getInstance();
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getActivity().getFragmentManager(), "datePicker");
	}
	
	public static class DatePickerFragment extends DialogFragment {
	    
	    public static final String YEAR = "Year";
	    public static final String MONTH = "Month";
	    public static final String DAY = "Day";

	    private int year;
	    private int month;
	    private int day;
	    
	    private OnDateSetListener mListener;

	    public DatePicker_Fix newInstance() {
	    	DatePicker_Fix newDialog = new DatePicker_Fix();

	        // Supply initial date to show in dialog.
	        Bundle args = new Bundle();
	        newDialog.setArguments(args);

	        return newDialog;
	    }

	    // Added to original version in order to restore bundle saved by Activity
	    @Override
	    public void onCreate(Bundle savedInstanceState){
	        super.onCreate(savedInstanceState);
	        cal = selectedImmunization.getDate();
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH); 
			day = cal.get(Calendar.DATE);
	    }
	    
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {

	        final DatePickerDialog picker = new DatePickerDialog(getActivity(), 
	                getConstructorListener(), year, month, day);
	        
	        if (hasJellyBeanAndAbove()) {
	        	/*
	        	 * Restriction of Date from 1980 Jan 1st to Current Date
	        	 */
	        	Calendar c = Calendar.getInstance();
	        	picker.getDatePicker().setMaxDate(c.getTimeInMillis());
	        	c.set(Calendar.YEAR, 1980);
	        	c.set(Calendar.MONTH, c.getMinimum(Calendar.MONTH));
	        	c.set(Calendar.DATE, c.getMinimum(Calendar.DATE));
	        	c.set(Calendar.HOUR_OF_DAY, c.getMinimum(Calendar.HOUR_OF_DAY));
	            c.set(Calendar.MINUTE, c.getMinimum(Calendar.MINUTE));
	            c.set(Calendar.SECOND, c.getMinimum(Calendar.SECOND));
	            c.set(Calendar.MILLISECOND, c.getMinimum(Calendar.MILLISECOND));
	        	picker.getDatePicker().setMinDate(c.getTimeInMillis());
	            picker.setButton(DialogInterface.BUTTON_POSITIVE, 
	                    getActivity().getString(android.R.string.ok),
	                    new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                    DatePicker dp = picker.getDatePicker();
	        			cal.set(Calendar.YEAR, dp.getYear());
	        			cal.set(Calendar.MONTH, dp.getMonth());
	        			cal.set(Calendar.DATE, dp.getDayOfMonth());
	        			try {
	        				//Change both the p_existing Immunization as well as the 
	        				//selectedImmunization in the adapter for don't want to repopulate the adapter
	        				ExistingActivity.p_existing.setImmunizationDate(selectedImmunization.getName(), cal);
	        				selectedImmunization.setDate(cal);
	        				adapter.notifyDataSetChanged();
	        				} catch (IllegalArgumentException e) {
	        					e.printStackTrace();
	        				} catch (NoSuchFieldException e) {
	        					e.printStackTrace();
	        				} catch (IllegalAccessException e) {
	        					e.printStackTrace();
	        				}	
	                }
	            });
	            picker.setButton(DialogInterface.BUTTON_NEGATIVE,
	                    getActivity().getString(android.R.string.cancel),
	                    new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int which) {}
	            });
	        }
	        return picker;
	    }
	    
	    private static boolean hasJellyBeanAndAbove() {
	        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	    }
	    
	    private OnDateSetListener getConstructorListener() {
	        return hasJellyBeanAndAbove() ? null : mListener;
	    }
	}
}