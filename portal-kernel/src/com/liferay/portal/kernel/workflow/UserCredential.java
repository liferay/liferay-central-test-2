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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * <a href="UserCredential.java.html"><b><i>View Source</i></b></a>
 *
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class UserCredential implements Serializable {

	public void addAttribute(String key, Object value) {
		if (_attributes == null) {
			_attributes = new HashMap<String, Object>();
		}

		_attributes.put(key, value);
	}

	public <T> T getAttribute(String key) {
		if (_attributes == null) {
			return null;
		}

		return (T)_attributes.get(key);
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
		return _roleIds;
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
		_locale = locale;
	}

	public void setLogin(String login) {
		_login = login;
	}

	public void setRoleIds(Set<Long> roleIds) {
		_roleIds = roleIds;
	}

	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private Map<String, Object> _attributes;
	private long _companyId;
	private String _emailAddress;
	private Locale _locale;
	private String _login;
	private Set<Long> _roleIds;
	private String _screenName;
	private long _userId;

}