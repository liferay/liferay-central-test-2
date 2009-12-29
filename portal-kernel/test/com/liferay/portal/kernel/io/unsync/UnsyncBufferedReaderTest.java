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
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * <a href="UnsyncBufferedReaderTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedReaderTest extends TestCase {

	public void testBlockRead() throws IOException {
		StringReader stringReader = new StringReader("abcdefghi");

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			stringReader, 5);

		assertEquals(5, unsyncBufferedReader.buffer.length);
		assertTrue(stringReader.ready());
		assertTrue(unsyncBufferedReader.ready());

		// In-memory

		assertEquals('a', unsyncBufferedReader.read());
		assertEquals(1, unsyncBufferedReader.index);
		assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		char[] buffer = new char[3];

		int read = unsyncBufferedReader.read(buffer);

		assertEquals(buffer.length, read);
		assertEquals('b', buffer[0]);
		assertEquals('c', buffer[1]);
		assertEquals('d', buffer[2]);
		assertEquals(4, unsyncBufferedReader.index);
		assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		// Exhaust buffer

		assertEquals('e', unsyncBufferedReader.read());
		assertEquals(5, unsyncBufferedReader.index);
		assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		// Force reload

		read = unsyncBufferedReader.read(buffer);

		assertEquals(buffer.length, read);

		assertEquals('f', buffer[0]);
		assertEquals('g', buffer[1]);
		assertEquals('h', buffer[2]);

		assertEquals(3, unsyncBufferedReader.index);
		assertEquals(4, unsyncBufferedReader.firstInvalidIndex);

		// Finish

		read = unsyncBufferedReader.read(buffer);

		assertEquals(1, read);
		assertEquals('i', buffer[0]);
		assertEquals(-1, unsyncBufferedReader.read(buffer));
	}

	public void testClose() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""));

		unsyncBufferedReader.close();

		assertNull(unsyncBufferedReader.buffer);
		assertNull(unsyncBufferedReader.reader);

		// Unable to read after close
		try {
			unsyncBufferedReader.read();
			fail();
		}
		catch(IOException ioe) {
		}

		// Unable to block read
		try {
			unsyncBufferedReader.read(new char[5]);
			fail();
		}
		catch(IOException ioe) {
		}

		// Unable to ready
		try {
			unsyncBufferedReader.ready();
			fail();
		}
		catch(IOException ioe) {
		}

		// Unable to mark
		try {
			unsyncBufferedReader.mark(0);
			fail();
		}
		catch(IOException ioe) {
		}

		// Unable to reset
		try {
			unsyncBufferedReader.reset();
			fail();
		}
		catch(IOException ioe) {
		}

		// Unable to skip
		try {
			unsyncBufferedReader.skip(0);
			fail();
		}
		catch(IOException ioe) {
		}

		// Close again should do nothing

		unsyncBufferedReader.close();
	}

	public void testConstructor() {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""));

		assertEquals(8192, unsyncBufferedReader.buffer.length);

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""), 10);

		assertEquals(10, unsyncBufferedReader.buffer.length);
	}

	public void testMarkAndReset() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		// Normal

		assertEquals(-1, unsyncBufferedReader.markIndex);

		int markLimit = 3;

		unsyncBufferedReader.mark(markLimit);

		assertEquals(markLimit, unsyncBufferedReader.markLimit);

		assertEquals(0, unsyncBufferedReader.markIndex);
		assertEquals('a', unsyncBufferedReader.read());
		assertEquals('b', unsyncBufferedReader.read());
		assertEquals('c', unsyncBufferedReader.read());
		assertEquals(3, unsyncBufferedReader.index);

		unsyncBufferedReader.reset();

		assertEquals(0, unsyncBufferedReader.markIndex);
		assertEquals('a', unsyncBufferedReader.read());
		assertEquals('b', unsyncBufferedReader.read());
		assertEquals('c', unsyncBufferedReader.read());
		assertEquals(3, unsyncBufferedReader.index);

		// Overrun

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		assertEquals(-1, unsyncBufferedReader.markIndex);

		unsyncBufferedReader.mark(markLimit);

		assertEquals(markLimit, unsyncBufferedReader.markLimit);
		assertEquals(0, unsyncBufferedReader.markIndex);
		assertEquals('a', unsyncBufferedReader.read());
		assertEquals('b', unsyncBufferedReader.read());
		assertEquals('c', unsyncBufferedReader.read());
		assertEquals('d', unsyncBufferedReader.read());
		assertEquals('e', unsyncBufferedReader.read());
		assertEquals('f', unsyncBufferedReader.read());
		assertEquals(1, unsyncBufferedReader.index);
		assertEquals(-1, unsyncBufferedReader.markIndex);

		// Grow buffer size to 8192, _MAX_MARK_WASTE_SIZE defaults to 4096

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(new String(_BUFFER)), 16);

		unsyncBufferedReader.mark(8192);

		assertEquals(0, unsyncBufferedReader.markIndex);

		for (int i = 0; i < 4097; i++) {
			assertEquals(i % 16 + 'a', unsyncBufferedReader.read());
		}

		assertEquals(8192, unsyncBufferedReader.buffer.length);
		assertEquals(0, unsyncBufferedReader.markIndex);
		assertEquals(4097, unsyncBufferedReader.index);

		// Shuffle

		unsyncBufferedReader.mark(8192);

		assertEquals(4097, unsyncBufferedReader.markIndex);

		for (int i = 4097; i < 8193; i++) {
			assertEquals(i % 16 + 'a', unsyncBufferedReader.read());
		}

		assertEquals(0, unsyncBufferedReader.markIndex);
	}

	public void testMarkSupported() {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghi"), 5);

		assertTrue(unsyncBufferedReader.markSupported());
	}

	public void testRead() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("ab\r\nef"), 3);

		assertEquals(3, unsyncBufferedReader.buffer.length);
		assertEquals(0, unsyncBufferedReader.index);
		assertEquals('a', unsyncBufferedReader.read());
		assertEquals(1, unsyncBufferedReader.index);
		assertEquals('b', unsyncBufferedReader.read());
		assertEquals(2, unsyncBufferedReader.index);
		assertEquals('\r', unsyncBufferedReader.read());
		assertEquals(3, unsyncBufferedReader.index);
		assertEquals('\n', unsyncBufferedReader.read());
		assertEquals(1, unsyncBufferedReader.index);
		assertEquals('e', unsyncBufferedReader.read());
		assertEquals(2, unsyncBufferedReader.index);
		assertEquals('f', unsyncBufferedReader.read());
		assertEquals(3, unsyncBufferedReader.index);
		assertEquals(-1, unsyncBufferedReader.read());
	}

	public void testReadLine() throws IOException {

		// With \r

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc\rde"), 5);

		assertEquals("abc", unsyncBufferedReader.readLine());
		assertEquals(4, unsyncBufferedReader.index);

		// With \n

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc\nde"), 5);

		assertEquals("abc", unsyncBufferedReader.readLine());
		assertEquals(4, unsyncBufferedReader.index);

		// With \r\n

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc\r\nde"), 5);

		assertEquals("abc", unsyncBufferedReader.readLine());
		assertEquals(5, unsyncBufferedReader.index);

		// Without \r or \n

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abc"), 5);

		assertEquals("abc", unsyncBufferedReader.readLine());
		assertEquals(0, unsyncBufferedReader.index);

		// Empty

		assertNull(unsyncBufferedReader.readLine());

		// Load multiple times for one line

		unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghijklmn\r"), 5);

		assertEquals("abcdefghijklmn", unsyncBufferedReader.readLine());
		assertEquals(5, unsyncBufferedReader.index);
	}

	public void testReady() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader(""));

		assertTrue(unsyncBufferedReader.ready());

		unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(new ByteArrayInputStream(new byte[0])));

		assertFalse(unsyncBufferedReader.ready());
	}

	public void testSkip() throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new StringReader("abcdefghijklmnopqrstuvwxyz"), 5);

		assertEquals(0, unsyncBufferedReader.index);
		assertEquals(0, unsyncBufferedReader.firstInvalidIndex);
		assertEquals('a', unsyncBufferedReader.read());
		assertEquals(1, unsyncBufferedReader.index);
		assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		// In-memory

		assertEquals(3, unsyncBufferedReader.skip(3));
		assertEquals(4, unsyncBufferedReader.index);
		assertEquals(5, unsyncBufferedReader.firstInvalidIndex);
		assertEquals(1, unsyncBufferedReader.skip(3));
		assertEquals(5, unsyncBufferedReader.index);
		assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		// Underlying reader

		assertEquals(6, unsyncBufferedReader.skip(6));
		assertEquals(11, unsyncBufferedReader.index);
		assertEquals(5, unsyncBufferedReader.firstInvalidIndex);
		assertEquals('l', unsyncBufferedReader.read());
		assertEquals(1, unsyncBufferedReader.index);
		assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		// Mark

		unsyncBufferedReader.mark(10);

		// In-memory

		assertEquals(4, unsyncBufferedReader.skip(4));
		assertEquals(5, unsyncBufferedReader.index);
		assertEquals(5, unsyncBufferedReader.firstInvalidIndex);

		// Force

		assertEquals(5, unsyncBufferedReader.skip(6));
		assertEquals(10, unsyncBufferedReader.index);
		assertEquals(10, unsyncBufferedReader.firstInvalidIndex);

		unsyncBufferedReader.reset();

		assertEquals(1, unsyncBufferedReader.index);
		assertEquals(10, unsyncBufferedReader.firstInvalidIndex);
		assertEquals('m', unsyncBufferedReader.read());
	}

	private static final int _SIZE = 16 * 1024;

	private static final char[] _BUFFER = new char[_SIZE];

	static {
		for (int i = 0; i < _SIZE; i++) {
			_BUFFER[i] = (char) (i % 16 + 'a');
		}
	}

}