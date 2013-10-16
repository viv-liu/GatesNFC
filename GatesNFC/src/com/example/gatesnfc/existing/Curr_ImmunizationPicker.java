package com.example.gatesnfc.existing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.example.gatesnfc.R;
import com.example.gatesnfc.existing.*;

public class Curr_ImmunizationPicker extends DialogFragment implements
		Comparator<Curr_Immunization> {
	/**
	 * View components
	 */
	private EditText searchEditText;
	private ListView ImmunizationListView;

	/**
	 * Adapter for the listview
	 */
	private ImmunizationListAdapter adapter;

	/**
	 * Hold all Immunizations, sorted by Immunization name
	 */
	private List<Curr_Immunization> allImmunizationsList;

	/**
	 * Hold Immunizations that matched user query
	 */
	private List<Curr_Immunization> selectedImmunizationsList;

	/**
	 * Set listener
	 * 
	 * @param listener
	 */

	public EditText getSearchEditText() {
		return searchEditText;
	}

	/**
	 * Convenient function to get currency code from Immunization code currency code
	 * is in English locale
	 * 
	 * @param ImmunizationCode
	 * @return
	 */

	/**
	 * Get all Immunizations with code and name from res/raw/Immunizations.json
	 * 
	 * @return
	 */
	private List<Curr_Immunization> getAllImmunizations() {
		if (allImmunizationsList == null) {
			try {
				allImmunizationsList = new ArrayList<Curr_Immunization>();

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
						Curr_Immunization Immunization = new Curr_Immunization();
						Immunization.setName(jsonObject.getString(key));
						Immunization.setTF("True");
						allImmunizationsList.add(Immunization);
					}
					//else don't display

				}

				// Sort the all Immunizations list based on Immunization name
				Collections.sort(allImmunizationsList, this);

				// Initialize selected Immunizations with all Immunizations
				selectedImmunizationsList = new ArrayList<Curr_Immunization>();
				selectedImmunizationsList.addAll(allImmunizationsList);

				// Return
				return allImmunizationsList;

			} catch (Exception e) {
				e.printStackTrace();
			}
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
		//TODO: change to other records as well && create new JSON in raw/
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

	/**
	 * Create view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate view
		View view = inflater.inflate(R.layout.immunization_picker, null);

		// Get Immunizations from the json
		getAllImmunizations();

		// Set dialog title if show as dialog
		Bundle args = getArguments();
		if (args != null) {
			String dialogTitle = args.getString("dialogTitle");
			getDialog().setTitle(dialogTitle);

			int width = getResources().getDimensionPixelSize(
					R.dimen.cp_dialog_width);
			int height = getResources().getDimensionPixelSize(
					R.dimen.cp_dialog_height);
			getDialog().getWindow().setLayout(width, height);
		}

		// Get view components
		searchEditText = (EditText) view
				.findViewById(R.id.immunization_picker_search);
		ImmunizationListView = (ListView) view
				.findViewById(R.id.immunization_picker_listview);

		// Set adapter
		adapter = new ImmunizationListAdapter(getActivity(), selectedImmunizationsList);
		ImmunizationListView.setAdapter(adapter);


		return view;
	}
	
	@Override
	public int compare(Curr_Immunization lhs, Curr_Immunization rhs) {
		return lhs.getName().compareTo(rhs.getName());
	}

}
