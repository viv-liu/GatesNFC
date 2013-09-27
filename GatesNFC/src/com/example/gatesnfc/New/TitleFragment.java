package com.example.gatesnfc.New;

import com.example.gatesnfc.R;
import com.example.gatesnfc.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TitleFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/*// set the title of the action bar
		getActivity().getActionBar().setTitle("myAnkle");*/
		
		// load the layout elements from an xml file
		View view = inflater.inflate(R.layout.title, container, false);
		
		// Link layout elements to code        
        return (view);
	}
}
