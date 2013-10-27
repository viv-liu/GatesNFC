package com.example.gatesnfc;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;

import com.example.gatesnfc.New.DateEntryFragment;

import android.text.format.DateFormat;
import android.util.Log;

public class Patient {

	// Indices for immArray
	public final static int BCG = 0;
	public final static int HepB1 = 1, HepB2 = 2, HepB3 = 3, HepB4 = 4;
	public final static int Pol_type1 = 5, Pol_type2 = 6, Pol1 = 7, Pol2 = 8, Pol3 = 9, Pol4 = 10;
	public final static int DTP1 = 11, DTP2 = 12, DTP3 = 13;
	public final static int HIB1 = 14, HIB2 = 15, HIB3 = 16;
	public final static int PC_type = 17, PC1 = 18, PC2 = 19, PC3 = 20;
	public final static int ROT_TYPE = 21, ROT1 = 22, ROT2 = 23, ROT3 = 24;
	public final static int MEA1 = 25, MEA2 = 26;
	public final static int RUB = 27;
	public final static int HPV1 = 28, HPV2 = 29, HPV3 = 30;
	public final static int JE_type = 31, JE1 = 32, JE2 = 33;
	public final static int YF1 = 34;
	public final static int TBE1 = 35, TBE2 = 36, TBE3 = 37;
	public final static int TYP_type = 38, TYP1 = 39, TYP2 = 40, TYP3 = 41, TYP4 = 42;
	public final static int CHO_type = 43, CHO1 = 44, CHO2 = 45, CHO3 = 46;
	public final static int MENG_type1 = 47, MENG_type2 = 48, MENG1 = 49, MENG2 = 50;
	public final static int RAB1 = 51, RAB2 = 52, RAB3 = 53;
	public final static int MUP1 = 54, MUP2 = 55;
	public final static int INF1 = 56, INF2 = 57;
	public final static int HepA1 = 58;
	
	// Actual immunization array
	private boolean[] immArray;
	
	//String variables
	private String code;
	public String firstName = "", lastName = "";
	public Calendar birthday = Calendar.getInstance();
	public String mom_firstName = "", mom_lastName = "";
	public String dad_firstName = "", dad_lastName = "";
	public int number = 0;
	public String street = "", optional = "", region = "", country = "", postal = "";
	public String notes = "";
	
	public Patient() {
		// size of array = max index + 1
		immArray = new boolean[HepA1 + 1];
		for(int i = 0; i < immArray.length; i++) {
			immArray[i] = false;
		}
		getDOBString();
	}
	//Get and set string subfunctions
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String Notes) {
		this.notes = Notes;
	}
	
	public void setBirthday(Calendar cal) {
		birthday = cal;
	}
	
	public String getAddressString() {
		return number + " " + street + ", " + optional + ", " + region + ", " + country + ", " + postal; 
	}
	public Boolean getImmunization(String mI) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
	{
		Class<?> c = this.getClass();
		Field f = c.getDeclaredField(mI);
		f.setAccessible(true);
		return immArray[(Integer) f.get(this)];
	}
	
	public void setImmunization(String mI) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
	{
		Class<?> c = this.getClass();
		Field f = c.getDeclaredField(mI);
		f.setAccessible(true);
		//Toggles the Boolean on click
		int index = (Integer) f.get(this);
		if (immArray[index])
		{
			immArray[index] = false;
		}
		else
		{
			immArray[index] = true;
		}
	}
	
	public String getDOBString() {
		String s = "";
		char year = (char) (birthday.get(Calendar.YEAR)- 2000);
		char month = (char) birthday.get(Calendar.MONTH);
		char date = (char) (birthday.get(Calendar.DATE));
		s += String.valueOf(year);
		s += String.valueOf(month);
		s += String.valueOf(date);
		// Testing purposes
		/*Log.d("month", String.valueOf(birthday.get(Calendar.MONTH)));
		Log.d("DOB", DateFormat.format(DateEntryFragment.DATEFORMAT, birthday).toString());
		Log.d("year", String.valueOf(year));
		Log.d("month", String.valueOf(month));
		Log.d("date", String.valueOf(date));
		Log.d("s", s);*/
		
		return null;
	}
	public String getPackagedImmuneString() {
		int[] intSet = new int[64];
		String s = "";
		int ascii = 0;
		for(int i = 0; i < intSet.length; i++) {
			// Construct int array identical to immArray
			if(i < immArray.length) {
				if(immArray[i]) {
					intSet[i] = 1;	
				} else {
					intSet[i] = 0;
				}
			} else { 
				// Past index 58 of immArray, just default load with 0s
				intSet[i] = 0; 
			}
			// Every 7, 15, 23th index
			if( (i+1)%8 == 0 ) {
				for(int j = 0; j < 8; j++) {
					// manually convert intSet's bit sequence to an ascii number (bits to int)
					ascii += intSet[i-7 + j] * Math.pow(2, j);
				}
				s += Character.toString((char) ascii);
				ascii = 0;
			}
		}
		return s;
	}
}