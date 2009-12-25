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
import java.io.Writer;

/**
 * <a href="UnsyncBufferedWriter.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://support.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncBufferedWriter extends Writer {

	public UnsyncBufferedWriter(Writer writer) {
		this(writer, _DEFAULT_BUFFER_SIZE);
	}

	public UnsyncBufferedWriter(Writer writer, int bufferSize) {
		if (bufferSize <= 0) {
			throw new IllegalArgumentException("Buffer size <= 0");
		}
		this.writer = writer;
		buffer = new char[bufferSize];
		this.bufferSize = bufferSize;
		count = 0;
		lineSeparator = System.getProperty("line.separator");
	}

	public void close() throws IOException {
		if (writer == null) {
			return;
		}
		if (count > 0) {
			writer.write(buffer, 0, count);
			count = 0;
		}
		writer.flush();
		writer.close();
		writer = null;
		buffer = null;
	}

	public void newLine() throws IOException {
		write(lineSeparator);
	}

	public void flush() throws IOException {
		if (count > 0) {
			writer.write(buffer, 0, count);
			count = 0;
		}
		writer.flush();
	}

	public void write(int c) throws IOException {
		if (writer == null) {
			throw new IOException("Writer closed");
		}

		if (count >= bufferSize) {
			writer.write(buffer);
			count = 0;
		}
		buffer[count++] = (char) c;
	}

	public void write(char[] cbuf, int off, int len) throws IOException {
		if (writer == null) {
			throw new IOException("Writer closed");
		}
		//Directly write
		if (len >= bufferSize) {
			if (count > 0) {
				writer.write(buffer, 0, count);
				count = 0;
			}
			writer.write(cbuf, off, len);
			return;
		}
		//Auto flush
		if (count > 0 && len > bufferSize - count) {
			writer.write(buffer, 0, count);
			count = 0;
		}
		System.arraycopy(cbuf, off, buffer, count, len);
		count += len;
	}

	public void write(String s, int off, int len) throws IOException {
		if (writer == null) {
			throw new IOException("Writer closed");
		}

		int begin = off;
		int end = off + len;
		while (begin < end) {
			//Flush buffer
			if (count >= bufferSize) {
				writer.write(buffer);
				count = 0;
			}
			int leftFreeSpace = bufferSize - count;
			int leftDataSize = end - begin;
			if (leftFreeSpace > leftDataSize) {
				//last copy
				s.getChars(begin, end, buffer, count);
				count += leftDataSize;
				break;
			}
			else {
				//need more copy
				int copyEnd = begin + leftFreeSpace;
				s.getChars(begin, copyEnd, buffer, count);
				count += leftFreeSpace;
				begin = copyEnd;
			}

		}
	}

	protected Writer writer;
	protected char[] buffer;
	protected int bufferSize;
	protected int count;
	protected String lineSeparator;

	private static int _DEFAULT_BUFFER_SIZE = 8192;

}