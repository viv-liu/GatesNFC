package com.example.gatesnfc.New;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.gatesnfc.R;

public class NameEntryFragment extends Fragment {
	private static InputFilter[] nameFilter;
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static EditText et_firstname;
	public static EditText et_lastname;
	
	public NameEntryFragment() {
	}
	
	public static Fragment newInstance(int position) {
		NameEntryFragment myFragment = new NameEntryFragment();
		
		Bundle args = new Bundle();
		args.putInt(NameEntryFragment.ARG_SECTION_NUMBER, position + 1);
		
		myFragment.setArguments(args);
		nameFilter = new InputFilter[2];
		nameFilter[0] = new InputFilter() { 
			@Override
	        public CharSequence filter(CharSequence source, int start, int end, 
	        		Spanned dest, int dstart, int dend) { 
	        		for (int i = start; i < end; i++) { 
	        		    // Only allow letters
	        		    if (!Character.isLetter(source.charAt(i))) { 
	        		    	return ""; 
	        		    } 
	                }     	
	        		return null; 
	            }
		};
		//TODO: Change Length Filter Length
		nameFilter[1] = new InputFilter.LengthFilter(10);
	    return myFragment;
	}
		
	public void onPause() {
		super.onPause();
		Log.d("NameEntry", "Pausing");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_single_name_entry, container, false);
		et_firstname = (EditText) rootView.findViewById(R.id.editText_firstname);
		et_firstname.setFilters(nameFilter);
		et_lastname = (EditText) rootView.findViewById(R.id.editText_lastname);
		et_lastname.setFilters(nameFilter);
		//int section_number = getArguments().getInt(ARG_SECTION_NUMBER);
		
		return rootView;
	}	
}

