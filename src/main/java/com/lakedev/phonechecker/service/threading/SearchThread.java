package com.lakedev.phonechecker.service.threading;

import java.util.concurrent.CountDownLatch;

import com.lakedev.phonechecker.service.resources.DataResource;

/**
 * SearchThread.java
 * 
 * Contains the steps to perform when
 * executing a phone number search on a 
 * separate thread.
 * 
 * Make use of CountDownLatch
 * 
 * @See https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CountDownLatch.html
 * @author William Lake
 *
 */
public class SearchThread implements Runnable
{
	private CountDownLatch latch;
	
	private DataResource resource;
	
	/**
	 * Constructor
	 * 
	 * @param latch
	 * 			The countdown latch to use.
	 * @param resource
	 * 			The DataResource to search.
	 */
	public SearchThread(CountDownLatch latch, DataResource resource)
	{
		this.latch = latch;
		
		this.resource = resource;
	}

	/** {@inheritdoc} */
	@Override
	public void run()
	{
		resource.collectPhoneNumberData();
		
		latch.countDown(); // Tells the CountDownLatch the work is done here.
	}

}
