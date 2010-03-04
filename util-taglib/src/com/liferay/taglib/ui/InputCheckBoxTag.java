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

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="InputCheckBoxTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class InputCheckBoxTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:input-checkbox:cssClass", _cssClass);
		request.setAttribute("liferay-ui:input-checkbox:formName", _formName);
		request.setAttribute("liferay-ui:input-checkbox:param", _param);
		request.setAttribute("liferay-ui:input-checkbox:id", _id);
		request.setAttribute(
			"liferay-ui:input-checkbox:defaultValue", _defaultValue);
		request.setAttribute("liferay-ui:input-checkbox:onClick", _onClick);
		request.setAttribute(
			"liferay-ui:input-checkbox:disabled", String.valueOf(_disabled));

		return EVAL_BODY_BUFFERED;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setParam(String param) {
		_param = param;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setDefaultValue(boolean defaultValue) {
		_defaultValue = Boolean.valueOf(defaultValue);
	}

	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/ui/input_checkbox/page.jsp";

	private String _cssClass;
	private String _formName = "fm";
	private String _param;
	private String _id;
	private Boolean _defaultValue = Boolean.FALSE;
	private String _onClick;
	private boolean _disabled;

}