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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ldap.Modifications;

import java.util.Properties;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Name;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.LdapContext;

/**
 * <a href="PortalLDAPExporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortalLDAPExporter {

	public static void exportToLDAP(Contact contact) throws Exception {
		long companyId = contact.getCompanyId();

		if (!LDAPSettingsUtil.isAuthEnabled(companyId) ||
			!LDAPSettingsUtil.isExportEnabled(companyId)) {

			return;
		}

		User user = UserLocalServiceUtil.getUserByContactId(
			contact.getContactId());

		long ldapServerId = PortalLDAPUtil.getLdapServerId(
			companyId, user.getScreenName());

		LdapContext ldapContext = PortalLDAPUtil.getContext(
			ldapServerId, companyId);

		try {
			if (ldapContext == null) {
				return;
			}

			Properties userMappings = LDAPSettingsUtil.getUserMappings(
				ldapServerId, companyId);
			Binding binding = PortalLDAPUtil.getUser(
				ldapServerId, contact.getCompanyId(), user.getScreenName());
			Name name = new CompositeName();

			if (binding == null) {

				// Create new user in LDAP

				_getDNName(
					ldapServerId, companyId, user, userMappings, name);

				LDAPUser ldapUser = (LDAPUser)Class.forName(
					PropsValues.LDAP_USER_IMPL).newInstance();

				ldapUser.setUser(user, ldapServerId);

				ldapContext.bind(name, ldapUser);
			}
			else {

				// Modify existing LDAP user record

				name.add(
					PortalLDAPUtil.getNameInNamespace(
						ldapServerId, companyId, binding));

				Modifications modifications = Modifications.getInstance();

				modifications.addItem(
					userMappings.getProperty("firstName"),
					contact.getFirstName());

				String middleNameMapping = userMappings.getProperty(
					"middleName");

				if (Validator.isNotNull(middleNameMapping)) {
					modifications.addItem(
						middleNameMapping, contact.getMiddleName());
				}

				modifications.addItem(
					userMappings.getProperty("lastName"),
					contact.getLastName());

				String fullNameMapping = userMappings.getProperty("fullName");

				if (Validator.isNotNull(fullNameMapping)) {
					modifications.addItem(
						fullNameMapping, contact.getFullName());
				}

				String jobTitleMapping = userMappings.getProperty("jobTitle");

				if (Validator.isNotNull(jobTitleMapping)) {
					modifications.addItem(
						jobTitleMapping, contact.getJobTitle());
				}

				ModificationItem[] modificationItems = modifications.getItems();

				ldapContext.modifyAttributes(name, modificationItems);
			}
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	public static void exportToLDAP(User user) throws Exception {
		long companyId = user.getCompanyId();

		if (!LDAPSettingsUtil.isAuthEnabled(companyId) ||
			!LDAPSettingsUtil.isExportEnabled(companyId)) {

			return;
		}

		long ldapServerId = PortalLDAPUtil.getLdapServerId(
			companyId, user.getScreenName());

		LdapContext ldapContext = PortalLDAPUtil.getContext(
			ldapServerId, companyId);

		try {
			if (ldapContext == null) {
				return;
			}

			Properties userMappings = LDAPSettingsUtil.getUserMappings(
				ldapServerId, companyId);
			Binding binding = PortalLDAPUtil.getUser(
				ldapServerId, user.getCompanyId(), user.getScreenName());
			Name name = new CompositeName();

			if (binding == null) {

				// Create new user in LDAP

				_getDNName(
					ldapServerId, companyId, user, userMappings, name);

				LDAPUser ldapUser = (LDAPUser) Class.forName(
					PropsValues.LDAP_USER_IMPL).newInstance();

				ldapUser.setUser(user, ldapServerId);

				ldapContext.bind(name, ldapUser);

				binding = PortalLDAPUtil.getUser(
					ldapServerId, user.getCompanyId(), user.getScreenName());

				name = new CompositeName();
			}

			// Modify existing LDAP user record

			name.add(
				PortalLDAPUtil.getNameInNamespace(
					ldapServerId, companyId, binding));

			Modifications modifications = Modifications.getInstance();

			modifications.addItem(
				userMappings.getProperty("firstName"), user.getFirstName());

			String middleNameMapping = userMappings.getProperty(
				"middleName");

			if (Validator.isNotNull(middleNameMapping)) {
				modifications.addItem(middleNameMapping, user.getMiddleName());
			}

			modifications.addItem(
				userMappings.getProperty("lastName"), user.getLastName());

			String fullNameMapping = userMappings.getProperty("fullName");

			if (Validator.isNotNull(fullNameMapping)) {
				modifications.addItem(fullNameMapping, user.getFullName());
			}

			if (user.isPasswordModified() &&
				Validator.isNotNull(user.getPasswordUnencrypted())) {

				modifications.addItem(
					userMappings.getProperty("password"),
					user.getPasswordUnencrypted());
			}

			if (Validator.isNotNull(user.getEmailAddress())) {
				modifications.addItem(
					userMappings.getProperty("emailAddress"),
					user.getEmailAddress());
			}

			String jobTitleMapping = userMappings.getProperty("jobTitle");

			if (Validator.isNotNull(jobTitleMapping)) {
				modifications.addItem(jobTitleMapping, user.getJobTitle());
			}

			ModificationItem[] modificationItems = modifications.getItems();

			ldapContext.modifyAttributes(name, modificationItems);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	private static void _getDNName(
			long ldapServerId, long companyId, User user,
			Properties userMappings, Name name)
		throws Exception {

		// Generate full DN based on user DN

		StringBundler sb = new StringBundler(5);

		sb.append(userMappings.getProperty("screenName"));
		sb.append(StringPool.EQUAL);
		sb.append(user.getScreenName());
		sb.append(StringPool.COMMA);
		sb.append(PortalLDAPUtil.getUsersDN(ldapServerId, companyId));

		name.add(sb.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(PortalLDAPExporter.class);

}