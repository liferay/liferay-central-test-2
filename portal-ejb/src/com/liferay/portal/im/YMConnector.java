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

package com.liferay.portal.im;

import com.liferay.portal.util.PropsUtil;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ymsg.network.Session;
import ymsg.network.YahooException;

/**
 * <a href="YMConnector.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Brett Randall
 *
 */
public class YMConnector {

	public static void disconnect() {
		if (_instance != null) {
			_instance._disconnect();
		}
	}

	public static void send(String to, String msg)
		throws IllegalStateException, IOException, YahooException {

		_instance._send(to, msg);
	}

	private YMConnector() {
	}

	private void _connect() {
		try {
			_ym = new Session();

			String login = PropsUtil.get(PropsUtil.YM_LOGIN);
			String password = PropsUtil.get(PropsUtil.YM_PASSWORD);

			_ym.login(login, password);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private void _disconnect() {
		try {
			if (_ym != null) {
				_ym.logout();
			}
		}
		catch (Exception e) {
			_log.warn(e);
		}
	}

	private void _send(String to, String msg)
		throws IllegalStateException , IOException {

		try {
			if (_ym == null) {
				_connect();
			}

			_ym.sendMessage(to, msg);
		}
		catch (Exception e) {
			_connect();

			_ym.sendMessage(to, msg);
		}
	}

	private static Log _log = LogFactory.getLog(YMConnector.class);

	private static YMConnector _instance = new YMConnector();

	private Session _ym;

}