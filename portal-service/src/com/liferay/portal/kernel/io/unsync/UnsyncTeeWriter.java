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

package com.liferay.portal.kernel.io.unsync;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Shuyang Zhou
 */
public class UnsyncTeeWriter extends Writer {

	public UnsyncTeeWriter(Writer writer1, Writer writer2) {
		_writer1 = writer1;
		_writer2 = writer2;
	}

	public Writer append(char c) throws IOException {
		_writer1.append(c);
		_writer2.append(c);

		return this;
	}

	public Writer append(CharSequence charSequence) throws IOException {
		_writer1.append(charSequence);
		_writer2.append(charSequence);

		return this;
	}

	public Writer append(CharSequence charSequence, int start, int end)
		throws IOException {

		_writer1.append(charSequence, start, end);
		_writer2.append(charSequence, start, end);

		return this;
	}

	public void close() throws IOException {
		_writer1.close();
		_writer2.close();
	}

	public void flush() throws IOException {
		_writer1.flush();
		_writer2.flush();
	}

	public void write(char[] chars) throws IOException {
		_writer1.write(chars);
		_writer2.write(chars);
	}

	public void write(char[] chars, int offset, int length) throws IOException {
		_writer1.write(chars, offset, length);
		_writer2.write(chars, offset, length);
	}

	public void write(int c) throws IOException {
		_writer1.write(c);
		_writer2.write(c);
	}

	public void write(String string) throws IOException {
		_writer1.write(string);
		_writer2.write(string);
	}

	public void write(String string, int offset, int length)
		throws IOException {

		_writer1.write(string, offset, length);
		_writer2.write(string, offset, length);
	}

	private Writer _writer1;
	private Writer _writer2;

}