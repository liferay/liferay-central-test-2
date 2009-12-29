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

	public UnsyncBufferedWriter(Writer writer, int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Buffer size is less than 0");
		}

		this.writer = writer;
		this.size = size;

		buffer = new char[size];
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

	public void flush() throws IOException {
		if (writer == null) {
			throw new IOException("Writer is null");
		}

		if (count > 0) {
			writer.write(buffer, 0, count);

			count = 0;
		}

		writer.flush();
	}

	public void newLine() throws IOException {
		write(_LINE_SEPARATOR);
	}

	public void write(char[] charArray, int offset, int length)
		throws IOException {

		if (writer == null) {
			throw new IOException("Writer is null");
		}

		if (length >= size) {
			if (count > 0) {
				writer.write(buffer, 0, count);

				count = 0;
			}

			writer.write(charArray, offset, length);

			return;
		}

		if ((count > 0) && (length > (size - count))) {
			writer.write(buffer, 0, count);

			count = 0;
		}

		System.arraycopy(charArray, offset, buffer, count, length);

		count += length;
	}

	public void write(int c) throws IOException {
		if (writer == null) {
			throw new IOException("Writer is null");
		}

		if (count >= size) {
			writer.write(buffer);

			count = 0;
		}

		buffer[count++] = (char)c;
	}

	public void write(String string, int offset, int length)
		throws IOException {

		if (writer == null) {
			throw new IOException("Writer is null");
		}

		int x = offset;
		int y = offset + length;

		while (x < y) {
			if (count >= size) {
				writer.write(buffer);

				count = 0;
			}

			int leftFreeSpace = size - count;
			int leftDataSize = y - x;

			if (leftFreeSpace > leftDataSize) {
				string.getChars(x, y, buffer, count);

				count += leftDataSize;

				break;
			}
			else {
				int copyEnd = x + leftFreeSpace;

				string.getChars(x, copyEnd, buffer, count);

				count += leftFreeSpace;

				x = copyEnd;
			}
		}
	}

	protected char[] buffer;
	protected int count;
	protected int size;
	protected Writer writer;

	private static int _DEFAULT_BUFFER_SIZE = 8192;

	private static String _LINE_SEPARATOR = System.getProperty(
		"line.separator");

}