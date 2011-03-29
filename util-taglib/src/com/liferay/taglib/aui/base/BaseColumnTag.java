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

package com.liferay.taglib.aui.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 * @generated
 */
public class BaseColumnTag extends com.liferay.taglib.util.IncludeTag {

	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.Integer getColumnWidth() {
		return _columnWidth;
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public java.lang.Boolean getFirst() {
		return _first;
	}

	public java.lang.Boolean getLast() {
		return _last;
	}

	public void setColumnWidth(java.lang.Integer columnWidth) {
		_columnWidth = columnWidth;

		setScopedAttribute("columnWidth", columnWidth);
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setFirst(java.lang.Boolean first) {
		_first = first;

		setScopedAttribute("first", first);
	}

	public void setLast(java.lang.Boolean last) {
		_last = last;

		setScopedAttribute("last", last);
	}

	protected void cleanUp() {
		_columnWidth = null;
		_cssClass = null;
		_first = null;
		_last = null;
	}

	protected String getEndPage() {
		return _END_PAGE;
	}

	protected String getStartPage() {
		return _START_PAGE;
	}


	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "columnWidth", _columnWidth);
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "first", _first);
		setNamespacedAttribute(request, "last", _last);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:column:";

	private static final String _END_PAGE =
		"/html/taglib/aui/column/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/column/start.jsp";

	protected java.lang.Integer _columnWidth;
	protected java.lang.String _cssClass;
	protected java.lang.Boolean _first;
	protected java.lang.Boolean _last;

}