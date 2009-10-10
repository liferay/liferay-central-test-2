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
 * <p>
 * The user credential is a container for a user's id and its roles and is used
 * as the credential towards the workflow engine. For convenience, it is just
 * added automatically through the request by creating it using the {@link
 * UserCredentialFactoryUtil#createCredential(long)} as the API just takes the
 * user id, the role set and additional information is being added by the {@link
 * UserCredentialFactory} invoked by the proxy (most likely the request builder)
 * in order to avoid the implementation or adapter having to call back the
 * portal for the set of roles of a user's id.
 * </p>
 *
 * @author Micha Kiener
 */
public class UserCredential implements Serializable {

	/**
	 * Default constructor, just used for de-serialization, never for
	 * construction.
	 */
	public UserCredential() {
	}

	/**
	 * Adds any additional attribute to this user credential. Make sure the
	 * value of the attribute is serializable as the user credential might get
	 * serialized through the message bus, if needed.
	 *
	 * @param key the name of the attribute for later retrieval
	 * @param value the value of the attribute to be stored within this user
	 *		  credential
	 */
	public void addAttribute(String key, Object value) {
		if (_attributes == null) {
			_attributes = new HashMap<String, Object>();
		}

		_attributes.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key) {
		if (_attributes == null) {
			return null;
		}

		return (T)_attributes.get(key);
	}

	/**
	 * @return the id of the company the user represented by this credential
	 *		   belongs to
	 */
	public long getCompanyId() {
		return _companyId;
	}

	/**
	 * @return the email address of the user represented by this credential
	 */
	public String getEmailAddress() {
		return _emailAddress;
	}

	/**
	 * @return the locale of the user represented by this credential
	 */
	public Locale getLocale() {
		return _locale;
	}

	/**
	 * @return the login name of the user represented by this credential
	 */
	public String getLogin() {
		return _login;
	}

	/**
	 * @return the set of the role ids the user reflected by this credential has
	 */
	public Set<Long> getRoleIds() {
		return _roleIds;
	}

	/**
	 * @return the screen name of the user represented by this credential
	 */
	public String getScreenName() {
		return _screenName;
	}

	/**
	 * @return the id of the user reflected by this credential
	 */
	public long getUserId() {
		return _userId;
	}

	/**
	 * @param companyId the id of the company the user reflected by this
	 *		  credential should be assigned to
	 */
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	/**
	 * @param emailAddress the email address of the user reflected by this
	 *		  credential
	 */
	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	/**
	 * @param locale the locale of the user reflected by this credential
	 */
	public void setLocale(Locale locale) {
		_locale = locale;
	}

	/**
	 * @param login the login name of the user reflected by this credential
	 */
	public void setLogin(String login) {
		_login = login;
	}

	/**
	 * @param roleIds the set of roles the user of this credential is assigned
	 *		  to
	 */
	public void setRoleIds(Set<Long> roleIds) {
		_roleIds = roleIds;
	}

	/**
	 * @param screenName the screen name of the user reflected by this
	 *		  credential
	 */
	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	/**
	 * @param userId the id of the user reflected by this credential
	 */
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