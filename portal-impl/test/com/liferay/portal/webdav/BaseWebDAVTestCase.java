/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.webdav;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.webdav.methods.Method;
import com.liferay.portlet.documentlibrary.webdav.DLWebDAVStorageImpl;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * <a href="BaseWebDAVTestCase.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class BaseWebDAVTestCase extends TestCase {

	public Tuple service(
		String method, String path, Map<String, String> headers, byte[] data) {

		if (headers == null) {
			headers = new HashMap<String, String>();
		}

		headers.put(HttpHeaders.USER_AGENT, getUserAgent());

		try {
			throw new Exception();
		}
		catch (Exception e) {
			StackTraceElement[] stackTraceElements = e.getStackTrace();

			for (StackTraceElement stackTraceElement : stackTraceElements) {
				String methodName = stackTraceElement.getMethodName();

				if (methodName.equals("setUp") ||
					methodName.equals("tearDown") ||
					methodName.startsWith("test")) {

					String testName = StringUtil.extractLast(
						stackTraceElement.getClassName(), StringPool.PERIOD);

					testName = StringUtil.replace(
						testName,
						new String[] {"WebDAV", "Test"},
						new String[] {"", ""});

					headers.put(
						"X-Litmus",
						testName + ": (" + stackTraceElement.getMethodName() +
							")");

					break;
				}
			}
		}

		String requestURI =
			_CONTEXT_PATH + _SERVLET_PATH + _PATH_INFO_PREFACE + path;

		MockHttpServletRequest request = new MockHttpServletRequest(
			method, requestURI);

		MockHttpServletResponse response = new MockHttpServletResponse();

		request.setContextPath(_CONTEXT_PATH);
		request.setServletPath(_SERVLET_PATH);
		request.setPathInfo(_PATH_INFO_PREFACE + path);

		if (data != null) {
			request.setContent(data);

			if (headers.containsKey(HttpHeaders.CONTENT_TYPE)) {
				request.setContentType(
					headers.remove(HttpHeaders.CONTENT_TYPE));
			}
			else {
				request.setContentType(ContentTypes.TEXT_PLAIN);
			}
		}

		for (String header : headers.keySet()) {
			request.addHeader(header, headers.get(header));
		}

		try {
			DLWebDAVStorageImpl storage = new DLWebDAVStorageImpl();

			storage.setToken("document_library");

			WebDAVUtil.addStorage(storage);

			WebDAVServlet servlet = new WebDAVServlet();

			servlet.service(request, response);

			int statusCode = response.getStatus();
			byte[] responseBody = response.getContentAsByteArray();

			Map<String, String> responseHeaders = new HashMap<String, String>();

			for (String name : response.getHeaderNames()) {
				responseHeaders.put(name, (String)response.getHeader(name));
			}

			return new Tuple(statusCode, responseBody, responseHeaders);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Tuple serviceCopyOrMove(
		String method, String path, Map<String, String> headers,
		String destination, int depth, boolean overwrite) {

		if (headers == null) {
			headers = new HashMap<String, String>();
		}

		headers.put("Depth", getDepth(depth));
		headers.put("Destination", _PATH_INFO_PREFACE + destination);
		headers.put("Overwrite", getOverwrite(overwrite));

		return service(method, path, headers, null);
	}

	public Tuple serviceLock(
		String path, Map<String, String> headers, byte[] data, int depth,
		int timeout, boolean overwrite) {

		if (headers == null) {
			headers = new HashMap<String, String>();
		}

		headers.put("Depth", getDepth(depth));
		headers.put("Overwrite", getOverwrite(overwrite));
		headers.put("Timeout", "Second-" + timeout);

		return service(Method.LOCK, path, headers, data);
	}

	public Tuple serviceUnlock(
		String path, Map<String, String> headers, String lockToken) {

		if (headers == null) {
			headers = new HashMap<String, String>();
		}

		headers.put("Lock-Token", lockToken);

		return service(Method.UNLOCK, path, headers, null);
	}

	protected String getDepth(int depth) {
		String depthString = "infinity";

		if (depth == 0) {
			depthString = "0";
		}

		return depthString;
	}

	protected Map<String, String> getHeaders(Tuple tuple) {
		return (Map<String, String>)tuple.getObject(2);
	}

	protected String getOverwrite(boolean overwrite) {
		String overwriteString = "F";

		if (overwrite) {
			overwriteString = "T";
		}

		return overwriteString;
	}

	protected byte[] getResponseBody(Tuple tuple) {
		return (byte[])tuple.getObject(1);
	}

	protected String getResponseBodyString(Tuple tuple) {
		byte[] data = getResponseBody(tuple);

		return new String(data);
	}

	protected int getStatusCode(Tuple tuple) {
		return (Integer)tuple.getObject(0);
	}

	protected String getUserAgent() {
		return _DEFAULT_USER_AGENT;
	}

	private static String _CONTEXT_PATH = "/webdav";

	private static String _DEFAULT_USER_AGENT = "Liferay-litmus";

	private static String _PATH_INFO_PREFACE =
		"/liferay.com/guest/document_library/WebDAVTest/";

	private static String _SERVLET_PATH = "";

}