package com.example.gatesnfc.New;

import com.example.gatesnfc.R;
import com.example.gatesnfc.existing.DatePicker_Fix;

import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Build;
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
	@SuppressWarnings("unused")
	private static final String TAG = DateEntryFragment.class.getSimpleName();
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static final String DATEFORMAT = "MMM dd, yyyy";
	private static TextView tv_date;
	public static Calendar cal;
	private DialogFragment datePickerDialog;
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
		cal = NewActivity.patient.birthday;
		tv_date.setText(DateFormat.format(DATEFORMAT, cal).toString());
		
		//int section_number = getArguments().getInt(ARG_SECTION_NUMBER);		
		
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
		if(datePickerDialog == null) {
			datePickerDialog = new DatePickerFragment();
		}
		// To ensure only one datePickerDialog is visible at a time
		if(!datePickerDialog.isVisible())
	    datePickerDialog.show(getActivity().getFragmentManager(), "datePicker");
	}
	
	public static class DatePickerFragment extends DialogFragment {
	    
	    public static final String YEAR = "Year";
	    public static final String MONTH = "Month";
	    public static final String DAY = "Day";

	    private int year;
	    private int month;
	    private int day;
	    
	    private OnDateSetListener mListener;

	    public DatePicker_Fix newInstance() {
	    	DatePicker_Fix newDialog = new DatePicker_Fix();

	        // Supply initial date to show in dialog.
	        Bundle args = new Bundle();
	        newDialog.setArguments(args);

	        return newDialog;
	    }

	    // Added to original version in order to restore bundle saved by Activity
	    @Override
	    public void onCreate(Bundle savedInstanceState){
	        super.onCreate(savedInstanceState);
	        cal = NewActivity.patient.birthday;
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH); 
			day = cal.get(Calendar.DATE);
	    }
	    
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {

	        final DatePickerDialog picker = new DatePickerDialog(getActivity(), 
	                getConstructorListener(), year, month, day);
	        
	        if (hasJellyBeanAndAbove()) {
	            picker.setButton(DialogInterface.BUTTON_POSITIVE, 
	                    getActivity().getString(android.R.string.ok),
	                    new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                    DatePicker dp = picker.getDatePicker();
	        			cal.set(Calendar.YEAR, dp.getYear());
	        			cal.set(Calendar.MONTH, dp.getMonth());
	        			cal.set(Calendar.DATE, dp.getDayOfMonth());
	        			NewActivity.patient.birthday = cal;
	        			tv_date.setText(DateFormat.format(DATEFORMAT, cal).toString());
	        			}
	            });
	            picker.setButton(DialogInterface.BUTTON_NEGATIVE,
	                    getActivity().getString(android.R.string.cancel),
	                    new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int which) {}
	            });
	        }
	        return picker;
	    }
	    
	    private static boolean hasJellyBeanAndAbove() {
	        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	    }
	    
	    private OnDateSetListener getConstructorListener() {
	        return hasJellyBeanAndAbove() ? null : mListener;
	    }
	}

}
