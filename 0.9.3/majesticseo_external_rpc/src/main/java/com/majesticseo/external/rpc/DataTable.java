/*
 * Version 0.9.3
 *
 * Copyright (c) 2011, Majestic-12 Ltd
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   1. Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *   2. Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *   3. Neither the name of the Majestic-12 Ltd nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Majestic-12 Ltd BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package com.majesticseo.external.rpc;

import java.util.*;

public class DataTable {

    private String tableName;
    private List<String> tableHeaders;
    private Map<String, String> tableParams;
    private List<Map<String, String>> tableRows;

    public DataTable() {
        tableName = "";
        tableHeaders = new ArrayList<String>();
        tableParams = new LinkedHashMap<String, String>();
        tableRows = new ArrayList<Map<String, String>>();
    }

    /**
     * Set the table's name
     * @param name name of table
     */
    public void setTableName(String name) {
        tableName = name;
    }

    /**
     * Set the table's headers
     * @param headers table's headers
     */
    public void setTableHeaders(String headers) {
        String[] headersArray = split(headers);
        tableHeaders = new ArrayList<String>(headersArray.length);
        Collections.addAll(tableHeaders, headersArray);
    }

    /**
     * Sets a table parameter
     * @param name name of parameter
     * @param value value of parameter
     */
    public void setTableParams(String name, String value) {
        tableParams.put(name, value);
    }

    /**
     * Sets a table row
     * @param row row of the table
     */
    public void setTableRow(String row) {
        LinkedHashMap<String, String> rowsHash = new LinkedHashMap<String, String>();
        String[] elements = split(row);

        for (int index = 0; index < elements.length; index++) {
            if (elements[index].equals(" ")) {
                elements[index] = "";
            }

            rowsHash.put(tableHeaders.get(index), elements[index]);
        }

        tableRows.add(rowsHash);
    }

    private String[] split(String value) {
        String[] array = value.split("(?<!\\|)\\|(?!\\|)", -1);

        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].replace("||", "|");
        }

        return array;
    }

    /**
     * Returns the table's name
     * @return The table's name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Returns the table's headers
     * @return The table's headers
     */
    public List<String> getTableHeaders() {
        return tableHeaders;
    }

    /**
     * Returns the table's parameters
     * @return The table's parameters
     */
    public Map<String, String> getTableParams() {
        return tableParams;
    }

    /**
     * Returns a specific parameter from the table's parameters
     * @param name name of parameter
     * @return Specific parameter from the table's parameters or null if it does not exist
     */
    public String getParamForName(String name) {
        return tableParams.get(name);
    }

    /**
     * Returns the number of rows in the table
     * @return The number of rows in the table
     */
    public int getRowCount() {
        return tableRows.size();
    }

    /**
     * Returns the table's rows
     * @return The table's rows
     */
    public List<Map<String, String>> getTableRows() {
        return tableRows;
    }
}
