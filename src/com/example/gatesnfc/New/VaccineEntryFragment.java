package com.example.gatesnfc.New;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.gatesnfc.R;

public class VaccineEntryFragment extends Fragment {
	@SuppressWarnings("unused")
	private static final String TAG = VaccineEntryFragment.class.getSimpleName();
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	public VaccineEntryFragment() {
	}
	
	public static Fragment newInstance(int position) {
		VaccineEntryFragment myFragment = new VaccineEntryFragment();
		
		Bundle args = new Bundle();
		args.putInt(VaccineEntryFragment.ARG_SECTION_NUMBER, position + 1);
		
		myFragment.setArguments(args);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_vaccine_entry, container, false);
		EditText et_firstname = (EditText) rootView.findViewById(R.id.editText_firstname);
		EditText et_lastname = (EditText) rootView.findViewById(R.id.editText_lastname);
		et_firstname.setText("Where are the vaccines?");
		et_lastname.setText("TODO: Shen's stuff");
		
		//int section_number = getArguments().getInt(ARG_SECTION_NUMBER);
		return rootView;
	}	
}