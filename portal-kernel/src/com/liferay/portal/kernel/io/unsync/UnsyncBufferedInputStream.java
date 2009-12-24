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
 * <p>
 * See http://support.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedInputStream extends UnsyncFilterInputStream {

	public UnsyncBufferedInputStream(InputStream inputStream) {
		this(inputStream, _DEFAULT_BUFFER_SIZE);
	}

	public UnsyncBufferedInputStream(InputStream inputStream, int size) {
		super(inputStream);

		buffer = new byte[size];
	}

	public int available() throws IOException {
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		return inputStream.available() + (firstInvalidIndex - index);
	}

	public void close() throws IOException {
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		buffer = null;

		inputStream.close();

		inputStream = null;
	}

	public void mark(int readLimit) {
		markLimit = readLimit;
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
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		if (len <= 0) {
			return 0;
		}

		int readNumber = 0;

		while (true) {
			int inBufferAvailable = firstInvalidIndex - index;

			if ((inBufferAvailable + readNumber) >= len) {

				// Enough data, stop reading

				int leftSize = len - readNumber;

				System.arraycopy(buffer, index, b, readNumber, leftSize);

				index += leftSize;

				return len;
			}

			if (inBufferAvailable <= 0) {

				// No more data in buffer, continue reading

				readUnderlyingInputStream();

				inBufferAvailable = firstInvalidIndex - index;

				if (inBufferAvailable <= 0) {

					// Cannot read any more, stop reading

					return readNumber == 0 ? -1 : readNumber;
				}
			}
			else {

				// Copy all in-memory data, continue reading

				System.arraycopy(
					buffer, index, b, readNumber, inBufferAvailable);

				index += inBufferAvailable;
				readNumber += inBufferAvailable;
			}
		}
	}
	public void reset() throws IOException {
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		if (markIndex < 0) {
			throw new IOException("Resetting to invalid mark");
		}

		index = markIndex;
	}

	public long skip(long n) throws IOException {
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		if (n <= 0) {
			return 0;
		}

		long skipped = 0;
		long inBufferAvailable = firstInvalidIndex - index;

		if (inBufferAvailable > 0) {

			// Skip the data in buffer

			if (inBufferAvailable < n) {
				skipped = inBufferAvailable;
			}
			else {
				skipped = n;
			}
		}
		else {

			// Skip the underlying input stream

			if (markIndex < 0) {

				// No mark required, skip

				skipped = inputStream.skip(n);
			}
			else {

				// Mark required, save the skipped data

				readUnderlyingInputStream();

				inBufferAvailable = firstInvalidIndex - index;

				if (inBufferAvailable > 0) {

					// Skip the data in buffer

					if (inBufferAvailable < n) {
						skipped = inBufferAvailable;
					}
					else {
						skipped = n;
					}
				}
			}
		}

		index += skipped;

		return skipped;
	}

	private void readUnderlyingInputStream() throws IOException {
		if (inputStream == null) {
			throw new IOException("Input stream is null");
		}

		if (markIndex < 0) {

			// No mark required, fill the buffer

			index = firstInvalidIndex = 0;

			int number = inputStream.read(buffer);

			if (number > 0) {
				firstInvalidIndex = number;
			}

			return;
		}

		// Mark required

		if (index >= buffer.length) {

			// Buffer is full, clean up or grow

			if ((firstInvalidIndex - markIndex) > markLimit) {

				// Passed mark limit, get rid of all cache data

				markIndex = -1;
				index = 0;
			}
			else if (markIndex > _MAX_MARK_WASTE_SIZE) {

				// There are more than _MAX_MARK_WASTE_SIZE free space at the
				// beginning of buffer, clean up by shuffling the buffer

				int realDataSize = index - markIndex;

				System.arraycopy(
					buffer, markIndex, buffer, 0, realDataSize);

				markIndex = 0;
				index = realDataSize;
			}
			else {

				// Grow the buffer because we cannot get rid of cache data and
				// it is inefficient to shuffle the buffer

				int newBufferSize = index << 1;

				if ((newBufferSize - _MAX_MARK_WASTE_SIZE) > markLimit) {

					// Make thew new buffer size larger than the mark limit

					newBufferSize = markLimit + _MAX_MARK_WASTE_SIZE;
				}

				byte[] newBuffer = new byte[newBufferSize];

				System.arraycopy(buffer, 0, newBuffer, 0, index);

				buffer = newBuffer;
			}
		}

		// Read underlying input stream since the buffer has more space

		firstInvalidIndex = index;

		int number = inputStream.read(buffer, index, buffer.length - index);

		if (number > 0) {
			firstInvalidIndex += number;
		}
	}

	protected byte[] buffer;
	protected int firstInvalidIndex;
	protected int index;
	protected int markIndex = -1;
	protected int markLimit;

	private static int _DEFAULT_BUFFER_SIZE = 8192;

	private static int _MAX_MARK_WASTE_SIZE = 4096;

}