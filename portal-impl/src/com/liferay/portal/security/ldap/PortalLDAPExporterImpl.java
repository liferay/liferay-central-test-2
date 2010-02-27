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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.Serializable;

import java.util.Map;
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
 * @author Brian Wing Shun Chan
 */
public class PortalLDAPExporterImpl implements PortalLDAPExporter {

	public void exportToLDAP(
			Contact contact, Map<String, Serializable> contactExpandoAttributes)
		throws Exception {

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
			Properties contactExpandoMappings =
				LDAPSettingsUtil.getContactExpandoMappings(
					ldapServerId, companyId);

			Binding binding = PortalLDAPUtil.getUser(
				ldapServerId, contact.getCompanyId(), user.getScreenName());

			if (binding == null) {
				Properties userMappings = LDAPSettingsUtil.getUserMappings(
					ldapServerId, companyId);

				binding = createLDAPUser(
					ldapServerId, ldapContext, user, userMappings);
			}

			Name name = new CompositeName();

			name.add(
				PortalLDAPUtil.getNameInNamespace(
					ldapServerId, companyId, binding));

			Modifications modifications =
				_portalToLDAPConverter.getLDAPContactModifications(
					contact, contactExpandoAttributes,
					contactMappings, contactExpandoMappings);

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

	public void exportToLDAP(
			User user, Map<String, Serializable> userExpandoAttributes)
		throws Exception {

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
			Properties userExpandoMappings =
				LDAPSettingsUtil.getUserExpandoMappings(
					ldapServerId, companyId);

			Binding binding = PortalLDAPUtil.getUser(
				ldapServerId, user.getCompanyId(), user.getScreenName());

			if (binding == null) {
				binding = createLDAPUser(
					ldapServerId, ldapContext, user, userMappings);
			}

			Name name = new CompositeName();

			name.add(
				PortalLDAPUtil.getNameInNamespace(
					ldapServerId, companyId, binding));

			Modifications modifications =
				_portalToLDAPConverter.getLDAPUserModifications(
					user, userExpandoAttributes, userMappings,
					userExpandoMappings);

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

	protected Binding createLDAPUser(
			long ldapServerId, LdapContext ldapContext, User user,
			Properties userMappings)
		throws Exception {

		Name name = new CompositeName();

		name.add(
			_portalToLDAPConverter.getUserDNName(
				ldapServerId, user, userMappings));

		Attributes attributes = _portalToLDAPConverter.getLDAPUserAttributes(
			ldapServerId, user, userMappings);

		ldapContext.bind(name, new PortalLDAPContext(attributes));

		Binding binding = PortalLDAPUtil.getUser(
			ldapServerId, user.getCompanyId(), user.getScreenName());

		return binding;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortalLDAPExporterImpl.class);

	private PortalToLDAPConverter _portalToLDAPConverter;

}