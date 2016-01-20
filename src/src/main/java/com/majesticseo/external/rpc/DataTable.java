/*
 * The license for this file can be found at https://github.com/majestic/Java-API-Connector.
 */

package com.majesticseo.external.rpc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataTable
{
	private String tableName;
	private List<String> tableHeaders;
	private Map<String, String> tableParams;
	private List<Map<String, String>> tableRows;

	public DataTable()
	{
		tableName = "";
		tableHeaders = new ArrayList<String>();
		tableParams = new LinkedHashMap<String, String>();
		tableRows = new ArrayList<Map<String, String>>();
	}

	/**
	 * Returns a specific parameter from the table's parameters
	 *
	 * @param name name of parameter
	 *
	 * @return Specific parameter from the table's parameters or null if it does not exist
	 */
	public String getParamForName(String name)
	{
		return tableParams.get(name);
	}

	/**
	 * Returns the number of rows in the table
	 *
	 * @return The number of rows in the table
	 */
	public int getRowCount()
	{
		return tableRows.size();
	}

	/**
	 * Returns the table's headers
	 *
	 * @return The table's headers
	 */
	public List<String> getTableHeaders()
	{
		return tableHeaders;
	}

	/**
	 * Returns the table's name
	 *
	 * @return The table's name
	 */
	public String getTableName()
	{
		return tableName;
	}

	/**
	 * Returns the table's parameters
	 *
	 * @return The table's parameters
	 */
	public Map<String, String> getTableParams()
	{
		return tableParams;
	}

	/**
	 * Returns the table's rows
	 *
	 * @return The table's rows
	 */
	public List<Map<String, String>> getTableRows()
	{
		return tableRows;
	}

	/**
	 * Set the table's headers
	 *
	 * @param headers table's headers
	 */
	public void setTableHeaders(String headers)
	{
		String[] headersArray = split(headers);
		tableHeaders = new ArrayList<String>(headersArray.length);
		Collections.addAll(tableHeaders, headersArray);
	}

	/**
	 * Set the table's name
	 *
	 * @param name name of table
	 */
	public void setTableName(String name)
	{
		tableName = name;
	}

	/**
	 * Sets a table parameter
	 *
	 * @param name name of parameter
	 * @param value value of parameter
	 */
	public void setTableParams(String name, String value)
	{
		tableParams.put(name, value);
	}

	/**
	 * Sets a table row
	 *
	 * @param row row of the table
	 */
	public void setTableRow(String row)
	{
		LinkedHashMap<String, String> rowsHash = new LinkedHashMap<String, String>();
		String[] elements = split(row);

		for (int index = 0; index < elements.length; index++)
		{
			if (elements[index].equals(" "))
			{
				elements[index] = "";
			}

			rowsHash.put(tableHeaders.get(index), elements[index]);
		}

		tableRows.add(rowsHash);
	}

	private String[] split(String value)
	{
		String[] array = value.split("(?<!\\|)\\|(?!\\|)", -1);

		for (int i = 0; i < array.length; i++)
		{
			array[i] = array[i].replace("||", "|");
		}

		return array;
	}
}