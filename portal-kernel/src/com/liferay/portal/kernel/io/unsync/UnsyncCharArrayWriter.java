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

import java.io.IOException;
import java.io.Writer;

import java.util.Arrays;

/**
 * <a href="UnsyncCharArrayWriter.java.html"><b><i>View Source</i></b></a>
 *
 * This class is a copy of {@link java.io.CharArrayWriter}, but removes all
 * synchronized protection for performance(See LPS-XXX for more detail).
 * You should use this class in single thread context or add external
 * synchronized protection by yourself.
 *
 * @author Herb Jellinek
 * @author Shuyang Zhou
 * @see java.io.CharArrayWriter
 */
public class UnsyncCharArrayWriter extends Writer {

	/**
	 * @see java.io.CharArrayWriter#buf
	 */
	protected char buf[];
	/**
	 * @see java.io.CharArrayWriter#count
	 */
	protected int count;

	/**
	 * @see java.io.CharArrayWriter#CharArrayWriter()
	 */
	public UnsyncCharArrayWriter() {
		this(32);
	}

	/**
	 * @see java.io.CharArrayWriter#CharArrayWriter(int)
	 */
	public UnsyncCharArrayWriter(int initialSize) {
		if (initialSize < 0) {
			throw new IllegalArgumentException("Negative initial size: "
				+ initialSize);
		}
		buf = new char[initialSize];
	}

	/**
	 * @see java.io.CharArrayWriter#write(int)
	 */
	public void write(int c) {
		int newcount = count + 1;
		if (newcount > buf.length) {
			buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
		}
		buf[count] = (char) c;
		count = newcount;
	}

	/**
	 * @see java.io.CharArrayWriter#write(char[], int, int)
	 */
	public void write(char c[], int off, int len) {
		if ((off < 0) || (off > c.length) || (len < 0)
			|| ((off + len) > c.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		}
		else if (len == 0) {
			return;
		}
		int newcount = count + len;
		if (newcount > buf.length) {
			buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
		}
		System.arraycopy(c, off, buf, count, len);
		count = newcount;
	}

	/**
	 * @see java.io.CharArrayWriter#write(java.lang.String, int, int)
	 */
	public void write(String str, int off, int len) {
		int newcount = count + len;
		if (newcount > buf.length) {
			buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
		}
		str.getChars(off, off + len, buf, count);
		count = newcount;
	}

	/**
	 * @see java.io.CharArrayWriter#writeTo(java.io.Writer)
	 */
	public void writeTo(Writer out) throws IOException {
		out.write(buf, 0, count);
	}

	/**
	 * @see java.io.CharArrayWriter#append(java.lang.CharSequence)
	 */
	public UnsyncCharArrayWriter append(CharSequence csq) {
		String s = (csq == null ? "null" : csq.toString());
		write(s, 0, s.length());
		return this;
	}

	/**
	 * @see java.io.CharArrayWriter#append(java.lang.CharSequence, int, int)
	 */
	public UnsyncCharArrayWriter append(CharSequence csq, int start, int end) {
		String s =
			(csq == null ? "null" : csq).subSequence(start, end).toString();
		write(s, 0, s.length());
		return this;
	}

	/**
	 * @see java.io.CharArrayWriter#append(char)
	 */
	public UnsyncCharArrayWriter append(char c) {
		write(c);
		return this;
	}

	/**
	 * @see java.io.CharArrayWriter#reset()
	 */
	public void reset() {
		count = 0;
	}

	/**
	 * @see java.io.CharArrayWriter#toCharArray()
	 */
	public char[] toCharArray() {
		return Arrays.copyOf(buf, count);
	}

	/**
	 * @see java.io.CharArrayWriter#size()
	 */
	public int size() {
		return count;
	}

	/**
	 * @see java.io.CharArrayWriter#toString()
	 */
	public String toString() {
		return new String(buf, 0, count);
	}

	/**
	 * @see java.io.CharArrayWriter#flush()
	 */
	public void flush() {
	}

	/**
	 * @see java.io.CharArrayWriter#close()
	 */
	public void close() {
	}

}