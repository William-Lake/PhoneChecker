package com.lakedev.backphone.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResult
{
	private String resource;
	
	private String targetResult;
	
	private String fullResult;
	
	public static final SearchResult NO_RESULT = new SearchResult("","","");

	public SearchResult(String resource, String targetResult, String result)
	{
		super();
		this.resource = resource;
		this.targetResult = targetResult;
		this.fullResult = result;
	}

	public String getResource()
	{
		return resource;
	}
	
	public String getResourceShorthand()
	{
		Pattern pattern = Pattern.compile("https:\\/\\/www\\.(.*?)\\.com.*?");
		
		Matcher matcher = pattern.matcher(resource);
		
		if (matcher.find()) return matcher.group(1);
		
		return resource;
	}

	public String getResult()
	{
		return fullResult;
	};
	
	public String getTargetResult()
	{
		return targetResult;
	}
	
	public String getShortenedResource()
	{
		return 
				this.equals(NO_RESULT) ?
						"" :
							resource.split("\\.")[1];
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return 
				obj instanceof SearchResult &&
				((SearchResult) obj).getResource().equals(resource) &&
				((SearchResult) obj).getResult().equals(fullResult) &&
				((SearchResult) obj).getTargetResult().equals(targetResult);
	}
	
	@Override
	public int hashCode()
	{
		return 
				(
						resource +
						targetResult +
						fullResult).hashCode();
	}
	
}
