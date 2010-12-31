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

import java.util.AbstractMap;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * @author Neil Griffin
 */
public class LiferayApplicationMap extends AbstractMap<String, Object> {

	public LiferayApplicationMap(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public Set<Entry<String, Object>> entrySet() {
		throw new UnsupportedOperationException();
	}

	public Object get(Object key) {
		return _servletContext.getAttribute(key.toString());
	}

	public Object put(String key, Object value) {
		Object previousValue = get(key);

		_servletContext.setAttribute(key.toString(), value);

		return previousValue;
	}

	public Object remove(Object key) {
		Object value = null;

		if (key != null) {
			value = get(key);

			_servletContext.removeAttribute(key.toString());
		}

		return value;
	}

	private ServletContext _servletContext;

}