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

import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <a href="Http.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
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

	public Cookie[] getCookies();

	public String getDomain(String url);

	public String getIpAddress(String url);

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

	public boolean hasProtocol(String url);

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

	public byte[] URLtoByteArray(Http.Options options) throws IOException;

	public byte[] URLtoByteArray(String location) throws IOException;

	public byte[] URLtoByteArray(String location, boolean post)
		throws IOException;

	public String URLtoString(Http.Options options) throws IOException;

	public String URLtoString(String location) throws IOException;

	public String URLtoString(String location, boolean post) throws IOException;

	/**
	 * This method only uses the default Commons HttpClient implementation when
	 * the URL object represents a HTTP resource. The URL object could also
	 * represent a file or some JNDI resource. In that case, the default Java
	 * implementation is used.
	 *
	 * @return A string representation of the resource referenced by the URL
	 *		   object
	 */
	public String URLtoString(URL url) throws IOException;

	public class Auth {

		public Auth(
			String host, int port, String realm, String username,
			String password) {

			_host = host;
			_port = port;
			_realm = realm;
			_username = username;
			_password = password;
		}

		public String getHost() {
			return _host;
		}

		public String getPassword() {
			return _password;
		}

		public int getPort() {
			return _port;
		}

		public String getRealm() {
			return _realm;
		}

		public String getUsername() {
			return _username;
		}

		private String _host;
		private String _password;
		private int _port;
		private String _realm;
		private String _username;

	}

	public class Body {

		public Body(String content, String contentType, String charset) {
			_content = content;
			_contentType = contentType;
			_charset = charset;
		}

		public String getCharset() {
			return _charset;
		}

		public String getContent() {
			return _content;
		}

		public String getContentType() {
			return _contentType;
		}

		private String _charset;
		private String _content;
		private String _contentType;

	}

	public enum Method {

		DELETE, GET, POST, PUT
	}

	public class Options {

		public void addHeader(String name, String value) {
			if (_headers == null) {
				_headers = new HashMap<String, String>();
			}

			_headers.put(name, value);
		}

		public void addPart(String name, String value) {
			if (_body != null) {
				throw new IllegalArgumentException(
					"Part cannot be added because a body has already been set");
			}

			if (_parts == null) {
				_parts = new HashMap<String, String>();
			}

			_parts.put(name, value);
		}

		public Auth getAuth() {
			return _auth;
		}

		public Body getBody() {
			return _body;
		}

		public Cookie[] getCookies() {
			return _cookies;
		}

		public boolean getFollowRedirects() {
			return _followRedirects;
		}

		public Map<String, String> getHeaders() {
			return _headers;
		}

		public String getLocation() {
			return _location;
		}

		public Method getMethod() {
			return _method;
		}

		public Map<String, String> getParts() {
			return _parts;
		}

		public Response getResponse() {
			return _response;
		}

		public boolean isDelete() {
			if (_method == Method.DELETE) {
				return true;
			}
			else {
				return false;
			}
		}

		public boolean isFollowRedirects() {
			return _followRedirects;
		}

		public boolean isGet() {
			if (_method == Method.GET) {
				return true;
			}
			else {
				return false;
			}
		}

		public boolean isPost() {
			if (_method == Method.POST) {
				return true;
			}
			else {
				return false;
			}
		}

		public boolean isPut() {
			if (_method == Method.PUT) {
				return true;
			}
			else {
				return false;
			}
		}

		public void setAuth(Http.Auth auth) {
			setAuth(
				auth.getHost(), auth.getPort(), auth.getRealm(),
				auth.getUsername(), auth.getPassword());
		}

		public void setAuth(
			String host, int port, String realm, String username,
			String password) {

			_auth = new Auth(host, port, realm, username, password);
		}

		public void setBody(Http.Body body) {
			setBody(
				body.getContent(), body.getContentType(), body.getCharset());
		}

		public void setBody(
			String content, String contentType, String charset) {

			if (_parts != null) {
				throw new IllegalArgumentException(
					"Body cannot be set because a part has already been added");
			}

			_body = new Body(content, contentType, charset);
		}

		public void setCookies(Cookie[] cookies) {
			_cookies = cookies;
		}

		public void setDelete(boolean delete) {
			if (delete) {
				_method = Method.DELETE;
			}
			else {
				_method = Method.GET;
			}
		}

		public void setHeaders(Map<String, String> headers) {
			_headers = headers;
		}

		public void setLocation(String location) {
			_location = location;
		}

		public void setParts(Map<String, String> parts) {
			_parts = parts;
		}

		public void setPost(boolean post) {
			if (post) {
				_method = Method.POST;
			}
			else {
				_method = Method.GET;
			}
		}

		public void setPut(boolean put) {
			if (put) {
				_method = Method.PUT;
			}
			else {
				_method = Method.GET;
			}
		}

		public void setResponse(Response response) {
			_response = response;
		}

		private Auth _auth;
		private Body _body;
		private Cookie[] _cookies;
		private Map<String, String> _headers;
		private String _location;
		private Map<String, String> _parts;
		private Response _response = new Response();
		private boolean _followRedirects = true;
		private Method _method = Method.GET;

	}

	public class Response {

		public String getRedirect() {
			return _redirect;
		}

		public void setRedirect(String redirect) {
			_redirect = redirect;
		}

		private String _redirect;

	}

}