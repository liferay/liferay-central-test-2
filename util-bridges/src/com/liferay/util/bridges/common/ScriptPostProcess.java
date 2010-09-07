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

import javax.portlet.PortletURL;

/**
 * @author Gavin Wan
 */
public class ScriptPostProcess {

	public String getFinalizedPage() {
		if (internalPage != null) {
			return internalPage.toString();
		}
		else {
			return "";
		}
	}

	public void postProcessPage(PortletURL actionURL,
		String actionParameterName){

		// Anchor tags

		processPage("<a", ">", "href=", actionURL, actionParameterName);
		processPage("<A", ">", "HREF=", actionURL, actionParameterName);
		processPage("<AREA", ">", "href=", actionURL, actionParameterName);

		// Forms

		processPage("<FORM", ">", "action=", actionURL, actionParameterName);
		processPage("<form", ">", "action=", actionURL, actionParameterName);
	}

	public void setInitalPage(StringBuffer page) {
		this.internalPage = page;
	}

	public void processPage(
		String startTag, String endTag, String ref, PortletURL actionURL,
		String actionParameterName) {

		final String SINGLE_QUOTE = "\'";
		final String DOUBLE_QUOTE = "\"";

		StringBuffer finalPage = new StringBuffer();

		String page = internalPage.toString();

		int ixTagOpen, ixTagEnd, ixRefStart, ixRefEnd;

		ixTagOpen = page.indexOf(startTag);

		try {
			while (ixTagOpen != -1) {
				finalPage.append(page.substring(0, ixTagOpen));
				page = page.substring(ixTagOpen);
				ixTagEnd = page.indexOf(endTag);
				ixRefStart = page.indexOf(ref);

				if (ixRefStart == -1 || ixRefStart > ixTagEnd) {
					finalPage.append(page.substring(0, ixTagEnd));
					page = page.substring(ixTagEnd);
				}
				else {
					String strQuote = "";
					String url = "";
					ixRefStart = ixRefStart + ref.length();
					finalPage.append(page.substring(0, ixRefStart));
					page = page.substring(ixRefStart);

					// Check if the argument starts with a single or double
					// quote or no quote

					if (page.startsWith(SINGLE_QUOTE)) {
						strQuote = SINGLE_QUOTE;
					}
					else if (page.startsWith(DOUBLE_QUOTE)) {
						strQuote = DOUBLE_QUOTE;
					}

					if (strQuote.length() > 0) {
						finalPage.append(strQuote);
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
						StringBuffer nqurl = new StringBuffer();
						boolean bEnd = false;

						while (bEnd == false) {
							char c = page.charAt(ixRefEnd);

							if ((Character.isSpaceChar(c) == false)
								&& (ixRefEnd < ixTagEnd)) {
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

					if (url.charAt(0) == '#' || url.startsWith("http")) {
						finalPage.append(url).append(strQuote);
					}
					else {
						// Prepend the Action URL

						actionURL.setParameter(actionParameterName, url);
						finalPage.append(actionURL.toString()).append(strQuote);
					}

					// Remainder

					page = page.substring(ixRefEnd + 1);
				}

				// Continue scan

				ixTagOpen = page.indexOf(startTag);
			}

			finalPage.append(page);
		}
		catch (Exception e) {
			_log.error(e);
		}

		internalPage = finalPage;

	}
	
	private StringBuffer internalPage;
	
	private static Log _log = LogFactoryUtil.getLog(ScriptPostProcess.class);
}