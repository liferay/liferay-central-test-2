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

package com.liferay.portal.servlet.filters.autologin;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AutoLogin;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="AutoLoginFilter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class AutoLoginFilter extends BasePortalFilter {

	public static void registerAutoLogin(AutoLogin autoLogin) {
		if (_autoLogins == null) {
			_log.error("AutoLoginFilter is not initialized yet");

			return;
		}

		List<AutoLogin> autoLogins = ListUtil.fromArray(_autoLogins);

		autoLogins.add(autoLogin);

		_autoLogins = autoLogins.toArray(new AutoLogin[autoLogins.size()]);
	}

	public static void unregisterAutoLogin(AutoLogin autoLogin) {
		if (_autoLogins == null) {
			_log.error("AutoLoginFilter is not initialized yet");

			return;
		}

		List<AutoLogin> autoLogins = ListUtil.fromArray(_autoLogins);

		if (autoLogins.remove(autoLogin)) {
			_autoLogins = autoLogins.toArray(new AutoLogin[autoLogins.size()]);
		}
	}

	public AutoLoginFilter() {
		List<AutoLogin> autoLogins = new ArrayList<AutoLogin>();

		for (String autoLoginClassName : PropsValues.AUTO_LOGIN_HOOKS) {
			AutoLogin autoLogin = (AutoLogin)InstancePool.get(
				autoLoginClassName);

			autoLogins.add(autoLogin);
		}

		_autoLogins = autoLogins.toArray(new AutoLogin[autoLogins.size()]);
	}

	protected String getLoginRemoteUser(
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session, String[] credentials)
		throws Exception {

		if ((credentials != null) && (credentials.length == 3)) {
			String jUsername = credentials[0];
			String jPassword = credentials[1];
			boolean encPassword = GetterUtil.getBoolean(credentials[2]);

			if (Validator.isNotNull(jUsername) &&
				Validator.isNotNull(jPassword)) {

				try {
					long userId = GetterUtil.getLong(jUsername);

					if (userId > 0) {
						User user = UserLocalServiceUtil.getUserById(userId);

						if (user.isLockout()) {
							return null;
						}
					}
					else {
						return null;
					}
				}
				catch (NoSuchUserException nsue) {
					return null;
				}

				session.setAttribute("j_username", jUsername);

				// Not having access to the unencrypted password
				// will not allow you to connect to external
				// resources that require it (mail server)

				if (encPassword) {
					session.setAttribute("j_password", jPassword);
				}
				else {
					session.setAttribute(
						"j_password", PwdEncryptor.encrypt(jPassword));

					session.setAttribute(WebKeys.USER_PASSWORD, jPassword);
				}

				if (PropsValues.PORTAL_JAAS_ENABLE) {
					response.sendRedirect(
						PortalUtil.getPathMain() + "/portal/touch_protected");
				}

				return jUsername;
			}
		}

		return null;
	}

	protected void processFilter(
			HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
		throws Exception {

		HttpSession session = request.getSession();

		String host = PortalUtil.getHost(request);

		if (PortalInstances.isAutoLoginIgnoreHost(host)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Ignore host " + host);
			}

			processFilter(
				AutoLoginFilter.class, request, response, filterChain);

			return;
		}

		String contextPath = PortalUtil.getPathContext();

		String path = request.getRequestURI().toLowerCase();

		if ((!contextPath.equals(StringPool.SLASH)) &&
			(path.indexOf(contextPath) != -1)) {

			path = path.substring(contextPath.length(), path.length());
		}

		if (PortalInstances.isAutoLoginIgnorePath(path)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Ignore path " + path);
			}

			processFilter(
				AutoLoginFilter.class, request, response, filterChain);

			return;
		}

		String remoteUser = request.getRemoteUser();
		String jUserName = (String)session.getAttribute("j_username");

		if ((remoteUser == null) && (jUserName == null)) {
			for (AutoLogin autoLogin : _autoLogins) {
				try {
					String[] credentials = autoLogin.login(request, response);

					String redirect = (String)request.getAttribute(
						AutoLogin.AUTO_LOGIN_REDIRECT);

					if (Validator.isNotNull(redirect)) {
						response.sendRedirect(redirect);

						return;
					}

					String loginRemoteUser = getLoginRemoteUser(
						request, response, session, credentials);

					if (loginRemoteUser != null) {
						request = new ProtectedServletRequest(
							request, loginRemoteUser);

						if (PropsValues.PORTAL_JAAS_ENABLE) {
							return;
						}

						redirect = (String)request.getAttribute(
							AutoLogin.AUTO_LOGIN_REDIRECT_AND_CONTINUE);

						if (Validator.isNotNull(redirect)) {
							response.sendRedirect(redirect);

							break;
						}
					}
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e, e);
					}

					String currentURL = PortalUtil.getCurrentURL(request);

					if (currentURL.endsWith(_PATH_CHAT_LATEST)) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Current URL " + currentURL +
									" generates exception: " + e.getMessage());
						}
					}
					else {
						_log.error(
							"Current URL " + currentURL +
								" generates exception: " + e.getMessage());
					}
				}
			}
		}

		processFilter(AutoLoginFilter.class, request, response, filterChain);
	}

	private static final String _PATH_CHAT_LATEST = "/-/chat/latest";

	private static Log _log = LogFactoryUtil.getLog(AutoLoginFilter.class);

	private static AutoLogin[] _autoLogins;

}