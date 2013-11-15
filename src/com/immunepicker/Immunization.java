package com.immunepicker;

import java.util.Calendar;

/**
 * Immunization Class that contains the date and the name
 *
 */
public class Immunization {
	private Calendar Date;
	private String name;
	private boolean isGreyed;
	
	public Immunization() {
		isGreyed = false;
	}
	public Calendar getDate() {
		return Date;
	}

	public void setDate(Calendar date) {
		this.Date = date;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isGreyed() {
		return isGreyed;
	}
	
	public void setGreyed(boolean b) {
		isGreyed = b;
	}

}