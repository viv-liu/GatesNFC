package com.gatesnfc.newpatient;

import com.gatesnfc.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditNotesDialog extends DialogFragment implements OnClickListener {
	private static InputFilter[] notesFilter;
	private EditText et_notes;
	private View rootView;
	private Button btn_confirm;
	private Button btn_cancel;
	public EditNotesDialog() {
        // Empty constructor required for DialogFragment
    }
    
    static EditNotesDialog newInstance() {
    	EditNotesDialog myFragment = new EditNotesDialog();
	    return myFragment;
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.notes_entry_dialog, container, false);
		notesFilter = new InputFilter[2];
		notesFilter[0] = new InputFilter() { 
			@Override
	        public CharSequence filter(CharSequence source, int start, int end, 
	        		Spanned dest, int dstart, int dend) { 
	        		return null; 
	            }
		};
		notesFilter[1] = new InputFilter.LengthFilter(150);
		et_notes = (EditText) rootView.findViewById(R.id.editText_notes);
		et_notes.setText(NewActivity.p_new.getNotes());
		et_notes.setFilters(notesFilter);
		btn_confirm = (Button) rootView.findViewById(R.id.confirm);
		btn_cancel = (Button) rootView.findViewById(R.id.cancel);
		btn_confirm.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

		getDialog().setTitle("Notes");
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case (R.id.confirm):
			saveNewNotes();
			this.dismiss();
			break;
		case (R.id.cancel):
			this.dismiss();
			break;
		}
		
	}	
	
	private void saveNewNotes() {
		NewActivity.p_new.notes = (et_notes).getText().toString();
		NewActivity.updateAllViews();
	}
}
