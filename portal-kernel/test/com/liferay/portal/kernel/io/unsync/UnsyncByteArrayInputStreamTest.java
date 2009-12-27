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

/**
 * <a href="UnsyncByteArrayInputStreamTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public class UnsyncByteArrayInputStreamTest extends TestCase {

	public void testBlockRead() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		int size = _SIZE * 2 / 3;

		byte[] buffer = new byte[size];

		int read = unsyncByteArrayInputStream.read(buffer);

		assertEquals(size, read);

		for (int i = 0; i < read; i++) {
			assertEquals(i, buffer[i]);
		}

		read = unsyncByteArrayInputStream.read(buffer);

		assertEquals(_SIZE - size, read);

		for (int i = 0; i < read; i++) {
			assertEquals(i + size, buffer[i]);
		}
	}

	public void testConstructor() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		assertEquals(_SIZE, unsyncByteArrayInputStream.available());

		unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(
			_BUFFER, _SIZE / 2, _SIZE / 2);

		assertEquals(_SIZE / 2, unsyncByteArrayInputStream.available());
	}

	public void testMarkAndReset() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		assertEquals(0, unsyncByteArrayInputStream.read());
		assertEquals(1, unsyncByteArrayInputStream.read());

		unsyncByteArrayInputStream.mark(-1);

		assertEquals(_SIZE - 2, unsyncByteArrayInputStream.available());
		assertEquals(2, unsyncByteArrayInputStream.read());
		assertEquals(3, unsyncByteArrayInputStream.read());
		assertEquals(_SIZE - 4, unsyncByteArrayInputStream.available());

		unsyncByteArrayInputStream.reset();

		assertEquals(_SIZE - 2, unsyncByteArrayInputStream.available());
		assertEquals(2, unsyncByteArrayInputStream.read());
		assertEquals(3, unsyncByteArrayInputStream.read());

		assertEquals(_SIZE - 4, unsyncByteArrayInputStream.available());
	}

	public void testMarkSupported() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		assertTrue(unsyncByteArrayInputStream.markSupported());
	}

	public void testRead() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		for (int i = 0; i < _SIZE; i++) {
			assertEquals(i, unsyncByteArrayInputStream.read());
		}

		assertEquals(-1, unsyncByteArrayInputStream.read());
	}

	public void testSkip() {
		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(_BUFFER);

		long size = _SIZE * 2 / 3;

		assertEquals(size, unsyncByteArrayInputStream.skip(size));
		assertEquals(
			_SIZE - size, unsyncByteArrayInputStream.available());
		assertEquals(
			_SIZE - size, unsyncByteArrayInputStream.skip(size));
		assertEquals(0, unsyncByteArrayInputStream.available());
	}

	private static final int _SIZE = 10;

	private static final byte[] _BUFFER = new byte[_SIZE];

	static {
		for (int i = 0; i < _SIZE; i++) {
			_BUFFER[i] = (byte)i;
		}
	}

}