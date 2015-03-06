/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.ldap.exportimport;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.authenticator.ldap.configuration.LDAPAuthConfiguration;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.ldap.GroupConverterKeys;
import com.liferay.portal.ldap.PortalLDAPContext;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.exportimport.UserExporter;
import com.liferay.portal.security.exportimport.UserOperation;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.security.ldap.Modifications;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.security.ldap.PortalToLDAPConverter;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SchemaViolationException;
import javax.naming.ldap.LdapContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Wesley Gong
 * @author Vilmos Papp
 */
@Component(
	configurationPid = "com.liferay.portal.authenticator.ldap.configuration.LDAPAuthConfiguration",
	immediate = true, service = UserExporter.class
)
public class LDAPUserExporterImpl implements UserExporter {

	@Override
	public void exportUser(
			Contact contact, Map<String, Serializable> contactExpandoAttributes)
		throws Exception {

		long companyId = contact.getCompanyId();

		if (!PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_AUTH_ENABLED,
				_ldapAuthConfiguration.enabled()) ||
			!LDAPSettingsUtil.isExportEnabled(companyId)) {

			return;
		}

		User user = UserLocalServiceUtil.getUserByContactId(
			contact.getContactId());

		if (user.isDefaultUser() ||
			(user.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		long ldapServerId = PortalLDAPUtil.getLdapServerId(
			companyId, user.getScreenName(), user.getEmailAddress());

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
				ldapServerId, contact.getCompanyId(), user.getScreenName(),
				user.getEmailAddress());

			if (binding == null) {
				Properties userMappings = LDAPSettingsUtil.getUserMappings(
					ldapServerId, companyId);

				binding = addUser(
					ldapServerId, ldapContext, user, userMappings);
			}

			Name name = new CompositeName();

			name.add(
				PortalLDAPUtil.getNameInNamespace(
					ldapServerId, companyId, binding));

			Modifications modifications =
				_portalToLDAPConverter.getLDAPContactModifications(
					contact, contactExpandoAttributes, contactMappings,
					contactExpandoMappings);

			if (modifications == null) {
				return;
			}

			ModificationItem[] modificationItems = modifications.getItems();

			ldapContext.modifyAttributes(name, modificationItems);
		}
		finally {
			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	@Override
	public void exportUser(
			long userId, long userGroupId, UserOperation userOperation)
		throws Exception {

		User user = UserLocalServiceUtil.getUser(userId);

		long companyId = user.getCompanyId();

		if (!PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_AUTH_ENABLED,
				_ldapAuthConfiguration.enabled()) ||
			!LDAPSettingsUtil.isExportEnabled(companyId) ||
			!LDAPSettingsUtil.isExportGroupEnabled(companyId)) {

			return;
		}

		long ldapServerId = PortalLDAPUtil.getLdapServerId(
			companyId, user.getScreenName(), user.getEmailAddress());

		LdapContext ldapContext = PortalLDAPUtil.getContext(
			ldapServerId, companyId);

		if (ldapContext == null) {
			return;
		}

		UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(
			userGroupId);

		Properties groupMappings = LDAPSettingsUtil.getGroupMappings(
			ldapServerId, companyId);
		Properties userMappings = LDAPSettingsUtil.getUserMappings(
			ldapServerId, companyId);

		Binding binding = PortalLDAPUtil.getGroup(
			ldapServerId, companyId, userGroup.getName());

		try {
			if (binding == null) {
				if (userOperation == UserOperation.ADD) {
					addGroup(
						ldapServerId, ldapContext, userGroup, user,
						groupMappings, userMappings);
				}

				return;
			}

			Name name = new CompositeName();

			name.add(
				PortalLDAPUtil.getNameInNamespace(
					ldapServerId, companyId, binding));

			Modifications modifications =
				_portalToLDAPConverter.getLDAPGroupModifications(
					ldapServerId, userGroup, user, groupMappings, userMappings,
					userOperation);

			ModificationItem[] modificationItems = modifications.getItems();

			ldapContext.modifyAttributes(name, modificationItems);
		}
		catch (SchemaViolationException sve) {
			String fullGroupDN = PortalLDAPUtil.getNameInNamespace(
				ldapServerId, companyId, binding);

			Attributes attributes = PortalLDAPUtil.getGroupAttributes(
				ldapServerId, companyId, ldapContext, fullGroupDN, true);

			Attribute groupMembers = attributes.get(
				groupMappings.getProperty(GroupConverterKeys.USER));

			if ((groupMembers != null) && (groupMembers.size() == 1)) {
				ldapContext.unbind(fullGroupDN);
			}
		}
		finally {
			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	@Override
	public void exportUser(
			User user, Map<String, Serializable> userExpandoAttributes)
		throws Exception {

		if (user.isDefaultUser() ||
			(user.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		long companyId = user.getCompanyId();

		if (!PrefsPropsUtil.getBoolean(
				companyId, PropsKeys.LDAP_AUTH_ENABLED,
				_ldapAuthConfiguration.enabled()) ||
			!LDAPSettingsUtil.isExportEnabled(companyId)) {

			return;
		}

		long ldapServerId = PortalLDAPUtil.getLdapServerId(
			companyId, user.getScreenName(), user.getEmailAddress());

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
				ldapServerId, user.getCompanyId(), user.getScreenName(),
				user.getEmailAddress(), true);

			if (binding == null) {
				binding = addUser(
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

			if (modifications == null) {
				return;
			}

			ModificationItem[] modificationItems = modifications.getItems();

			ldapContext.modifyAttributes(name, modificationItems);

			if (!LDAPSettingsUtil.isExportGroupEnabled(companyId)) {
				return;
			}

			List<UserGroup> userGroups =
				UserGroupLocalServiceUtil.getUserUserGroups(user.getUserId());

			for (UserGroup userGroup : userGroups) {
				exportUser(
					user.getUserId(), userGroup.getUserGroupId(),
					UserOperation.ADD);
			}

			Modifications groupModifications =
				_portalToLDAPConverter.getLDAPUserGroupModifications(
					ldapServerId, userGroups, user, userMappings);

			ModificationItem[] groupModificationItems =
				groupModifications.getItems();

			if (groupModificationItems.length > 0) {
				ldapContext.modifyAttributes(name, groupModificationItems);
			}
		}
		catch (NameNotFoundException nnfe) {
			if (PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.LDAP_AUTH_REQUIRED,
					_ldapAuthConfiguration.required())) {

				throw nnfe;
			}

			_log.error(nnfe, nnfe);
		}
		finally {
			if (ldapContext != null) {
				ldapContext.close();
			}
		}
	}

	@Reference
	public void setPortalToLDAPConverter(
		PortalToLDAPConverter portalToLDAPConverter) {

		_portalToLDAPConverter = portalToLDAPConverter;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ldapAuthConfiguration = Configurable.createConfigurable(
			LDAPAuthConfiguration.class, properties);
	}

	protected Binding addGroup(
			long ldapServerId, LdapContext ldapContext, UserGroup userGroup,
			User user, Properties groupMappings, Properties userMappings)
		throws Exception {

		Name name = new CompositeName();

		name.add(
			_portalToLDAPConverter.getGroupDNName(
				ldapServerId, userGroup, groupMappings));

		Attributes attributes = _portalToLDAPConverter.getLDAPGroupAttributes(
			ldapServerId, userGroup, user, groupMappings, userMappings);

		ldapContext.bind(name, new PortalLDAPContext(attributes));

		Binding binding = PortalLDAPUtil.getGroup(
			ldapServerId, userGroup.getCompanyId(), userGroup.getName());

		return binding;
	}

	protected Binding addUser(
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
			ldapServerId, user.getCompanyId(), user.getScreenName(),
			user.getEmailAddress());

		return binding;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LDAPUserExporterImpl.class);

	private volatile LDAPAuthConfiguration _ldapAuthConfiguration;
	private PortalToLDAPConverter _portalToLDAPConverter;

}