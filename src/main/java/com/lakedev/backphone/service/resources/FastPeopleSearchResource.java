package com.lakedev.backphone.service.resources;

import com.lakedev.backphone.service.PhoneNumberFormatter;

/**
 * FastPeopleSearch.java
 * 
 * Collects phone number data from FastPeopleSearch.com
 * 
 * @See https://www.fastpeoplesearch.com/
 * @author William Lake
 *
 */
public class FastPeopleSearchResource extends DataResource
{
	/**
	 * Constructor
	 * 
	 * Sets the selection string used by JSOUP to gather 
	 * the phone number data from the html provided by this
	 * html resource.
	 */
	public FastPeopleSearchResource()
	{
		setSelectionString("div.card[data-link]");
	}
	
	/** {@inheritDoc} */
	@Override
	protected void constructUrl()
	{
		setUrl("https://www.fastpeoplesearch.com/" +
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
	
	/** {@inheritDoc} */
	@Override
	protected String gatherTargetResult(String totalResult)
	{
		return 
				totalResult
				.split(" Lives in: ")
				[0];
	}
	
}
