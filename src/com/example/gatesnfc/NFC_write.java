package com.example.gatesnfc;

import java.io.IOException;
import java.nio.charset.Charset;

import com.example.gatesnfc.MimeType;
import com.example.gatesnfc.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NFC_write extends Activity implements OnClickListener {
	private NfcAdapter mAdapter;
	private boolean mInWriteMode;
	private Button mWriteTagButton;
	private String mMessage;
	private String mToWrite;
	private String mID;
	private Tag mTag;
	private AlertDialog box;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_write);
        Intent intent = getIntent();
        mToWrite = intent.getStringExtra("SendData");
        String ID = intent.getStringExtra("ID");
        mID = ID;
        // grab our NFC Adapter
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        
        // button that starts the tag-write procedure
        mWriteTagButton = (Button)findViewById(R.id.write_nfc);
        mWriteTagButton.setOnClickListener(this);
        mWriteTagButton.setBackgroundResource(R.drawable.rectangle);
    }
    
	public void onClick(View v) {
		if(v.getId() == R.id.write_nfc) {
			AlertDialog.Builder dlgAlert= new AlertDialog.Builder(this)
	        .setTitle("Touch and Hold Tag to Device to Write")
	        .setCancelable(true);
			box=dlgAlert.create();
	        box.show();
			enableWriteMode();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		disableWriteMode();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (mInWriteMode)
		{
			enableWriteMode();
		}
	}
	
	/**
	 * Called when our blank tag is scanned executing the PendingIntent
	 */
	@Override
    public void onNewIntent(Intent intent) {
		if(mInWriteMode) {
			mInWriteMode = false;
			
			//Dismiss Previous AlertDialog
			box.cancel();
			
			mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			
			byte[] tagId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
	        String theID = ByteArrayToHexString(tagId);
	        
			if (mID.equals(theID) || mID.equals("None"))
			{
				writeToTag();
			}
			else
			{
				 AlertDialog.Builder dlgAlert= new AlertDialog.Builder(this)
			        .setTitle("ID not Matching \n Are you sure you want to overwrite")
			        .setCancelable(false)
			        .setPositiveButton("Yes Done", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int whichButton) {
			            	writeToTag();
			            }
			        })
			        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int whichButton) {
			            	Intent returnIntent = new Intent();
			            	displayMessage("ID Not Matching");
			            	returnIntent.putExtra("result", mMessage);
			        		setResult(RESULT_CANCELED, returnIntent);

			        		finish();
			            }
			        });
				 
					box = dlgAlert.create();
		            box.show();
			}

		}
    }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Log.d("KeyDown Pressed", "Back Pressed)");
			Intent returnIntent = new Intent();
			returnIntent.putExtra("result", "Canceled by User");
			setResult(RESULT_CANCELED, returnIntent);
		} 
		return super.onKeyDown(keyCode, event);
	}
	
	private void writeToTag(){
		// write to newly scanned tag
		writeTag(mTag);
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result", mMessage);
		if (mMessage == "Tag written successfully!")
		{
			setResult(RESULT_OK, returnIntent);
		}
		else
		{
			setResult(RESULT_CANCELED, returnIntent);
		}
		
		finish();
	}
	
	/**
	 * Force this Activity to get NFC events first
	 */
	private void enableWriteMode() {
		mInWriteMode = true;
		
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
	
	/**
	 * Format a tag and write our NDEF message
	 */
	private boolean writeTag(Tag tag) {
		// record to launch Play Store if app is not installed
		// decided not to let it access the Play Store as users probably won't have access to internet
//		NdefRecord appRecord = NdefRecord.createApplicationRecord("com.example.gatesnfc");
		
		byte[] payload1 = mToWrite.getBytes();
		byte[] mimeBytes = MimeType.NFC_DEMO.getBytes(Charset.forName("US-ASCII"));
        NdefRecord cardRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeBytes, 
        										new byte[0], payload1);
		NdefMessage message = new NdefMessage(new NdefRecord[] { cardRecord});
        
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
				if (ndef.getMaxSize() < size) {
					displayMessage("Tag doesn't have enough free space.");
					return false;
				}

				ndef.writeNdefMessage(message);
				displayMessage("Tag written successfully!");
				return true;
			} else {
				// attempt to format tag
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						format.format(message);
						displayMessage("Tag written successfully!");
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
			displayMessage("Failed to write tag.");
		}

        return false;
    }

	
	private void displayMessage(String message) {
		mMessage = message;
	}
	
	
	private String ByteArrayToHexString(byte [] inarray) 
    {
    int i, j, in;
    String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    String out= "";

    for(j = 0 ; j < inarray.length ; ++j) 
        {
        in = (int) inarray[j] & 0xff;
        i = (in >> 4) & 0x0f;
        out += hex[i];
        i = in & 0x0f;
        out += hex[i];
        }
    return out;
    }
}
