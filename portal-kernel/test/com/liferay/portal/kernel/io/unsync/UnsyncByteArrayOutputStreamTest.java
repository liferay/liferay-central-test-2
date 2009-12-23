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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.Arrays;

/**
 * <a href="UnsyncByteArrayOutputStreamTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public class UnsyncByteArrayOutputStreamTest extends TestCase {

	public void testBlockWrite() {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();

		unsyncBaos.write(_BUFFER);

		assertEquals(_BUFFER_SIZE, unsyncBaos.size());
		assertTrue(Arrays.equals(_BUFFER, unsyncBaos.toByteArray()));
	}

	public void testConstructor() {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();

		assertEquals(0, unsyncBaos.size());

		unsyncBaos = new UnsyncByteArrayOutputStream(64);

		assertEquals(0, unsyncBaos.size());
	}

	public void testSizeAndReset() {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();

		assertEquals(0, unsyncBaos.size());

		unsyncBaos.write(0);

		assertEquals(1, unsyncBaos.size());

		unsyncBaos.write(1);

		assertEquals(2, unsyncBaos.size());

		unsyncBaos.reset();

		assertEquals(0, unsyncBaos.size());

		unsyncBaos.write(0);

		assertEquals(1, unsyncBaos.size());

		unsyncBaos.write(1);

		assertEquals(2, unsyncBaos.size());
	}

	public void testToByteArray() {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();

		unsyncBaos.write(_BUFFER);

		byte[] byteArray1 = unsyncBaos.toByteArray();

		assertTrue(Arrays.equals(_BUFFER, byteArray1));

		byte[] byteArray2 = unsyncBaos.toByteArray();

		assertTrue(Arrays.equals(_BUFFER, unsyncBaos.toByteArray()));

		assertNotSame(byteArray1, byteArray2);
	}

	public void testToString() throws UnsupportedEncodingException {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();

		unsyncBaos.write(_BUFFER);

		assertEquals(new String(_BUFFER), unsyncBaos.toString());

		String charsetName1 = "UTF-16BE";
		String charsetName2 = "UTF-16LE";

		assertFalse(
			new String(_BUFFER, charsetName1).equals(
				unsyncBaos.toString(charsetName2)));
		assertEquals(
			new String(_BUFFER, charsetName1),
			unsyncBaos.toString(charsetName1));
		assertEquals(
			new String(_BUFFER, charsetName2),
			unsyncBaos.toString(charsetName2));
	}

	public void testWrite() {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();

		for (int i = 0; i < _BUFFER_SIZE; i++) {
			unsyncBaos.write(i);

			assertEquals(i + 1, unsyncBaos.size());
		}

		assertTrue(Arrays.equals(_BUFFER, unsyncBaos.toByteArray()));
	}

	public void testWriteTo() throws IOException {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();

		unsyncBaos.write(_BUFFER);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		unsyncBaos.writeTo(baos);

		assertTrue(Arrays.equals(_BUFFER, baos.toByteArray()));
	}

	private static final int _BUFFER_SIZE = 64;

	private static final byte[] _BUFFER = new byte[_BUFFER_SIZE];

	static {
		for (int i = 0; i < _BUFFER_SIZE; i++) {
			_BUFFER[i] = (byte)i;
		}
	}

}