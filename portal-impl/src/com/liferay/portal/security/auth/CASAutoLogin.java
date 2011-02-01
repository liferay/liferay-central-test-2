/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.security.ldap.PortalLDAPImporterUtil;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.filters.sso.cas.CASFilter;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Properties;

import javax.naming.Binding;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.liferay.portal.NoSuchUserException;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Wesley Gong
 * @author Daeyoung Song
 */
public class CASAutoLogin implements AutoLogin {

	public static String NO_SUCH_USER_ATTRIBUTE = "NO_SUCH_USER";
	public static String FIRST_TIME_WITH_NO_SUCH_USER = "FIRST_TIME_WITH_NO_SUCH_USER";

	public String[] login(
		HttpServletRequest request, HttpServletResponse response) {

		String[] credentials = null;

		HttpSession session = request.getSession();

		try {
			long companyId = PortalUtil.getCompanyId(request);

			if (!PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.CAS_AUTH_ENABLED,
					PropsValues.CAS_AUTH_ENABLED)) {
				return credentials;
			}

			String login = (String)session.getAttribute(CASFilter.LOGIN);

			if (Validator.isNull(login)) {
				if (session.getAttribute(CASAutoLogin.NO_SUCH_USER_ATTRIBUTE) != null) {
		
					session.removeAttribute(CASAutoLogin.NO_SUCH_USER_ATTRIBUTE);
					session.setAttribute(FIRST_TIME_WITH_NO_SUCH_USER, "");

					String redirect = PrefsPropsUtil.getString(
					companyId, PropsKeys.CAS_NO_SUCH_USER_REDIRECT_URL,
					PropsKeys.CAS_LOGOUT_URL);
			
					request.setAttribute(AutoLogin.AUTO_LOGIN_REDIRECT, redirect);
					return credentials;
				}

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
		catch (NoSuchUserException e) {
			session.setAttribute(NO_SUCH_USER_ATTRIBUTE, "");
			session.setAttribute(CASFilter.LOGIN, "");
		} 
		catch (Exception e) {
			_log.error(e, e);
		}

		return credentials;
	}

	/**
	 * @deprecated Use <code>importLDAPUser</code>.
	 */
	protected User addUser(long companyId, String screenName) throws Exception {
		return importLDAPUser(companyId, StringPool.BLANK, screenName);
	}

	protected User importLDAPUser(
			long ldapServerId, long companyId, String emailAddress,
			String screenName)
		throws Exception {

		LdapContext ldapContext = null;

		try {
			String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

			String baseDN = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_BASE_DN + postfix);

			ldapContext = PortalLDAPUtil.getContext(ldapServerId, companyId);

			if (ldapContext == null) {
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

			Properties userMappings = LDAPSettingsUtil.getUserMappings(
				ldapServerId, companyId);

			String userMappingsScreenName = GetterUtil.getString(
				userMappings.getProperty("screenName")).toLowerCase();

			SearchControls searchControls = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0,
				new String[] {userMappingsScreenName}, false, false);

			NamingEnumeration<SearchResult> enu = ldapContext.search(
				baseDN, filter, searchControls);

			if (enu.hasMoreElements()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Search filter returned at least one result");
				}

				Binding binding = enu.nextElement();

				Attributes attributes = PortalLDAPUtil.getUserAttributes(
					ldapServerId, companyId, ldapContext,
					PortalLDAPUtil.getNameInNamespace(
						ldapServerId, companyId, binding));

				return PortalLDAPImporterUtil.importLDAPUser(
					ldapServerId, companyId, ldapContext, attributes,
					StringPool.BLANK);
			}
			else {
				return null;
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
			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	protected User importLDAPUser(
			long companyId, String emailAddress, String screenName)
		throws Exception {

		long[] ldapServerIds = StringUtil.split(
			PrefsPropsUtil.getString(companyId, "ldap.server.ids"), 0L);

		if (ldapServerIds.length <= 0) {
			ldapServerIds = new long[] {0};
		}

		for (long ldapServerId : ldapServerIds) {
			User user = importLDAPUser(
				ldapServerId, companyId, emailAddress, screenName);

			if (user != null) {
				return user;
			}
		}

		if (_log.isDebugEnabled()) {
			if (Validator.isNotNull(emailAddress)) {
				_log.debug(
					"User with the email address " + emailAddress +
						" was not found in any LDAP servers");
			}
			else {
				_log.debug(
					"User with the screen name " + screenName +
						" was not found in any LDAP servers");
			}
		}

		return null;
	}

	private static Log _log = LogFactoryUtil.getLog(CASAutoLogin.class);

}