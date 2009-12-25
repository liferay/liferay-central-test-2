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
 * <a href="UnsyncStringWriterTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class UnsyncStringWriterTest extends TestCase {

	public void testAppendChar() {
		//StringBuilder
		UnsyncStringWriter usw = new UnsyncStringWriter(false);
		assertNotNull(usw.stringBuilder);
		assertNull(usw.stringBundler);

		usw.append('a');
		assertEquals(1, usw.stringBuilder.length());
		assertEquals('a', usw.stringBuilder.charAt(0));

		usw.append('b');
		assertEquals(2, usw.stringBuilder.length());
		assertEquals('a', usw.stringBuilder.charAt(0));
		assertEquals('b', usw.stringBuilder.charAt(1));

		//StringBundler
		usw = new UnsyncStringWriter(true);
		assertNull(usw.stringBuilder);
		assertNotNull(usw.stringBundler);

		usw.append('a');
		assertEquals(1, usw.stringBundler.index());
		assertEquals("a", usw.stringBundler.stringAt(0));

		usw.append('b');
		assertEquals(2, usw.stringBundler.index());
		assertEquals("a", usw.stringBundler.stringAt(0));
		assertEquals("b", usw.stringBundler.stringAt(1));
	}

	public void testAppendCharSequence() {
		//StringBuilder
		UnsyncStringWriter usw = new UnsyncStringWriter(false);
		assertNotNull(usw.stringBuilder);
		assertNull(usw.stringBundler);

		usw.append(new StringBuilder("ab"));
		assertEquals(2, usw.stringBuilder.length());
		assertEquals('a', usw.stringBuilder.charAt(0));
		assertEquals('b', usw.stringBuilder.charAt(1));

		usw.append(new StringBuilder("cd"));
		assertEquals(4, usw.stringBuilder.length());
		assertEquals('a', usw.stringBuilder.charAt(0));
		assertEquals('b', usw.stringBuilder.charAt(1));
		assertEquals('c', usw.stringBuilder.charAt(2));
		assertEquals('d', usw.stringBuilder.charAt(3));

		//StringBundler
		usw = new UnsyncStringWriter(true);
		assertNull(usw.stringBuilder);
		assertNotNull(usw.stringBundler);

		usw.append(new StringBuilder("ab"));
		assertEquals(1, usw.stringBundler.index());
		assertEquals("ab", usw.stringBundler.stringAt(0));

		usw.append(new StringBuilder("cd"));
		assertEquals(2, usw.stringBundler.index());
		assertEquals("ab", usw.stringBundler.stringAt(0));
		assertEquals("cd", usw.stringBundler.stringAt(1));
	}

	public void testConstructor() {
		//StringBuilder
		UnsyncStringWriter usw = new UnsyncStringWriter(false);
		assertNotNull(usw.stringBuilder);
		assertEquals(16, usw.stringBuilder.capacity());
		assertNull(usw.stringBundler);

		usw = new UnsyncStringWriter(false, 32);
		assertNotNull(usw.stringBuilder);
		assertEquals(32, usw.stringBuilder.capacity());
		assertNull(usw.stringBundler);

		//StringBundler
		usw = new UnsyncStringWriter(true);
		assertNull(usw.stringBuilder);
		assertNotNull(usw.stringBundler);
		assertEquals(16, usw.stringBundler.capacity());

		usw = new UnsyncStringWriter(true, 32);
		assertNull(usw.stringBuilder);
		assertNotNull(usw.stringBundler);
		assertEquals(32, usw.stringBundler.capacity());
	}

	public void testToString() {
		//StringBuilder
		UnsyncStringWriter usw = new UnsyncStringWriter(false);
		assertNotNull(usw.stringBuilder);
		assertNull(usw.stringBundler);

		usw.append('a');
		assertEquals(1, usw.stringBuilder.length());
		assertEquals("a", usw.toString());

		usw.append('b');
		assertEquals(2, usw.stringBuilder.length());
		assertEquals("ab", usw.toString());

		//StringBundler
		usw = new UnsyncStringWriter(true);
		assertNull(usw.stringBuilder);
		assertNotNull(usw.stringBundler);

		usw.append('a');
		assertEquals(1, usw.stringBundler.index());
		assertEquals("a", usw.toString());

		usw.append('b');
		assertEquals(2, usw.stringBundler.index());
		assertEquals("ab", usw.toString());
	}

	public void testWriteChar() {
		//StringBuilder
		UnsyncStringWriter usw = new UnsyncStringWriter(false);
		assertNotNull(usw.stringBuilder);
		assertNull(usw.stringBundler);

		usw.write('a');
		assertEquals(1, usw.stringBuilder.length());
		assertEquals('a', usw.stringBuilder.charAt(0));

		usw.write('b');
		assertEquals(2, usw.stringBuilder.length());
		assertEquals('a', usw.stringBuilder.charAt(0));
		assertEquals('b', usw.stringBuilder.charAt(1));

		//StringBundler
		usw = new UnsyncStringWriter(true);
		assertNull(usw.stringBuilder);
		assertNotNull(usw.stringBundler);

		usw.write('a');
		assertEquals(1, usw.stringBundler.index());
		assertEquals("a", usw.stringBundler.stringAt(0));

		usw.write('b');
		assertEquals(2, usw.stringBundler.index());
		assertEquals("a", usw.stringBundler.stringAt(0));
		assertEquals("b", usw.stringBundler.stringAt(1));
	}

	public void testWriteCharArray() {
		//StringBuilder
		UnsyncStringWriter usw = new UnsyncStringWriter(false);
		assertNotNull(usw.stringBuilder);
		assertNull(usw.stringBundler);

		usw.write("ab".toCharArray());
		assertEquals(2, usw.stringBuilder.length());
		assertEquals('a', usw.stringBuilder.charAt(0));
		assertEquals('b', usw.stringBuilder.charAt(1));

		usw.write("cd".toCharArray());
		assertEquals(4, usw.stringBuilder.length());
		assertEquals('a', usw.stringBuilder.charAt(0));
		assertEquals('b', usw.stringBuilder.charAt(1));
		assertEquals('c', usw.stringBuilder.charAt(2));
		assertEquals('d', usw.stringBuilder.charAt(3));

		//StringBundler
		usw = new UnsyncStringWriter(true);
		assertNull(usw.stringBuilder);
		assertNotNull(usw.stringBundler);

		usw.write("ab".toCharArray());
		assertEquals(1, usw.stringBundler.index());
		assertEquals("ab", usw.stringBundler.stringAt(0));

		usw.write("cd".toCharArray());
		assertEquals(2, usw.stringBundler.index());
		assertEquals("ab", usw.stringBundler.stringAt(0));
		assertEquals("cd", usw.stringBundler.stringAt(1));
	}

	public void testWriteString() {
		//StringBuilder
		UnsyncStringWriter usw = new UnsyncStringWriter(false);
		assertNotNull(usw.stringBuilder);
		assertNull(usw.stringBundler);

		usw.write("ab");
		assertEquals(2, usw.stringBuilder.length());
		assertEquals('a', usw.stringBuilder.charAt(0));
		assertEquals('b', usw.stringBuilder.charAt(1));

		usw.write("cd");
		assertEquals(4, usw.stringBuilder.length());
		assertEquals('a', usw.stringBuilder.charAt(0));
		assertEquals('b', usw.stringBuilder.charAt(1));
		assertEquals('c', usw.stringBuilder.charAt(2));
		assertEquals('d', usw.stringBuilder.charAt(3));

		//StringBundler
		usw = new UnsyncStringWriter(true);
		assertNull(usw.stringBuilder);
		assertNotNull(usw.stringBundler);

		usw.write("ab");
		assertEquals(1, usw.stringBundler.index());
		assertEquals("ab", usw.stringBundler.stringAt(0));

		usw.write("cd");
		assertEquals(2, usw.stringBundler.index());
		assertEquals("ab", usw.stringBundler.stringAt(0));
		assertEquals("cd", usw.stringBundler.stringAt(1));
	}

}