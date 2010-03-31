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
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;

/**
 * <a href="JspWriterAdapter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class JspWriterAdapter extends JspWriter {

	public JspWriterAdapter(PrintWriter printWriter) {
		super(NO_BUFFER, false);
		_printWriter = printWriter;
	}

	public JspWriterAdapter(Writer writer) {
		super(NO_BUFFER, false);
		_printWriter = new PrintWriter(writer, true);
	}

	public void write(char[] buf, int off, int len) {
		_printWriter.write(buf, off, len);
	}

	public void println(Object x) {
		_printWriter.println(x);
	}

	public void println(String x) {
		_printWriter.println(x);
	}

	public void println(char[] x) {
		_printWriter.println(x);
	}

	public void println(double x) {
		_printWriter.println(x);
	}

	public void println(float x) {
		_printWriter.println(x);
	}

	public void println(long x) {
		_printWriter.println(x);
	}

	public void println(int x) {
		_printWriter.println(x);
	}

	public void println(char x) {
		_printWriter.println(x);
	}

	public void println(boolean x) {
		_printWriter.println(x);
	}

	public void println() {
		_printWriter.println();
	}

	public void print(Object obj) {
		_printWriter.print(obj);
	}

	public void print(String s) {
		_printWriter.print(s);
	}

	public void print(char[] s) {
		_printWriter.print(s);
	}

	public void print(double d) {
		_printWriter.print(d);
	}

	public void print(float f) {
		_printWriter.print(f);
	}

	public void print(long l) {
		_printWriter.print(l);
	}

	public void print(int i) {
		_printWriter.print(i);
	}

	public void print(char c) {
		_printWriter.print(c);
	}

	public void print(boolean b) {
		_printWriter.print(b);
	}

	public void flush() {
		_printWriter.flush();
	}

	public void close() {
		_printWriter.close();
	}

	public void clear() throws IOException {
		throw new IOException("Unable to clear on a JspWriterAdapter");
	}

	public void clearBuffer() throws IOException {
	}

	public int getRemaining() {
		return 0;
	}

	public void newLine() throws IOException {
		_printWriter.println();
	}

	private PrintWriter _printWriter;

}