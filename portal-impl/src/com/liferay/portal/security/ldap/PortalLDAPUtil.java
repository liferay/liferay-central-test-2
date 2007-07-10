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

package com.liferay.portal.security.ldap;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.PropertiesUtil;
import com.liferay.util.Validator;
import com.liferay.util.ldap.LDAPUtil;
import com.liferay.util.ldap.Modifications;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * <a href="PortalLDAPUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 * @author Brian Wing Shun Chan
 * @author Jerry Niu
 * @author Scott Lee
 * @author Hervé Ménage
 *
 */
public class PortalLDAPUtil {

	public static final String IMPORT_BY_USER = "user";

	public static final String IMPORT_BY_GROUP = "group";

	public static void exportToLDAP(Contact contact) throws Exception {
		long companyId = contact.getCompanyId();

		if (!isAuthEnabled(companyId) || !isExportEnabled(companyId)) {
			return;
		}

		LdapContext ctx = getContext(companyId);

		if (ctx == null) {
			return;
		}

		Properties userMappings = getUserMappings(companyId);

		User user = UserLocalServiceUtil.getUserById(contact.getUserId());

		String name =
			userMappings.getProperty("screenName") + StringPool.EQUAL +
				user.getScreenName() + StringPool.COMMA + getUsersDN(companyId);

		if (!hasUser(companyId, user.getScreenName())) {
			LDAPUser ldapUser = (LDAPUser)Class.forName(
				PropsUtil.get(PropsUtil.LDAP_USER_IMPL)).newInstance();

			ldapUser.setUser(user);

			ctx.bind(name, ldapUser);
		}
		else {
			Modifications mods = Modifications.getInstance();

			mods.addItem(
				userMappings.getProperty("firstName"), contact.getFirstName());
			mods.addItem(
				userMappings.getProperty("lastName"), contact.getLastName());

			ModificationItem[] modItems = mods.getItems();

			ctx.modifyAttributes(name, modItems);
		}
	}

	public static void exportToLDAP(User user) throws Exception {
		long companyId = user.getCompanyId();

		if (!isAuthEnabled(companyId) || !isExportEnabled(companyId)) {
			return;
		}

		LdapContext ctx = getContext(companyId);

		if (ctx == null) {
			return;
		}

		Properties userMappings = getUserMappings(companyId);

		String name =
			userMappings.getProperty("screenName") + StringPool.EQUAL +
				user.getScreenName() + StringPool.COMMA + getUsersDN(companyId);

		if (!hasUser(companyId, user.getScreenName())) {
			LDAPUser ldapUser = (LDAPUser)Class.forName(
				PropsUtil.get(PropsUtil.LDAP_USER_IMPL)).newInstance();

			ldapUser.setUser(user);

			ctx.bind(name, ldapUser);
		}
		else {
			Modifications mods = Modifications.getInstance();

			if (user.isPasswordModified() &&
				Validator.isNotNull(user.getPasswordUnencrypted())) {

				mods.addItem(
					userMappings.getProperty("password"),
					user.getPasswordUnencrypted());
			}

			mods.addItem(
				userMappings.getProperty("emailAddress"),
				user.getEmailAddress());

			ModificationItem[] modItems = mods.getItems();

			ctx.modifyAttributes(name, modItems);
		}
	}

	public static LdapContext getContext(long companyId) throws Exception {
		Properties env = new Properties();

		env.put(
			Context.INITIAL_CONTEXT_FACTORY,
			PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_FACTORY_INITIAL));
		env.put(
			Context.PROVIDER_URL,
			PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_BASE_PROVIDER_URL));
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
			if (_log.isWarnEnabled()) {
				_log.warn("Failed to bind to the LDAP server");
			}

			if (_log.isDebugEnabled()) {
				_log.debug(e);
			}
		}

		return ctx;
	}

	public static Properties getGroupMappings(long companyId)
		throws Exception {

		Properties groupMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(companyId, PropsUtil.LDAP_GROUP_MAPPINGS));

		LogUtil.debug(_log, groupMappings);

		return groupMappings;
	}

	public static Binding getUser(long companyId, String screenName)
		throws Exception {

		LdapContext ctx = getContext(companyId);

		if (ctx == null) {
			return null;
		}

		Properties userMappings = getUserMappings(companyId);

		String name = getUsersDN(companyId);
		String filter =
			"(" + userMappings.getProperty("screenName") + "=" +
				screenName + ")";
		SearchControls cons = new SearchControls(
			SearchControls.SUBTREE_SCOPE, 1, 0, null, false, false);

		NamingEnumeration enu = ctx.search(name, filter, cons);

		if (enu.hasMore()) {
			Binding binding = (Binding)enu.next();

			return binding;
		}
		else {
			return null;
		}
	}

	public static Properties getUserMappings(long companyId) throws Exception {
		Properties userMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(companyId, PropsUtil.LDAP_USER_MAPPINGS));

		LogUtil.debug(_log, userMappings);

		return userMappings;
	}

	public static String getUsersDN(long companyId) throws Exception {
		return PrefsPropsUtil.getString(companyId, PropsUtil.LDAP_USERS_DN);
	}

	public static boolean hasUser(long companyId, String screenName)
		throws Exception {

		if (getUser(companyId, screenName) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void importFromLDAP() throws Exception {
		List companies = CompanyLocalServiceUtil.getCompanies();

		for (int i = 0; i < companies.size(); i++) {
			Company company = (Company)companies.get(i);

			importFromLDAP(company.getCompanyId());
		}
	}

	public static void importFromLDAP(long companyId) throws Exception {
		if (!isImportEnabled(companyId)) {
			return;
		}

		LdapContext ctx = getContext(companyId);

		if (ctx == null) {
			return;
		}

		try {
			String importMethod = PrefsPropsUtil.getString(
				companyId, PropsUtil.LDAP_IMPORT_METHOD);

			if (importMethod.equals(IMPORT_BY_USER)) {
				String baseDN = PrefsPropsUtil.getString(
					companyId, PropsUtil.LDAP_BASE_DN);
				String filter = PrefsPropsUtil.getString(
					companyId, PropsUtil.LDAP_IMPORT_USER_SEARCH_FILTER);
				SearchControls cons = new SearchControls(
					SearchControls.SUBTREE_SCOPE, 0, 0, null, false, false);

				NamingEnumeration enu = ctx.search(baseDN, filter, cons);

				// Loop through all users in LDAP

				while (enu.hasMore()) {
					SearchResult result = (SearchResult)enu.next();

					importLDAPUser(companyId, ctx, result.getAttributes(),
						true);
				}
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

	public static User importLDAPUser(
			long companyId, LdapContext ctx, Attributes attrs,
			boolean importGroupMembership)
		throws Exception {

		Properties userMappings = getUserMappings(companyId);

		LogUtil.debug(_log, userMappings);

		long creatorUserId = 0;
		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		boolean passwordReset = false;
		boolean autoScreenName = false;
		String screenName = LDAPUtil.getAttributeValue(
			attrs, userMappings.getProperty("screenName")).toLowerCase();
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

		User user = importLDAPUser(
			creatorUserId, companyId, autoPassword, password1, password2,
			passwordReset, autoScreenName, screenName, emailAddress, locale,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, organizationId,
			locationId, sendEmail, true, false);

		// Import user groups and membership

		if (importGroupMembership && user != null) {
			Attribute attr = attrs.get(userMappings.getProperty("group"));

			if (attr != null){
				_importGroupsAndMembershipFromLDAPUser(
					companyId, ctx, user.getUserId(), attr);
			}
		}

		return user;
	}

	public static User importLDAPUser(
			long creatorUserId, long companyId, boolean autoPassword,
			String password1, String password2, boolean passwordReset,
			boolean autoScreenName, String screenName, String emailAddress,
			Locale locale, String firstName, String middleName, String lastName,
			int prefixId, int suffixId, boolean male, int birthdayMonth,
			int birthdayDay, int birthdayYear, String jobTitle,
			long organizationId, long locationId, boolean sendEmail,
			boolean checkExists, boolean updatePassword)
		throws PortalException, SystemException {

		User user = null;

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Screen name " + screenName + " and email address " +
					emailAddress);
		}

		if (Validator.isNull(screenName) || Validator.isNull(emailAddress)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Cannot add user because screen name and email address " +
						"are required");
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
						user.getUserId(), password1, password2, passwordReset,
						true);
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
					creatorUserId, companyId, autoPassword, password1,
					password2, autoScreenName, screenName, emailAddress, locale,
					firstName, middleName, lastName, prefixId, suffixId, male,
					birthdayMonth, birthdayDay, birthdayYear, jobTitle,
					organizationId, locationId, sendEmail);
			}
			catch (Exception e){
				_log.error(
					"Problem adding user with screen name " + screenName +
						" and email address " + emailAddress,
					e);
			}
		}

		return user;
	}

	public static boolean isAuthEnabled(long companyId)
		throws PortalException, SystemException {

		if (PrefsPropsUtil.getBoolean(companyId, PropsUtil.LDAP_AUTH_ENABLED)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isExportEnabled(long companyId)
		throws PortalException, SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsUtil.LDAP_EXPORT_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isImportEnabled(long companyId)
		throws PortalException, SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsUtil.LDAP_IMPORT_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isImportOnStartup(long companyId)
		throws PortalException, SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsUtil.LDAP_IMPORT_ON_STARTUP)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isNtlmEnabled(long companyId)
		throws PortalException, SystemException {

		if (!isAuthEnabled(companyId)) {
			return false;
		}

		if (PrefsPropsUtil.getBoolean(companyId, PropsUtil.NTLM_AUTH_ENABLED)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isPasswordPolicyEnabled(long companyId)
		throws PortalException, SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsUtil.LDAP_PASSWORD_POLICY_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	private static void _importGroupsAndMembershipFromLDAPUser(
			long companyId, LdapContext ctx, long userId, Attribute attr)
		throws Exception {

		Properties groupMappings = getGroupMappings(companyId);

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			companyId);

		for (int i = 0; i < attr.size(); i++) {
			String groupDN = (String)attr.get(i);

			Attributes groupAttrs = ctx.getAttributes(groupDN);

			String groupName = LDAPUtil.getAttributeValue(
				groupAttrs, groupMappings.getProperty("groupName"));
			String description = LDAPUtil.getAttributeValue(
				groupAttrs, groupMappings.getProperty("description"));

			// Get associated user group

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
						defaultUserId, companyId, groupName, description);
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

			// Add user to user group

			if (userGroup != null) {
				if (_log.isDebugEnabled()) {
					_log.debug("Adding " + userId + " to group " + groupName);
				}

				UserLocalServiceUtil.addUserGroupUsers(
					userGroup.getUserGroupId(), new long[] {userId});
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortalLDAPUtil.class);

}