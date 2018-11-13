package com.lakedev.phonechecker.service.resources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import com.lakedev.phonechecker.service.PhoneNumberFormatter;

/**
 * AdvancedBackgroundChecksResource.java
 * 
 * Collects phone number data from AdvancedBackgroundChecks.com
 * 
 * @See https://www.advancedbackgroundchecks.com
 * @author William Lake
 *
 */
public class AdvancedBackgroundChecksResource extends DataResource
{
	/**
	 * Constructor
	 * 
	 * Sets the selection string used by JSOUP to gather 
	 * the phone number data from the html provided by this
	 * html resource.
	 */
	public AdvancedBackgroundChecksResource()
	{
		// Should be a string to get multiple results
		setSelectionString("div.card-block");
	}

	/** {@inheritDoc} */
	@Override
	protected void constructUrl()
	{
		setUrl("https://www.advancedbackgroundchecks.com/" +
				PhoneNumberFormatter
				.formatPhoneNumber(
						getPhoneNumber(),
						PhoneNumberFormatter.DASHES));
	}

	/** {@inheritDoc} */
	@Override
	protected String gatherTargetData(Element result)
	{
		return gatherTargetDataViaGenericMethod(result);
	}

	@Override
	protected String gatherTargetResult(String totalResult)
	{
		Pattern pattern = Pattern.compile("(.+?) Age \\d+ Lives in: .+");
		
		Matcher matcher = pattern.matcher(totalResult);
		
		if (matcher.find())
		{
			return matcher.group(1);
		}
		
		return "";
	}

}
