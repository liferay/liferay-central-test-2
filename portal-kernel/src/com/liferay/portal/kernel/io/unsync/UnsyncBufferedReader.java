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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;
import java.io.Reader;

/**
 * <a href="UnsyncBufferedReader.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://support.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedReader extends Reader {

	public UnsyncBufferedReader(Reader reader) {
		this(reader, _DEFAULT_BUFFER_SIZE);
	}

	public UnsyncBufferedReader(Reader reader, int size) {
		this.reader = reader;
		buffer = new char[size];
	}

	public void close() throws IOException {
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		reader.close();

		reader = null;
		buffer = null;
	}

	public void mark(int markLimit) throws IOException {
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		this.markLimit = markLimit;
		markIndex = index;
	}

	public boolean markSupported() {
		return true;
	}

	public int read() throws IOException {
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		if (index >= firstInvalidIndex) {
			readUnderlyingReader();

			if (index >= firstInvalidIndex) {
				return -1;
			}
		}

		return buffer[index++];
	}

	public int read(char[] charArray) throws IOException {
		return read(charArray, 0, charArray.length);
	}

	public int read(char[] charArray, int offset, int length)
		throws IOException {

		if (reader == null) {
			throw new IOException("Reader is null");
		}

		if (length <= 0) {
			return 0;
		}

		int read = 0;

		while (true) {
			int available = firstInvalidIndex - index;

			if ((available + read) >= length) {

				// Enough data, stop reading

				int leftSize = length - read;

				System.arraycopy(buffer, index, charArray, read, leftSize);

				index += leftSize;

				return length;
			}

			if (available <= 0) {

				// No more data in buffer, continue reading

				readUnderlyingReader();

				available = firstInvalidIndex - index;

				if (available <= 0) {

					// Cannot read any more, stop reading

					if (read == 0) {
						return -1;
					}
					else {
						return read;
					}
				}
			}
			else {

				// Copy all in-memory data, continue reading

				System.arraycopy(buffer, index, charArray, read, available);

				index += available;
				read += available;
			}
		}
	}

	public String readLine() throws IOException {
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		StringBundler sb = null;

		while (true) {
			if (index >= firstInvalidIndex) {
				readUnderlyingReader();
			}

			if (index >= firstInvalidIndex) {
				if ((sb != null) && (sb.index() > 0)) {
					return sb.toString();
				}
				else {
					return null;
				}
			}

			boolean hasLineBreak = false;
			char lineEndChar = 0;

			int x = index;
			int y = index;

			while (y < firstInvalidIndex) {
				lineEndChar = buffer[y];

				if ((lineEndChar == CharPool.NEW_LINE) ||
					(lineEndChar == CharPool.RETURN)) {

					hasLineBreak = true;

					break;
				}

				y++;
			}

			String line = new String(buffer, x, y - x);

			index = y;

			if (hasLineBreak) {
				index++;

				if (lineEndChar == CharPool.RETURN) {
					if ((index < buffer.length) &&
						(buffer[index] == CharPool.NEW_LINE)) {

						index++;
					}
				}

				if (sb == null) {
					return line;
				}
				else {
					sb.append(line);

					return sb.toString();
				}
			}

			if (sb == null) {
				sb = new StringBundler();
			}

			sb.append(line);
		}
	}

	public boolean ready() throws IOException {
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		return (index < firstInvalidIndex) || reader.ready();
	}

	public void reset() throws IOException {
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		if (markIndex < 0) {
			throw new IOException("Resetting to invalid mark");
		}

		index = markIndex;
	}

	public long skip(long skip) throws IOException {
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		if (skip <= 0) {
			return 0;
		}

		long available = firstInvalidIndex - index;

		if (available > 0) {

			// Skip the data in buffer

			if (available < skip) {
				skip = available;
			}
		}
		else {

			// Skip the underlying reader

			if (markIndex < 0) {

				// No mark required, skip

				skip = reader.skip(skip);
			}
			else {

				// Mark required, save the skipped data

				readUnderlyingReader();

				available = firstInvalidIndex - index;

				if (available > 0) {

					// Skip the data in buffer

					if (available < skip) {
						skip = available;
					}
				}
			}
		}

		index += skip;

		return skip;
	}

	protected void readUnderlyingReader() throws IOException {
		if (reader == null) {
			throw new IOException("Reader is null");
		}

		if (markIndex < 0) {

			// No mark required, fill the buffer

			index = firstInvalidIndex = 0;

			int number = reader.read(buffer);

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

				char[] newBuffer = new char[newBufferSize];

				System.arraycopy(buffer, 0, newBuffer, 0, index);

				buffer = newBuffer;
			}
		}

		// Read underlying reader since the buffer has more space

		firstInvalidIndex = index;

		int number = reader.read(buffer, index, buffer.length - index);

		if (number > 0) {
			firstInvalidIndex += number;
		}
	}

	protected char[] buffer;
	protected int firstInvalidIndex;
	protected int index;
	protected int markIndex = -1;
	protected int markLimit;
	protected Reader reader;

	private static int _DEFAULT_BUFFER_SIZE = 8192;

	private static int _MAX_MARK_WASTE_SIZE = 4096;

}