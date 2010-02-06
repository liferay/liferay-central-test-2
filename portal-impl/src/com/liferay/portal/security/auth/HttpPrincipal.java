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

package com.liferay.portal.security.auth;

import com.liferay.portal.PwdEncryptorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.pwd.PwdEncryptor;

import java.io.Serializable;

/**
 * <a href="HttpPrincipal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class HttpPrincipal implements Serializable {

	public HttpPrincipal(String url) {
		_url = url;
	}

	public HttpPrincipal(String url, String login, String password) {
		this(url, login, password, false);
	}

	public HttpPrincipal(
		String url, String login, String password, boolean digested) {

		_url = url;
		_login = login;

		if (digested) {
			_password = password;
		}
		else {
			try {
				_password = PwdEncryptor.encrypt(password);
			}
			catch (PwdEncryptorException pee) {
				_log.error(pee, pee);
			}
		}
	}

	public String getUrl() {
		return _url;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public String getLogin() {
		return _login;
	}

	public String getPassword() {
		return _password;
	}

	private static Log _log = LogFactoryUtil.getLog(HttpPrincipal.class);

	private String _url;
	private long _companyId;
	private String _login;
	private String _password;

}