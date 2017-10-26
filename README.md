JAVA-API-Connector
====================
 
Use the https://majestic.com connectors to access web data from one of the largest web indexes available. 
The Majestic® search engine is mainly used to instantly provide Flow Metrics® which evaluate the any page on the Internet in scores between 0 and 100.
 

For set up(note: the following has only been tested in Windows)
----------------------------------------------------------------

Clone the repository in a directory.

navaigate to the src folder and run the flowing from your terminal:

```
mvn install
```
After that navigate into samples and run:
```
mvn package
```

Now that this is done the samples should be compiled into a single jar in the target folder of the samples.
To run a specified sample you will need to run the following from the JAVA-API-Connetors directory:

```
java -cp "src\target\majesticseo-external-rpc-0.9.6-SNAPSHOT.jar;samples\target\sample-0.9.5.jar" com.majesticseo.external.rpc.sample.GetIndexItemInfo
```


Examples
-------------
There are a few examples of using the API-Connector by the Jar, use the following commands:

* GetIndexItemInfo 
  * The GetIndexItemInfo command provides data on the number of backlinks to any web page or site, linking domains and the main topics for that page or web site
* GetTopBackLinks 
  * GetBacklinkData will return rows of data with information about all the pages linking to a given URL or domain
  
The follwoing code is from GetIndexItemInfo.java, it will shows how the API-Connector can be used.
```Java
APIService api = new APIService("MY_API_KEY", "https://api.majestic.com/api_command");

Map<String, String> parameters = new HashMap<String, String>();
parameters.put("items", "1");
parameters.put("item0", "majestic.com");

Response response = api.executeCommand("GetIndexItemInfo", parameters);

if(response.isOK())
{
    DataTable dataTable = response.getTableForName("Results");
    List<Map<String, String>> rows = dataTable.getTableRows();
    Map<String, String> result = rows.get(0);

    System.out.println(result.get("ExtBackLinks"));
}
else
{
    System.out.println(response.getErrorMessage());
}
```

Further notes  
------------------

The Java connector targets the 1.5 language specification, and is built using Maven.

A full list of available commands can be found within the [developer documentation](https://developer-support.majestic.com/api/commands/).

For further information see api documentation @ https://developer-support.majestic.com/
