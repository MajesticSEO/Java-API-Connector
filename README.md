JAVA-API-Connector
====================

 
Use the https://majestic.com connectors to access web data from one of the largest web indexes available. 
The Majestic® search engine is mainly used to instantly provide Flow Metrics® which evaluate the any page on the Internet in scores between 0 and 100.
 

For set up(note: the following has only been tested in Windows)
---------------

Clone the repository in a directory.\

navaigate to the src folder and run the flowing from your terminal:

```
mvn install
```
After that navigate into samples and run:
```
mvn package
```

To run the samples you'll need mutiple jars run the following from the JAVA-API-connector directory
```
java -cp "src\target\majesticseo-external-rpc-0.9.6-SNAPSHOT.jar;samples\target\sample-0.9.5.jar" com.majesticseo.external.rpc.sample.GetIndexItemInfo
```


Examples
-------------



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

A full list of available commands can be found within the [developer documentation](https://developer-support.majestic.com/api/commands/).
				


Further notes  
------------------

The Java connector targets the 1.5 language specification, and is built using Maven.


For further information see api documentation @ https://developer-support.majestic.com/
