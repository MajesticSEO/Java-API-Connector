package com.majesticseo.external.rpc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import junit.framework.TestCase;

/**
 *
 * @author fujayelk
 */
public class DataTableTest extends TestCase {

    private Response response;
    private DataTable table1;
    private DataTable table2;
    private DataTable table3;
    private DataTable table4;
    private DataTable table5;

    public DataTableTest(String testName) {
        super(testName);
        response = new Response(convertStringToInputStream("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<Result Code=\"OK\" ErrorMessage=\"\" FullError=\"\">\r\n\t\t<GlobalVars APIAccess=\"0\" CustomerAdvancedReportsRemaining=\"0\" CustomerAvailableAnalysisResUnits=\"0\" CustomerCreditLimit=\"0\" CustomerCredits=\"50.00\" CustomerEmail=\"fujayelk3@majestic12.co.uk\" CustomerIsAdmin=\"0\" CustomerName=\"fujayelk3\" CustomerStandardReportBacklinksShown=\"0\" CustomerStandardReportsRemaining=\"0\" CustomerSubscriptions=\"0\" DeveloperAPIAccess=\"0\" ServerName=\"LOTTE\" ThirdPartyAPIAccess=\"0\"/>\r\n\t<DataTables Count=\"3\">\r\n\t\t<DataTable Name=\"Folders\" RowsCount=\"5\" Headers=\"FolderName|Created|FoldersInside|DomainsInside\">\r\n\t\t\t<Row>&#38;|13/09/2010 11:36:14|0|0</Row>\r\n\t\t\t<Row>a|07/09/2010 17:15:16|2|0</Row>\r\n\t\t\t<Row>Aston|17/09/2010 17:05:43|1|0</Row>\r\n\t\t\t<Row>Aston &#38;|17/09/2010 17:05:36|0|0</Row>\r\n\t\t\t<Row>Enter folder name here|15/09/2010 17:07:22|1|0</Row>\r\n\t\t</DataTable>\r\n\t\t<DataTable Name=\"AdvancedReports\" RowsCount=\"3\" Headers=\"ReportName|ReportCode|ReportLocked|Domain|LastUpdated|Status|SubDomains|Pages|Links|TotalBackLinks|ExtBackLinks|ExtDomains|ExtUniqueIPs|MaxPageACRank|MaxRefACRank|FreshNewBackLinks|FreshNewRefDomains|HumanLastFreshUpdateDate|LastFreshUpdateDate|AnalysisProgressInfo|Expires\">\r\n\t\t\t<Row>aston.ac.uk|01401545E146F5AA|0|aston.ac.uk|07/10/2010 16:21:28|Expired|790|85381|175757|446562|375435|22664|13599|11|12|10488|902|22 Oct 2010|2010-10-22| |07/10/2010 14:46:39</Row>\r\n\t\t\t<Row>majesticseo.com|35386CE11949C3C4|0|majesticseo.com|15/09/2010 10:02:19|Expired|82|4335|106744|244664|241031|1638|1366|10|11|137539|354|22 Oct 2010|2010-10-22| |15/09/2010 09:24:52</Row>\r\n\t\t\t<Row>birmingham.ac.uk|A879946EE693E277|0|birmingham.ac.uk|20/09/2010 11:57:30|Expired|27|342|76|5233|3913|1673|1038|10|11|1420|74|22 Oct 2010|2010-10-22| |20/09/2010 11:55:15</Row>\r\n\t\t</DataTable>\r\n\t\t<DataTable Name=\"StandardReports\" RowsCount=\"0\" Headers=\"ReportName|ReportCode|ReportLocked|Created|Status|URL|SubDomain|Domain|TotalUrlBackLinks|TotalSubDomainBackLinks|TotalDomainBackLinks|ExternalUrlBackLinks|ExternalSubDomainBackLinks|ExternalDomainBackLinks|AvailableUrlBackLinks|AvailableSubDomainBackLinks|AvailableDomainBackLinks|UrlRefDomains|SubDomainRefDomains|DomainRefDomains|AvailableUrlRefDomains|AvailableSubDomainRefDomains|AvailableDomainRefDomains\">\r\n\t\t</DataTable>\r\n\t\t<DataTable Name=\"SplitData\" RowsCount=\"1\" Headers=\"a|b|c||c|d|e|||f|||g|h|||i|j\">\r\n\t\t\t<Row>a|b|c||c|d|e|||f|||g|h|||i|j</Row>\r\n\t\t</DataTable>\r\n\t</DataTables>\r\n</Result>\r\n"));
        table1 = response.getTableForName("Folders");
        table2 = response.getTableForName("AdvancedReports");
        table3 = response.getTableForName("StandardReports");
        table4 = response.getTableForName("SplitData");
        table5 = new DataTable();
    }

    public void testGetTableHeaders() {
        System.out.println("getTableHeaders");

        // table 1
        String expResult = "[FolderName, Created, FoldersInside, DomainsInside]";
        String result = table1.getTableHeaders().toString();
        assertEquals(expResult, result);

        // table 3
        String expResult2 = "[ReportName, ReportCode, ReportLocked, Created, Status, URL, SubDomain, Domain, TotalUrlBackLinks, TotalSubDomainBackLinks, TotalDomainBackLinks, ExternalUrlBackLinks, ExternalSubDomainBackLinks, ExternalDomainBackLinks, AvailableUrlBackLinks, AvailableSubDomainBackLinks, AvailableDomainBackLinks, UrlRefDomains, SubDomainRefDomains, DomainRefDomains, AvailableUrlRefDomains, AvailableSubDomainRefDomains, AvailableDomainRefDomains]";
        String result2 = table3.getTableHeaders().toString();
        assertEquals(expResult2, result2);

        // table 5
        String expResult3 = "[]";
        String result3 = table5.getTableHeaders().toString();
        assertEquals(expResult3, result3);
    }

    public void testParamForName() {
        System.out.println("paramForName");

        // table 2
        String expResult = "3";
        String result = table2.getParamForName("RowsCount");
        assertEquals(expResult, result);

        // table 5
        assertNull(table5.getParamForName("RowsCount"));
    }

    public void testGetRowCount() {
        System.out.println("getRowCount");

        // table 1
        int expResult = 5;
        int result = table1.getRowCount();
        assertEquals(expResult, result);

        // table 5
        int expResult2 = 0;
        int result2 = table5.getRowCount();
        assertEquals(expResult2, result2);
    }

    public void testGetRowsAsArray() {
        System.out.println("getRowsAsArray");

        // table 1
        String expResult = "[{FolderName=&, Created=13/09/2010 11:36:14, FoldersInside=0, DomainsInside=0}, {FolderName=a, Created=07/09/2010 17:15:16, FoldersInside=2, DomainsInside=0}, {FolderName=Aston, Created=17/09/2010 17:05:43, FoldersInside=1, DomainsInside=0}, {FolderName=Aston &, Created=17/09/2010 17:05:36, FoldersInside=0, DomainsInside=0}, {FolderName=Enter folder name here, Created=15/09/2010 17:07:22, FoldersInside=1, DomainsInside=0}]";
        String result = table1.getTableRows().toString();
        assertEquals(expResult, result);

        // table 3
        String expResult2 = "[]";
        String result2 = table3.getTableRows().toString();
        assertEquals(expResult2, result2);

        // table 5
        String expResult3 = "[]";
        String result3 = table5.getTableRows().toString();
        assertEquals(expResult3, result3);
    }

    public void testGetTableName() {
        System.out.println("getTableName");

        // table 2
        String expResult = "AdvancedReports";
        String result = table2.getTableName();
        assertEquals(expResult, result);

        // table 5
        String expResult2 = "";
        String result2 = table5.getTableName();
        assertEquals(expResult2, result2);
    }

    public void testSplit() {
        System.out.println("split");

        // table 4
        String[] expResult = {"a", "b", "c|c", "d", "e||f||g", "h||i", "j"};
        String[] actResult = new String[7];
        int index = 0;

        for (Map<String, String> row : table4.getTableRows()) {
            Set<String> keys = row.keySet();
            for (String key : keys) {
                String value = row.get(key);
                actResult[index] = value;
                index++;
            }
        }

        assertTrue(Arrays.equals(expResult, actResult));
    }

    private InputStream convertStringToInputStream(String text) {
        InputStream is = new ByteArrayInputStream(text.getBytes());
        return is;
    }
}
