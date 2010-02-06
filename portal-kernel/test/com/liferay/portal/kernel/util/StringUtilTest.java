/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.test.TestCase;

/**
 * <a href="StringUtilTest.java.html"><b><i>View Source</i></b></a>
 *
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