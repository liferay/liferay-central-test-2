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
 * <a href="SMTPAccount.java.html"><b><i>View Source</i></b></a>
 *
 * @author Thiago Moreira
 *
 */
public class SMTPAccount implements Serializable {

	public String getPassword() {
		return _password;
	}

	public String getServerName() {
		return _serverName;
	}

	public Integer getServerPort() {
		return _serverPort;
	}

	public String getUserName() {
		return _userName;
	}

	public Boolean isUseSSL() {
		return _useSSL;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setServerName(String serverName) {
		_serverName = serverName;
	}

	public void setServerPort(Integer serverPort) {
		_serverPort = serverPort;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public void setUseSSL(Boolean useSSL) {
		_useSSL = useSSL;
	}

	public boolean requiresAuthentication() {
		return Validator.isNotNull(_userName) && _password != null;
	}

	private String _password;
	private String _serverName;
	private Integer _serverPort;
	private String _userName;
	private Boolean _useSSL;

}