//package com.example.gatesnfc.existing;
//
//import com.example.gatesnfc.R;
//
//import android.os.Bundle;
//import android.support.v4.app.DialogFragment;
//import android.view.*;
//import android.view.inputmethod.EditorInfo;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.TextView.OnEditorActionListener;
//
//import com.example.gatesnfc.existing.ExistingActivity;
//
//public class CopyOfEditNameDialog extends DialogFragment implements OnEditorActionListener {
//
//    private EditText mEditText;
//    private String mType;
//    
//    public CopyOfEditNameDialog() {
//        // Empty constructor required for DialogFragment
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.text_input_frag, container);
//        mEditText = (EditText) view.findViewById(R.id.input_text);
//        getDialog().setTitle("Hello");
//
//        // Show soft keyboard automatically
//        mEditText.requestFocus();
//        mEditText.setOnEditorActionListener(this);
//        
//        mType = ((PatientSummaryFragment)getTargetFragment()).getStatus();
//
//        return view;
//    }
//
//    @Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (EditorInfo.IME_ACTION_DONE == actionId) {
//        	//Directly change existing activity patient info
//        	if (mType == "Name"){
//        		ExistingActivity.p_existing.firstName = (v.getText().toString());
//        	}
//        	if (mType == "momName")
//        	{
//        		ExistingActivity.p_existing.mom_firstName = (v.getText().toString());
//        	}
//        	if (mType == "dadName")
//        	{
//        		ExistingActivity.p_existing.dad_firstName = (v.getText().toString());
//        	}
//        	if (mType == "address")
//        	{
//        		ExistingActivity.p_existing.street = (v.getText().toString());
//        	}
//        	if (mType == "id")
//        	{
//        		ExistingActivity.p_existing.setCode(v.getText().toString());
//        	}
//        	((PatientSummaryFragment)getTargetFragment()).updateView();
//            this.dismiss();
//            return true;
//        }
//        return false;
//    }
//}
