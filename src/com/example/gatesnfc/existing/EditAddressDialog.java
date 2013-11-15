package com.example.gatesnfc.existing;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.gatesnfc.PrefUtils;
import com.example.gatesnfc.R;

public class EditAddressDialog extends DialogFragment implements OnClickListener {
	private static InputFilter[] addressFilter;
	public static EditText et_number;
	public static EditText et_street;
	public static EditText et_optional;
	public static EditText et_city;
	public static EditText et_region;
	public static Button b_country;
	public static EditText et_postal;
	private View rootView;
	private Button btn_confirm;
	private Button btn_cancel;
    
    public EditAddressDialog() {
        // Empty constructor required for DialogFragment
    }
    
    static EditAddressDialog newInstance() {
    	EditAddressDialog myFragment = new EditAddressDialog();
	    return myFragment;
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.address_entry_dialog, container, false);
		//TODO: How to Inflate but with -10dip so it doesnt look weird as it does now
		
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
		
		//TODO: Decide whether or not this is useful?
		//Pre-sets the Values for Easier Changing of one value?
		et_number.setText(ExistingActivity.p_existing.number);
		et_street.setText(ExistingActivity.p_existing.street);
		et_optional.setText(ExistingActivity.p_existing.optional);
		et_city.setText(ExistingActivity.p_existing.city);
		et_region.setText(ExistingActivity.p_existing.region);
		et_postal.setText(ExistingActivity.p_existing.postal);
		
		
		btn_confirm = (Button) rootView.findViewById(R.id.confirm);
		btn_cancel = (Button) rootView.findViewById(R.id.cancel);
		
		btn_confirm.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		
		// Initialize country button
				b_country = (Button) rootView.findViewById(R.id.button_country);
				b_country.setOnClickListener((OnClickListener) getActivity());
				String country = PrefUtils.getStringPreference(getActivity(), PrefUtils.COUNTRY_KEY);
				if(country != null && !country.isEmpty()) {
					b_country.setText(country);
				} 

		getDialog().setTitle("Change Address");
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case (R.id.confirm):
			changeText();
			this.dismiss();
			break;
		case (R.id.cancel):
			this.dismiss();
			break;
		}
		
	}	
	
	private void changeText()
	{
		ExistingActivity.p_existing.number = et_number.getText().toString();
		ExistingActivity.p_existing.street = et_street.getText().toString();
		ExistingActivity.p_existing.optional = et_optional.getText().toString();
		ExistingActivity.p_existing.city = et_city.getText().toString();
		ExistingActivity.p_existing.region = et_region.getText().toString();
		ExistingActivity.p_existing.country = b_country.getText().toString();
		ExistingActivity.p_existing.postal = et_postal.getText().toString();
		((ExistingActivity) getActivity()).updateView();
	}
}
