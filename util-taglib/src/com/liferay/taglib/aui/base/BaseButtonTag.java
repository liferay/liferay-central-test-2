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
 */
public class BaseButtonTag extends com.liferay.taglib.util.IncludeTag {

	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public java.lang.String getCssClass() {
		return _cssClass;
	}

	public java.lang.Object getData() {
		return _data;
	}

	public java.lang.Boolean getDisabled() {
		return _disabled;
	}

	public java.lang.String getInputCssClass() {
		return _inputCssClass;
	}

	public java.lang.String getName() {
		return _name;
	}

	public java.lang.String getOnClick() {
		return _onClick;
	}

	public java.lang.String getType() {
		return _type;
	}

	public java.lang.String getValue() {
		return _value;
	}

	public void setCssClass(java.lang.String cssClass) {
		_cssClass = cssClass;

		setScopedAttribute("cssClass", cssClass);
	}

	public void setData(java.lang.Object data) {
		_data = data;

		setScopedAttribute("data", data);
	}

	public void setDisabled(java.lang.Boolean disabled) {
		_disabled = disabled;

		setScopedAttribute("disabled", disabled);
	}

	public void setInputCssClass(java.lang.String inputCssClass) {
		_inputCssClass = inputCssClass;

		setScopedAttribute("inputCssClass", inputCssClass);
	}

	public void setName(java.lang.String name) {
		_name = name;

		setScopedAttribute("name", name);
	}

	public void setOnClick(java.lang.String onClick) {
		_onClick = onClick;

		setScopedAttribute("onClick", onClick);
	}

	public void setType(java.lang.String type) {
		_type = type;

		setScopedAttribute("type", type);
	}

	public void setValue(java.lang.String value) {
		_value = value;

		setScopedAttribute("value", value);
	}

	protected void cleanUp() {
		_cssClass = null;
		_data = null;
		_disabled = null;
		_inputCssClass = null;
		_name = null;
		_onClick = null;
		_type = "button";
		_value = null;
	}

	protected String getPage() {
		return _PAGE;
	}

	protected void setAttributes(HttpServletRequest request) {
		setNamespacedAttribute(request, "cssClass", _cssClass);
		setNamespacedAttribute(request, "data", _data);
		setNamespacedAttribute(request, "disabled", _disabled);
		setNamespacedAttribute(request, "inputCssClass", _inputCssClass);
		setNamespacedAttribute(request, "name", _name);
		setNamespacedAttribute(request, "onClick", _onClick);
		setNamespacedAttribute(request, "type", _type);
		setNamespacedAttribute(request, "value", _value);
	}

	protected static final String _ATTRIBUTE_NAMESPACE = "aui:button:";

	private static final String _PAGE =
		"/html/taglib/aui/button/page.jsp";

	protected java.lang.String _cssClass;
	protected java.lang.Object _data;
	protected java.lang.Boolean _disabled;
	protected java.lang.String _inputCssClass;
	protected java.lang.String _name;
	protected java.lang.String _onClick;
	protected java.lang.String _type;
	protected java.lang.String _value;

}