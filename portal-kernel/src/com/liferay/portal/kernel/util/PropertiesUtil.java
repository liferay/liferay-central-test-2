/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
		UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		properties.list(ps);

		return baos.toString();
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

		StringBuilder sb = new StringBuilder();

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