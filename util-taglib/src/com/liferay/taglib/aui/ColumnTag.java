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

package com.liferay.taglib.aui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class ColumnTag extends IncludeTag {

	public void setColumnWidth(int columnWidth) {
		_columnWidth = columnWidth;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setFirst(boolean first) {
		_first = first;
	}

	public void setLast(boolean last) {
		_last = last;
	}

	protected void cleanUp() {
		_columnWidth = 0;
		_cssClass = null;
		_first = false;
		_last = false;
	}

	protected String getEndPage() {
		return _END_PAGE;
	}

	protected String getStartPage() {
		return _START_PAGE;
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"aui:column:columnWidth", String.valueOf(_columnWidth));
		request.setAttribute("aui:column:cssClass", _cssClass);
		request.setAttribute(
			"aui:column:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:column:first", String.valueOf(_first));
		request.setAttribute("aui:column:last", String.valueOf(_last));
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _END_PAGE =
		"/html/taglib/aui/column/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/column/start.jsp";

	private int _columnWidth;
	private String _cssClass;
	private boolean _first;
	private boolean _last;

}