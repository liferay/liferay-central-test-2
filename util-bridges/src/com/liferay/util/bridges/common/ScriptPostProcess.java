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

package com.liferay.util.bridges.common;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import javax.portlet.PortletURL;

/**
 * @author Gavin Wan
 */
public class ScriptPostProcess {

	public String getFinalizedPage() {
		if (_internalPage != null) {
			return _internalPage.toString();
		}

		return StringPool.BLANK;
	}

	public void postProcessPage(
		PortletURL actionURL, String actionParameterName) {

		processPage(
			"<a", StringPool.GREATER_THAN, "href=", actionURL,
			actionParameterName);
		processPage(
			"<A", StringPool.GREATER_THAN, "HREF=", actionURL,
			actionParameterName);
		processPage(
			"<area", StringPool.GREATER_THAN, "href=", actionURL,
			actionParameterName);
		processPage(
			"<AREA", StringPool.GREATER_THAN, "HREF=", actionURL,
			actionParameterName);
		processPage(
			"<FORM", StringPool.GREATER_THAN, "ACTION=", actionURL,
			actionParameterName);
		processPage(
			"<form", StringPool.GREATER_THAN, "action=", actionURL,
			actionParameterName);
	}

	public void processPage(
		String startTag, String endTag, String ref, PortletURL actionURL,
		String actionParameterName) {

		StringBundler sb = new StringBundler();

		String page = _internalPage.toString();

		int ixTagOpen = page.indexOf(startTag);

		int ixTagEnd = 0;
		int ixRefStart = 0; 
		int ixRefEnd = 0;

		try {
			while (ixTagOpen != -1) {
				sb.append(page.substring(0, ixTagOpen));

				page = page.substring(ixTagOpen);

				ixTagEnd = page.indexOf(endTag);
				ixRefStart = page.indexOf(ref);

				if ((ixRefStart == -1) || (ixRefStart > ixTagEnd)) {
					sb.append(page.substring(0, ixTagEnd));

					page = page.substring(ixTagEnd);
				}
				else {
					String strQuote = StringPool.BLANK;
					String url = StringPool.BLANK;

					ixRefStart = ixRefStart + ref.length();

					sb.append(page.substring(0, ixRefStart));

					page = page.substring(ixRefStart);

					// Check if the argument starts with a single or double
					// quote or no quote

					if (page.startsWith(StringPool.APOSTROPHE)) {
						strQuote = StringPool.APOSTROPHE;
					}
					else if (page.startsWith(StringPool.QUOTE)) {
						strQuote = StringPool.QUOTE;
					}

					if (strQuote.length() > 0) {
						sb.append(strQuote);

						page = page.substring(1);

						ixRefEnd = page.indexOf(strQuote);

						// Extract the URL

						url = page.substring(0, ixRefEnd);
					}
					else {

						// Make sure that we don't parse over the tag end

						ixTagEnd = page.indexOf(endTag);

						// No quote just the first space or tagEnd index

						ixRefEnd = 0;

						StringBundler nqurl = new StringBundler();

						boolean bEnd = false;

						while (bEnd == false) {
							char c = page.charAt(ixRefEnd);

							if ((Character.isSpaceChar(c) == false) &&
								(ixRefEnd < ixTagEnd)) {

								ixRefEnd++;

								nqurl.append(c);
							}
							else {
								bEnd = true;

								ixRefEnd--;
							}
						}

						// Get the string

						url = nqurl.toString();
					}

					if (url.charAt(0) == CharPool.POUND ||
						url.startsWith("http")) {

						sb.append(url).append(strQuote);
					}
					else {
						
						// Prepend the Action URL

						actionURL.setParameter(actionParameterName, url);
						
						sb.append(actionURL.toString()).append(strQuote);
					}

					// Remainder

					page = page.substring(ixRefEnd + 1);
				}

				// Continue scan

				ixTagOpen = page.indexOf(startTag);
			}

			sb.append(page);
		}
		catch (Exception e) {
			_log.error(e);
		}

		_internalPage = sb;
	}

	public void setInitalPage(StringBundler page) {
		_internalPage = page;
	}

	private static Log _log = LogFactoryUtil.getLog(ScriptPostProcess.class);

	private StringBundler _internalPage;

}