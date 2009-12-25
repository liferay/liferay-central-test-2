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

	public UnsyncStringReader(String s) {
		string = s;
		capability = s.length();
	}

	public void close() {
	}

	public void mark(int readAheadLimit) {
		markIndex = index;
	}

	public boolean markSupported() {
		return true;
	}

	public int read() {
		if (index >= capability) {
			return -1;
		}
		return string.charAt(index++);
	}

	public int read(CharBuffer target) {
		int length = target.remaining();
		char[] cbuf = new char[length];
		int number = read(cbuf, 0, length);
		if (number > 0) {
			target.put(cbuf, 0, number);
		}
		return number;
	}

	public int read(char[] cbuf) {
		return read(cbuf, 0, cbuf.length);
	}

	public int read(char[] cbuf, int off, int len) {
		if (len <= 0) {
			return 0;
		}
		if (index >= capability) {
			return -1;
		}

		int length = len;

		if (index + length > capability) {
			length = capability - index;
		}

		string.getChars(index, index + length, cbuf, off);
		index += length;
		return length;
	}

	public boolean ready() {
		return true;
	}

	public void reset() {
		index = markIndex;
	}

	public long skip(long ns) {
		if (index >= capability) {
			return 0;
		}
		long length = ns;
		if (index + ns > capability) {
			length = capability - index;
		}
		index += length;
		return length;
	}

	protected String string;
	protected int capability;
	protected int index;
	protected int markIndex;

}