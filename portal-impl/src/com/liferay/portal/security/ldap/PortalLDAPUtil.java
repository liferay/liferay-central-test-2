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

package com.liferay.portal.security.ldap;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.NoSuchUserGroupException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PropertiesUtil;
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
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ldap.LDAPUtil;
import com.liferay.util.ldap.Modifications;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

public class PortalLDAPUtil {

	public static final String IMPORT_BY_USER = "user";

	public static final String IMPORT_BY_GROUP = "group";

	public static void exportToLDAP(Contact contact) throws Exception {
		long companyId = contact.getCompanyId();

		if (!isAuthEnabled(companyId) || !isExportEnabled(companyId)) {
			return;
		}

		LdapContext ctx = getContext(companyId);

		try {
			if (ctx == null) {
				return;
			}

			User user = UserLocalServiceUtil.getUserByContactId(
				contact.getContactId());

			Properties userMappings = getUserMappings(companyId);
			Binding binding = getUser(
				contact.getCompanyId(), user.getScreenName());
			String name = StringPool.BLANK;

			if (binding == null) {

				// Generate full DN based on user DN

				StringBuilder sb = new StringBuilder();

				sb.append(userMappings.getProperty("screenName"));
				sb.append(StringPool.EQUAL);
				sb.append(user.getScreenName());
				sb.append(StringPool.COMMA);
				sb.append(getUsersDN(companyId));

				name = sb.toString();

				// Create new user in LDAP

				LDAPUser ldapUser = (LDAPUser)Class.forName(
					PropsValues.LDAP_USER_IMPL).newInstance();

				ldapUser.setUser(user);

				ctx.bind(name, ldapUser);
			}
			else {

				// Modify existing LDAP user record

				name = getNameInNamespace(companyId, binding);

				Modifications mods = Modifications.getInstance();

				mods.addItem(
					userMappings.getProperty("firstName"),
					contact.getFirstName());
				mods.addItem(
					userMappings.getProperty("lastName"),
					contact.getLastName());

				String fullNameMapping = userMappings.getProperty("fullName");

				if (Validator.isNotNull(fullNameMapping)) {
					mods.addItem(fullNameMapping, contact.getFullName());
				}

				String jobTitleMapping = userMappings.getProperty("jobTitle");

				if (Validator.isNotNull(jobTitleMapping)) {
					mods.addItem(jobTitleMapping, contact.getJobTitle());
				}

				ModificationItem[] modItems = mods.getItems();

				ctx.modifyAttributes(name, modItems);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (ctx != null) {
				ctx.close();
			}
		}
	}

	public static void exportToLDAP(User user) throws Exception {
		long companyId = user.getCompanyId();

		if (!isAuthEnabled(companyId) || !isExportEnabled(companyId)) {
			return;
		}

		LdapContext ctx = getContext(companyId);

		try {
			if (ctx == null) {
				return;
			}

			Properties userMappings = getUserMappings(companyId);
			Binding binding = getUser(
				user.getCompanyId(), user.getScreenName());
			String name = StringPool.BLANK;

			if (binding == null) {

				// User is not exported until contact is updated

				return;
			}

			// Modify existing LDAP user record

			name = getNameInNamespace(companyId, binding);

			Modifications mods = Modifications.getInstance();

			mods.addItem(
				userMappings.getProperty("firstName"), user.getFirstName());
			mods.addItem(
				userMappings.getProperty("lastName"), user.getLastName());

			String fullNameMapping = userMappings.getProperty("fullName");

			if (Validator.isNotNull(fullNameMapping)) {
				mods.addItem(fullNameMapping, user.getFullName());
			}

			if (user.isPasswordModified() &&
				Validator.isNotNull(user.getPasswordUnencrypted())) {

				mods.addItem(
					userMappings.getProperty("password"),
					user.getPasswordUnencrypted());
			}

			if (Validator.isNotNull(user.getEmailAddress())) {
				mods.addItem(
					userMappings.getProperty("emailAddress"),
					user.getEmailAddress());
			}

			String jobTitleMapping = userMappings.getProperty("jobTitle");

			if (Validator.isNotNull(jobTitleMapping)) {
				mods.addItem(jobTitleMapping, user.getJobTitle());
			}

			ModificationItem[] modItems = mods.getItems();

			ctx.modifyAttributes(name, modItems);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			if (ctx != null) {
				ctx.close();
			}
		}
	}

	public static String getAuthSearchFilter(
			long companyId, String emailAddress, String screenName,
			String userId)
		throws SystemException {

		String filter = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_AUTH_SEARCH_FILTER);

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
				userId
			});

		if (_log.isDebugEnabled()) {
			_log.debug("Search filter after transformation " + filter);
		}

		return filter;
	}

	public static LdapContext getContext(long companyId) throws Exception {
		String baseProviderURL = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_PROVIDER_URL);
		String pricipal = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_SECURITY_PRINCIPAL);
		String credentials = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_SECURITY_CREDENTIALS);

		return getContext(companyId, baseProviderURL, pricipal, credentials);
	}

	public static LdapContext getContext(
			long companyId, String providerURL, String pricipal,
			String credentials)
		throws Exception {

		Properties env = new Properties();

		env.put(
			Context.INITIAL_CONTEXT_FACTORY,
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_FACTORY_INITIAL));
		env.put(Context.PROVIDER_URL, providerURL);
		env.put(Context.SECURITY_PRINCIPAL, pricipal);
		env.put(Context.SECURITY_CREDENTIALS, credentials);
		env.put(
			Context.REFERRAL,
			PrefsPropsUtil.getString(companyId, PropsKeys.LDAP_REFERRAL));

		// Enable pooling

		env.put("com.sun.jndi.ldap.connect.pool", "true");
		env.put("com.sun.jndi.ldap.connect.pool.maxsize","50");
		env.put("com.sun.jndi.ldap.connect.pool.timeout", "10000");

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

	public static Attributes getGroupAttributes(
			long companyId, LdapContext ctx, String fullDistinguishedName)
		throws Exception {

		return getGroupAttributes(companyId, ctx, fullDistinguishedName, false);
	}

	public static Attributes getGroupAttributes(
			long companyId, LdapContext ctx, String fullDistinguishedName,
			boolean includeReferenceAttributes)
		throws Exception {

		Properties groupMappings = getGroupMappings(companyId);

		List<String> mappedGroupAttributeIds = new ArrayList<String>();

		mappedGroupAttributeIds.add(groupMappings.getProperty("groupName"));
		mappedGroupAttributeIds.add(groupMappings.getProperty("description"));

		if (includeReferenceAttributes) {
			mappedGroupAttributeIds.add(groupMappings.getProperty("user"));
		}

		return _getAttributes(
			ctx, fullDistinguishedName,
			mappedGroupAttributeIds.toArray(new String[0]));
	}

	public static Properties getGroupMappings(long companyId)
		throws Exception {

		Properties groupMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(companyId, PropsKeys.LDAP_GROUP_MAPPINGS));

		LogUtil.debug(_log, groupMappings);

		return groupMappings;
	}

	public static List<SearchResult> getGroups(
			long companyId, LdapContext ctx, int maxResults)
		throws Exception {

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN);
		String groupFilter = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER);

		return getGroups(companyId, ctx, maxResults, baseDN, groupFilter);
	}

	public static List<SearchResult> getGroups(
			long companyId, LdapContext ctx, int maxResults, String baseDN,
			String groupFilter)
		throws Exception {

		return _searchLDAP(
			companyId, ctx, maxResults, baseDN, groupFilter, null);
	}

	public static Attribute getMultiValuedAttribute(
			long companyId, LdapContext ctx, String baseDN, String filter,
			Attribute attribute)
		throws Exception {

		if (attribute.size() > 0) {
			return attribute;
		}

		String[] attributeIds = {
			_getNextRange(attribute.getID())
		};

		while (true) {
			List<SearchResult> results = _searchLDAP(
				companyId, ctx, 0, baseDN, filter, attributeIds);

			if (results.size() != 1) {
				break;
			}

			SearchResult result = results.get(0);

			Attributes attributes = result.getAttributes();

			if (attributes.size() != 1) {
				break;
			}

			NamingEnumeration<? extends Attribute> enu = attributes.getAll();

			if (!enu.hasMoreElements()) {
				break;
			}

			Attribute attr = enu.nextElement();

			for (int i = 0; i < attr.size(); i++) {
				attribute.add(attr.get(i));
			}

			if (StringUtil.endsWith(attr.getID(), StringPool.STAR) ||
				(attr.size() < PropsValues.LDAP_RANGE_SIZE)) {

				break;
			}

			attributeIds[0] = _getNextRange(attributeIds[0]);
		}

		return attribute;
	}

	public static String getNameInNamespace(long companyId, Binding binding)
		throws Exception {

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN);

		if (Validator.isNull(baseDN)) {
			return binding.getName();
		}
		else {
			StringBuilder sb = new StringBuilder();

			sb.append(binding.getName());
			sb.append(StringPool.COMMA);
			sb.append(baseDN);

			return sb.toString();
		}
	}

	public static Binding getUser(long companyId, String screenName)
		throws Exception {

		LdapContext ctx = getContext(companyId);

		NamingEnumeration<SearchResult> enu = null;

		try {
			if (ctx == null) {
				return null;
			}

			String baseDN = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_BASE_DN);

			Properties userMappings = getUserMappings(companyId);

			StringBuilder filter = new StringBuilder();

			filter.append(StringPool.OPEN_PARENTHESIS);
			filter.append(userMappings.getProperty("screenName"));
			filter.append(StringPool.EQUAL);
			filter.append(screenName);
			filter.append(StringPool.CLOSE_PARENTHESIS);

			SearchControls cons = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0, null, false, false);

			enu = ctx.search(
				baseDN, filter.toString(), cons);
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (ctx != null) {
				ctx.close();
			}
		}

		if (enu.hasMoreElements()) {
			Binding binding = enu.nextElement();

			enu.close();

			return binding;
		}
		else {
			return null;
		}
	}

	public static Attributes getUserAttributes(
			long companyId, LdapContext ctx, String fullDistinguishedName)
		throws Exception {

		Properties userMappings = getUserMappings(companyId);

		String[] mappedUserAttributeIds = {
			userMappings.getProperty("screenName"),
			userMappings.getProperty("emailAddress"),
			userMappings.getProperty("fullName"),
			userMappings.getProperty("firstName"),
			userMappings.getProperty("middleName"),
			userMappings.getProperty("lastName"),
			userMappings.getProperty("jobTitle"),
			userMappings.getProperty("group")
		};

		return _getAttributes(
			ctx, fullDistinguishedName, mappedUserAttributeIds);
	}

	public static Properties getUserMappings(long companyId) throws Exception {
		Properties userMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(companyId, PropsKeys.LDAP_USER_MAPPINGS));

		LogUtil.debug(_log, userMappings);

		return userMappings;
	}

	public static List<SearchResult> getUsers(
			long companyId, LdapContext ctx, int maxResults)
		throws Exception {

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN);
		String userFilter = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_IMPORT_USER_SEARCH_FILTER);

		return getUsers(companyId, ctx, maxResults, baseDN, userFilter);
	}

	public static List<SearchResult> getUsers(
			long companyId, LdapContext ctx, int maxResults, String baseDN,
			String userFilter)
		throws Exception {

		return _searchLDAP(
			companyId, ctx, maxResults, baseDN, userFilter, null);
	}

	public static String getUsersDN(long companyId) throws Exception {
		return PrefsPropsUtil.getString(companyId, PropsKeys.LDAP_USERS_DN);
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
		List<Company> companies = CompanyLocalServiceUtil.getCompanies(false);

		for (Company company : companies) {
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
				companyId, PropsKeys.LDAP_IMPORT_METHOD);

			if (importMethod.equals(IMPORT_BY_USER)) {
				List<SearchResult> results = getUsers(companyId, ctx, 0);

				// Loop through all LDAP users

				for (SearchResult result : results) {
					Attributes attributes = getUserAttributes(
						companyId, ctx, getNameInNamespace(companyId, result));

					importLDAPUser(
						companyId, ctx, attributes, StringPool.BLANK, true);
				}
			}
			else if (importMethod.equals(IMPORT_BY_GROUP)) {
				List<SearchResult> results = getGroups(companyId, ctx, 0);

				// Loop through all LDAP groups

				for (SearchResult result : results) {
					Attributes attributes = getGroupAttributes(
						companyId, ctx, getNameInNamespace(companyId, result),
						true);

					importLDAPGroup(companyId, ctx, attributes, true);
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

	public static UserGroup importLDAPGroup(
			long companyId, LdapContext ctx, Attributes attributes,
			boolean importGroupMembership)
		throws Exception {

		AttributesTransformer attributesTransformer =
			AttributesTransformerFactory.getInstance();

		attributes = attributesTransformer.transformGroup(attributes);

		Properties groupMappings = getGroupMappings(companyId);

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
				_log.debug("Adding user group to portal " + groupName);
			}

			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				companyId);

			try {
				userGroup = UserGroupLocalServiceUtil.addUserGroup(
					defaultUserId, companyId, groupName, description);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Could not create user group " + groupName);
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
					companyId, PropsKeys.LDAP_BASE_DN);

				StringBuilder sb = new StringBuilder();

				sb.append("(&");
				sb.append(
					PrefsPropsUtil.getString(
						companyId, PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER));
				sb.append("(");
				sb.append(groupMappings.getProperty("groupName"));
				sb.append("=");
				sb.append(LDAPUtil.getAttributeValue(
					attributes, groupMappings.getProperty("groupName")));
				sb.append("))");

				attribute = getMultiValuedAttribute(
					companyId, ctx, baseDN, sb.toString(), attribute);

				_importUsersAndMembershipFromLDAPGroup(
					companyId, ctx, userGroup.getUserGroupId(), attribute);
			}
		}

		return userGroup;
	}

	public static User importLDAPUser(
			long companyId, LdapContext ctx, Attributes attributes,
			String password, boolean importGroupMembership)
		throws Exception {

		AttributesTransformer attributesTransformer =
			AttributesTransformerFactory.getInstance();

		attributes = attributesTransformer.transformUser(attributes);

		Properties userMappings = getUserMappings(companyId);

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
					DateFormat dateFormat = new SimpleDateFormat(
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
					(ScreenNameGenerator)InstancePool.get(
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
					_log.debug("Adding user to portal " + emailAddress);
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
					_importGroupsAndMembershipFromLDAPUser(
						companyId, ctx, user.getUserId(), attribute);
				}
			}
		}

		return user;
	}

	public static boolean isAuthEnabled(long companyId) throws SystemException {
		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_AUTH_ENABLED,
				PropsValues.LDAP_AUTH_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isExportEnabled(long companyId)
		throws SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_EXPORT_ENABLED,
				PropsValues.LDAP_EXPORT_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isImportEnabled(long companyId)
		throws SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_IMPORT_ENABLED,
				PropsValues.LDAP_IMPORT_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isImportOnStartup(long companyId)
		throws SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_IMPORT_ON_STARTUP)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isNtlmEnabled(long companyId)
		throws SystemException {

		if (!isAuthEnabled(companyId)) {
			return false;
		}

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.NTLM_AUTH_ENABLED,
				PropsValues.NTLM_AUTH_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isPasswordPolicyEnabled(long companyId)
		throws SystemException {

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_PASSWORD_POLICY_ENABLED,
				PropsValues.LDAP_PASSWORD_POLICY_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isSiteMinderEnabled(long companyId)
		throws SystemException {

		if (!isAuthEnabled(companyId)) {
			return false;
		}

		if (PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.SITEMINDER_AUTH_ENABLED,
				PropsValues.SITEMINDER_AUTH_ENABLED)) {

			return true;
		}
		else {
			return false;
		}
	}

	private static Attributes _getAttributes(
			LdapContext ctx, String fullDistinguishedName,
			String[] attributeIds)
		throws Exception {

		Attributes attributes = null;

		String[] auditAttributeIds = {
			"creatorsName", "createTimestamp", "modifiersName",
			"modifyTimestamp"
		};

		if (attributeIds == null) {

			// Get complete listing of LDAP attributes (slow)

			attributes = ctx.getAttributes(fullDistinguishedName);

			NamingEnumeration<? extends Attribute> enu = ctx.getAttributes(
				fullDistinguishedName, auditAttributeIds).getAll();

			while (enu.hasMoreElements()) {
				attributes.put(enu.nextElement());
			}

			enu.close();
		}
		else {

			// Get specified LDAP attributes

			int attributeCount = attributeIds.length + auditAttributeIds.length;

			String[] allAttributeIds = new String[attributeCount];

			System.arraycopy(
				attributeIds, 0, allAttributeIds, 0, attributeIds.length);
			System.arraycopy(
				auditAttributeIds, 0, allAttributeIds, attributeIds.length,
				auditAttributeIds.length);

			attributes = ctx.getAttributes(
				fullDistinguishedName, allAttributeIds);
		}

		return attributes;
	}

	private static byte[] _getCookie(Control[] controls) {
		if (controls == null) {
			return null;
		}

		for (Control control : controls) {
			if (control instanceof PagedResultsResponseControl) {
				PagedResultsResponseControl pagedResultsResponseControl =
					(PagedResultsResponseControl)control;

				return pagedResultsResponseControl.getCookie();
			}
		}

		return null;
	}

	private static String _getNextRange(String attributeId) {
		String originalAttributeId = null;
		int start = 0;
		int end = 0;

		int x = attributeId.indexOf(StringPool.SEMICOLON);

		if (x < 0) {
			originalAttributeId = attributeId;
			end = PropsValues.LDAP_RANGE_SIZE - 1;
		}
		else {
			int y = attributeId.indexOf(StringPool.EQUAL, x);
			int z = attributeId.indexOf(StringPool.DASH, y);

			originalAttributeId = attributeId.substring(0, x);
			start = GetterUtil.getInteger(attributeId.substring(y + 1, z));
			end = GetterUtil.getInteger(attributeId.substring(z + 1));

			start += PropsValues.LDAP_RANGE_SIZE;
			end += PropsValues.LDAP_RANGE_SIZE;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(originalAttributeId);
		sb.append(StringPool.SEMICOLON);
		sb.append("range=");
		sb.append(start);
		sb.append(StringPool.DASH);
		sb.append(end);

		return sb.toString();
	}

	private static void _importGroupsAndMembershipFromLDAPUser(
			long companyId, LdapContext ctx, long userId, Attribute attr)
		throws Exception {

		// Remove all user group membership from user

		UserGroupLocalServiceUtil.clearUserUserGroups(userId);

		for (int i = 0; i < attr.size(); i++) {

			// Find group in LDAP

			String fullGroupDN = (String)attr.get(i);

			Attributes groupAttrs = null;

			try {
				groupAttrs = getGroupAttributes(companyId, ctx, fullGroupDN);
			}
			catch (NameNotFoundException nnfe) {
				_log.error(
					"LDAP group not found with fullGroupDN " + fullGroupDN);

				_log.error(nnfe, nnfe);

				continue;
			}

			UserGroup userGroup = importLDAPGroup(
				companyId, ctx, groupAttrs, false);

			// Add user to user group

			if (userGroup != null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Adding " + userId + " to group " +
							userGroup.getUserGroupId());
				}

				UserLocalServiceUtil.addUserGroupUsers(
					userGroup.getUserGroupId(), new long[] {userId});
			}
		}
	}

	private static void _importUsersAndMembershipFromLDAPGroup(
			long companyId, LdapContext ctx, long userGroupId, Attribute attr)
		throws Exception {

		// Remove all user membership from user group

		UserLocalServiceUtil.clearUserGroupUsers(userGroupId);

		for (int i = 0; i < attr.size(); i++) {

			// Find user in LDAP

			String fullUserDN = (String)attr.get(i);

			Attributes userAttrs = null;

			try {
				userAttrs = getUserAttributes(companyId, ctx, fullUserDN);
			}
			catch (NameNotFoundException nnfe) {
				_log.error(
					"LDAP user not found with fullUserDN " + fullUserDN);

				_log.error(nnfe, nnfe);

				continue;
			}

			User user = importLDAPUser(
				companyId, ctx, userAttrs, StringPool.BLANK, false);

			// Add user to user group

			if (user != null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Adding " + user.getUserId() + " to group " +
							userGroupId);
				}

				UserLocalServiceUtil.addUserGroupUsers(
					userGroupId, new long[] {user.getUserId()});
			}
		}
	}

	private static List<SearchResult> _searchLDAP(
			long companyId, LdapContext ctx, int maxResults, String baseDN,
			String filter, String[] attributeIds)
		throws Exception {

		List<SearchResult> results = new ArrayList<SearchResult>();

		SearchControls cons = new SearchControls(
			SearchControls.SUBTREE_SCOPE, maxResults, 0, attributeIds, false,
			false);

		try {
			byte[] cookie = new byte[0];

			while (cookie != null) {
				if (cookie.length == 0) {
					ctx.setRequestControls(
						new Control[] {
							new PagedResultsControl(
								PropsValues.LDAP_PAGE_SIZE, Control.CRITICAL)
						});
				}
				else {
					ctx.setRequestControls(
						new Control[] {
							new PagedResultsControl(
								PropsValues.LDAP_PAGE_SIZE, cookie,
								Control.CRITICAL)
						});
				}

				NamingEnumeration<SearchResult> enu = ctx.search(
					baseDN, filter, cons);

				while (enu.hasMoreElements()) {
					results.add(enu.nextElement());
				}

				enu.close();

				cookie = _getCookie(ctx.getResponseControls());
			}
		}
		catch (OperationNotSupportedException onse) {
			ctx.setRequestControls(new Control[] {});

			NamingEnumeration<SearchResult> enu = ctx.search(
				baseDN, filter, cons);

			while (enu.hasMoreElements()) {
				results.add(enu.nextElement());
			}

			enu.close();
		}

		return results;
	}

	private static Log _log = LogFactoryUtil.getLog(PortalLDAPUtil.class);

}