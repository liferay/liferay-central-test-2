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

		UnsyncBufferedInputStream ubis = new UnsyncBufferedInputStream(
			bais, bufferSize);

		assertEquals(_BUFFER_SIZE, bais.available());
		assertEquals(_BUFFER_SIZE, ubis.available());

		// In-memory

		assertEquals(0, ubis.read());
		assertEquals(_BUFFER_SIZE - bufferSize, bais.available());
		assertEquals(_BUFFER_SIZE - 1, ubis.available());

		byte[] buffer = new byte[5];

		int number = ubis.read(buffer);

		assertEquals(buffer.length, number);

		for (int i = 1; i < buffer.length + 1; i++) {
			assertEquals(i, buffer[i - 1]);
		}

		// Exhaust buffer

		assertEquals(6, ubis.read());
		assertEquals(7, ubis.read());
		assertEquals(8, ubis.read());
		assertEquals(9, ubis.read());

		// Force reload

		number = ubis.read(buffer);

		assertEquals(buffer.length, number);

		for (int i = 10; i < buffer.length + 10; i++) {
			assertEquals(i, buffer[i - 10]);
		}

		assertEquals(_BUFFER_SIZE - bufferSize * 2, bais.available());
		assertEquals(_BUFFER_SIZE - 15, ubis.available());

		// Fill the buffer

		buffer = new byte[10];

		number = ubis.read(buffer);

		assertEquals(buffer.length, number);

		for (int i = 15; i < buffer.length + 15; i++) {
			assertEquals(i, buffer[i - 15]);
		}

		assertEquals(_BUFFER_SIZE - bufferSize * 3, bais.available());
		assertEquals(_BUFFER_SIZE - 25, ubis.available());

		// Leave 5 bytes

		for (int i = 25; i < _BUFFER_SIZE - 5; i++) {
			assertEquals(i & 0xff, ubis.read());
		}

		assertEquals(_BUFFER_SIZE % 5, bais.available());
		assertEquals(5, ubis.available());

		// Finish

		number = ubis.read(buffer);

		assertEquals(5, number);

		assertEquals(-1, ubis.read(buffer));
	}

	public void testClose() throws IOException {
		int bufferSize = 10;

		UnsyncBufferedInputStream ubis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[bufferSize]));

		ubis.close();

		assertTrue(ubis.inputStream == null);
		assertTrue(ubis.buffer == null);
	}

	public void testConstructor() throws IOException {
		int bufferSize = 10;

		UnsyncBufferedInputStream ubis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[bufferSize]));

		assertEquals(bufferSize, ubis.available());

		ubis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[bufferSize]), _BUFFER_SIZE);

		assertEquals(bufferSize, ubis.available());
	}

	public void testMarkAndReset() throws IOException {
		UnsyncBufferedInputStream ubis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER));

		// Normal

		assertEquals(-1, ubis.markIndex);

		int markLimit = 10;

		ubis.mark(markLimit);

		assertEquals(0, ubis.markIndex);
		assertEquals(_BUFFER_SIZE, ubis.available());
		assertEquals(0, ubis.read());
		assertEquals(1, ubis.read());
		assertEquals(2, ubis.read());
		assertEquals(3, ubis.index);

		ubis.reset();

		assertEquals(_BUFFER_SIZE, ubis.available());
		assertEquals(0, ubis.read());
		assertEquals(1, ubis.read());
		assertEquals(2, ubis.read());
		assertEquals(3, ubis.index);

		// Overrun

		int bufferSize = 20;

		ubis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER), bufferSize);

		assertEquals(-1, ubis.markIndex);

		ubis.mark(markLimit);

		assertEquals(0, ubis.markIndex);

		for (int i = 0; i < bufferSize * 2; i++) {
			assertEquals(i, ubis.read());
		}

		assertEquals(bufferSize, ubis.index);
		assertEquals(_BUFFER_SIZE - bufferSize * 2, ubis.available());

		assertEquals(-1, ubis.markIndex);

		// Shuffle

		ubis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER));

		// _MAX_MARK_WASTE_SIZE defaults to 4096

		for (int i = 0; i < 4097; i++) {
			assertEquals(i & 0xff, ubis.read());
		}

		ubis.mark(markLimit);

		assertEquals(_BUFFER_SIZE - 4097, ubis.available());
		assertEquals(4097 & 0xff, ubis.read());
		assertEquals(4098 & 0xff, ubis.read());
		assertEquals(_BUFFER_SIZE - 4099, ubis.available());

		ubis.reset();

		assertEquals(_BUFFER_SIZE - 4097, ubis.available());
		assertEquals(4097 & 0xff, ubis.read());
		assertEquals(4098 & 0xff, ubis.read());
		assertEquals(_BUFFER_SIZE - 4099, ubis.available());

		// Grow

		ubis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER), 5);

		assertEquals(0, ubis.read());
		assertEquals(1, ubis.read());

		ubis.mark(markLimit);

		assertEquals(2, ubis.read());
		assertEquals(3, ubis.read());
		assertEquals(4, ubis.read());
		assertEquals(5, ubis.read());

		ubis.reset();

		assertEquals(2, ubis.read());
		assertEquals(3, ubis.read());
		assertEquals(4, ubis.read());
		assertEquals(5, ubis.read());

		// Grow buffer size to 8192, _MAX_MARK_WASTE_SIZE defaults to 4096

		ubis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(_BUFFER), 16);

		ubis.mark(8192);
		assertEquals(0, ubis.markIndex);

		for (int i = 0; i < 4097; i++) {
			assertEquals(i & 0xff, ubis.read());
		}
		assertEquals(8192, ubis.buffer.length);
		assertEquals(0, ubis.markIndex);
		assertEquals(4097, ubis.index);

		// Shuffle

		ubis.mark(8192);
		assertEquals(4097, ubis.markIndex);

		for (int i = 4097; i < 8193; i++) {
			assertEquals(i & 0xff, ubis.read());
		}

		assertEquals(0, ubis.markIndex);
	}

	public void testMarkSupported() {
		int bufferSize = 10;

		UnsyncBufferedInputStream ubis = new UnsyncBufferedInputStream(
			new ByteArrayInputStream(new byte[bufferSize]));

		assertTrue(ubis.markSupported());
	}

	public void testRead() throws IOException {
		int bufferSize = 10;

		ByteArrayInputStream bais = new ByteArrayInputStream(_BUFFER);

		UnsyncBufferedInputStream ubis = new UnsyncBufferedInputStream(
			bais, bufferSize);

		assertEquals(_BUFFER_SIZE, bais.available());
		assertEquals(_BUFFER_SIZE, ubis.available());

		assertEquals(0, ubis.read());

		assertEquals(_BUFFER_SIZE - bufferSize, bais.available());
		assertEquals(_BUFFER_SIZE - 1, ubis.available());

		for (int i = 1; i < bufferSize + 1; i++) {
			assertEquals(i, ubis.read());
		}

		assertEquals(_BUFFER_SIZE - bufferSize * 2, bais.available());
		assertEquals(_BUFFER_SIZE - bufferSize - 1, ubis.available());
	}

	public void testSkip() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(_BUFFER);

		int bufferSize = 10;

		UnsyncBufferedInputStream ubis = new UnsyncBufferedInputStream(
			bais, bufferSize);

		assertEquals(_BUFFER_SIZE, bais.available());
		assertEquals(_BUFFER_SIZE, ubis.available());
		assertEquals(0, ubis.read());
		assertEquals(_BUFFER_SIZE - bufferSize, bais.available());
		assertEquals(_BUFFER_SIZE - 1, ubis.available());

		// In-memory

		assertEquals(bufferSize - 1, ubis.skip(bufferSize * 2));
		assertEquals(10, ubis.read());

		assertEquals(bufferSize - 1, ubis.skip(bufferSize * 2));

		// Underlying input stream

		assertEquals(bufferSize * 2, ubis.skip(bufferSize * 2));
		assertEquals(40, ubis.read());

		// Mark

		ubis.mark(bufferSize * 2);

		// In-memory

		assertEquals(bufferSize - 1, ubis.skip(bufferSize * 2));

		// Force

		assertEquals(bufferSize / 2, ubis.skip(bufferSize / 2));

		ubis.reset();

		assertEquals(41, ubis.read());
	}

	private static final int _BUFFER_SIZE = 16 * 1024;

	private static final byte[] _BUFFER = new byte[_BUFFER_SIZE];

	static {
		for (int i = 0; i < _BUFFER_SIZE; i++) {
			_BUFFER[i] = (byte)i;
		}
	}

}