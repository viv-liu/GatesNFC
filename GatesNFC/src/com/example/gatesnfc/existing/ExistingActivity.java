package com.example.gatesnfc.existing;

import com.example.gatesnfc.MainActivity;
import com.example.gatesnfc.MimeType;
import com.example.gatesnfc.R;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExistingActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_container);
		
		//tv_name = (TextView)findViewById(R.id.tv_name);
		// ImageView that we'll use to display cards
        //mCardView = (ImageView)findViewById(R.id.card_view);
        
        // see if app was started from a tag and show game console
        Intent intent = getIntent();
        if(intent.getType() != null && intent.getType().equals(MimeType.NFC_DEMO)) {
        	Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg = (NdefMessage) rawMsgs[0];
            NdefRecord cardRecord = msg.getRecords()[0];
            String patientName = new String(cardRecord.getPayload());
            MainActivity.swapFragments(this, new PatientSummaryFragment(), null, PatientSummaryFragment.TAG, true);
        }
	}
}
