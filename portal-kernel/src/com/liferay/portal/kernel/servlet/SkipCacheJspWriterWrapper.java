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
 * <a href="SkipCacheJspWriterWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class SkipCacheJspWriterWrapper extends JspWriter {

	public SkipCacheJspWriterWrapper(JspWriter wrappedJspWriter)
		throws IOException {

		super(NO_BUFFER, true);
		_wrappedJspWriter = wrappedJspWriter;
		_wrappedJspWriter.flush();
		_disableBuffer(_wrappedJspWriter);
	}

	public Writer append(char c) throws IOException {
		return _wrappedJspWriter.append(c);
	}

	public Writer append(CharSequence csq, int start, int end)
		throws IOException {
		return _wrappedJspWriter.append(csq, start, end);
	}

	public Writer append(CharSequence csq) throws IOException {
		return _wrappedJspWriter.append(csq);
	}

	public void clear() throws IOException {
		_wrappedJspWriter.clear();
	}

	public void clearBuffer() throws IOException {
		_wrappedJspWriter.clearBuffer();
	}

	public void close() throws IOException {
		_wrappedJspWriter.close();
	}

	public void flush() throws IOException {
		_wrappedJspWriter.flush();
	}

	public int getRemaining() {
		return _wrappedJspWriter.getRemaining();
	}

	public void newLine() throws IOException {
		_wrappedJspWriter.newLine();
	}

	public void print(Object o) throws IOException {
		_wrappedJspWriter.print(o);
	}

	public void print(String string) throws IOException {
		_wrappedJspWriter.print(string);
	}

	public void print(char[] chars) throws IOException {
		_wrappedJspWriter.print(chars);
	}

	public void print(double d) throws IOException {
		_wrappedJspWriter.print(d);
	}

	public void print(float f) throws IOException {
		_wrappedJspWriter.print(f);
	}

	public void print(long l) throws IOException {
		_wrappedJspWriter.print(l);
	}

	public void print(int i) throws IOException {
		_wrappedJspWriter.print(i);
	}

	public void print(char c) throws IOException {
		_wrappedJspWriter.print(c);
	}

	public void print(boolean bln) throws IOException {
		_wrappedJspWriter.print(bln);
	}

	public void println(Object o) throws IOException {
		_wrappedJspWriter.println(o);
	}

	public void println(String string) throws IOException {
		_wrappedJspWriter.println(string);
	}

	public void println(char[] chars) throws IOException {
		_wrappedJspWriter.println(chars);
	}

	public void println(double d) throws IOException {
		_wrappedJspWriter.println(d);
	}

	public void println(float f) throws IOException {
		_wrappedJspWriter.println(f);
	}

	public void println(long l) throws IOException {
		_wrappedJspWriter.println(l);
	}

	public void println(int i) throws IOException {
		_wrappedJspWriter.println(i);
	}

	public void println(char c) throws IOException {
		_wrappedJspWriter.println(c);
	}

	public void println(boolean bln) throws IOException {
		_wrappedJspWriter.println(bln);
	}

	public void println() throws IOException {
		_wrappedJspWriter.println();
	}

	public void write(char[] cbuf, int off, int len) throws IOException {
		_wrappedJspWriter.write(cbuf, off, len);
	}

	public void write(String str, int off, int len) throws IOException {
		_wrappedJspWriter.write(str, off, len);
	}

	public void write(String str) throws IOException {
		_wrappedJspWriter.write(str);
	}

	public void write(char[] cbuf) throws IOException {
		_wrappedJspWriter.write(cbuf);
	}

	public void write(int c) throws IOException {
		_wrappedJspWriter.write(c);
	}

	public static void _disableBuffer(JspWriter jspWriter) {
		try {
			_bufferSizeField.set(jspWriter, 0);
		} catch (Exception ex) {
			throw new RuntimeException("Fail to disable buffer for " +
				jspWriter, ex);
		}
	}

	private static Field _bufferSizeField;

	static {
		try {
			_bufferSizeField = JspWriter.class.getDeclaredField("bufferSize");
			_bufferSizeField.setAccessible(true);
		} catch (Exception ex) {
			throw new ExceptionInInitializerError(
				"Fail to access bufferSize field in " +
				"javax.servlet.jsp.JspWriter, " +
				"you may run with a spoiled jsp impl.");
		}
	}

	private JspWriter _wrappedJspWriter;

}