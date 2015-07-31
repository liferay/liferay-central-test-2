/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.dynamic.data.mapping.taglib.servlet.taglib.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.liferay.dynamic.data.mapping.taglib.servlet.ServletContextUtil;

/**
 * @author Bruno Basto
 * @generated
 */
public abstract class BaseTemplateRendererTag extends com.liferay.taglib.util.IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getClassName() {
		return _className;
	}

	public java.util.Map<java.lang.String, java.lang.Object> getContextObjects() {
		return _contextObjects;
	}

	public java.lang.String getDisplayStyle() {
		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		return _displayStyleGroupId;
	}

	public java.util.List<?> getEntries() {
		return _entries;
	}

	public void setClassName(java.lang.String className) {
		_className = className;

		setScopedAttribute("className", className);
	}

	public void setContextObjects(java.util.Map<java.lang.String, java.lang.Object> contextObjects) {
		_contextObjects = contextObjects;

		setScopedAttribute("contextObjects", contextObjects);
	}

	public void setDisplayStyle(java.lang.String displayStyle) {
		_displayStyle = displayStyle;

		setScopedAttribute("displayStyle", displayStyle);
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;

		setScopedAttribute("displayStyleGroupId", displayStyleGroupId);
	}

	public void setEntries(java.util.List<?> entries) {
		_entries = entries;

		setScopedAttribute("entries", entries);
	}
	
	public void setServletContext(java.lang.Object servletContext) {
		_servletContext = servletContext;

		setScopedAttribute("servletContext", servletContext);
	}
	
	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_contextObjects = null;
		_displayStyle = null;
		_displayStyleGroupId = 0;
		_entries = null;
		_servletContext = null;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "className", _className);
		setNamespacedAttribute(request, "contextObjects", _contextObjects);
		setNamespacedAttribute(request, "displayStyle", _displayStyle);
		setNamespacedAttribute(request, "displayStyleGroupId", _displayStyleGroupId);
		setNamespacedAttribute(request, "entries", _entries);
		setNamespacedAttribute(request, "servletContext", _servletContext);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "liferay-ddm:template-renderer:";

	private static final String _END_PAGE =
		"/taglib/template_renderer/end.jsp";

	private static final String _START_PAGE =
		"/taglib/template_renderer/start.jsp";

	private java.lang.String _className = null;
	private java.util.Map<java.lang.String, java.lang.Object> _contextObjects = null;
	private java.lang.String _displayStyle = null;
	private long _displayStyleGroupId = 0;
	private java.util.List<?> _entries = null;
	private java.lang.Object _servletContext = null;

}