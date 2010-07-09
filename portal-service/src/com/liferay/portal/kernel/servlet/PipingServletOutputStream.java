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
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

/**
 * @author Shuyang Zhou
 */
public class PipingServletOutputStream extends ServletOutputStream {

	public PipingServletOutputStream(OutputStream outputStream) {
		_outputStream = outputStream;
	}

	public void close() throws IOException {
		super.close();

		_closed = true;
	}

	public boolean isClosed() {
		return _closed;
	}

	public void write(byte[] byteArray) throws IOException {
		_outputStream.write(byteArray);
	}

	public void write(byte[] byteArray, int offset, int length)
		throws IOException {

		_outputStream.write(byteArray, offset, length);
	}

	public void write(int i) throws IOException {
		_outputStream.write(i);
	}

	private boolean _closed;
	private OutputStream _outputStream;

}