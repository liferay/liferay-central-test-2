/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.StringBundler;

import javax.servlet.jsp.JspWriter;

/**
 * <a href="StringJspWriter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class StringJspWriter extends JspWriter {

	public StringJspWriter() {
		super(NO_BUFFER, false);
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public void clearBuffer() {
		throw new UnsupportedOperationException();
	}

	public void close() {
		throw new UnsupportedOperationException();
	}

	public void flush() {
		throw new UnsupportedOperationException();
	}

	public int getRemaining() {
		throw new UnsupportedOperationException();
	}

	public String getString() {
		if (_stringBundler != null) {
			return _stringBundler.toString();
		}
		else {
			return _firstString;
		}
	}

	public void newLine() {
		throw new UnsupportedOperationException();
	}

	public void print(boolean value) {
		throw new UnsupportedOperationException();
	}

	public void print(char value) {
		throw new UnsupportedOperationException();
	}

	public void print(int value) {
		throw new UnsupportedOperationException();
	}

	public void print(long value) {
		throw new UnsupportedOperationException();
	}

	public void print(float value) {
		throw new UnsupportedOperationException();
	}

	public void print(double value) {
		throw new UnsupportedOperationException();
	}

	public void print(char[] value) {
		throw new UnsupportedOperationException();
	}

	public void print(String value) {
		if (_firstString == null) {
			_firstString = value;
		}
		else if (_stringBundler == null) {
			_stringBundler = new StringBundler();

			_stringBundler.append(_firstString);
		}

		_stringBundler.append(value);
	}

	public void print(Object value) {
		throw new UnsupportedOperationException();
	}

	public void println() {
		throw new UnsupportedOperationException();
	}

	public void println(boolean value) {
		throw new UnsupportedOperationException();
	}

	public void println(char value) {
		throw new UnsupportedOperationException();
	}

	public void println(int value) {
		throw new UnsupportedOperationException();
	}

	public void println(long value) {
		throw new UnsupportedOperationException();
	}

	public void println(float value) {
		throw new UnsupportedOperationException();
	}

	public void println(double value) {
		throw new UnsupportedOperationException();
	}

	public void println(char[] value) {
		throw new UnsupportedOperationException();
	}

	public void println(String value) {
		throw new UnsupportedOperationException();
	}

	public void println(Object value) {
		throw new UnsupportedOperationException();
	}

	public void write(char[] charArray, int offset, int length) {
		throw new UnsupportedOperationException();
	}

	private String _firstString;
	private StringBundler _stringBundler;

}