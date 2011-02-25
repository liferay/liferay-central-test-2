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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class ButtonTag extends IncludeTag {

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setData(Map<String,Object> data) {
		_data = data;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setInputCssClass(String inputCssClass) {
		_inputCssClass = inputCssClass;
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
		_data = null;
		_disabled = false;
		_inputCssClass = null;
		_name = null;
		_onClick = null;
		_type = "button";
		_value = null;
	}

	protected String getPage() {
		return _PAGE;
	}

	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected void setAttributes(HttpServletRequest request) {
		String value = _value;

		if (Validator.isNull(value)) {
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
		request.setAttribute(
			"aui:button:customAttributes", getCustomAttributes());
		request.setAttribute("aui:button:data", _data);
		request.setAttribute("aui:button:disabled", String.valueOf(_disabled));
		request.setAttribute(
			"aui:button:dynamicAttributes", getDynamicAttributes());
		request.setAttribute("aui:button:inputCssClass", _inputCssClass);
		request.setAttribute("aui:button:name", _name);
		request.setAttribute("aui:button:onClick", _onClick);
		request.setAttribute("aui:button:type", _type);
		request.setAttribute("aui:button:value", value);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/html/taglib/aui/button/page.jsp";

	private String _cssClass;
	private Map<String, Object> _data;
	private boolean _disabled;
	private String _inputCssClass;
	private String _name;
	private String _onClick;
	private String _type = "button";
	private String _value;

}