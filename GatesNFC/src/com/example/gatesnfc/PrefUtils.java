package com.example.gatesnfc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {
	public static final String ANKLE_SIDE_KEY = "ankleSide";
	public static final String USER_ID_KEY = "user_id";
	/*public static final String FIRST_NAME_KEY = "firstName", LAST_NAME_KEY = "lastName";
	public static final String DAY_KEY = "day", MONTH_KEY = "month", YEAR_KEY = "year";
	public static final String MOM_FIRST_NAME_KEY = "momFirst", MOM_LAST_NAME_KEY = "momLast";
	public static final String DAD_FIRST_NAME_KEY = "dadFirst", DAD_LAST_NAME_KEY = "dadLast";
	public static final String ADDRESS_NUM_KEY = "addressNum";*/
	public static final String COUNTRY_KEY = "country";
	
	public static void setStringPreference(Context c, String key, String value) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        
	    SharedPreferences.Editor editor = prefs.edit();
	  
	    editor.putString(key, value);
	    editor.commit(); 
	}
	
	public static String getStringPreference(Context c, String key) {
		String value = "";
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
	    value = prefs.getString(key, null);
	    
	    return value;
	}
	public static void setIntPreference(Context c, String key, int value) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        
	    SharedPreferences.Editor editor = prefs.edit();
	    
	    editor.putInt(key, value);
	    editor.commit();
	}
	
	public static int getIntPreference(Context c, String key) {
		int value = -1;
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
	    value = prefs.getInt(key, -1);
	    
	    return value;
	}
}
