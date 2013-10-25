package com.example.gatesnfc.New;

import com.example.gatesnfc.PrefUtils;
import com.example.gatesnfc.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddressEntryFragment extends Fragment {
	@SuppressWarnings("unused")
	private static final String TAG = AddressEntryFragment.class.getSimpleName();
	private static InputFilter[] nameFilter;
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static EditText et_number;
	public static EditText et_street;
	public static EditText et_optional;
	public static EditText et_region;
	public static String country;
	public static EditText et_postal;
	
	public AddressEntryFragment() {
	}
	
	public static Fragment newInstance(int position) {
		AddressEntryFragment myFragment = new AddressEntryFragment();
		
		Bundle args = new Bundle();
		args.putInt(AddressEntryFragment.ARG_SECTION_NUMBER, position + 1);
		
		myFragment.setArguments(args);
		nameFilter = new InputFilter[1];
		nameFilter[0] = new InputFilter.LengthFilter(10);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_address_entry, container, false);
		et_number = (EditText) rootView.findViewById(R.id.editText_number);
		et_street = (EditText) rootView.findViewById(R.id.editText_al1);
		et_optional = (EditText) rootView.findViewById(R.id.editText_al2);
		et_region = (EditText) rootView.findViewById(R.id.editText_region);
		et_postal = (EditText) rootView.findViewById(R.id.editText_postal);
		
		// Initialize country button
		Button b_country = (Button) rootView.findViewById(R.id.button_country);
		b_country.setOnClickListener((OnClickListener) getActivity());
		country = PrefUtils.getStringPreference(getActivity(), PrefUtils.COUNTRY_KEY);
		if(country != null && !country.isEmpty()) {
			b_country.setText(PrefUtils.getStringPreference(getActivity(), PrefUtils.COUNTRY_KEY));
		} 
		//int section_number = getArguments().getInt(ARG_SECTION_NUMBER);
				
		return rootView;
	}
}