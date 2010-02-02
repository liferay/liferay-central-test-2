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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
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
 * @author Ryan Park
 * @author Wesley Gong
 */
public class PortalLDAPUtil {

	public static LdapContext getContext(long ldapServerId, long companyId)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		String baseProviderURL = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_PROVIDER_URL + postfix);
		String pricipal = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_SECURITY_PRINCIPAL + postfix);
		String credentials = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_SECURITY_CREDENTIALS + postfix);

		return getContext(companyId, baseProviderURL, pricipal, credentials);
	}

	public static LdapContext getContext(
			long companyId, String providerURL, String principal,
			String credentials)
		throws Exception {

		Properties env = new Properties();

		env.put(
			Context.INITIAL_CONTEXT_FACTORY,
			PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_FACTORY_INITIAL));
		env.put(Context.PROVIDER_URL, providerURL);
		env.put(Context.SECURITY_PRINCIPAL, principal);
		env.put(Context.SECURITY_CREDENTIALS, credentials);
		env.put(
			Context.REFERRAL,
			PrefsPropsUtil.getString(companyId, PropsKeys.LDAP_REFERRAL));

		// Enable pooling

		env.put("com.sun.jndi.ldap.connect.pool", "true");
		env.put("com.sun.jndi.ldap.connect.pool.maxsize","50");
		env.put("com.sun.jndi.ldap.connect.pool.timeout", "10000");

		LogUtil.debug(_log, env);

		LdapContext ldapContext = null;

		try {
			ldapContext = new InitialLdapContext(env, null);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Failed to bind to the LDAP server");
			}

			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return ldapContext;
	}

	public static Attributes getGroupAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName)
		throws Exception {

		return getGroupAttributes(ldapServerId, companyId, ldapContext,
			fullDistinguishedName, false);
	}

	public static Attributes getGroupAttributes(
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName, boolean includeReferenceAttributes)
		throws Exception {

		Properties groupMappings = LDAPSettingsUtil.getGroupMappings(
			ldapServerId, companyId);

		List<String> mappedGroupAttributeIds = new ArrayList<String>();

		mappedGroupAttributeIds.add(groupMappings.getProperty("groupName"));
		mappedGroupAttributeIds.add(groupMappings.getProperty("description"));

		if (includeReferenceAttributes) {
			mappedGroupAttributeIds.add(groupMappings.getProperty("user"));
		}

		return _getAttributes(
			ldapContext, fullDistinguishedName,
			mappedGroupAttributeIds.toArray(new String[0]));
	}

	public static List<SearchResult> getGroups(
			long companyId, LdapContext ldapContext, int maxResults,
			String baseDN, String groupFilter)
		throws Exception {

		return searchLDAP(
			companyId, ldapContext, maxResults, baseDN, groupFilter, null);
	}

	public static List<SearchResult> getGroups(
			long ldapServerId, long companyId, LdapContext ldapContext,
			int maxResults)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN + postfix);
		String groupFilter = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER + postfix);

		return getGroups(
			companyId, ldapContext, maxResults, baseDN, groupFilter);
	}

	public static long getLdapServerId(long companyId, String screenName)
		throws Exception {

		long[] ldapServerIds = StringUtil.split(
			PrefsPropsUtil.getString(companyId, "ldap.server.ids"), 0L);

		for (long ldapServerId : ldapServerIds) {
			if (hasUser(ldapServerId, companyId, screenName)) {
				return ldapServerId;
			}
		}

		if (ldapServerIds.length > 0) {
			return ldapServerIds[0];
		}

		return 0;
	}

	public static Attribute getMultivaluedAttribute(
			long companyId, LdapContext ldapContext, String baseDN,
			String filter, Attribute attribute)
		throws Exception {

		if (attribute.size() > 0) {
			return attribute;
		}

		String[] attributeIds = {_getNextRange(attribute.getID())};

		while (true) {
			List<SearchResult> searchResults = searchLDAP(
				companyId, ldapContext, 0, baseDN, filter, attributeIds);

			if (searchResults.size() != 1) {
				break;
			}

			SearchResult searchResult = searchResults.get(0);

			Attributes attributes = searchResult.getAttributes();

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

	public static String getNameInNamespace(
			long ldapServerId, long companyId, Binding binding)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN + postfix);

		String name = binding.getName();

		if (name.startsWith(StringPool.QUOTE) &&
			name.endsWith(StringPool.QUOTE)) {

			name = name.substring(1, name.length() - 1);
		}

		if (Validator.isNull(baseDN)) {
			return name.toString();
		}
		else {
			return name.concat(StringPool.COMMA).concat(baseDN);
		}
	}

	public static Binding getUser(
			long ldapServerId, long companyId, String screenName)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		LdapContext ldapContext = getContext(ldapServerId, companyId);

		NamingEnumeration<SearchResult> enu = null;

		try {
			if (ldapContext == null) {
				return null;
			}

			String baseDN = PrefsPropsUtil.getString(
				companyId, PropsKeys.LDAP_BASE_DN + postfix);

			Properties userMappings = LDAPSettingsUtil.getUserMappings(
				ldapServerId, companyId);

			StringBundler filter = new StringBundler(5);

			filter.append(StringPool.OPEN_PARENTHESIS);
			filter.append(userMappings.getProperty("screenName"));
			filter.append(StringPool.EQUAL);
			filter.append(screenName);
			filter.append(StringPool.CLOSE_PARENTHESIS);

			SearchControls cons = new SearchControls(
				SearchControls.SUBTREE_SCOPE, 1, 0, null, false, false);

			enu = ldapContext.search(baseDN, filter.toString(), cons);
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (ldapContext != null) {
				ldapContext.close();
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
			long ldapServerId, long companyId, LdapContext ldapContext,
			String fullDistinguishedName)
		throws Exception {

		Properties userMappings = LDAPSettingsUtil.getUserMappings(
			ldapServerId, companyId);

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
			ldapContext, fullDistinguishedName, mappedUserAttributeIds);
	}

	public static List<SearchResult> getUsers(
			long companyId, LdapContext ldapContext, int maxResults,
			String baseDN, String userFilter)
		throws Exception {

		return searchLDAP(
			companyId, ldapContext, maxResults, baseDN, userFilter, null);
	}

	public static List<SearchResult> getUsers(
			long ldapServerId, long companyId, LdapContext ldapContext,
			int maxResults)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		String baseDN = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_BASE_DN + postfix);
		String userFilter = PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_IMPORT_USER_SEARCH_FILTER + postfix);

		return getUsers(companyId, ldapContext, maxResults, baseDN, userFilter);
	}

	public static String getUsersDN(long ldapServerId, long companyId)
		throws Exception {

		String postfix = LDAPSettingsUtil.getPropertyPostfix(ldapServerId);

		return PrefsPropsUtil.getString(
			companyId, PropsKeys.LDAP_USERS_DN + postfix);
	}

	public static boolean hasUser(
			long ldapServerId, long companyId, String screenName)
		throws Exception {

		if (getUser(ldapServerId, companyId, screenName) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public static List<SearchResult> searchLDAP(
			long companyId, LdapContext ldapContext, int maxResults,
			String baseDN, String filter, String[] attributeIds)
		throws Exception {

		List<SearchResult> searchResults = new ArrayList<SearchResult>();

		SearchControls cons = new SearchControls(
			SearchControls.SUBTREE_SCOPE, maxResults, 0, attributeIds, false,
			false);

		try {
			byte[] cookie = new byte[0];

			while (cookie != null) {
				if (cookie.length == 0) {
					ldapContext.setRequestControls(
						new Control[] {
							new PagedResultsControl(
								PropsValues.LDAP_PAGE_SIZE, Control.CRITICAL)
						});
				}
				else {
					ldapContext.setRequestControls(
						new Control[] {
							new PagedResultsControl(
								PropsValues.LDAP_PAGE_SIZE, cookie,
								Control.CRITICAL)
						});
				}

				NamingEnumeration<SearchResult> enu = ldapContext.search(
					baseDN, filter, cons);

				while (enu.hasMoreElements()) {
					searchResults.add(enu.nextElement());
				}

				enu.close();

				cookie = _getCookie(ldapContext.getResponseControls());
			}
		}
		catch (OperationNotSupportedException onse) {
			ldapContext.setRequestControls(null);

			NamingEnumeration<SearchResult> enu = ldapContext.search(
				baseDN, filter, cons);

			while (enu.hasMoreElements()) {
				searchResults.add(enu.nextElement());
			}

			enu.close();
		}
		finally {
			ldapContext.setRequestControls(null);
		}

		return searchResults;
	}

	private static Attributes _getAttributes(
			LdapContext ldapContext, String fullDistinguishedName,
			String[] attributeIds)
		throws Exception {

		Name fullDN = new CompositeName().add(fullDistinguishedName);

		Attributes attributes = null;

		String[] auditAttributeIds = {
			"creatorsName", "createTimestamp", "modifiersName",
			"modifyTimestamp"
		};

		if (attributeIds == null) {

			// Get complete listing of LDAP attributes (slow)

			attributes = ldapContext.getAttributes(fullDN);

			NamingEnumeration<? extends Attribute> enu =
				ldapContext.getAttributes(fullDN, auditAttributeIds).getAll();

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

			attributes = ldapContext.getAttributes(fullDN, allAttributeIds);
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

		StringBundler sb = new StringBundler(6);

		sb.append(originalAttributeId);
		sb.append(StringPool.SEMICOLON);
		sb.append("range=");
		sb.append(start);
		sb.append(StringPool.DASH);
		sb.append(end);

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(PortalLDAPUtil.class);

}