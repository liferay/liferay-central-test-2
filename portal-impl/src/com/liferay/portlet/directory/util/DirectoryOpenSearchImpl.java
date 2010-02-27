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

package com.liferay.portlet.directory.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.User;
import com.liferay.portal.search.BaseOpenSearchImpl;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portal.util.comparator.UserLastNameComparator;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="DirectoryOpenSearchImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DirectoryOpenSearchImpl extends BaseOpenSearchImpl {

	public static final String SEARCH_PATH = "/c/directory/open_search";

	public String search(
			HttpServletRequest request, long groupId, long userId,
			String keywords, int startPage, int itemsPerPage, String format)
		throws SearchException {

		try {
			return _search(request, keywords, startPage, itemsPerPage, format);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	private String _search(
			HttpServletRequest request, String keywords, int startPage,
			int itemsPerPage, String format)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		int start = (startPage * itemsPerPage) - itemsPerPage;
		int end = startPage * itemsPerPage;

		List<User> results = UserLocalServiceUtil.search(
			themeDisplay.getCompanyId(), keywords, Boolean.TRUE, null, start,
			end, new UserLastNameComparator(true));

		String[] queryTerms = StringUtil.split(keywords, StringPool.SPACE);

		int total = UserLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), keywords, Boolean.TRUE, null);

		Object[] values = addSearchResults(
			queryTerms, keywords, startPage, itemsPerPage, total, start,
			"Liferay Directory Search: " + keywords, SEARCH_PATH, format,
			themeDisplay);

		Document doc = (Document)values[0];
		Element root = (Element)values[1];

		for (User user : results) {
			String portletId = PortletKeys.DIRECTORY;

			//String portletTitle = PortalUtil.getPortletTitle(
			//	portletId, themeDisplay.getUser());

			PortletURL portletURL = getPortletURL(
				request, portletId, themeDisplay.getScopeGroupId());

			portletURL.setParameter("struts_action", "/directory/view_user");
			portletURL.setParameter(
				"p_u_i_d", String.valueOf(user.getUserId()));

			String title = user.getFullName();
			String url = portletURL.toString();
			Date modifedDate = user.getModifiedDate();
			String content =
				user.getFullName() + " &lt;" + user.getEmailAddress() + "&gt;";
			double score = 1.0;

			addSearchResult(
				root, title, url, modifedDate, content, score, format);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return\n" + doc.asXML());
		}

		return doc.asXML();
	}

	private static Log _log = LogFactoryUtil.getLog(
		DirectoryOpenSearchImpl.class);

}