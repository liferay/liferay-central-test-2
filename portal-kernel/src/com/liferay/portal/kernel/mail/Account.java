/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.mail;

import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

/**
 * <a href="Account.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 *
 */
public abstract class Account implements Serializable {

	public static Account getInstance(String protocol, boolean useSSL) {

		Account account = null;

		if (protocol.equals(Account.IMAP_PROTOCOL) ||
				protocol.equals(Account.IMAPS_PROTOCOL)) {

			account = new IMAPAccount(protocol, useSSL);
		}
		else if (protocol.equals(Account.POP3_PROTOCOL) ||
				protocol.equals(Account.POP3S_PROTOCOL)) {

			account = new POP3Account(protocol, useSSL);
		}
		else if (protocol.equals(Account.SMTP_PROTOCOL) ||
				protocol.equals(Account.SMTPS_PROTOCOL)) {

			account = new SMTPAccount(protocol, useSSL);
		}
		else {
			throw new IllegalArgumentException(
				"Mail protocol not recognized: " + protocol);
		}

		return account;
	}

	Account(String protocol, boolean useSSL) {

		_protocol = protocol;
		_useSSL = useSSL;
	}

	public String getPassword() {
		return _password;
	}

	public String getProtocol() {
		return _protocol;
	}

	public String getServerName() {
		return _serverName;
	}

	public int getServerPort() {
		return _serverPort;
	}

	public String getUserName() {
		return _userName;
	}

	public boolean isRequiresAuthentication() {
		if (Validator.isNotNull(_userName) &&
			Validator.isNotNull(_password)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isUseSSL() {
		return _useSSL;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setServerName(String serverName) {
		_serverName = serverName;
	}

	public void setServerPort(int serverPort) {
		_serverPort = serverPort;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public static final String IMAP_PROTOCOL = "imap";

	public static final String IMAPS_PROTOCOL = "imaps";

	public static final String POP3_PROTOCOL = "pop3";

	public static final String POP3S_PROTOCOL = "pop3s";

	public static final String SMTP_PROTOCOL = "smtp";

	public static final String SMTPS_PROTOCOL = "smtps";

	private String _password;
	private String _protocol;
	private String _serverName;
	private int _serverPort;
	private String _userName;
	private boolean _useSSL;

}