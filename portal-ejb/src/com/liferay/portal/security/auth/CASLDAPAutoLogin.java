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

package com.liferay.portal.security.auth;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.ldap.LDAPUtil;

import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="CASLDAPAutoLogin.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class CASLDAPAutoLogin extends CASAutoLogin {

	protected User processNoSuchUserException(
			long companyId, String screenName, NoSuchUserException nsuse)
		throws PortalException, SystemException {

		try {
			Properties env = new Properties();

			String baseProviderURL = PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_BASE_PROVIDER_URL);

			String baseDN = PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_BASE_DN);

			env.put(
				Context.INITIAL_CONTEXT_FACTORY,
				PrefsPropsUtil.getString(
					companyId, PropsUtil.LDAP_FACTORY_INITIAL));
			env.put(
				Context.PROVIDER_URL,
				LDAPUtil.getFullProviderURL(baseProviderURL, baseDN));
			env.put(
				Context.SECURITY_PRINCIPAL,
				PrefsPropsUtil.getString(
					companyId, PropsUtil.LDAP_SECURITY_PRINCIPAL));
			env.put(
				Context.SECURITY_CREDENTIALS,
				PrefsPropsUtil.getString(
					companyId, PropsUtil.LDAP_SECURITY_CREDENTIALS));

			LdapContext ctx = null;

			try {
				ctx = new InitialLdapContext(env, null);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("Failed to bind to the LDAP server", e);
				}

				throw new SystemException(e);
			}

			String filter = PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_AUTH_SEARCH_FILTER);

			if (_log.isDebugEnabled()) {
				_log.debug("Search filter before transformation " + filter);
			}

			filter = StringUtil.replace(
				filter,
				new String[] {
					"@company_id@", "@email_address@", "@screen_name@"
				},
				new String[] {
					String.valueOf(companyId), StringPool.BLANK, screenName
				});

			if (_log.isDebugEnabled()) {
				_log.debug("Search filter after transformation " + filter);
			}

			SearchControls cons = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0, null, false, false);

			NamingEnumeration enu = ctx.search(StringPool.BLANK, filter, cons);

			if (enu.hasMore()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Search filter returned at least one result");
				}

				Binding binding = (Binding)enu.next();

				Attributes attrs = ctx.getAttributes(binding.getName());

				Properties userMappings =
					PortalLDAPUtil.getUserMappings(companyId);

				Attribute emailAddressAttr = attrs.get("mail");

				String emailAddress = StringPool.BLANK;

				if (emailAddressAttr != null) {
					emailAddress = emailAddressAttr.get().toString();
				}

				return processUser(
					attrs, userMappings, companyId, emailAddress, screenName, 0,
					_PASSWORD);
			}
			else {
				throw new NoSuchUserException(
					"User " + screenName + " was not found in the LDAP server");
			}
		}
		catch (Exception e) {
			_log.error("Problem accessing LDAP server ", e);

			throw new RuntimeException(
				"Problem accessign LDAP server " + e.getMessage());
		}
	}

	protected User processUser(
			Attributes attrs, Properties userMappings, long companyId,
			String emailAddress, String screenName, long userId,
			String password)
		throws Exception {

		long creatorUserId = 0;

		boolean autoPassword = false;
		String password1 = password;
		String password2 = password;
		boolean passwordReset = false;

		boolean autoScreenName = false;

		if (Validator.isNull(screenName)) {
			screenName = LDAPUtil.getAttributeValue(
				attrs, userMappings.getProperty("screenName")).toLowerCase();
		}

		if (Validator.isNull(emailAddress)) {
			emailAddress = LDAPUtil.getAttributeValue(
				attrs, userMappings.getProperty("emailAddress"));
		}

		Locale locale = Locale.US;
		String firstName = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("firstName"));
		String middleName = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("middleName"));
		String lastName = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("lastName"));

		if (Validator.isNull(firstName) || Validator.isNull(lastName)) {
			String fullName = LDAPUtil.getAttributeValue(
				attrs, userMappings.getProperty("fullName"));

			String[] names = LDAPUtil.splitFullName(fullName);

			firstName = names[0];
			middleName = names[1];
			lastName = names[2];
		}

		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("jobTitle"));
		String organizationId = null;
		String locationId = null;
		boolean sendEmail = false;
		boolean checkExists = true;
		boolean updatePassword = true;

		return PortalLDAPUtil.importFromLDAP(
			creatorUserId, companyId, autoPassword, password1, password2,
			passwordReset, autoScreenName, screenName, emailAddress, locale,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, organizationId,
			locationId, sendEmail, checkExists, updatePassword);
	}

	private static Log _log = LogFactory.getLog(CASLDAPAutoLogin.class);

	private static final String _PASSWORD = "password";

}