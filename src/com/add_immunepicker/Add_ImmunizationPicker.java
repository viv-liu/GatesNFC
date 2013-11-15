package com.add_immunepicker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.gatesnfc.R;
import com.example.gatesnfc.New.NewActivity;
import com.example.gatesnfc.existing.*;

public class Add_ImmunizationPicker extends DialogFragment implements
		Comparator<Add_Immunization>, OnClickListener {
	
	private static boolean isNew;
	/**
	 * Private Status Value
	 */
	private String mStatus;
	/**
		 * View components
	 */
	private EditText searchEditText;
	private ListView ImmunizationListView;
	private Button mConfirm;
	private Button mCancel;
	private View mView;

	/**
	 * Adapter for the listview
	 */
	private Add_ImmunizationListAdapter adapter;

	/**
	 * Hold all Immunizations, sorted by Immunization name
	 */
	private List<Add_Immunization> allImmunizationsList;

	/**
	 * Hold Immunizations that matched user query
	 */
	private List<Add_Immunization> selectedImmunizationsList;

	/**
	 * Listener to which Immunization user selected
	 */
	private Add_ImmunizationPickerListener listener;
	private CharSequence dialogTitle;

	/**
	 * Set listener
	 * 
	 * @param listener
	 */

	public void setListener(Add_ImmunizationPickerListener listener) {
		this.listener = listener;
	}

	
	public EditText getSearchEditText() {
		return searchEditText;
	}

	/**
	 * Get all Immunizations with code and name from res/raw/Immunizations.json
	 * 
	 * @return
	 */
	private List<Add_Immunization> getAllImmunizations() {
		if (allImmunizationsList == null) {
			try {
				allImmunizationsList = new ArrayList<Add_Immunization>();

				// Read from local file
				String allImmunizationsString = readFileAsString(getActivity());
				JSONObject jsonObject = new JSONObject(allImmunizationsString);
				Iterator<?> keys = jsonObject.keys();

				// Add the data to all Immunizations list
				while (keys.hasNext()) {
					String key = (String) keys.next();
					if(isNew)
					{
						if(!NewActivity.patient.getImmunization(jsonObject.getString(key)))
						{
							Add_Immunization Immunization = new Add_Immunization();
							Immunization.setName(jsonObject.getString(key));
							allImmunizationsList.add(Immunization);
						}
					}
					if(!isNew)
					{
						if(!ExistingActivity.p_existing.getImmunization(jsonObject.getString(key)))
						{
							Add_Immunization Immunization = new Add_Immunization();
							Immunization.setName(jsonObject.getString(key));
							allImmunizationsList.add(Immunization);
						}
					}
				}

				// Sort the all Immunizations list based on Immunization name
				Collections.sort(allImmunizationsList, this);

				// Initialize selected Immunizations with all Immunizations
				selectedImmunizationsList = new ArrayList<Add_Immunization>();
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
	 * To support show as dialog
	 * 
	 * @param where it is from
	 * @return
	 */
	public static Add_ImmunizationPicker newInstance(String isFrom) {
		Add_ImmunizationPicker picker = new Add_ImmunizationPicker();
		isNew = isFrom.equals("New");
		return picker;
	}
	
	/**
	 * Create view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Set the status to false
		mStatus = "F";
		// Inflate view
		mView = inflater.inflate(R.layout.immunization_picker, null);

		// Get Immunizations from the json
		getAllImmunizations();

		// Set dialog title if show as dialog
		dialogTitle = "Add Immunization";
		getDialog().setTitle(dialogTitle);

		// Get view components
		searchEditText = (EditText) mView
				.findViewById(R.id.immunization_picker_search);
		ImmunizationListView = (ListView) mView
				.findViewById(R.id.immunization_picker_listview);
		
		mConfirm = (Button) mView
				.findViewById(R.id.confirm_picker);
		
		mCancel = (Button) mView
				.findViewById(R.id.cancel_picker);
		
		mConfirm.setOnClickListener((android.view.View.OnClickListener) this);
	    mCancel.setOnClickListener((android.view.View.OnClickListener) this);

		// Set adapter
		adapter = new Add_ImmunizationListAdapter(getActivity(), selectedImmunizationsList);
		ImmunizationListView.setAdapter(adapter);

		// Inform listener
		ImmunizationListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (listener != null) {
					Add_Immunization Immunization = selectedImmunizationsList.get(position);
					listener.onSelectImmunization(Immunization.getName());
					Immunization.setTF();
					adapter.notifyDataSetChanged();
				}
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
		return mView;
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

		for (Add_Immunization Immunization : allImmunizationsList) {
			if (Immunization.getName().toLowerCase(Locale.ENGLISH)
					.contains(text.toLowerCase())) {
				selectedImmunizationsList.add(Immunization);
			}
		}

		adapter.notifyDataSetChanged();
	}
	
	@Override
	public int compare(Add_Immunization lhs, Add_Immunization rhs) {
		return lhs.getName().compareTo(rhs.getName());
	}
	
	private void sendResult(int INT_CODE) {
	    Intent i = new Intent();
	    i.putExtra("Status", mStatus);
	    getTargetFragment().onActivityResult(getTargetRequestCode(), INT_CODE, i);
	}

	@Override
	public void onClick(View view) {
		
		switch(view.getId()) {
		case R.id.confirm_picker:
			mStatus = "Add";
			sendResult(1);
			this.dismiss();
			break;
		case R.id.cancel_picker:
			this.dismiss();
		break;
		}
	}

}
