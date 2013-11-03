package com.example.gatesnfc.New;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gatesnfc.Patient;
import com.example.gatesnfc.R;

public class PatientSummaryFragment extends Fragment {
	@SuppressWarnings("unused")
	private static final String TAG = PatientSummaryFragment.class.getSimpleName();
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	public PatientSummaryFragment() {
	}
	
	public static Fragment newInstance(int position, Patient patient) {
		PatientSummaryFragment myFragment = new PatientSummaryFragment();
		
		Bundle args = new Bundle();
		args.putInt(PatientSummaryFragment.ARG_SECTION_NUMBER, position + 1);
		
		myFragment.setArguments(args);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_patient_summary, container, false);	
		Log.d("patient's first name", NewActivity.patient.firstName);
		
		Button name = (Button)rootView.findViewById(R.id.button_name);
		Button birthdate = (Button)rootView.findViewById(R.id.button_birthdate);
		Button mom = (Button)rootView.findViewById(R.id.button_mom);
		Button dad = (Button)rootView.findViewById(R.id.button_dad);
		Button address = (Button)rootView.findViewById(R.id.button_address);
		Button notes = (Button)rootView.findViewById(R.id.button_notes);
		Button complete = (Button) rootView.findViewById(R.id.button_complete);
				
		// Set listener to NewActivity
		name.setOnClickListener((OnClickListener) getActivity());
		birthdate.setOnClickListener((OnClickListener) getActivity());
		mom.setOnClickListener((OnClickListener) getActivity());
		dad.setOnClickListener((OnClickListener) getActivity());
		address.setOnClickListener((OnClickListener) getActivity());
		notes.setOnClickListener((OnClickListener) getActivity());
		complete.setOnClickListener((OnClickListener) getActivity());
		
		// Set text for all buttons
		name.setText(NewActivity.patient.firstName + " " + NewActivity.patient.lastName);
		birthdate.setText(DateFormat.format(DateEntryFragment.DATEFORMAT, NewActivity.patient.birthday).toString());
		mom.setText(NewActivity.patient.mom_firstName + " " + NewActivity.patient.mom_lastName);
		dad.setText(NewActivity.patient.dad_firstName + " " + NewActivity.patient.dad_lastName);
		address.setText(NewActivity.patient.getAddressString());
		notes.setText(NewActivity.patient.notes);
		
		return rootView;
	}	
	
}

