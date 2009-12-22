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

/**
 * <a href="UnsyncBufferedWriter.java.html"><b><i>View Source</i></b></a>
 *
 * This class is a copy of {@link java.io.BufferedWriter}, but removes all
 * synchronized protection for performance(See LPS-XXX for more detail).
 * You should use this class in single thread context or add external
 * synchronized protection by yourself.
 *
 * @author Mark Reinhold
 * @author Shuyang Zhou
 * @see java.io.BufferedWriter
 */
public class UnsyncBufferedWriter extends Writer {

	private Writer out;
	private char cb[];
	private int nChars, nextChar;
	private static int defaultCharBufferSize = 8192;
	/**
	 * @see java.io.BufferedWriter#lineSeparator
	 */
	private String lineSeparator;

	/**
	 * @see java.io.BufferedWriter#BufferedWriter(java.io.Writer)
	 */
	public UnsyncBufferedWriter(Writer out) {
		this(out, defaultCharBufferSize);
	}

	/**
	 * @see java.io.BufferedWriter#BufferedWriter(java.io.Writer, int)
	 */
	public UnsyncBufferedWriter(Writer out, int sz) {
		super(out);
		if (sz <= 0) {
			throw new IllegalArgumentException("Buffer size <= 0");
		}
		this.out = out;
		cb = new char[sz];
		nChars = sz;
		nextChar = 0;

		lineSeparator = java.security.AccessController.doPrivileged(
			new sun.security.action.GetPropertyAction("line.separator"));
	}

	/**
	 * @see java.io.BufferedWriter#ensureOpen()
	 */
	private void ensureOpen() throws IOException {
		if (out == null) {
			throw new IOException("Stream closed");
		}
	}

	/**
	 * @see java.io.BufferedWriter#flushBuffer()
	 */
	void flushBuffer() throws IOException {
		ensureOpen();
		if (nextChar == 0) {
			return;
		}
		out.write(cb, 0, nextChar);
		nextChar = 0;
	}

	/**
	 * @see java.io.BufferedWriter#write(int)
	 */
	public void write(int c) throws IOException {
		ensureOpen();
		if (nextChar >= nChars) {
			flushBuffer();
		}
		cb[nextChar++] = (char) c;
	}

	/**
	 * @see java.io.BufferedWriter#min(int, int)
	 */
	private int min(int a, int b) {
		if (a < b) {
			return a;
		}
		return b;
	}

	/**
	 * @see java.io.BufferedWriter#write(char[], int, int)
	 */
	public void write(char cbuf[], int off, int len) throws IOException {
		ensureOpen();
		if ((off < 0) || (off > cbuf.length) || (len < 0)
			|| ((off + len) > cbuf.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		}
		else if (len == 0) {
			return;
		}

		if (len >= nChars) {
			/* If the request length exceeds the size of the output buffer,
			flush the buffer and then write the data directly.  In this
			way buffered streams will cascade harmlessly. */
			flushBuffer();
			out.write(cbuf, off, len);
			return;
		}

		int b = off, t = off + len;
		while (b < t) {
			int d = min(nChars - nextChar, t - b);
			System.arraycopy(cbuf, b, cb, nextChar, d);
			b += d;
			nextChar += d;
			if (nextChar >= nChars) {
				flushBuffer();
			}
		}
	}

	/**
	 * @see java.io.BufferedWriter#write(java.lang.String, int, int)
	 */
	public void write(String s, int off, int len) throws IOException {
		ensureOpen();

		int b = off, t = off + len;
		while (b < t) {
			int d = min(nChars - nextChar, t - b);
			s.getChars(b, b + d, cb, nextChar);
			b += d;
			nextChar += d;
			if (nextChar >= nChars) {
				flushBuffer();
			}
		}
	}

	/**
	 * @see java.io.BufferedWriter#newLine()
	 */
	public void newLine() throws IOException {
		write(lineSeparator);
	}

	/**
	 * @see java.io.BufferedWriter#flush()
	 */
	public void flush() throws IOException {
		flushBuffer();
		out.flush();
	}

	public void close() throws IOException {
		if (out == null) {
			return;
		}
		try {
			flushBuffer();
		}
		finally {
			out.close();
			out = null;
			cb = null;
		}
	}

}