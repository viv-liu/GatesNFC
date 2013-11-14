package com.example.gatesnfc.New;

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
import com.example.gatesnfc.existing.DatePicker_Fix;
import com.immunepicker.*;
import com.remove_immunepicker.*;

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
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class VaccineEntryFragment extends Fragment implements OnClickListener,
Comparator<Immunization>{
	private static InputFilter[] nameFilter;
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static View rootView;
	private static Calendar cal;
	
	private List<String> setValuesList;
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
	 * Hold all Immunizations, sorted by Immunization name
	 */
	private List<Immunization> allImmunizationsList;

	/**
	 * Hold Immunizations that matched user query
	 */
	private List<Immunization> selectedImmunizationsList;

	public EditText getSearchEditText() {
		return searchEditText;
	}
	
	public static Fragment newInstance(int position) {
		VaccineEntryFragment myFragment = new VaccineEntryFragment();
		
		Bundle args = new Bundle();
		args.putInt(VaccineEntryFragment.ARG_SECTION_NUMBER, position + 1);
		
		myFragment.setArguments(args);
		nameFilter = new InputFilter[1];
		nameFilter[0] = new InputFilter.LengthFilter(10);

	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_immunization_sum, container, false);
		setValuesList = new ArrayList<String>();
		
		Button immunization_button = (Button) rootView.findViewById(R.id.add_immunization);
		Button list_immune_button = (Button) rootView.findViewById(R.id.remove_immunization);
		
		list_immune_button.setOnClickListener(this);
	    immunization_button.setOnClickListener(this);
	    
	    updateView();
	    
		return rootView;
	}
	
	/**
	 * Search allImmunizationsList contains text and put result into
	 * selectedImmunizationsList
	 * 
	 * @param text
	 */
	@SuppressLint("DefaultLocale")
	private void search(String text) {
		selectedImmunizationsList.clear();

		for (Immunization Immunization : allImmunizationsList) {
			if (Immunization.getName().toLowerCase(Locale.ENGLISH)
					.contains(text.toLowerCase())) {
				selectedImmunizationsList.add(Immunization);
			}
		}

		adapter.notifyDataSetChanged();
	}

	/**
	 * Support sorting the Immunizations list
	 */
	@Override
	public int compare(Immunization lhs, Immunization rhs) {
		return lhs.getName().compareTo(rhs.getName());
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
		case R.id.remove_immunization:
			remove_immunization();
		break;
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 1) //make sure fragment codes match up
	    {
	    int i;
	       if (data.getStringExtra("Status") == "T")
	       {
	    	   for (i = 0; i < setValuesList.size(); i++)
	    	   {
	    		   try {
						//Set the selected Immunization to True, by default the date is set to today
	    			    //User can change date once it appears in list
						NewActivity.patient.setImmunization(setValuesList.get(i));
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
	
	
	private void remove_immunization() {
				
				Curr_ImmunizationPicker picker = new Curr_ImmunizationPicker();
				picker.setTargetFragment(this, 1);
				setValuesList.clear();
				
				picker.setListener(new Curr_ImmunizationPickerListener() {
					@Override
					public void onSelectImmunization(String name) {
						setValuesList.add(name);
					}
				});
				
				picker.show(getFragmentManager(), "Curr_Immunization_PICKER");
	}
	
	private void add_immunization() {
		
		Add_ImmunizationPicker picker = new Add_ImmunizationPicker();
		picker.setTargetFragment(this, 1);
		setValuesList.clear();
		
		picker.setListener(new Add_ImmunizationPickerListener() {
			@Override
			public void onSelectImmunization(String name) {
				setValuesList.add(name);
			}
		});
		
		picker.show(getFragmentManager(), "Immunization_PICKER");
	}
	
	/**Function that repopulates the view
	 * 
	 * 
	 * 
	 */

	public void updateView(){
		
 		// Get view components
 		searchEditText = (EditText) rootView
 				.findViewById(R.id.immunization_picker_search);
 		ImmunizationListView = (ListView) rootView
 				.findViewById(R.id.immunization_picker_listview);
 	
		// Get Immunizations from the json
 		allImmunizationsList = getAllImmunizations();
 		
 		// Set adapter
 		adapter = new ImmunizationListAdapter(getActivity(), selectedImmunizationsList);
 		ImmunizationListView.setAdapter(adapter);

 		// Inform listener
 		ImmunizationListView.setOnItemClickListener(new OnItemClickListener() {

 			@Override
 			public void onItemClick(AdapterView<?> parent, View view,
 					int position, long id) {
 					selectedImmunization = selectedImmunizationsList.get(position);
 					showDatePickerDialog();
 					adapter.notifyDataSetChanged();
 			}
 		});

 		// Search for which Immunizations matched user query
 		searchEditText.addTextChangedListener(new TextWatcher() {

 			@Override
 			public void onTextChanged(CharSequence s, int start, int before,
 					int count) {
 			}

 			@Override
 			public void beforeTextChanged(CharSequence s, int start, int count,
 					int after) {
 			}

 			@Override
 			public void afterTextChanged(Editable s) {
 				search(s.toString());
 			}
 		});
 		
	}
	

	/**
	 * Get all Immunizations with code and name from res/raw/Immunizations.json
	 * 
	 * @return
	 */
	private List<Immunization> getAllImmunizations() {
		try {
			List<Immunization> theList = new ArrayList<Immunization>();

			// Read from local file
			String allImmunizationsString = readFileAsString(getActivity());
			JSONObject jsonObject = new JSONObject(allImmunizationsString);
			Iterator<?> keys = jsonObject.keys();
			// Add the data to all Immunizations list
			while (keys.hasNext()) {
			String key = (String) keys.next();
				if(NewActivity.patient.getImmunization(jsonObject.getString(key)))
				{
					//If the patient has the immunization then set as below
					Immunization Immunization = new Immunization();
					Immunization.setName(jsonObject.getString(key));
					Immunization.setDate(NewActivity.patient.getImmunizationDate(jsonObject.getString(key)));
					theList.add(Immunization);
				}
				//else don't display
			}
			// Sort the all Immunizations list based on Immunization name
			Collections.sort(theList, this);
			// Initialize selected Immunizations with all Immunizations
			selectedImmunizationsList = new ArrayList<Immunization>();
			selectedImmunizationsList.addAll(theList);
			// Return
			return theList;

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
	
	
	/**DatePickerDialogFragment
	 * 
	 * Functions that displays the Date on Click of Adapter item and edits that adapter's
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
	        				NewActivity.patient.setImmunizationDate(selectedImmunization.getName(), cal);
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