package com.example.gatesnfc;

public class MimeType {
	/**
	 * MimeType is a fake data type the author made. 
	 * 
	 * When loading data into the NFC, a type is given (ie. Note, Music, etc) to identify
	 * the type of data we're going to be writing in. The NFC_DEMO string will be loaded into
	 * the NFC chip in MainActivity.
	 * 
	 * The string value of NFC_DEMO is also in the manifest as the data component 
	 * of CardActivity's intent filter. CardActivity will only be launched when: 
	 * 		- an NFC chip is detected
	 * 		- the type of data in the NFC == NFC_DEMO
	 * 		- some default category that I don't know much about.
	 * 
	 * -Vivian
	 * */
	public static final String NFC_DEMO = "application/vnd.example.gatesnfc.existing";
}
