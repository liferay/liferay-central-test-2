/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.util.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletInputStream;

/**
 * <a href="ByteArrayInputStreamWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ByteArrayInputStreamWrapper extends ServletInputStream {

	public ByteArrayInputStreamWrapper(
		ByteArrayInputStream byteArrayInputStream) {

		_byteArrayInputStream = byteArrayInputStream;
	}

	public int available() {
		return _byteArrayInputStream.available();
	}

	public void close() throws IOException {
		_byteArrayInputStream.close();
	}

	public void mark(int readLimit) {
		_byteArrayInputStream.mark(readLimit);
	}

	public boolean markSupported() {
		return _byteArrayInputStream.markSupported();
	}

	public int read() {
		return _byteArrayInputStream.read();
	}

	public int read(byte[] byteArray) throws IOException {
		return _byteArrayInputStream.read(byteArray);
	}

	public int read(byte[] byteArray, int offset, int length) {
		return _byteArrayInputStream.read(byteArray, offset, length);
	}

	public int readLine(byte[] byteArray, int offset, int length) {
		return _byteArrayInputStream.read(byteArray, offset, length);
	}

	public void reset() {
		_byteArrayInputStream.reset();
	}

	public long skip(long skip) {
		return _byteArrayInputStream.skip(skip);
	}

	private ByteArrayInputStream _byteArrayInputStream;

}