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

package com.liferay.portal.servlet.filters.gzip;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="GZipStream.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jayson Falkner
 * @author Brian Wing Shun Chan
 */
public class GZipStream extends ServletOutputStream {

	public GZipStream(HttpServletResponse response) throws IOException {
		super();

		_response = response;
		_output = response.getOutputStream();
		_bufferedOutput = new ByteArrayOutputStream();
		_closed = false;
	}

	public void close() throws IOException {
		if (_closed) {
			throw new IOException();
		}

		ByteArrayOutputStream baos = (ByteArrayOutputStream)_bufferedOutput;

		if (baos.size() > 20) {
			ByteArrayOutputStream compressedContent =
				new ByteArrayOutputStream();

			GZIPOutputStream gzipOutput = new GZIPOutputStream(
				compressedContent);

			gzipOutput.write(baos.toByteArray());
			gzipOutput.finish();

			byte[] compressedBytes = compressedContent.toByteArray();

			_response.setContentLength(compressedBytes.length);
			_response.addHeader(HttpHeaders.CONTENT_ENCODING, _GZIP);

			_output.write(compressedBytes);
		}
		else {
			_response.setContentLength(baos.size());

			_output.write(baos.toByteArray());
		}

		_output.flush();
		_output.close();

		_closed = true;
	}

	public void flush() throws IOException {
		if (_closed) {
			throw new IOException();
		}

		_bufferedOutput.flush();
	}

	public void write(int b) throws IOException {
		if (_closed) {
			throw new IOException();
		}

		// LEP-649

		//_checkBufferSize(1);

		_bufferedOutput.write((byte)b);
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
			_bufferedOutput.write(b, off, len);
		}
		catch (IOException ioe) {
			_log.warn(ioe.getMessage());
		}
	}

	public boolean closed() {
		return _closed;
	}

	public void reset() {
	}

	private static final String _GZIP = "gzip";

	private static Log _log = LogFactoryUtil.getLog(GZipStream.class);

	private HttpServletResponse _response = null;
	private ServletOutputStream _output = null;
	private OutputStream _bufferedOutput = null;
	private boolean _closed = false;

}