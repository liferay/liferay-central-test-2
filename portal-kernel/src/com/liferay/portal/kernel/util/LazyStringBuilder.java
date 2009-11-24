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

package com.liferay.portal.kernel.util;

/**
 * <a href="LazyStringBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * A better replacer of jdk's StringBuilder.
 * StringBuilder suffers from internal buffer char[] expanding, which may create
 * a lot of short life char[] causes GC problem.
 * LazyStringBuilder builds the final String in a lazy manner, so can avoid the
 * temporary buffer objects as much as possible, which create fewer garbages.
 *
 * Warning! LazyStringBuilder is the same as StringBuilder, they are not thread
 * safe. Try to not use them in multi-thread context or provide external
 * synchronization.
 *
 * For more info, see LPS-6072
 *
 * @author Shuyang Zhou
 */
public class LazyStringBuilder {

	public LazyStringBuilder() {
		_strings = new String[_DEFAULT_STRING_NUMBER];
	}

	public LazyStringBuilder(String str) {
		_strings = new String[_DEFAULT_STRING_NUMBER];
		_strings[0] = str;
		_stringCount = 1;
	}

	public LazyStringBuilder(int stringNumber) {
		if (stringNumber <= 0) {
			throw new IllegalArgumentException(
				"String number has to be bigger than 0");
		}
		_strings = new String[stringNumber];
	}

	public LazyStringBuilder append(Object obj) {
		return append(String.valueOf(obj));
	}

	public LazyStringBuilder append(String str) {
		if (_stringCount >= _strings.length) {
			expandCapacity();
		}
		_strings[_stringCount++] = str;
		return this;
	}

	public LazyStringBuilder append(boolean b) {
		return append(b ? "true" : "false");
	}

	public LazyStringBuilder append(int i) {
		return append(Integer.toString(i));
	}

	public LazyStringBuilder append(long l) {
		return append(Long.toString(l));
	}

	public LazyStringBuilder append(float f) {
		return append(Float.toString(f));
	}

	public LazyStringBuilder append(double d) {
		return append(Double.toString(d));
	}

	public String getStringAt(int index) {
		if (index >= _stringCount) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return _strings[index];
	}

	public int getStringCount() {
		return _stringCount;
	}

	public int getStringLength() {
		int length = 0;
		for (int i = 0; i < _stringCount; i++) {
			length += _strings[i].length();
		}
		return length;
	}

	public String toString() {
		if (_stringCount == 0) {
			return _EMPTY_STRING;
		}
		String result = null;
		if (_stringCount <= 3) {
			result = _strings[0];
			for (int i = 1; i < _stringCount; i++) {
				result = result.concat(_strings[i]);
			}
		}
		else {
			int length = getStringLength();
			StringBuilder sb = new StringBuilder(length);
			for (int i = 0; i < _stringCount; i++) {
				sb.append(_strings[i]);
			}
			result = sb.toString();
		}

		return result;
	}

	protected void expandCapacity() {
		String[] newStrings = new String[_strings.length << 1];
		System.arraycopy(_strings, 0, newStrings, 0, _strings.length);
		_strings = newStrings;
	}

	private static final int _DEFAULT_STRING_NUMBER = 16;
	private static final String _EMPTY_STRING = "";
	private int _stringCount;
	private String[] _strings;

}