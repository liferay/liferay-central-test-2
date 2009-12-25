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
		StringReader sr = new StringReader("abcdefghi");
		UnsyncBufferedReader ubr = new UnsyncBufferedReader(sr, 5);

		assertEquals(5, ubr.buffer.length);

		assertTrue(sr.ready());
		assertTrue(ubr.ready());

		// In-memory

		assertEquals('a', ubr.read());
		assertEquals(1, ubr.index);
		assertEquals(5, ubr.firstInvalidIndex);

		char[] buffer = new char[3];

		int number = ubr.read(buffer);

		assertEquals(buffer.length, number);
		assertEquals('b', buffer[0]);
		assertEquals('c', buffer[1]);
		assertEquals('d', buffer[2]);
		assertEquals(4, ubr.index);
		assertEquals(5, ubr.firstInvalidIndex);

		// Exhaust buffer

		assertEquals('e', ubr.read());
		assertEquals(5, ubr.index);
		assertEquals(5, ubr.firstInvalidIndex);

		// Force reload

		number = ubr.read(buffer);

		assertEquals(buffer.length, number);

		assertEquals('f', buffer[0]);
		assertEquals('g', buffer[1]);
		assertEquals('h', buffer[2]);

		assertEquals(3, ubr.index);
		assertEquals(4, ubr.firstInvalidIndex);

		//Finish

		number = ubr.read(buffer);

		assertEquals(1, number);

		assertEquals('i', buffer[0]);

		assertEquals(-1, ubr.read(buffer));
	}

	public void testClose() throws IOException {
		UnsyncBufferedReader ubr =
			new UnsyncBufferedReader(new StringReader(""));
		ubr.close();
		assertNull(ubr.buffer);
		assertNull(ubr.reader);
	}

	public void testConstructor() throws IOException {
		UnsyncBufferedReader ubr =
			new UnsyncBufferedReader(new StringReader(""));
		assertEquals(8192, ubr.buffer.length);
		ubr = new UnsyncBufferedReader(new StringReader(""), 10);
		assertEquals(10, ubr.buffer.length);
	}

	public void testMarkAndReset() throws IOException {
		UnsyncBufferedReader ubr =
			new UnsyncBufferedReader(new StringReader("abcdefghi"), 5);
		// Normal
		assertEquals(-1, ubr.markIndex);

		int markLimit = 3;

		ubr.mark(markLimit);
		assertEquals(markLimit, ubr.markLimit);

		assertEquals(0, ubr.markIndex);
		assertEquals('a', ubr.read());
		assertEquals('b', ubr.read());
		assertEquals('c', ubr.read());
		assertEquals(3, ubr.index);

		ubr.reset();

		assertEquals(0, ubr.markIndex);
		assertEquals('a', ubr.read());
		assertEquals('b', ubr.read());
		assertEquals('c', ubr.read());
		assertEquals(3, ubr.index);

		// Overrun

		ubr = new UnsyncBufferedReader(new StringReader("abcdefghi"), 5);

		assertEquals(-1, ubr.markIndex);

		ubr.mark(markLimit);
		assertEquals(markLimit, ubr.markLimit);

		assertEquals(0, ubr.markIndex);

		assertEquals('a', ubr.read());
		assertEquals('b', ubr.read());
		assertEquals('c', ubr.read());
		assertEquals('d', ubr.read());
		assertEquals('e', ubr.read());
		assertEquals('f', ubr.read());
		assertEquals(1, ubr.index);
		assertEquals(-1, ubr.markIndex);

		// Grow buffer size to 8192, _MAX_MARK_WASTE_SIZE defaults to 4096

		ubr = new UnsyncBufferedReader(
			new StringReader(new String(_BUFFER)), 16);

		ubr.mark(8192);
		assertEquals(0, ubr.markIndex);

		for (int i = 0; i < 4097; i++) {
			assertEquals(i % 16 + 'a', ubr.read());
		}
		assertEquals(8192, ubr.buffer.length);
		assertEquals(0, ubr.markIndex);
		assertEquals(4097, ubr.index);

		// Shuffle

		ubr.mark(8192);
		assertEquals(4097, ubr.markIndex);

		for (int i = 4097; i < 8193; i++) {
			assertEquals(i % 16 + 'a', ubr.read());
		}

		assertEquals(0, ubr.markIndex);
	}

	public void testMarkSupported() {
		UnsyncBufferedReader ubr =
			new UnsyncBufferedReader(new StringReader("abcdefghi"), 5);
		assertTrue(ubr.markSupported());
	}

	public void testRead() throws IOException {
		UnsyncBufferedReader ubr =
			new UnsyncBufferedReader(new StringReader("ab\r\nef"), 3);

		assertEquals(3, ubr.buffer.length);
		assertEquals(0, ubr.index);

		assertEquals('a', ubr.read());
		assertEquals(1, ubr.index);

		assertEquals('b', ubr.read());
		assertEquals(2, ubr.index);

		assertEquals('\r', ubr.read());
		assertEquals(3, ubr.index);

		assertEquals('\n', ubr.read());
		assertEquals(1, ubr.index);

		assertEquals('e', ubr.read());
		assertEquals(2, ubr.index);

		assertEquals('f', ubr.read());
		assertEquals(3, ubr.index);

		assertEquals(-1, ubr.read());
	}

	public void testReadLine() throws IOException {
		// In Buffer read with \r
		UnsyncBufferedReader ubr =
			new UnsyncBufferedReader(new StringReader("abc\rde"), 5);
		String line = ubr.readLine();
		assertEquals("abc", line);
		assertEquals(4, ubr.index);

		// In Buffer read with \n
		ubr = new UnsyncBufferedReader(new StringReader("abc\nde"), 5);
		line = ubr.readLine();
		assertEquals("abc", line);
		assertEquals(4, ubr.index);

		// In Buffer read with \r\n
		ubr = new UnsyncBufferedReader(new StringReader("abc\r\nde"), 5);
		line = ubr.readLine();
		assertEquals("abc", line);
		assertEquals(5, ubr.index);

		// In Buffer read without \r or \n
		ubr = new UnsyncBufferedReader(new StringReader("abc"), 5);
		line = ubr.readLine();
		assertEquals("abc", line);
		assertEquals(0, ubr.index);

		// Empty read
		line = ubr.readLine();
		assertNull(line);

		// Load buffer multiple time for one line
		ubr = new UnsyncBufferedReader(new StringReader("abcdefghijklmn\r"), 5);
		line = ubr.readLine();
		assertEquals("abcdefghijklmn", line);
		assertEquals(5, ubr.index);
	}

	public void testReady() throws IOException {
		UnsyncBufferedReader ubr =
		new UnsyncBufferedReader(new StringReader(""));
		assertTrue(ubr.ready());

		ubr = new UnsyncBufferedReader(
			new InputStreamReader(new ByteArrayInputStream(new byte[0])));

		assertFalse(ubr.ready());
	}

	public void testSkip() throws IOException {

		UnsyncBufferedReader ubr =
		new UnsyncBufferedReader(
			new StringReader("abcdefghijklmnopqrstuvwxyz"), 5);

		assertEquals(0, ubr.index);
		assertEquals(0, ubr.firstInvalidIndex);
		assertEquals('a', ubr.read());
		assertEquals(1, ubr.index);
		assertEquals(5, ubr.firstInvalidIndex);

		// In-memory

		assertEquals(3, ubr.skip(3));
		assertEquals(4, ubr.index);
		assertEquals(5, ubr.firstInvalidIndex);

		assertEquals(1, ubr.skip(3));
		assertEquals(5, ubr.index);
		assertEquals(5, ubr.firstInvalidIndex);

		// Underlying reader

		assertEquals(6, ubr.skip(6));
		assertEquals(11, ubr.index);
		assertEquals(5, ubr.firstInvalidIndex);
		assertEquals('l', ubr.read());
		assertEquals(1, ubr.index);
		assertEquals(5, ubr.firstInvalidIndex);

		// Mark

		ubr.mark(10);

		// In-memory

		assertEquals(4, ubr.skip(4));
		assertEquals(5, ubr.index);
		assertEquals(5, ubr.firstInvalidIndex);

		// Force

		assertEquals(5, ubr.skip(6));
		assertEquals(10, ubr.index);
		assertEquals(10, ubr.firstInvalidIndex);

		ubr.reset();
		assertEquals(1, ubr.index);
		assertEquals(10, ubr.firstInvalidIndex);
		assertEquals('m', ubr.read());

	}

	private static final int _BUFFER_SIZE = 16 * 1024;

	private static final char[] _BUFFER = new char[_BUFFER_SIZE];

	static {
		for (int i = 0; i < _BUFFER_SIZE; i++) {
			_BUFFER[i] = (char) (i % 16 + 'a');
		}
	}

}