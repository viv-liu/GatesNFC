//package com.example.gatesnfc.existing;
//
//import com.example.gatesnfc.MainActivity;
//import com.example.gatesnfc.MimeType;
//import com.example.gatesnfc.R;
//
//import android.app.AlertDialog;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.nfc.NdefMessage;
//import android.nfc.NdefRecord;
//import android.nfc.NfcAdapter;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//public class ExistingActivity extends FragmentActivity {
//	public static String patientName;
//	private int mCurCheckPosition = 1;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.fragment_container);
//		
//		Intent intent = getIntent();
//		if(intent.getType() != null && intent.getType().equals(MimeType.NFC_DEMO)) {
//			Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//			NdefMessage msg = (NdefMessage) rawMsgs[0];
//			NdefRecord cardRecord = msg.getRecords()[0];
//			patientName = new String(cardRecord.getPayload());
//			/*Temporarily pass a set class from this intent to the patient summary frag*/
//            MainActivity.swapFragments(this, new PatientSummaryFragment(), null, PatientSummaryFragment.TAG, false);
//		}
//		else if(patientName != null){
//            MainActivity.swapFragments(this, new PatientSummaryFragment(), null, PatientSummaryFragment.TAG, false);
//	    }
//		else{
//			showWaitingDialog();
//		}
//	}
//	
//	
//	
//	private void showWaitingDialog() {
//		AlertDialog.Builder dlgAlert= new AlertDialog.Builder(this)
//        .setTitle("Searching for NFC...")
//        .setCancelable(true);
//		AlertDialog box=dlgAlert.create();
//        box.show();
//        Log.d("showing waiting dialog", "lol");
//	}
//
//}
//