package com.example.gatesnfc;

import com.example.gatesnfc.R;
import com.example.gatesnfc.existing.ExistingActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NFC_read extends Activity implements OnClickListener {
	public static String mMessage;

	private NfcAdapter mAdapter;
	private boolean mInReadMode;
	private Button mReadTagButton;
	private AlertDialog box;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nfc_read);
		
		// grab our NFC Adapter
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        
        // button that starts the tag-Read procedure
        mReadTagButton = (Button)findViewById(R.id.read_nfc);
        mReadTagButton.setOnClickListener(this);
        mReadTagButton.setBackgroundResource(R.drawable.rectangle);
        
	}
	
	public void onClick(View v) {
		if(v.getId() == R.id.read_nfc) {
			displayMessage("Touch and hold tag against phone to Read.");
			enableReadMode();
		}
	}
	
	private void displayMessage(String message) {
		
		AlertDialog.Builder dlgAlert= new AlertDialog.Builder(this)
        .setTitle(message);

		box = dlgAlert.create();
        box.show();
	}

	@Override
	protected void onPause() {
		super.onPause();
		disableReadMode();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (mInReadMode) {
			enableReadMode();
		}
	}
	
	/**
	 * Called when our blank tag is scanned executing the PendingIntent
	 */
	@Override
    public void onNewIntent(Intent intent) {
		if(mInReadMode) {
			mInReadMode = false;
	        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	        NdefMessage msg = (NdefMessage) rawMsgs[0];
	        NdefRecord cardRecord = msg.getRecords()[0];
	        
	        mMessage = new String(cardRecord.getPayload());
				
	        byte[] tagId = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
	        String theID = ByteArrayToHexString(tagId);
	            
			Intent e = new Intent(this, ExistingActivity.class);
			e.putExtra("SentData", mMessage);
			Log.d("Reading", mMessage);
			e.putExtra("SentCode", theID);
			box.dismiss();
			startActivity(e);
		}
    }
	

	/**
	 * Force this Activity to get NFC events first
	 */
	private void enableReadMode() {
		mInReadMode = true;
		
		// set up a PendingIntent to open the app when a tag is scanned
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
            new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] filters = new IntentFilter[] { tagDetected };
        
		mAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);
	}
	
	private void disableReadMode() {
		mAdapter.disableForegroundDispatch(this);
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
