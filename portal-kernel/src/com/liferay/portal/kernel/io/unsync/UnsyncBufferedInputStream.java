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
 * <a href="UnsyncBufferedInputStream.java.html"><b><i>View Source</i></b></a>
 *
 * This class is a copy of {@link java.io.BufferedInputStream}, but removes all
 * synchronized protection for performance(See LPS-XXX for more detail).
 * You should use this class in single thread context or add external
 * synchronized protection by yourself.
 *
 * @author Arthur van Hoff
 * @author Shuyang Zhou
 * @see java.io.BufferedInputStream
 */
public class UnsyncBufferedInputStream extends UnsyncFilterInputStream {

	private static int defaultBufferSize = 8192;
	/**
	 * @see java.io.BufferedInputStream#buf
	 */
	protected byte buf[];
	/**
	 * @see java.io.BufferedInputStream#count
	 */
	protected int count;
	/**
	 * @see java.io.BufferedInputStream#pos
	 */
	protected int pos;
	/**
	 * @see java.io.BufferedInputStream#markpos
	 */
	protected int markpos = -1;
	/**
	 * @see java.io.BufferedInputStream#marklimit
	 */
	protected int marklimit;

	/**
	 * @see java.io.BufferedInputStream#getBufIfOpen()
	 */
	private InputStream getInIfOpen() throws IOException {
		InputStream input = in;
		if (input == null) {
			throw new IOException("Stream closed");
		}
		return input;
	}

	/**
	 * @see java.io.BufferedInputStream#getBufIfOpen()
	 */
	private byte[] getBufIfOpen() throws IOException {
		byte[] buffer = buf;
		if (buffer == null) {
			throw new IOException("Stream closed");
		}
		return buffer;
	}

	/**
	 * @see java.io.BufferedInputStream#BufferedInputStream(java.io.InputStream)
	 */
	public UnsyncBufferedInputStream(InputStream in) {
		this(in, defaultBufferSize);
	}

	/**
	 * @see
	 * java.io.BufferedInputStream#BufferedInputStream(java.io.InputStream, int)
	 */
	public UnsyncBufferedInputStream(InputStream in, int size) {
		super(in);
		if (size <= 0) {
			throw new IllegalArgumentException("Buffer size <= 0");
		}
		buf = new byte[size];
	}

	/**
	 * @see java.io.BufferedInputStream#fill()
	 */
	private void fill() throws IOException {
		byte[] buffer = getBufIfOpen();
		if (markpos < 0) {
			pos = 0;			/* no mark: throw away the buffer */
		}
		else if (pos >= buffer.length) /* no room left in buffer */ {
			if (markpos > 0) {  /* can throw away early part of the buffer */
				int sz = pos - markpos;
				System.arraycopy(buffer, markpos, buffer, 0, sz);
				pos = sz;
				markpos = 0;
			}
			else if (buffer.length >= marklimit) {
				markpos = -1;   /* buffer got too big, invalidate mark */
				pos = 0;		/* drop buffer contents */
			}
			else {			/* grow buffer */
				int nsz = pos * 2;
				if (nsz > marklimit) {
					nsz = marklimit;
				}
				byte nbuf[] = new byte[nsz];
				System.arraycopy(buffer, 0, nbuf, 0, pos);
				buf = nbuf;
				buffer = nbuf;
			}
		}
		count = pos;
		int n = getInIfOpen().read(buffer, pos, buffer.length - pos);
		if (n > 0) {
			count = n + pos;
		}
	}

	/**
	 * @see java.io.BufferedInputStream#read()
	 */
	public int read() throws IOException {
		if (pos >= count) {
			fill();
			if (pos >= count) {
				return -1;
			}
		}
		return getBufIfOpen()[pos++] & 0xff;
	}

	/**
	 * @see java.io.BufferedInputStream#read1(byte[], int, int)
	 */
	private int read1(byte[] b, int off, int len) throws IOException {
		int avail = count - pos;
		if (avail <= 0) {
			/* If the requested length is at least as large as the buffer, and
			if there is no mark/reset activity, do not bother to copy the
			bytes into the local buffer.  In this way buffered streams will
			cascade harmlessly. */
			if (len >= getBufIfOpen().length && markpos < 0) {
				return getInIfOpen().read(b, off, len);
			}
			fill();
			avail = count - pos;
			if (avail <= 0) {
				return -1;
			}
		}
		int cnt = (avail < len) ? avail : len;
		System.arraycopy(getBufIfOpen(), pos, b, off, cnt);
		pos += cnt;
		return cnt;
	}

	/**
	 * @see java.io.BufferedInputStream#read1(byte[], int, int)
	 */
	public int read(byte b[], int off, int len)
		throws IOException {
		getBufIfOpen(); // Check for closed stream
		if ((off | len | (off + len) | (b.length - (off + len))) < 0) {
			throw new IndexOutOfBoundsException();
		}
		else if (len == 0) {
			return 0;
		}

		int n = 0;
		for (;;) {
			int nread = read1(b, off + n, len - n);
			if (nread <= 0) {
				return (n == 0) ? nread : n;
			}
			n += nread;
			if (n >= len) {
				return n;
			}
			// if not closed but no bytes available, return
			InputStream input = in;
			if (input != null && input.available() <= 0) {
				return n;
			}
		}
	}

	/**
	 * @see java.io.BufferedInputStream#skip(long)
	 */
	public long skip(long n) throws IOException {
		getBufIfOpen(); // Check for closed stream
		if (n <= 0) {
			return 0;
		}
		long avail = count - pos;

		if (avail <= 0) {
			// If no mark position set then don't keep in buffer
			if (markpos < 0) {
				return getInIfOpen().skip(n);
			}

			// Fill in buffer to save bytes for reset
			fill();
			avail = count - pos;
			if (avail <= 0) {
				return 0;
			}
		}

		long skipped = (avail < n) ? avail : n;
		pos += skipped;
		return skipped;
	}

	/**
	 * @see java.io.BufferedInputStream#available()
	 */
	public int available() throws IOException {
		return getInIfOpen().available() + (count - pos);
	}

	/**
	 * @see java.io.BufferedInputStream#mark(int)
	 */
	public void mark(int readlimit) {
		marklimit = readlimit;
		markpos = pos;
	}

	/**
	 * @see java.io.BufferedInputStream#reset()
	 */
	public void reset() throws IOException {
		getBufIfOpen(); // Cause exception if closed
		if (markpos < 0) {
			throw new IOException("Resetting to invalid mark");
		}
		pos = markpos;
	}

	/**
	 * @see java.io.BufferedInputStream#markSupported()
	 */
	public boolean markSupported() {
		return true;
	}

	/**
	 * @see java.io.BufferedInputStream#close()
	 */
	public void close() throws IOException {
		buf = null;
		InputStream input = in;
		in = null;
		if (input != null) {
			input.close();
		}
	}

}