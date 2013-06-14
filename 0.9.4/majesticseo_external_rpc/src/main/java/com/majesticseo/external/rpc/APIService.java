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

import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class APIService
{
	/**
	 * The default timeout used if not specified when executing the request.
	 */
	private static final int DEFAULT_TIMEOUT = 5;

	private final String applicationId;
	private final String endpoint;

	/**
	 * Constructs an instance of this class
	 * 
	 * @param applicationId is your api key and must be provided.
	 * @param endpoint is required and must point to the url you wish to target, ie: enterprise or developer.
	 */
	public APIService(String applicationId, String endpoint)
	{
		this.applicationId = applicationId;
		this.endpoint = endpoint;
	}

	/**
	 * Executes the requested command as an api request.
	 * 
	 * @param name name of the command.
	 * @param parameters a map of command parameters.
	 * @return the response
	 */
	public final Response executeCommand(String name, Map<String, String> parameters)
	{
		return executeCommand(name, parameters, DEFAULT_TIMEOUT);
	}

	/**
	 * Executes the requested command as an api request.
	 * 
	 * @param name name of the command.
	 * @param parameters a map of command parameters.
	 * @param timeout the amount of time to wait, expressed in seconds, before aborting the request.
	 * @return the response
	 */
	public final Response executeCommand(String name, Map<String, String> parameters, int timeout)
	{
		Map<String, String> parameterCopy = new HashMap<String, String>(parameters);
		parameterCopy.put("app_api_key", applicationId);
		parameterCopy.put("cmd", name);

		return executeRequest(parameterCopy, timeout);
	}

	/**
	 * Executes the requested command as an OpenApp request.
	 * 
	 * @param commandName name of the command.
	 * @param parameters a map of command parameters.
	 * @param accessToken the token the user provided to access their resources.
	 * @return the response
	 */
	public final Response executeOpenAppRequest(String commandName, Map<String, String> parameters, String accessToken)
	{
		return executeOpenAppRequest(commandName, parameters, accessToken, DEFAULT_TIMEOUT);
	}

	/**
	 * Executes the requested command as an OpenApp request.
	 * 
	 * @param commandName name of the command.
	 * @param parameters a map of command parameters.
	 * @param accessToken the token the user provided to access their resources.
	 * @param timeout the amount of time to wait, expressed in seconds, before aborting the request.
	 * @return the response
	 */
	public final Response executeOpenAppRequest(String commandName, Map<String, String> parameters, String accessToken, int timeout)
	{
		Map<String, String> parameterCopy = new HashMap<String, String>(parameters);
		parameterCopy.put("accesstoken", accessToken);
		parameterCopy.put("cmd", commandName);
		parameterCopy.put("privatekey", applicationId);

		return executeRequest(parameterCopy, timeout);
	}

	/**
	 * Executes the request against the target <code>endpoint</code>.
	 * 
	 * @param parameters a map of command parameters.
	 * @param timeout the amount of time to wait, expressed in seconds, before aborting the request.
	 * @return the response
	 */
	private Response executeRequest(Map<String, String> parameters, int timeout)
	{
		try
		{
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : parameters.entrySet())
			{
				sb.append(entry.getKey()).append("=");
				sb.append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
			}

			String queryString = sb.substring(0, sb.length() - 1);

			HttpURLConnection connection = (HttpURLConnection) new URL(endpoint).openConnection();
			connection.setConnectTimeout(timeout * 1000);
			connection.setDoOutput(true);
			connection.setReadTimeout(timeout * 1000);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.getOutputStream().write(queryString.getBytes());

			return new Response(connection.getInputStream());
		}
		catch (FileNotFoundException e)
		{
			return new Response("Invalid Endpoint", e.getMessage());
		}
		catch (Exception e)
		{
			return new Response("ConnectionError", e.getMessage());
		}
	}
}
