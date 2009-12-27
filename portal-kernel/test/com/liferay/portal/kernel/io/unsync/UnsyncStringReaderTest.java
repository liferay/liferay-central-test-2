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
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader(
			"abcdefg");

		char[] charArray = new char[4];

		assertEquals(4, unsyncStringReader.read(charArray));
		assertEquals(4, unsyncStringReader.index);
		assertTrue(Arrays.equals("abcd".toCharArray(), charArray));

		assertEquals(3, unsyncStringReader.read(charArray));
		assertEquals('e', charArray[0]);
		assertEquals('f', charArray[1]);
		assertEquals('g', charArray[2]);

		assertEquals(-1, unsyncStringReader.read(charArray));
	}

	public void testConstructor() {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		assertEquals("abc", unsyncStringReader.string);
		assertEquals(3, unsyncStringReader.stringLength);

		unsyncStringReader = new UnsyncStringReader("defg");

		assertEquals("defg", unsyncStringReader.string);
		assertEquals(4, unsyncStringReader.stringLength);
	}

	public void testMarkAndReset() {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		assertEquals('a', unsyncStringReader.read());

		unsyncStringReader.mark(-1);

		assertEquals('b', unsyncStringReader.read());
		assertEquals('c', unsyncStringReader.read());
		assertEquals(-1, unsyncStringReader.read());

		unsyncStringReader.reset();

		assertEquals('b', unsyncStringReader.read());
		assertEquals('c', unsyncStringReader.read());
		assertEquals(-1, unsyncStringReader.read());
	}

	public void testMarkSupported() {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		assertTrue(unsyncStringReader.markSupported());
	}

	public void testRead() {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader("abc");

		assertEquals('a', unsyncStringReader.read());
		assertEquals('b', unsyncStringReader.read());
		assertEquals('c', unsyncStringReader.read());
		assertEquals(-1, unsyncStringReader.read());
	}

	public void testSkip() {
		UnsyncStringReader unsyncStringReader = new UnsyncStringReader(
			"abcdef");

		assertEquals('a', unsyncStringReader.read());
		assertEquals(2, unsyncStringReader.skip(2));
		assertEquals('d', unsyncStringReader.read());
		assertEquals(2, unsyncStringReader.skip(3));
		assertEquals(-1, unsyncStringReader.read());
	}

}