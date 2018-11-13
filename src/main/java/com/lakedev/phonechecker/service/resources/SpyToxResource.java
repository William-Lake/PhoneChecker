package com.lakedev.phonechecker.service.resources;

import org.jsoup.nodes.Element;

import com.lakedev.phonechecker.service.PhoneNumberFormatter;

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
