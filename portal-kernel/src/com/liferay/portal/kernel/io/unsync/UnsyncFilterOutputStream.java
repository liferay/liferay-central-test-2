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
 * <a href="UnsyncFilterOutputStream.java.html"><b><i>View Source</i></b></a>
 *
 * This class is a copy of {@link java.io.FilterOutputStream}, but removes all
 * synchronized protection for performance(See LPS-XXX for more detail).
 * You should use this class in single thread context or add external
 * synchronized protection by yourself.
 * @author  Jonathan Payne
 * @author Shuyang Zhou
 * @see java.io.FilterOutputStream
 */
public class UnsyncFilterOutputStream extends OutputStream {
	/**
	 * @see java.io.FilterOutputStream#out
	 */
	protected OutputStream out;

	/**
	 * @see java.io.FilterOutputStream#FilterOutputStream(java.io.OutputStream)
	 */
	public UnsyncFilterOutputStream(OutputStream out) {
		this.out = out;
	}

	/**
	 * @see java.io.FilterOutputStream#write(int)
	 */
	public void write(int b) throws IOException {
		out.write(b);
	}

	/**
	 * @see java.io.FilterOutputStream#write(byte[])
	 */
	public void write(byte b[]) throws IOException {
		write(b, 0, b.length);
	}

	/**
	 * @see java.io.FilterOutputStream#write(byte[], int, int)
	 */
	public void write(byte b[], int off, int len) throws IOException {
		if ((off | len | (b.length - (len + off)) | (off + len)) < 0)
			throw new IndexOutOfBoundsException();

		for (int i = 0 ; i < len ; i++) {
			write(b[off + i]);
		}
	}

	/**
	 * @see java.io.FilterOutputStream#flush()
	 */
	public void flush() throws IOException {
		out.flush();
	}

	/**
	 * @see java.io.FilterOutputStream#close()
	 */
	public void close() throws IOException {
		try {
		  flush();
		} catch (IOException ignored) {
		}
		out.close();
	}

}