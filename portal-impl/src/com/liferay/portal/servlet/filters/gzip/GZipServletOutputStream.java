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

package com.liferay.portal.servlet.filters.gzip;

import java.io.IOException;
import java.io.OutputStream;

import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;

/**
 * @author Shuyang Zhou
 */
public class GZipServletOutputStream extends ServletOutputStream {

	public GZipServletOutputStream(OutputStream outputStream)
		throws IOException {
		_gzipOutputStream = new GZIPOutputStream(outputStream);
	}

	public void close() throws IOException {
		_gzipOutputStream.close();
	}

	public void flush() throws IOException {
		_gzipOutputStream.flush();
	}

	public void write(byte[] b) throws IOException {
		_gzipOutputStream.write(b);
	}

	public void write(byte[] b, int off, int len) throws IOException {
		_gzipOutputStream.write(b, off, len);
	}

	public void write(int b) throws IOException {
		_gzipOutputStream.write(b);
	}

	private GZIPOutputStream _gzipOutputStream;

}