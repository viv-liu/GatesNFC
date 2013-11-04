package com.example.gatesnfc.existing;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;


public class DatePickerFragment extends DialogFragment
implements DatePickerDialog.OnDateSetListener {
	private Calendar cal;
	private String calledFrom;
	private DatePickerDialogListener mListener;
	
	public interface DatePickerDialogListener {
        public void onDatePicked(DialogFragment dialog, Calendar c,
                String calledFrom);
    }
	
	public static DatePickerFragment newInstance(String isFrom) {
        DatePickerFragment instance = new DatePickerFragment();

        instance = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString("isFrom", isFrom);
        instance.setArguments(args);
        return instance;
    }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            calledFrom = getArguments().getString("isFrom");
    }

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		cal = Calendar.getInstance();
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
		mListener.onDatePicked(this, cal, calledFrom);
	}
	
	// Override the Fragment.onAttach() method to instantiate the
    // NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the
            // host
            mListener = (DatePickerDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}	
