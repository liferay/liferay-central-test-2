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
 * <a href="PermissionImporter.java.html"><b><i>View Source</i></b></a>
 *
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
			long companyId, long groupId, String resourceName,
			String resourcePrimKey, Element parentEl, String elName,
			boolean portletActions)
		throws PortalException, SystemException {

		Element actionEl = parentEl.element(elName);

		if (actionEl == null) {
			return;
		}

		List<String> actions = getActions(actionEl);

		Resource resource = _layoutCache.getResource(
			companyId, groupId, resourceName,
			ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
			portletActions);

		PermissionLocalServiceUtil.setGroupPermissions(
			groupId, actions.toArray(new String[actions.size()]),
			resource.getResourceId());
	}

	protected void importGroupRoles(
			long companyId, long groupId, String resourceName,
			String entityName, Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = parentEl.element(entityName + "-roles");

		if (entityRolesEl == null) {
			return;
		}

		importRolePermissions(
			companyId, resourceName, ResourceConstants.SCOPE_GROUP,
			String.valueOf(groupId), entityRolesEl, true);
	}

	protected void importInheritedPermissions(
			long companyId, String resourceName, String resourcePrimKey,
			Element permissionsEl, String entityName, boolean portletActions)
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

			long entityGroupId = _layoutCache.getEntityGroupId(
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
					companyId, entityGroupId, resourceName, resourcePrimKey,
					parentEl, entityName + "-actions", portletActions);
			}
		}
	}

	protected void importInheritedRoles(
			long companyId, long groupId, String resourceName,
			String entityName, Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = parentEl.element(entityName + "-roles");

		if (entityRolesEl == null) {
			return;
		}

		List<Element> entityEls = entityRolesEl.elements(entityName);

		for (int i = 0; i < entityEls.size(); i++) {
			Element entityEl = entityEls.get(i);

			String name = entityEl.attributeValue("name");

			long entityGroupId = _layoutCache.getEntityGroupId(
				companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited roles for entity " + entityName +
						" with name " + name);
			}
			else {
				importRolePermissions(
					companyId, resourceName, ResourceConstants.SCOPE_GROUP,
					String.valueOf(groupId), entityEl, false);
			}
		}
	}

	protected void importLayoutPermissions(
			long companyId, long groupId, Layout layout, Element layoutEl,
			Element parentEl, long userId, boolean importUserPermissions)
		throws PortalException, SystemException {

		Element permissionsEl = layoutEl.element("permissions");
		Element rolesEl = parentEl.element("roles");

		if (permissionsEl != null) {
			String resourceName = Layout.class.getName();
			String resourcePrimKey = String.valueOf(layout.getPlid());

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				importPermissions_5(
					companyId, groupId, userId, resourceName, resourcePrimKey,
					permissionsEl, false);
			}
			else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				importPermissions_6(
					companyId, groupId, userId, resourceName, resourcePrimKey,
					permissionsEl, false);
			}
			else {
				Group guestGroup = GroupLocalServiceUtil.getGroup(
					companyId, GroupConstants.GUEST);

				importLayoutPermissions_4(
					companyId, groupId, guestGroup, layout, resourceName,
					resourcePrimKey, permissionsEl, importUserPermissions);
			}
		}

		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 5) &&
				(rolesEl != null)) {

			importLayoutRoles(companyId, groupId, rolesEl);
		}
	}

	protected void importLayoutPermissions_4(
			long companyId, long groupId, Group guestGroup, Layout layout,
			String resourceName, String resourcePrimKey, Element permissionsEl,
			boolean importUserPermissions)
		throws PortalException, SystemException {

		importGroupPermissions(
			companyId, groupId, resourceName, resourcePrimKey, permissionsEl,
			"community-actions", false);

		if (groupId != guestGroup.getGroupId()) {
			importGroupPermissions(
				companyId, guestGroup.getGroupId(), resourceName,
				resourcePrimKey, permissionsEl, "guest-actions", false);
		}

		if (importUserPermissions) {
			importUserPermissions(
				companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, false);
		}

		importInheritedPermissions(
			companyId, resourceName, resourcePrimKey, permissionsEl,
			"organization", false);

		importInheritedPermissions(
			companyId, resourceName, resourcePrimKey, permissionsEl,
			"user-group", false);
	}

	protected void importLayoutRoles(
			long companyId, long groupId, Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();

		importGroupRoles(
			companyId, groupId, resourceName, "community", rolesEl);

		importUserRoles(companyId, groupId, resourceName, rolesEl);

		importInheritedRoles(
			companyId, groupId, resourceName, "organization", rolesEl);

		importInheritedRoles(
			companyId, groupId, resourceName, "user-group", rolesEl);
	}

	protected void importPermissions_5(
			long companyId, long groupId, long userId, String resourceName,
			String resourcePrimKey, Element permissionsEl,
			boolean portletActions)
		throws PortalException, SystemException {

		Resource resource = _layoutCache.getResource(
			companyId, groupId, resourceName,
			ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
			portletActions);

		List<Element> roleEls = permissionsEl.elements("role");

		for (Element roleEl : roleEls) {
			String name = roleEl.attributeValue("name");

			Role role = _layoutCache.getRole(companyId, name);

			if (role == null) {
				String description = roleEl.attributeValue("description");
				int type = Integer.valueOf(roleEl.attributeValue("type"));

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
			long companyId, long groupId, long userId, String resourceName,
			String resourcePrimKey, Element permissionsEl,
			boolean portletActions)
		throws PortalException, SystemException {

		List<Element> roleEls = permissionsEl.elements("role");

		for (Element roleEl : roleEls) {
			String name = roleEl.attributeValue("name");

			Role role = _layoutCache.getRole(companyId, name);

			if (role == null) {
				String description = roleEl.attributeValue("description");
				int type = Integer.valueOf(roleEl.attributeValue("type"));

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
			long companyId, long groupId, Layout layout, Element portletEl,
			String portletId, long userId, boolean importUserPermissions)
		throws PortalException, SystemException {

		Element permissionsEl = portletEl.element("permissions");
		Element rolesEl = portletEl.element("roles");

		if (permissionsEl != null) {
			String resourceName = PortletConstants.getRootPortletId(portletId);

			String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				importPermissions_5(
					companyId, groupId, userId, resourceName, resourcePrimKey,
					permissionsEl, true);
			}
			else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				importPermissions_6(
					companyId, groupId, userId, resourceName, resourcePrimKey,
					permissionsEl, true);
			}
			else {
				Group guestGroup = GroupLocalServiceUtil.getGroup(
					companyId, GroupConstants.GUEST);

				importPortletPermissions_4(
					companyId, groupId, guestGroup, layout, permissionsEl,
					importUserPermissions);
			}
		}

		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 5) &&
				(rolesEl != null)) {

			importPortletRoles(companyId, groupId, portletEl);
			importPortletRoles(companyId, groupId, portletId, rolesEl);
		}
	}

	protected void importPortletPermissions_4(
			long companyId, long groupId, Group guestGroup, Layout layout,
			Element permissionsEl, boolean importUserPermissions)
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
					companyId, groupId, resourceName, resourcePrimKey,
					portletEl, "community-actions", true);

				if (groupId != guestGroup.getGroupId()) {
					importGroupPermissions(
						companyId, guestGroup.getGroupId(), resourceName,
						resourcePrimKey, portletEl, "guest-actions", true);
				}

				if (importUserPermissions) {
					importUserPermissions(
						companyId, groupId, resourceName, resourcePrimKey,
						portletEl, true);
				}

				importInheritedPermissions(
					companyId, resourceName, resourcePrimKey, portletEl,
					"organization", true);

				importInheritedPermissions(
					companyId, resourceName, resourcePrimKey, portletEl,
					"user-group", true);
			}
		}
	}

	protected void importPortletRoles(
			long companyId, long groupId, String portletId, Element rolesEl)
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
				companyId, groupId, resourceName, "community", rolesEl);

			importUserRoles(companyId, groupId, resourceName, rolesEl);

			importInheritedRoles(
				companyId, groupId, resourceName, "organization", rolesEl);

			importInheritedRoles(
				companyId, groupId, resourceName, "user-group", rolesEl);
		}
	}

	protected void importPortletRoles(
			long companyId, long groupId, Element rolesEl)
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
					companyId, groupId, resourceName, "community", portletEl);

				importUserRoles(companyId, groupId, resourceName, portletEl);

				importInheritedRoles(
					companyId, groupId, resourceName, "organization",
					portletEl);

				importInheritedRoles(
					companyId, groupId, resourceName, "user-group", portletEl);
			}
		}
	}

	protected void importRolePermissions(
			long companyId, String resourceName, int scope,
			String resourcePrimKey, Element parentEl, boolean communityRole)
		throws PortalException, SystemException {

		List<Element> roleEls = parentEl.elements("role");

		for (int i = 0; i < roleEls.size(); i++) {
			Element roleEl = roleEls.get(i);

			String roleName = roleEl.attributeValue("name");

			Role role = _layoutCache.getRole(companyId, roleName);

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
			long companyId, long groupId, String resourceName,
			String resourcePrimKey, Element parentEl, boolean portletActions)
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

			User user = _layoutCache.getUser(companyId, groupId, uuid);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring permissions for user with uuid " + uuid);
				}
			}
			else {
				List<String> actions = getActions(userActionsEl);

				Resource resource = _layoutCache.getResource(
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
			long companyId, long groupId, String resourceName, Element parentEl)
		throws PortalException, SystemException {

		Element userRolesEl = parentEl.element("user-roles");

		if (userRolesEl == null) {
			return;
		}

		List<Element> userEls = userRolesEl.elements("user");

		for (int i = 0; i < userEls.size(); i++) {
			Element userEl = userEls.get(i);

			String uuid = userEl.attributeValue("uuid");

			User user = _layoutCache.getUser(companyId, groupId, uuid);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("Ignoring roles for user with uuid " + uuid);
				}
			}
			else {
				importRolePermissions(
					companyId, resourceName, ResourceConstants.SCOPE_GROUP,
					String.valueOf(groupId), userEl, false);
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
				String className = portletDataEl.attributeValue("class-name");
				long classPK = GetterUtil.getLong(
					portletDataEl.attributeValue("class-pk"));

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

				context.addPermissions(className, classPK, permissions);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PermissionImporter.class);

	private LayoutCache _layoutCache = new LayoutCache();

}