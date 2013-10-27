package com.example.gatesnfc.New;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.gatesnfc.R;

public class NotesEntryFragment extends Fragment {
	@SuppressWarnings("unused")
	private static final String TAG = NotesEntryFragment.class.getSimpleName();
	private static InputFilter[] lengthFilter;
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static EditText et_notes;
	public NotesEntryFragment() {
	}
	
	public static Fragment newInstance(int position) {
		NotesEntryFragment myFragment = new NotesEntryFragment();
		
		Bundle args = new Bundle();
		args.putInt(NotesEntryFragment.ARG_SECTION_NUMBER, position + 1);
		
		myFragment.setArguments(args);
		lengthFilter = new InputFilter[1];
		lengthFilter[0] = new InputFilter.LengthFilter(50);
	    return myFragment;
	}
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_notes_entry, container, false);
		et_notes = (EditText) rootView.findViewById(R.id.editText_notes);
		et_notes.setFilters(lengthFilter);
		int section_number = getArguments().getInt(ARG_SECTION_NUMBER);
		
		switch (section_number) {
		case 1: 
			// Vivian trying to remember how to use switch and case, plz don't remove
			break;
		case 3: 
			break;
		}		
		return rootView;
	}	
}
