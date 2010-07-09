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

package com.liferay.util.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class GenericServletOutputStream extends ServletOutputStream {

	public GenericServletOutputStream(OutputStream os) {
		_os = os;
	}

	public void write(int b) throws IOException {
		_os.write(b);
	}

	public void write(byte[] b) throws IOException {
		_os.write(b);
	}

	public void write(byte[] b, int off, int len) throws IOException {
		_os.write(b, off, len);
	}

	private OutputStream _os;

}