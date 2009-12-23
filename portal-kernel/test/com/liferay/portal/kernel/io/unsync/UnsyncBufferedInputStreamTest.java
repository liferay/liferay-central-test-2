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

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * <a href="UnsyncBufferedInputStreamTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedInputStreamTest extends TestCase {

	public void testConstruct() throws IOException {
		int testSize = 10;
		UnsyncBufferedInputStream unsyncBis =
			new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[testSize]));
		assertEquals(testSize, unsyncBis.available());

		unsyncBis =
			new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[testSize]), testDataSize);
		assertEquals(testSize, unsyncBis.available());
	}

	public void testClose() throws IOException {
		int testSize = 10;
		UnsyncBufferedInputStream unsyncBis =
			new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[testSize]));

		unsyncBis.close();

		assertTrue(unsyncBis.in == null);
		assertTrue(unsyncBis.buffer == null);
	}

	public void testMarkAndReset() throws IOException {

		UnsyncBufferedInputStream unsyncBis =
			new UnsyncBufferedInputStream(
			new ByteArrayInputStream(testData));
		int markLimit = 10;
		//normal mark
		assertEquals(-1, unsyncBis.markIndex);
		
		unsyncBis.mark(markLimit);

		assertEquals(0, unsyncBis.markIndex);
		
		assertEquals(testDataSize, unsyncBis.available());
		assertEquals(0, unsyncBis.read());
		assertEquals(1, unsyncBis.read());
		assertEquals(2, unsyncBis.read());
		assertEquals(3, unsyncBis.index);

		unsyncBis.reset();

		assertEquals(testDataSize, unsyncBis.available());
		assertEquals(0, unsyncBis.read());
		assertEquals(1, unsyncBis.read());
		assertEquals(2, unsyncBis.read());
		assertEquals(3, unsyncBis.index);

		//overrun mark limit

		int bufferSize = 20;
		unsyncBis =
			new UnsyncBufferedInputStream(
			new ByteArrayInputStream(testData), bufferSize);

		assertEquals(-1, unsyncBis.markIndex);

		unsyncBis.mark(markLimit);

		assertEquals(0, unsyncBis.markIndex);
		
		for (int i = 0; i < bufferSize * 2; i++) {
			assertEquals(i, unsyncBis.read());
		}
		assertEquals(bufferSize, unsyncBis.index);
		assertEquals(testDataSize - bufferSize * 2, unsyncBis.available());

		assertEquals(-1, unsyncBis.markIndex);

		//Mark shuffle
		unsyncBis =
			new UnsyncBufferedInputStream(
			new ByteArrayInputStream(testData));
		//Default _MAX_MARK_WASTE_SIZE == 4096
		for (int i = 0; i < 4097; i++) {
			assertEquals(i & 0xff, unsyncBis.read());
		}
		unsyncBis.mark(markLimit);

		assertEquals(testDataSize - 4097, unsyncBis.available());
		assertEquals(4097 & 0xff, unsyncBis.read());
		assertEquals(4098 & 0xff, unsyncBis.read());
		assertEquals(testDataSize - 4099, unsyncBis.available());

		unsyncBis.reset();

		assertEquals(testDataSize - 4097, unsyncBis.available());
		assertEquals(4097 & 0xff, unsyncBis.read());
		assertEquals(4098 & 0xff, unsyncBis.read());
		assertEquals(testDataSize - 4099, unsyncBis.available());

		//Grow Mark
		unsyncBis =
			new UnsyncBufferedInputStream(
			new ByteArrayInputStream(testData), 5);
		assertEquals(0, unsyncBis.read());
		assertEquals(1, unsyncBis.read());

		unsyncBis.mark(markLimit);

		assertEquals(2, unsyncBis.read());
		assertEquals(3, unsyncBis.read());
		assertEquals(4, unsyncBis.read());
		assertEquals(5, unsyncBis.read());

		unsyncBis.reset();

		assertEquals(2, unsyncBis.read());
		assertEquals(3, unsyncBis.read());
		assertEquals(4, unsyncBis.read());
		assertEquals(5, unsyncBis.read());

	}

	public void testMarkSupported() {
		int testSize = 10;
		UnsyncBufferedInputStream unsyncBis =
			new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[testSize]));
		assertTrue(unsyncBis.markSupported());
	}

	public void testRead() throws IOException {
		int bufferSize = 10;
		ByteArrayInputStream bais = new ByteArrayInputStream(testData);
		UnsyncBufferedInputStream unsyncBis =
			new UnsyncBufferedInputStream(bais, bufferSize);
		assertEquals(testDataSize, bais.available());
		assertEquals(testDataSize, unsyncBis.available());

		assertEquals(0, unsyncBis.read());

		assertEquals(testDataSize - bufferSize, bais.available());
		assertEquals(testDataSize - 1, unsyncBis.available());

		for (int i = 1; i < bufferSize + 1; i++) {
			assertEquals(i, unsyncBis.read());
		}

		assertEquals(testDataSize - bufferSize * 2, bais.available());
		assertEquals(testDataSize - bufferSize - 1, unsyncBis.available());
	}

	public void testBlockRead() throws IOException {
		int bufferSize = 10;
		ByteArrayInputStream bais = new ByteArrayInputStream(testData);
		UnsyncBufferedInputStream unsyncBis =
			new UnsyncBufferedInputStream(bais, bufferSize);
		assertEquals(testDataSize, bais.available());
		assertEquals(testDataSize, unsyncBis.available());

		//Hit in memory
		assertEquals(0, unsyncBis.read());
		assertEquals(testDataSize - bufferSize, bais.available());
		assertEquals(testDataSize - 1, unsyncBis.available());

		byte[] testBuffer = new byte[5];
		int number = unsyncBis.read(testBuffer);
		assertEquals(testBuffer.length, number);
		for (int i = 1; i < testBuffer.length + 1; i++) {
			assertEquals(i, testBuffer[i - 1]);
		}

		//Exhaust buffer
		assertEquals(6, unsyncBis.read());
		assertEquals(7, unsyncBis.read());
		assertEquals(8, unsyncBis.read());
		assertEquals(9, unsyncBis.read());

		//Force to load again
		number = unsyncBis.read(testBuffer);
		assertEquals(testBuffer.length, number);
		for (int i = 10; i < testBuffer.length + 10; i++) {
			assertEquals(i, testBuffer[i - 10]);
		}

		assertEquals(testDataSize - bufferSize * 2, bais.available());
		assertEquals(testDataSize - 15, unsyncBis.available());

		//Try best to fill the buffer
		byte[] testBuffer2 = new byte[10];
		number = unsyncBis.read(testBuffer2);
		assertEquals(testBuffer2.length, number);
		for (int i = 15; i < testBuffer2.length + 15; i++) {
			assertEquals(i, testBuffer2[i - 15]);
		}

		assertEquals(testDataSize - bufferSize * 3, bais.available());
		assertEquals(testDataSize - 25, unsyncBis.available());

		//Leave 5 bytes
		for (int i = 25; i < testDataSize - 5; i++) {
			assertEquals(i & 0xff, unsyncBis.read());
		}

		assertEquals(testDataSize % 5, bais.available());
		assertEquals(5, unsyncBis.available());

		//Finish reading
		number = unsyncBis.read(testBuffer2);
		assertEquals(5, number);

		assertEquals(-1, unsyncBis.read());
	}

	public void testSkip() throws IOException {
		int bufferSize = 10;
		ByteArrayInputStream bais = new ByteArrayInputStream(testData);
		UnsyncBufferedInputStream unsyncBis =
			new UnsyncBufferedInputStream(bais, bufferSize);
		assertEquals(testDataSize, bais.available());
		assertEquals(testDataSize, unsyncBis.available());
		assertEquals(0, unsyncBis.read());
		assertEquals(testDataSize - bufferSize, bais.available());
		assertEquals(testDataSize - 1, unsyncBis.available());
		//Skip in memory
		long skippedNumber = unsyncBis.skip(bufferSize * 2);
		assertEquals(bufferSize - 1, skippedNumber);

		assertEquals(10, unsyncBis.read());

		skippedNumber = unsyncBis.skip(bufferSize * 2);
		assertEquals(bufferSize - 1, skippedNumber);

		//Directly skip underlying stream
		skippedNumber = unsyncBis.skip(bufferSize * 2);
		assertEquals(bufferSize * 2, skippedNumber);

		assertEquals(40, unsyncBis.read());

		//Mark skip
		unsyncBis.mark(bufferSize * 2);
		//in-memory
		skippedNumber = unsyncBis.skip(bufferSize * 2);
		assertEquals(bufferSize - 1, skippedNumber);
		//force read skip
		skippedNumber = unsyncBis.skip(bufferSize / 2);
		assertEquals(bufferSize / 2, skippedNumber);

		unsyncBis.reset();
		assertEquals(41, unsyncBis.read());
	}

	private static final int testDataSize = 16 * 1024;
	private static final byte[] testData = new byte[testDataSize];

	static {
		for (int i = 0; i < testDataSize; i++) {
			testData[i] = (byte) i;
		}
	}

}