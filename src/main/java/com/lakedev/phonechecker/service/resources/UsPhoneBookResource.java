package com.lakedev.phonechecker.service.resources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import com.lakedev.phonechecker.service.PhoneNumberFormatter;

/**
 * UsPhoneBookResource.java
 * 
 * Collects phone number data from UsPhoneBook.com
 * 
 * @See https://www.usphonebook.com
 * @author William Lake
 *
 */
public class UsPhoneBookResource extends DataResource
{
	/**
	 * Constructor
	 * 
	 * Sets the selection string used by JSOUP to gather 
	 * the phone number data from the html provided by this
	 * html resource.
	 */
	public UsPhoneBookResource()
	{
		setSelectionString("div.row.info-text");
	}
	
	/** {@inheritDoc} */
	@Override
	protected void constructUrl()
	{
		setUrl("https://www.usphonebook.com/" + 
				PhoneNumberFormatter
				.formatPhoneNumber(
						getPhoneNumber(),
						PhoneNumberFormatter.DASHES));
	}
	
	/** {@inheritDoc} */
	@Override
	protected String gatherTargetData(Element result)
	{
		return
				result.select("span").isEmpty() ?
						NOTHING :
							gatherTargetDataViaGenericMethod(result);
	}

	/** {@inheritDoc} */
	@Override
	protected String gatherTargetResult(String totalResult)
	{
		Pattern pattern = Pattern.compile(".+? The international number for this[^\\.]+?\\. (.+?) may also go.+");
		
		Matcher matcher = pattern.matcher(totalResult);
		
		if (matcher.find())
		{
			return matcher.group(1);
		}
		
		return "";
	}

}
