package com.immunepicker;

/**
 * Inform the client which Immunization has been selected
 *
 */
public interface ImmunizationPickerListener {
	public void onSelectImmunization(String name, String code);
}
