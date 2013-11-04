package com.example.gatesnfc.existing;

import com.example.gatesnfc.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


import com.example.gatesnfc.existing.ExistingActivity;

public class EditNameDialog extends DialogFragment implements OnClickListener {
	private static InputFilter[] nameFilter;
	public static EditText et_firstname;
	public static EditText et_lastname;
	private String mType;
	private View rootView;
	private Button btn_confirm;
	private Button btn_cancel;
    
    public EditNameDialog() {
        // Empty constructor required for DialogFragment
    }
    
    static EditNameDialog newInstance() {
    	EditNameDialog myFragment = new EditNameDialog();
		
	    return myFragment;
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.edit_name_fragment, container, false);
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
		
		et_firstname = (EditText) rootView.findViewById(R.id.editText_firstname);
		et_firstname.setFilters(nameFilter);
		et_lastname = (EditText) rootView.findViewById(R.id.editText_lastname);
		et_lastname.setFilters(nameFilter);
		
		btn_confirm = (Button) rootView.findViewById(R.id.confirm);
		btn_cancel = (Button) rootView.findViewById(R.id.cancel);
		
		btn_confirm.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

		mType = ((ExistingActivity) getActivity()).getStatus();
		getDialog().setTitle("Change " + mType);
		
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
		if (mType == "Patient's Name"){
			ExistingActivity.p_existing.firstName = (et_firstname.getText().toString());
			ExistingActivity.p_existing.lastName = (et_lastname.getText().toString());
		}
		if (mType == "Mom's Name")
		{
			ExistingActivity.p_existing.mom_firstName = (et_firstname.getText().toString());
			ExistingActivity.p_existing.mom_lastName = (et_lastname.getText().toString());
		}
		if (mType == "Dad's Name")
		{
			ExistingActivity.p_existing.dad_firstName = (et_firstname.getText().toString());
			ExistingActivity.p_existing.dad_lastName = (et_lastname.getText().toString());
		}
		((ExistingActivity) getActivity()).updateView();
	}

}
