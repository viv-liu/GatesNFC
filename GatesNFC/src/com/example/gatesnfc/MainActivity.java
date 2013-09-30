package com.example.gatesnfc;

import com.example.gatesnfc.New.NewActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button bExisting;
	private Button bNew;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bNew = (Button) findViewById(R.id.button_new);
		bNew.setOnClickListener(this);
		bExisting = (Button) findViewById(R.id.button_existing);
		bExisting.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public static void swapFragments(FragmentActivity context, Fragment fragment, Bundle args, String tag, boolean addToBackStack) {
		Fragment f = fragment;
	    FragmentManager manager = context.getSupportFragmentManager();
	    if(args != null) {
	    	f.setArguments(args);
	    }
	    FragmentTransaction transaction = manager.beginTransaction();
		transaction.replace(R.id.container, f, tag);
		if(addToBackStack) {
			transaction.addToBackStack(null);
		}
		transaction.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_existing:
			showWaitingDialog();
			break;
		case R.id.button_new:
			Intent i = new Intent(this, NewActivity.class);
			startActivity(i);
			break;
		default:
			Log.d("clicked", "lol");
			break;
		}
	}
	
	private void showWaitingDialog() {
		AlertDialog.Builder dlgAlert= new AlertDialog.Builder(this)
        .setTitle("Searching for NFC...")
        .setCancelable(true);
		AlertDialog box=dlgAlert.create();
        box.show();
        Log.d("showing waiting dialog", "lol");
	}
}