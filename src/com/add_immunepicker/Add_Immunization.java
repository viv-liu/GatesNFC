package com.add_immunepicker;

public class Add_Immunization {
	private String name;
	private boolean TF;

	public String getName() {
		return this.name;
	}
	
	public boolean getTF() {
		return this.TF;
	}

	public void setName(String input) {
		this.name = input;
	}
	
	public void setTF() {
		if (TF)
			{
		TF = false;;
			}
		else
		{
			TF = true;
		}
	}

}
