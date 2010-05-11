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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.HitsOpenSearchImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portlet.enterpriseadmin.util.UserIndexer;

import javax.portlet.PortletURL;

/**
 * <a href="DirectoryOpenSearchImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DirectoryOpenSearchImpl extends HitsOpenSearchImpl {

	public static final String SEARCH_PATH = "/c/directory/open_search";

	public static final String TITLE = "Liferay Directory Search: ";

	public String getPortletId() {
		return UserIndexer.PORTLET_ID;
	}

	public String getSearchPath() {
		return SEARCH_PATH;
	}

	public Summary getSummary(
		Indexer indexer, Document document, String snippet,
		PortletURL portletURL) {

		Summary summary = super.getSummary(
			indexer, document, snippet, portletURL);

		portletURL = summary.getPortletURL();

		portletURL.setParameter("struts_action", "/directory/view_user");

		return summary;
	}

	public String getTitle(String keywords) {
		return TITLE + keywords;
	}

}