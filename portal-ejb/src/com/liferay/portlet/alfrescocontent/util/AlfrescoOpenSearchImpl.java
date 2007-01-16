/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.alfrescocontent.util;

import com.liferay.portal.kernel.search.OpenSearch;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.Http;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="AlfrescoOpenSearchImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 * @author  Brian Wing Shun Chan
 *
 */
public class AlfrescoOpenSearchImpl implements OpenSearch {

	public static final boolean ENABLED = GetterUtil.getBoolean(PropsUtil.get(
		PropsUtil.ALFRESCO_OPEN_SEARCH_ENABLED));

	public static final String PROTOCOL = PropsUtil.get(
		PropsUtil.ALFRESCO_OPEN_SEARCH_PROTOCOL);

	public static final String HOST = PropsUtil.get(
		PropsUtil.ALFRESCO_OPEN_SEARCH_HOST);

	public static final int PORT = GetterUtil.getInteger(PropsUtil.get(
		PropsUtil.ALFRESCO_OPEN_SEARCH_PORT));

	public static final String REALM = PropsUtil.get(
		PropsUtil.ALFRESCO_OPEN_SEARCH_REALM);

	public static final String USERNAME = PropsUtil.get(
		PropsUtil.ALFRESCO_OPEN_SEARCH_USERNAME);

	public static final String PASSWORD = PropsUtil.get(
		PropsUtil.ALFRESCO_OPEN_SEARCH_PASSWORD);

	public static final String PATH = PropsUtil.get(
		PropsUtil.ALFRESCO_OPEN_SEARCH_PATH);

	public static final String SEARCH_URL =
		HOST + StringPool.COLON + PORT + PATH;

	public boolean isEnabled() {
		return ENABLED;
	}

	public String search(HttpServletRequest req, String url)
		throws SearchException {

		String xml = StringPool.BLANK;

		if (!ENABLED) {
			if (_log.isDebugEnabled()) {
				_log.debug("Search is disabled");
			}

			return xml;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Search with " + url);
		}

		HttpClient client = new HttpClient();

		client.getState().setCredentials(
			new AuthScope(HOST, PORT, REALM),
			new UsernamePasswordCredentials(USERNAME, PASSWORD));

		GetMethod get = new GetMethod(url);

		get.setDoAuthentication(true);

		try {
			int status = client.executeMethod(get);

			xml = get.getResponseBodyAsString();
		}
		catch (IOException ioe) {
			_log.error("Unable to search with " + url, ioe);
		}
		finally {
			get.releaseConnection();
		}

		return xml;
	}

	public String search(
			HttpServletRequest req, String keywords, int startPage,
			int itemsPerPage)
		throws SearchException {

		String url =
			PROTOCOL + "://" + SEARCH_URL + "?q=" + Http.encodeURL(keywords) +
				"&p=" + startPage + "&c=" + itemsPerPage +
					"&guest=&format=atom";

		return search(req, url);
	}

	private static Log _log = LogFactory.getLog(AlfrescoOpenSearchImpl.class);

}