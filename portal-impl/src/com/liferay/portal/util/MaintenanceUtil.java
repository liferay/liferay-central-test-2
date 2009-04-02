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

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.servlet.PortalSessionContext;

import java.util.Collection;

import javax.servlet.http.HttpSession;

/**
 * <a href="MaintenanceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class MaintenanceUtil {

	public static void appendStatus(String status) {
		_instance._appendStatus(status);
	}

	public static void cancel() {
		_instance._cancel();
	}

	public static String getClassName() {
		return _instance._getClassName();
	}

	public static String getSessionId() {
		return _instance._getSessionId();
	}

	public static String getStatus() {
		return _instance._getStatus();
	}

	public static boolean isMaintaining() {
		return _instance._isMaintaining();
	}

	public static void maintain(String sessionId, String className) {

		_instance._maintain(sessionId, className);
	}

	private MaintenanceUtil() {
	}

	private void _appendStatus(String status) {
		if (_log.isDebugEnabled()) {
			_log.debug(status);
		}

		_status.append(Time.getRFC822() + " " + status + "<br />");
	}

	private void _cancel() {
		_isMaintaining = false;
	}

	public String _getClassName() {
		return _className;
	}

	public String _getSessionId() {
		return _sessionId;
	}

	private String _getStatus() {
		return _status.toString();
	}

	private boolean _isMaintaining() {
		return _isMaintaining;
	}

	private void _maintain(String sessionId, String className) {

		// Reset values

		_sessionId = sessionId;
		_className = className;
		_isMaintaining = true;
		_status = new StringBuffer();

		// Kill all sessions

		Collection<HttpSession> sessionPool =
			PortalSessionContext.getSessionPool();

		for (HttpSession session : sessionPool) {
			session.invalidate();
		}
	}

	private static MaintenanceUtil _instance = new MaintenanceUtil();

	private String _sessionId;
	private String _className;
	private StringBuffer _status = new StringBuffer();
	private boolean _isMaintaining = false;

	private static Log _log = LogFactoryUtil.getLog(MaintenanceUtil.class);

}