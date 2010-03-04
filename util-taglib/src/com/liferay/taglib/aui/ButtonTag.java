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
 * <a href="ButtonTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class ButtonTag extends IncludeTag {

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setValue(String value) {
		_value = value;
	}

	protected void cleanUp() {
		_cssClass = null;
		_disabled = false;
		_name = null;
		_onClick = null;
		_type = "button";
		_value = null;
	}

	protected String getPage() {
		return _PAGE;
	}

	protected void setAttributes(HttpServletRequest request) {
		String value = _value;

		if (value == null) {
			if (_type.equals("submit")) {
				value = "save";
			}
			else if (_type.equals("cancel")) {
				value = "cancel";
			}
			else if (_type.equals("reset")) {
				value = "reset";
			}
		}

		request.setAttribute("aui:button:cssClass", _cssClass);
		request.setAttribute("aui:button:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"aui:button:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:button:name", _name);
		request.setAttribute("aui:button:onClick", _onClick);
		request.setAttribute("aui:button:type", _type);
		request.setAttribute("aui:button:value", value);
	}

	private static final String _PAGE = "/html/taglib/aui/button/page.jsp";

	private String _cssClass;
	private boolean _disabled;
	private String _name;
	private String _onClick;
	private String _type = "button";
	private String _value;

}