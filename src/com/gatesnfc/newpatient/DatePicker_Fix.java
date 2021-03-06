/*
 * Copyright 2012 David Cesarino de Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gatesnfc.newpatient;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

/**
 * <p>This class provides a usable {@link DatePickerDialog} wrapped as a {@link DialogFragment},
 * using the compatibility package v4. Its main advantage is handling Issue 34833 
 * automatically for you.</p>
 * 
 * <p>Current implementation (because I wanted that way =) ):</p>
 * 
 * <ul>
 * <li>Only two buttons, a {@code BUTTON_POSITIVE} and a {@code BUTTON_NEGATIVE}.
 * <li>Buttons labeled from {@code android.R.string.ok} and {@code android.R.string.cancel}.
 * </ul>
 * 
 * <p><strong>Usage sample:</strong></p>
 * 
 * <pre>class YourActivity extends Activity implements OnDateSetListener
 * 
 * // ...
 * 
 * Bundle b = new Bundle();
 * b.putInt(DatePickerDialogFragment.YEAR, 2012);
 * b.putInt(DatePickerDialogFragment.MONTH, 6);
 * b.putInt(DatePickerDialogFragment.DATE, 17);
 * DialogFragment picker = new DatePickerDialogFragment();
 * picker.setArguments(b);
 * picker.show(getActivity().getSupportFragmentManager(), "fragment_date_picker");</pre>
 * 
 * @author davidcesarino@gmail.com
 * @version 2012.0828
 * @see <a href="http://code.google.com/p/android/issues/detail?id=34833">Android Issue 34833</a>
 * @see <a href="http://stackoverflow.com/q/11444238/489607"
 * >Jelly Bean DatePickerDialog — is there a way to cancel?</a>
 *
 */
public class DatePicker_Fix extends DialogFragment {
    
    public static final String YEAR = "Year";
    public static final String MONTH = "Month";
    public static final String DAY = "Day";
    
    private OnDateSetListener mListener;

    private int year;
    private int month;
    private int day;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mListener = (OnDateSetListener) activity;
    }
    
    @Override
    public void onDetach() {
        this.mListener = null;
        super.onDetach();
    }

    public static DatePicker_Fix newInstance (int year, int month, int day) {
    	DatePicker_Fix newDialog = new DatePicker_Fix();

        // Supply initial date to show in dialog.
        Bundle args = new Bundle();

        args.putInt(DAY, day);
        args.putInt(MONTH, month);
        args.putInt(YEAR, year);

        newDialog.setArguments(args);

        return newDialog;
    }

    // Added to original version in order to restore bundle saved by Activity
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        day = getArguments().getInt(DAY);
        month = getArguments().getInt(MONTH);
        year = getArguments().getInt(YEAR);
    }

    @TargetApi(11)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
        // Jelly Bean introduced a bug in DatePickerDialog (and possibly 
        // TimePickerDialog as well), and one of the possible solutions is 
        // to postpone the creation of both the listener and the BUTTON_* .
        // 
        // Passing a null here won't harm because DatePickerDialog checks for a null
        // whenever it reads the listener that was passed here. >>> This seems to be 
        // true down to 1.5 / API 3, up to 4.1.1 / API 16. <<< No worries. For now.
        //
        // See my own question and answer, and details I included for the issue:
        //
        // http://stackoverflow.com/a/11493752/489607
        // http://code.google.com/p/android/issues/detail?id=34833
        //
        // Of course, suggestions welcome.
        
        final DatePickerDialog picker = new DatePickerDialog(getActivity(), 
                getConstructorListener(), year, month, day);
        
        if (hasJellyBeanAndAbove()) {
        	/*
        	 * Restriction of Date from 1980 Jan 1st to Current Date
        	 */
        	Calendar c = Calendar.getInstance();
        	picker.getDatePicker().setMaxDate(c.getTimeInMillis());
        	c.set(Calendar.YEAR, 1980);
        	c.set(Calendar.MONTH, c.getMinimum(Calendar.MONTH));
        	c.set(Calendar.DATE, c.getMinimum(Calendar.DATE));
        	c.set(Calendar.HOUR_OF_DAY, c.getMinimum(Calendar.HOUR_OF_DAY));
            c.set(Calendar.MINUTE, c.getMinimum(Calendar.MINUTE));
            c.set(Calendar.SECOND, c.getMinimum(Calendar.SECOND));
            c.set(Calendar.MILLISECOND, c.getMinimum(Calendar.MILLISECOND));
        	picker.getDatePicker().setMinDate(c.getTimeInMillis());
            picker.setButton(DialogInterface.BUTTON_POSITIVE, 
                    getActivity().getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatePicker dp = picker.getDatePicker();
                    mListener.onDateSet(dp, 
                            dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
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