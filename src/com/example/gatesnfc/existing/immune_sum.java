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
import com.remove_immunepicker.*;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
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


public class immune_sum extends Fragment implements OnClickListener,
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
		immune_sum myFragment = new immune_sum();
		
		Bundle args = new Bundle();
		args.putInt(immune_sum.ARG_SECTION_NUMBER, position);
		
		myFragment.setArguments(args);
		nameFilter = new InputFilter[1];
		nameFilter[0] = new InputFilter.LengthFilter(10);

	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_immunization_sum, container, false);
		setValuesList = new ArrayList<String>();
		// load the layout elements from an xml file
//		FragmentManager fm = getActivity().getSupportFragmentManager();
		
		Button immunization_button = (Button) rootView.findViewById(R.id.add_immunization);
		Button list_immune_button = (Button) rootView.findViewById(R.id.remove_immunization);
		
		list_immune_button.setOnClickListener(this);
	    immunization_button.setOnClickListener(this);
	    
	    updateView();
	    
		// Link layout elements to code        	
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
						ExistingActivity.p_existing.setImmunization(setValuesList.get(i));
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
	    else if (requestCode == 2)
	    {
	    	//TODO: Get date Picker Dialog
	    }
	    updateView(); //Always updateView
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
	 * All below for List Adapter
	 */
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
				if(ExistingActivity.p_existing.getImmunization(jsonObject.getString(key)))
				{
					//If its true then set as below
					Immunization Immunization = new Immunization();
					Immunization.setName(jsonObject.getString(key));
					Immunization.setDate(ExistingActivity.p_existing.getImmunizationDate(jsonObject.getString(key)));
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

	/**
	 * Convenient function to read from raw file
	 * 
	 * @param context
	 * @return
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
	
	public static void updatingView()
	{
		adapter.notifyDataSetChanged();
	}
	
	public void showDatePickerDialog() {
		cal = Calendar.getInstance();
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getActivity().getFragmentManager(), "datePicker");
	}
	
	public void onDatePicked(DialogFragment dialog) {
		//TODO: Update the View once clicked... it's proving difficult
			updateView();
    }
	
	public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

	    public interface DatePickerDialogListener {
	        public void onDatePicked(DialogFragment dialog);
	    }
	    
	 // Use this instance of the interface to deliver action events
	    DatePickerDialogListener mListener;
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the date in the cal object
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH); 
			int day = cal.get(Calendar.DATE);
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		public void onDateSet(DatePicker view, int year, int month, int day) {
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DATE, day);
			try {
				ExistingActivity.p_existing.setImmunizationDate(selectedImmunization.getName(), cal);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}	
			updatingView();
		}	
	}
}