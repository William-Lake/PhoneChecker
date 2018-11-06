package com.lakedev.backphone.service.resources;

/**
 * PhoneSearchFreeResource.java
 * 
 * Collects phone number data from PhoneSearchFree.com
 * 
 * @See https://www.phonesearchfree.com/
 * @author William Lake
 *
 */
public class PhoneSearchFreeResource extends DataResource
{
	/**
	 * Constructor
	 * 
	 * Sets the selection string used by JSOUP to gather 
	 * the phone number data from the html provided by this
	 * html resource.
	 */
	public PhoneSearchFreeResource()
	{
		setSelectionString("div.ls_contacts-people-finder-wrapper");
	}

	/** {@inheritDoc} */
	@Override
	protected void constructUrl()
	{
		setUrl("https://www.phonesearchfree.com/" + getPhoneNumber());
	}

	/** {@inheritDoc} */
	@Override
	protected String gatherTargetData(Element result)
	{
		return
				result
				.text()
				.contains("Why use") ?
						NOTHING :
							gatherTargetDataViaGenericMethod(result);
	}

	@Override
	protected String gatherTargetResult(String totalResult)
	{
		return totalResult.split(",")[0];
	}

}
