package com.lakedev.phonechecker.service.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lakedev.phonechecker.service.SearchResult;

/**
 * DataResource.java
 * 
 * Resource for gathering Phone Number data.
 * 
 * Uses Jsoup.
 * 
 * @See https://jsoup.org/
 * @author William Lake
 *
 */
public abstract class DataResource
{
	/*
	 * Any new extending class needs to do the following:
	 * 
	 * 		1. Create a constructor which sets the Selection String.
	 * 			The Selection String is used by JSOUP to identify the
	 * 			root html element which contains the target data.
	 * 			See more here: https://jsoup.org/cookbook/extracting-data/selector-syntax
	 * 
	 * 		2. Fill out the constructURL method so it constructs a
	 * 			url usable for performing a search via the given resource
	 * 			for a given phone number.
	 * 
	 *  	3. Fill out the gatherTargetData method so the correct 
	 *  		text is gathered from the JSOUP Element provided
	 *  		by applying your selection string to the resource html.
	 *  
	 *  	4. Fill out the gatherTargetResult method so the person's
	 *  		name is correctly gathered from the total text result
	 *  		gathered via gatherTargetData().
	 *  
	 *  	5. Add the new Resource class to the PhoneNumberSearcher 
	 *  		resources List via the initialization block.
	 */
	
	private List<SearchResult> searchResults = new ArrayList<>();
	
	/** The phone number to search the resource for. */
	private String phoneNumber;
	
	/** The phone number specific url used to gather data. */
	private String url;
	
	/** The selection string passed to Jsoup when parsing the resource url. */
	private String selectionString;
	
	public static final String NOTHING = "";
	
	public static final String NO_RESULT = "No Result Found";
	
	/**
	 * Collects phone number data from the url resource
	 * via Jsoup.
	 * 
	 * @See https://jsoup.org/cookbook/extracting-data/selector-syntax 
	 * @return The search results.
	 */
	public void collectPhoneNumberData()
	{
		if (url == null) constructUrl();
		
		/*
		 * Gather the phone number specific webpage,
		 * gather the html elements containing the target data,
		 * collect the target data from the elements,
		 * save the data and return it.
		 */
		try
		{
			Document document = Jsoup.connect(url).get();
			
			Elements htmlElements = document.select(selectionString);
			
			List<String> collectedData = new LinkedList<>();
			
			for (Element htmlElement : htmlElements) collectedData.add(gatherTargetData(htmlElement));
			
			for (String totalResult : collectedData)
			{
				if (totalResult == null || totalResult.trim().isEmpty()) continue;
				
				SearchResult searchResult = SearchResult.NO_RESULT;
				
				String targetResult = gatherTargetResult(totalResult);
				
				searchResult = new SearchResult(url, targetResult, totalResult);
				
				searchResults.add(searchResult);
			}
			
		} catch (IOException e)
		{
			// Do Nothing, resource couldn't be collected.
			// TODO Log this
		} 
	}
	
	/**
	 * Constructs a phone number and resource specific url.
	 */
	protected abstract void constructUrl();
	
	/**
	 * Gathers target data from the given html element.
	 * 
	 * @param htmlElement
	 * 			The html element to gather the target data from.
	 * @return The target data.
	 */
	protected abstract String gatherTargetData(Element result);
	
	protected String gatherTargetDataViaGenericMethod(Element result)
	{
		List<String> textResults = new LinkedList<>();
		
		textResults.add(result.text());
			
		for (Element childElement : result.children())
		{
			textResults.add(childElement.text());
		}
		
		return String.join(" ", textResults);
	}
	
	/**
	 * Extracts and returns the target result from 
	 * the total result.
	 * 
	 * @param totalResult
	 * 			The total result to gather the target result from.
	 * @return The target result.
	 */
	protected abstract String gatherTargetResult(String totalResult);
	
	protected void setSelectionString(String selectionString)
	{
		this.selectionString = selectionString;
	}
	
	protected void setUrl(String url)
	{
		this.url = url;
	}
	
	protected String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String getUrl()
	{
		return 
				url == null ?
						NOTHING :
							url;
	}
	
	public List<SearchResult> getSearchResults()
	{
		return searchResults;
	}

}
