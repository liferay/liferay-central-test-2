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

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * <a href="BodyContentWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class BodyContentWrapper extends BodyContent {

	public BodyContentWrapper(
		BodyContent bodyContent, UnsyncStringWriter unsyncStringWriter) {

		super(bodyContent.getEnclosingWriter());
		_unsyncStringWriter = unsyncStringWriter;
		_bodyContent = bodyContent;
	}

	public Writer append(char c) throws IOException {
		return _bodyContent.append(c);
	}

	public Writer append(CharSequence csq) throws IOException {
		return _bodyContent.append(csq);
	}

	public Writer append(CharSequence csq, int start, int end)
		throws IOException {
		return _bodyContent.append(csq, start, end);
	}

	public void clear() throws IOException {
		_bodyContent.clear();
	}

	public void clearBody() {
		_unsyncStringWriter.reset();
	}

	public void clearBuffer() throws IOException {
		_unsyncStringWriter.reset();
	}

	public void close() throws IOException {
		_bodyContent.close();
	}

	public void flush() throws IOException {
		_bodyContent.flush();
	}

	public int getBufferSize() {
		return _bodyContent.getBufferSize();
	}

	public JspWriter getEnclosingWriter() {
		return _bodyContent.getEnclosingWriter();
	}

	public Reader getReader() {
		return _bodyContent.getReader();
	}

	public int getRemaining() {
		return _bodyContent.getRemaining();
	}

	public String getString() {
		return _unsyncStringWriter.toString();
	}

	public boolean isAutoFlush() {
		return _bodyContent.isAutoFlush();
	}

	public void newLine() throws IOException {
		_bodyContent.newLine();
	}

	public void print(Object o) throws IOException {
		_bodyContent.print(o);
	}

	public void print(String string) throws IOException {
		_bodyContent.print(string);
	}

	public void print(char[] chars) throws IOException {
		_bodyContent.print(chars);
	}

	public void print(double d) throws IOException {
		_bodyContent.print(d);
	}

	public void print(float f) throws IOException {
		_bodyContent.print(f);
	}

	public void print(long l) throws IOException {
		_bodyContent.print(l);
	}

	public void print(int i) throws IOException {
		_bodyContent.print(i);
	}

	public void print(char c) throws IOException {
		_bodyContent.print(c);
	}

	public void print(boolean bln) throws IOException {
		_bodyContent.print(bln);
	}

	public void println(Object o) throws IOException {
		_bodyContent.println(o);
	}

	public void println(String string) throws IOException {
		_bodyContent.println(string);
	}

	public void println(char[] chars) throws IOException {
		_bodyContent.println(chars);
	}

	public void println(double d) throws IOException {
		_bodyContent.println(d);
	}

	public void println(float f) throws IOException {
		_bodyContent.println(f);
	}

	public void println(long l) throws IOException {
		_bodyContent.println(l);
	}

	public void println(int i) throws IOException {
		_bodyContent.println(i);
	}

	public void println(char c) throws IOException {
		_bodyContent.println(c);
	}

	public void println(boolean bln) throws IOException {
		_bodyContent.println(bln);
	}

	public void println() throws IOException {
		_bodyContent.println();
	}

	public void write(String str, int off, int len) throws IOException {
		_bodyContent.write(str, off, len);
	}

	public void write(String str) throws IOException {
		_bodyContent.write(str);
	}

	public void write(char[] cbuf, int off, int len) throws IOException {
		_bodyContent.write(cbuf, off, len);
	}

	public void write(char[] cbuf) throws IOException {
		_bodyContent.write(cbuf);
	}

	public void write(int c) throws IOException {
		_bodyContent.write(c);
	}

	public void writeOut(Writer writer) throws IOException {
		_bodyContent.writeOut(writer);
	}

	private UnsyncStringWriter _unsyncStringWriter;
	private BodyContent _bodyContent;

}