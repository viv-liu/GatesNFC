package com.immunepicker;

import java.util.Calendar;

/**
 * POJO
 *
 */
public class Immunization {
	private Calendar Date;
	private String name;
	
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

}