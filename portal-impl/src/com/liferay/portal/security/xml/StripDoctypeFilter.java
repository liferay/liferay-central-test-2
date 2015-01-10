/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.xml;

import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * @author Tomas Polesovsky
 */
public class StripDoctypeFilter {

	public StripDoctypeFilter(InputStream inputStream) {
		this._inputStream = inputStream;
	}

	public StripDoctypeFilter(Reader reader) {
		this._reader = reader;
	}

	public int read() throws IOException {
		if (!bufferEmpty()) {
			return readFromBuffer();
		}

		int first = readFromSource();

		if (_documentStarted) {
			return first;
		}

		if (first == '<') {
			int[] next = new int[2];
			next[0] = readFromSource();
			next[1] = readFromSource();

			if (next[0] == '?') {
				setBuffer(next);
				return first;
			}

			if ((next[0] == '!') && (next[1] == '-')) {
				setBuffer(next);
				return first;
			}

			if ((next[0] == '!') && (next[1] == 'D')) {
				while (true) {
					int doctypeContent = readFromSource();

					switch (doctypeContent) {
						case '[': {
							entityDeclaration = true;
							break;
						}

						case ']': {
							entityDeclaration = false;
							break;
						}

						case '>': {
							if (!entityDeclaration) {
								_documentStarted = true;
								return readFromSource();
							}
						}
					}
				}
			}

			setBuffer(next);
			_documentStarted = true;
		}

		return first;
	}

	public int read(char[] cbuf, int off, int len) throws IOException {
		int read;

		for (read = 0; read < len; read++) {
			int c = read();

			if (c == -1) {
				if (read == 0) {
					return -1;
				}

				return read;
			}

			cbuf[off + read] = (char)c;
		}

		return read;
	}

	public int read(byte[] buffer, int off, int len) throws IOException {
		int read;

		for (read = 0; read < len; read++) {
			int c = read();

			if (c == -1) {
				if (read == 0) {
					return -1;
				}

				return read;
			}

			buffer[off + read] = (byte) (c & 0xFF);
		}

		return read;
	}

	protected int readFromSource() throws IOException {
		if (_inputStream != null) {
			return _inputStream.read();
		}

		if (_reader != null) {
			return _reader.read();
		}

		throw new IllegalStateException("No underlying source available");
	}

	protected boolean bufferEmpty() {
		return _bufferLen == 0;
	}

	protected int readFromBuffer() {
		_bufferLen--;
		return _buffer[_bufferLen];
	}

	protected void setBuffer(int[] buff) {
		_buffer = buff;
		ArrayUtil.reverse(_buffer);
		_bufferLen = _buffer.length;
	}

	private boolean _documentStarted;
	private InputStream _inputStream;
	private Reader _reader;
	boolean entityDeclaration = false;
	private int[] _buffer;
	private int _bufferLen = 0;

}