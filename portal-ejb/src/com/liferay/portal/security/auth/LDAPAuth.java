/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.ldap.LDAPImportUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.admin.util.OmniadminUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.LDAPUtil;
import com.liferay.util.LogUtil;
import com.liferay.util.PropertiesUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

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
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LDAPAuth.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LDAPAuth implements Authenticator {

	public int authenticateByEmailAddress(
			String companyId, String emailAddress, String password,
			Map headerMap, Map parameterMap)
		throws AuthException {

		try {
			return authenticate(
				companyId, emailAddress, StringPool.BLANK, password);
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));

			throw new AuthException(e);
		}
	}

	public int authenticateByUserId(
			String companyId, String userId, String password, Map headerMap,
			Map parameterMap)
		throws AuthException {

		try {
			return authenticate(companyId, StringPool.BLANK, userId, password);
		}
		catch (Exception e) {
			_log.error(StackTraceUtil.getStackTrace(e));

			throw new AuthException(e);
		}
	}

	protected int authenticate(
			String companyId, String emailAddress, String userId,
			String password)
		throws Exception {

		boolean enabled = PrefsPropsUtil.getBoolean(
			companyId, PropsUtil.AUTH_IMPL_LDAP_ENABLED);

		if (!enabled) {
			if (_log.isDebugEnabled()) {
				_log.debug("Authenticator is not enabled");
			}

			return SUCCESS;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Authenticator is enabled");
		}

		// always allow Omniadmin
		if (Validator.isNotNull(userId)) {
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

		Properties env = new Properties();

		String baseProviderURL = PrefsPropsUtil.getString(
			companyId, PropsUtil.AUTH_IMPL_LDAP_BASE_PROVIDER_URL);

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsUtil.AUTH_IMPL_LDAP_BASE_DN);

		env.put(
			Context.INITIAL_CONTEXT_FACTORY,
			PrefsPropsUtil.getString(
				companyId, PropsUtil.AUTH_IMPL_LDAP_FACTORY_INITIAL));
		env.put(
			Context.PROVIDER_URL,
			LDAPUtil.getFullProviderURL(baseProviderURL, baseDN));
		env.put(
			Context.SECURITY_PRINCIPAL,
			PrefsPropsUtil.getString(
				companyId, PropsUtil.AUTH_IMPL_LDAP_SECURITY_PRINCIPAL));
		env.put(
			Context.SECURITY_CREDENTIALS,
			PrefsPropsUtil.getString(
				companyId, PropsUtil.AUTH_IMPL_LDAP_SECURITY_CREDENTIALS));

		if (_log.isDebugEnabled()) {
			LogUtil.log(_log, env);
		}

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
			companyId, PropsUtil.AUTH_IMPL_LDAP_SEARCH_FILTER);

		if (_log.isDebugEnabled()) {
			_log.debug("Search filter before transformation " + filter);
		}

		filter = StringUtil.replace(
			filter,
			new String[] {
				"@company_id@", "@email_address@", "@user_id@"
			},
			new String[] {
				companyId, emailAddress, userId
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

				Properties userMappings = PropertiesUtil.load(
					PrefsPropsUtil.getString(
						companyId, PropsUtil.AUTH_IMPL_LDAP_USER_MAPPINGS));

				if (_log.isDebugEnabled()) {
					LogUtil.log(_log, userMappings);
				}

				Attribute userPassword = attrs.get("userPassword");
				
				boolean authenticated = 
					_authenticate(ctx, env, binding, baseDN, userPassword, 
						password, companyId, userId, emailAddress);
				
				if (!authenticated) {
					return _authenticateRequired(
						companyId, userId, emailAddress, FAILURE);		
				}

				_processUser(attrs, userMappings, companyId, emailAddress, 
					userId, password);
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Search filter did not return any results");
				}

				return _authenticateRequired(
					companyId, userId, emailAddress, DNE);
			}
		}
		catch (Exception e) {
			_log.warn("Problem accessing LDAP server");

			return _authenticateRequired(
				companyId, userId, emailAddress, FAILURE);
		}

		return SUCCESS;
	}

	private static void _processUser(Attributes attrs, Properties userMappings, 
			String companyId, String emailAddress, String userId, 
			String password) 
		throws Exception {

		String creatorUserId = null;
		boolean autoUserId = false;

		if (Validator.isNull(userId)) {
			userId = LDAPUtil.getAttributeValue(
				attrs, userMappings.getProperty("userId"));
		}

		boolean autoPassword = false;
		String password1 = password;
		String password2 = password;
		boolean passwordReset = false;

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

		String nickName = null;
		String prefixId = null;
		String suffixId = null;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("jobTitle"));
		String organizationId = null;
		String locationId = null;
		boolean sendEmail = false;
		
		LDAPImportUtil.addOrUpdateUser(
			creatorUserId, companyId, autoUserId, userId, autoPassword,
			password1, password2, passwordReset, emailAddress, locale,
			firstName, middleName, lastName, nickName, prefixId,
			suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
			jobTitle, organizationId, locationId, sendEmail, true,
			false);	
	}
	
	private static boolean _authenticate(LdapContext ctx, Properties env, 
			Binding binding, String baseDN, Attribute userPassword, String password, 
			String companyId, String userId, String emailAddress) 
		throws Exception {

		// Check passwords by either doing a comparison between the
		// passwords or by binding to the LDAP server
		if (userPassword != null) {
			String ldapPassword =
				new String((byte[])userPassword.get());

			String encryptedPassword = password;

			String algorithm = PrefsPropsUtil.getString(
				companyId,
				PropsUtil.AUTH_IMPL_LDAP_PASSWORD_ENCRYPTION_ALGORITHM);

			if (Validator.isNotNull(algorithm)) {
				encryptedPassword =
					"{" + algorithm + "}" +
						Encryptor.digest(algorithm, password);
			}

			if (!ldapPassword.equals(encryptedPassword)) {
				_log.error(
					"LDAP password " + ldapPassword +
						" does not match with given password " +
							encryptedPassword + " for user id " + userId);

				return false;
			}
		}
		else {
			try {
				String userDN =
					binding.getName() + StringPool.COMMA + baseDN;

				env.put(Context.SECURITY_PRINCIPAL, userDN);
				env.put(Context.SECURITY_CREDENTIALS, password);

				ctx = new InitialLdapContext(env, null);
			}
			catch (Exception e) {
				_log.error(
					"Failed to bind to the LDAP server with " + userId +
						" " + password, e);

				return false;
			}
		}
		
		return true;
	}
	
	private static int _authenticateRequired(
			String companyId, String userId, String emailAddress,
			int failureCode)
		throws Exception {

		if (PrefsPropsUtil.getBoolean(
			companyId, PropsUtil.AUTH_IMPL_LDAP_REQUIRED)) {
			
			return failureCode;
		}
		else {
			return SUCCESS;
		}
	}

	private static Log _log = LogFactory.getLog(LDAPAuth.class);

}