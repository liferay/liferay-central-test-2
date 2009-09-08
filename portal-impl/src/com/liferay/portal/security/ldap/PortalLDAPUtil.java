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
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.ScreenNameGenerator;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ldap.LDAPUtil;
import com.liferay.util.ldap.Modifications;
import com.liferay.portlet.enterpriseadmin.util.UserIndexer;
import com.liferay.portlet.expando.util.ExpandoConverterUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeLocalImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Map;
import java.io.Serializable;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.Name;
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

/**
 * <a href="PortalLDAPUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 * @author Brian Wing Shun Chan
 * @author Jerry Niu
 * @author Scott Lee
 * @author Hervé Ménage
 * @author Samuel Kong
 */
public class PortalLDAPUtil {

	public static final String IMPORT_BY_USER = "user";

	public static final String IMPORT_BY_GROUP = "group";

	public static final String[] _AUDIT_ATTRIBUTE_NAMES = {
			"creatorsName", "createTimestamp", "modifiersName",
			"modifyTimestamp"
		};


	public static void exportToLDAP(Contact contact) throws Exception {
		long companyId = contact.getCompanyId();

		User user =
			UserLocalServiceUtil.getUserByContactId(contact.getContactId());

		Properties contactMappings = LDAPSettingsUtil.getContactMappings(companyId);

		_exportToLDAP(user, contactMappings);
	}

	public static void exportToLDAP(User user) throws Exception {
		Properties userMappings =
			LDAPSettingsUtil.getAllUserMappings(user.getCompanyId());

		_exportToLDAP(user, userMappings);
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

	public static String getNameInNamespace(long companyId, Binding binding)
		throws Exception {

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN);

		String name = binding.getName();

		if (name.startsWith(StringPool.QUOTE) &&
			name.endsWith(StringPool.QUOTE)) {

			name = name.substring(1, name.length() - 1);
		}

		if (Validator.isNull(baseDN)) {
			return name;
		}
		else {
			StringBuilder sb = new StringBuilder();

			if (Validator.isNotNull(name)) {
				sb.append(name);
				sb.append(StringPool.COMMA);
				sb.append(baseDN);
			}
			else {
				sb.append(baseDN);
			}

			return sb.toString();
		}
	}

	public static Binding getUser(
		long companyId, String screenName)
		throws Exception {

		return _getUser(companyId, screenName, LDAPSettingsUtil.getAllUserMappings(companyId));
	}

	public static Attributes getUserAttributes(
			long companyId, LdapContext ctx, String fullDistinguishedName)
		throws Exception {

		Properties userMappings = LDAPSettingsUtil.getAllUserMappings(companyId);

		List<String> mappedUserAttributeIds =
			new ArrayList<String>(userMappings.size());

		for (Object mapping : userMappings.keySet()) {
			mappedUserAttributeIds.add(
				userMappings.getProperty((String) mapping));
		}

		return _getAttributes(
			ctx, fullDistinguishedName,
			mappedUserAttributeIds.toArray(new String[0]));
	}

/*  method not used anywhere?
	public static boolean hasUser(long companyId, String screenName)
		throws Exception {

		if (getUser(companyId, screenName) != null) {
			return true;
		}
		else {
			return false;
		}
	}*/

	public static void importFromLDAP() throws Exception {
		List<Company> companies = CompanyLocalServiceUtil.getCompanies(false);

		for (Company company : companies) {
			importFromLDAP(company.getCompanyId());
		}
	}

	public static void importFromLDAP(long companyId) throws Exception {
		if (!LDAPSettingsUtil.isImportEnabled(companyId)) {
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
				List<SearchResult> results = _getUsers(companyId, ctx, 0);

				// Loop through all LDAP users

				for (SearchResult result : results) {
					Attributes attributes = getUserAttributes(
						companyId, ctx, getNameInNamespace(companyId, result));

					_importLDAPUserAndGroupMembership(
						companyId, ctx, attributes, StringPool.BLANK, true);
				}
			}
			else if (importMethod.equals(IMPORT_BY_GROUP)) {
				List<SearchResult> results = _getGroups(companyId, ctx, 0);

				// Loop through all LDAP groups

				for (SearchResult result : results) {
					Attributes attributes = _getGroupAttributes(
						companyId, ctx, getNameInNamespace(companyId, result),
						true);

					_importLDAPGroup(companyId, ctx, attributes, true);
				}
			}
		}
		catch (Exception e) {
			_log.error("Error importing LDAP users and groups", e);
		}
		finally {
			ctx.close();
		}
	}

	public static User importLDAPUser(
			long companyId, LdapContext ctx, Attributes attributes,
			String password, boolean importGroupMembership)
		throws Exception {

		LDAPUserTransactionThreadLocal.setOriginatesFromLDAP(true);

		try {
			return _importLDAPUserAndGroupMembership(
				companyId, ctx, attributes, password, importGroupMembership);
		}
		finally {
			LDAPUserTransactionThreadLocal.setOriginatesFromLDAP(false);
		}
	}

	private static void _exportToLDAP(User user, Properties userMappings) throws Exception {
		long companyId = user.getCompanyId();

		if (!LDAPSettingsUtil.isAuthEnabled(companyId) ||
			!LDAPSettingsUtil.isExportEnabled(companyId)) {

			return;
		}

		if (_converter == null) {
			_converter =
				(LDAPConverter) Class.
					forName(PropsValues.LDAP_CONVERTER_IMPL).newInstance();
		}

		if (user.isDefaultUser()) {
			return;
		}

		LdapContext ctx = getContext(companyId);

		try {
			if (ctx == null) {
				return;
			}

			Binding binding = _getUser(
				user.getCompanyId(), user.getScreenName(), userMappings);
			Name name = new CompositeName();

			if (binding == null) {
				name = _getDNName(userMappings, user.getScreenName(), companyId);

				ctx.bind(
					name,
					new LDAPContext(
						_converter.createLDAPUser(name, userMappings, user)));

				binding =
					_getUser(
						user.getCompanyId(), user.getScreenName(),
						userMappings);

				name = new CompositeName();
			}

			name.add(getNameInNamespace(user.getCompanyId(), binding));

			Modifications mods =
				_converter.updateLDAPUser(name, userMappings,  user,  binding);

			ModificationItem[] modItems = mods.getItems();

			ctx.modifyAttributes(name, modItems);
		}
		finally {
			if (ctx != null) {
				ctx.close();
			}
		}
	}

	private static Attributes _getAttributes(
			LdapContext ctx, String fullDistinguishedName,
			String[] attributeIds)
		throws Exception {

		Name fullDN = new CompositeName().add(fullDistinguishedName);

		Attributes attributes;
		if (attributeIds == null) {

			// Get complete listing of LDAP attributes (slow)

			attributes = ctx.getAttributes(fullDN);

			NamingEnumeration<? extends Attribute> enu = ctx.getAttributes(
				fullDN, _AUDIT_ATTRIBUTE_NAMES).getAll();

			while (enu.hasMoreElements()) {
				attributes.put(enu.nextElement());
			}

			enu.close();
		}
		else {

			// Get specified LDAP attributes

			int attributeCount = attributeIds.length + _AUDIT_ATTRIBUTE_NAMES.length;

			String[] allAttributeIds = new String[attributeCount];

			System.arraycopy(
				attributeIds, 0, allAttributeIds, 0, attributeIds.length);
			System.arraycopy(
				_AUDIT_ATTRIBUTE_NAMES, 0, allAttributeIds, attributeIds.length,
				_AUDIT_ATTRIBUTE_NAMES.length);

			attributes = ctx.getAttributes(fullDN, allAttributeIds);
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

	private static Name _getDNName (
		Properties userMappings, String screenName, long companyId)
		throws Exception {

		Name name = new CompositeName();

		StringBuilder sb = new StringBuilder();

		sb.append(userMappings.getProperty(LDAPConverterKeys.USER_SCREEN_NAME));
		sb.append(StringPool.EQUAL);
		sb.append(screenName);
		sb.append(StringPool.COMMA);
		sb.append(_getUsersDN(companyId));

		name.add(sb.toString());

		return name;
	}

	private static Attributes _getGroupAttributes(
			long companyId, LdapContext ctx, String fullDistinguishedName)
		throws Exception {

		return _getGroupAttributes(companyId, ctx, fullDistinguishedName, false);
	}

	private static Attributes _getGroupAttributes(
			long companyId, LdapContext ctx, String fullDistinguishedName,
			boolean includeReferenceAttributes)
		throws Exception {

		Properties groupMappings = _getGroupMappings(companyId);

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

	private static Properties _getGroupMappings(long companyId)
		throws Exception {

		Properties groupMappings = PropertiesUtil.load(
			PrefsPropsUtil.getString(companyId, PropsKeys.LDAP_GROUP_MAPPINGS));

		LogUtil.debug(_log, groupMappings);

		return groupMappings;
	}

	private static List<SearchResult> _getGroups(
			long companyId, LdapContext ctx, int maxResults)
		throws Exception {

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN);
		String groupFilter = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER);

		return _getGroups(companyId, ctx, maxResults, baseDN, groupFilter);
	}

	private static List<SearchResult> _getGroups(
			long companyId, LdapContext ctx, int maxResults, String baseDN,
			String groupFilter)
		throws Exception {

		return _searchLDAP(
			companyId, ctx, maxResults, baseDN, groupFilter, null);
	}

	private static Attribute _getMultivaluedAttribute(
		long companyId, LdapContext ctx, String baseDN, String filter,
		Attribute attribute)
		throws Exception {

		if (attribute.size() > 0) {
			return attribute;
		}

		String[] attributeIds = {_getNextRange(attribute.getID())};

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

			Attribute curAttribute = enu.nextElement();

			for (int i = 0; i < curAttribute.size(); i++) {
				attribute.add(curAttribute.get(i));
			}

			if (StringUtil.endsWith(curAttribute.getID(), StringPool.STAR) ||
				(curAttribute.size() < PropsValues.LDAP_RANGE_SIZE)) {

				break;
			}

			attributeIds[0] = _getNextRange(attributeIds[0]);
		}

		return attribute;
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

	private static Binding _getUser(
		long companyId, String screenName, Properties userMappings)
		throws Exception {

		LdapContext ctx = getContext(companyId);

		NamingEnumeration<SearchResult> enu = null;

		try {
			if (ctx == null) {
				return null;
			}

			String baseDN = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_BASE_DN);

			StringBuilder filter = new StringBuilder();

			filter.append(StringPool.OPEN_PARENTHESIS);
			filter.append(userMappings.getProperty(LDAPConverterKeys.USER_SCREEN_NAME));
			filter.append(StringPool.EQUAL);
			filter.append(screenName);
			filter.append(StringPool.CLOSE_PARENTHESIS);

			SearchControls cons = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0, null, false, false);

			enu = ctx.search(baseDN, filter.toString(), cons);
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

	private static List<SearchResult> _getUsers(
			long companyId, LdapContext ctx, int maxResults)
		throws Exception {

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN);
		String userFilter = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_IMPORT_USER_SEARCH_FILTER);

		return _getUsers(companyId, ctx, maxResults, baseDN, userFilter);
	}

	private static List<SearchResult> _getUsers(
			long companyId, LdapContext ctx, int maxResults, String baseDN,
			String userFilter)
		throws Exception {

		return _searchLDAP(
			companyId, ctx, maxResults, baseDN, userFilter, null);
	}

	private static String _getUsersDN(long companyId) throws Exception {
		return PrefsPropsUtil.getString(companyId, PropsKeys.LDAP_USERS_DN);
	}

	private static User _importLDAPUserAndGroupMembership(
		long companyId, LdapContext ctx, Attributes attributes,
		String password, boolean importGroupMembership)
		throws Exception {

		AttributesTransformer attributesTransformer =
			AttributesTransformerFactory.getInstance();

		attributes = attributesTransformer.transformUser(attributes);

		Properties userMappings = LDAPSettingsUtil.getUserMappings(companyId);
		Properties customMappings = LDAPSettingsUtil.getCustomMappings(companyId);

		if (_converter == null) {
			_converter =
				(LDAPConverter) Class.
					forName(PropsValues.LDAP_CONVERTER_IMPL).newInstance();
		}

		User user = null;

		LDAPUserTransactionThreadLocal.setOriginatesFromLDAP(true);

		try {
			user = _importLDAPUser(attributes, userMappings, customMappings, companyId, password);
		}
		finally {
			LDAPUserTransactionThreadLocal.setOriginatesFromLDAP(false);
		}

		// Import user groups and membership

		if (importGroupMembership && (user != null)) {
			String userMappingsGroup = userMappings.getProperty(LDAPConverterKeys.USER_GROUP);

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

	private static void _importGroupsAndMembershipFromLDAPUser(
			long companyId, LdapContext ctx, long userId, Attribute attr)
		throws Exception {

		// Remove all user group membership from user

		UserGroupLocalServiceUtil.clearUserUserGroups(userId);

		for (int i = 0; i < attr.size(); i++) {

			// Find group in LDAP

			String fullGroupDN = (String)attr.get(i);

			Attributes groupAttributes;

			try {
				groupAttributes = _getGroupAttributes(companyId, ctx, fullGroupDN);
			}
			catch (NameNotFoundException nnfe) {
				_log.error(
					"LDAP group not found with fullGroupDN " + fullGroupDN);

				_log.error(nnfe, nnfe);

				continue;
			}

			UserGroup userGroup = _importLDAPGroup(
				companyId, ctx, groupAttributes, false);

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

	public static UserGroup _importLDAPGroup(
		long companyId, LdapContext ctx, Attributes attributes,
		boolean importGroupMembership)
		throws Exception {

		AttributesTransformer attributesTransformer =
			AttributesTransformerFactory.getInstance();

		attributes = attributesTransformer.transformGroup(attributes);

		Properties groupMappings = _getGroupMappings(companyId);

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
				sb.append(
					LDAPUtil.getAttributeValue(
						attributes, groupMappings.getProperty("groupName")));
				sb.append("))");

				attribute = _getMultivaluedAttribute(
					companyId, ctx, baseDN, sb.toString(), attribute);

				_importUsersAndMembershipFromLDAPGroup(
					companyId, ctx, userGroup.getUserGroupId(), attribute);
			}
		}

		return userGroup;
	}

	private static User _importLDAPUser(
			Attributes attributes, Properties userMappings,
			Properties customMappings, long companyId, String password)
		throws Exception {

		LDAPUserHolder data =
			_converter.importLDAPUser(
				attributes, userMappings, customMappings, companyId, password);

		if (data == null) {
			return null;
		}

		User user = _findLiferayUser(data, companyId);

		if (user != null && user.isDefaultUser()) {
			return null;
		}

		Date ldapUserModifiedDate = _getLDAPUserModifiedDate(attributes);

		if (user != null) {
			// Check timestamp for last sync
			if (ldapUserModifiedDate != null &&
				ldapUserModifiedDate.equals(user.getModifiedDate()) &&
				data.isAutoPassword()) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"User is already syncronized, skipping user " +
							user.getEmailAddress());
				}

				return user;
			}

			// LPS-443

			if (Validator.isNull(data.getUserData().getScreenName())) {
				data.setAutoScreenName(true);
			}

			if (data.isAutoScreenName()) {
				ScreenNameGenerator screenNameGenerator =
					(ScreenNameGenerator) InstancePool.get(
						PropsValues.USERS_SCREEN_NAME_GENERATOR);

				data.getUserData().setScreenName(
						screenNameGenerator.generate(
							companyId, user.getUserId(),
							data.getUserData().getEmailAddress()));
			}
			user = _updateUser(data, user, ldapUserModifiedDate, password);
		}
		else {
			user = _createUser(data, companyId, password);
		}

		if (user == null) {
			return user;
		}

		UserIndexer.setEnabled(true);

		try {
			UserIndexer.updateUser(user);
		}
		catch (SearchException se) {
			_log.error("Indexing " + user.getUserId(), se);
		}

		return user;
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
				_log.error("LDAP user not found with fullUserDN " + fullUserDN);

				_log.error(nnfe, nnfe);

				continue;
			}

			User user = _importLDAPUserAndGroupMembership(
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

	private static User _findLiferayUser(LDAPUserHolder data, long companyId)
		throws Exception {

		User user = null;

		try {
			String authType = PrefsPropsUtil.getString(
				companyId, PropsKeys.COMPANY_SECURITY_AUTH_TYPE,
				PropsValues.COMPANY_SECURITY_AUTH_TYPE);

			if (authType.equals(CompanyConstants.AUTH_TYPE_SN)) {
				user = UserLocalServiceUtil.getUserByScreenName(
					companyId, data.getUserData().getScreenName());
			}
			else {
				user = UserLocalServiceUtil.getUserByEmailAddress(
					companyId, data.getUserData().getEmailAddress());
			}
		}
		catch (NoSuchUserException e) {
			//	User not found, don't care about the reason
		}

		return user;
	}

	private static Date _getLDAPUserModifiedDate(Attributes attributes) {

		Date ldapUserModifiedDate = null;

		try {
			String modifiedDate = LDAPUtil.getAttributeValue(
				attributes, "modifyTimestamp");

			if (Validator.isNotNull(modifiedDate)) {
				DateFormat dateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");

				ldapUserModifiedDate = dateFormat.parse(modifiedDate);
			}
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to retrieve last modified date");
			}
		}

		return ldapUserModifiedDate;
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
			ctx.setRequestControls(new Control[0]);

			NamingEnumeration<SearchResult> enu = ctx.search(
				baseDN, filter, cons);

			while (enu.hasMoreElements()) {
				results.add(enu.nextElement());
			}

			enu.close();
		}

		return results;
	}

	private static User _createUser(
		LDAPUserHolder data, long companyId, String password) {

		User user = null;

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Adding user to portal " +
					data.getEmailAddress());
			}

			Calendar birthdayCal = CalendarFactoryUtil.getCalendar();
			birthdayCal.setTime(data.getBirthday());

			int birthdayMonth = birthdayCal.get(Calendar.MONTH);
			int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
			int birthdayYear = birthdayCal.get(Calendar.YEAR);

			CompanyThreadLocal.setCompanyId(companyId);
			
			user = UserLocalServiceUtil.addUser(
				data.getCreatorUserId(), companyId, data.isAutoPassword(),
				password, password, data.isAutoScreenName(),
				data.getScreenName(), data.getEmailAddress(),
				data.getOpenId(), data.getLocale(), data.getFirstName(),
				data.getMiddleName(), data.getLastName(),
				data.getPrefixId(), data.getSuffixId(), data.getMale(),
				birthdayMonth, birthdayDay, birthdayYear,
				data.getJobTitle(), data.getGroupIds(),
				data.getOrganizationIds(), data.getRoleIds(),
				data.getUserGroupIds(), data.isSendEmail(),
				data.getServiceContext());

			_updateExpando(user, data);
		}
		catch (Exception e) {
			_log.error(
				"Problem adding user with screen name " +
					data.getScreenName() + " and email address " +
					data.getEmailAddress(),
				e);
		}

		return user;
	}

	private static User _updateUser(
		LDAPUserHolder data, User existingUser, Date ldapUserModifiedDate,
		String password) {

		User user = null;

		try {
			Calendar birthdayCal = CalendarFactoryUtil.getCalendar();

			birthdayCal.setTime(existingUser.getContact().getBirthday());

			int birthdayMonth = birthdayCal.get(Calendar.MONTH);
			int birthdayDay = birthdayCal.get(Calendar.DAY_OF_MONTH);
			int birthdayYear = birthdayCal.get(Calendar.YEAR);

			data.getContactData().setBirthday(birthdayCal.getTime());

			// User exists so update user information

			CompanyThreadLocal.setCompanyId(existingUser.getCompanyId());

			if (data.isUpdatePassword()) {
				UserLocalServiceUtil.updatePassword(
					existingUser.getUserId(), password, password,
					data.isPasswordReset(), true);
			}

			user = UserLocalServiceUtil.updateUser(
				existingUser.getUserId(), password, StringPool.BLANK,
				StringPool.BLANK, data.isPasswordReset(),
				data.getReminderQueryQuestion(), data.getReminderQueryAnswer(),
				data.getScreenName(), data.getEmailAddress(), data.getOpenId(),
				data.getLanguageId(), data.getTimeZoneId(), data.getGreeting(),
				data.getComments(), data.getFirstName(), data.getMiddleName(),
				data.getLastName(), data.getPrefixId(), data.getSuffixId(),
				data.getMale(), birthdayMonth, birthdayDay, birthdayYear,
				data.getSmsSn(), data.getAimSn(), data.getFacebookSn(),
				data.getIcqSn(), data.getJabberSn(), data.getMsnSn(),
				data.getMySpaceSn(), data.getSkypeSn(), data.getTwitterSn(),
				data.getYmSn(), data.getJobTitle(), data.getGroupIds(),
				data.getOrganizationIds(), data.getRoleIds(),
				data.getUserGroupRoles(), data.getUserGroupIds(),
				data.getServiceContext());

				_updateExpando(user, data);

			if (ldapUserModifiedDate != null) {
				UserLocalServiceUtil.updateModifiedDate(
					user.getUserId(), ldapUserModifiedDate);
			}
		}
		catch (Exception e) {
			_log.error(
				"Error updating user with screen name " + data.getScreenName() +
					" and email address " + data.getEmailAddress(),
				e);
		}

		return user;
	}

	private static void _updateExpando(User user, LDAPUserHolder data) {
		ExpandoBridge expando = new ExpandoBridgeLocalImpl(user.getExpandoBridge());

		for (Map.Entry<String, String> expandoAttribute : data.getExpandoData().entrySet()) {
			if (expando.hasAttribute(expandoAttribute.getKey())) {
				int type = expando.getAttributeType(expandoAttribute.getKey());

				Serializable expandoValue =
					ExpandoConverterUtil.
						getAttributeFromString(
							type, expandoAttribute.getValue());

				expando.setAttribute(expandoAttribute.getKey(), expandoValue);
			}
		}
	}

	private static LDAPConverter _converter;

	private static Log _log = LogFactoryUtil.getLog(PortalLDAPUtil.class);

}