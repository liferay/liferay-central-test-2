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

import com.liferay.portal.kernel.test.TestCase;

/**
 * @author Alexander Chow
 */
public class StringUtilTest extends TestCase {

	public void testReplaceChar() throws Exception {
		String original = "127.0.0.1";
		String expected = "127_0_0_1";

		String actual = StringUtil.replace(original, '.', '_');

		assertEquals(expected, actual);
	}

	public void testReplaceString() throws Exception {
		String original = "Hello World HELLO WORLD Hello World";
		String expected = "Aloha World HELLO WORLD Aloha World";

		String actual = StringUtil.replace(original, "Hello", "Aloha");

		assertEquals(expected, actual);
	}

	public void testReplaceStringArray() throws Exception {
		String original = "Hello World HELLO WORLD Hello World";
		String expected = "Aloha World ALOHA WORLD Aloha World";

		String actual = StringUtil.replace(
			original,
			new String[] {"Hello", "HELLO"},
			new String[] {"Aloha", "ALOHA"});

		assertEquals(expected, actual);
	}

}