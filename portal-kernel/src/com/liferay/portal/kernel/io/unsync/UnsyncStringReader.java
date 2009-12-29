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
import java.io.Reader;

import java.nio.CharBuffer;

/**
 * <a href="UnsyncStringReader.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://support.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncStringReader extends Reader {

	public UnsyncStringReader(String string) {
		this.string = string;
		stringLength = string.length();
	}

	public void close() {
		string = null;
	}

	public void mark(int readAheadLimit) throws IOException {
		if (string == null) {
			throw new IOException("String is null");
		}
		markIndex = index;
	}

	public boolean markSupported() {
		return true;
	}

	public int read() throws IOException {
		if (string == null) {
			throw new IOException("String is null");
		}

		if (index >= stringLength) {
			return -1;
		}

		return string.charAt(index++);
	}

	public int read(char[] charArray) throws IOException {
		return read(charArray, 0, charArray.length);
	}

	public int read(char[] charArray, int offset, int length)
		throws IOException {
		if (string == null) {
			throw new IOException("String is null");
		}

		if (length <= 0) {
			return 0;
		}

		if (index >= stringLength) {
			return -1;
		}

		int read = length;

		if ((index + read) > stringLength) {
			read = stringLength - index;
		}

		string.getChars(index, index + read, charArray, offset);

		index += read;

		return read;
	}

	public int read(CharBuffer charBuffer) throws IOException {
		int remaining = charBuffer.remaining();

		char[] charArray = new char[remaining];

		int read = read(charArray, 0, remaining);

		if (read > 0) {
			charBuffer.put(charArray, 0, read);
		}

		return read;
	}

	public boolean ready() throws IOException {
		if (string == null) {
			throw new IOException("String is null");
		}
		return true;
	}

	public void reset() throws IOException {
		if (string == null) {
			throw new IOException("String is null");
		}
		index = markIndex;
	}

	public long skip(long skip) {
		if (index >= stringLength) {
			return 0;
		}

		if ((skip + index) > stringLength) {
			skip = stringLength - index;
		}

		index += skip;

		return skip;
	}

	protected int index;
	protected int stringLength;
	protected int markIndex;
	protected String string;

}