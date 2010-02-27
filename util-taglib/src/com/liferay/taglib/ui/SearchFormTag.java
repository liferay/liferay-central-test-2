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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="SearchFormTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SearchFormTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		SearchContainerTag parentTag =
			(SearchContainerTag)findAncestorWithClass(
				this, SearchContainerTag.class);

		if (parentTag != null) {
			_searchContainer = parentTag.getSearchContainer();
		}

		request.setAttribute(
			"liferay-ui:search:searchContainer", _searchContainer);
		request.setAttribute(
			"liferay-ui:search:showAddButton", String.valueOf(_showAddButton));

		return EVAL_BODY_BUFFERED;
	}

	public void setSearchContainer(SearchContainer<?> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setShowAddButton(boolean showAddButton) {
		_showAddButton = showAddButton;
	}

	private SearchContainer<?> _searchContainer;
	private boolean _showAddButton = false;

}