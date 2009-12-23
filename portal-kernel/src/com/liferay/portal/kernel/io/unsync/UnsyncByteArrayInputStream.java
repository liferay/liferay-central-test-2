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

import java.io.InputStream;

/**
 * <a href="UnsyncByteArrayInputStream.java.html"><b><i>View Source</i></b></a>
 *
 * Note: This class has the same function as
 * {@link java.io.ByteArrayInputStream}, but without synchronized protection.
 * We make this for performance, see http://issues.liferay.com/browse/LPS-6648.
 *
 * Warning: This class is not thread safe, make sure using it only under single
 * thread context or adding external synchronized protection.
 *
 * @author Shuyang Zhou
 */
public class UnsyncByteArrayInputStream extends InputStream {

	public UnsyncByteArrayInputStream(byte[] buffer) {
		this.buffer = buffer;
		this.index = 0;
		this.capability = buffer.length;
	}

	public UnsyncByteArrayInputStream(byte[] buffer, int offset, int length) {
		this.buffer = buffer;
		this.index = offset;
		int expectedCapability = offset + length;
		int physicalCapability = buffer.length;
		this.capability =
			expectedCapability > physicalCapability
				? physicalCapability : expectedCapability;
		this.markIndex = offset;
	}

	public int available() {
		return capability - index;
	}

	public void mark(int readAheadLimit) {
		markIndex = index;
	}

	public boolean markSupported() {
		return true;
	}

	public int read() {
		return (index < capability) ? (buffer[index++] & 0xff) : -1;
	}

	public int read(byte[] b) {
		return read(b, 0, b.length);
	}

	public int read(byte[] b, int off, int len) {

		if (len <= 0) {
			return 0;
		}
		int length = len;

		if (index >= capability) {
			return -1;
		}
		//Trim len to available
		if (index + length > capability) {
			length = capability - index;
		}

		System.arraycopy(buffer, index, b, off, length);
		index += length;
		return length;
	}

	public void reset() {
		index = markIndex;
	}

	public long skip(long n) {
		if (n < 0) {
			return 0;
		}
		long number = n;
		//Trim skip number
		if (index + number > capability) {
			number = capability - index;
		}

		index += number;
		return number;
	}

	protected byte[] buffer;
	protected int capability;
	protected int index;
	protected int markIndex;

}