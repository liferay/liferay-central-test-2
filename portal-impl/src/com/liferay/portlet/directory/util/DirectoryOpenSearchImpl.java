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
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.search.HitsOpenSearchImpl;
import com.liferay.portal.security.auth.FullNameGenerator;
import com.liferay.portal.security.auth.FullNameGeneratorFactory;
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
			PortletURL portletURL)
		throws SearchException {

		String emailAddress = document.get("emailAddress");
		String firstName = document.get("firstName");
		String middleName = document.get("middleName");
		String lastName = document.get("lastName");

		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		String title = fullNameGenerator.getFullName(
			firstName, middleName, lastName);

		String content = title + " <" + emailAddress + ">";

		String userId = document.get(Field.USER_ID);

		portletURL.setParameter("struts_action", "/directory/view_user");
		portletURL.setParameter("p_u_i_d", String.valueOf(userId));

		return new Summary(title, content, portletURL);
	}

	public String getTitle(String keywords) {
		return TITLE + keywords;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DirectoryOpenSearchImpl.class);

}