package com.example.gatesnfc.New;

import com.example.gatesnfc.PrefUtils;
import com.example.gatesnfc.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddressEntryFragment extends Fragment {
	@SuppressWarnings("unused")
	private static final String TAG = AddressEntryFragment.class.getSimpleName();
	private static InputFilter[] addressFilter;
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static EditText et_number;
	public static EditText et_street;
	public static EditText et_optional;
	public static EditText et_city;
	public static EditText et_region;
	public static Button b_country;
	public static EditText et_postal;
	
	public AddressEntryFragment() {
	}
	
	public static Fragment newInstance(int position) {
		AddressEntryFragment myFragment = new AddressEntryFragment();
		
		Bundle args = new Bundle();
		args.putInt(AddressEntryFragment.ARG_SECTION_NUMBER, position + 1);
		
		myFragment.setArguments(args);
		addressFilter = new InputFilter[2];
		addressFilter[0] = new InputFilter() { 
			@Override
	        public CharSequence filter(CharSequence source, int start, int end, 
	        		Spanned dest, int dstart, int dend) { 
	        		for (int i = start; i < end; i++) { 
	        		    // No new lines allowed
	        		    if (source.charAt(i) == '\n') { 
	        		    	return ""; 
	        		    } 
	                }     	
	        		return null; 
	            }
		};
		addressFilter[1] = new InputFilter.LengthFilter(10);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_address_entry, container, false);
		et_number = (EditText) rootView.findViewById(R.id.editText_number);
		et_street = (EditText) rootView.findViewById(R.id.editText_al1);
		et_street.setFilters(addressFilter);
		et_optional = (EditText) rootView.findViewById(R.id.editText_al2);
		et_optional.setFilters(addressFilter);
		et_city = (EditText) rootView.findViewById(R.id.editText_city);
		et_city.setFilters(addressFilter);
		et_region = (EditText) rootView.findViewById(R.id.editText_region);
		et_region.setFilters(addressFilter);
		et_postal = (EditText) rootView.findViewById(R.id.editText_postal);
		et_postal.setFilters(addressFilter);
		
		// Initialize country button
		b_country = (Button) rootView.findViewById(R.id.button_country);
		b_country.setOnClickListener((OnClickListener) getActivity());
		String country = PrefUtils.getStringPreference(getActivity(), PrefUtils.COUNTRY_KEY);
		if(country != null && !country.isEmpty()) {
			b_country.setText(country);
		} 
				
		return rootView;
	}
}