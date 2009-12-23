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

import java.util.Arrays;

/**
 * <a href="UnsyncBufferedOutputStreamTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedOutputStreamTest extends TestCase {

	public void testConstruct() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		UnsyncBufferedOutputStream unsyncBos =
			new UnsyncBufferedOutputStream(baos);
		assertEquals(8192, unsyncBos.buffer.length);

		unsyncBos = new UnsyncBufferedOutputStream(baos, 10);
		assertEquals(10, unsyncBos.buffer.length);
	}

	public void testWrite() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		UnsyncBufferedOutputStream unsyncBos =
			new UnsyncBufferedOutputStream(baos, testDataSize * 2);
		assertEquals(testDataSize * 2, unsyncBos.buffer.length);
		for (int i = 0; i < testDataSize; i++) {
			unsyncBos.write(i);
			assertEquals(i, unsyncBos.buffer[i]);
		}
		unsyncBos.flush();
		byte[] resultData = baos.toByteArray();
		assertTrue(Arrays.equals(testData, resultData));
	}

	public void testBlockWrite() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		UnsyncBufferedOutputStream unsyncBos =
			new UnsyncBufferedOutputStream(baos, testDataSize * 2);
		assertEquals(testDataSize * 2, unsyncBos.buffer.length);
		unsyncBos.write(testData);
		for (int i = 0; i < testDataSize; i++) {
			assertEquals(i, unsyncBos.buffer[i]);
		}
		unsyncBos.write(testData);
		for (int i = testDataSize; i < testDataSize * 2; i++) {
			assertEquals(i - testDataSize, unsyncBos.buffer[i]);
		}
		//auto flush
		unsyncBos.write(100);
		assertEquals(100, unsyncBos.buffer[0]);
		assertEquals(testDataSize * 2, baos.size());
	}

	private static final int testDataSize = 10;
	private static final byte[] testData = new byte[testDataSize];

	static {
		for (int i = 0; i < testDataSize; i++) {
			testData[i] = (byte) i;
		}
	}

}