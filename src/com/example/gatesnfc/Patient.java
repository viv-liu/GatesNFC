package com.example.gatesnfc;

import java.lang.reflect.Field;
import java.util.Calendar;

import android.util.Log;

public class Patient {
	// String dividers. Note: Can't be accessed on keyboard! 1 byte long
	private final static String DIVIDER = Character.toString((char) 0);		// Separates FIELDS
	private final static String SMALL_DIVIDER = Character.toString((char) 2);	// Separates parts of fields
	//public static final String DATEFORMAT = "MMM dd, yyyy";
	private final static int EXPECTED_NUM_DATE_BYTES = 6;
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
	private Calendar[] immDatesArray;
	
	//String variables
	private String code;
	public String firstName = "", lastName = "";
	public Calendar birthday = Calendar.getInstance();
	public String mom_firstName = "", mom_lastName = "";
	public String dad_firstName = "", dad_lastName = "";
	public String number = "";
	public String street = "", optional = "", city = "", region = "", country = "", postal = "";
	public String notes = "";
	
	public Patient() {
		// size of array = max index + 1
		immDatesArray = new Calendar[HepA1 + 1];
		for(int i = 0; i < immDatesArray.length; i++) {
			immDatesArray[i] = null;
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

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public void setBirthday(Calendar cal) {
		birthday = cal;
	}
	
	public Calendar getImmunizationDate(String mI) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
	{
		Class<?> c = this.getClass();
		Field f = c.getDeclaredField(mI);
		f.setAccessible(true);
		return immDatesArray[(Integer) f.get(this)];
	}
	
	public void setImmunizationDate(String mI, Calendar cal) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
	{
		Class<?> c = this.getClass();
		Field f = c.getDeclaredField(mI);
		f.setAccessible(true);
		int index = (Integer) f.get(this);
		immDatesArray[index] = cal;
	}
	
	public String constructPatientString() {
		String s = "";
		s += firstName + DIVIDER + lastName + DIVIDER;
		s += getDOBString() + DIVIDER;
		s += mom_firstName + DIVIDER + mom_lastName + DIVIDER;
		s += dad_firstName + DIVIDER + dad_lastName + DIVIDER;
		s += constructAddressString() + DIVIDER;
		s += constructImmuneString() + DIVIDER;
		s += notes;
		Log.d("constructPatientString", s);
		return s;
	}

	public boolean decryptPatientString(String s) {
		Log.d("decryptPatientString", s);
		String frag = "";
		char letter = 'a';
		int count = 0;
		for(int i = 0; i < s.length(); i++) {
			letter = s.charAt(i);
			if(letter == DIVIDER.charAt(0)) {		// compares letter to DIVIDER char
				switch (count++) {
				case 0: firstName = frag; break;
				case 1: lastName = frag; break;
				case 2: birthday = decryptCalendarString(frag); break;
				case 3: mom_firstName = frag; break;
				case 4: mom_lastName = frag; break;
				case 5: dad_firstName = frag; break;
				case 6: dad_lastName = frag; break;
				case 7: number = frag; break;
				case 8: street = frag; break;
				case 9: optional = frag; break;
				case 10: city = frag; break;
				case 11: region = frag; break;
				case 12: country = frag; break;
				case 13: postal = frag; break;
				case 14: decryptImmuneString(frag); break;
				case 15: notes = frag; break;
				}
				frag = "";
			} else {
				frag += s.charAt(i);
			}
		}
		return false;
	}

	/**
	 * Checks if immDatesArray entry has a valid Calendar object. 
	 * @param mI
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	
	public Boolean getImmunization(String mI) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
	{
		Class<?> c = this.getClass();
		Field f = c.getDeclaredField(mI);
		f.setAccessible(true);
		if(immDatesArray[(Integer) f.get(this)] != null) {
			return true;
		} else {
			return false;
		}
	}
	
/*	public ArrayList<Immunization> getAllMyShotsList() {
		for(int i = 0; i < immDatesArray.length; i++) {
			if(immDatesArray[i] != null) {
				Immunization im = new Immunization();
				im.setGreyed(true);
				
			}
		}
	}*/

	//*******************************************************************
	// Private methods for constructing & decrypting Patient string
	//*******************************************************************
	/**
	 * Get Date of Birth string
	 * @return
	 */
	private String getDOBString() {	
		return constructCalendarString(birthday);
	}
	
	public String getAddressString() {
		String s = "";
		s += (number != null) ? number + " ": "";
		s += (street != null && !street.isEmpty()) ? street + ", ": "";
		s += (optional != null && !optional.isEmpty()) ? optional + ", ":"";
		s += (city != null && !city.isEmpty()) ? city + ", ": "";
		s += (region != null && !region.isEmpty()) ? region + ", ":"";
		s += (country != null && !country.isEmpty()) ? country + " ": "";
		s += (postal != null && !postal.isEmpty()) ? postal:"";
		return s;
	}
	
	private String constructAddressString() {
		return number + DIVIDER + street + DIVIDER + optional + DIVIDER + city + DIVIDER + region + DIVIDER + country + DIVIDER + postal; 
	}
	private void decryptImmuneString(String s) {
		String frag = "";
		int immArrayIndex = 0;
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == SMALL_DIVIDER.charAt(0)) {
				immDatesArray[immArrayIndex++] = decryptCalendarString(frag);
				frag = "";
			} else {
				frag += s.charAt(i);
			}
		}
	}
	/**
	 * Converts entire immDatesArray of Calendar objects into a string
	 * Each calendar object is expected to be EXPECTED_NUM_DATE_BYTES = 6 
	 * Empty entries of the array are loaded with a 1 byte SMALL_DIVIDER.
	 * 
	 * ie. 		date + SMALL_DIVIDER + date + date +SMALL_DIVIDER
	 * 	Bytes:		6				1							6			6				1
	 * @return
	 */
	private String constructImmuneString() {
		String s = "";
		for(int i = 0; i < immDatesArray.length; i++) {
			if(immDatesArray[i] != null) {
				s += constructCalendarString(immDatesArray[i]) + SMALL_DIVIDER;
			} else {
				s += SMALL_DIVIDER;
			}
		}
		return s;
	}
	
	/**
	 * Expects a 6 char string terminated with a DIVIDER (null) character and produces
	 * a calendar object.
	 * @param s
	 * @return
	 */
	private Calendar decryptCalendarString(String s) {
		assert(s.length() == 7);
		Calendar c = Calendar.getInstance();
		String frag = "";
		int calField = 0;
		if(s != null && !s.isEmpty()) {
			for (int i = 0; i < s.length(); i++) {
				frag += s.charAt(i);
				switch(i) {
				case 1:
					calField = Integer.parseInt(frag);
					Calendar now = Calendar.getInstance();
					if(calField + 2000 > now.get(Calendar.YEAR)) {
						calField += 1900;
					} else {
						calField += 2000;
					}
					c.set(Calendar.YEAR, calField);
					frag = "";	
					break;
				case 3:
					calField = Integer.parseInt(frag);
					c.set(Calendar.MONTH, calField);
					frag = "";	
					break;
				case 5:
					calField = Integer.parseInt(frag);
					c.set(Calendar.DATE, calField);
					frag = "";	
					break;
				}
			}
			return c;
		}
		return null;
	}
	/** Converts a calendar object into numbers and into a string sequence
	 * ie October 31, 2013 is converted to "130931"~
	 * 
	 * ~ Year = Current year - 2000
	 * Returns a string of byte length: 6*/
		private String constructCalendarString(Calendar c) {	
			// Reduce year to last 2 digits: check if year > 2000. 
			int year = c.get(Calendar.YEAR);
			if(year > 2000) {
				year -= 2000;
			} else { // assume we're in the 1900s
				year -= 1900;
			}
			int month = c.get(Calendar.MONTH);	// month is an index 0 - 11, where January = 0
			int date = c.get(Calendar.DATE);
			String s = "";
			if(year - 9 <= 0) {
				s += "0" + String.valueOf(year);
			} else {
				s += String.valueOf(year);
			}
			if (month - 9 <= 0) {
				// Pad the single digit of month
				s += "0" + String.valueOf(month); 
			} else {
				s += String.valueOf(month);
			}
			if(date < 10) {
				// Pad the single digit of date
				s += "0" + String.valueOf(date);
			} else {
				s += String.valueOf(date);
			}
			// Just in case
			assert(s.getBytes().length == EXPECTED_NUM_DATE_BYTES);
			return s;
		}
}