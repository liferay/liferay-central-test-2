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
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.test.TestCase;

import java.util.Properties;

/**
 * @author Shuyang Zhou
 */
public class PropertiesUtilTest extends TestCase {

	public void testLoad() throws Exception {

		Properties properties = PropertiesUtil.load(propertiesString);

		for(String[] property : propertyArray) {
			assertEquals(property[1], properties.get(property[0]));
		}
	}

	public void testLoadJDK5() throws Exception {

		byte[] utf8Encoded = propertiesString.getBytes(StringPool.UTF8);
		Properties properties = PropertiesUtil.loadJDK5(
			new UnsyncByteArrayInputStream(utf8Encoded), StringPool.UTF8);

		for(String[] property : propertyArray) {
			assertEquals(property[1], properties.get(property[0]));
		}
	}

	public void testLoadJDK6() throws Exception {

		if (JavaProps.isJDK6()) {
			Properties properties = PropertiesUtil.loadJDK6(
				new UnsyncStringReader(propertiesString));

			for(String[] property : propertyArray) {
				assertEquals(property[1], properties.get(property[0]));
			}
		}
	}

	private final static String[][] propertyArray = {
		{"testKey", "testValue"},
		{"测试键", "测试值"}
	};

	private static String propertiesString;

	static {
		StringBundler sb = new StringBundler(propertyArray.length * 4);

		for(String[] property : propertyArray) {
			sb.append(property[0]);
			sb.append(StringPool.EQUAL);
			sb.append(property[1]);
			sb.append(StringPool.NEW_LINE);
		}
		propertiesString = sb.toString();
	}

}