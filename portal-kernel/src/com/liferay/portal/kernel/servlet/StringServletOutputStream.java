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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import javax.servlet.ServletOutputStream;

/**
 * <a href="StringServletOutputStream.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class StringServletOutputStream extends ServletOutputStream {

	public StringServletOutputStream(UnsyncByteArrayOutputStream ubaos) {
		_ubaos = ubaos;
	}

	public void write(byte[] b) {
		_ubaos.write(b);
	}

	public void write(byte[] b, int off, int len) {
		_ubaos.write(b, off, len);
	}

	public void write(int b) {
		_ubaos.write(b);
	}

	private UnsyncByteArrayOutputStream _ubaos = null;

}