/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.ldap;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.ScreenNameGeneratorFactory;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;
import com.liferay.portlet.expando.util.ExpandoConverterUtil;
import com.liferay.util.ldap.LDAPUtil;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.NameNotFoundException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

/**
 * <a href="PortalLDAPImporterImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortalLDAPImporterImpl implements PortalLDAPImporter {

	public void importFromLDAP() throws Exception {
		List<Company> companies = CompanyLocalServiceUtil.getCompanies(false);

		for (Company company : companies) {
			importFromLDAP(company.getCompanyId());
		}
	}

	public void importFromLDAP(long companyId) throws Exception {
		if (!LDAPSettingsUtil.isImportEnabled(companyId)) {
			return;
		}

		long[] ldapServerIds = StringUtil.split(
			PrefsPropsUtil.getString(companyId, "ldap.server.ids"), 0L);

		for (long ldapServerId : ldapServerIds) {
			importFromLDAP(ldapServerId, companyId);
		}
	}

	public void importFromLDAP(long ldapServerId, long companyId)
		throws Exception {

		if (!LDAPSettingsUtil.isImportEnabled(companyId)) {
			return;
		}

		LdapContext ldapContext = PortalLDAPUtil.getContext(
			ldapServerId, companyId);

		if (ldapContext == null) {
			return;
		}

		try {
			String importMethod = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_IMPORT_METHOD);

			Properties userMappings = LDAPSettingsUtil.getUserMappings(
				ldapServerId, companyId);

			Properties groupMappings = LDAPSettingsUtil.getGroupMappings(
				ldapServerId, companyId);

			Properties userExpandoMappings =
				LDAPSettingsUtil.getUserExpandoMappings(
					ldapServerId, companyId);

			Properties contactMappings = LDAPSettingsUtil.getContactMappings(
				ldapServerId, companyId);

			Properties contactExpandoMappings =
				LDAPSettingsUtil.getContactExpandoMappings(
					ldapServerId, companyId);
			
			if (importMethod.equals(_IMPORT_BY_USER)) {
				List<SearchResult> searchResults = PortalLDAPUtil.getUsers(
					ldapServerId, companyId, ldapContext, 0);

				// Loop through all LDAP users
				for (SearchResult searchResult : searchResults) {
					try {
						Attributes userAttributes =
							PortalLDAPUtil.getUserAttributes(
								ldapServerId, companyId, ldapContext,
								PortalLDAPUtil.getNameInNamespace(
									ldapServerId, companyId, searchResult));

						User user = addUserFromLdap(
							companyId, userMappings, userExpandoMappings,
							contactMappings, contactExpandoMappings,
							userAttributes, StringPool.BLANK);

						importGroupsAndMembershipFromLDAPUser(
							ldapServerId, companyId, ldapContext,
							userAttributes, user, userMappings, groupMappings);
					}
					catch (Exception e) {
						_log.error("Unable to import user " + searchResult, e);
					}
				}
			}
			else if (importMethod.equals(_IMPORT_BY_GROUP)) {
				List<SearchResult> searchResults = PortalLDAPUtil.getGroups(
					ldapServerId, companyId, ldapContext, 0);

				// Loop through all LDAP groups
				for (SearchResult searchResult : searchResults) {
					try {
						Attributes attributes =
							PortalLDAPUtil.getGroupAttributes(
								ldapServerId, companyId, ldapContext,
								PortalLDAPUtil.getNameInNamespace(
									ldapServerId, companyId, searchResult),
								true);

						UserGroup userGroup = addUserGroupFromLdap(
							companyId, attributes, groupMappings);

						Attribute usersAttribute = getUsersInUserGroup(
							ldapServerId, companyId, ldapContext,
							attributes, userGroup, groupMappings);

						if (usersAttribute == null) {
							if (_log.isInfoEnabled()) {
								_log.info(
									"No users found in : " +
									userGroup.getName());
							}
							continue;
						}

						importUsersAndMembershipFromLDAPGroup(
							ldapServerId, companyId, ldapContext,
							userMappings, userExpandoMappings,
							contactMappings, contactExpandoMappings, 
							userGroup.getUserGroupId(),
							usersAttribute);
					}
					catch (Exception e) {
						_log.error(
							"Unable to import group: " + searchResult, e);
					}
				}
			}
		}
		catch (Exception e) {
			_log.error("Error importing LDAP users and groups", e);
		}
		finally {
			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	public User importLDAPUser(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, String password)
		throws Exception {

		Properties userMappings = LDAPSettingsUtil.getUserMappings(
			ldapServerId, companyId);

		Properties userExpandoMappings =
			LDAPSettingsUtil.getUserExpandoMappings(
				ldapServerId, companyId);

		Properties contactMappings = LDAPSettingsUtil.getContactMappings(
			ldapServerId, companyId);

		Properties contactExpandoMappings =
			LDAPSettingsUtil.getContactExpandoMappings(ldapServerId, companyId);

		Properties groupMappings = LDAPSettingsUtil.getGroupMappings(
			ldapServerId, companyId);

		
		User user = addUserFromLdap(
			companyId, userMappings, userExpandoMappings,
			contactMappings, contactExpandoMappings, attributes, password);

		importGroupsAndMembershipFromLDAPUser(
			ldapServerId, companyId, ldapContext, attributes,
			user, userMappings, groupMappings);

		return user;
	}

	public void setLDAPToPortalConverter(
		LDAPToPortalConverter ldapToPortalConverter) {

		_ldapToPortalConverter = ldapToPortalConverter;
	}

	protected User addUserFromLdap(
			long companyId, Properties userMappings,
			Properties userExpandoMappings, Properties contactMappings,
			Properties contactExpandoMappings,
			Attributes attributes, String password)
		throws Exception {

		LDAPUserTransactionThreadLocal.setOriginatesFromLDAP(true);

		try {
			return doAddLdapUser(
				companyId, userMappings, userExpandoMappings,
				contactMappings, contactExpandoMappings, attributes, password);
		}
		finally {
			LDAPUserTransactionThreadLocal.setOriginatesFromLDAP(false);
		}
	}

	protected UserGroup addUserGroupFromLdap(
		long companyId, Attributes attributes, Properties groupMappings)
		throws Exception {

		AttributesTransformer attributesTransformer =
			AttributesTransformerFactory.getInstance();

		attributes = attributesTransformer.transformGroup(attributes);

		LDAPGroup ldapGroup = _ldapToPortalConverter.importLDAPGroup(
			companyId, attributes, groupMappings);

		UserGroup userGroup = null;

		try {
			userGroup = UserGroupLocalServiceUtil.getUserGroup(
				companyId, ldapGroup.getGroupName());

			UserGroupLocalServiceUtil.updateUserGroup(
				companyId, userGroup.getUserGroupId(), ldapGroup.getGroupName(),
				ldapGroup.getDescription());
		}
		catch (NoSuchUserGroupException nsuge) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Adding user group to portal " + ldapGroup.getGroupName());
			}

			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				companyId);

			try {
				userGroup = UserGroupLocalServiceUtil.addUserGroup(
					defaultUserId, companyId, ldapGroup.getGroupName(),
					ldapGroup.getDescription());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to create user group " +
						ldapGroup.getGroupName());
				}

				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		doImportRoleFromLDAPGroup(companyId, ldapGroup, userGroup);

		return userGroup;
	}

	protected User createLiferayUser(
			long companyId, LDAPUser ldapUser, String password)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Adding user to portal " + ldapUser.getEmailAddress());
		}

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(ldapUser.getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		return UserLocalServiceUtil.addUser(
			ldapUser.getCreatorUserId(), companyId,
			ldapUser.isAutoPassword(), password, password,
			ldapUser.isAutoScreenName(), ldapUser.getScreenName(),
			ldapUser.getEmailAddress(), ldapUser.getOpenId(),
			ldapUser.getLocale(), ldapUser.getFirstName(),
			ldapUser.getMiddleName(), ldapUser.getLastName(),
			ldapUser.getPrefixId(), ldapUser.getSuffixId(),
			ldapUser.isMale(), birthdayMonth, birthdayDay, birthdayYear,
			ldapUser.getJobTitle(), ldapUser.getGroupIds(),
			ldapUser.getOrganizationIds(), ldapUser.getRoleIds(),
			ldapUser.getUserGroupIds(), ldapUser.isSendEmail(),
			ldapUser.getServiceContext());
	}

	protected User doAddLdapUser(
			long companyId, Properties userMappings,
			Properties userExpandoMappings, Properties contactMappings,
			Properties contactExpandoMappings,
			Attributes attributes, String password)
		throws Exception {

		AttributesTransformer attributesTransformer =
			AttributesTransformerFactory.getInstance();

		attributes = attributesTransformer.transformUser(attributes);

		LDAPUser ldapUser = _ldapToPortalConverter.importLDAPUser(
			companyId, attributes, userMappings, userExpandoMappings,
			contactMappings, contactExpandoMappings, password);

		User user = findLiferayUser(companyId, ldapUser);

		if ((user != null) && user.isDefaultUser()) {
			return user;
		}

		if (user != null) {

			// User already exists in the Liferay database. Skip import if user
			// fields have been already synced, if import is part of a scheduled
			// import, or if the LDAP entry has never been modified.

			String modifiedDate = LDAPUtil.getAttributeValue(
				attributes, "modifyTimestamp");

			user = updateLiferayUser(
				companyId, ldapUser, user, password, modifiedDate);
		}
		else {
			user = createLiferayUser(companyId, ldapUser, password);
		}

		updateExpandoAttributes(user, ldapUser);

		return user;
	}

	protected void doImportRoleFromLDAPGroup(
		long companyId, LDAPGroup ldapGroup, UserGroup userGroup)
		throws Exception {

		if (!PropsValues.LDAP_IMPORT_CREATE_ROLE_PER_GROUP_ENABLED) {
			return;
		}

		Role role = null;

		try {
			role = RoleLocalServiceUtil.getRole(
				companyId, ldapGroup.getGroupName());
		}
		catch (NoSuchRoleException e) {
			User defaultUser = UserLocalServiceUtil.getDefaultUser(
				companyId);

			role = RoleLocalServiceUtil.addRole(
				defaultUser.getUserId(), companyId,
				ldapGroup.getGroupName(), null,
				"Automatically imported via LDAP import",
				RoleConstants.TYPE_REGULAR);
		}

		long userGroupGroupId = userGroup.getGroup().getGroupId();

		if (GroupLocalServiceUtil.hasRoleGroup(
				role.getRoleId(), userGroupGroupId)) {

			return;
		}

		GroupLocalServiceUtil.addRoleGroups(
			role.getRoleId(), new long[] { userGroupGroupId });
	}

	protected User findLiferayUser(long companyId, LDAPUser ldapUser)
		throws Exception {

		User user = null;

		try {
			String authType = PrefsPropsUtil.getString(
				companyId, PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
				PropsValues.COMPANY_SECURITY_AUTH_TYPE);

			if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				user = UserLocalServiceUtil.getUserByScreenName(
					companyId, ldapUser.getScreenName());
			}
			else {
				user = UserLocalServiceUtil.getUserByEmailAddress(
					companyId, ldapUser.getEmailAddress());
			}
		}
		catch (NoSuchUserException nsue) {
		}

		return user;
	}

	protected void importGroupsAndMembershipFromLDAPUser(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, User user,
			Properties userMappings, Properties groupMappings)
		throws Exception {

		String userMappingsGroup = userMappings.getProperty("group");

		if (Validator.isNull(userMappingsGroup)) {
			return;
		}

		Attribute attribute = attributes.get(userMappingsGroup);

		if (attribute == null) {
			return;
		}

		attribute.clear();

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN + postfix);

		Binding binding = PortalLDAPUtil.getUser(
			ldapServerId, companyId, user.getScreenName());

		String fullUserDN = PortalLDAPUtil.getNameInNamespace(
			ldapServerId, companyId, binding);

		StringBundler sb = new StringBundler(9);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(StringPool.AMPERSAND);
		sb.append(
			PrefsPropsUtil.getString(
				companyId,
				PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER + postfix));
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(groupMappings.getProperty("user"));
		sb.append(StringPool.EQUAL);
		sb.append(fullUserDN);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		List<SearchResult> searchResults = PortalLDAPUtil.searchLDAP(
			companyId, ldapContext, 0, baseDN, sb.toString(), null);

		for (SearchResult searchResult : searchResults) {
			String fullGroupDN = PortalLDAPUtil.getNameInNamespace(
				ldapServerId, companyId, searchResult);

			attribute.add(fullGroupDN);
		}

		List<Long> newUserGroupIds = new ArrayList<Long>(attribute.size());

		for (int i = 0; i < attribute.size(); i++) {
			String fullGroupDN = (String) attribute.get(i);

			Attributes groupAttributes = null;

			try {
				groupAttributes = PortalLDAPUtil.getGroupAttributes(
					ldapServerId, companyId, ldapContext, fullGroupDN);
			}
			catch (NameNotFoundException nnfe) {
				_log.error(
					"LDAP group not found with fullGroupDN " + fullGroupDN,
					nnfe);

				continue;
			}

			UserGroup userGroup = addUserGroupFromLdap(
				companyId, groupAttributes, groupMappings);

			if (userGroup != null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Adding " + user.getUserId() + " to group " +
							userGroup.getUserGroupId());
				}

				newUserGroupIds.add(userGroup.getUserGroupId());
			}
		}

		UserGroupLocalServiceUtil.setUserUserGroups(
			user.getUserId(),
			ArrayUtil.toArray(
				newUserGroupIds.toArray(new Long[newUserGroupIds.size()])));
	}

	protected Attribute getUsersInUserGroup(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, UserGroup userGroup,
			Properties groupMappings) 
		throws Exception {

		Attribute attribute = attributes.get(groupMappings.getProperty("user"));

		if (attribute == null) {
			return null;
		}

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN + postfix);

		StringBundler sb = new StringBundler(7);

		sb.append("(&");
		sb.append(
			PrefsPropsUtil.getString(
				companyId,
				PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER + postfix));
		sb.append("(");
		sb.append(groupMappings.getProperty("groupName"));
		sb.append("=");
		sb.append(userGroup.getName());
		sb.append("))");

		return PortalLDAPUtil.getMultivaluedAttribute(
			companyId, ldapContext, baseDN, sb.toString(), attribute);
	}

	protected void importUsersAndMembershipFromLDAPGroup(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Properties userMappings, Properties userExpandoMappings,
			Properties contactMappings, Properties contactExpandoMappings,
			long userGroupId, Attribute attribute)
		throws Exception {

		List<Long> newUserIds = new ArrayList<Long>(attribute.size());

		for (int i = 0; i < attribute.size(); i++) {
			String fullUserDN = (String)attribute.get(i);

			Attributes userAttributes = null;

			try {
				userAttributes = PortalLDAPUtil.getUserAttributes(
					ldapServerId, companyId, ldapContext, fullUserDN);
			}
			catch (NameNotFoundException nnfe) {
				_log.error(
					"LDAP user not found with fullUserDN " + fullUserDN, nnfe);

				continue;
			}

			try {
				User user = addUserFromLdap(
					companyId, userMappings,
					userExpandoMappings, contactMappings,
					contactExpandoMappings, userAttributes, StringPool.BLANK);

				if (user != null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Adding " + user.getUserId() + " to group " +
								userGroupId);
					}

					newUserIds.add(user.getUserId());
				}
			}
			catch (Exception e) {
				_log.error("Unable to load user " + userAttributes, e);
			}
		}

		UserLocalServiceUtil.setUserGroupUsers(
			userGroupId,
			ArrayUtil.toArray(newUserIds.toArray(new Long[newUserIds.size()])));
	}

	protected void populateExpandoAttributes(
		ExpandoBridge expandoBridge, Map<String, String> expandoAttributes) {

		for (Map.Entry<String, String> expandoAttribute :
				expandoAttributes.entrySet()) {

			String name = expandoAttribute.getKey();

			if (!expandoBridge.hasAttribute(name)) {
				continue;
			}

			int type = expandoBridge.getAttributeType(name);

			Serializable value = ExpandoConverterUtil.getAttributeFromString(
				type, expandoAttribute.getValue());

			try {
				ExpandoValueLocalServiceUtil.addValue(
					expandoBridge.getCompanyId(), expandoBridge.getClassName(),
					ExpandoTableConstants.DEFAULT_TABLE_NAME,
					name, expandoBridge.getClassPK(), value);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void updateExpandoAttributes(User user, LDAPUser ldapUser)
		throws Exception {

		ExpandoBridge userExpandoBridge = user.getExpandoBridge();

		populateExpandoAttributes(
			userExpandoBridge, ldapUser.getUserExpandoAttributes());

		Contact contact = user.getContact();

		ExpandoBridge contactExpandoBridge = contact.getExpandoBridge();

		populateExpandoAttributes(
			contactExpandoBridge , ldapUser.getContactExpandoAttributes());
	}

	protected User updateLiferayUser(
			long companyId, LDAPUser ldapUser, User user,
			String password, String modifiedDate)
		throws Exception {

		Date ldapUserModifiedDate = null;

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
				ldapUser.isAutoPassword()) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"User is already synchronized, skipping user " +
							user.getEmailAddress());
				}

				return user;
			}
		}
		catch (ParseException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to parse LDAP modify timestamp " + modifiedDate,
					pe);
			}
		}

		if (Validator.isNull(ldapUser.getScreenName())) {
			ldapUser.setAutoScreenName(true);
		}

		if (ldapUser.isAutoScreenName()) {
			ScreenNameGenerator screenNameGenerator =
				ScreenNameGeneratorFactory.getInstance();

			ldapUser.setScreenName(
				screenNameGenerator.generate(
					companyId, user.getUserId(), ldapUser.getEmailAddress()));
		}

		Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

		birthdayCal.setTime(user.getContact().getBirthday());

		int birthdayMonth = birthdayCal.get(Calendar.MONTH);
		int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
		int birthdayYear = birthdayCal.get(Calendar.YEAR);

		if (ldapUser.isUpdatePassword()) {
			UserLocalServiceUtil.updatePassword(
				user.getUserId(), password, password,
				ldapUser.isPasswordReset(), true);
		}

		user = UserLocalServiceUtil.updateUser(
			user.getUserId(), password, StringPool.BLANK,
			StringPool.BLANK, ldapUser.isPasswordReset(),
			ldapUser.getReminderQueryQuestion(),
			ldapUser.getReminderQueryAnswer(), ldapUser.getScreenName(),
			ldapUser.getEmailAddress(), ldapUser.getOpenId(),
			ldapUser.getLanguageId(), ldapUser.getTimeZoneId(),
			ldapUser.getGreeting(), ldapUser.getComments(),
			ldapUser.getFirstName(), ldapUser.getMiddleName(),
			ldapUser.getLastName(), ldapUser.getPrefixId(),
			ldapUser.getSuffixId(), ldapUser.isMale(), birthdayMonth,
			birthdayDay, birthdayYear, ldapUser.getSmsSn(),
			ldapUser.getAimSn(), ldapUser.getFacebookSn(),
			ldapUser.getIcqSn(), ldapUser.getJabberSn(),
			ldapUser.getMsnSn(), ldapUser.getMySpaceSn(),
			ldapUser.getSkypeSn(), ldapUser.getTwitterSn(),
			ldapUser.getYmSn(), ldapUser.getJobTitle(),
			ldapUser.getGroupIds(), ldapUser.getOrganizationIds(),
			ldapUser.getRoleIds(), ldapUser.getUserGroupRoles(),
			ldapUser.getUserGroupIds(), ldapUser.getServiceContext());

		if (ldapUserModifiedDate != null) {
			user = UserLocalServiceUtil.updateModifiedDate(
				user.getUserId(), ldapUserModifiedDate);
		}

		return user;
	}

	private static final String _IMPORT_BY_GROUP = "group";

	private static final String _IMPORT_BY_USER = "user";

	private static Log _log = LogFactoryUtil.getLog(
		PortalLDAPImporterImpl.class);

	private LDAPToPortalConverter _ldapToPortalConverter;

}