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

package com.liferay.portal.kernel.util;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Mirco Tamburini
 * @author Brett Randall
 */
public class SystemProperties {

	public static final String SYSTEM_PROPERTIES_QUIET =
		"system.properties.quiet";

	public static final String SYSTEM_PROPERTIES_SET = "system.properties.set";

	public static final String SYSTEM_PROPERTIES_SET_OVERRIDE =
		"system.properties.set.override";

	public static final String TMP_DIR = "java.io.tmpdir";

	public static void clear(String key) {
		System.clearProperty(key);

		_properties.remove(key);
	}

	public static String get(String key) {
		String value = _properties.get(key);

		if (value == null) {
			value = System.getProperty(key);
		}

		return value;
	}

	public static String[] getArray(String key) {
		String value = get(key);

		if (value == null) {
			return new String[0];
		}
		else {
			return StringUtil.split(value);
		}
	}

	public static Properties getProperties() {
		return PropertiesUtil.fromMap(_properties);
	}

	public static void set(String key, String value) {
		System.setProperty(key, value);

		_properties.put(key, value);
	}

	private static final Map<String, String> _properties;

	static {
		Properties properties = new Properties();

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		boolean systemPropertiesQuiet = GetterUtil.getBoolean(
			System.getProperty(SYSTEM_PROPERTIES_QUIET));

		// system.properties

		try {
			Enumeration<URL> enumeration = classLoader.getResources(
				"system.properties");

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				try (InputStream inputStream = url.openStream()) {
					properties.load(inputStream);
				}

				if (!systemPropertiesQuiet) {
					System.out.println("Loading " + url);
				}
			}
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}

		// system-ext.properties

		try {
			Enumeration<URL> enumeration = classLoader.getResources(
				"system-ext.properties");

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				try (InputStream inputStream = url.openStream()) {
					properties.load(inputStream);
				}

				if (!systemPropertiesQuiet) {
					System.out.println("Loading " + url);
				}
			}
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}

		// Set environment properties

		SystemEnv.setProperties(properties);

		// Set system properties

		boolean systemPropertiesSet = GetterUtil.getBoolean(
			System.getProperty(SYSTEM_PROPERTIES_SET), true);

		boolean systemPropertiesSetOverride = GetterUtil.getBoolean(
			System.getProperty(SYSTEM_PROPERTIES_SET_OVERRIDE), true);

		if (systemPropertiesSet) {
			Enumeration<String> enu =
				(Enumeration<String>)properties.propertyNames();

			while (enu.hasMoreElements()) {
				String key = enu.nextElement();

				if (systemPropertiesSetOverride ||
					Validator.isNull(System.getProperty(key))) {

					System.setProperty(key, properties.getProperty(key));
				}
			}
		}

		_properties = new ConcurrentHashMap<>();

		// Use a fast concurrent hash map implementation instead of the slower
		// java.util.Properties

		PropertiesUtil.fromProperties(properties, _properties);
	}

}