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
	//private ImageView mCardView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_activity);
		
		tv_name = (TextView)findViewById(R.id.tv_name);
		// ImageView that we'll use to display cards
        //mCardView = (ImageView)findViewById(R.id.card_view);
        
        // see if app was started from a tag and show the String stored
        Intent intent = getIntent();
        if(intent.getType() != null && intent.getType().equals(MimeType.NFC_DEMO)) {
        	Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg = (NdefMessage) rawMsgs[0];
            NdefRecord cardRecord = msg.getRecords()[0];
            String patientName = new String(cardRecord.getPayload());
            String cutName = patientName.substring(2, 5); //Takes from char 2-5 of name
            											  //Eg) abcdefghi == cde
            tv_name.setText("Patient's name: " + cutName);
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
