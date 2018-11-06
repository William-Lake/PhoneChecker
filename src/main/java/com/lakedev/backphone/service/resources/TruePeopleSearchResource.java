package com.lakedev.backphone.service.resources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lakedev.backphone.service.PhoneNumberFormatter;

/**
 * TruePeopleSearchResource.java
 * 
 * Collects phone number data from TruePeopleSearch.com
 * 
 * @See https://www.truepeoplesearch.com
 * @author William Lake
 *
 */
public class TruePeopleSearchResource extends DataResource
{
	/**
	 * Constructor
	 * 
	 * Sets the selection string used by JSOUP to gather 
	 * the phone number data from the html provided by this
	 * html resource.
	 */
	public TruePeopleSearchResource()
	{
		setSelectionString("div.card.card-block.shadow-form.card-summary");
	}
	
	/** {@inheritDoc} */
	@Override
	protected void constructUrl()
	{
		setUrl("https://www.truepeoplesearch.com/results?phoneno=" + 
				PhoneNumberFormatter.formatPhoneNumber(
						getPhoneNumber(), 
						PhoneNumberFormatter.PARENS, 
						PhoneNumberFormatter.DASHES));
	}

	/** {@inheritDoc} */
	@Override
	protected String gatherTargetData(Element result)
	{
		return result.text();
	}

	@Override
	protected String gatherTargetResult(String totalResult)
	{
		Pattern pattern = Pattern.compile("(.+?) Age \\d+ Lives in .+");
		
		Matcher matcher = pattern.matcher(totalResult);
		
		if (matcher.find())
		{
			return matcher.group(1);
		}
		
		return "";
	}

}
