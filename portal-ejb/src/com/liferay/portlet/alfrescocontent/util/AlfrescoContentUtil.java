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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.alfresco.webservice.types.NamedValue;
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

	public static String formatContent(
		String content, boolean maximizeLinks, RenderResponse res) {

		content = content.replaceAll("%28", "(");
		content = content.replaceAll("%29", ")");

		Pattern p = Pattern.compile("\"/c/portal/\\$link=\\((.*)\\)\"");

		Matcher m = p.matcher(content);

		StringBuffer portletURLSB = new StringBuffer();

		try {
			while (m.find()) {
				String rawPath[] = m.group(1).split("/");
				
				StringBuffer nodePathSB = new StringBuffer("/app:company_home");
				
				for (int i = 0; i < rawPath.length; i++) {
					nodePathSB.append("/cm:").append(rawPath[i]);
				}
				
				PortletURL portletURL = res.createRenderURL();

				portletURL.setParameter("nodePath", nodePathSB.toString());

				if (maximizeLinks) {
					portletURL.setWindowState(WindowState.MAXIMIZED);
				}

				m.appendReplacement(
					portletURLSB, "\"" + portletURL.toString() + "\"");
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		m.appendTail(portletURLSB);

		return portletURLSB.toString();
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

	private static Log _log = LogFactory.getLog(AlfrescoContentUtil.class);

}