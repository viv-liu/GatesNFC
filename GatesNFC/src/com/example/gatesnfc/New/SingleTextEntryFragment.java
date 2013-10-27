package com.example.gatesnfc.New;

import com.example.gatesnfc.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SingleTextEntryFragment extends Fragment {
	@SuppressWarnings("unused")
	private static final String TAG = SingleTextEntryFragment.class.getSimpleName();
	private static InputFilter[] nameFilter;
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	public SingleTextEntryFragment() {
	}
	
	public static Fragment newInstance(int position) {
		SingleTextEntryFragment myFragment = new SingleTextEntryFragment();
		
		Bundle args = new Bundle();
		args.putInt(SingleTextEntryFragment.ARG_SECTION_NUMBER, position + 1);
		
		myFragment.setArguments(args);
		nameFilter = new InputFilter[1];
		nameFilter[0] = new InputFilter.LengthFilter(10);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_single_name_entry, container, false);
		EditText et_firstname = (EditText) rootView.findViewById(R.id.editText_firstname);
		et_firstname.setFilters(nameFilter);
		EditText et_lastname = (EditText) rootView.findViewById(R.id.editText_lastname);
		et_lastname.setFilters(nameFilter);
		int section_number = getArguments().getInt(ARG_SECTION_NUMBER);
		
		switch (section_number) {
		case 1: 
			// Vivian trying to remember how to use switch and case, plz don't remove
			break;
		case 3: 
			break;
		}		
		return rootView;
	}	
}
