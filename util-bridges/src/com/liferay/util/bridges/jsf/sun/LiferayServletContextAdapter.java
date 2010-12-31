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

package com.liferay.util.bridges.jsf.sun;

import java.io.InputStream;

import java.net.URL;

import java.security.Principal;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;

import javax.servlet.ServletContext;

/**
 * @author Neil Griffin
 */
public class LiferayServletContextAdapter extends ExternalContext {

	public LiferayServletContextAdapter(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public Map<String, Object> getApplicationMap() {
		if (_applicationMap == null) {
			_applicationMap = new LiferayApplicationMap(_servletContext);
		}

		return _applicationMap;
	}

	public String getAuthType() {
		return null;
	}

	public Object getContext() {
		return null;
	}

	public void dispatch(String string) {
	}

	public String encodeActionURL(String string) {
		return null;
	}

	public String encodeNamespace(String string) {
		return null;
	}

	public String encodeResourceURL(String string) {
		return null;
	}

	public String getInitParameter(String string) {
		return null;
	}

	public Map<String, String> getInitParameterMap() {
		return null;
	}

	public void log(String string) {
	}

	public void log(String string, Throwable throwable) {
	}

	public void redirect(String string) {
	}

	public String getRemoteUser() {
		return null;
	}

	public Object getRequest() {
		return null;
	}

	public String getRequestContextPath() {
		return null;
	}

	public Map<String, Object> getRequestCookieMap() {
		return null;
	}

	public Map<String, String> getRequestHeaderMap() {
		return null;
	}

	public Map<String, String[]> getRequestHeaderValuesMap() {
		return null;
	}

	public Locale getRequestLocale() {
		return null;
	}

	public Iterator<Locale> getRequestLocales() {
		return null;
	}

	public Map<String, Object> getRequestMap() {
		return null;
	}

	public Map<String, String> getRequestParameterMap() {
		return null;
	}

	public Iterator<String> getRequestParameterNames() {
		return null;
	}

	public Map<String, String[]> getRequestParameterValuesMap() {
		return null;
	}

	public String getRequestPathInfo() {
		return null;
	}

	public String getRequestServletPath() {
		return null;
	}

	public URL getResource(String string) {
		return null;
	}

	public InputStream getResourceAsStream(String string) {
		return null;
	}

	public Set<String> getResourcePaths(String string) {
		return null;
	}

	public Object getResponse() {
		return null;
	}

	public Object getSession(boolean b) {
		return null;
	}

	public Map<String, Object> getSessionMap() {
		return null;
	}

	public boolean isUserInRole(String string) {
		return false;
	}

	public Principal getUserPrincipal() {
		return null;
	}

	private ServletContext _servletContext;
	private LiferayApplicationMap _applicationMap;

}