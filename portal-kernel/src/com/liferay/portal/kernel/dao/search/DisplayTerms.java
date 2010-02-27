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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="DisplayTerms.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DisplayTerms {

	public static final String KEYWORDS = "keywords";

	public static final String ADVANCED_SEARCH = "advancedSearch";

	public static final String AND_OPERATOR = "andOperator";

	public DisplayTerms(HttpServletRequest request) {
		keywords = ParamUtil.getString(request, KEYWORDS);
		advancedSearch = ParamUtil.getBoolean(request, ADVANCED_SEARCH);
		andOperator = ParamUtil.getBoolean(request, AND_OPERATOR, true);
	}

	public DisplayTerms(PortletRequest portletRequest) {
		keywords = ParamUtil.getString(portletRequest, KEYWORDS);
		advancedSearch = ParamUtil.getBoolean(portletRequest, ADVANCED_SEARCH);
		andOperator = ParamUtil.getBoolean(portletRequest, AND_OPERATOR, true);
	}

	public String getKeywords() {
		return keywords;
	}

	public boolean isAdvancedSearch() {
		return advancedSearch;
	}

	public void setAdvancedSearch(boolean advancedSearch) {
		this.advancedSearch = advancedSearch;
	}

	public boolean isAndOperator() {
		return andOperator;
	}

	protected String keywords;
	protected boolean advancedSearch;
	protected boolean andOperator;

}