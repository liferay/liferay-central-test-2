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

package com.liferay.portal.security.jaas.ext.sun7;

import com.iplanet.ias.security.auth.login.PasswordLoginModule;

import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.GetterUtil;

import com.sun.enterprise.security.auth.AuthenticationStatus;

import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortalLoginModule.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortalLoginModule extends PasswordLoginModule {

	protected AuthenticationStatus authenticate() throws LoginException {
		if (!(_currentRealm instanceof PortalRealm)) {
			throw new LoginException(PortalRealm.class.getName());
		}

		long userId = GetterUtil.getLong(_username);

		String[] groups = _getGroups(userId, _password);

		if (groups == null) {
			throw new LoginException(_username);
		}

		PortalRealm portalRealm = (PortalRealm)_currentRealm;

		portalRealm.setGroupNames(_username, groups);

		return commitAuthentication(
			_username, _password, _currentRealm, groups);
	}

	private String[] _getGroups(long userId, String password) {
		try {
			if (UserLocalServiceUtil.authenticateForJAAS(userId, password)) {
				return new String[] {"users"};
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	private static Log _log = LogFactory.getLog(PortalLoginModule.class);

}