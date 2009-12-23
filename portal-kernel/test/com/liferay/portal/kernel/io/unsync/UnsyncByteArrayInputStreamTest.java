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
		UnsyncByteArrayInputStream unsyncBais =
			new UnsyncByteArrayInputStream(testData);
		int blockSize = testDataSize * 2 / 3;
		byte[] block = new byte[blockSize];

		int number = unsyncBais.read(block);

		assertEquals(blockSize, number);
		for (int i = 0; i < number; i++) {
			assertEquals(i, block[i]);
		}

		number = unsyncBais.read(block);

		assertEquals(testDataSize - blockSize, number);
		for (int i = 0; i < number; i++) {
			assertEquals(i + blockSize, block[i]);
		}
	}

	public void testConstruct() {
		UnsyncByteArrayInputStream unsyncBais =
			new UnsyncByteArrayInputStream(testData);
		int available = unsyncBais.available();
		assertEquals(testDataSize, available);

		unsyncBais =
			new UnsyncByteArrayInputStream(
			testData, testDataSize / 2, testDataSize / 2);
		available = unsyncBais.available();
		assertEquals(testDataSize / 2, available);
	}

	public void testMarkAndReset() {
		UnsyncByteArrayInputStream unsyncBais =
			new UnsyncByteArrayInputStream(testData);
		int result = unsyncBais.read();
		assertEquals(0, result);
		result = unsyncBais.read();
		assertEquals(1, result);

		unsyncBais.mark(-1);

		int available = unsyncBais.available();
		assertEquals(testDataSize - 2, available);

		result = unsyncBais.read();
		assertEquals(2, result);
		result = unsyncBais.read();
		assertEquals(3, result);
		available = unsyncBais.available();
		assertEquals(testDataSize - 4, available);

		unsyncBais.reset();
		available = unsyncBais.available();
		assertEquals(testDataSize - 2, available);
		result = unsyncBais.read();
		assertEquals(2, result);
		result = unsyncBais.read();
		assertEquals(3, result);
		available = unsyncBais.available();
		assertEquals(testDataSize - 4, available);
	}

	public void testMarkSupported() {
		UnsyncByteArrayInputStream unsyncBais =
			new UnsyncByteArrayInputStream(testData);
		assertTrue(unsyncBais.markSupported());
	}

	public void testRead() {
		UnsyncByteArrayInputStream unsyncBais =
			new UnsyncByteArrayInputStream(testData);
		for (int i = 0; i < testDataSize; i++) {
			assertEquals(i, unsyncBais.read());
		}
		assertEquals(-1, unsyncBais.read());
	}

	public void testSkip() {
		UnsyncByteArrayInputStream unsyncBais =
			new UnsyncByteArrayInputStream(testData);
		long skipSize = testDataSize * 2 / 3;

		long actualSkipSize = unsyncBais.skip(skipSize);

		assertEquals(skipSize, actualSkipSize);
		assertEquals(testDataSize - skipSize, unsyncBais.available());

		actualSkipSize = unsyncBais.skip(skipSize);

		assertEquals(testDataSize - skipSize, actualSkipSize);
		assertEquals(0, unsyncBais.available());
	}

	private static final int testDataSize = 10;
	private static final byte[] testData = new byte[testDataSize];

	static {
		for (int i = 0; i < testDataSize; i++) {
			testData[i] = (byte) i;
		}
	}

}