Java Majestic API Connector
===========================

The Java connector targets the 1.5 language specification, and is built using Maven.

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