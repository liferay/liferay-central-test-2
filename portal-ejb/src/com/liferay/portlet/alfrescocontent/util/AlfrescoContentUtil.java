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

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.util.WebServiceException;
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
 * @author Brian Wing Shun Chan
 * @author Raymond Auge
 * 
 */
public class AlfrescoContentUtil {

	public static String getContent(String url, String userId, String password,
		boolean maximizeLinks, RenderResponse res) {

		if (Validator.isNull(url) || Validator.isNull(userId)
			|| Validator.isNull(password)) {

			return null;
		}

		String content = null;

		HttpMethod method = new GetMethod(url);

		try {
			HttpClient client = new HttpClient();

			URL urlObj = new URL(url);

			client.getState().setCredentials(
				new AuthScope(urlObj.getHost(), urlObj.getPort(),
					AuthScope.ANY_REALM),
				new UsernamePasswordCredentials(userId, password));

			method.setDoAuthentication(true);

			client.executeMethod(method);

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

	private static String _formatContent(String content, boolean maximizeLinks,
		RenderResponse res) {

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

	public static String getNamedValue(NamedValue[] namedValues, String name) {
		String value = null;

		for (int j = 0; j < namedValues.length; j++) {
			if (namedValues[j].getName().endsWith(name)) {
				value = namedValues[j].getValue();
			}
		}

		return value;
	}

	public static String getContentAsString(Content content) {

		StringBuilder readContent = new StringBuilder();
		try {
			AuthenticationUtils.startSession("admin", "admin");
			
			String ticket = AuthenticationUtils.getCurrentTicket();
			String strUrl = content.getUrl() + "?ticket=" + ticket;

			URL url = new URL(strUrl);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			int read = is.read();
			while (read != -1) {
				readContent.append((char) read);
				read = is.read();
			}
		}
		catch (Exception e) {
			throw new WebServiceException("Unable to get content as string.",
				e);
		}
		finally {
			try {
				AuthenticationUtils.endSession();
			}
			catch (Exception e) {
				throw new WebServiceException("Unable to close session.", e);
			}
		}
		return readContent.toString();
	}

	private static Log _log = LogFactory.getLog(AlfrescoContentUtil.class);

}