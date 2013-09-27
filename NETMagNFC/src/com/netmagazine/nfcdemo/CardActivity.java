package com.netmagazine.nfcdemo;

import java.nio.charset.Charset;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

public class CardActivity extends Activity {
	private TextView tv_name;
	private TextView tv_name2;
	//private ImageView mCardView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_activity);
		
		tv_name = (TextView)findViewById(R.id.tv_name);
		tv_name2 = (TextView)findViewById(R.id.tv_name2);
		// ImageView that we'll use to display cards
        //mCardView = (ImageView)findViewById(R.id.card_view);
        
        // see if app was started from a tag and show the String stored
        Intent intent = getIntent();
        if(intent.getType() != null && intent.getType().equals(MimeType.NFC_DEMO)) {
        	Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg = (NdefMessage) rawMsgs[0];
            NdefRecord cardRecord = msg.getRecords()[0];
            String storedstr = new String(cardRecord.getPayload());
            String patientname = storedstr.substring(0, 8); //Takes patientname
            											  //Eg) abcdefghi == cde
            //Stored Date of Birth
            String Year1 = storedstr.substring(8, 12);
            String Month1 = storedstr.substring(12, 14);
            String Date1 = storedstr.substring(14, 16);
            // If you try to access a value outside of the stored amount, your program crashes
            tv_name.setText("Patient's name: " + patientname);
            tv_name2.setText("Date of Birth: " + Year1 + "/" + Month1 + "/" + Date1);
            //displayCard(consoleName);
        }
	}
	
	/*private void displayCard(String consoleName) {
		int cardResId = 0;
		if(consoleName.equals("nes")) cardResId = R.drawable.nes;
		else if(consoleName.equals("snes")) cardResId = R.drawable.snes;
		else if(consoleName.equals("megadrive")) cardResId = R.drawable.megadrive;
		else if(consoleName.equals("mastersystem")) cardResId = R.drawable.mastersystem;
		mCardView.setImageResource(cardResId);
	}*/
}
