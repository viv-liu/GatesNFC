package com.example.gatesnfc.New;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gatesnfc.R;

public class ParentsNameEntryFragment extends Fragment {
	@SuppressWarnings("unused")
	private static final String TAG = ParentsNameEntryFragment.class.getSimpleName();
	private static InputFilter[] nameFilter;
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static EditText et_momfirstname;
	public static EditText et_momlastname;
	public static EditText et_dadfirstname;
	public static EditText et_dadlastname;
	
	public ParentsNameEntryFragment() {
	}
	
	public static Fragment newInstance(int position) {
		ParentsNameEntryFragment myFragment = new ParentsNameEntryFragment();
		
		Bundle args = new Bundle();
		args.putInt(ParentsNameEntryFragment.ARG_SECTION_NUMBER, position + 1);
		
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
		nameFilter[1] = new InputFilter.LengthFilter(10);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_double_name_entry, container, false);
		
		LinearLayout momGroup = (LinearLayout) rootView.findViewById(R.id.momGroup);
		TextView tv_firstname = (TextView) momGroup.getChildAt(0);
		et_momfirstname = (EditText) momGroup.getChildAt(1);
		TextView tv_lastname = (TextView) momGroup.getChildAt(2);
		et_momlastname = (EditText) momGroup.getChildAt(3);
		tv_firstname.setText("Mom's First Name: ");
		tv_lastname.setText("Mom's Last Name: ");
		et_momfirstname.setFilters(nameFilter);
		et_momfirstname.setHint("Janice");
		et_momlastname.setFilters(nameFilter);
		et_momlastname.setHint("Wang");
		
		LinearLayout dadGroup = (LinearLayout) rootView.findViewById(R.id.dadGroup);
		tv_firstname = (TextView) dadGroup.getChildAt(0);
		et_dadfirstname = (EditText) dadGroup.getChildAt(1);
		tv_lastname = (TextView) dadGroup.getChildAt(2);
		et_dadlastname = (EditText) dadGroup.getChildAt(3);
		tv_firstname.setText("Dad's First Name: ");
		tv_lastname.setText("Dad's Last Name: ");
		et_dadfirstname.setFilters(nameFilter);
		et_dadlastname.setFilters(nameFilter);
		et_dadlastname.setHint("Doe Sr.");
		//int section_number = getArguments().getInt(ARG_SECTION_NUMBER);
		return rootView;
	}	
}
