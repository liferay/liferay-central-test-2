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
import com.liferay.portal.PasswordExpiredException;
import com.liferay.portal.UserLockoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.security.pwd.PwdEncryptor;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.admin.util.OmniadminUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.ldap.LDAPUtil;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * <a href="LDAPAuth.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Scott Lee
 *
 */
public class LDAPAuth implements Authenticator {

	public static final String AUTH_METHOD_BIND = "bind";

	public static final String AUTH_METHOD_PASSWORD_COMPARE =
		"password-compare";

	public static final String RESULT_PASSWORD_RESET =
		"2.16.840.1.113730.3.4.4";

	public static final String RESULT_PASSWORD_EXP_WARNING =
		"2.16.840.1.113730.3.4.5";

	public int authenticateByEmailAddress(
			long companyId, String emailAddress, String password, Map headerMap,
			Map parameterMap)
		throws AuthException {

		try {
			return authenticate(
				companyId, emailAddress, StringPool.BLANK, 0, password);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new AuthException(e);
		}
	}

	public int authenticateByScreenName(
			long companyId, String screenName, String password, Map headerMap,
			Map parameterMap)
		throws AuthException {

		try {
			return authenticate(
				companyId, StringPool.BLANK, screenName, 0, password);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new AuthException(e);
		}
	}

	public int authenticateByUserId(
			long companyId, long userId, String password, Map headerMap,
			Map parameterMap)
		throws AuthException {

		try {
			return authenticate(
				companyId, StringPool.BLANK, StringPool.BLANK, userId,
				password);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new AuthException(e);
		}
	}

	protected int authenticate(
			long companyId, String emailAddress, String screenName, long userId,
			String password)
		throws Exception {

		if (!PortalLDAPUtil.isAuthEnabled(companyId)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Authenticator is not enabled");
			}

			return SUCCESS;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Authenticator is enabled");
		}

		// Make exceptions for omniadmins so that if they break the LDAP
		// configuration, they can still login to fix the problem

		if (authenticateOmniadmin(companyId, emailAddress, userId) == SUCCESS) {
			return SUCCESS;
		}

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

		LogUtil.debug(_log, env);

		LdapContext ctx = null;

		try {
			ctx = new InitialLdapContext(env, null);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Failed to bind to the LDAP server");
			}

			return SUCCESS;
		}

		String filter = PrefsPropsUtil.getString(
			companyId, PropsUtil.LDAP_AUTH_SEARCH_FILTER);

		if (_log.isDebugEnabled()) {
			_log.debug("Search filter before transformation " + filter);
		}

		filter = StringUtil.replace(
			filter,
			new String[] {
				"@company_id@", "@email_address@", "@screen_name@", "@user_id@"
			},
			new String[] {
				String.valueOf(companyId), emailAddress, screenName,
				String.valueOf(userId)
			});

		if (_log.isDebugEnabled()) {
			_log.debug("Search filter after transformation " + filter);
		}

		try {
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

				LogUtil.debug(_log, userMappings);

				Attribute userPassword = attrs.get("userPassword");

				LDAPAuthResult ldapAuthResult = authenticate(
					ctx, env, binding, baseDN, userPassword, companyId,
					emailAddress, screenName, userId, password);

				// Process LDAP failure codes

				String errorMessage = ldapAuthResult.getErrorMessage();

				if (errorMessage != null) {
					if (errorMessage.indexOf(PrefsPropsUtil.getString(
							companyId, PropsUtil.LDAP_ERROR_USER_LOCKOUT))
								!= -1) {

						throw new UserLockoutException();
					}
					else if (errorMessage.indexOf(PrefsPropsUtil.getString(
						companyId, PropsUtil.LDAP_ERROR_PASSWORD_EXPIRED))
							!= -1) {

						throw new PasswordExpiredException();
					}
				}

				if (!ldapAuthResult.isAuthenticated()) {
					return authenticateRequired(
						companyId, userId, emailAddress, FAILURE);
				}

				// Get user or create from LDAP

				User user = processUser(
					attrs, userMappings, companyId, emailAddress, screenName,
					userId, password);

				// Process LDAP success codes

				String resultCode = ldapAuthResult.getResponseControl();

				if (resultCode.equals(LDAPAuth.RESULT_PASSWORD_RESET)) {
					UserLocalServiceUtil.updatePasswordReset(
						user.getUserId(), true);
				}
				else if (
					resultCode.equals(LDAPAuth.RESULT_PASSWORD_EXP_WARNING)) {

					UserLocalServiceUtil.updatePasswordReset(
						user.getUserId(), true);
				}
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Search filter did not return any results");
				}

				return authenticateRequired(
					companyId, userId, emailAddress, DNE);
			}
		}
		catch (Exception e) {
			_log.error("Problem accessing LDAP server: " + e.getMessage());

			if (authenticateRequired(
					companyId, userId, emailAddress, FAILURE) == FAILURE) {

				throw e;
			}
		}

		return SUCCESS;
	}

	protected LDAPAuthResult authenticate(
			LdapContext ctx, Properties env, Binding binding, String baseDN,
			Attribute userPassword, long companyId, String emailAddress,
			String screenName, long userId, String password)
		throws Exception {

		LDAPAuthResult ldapAuthResult = new LDAPAuthResult();

		// Check passwords by either doing a comparison between the passwords or
		// by binding to the LDAP server.  If using LDAP password policies, bind
		// auth method must be used in order to get the result control codes.

		String authMethod = PrefsPropsUtil.getString(
			companyId, PropsUtil.LDAP_AUTH_METHOD);

		String userDN = binding.getName() + StringPool.COMMA + baseDN;

		if (authMethod.equals(AUTH_METHOD_BIND)) {
			try {

				env.put(Context.SECURITY_PRINCIPAL, userDN);
				env.put(Context.SECURITY_CREDENTIALS, password);

				ctx = new InitialLdapContext(env, null);

				// Get LDAP bind results

				Control[] responseControls =  ctx.getResponseControls();

				ldapAuthResult.setAuthenticated(true);
				ldapAuthResult.setResponseControl(responseControls);
			}
			catch (Exception e) {
				_log.error(
					"Failed to bind to the LDAP server with userDN " + userDN +
						" and password " + password + ": " + e.getMessage());

				ldapAuthResult.setAuthenticated(false);
				ldapAuthResult.setErrorMessage(e.getMessage());
			}
		}
		else if (authMethod.equals(AUTH_METHOD_PASSWORD_COMPARE)) {
			if (userPassword != null) {
				String ldapPassword = new String((byte[])userPassword.get());

				String encryptedPassword = password;

				String algorithm = PrefsPropsUtil.getString(
					companyId,
					PropsUtil.LDAP_AUTH_PASSWORD_ENCRYPTION_ALGORITHM);

				if (Validator.isNotNull(algorithm)) {
					encryptedPassword =
						"{" + algorithm + "}" +
							PwdEncryptor.encrypt(
								algorithm, password, ldapPassword);
				}

				if (ldapPassword.equals(encryptedPassword)) {
					ldapAuthResult.setAuthenticated(true);
				}
				else {
					ldapAuthResult.setAuthenticated(false);

					_log.error(
						"LDAP password " + ldapPassword +
							" does not match with given password " +
								encryptedPassword + " for user id " + userId);
				}
			}
		}

		return ldapAuthResult;
	}

	protected int authenticateOmniadmin(
			long companyId, String emailAddress, long userId)
		throws Exception {

		// Only allow omniadmin if Liferay password checking is enabled

		if (GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.AUTH_PIPELINE_ENABLE_LIFERAY_CHECK))) {

			if (userId > 0) {
				if (OmniadminUtil.isOmniadmin(userId)) {
					return SUCCESS;
				}
			}
			else if (Validator.isNotNull(emailAddress)) {
				try {
					User user = UserLocalServiceUtil.getUserByEmailAddress(
						companyId, emailAddress);

					if (OmniadminUtil.isOmniadmin(user.getUserId())) {
						return SUCCESS;
					}
				}
				catch (NoSuchUserException nsue) {
				}
			}
		}

		return FAILURE;
	}

	protected int authenticateRequired(
			long companyId, long userId, String emailAddress, int failureCode)
		throws Exception {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsUtil.LDAP_AUTH_REQUIRED)) {

			return failureCode;
		}
		else {
			return SUCCESS;
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
		long organizationId = 0;
		long locationId = 0;
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

	private static Log _log = LogFactoryUtil.getLog(LDAPAuth.class);

}