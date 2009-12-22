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
import java.io.OutputStream;

/**
 * <a href="UnsyncBufferedOutputStream.java.html"><b><i>View Source</i></b></a>
 *
 * This class is a copy of {@link java.io.BufferedOutputStream}, but removes all
 * synchronized protection for performance(See LPS-XXX for more detail).
 * You should use this class in single thread context or add external
 * synchronized protection by yourself.
 *
 * @author Arthur van Hoff
 * @author Shuyang Zhou
 * @see java.io.BufferedOutputStream
 */
public class UnsyncBufferedOutputStream extends UnsyncFilterOutputStream {

	/**
	 * @see java.io.BufferedOutputStream#buf
	 */
	protected byte buf[];
	/**
	 * @see java.io.BufferedOutputStream#count
	 */
	protected int count;

	/**
	 * @see java.io.BufferedOutputStream#BufferedOutputStream(
	 *		java.io.OutputStream)
	 */
	public UnsyncBufferedOutputStream(OutputStream out) {
		this(out, 8192);
	}

	/**
	 * @see java.io.BufferedOutputStream#BufferedOutputStream(
	 *		java.io.OutputStream, int)
	 */
	public UnsyncBufferedOutputStream(OutputStream out, int size) {
		super(out);
		if (size <= 0) {
			throw new IllegalArgumentException("Buffer size <= 0");
		}
		buf = new byte[size];
	}

	/**
	 * @see java.io.BufferedOutputStream#flushBuffer()
	 */
	private void flushBuffer() throws IOException {
		if (count > 0) {
			out.write(buf, 0, count);
			count = 0;
		}
	}

	/**
	 * @see java.io.BufferedOutputStream#write(int)
	 */
	public void write(int b) throws IOException {
		if (count >= buf.length) {
			flushBuffer();
		}
		buf[count++] = (byte) b;
	}

	/**
	 * @see java.io.BufferedOutputStream#write(byte[], int, int)
	 */
	public void write(byte b[], int off, int len) throws IOException {
		if (len >= buf.length) {
			/* If the request length exceeds the size of the output buffer,
			flush the output buffer and then write the data directly.
			In this way buffered streams will cascade harmlessly. */
			flushBuffer();
			out.write(b, off, len);
			return;
		}
		if (len > buf.length - count) {
			flushBuffer();
		}
		System.arraycopy(b, off, buf, count, len);
		count += len;
	}

	/**
	 * @see java.io.BufferedOutputStream#flush()
	 */
	public void flush() throws IOException {
		flushBuffer();
		out.flush();
	}

}