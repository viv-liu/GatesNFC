package com.netmagazine.nfcdemo;

import java.io.IOException;
import java.nio.charset.Charset;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 */
public class MainActivity extends Activity implements OnClickListener {
	private NfcAdapter mAdapter;
	private boolean mInWriteMode = false;
	private boolean mInWaitMode = false;
	private Button mWriteTagButton;
	private Button mWaitTagButton;
	private TextView tv_writeMode;
	private EditText et_name;
	private TextView mTextView;
	private TextView mTextView2;
	private TextView tv_waitMode;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
       
        // grab our NFC Adapter
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        
        // button that starts the tag-write procedure
        mWriteTagButton = (Button)findViewById(R.id.write_tag_button);
        mWriteTagButton.setOnClickListener(this);
        
        mWaitTagButton = (Button)findViewById(R.id.foreground_button);
        mWaitTagButton.setOnClickListener(this);
        
        // Shows value of mInWriteMode
        tv_writeMode = (TextView)findViewById(R.id.tv_writeMode);
        tv_writeMode.setText(String.valueOf(mInWriteMode));
        
        tv_waitMode = (TextView)findViewById(R.id.tv_waitMode);
        tv_waitMode.setText(String.valueOf(mInWaitMode));
        
        // EditText field to fill out patient name
        et_name = (EditText)findViewById(R.id.editText1);
        // TextView that we'll use to output messages to screen
        mTextView = (TextView)findViewById(R.id.text_view);
        mTextView2 = (TextView)findViewById(R.id.text_view2);
	}
    
	public void onClick(View v) {
		if(v.getId() == R.id.write_tag_button) {
			displayMessage("Touch and hold tag against phone to write.");
			enableWriteMode();
		}
		if(v.getId() == R.id.foreground_button) {
			if (mInWaitMode){
				displayMessage("Not Waiting");
				mInWaitMode = false;
				tv_waitMode.setText(String.valueOf(mInWaitMode));
				disableWaitMode();
			}
			else{
				displayMessage("Waiting");
				enableWaitMode();
			}
		}
	}
	
	
	//NFC tag causes the app to PAUSE so must close foreground dispatch
	@Override
	protected void onPause() {
		super.onPause();
		Log.d("PAUSING", String.valueOf(mInWaitMode));
		disableWriteMode();
		disableWaitMode();
	}
	//MUST RESUME MY FOREGROUND DISPATCH TO CONTINUE it after pause
	@Override
	protected void onResume() {
		super.onResume();
		if(mInWaitMode) {
			enableWaitMode();
		}
		if (mInWriteMode) {
			enableWriteMode();
		}
		
	}
	/**
	 * Called when our blank tag is scanned executing the PendingIntent
	 */
	@Override
    public void onNewIntent(Intent intent) {
		
		Log.d("Intent_Value", String.valueOf(mInWaitMode));
		
		if(mInWaitMode) {
			Toast.makeText(this, "Still Waiting", Toast.LENGTH_LONG).show();
			Log.d("INSIDER", String.valueOf(mInWaitMode));
		}
		else if(mInWriteMode) {
			mInWriteMode = false;
			tv_writeMode.setText(String.valueOf(mInWriteMode));
			
			// write to newly scanned tag
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			writeTag(tag);
		}

    }
	
	private void waitAgain() {
		mInWaitMode = true;
		mAdapter.disableForegroundDispatch(this);
		enableWaitMode();
	}
	
	/**
	 * Force this Activity to get NFC events first
	 */
	private void enableWriteMode() {
		mInWriteMode = true;
		tv_writeMode.setText(String.valueOf(mInWriteMode));
		
		// set up a PendingIntent to open the app when a tag is scanned
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
            new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] filters = new IntentFilter[] { tagDetected };
        
		mAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);
	}
	
	private void disableWriteMode() {
		mAdapter.disableForegroundDispatch(this);
	}
	
	
	private void enableWaitMode() {
		mInWaitMode = true;
		tv_waitMode.setText(String.valueOf(mInWaitMode));
		
		// set up a PendingIntent to open the app when a tag is scanned
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
            new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] filters = new IntentFilter[] { tagDetected };
        
		Log.d("Passed Intent", String.valueOf(mInWaitMode));	
		
		mAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);
	}
	
	private void disableWaitMode() {
		mAdapter.disableForegroundDispatch(this);
	}
	
	/**
	 * Format a tag and write our NDEF message
	 */
	private boolean writeTag(Tag tag) {
		// record to launch Play Store if app is not installed
		// Literally looks for this package in the phone, if not found, launch Google Play.
//		NdefRecord appRecord = NdefRecord.createApplicationRecord("");
		
		// record that contains our custom "retro console" game data, using custom MIME_TYPE
		
		// Generates string corresponding to game console, convert to bytes
		byte[] payload = getNameFromET().getBytes();
		// Converts NFC_DEMO string into bytes following US-ASCII conventions  
		byte[] mimeBytes = MimeType.NFC_DEMO.getBytes(Charset.forName("US-ASCII"));
		// Makes a compiled NdefRecord containing the type(MimeType) and payload(game console string)
        NdefRecord cardRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeBytes, 
        										new byte[0], payload);
        // Puts cardRecord above appRecord
		NdefMessage message = new NdefMessage(new NdefRecord[] {cardRecord});
        
		try {
			// see if tag is already NDEF formatted
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				ndef.connect();

				if (!ndef.isWritable()) {
					displayMessage("Read-only tag.");
					return false;
				}
				
				// work out how much space we need for the data
				int size = message.toByteArray().length;
				int maxSize = ndef.getMaxSize();
				// Log results: header stuff + Sherlock Holmes is 94 bytes!!
				// Shen is 83 bytes
				// ShenShen is 87 bytes
				// Thus Shen is actually 4 bytes (as expected)
				// And Header is 79 bytes
				Log.d("Ndef maxSize", String.valueOf(maxSize));
				Log.d("message size", String.valueOf(size));
				if (maxSize < size) {
					displayMessage("Tag doesn't have enough free space.");
					return false;
				}

				ndef.writeNdefMessage(message);
				displayMessage("Tag written successfully.");
				return true;
			} else {
				// attempt to format tag to be Ndef formatted
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						format.format(message);
						displayMessage("Tag written successfully!\nClose this app and scan tag.");
						return true;
					} catch (IOException e) {
						displayMessage("Unable to format tag to NDEF.");
						return false;
					}
				} else {
					displayMessage("Tag doesn't appear to support NDEF format.");
					return false;
				}
			}
		} catch (Exception e) {
			displayMessage("Failed to write tag");
		}

        return false;
    }
	
	private String getNameFromET() {
		return et_name.getText().toString();
	}

	private void displayMessage(String message) {
		mTextView.setText(message);
	}
}