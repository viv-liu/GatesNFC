package com.example.gatesnfc.New;

import com.example.gatesnfc.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

public class DateEntryFragment extends Fragment implements OnClickListener {

	private static final String TAG = DateEntryFragment.class.getSimpleName();
	public static final String ARG_SECTION_NUMBER = "section_number";
	private static final String DATEFORMAT = "MMM dd, yyyy";
	private static TextView tv_date;
	private static Calendar cal;
	public DateEntryFragment() {
	}
	
	public static Fragment newInstance(int position) {
		DateEntryFragment myFragment = new DateEntryFragment();
		
		Bundle args = new Bundle();
		args.putInt(DateEntryFragment.ARG_SECTION_NUMBER, position+1);
		
		myFragment.setArguments(args);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_date_entry, container, false);
		tv_date = (TextView) rootView.findViewById(R.id.textView_date);
		tv_date.setOnClickListener(this);
		cal = Calendar.getInstance();
		tv_date.setText(DateFormat.format(DATEFORMAT, cal).toString());
		
		int section_number = getArguments().getInt(ARG_SECTION_NUMBER);		
		
		return rootView;
	}	
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.textView_date:
			showDatePickerDialog();
		}
	}
	
	public void showDatePickerDialog() {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getActivity().getFragmentManager(), "datePicker");
	}
	
	public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

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
			tv_date.setText(DateFormat.format(DATEFORMAT, cal).toString());
			/*date.day = (day);
			date.month = (month);
			date.year = year - 1900;
			date.setText(tv_date);*/
		}
	}	
}
