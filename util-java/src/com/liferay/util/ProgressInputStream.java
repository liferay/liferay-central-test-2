/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.util;

import com.liferay.util.servlet.fileupload.LiferayFileUpload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.PortletSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ProgressInputStream.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Jorge Ferrer
 *
 */
public class ProgressInputStream extends InputStream {

	public ProgressInputStream(
			ActionRequest req, InputStream is, long totalSize,
			String progressId)
			throws IOException {
		_is = is;
		_progressId = progressId;

		_ses = req.getPortletSession();
		_totalSize = totalSize;

	}

	public int available() throws IOException {
		return _is.available();
	}

	public void clearProgress() {
		_ses.removeAttribute(_getPercentAttributeName());
	}

	public void close() throws IOException {
		_is.close();
	}

	public void mark(int readlimit) {
		_is.mark(readlimit);
	}

	public boolean markSupported() {
		return _is.markSupported();
	}

	public int read() throws IOException {
		int byteRead = _is.read();
		//_updateProgress((byteRead == -1)?0:1);
		// WARN: not counting if this method is called directly
		return byteRead;
	}

	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	public void reset() throws IOException {
		_is.reset();
	}

	public long skip(long n) throws IOException {
		long result = _is.skip(n);
		_updateProgress(result);
		return result;
	}

	public int read(byte[] b, int off, int len) throws IOException {
		int bytesRead = super.read(b, off, len);
		_updateProgress(bytesRead);
		return bytesRead;
	}

	public byte[] readAll() throws IOException{
		ByteArrayOutputStream outstream = new ByteArrayOutputStream(
				_totalSize > 0 ? (int) _totalSize:_DEFAULT_INITIAL_BUFFER_SIZE);

		byte[] buffer = new byte[_DEFAULT_INITIAL_BUFFER_SIZE];
		int len;
		while ((len = read(buffer)) > 0) {
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		return outstream.toByteArray();
	}

	private String _getPercentAttributeName() {
		return LiferayFileUpload.PERCENT + _progressId;
	}

	private void _updateProgress(long bytesRead) {

		if (bytesRead > 0) {
			_totalRead += bytesRead;
		}
		else {
			_totalRead = _totalSize;
		}

		int percent = (int) ((_totalRead * 100) / _totalSize);

		if (_log.isDebugEnabled()) {
			_log.debug(bytesRead + "/" + _totalRead + "=" + percent);
		}

		Integer curPercent = (Integer)_ses.getAttribute(
				_getPercentAttributeName(), PortletSession.APPLICATION_SCOPE);

		if ((curPercent == null) || (percent - curPercent.intValue() >= 1)) {
			_ses.setAttribute(
					_getPercentAttributeName(), new Integer(percent),
					PortletSession.APPLICATION_SCOPE);
		}
	}

	private static Log _log = LogFactory.getLog(ProgressInputStream.class);

	private InputStream _is;
	private String _progressId;
	private PortletSession _ses;
	private long _totalRead;
	private long _totalSize;

	private static final int _DEFAULT_INITIAL_BUFFER_SIZE = 4*1024;

}