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
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Zsigmond Rab
 * @author Douglas Wong
 */
public class PermissionExporter {

	public static final String TEAM_ROLE = "[$TEAM_ROLE$]";

	protected Element exportGroupPermissions(
			long companyId, long groupId, String resourceName,
			String resourcePrimKey, Element parentEl, String elName)
		throws SystemException {

		Element el = parentEl.addElement(elName);

		List<Permission> permissions =
			PermissionLocalServiceUtil.getGroupPermissions(
				groupId, companyId, resourceName,
				ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);

		List<String> actions = ResourceActionsUtil.getActions(permissions);

		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i);

			Element actionKeyEl = el.addElement("action-key");

			actionKeyEl.addText(action);
		}

		return el;
	}

	protected void exportGroupRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentEl)
		throws SystemException {

		List<Role> roles = layoutCache.getGroupRoles_1to4(groupId);

		Element groupEl = exportRoles(
			companyId, resourceName, ResourceConstants.SCOPE_GROUP,
			String.valueOf(groupId), parentEl, entityName + "-roles", roles);

		if (groupEl.elements().isEmpty()) {
			parentEl.remove(groupEl);
		}
	}

	protected void exportInheritedPermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			String resourcePrimKey, Element parentEl, String entityName)
		throws PortalException, SystemException {

		Element entityPermissionsEl = SAXReaderUtil.createElement(
			entityName + "-permissions");

		Map<String, Long> entityMap = layoutCache.getEntityMap(
			companyId, entityName);

		Iterator<Map.Entry<String, Long>> itr = entityMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Long> entry = itr.next();

			String name = entry.getKey().toString();

			long entityGroupId = entry.getValue();

			Element entityEl = exportGroupPermissions(
				companyId, entityGroupId, resourceName, resourcePrimKey,
				entityPermissionsEl, entityName + "-actions");

			if (entityEl.elements().isEmpty()) {
				entityPermissionsEl.remove(entityEl);
			}
			else {
				entityEl.addAttribute("name", name);
			}
		}

		if (!entityPermissionsEl.elements().isEmpty()) {
			parentEl.add(entityPermissionsEl);
		}
	}

	protected void exportInheritedRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = SAXReaderUtil.createElement(
			entityName + "-roles");

		Map<String, Long> entityMap = layoutCache.getEntityMap(
			companyId, entityName);

		Iterator<Map.Entry<String, Long>> itr = entityMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Long> entry = itr.next();

			String name = entry.getKey().toString();

			long entityGroupId = entry.getValue();

			List<Role> entityRoles = layoutCache.getGroupRoles_1to4(
				entityGroupId);

			Element entityEl = exportRoles(
				companyId, resourceName, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId), entityRolesEl, entityName,
				entityRoles);

			if (entityEl.elements().isEmpty()) {
				entityRolesEl.remove(entityEl);
			}
			else {
				entityEl.addAttribute("name", name);
			}
		}

		if (!entityRolesEl.elements().isEmpty()) {
			parentEl.add(entityRolesEl);
		}
	}

	protected void exportLayoutPermissions(
			PortletDataContext context, LayoutCache layoutCache, long companyId,
			long groupId, Layout layout, Element layoutEl,
			boolean exportUserPermissions)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();
		String resourcePrimKey = String.valueOf(layout.getPlid());

		Element permissionsEl = layoutEl.addElement("permissions");

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			exportPermissions_5(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, false);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			exportPermissions_6(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, false);
		}
		else {
			exportPermissions_1to4(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, exportUserPermissions);
		}
	}

	protected void exportLayoutRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();

		exportGroupRoles(
			layoutCache, companyId, groupId, resourceName, "community",
			rolesEl);

		exportUserRoles(layoutCache, companyId, groupId, resourceName, rolesEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "organization",
			rolesEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			rolesEl);
	}

	protected void exportPermissions_1to4(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element permissionsEl,
			boolean exportUserPermissions)
		throws PortalException, SystemException {

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.GUEST);

		exportGroupPermissions(
			companyId, groupId, resourceName, resourcePrimKey, permissionsEl,
			"community-actions");

		if (groupId != guestGroup.getGroupId()) {
			exportGroupPermissions(
				companyId, guestGroup.getGroupId(), resourceName,
				resourcePrimKey, permissionsEl, "guest-actions");
		}

		if (exportUserPermissions) {
			exportUserPermissions(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl);
		}

		exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "organization");

		exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "user-group");
	}

	protected void exportPermissions_5(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element permissionsEl,
			boolean portletActions)
		throws PortalException, SystemException {

		Resource resource = layoutCache.getResource(
			companyId, groupId, resourceName,
			ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
			portletActions);

		List<Role> roles = layoutCache.getGroupRoles_5(groupId, resourceName);

		for (Role role : roles) {
			if (role.getName().equals(RoleConstants.ADMINISTRATOR)) {
				continue;
			}

			Element roleEl = permissionsEl.addElement("role");

			roleEl.addAttribute("name", role.getName());
			roleEl.addAttribute("description", role.getDescription());
			roleEl.addAttribute("type", String.valueOf(role.getType()));

			List<Permission> permissions =
				PermissionLocalServiceUtil.getRolePermissions(
					role.getRoleId(), resource.getResourceId());

			List<String> actions = ResourceActionsUtil.getActions(permissions);

			for (String action : actions) {
				Element actionKeyEl = roleEl.addElement("action-key");

				actionKeyEl.addText(action);
			}
		}
	}

	protected void exportPermissions_6(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element permissionsEl,
			boolean portletActions)
		throws PortalException, SystemException {

		List<Role> roles = layoutCache.getGroupRoles_5(groupId, resourceName);

		for (Role role : roles) {
			if (role.getName().equals(RoleConstants.ADMINISTRATOR)) {
				continue;
			}

			Element roleEl = permissionsEl.addElement("role");

			roleEl.addAttribute("name", role.getName());
			roleEl.addAttribute("description", role.getDescription());
			roleEl.addAttribute("type", String.valueOf(role.getType()));

			List<String> actionIds = null;

			if (portletActions) {
				actionIds = ResourceActionsUtil.getPortletResourceActions(
					resourceName);
			}
			else {
				actionIds = ResourceActionsUtil.getModelResourceActions(
					resourceName);
			}

			List<String> actions =
				ResourcePermissionLocalServiceUtil.
					getAvailableResourcePermissionActionIds(
						companyId, resourceName,
						ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
						role.getRoleId(), actionIds);

			for (String action : actions) {
				Element actionKeyEl = roleEl.addElement("action-key");

				actionKeyEl.addText(action);
			}
		}
	}

	protected void exportPortletDataPermissions(PortletDataContext context)
		throws SystemException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("portlet-data-permissions");

			Map<String, List<KeyValuePair>> permissionsMap =
				context.getPermissions();

			for (Map.Entry<String, List<KeyValuePair>> entry :
					permissionsMap.entrySet()) {

				String[] permissionEntry = entry.getKey().split(
					StringPool.POUND);

				Element portletDataEl = root.addElement("portlet-data");

				portletDataEl.addAttribute("resource-name", permissionEntry[0]);
				portletDataEl.addAttribute("resource-pk", permissionEntry[1]);

				List<KeyValuePair> permissions = entry.getValue();

				for (KeyValuePair permission : permissions) {
					String roleName = permission.getKey();
					String actions = permission.getValue();

					Element permissionsEl = portletDataEl.addElement(
						"permissions");

					permissionsEl.addAttribute("role-name", roleName);
					permissionsEl.addAttribute("actions", actions);
				}
			}

			context.addZipEntry(
				context.getRootPath() + "/portlet-data-permissions.xml",
				doc.formattedString());
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void exportPortletPermissions(
			PortletDataContext context, LayoutCache layoutCache,
			String portletId, Layout layout, Element portletEl)
		throws PortalException, SystemException {

		long companyId = context.getCompanyId();
		long groupId = context.getGroupId();

		String resourceName = PortletConstants.getRootPortletId(portletId);
		String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			layout.getPlid(), portletId);

		Element permissionsEl = portletEl.addElement("permissions");

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			exportPermissions_5(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, true);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			exportPermissions_6(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, true);
		}
		else {
			boolean exportUserPermissions = MapUtil.getBoolean(
				context.getParameterMap(),
				PortletDataHandlerKeys.USER_PERMISSIONS);

			exportPermissions_1to4(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, exportUserPermissions);

			Element rolesEl = portletEl.addElement("roles");

			exportPortletRoles(
				layoutCache, companyId, groupId, portletId, rolesEl);
		}
	}

	protected void exportPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String portletId, Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = PortletConstants.getRootPortletId(
			portletId);

		Element portletEl = rolesEl.addElement("portlet");

		portletEl.addAttribute("portlet-id", portletId);

		exportGroupRoles(
			layoutCache, companyId, groupId, resourceName, "community",
			portletEl);

		exportUserRoles(
			layoutCache, companyId, groupId, resourceName, portletEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "organization",
			portletEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			portletEl);

		if (portletEl.elements().isEmpty()) {
			rolesEl.remove(portletEl);
		}
	}

	protected Element exportRoles(
			long companyId, String resourceName, int scope,
			String resourcePrimKey, Element parentEl, String elName,
			List<Role> roles)
		throws SystemException {

		Element el = parentEl.addElement(elName);

		Map<String, List<String>> resourceRoles =
			RoleLocalServiceUtil.getResourceRoles(
				companyId, resourceName, scope, resourcePrimKey);

		Iterator<Map.Entry<String, List<String>>> itr =
			resourceRoles.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, List<String>> entry = itr.next();

			String roleName = entry.getKey().toString();

			if (hasRole(roles, roleName)) {
				Element roleEl = el.addElement("role");

				roleEl.addAttribute("name", roleName);

				List<String> actions = entry.getValue();

				for (int i = 0; i < actions.size(); i++) {
					String action = actions.get(i);

					Element actionKeyEl = roleEl.addElement("action-key");

					actionKeyEl.addText(action);
					actionKeyEl.addAttribute("scope", String.valueOf(scope));
				}
			}
		}

		return el;
	}

	protected void exportUserPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentEl)
		throws SystemException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		Element userPermissionsEl = SAXReaderUtil.createElement(
			"user-permissions");

		List<User> users = layoutCache.getGroupUsers(groupId);

		for (User user : users) {
			String uuid = user.getUuid();

			Element userActionsEl = SAXReaderUtil.createElement("user-actions");

			List<Permission> permissions =
				PermissionLocalServiceUtil.getUserPermissions(
					user.getUserId(), companyId, resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);

			List<String> actions = ResourceActionsUtil.getActions(permissions);

			for (String action : actions) {
				Element actionKeyEl = userActionsEl.addElement("action-key");

				actionKeyEl.addText(action);
			}

			if (!userActionsEl.elements().isEmpty()) {
				userActionsEl.addAttribute("uuid", uuid);
				userPermissionsEl.add(userActionsEl);
			}
		}

		if (!userPermissionsEl.elements().isEmpty()) {
			parentEl.add(userPermissionsEl);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Export user permissions for {" + resourceName + ", " +
					resourcePrimKey + "} with " + users.size() +
						" users takes " + stopWatch.getTime() + " ms");
		}
	}

	protected void exportUserRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, Element parentEl)
		throws SystemException {

		Element userRolesEl = SAXReaderUtil.createElement("user-roles");

		List<User> users = layoutCache.getGroupUsers(groupId);

		for (User user : users) {
			long userId = user.getUserId();
			String uuid = user.getUuid();

			List<Role> userRoles = layoutCache.getUserRoles(userId);

			Element userEl = exportRoles(
				companyId, resourceName, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId), userRolesEl, "user", userRoles);

			if (userEl.elements().isEmpty()) {
				userRolesEl.remove(userEl);
			}
			else {
				userEl.addAttribute("uuid", uuid);
			}
		}

		if (!userRolesEl.elements().isEmpty()) {
			parentEl.add(userRolesEl);
		}
	}

	protected boolean hasRole(List<Role> roles, String roleName) {
		if ((roles == null) || (roles.size() == 0)) {
			return false;
		}

		for (Role role : roles) {
			if (role.getName().equals(roleName)) {
				return true;
			}
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(PermissionExporter.class);

}