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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.IOException;
import java.io.OutputStream;

import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;

/**
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class GZipServletOutputStream extends ServletOutputStream {

	public static final int COMPRESSION_LEVEL = GetterUtil.getInteger(
		PropsUtil.get(GZipFilter.class.getName().concat(".compression.level")));

	public GZipServletOutputStream(OutputStream outputStream)
		throws IOException {

		_gZipOutputStream = new GZIPOutputStream(outputStream) {
			{
				def.setLevel(COMPRESSION_LEVEL);
			}
		};
	}

	public void close() throws IOException {
		_gZipOutputStream.close();
	}

	public void flush() throws IOException {
		_gZipOutputStream.flush();
	}

	public void write(byte[] bytes) throws IOException {
		_gZipOutputStream.write(bytes);
	}

	public void write(byte[] bytes, int offset, int length)
		throws IOException {

		_gZipOutputStream.write(bytes, offset, length);
	}

	public void write(int b) throws IOException {
		_gZipOutputStream.write(b);
	}

	private GZIPOutputStream _gZipOutputStream;

}