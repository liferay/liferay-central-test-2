/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.io.PrintStream;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class SortedProperties extends Properties {

	public SortedProperties() {
		this(null, null);
	}

	public SortedProperties(Comparator<String> comparator) {
		this(comparator, null);
	}

	public SortedProperties(
		Comparator<String> comparator, Properties properties) {

		if (comparator != null) {
			_names = new TreeSet<String>(comparator);
		}
		else {
			_names = new TreeSet<String>();
		}

		if (properties != null) {
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				String key = (String)entry.getKey();
				String value = (String)entry.getValue();

				setProperty(key, value);
			}
		}
	}

	public SortedProperties(Properties properties) {
		this(null, properties);
	}

	@Override
	public void clear() {
		super.clear();

		_names.clear();
	}

	@Override
	public void list(PrintStream out) {
		System.out.println("-- listing properties --");

		Enumeration<String> enu = propertyNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			out.println(name + StringPool.EQUAL + getProperty(name));
		}
	}

	@Override
	public void list(PrintWriter out) {
		System.out.println("-- listing properties --");

		Enumeration<String> enu = propertyNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			out.println(name + StringPool.EQUAL + getProperty(name));
		}
	}

	@Override
	public Enumeration<String> propertyNames() {
		return Collections.enumeration(_names);
	}

	public Object put(String key, String value) {
		if (_names.contains(key)) {
			_names.remove(key);
		}

		_names.add(key);

		return super.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		_names.remove(key);

		return super.remove(key);
	}

	@Override
	public Object setProperty(String key, String value) {
		return put(key, value);
	}

	private Set<String> _names;

}