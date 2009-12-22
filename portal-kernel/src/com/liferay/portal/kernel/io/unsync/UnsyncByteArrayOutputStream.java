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
import java.io.UnsupportedEncodingException;

import java.util.Arrays;

/**
 * <a href="UnsyncByteArrayOutputStream.java.html"><b><i>View Source</i></b></a>
 *
 * This class is a copy of {@link java.io.ByteArrayOutputStream}, but removes
 * all synchronized protection for performance(See LPS-XXX for more detail).
 * You should use this class in single thread context or add external
 * synchronized protection by yourself.
 *
 * @author Arthur van Hoff
 * @author Shuyang Zhou
 * @see java.io.ByteArrayOutputStream
 */
public class UnsyncByteArrayOutputStream extends OutputStream {

	/**
	 * @see java.io.ByteArrayOutputStream#buf
	 */
	protected byte buf[];
	/**
	 * @see java.io.ByteArrayOutputStream#count
	 */
	protected int count;

	/**
	 * @see java.io.ByteArrayOutputStream#ByteArrayOutputStream()
	 */
	public UnsyncByteArrayOutputStream() {
		this(32);
	}

	/**
	 * @see java.io.ByteArrayOutputStream#ByteArrayOutputStream(int)
	 */
	public UnsyncByteArrayOutputStream(int size) {
		if (size < 0) {
			throw new IllegalArgumentException("Negative initial size: "
				+ size);
		}
		buf = new byte[size];
	}

	/**
	 * @see java.io.ByteArrayOutputStream#write(int)
	 */
	public void write(int b) {
		int newcount = count + 1;
		if (newcount > buf.length) {
			buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
		}
		buf[count] = (byte) b;
		count = newcount;
	}

	/**
	 * @see java.io.ByteArrayOutputStream#write(byte[], int, int)
	 */
	public void write(byte b[], int off, int len) {
		if ((off < 0) || (off > b.length) || (len < 0)
			|| ((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		}
		else if (len == 0) {
			return;
		}
		int newcount = count + len;
		if (newcount > buf.length) {
			buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
		}
		System.arraycopy(b, off, buf, count, len);
		count = newcount;
	}

	/**
	 * @see java.io.ByteArrayOutputStream#writeTo(java.io.OutputStream)
	 */
	public void writeTo(OutputStream out) throws IOException {
		out.write(buf, 0, count);
	}

	/**
	 * @see java.io.ByteArrayOutputStream#reset()
	 */
	public void reset() {
		count = 0;
	}

	/**
	 * @see java.io.ByteArrayOutputStream#toByteArray()
	 */
	public byte toByteArray ()

		   [] {
		return Arrays.copyOf(buf, count);
	}

	/**
	 * @see java.io.ByteArrayOutputStream#size()
	 */
	public int size() {
		return count;
	}

	/**
	 * @see java.io.ByteArrayOutputStream#toString()
	 */
	public String toString() {
		return new String(buf, 0, count);
	}

	/**
	 * @see java.io.ByteArrayOutputStream#toString(java.lang.String)
	 */
	public String toString(String charsetName)
		throws UnsupportedEncodingException {
		return new String(buf, 0, count, charsetName);
	}

	/**
	 * @see java.io.ByteArrayOutputStream#toString(int)
	 */
	@Deprecated
	public String toString(int hibyte) {
		return new String(buf, hibyte, 0, count);
	}

	/**
	 * @see java.io.ByteArrayOutputStream#close()
	 */
	public void close() throws IOException {
	}

}