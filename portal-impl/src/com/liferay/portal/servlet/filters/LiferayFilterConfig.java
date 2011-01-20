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

package com.liferay.portal.servlet.filters;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

/**
 * @author Mika Koivisto
 */
public class LiferayFilterConfig implements FilterConfig {

	public LiferayFilterConfig(
		String filterName, Map<String, String> initParameters,
		ServletContext servletContext) {

		_filterName = filterName;
		_initParameterMap = initParameters;
		_servletContext = servletContext;
	}

	public String getFilterName() {
		return _filterName;
	}

	public String getInitParameter(String key) {
		return _initParameterMap.get(key);
	}

	public Enumeration getInitParameterNames() {
		return new Enumeration() {
			public boolean hasMoreElements() {
				return _keys.hasNext();
			}

			public Object nextElement() {
				return _keys.next();
			}

			Iterator<String> _keys = _initParameterMap.keySet().iterator();
		};
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	private String _filterName;
	private Map<String, String> _initParameterMap;
	private ServletContext _servletContext;

}