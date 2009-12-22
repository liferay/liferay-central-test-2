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
 * <a href="UnsyncFilterInputStream.java.html"><b><i>View Source</i></b></a>
 *
 * This class is a copy of {@link java.io.FilterInputStream}, but removes all
 * synchronized protection for performance(See LPS-XXX for more detail).
 * You should use this class in single thread context or add external
 * synchronized protection by yourself.
 *
 * @author  Jonathan Payne
 * @author Shuyang Zhou
 * @see java.io.FilterInputStream
 */
public class UnsyncFilterInputStream extends InputStream {
	/**
	 * @see java.io.FilterInputStream#in
	 */
	protected InputStream in;

	/**
	 * @see java.io.FilterInputStream#FilterInputStream(InputStream)
	 */
	protected UnsyncFilterInputStream(InputStream in) {
		this.in = in;
	}

	/**
	 * @see java.io.FilterInputStream#read()
	 */
	public int read() throws IOException {
		return in.read();
	}

	/**
	 * @see java.io.FilterInputStream#read(byte[])
	 */
	public int read(byte b[]) throws IOException {
		return read(b, 0, b.length);
	}

	/**
	 * @see java.io.FilterInputStream#read(byte[], int, int)
	 */
	public int read(byte b[], int off, int len) throws IOException {
		return in.read(b, off, len);
	}

	/**
	 * @see java.io.FilterInputStream#skip(long)
	 */
	public long skip(long n) throws IOException {
		return in.skip(n);
	}

	/**
	 * @see java.io.FilterInputStream#available()
	 */
	public int available() throws IOException {
		return in.available();
	}

	/**
	 * @see java.io.FilterInputStream#close()
	 */
	public void close() throws IOException {
		in.close();
	}

	/**
	 * @see java.io.FilterInputStream#mark(int)
	 */
	public void mark(int readlimit) {
		in.mark(readlimit);
	}

	/**
	 * @see java.io.FilterInputStream#reset()
	 */
	public void reset() throws IOException {
		in.reset();
	}

	/**
	 * @see java.io.FilterInputStream#markSupported()
	 */
	public boolean markSupported() {
		return in.markSupported();

	}

}