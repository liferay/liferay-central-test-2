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
import java.io.InputStream;

/**
 * <a href="UnsyncByteArrayInputStream.java.html"><b><i>View Source</i></b></a>
 *
 * This class is a copy of {@link java.io.ByteArrayInputStream}, but removes all
 * synchronized protection for performance(See LPS-XXX for more detail).
 * You should use this class in single thread context or add external
 * synchronized protection by yourself.
 *
 * @author Arthur van Hoff
 * @author Shuyang Zhou
 * @see java.io.ByteArrayInputStream
 */
public class UnsyncByteArrayInputStream extends InputStream {

	/**
	 * @see java.io.ByteArrayInputStream#buf
	 */
	protected byte buf[];
	/**
	 * @see java.io.ByteArrayInputStream#pos
	 */
	protected int pos;
	/**
	 * @see java.io.ByteArrayInputStream#mark
	 */
	protected int mark = 0;
	/**
	 * @see java.io.ByteArrayInputStream#count
	 */
	protected int count;

	/**
	 * @see java.io.ByteArrayInputStream#ByteArrayInputStream(byte[])
	 */
	public UnsyncByteArrayInputStream(byte buf[]) {
		this.buf = buf;
		this.pos = 0;
		this.count = buf.length;
	}

	/**
	 * @see java.io.ByteArrayInputStream#ByteArrayInputStream(byte[], int, int)
	 */
	public UnsyncByteArrayInputStream(byte buf[], int offset, int length) {
		this.buf = buf;
		this.pos = offset;
		this.count = Math.min(offset + length, buf.length);
		this.mark = offset;
	}

	/**
	 * @see java.io.ByteArrayInputStream#read()
	 */
	public int read() {
		return (pos < count) ? (buf[pos++] & 0xff) : -1;
	}

	/**
	 * @see java.io.ByteArrayInputStream#read(byte[], int, int)
	 */
	public int read(byte b[], int off, int len) {
		if (b == null) {
			throw new NullPointerException();
		}
		else if (off < 0 || len < 0 || len > b.length - off) {
			throw new IndexOutOfBoundsException();
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
	 * @see java.io.ByteArrayInputStream#skip(long)
	 */
	public long skip(long n) {
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
	 * @see java.io.ByteArrayInputStream#available()
	 */
	public int available() {
		return count - pos;
	}

	/**
	 * @see java.io.ByteArrayInputStream#markSupported()
	 */
	public boolean markSupported() {
		return true;
	}

	/**
	 * @see java.io.ByteArrayInputStream#mark(int)
	 */
	public void mark(int readAheadLimit) {
		mark = pos;
	}

	/**
	 * @see java.io.ByteArrayInputStream#reset()
	 */
	public void reset() {
		pos = mark;
	}

	/**
	 * @see java.io.ByteArrayInputStream#close()
	 */
	public void close() throws IOException {
	}

}