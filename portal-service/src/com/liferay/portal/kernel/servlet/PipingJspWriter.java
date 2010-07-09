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

import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;

/**
 * @author Shuyang Zhou
 */
public class PipingJspWriter extends JspWriter {

	public PipingJspWriter(PrintWriter printWriter) {
		super(NO_BUFFER, false);

		_printWriter = printWriter;
	}

	public PipingJspWriter(Writer writer) {
		super(NO_BUFFER, false);

		_printWriter = new UnsyncPrintWriter(writer, true);
	}

	public void clear() throws IOException {
		throw new IOException();
	}

	public void clearBuffer() {
	}

	public void close() {
		_printWriter.close();
	}

	public void flush() {
		_printWriter.flush();
	}

	public int getRemaining() {
		return 0;
	}

	public void newLine() {
		_printWriter.println();
	}

	public void print(boolean b) {
		_printWriter.print(b);
	}

	public void print(char c) {
		_printWriter.print(c);
	}

	public void print(char[] charArray) {
		_printWriter.print(charArray);
	}

	public void print(double d) {
		_printWriter.print(d);
	}

	public void print(float f) {
		_printWriter.print(f);
	}

	public void print(int i) {
		_printWriter.print(i);
	}

	public void print(long l) {
		_printWriter.print(l);
	}

	public void print(Object object) {
		_printWriter.print(object);
	}

	public void print(String string) {
		_printWriter.print(string);
	}

	public void println() {
		_printWriter.println();
	}

	public void println(boolean b) {
		_printWriter.println(b);
	}

	public void println(char c) {
		_printWriter.println(c);
	}

	public void println(char[] charArray) {
		_printWriter.println(charArray);
	}

	public void println(double d) {
		_printWriter.println(d);
	}

	public void println(float f) {
		_printWriter.println(f);
	}

	public void println(int i) {
		_printWriter.println(i);
	}

	public void println(long l) {
		_printWriter.println(l);
	}

	public void println(Object object) {
		_printWriter.println(object);
	}

	public void println(String string) {
		_printWriter.println(string);
	}

	public void write(char[] charArray) {
		_printWriter.write(charArray);
	}

	public void write(char[] charArray, int offset, int length) {
		_printWriter.write(charArray, offset, length);
	}

	public void write(int c) {
		_printWriter.write(c);
	}

	public void write(String string) {
		_printWriter.write(string);
	}

	public void write(String string, int offset, int length) {
		_printWriter.write(string, offset, length);
	}

	private PrintWriter _printWriter;

}