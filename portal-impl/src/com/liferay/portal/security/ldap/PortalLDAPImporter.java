/*
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

package com.liferay.portal.security.ldap;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ldap.LDAPUtil;

import javax.naming.Binding;
import javax.naming.NameNotFoundException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class PortalLDAPImporter {
	public static final String IMPORT_BY_GROUP = "group";
	public static final String IMPORT_BY_USER = "user";

	public static void importFromLDAP() throws Exception {
			List<Company> companies = CompanyLocalServiceUtil.getCompanies(false);

			for (Company company : companies) {
				PortalLDAPImporter.importFromLDAP(company.getCompanyId());
			}
		}

	public static void importFromLDAP(long companyId) throws Exception {
		if (!LDAPSettingsUtil.isImportEnabled(companyId)) {
			return;
		}

		long[] ldapServerIds = StringUtil.split(
			PrefsPropsUtil.getString(companyId, "ldap.server.ids"), 0L);

		for (long ldapServerId : ldapServerIds) {
			importFromLDAP(ldapServerId, companyId);
		}
	}

	public static void importFromLDAP(long ldapServerId, long companyId)
		throws Exception {

		if (!LDAPSettingsUtil.isImportEnabled(companyId)) {
			return;
		}

		LdapContext ctx = PortalLDAPUtil.getContext(ldapServerId, companyId);

		if (ctx == null) {
			return;
		}

		try {
			String importMethod = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_IMPORT_METHOD);

			if (importMethod.equals(IMPORT_BY_USER)) {
				List<SearchResult> results = PortalLDAPUtil.getUsers(
					ldapServerId, companyId, ctx, 0);

				// Loop through all LDAP users

				for (SearchResult result : results) {
					Attributes attributes = PortalLDAPUtil.getUserAttributes(
						ldapServerId, companyId, ctx,
						PortalLDAPUtil.getNameInNamespace(
							ldapServerId, companyId, result));

					importLDAPUser(
						ldapServerId, companyId, ctx, attributes,
						StringPool.BLANK, true);
				}
			}
			else if (importMethod.equals(IMPORT_BY_GROUP)) {
				List<SearchResult> results = PortalLDAPUtil.getGroups(
					ldapServerId, companyId, ctx, 0);

				// Loop through all LDAP groups

				for (SearchResult result : results) {
					Attributes attributes = PortalLDAPUtil.getGroupAttributes(
						ldapServerId, companyId, ctx,
						PortalLDAPUtil.getNameInNamespace(
							ldapServerId, companyId, result),
						true);

					importLDAPGroup(
						ldapServerId, companyId, ctx, attributes,
						true);
				}
			}
		}
		catch (Exception e) {
			_log.error(
				"Error importing LDAP users and groups", e);
		}
		finally {
			if (ctx != null) {
				ctx.close();
			}
		}
	}

	public static UserGroup importLDAPGroup(
		long ldapServerId, long companyId, LdapContext ctx,
		Attributes attributes, boolean importGroupMembership)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		AttributesTransformer attributesTransformer =
			AttributesTransformerFactory.getInstance();

		attributes = attributesTransformer.transformGroup(attributes);

		Properties groupMappings = LDAPSettingsUtil.getGroupMappings(
			ldapServerId, companyId);

		LogUtil.debug(_log, groupMappings);

		String groupName = LDAPUtil.getAttributeValue(
			attributes, groupMappings.getProperty("groupName")).toLowerCase();
		String description = LDAPUtil.getAttributeValue(
			attributes, groupMappings.getProperty("description"));

		// Get or create user group

		UserGroup userGroup = null;

		try {
			userGroup = UserGroupLocalServiceUtil.getUserGroup(
				companyId, groupName);

			UserGroupLocalServiceUtil.updateUserGroup(
				companyId, userGroup.getUserGroupId(), groupName, description);
		}
		catch (NoSuchUserGroupException nsuge) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Adding user group to portal " + groupName);
			}

			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				companyId);

			try {
				userGroup = UserGroupLocalServiceUtil.addUserGroup(
					defaultUserId, companyId, groupName, description);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Could not create user group " + groupName);
				}

				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		// Import users and membership

		if (importGroupMembership && (userGroup != null)) {
			Attribute attribute = attributes.get(
				groupMappings.getProperty("user"));

			if (attribute != null) {
				String baseDN = PrefsPropsUtil.getString(
					companyId, PropsKeys.LDAP_BASE_DN + postfix);

				StringBuilder sb = new StringBuilder();

				sb.append("(&");
				sb.append(
					PrefsPropsUtil.getString(
						companyId,
						PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER + postfix));
				sb.append("(");
				sb.append(groupMappings.getProperty("groupName"));
				sb.append("=");
				sb.append(
					LDAPUtil.getAttributeValue(
						attributes, groupMappings.getProperty("groupName")));
				sb.append("))");

				attribute = PortalLDAPUtil.getMultivaluedAttribute(
					companyId, ctx, baseDN, sb.toString(), attribute);

				_importUsersAndMembershipFromLDAPGroup(
					ldapServerId, companyId, ctx, userGroup.getUserGroupId(),
					attribute);
			}
		}

		return userGroup;
	}

	public static User importLDAPUser(
		long ldapServerId, long companyId, LdapContext ctx,
		Attributes attributes, String password,
		boolean importGroupMembership)
		throws Exception {

		LDAPUserTransactionThreadLocal.setOriginatesFromLDAP(true);

		try {
			return _importLDAPUser(
				ldapServerId, companyId, ctx, attributes, password,
				importGroupMembership);
		}
		finally {
			LDAPUserTransactionThreadLocal.setOriginatesFromLDAP(false);
		}
	}

	private static User _importLDAPUser(
		long ldapServerId, long companyId, LdapContext ctx,
		Attributes attributes, String password,
		boolean importGroupMembership)
		throws Exception {

		AttributesTransformer attributesTransformer =
			AttributesTransformerFactory.getInstance();

		attributes = attributesTransformer.transformUser(attributes);

		Properties userMappings = LDAPSettingsUtil.getUserMappings(
			ldapServerId, companyId);

		LogUtil.debug(_log, userMappings);

		User defaultUser = UserLocalServiceUtil.getDefaultUser(companyId);

		boolean autoPassword = false;
		boolean updatePassword = true;

		if (password.equals(StringPool.BLANK)) {
			autoPassword = true;
			updatePassword = false;
		}

		long creatorUserId = 0;
		boolean passwordReset = false;
		boolean autoScreenName = false;
		String screenName = LDAPUtil.getAttributeValue(
			attributes, userMappings.getProperty("screenName")).toLowerCase();
		String emailAddress = LDAPUtil.getAttributeValue(
			attributes, userMappings.getProperty("emailAddress"));
		String openId = StringPool.BLANK;
		Locale locale = defaultUser.getLocale();
		String firstName = LDAPUtil.getAttributeValue(
			attributes, userMappings.getProperty("firstName"));
		String middleName = LDAPUtil.getAttributeValue(
			attributes, userMappings.getProperty("middleName"));
		String lastName = LDAPUtil.getAttributeValue(
			attributes, userMappings.getProperty("lastName"));

		if (Validator.isNull(firstName) || Validator.isNull(lastName)) {
			String fullName = LDAPUtil.getAttributeValue(
				attributes, userMappings.getProperty("fullName"));

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
			attributes, userMappings.getProperty("jobTitle"));
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		List<UserGroupRole> userGroupRoles = null;
		long[] userGroupIds = null;
		boolean sendEmail = false;
		ServiceContext serviceContext = new ServiceContext();

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

			return null;
		}

		User user = null;

		try {

			// Find corresponding portal user

			String authType = PrefsPropsUtil.getString(
				companyId, PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
				PropsValues.COMPANY_SECURITY_AUTH_TYPE);

			if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				user = UserLocalServiceUtil.getUserByScreenName(
					companyId, screenName);
			}
			else {
				user = UserLocalServiceUtil.getUserByEmailAddress(
					companyId, emailAddress);
			}

			// Skip if is default user

			if (user.isDefaultUser()) {
				return user;
			}

			// User already exists in the Liferay database. Skip import if user
			// fields have been already synced, if import is part of a scheduled
			// import, or if the LDAP entry has never been modified.

			Date ldapUserModifiedDate = null;

			String modifiedDate = LDAPUtil.getAttributeValue(
				attributes, "modifyTimestamp");

			try {
				if (Validator.isNull(modifiedDate)) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"LDAP entry never modified, skipping user " +
							user.getEmailAddress());
					}

					return user;
				}
				else {
					DateFormat dateFormat =
						DateFormatFactoryUtil.getSimpleDateFormat(
							"yyyyMMddHHmmss");

					ldapUserModifiedDate = dateFormat.parse(modifiedDate);
				}

				if (ldapUserModifiedDate.equals(user.getModifiedDate()) &&
					autoPassword) {

					if (_log.isDebugEnabled()) {
						_log.debug(
							"User is already syncronized, skipping user " +
							user.getEmailAddress());
					}

					return user;
				}
			}
			catch (ParseException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to parse LDAP modify timestamp " +
						modifiedDate);
				}

				_log.debug(pe, pe);
			}

			// LPS-443

			if (Validator.isNull(screenName)) {
				autoScreenName = true;
			}

			if (autoScreenName) {
				ScreenNameGenerator screenNameGenerator =
					(ScreenNameGenerator) InstancePool.get(
						PropsValues.USERS_SCREEN_NAME_GENERATOR);

				screenName = screenNameGenerator.generate(
					companyId, user.getUserId(), emailAddress);
			}

			Contact contact = user.getContact();

			Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

			birthdayCal.setTime(contact.getBirthday());

			birthdayMonth = birthdayCal.get(Calendar.MONTH);
			birthdayDay = birthdayCal.get(Calendar.DATE);
			birthdayYear = birthdayCal.get(Calendar.YEAR);

			// User exists so update user information

			if (updatePassword) {
				user = UserLocalServiceUtil.updatePassword(
					user.getUserId(), password, password, passwordReset, true);
			}

			user = UserLocalServiceUtil.updateUser(
				user.getUserId(), password, StringPool.BLANK, StringPool.BLANK,
				user.isPasswordReset(), user.getReminderQueryQuestion(),
				user.getReminderQueryAnswer(), screenName, emailAddress, openId,
				user.getLanguageId(), user.getTimeZoneId(), user.getGreeting(),
				user.getComments(), firstName, middleName, lastName,
				contact.getPrefixId(), contact.getSuffixId(), contact.getMale(),
				birthdayMonth, birthdayDay, birthdayYear, contact.getSmsSn(),
				contact.getAimSn(), contact.getFacebookSn(), contact.getIcqSn(),
				contact.getJabberSn(), contact.getMsnSn(),
				contact.getMySpaceSn(), contact.getSkypeSn(),
				contact.getTwitterSn(), contact.getYmSn(), jobTitle, groupIds,
				organizationIds, roleIds, userGroupRoles, userGroupIds,
				serviceContext);

			if (ldapUserModifiedDate != null) {
				UserLocalServiceUtil.updateModifiedDate(
					user.getUserId(), ldapUserModifiedDate);
			}
		}
		catch (NoSuchUserException nsue) {

			// User does not exist so create

		}
		catch (Exception e) {
			_log.error(
				"Error updating user with screen name " + screenName +
				" and email address " + emailAddress,
				e);

			return null;
		}

		if (user == null) {
			try {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Adding user to portal " + emailAddress);
				}

				user = UserLocalServiceUtil.addUser(
					creatorUserId, companyId, autoPassword, password, password,
					autoScreenName, screenName, emailAddress, openId, locale,
					firstName, middleName, lastName, prefixId, suffixId, male,
					birthdayMonth, birthdayDay, birthdayYear, jobTitle,
					groupIds, organizationIds, roleIds, userGroupIds, sendEmail,
					serviceContext);
			}
			catch (Exception e) {
				_log.error(
					"Problem adding user with screen name " + screenName +
					" and email address " + emailAddress,
					e);
			}
		}

		// Import user groups and membership

		if (importGroupMembership && (user != null)) {
			String userMappingsGroup = userMappings.getProperty("group");

			if (userMappingsGroup != null) {
				Attribute attribute = attributes.get(userMappingsGroup);

				if (attribute != null) {
					attribute.clear();

					Properties groupMappings = LDAPSettingsUtil
						.getGroupMappings(
							ldapServerId, companyId);

					String postfix = LDAPSettingsUtil.getPropertyPostfix(
						ldapServerId);

					String baseDN = PrefsPropsUtil.getString(
						companyId, PropsKeys.LDAP_BASE_DN + postfix);

					Binding binding = PortalLDAPUtil.getUser(
						ldapServerId, companyId, screenName);

					String fullUserDN = PortalLDAPUtil.getNameInNamespace(
						ldapServerId, companyId, binding);

					StringBuilder sb = new StringBuilder();

					sb.append(StringPool.OPEN_PARENTHESIS);
					sb.append(StringPool.AMPERSAND);
					sb.append(
						PrefsPropsUtil.getString(
							companyId,
							PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER +
							postfix));
					sb.append(StringPool.OPEN_PARENTHESIS);
					sb.append(groupMappings.getProperty("user"));
					sb.append(StringPool.EQUAL);
					sb.append(fullUserDN);
					sb.append(StringPool.CLOSE_PARENTHESIS);
					sb.append(StringPool.CLOSE_PARENTHESIS);

					List<SearchResult> results = PortalLDAPUtil.searchLDAP(
						companyId, ctx, 0, baseDN, sb.toString(), null);

					for (SearchResult result : results) {
						String fullGroupDN = PortalLDAPUtil.getNameInNamespace(
							ldapServerId, companyId, result);

						attribute.add(fullGroupDN);
					}

					_importGroupsAndMembershipFromLDAPUser(
						ldapServerId, companyId, ctx, user.getUserId(),
						attribute);
				}
			}
		}

		return user;
	}

	private static void _importUsersAndMembershipFromLDAPGroup(
		long ldapServerId, long companyId, LdapContext ctx,
		long userGroupId, Attribute attr)
		throws Exception {

		List<Long> newUserIds = new ArrayList<Long>(attr.size());

		for (int i = 0; i < attr.size(); i++) {

			// Find user in LDAP

			String fullUserDN = (String) attr.get(i);

			Attributes userAttributes = null;

			try {
				userAttributes = PortalLDAPUtil.getUserAttributes(
					ldapServerId, companyId, ctx,
					fullUserDN);
			}
			catch (NameNotFoundException nnfe) {
				_log.error(
					"LDAP user not found with fullUserDN " + fullUserDN);

				_log.error(nnfe, nnfe);

				continue;
			}

			User user = PortalLDAPImporter.importLDAPUser(
				ldapServerId, companyId, ctx, userAttributes, StringPool.BLANK,
				false);

			// Add user to user group

			if (user != null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Adding " + user.getUserId() + " to group " +
						userGroupId);
				}

				newUserIds.add(user.getUserId());
			}
		}

		UserLocalServiceUtil.setUserGroupUsers(
			userGroupId,
			ArrayUtil.toArray(newUserIds.toArray(new Long[newUserIds.size()])));
	}

	private static void _importGroupsAndMembershipFromLDAPUser(
		long ldapServerId, long companyId, LdapContext ctx, long userId,
		Attribute attr)
		throws Exception {

		List<Long> newUserGroupIds = new ArrayList<Long>(attr.size());

		for (int i = 0; i < attr.size(); i++) {

			// Find group in LDAP

			String fullGroupDN = (String) attr.get(i);

			Attributes groupAttributes = null;

			try {
				groupAttributes = PortalLDAPUtil.getGroupAttributes(
					ldapServerId, companyId, ctx, fullGroupDN);
			}
			catch (NameNotFoundException nnfe) {
				_log.error(
					"LDAP group not found with fullGroupDN " + fullGroupDN);

				_log.error(nnfe, nnfe);

				continue;
			}

			UserGroup userGroup = PortalLDAPImporter.importLDAPGroup(
				ldapServerId, companyId, ctx, groupAttributes, false);

			// Add user to user group

			if (userGroup != null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Adding " + userId + " to group " +
						userGroup.getUserGroupId());
				}

				newUserGroupIds.add(userGroup.getUserGroupId());
			}
		}

		UserGroupLocalServiceUtil.setUserUserGroups(
			userId,
			ArrayUtil.toArray(
				newUserGroupIds.toArray(new Long[newUserGroupIds.size()])));
	}

	private static Log _log = LogFactoryUtil.getLog(PortalLDAPImporter.class);
}