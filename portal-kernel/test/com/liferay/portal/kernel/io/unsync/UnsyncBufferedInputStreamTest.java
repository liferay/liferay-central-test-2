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

	public void testBlockRead() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(_BUFFER);

		int bufferSize = 10;

		UnsyncBufferedInputStream unsyncBis = new UnsyncBufferedInputStream(
			bais, bufferSize);

		assertEquals(_BUFFER_SIZE, bais.available());
		assertEquals(_BUFFER_SIZE, unsyncBis.available());

		// In-memory

		assertEquals(0, unsyncBis.read());
		assertEquals(_BUFFER_SIZE - bufferSize, bais.available());
		assertEquals(_BUFFER_SIZE - 1, unsyncBis.available());

		byte[] buffer = new byte[5];

		int number = unsyncBis.read(buffer);

		assertEquals(buffer.length, number);

		for (int i = 1; i < buffer.length + 1; i++) {
			assertEquals(i, buffer[i - 1]);
		}

		// Exhaust buffer

		assertEquals(6, unsyncBis.read());
		assertEquals(7, unsyncBis.read());
		assertEquals(8, unsyncBis.read());
		assertEquals(9, unsyncBis.read());

		// Force reload

		number = unsyncBis.read(buffer);

		assertEquals(buffer.length, number);

		for (int i = 10; i < buffer.length + 10; i++) {
			assertEquals(i, buffer[i - 10]);
		}

		assertEquals(_BUFFER_SIZE - bufferSize * 2, bais.available());
		assertEquals(_BUFFER_SIZE - 15, unsyncBis.available());

		// Fill the buffer

		buffer = new byte[10];

		number = unsyncBis.read(buffer);

		assertEquals(buffer.length, number);

		for (int i = 15; i < buffer.length + 15; i++) {
			assertEquals(i, buffer[i - 15]);
		}

		assertEquals(_BUFFER_SIZE - bufferSize * 3, bais.available());
		assertEquals(_BUFFER_SIZE - 25, unsyncBis.available());

		// Leave 5 bytes

		for (int i = 25; i < _BUFFER_SIZE - 5; i++) {
			assertEquals(i & 0xff, unsyncBis.read());
		}

		assertEquals(_BUFFER_SIZE % 5, bais.available());
		assertEquals(5, unsyncBis.available());

		// Finish

		number = unsyncBis.read(buffer);

		assertEquals(5, number);

		assertEquals(-1, unsyncBis.read());
	}

	public void testClose() throws IOException {
		int bufferSize = 10;

		UnsyncBufferedInputStream unsyncBis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[bufferSize]));

		unsyncBis.close();

		assertTrue(unsyncBis.inputStream == null);
		assertTrue(unsyncBis.buffer == null);
	}

	public void testConstructor() throws IOException {
		int bufferSize = 10;

		UnsyncBufferedInputStream unsyncBis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[bufferSize]));

		assertEquals(bufferSize, unsyncBis.available());

		unsyncBis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[bufferSize]), _BUFFER_SIZE);

		assertEquals(bufferSize, unsyncBis.available());
	}

	public void testMarkAndReset() throws IOException {
		UnsyncBufferedInputStream unsyncBis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER));

		// Normal

		assertEquals(-1, unsyncBis.markIndex);

		int markLimit = 10;

		unsyncBis.mark(markLimit);

		assertEquals(0, unsyncBis.markIndex);
		assertEquals(_BUFFER_SIZE, unsyncBis.available());
		assertEquals(0, unsyncBis.read());
		assertEquals(1, unsyncBis.read());
		assertEquals(2, unsyncBis.read());
		assertEquals(3, unsyncBis.index);

		unsyncBis.reset();

		assertEquals(_BUFFER_SIZE, unsyncBis.available());
		assertEquals(0, unsyncBis.read());
		assertEquals(1, unsyncBis.read());
		assertEquals(2, unsyncBis.read());
		assertEquals(3, unsyncBis.index);

		// Overrun

		int bufferSize = 20;

		unsyncBis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER), bufferSize);

		assertEquals(-1, unsyncBis.markIndex);

		unsyncBis.mark(markLimit);

		assertEquals(0, unsyncBis.markIndex);

		for (int i = 0; i < bufferSize * 2; i++) {
			assertEquals(i, unsyncBis.read());
		}

		assertEquals(bufferSize, unsyncBis.index);
		assertEquals(_BUFFER_SIZE - bufferSize * 2, unsyncBis.available());

		assertEquals(-1, unsyncBis.markIndex);

		// Shuffle

		unsyncBis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER));

		// _MAX_MARK_WASTE_SIZE defaults to 4096

		for (int i = 0; i < 4097; i++) {
			assertEquals(i & 0xff, unsyncBis.read());
		}

		unsyncBis.mark(markLimit);

		assertEquals(_BUFFER_SIZE - 4097, unsyncBis.available());
		assertEquals(4097 & 0xff, unsyncBis.read());
		assertEquals(4098 & 0xff, unsyncBis.read());
		assertEquals(_BUFFER_SIZE - 4099, unsyncBis.available());

		unsyncBis.reset();

		assertEquals(_BUFFER_SIZE - 4097, unsyncBis.available());
		assertEquals(4097 & 0xff, unsyncBis.read());
		assertEquals(4098 & 0xff, unsyncBis.read());
		assertEquals(_BUFFER_SIZE - 4099, unsyncBis.available());

		// Grow

		unsyncBis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER), 5);

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
		int bufferSize = 10;

		UnsyncBufferedInputStream unsyncBis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[bufferSize]));

		assertTrue(unsyncBis.markSupported());
	}

	public void testRead() throws IOException {
		int bufferSize = 10;

		ByteArrayInputStream bais = new ByteArrayInputStream(_BUFFER);

		UnsyncBufferedInputStream unsyncBis = new UnsyncBufferedInputStream(
			bais, bufferSize);

		assertEquals(_BUFFER_SIZE, bais.available());
		assertEquals(_BUFFER_SIZE, unsyncBis.available());

		assertEquals(0, unsyncBis.read());

		assertEquals(_BUFFER_SIZE - bufferSize, bais.available());
		assertEquals(_BUFFER_SIZE - 1, unsyncBis.available());

		for (int i = 1; i < bufferSize + 1; i++) {
			assertEquals(i, unsyncBis.read());
		}

		assertEquals(_BUFFER_SIZE - bufferSize * 2, bais.available());
		assertEquals(_BUFFER_SIZE - bufferSize - 1, unsyncBis.available());
	}

	public void testSkip() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(_BUFFER);

		int bufferSize = 10;

		UnsyncBufferedInputStream unsyncBis = new UnsyncBufferedInputStream(
			bais, bufferSize);

		assertEquals(_BUFFER_SIZE, bais.available());
		assertEquals(_BUFFER_SIZE, unsyncBis.available());
		assertEquals(0, unsyncBis.read());
		assertEquals(_BUFFER_SIZE - bufferSize, bais.available());
		assertEquals(_BUFFER_SIZE - 1, unsyncBis.available());

		// In-memory

		assertEquals(bufferSize - 1, unsyncBis.skip(bufferSize * 2));
		assertEquals(10, unsyncBis.read());

		assertEquals(bufferSize - 1, unsyncBis.skip(bufferSize * 2));

		// Underlying input stream

		assertEquals(bufferSize * 2, unsyncBis.skip(bufferSize * 2));
		assertEquals(40, unsyncBis.read());

		// Mark

		unsyncBis.mark(bufferSize * 2);

		// In-memory

		assertEquals(bufferSize - 1, unsyncBis.skip(bufferSize * 2));

		// Force

		assertEquals(bufferSize / 2, unsyncBis.skip(bufferSize / 2));

		unsyncBis.reset();

		assertEquals(41, unsyncBis.read());
	}

	private static final int _BUFFER_SIZE = 16 * 1024;

	private static final byte[] _BUFFER = new byte[_BUFFER_SIZE];

	static {
		for (int i = 0; i < _BUFFER_SIZE; i++) {
			_BUFFER[i] = (byte)i;
		}
	}

}