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
 * Note: This class has the same function as
 * {@link java.io.BufferedInputStream}, but without synchronized protection.
 * We make this for performance, see http://issues.liferay.com/browse/LPS-6648.
 *
 * Warning: This class is not thread safe, make sure using it only under single
 * thread context or adding external synchronized protection.
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedInputStream extends UnsyncFilterInputStream {

	public UnsyncBufferedInputStream(InputStream in) {
		this(in, _DEFAULT_BUFFER_SIZE);
	}

	public UnsyncBufferedInputStream(InputStream in, int size) {
		super(in);
		buffer = new byte[size];
	}

	public int available() throws IOException {
		if (in == null) {
			throw new IOException("Stream closed");
		}
		return in.available() + (firstInvalidIndex - index);
	}

	public void close() throws IOException {
		if (in == null) {
			throw new IOException("Stream already closed");
		}
		buffer = null;
		in.close();
		in = null;
	}

	public void mark(int readlimit) {
		marklimit = readlimit;
		markIndex = index;
	}

	public boolean markSupported() {
		return true;
	}

	public int read() throws IOException {
		if (index >= firstInvalidIndex) {
			readUnderlyingInputStream();
			if (index >= firstInvalidIndex) {
				return -1;
			}
		}
		return buffer[index++] & 0xff;
	}

	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	public int read(byte[] b, int off, int len) throws IOException {
		if (in == null) {
			throw new IOException("Stream closed");
		}
		if (len <= 0) {
			return 0;
		}

		int readedNumber = 0;
		while (true) {
			int inBufferAvailable = firstInvalidIndex - index;
			if (inBufferAvailable + readedNumber >= len) {
				//Enough data, stop reading
				int leftSize = len - readedNumber;
				System.arraycopy(buffer, index, b, readedNumber, leftSize);
				index += leftSize;
				return len;
			}

			if (inBufferAvailable <= 0) {
				//No more data in buffer, try to read more
				readUnderlyingInputStream();
				inBufferAvailable = firstInvalidIndex - index;
				if (inBufferAvailable <= 0) {
					//Can not read any more, stop trying
					return readedNumber;
				}
			}
			else {
				//Copy all in-memory data, try to read more.
				System.arraycopy(
					buffer, index, b, readedNumber, inBufferAvailable);
				index += inBufferAvailable;
				readedNumber += inBufferAvailable;
			}
		}
	}

	public void reset() throws IOException {
		if (in == null) {
			throw new IOException("Stream closed");
		}
		if (markIndex < 0) {
			throw new IOException("Resetting to invalid mark");
		}
		index = markIndex;
	}

	public long skip(long n) throws IOException {
		if (in == null) {
			throw new IOException("Stream closed");
		}
		if (n <= 0) {
			return 0;
		}

		long skipped = 0;
		long inBufferAvailable = firstInvalidIndex - index;
		if (inBufferAvailable > 0) {
			//only skip the data in buffer
			skipped = (inBufferAvailable < n) ? inBufferAvailable : n;
		}
		else {
			//Have to skip underlying InputStream
			if (markIndex < 0) {
				//No mark required, so directly skip
				skipped = in.skip(n);
			}
			else {
				//Mark required, have to save the skipped data
				readUnderlyingInputStream();
				inBufferAvailable = firstInvalidIndex - index;

				if (inBufferAvailable > 0) {
					//only skip the data in buffer
					skipped = (inBufferAvailable < n) ? inBufferAvailable : n;
				}
			}
		}
		index += skipped;
		return skipped;
	}

	private void readUnderlyingInputStream() throws IOException {
		if (in == null) {
			throw new IOException("Stream closed");
		}
		if (markIndex < 0) {
			//No need to mark, simply fill the buffer
			index = firstInvalidIndex = 0;
			int number = in.read(buffer);
			if (number > 0) {
				firstInvalidIndex = number;
			}
			return;
		}

		//Mark enabled
		if (index >= buffer.length) {
			//Buffer is full, needs cleanup or grow

			if (firstInvalidIndex - markIndex > marklimit) {
				//Overrun marklimit, get rid of all cache data
				markIndex = -1;
				index = 0;
			}
			else if (markIndex > _MAX_MARK_WASTE_SIZE) {
				//There are more than _MAX_MARK_WASTE_SIZE free space at the
				//beginning of buffer, shuffle the buffer to clean up room.
				int realDataSize = index - markIndex;
				System.arraycopy(
					buffer, markIndex, buffer, 0, realDataSize);
				index = realDataSize;
				markIndex = 0;
			}
			else {
				//Can't get rid of cache data, and it is not efficient to
				//shuffle the buffer, so have to grow buffer.
				int newBufferSize = index << 1;
				if (newBufferSize - _MAX_MARK_WASTE_SIZE > marklimit) {
					//No need to make new buffer size bigger than marklimit.
					newBufferSize = marklimit + _MAX_MARK_WASTE_SIZE;
				}
				byte[] newBuffer = new byte[newBufferSize];
				System.arraycopy(buffer, 0, newBuffer, 0, index);
				buffer = newBuffer;
			}
		}

		//There should be free space in buffer now, read underlying InputStream
		firstInvalidIndex = index;
		int number = in.read(buffer, index, buffer.length - index);
		if (number > 0) {
			firstInvalidIndex += number;
		}
	}

	protected byte[] buffer;
	protected int firstInvalidIndex;
	protected int index;
	protected int markIndex = -1;
	protected int marklimit;
	private static int _DEFAULT_BUFFER_SIZE = 8192;
	private static int _MAX_MARK_WASTE_SIZE = 4096;

}