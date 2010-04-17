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

package com.liferay.portal.servlet.filters.strip;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.servlet.PipingServletOutputStream;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * <a href="StripResponse.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class StripResponse extends HttpServletResponseWrapper {

	public StripResponse(HttpServletResponse response) {
		super(response);
	}

	public void finishResponse() {
		try {
			if (_printWriter != null) {
				_printWriter.close();
			}
			else if (_servletOutputStream != null) {
				_servletOutputStream.close();
			}
		}
		catch (IOException ioe) {
		}
	}

	public void flushBuffer() throws IOException {
		if (_servletOutputStream != null) {
			_servletOutputStream.flush();
		}
	}

	public String getContentType() {
		return _contentType;
	}

	public byte[] getData() {
		finishResponse();

		if (_unsyncByteArrayOutputStream != null) {
			return _unsyncByteArrayOutputStream.toByteArray();
		}

		return null;
	}

	public ServletOutputStream getOutputStream() {
		if (_printWriter != null) {
			throw new IllegalStateException();
		}

		if (_servletOutputStream == null) {
			_servletOutputStream = createOutputStream();
		}

		return _servletOutputStream;
	}

	public PrintWriter getWriter() throws IOException {
		if (_printWriter != null) {
			return _printWriter;
		}

		if (_servletOutputStream != null) {
			throw new IllegalStateException();
		}

		_servletOutputStream = createOutputStream();

		_printWriter = new PrintWriter(
			new OutputStreamWriter(_servletOutputStream, StringPool.UTF8));

		return _printWriter;
	}

	public boolean isCommitted() {
		if (_servletOutputStream != null) {
			return true;
		}
		else {
			return super.isCommitted();
		}
	}

	public void setContentType(String contentType) {
		_contentType = contentType;

		super.setContentType(contentType);
	}

	protected ServletOutputStream createOutputStream() {
		_unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();

		return new PipingServletOutputStream(_unsyncByteArrayOutputStream);
	}

	private String _contentType;
	private PrintWriter _printWriter;
	private ServletOutputStream _servletOutputStream;
	private UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream;

}