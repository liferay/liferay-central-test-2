/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * <a href="StringServletResponse.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class StringServletResponse extends HttpServletResponseWrapper {

	public StringServletResponse(HttpServletResponse response) {
		super(response);
	}

	public int getBufferSize() {
		return _bufferSize;
	}

	public ByteArrayOutputStream getByteArrayOutputStream() {
		return _baos;
	}

	public String getContentType() {
		return _contentType;
	}

	public ServletOutputStream getOutputStream() {
		/*if (_callGetWriter) {
			throw new IllegalStateException();
		}*/

		_callGetOutputStream = true;

		return _sos;
	}

	public int getStatus() {
		return _status;
	}

	public String getString() {
		if (_string != null) {
			return _string;
		}
		else if (_callGetOutputStream) {
			try {
				return _baos.toString(StringPool.UTF8);
			}
			catch (UnsupportedEncodingException uee) {
				_log.error(uee, uee);

				return StringPool.BLANK;
			}
		}
		else if (_callGetWriter) {
			return _sw.toString();
		}
		else {
			return StringPool.BLANK;
		}
	}

	public PrintWriter getWriter() {
		/*if (_callGetOutputStream) {
			throw new IllegalStateException();
		}*/

		_callGetWriter = true;

		return _pw;
	}

	public boolean isCalledGetOutputStream() {
		return _callGetOutputStream;
	}

	public void recycle() {
		_baos.reset();
		//_sos = new StringServletOutputStream(_bam);
		_status = SC_OK;
		_sw = new StringWriter();
		_pw = new PrintWriter(_sw);
		_callGetOutputStream = false;
		_callGetWriter = false;
		_string = null;
	}

	public void resetBuffer() {
		if (_callGetOutputStream) {
			_baos.reset();
		}
		else if (_callGetWriter) {
			StringBuffer sb = _sw.getBuffer();

			sb.delete(0, sb.length());
		}
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
	}

	public void setString(String string) {
		_string = string;
	}

	private static Log _log =
		LogFactoryUtil.getLog(StringServletResponse.class);

	private String _contentType;
	private int _status = SC_OK;
	private ByteArrayOutputStream _baos = new ByteArrayOutputStream();
	private ServletOutputStream _sos = new StringServletOutputStream(_baos);
	private StringWriter _sw = new StringWriter();
	private PrintWriter _pw = new PrintWriter(_sw);
	private int _bufferSize;
	private boolean _callGetOutputStream;
	private boolean _callGetWriter;
	private String _string = null;

}