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

package com.liferay.portal.kernel.io;

import java.io.IOException;
import java.io.Writer;

/**
 * <a href="DummyWriter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class DummyWriter extends Writer{

	public Writer append(CharSequence csq) throws IOException {
		return this;
	}

	public Writer append(CharSequence csq, int start, int end)
		throws IOException {
		return this;
	}

	public Writer append(char c) throws IOException {
		return this;
	}

	public void close() throws IOException {
	}

	public void flush() throws IOException {
	}

	public void write(int c) throws IOException {
	}

	public void write(char[] cbuf) throws IOException {
	}

	public void write(char[] cbuf, int off, int len) throws IOException {
	}

	public void write(String str) throws IOException {
	}

	public void write(String str, int off, int len) throws IOException {
	}

}