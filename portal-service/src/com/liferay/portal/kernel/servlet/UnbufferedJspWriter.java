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

import java.io.IOException;
import java.io.Writer;

import java.lang.reflect.Field;

import javax.servlet.jsp.JspWriter;

/**
 * <a href="UnbufferedJspWriter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class UnbufferedJspWriter extends JspWriter {

	public UnbufferedJspWriter(JspWriter jspWriter) throws IOException {
		super(NO_BUFFER, true);

		_jspWriter = jspWriter;

		jspWriter.flush();

		_disableBuffer(jspWriter);
	}

	public Writer append(char c) throws IOException {
		return _jspWriter.append(c);
	}

	public Writer append(CharSequence charSequence) throws IOException {
		return _jspWriter.append(charSequence);
	}

	public Writer append(CharSequence charSequence, int start, int end)
		throws IOException {
		return _jspWriter.append(charSequence, start, end);
	}

	public void clear() throws IOException {
		_jspWriter.clear();
	}

	public void clearBuffer() throws IOException {
		_jspWriter.clearBuffer();
	}

	public void close() throws IOException {
		_jspWriter.close();
	}

	public void flush() throws IOException {
		_jspWriter.flush();
	}

	public int getRemaining() {
		return _jspWriter.getRemaining();
	}

	public void newLine() throws IOException {
		_jspWriter.newLine();
	}

	public void print(boolean bln) throws IOException {
		_jspWriter.print(bln);
	}

	public void print(char c) throws IOException {
		_jspWriter.print(c);
	}

	public void print(char[] charArray) throws IOException {
		_jspWriter.print(charArray);
	}

	public void print(double d) throws IOException {
		_jspWriter.print(d);
	}

	public void print(float f) throws IOException {
		_jspWriter.print(f);
	}

	public void print(int i) throws IOException {
		_jspWriter.print(i);
	}

	public void print(long l) throws IOException {
		_jspWriter.print(l);
	}

	public void print(Object object) throws IOException {
		_jspWriter.print(object);
	}

	public void print(String string) throws IOException {
		_jspWriter.print(string);
	}

	public void println() throws IOException {
		_jspWriter.println();
	}

	public void println(boolean b) throws IOException {
		_jspWriter.println(b);
	}

	public void println(char c) throws IOException {
		_jspWriter.println(c);
	}

	public void println(char[] charArray) throws IOException {
		_jspWriter.println(charArray);
	}

	public void println(double d) throws IOException {
		_jspWriter.println(d);
	}

	public void println(float f) throws IOException {
		_jspWriter.println(f);
	}

	public void println(int i) throws IOException {
		_jspWriter.println(i);
	}

	public void println(long l) throws IOException {
		_jspWriter.println(l);
	}

	public void println(Object object) throws IOException {
		_jspWriter.println(object);
	}

	public void println(String string) throws IOException {
		_jspWriter.println(string);
	}

	public void write(char[] charArray) throws IOException {
		_jspWriter.write(charArray);
	}

	public void write(char[] charArray, int offset, int length)
		throws IOException {

		_jspWriter.write(charArray, offset, length);
	}

	public void write(int c) throws IOException {
		_jspWriter.write(c);
	}

	public void write(String string) throws IOException {
		_jspWriter.write(string);
	}

	public void write(String string, int offset, int length)
		throws IOException {

		_jspWriter.write(string, offset, length);
	}

	public static void _disableBuffer(JspWriter jspWriter) {
		try {
			_bufferSizeField.set(jspWriter, 0);
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to disable buffer for " + jspWriter, e);
		}
	}

	private static Field _bufferSizeField;

	static {
		try {
			_bufferSizeField = JspWriter.class.getDeclaredField("bufferSize");

			_bufferSizeField.setAccessible(true);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(
				"Unable to access bufferSize field in " +
					"javax.servlet.jsp.JspWriter");
		}
	}

	private JspWriter _jspWriter;

}