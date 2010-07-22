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
public class ATag extends IncludeTag {

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setLang(String lang) {
		_lang = lang;
	}

	public void setTarget(String target) {
		_target = target;
	}

	protected void cleanUp() {
		_cssClass = null;
		_href = null;
		_id = null;
		_label = null;
		_lang = null;
		_target = null;
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

	protected boolean isTrimNewLines() {
		return _TRIM_NEW_LINES;
	}

	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("aui:a:cssClass", _cssClass);
		request.setAttribute("aui:a:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:a:href", _href);
		request.setAttribute("aui:a:id", _id);
		request.setAttribute("aui:a:label", _label);
		request.setAttribute("aui:a:lang", _lang);
		request.setAttribute("aui:a:target", _target);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final boolean _TRIM_NEW_LINES = true;

	private static final String _END_PAGE =
		"/html/taglib/aui/a/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/aui/a/start.jsp";

	private String _cssClass;
	private String _href;
	private String _id;
	private String _label;
	private String _lang;
	private String _target;

}