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

package com.liferay.portal.upload;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.portlet.ActionRequest;
import javax.portlet.PortletSession;

/**
 * @author Jorge Ferrer
 */
public class ProgressInputStream extends InputStream {

	public ProgressInputStream(
		ActionRequest actionRequest, InputStream is, long totalSize,
		String progressId) {

		_portletSession = actionRequest.getPortletSession();
		_is = is;
		_totalSize = totalSize;
		_progressId = progressId;

		initProgress();
	}

	public int available() throws IOException {
		return _is.available();
	}

	public void clearProgress() {
		_portletSession.removeAttribute(_getPercentAttributeName());
	}

	public void close() throws IOException {
		_is.close();
	}

	public long getTotalRead() {
		return _totalRead;
	}

	public void initProgress() {
		_portletSession.setAttribute(
			_getPercentAttributeName(), new Integer(0),
			PortletSession.APPLICATION_SCOPE);
	}

	public void mark(int readlimit) {
		_is.mark(readlimit);
	}

	public boolean markSupported() {
		return _is.markSupported();
	}

	public int read() throws IOException {
		return _is.read();
	}

	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	public int read(byte[] b, int off, int len) throws IOException {
		int bytesRead = super.read(b, off, len);

		_updateProgress(bytesRead);

		return bytesRead;
	}

	public void readAll(OutputStream os) throws IOException {
		byte[] buffer = new byte[_DEFAULT_INITIAL_BUFFER_SIZE];

		int len = 0;

		while ((len = read(buffer)) > 0) {
			os.write(buffer, 0, len);
		}

		os.close();
	}

	public void reset() throws IOException {
		_is.reset();
	}

	public long skip(long n) throws IOException {
		long result = _is.skip(n);

		_updateProgress(result);

		return result;
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

		Integer curPercent = (Integer)_portletSession.getAttribute(
			_getPercentAttributeName(), PortletSession.APPLICATION_SCOPE);

		if ((curPercent == null) || (percent - curPercent.intValue() >= 1)) {
			_portletSession.setAttribute(
				_getPercentAttributeName(), new Integer(percent),
				PortletSession.APPLICATION_SCOPE);
		}
	}

	private static final int _DEFAULT_INITIAL_BUFFER_SIZE = 4 * 1024;

	private static Log _log = LogFactoryUtil.getLog(ProgressInputStream.class);

	private PortletSession _portletSession;
	private InputStream _is;
	private long _totalRead;
	private long _totalSize;
	private String _progressId;

}