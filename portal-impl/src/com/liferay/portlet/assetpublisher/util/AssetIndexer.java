/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetEntry;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class AssetIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {AssetEntry.class.getName()};

	public static final String PORTLET_ID = PortletKeys.ASSET_PUBLISHER;

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected void doDelete(Object obj) {
	}

	protected Document doGetDocument(Object obj) {
		return null;
	}

	protected Summary doGetSummary(
		Document document, String snippet, PortletURL portletURL) {

		return null;
	}

	protected void doReindex(Object obj) {
	}

	protected void doReindex(String className, long classPK) {
	}

	protected void doReindex(String[] ids) {
	}

	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		if (searchContext.getAttributes() == null) {
			return;
		}

		String description = (String)searchContext.getAttribute(
			Field.DESCRIPTION);

		if (Validator.isNotNull(description)) {
			if (searchContext.isAndSearch()) {
				searchQuery.addRequiredTerm(
					Field.DESCRIPTION, description, true);
			}
			else {
				searchQuery.addTerm(Field.DESCRIPTION, description, true);
			}
		}

		String title = (String)searchContext.getAttribute(Field.TITLE);

		if (Validator.isNotNull(title)) {
			if (searchContext.isAndSearch()) {
				searchQuery.addRequiredTerm(Field.TITLE, title, true);
			}
			else {
				searchQuery.addTerm(Field.TITLE, title, true);
			}
		}

		String userName = (String)searchContext.getAttribute(Field.USER_NAME);

		if (Validator.isNotNull(userName)) {
			if (searchContext.isAndSearch()) {
				searchQuery.addRequiredTerm(Field.USER_NAME, userName, true);
			}
			else {
				searchQuery.addTerm(Field.USER_NAME, userName, true);
			}
		}
	}

}