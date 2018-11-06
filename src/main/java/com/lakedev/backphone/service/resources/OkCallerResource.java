package com.lakedev.backphone.service.resources;

public class OkCallerResource extends DataResource
{

	public OkCallerResource()
	{
		setSelectionString("table#example-stats>class*=ss_tr_");
	}

	@Override
	protected void constructUrl()
	{
		setUrl("http://www.okcaller.com/" + getPhoneNumber());
	}
	
	@Override
	protected String gatherTargetData(Element result)
	{
		return gatherTargetDataViaGenericMethod(result);
	}

	@Override
	protected String gatherTargetResult(String totalResult)
	{
		return "Test";
	}

}
