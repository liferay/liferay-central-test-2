/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.util.Validator;

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="AlfrescoContentUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Raymond Auge
 *
 */
public class AlfrescoContentUtil {

	public static String getContent(
		String baseURL, String url, String userId, String password,
		boolean maximizeLinks, RenderResponse res) {

		if (Validator.isNull(baseURL) || Validator.isNull(url) ||
			Validator.isNull(userId) || Validator.isNull(password)) {

			return null;
		}

		String content = null;

		HttpMethod method = new GetMethod(baseURL + "/" + url);

		try {
			HttpClient client = new HttpClient();

			URL urlObj = new URL(baseURL);

			client.getState().setCredentials(
				new AuthScope(
					urlObj.getHost(), urlObj.getPort(), AuthScope.ANY_REALM),
				new UsernamePasswordCredentials(userId, password));

			method.setDoAuthentication(true);

			int status = client.executeMethod(method);

			content = method.getResponseBodyAsString();
		}
		catch (Exception e) {
			_log.error(e);
		}
		finally {
			method.releaseConnection();
		}

		if (content == null) {
			return null;
		}

		return _formatContent(content, maximizeLinks, res);
	}

	private static String _formatContent(
		String content, boolean maximizeLinks, RenderResponse res) {

		content = content.replaceAll("%28", "(");
		content = content.replaceAll("%29", ")");

		Pattern p = Pattern.compile("\"/c/portal/\\$link=\\((.*)\\)\"");

		Matcher m = p.matcher(content);

		StringBuffer sb = new StringBuffer();

		try {
			while (m.find()) {
				String url = m.group(1);

				PortletURL portletURL = res.createRenderURL();

				portletURL.setParameter("url", url);

				if (maximizeLinks) {
					portletURL.setWindowState(WindowState.MAXIMIZED);
				}

				m.appendReplacement(sb, "\"" + portletURL.toString() + "\"");
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		m.appendTail(sb);

		return sb.toString();
	}

	private static Log _log = LogFactory.getLog(AlfrescoContentUtil.class);

}