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

package com.liferay.portal.kernel.audit;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

/**
 * <a href="AuditRequestThreadLocal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class AuditRequestThreadLocal {

	public static AuditRequestThreadLocal getAuditThreadLocal() {
		AuditRequestThreadLocal auditRequestThreadLocal =
			_auditRequestThreadLocal.get();

		if (auditRequestThreadLocal == null) {
			auditRequestThreadLocal = new AuditRequestThreadLocal();

			_auditRequestThreadLocal.set(auditRequestThreadLocal);
		}

		return auditRequestThreadLocal;
	}

	public static void removeAuditThreadLocal() {
		_auditRequestThreadLocal.remove();
	}

	public String getClientHost() {
		return _clientHost;
	}

	public String getClientIP() {
		return _clientIP;
	}

	public String getQueryString() {
		return _queryString;
	}

	public long getRealUserId() {
		return _realUserId;
	}

	public String getRequestURL() {
		return _requestURL;
	}

	public String getServerName() {
		return _serverName;
	}

	public int getServerPort() {
		return _serverPort;
	}

	public String getSessionID() {
		return _sessionID;
	}

	public void setClientHost(String clientHost) {
		_clientHost = clientHost;
	}

	public void setClientIP(String clientIP) {
		_clientIP = clientIP;
	}

	public void setQueryString(String queryString) {
		_queryString = queryString;
	}

	public void setRealUserId(long realUserId) {
		_realUserId = realUserId;
	}

	public void setRequestURL(String requestURL) {
		_requestURL = requestURL;
	}

	public void setServerName(String serverName) {
		_serverName = serverName;
	}

	public void setServerPort(int serverPort) {
		_serverPort = serverPort;
	}

	public void setSessionID(String sessionID) {
		_sessionID = sessionID;
	}

	private static ThreadLocal<AuditRequestThreadLocal>
		_auditRequestThreadLocal =
			new AutoResetThreadLocal<AuditRequestThreadLocal>();

	private String _clientHost;
	private String _clientIP;
	private String _queryString;
	private long _realUserId;
	private String _requestURL;
	private String _serverName;
	private int _serverPort;
	private String _sessionID;

}