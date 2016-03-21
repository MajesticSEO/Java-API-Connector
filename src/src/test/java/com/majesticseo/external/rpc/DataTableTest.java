package com.majesticseo.external.rpc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

public class DataTableTest extends TestCase
{
	private Response response;
	private DataTable table1;
	private DataTable table2;
	private DataTable table3;
	private DataTable table4;

	public DataTableTest(String testName)
	{
		super(testName);

		response = new Response(getClass().getResourceAsStream("/example_response.xml"));
		table1 = response.getTableForName("Results");
		table2 = response.getTableForName("Simple");
		table3 = response.getTableForName("Empty");
		table4 = response.getTableForName("Split");
	}

	public void testGetRowCount()
	{
		assertEquals(2, table1.getRowCount());
		assertEquals(1, table2.getRowCount());
		assertEquals(0, table3.getRowCount());
		assertEquals(1, table4.getRowCount());
	}

	public void testGetTableRows()
	{
		List<Map<String, String>> table1Rows = table1.getTableRows();
		assertEquals(2, table1Rows.size());

		assertEquals("0", table1Rows.get(0).get("ItemNum"));
		assertEquals("http://majestic.com/", table1Rows.get(0).get("Item"));

		assertEquals("1", table1Rows.get(1).get("ItemNum"));
		assertEquals("majestic.com", table1Rows.get(1).get("Item"));
	}

	public void testGetTableHeaders()
	{
		assertEquals(Arrays.asList("IsOk"), table2.getTableHeaders());
	}

	public void testGetTableName()
	{
		assertEquals("Results", table1.getTableName());
		assertEquals("Simple", table2.getTableName());
		assertEquals("Empty", table3.getTableName());
		assertEquals("Split", table4.getTableName());
	}

	public void testGetParamForName()
	{
		assertEquals("30", table1.getParamForName("MaxTopicsRootDomain"));
		assertEquals("0", table3.getParamForName("RowsCount"));
	}

	public void testSplit()
	{
		// table 4
		String[] expResult = { "a", "b", "c|c", "d", "e||f||g", "h||i", "j" };
		String[] actResult = new String[7];
		int index = 0;

		for (Map<String, String> row : table4.getTableRows())
		{
			Set<String> keys = row.keySet();
			for (String key : keys)
			{
				String value = row.get(key);
				actResult[index] = value;
				index++;
			}
		}

		assertTrue(Arrays.equals(expResult, actResult));
	}
}