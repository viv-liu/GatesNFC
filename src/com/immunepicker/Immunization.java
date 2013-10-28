package com.immunepicker;

/**
 * POJO
 *
 */
public class Immunization {
	private String Date;
	private String name;
	
	private String oldName;
	private String oldDate;

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		this.Date = date;
	}
	
	public String getOldDate() {
		return oldDate;
	}

	public void setOldDate(String date) {
		this.oldDate = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOldName() {
		return oldName;
	}

	public void setOldName(String name) {
		this.oldName = name;
	}

}