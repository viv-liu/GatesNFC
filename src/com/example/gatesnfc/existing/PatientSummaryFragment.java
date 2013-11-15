package com.example.gatesnfc.existing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gatesnfc.R;

public class PatientSummaryFragment extends Fragment {
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static final String DATEFORMAT = "MMM dd, yyyy";
	
	public static Button name;
	public static Button birthdate;
	public static Button mom;
	public static Button dad;
	public static Button address;
	public static Button notes;
	public static Button code;
	
	public PatientSummaryFragment() {
	}
	
	public static Fragment newInstance(int position) {
		PatientSummaryFragment myFragment = new PatientSummaryFragment();
		
		Bundle args = new Bundle();
		args.putInt(PatientSummaryFragment.ARG_SECTION_NUMBER, position + 1);
		
		myFragment.setArguments(args);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.existing_fragment_patient_summary, container, false);	
		
		code = (Button)rootView.findViewById(R.id.button_id);
		name = (Button)rootView.findViewById(R.id.button_name);
		birthdate = (Button)rootView.findViewById(R.id.button_birthdate);
		mom = (Button)rootView.findViewById(R.id.button_mom);
		dad = (Button)rootView.findViewById(R.id.button_dad);
		address = (Button)rootView.findViewById(R.id.button_address);
		notes = (Button)rootView.findViewById(R.id.button_notes);
				
		// Set listener to ExistingActivity
		name.setOnClickListener((OnClickListener) getActivity());
		birthdate.setOnClickListener((OnClickListener) getActivity());
		mom.setOnClickListener((OnClickListener) getActivity());
		dad.setOnClickListener((OnClickListener) getActivity());
		address.setOnClickListener((OnClickListener) getActivity());
		notes.setOnClickListener((OnClickListener) getActivity());
		
		// Set text for all buttons
		updateButtonTexts();
		
		return rootView;
	}	
	
	public static void updateButtonTexts() {
		code.setText(ExistingActivity.p_existing.getCode());
		name.setText(ExistingActivity.p_existing.firstName + " " + ExistingActivity.p_existing.lastName);
		birthdate.setText(DateFormat.format(DATEFORMAT, ExistingActivity.p_existing.birthday).toString());
		mom.setText(ExistingActivity.p_existing.mom_firstName + " " + ExistingActivity.p_existing.mom_lastName);
		dad.setText(ExistingActivity.p_existing.dad_firstName + " " + ExistingActivity.p_existing.dad_lastName);
		address.setText(ExistingActivity.p_existing.getAddressString());
		notes.setText(ExistingActivity.p_existing.getNotes());
	}
	
}

