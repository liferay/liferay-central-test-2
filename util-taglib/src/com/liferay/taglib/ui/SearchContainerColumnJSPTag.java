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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * @author Raymond Augé
 */
public class SearchContainerColumnJSPTag<R> extends SearchContainerColumnTag {

	public int doEndTag() {
		try {
			SearchContainerRowTag<R> searchContainerRowTag =
				(SearchContainerRowTag<R>)findAncestorWithClass(
					this, SearchContainerRowTag.class);

			ResultRow row = searchContainerRowTag.getRow();

			if (index <= -1) {
				index = row.getEntries().size();
			}

			row.addJSP(
				index, getAlign(), getValign(), getColspan(), getCssClass(),
				getPath(), pageContext.getServletContext(), getServletRequest(),
				new PipingServletResponse(pageContext));

			return EVAL_PAGE;
		}
		finally {
			index = -1;

			if (!ServerDetector.isResin()) {
				align = SearchEntry.DEFAULT_ALIGN;
				colspan = SearchEntry.DEFAULT_COLSPAN;
				cssClass = SearchEntry.DEFAULT_CSS_CLASS;
				name = StringPool.BLANK;
				_path = null;
				valign = SearchEntry.DEFAULT_VALIGN;
			}
		}
	}

	public int doStartTag() throws JspException {
		SearchContainerRowTag<R> searchContainerRowTag =
			(SearchContainerRowTag<R>)findAncestorWithClass(
				this, SearchContainerRowTag.class);

		if (searchContainerRowTag == null) {
			throw new JspTagException(
				"Requires liferay-ui:search-container-row");
		}

		if (!searchContainerRowTag.isHeaderNamesAssigned()) {
			List<String> headerNames = searchContainerRowTag.getHeaderNames();

			headerNames.add(name);
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getPath() {
		return _path;
	}

	public void setPath(String path) {
		_path = path;
	}

	private String _path;

}