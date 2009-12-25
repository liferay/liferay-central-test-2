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

package com.liferay.portal.kernel.io.unsync;

import com.liferay.portal.kernel.test.TestCase;

import java.util.Arrays;

/**
 * <a href="UnsyncStringReaderTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public class UnsyncStringReaderTest extends TestCase {

	public void testBlockRead() {
		UnsyncStringReader usr = new UnsyncStringReader("abcdefg");
		char[] resultData = new char[4];
		int number = usr.read(resultData);
		assertEquals(4, number);
		assertEquals(4, usr.index);
		assertTrue(Arrays.equals("abcd".toCharArray(), resultData));
		number = usr.read(resultData);
		assertEquals(3, number);
		assertEquals('e', resultData[0]);
		assertEquals('f', resultData[1]);
		assertEquals('g', resultData[2]);
		number = usr.read(resultData);
		assertEquals(-1, number);
	}

	public void testConstructor() {
		UnsyncStringReader usr = new UnsyncStringReader("abc");
		assertEquals("abc", usr.string);
		assertEquals(3, usr.capability);

		usr = new UnsyncStringReader("defg");
		assertEquals("defg", usr.string);
		assertEquals(4, usr.capability);
	}

	public void testMarkAndReset() {
		UnsyncStringReader usr = new UnsyncStringReader("abc");
		assertEquals('a', usr.read());
		usr.mark(-1);
		assertEquals('b', usr.read());
		assertEquals('c', usr.read());
		assertEquals(-1, usr.read());
		usr.reset();
		assertEquals('b', usr.read());
		assertEquals('c', usr.read());
		assertEquals(-1, usr.read());
	}

	public void testMarkSupported() {
		UnsyncStringReader usr = new UnsyncStringReader("abc");
		assertTrue(usr.markSupported());
	}

	public void testRead() {
		UnsyncStringReader usr = new UnsyncStringReader("abc");
		assertEquals('a', usr.read());
		assertEquals('b', usr.read());
		assertEquals('c', usr.read());
		assertEquals(-1, usr.read());
	}

	public void testSkip() {
		UnsyncStringReader usr = new UnsyncStringReader("abcdef");
		assertEquals('a', usr.read());
		long skippedNumber = usr.skip(2);
		assertEquals(2, skippedNumber);
		assertEquals('d', usr.read());
		skippedNumber = usr.skip(3);
		assertEquals(2, skippedNumber);
		assertEquals(-1, usr.read());
	}

}