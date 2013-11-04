package com.example.gatesnfc.existing;

import com.example.gatesnfc.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.gatesnfc.existing.ExistingActivity;

public class EditIDDialog extends DialogFragment implements OnEditorActionListener {

    private EditText mEditText;
    
    public EditIDDialog() {
        // Empty constructor required for DialogFragment
    }
    
    static EditIDDialog newInstance() {
        return new EditIDDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_input_frag, container);
        mEditText = (EditText) view.findViewById(R.id.input_text);
        getDialog().setTitle("Change the ID");

        // Show soft keyboard automatically
        mEditText.requestFocus();
        mEditText.setOnEditorActionListener(this);

        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
        	//Directly change existing activity ID
        	ExistingActivity.p_existing.setCode((v.getText().toString()));
        	((ExistingActivity) getActivity()).updateView();
            this.dismiss();
            return true;
        }
        return false;
    }
}
