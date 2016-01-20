package com.majesticseo.external.rpc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author fujayelk
 */
public class ResponseTest extends TestCase
{
	private Response response1; // no data tables
	private Response response2; // code not ok
	private Response response3; // data tables included

	public ResponseTest(String testName)
	{
		super(testName);
		response1 = new Response(convertStringToInputStream(
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<Result Code=\"OK\" ErrorMessage=\"\" FullError=\"\">\r\n\t\t<GlobalVars APIAccess=\"0\" BusinessName=\"Majestic SEO\" BusinessStatus=\"AutoOnlineVerified\" BusinessVAT=\"SI81684991\" Country=\"\" Created=\"16/09/2010 15:10:05\" CustomerCreditLimit=\"0\" CustomerCredits=\"0.00\" CustomerEmail=\"fujayelk@majesticseo.com\" CustomerIsAdmin=\"0\" CustomerName=\"fujayel\" CustomerSubscriptions=\"1\" DeveloperAPIAccess=\"1\" EmailAddress=\"fujayelk@majesticseo.com\" EmailPref=\"0\" Name=\"fujayel\" ServerName=\"LEEPFROG\" ThirdPartyAPIAccess=\"0\" Updated=\"18/02/2011 11:07:49\"/>\r\n</Result>\r\n"));
		response2 = new Response(convertStringToInputStream(
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<Result Code=\"InvalidParameter\" ErrorMessage=\"Sorry but folder &#39;test&#39; already exists, please choose unique name!\" FullError=\"Majestic12.SearchIndexReportResponse+ResponseException: Sorry but folder &#39;test&#39; already exists, please choose unique name!&#13;&#10;   at Majestic12.SearchIndexReportRequest_CreateFolder.GetReport(SearchConfig oConfig, SearchIndex oSI, SearchIndexReportRequest oReq, SearchIndexReportManager oSIRM) in W:\\VersionFiles\\WorldSource\\MJ12searchLib\\ReportEngine_Admin.cs:line 7091&#13;&#10;   at Majestic12.SearchIndexReportManager.RunReport(SearchConfig oConfig, SearchIndex oSI, SearchIndexReportRequest oReq, Boolean bAvoidQueueing, SearchIndexReportResponse&#38; oRes) in W:\\VersionFiles\\WorldSource\\MJ12searchLib\\SearchIndexReportManager.cs:line 2285\">\r\n\t\t<GlobalVars APIAccess=\"0\" CustomerCreditLimit=\"0\" CustomerCredits=\"0.00\" CustomerEmail=\"fujayelk@majesticseo.com\" CustomerIsAdmin=\"0\" CustomerName=\"fujayel\" CustomerSubscriptions=\"1\" DeveloperAPIAccess=\"1\" ServerName=\"LEEPFROG\" ThirdPartyAPIAccess=\"0\"/>\r\n</Result>\r\n"));
		response3 = new Response(convertStringToInputStream(
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<Result Code=\"OK\" ErrorMessage=\"\" FullError=\"\">\r\n\t\t<GlobalVars APIAccess=\"0\" CustomerAdvancedReportsRemaining=\"0\" CustomerAvailableAnalysisResUnits=\"0\" CustomerCreditLimit=\"0\" CustomerCredits=\"50.00\" CustomerEmail=\"fujayelk3@majestic12.co.uk\" CustomerIsAdmin=\"0\" CustomerName=\"fujayelk3\" CustomerStandardReportBacklinksShown=\"0\" CustomerStandardReportsRemaining=\"0\" CustomerSubscriptions=\"0\" DeveloperAPIAccess=\"0\" ServerName=\"LOTTE\" ThirdPartyAPIAccess=\"0\"/>\r\n\t<DataTables Count=\"3\">\r\n\t\t<DataTable Name=\"Folders\" RowsCount=\"5\" Headers=\"FolderName|Created|FoldersInside|DomainsInside\">\r\n\t\t\t<Row>&#38;|13/09/2010 11:36:14|0|0</Row>\r\n\t\t\t<Row>a|07/09/2010 17:15:16|2|0</Row>\r\n\t\t\t<Row>Aston|17/09/2010 17:05:43|1|0</Row>\r\n\t\t\t<Row>Aston &#38;|17/09/2010 17:05:36|0|0</Row>\r\n\t\t\t<Row>Enter folder name here|15/09/2010 17:07:22|1|0</Row>\r\n\t\t</DataTable>\r\n\t\t<DataTable Name=\"AdvancedReports\" RowsCount=\"3\" Headers=\"ReportName|ReportCode|ReportLocked|Domain|LastUpdated|Status|SubDomains|Pages|Links|TotalBackLinks|ExtBackLinks|ExtDomains|ExtUniqueIPs|MaxPageACRank|MaxRefACRank|FreshNewBackLinks|FreshNewRefDomains|HumanLastFreshUpdateDate|LastFreshUpdateDate|AnalysisProgressInfo|Expires\">\r\n\t\t\t<Row>aston.ac.uk|01401545E146F5AA|0|aston.ac.uk|07/10/2010 16:21:28|Expired|790|85381|175757|446562|375435|22664|13599|11|12|10488|902|22 Oct 2010|2010-10-22| |07/10/2010 14:46:39</Row>\r\n\t\t\t<Row>majesticseo.com|35386CE11949C3C4|0|majesticseo.com|15/09/2010 10:02:19|Expired|82|4335|106744|244664|241031|1638|1366|10|11|137539|354|22 Oct 2010|2010-10-22| |15/09/2010 09:24:52</Row>\r\n\t\t\t<Row>birmingham.ac.uk|A879946EE693E277|0|birmingham.ac.uk|20/09/2010 11:57:30|Expired|27|342|76|5233|3913|1673|1038|10|11|1420|74|22 Oct 2010|2010-10-22| |20/09/2010 11:55:15</Row>\r\n\t\t</DataTable>\r\n\t\t<DataTable Name=\"StandardReports\" RowsCount=\"0\" Headers=\"ReportName|ReportCode|ReportLocked|Created|Status|URL|SubDomain|Domain|TotalUrlBackLinks|TotalSubDomainBackLinks|TotalDomainBackLinks|ExternalUrlBackLinks|ExternalSubDomainBackLinks|ExternalDomainBackLinks|AvailableUrlBackLinks|AvailableSubDomainBackLinks|AvailableDomainBackLinks|UrlRefDomains|SubDomainRefDomains|DomainRefDomains|AvailableUrlRefDomains|AvailableSubDomainRefDomains|AvailableDomainRefDomains\">\r\n\t\t</DataTable>\r\n\t</DataTables>\r\n</Result>\r\n"));
	}

	private InputStream convertStringToInputStream(String text)
	{
		InputStream is = new ByteArrayInputStream(text.getBytes());
		return is;
	}

	private String getDataTableAsString(DataTable dataTable)
	{
		String dataTableString = "Table Name: " + dataTable.getTableName() + "\nTable Headers: ";

		if (dataTable.getTableHeaders().size() > 0)
		{
			for (String header : dataTable.getTableHeaders())
			{
				dataTableString += header + ", ";
			}

			dataTableString = dataTableString.substring(0, dataTableString.length() - 2);
		}

		dataTableString += "\nTable Parameters: ";

		if (!dataTable.getTableParams().isEmpty())
		{
			for (Map.Entry<String, String> param : dataTable.getTableParams().entrySet())
			{
				dataTableString += param.getKey() + "=" + param.getValue() + ", ";
			}

			dataTableString = dataTableString.substring(0, dataTableString.length() - 2);
		}

		dataTableString += "\nTable Rows: ";

		if (dataTable.getRowCount() > 0)
		{
			for (Map<String, String> row : dataTable.getTableRows())
			{
				dataTableString += "\n\t[ ";

				for (Map.Entry<String, String> entry : row.entrySet())
				{
					dataTableString += entry.getKey() + "=" + entry.getValue() + ", ";
				}

				dataTableString = dataTableString.substring(0, dataTableString.length() - 2);
				dataTableString += " ]";
			}
		}

		return dataTableString;
	}

	public void testGetCode()
	{
		System.out.println("Test getCode");

		// response 1
		String expResult = "OK";
		String result = response1.getCode();
		assertEquals(expResult, result);

		// response 2
		String expResult2 = "InvalidParameter";
		String result2 = response2.getCode();
		assertEquals(expResult2, result2);
	}

	public void testGetErrorMessage()
	{
		System.out.println("Test getErrorMessage");

		// response 1
		String expResult = "";
		String result = response1.getErrorMessage();
		assertEquals(expResult, result);

		// response 2
		String expResult2 = "Sorry but folder 'test' already exists, please choose unique name!";
		String result2 = response2.getErrorMessage();
		assertEquals(expResult2, result2);
	}

	public void testGetFullError()
	{
		System.out.println("Test getFullError");

		// response 1
		String expResult = "";
		String result = response1.getFullError();
		assertEquals(expResult, result);

		// response 2
		String expResult2 = "Majestic12.SearchIndexReportResponse+ResponseException: Sorry but folder 'test' already exists, please choose unique name!\r\n   at Majestic12.SearchIndexReportRequest_CreateFolder.GetReport(SearchConfig oConfig, SearchIndex oSI, SearchIndexReportRequest oReq, SearchIndexReportManager oSIRM) in W:\\VersionFiles\\WorldSource\\MJ12searchLib\\ReportEngine_Admin.cs:line 7091\r\n   at Majestic12.SearchIndexReportManager.RunReport(SearchConfig oConfig, SearchIndex oSI, SearchIndexReportRequest oReq, Boolean bAvoidQueueing, SearchIndexReportResponse& oRes) in W:\\VersionFiles\\WorldSource\\MJ12searchLib\\SearchIndexReportManager.cs:line 2285";
		String result2 = response2.getFullError();
		assertEquals(expResult2, result2);
	}

	public void testGetGlobalParams()
	{
		System.out.println("Test getGlobalParams");

		// response 1
		String expResult = "{APIAccess=0, BusinessName=Majestic SEO, BusinessStatus=AutoOnlineVerified, BusinessVAT=SI81684991, Country=, Created=16/09/2010 15:10:05, CustomerCreditLimit=0, CustomerCredits=0.00, CustomerEmail=fujayelk@majesticseo.com, CustomerIsAdmin=0, CustomerName=fujayel, CustomerSubscriptions=1, DeveloperAPIAccess=1, EmailAddress=fujayelk@majesticseo.com, EmailPref=0, Name=fujayel, ServerName=LEEPFROG, ThirdPartyAPIAccess=0, Updated=18/02/2011 11:07:49}";
		String result = response1.getGlobalParams().toString();
		assertEquals(expResult, result);

		// response 2
		String expResult2 = "{APIAccess=0, CustomerCreditLimit=0, CustomerCredits=0.00, CustomerEmail=fujayelk@majesticseo.com, CustomerIsAdmin=0, CustomerName=fujayel, CustomerSubscriptions=1, DeveloperAPIAccess=1, ServerName=LEEPFROG, ThirdPartyAPIAccess=0}";
		String result2 = response2.getGlobalParams().toString();
		assertEquals(expResult2, result2);
	}

	public void testGetParamForName()
	{
		System.out.println("Test getParamForName");

		// response 1
		String expResult1 = "Majestic SEO";
		String result1 = response1.getParamForName("BusinessName");
		assertEquals(expResult1, result1);

		String expResult2 = "SI81684991";
		String result2 = response1.getParamForName("BusinessVAT");
		assertEquals(expResult2, result2);

		// response 2
		String expResult3 = "0.00";
		String result3 = response2.getParamForName("CustomerCredits");
		assertEquals(expResult3, result3);

		String expResult4 = "1";
		String result4 = response2.getParamForName("CustomerSubscriptions");
		assertEquals(expResult4, result4);

		assertNull(response2.getParamForName("Nothing")); // non-existent parameter
	}

	public void testGetTableForName()
	{
		System.out.println("Test getTableForName");

		// response 1 - no data tables
		String expResult = "Table Name: \nTable Headers: \nTable Parameters: \nTable Rows: ";

		String result = getDataTableAsString(response1.getTableForName(null)); // test for null parameter
		assertEquals(expResult, result);

		String result2 = getDataTableAsString(response1.getTableForName("Nothing")); // test for non-existent parameter
		assertEquals(expResult, result2);

		// response 3
		String expResult2 = "Table Name: Folders\nTable Headers: FolderName, Created, FoldersInside, DomainsInside\nTable Parameters: RowsCount=5\nTable Rows: \n\t[ FolderName=&, Created=13/09/2010 11:36:14, FoldersInside=0, DomainsInside=0 ]\n\t[ FolderName=a, Created=07/09/2010 17:15:16, FoldersInside=2, DomainsInside=0 ]\n\t[ FolderName=Aston, Created=17/09/2010 17:05:43, FoldersInside=1, DomainsInside=0 ]\n\t[ FolderName=Aston &, Created=17/09/2010 17:05:36, FoldersInside=0, DomainsInside=0 ]\n\t[ FolderName=Enter folder name here, Created=15/09/2010 17:07:22, FoldersInside=1, DomainsInside=0 ]";
		String result3 = getDataTableAsString(response3.getTableForName("Folders"));
		assertEquals(expResult2, result3);

		String expResult3 = "Table Name: StandardReports\nTable Headers: ReportName, ReportCode, ReportLocked, Created, Status, URL, SubDomain, Domain, TotalUrlBackLinks, TotalSubDomainBackLinks, TotalDomainBackLinks, ExternalUrlBackLinks, ExternalSubDomainBackLinks, ExternalDomainBackLinks, AvailableUrlBackLinks, AvailableSubDomainBackLinks, AvailableDomainBackLinks, UrlRefDomains, SubDomainRefDomains, DomainRefDomains, AvailableUrlRefDomains, AvailableSubDomainRefDomains, AvailableDomainRefDomains\nTable Parameters: RowsCount=0\nTable Rows: ";
		String result4 = getDataTableAsString(response3.getTableForName("StandardReports"));
		assertEquals(expResult3, result4);

		String result5 = getDataTableAsString(response3.getTableForName("Nothing"));
		assertEquals(expResult, result5);
	}

	public void testIsOK()
	{
		System.out.println("Test isOK");

		// response 1
		boolean expResult = true;
		boolean result = response1.isOK();
		assertEquals(expResult, result);

		//response 2
		boolean expResult2 = false;
		boolean result2 = response2.isOK();
		assertEquals(expResult2, result2);
	}
}