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
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author Shuyang Zhou
 */
public class BodyContentWrapper extends BodyContent {

	public BodyContentWrapper(
		BodyContent bodyContent, UnsyncStringWriter unsyncStringWriter) {

		super(bodyContent.getEnclosingWriter());

		_bodyContent = bodyContent;
		_unsyncStringWriter = unsyncStringWriter;
	}

	public Writer append(char c) throws IOException {
		return _bodyContent.append(c);
	}

	public Writer append(CharSequence charSequence) throws IOException {
		return _bodyContent.append(charSequence);
	}

	public Writer append(CharSequence charSequence, int start, int end)
		throws IOException {

		return _bodyContent.append(charSequence, start, end);
	}

	public void clear() throws IOException {
		_bodyContent.clear();
	}

	public void clearBody() {
		_unsyncStringWriter.reset();
	}

	public void clearBuffer() {
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

	public StringBundler getStringBundler() {
		return _unsyncStringWriter.getStringBundler();
	}

	public boolean isAutoFlush() {
		return _bodyContent.isAutoFlush();
	}

	public void newLine() throws IOException {
		_bodyContent.newLine();
	}

	public void print(boolean b) throws IOException {
		_bodyContent.print(b);
	}

	public void print(char c) throws IOException {
		_bodyContent.print(c);
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

	public void print(int i) throws IOException {
		_bodyContent.print(i);
	}

	public void print(long l) throws IOException {
		_bodyContent.print(l);
	}

	public void print(Object object) throws IOException {
		_bodyContent.print(object);
	}

	public void print(String string) throws IOException {
		_bodyContent.print(string);
	}

	public void println() throws IOException {
		_bodyContent.println();
	}

	public void println(boolean b) throws IOException {
		_bodyContent.println(b);
	}

	public void println(char c) throws IOException {
		_bodyContent.println(c);
	}

	public void println(char[] charArray) throws IOException {
		_bodyContent.println(charArray);
	}

	public void println(double d) throws IOException {
		_bodyContent.println(d);
	}

	public void println(float f) throws IOException {
		_bodyContent.println(f);
	}

	public void println(int i) throws IOException {
		_bodyContent.println(i);
	}

	public void println(long l) throws IOException {
		_bodyContent.println(l);
	}

	public void println(Object object) throws IOException {
		_bodyContent.println(object);
	}

	public void println(String string) throws IOException {
		_bodyContent.println(string);
	}

	public void write(char[] charArray) throws IOException {
		_bodyContent.write(charArray);
	}

	public void write(char[] charArray, int offset, int length)
		throws IOException {

		_bodyContent.write(charArray, offset, length);
	}

	public void write(int c) throws IOException {
		_bodyContent.write(c);
	}

	public void write(String string) throws IOException {
		_bodyContent.write(string);
	}

	public void write(String string, int offset, int length)
		throws IOException {

		_bodyContent.write(string, offset, length);
	}

	public void writeOut(Writer writer) throws IOException {
		_bodyContent.writeOut(writer);
	}

	private BodyContent _bodyContent;
	private UnsyncStringWriter _unsyncStringWriter;

}