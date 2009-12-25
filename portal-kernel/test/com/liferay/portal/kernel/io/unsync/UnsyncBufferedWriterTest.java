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

import java.io.IOException;
import java.io.StringWriter;

/**
 * <a href="UnsyncBufferedWriterTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedWriterTest extends TestCase {

	public void testBlockWrite() throws IOException {
		StringWriter sw = new StringWriter();
		UnsyncBufferedWriter ubw = new UnsyncBufferedWriter(sw, 3);
		//Normal write
		ubw.write("ab".toCharArray());
		assertEquals(2, ubw.count);
		assertEquals('a', ubw.buffer[0]);
		assertEquals('b', ubw.buffer[1]);
		assertEquals(0, sw.getBuffer().length());

		//Auto flush
		ubw.write("cd".toCharArray());
		assertEquals(2, ubw.count);
		assertEquals('c', ubw.buffer[0]);
		assertEquals('d', ubw.buffer[1]);
		assertEquals(2, sw.getBuffer().length());
		assertEquals("ab", sw.getBuffer().toString());

		//Directly write with auto flush
		ubw.write("efg".toCharArray());
		assertEquals(0, ubw.count);
		assertEquals(7, sw.getBuffer().length());
		assertEquals("abcdefg", sw.getBuffer().toString());

		//Directly write without auto flush
		ubw.write("hij".toCharArray());
		assertEquals(0, ubw.count);
		assertEquals(10, sw.getBuffer().length());
		assertEquals("abcdefghij", sw.getBuffer().toString());
	}

	public void testClose() throws IOException {
		UnsyncBufferedWriter ubw = new UnsyncBufferedWriter(new StringWriter());
		assertNotNull(ubw.buffer);
		assertNotNull(ubw.writer);
		ubw.close();
		assertNull(ubw.buffer);
		assertNull(ubw.writer);
	}

	public void testConstructor() {
		UnsyncBufferedWriter ubw = new UnsyncBufferedWriter(new StringWriter());
		assertEquals(8192, ubw.buffer.length);
		assertEquals(0, ubw.count);

		ubw = new UnsyncBufferedWriter(new StringWriter(), 10);
		assertEquals(10, ubw.buffer.length);
		assertEquals(0, ubw.count);
	}

	public void testStringWrite() throws IOException {
		StringWriter sw = new StringWriter();
		UnsyncBufferedWriter ubw = new UnsyncBufferedWriter(sw, 3);
		//Normal write
		ubw.write("ab");
		assertEquals(2, ubw.count);
		assertEquals('a', ubw.buffer[0]);
		assertEquals('b', ubw.buffer[1]);
		assertEquals(0, sw.getBuffer().length());

		//Auto flush
		ubw.write("cd");
		assertEquals(1, ubw.count);
		assertEquals('d', ubw.buffer[0]);
		assertEquals(3, sw.getBuffer().length());
		assertEquals("abc", sw.getBuffer().toString());

		//cycle write
		ubw.write("efghi".toCharArray());
		assertEquals(0, ubw.count);
		assertEquals(9, sw.getBuffer().length());
		assertEquals("abcdefghi", sw.getBuffer().toString());

	}

	public void testWrite() throws IOException {
		StringWriter sw = new StringWriter();
		UnsyncBufferedWriter ubw = new UnsyncBufferedWriter(sw, 3);
		//Normal write
		ubw.write('a');
		assertEquals(1, ubw.count);
		assertEquals('a', ubw.buffer[0]);
		assertEquals(0, sw.getBuffer().length());
		ubw.write('b');
		assertEquals(2, ubw.count);
		assertEquals('b', ubw.buffer[1]);
		assertEquals(0, sw.getBuffer().length());
		ubw.write('c');
		assertEquals(3, ubw.count);
		assertEquals('c', ubw.buffer[2]);
		assertEquals(0, sw.getBuffer().length());

		//Auto flush
		ubw.write('d');
		assertEquals(1, ubw.count);
		assertEquals('d', ubw.buffer[0]);
		assertEquals(3, sw.getBuffer().length());
		assertEquals("abc", sw.getBuffer().toString());
	}

}