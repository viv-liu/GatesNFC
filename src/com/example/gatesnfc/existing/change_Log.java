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
import org.json.JSONObject;

import com.example.gatesnfc.R;
import com.immunepicker.*;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class change_Log extends Fragment implements OnClickListener,
Comparator<Immunization>{
	private static InputFilter[] nameFilter;
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static View rootView;
	private static Calendar cal;
	
	private List<String> setValuesList;
	
	/**
	 * View components
	 */
	private EditText searchEditText;
	private ListView ImmunizationListView;
	private ListView NImmunizationListView;
	private ListView DemoListView;

	/**
	 * Adapter for the listview
	 */
	private Change_Log_ImmunizationListAdapter adapter;
	private Change_Log_ImmunizationListAdapter Nadapter;

	/**
	 * Hold all Immunizations, sorted by Immunization name
	 */
	private List<Immunization> allImmunizationsList;

	/**
	 * Hold Immunizations that matched user query
	 */
	private ArrayList<Immunization> selectedImmunizationsList;
	private ArrayList<Immunization> removedImmunizationsList;


	public EditText getSearchEditText() {
		return searchEditText;
	}
	
	public static Fragment newInstance(int position) {
		change_Log myFragment = new change_Log();
		
		Bundle args = new Bundle();
		args.putInt(change_Log.ARG_SECTION_NUMBER, position);
		
		myFragment.setArguments(args);
		nameFilter = new InputFilter[1];
		nameFilter[0] = new InputFilter.LengthFilter(10);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_change_log, container, false);
		setValuesList = new ArrayList<String>();
		// load the layout elements from an xml file
		
		Button confirm_log = (Button) rootView.findViewById(R.id.change_confirm_log);
		Button reset_log = (Button) rootView.findViewById(R.id.change_reset_log);
		
//		confirm_log.setOnClickListener(this);
//	    reset_log.setOnClickListener(this);
		
		// Set listener to ExistingActivity
		confirm_log.setOnClickListener((OnClickListener) getActivity());
	    
	    ImmunizationListView = (ListView) rootView
 				.findViewById(R.id.change_immunization_picker_listview);
 		NImmunizationListView = (ListView) rootView.findViewById(R.id.change_immunization_picker_listview_N);
 		DemoListView = (ListView) rootView.findViewById(R.id.demographics_change);
 		
		// Get Immunizations from the json
 		getAllImmunizations();
 		
 		// Set adapter
 		adapter = new Change_Log_ImmunizationListAdapter(getActivity(), selectedImmunizationsList);
 		adapter.mStatus = true;
 		ImmunizationListView.setAdapter(adapter);
 		
 		Nadapter = new Change_Log_ImmunizationListAdapter(getActivity(), removedImmunizationsList);
 		Nadapter.mStatus = false;
 		NImmunizationListView.setAdapter(Nadapter);
 		
 		//TODO: How to do the demographics change_log?
// 		Dadapter = new Change_Log_DemoListAdapter(getActivity(), 0)
 		
 		
 		// Inform listener
 		ImmunizationListView.setOnItemClickListener(new OnItemClickListener() {
 			
 			@Override
 			public void onItemClick(AdapterView<?> parent, View view,
 					int position, long id) {
 					Immunization Immunization = selectedImmunizationsList.get(position);
					adapter.notifyDataSetChanged();
 					//TODO: need to show up dialog for reset
 			}
 		});
	    
		// Link layout elements to code        	
		return rootView;
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
//		switch(view.getId()) {
//		case R.id.change_confirm_log:
//			//TODO: Either put this in Existing or in this
//			//TODO: temp testing measure to updateView
//			Log.d("Confirm", "Clicked");
//			updateView();
//			break;
//		}
	}

	public void updateView(){
		removedImmunizationsList.clear();
		selectedImmunizationsList.clear();
 		// Get view components
 		ImmunizationListView = (ListView) rootView
 				.findViewById(R.id.change_immunization_picker_listview);
 		NImmunizationListView = (ListView) rootView.findViewById(R.id.change_immunization_picker_listview_N);
 		DemoListView = (ListView) rootView.findViewById(R.id.demographics_change);
 		
		// Get Immunizations from the json
 		getAllImmunizations();
 		
 		// Set adapter
 		adapter = new Change_Log_ImmunizationListAdapter(getActivity(), selectedImmunizationsList);
 		adapter.mStatus = true;
 		ImmunizationListView.setAdapter(adapter);
 		
 		Nadapter = new Change_Log_ImmunizationListAdapter(getActivity(), removedImmunizationsList);
 		Nadapter.mStatus = false;
 		NImmunizationListView.setAdapter(Nadapter);
 		
 		//TODO: How to do the demographics change_log?
// 		Dadapter = new Change_Log_DemoListAdapter(getActivity(), 0)
 		
 		
 		// Inform listener
 		ImmunizationListView.setOnItemClickListener(new OnItemClickListener() {
 			
 			@Override
 			public void onItemClick(AdapterView<?> parent, View view,
 					int position, long id) {
 					Immunization Immunization = selectedImmunizationsList.get(position);
 					//TODO: need to show up dialog for reset
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
			List<Immunization> selectedList = new ArrayList<Immunization>();
			List<Immunization> removedList = new ArrayList<Immunization>();
			// Read from local file
			String allImmunizationsString = readFileAsString(getActivity());
			JSONObject jsonObject = new JSONObject(allImmunizationsString);
			Iterator<?> keys = jsonObject.keys();
			// Add the data to all Immunizations list
			while (keys.hasNext()) {
			String key = (String) keys.next();
				if(ExistingActivity.p_existing.getImmunization(jsonObject.getString(key)) 
						!= ExistingActivity.p_reset.getImmunization(jsonObject.getString(key)))
				{
					Immunization Immunization = new Immunization();
					Immunization.setName(jsonObject.getString(key));
					//Sets the date of the immunizations
					Immunization.setDate(ExistingActivity.p_existing.getImmunizationDate(jsonObject.getString(key)));
					if(ExistingActivity.p_existing.getImmunization(jsonObject.getString(key)))
					{
						selectedList.add(Immunization);
					}
					else
					{
						removedList.add(Immunization);
					}

				}
				//else don't display
			}
			// Sort the all Immunizations list based on Immunization name
			Collections.sort(selectedList, this);
			Collections.sort(removedList, this);
			// Initialize selected Immunizations with all Immunizations
			selectedImmunizationsList = new ArrayList<Immunization>();
			selectedImmunizationsList.addAll(selectedList);
			
			removedImmunizationsList = new ArrayList<Immunization>();
			removedImmunizationsList.addAll(removedList);
			
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
	
}