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

package com.liferay.portlet.dynamicdatamapping.render;

import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pablo Carvalho
 */
public class DDMFormFieldRenderingContext {

	public Fields getFields() {
		return _fields;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return _httpServletResponse;
	}

	public Locale getLocale() {
		return _locale;
	}

	public String getMode() {
		return _mode;
	}

	public String getNamespace() {
		return _namespace;
	}

	public String getPortletNamespace() {
		return _portletNamespace;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public boolean isShowEmptyFieldLabel() {
		return _showEmptyFieldLabel;
	}

	public void setField(Field field) {
		Fields fields = new Fields();

		fields.put(field);

		_fields = fields;
	}

	public void setFields(Fields fields) {
		_fields = fields;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public void setHttpServletResponse(
		HttpServletResponse httpServletResponse) {

		_httpServletResponse = httpServletResponse;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setMode(String mode) {
		_mode = mode;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public void setPortletNamespace(String portletNamespace) {
		_portletNamespace = portletNamespace;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	public void setShowEmptyFieldLabel(boolean showEmptyFieldLabel) {
		_showEmptyFieldLabel = showEmptyFieldLabel;
	}

	private Fields _fields;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private Locale _locale;
	private String _mode;
	private String _namespace;
	private String _portletNamespace;
	private boolean _readOnly;
	private boolean _showEmptyFieldLabel;

}