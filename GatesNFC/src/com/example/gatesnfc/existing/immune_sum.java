package com.example.gatesnfc.existing;


import com.example.gatesnfc.R;
import com.immunepicker.*;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class immune_sum extends Fragment implements OnClickListener{
	private static InputFilter[] nameFilter;
	public static final String ARG_SECTION_NUMBER = "section_number";
	private View rootView;
	
	private TextView tV;
	
	public static Fragment newInstance(int position) {
		immune_sum myFragment = new immune_sum();
		
		Bundle args = new Bundle();
		args.putInt(immune_sum.ARG_SECTION_NUMBER, position);
		
		myFragment.setArguments(args);
		nameFilter = new InputFilter[1];
		nameFilter[0] = new InputFilter.LengthFilter(10);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_immunization_sum, container, false);
		
		// load the layout elements from an xml file
	    
		//Initialize the Views
		tV = (TextView) rootView.findViewById(R.id.textView_name);
		Button immunization_button = (Button) rootView.findViewById(R.id.add_immunization);
		Button list_immune_button = (Button) rootView.findViewById(R.id.list_immunization);
		
		updateView();
		
		list_immune_button.setOnClickListener(this);
	    immunization_button.setOnClickListener(this);
	    
		// Link layout elements to code        	
		return rootView;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()) {
		case R.id.textView_name:
			updateView();
			break;
		case R.id.add_immunization:
			add_immunization();
		break;
		case R.id.list_immunization:
			//Test of how to get the values of Immunizations iff they are true
			Curr_ImmunizationPicker picker = new Curr_ImmunizationPicker();
			picker.show(getFragmentManager(), "Curr_Immunization_PICKER");
		break;
		}
	}	
	
	private void add_immunization() {
		//TODO: finish this
		//ATM only sets true/false whenever clicked.
		
		ImmunizationPicker picker = new ImmunizationPicker();
		picker.setListener(new ImmunizationPickerListener() {

			@Override
			public void onSelectImmunization(String name, String code) {
				try {
					//Set the selected Immunization to True
					ExistingActivity.p_existing.setImmunization(code);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//To update the View After a change
				updateView();
			}
		});
		
		picker.show(getFragmentManager(), "Immunization_PICKER");

	}

	private void updateView(){
		try {
			if (ExistingActivity.p_existing.getImmunization("DTP1"))
			{
				tV.setText("true");
			}
			else
			{
				tV.setText("false");
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}