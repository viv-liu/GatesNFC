package com.gatesnfc.newpatient;

import com.gatesnfc.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
		rootView = inflater.inflate(R.layout.edit_name_dialog, container, false);
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
		nameFilter[1] = new InputFilter.LengthFilter(25);
		
		et_firstname = (EditText) rootView.findViewById(R.id.editText_firstname);
		et_firstname.setFilters(nameFilter);
		et_lastname = (EditText) rootView.findViewById(R.id.editText_lastname);
		et_lastname.setFilters(nameFilter);
		
		btn_confirm = (Button) rootView.findViewById(R.id.confirm);
		btn_cancel = (Button) rootView.findViewById(R.id.cancel);
		
		btn_confirm.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

		mType = ((NewActivity) getActivity()).getStatus();
		getDialog().setTitle(mType);	
		setText();
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
	private void setText() {
		String firstToSet = "";
		String lastToSet = "";
		if (mType == "Patient's Name"){
			String firstName = NewActivity.p_new.firstName;
			String lastName = NewActivity.p_new.lastName;
			if(firstName != null && !firstName.isEmpty()) {
				firstToSet = firstName;
			} 
			if(lastName != null && !lastName.isEmpty()) {
				lastToSet = lastName;
			}
		}
		if (mType == "Mom's Name"){
			String mfn = NewActivity.p_new.mom_firstName;
			String mln = NewActivity.p_new.mom_lastName;
			if(mfn!= null && !mfn.isEmpty()) {
				firstToSet = mfn;
			} 
			if(mln != null && !mln.isEmpty()) {
				lastToSet = mln;
			}
		}
		if (mType == "Dad's Name")
		{
			String dfn = NewActivity.p_new.dad_firstName;
			String dln = NewActivity.p_new.dad_lastName;
			if(dfn!= null && !dfn.isEmpty()) {
				firstToSet = dfn;
			} 
			if(dln != null && !dln.isEmpty()) {
				lastToSet = dln;
			}
		}
		et_firstname.setText(firstToSet);
		et_lastname.setText(lastToSet);
		NewActivity.updateAllViews();
	}
		
	private void changeText()
	{
		if (mType == "Patient's Name"){
			NewActivity.p_new.firstName = (et_firstname.getText().toString());
			NewActivity.p_new.lastName = (et_lastname.getText().toString());
		}
		if (mType == "Mom's Name")
		{
			NewActivity.p_new.mom_firstName = (et_firstname.getText().toString());
			NewActivity.p_new.mom_lastName = (et_lastname.getText().toString());
		}
		if (mType == "Dad's Name")
		{
			NewActivity.p_new.dad_firstName = (et_firstname.getText().toString());
			NewActivity.p_new.dad_lastName = (et_lastname.getText().toString());
		}
		NewActivity.updateAllViews();
	}

}
