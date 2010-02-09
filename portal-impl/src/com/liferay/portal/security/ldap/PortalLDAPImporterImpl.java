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

package com.liferay.portal.security.ldap;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
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
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.ScreenNameGeneratorFactory;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ldap.LDAPUtil;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.NameNotFoundException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

/**
 * <a href="PortalLDAPImporterImpl.java.html}"><b><i>View Source</i></b></a>
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

			if (importMethod.equals(_IMPORT_BY_USER)) {
				List<SearchResult> searchResults = PortalLDAPUtil.getUsers(
					ldapServerId, companyId, ldapContext, 0);

				// Loop through all LDAP users

				for (SearchResult searchResult : searchResults) {
					Attributes attributes = PortalLDAPUtil.getUserAttributes(
						ldapServerId, companyId, ldapContext,
						PortalLDAPUtil.getNameInNamespace(
							ldapServerId, companyId, searchResult));

					importLDAPUser(
						ldapServerId, companyId, ldapContext, attributes,
						StringPool.BLANK, true);
				}
			}
			else if (importMethod.equals(_IMPORT_BY_GROUP)) {
				List<SearchResult> searchResults = PortalLDAPUtil.getGroups(
					ldapServerId, companyId, ldapContext, 0);

				// Loop through all LDAP groups

				for (SearchResult searchResult : searchResults) {
					Attributes attributes = PortalLDAPUtil.getGroupAttributes(
						ldapServerId, companyId, ldapContext,
						PortalLDAPUtil.getNameInNamespace(
							ldapServerId, companyId, searchResult),
						true);

					importLDAPGroup(
						ldapServerId, companyId, ldapContext, attributes, true);
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
			Attributes attributes, String password,
			boolean importGroupMembership)
		throws Exception {

		LDAPUserTransactionThreadLocal.setOriginatesFromLDAP(true);

		try {
			return doImportLDAPUser(
				ldapServerId, companyId, ldapContext, attributes, password,
				importGroupMembership);
		}
		finally {
			LDAPUserTransactionThreadLocal.setOriginatesFromLDAP(false);
		}
	}

	public void setLDAPToPortalConverter(
		LDAPToPortalConverter ldapToPortalConverter) {

		_ldapToPortalConverter = ldapToPortalConverter;
	}

	protected User createLiferayUser(
		long companyId, LDAPUser ldapUser, String password) {

		try {
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
		catch (Exception e) {
			_log.error(
				"Unable to add user with screen name " +
					ldapUser.getScreenName() + " and email address " +
						ldapUser.getEmailAddress(),
				e);

			return null;
		}
	}

	protected User doImportLDAPUser(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, String password,
			boolean importGroupMembership)
		throws Exception {

		AttributesTransformer attributesTransformer =
			AttributesTransformerFactory.getInstance();

		attributes = attributesTransformer.transformUser(attributes);

		Properties userMappings = LDAPSettingsUtil.getUserMappings(
			ldapServerId, companyId);

		LogUtil.debug(_log, userMappings);

		Properties contactMappings = LDAPSettingsUtil.getContactMappings(
			ldapServerId, companyId);

		LogUtil.debug(_log, contactMappings);

		LDAPUser ldapUser = _ldapToPortalConverter.importLDAPUser(
			companyId, attributes, userMappings, contactMappings, password);

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

		if (!importGroupMembership || (user == null)) {
			return user;
		}

		importGroupsAndMembershipFromLDAPUser(
			ldapServerId, companyId, ldapContext, attributes, ldapUser,
			user, userMappings);

		return user;
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
			Attributes attributes, LDAPUser ldapUser, User user,
			Properties userMappings)
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

		Properties groupMappings = LDAPSettingsUtil.getGroupMappings(
			ldapServerId, companyId);

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN + postfix);

		Binding binding = PortalLDAPUtil.getUser(
			ldapServerId, companyId, ldapUser.getScreenName());

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

			UserGroup userGroup = importLDAPGroup(
				ldapServerId, companyId, ldapContext, groupAttributes, false);

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

	protected UserGroup importLDAPGroup(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, boolean importGroupMembership)
		throws Exception {

		AttributesTransformer attributesTransformer =
			AttributesTransformerFactory.getInstance();

		attributes = attributesTransformer.transformGroup(attributes);

		Properties groupMappings = LDAPSettingsUtil.getGroupMappings(
			ldapServerId, companyId);

		LogUtil.debug(_log, groupMappings);

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

		if (!importGroupMembership || (userGroup == null)) {
			return userGroup;
		}

		Attribute attribute = attributes.get(groupMappings.getProperty("user"));

		if (attribute == null) {
			return userGroup;
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
		sb.append(
			LDAPUtil.getAttributeValue(
				attributes, groupMappings.getProperty("groupName")));
		sb.append("))");

		attribute = PortalLDAPUtil.getMultivaluedAttribute(
			companyId, ldapContext, baseDN, sb.toString(), attribute);

		importUsersAndMembershipFromLDAPGroup(
			ldapServerId, companyId, ldapContext, userGroup.getUserGroupId(),
			attribute);

		return userGroup;
	}

	protected void importUsersAndMembershipFromLDAPGroup(
			long ldapServerId, long companyId, LdapContext ldapContext,
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

			User user = importLDAPUser(
				ldapServerId, companyId, ldapContext, userAttributes,
				StringPool.BLANK, false);

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
						"User is already syncronized, skipping user " +
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

		try {
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
		catch (Exception e) {
			_log.error(
				"Unable to update user with screen name " +
					ldapUser.getScreenName() + " and email address " +
						ldapUser.getEmailAddress(),
				e);

			return null;
		}
	}

	private static final String _IMPORT_BY_GROUP = "group";

	private static final String _IMPORT_BY_USER = "user";

	private static Log _log = LogFactoryUtil.getLog(
		PortalLDAPImporterImpl.class);

	private LDAPToPortalConverter _ldapToPortalConverter;

}