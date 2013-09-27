package com.example.gatesnfc.existing;

import com.example.gatesnfc.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PatientSummaryFragment extends Fragment {
	public static final String TAG = PatientSummaryFragment.class.getSimpleName();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// load the layout elements from an xml file
		View view = inflater.inflate(R.layout.fragment_patient_summary, container, false);
		
		// Link layout elements to code        
        return (view);
	}
}
