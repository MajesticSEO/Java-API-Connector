/*
 * The license for this file can be found at https://github.com/majestic/Java-API-Connector.
 */

package com.majesticseo.external.rpc;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parses the XML response from the API, and provides accessor methods to retrieve the parsed data.
 */
public class Response
{
	/**
	 * SAX Parser Handler for Majestic SEO's API data
	 */
	private static class Handler extends DefaultHandler
	{
		private Response response;
		private DataTable dataTable;
		private boolean isRow;
		private StringBuilder row;

		public Handler(Response response)
		{
			this.response = response;
			isRow = false;
			row = new StringBuilder();
		}

		@Override
		public void characters(char ch[], int start, int length) throws SAXException
		{
			if (isRow)
			{
				row.append(ch, start, length);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			if (qName.equals("Row"))
			{
				dataTable.setTableRow(row.toString());
				isRow = false;
				row = new StringBuilder();
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXParseException, SAXException
		{
			if (qName.equals("Result"))
			{
				for (int i = 0; i < attrs.getLength(); i++)
				{
					response.getResponseAttributes().put(attrs.getQName(i), attrs.getValue(i));
				}
			}

			if (qName.equals("GlobalVars"))
			{
				for (int i = 0; i < attrs.getLength(); i++)
				{
					response.getGlobalParams().put(attrs.getQName(i), attrs.getValue(i));
				}
			}

			if (qName.equals("DataTable"))
			{
				dataTable = new DataTable();
				dataTable.setTableName(attrs.getValue("Name"));
				dataTable.setTableHeaders(attrs.getValue("Headers"));

				for (int i = 0; i < attrs.getLength(); i++)
				{
					if (!"Name".equals(attrs.getQName(i)) && !"Headers".equals(attrs.getQName(i)))
					{
						dataTable.setTableParams(attrs.getQName(i), attrs.getValue(i));
					}
				}

				response.getTables().put(dataTable.getTableName(), dataTable);
			}

			if (qName.equals("Row"))
			{
				isRow = true;
			}
		}
	}

	private Map<String, String> responseAttributes;
	private Map<String, String> params;
	private SAXParserFactory spf;
	private Map<String, DataTable> tables;

	private Response()
	{
		responseAttributes = new LinkedHashMap<String, String>();
		params = new LinkedHashMap<String, String>();
		spf = SAXParserFactory.newInstance();
		tables = new LinkedHashMap<String, DataTable>();
	}

	/**
	 * Constructor for correct response
	 *
	 * @param response response to parse
	 */
	public Response(InputStream response)
	{
		this();

		// TL required to keep this constructor backwards compatible
		if (response == null)
			return;

		try
		{
			SAXParser saxParser = spf.newSAXParser();
			Handler handler = new Handler(this);
			saxParser.parse(response, handler);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public Response(String code, String errorMessage)
	{
		this();

		responseAttributes.put("Code", code);
		responseAttributes.put("ErrorMessage", errorMessage);
		responseAttributes.put("FullError", errorMessage);
	}

	/**
	 * Returns the response's code
	 *
	 * @return Response's code
	 */
	public String getCode()
	{
		return responseAttributes.get("Code");
	}

	/**
	 * Returns the response's error message
	 *
	 * @return Response's error message
	 */
	public String getErrorMessage()
	{
		return responseAttributes.get("ErrorMessage");
	}

	/**
	 * Returns the response's full error message
	 *
	 * @return Response's full error message
	 */
	public String getFullError()
	{
		return responseAttributes.get("FullError");
	}

	/**
	 * Returns a hashmap with the response's global parameters
	 *
	 * @return Response's global parameters
	 */
	public Map<String, String> getGlobalParams()
	{
		return params;
	}

	/**
	 * Returns a specific parameter from the response's parameters
	 *
	 * @param name - name of parameter
	 *
	 * @return Specific parameter from the response's parameter
	 */
	public String getParamForName(String name)
	{
		return params.get(name);
	}

	/**
	 * Returns the response's attributes
	 *
	 * @return The response's attributes
	 */
	public Map<String, String> getResponseAttributes()
	{
		return responseAttributes;
	}

	/**
	 * Returns a data table object from the response's data tables
	 *
	 * @param name name of table
	 *
	 * @return Data table object
	 */
	public DataTable getTableForName(String name)
	{
		if (!(tables.containsKey(name)))
		{
			return new DataTable();
		}

		return tables.get(name);
	}

	/**
	 * Returns the response's data tables
	 *
	 * @return The response's data tables
	 */
	public Map<String, DataTable> getTables()
	{
		return tables;
	}

	/**
	 * Returns whether the request was completed successfully. Successful requests are determined by checking {@link #getCode} is either <code>OK</code> or
	 * <code>QueuedForProcessing</code>.
	 *
	 * @return <code>true</code> if the response code indicates the request was completed successfully, <code>false</code> if not.
	 */
	public boolean isOK()
	{
		return "OK".equals(getCode()) || "QueuedForProcessing".equals(getCode());
	}
}