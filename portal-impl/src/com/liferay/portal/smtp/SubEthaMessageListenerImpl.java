/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.smtp;

import com.liferay.portal.kernel.smtp.MessageListener;
import com.liferay.portal.kernel.smtp.MessageListenerException;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.subethamail.smtp.TooMuchDataException;

/**
 * <a href="SubEthaMessageListenerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SubEthaMessageListenerImpl
	implements org.subethamail.smtp.MessageListener {

	public SubEthaMessageListenerImpl(MessageListener listener) {
		_listener = listener;
	}

	public boolean accept(String from, String recipient) {
		if (_log.isDebugEnabled()) {
			_log.debug("Listener " + _listener.getClass().getName());
			_log.debug("From " + from);
			_log.debug("Recipient " + recipient);
		}

		boolean value = _listener.accept(from, recipient);

		if (_log.isDebugEnabled()) {
			_log.debug("Accept " + value);
		}

		return value;
	}

	public void deliver(String from, String recipient, InputStream data)
		throws IOException, TooMuchDataException {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Listener " + _listener.getClass().getName());
				_log.debug("From " + from);
				_log.debug("Recipient " + recipient);
				_log.debug("Data " + data);
			}

			_listener.deliver(from, recipient, data);
		}
		catch (MessageListenerException mle) {
			Throwable cause = mle.getCause();

			if (cause instanceof IOException) {
				throw (IOException)cause;
			}
			else if (cause instanceof TooMuchDataException) {
				throw (TooMuchDataException)cause;
			}
			else if (cause != null) {
				throw new IOException(cause.getMessage());
			}
			else {
				throw new IOException(mle.getMessage());
			}
		}
	}

	public String getId() {
		return _listener.getId();
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		SubEthaMessageListenerImpl listener = null;

		try {
			listener = (SubEthaMessageListenerImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String id = listener.getId();

		if (getId().equals(id)) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log =
		LogFactory.getLog(SubEthaMessageListenerImpl.class);

	private MessageListener _listener;

}