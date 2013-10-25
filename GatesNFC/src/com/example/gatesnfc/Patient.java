package com.example.gatesnfc;

import java.lang.reflect.Field;
import java.util.Calendar;

public class Patient {

	//String variables
	private String code;
	public String firstName = "", lastName = "";
	public Calendar birthday = Calendar.getInstance();
	public String mom_firstName = "", mom_lastName = "";
	public String dad_firstName = "", dad_lastName = "";
	public int number = 0;
	public String street = "", optional = "", region = "", country = "", postal = "";
	public String notes = "";
	
	//Immunizations Booleans
	private boolean BCG;
	private boolean HepB1;
	private boolean HepB2;
	private boolean HepB3;
	private boolean HepB4;
	private boolean Pol_type1;
	private boolean Pol_type2;
	private boolean Pol1;
	private boolean Pol2;
	private boolean Pol3;
	private boolean Pol4;
	private boolean DTP1;
	private boolean DTP2;
	private boolean DTP3;
	private boolean HIB1;
	private boolean HIB2;
	private boolean HIB3;
	private boolean PC_type;
	private boolean PC1;
	private boolean PC2;
	private boolean PC3;
	private boolean ROT_TYPE;
	private boolean ROT1;
	private boolean ROT2;
	private boolean ROT3;
	private boolean MEA1;
	private boolean MEA2;
	private boolean RUB;
	private boolean HPV1;
	private boolean HPV2;
	private boolean HPV3;
	private boolean JE_type;
	private boolean JE1;
	private boolean JE2;
	private boolean YF1;
	private boolean TBE1;
	private boolean TBE2;
	private boolean TBE3;
	private boolean TYP_type;
	private boolean TYP1;
	private boolean TYP2;
	private boolean TYP3;
	private boolean TYP4;
	private boolean CHO_type;
	private boolean CHO1;
	private boolean CHO2;
	private boolean CHO3;
	private boolean MENG_type1;
	private boolean MENG_type2;
	private boolean MENG1;
	private boolean MENG2;
	private boolean RAB1;
	private boolean RAB2;
	private boolean RAB3;
	private boolean MUP1;
	private boolean MUP2;
	private boolean INF1;
	private boolean INF2;
	private boolean HepA1;
	
	
	//Get and set string subfunctions
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public String getName() {
//		return this.name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//	
//	public String get_momName() {
//		return this.mom_name;
//	}
//
//	public void set_momName(String name) {
//		this.mom_name = name;
//	}
//
//	public String get_dadName() {
//		return this.dad_name;
//	}
//
//	public void set_dadName(String name) {
//		this.dad_name = name;
//	}
//	
//	public String getAdd() {
//		return this.address;
//	}
//
//	public void setAdd(String add) {
//		this.address = add;
//	}
	
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
		
		return (Boolean) f.get(this);
	}
	
	public void setImmunization(String mI) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException
	{
		Class<?> c = this.getClass();
		Field f = c.getDeclaredField(mI);
		f.setAccessible(true);
		//Toggles the Boolean on click
		if ((Boolean) f.get(this))
		{
			f.set(this, false);
		}
		else
		{
			f.set(this, true);
		}
	}
	
	
}

