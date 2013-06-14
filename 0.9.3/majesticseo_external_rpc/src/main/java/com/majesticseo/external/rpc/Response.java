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

import java.io.InputStream;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class Response {

    private static SAXParserFactory _spf;

    static {
        _spf = SAXParserFactory.newInstance();
    }
    private Map<String, String> responseAttributes;
    private Map<String, String> params;
    private Map<String, DataTable> tables;

    /**
     * Constructor for correct response
     * @param response response to parse
     */
    public Response(InputStream response) {
        responseAttributes = new LinkedHashMap<String, String>();
        params = new LinkedHashMap<String, String>();
        tables = new LinkedHashMap<String, DataTable>();

        if (response != null) {
            try {
                SAXParser saxParser = _spf.newSAXParser();
                Handler handler = new Handler(this);
                saxParser.parse(response, handler);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Response(String code, String errorMessage) {
        this(null);
        responseAttributes.put("Code", code);
        responseAttributes.put("ErrorMessage", errorMessage);
        responseAttributes.put("FullError", errorMessage);
    }

    /**
     * Returns the response's attributes
     * @return The response's attributes
     */
    public Map<String, String> getResponseAttributes() {
        return responseAttributes;
    }

    /**
     * Returns whether the response's code is ok
     * @return Whether the response's code is ok
     */
    public boolean isOK() {
        return "OK".equals(responseAttributes.get("Code"));
    }

    /**
     * Returns the response's code
     * @return Response's code
     */
    public String getCode() {
        return responseAttributes.get("Code");
    }

    /**
     * Returns the response's error message
     * @return Response's error message
     */
    public String getErrorMessage() {
        return responseAttributes.get("ErrorMessage");
    }

    /**
     * Returns the response's full error message
     * @return Response's full error message
     */
    public String getFullError() {
        return responseAttributes.get("FullError");
    }

    /**
     * Returns a hashmap with the response's global parameters
     * @return Response's global parameters
     */
    public Map<String, String> getGlobalParams() {
        return params;
    }

    /**
     * Returns a specific parameter from the response's parameters
     * @param name - name of parameter
     * @return Specific parameter from the response's parameter
     */
    public String getParamForName(String name) {
        return params.get(name);
    }

    /**
     * Returns the response's data tables
     * @return The response's data tables
     */
    public Map<String, DataTable> getTables() {
        return tables;
    }

    /**
     * Returns a data table object from the response's data tables
     * @param name name of table
     * @return Data table object
     */
    public DataTable getTableForName(String name) {
        if (!(tables.containsKey(name))) {
            return new DataTable();
        }

        return tables.get(name);
    }

    /**
     * SAX Parser Handler for Majestic SEO's API data
     */
    private static class Handler extends DefaultHandler {

        private Response response;
        private DataTable dataTable;
        private boolean isRow;
        private StringBuilder row;

        public Handler(Response response) {
            this.response = response;
            isRow = false;
            row = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attrs)
                throws SAXParseException, SAXException {

            if (qName.equals("Result")) {
                for (int i = 0; i < attrs.getLength(); i++) {
                    response.getResponseAttributes().put(attrs.getQName(i), attrs.getValue(i));
                }
            }

            if (qName.equals("GlobalVars")) {
                for (int i = 0; i < attrs.getLength(); i++) {
                    response.getGlobalParams().put(attrs.getQName(i), attrs.getValue(i));
                }
            }

            if (qName.equals("DataTable")) {
                dataTable = new DataTable();
                dataTable.setTableName(attrs.getValue("Name"));
                dataTable.setTableHeaders(attrs.getValue("Headers"));

                for (int i = 0; i < attrs.getLength(); i++) {
                    if (!"Name".equals(attrs.getQName(i))
                            && !"Headers".equals(attrs.getQName(i))) {
                        dataTable.setTableParams(attrs.getQName(i), attrs.getValue(i));
                    }
                }

                response.getTables().put(dataTable.getTableName(), dataTable);
            }

            if (qName.equals("Row")) {
                isRow = true;
            }
        }

        @Override
        public void characters(char ch[], int start, int length)
                throws SAXException {

            if (isRow) {
                row.append(ch, start, length);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {

            if (qName.equals("Row")) {
                dataTable.setTableRow(row.toString());
                isRow = false;
                row = new StringBuilder();
            }
        }
    }
}
