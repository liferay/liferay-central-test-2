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

package com.liferay.portal.security.ldap;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.spring.CompanyLocalServiceUtil;
import com.liferay.portal.service.spring.UserGroupLocalServiceUtil;
import com.liferay.portal.service.spring.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.LDAPUtil;
import com.liferay.util.PropertiesUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * <a href="LDAPImportUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Michael Young
 *
 */
public class LDAPImportUtil {

	public static User addOrUpdateUser(
			String creatorUserId, String companyId, boolean autoUserId,
			String userId, boolean autoPassword, String password1,
			String password2, boolean passwordReset, String emailAddress,
			Locale locale, String firstName, String middleName, String lastName,
			String nickName, String prefixId, String suffixId, boolean male,
			int birthdayMonth, int birthdayDay, int birthdayYear,
			String jobTitle, String organizationId, String locationId,
			boolean sendEmail, boolean checkExists, boolean updatePassword)
		throws PortalException, SystemException {

		User user = null;

		if (_log.isDebugEnabled()) {
			_log.debug(
				"User Id " + userId + " and email address " + emailAddress);
		}

		if (Validator.isNull(userId) || Validator.isNull(emailAddress)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Cannot add user because user id and email address are " +
						"required");
			}

			return user;
		}

		boolean create = true;

		if (checkExists) {
			try {
				user = UserLocalServiceUtil.getUserByEmailAddress(
					companyId, emailAddress);

				if (updatePassword) {
					UserLocalServiceUtil.updatePassword(
						user.getUserId(), password1, password2, passwordReset);
				}

				create = false;
			}
			catch (NoSuchUserException nsue) {

				// User does not exist so create

			}
		}

		if (create) {
			try {
				user = UserLocalServiceUtil.addUser(
					creatorUserId, companyId, autoUserId, userId, autoPassword,
					password1, password2, passwordReset, emailAddress, locale,
					firstName, middleName, lastName, nickName, prefixId,
					suffixId, male, birthdayMonth, birthdayDay, birthdayYear,
					jobTitle, organizationId, locationId, sendEmail);
			}
			catch (Exception e){
				if (_log.isWarnEnabled()) {
					_log.warn("Problem adding user " + userId);
				}

				if (_log.isDebugEnabled()) {
					_log.debug(StackTraceUtil.getStackTrace(e));
				}
			}
		}

		return user;
	}

	public static void importLDAP() throws Exception {
		List companies = CompanyLocalServiceUtil.getCompanies();

		for (int i = 0; i < companies.size(); i++) {
			Company company = (Company)companies.get(i);

			if (PrefsPropsUtil.getBoolean(
					company.getCompanyId(), PropsUtil.LDAP_IMPORT_ENABLED)) {

				importLDAP(company.getCompanyId());
			}
		}
	}

	public static void importLDAP(String companyId) throws Exception {
		Properties env = new Properties();

		env.put(
			Context.INITIAL_CONTEXT_FACTORY,
			PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_IMPORT_FACTORY_INITIAL));
		env.put(
			Context.PROVIDER_URL,
			PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_IMPORT_BASE_PROVIDER_URL));
		env.put(
			Context.SECURITY_PRINCIPAL,
			PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_IMPORT_SECURITY_PRINCIPAL));
		env.put(
			Context.SECURITY_CREDENTIALS,
			PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_IMPORT_SECURITY_CREDENTIALS));

		LogUtil.debug(_log, env);

		LdapContext ctx = null;

		try {
			ctx = new InitialLdapContext(env, null);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Failed to bind to the LDAP server");
			}

			if (_log.isDebugEnabled()) {
				_log.debug(e);
			}

			return;
		}

		Properties userMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_IMPORT_USER_MAPPINGS));

		LogUtil.debug(_log, userMappings);

		Properties groupMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_IMPORT_GROUP_MAPPINGS));

		LogUtil.debug(_log, groupMappings);

		try {
			String filter = PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_IMPORT_SEARCH_FILTER);

			String context = PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_IMPORT_BASE_DN);

			SearchControls cons = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 0, 0, null, false, false);

			NamingEnumeration enu = ctx.search(context, filter, cons);

			while (enu.hasMore()) {
				SearchResult result = (SearchResult)enu.next();

				_importLDAP(
					companyId, ctx, userMappings, groupMappings, result);
			}
		}
		catch (Exception e) {
			_log.error("Error importing LDAP users and groups", e);
		}
		finally {
			if (ctx != null) {
				ctx.close();
			}
		}
	}

	private static void _importLDAP(
			String companyId, LdapContext ctx, Properties userMappings,
			Properties groupMappings, SearchResult result)
		throws Exception {

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsUtil.LDAP_IMPORT_BASE_DN);

		String userDN = result.getName() + StringPool.COMMA + baseDN;

		if (_log.isDebugEnabled()) {
			_log.debug("User DN " + userDN);
		}

		Attributes attrs = result.getAttributes();

		String creatorUserId = null;
		boolean autoUserId = false;
		String userId = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("userId"));
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean passwordReset = false;
		String emailAddress = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("emailAddress"));
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

		User user = addOrUpdateUser(
			creatorUserId, companyId, autoUserId, userId, autoPassword,
			password1, password2, passwordReset, emailAddress, locale,
			firstName, middleName, lastName, nickName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, organizationId,
			locationId, sendEmail, true, false);

		// Import and add user to group

		if (user != null) {
			Attribute attr = attrs.get(userMappings.getProperty("group"));

			if (attr != null){
				_importLDAPGroup(companyId, ctx, groupMappings, userId, attr);
			}
		}
	}

	private static void _importLDAPGroup(
		String companyId, LdapContext ctx, Properties groupMappings,
		String userId, Attribute attr) throws Exception {

		for (int i = 0; i < attr.size(); i++) {
			String groupDN = (String)attr.get(i);

			Attributes groupAttrs = ctx.getAttributes(groupDN);

			String groupName = LDAPUtil.getAttributeValue(
				groupAttrs, groupMappings.getProperty("groupName"));
			String description = LDAPUtil.getAttributeValue(
				groupAttrs, groupMappings.getProperty("description"));

			UserGroup userGroup = null;

			try {
				userGroup = UserGroupLocalServiceUtil.getUserGroup(
					companyId, groupName);
			}
			catch (NoSuchUserGroupException nsuge) {
				try {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Adding user group" + groupName + " at " + groupDN);
					}

					userGroup = UserGroupLocalServiceUtil.addUserGroup(
						User.getDefaultUserId(companyId), companyId, groupName,
						description);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn("Could not create user group " + groupName);
					}

					if (_log.isDebugEnabled()) {
						_log.debug(e);
					}
				}
			}

			if (userGroup != null) {
				if (_log.isDebugEnabled()) {
					_log.debug("Adding " + userId + " to group " + groupName);
				}

				UserLocalServiceUtil.addUserGroupUsers(
					userGroup.getUserGroupId(), new String[] {userId});
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LDAPImportUtil.class);

}