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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.ListUtil;

import java.io.InputStream;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class TestPropsUtil {

	public static String get(String key) {
		return _instance._get(key);
	}

	public static Properties getProperties() {
		return _instance._props;
	}

	private TestPropsUtil() {
		try {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();

			InputStream is = classLoader.getResourceAsStream(
				"test-portal-impl.properties");

			_props.load(is);

			is = classLoader.getResourceAsStream(
				"test-portal-impl-ext.properties");

			if (is != null) {
				_props.load(is);
			}

			List<String> keys = Collections.list(
				(Enumeration<String>)_props.propertyNames());

			keys = ListUtil.sort(keys);

			System.out.println("-- listing properties --");

			for (String key : keys) {
				System.out.println(key + "=" + _props.getProperty(key));
			}

			System.out.println("");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String _get(String key) {
		return _props.getProperty(key);
	}

	private static TestPropsUtil _instance = new TestPropsUtil();

	private Properties _props = new Properties();

}