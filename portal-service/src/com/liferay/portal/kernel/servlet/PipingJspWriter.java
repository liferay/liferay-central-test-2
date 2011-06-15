/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;

/**
 * @author Shuyang Zhou
 */
public class PipingJspWriter extends JspWriter {

	public PipingJspWriter(Writer writer) {
		super(NO_BUFFER, false);

		_writer = writer;
	}

	public void clear() throws IOException {
		throw new IOException();
	}

	public void clearBuffer() {
	}

	public void close() throws IOException {
		_writer.close();
	}

	public void flush() throws IOException {
		_writer.flush();
	}

	public int getRemaining() {
		return 0;
	}

	public void newLine() throws IOException {
		_writer.write(_LINE_SEPARATOR);
	}

	public void print(boolean b) throws IOException {
		if (b) {
			_writer.write(StringPool.TRUE);
		}
		else {
			_writer.write(StringPool.FALSE);
		}
	}

	public void print(char c) throws IOException {
		_writer.write(c);
	}

	public void print(char[] chars) throws IOException {
		_writer.write(chars);
	}

	public void print(double d) throws IOException {
		_writer.write(String.valueOf(d));
	}

	public void print(float f) throws IOException {
		_writer.write(String.valueOf(f));
	}

	public void print(int i) throws IOException {
		_writer.write(String.valueOf(i));
	}

	public void print(long l) throws IOException {
		_writer.write(String.valueOf(l));
	}

	public void print(Object object) throws IOException {
		_writer.write(String.valueOf(object));
	}

	public void print(String string) throws IOException {
		_writer.write(string);
	}

	public void println() throws IOException {
		_writer.write(_LINE_SEPARATOR);
	}

	public void println(boolean b) throws IOException {
		if (b) {
			_writer.write(StringPool.TRUE);
		}
		else {
			_writer.write(StringPool.FALSE);
		}

		_writer.write(_LINE_SEPARATOR);
	}

	public void println(char c) throws IOException {
		_writer.write(c);
		_writer.write(_LINE_SEPARATOR);
	}

	public void println(char[] chars) throws IOException {
		_writer.write(chars);
		_writer.write(_LINE_SEPARATOR);
	}

	public void println(double d) throws IOException {
		_writer.write(String.valueOf(d));
		_writer.write(_LINE_SEPARATOR);
	}

	public void println(float f) throws IOException {
		_writer.write(String.valueOf(f));
		_writer.write(_LINE_SEPARATOR);
	}

	public void println(int i) throws IOException {
		_writer.write(String.valueOf(i));
		_writer.write(_LINE_SEPARATOR);
	}

	public void println(long l) throws IOException {
		_writer.write(String.valueOf(l));
		_writer.write(_LINE_SEPARATOR);
	}

	public void println(Object object) throws IOException {
		_writer.write(String.valueOf(object));
		_writer.write(_LINE_SEPARATOR);
	}

	public void println(String string) throws IOException {
		_writer.write(string);
		_writer.write(_LINE_SEPARATOR);
	}

	public void write(char[] chars) throws IOException {
		_writer.write(chars);
	}

	public void write(char[] chars, int offset, int length) throws IOException {
		_writer.write(chars, offset, length);
	}

	public void write(int c) throws IOException {
		_writer.write(c);
	}

	public void write(String string) throws IOException {
		_writer.write(string);
	}

	public void write(String string, int offset, int length)
		throws IOException {

		_writer.write(string, offset, length);
	}

	private static String _LINE_SEPARATOR = System.getProperty(
		"line.separator");

	private Writer _writer;

}