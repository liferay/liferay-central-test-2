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

package com.liferay.portal.smtp;

import com.liferay.portal.kernel.smtp.MessageListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.subethamail.smtp.server.SMTPServer;

/**
 * <a href="SMTPServerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SMTPServerUtil {

	public static void addListener(MessageListener listener)
		throws Exception {

		_instance._addListener(listener);
	}

	public static void deleteListener(MessageListener listener)
		throws Exception {

		_instance._deleteListener(listener);
	}

	public static void setPort(int port) {
		_instance._setPort(port);
	}

	public static void start() {
		_instance._start();
	}

	public static void stop() {
		_instance._stop();
	}

	private SMTPServerUtil() {
		_smtpServer = new SMTPServer(_listeners);
	}

	private void _addListener(MessageListener listener) {
		if (listener == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Do not add null listener");
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Add listener " + listener.getClass().getName());
		}

		SubEthaMessageListenerImpl subEthaListener =
			new SubEthaMessageListenerImpl(listener);

		_deleteListener(subEthaListener);

		_listeners.add(subEthaListener);

		if (_log.isDebugEnabled()) {
			_log.debug("Listeners size " + _listeners.size());
		}
	}

	private void _deleteListener(SubEthaMessageListenerImpl listener) {
		Iterator itr = _listeners.iterator();

		while (itr.hasNext()) {
			SubEthaMessageListenerImpl curListener =
				(SubEthaMessageListenerImpl)itr.next();

			if (curListener.equals(listener)) {
				itr.remove();
			}
		}
	}

	private void _deleteListener(MessageListener listener) {
		if (listener == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Do not delete null listener");
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Delete listener " + listener.getClass().getName());
		}

		SubEthaMessageListenerImpl subEthaListener =
			new SubEthaMessageListenerImpl(listener);

		_deleteListener(subEthaListener);

		if (_log.isDebugEnabled()) {
			_log.debug("Listeners size " + _listeners.size());
		}
	}

	private void _setPort(int port) {
		if (_log.isDebugEnabled()) {
			_log.debug("Listen on port " + port);
		}

		_smtpServer.setPort(port);
	}

	private void _start() {
		if (_log.isDebugEnabled()) {
			_log.debug("Start");
		}

		_smtpServer.start();
	}

	private void _stop() {
		if (_log.isDebugEnabled()) {
			_log.debug("Stop");
		}

		_smtpServer.stop();
	}

	private static Log _log = LogFactory.getLog(SMTPServerUtil.class);

	private static SMTPServerUtil _instance = new SMTPServerUtil();

	private SMTPServer _smtpServer;
	private List _listeners = new ArrayList();

}