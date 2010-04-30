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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <a href="PropertiesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PropertiesUtil {

	public static void copyProperties(Properties from, Properties to) {
		Iterator<Map.Entry<Object, Object>> itr = from.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Object, Object> entry = itr.next();

			to.setProperty((String)entry.getKey(), (String)entry.getValue());
		}
	}

	public static Properties fromMap(Map<String, String> map) {
		Properties properties = new Properties();

		Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();

			String key = entry.getKey();
			String value = entry.getValue();

			if (value != null) {
				properties.setProperty(key, value);
			}
		}

		return properties;
	}

	public static Properties fromMap(Properties properties) {
		return properties;
	}

	public static void fromProperties(
		Properties properties, Map<String, String> map) {

		map.clear();

		Iterator<Map.Entry<Object, Object>> itr =
			properties.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<Object, Object> entry = itr.next();

			map.put((String)entry.getKey(), (String)entry.getValue());
		}
	}

	public static Properties getProperties(
		Properties properties, String prefix, boolean removePrefix) {

		Properties subProperties = new Properties();

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			if (key.startsWith(prefix)) {
				String value = properties.getProperty(key);

				if (removePrefix) {
					key = key.substring(prefix.length());
				}

				subProperties.setProperty(key, value);
			}
		}

		return subProperties;
	}

	public static String list(Map<String, String> map) {
		Properties properties = fromMap(map);

		return list(properties);
	}

	public static void list(Map<String, String> map, PrintStream out) {
		Properties properties = fromMap(map);

		properties.list(out);
	}

	public static void list(Map<String, String> map, PrintWriter out) {
		Properties properties = fromMap(map);

		properties.list(out);
	}

	public static String list(Properties properties) {
		UnsyncByteArrayOutputStream ubaos = new UnsyncByteArrayOutputStream();
		PrintStream ps = new PrintStream(ubaos);

		properties.list(ps);

		return ubaos.toString();
	}

	public static void load(Properties p, String s) throws IOException {
		if (Validator.isNotNull(s)) {
			s = UnicodeFormatter.toString(s);

			s = StringUtil.replace(s, "\\u003d", "=");
			s = StringUtil.replace(s, "\\u000a", "\n");
			s = StringUtil.replace(s, "\\u0021", "!");
			s = StringUtil.replace(s, "\\u0023", "#");
			s = StringUtil.replace(s, "\\u0020", " ");
			s = StringUtil.replace(s, "\\u005c", "\\");

			p.load(new UnsyncByteArrayInputStream(s.getBytes()));

			List<String> propertyNames = Collections.list(
				(Enumeration<String>)p.propertyNames());

			for (int i = 0; i < propertyNames.size(); i++) {
				String key = propertyNames.get(i);

				String value = p.getProperty(key);

				// Trim values because it may leave a trailing \r in certain
				// Windows environments. This is a known case for loading SQL
				// scripts in SQL Server.

				if (value != null) {
					value = value.trim();

					p.setProperty(key, value);
				}
			}
		}
	}

	public static Properties load(String s) throws IOException {
		Properties p = new Properties();

		load(p, s);

		return p;
	}

	public static void merge(Properties p1, Properties p2) {
		Enumeration<String> enu = (Enumeration<String>)p2.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();
			String value = p2.getProperty(key);

			p1.setProperty(key, value);
		}
	}

	public static String toString(Properties p) {
		SafeProperties safeProperties = null;

		if (p instanceof SafeProperties) {
			safeProperties = (SafeProperties)p;
		}

		StringBundler sb = null;

		if (p.isEmpty()) {
			sb = new StringBundler();
		}
		else {
			sb = new StringBundler(p.size() * 4);
		}

		Enumeration<String> enu = (Enumeration<String>)p.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			sb.append(key);
			sb.append(StringPool.EQUAL);

			if (safeProperties != null) {
				sb.append(safeProperties.getEncodedProperty(key));
			}
			else {
				sb.append(p.getProperty(key));
			}

			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	public static void trimKeys(Properties p) {
		Enumeration<String> enu = (Enumeration<String>)p.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();
			String value = p.getProperty(key);

			String trimmedKey = key.trim();

			if (!key.equals(trimmedKey)) {
				p.remove(key);
				p.setProperty(trimmedKey, value);
			}
		}
	}

}