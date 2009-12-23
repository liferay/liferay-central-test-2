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
 * Note: This class has the same function as
 * {@link java.io.BufferedOutputStream}, but without synchronized protection.
 * We make this for performance, see http://issues.liferay.com/browse/LPS-6648.
 *
 * Warning: This class is not thread safe, make sure using it only under single
 * thread context or adding external synchronized protection.
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedOutputStream extends UnsyncFilterOutputStream {

	public UnsyncBufferedOutputStream(OutputStream out) {
		this(out, 8192);
	}

	public UnsyncBufferedOutputStream(OutputStream out, int size) {
		super(out);
		buffer = new byte[size];
	}

	public void write(int b) throws IOException {
		if (count >= buffer.length) {
			out.write(buffer, 0, count);
			count = 0;
		}
		buffer[count++] = (byte) b;
	}

	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}

	public void write(byte b[], int off, int len) throws IOException {
		if (len >= buffer.length) {
			if (count > 0) {
				out.write(buffer, 0, count);
				count = 0;
			}
			out.write(b, off, len);
			return;
		}
		if (count > 0 && len > buffer.length - count) {
			out.write(buffer, 0, count);
			count = 0;
		}
		System.arraycopy(b, off, buffer, count, len);
		count += len;
	}

	public void flush() throws IOException {
		if (count > 0) {
			out.write(buffer, 0, count);
			count = 0;
		}
		out.flush();
	}

	protected byte[] buffer;
	protected int count;

}