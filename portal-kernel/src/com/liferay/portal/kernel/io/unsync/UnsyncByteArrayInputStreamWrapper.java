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

import javax.servlet.ServletInputStream;

/**
 * <a href="UnsyncByteArrayInputStreamWrapper.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UnsyncByteArrayInputStreamWrapper extends ServletInputStream {

	public UnsyncByteArrayInputStreamWrapper(
		UnsyncByteArrayInputStream unsyncByteArrayInputStream) {

		_unsyncByteArrayInputStream = unsyncByteArrayInputStream;
	}

	public int available() {
		return _unsyncByteArrayInputStream.available();
	}

	public void close() throws IOException {
		_unsyncByteArrayInputStream.close();
	}

	public void mark(int readLimit) {
		_unsyncByteArrayInputStream.mark(readLimit);
	}

	public boolean markSupported() {
		return _unsyncByteArrayInputStream.markSupported();
	}

	public int read() {
		return _unsyncByteArrayInputStream.read();
	}

	public int read(byte[] byteArray) {
		return _unsyncByteArrayInputStream.read(byteArray);
	}

	public int read(byte[] byteArray, int offset, int length) {
		return _unsyncByteArrayInputStream.read(byteArray, offset, length);
	}

	public int readLine(byte[] byteArray, int offset, int length) {
		return _unsyncByteArrayInputStream.read(byteArray, offset, length);
	}

	public void reset() {
		_unsyncByteArrayInputStream.reset();
	}

	public long skip(long skip) {
		return _unsyncByteArrayInputStream.skip(skip);
	}

	private UnsyncByteArrayInputStream _unsyncByteArrayInputStream;

}