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

import java.nio.charset.Charset;

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
		unsyncBaos.write(testData);
		assertEquals(testDataSize, unsyncBaos.size());
		assertTrue(Arrays.equals(testData, unsyncBaos.toByteArray()));
	}

	public void testConstruct() {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();
		int size = unsyncBaos.size();
		assertEquals(0, size);

		unsyncBaos =
			new UnsyncByteArrayOutputStream(64);
		size = unsyncBaos.size();
		assertEquals(0, size);
	}

	public void testSizeAndReset() {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();
		int size = unsyncBaos.size();
		assertEquals(0, size);
		unsyncBaos.write(0);
		size = unsyncBaos.size();
		assertEquals(1, size);
		unsyncBaos.write(1);
		size = unsyncBaos.size();
		assertEquals(2, size);

		unsyncBaos.reset();

		size = unsyncBaos.size();
		assertEquals(0, size);
		unsyncBaos.write(0);
		size = unsyncBaos.size();
		assertEquals(1, size);
		unsyncBaos.write(1);
		size = unsyncBaos.size();
		assertEquals(2, size);
	}

	public void testToByteArray() {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();
		unsyncBaos.write(testData);
		byte[] newByteArray1 = unsyncBaos.toByteArray();

		assertTrue(Arrays.equals(testData, newByteArray1));

		byte[] newByteArray2 = unsyncBaos.toByteArray();

		assertTrue(Arrays.equals(testData, newByteArray2));

		//Make sure it creates a new copy everytime
		assertNotSame(newByteArray1, newByteArray2);
	}

	public void testToString() throws UnsupportedEncodingException {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();
		unsyncBaos.write(testData);

		assertEquals(new String(testData), unsyncBaos.toString());
		String testCharSetName1 = "UTF-16BE";
		String testCharSetName2 = "UTF-16LE";
		assertFalse(
			new String(
			testData, Charset.forName(testCharSetName1)).equals(
			unsyncBaos.toString(testCharSetName2)));
		assertEquals(
			new String(testData, Charset.forName(testCharSetName1)),
			unsyncBaos.toString(testCharSetName1));
		assertEquals(
			new String(testData, Charset.forName(testCharSetName2)),
			unsyncBaos.toString(testCharSetName2));
	}

	public void testWrite() {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();
		for (int i = 0; i < testDataSize; i++) {
			unsyncBaos.write(i);
			assertEquals(i + 1, unsyncBaos.size());
		}
		assertTrue(Arrays.equals(testData, unsyncBaos.toByteArray()));
	}

	public void testWriteTo() throws IOException {
		UnsyncByteArrayOutputStream unsyncBaos =
			new UnsyncByteArrayOutputStream();
		unsyncBaos.write(testData);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		unsyncBaos.writeTo(baos);

		assertTrue(Arrays.equals(testData, baos.toByteArray()));
	}

	private static final int testDataSize = 64;
	private static final byte[] testData = new byte[testDataSize];

	static {
		for (int i = 0; i < testDataSize; i++) {
			testData[i] = (byte) i;
		}
	}

}