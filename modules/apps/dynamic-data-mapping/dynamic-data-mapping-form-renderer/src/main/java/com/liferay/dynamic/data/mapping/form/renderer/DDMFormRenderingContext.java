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

package com.liferay.dynamic.data.mapping.form.renderer;

import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcellus Tavares
 */
public class DDMFormRenderingContext {

	public DDMFormValues getDDMFormValues() {
		return _ddmFormValues;
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

	public String getPortletNamespace() {
		return _portletNamespace;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public void setDDMFormValues(DDMFormValues ddmFormValues) {
		_ddmFormValues = ddmFormValues;
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

	public void setPortletNamespace(String portletNamespace) {
		_portletNamespace = portletNamespace;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	private DDMFormValues _ddmFormValues;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private Locale _locale;
	private String _portletNamespace;
	private boolean _readOnly;

}