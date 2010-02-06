/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.servlet.filters.strip;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.servlet.StringServletOutputStream;
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

		return new StringServletOutputStream(_unsyncByteArrayOutputStream);
	}

	private String _contentType;
	private PrintWriter _printWriter;
	private ServletOutputStream _servletOutputStream;
	private UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream;

}