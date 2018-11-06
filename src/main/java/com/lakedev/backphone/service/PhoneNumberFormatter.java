package com.lakedev.backphone.service;

import java.util.Arrays;
import java.util.List;

/**
 * PhoneNumberFormatter.java
 *
 * Formats phone numbers.
 * 
 * @author William Lake
 *
 */
public class PhoneNumberFormatter
{
	/** The phone number format containing dashes. E.g. XXX-XXX-XXXX */
	public static final String DASHES = "XXX-XXX-XXXX";
	
	/** The phone number format containing parentheses. E.g. (XXX)XXXXXXX */
	public static final String PARENS = "(XXX)XXXXXXX";
	
	/**
	 * Formats the given phone number using the requested formats.
	 * 
	 * @param phoneNumber
	 * 			The phone number to format.
	 * @param requestedFormats
	 * 			The requested formats.
	 * @return The formatted phone number.
	 */
	public static String formatPhoneNumber(String phoneNumber, String...formats)
	{
		List<String> requestedFormats = Arrays.asList(formats);
		
		String formattedPhoneNumber = "";
		
		String areaCode = phoneNumber.substring(0, 3);
		
		String prefix = phoneNumber.substring(3,6);
		
		String suffix = phoneNumber.substring(6);
		
		String formatString = "";
		
		boolean parensRequested = requestedFormats.contains(PARENS);
		
		boolean dashesRequested = requestedFormats.contains(DASHES);
		
		if (parensRequested && dashesRequested) formatString = "(%s)%s-%s";
		
		else if (parensRequested) formatString = "(%s)%s%s";
		
		else if (dashesRequested) formatString = "%s-%s-%s";
		
		formattedPhoneNumber = String.format(formatString, areaCode, prefix, suffix);
		
		return formattedPhoneNumber;
	}
	
}
