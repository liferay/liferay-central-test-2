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

package com.liferay.portal.upload;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStreamWrapper;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.servlet.ServletInputStreamWrapper;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <a href="LiferayInputStream.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Myunghun Kim
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 */
public class LiferayInputStream extends ServletInputStreamWrapper {

	public static final int THRESHOLD_SIZE = GetterUtil.getInteger(
		PropsUtil.get(LiferayInputStream.class.getName() + ".threshold.size"));

	public LiferayInputStream(HttpServletRequest request) throws IOException {
		super(request.getInputStream());

		_session = request.getSession();
		_totalSize = request.getContentLength();
	}

	public int read(byte[] b, int off, int len) throws IOException {
		int bytesRead = super.read(b, off, len);

		if (bytesRead > 0) {
			_totalRead += bytesRead;
		}
		else {
			return bytesRead;
		}

		int percent = (_totalRead * 100) / _totalSize;

		if (_log.isDebugEnabled()) {
			_log.debug(bytesRead + "/" + _totalRead + "=" + percent);
		}

		if (_totalSize < THRESHOLD_SIZE) {
			_cachedBytes.write(b, off, bytesRead);
		}

		Integer curPercent = (Integer)_session.getAttribute(
			LiferayFileUpload.PERCENT);

		if ((curPercent == null) || (percent - curPercent.intValue() >= 1)) {
			_session.setAttribute(
				LiferayFileUpload.PERCENT, new Integer(percent));
		}

		return bytesRead;
	}

	public ServletInputStream getCachedInputStream() {
		if (_totalSize < THRESHOLD_SIZE) {
			return this;
		}
		else {
			return new UnsyncByteArrayInputStreamWrapper(
				new UnsyncByteArrayInputStream(_cachedBytes.toByteArray()));
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LiferayInputStream.class);

	private HttpSession _session;
	private int _totalRead;
	private int _totalSize;
	private UnsyncByteArrayOutputStream _cachedBytes =
		new UnsyncByteArrayOutputStream();

}