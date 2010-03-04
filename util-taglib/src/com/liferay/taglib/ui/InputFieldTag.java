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

import java.text.Format;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="InputFieldTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class InputFieldTag extends IncludeTag {

	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		request.setAttribute("liferay-ui:input-field:cssClass", _cssClass);
		request.setAttribute("liferay-ui:input-field:formName", _formName);
		request.setAttribute("liferay-ui:input-field:model", _model.getName());
		request.setAttribute("liferay-ui:input-field:bean", _bean);
		request.setAttribute("liferay-ui:input-field:field", _field);
		request.setAttribute("liferay-ui:input-field:fieldParam", _fieldParam);
		request.setAttribute(
			"liferay-ui:input-field:defaultValue", _defaultValue);
		request.setAttribute(
			"liferay-ui:input-field:disabled", String.valueOf(_disabled));
		request.setAttribute("liferay-ui:input-field:format", _format);

		return EVAL_BODY_BUFFERED;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setModel(Class<?> model) {
		_model = model;
	}

	public void setBean(Object bean) {
		_bean = bean;
	}

	public void setField(String field) {
		_field = field;
	}

	public void setFieldParam(String fieldParam) {
		_fieldParam = fieldParam;
	}

	public void setDefaultValue(Object defaultValue) {
		_defaultValue = defaultValue;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setFormat(Format format) {
		_format = format;
	}

	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE = "/html/taglib/ui/input_field/page.jsp";

	private String _cssClass;
	private String _formName = "fm";
	private Class<?> _model;
	private Object _bean;
	private String _field;
	private String _fieldParam;
	private Object _defaultValue;
	private boolean _disabled;
	private Format _format;

}