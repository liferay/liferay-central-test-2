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
import java.io.Reader;

/**
 * <a href="UnsyncCharArrayReader.java.html"><b><i>View Source</i></b></a>
 *
 * This class is a copy of {@link java.io.CharArrayReader}, but removes all
 * synchronized protection for performance(See LPS-XXX for more detail).
 * You should use this class in single thread context or add external
 * synchronized protection by yourself.
 *
 * @author Herb Jellinek
 * @author Shuyang Zhou
 * @see java.io.CharArrayReader
 */
public class UnsyncCharArrayReader extends Reader {

	/**
	 * @see java.io.CharArrayReader#buf
	 */
	protected char buf[];
	/**
	 * @see java.io.CharArrayReader#pos
	 */
	protected int pos;
	/**
	 * @see java.io.CharArrayReader#markedPos
	 */
	protected int markedPos = 0;
	/**
	 * @see java.io.CharArrayReader#count
	 */
	protected int count;

	/**
	 * @see java.io.CharArrayReader#CharArrayReader(char[])
	 */
	public UnsyncCharArrayReader(char buf[]) {
		this.buf = buf;
		this.pos = 0;
		this.count = buf.length;
	}

	/**
	 * @see java.io.CharArrayReader#CharArrayReader(char[], int, int)
	 */
	public UnsyncCharArrayReader(char buf[], int offset, int length) {
		if ((offset < 0) || (offset > buf.length) || (length < 0)
			|| ((offset + length) < 0)) {
			throw new IllegalArgumentException();
		}
		this.buf = buf;
		this.pos = offset;
		this.count = Math.min(offset + length, buf.length);
		this.markedPos = offset;
	}

	/**
	 * @see java.io.CharArrayReader#ensureOpen()
	 */
	private void ensureOpen() throws IOException {
		if (buf == null) {
			throw new IOException("Stream closed");
		}
	}

	/**
	 * @see java.io.CharArrayReader#read()
	 */
	public int read() throws IOException {
		ensureOpen();
		if (pos >= count) {
			return -1;
		}
		else {
			return buf[pos++];
		}
	}

	/**
	 * @see java.io.CharArrayReader#read(char[], int, int)
	 */
	public int read(char b[], int off, int len) throws IOException {
		ensureOpen();
		if ((off < 0) || (off > b.length) || (len < 0)
			|| ((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		}
		else if (len == 0) {
			return 0;
		}

		if (pos >= count) {
			return -1;
		}
		if (pos + len > count) {
			len = count - pos;
		}
		if (len <= 0) {
			return 0;
		}
		System.arraycopy(buf, pos, b, off, len);
		pos += len;
		return len;
	}

	/**
	 * @see java.io.CharArrayReader#skip(long)
	 */
	public long skip(long n) throws IOException {
		ensureOpen();
		if (pos + n > count) {
			n = count - pos;
		}
		if (n < 0) {
			return 0;
		}
		pos += n;
		return n;
	}

	/**
	 * @see java.io.CharArrayReader#ready()
	 */
	public boolean ready() throws IOException {
		ensureOpen();
		return (count - pos) > 0;
	}

	/**
	 * @see java.io.CharArrayReader#markSupported()
	 */
	public boolean markSupported() {
		return true;
	}

	/**
	 * @see java.io.CharArrayReader#mark(int)
	 */
	public void mark(int readAheadLimit) throws IOException {
		ensureOpen();
		markedPos = pos;
	}

	/**
	 * @see java.io.CharArrayReader#reset()
	 */
	public void reset() throws IOException {
		ensureOpen();
		pos = markedPos;
	}

	/**
	 * @see java.io.CharArrayReader#close()
	 */
	public void close() {
		buf = null;
	}

}