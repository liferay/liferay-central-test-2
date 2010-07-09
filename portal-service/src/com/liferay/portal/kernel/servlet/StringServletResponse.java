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
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class StringServletResponse extends HeaderCacheServletResponse {

	public StringServletResponse(HttpServletResponse response) {
		super(response);
	}

	public int getBufferSize() {
		return _bufferSize;
	}

	public String getContentType() {
		return _contentType;
	}

	public void flushBuffer() throws IOException {
		if (_servletOutputStream != null) {
			_unsyncByteArrayOutputStream.flush();
		}
		else if (_printWriter != null) {
			_unsyncStringWriter.flush();
		}
	}

	public ServletOutputStream getOutputStream() {
		if (_printWriter != null) {
			throw new IllegalStateException(
				"Cannot obtain OutputStream because Writer is already in use");
		}

		if (_servletOutputStream == null) {
			_unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
			_servletOutputStream = new PipingServletOutputStream(
				_unsyncByteArrayOutputStream);
		}

		return _servletOutputStream;
	}

	public int getStatus() {
		return _status;
	}

	public String getString() {
		if (_string != null) {
			return _string;
		}

		if (_servletOutputStream != null) {
			try {
				_string = _unsyncByteArrayOutputStream.toString(
					StringPool.UTF8);
			}
			catch (UnsupportedEncodingException uee) {
				_log.error(uee, uee);

				_string = StringPool.BLANK;
			}
		}
		else if (_printWriter != null) {
			_string = _unsyncStringWriter.toString();
		}
		else {
			_string = StringPool.BLANK;
		}

		return _string;
	}

	public UnsyncByteArrayOutputStream getUnsyncByteArrayOutputStream() {
		return _unsyncByteArrayOutputStream;
	}

	public PrintWriter getWriter() {
		if (_servletOutputStream != null) {
			throw new IllegalStateException(
				"Cannot obtain Writer because OutputStream is already in use");
		}

		if (_printWriter == null) {
			_unsyncStringWriter = new UnsyncStringWriter();
			_printWriter = new UnsyncPrintWriter(_unsyncStringWriter);
		}

		return _printWriter;
	}

	public boolean isCalledGetOutputStream() {
		if (_servletOutputStream != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public void recycle() {
		_status = SC_OK;
		_string = null;

		resetBuffer();
	}

	public void resetBuffer() {
		if (_servletOutputStream != null) {
			_unsyncByteArrayOutputStream.reset();
		}
		else if (_printWriter != null) {
			_unsyncStringWriter.reset();
		}
	}

	public void sendError(int status) throws IOException {
		_status = status;

		super.sendError(status);
	}

	public void sendError(int status, String msg) throws IOException {
		_status = status;

		super.sendError(status, msg);
	}

	public void setBufferSize(int bufferSize) {
		_bufferSize = bufferSize;
	}

	public void setContentType(String contentType) {
		_contentType = contentType;

		super.setContentType(contentType);
	}

	public void setLocale(Locale locale) {
	}

	public void setStatus(int status) {
		_status = status;

		super.setStatus(_status);
	}

	public void setString(String string) {
		_string = string;
	}

	private static Log _log = LogFactoryUtil.getLog(
		StringServletResponse.class);

	private int _bufferSize;
	private String _contentType;
	private PrintWriter _printWriter;
	private ServletOutputStream _servletOutputStream;
	private int _status = SC_OK;
	private String _string;
	private UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream;
	private UnsyncStringWriter _unsyncStringWriter;

}