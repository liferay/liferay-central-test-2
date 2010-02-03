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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.ldap.Modifications;

import java.util.Properties;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.LdapContext;

/**
 * <a href="PortalLDAPExporterImpl.java.html}"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class PortalLDAPExporterImpl implements PortalLDAPExporter {

	/**
	 * Export contact information to LDAP.
	 * @param contact
	 * @throws Exception
	 */
	public void exportToLDAP(Contact contact) throws Exception {
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

			Properties contactMappings = LDAPSettingsUtil.getContactMappings(
				ldapServerId, companyId);

			Binding binding = PortalLDAPUtil.getUser(
				ldapServerId, contact.getCompanyId(), user.getScreenName());

			if (binding == null) {
				Properties userMappings = LDAPSettingsUtil.getUserMappings(
					ldapServerId, companyId);

				binding = _createLDAPUser(
					companyId, user, userMappings, ldapServerId, ldapContext);
			}

			Name name = new CompositeName();

			name.add(
				PortalLDAPUtil.getNameInNamespace(
					ldapServerId, companyId, binding));

			Modifications modifications =
				_portalToLDAPConverter.getLDAPContactModifications(
					name, contactMappings, contact, binding);

			ModificationItem[] modificationItems = modifications.getItems();

			ldapContext.modifyAttributes(name, modificationItems);
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

	public void exportToLDAP(User user) throws Exception {
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

			if (binding == null) {

				// Create new user in LDAP
				binding = _createLDAPUser(
					companyId, user, userMappings, ldapServerId, ldapContext);
			}

			Name name = new CompositeName();

			// Modify existing LDAP user record
			name.add(
				PortalLDAPUtil.getNameInNamespace(
					ldapServerId, companyId, binding));

			Modifications modifications =
				_portalToLDAPConverter.getLDAPUserModifications(
					name, userMappings, user, binding);

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

	public void setPortalToLDAPConverter(
		PortalToLDAPConverter portalToLDAPConverter) {

		_portalToLDAPConverter = portalToLDAPConverter;
	}

	private Binding _createLDAPUser(
			long companyId, User user, Properties userMappings,
			long ldapServerId, LdapContext ldapContext)
		throws Exception {

		Name name = new CompositeName();

		String dnName = _portalToLDAPConverter.getUserDNName(
			ldapServerId, companyId, user, userMappings);
		name.add(dnName);

		Attributes ldapUser = _portalToLDAPConverter.getLDAPUserAttributes(
			name, userMappings, user, ldapServerId);

		ldapContext.bind(name, new LDAPContext(ldapUser));

		Binding binding = PortalLDAPUtil.getUser(
			ldapServerId, user.getCompanyId(), user.getScreenName());

		return binding;
	}

	private static Log _log =
		LogFactoryUtil.getLog(PortalLDAPExporterImpl.class);
	private PortalToLDAPConverter _portalToLDAPConverter;

}