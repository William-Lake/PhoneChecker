package com.lakedev.phonechecker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.lakedev.phonechecker.service.resources.AdvancedBackgroundChecksResource;
import com.lakedev.phonechecker.service.resources.DataResource;
import com.lakedev.phonechecker.service.resources.FastPeopleSearchResource;
import com.lakedev.phonechecker.service.resources.PhoneSearchFreeResource;
import com.lakedev.phonechecker.service.resources.SpyToxResource;
import com.lakedev.phonechecker.service.resources.TruePeopleSearchResource;
import com.lakedev.phonechecker.service.resources.UsPhoneBookResource;
import com.lakedev.phonechecker.service.threading.SearchThread;

/**
 * PhoneNumberSearcher.java
 * 
 * Singleton directing the steps for searching 
 * a list of resources for a given phone number.
 * 
 * Uses Initialization-on-demand holder.
 * 
 * @author William Lake
 * @See https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
 *
 */
public class PhoneNumberSearcher
{
	/**
	 * Wrapper.java
	 * 
	 * Holds an instance of the PhoneNumberSearcher.
	 * 
	 * Part of the Initialization-on-demand holder pattern.
	 * 
	 * @author William Lake
	 *
	 */
	private static final class Wrapper
	{
		static final PhoneNumberSearcher INSTANCE = new PhoneNumberSearcher();
	}
	
	/** Map of search results and the url that they came from. */
	private List<SearchResult> results;
	
	/** The List of DataResources used to search for a given phone number. */
	private List<DataResource> resources;
	
	/** Building the List of DataResources used when searching. @See https://stackoverflow.com/a/2420466 */ 
	{
		resources = new ArrayList<>();
		
		resources.add(new PhoneSearchFreeResource());
		
		resources.add(new UsPhoneBookResource());
		
		resources.add(new AdvancedBackgroundChecksResource());
		
		resources.add(new TruePeopleSearchResource());
		
		resources.add(new FastPeopleSearchResource());
		
		resources.add(new SpyToxResource());
		
//		resources.add(new OkCallerResource()); Still Testing
	}
	
	/** 
	 * Static initialization block used to set the 
	 * set jsse.enableSNIExension to false.
	 * 
	 * TODO: Move to DataResource class, make it catch the error thrown when this isn't set, set it in the catch then try the search again via recursion (Infinite loop?).
	 * @See https://stackoverflow.com/a/14884941 
	 * @See https://stackoverflow.com/a/36328975*/
	static
	{
		System.setProperty("jsse.enableSNIExtension", "false");
	}
	
	/**
	 * Returns the current PhoneNumberSearcher instance.
	 * 
	 * Part of the Initialization-on-demand holder pattern.
	 * 
	 * @return The current PhoneNumberSearcher instance.
	 */
	public static PhoneNumberSearcher getInstance()
	{
		return Wrapper.INSTANCE;
	}
	
	/**
	 * Performs a phone number search for the given phone number.
	 * 
	 * Utilizes Multi-Threading via CountDownLatch.
	 * This ensures the main thread doesn't continue until
	 * all child threads have completed their work.
	 * 
	 * @See https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CountDownLatch.html
	 * @param phoneNumber
	 * 			The phone number to search for.
	 * @return A HashMap of the results. Keys are the urls, Values are the results.
	 */
	public List<SearchResult> performSearch(String phoneNumber)
	{
		results = new ArrayList<>();
		
		// Parameter is the number of threads we will create. We want one thread per resource.
		CountDownLatch latch = new CountDownLatch(resources.size());
		
		// For each resource provide the phone number and start the search on a separate thread.
		resources
		.stream()
		.forEach(resource -> 
		{
			resource.setPhoneNumber(phoneNumber);
			
			Thread thread = new Thread(new SearchThread(latch, resource));
			
			thread.start();
		});
		
		// Wait until all threads have completed their work.
		try
		{
			latch.await();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		// Collect and save the search results.
		for (DataResource resource : resources)
		{
			if (
					resource
					.getSearchResults()
					.isEmpty() == false) 
				
				results.addAll(resource.getSearchResults());
		}
		
		return results;
	}
}
