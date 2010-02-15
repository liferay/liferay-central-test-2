/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.webdav;

import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.service.BaseServiceJUnit4TestCase;
import com.liferay.portlet.documentlibrary.webdav.DLWebDAVStorageImpl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * <a href="BaseWebDAVTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class BaseWebDAVTest extends BaseServiceJUnit4TestCase {

	public enum Method {
		COPY, DELETE, GET, HEAD, LOCK, MKCOL, MOVE, OPTIONS, PROPFIND,
		PROPPATCH, PUT, UNLOCK
	}

	@BeforeClass
	public static void setUpOnce() {
		Tuple tuple = service(
			Method.MKCOL, "", null, null);

		if (getStatusCode(tuple) == HttpServletResponse.SC_METHOD_NOT_ALLOWED) {
			service(Method.DELETE, "", null, null);

			tuple = service(
				Method.MKCOL, "", null, null);

			assertEquals(HttpServletResponse.SC_CREATED, getStatusCode(tuple));
		}
	}

	@AfterClass
	public static void tearDownOnce() {
		service(Method.DELETE, "", null, null);
	}

	protected static Map<String, String> getHeaders(Tuple tuple) {
		return (Map<String, String>)tuple.getObject(2);
	}

	protected static int getStatusCode(Tuple tuple) {
		return (Integer)tuple.getObject(0);
	}

	protected static byte[] getResponseBody(Tuple tuple) {
		return (byte[])tuple.getObject(1);
	}

	protected static String getResponseBodyString(Tuple tuple) {
		byte[] data = getResponseBody(tuple);

		return new String(data);
	}

	protected static String getUserAgent() {
		return _DEFAULT_USER_AGENT;
	}

	protected static Tuple service(
			Method method, String path, Map<String, String> headers,
			byte[] data) {

		if (headers == null) {
			headers = new HashMap<String, String>();
		}

		headers.put(HttpHeaders.USER_AGENT, getUserAgent());

		try {
			throw new Exception();
		}
		catch (Exception e) {
			StackTraceElement[] stackTrace = e.getStackTrace();

			for (StackTraceElement element : stackTrace) {
				String methodName = element.getMethodName();

				if (methodName.equals("setUpOnce") ||
					methodName.equals("tearDownOnce") ||
					methodName.startsWith("test")) {

					String testName = StringUtil.extractLast(
						element.getClassName(), StringPool.PERIOD);

					testName = StringUtil.replace(
						testName,
						new String[] { "WebDAV", "Test" },
						new String[] {"", ""});

					headers.put(
						"X-Litmus",
						testName + ": (" + element.getMethodName() + ")");

					break;
				}
			}
		}

		String requestURI =
			_CONTEXT_PATH + _SERVLET_PATH + _PATH_INFO_PREFACE + path;

		MockHttpServletRequest request = new MockHttpServletRequest(
			method.toString(), requestURI);

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

	protected static Tuple serviceCopyOrMove(
		Method method, String path, Map<String, String> headers,
		String destination, int depth, boolean overwrite) {

		if (headers == null) {
			headers = new HashMap<String, String>();
		}

		headers.put("Destination", _PATH_INFO_PREFACE + destination);
		headers.put("Depth", _getDepth(depth));
		headers.put("Overwrite", _getOverwrite(overwrite));

		return service(method, path, headers, null);
	}

	protected static Tuple serviceLock(
		String path, Map<String, String> headers, byte[] data, int depth,
		int timeout, boolean overwrite) {

		if (headers == null) {
			headers = new HashMap<String, String>();
		}

		headers.put("Timeout", "Second-" + timeout);
		headers.put("Depth", _getDepth(depth));
		headers.put("Overwrite", _getOverwrite(overwrite));

		return service(Method.LOCK, path, headers, data);
	}

	protected static Tuple serviceUnlock(
		String path, Map<String, String> headers, String lockToken) {

		if (headers == null) {
			headers = new HashMap<String, String>();
		}

		headers.put("Lock-Token", lockToken);

		return service(Method.UNLOCK, path, headers, null);
	}

	private static String _getDepth(int depth) {
		String depthString = "infinity";

		if (depth == 0) {
			depthString = "0";
		}

		return depthString;
	}

	private static String _getOverwrite(boolean overwrite) {
		String overwriteString = "F";

		if (overwrite) {
			overwriteString = "T";
		}

		return overwriteString;
	}

	private static String _DEFAULT_USER_AGENT = "Liferay-litmus";

	private static String _CONTEXT_PATH = "/webdav";

	private static String _SERVLET_PATH = "";

	private static String _PATH_INFO_PREFACE =
		"/liferay.com/guest/document_library/WebDAVTest/";

}