package com.lakedev.backphone.service.resources;

import com.lakedev.backphone.service.PhoneNumberFormatter;

public class SpyToxResource extends DataResource
{
	public SpyToxResource()
	{
		setSelectionString("div.person");
	}

	@Override
	protected void constructUrl()
	{
		setUrl("https://www.spytox.com/reverse-phone-lookup/" + 
				PhoneNumberFormatter
				.formatPhoneNumber(getPhoneNumber(), 
						PhoneNumberFormatter
						.DASHES));
	}

	@Override
	protected String gatherTargetData(Element result)
	{
		return result.text();
	}

	@Override
	protected String gatherTargetResult(String totalResult)
	{
		return totalResult;
	}

}
