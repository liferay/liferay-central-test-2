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

import com.liferay.portal.kernel.io.WriterOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;

import java.io.IOException;
import java.io.OutputStream;

import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jayson Falkner
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class GZipStream extends ServletOutputStream {

	public GZipStream(HttpServletResponse response) throws IOException {
		super();

		_response = response;
		_unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
		_gzipOutputStream = new GZIPOutputStream(_unsyncByteArrayOutputStream);
	}

	public void close() throws IOException {
		if (_closed) {
			throw new IOException();
		}

		_gzipOutputStream.finish();

		int contentLength = _unsyncByteArrayOutputStream.size();

		_response.setContentLength(contentLength);
		_response.addHeader(HttpHeaders.CONTENT_ENCODING, _GZIP);

		try {
			flushOutToOutputStream();
		}
		catch (IllegalStateException ise) {
			flushOutToWriter();
		}

		_closed = true;
	}

	public boolean closed() {
		return _closed;
	}

	public void flush() throws IOException {
		if (_closed) {
			throw new IOException();
		}

		_gzipOutputStream.flush();
	}

	public void reset() {
	}

	public void write(byte b[]) throws IOException {
		write(b, 0, b.length);
	}

	public void write(byte b[], int off, int len) throws IOException {
		if (_closed) {
			throw new IOException();
		}

		// LEP-649

		//_checkBufferSize(len);

		try {
			_gzipOutputStream.write(b, off, len);
		}
		catch (IOException ioe) {
			_log.warn(ioe.getMessage());
		}
	}

	public void write(int b) throws IOException {
		if (_closed) {
			throw new IOException();
		}

		// LEP-649

		//_checkBufferSize(1);

		_gzipOutputStream.write((byte)b);
	}

	private void flushOutToOutputStream() throws IOException {
		OutputStream outputStream = _response.getOutputStream();

		outputStream.write(
			_unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
			_unsyncByteArrayOutputStream.size());

		outputStream.flush();
		outputStream.close();
	}

	private void flushOutToWriter() throws IOException {
		WriterOutputStream writerOutputStream = new WriterOutputStream(
			_response.getWriter());

		writerOutputStream.write(
			_unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
			_unsyncByteArrayOutputStream.size());

		writerOutputStream.flush();
		writerOutputStream.close();
	}

	private static final String _GZIP = "gzip";

	private static Log _log = LogFactoryUtil.getLog(GZipStream.class);

	private boolean _closed;
	private GZIPOutputStream _gzipOutputStream;
	private HttpServletResponse _response;
	private UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream;

}