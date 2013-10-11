package com.example.gatesnfc.New;

import com.example.gatesnfc.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class SingleTextEntryFragment extends Fragment {
	@SuppressWarnings("unused")
	private static final String TAG = SingleTextEntryFragment.class.getSimpleName();
	private static InputFilter[] nameFilter;
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	public SingleTextEntryFragment() {
	}
	
	public static Fragment newInstance(int position) {
		SingleTextEntryFragment myFragment = new SingleTextEntryFragment();
		
		Bundle args = new Bundle();
		args.putInt(SingleTextEntryFragment.ARG_SECTION_NUMBER, position + 1);
		
		myFragment.setArguments(args);
		nameFilter = new InputFilter[1];
		nameFilter[0] = new InputFilter.LengthFilter(10);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_single_text_entry, container, false);
		TextView tv_label = (TextView) rootView.findViewById(R.id.textView_label);
		EditText et_field = (EditText) rootView.findViewById(R.id.editText1);
		int section_number = getArguments().getInt(ARG_SECTION_NUMBER);
		
		switch (section_number) {
		case 1: 
			// Child's name
			tv_label.setText("Name: ");
			et_field.setHint("Shen");
			et_field.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
			et_field.setFilters(nameFilter);
			break;
		case 3:
			tv_label.setText("Date of Birth: ");
			et_field.setHint("1");
			et_field.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
			
			/* The below sets an intent filter for the max Length of the input age */
			InputFilter[] FilterArray2 = new InputFilter[1];
			FilterArray2[0] = new InputFilter.LengthFilter(10);
			et_field.setFilters(FilterArray2);
			break;
		case 4: 
			// Mother's name
			tv_label.setText("Mother's Name: ");
			et_field.setHint("mommy");
			et_field.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
			et_field.setFilters(nameFilter);
			break;
		case 5: 
			// Father's name
			tv_label.setText("Father's Name: ");
			et_field.setHint("daddy");
			et_field.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
			et_field.setFilters(nameFilter);
			break;
		}		
		return rootView;
	}	
}
