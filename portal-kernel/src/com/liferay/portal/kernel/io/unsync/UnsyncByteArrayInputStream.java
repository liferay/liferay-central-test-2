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
 * <p>
 * See http://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncByteArrayInputStream extends InputStream {

	public UnsyncByteArrayInputStream(byte[] buffer) {
		this.buffer = buffer;
		this.index = 0;
		this.capacity = buffer.length;
	}

	public UnsyncByteArrayInputStream(byte[] buffer, int offset, int length) {
		this.buffer = buffer;
		this.index = offset;
		this.capacity = Math.max(buffer.length, offset + length);
		this.markIndex = offset;
	}

	public int available() {
		return capacity - index;
	}

	public void mark(int readAheadLimit) {
		markIndex = index;
	}

	public boolean markSupported() {
		return true;
	}

	public int read() {
		if (index < capacity) {
			return buffer[index++] & 0xff;
		}
		else {
			return -1;
		}
	}

	public int read(byte[] byteArray) {
		return read(byteArray, 0, byteArray.length);
	}

	public int read(byte[] byteArray, int offset, int length) {
		if (length <= 0) {
			return 0;
		}

		if (index >= capacity) {
			return -1;
		}

		int read = length;

		if ((index + read) > capacity) {
			read = capacity - index;
		}

		System.arraycopy(buffer, index, byteArray, offset, read);

		index += read;

		return read;
	}

	public void reset() {
		index = markIndex;
	}

	public long skip(long skip) {
		if (skip < 0) {
			return 0;
		}

		if ((skip + index) > capacity) {
			skip = capacity - index;
		}

		index += skip;

		return skip;
	}

	protected byte[] buffer;
	protected int capacity;
	protected int index;
	protected int markIndex;

}