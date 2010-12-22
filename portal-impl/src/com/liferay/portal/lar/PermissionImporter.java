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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Wesley Gong
 * @author Zsigmond Rab
 * @author Douglas Wong
 */
public class PermissionImporter {

	protected List<String> getActions(Element el) {
		List<String> actions = new ArrayList<String>();

		Iterator<Element> itr = el.elements("action-key").iterator();

		while (itr.hasNext()) {
			Element actionEl = itr.next();

			actions.add(actionEl.getText());
		}

		return actions;
	}

	protected void importGroupPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentEl,
			String elName, boolean portletActions)
		throws PortalException, SystemException {

		Element actionEl = parentEl.element(elName);

		if (actionEl == null) {
			return;
		}

		List<String> actions = getActions(actionEl);

		Resource resource = layoutCache.getResource(
			companyId, groupId, resourceName,
			ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
			portletActions);

		PermissionLocalServiceUtil.setGroupPermissions(
			groupId, actions.toArray(new String[actions.size()]),
			resource.getResourceId());
	}

	protected void importGroupRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName,
			Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = parentEl.element(entityName + "-roles");

		if (entityRolesEl == null) {
			return;
		}

		importRolePermissions(
			layoutCache, companyId, resourceName, ResourceConstants.SCOPE_GROUP,
			String.valueOf(groupId), entityRolesEl, true);
	}

	protected void importInheritedPermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			String resourcePrimKey, Element permissionsEl, String entityName,
			boolean portletActions)
		throws PortalException, SystemException {

		Element entityPermissionsEl = permissionsEl.element(
			entityName + "-permissions");

		if (entityPermissionsEl == null) {
			return;
		}

		List<Element> actionsEls = entityPermissionsEl.elements(
			entityName + "-actions");

		for (int i = 0; i < actionsEls.size(); i++) {
			Element actionEl = actionsEls.get(i);

			String name = actionEl.attributeValue("name");

			long entityGroupId = layoutCache.getEntityGroupId(
				companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited permissions for entity " + entityName +
						" with name " + name);
			}
			else {
				Element parentEl = SAXReaderUtil.createElement("parent");

				parentEl.add(actionEl.createCopy());

				importGroupPermissions(
					layoutCache, companyId, entityGroupId, resourceName,
					resourcePrimKey, parentEl, entityName + "-actions",
					portletActions);
			}
		}
	}

	protected void importInheritedRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = parentEl.element(entityName + "-roles");

		if (entityRolesEl == null) {
			return;
		}

		List<Element> entityEls = entityRolesEl.elements(entityName);

		for (int i = 0; i < entityEls.size(); i++) {
			Element entityEl = entityEls.get(i);

			String name = entityEl.attributeValue("name");

			long entityGroupId = layoutCache.getEntityGroupId(
				companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited roles for entity " + entityName +
						" with name " + name);
			}
			else {
				importRolePermissions(
					layoutCache, companyId, resourceName,
					ResourceConstants.SCOPE_GROUP, String.valueOf(groupId),
					entityEl, false);
			}
		}
	}

	protected void importLayoutPermissions(
			LayoutCache layoutCache, long companyId, long groupId, long userId,
			Layout layout, Element layoutEl, Element parentEl,
			boolean importUserPermissions)
		throws PortalException, SystemException {

		Element permissionsEl = layoutEl.element("permissions");

		if (permissionsEl != null) {
			String resourceName = Layout.class.getName();
			String resourcePrimKey = String.valueOf(layout.getPlid());

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				importPermissions_5(
					layoutCache, companyId, groupId, userId, resourceName,
					resourcePrimKey, permissionsEl, false);
			}
			else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				importPermissions_6(
					layoutCache, companyId, groupId, userId, resourceName,
					resourcePrimKey, permissionsEl, false);
			}
			else {
				Group guestGroup = GroupLocalServiceUtil.getGroup(
					companyId, GroupConstants.GUEST);

				importLayoutPermissions_1to4(
					layoutCache, companyId, groupId, guestGroup, layout,
					resourceName, resourcePrimKey, permissionsEl,
					importUserPermissions);
			}
		}

		Element rolesEl = parentEl.element("roles");

		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 5) &&
			(rolesEl != null)) {

			importLayoutRoles(layoutCache, companyId, groupId, rolesEl);
		}
	}

	protected void importLayoutPermissions_1to4(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, String resourceName,
			String resourcePrimKey, Element permissionsEl,
			boolean importUserPermissions)
		throws PortalException, SystemException {

		importGroupPermissions(
			layoutCache, companyId, groupId, resourceName, resourcePrimKey,
			permissionsEl, "community-actions", false);

		if (groupId != guestGroup.getGroupId()) {
			importGroupPermissions(
				layoutCache, companyId, guestGroup.getGroupId(), resourceName,
				resourcePrimKey, permissionsEl, "guest-actions", false);
		}

		if (importUserPermissions) {
			importUserPermissions(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, false);
		}

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "organization", false);

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "user-group", false);
	}

	protected void importLayoutRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();

		importGroupRoles(
			layoutCache, companyId, groupId, resourceName, "community",
			rolesEl);

		importUserRoles(layoutCache, companyId, groupId, resourceName, rolesEl);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "organization",
			rolesEl);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			rolesEl);
	}

	protected void importPermissions_5(
			LayoutCache layoutCache, long companyId, long groupId, long userId,
			String resourceName, String resourcePrimKey, Element permissionsEl,
			boolean portletActions)
		throws PortalException, SystemException {

		Resource resource = layoutCache.getResource(
			companyId, groupId, resourceName,
			ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
			portletActions);

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		List<Element> roleEls = permissionsEl.elements("role");

		for (Element roleEl : roleEls) {
			String name = roleEl.attributeValue("name");

			Role role = layoutCache.getRole(companyId, name);

			if (role == null) {
				String description = roleEl.attributeValue("description");
				int type = Integer.valueOf(roleEl.attributeValue("type"));

				if ((type == RoleConstants.TYPE_COMMUNITY) &&
					group.isOrganization()) {

					type = RoleConstants.TYPE_ORGANIZATION;
				}
				else if ((type == RoleConstants.TYPE_ORGANIZATION) &&
						 group.isCommunity()) {

					type = RoleConstants.TYPE_COMMUNITY;
				}

				role = RoleLocalServiceUtil.addRole(
					userId, companyId, name, null, description, type);
			}

			List<String> actions = getActions(roleEl);

			PermissionLocalServiceUtil.setRolePermissions(
				role.getRoleId(), actions.toArray(new String[actions.size()]),
				resource.getResourceId());
		}
	}

	protected void importPermissions_6(
			LayoutCache layoutCache, long companyId, long groupId, long userId,
			String resourceName, String resourcePrimKey, Element permissionsEl,
			boolean portletActions)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		List<Element> roleEls = permissionsEl.elements("role");

		for (Element roleEl : roleEls) {
			String name = roleEl.attributeValue("name");

			Role role = layoutCache.getRole(companyId, name);

			if (role == null) {
				String description = roleEl.attributeValue("description");
				int type = Integer.valueOf(roleEl.attributeValue("type"));

				if ((type == RoleConstants.TYPE_COMMUNITY) &&
					group.isOrganization()) {

					type = RoleConstants.TYPE_ORGANIZATION;
				}
				else if ((type == RoleConstants.TYPE_ORGANIZATION) &&
						 group.isCommunity()) {

					type = RoleConstants.TYPE_COMMUNITY;
				}

				role = RoleLocalServiceUtil.addRole(
					userId, companyId, name, null, description, type);
			}

			List<String> actions = getActions(roleEl);

			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				companyId, resourceName, ResourceConstants.SCOPE_INDIVIDUAL,
				resourcePrimKey, role.getRoleId(),
				actions.toArray(new String[actions.size()]));
		}
	}

	protected void importPortletPermissions(
			LayoutCache layoutCache, long companyId, long groupId, long userId,
			Layout layout, Element portletEl, String portletId,
			boolean importUserPermissions)
		throws PortalException, SystemException {

		Element permissionsEl = portletEl.element("permissions");

		if (permissionsEl != null) {
			String resourceName = PortletConstants.getRootPortletId(portletId);

			String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				importPermissions_5(
					layoutCache, companyId, groupId, userId, resourceName,
					resourcePrimKey, permissionsEl, true);
			}
			else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				importPermissions_6(
					layoutCache, companyId, groupId, userId, resourceName,
					resourcePrimKey, permissionsEl, true);
			}
			else {
				Group guestGroup = GroupLocalServiceUtil.getGroup(
					companyId, GroupConstants.GUEST);

				importPortletPermissions_1to4(
					layoutCache, companyId, groupId, guestGroup, layout,
					permissionsEl, importUserPermissions);
			}
		}

		Element rolesEl = portletEl.element("roles");

		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 5) &&
			(rolesEl != null)) {

			importPortletRoles(layoutCache, companyId, groupId, portletEl);
			importPortletRoles(
				layoutCache, companyId, groupId, portletId, rolesEl);
		}
	}

	protected void importPortletPermissions_1to4(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, Element permissionsEl,
			boolean importUserPermissions)
		throws PortalException, SystemException {

		Iterator<Element> itr = permissionsEl.elements("portlet").iterator();

		while (itr.hasNext()) {
			Element portletEl = itr.next();

			String portletId = portletEl.attributeValue("portlet-id");

			String resourceName = PortletConstants.getRootPortletId(portletId);
			String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, resourceName);

			if (portlet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not import portlet permissions for " + portletId +
							" because the portlet does not exist");
				}
			}
			else {
				importGroupPermissions(
					layoutCache, companyId, groupId, resourceName,
					resourcePrimKey, portletEl, "community-actions", true);

				if (groupId != guestGroup.getGroupId()) {
					importGroupPermissions(
						layoutCache, companyId, guestGroup.getGroupId(),
						resourceName, resourcePrimKey, portletEl,
						"guest-actions", true);
				}

				if (importUserPermissions) {
					importUserPermissions(
						layoutCache, companyId, groupId, resourceName,
						resourcePrimKey, portletEl, true);
				}

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "organization", true);

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "user-group", true);
			}
		}
	}

	protected void importPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String portletId, Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = PortletConstants.getRootPortletId(portletId);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, resourceName);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not import portlet roles for " + portletId +
						" because the portlet does not exist");
			}
		}
		else {
			importGroupRoles(
				layoutCache, companyId, groupId, resourceName, "community",
				rolesEl);

			importUserRoles(
				layoutCache, companyId, groupId, resourceName, rolesEl);

			importInheritedRoles(
				layoutCache, companyId, groupId, resourceName,
				"organization", rolesEl);

			importInheritedRoles(
				layoutCache, companyId, groupId, resourceName, "user-group",
				rolesEl);
		}
	}

	protected void importPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesEl)
		throws PortalException, SystemException {

		Iterator<Element> itr = rolesEl.elements("portlet").iterator();

		while (itr.hasNext()) {
			Element portletEl = itr.next();

			String portletId = portletEl.attributeValue("portlet-id");

			String resourceName = PortletConstants.getRootPortletId(portletId);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, resourceName);

			if (portlet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not import portlet roles for " + portletId +
							" because the portlet does not exist");
				}
			}
			else {
				importGroupRoles(
					layoutCache, companyId, groupId, resourceName, "community",
					portletEl);

				importUserRoles(
					layoutCache, companyId, groupId, resourceName, portletEl);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName,
					"organization", portletEl);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName, "user-group",
					portletEl);
			}
		}
	}

	protected void importRolePermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			int scope, String resourcePrimKey, Element parentEl,
			boolean communityRole)
		throws PortalException, SystemException {

		List<Element> roleEls = parentEl.elements("role");

		for (int i = 0; i < roleEls.size(); i++) {
			Element roleEl = roleEls.get(i);

			String roleName = roleEl.attributeValue("name");

			Role role = layoutCache.getRole(companyId, roleName);

			if (role == null) {
				_log.warn(
					"Ignoring permissions for role with name " + roleName);
			}
			else {
				List<String> actions = getActions(roleEl);

				PermissionLocalServiceUtil.setRolePermissions(
					role.getRoleId(), companyId, resourceName, scope,
					resourcePrimKey,
					actions.toArray(new String[actions.size()]));

				if (communityRole) {
					long[] groupIds = {GetterUtil.getLong(resourcePrimKey)};

					GroupLocalServiceUtil.addRoleGroups(
						role.getRoleId(), groupIds);
				}
			}
		}
	}

	protected void importUserPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentEl,
			boolean portletActions)
		throws PortalException, SystemException {

		Element userPermissionsEl = parentEl.element("user-permissions");

		if (userPermissionsEl == null) {
			return;
		}

		List<Element> userActionsEls = userPermissionsEl.elements(
			"user-actions");

		for (int i = 0; i < userActionsEls.size(); i++) {
			Element userActionsEl = userActionsEls.get(i);

			String uuid = userActionsEl.attributeValue("uuid");

			User user = layoutCache.getUser(companyId, groupId, uuid);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring permissions for user with uuid " + uuid);
				}
			}
			else {
				List<String> actions = getActions(userActionsEl);

				Resource resource = layoutCache.getResource(
					companyId, groupId, resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
					portletActions);

				PermissionLocalServiceUtil.setUserPermissions(
					user.getUserId(),
					actions.toArray(new String[actions.size()]),
					resource.getResourceId());
			}
		}
	}

	protected void importUserRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, Element parentEl)
		throws PortalException, SystemException {

		Element userRolesEl = parentEl.element("user-roles");

		if (userRolesEl == null) {
			return;
		}

		List<Element> userEls = userRolesEl.elements("user");

		for (int i = 0; i < userEls.size(); i++) {
			Element userEl = userEls.get(i);

			String uuid = userEl.attributeValue("uuid");

			User user = layoutCache.getUser(companyId, groupId, uuid);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Ignoring roles for user with uuid " + uuid);
				}
			}
			else {
				importRolePermissions(
					layoutCache, companyId, resourceName,
					ResourceConstants.SCOPE_GROUP, String.valueOf(groupId),
					userEl, false);
			}
		}
	}

	protected void readPortletDataPermissions(PortletDataContext context)
		throws SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getSourceRootPath() + "/portlet-data-permissions.xml");

			if (xml == null) {
				return;
			}

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<Element> portletDataEls = root.elements("portlet-data");

			for (Element portletDataEl : portletDataEls) {
				String resourceName = portletDataEl.attributeValue(
					"resource-name");
				long resourcePK = GetterUtil.getLong(
					portletDataEl.attributeValue("resource-pk"));

				List<KeyValuePair> permissions = new ArrayList<KeyValuePair>();

				List<Element> permissionsEls = portletDataEl.elements(
					"permissions");

				for (Element permissionsEl : permissionsEls) {
					String roleName = permissionsEl.attributeValue("role-name");
					String actions = permissionsEl.attributeValue("actions");

					KeyValuePair permission = new KeyValuePair(
						roleName, actions);

					permissions.add(permission);
				}

				context.addPermissions(resourceName, resourcePK, permissions);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PermissionImporter.class);

}