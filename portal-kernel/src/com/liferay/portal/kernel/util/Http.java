/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.io.IOException;

import java.net.URL;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <a href="Http.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface Http {

	public static final String HTTP = "http";

	public static final int HTTP_PORT = 80;

	public static final String HTTP_WITH_SLASH = "http://";

	public static final String HTTPS = "https";

	public static final int HTTPS_PORT = 443;

	public static final String HTTPS_WITH_SLASH = "https://";

	public static final String PROTOCOL_DELIMITER = "://";

	public String addParameter(String url, String name, boolean value);

	public String addParameter(String url, String name, double value);

	public String addParameter(String url, String name, int value);

	public String addParameter(String url, String name, long value);

	public String addParameter(String url, String name, short value);

	public String addParameter(String url, String name, String value);

	public String decodeURL(String url);

	public String decodeURL(String url, boolean unescapeSpace);

	public String encodeURL(String url);

	public String encodeURL(String url, boolean escapeSpaces);

	public String getCompleteURL(HttpServletRequest request);

	public String getDomain(String url);

	public String getParameter(String url, String name);

	public String getParameter(String url, String name, boolean escaped);

	public Map<String, String[]> getParameterMap(String queryString);

	public String getProtocol(ActionRequest actionRequest);

	public String getProtocol(boolean secure);

	public String getProtocol(HttpServletRequest request);

	public String getProtocol(RenderRequest renderRequest);

	public String getProtocol(String url);

	public String getQueryString(String url);

	public String getRequestURL(HttpServletRequest request);

	public boolean hasDomain(String url);

	public boolean hasProxyConfig();

	public boolean isNonProxyHost(String host);

	public boolean isProxyHost(String host);

	public Map<String, String[]> parameterMapFromString(String queryString);

	public String parameterMapToString(Map<String, String[]> parameterMap);

	public String parameterMapToString(
		Map<String, String[]> parameterMap, boolean addQuestion);

	public String protocolize(String url, ActionRequest actionRequest);

	public String protocolize(String url, boolean secure);

	public String protocolize(String url, HttpServletRequest request);

	public String protocolize(String url, RenderRequest renderRequest);

	public String removeDomain(String url);

	public String removeParameter(String url, String name);

	public String removeProtocol(String url);

	public String setParameter(String url, String name, boolean value);

	public String setParameter(String url, String name, double value);

	public String setParameter(String url, String name, int value);

	public String setParameter(String url, String name, long value);

	public String setParameter(String url, String name, short value);

	public String setParameter(String url, String name, String value);

	public void submit(String location) throws IOException;

	public void submit(String location, boolean post) throws IOException;

	public void submit(String location, Cookie[] cookies) throws IOException;

	public void submit(String location, Cookie[] cookies, boolean post)
		throws IOException;

	public void submit(
			String location, Cookie[] cookies, Http.Body body, boolean post)
		throws IOException;

	public void submit(
			String location, Cookie[] cookies, Map<String, String> parts,
			boolean post)
		throws IOException;

	public byte[] URLtoByteArray(String location) throws IOException;

	public byte[] URLtoByteArray(String location, boolean post)
		throws IOException;

	public byte[] URLtoByteArray(String location, Cookie[] cookies)
		throws IOException;

	public byte[] URLtoByteArray(
			String location, Cookie[] cookies, boolean post)
		throws IOException;

	public byte[] URLtoByteArray(
			String location, Cookie[] cookies, Http.Body body, boolean post)
		throws IOException;

	public byte[] URLtoByteArray(
			String location, Cookie[] cookies, Map<String, String> parts,
			boolean post)
		throws IOException;

	public String URLtoString(String location) throws IOException;

	public String URLtoString(String location, boolean post) throws IOException;

	public String URLtoString(String location, Cookie[] cookies)
		throws IOException;

	public String URLtoString(
			String location, Cookie[] cookies, boolean post)
		throws IOException;

	public String URLtoString(
			String location, Cookie[] cookies, Http.Body body, boolean post)
		throws IOException;

	public String URLtoString(
			String location, Cookie[] cookies, Map<String, String> parts,
			boolean post)
		throws IOException;

	public String URLtoString(
			String location, String host, int port, String realm,
			String username, String password)
		throws IOException;

	/**
	 * This method only uses the default Commons HttpClient implementation when
	 * the URL object represents a HTTP resource. The URL object could also
	 * represent a file or some JNDI resource. In that case, the default Java
	 * implementation is used.
	 *
	 * @param		url URL object
	 * @return		A string representation of the resource referenced by the
	 *				URL object
	 * @throws		IOException
	 */
	public String URLtoString(URL url) throws IOException;

	public class Body {

		public Body(String content, String contentType, String charset) {
			_content = content;
			_contentType = contentType;
			_charset = charset;
		}

		public String getContent() {
			return _content;
		}

		public String getContentType() {
			return _contentType;
		}

		public String getCharset() {
			return _charset;
		}

		private String _content;
		private String _contentType;
		private String _charset;

	}

}