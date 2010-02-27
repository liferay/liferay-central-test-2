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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * <a href="ButtonTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class ButtonTag extends IncludeTag implements DynamicAttributes {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (_value == null) {
			if (_type.equals("submit")) {
				_value = "save";
			}
			else if (_type.equals("cancel")) {
				_value = "cancel";
			}
			else if (_type.equals("reset")) {
				_value = "reset";
			}
		}

		request.setAttribute("aui:button:cssClass", _cssClass);
		request.setAttribute("aui:button:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"aui:button:dynamicAttributes", _dynamicAttributes);
		request.setAttribute("aui:button:name", _name);
		request.setAttribute("aui:button:onClick", _onClick);
		request.setAttribute("aui:button:type", _type);
		request.setAttribute("aui:button:value", _value);

		return EVAL_BODY_BUFFERED;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setDynamicAttribute(
		String uri, String localName, Object value) {

		_dynamicAttributes.put(localName, value);
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

	protected String getDefaultPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/aui/button/page.jsp";

	private String _cssClass;
	private boolean _disabled;
	private Map<String, Object> _dynamicAttributes =
		new HashMap<String, Object>();
	private String _name;
	private String _onClick;
	private String _type = "button";
	private String _value;

}