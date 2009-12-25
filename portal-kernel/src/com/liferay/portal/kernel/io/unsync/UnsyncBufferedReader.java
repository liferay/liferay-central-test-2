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

	public UnsyncBufferedReader(Reader reader, int bufferSize) {
		this.reader = reader;
		buffer = new char[bufferSize];
	}

	public UnsyncBufferedReader(Reader in) {
		this(in, _DEFAULT_BUFFER_SIZE);
	}

	public void close() throws IOException {
		if (reader == null) {
			throw new IOException("Reader closed");
		}
		reader.close();
		reader = null;
		buffer = null;

	}

	public void mark(int markLimit) throws IOException {
		if (reader == null) {
			throw new IOException("Reader closed");
		}
		this.markLimit = markLimit;
		markIndex = index;
	}

	public boolean markSupported() {
		return true;
	}

	public int read() throws IOException {
		if (reader == null) {
			throw new IOException("Reader closed");
		}
		if (index >= firstInvalidIndex) {
			readUnderlyingReader();
			if (index >= firstInvalidIndex) {
				return -1;
			}
		}
		return buffer[index++];
	}

	public int read(char[] b) throws IOException {
		return read(b, 0, b.length);
	}

	public int read(char[] b, int off, int len) throws IOException {
		if (reader == null) {
			throw new IOException("Reader closed");
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

				readUnderlyingReader();

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

	public String readLine() throws IOException {
		if (reader == null) {
			throw new IOException("Reader closed");
		}

		StringBundler sb = null;

		while (true) {
			//Fill buffer if needed
			if (index >= firstInvalidIndex) {
				readUnderlyingReader();
			}
			if (index >= firstInvalidIndex) {
				//No more data, stop reading
				if (sb != null && sb.index() > 0) {
					return sb.toString();
				}
				else {
					return null;
				}
			}

			boolean isLineEnd = false;
			int lineStartPosition = index;
			int lineEndPosition = index;
			char lineEndChar = 0;

			//Search line end position
			while (lineEndPosition < firstInvalidIndex) {
				lineEndChar = buffer[lineEndPosition];
				if ((lineEndChar == '\n') || (lineEndChar == '\r')) {
					isLineEnd = true;
					break;
				}
				lineEndPosition++;
			}

			String thisLine =
				new String(buffer, lineStartPosition,
					lineEndPosition - lineStartPosition);

			//skip line data
			index = lineEndPosition;

			if (isLineEnd) {
				//skip line end char '\n' or '\r'
				index++;
				if (lineEndChar == '\r') {
					//if end with '\r', check next char for '\n'
					if (index < buffer.length && buffer[index] == '\n') {
						//skip '\n'
						index++;
					}
				}

				if (sb == null) {
					//SB has not been created yet, return the String directly
					return thisLine;
				}
				else {
					//Append to SB, then get the final String
					sb.append(thisLine);
					return sb.toString();
				}

			}

			//lazy create SB
			if (sb == null) {
				sb = new StringBundler();
			}
			sb.append(thisLine);
		}
	}

	public boolean ready() throws IOException {
		if (reader == null) {
			throw new IOException("Reader closed");
		}

		return (index < firstInvalidIndex) || reader.ready();
	}

	public void reset() throws IOException {
		if (reader == null) {
			throw new IOException("Reader closed");
		}
		if (markIndex < 0) {
			throw new IOException("Resetting to invalid mark");
		}

		index = markIndex;
	}

	public long skip(long n) throws IOException {
		if (reader == null) {
			throw new IOException("Reader closed");
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

			// Skip the underlying reader

			if (markIndex < 0) {

				// No mark required, skip

				skipped = reader.skip(n);
			}
			else {

				// Mark required, save the skipped data

				readUnderlyingReader();

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

	private void readUnderlyingReader() throws IOException {
		if (reader == null) {
			throw new IOException("Reader closed");
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