/**
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

package com.liferay.portal.lar;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="LayoutCache.java.html"><b><i>View Source</i></b></a>
 *
 * @author Charles May
 */
public class LayoutCache {

	protected long getEntityGroupId(
			long companyId, String entityName, String name)
		throws SystemException {

		long entityGroupId = 0;

		Long entityGroupIdObj = entityGroupIdMap.get(entityName);

		if (entityGroupIdObj == null) {
			if (entityName.equals("user-group")) {
				List<UserGroup> userGroups = UserGroupLocalServiceUtil.search(
					companyId, name, null, null, 0, 1, null);

				if (userGroups.size() > 0) {
					UserGroup userGroup = userGroups.get(0);

					Group group = userGroup.getGroup();

					entityGroupId = group.getGroupId();
				}
			}
			else if (entityName.equals("organization")) {
				List<Organization> organizations =
					OrganizationLocalServiceUtil.search(
						companyId,
						OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, name,
						null, null, null, null, null, null, null, true, 0, 1);

				if (organizations.size() > 0) {
					Organization organization = organizations.get(0);

					Group group = organization.getGroup();

					entityGroupId = group.getGroupId();
				}
			}

			entityGroupIdMap.put(entityName, entityGroupId);
		}
		else {
			entityGroupId = entityGroupIdObj.longValue();
		}

		return entityGroupId;
	}

	protected Map<String, Long> getEntityMap(long companyId, String entityName)
		throws SystemException {

		Map<String, Long> entityMap = entityMapMap.get(entityName);

		if (entityMap == null) {
			entityMap = new HashMap<String, Long>();

			if (entityName.equals("user-group")) {
				List<UserGroup> userGroups = UserGroupLocalServiceUtil.search(
					companyId, null, null, null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

				for (int i = 0; i < userGroups.size(); i++) {
					UserGroup userGroup = userGroups.get(i);

					Group group = userGroup.getGroup();

					entityMap.put(userGroup.getName(), group.getGroupId());
				}
			}
			else if (entityName.equals("organization")) {
				List<Organization> organizations =
					OrganizationLocalServiceUtil.search(
						companyId,
						OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, null,
						OrganizationConstants.TYPE_REGULAR_ORGANIZATION, null,
						null, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (int i = 0; i < organizations.size(); i++) {
					Organization organization = organizations.get(i);

					Group group = organization.getGroup();

					entityMap.put(organization.getName(), group.getGroupId());
				}
			}

			entityMapMap.put(entityName, entityMap);
		}

		return entityMap;
	}

	protected List<Role> getGroupRoles_4(long groupId) throws SystemException {
		List<Role> roles = groupRolesMap.get(groupId);

		if (roles == null) {
			roles = RoleLocalServiceUtil.getGroupRoles(groupId);

			groupRolesMap.put(groupId, roles);
		}

		return roles;
	}

	protected List<Role> getGroupRoles_5(long groupId, String resourceName)
		throws PortalException, SystemException {

		List<Role> roles = groupRolesMap.get(groupId);

		if (roles == null) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			roles = ResourceActionsUtil.getRoles(group, resourceName);

			groupRolesMap.put(groupId, roles);
		}

		return roles;
	}

	protected List<User> getGroupUsers(long groupId) throws SystemException {
		List<User> users = groupUsersMap.get(groupId);

		if (users == null) {
			users = UserLocalServiceUtil.getGroupUsers(groupId);

			groupUsersMap.put(groupId, users);
		}

		return users;
	}

	protected Resource getResource(
			long companyId, long groupId, String resourceName, int scope,
			String resourcePrimKey, boolean portletActions)
		throws PortalException, SystemException {

		StringBundler sb = new StringBundler(5);

		sb.append(resourceName);
		sb.append(StringPool.PIPE);
		sb.append(scope);
		sb.append(StringPool.PIPE);
		sb.append(resourcePrimKey);

		String key = sb.toString();

		Resource resource = resourcesMap.get(key);

		if (resource == null) {
			try {
				resource = ResourceLocalServiceUtil.getResource(
					companyId, resourceName, scope, resourcePrimKey);
			}
			catch (NoSuchResourceException nsre) {
				ResourceLocalServiceUtil.addResources(
					companyId, groupId, 0, resourceName, resourcePrimKey,
					portletActions, true, true);

				resource = ResourceLocalServiceUtil.getResource(
					companyId, resourceName, scope, resourcePrimKey);
			}

			resourcesMap.put(key, resource);
		}

		return resource;
	}

	protected Role getRole(long companyId, String roleName)
		throws PortalException, SystemException {

		Role role = rolesMap.get(roleName);

		if (role == null) {
			try {
				role = RoleLocalServiceUtil.getRole(companyId, roleName);

				rolesMap.put(roleName, role);
			}
			catch (NoSuchRoleException nsre) {
			}
		}

		return role;
	}

	protected User getUser(long companyId, long groupId, String uuid)
		throws SystemException {

		List<User> users = usersMap.get(uuid);

		if (users == null) {
			LinkedHashMap<String, Object> params =
				new LinkedHashMap<String, Object>();

			params.put("usersGroups", new Long(groupId));

			try {
				User user = UserLocalServiceUtil.getUserByUuid(uuid);

				users = UserLocalServiceUtil.search(
					companyId, null, null, null, user.getScreenName(), null,
					Boolean.TRUE, params, true, 0, 1, (OrderByComparator)null);

			}
			catch (PortalException pe) {
			}

			usersMap.put(uuid, users);
		}

		if (users.size() == 0) {
			return null;
		}
		else {
			return users.get(0);
		}
	}

	protected List<Role> getUserRoles(long userId) throws SystemException {
		List<Role> userRoles = userRolesMap.get(userId);

		if (userRoles == null) {
			userRoles = RoleLocalServiceUtil.getUserRoles(userId);

			userRolesMap.put(userId, userRoles);
		}

		return userRoles;
	}

	protected Map<String, Long> entityGroupIdMap = new HashMap<String, Long>();
	protected Map<String, Map<String, Long>> entityMapMap =
		new HashMap<String, Map<String, Long>>();
	protected Map<Long, List<Role>> groupRolesMap =
		new HashMap<Long, List<Role>>();
	protected Map<Long, List<User>> groupUsersMap =
		new HashMap<Long, List<User>>();
	protected Map<String, Resource> resourcesMap =
		new HashMap<String, Resource>();
	protected Map<String, Role> rolesMap = new HashMap<String, Role>();
	protected Map<Long, List<Role>> userRolesMap =
		new HashMap<Long, List<Role>>();
	protected Map<String, List<User>> usersMap =
		new HashMap<String, List<User>>();

}