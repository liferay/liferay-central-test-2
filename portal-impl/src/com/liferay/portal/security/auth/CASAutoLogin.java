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

package com.liferay.portal.security.auth;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.filters.sso.cas.CASFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import javax.naming.Binding;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <a href="CASAutoLogin.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Wesley Gong
 * @author Daeyoung Song
 */
public class CASAutoLogin implements AutoLogin {

	public String[] login(
		HttpServletRequest request, HttpServletResponse response) {

		String[] credentials = null;

		try {
			long companyId = PortalUtil.getCompanyId(request);

			if (!PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.CAS_AUTH_ENABLED,
					PropsValues.CAS_AUTH_ENABLED)) {

				return credentials;
			}

			HttpSession session = request.getSession();

			String login = (String)session.getAttribute(CASFilter.LOGIN);

			if (Validator.isNull(login)) {
				return credentials;
			}

			String authType = PrefsPropsUtil.getString(
				companyId, PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
				PropsValues.COMPANY_SECURITY_AUTH_TYPE);

			User user = null;

			if (PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.CAS_IMPORT_FROM_LDAP,
					PropsValues.CAS_IMPORT_FROM_LDAP)) {

				try {
					if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
						user = importLDAPUser(
							companyId, StringPool.BLANK, login);
					}
					else {
						user = importLDAPUser(
							companyId, login, StringPool.BLANK);
					}
				}
				catch (SystemException se) {
				}
			}

			if (user == null) {
				if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
					user = UserLocalServiceUtil.getUserByScreenName(
						companyId, login);
				}
				else {
					user = UserLocalServiceUtil.getUserByEmailAddress(
						companyId, login);
				}
			}

			String redirect = ParamUtil.getString(request, "redirect");

			if (Validator.isNotNull(redirect)) {
				request.setAttribute(AutoLogin.AUTO_LOGIN_REDIRECT, redirect);
			}

			credentials = new String[3];

			credentials[0] = String.valueOf(user.getUserId());
			credentials[1] = user.getPassword();
			credentials[2] = Boolean.TRUE.toString();

			return credentials;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return credentials;
	}

	/**
	 * @deprecated Use <code>importLDAPUser</code>.
	 */
	protected User addUser(long companyId, String screenName)
		throws Exception {

		return importLDAPUser(companyId, StringPool.BLANK, screenName);
	}

	protected User importLDAPUser(
			long companyId, String emailAddress, String screenName)
		throws Exception {

		LdapContext ctx = null;

		try {
			long ldapServerId = PortalLDAPUtil.getLdapServerId(
				companyId, screenName);

			String postfix = PortalLDAPUtil.getPropertyPostfix(ldapServerId);

			String baseDN = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_BASE_DN + postfix);

			ctx = PortalLDAPUtil.getContext(ldapServerId, companyId);

			if (ctx == null) {
				throw new SystemException("Failed to bind to the LDAP server");
			}

			String filter = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_AUTH_SEARCH_FILTER + postfix);

			if (_log.isDebugEnabled()) {
				_log.debug("Search filter before transformation " + filter);
			}

			filter = StringUtil.replace(
				filter,
				new String[] {
					"@company_id@", "@email_address@", "@screen_name@"
				},
				new String[] {
					String.valueOf(companyId), emailAddress, screenName
				});

			if (_log.isDebugEnabled()) {
				_log.debug("Search filter after transformation " + filter);
			}

			SearchControls cons = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0, null, false, false);

			NamingEnumeration<SearchResult> enu = ctx.search(
				baseDN, filter, cons);

			if (enu.hasMoreElements()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Search filter returned at least one result");
				}

				Binding binding = enu.nextElement();

				Attributes attrs = PortalLDAPUtil.getUserAttributes(
					ldapServerId, companyId, ctx,
					PortalLDAPUtil.getNameInNamespace(
						ldapServerId, companyId, binding));

				return PortalLDAPUtil.importLDAPUser(
					ldapServerId, companyId, ctx, attrs, StringPool.BLANK,
					true);
			}
			else {
				throw new NoSuchUserException(
					"User " + screenName + " was not found in the LDAP server");
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Problem accessing LDAP server " + e.getMessage());
			}

			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new SystemException(
				"Problem accessing LDAP server " + e.getMessage());
		}
		finally {
			if (ctx != null) {
				ctx.close();
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CASAutoLogin.class);

}