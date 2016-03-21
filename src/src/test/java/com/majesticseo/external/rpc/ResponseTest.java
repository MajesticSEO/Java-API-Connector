package com.majesticseo.external.rpc;

import java.util.Map;

import junit.framework.TestCase;

public class ResponseTest extends TestCase
{
	private Response response1; // no data tables
	private Response response2; // code not ok
	private Response response3; // data tables included

	public ResponseTest(String testName)
	{
		super(testName);
		response1 = new Response(ResponseTest.class.getResourceAsStream("/example_empty_response.xml"));
		response2 = new Response(ResponseTest.class.getResourceAsStream("/example_error_response.xml"));
		response3 = new Response(ResponseTest.class.getResourceAsStream("/example_response.xml"));
	}

	public void testGetCode()
	{
		assertEquals("OK", response1.getCode());
		assertEquals("FailedRequestViaAPI", response2.getCode());
		assertEquals("OK", response3.getCode());
	}

	public void testGetErrorMessage()
	{
		assertEquals("", response1.getErrorMessage());
		assertEquals("The command 'Error' is not in the list of supported commands.", response2.getErrorMessage());
		assertEquals("", response3.getErrorMessage());
	}

	public void testGetFullError()
	{
		assertEquals("", response1.getFullError());
		assertEquals("This is the full error!", response2.getFullError());
		assertEquals("", response3.getFullError());
	}

	public void testGetGlobalParams()
	{
		Map<String, String> globalParams1 = response1.getGlobalParams();
		assertEquals(8, globalParams1.size());
		assertEquals("2012-05-01", globalParams1.get("FirstBackLinkDate"));

		Map<String, String> globalParams2 = response2.getGlobalParams();
		assertEquals(0, globalParams2.size());

		Map<String, String> globalParams3 = response3.getGlobalParams();
		assertEquals(8, globalParams3.size());
		assertEquals("2012-05-01", globalParams1.get("FirstBackLinkDate"));
	}

	public void testGetParamForName()
	{
		assertEquals("2012-05-01", response1.getParamForName("FirstBackLinkDate"));
		assertEquals("DAVE", response1.getParamForName("ServerName"));

		assertEquals("2012-05-01", response3.getParamForName("FirstBackLinkDate"));
		assertEquals("DAVE", response3.getParamForName("ServerName"));
	}

	public void testGetTableForName()
	{
		DataTable table1 = response1.getTableForName("Results");
		assertEquals(0, table1.getRowCount());

		DataTable table31 = response3.getTableForName("Results");
		assertEquals(2, table31.getRowCount());

		DataTable table32 = response3.getTableForName("Simple");
		assertEquals(1, table32.getRowCount());
	}

	public void testIsOK()
	{
		assertEquals(true, response1.isOK());
		assertEquals(false, response2.isOK());
	}
}