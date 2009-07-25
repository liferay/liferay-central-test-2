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

package com.liferay.portal.kernel.workflow;

import java.io.Serializable;

import java.util.Locale;
import java.util.Set;

public class UserCredential implements Serializable {
	private static final long serialVersionUID = -2223408262504608474L;

	public UserCredential() {

	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public Locale getLocale() {
		return _locale;
	}

	public String getLogin() {
		return _login;
	}

	public Set<Long> getRoleIds() {
		return _roleSet;
	}

	public String getScreenName() {
		return _screenName;
	}

	public long getUserId() {
		return _userId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setLocale(Locale locale) {
		this._locale = locale;
	}

	public void setLogin(String login) {
		this._login = login;
	}

	public void setRoleSet(Set<Long> roleSet) {
		_roleSet = roleSet;
	}

	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private long _companyId;
	private String _emailAddress;
	private Locale _locale;
	private String _login;
	private Set<Long> _roleSet;
	private String _screenName;
	private long _userId;

}