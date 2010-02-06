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

package com.liferay.portal.pop;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.pop.MessageListenerException;

import javax.mail.Message;

/**
 * <a href="MessageListenerWrapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MessageListenerWrapper implements MessageListener {

	public MessageListenerWrapper(MessageListener listener) {
		_listener = listener;
	}

	public boolean accept(String from, String recipient, Message message) {
		if (_log.isDebugEnabled()) {
			_log.debug("Listener " + _listener.getClass().getName());
			_log.debug("From " + from);
			_log.debug("Recipient " + recipient);
		}

		boolean value = _listener.accept(from, recipient, message);

		if (_log.isDebugEnabled()) {
			_log.debug("Accept " + value);
		}

		return value;
	}

	public void deliver(String from, String recipient, Message message)
		throws MessageListenerException {

		if (_log.isDebugEnabled()) {
			_log.debug("Listener " + _listener.getClass().getName());
			_log.debug("From " + from);
			_log.debug("Recipient " + recipient);
			_log.debug("Message " + message);
		}

		_listener.deliver(from, recipient, message);
	}

	public String getId() {
		return _listener.getId();
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MessageListenerWrapper listener = null;

		try {
			listener = (MessageListenerWrapper)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String id = listener.getId();

		return getId().equals(id);
	}

	private static Log _log =
		LogFactoryUtil.getLog(MessageListenerWrapper.class);

	private MessageListener _listener;

}