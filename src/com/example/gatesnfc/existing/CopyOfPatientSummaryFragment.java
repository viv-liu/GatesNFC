package com.example.gatesnfc.existing;

import com.example.gatesnfc.R;
import com.example.gatesnfc.New.DateEntryFragment;

import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

public class CopyOfPatientSummaryFragment extends Fragment implements OnClickListener{

	private static InputFilter[] nameFilter;
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	private View rootView;
	
	private static Button nameView;
	private static Button momNameView;
	private static Button dadNameView;
	private static Button addressView;
	private static Button uniqueIdView;
	private static Button mDateView;

	private String mStatus;
	
	private static Calendar cal;
	
	public CopyOfPatientSummaryFragment() {
	}
	
	public static Fragment newInstance(int position) {
		CopyOfPatientSummaryFragment myFragment = new CopyOfPatientSummaryFragment();
		
		Bundle args = new Bundle();
		args.putInt(CopyOfPatientSummaryFragment.ARG_SECTION_NUMBER, position + 1);
		
		myFragment.setArguments(args);
		nameFilter = new InputFilter[1];
		nameFilter[0] = new InputFilter.LengthFilter(10);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// load the layout elements from an xml file
		rootView = inflater.inflate(R.layout.existing_patient_summary, container, false);
		
		//Set Views by patientName created in Existing Activity
		nameView = (Button) rootView.findViewById(R.id.name);
		momNameView = (Button) rootView.findViewById(R.id.mom_name);
		dadNameView = (Button) rootView.findViewById(R.id.dad_name);
		addressView = (Button) rootView.findViewById(R.id.address);
		uniqueIdView = (Button) rootView.findViewById(R.id.unique_id);
		mDateView = (Button) rootView.findViewById(R.id.mDate);
		
		nameView.setOnClickListener(this);
		momNameView.setOnClickListener(this);
		dadNameView.setOnClickListener(this);
		addressView.setOnClickListener(this);
		uniqueIdView.setOnClickListener(this);
		mDateView.setOnClickListener(this);
		
		updateView();

		// Link layout elements to code        	
		return rootView;
	}

	@Override
	public void onClick(View view) {
		// TODO DATE PICKER
		switch(view.getId()) {
		case R.id.name:
			mStatus = "Name";
			showDialog();
			break;
		case R.id.mom_name:
			mStatus = "momName";
			showDialog();
			break;
		case R.id.dad_name:
			mStatus = "dadName";
			showDialog();
			break;
		case R.id.unique_id:
			mStatus = "id";
			showDialog();
			break;
		case R.id.address:
			mStatus = "address";
			showDialog();
			break;
		case R.id.mDate:
			showDatePickerDialog();
		}
	}	
	
	private void showDialog() {
        EditNameDialog dialog = new EditNameDialog();
        dialog.setTargetFragment(this, 0);
        dialog.show(getFragmentManager(), "dialog");
    }
	
	public String getStatus() {
		return mStatus;
	}

	public static void updateView(){
		nameView.setText (ExistingActivity.p_existing.firstName + " " + ExistingActivity.p_existing.lastName);
		mDateView.setText(DateFormat.format(DateEntryFragment.DATEFORMAT, ExistingActivity.p_existing.birthday).toString());
		momNameView.setText (ExistingActivity.p_existing.mom_firstName + " " + ExistingActivity.p_existing.mom_lastName);
		dadNameView.setText (ExistingActivity.p_existing.dad_firstName + " " + ExistingActivity.p_existing.dad_lastName);
		addressView.setText (ExistingActivity.p_existing.getAddressString());
		uniqueIdView.setText (ExistingActivity.p_existing.getCode());
	}
	
	public void showDatePickerDialog() {
		cal = Calendar.getInstance();
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getActivity().getFragmentManager(), "datePicker");
	}
	
	public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

		private static final String DATEFORMAT = "MMM dd, yyyy";

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the date in the cal object
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH); 
			int day = cal.get(Calendar.DATE);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		public void onDateSet(DatePicker view, int year, int month, int day) {
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DATE, day);
			mDateView.setText(DateFormat.format(DATEFORMAT, cal).toString());
		}
	}	
	
}
