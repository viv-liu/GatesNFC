package com.example.gatesnfc.New;

import com.countrypicker.CountryPicker;
import com.countrypicker.CountryPickerListener;

import com.example.gatesnfc.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SingleTextEntryFragment extends Fragment {
	@SuppressWarnings("unused")
	private static final String TAG = SingleTextEntryFragment.class.getSimpleName();
	
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	public SingleTextEntryFragment() {
	}
	
	
	public static Fragment newInstance(int position) {
		SingleTextEntryFragment myFragment = new SingleTextEntryFragment();
		
		Bundle args = new Bundle();
		args.putInt(SingleTextEntryFragment.ARG_SECTION_NUMBER, position + 1);
		
		myFragment.setArguments(args);
	    return myFragment;
	}
	


	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_single_text_entry, container, false);
		TextView tv_label = (TextView) rootView.findViewById(R.id.textView_label);
		EditText et_field = (EditText) rootView.findViewById(R.id.editText1);
		int section_number = getArguments().getInt(ARG_SECTION_NUMBER);
		
		
		// set appropriate image and text for this step
		switch (section_number) {
		case 1: 
			// Child's name
			tv_label.setText("Child's name: ");
			et_field.setHint("Shen");
			
			/*The below sets the max length of the text edit field*/
			InputFilter[] FilterArray1 = new InputFilter[1];
			FilterArray1[0] = new InputFilter.LengthFilter(7);
			et_field.setFilters(FilterArray1);
			
			break;
		case 2:
			tv_label.setText("Child's Age: ");
			et_field.setHint("1");
			
			/*The below makes a filter so that only integers can be the input of this field*/
			InputFilter filter = new InputFilter() { 
				@Override
				public CharSequence filter(CharSequence source, int start, int end, 
		        		Spanned dest, int dstart, int dend) {
					for (int i = start; i < end; i++) { 
                        if (!Character.isDigit(source.charAt(i))) { 
                                return ""; 
                        } 
					} 
					return null; 
				} 
				}; 
			
			/* The below sets an intent filter for the max Length of the input
			 * also sets the premade filter above */
			InputFilter[] FilterArray2 = new InputFilter[2];
			FilterArray2[0] = new InputFilter.LengthFilter(2);
			FilterArray2[1] = filter;
			et_field.setFilters(FilterArray2);
			break;
		case 3:	 

			
			break;
//		case 4:	dummyTextView.setText(R.string.tutorial_description_section4); break;
//		case 5:	dummyTextView.setText(R.string.tutorial_description_section5); break;
//		case 6:	dummyTextView.setText(R.string.tutorial_description_section6); break;
//		case 7:	dummyTextView.setText(R.string.tutorial_description_section7); break;	
//		case 8:	dummyTextView.setText(R.string.tutorial_description_section8); break;
//		}
//		
//		switch (section_number) {
//		case 1: dummyImageView.setImageResource(R.drawable.tutrial_step_1); break;
//		case 2: dummyImageView.setImageResource(R.drawable.tutrial_step_2); break;
//		case 3:	dummyImageView.setImageResource(R.drawable.tutrial_step_3); break;
//		case 4:	dummyImageView.setImageResource(R.drawable.tutrial_step_4); break;
//		case 5:	dummyImageView.setImageResource(R.drawable.tutrial_step_5); break;
//		case 6:	dummyImageView.setImageResource(R.drawable.tutrial_step_6); break;
//		case 7:	dummyImageView.setImageResource(R.drawable.tutrial_step_7); break;	
//		case 8:	dummyImageView.setImageResource(R.drawable.ankle); break;
		}
		
		
		return rootView;
	}
	
	
}