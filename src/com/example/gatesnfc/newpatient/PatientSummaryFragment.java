package com.example.gatesnfc.newpatient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
		TextView tv_code = (TextView)rootView.findViewById(R.id.tv_id);
		tv_code.setVisibility(View.GONE);
		code = (Button)rootView.findViewById(R.id.button_id);
		code.setVisibility(View.GONE);
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
		code.setText(NewActivity.p_new.getCode());
		name.setText(NewActivity.p_new.firstName + " " + NewActivity.p_new.lastName);
		birthdate.setText(DateFormat.format(DATEFORMAT, NewActivity.p_new.birthday).toString());
		mom.setText(NewActivity.p_new.mom_firstName + " " + NewActivity.p_new.mom_lastName);
		dad.setText(NewActivity.p_new.dad_firstName + " " + NewActivity.p_new.dad_lastName);
		address.setText(NewActivity.p_new.getAddressString());
		notes.setText(NewActivity.p_new.getNotes());
	}
	
}

