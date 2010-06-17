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

package com.liferay.portal.kernel.io.unsync;

import java.io.IOException;
import java.io.Reader;

import java.nio.CharBuffer;

/**
 * <a href="UnsyncCharArrayReader.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class UnsyncCharArrayReader extends Reader {

	public UnsyncCharArrayReader(char[] buffer) {
		this.buffer = buffer;
		this.index = 0;
		this.capacity = buffer.length;
	}

	public UnsyncCharArrayReader(char[] buffer, int offset, int length) {
		this.buffer = buffer;
		this.index = offset;
		this.capacity = Math.min(buffer.length, offset + length);
		this.markIndex = offset;
	}

	public void close() throws IOException {
		buffer = null;
	}

	public void mark(int readAheadLimit) throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}
		markIndex = index;
	}

	public boolean markSupported() {
		return true;
	}

	public int read() throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}
		if (index >= capacity) {
			return -1;
		} else {
			return buffer[index++];
		}
	}

	public int read(char[] cbuf) throws IOException {
		return read(cbuf, 0, cbuf.length);
	}

	public int read(char[] charArray, int offset, int length)
			throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}
		if (length <= 0) {
			return 0;
		}

		if (index >= capacity) {
			return -1;
		}

		int read = length;

		if ((index + read) > capacity) {
			read = capacity - index;
		}

		System.arraycopy(buffer, index, charArray, offset, read);

		index += read;

		return read;
	}

	public int read(CharBuffer target) throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}
		int length = target.remaining();

		if (length <= 0) {
			return 0;
		}

		if (index >= capacity) {
			return -1;
		}

		if ((index + length) > capacity) {
			length = capacity - index;
		}

		target.put(buffer, index, length);

		index += length;

		return length;
	}

	public boolean ready() throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}
		return capacity > index;
	}

	public void reset() throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}
		index = markIndex;
	}

	public long skip(long n) throws IOException {
		if (buffer == null) {
			throw new IOException("Stream closed");
		}
		if (n < 0) {
			return 0;
		}
		if (index + n > capacity) {
			n = capacity - index;
		}
		index += n;
		return n;
	}

	protected char[] buffer;
	protected int capacity;
	protected int index;
	protected int markIndex;

}