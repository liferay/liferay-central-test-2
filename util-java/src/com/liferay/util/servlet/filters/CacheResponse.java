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

package com.liferay.util.servlet.filters;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.util.servlet.Header;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * <a href="CacheResponse.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class CacheResponse extends HttpServletResponseWrapper {

	public CacheResponse(HttpServletResponse response, String encoding) {
		super(response);

		_encoding = encoding;
	}

	public void addDateHeader(String name, long value) {
		List<Header> values = _headers.get(name);

		if (values == null) {
			values = new ArrayList<Header>();

			_headers.put(name, values);
		}

		Header header = new Header();

		header.setType(Header.DATE_TYPE);
		header.setDateValue(value);

		values.add(header);
	}

	public void addHeader(String name, String value) {
		List<Header> values = _headers.get(name);

		if (values == null) {
			values = new ArrayList<Header>();

			_headers.put(name, values);
		}

		Header header = new Header();

		header.setType(Header.STRING_TYPE);
		header.setStringValue(value);

		values.add(header);

		if (name.equals(HttpHeaders.CONTENT_TYPE)) {
			setContentType(value);
		}
	}

	public void addIntHeader(String name, int value) {
		List<Header> values = _headers.get(name);

		if (values == null) {
			values = new ArrayList<Header>();

			_headers.put(name, values);
		}

		Header header = new Header();

		header.setType(Header.INTEGER_TYPE);
		header.setIntValue(value);

		values.add(header);
	}

	public void finishResponse() {
		try {
			if (_writer != null) {
				_writer.close();
			}
			else if (_stream != null) {
				_stream.close();
			}
		}
		catch (IOException e) {
		}
	}

	public void flushBuffer() throws IOException {
		if (_stream != null) {
			_stream.flush();
		}
	}

	public int getBufferSize() {
		return _bufferSize;
	}

	public String getContentType() {
		return _contentType;
	}

	public int getDataLength() {
		return _ubaos.size();
	}

	public String getHeader(String name) {
		List<Header> values = _headers.get(name);

		if ((values == null) || values.isEmpty()) {
			return null;
		}

		Header header = values.get(0);

		return header.toString();
	}

	public Map<String, List<Header>> getHeaders() {
		return _headers;
	}

	public ServletOutputStream getOutputStream() {
		if (_writer != null) {
			throw new IllegalStateException();
		}

		if (_stream == null) {
			_stream = createOutputStream();
		}

		return _stream;
	}

	public int getStatus() {
		return _status;
	}

	public PrintWriter getWriter() throws IOException {
		if (_writer != null) {
			return _writer;
		}

		if (_stream != null) {
			throw new IllegalStateException();
		}

		_stream = createOutputStream();

		_writer = new PrintWriter(new OutputStreamWriter(
			//_stream, _res.getCharacterEncoding()));
			_stream, _encoding));

		return _writer;
	}

	public boolean isCommitted() {
		if (_writer != null) {
			return true;
		}

		if ((_stream != null) && _stream.isClosed()) {
			return true;
		}

		return super.isCommitted();
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

	public void setDateHeader(String name, long value) {
		List<Header> values = new ArrayList<Header>();

		_headers.put(name, values);

		Header header = new Header();

		header.setType(Header.DATE_TYPE);
		header.setDateValue(value);

		values.add(header);
	}

	public void setHeader(String name, String value) {
		List<Header> values = new ArrayList<Header>();

		_headers.put(name, values);

		Header header = new Header();

		header.setType(Header.STRING_TYPE);
		header.setStringValue(value);

		values.add(header);

		if (name.equals(HttpHeaders.CONTENT_TYPE)) {
			setContentType(value);
		}
	}

	public void setIntHeader(String name, int value) {
		List<Header> values = new ArrayList<Header>();

		_headers.put(name, values);

		Header header = new Header();

		header.setType(Header.INTEGER_TYPE);
		header.setIntValue(value);

		values.add(header);
	}

	public void setStatus(int status) {
		_status = status;

		super.setStatus(status);
	}

	public byte[] unsafeGetData() {
		finishResponse();

		return _ubaos.unsafeGetByteArray();
	}

	protected CacheResponseStream createOutputStream() {
		return new CacheResponseStream(_ubaos);
	}

	private int _bufferSize;
	private String _contentType;
	private String _encoding;
	private Map<String, List<Header>> _headers =
		new HashMap<String, List<Header>>();
	private int _status = HttpServletResponse.SC_OK;
	private CacheResponseStream _stream;
	private UnsyncByteArrayOutputStream _ubaos =
		new UnsyncByteArrayOutputStream();
	private PrintWriter _writer;

}