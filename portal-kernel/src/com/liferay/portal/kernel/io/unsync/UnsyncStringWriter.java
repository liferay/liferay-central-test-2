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
import com.liferay.portal.kernel.util.StringPool;

import java.io.Writer;

/**
 * <a href="UnsyncStringWriter.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * See http://issues.liferay.com/browse/LPS-6648.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class UnsyncStringWriter extends Writer {

	public UnsyncStringWriter(boolean useStringBundler) {
		if (useStringBundler) {
			stringBundler = new StringBundler();
		}
		else {
			stringBuilder = new StringBuilder();
		}
	}

	public UnsyncStringWriter(boolean useStringBundler, int initialCapacity) {
		if (useStringBundler) {
			stringBundler = new StringBundler(initialCapacity);
		}
		else {
			stringBuilder = new StringBuilder(initialCapacity);
		}
	}

	public UnsyncStringWriter append(char c) {
		write(c);

		return this;
	}

	public UnsyncStringWriter append(CharSequence charSequence) {
		if (charSequence == null) {
			write(StringPool.NULL);
		}
		else {
			write(charSequence.toString());
		}

		return this;
	}

	public UnsyncStringWriter append(
		CharSequence charSequence, int start, int end) {

		if (charSequence == null) {
			charSequence = StringPool.NULL;
		}

		write(charSequence.subSequence(start, end).toString());

		return this;
	}

	public void close() {
	}

	public void flush() {
	}

	public StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	public StringBundler getStringBundler() {
		return stringBundler;
	}

	public void reset() {
		if (stringBundler != null) {
			stringBundler.setIndex(0);
		}
		else {
			stringBuilder.setLength(0);
		}
	}

	public String toString() {
		if (stringBundler != null) {
			return stringBundler.toString();
		}
		else {
			return stringBuilder.toString();
		}
	}

	public void write(char[] charArray, int offset, int length) {
		if (length <= 0) {
			return;
		}

		if (stringBundler != null) {
			stringBundler.append(new String(charArray, offset, length));
		}
		else {
			stringBuilder.append(charArray, offset, length);
		}
	}

	public void write(char[] charArray) {
		write(charArray, 0, charArray.length);

	}

	public void write(int c) {
		if (stringBundler != null) {
			stringBundler.append(String.valueOf((char)c));
		}
		else {
			stringBuilder.append((char)c);
		}
	}

	public void write(String string) {
		if (stringBundler != null) {
			stringBundler.append(string);
		}
		else {
			stringBuilder.append(string);
		}
	}

	public void write(String string, int offset, int length) {
		if (stringBundler != null) {
			stringBundler.append(string.substring(offset, offset + length));
		}
		else {
			stringBuilder.append(string.substring(offset, offset + length));
		}
	}

	protected StringBuilder stringBuilder;
	protected StringBundler stringBundler;

}