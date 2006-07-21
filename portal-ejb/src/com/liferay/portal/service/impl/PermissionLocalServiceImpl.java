/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.counter.service.spring.CounterServiceUtil;
import com.liferay.portal.NoSuchPermissionException;
import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.OrgGroupPermission;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.permission.PermissionCheckerBag;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.service.persistence.OrgGroupPermissionFinder;
import com.liferay.portal.service.persistence.OrgGroupPermissionPK;
import com.liferay.portal.service.persistence.OrgGroupPermissionUtil;
import com.liferay.portal.service.persistence.OrganizationUtil;
import com.liferay.portal.service.persistence.PermissionFinder;
import com.liferay.portal.service.persistence.PermissionUtil;
import com.liferay.portal.service.persistence.ResourceUtil;
import com.liferay.portal.service.persistence.RoleUtil;
import com.liferay.portal.service.persistence.UserGroupUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.GroupLocalServiceUtil;
import com.liferay.portal.service.spring.PermissionLocalService;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.util.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PermissionLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Charles May
 * @author  Brian Wing Shun Chan
 *
 */
public class PermissionLocalServiceImpl implements PermissionLocalService {

	public Permission addPermission(
			String companyId, String actionId, String resourceId)
		throws PortalException, SystemException {

		String permissionId = Long.toString(CounterServiceUtil.increment(
			Permission.class.getName()));

		Permission permission = PermissionUtil.create(permissionId);

		permission.setCompanyId(companyId);
		permission.setActionId(actionId);
		permission.setResourceId(resourceId);

		PermissionUtil.update(permission);

		return permission;
	}

	public List addPermissions(
			String companyId, String name, String resourceId,
			boolean portletActions)
		throws PortalException, SystemException {

		List permissions = new ArrayList();

		List actions = null;

		if (portletActions) {
			actions =
				ResourceActionsUtil.getPortletResourceActions(companyId, name);
		}
		else {
			actions = ResourceActionsUtil.getModelResourceActions(name);
		}

		for (int i = 0; i < actions.size(); i++) {
			String actionId = (String)actions.get(i);

			Permission permission =
				addPermission(companyId, actionId, resourceId);

			permissions.add(permission);
		}

		return permissions;
	}

	public void addUserPermissions(
			String userId, String[] actionIds, String resourceId)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		List permissions = PermissionFinder.findByU_R(userId, resourceId);

		permissions = getPermissions(
			user.getCompanyId(), actionIds, resourceId);

		UserUtil.addPermissions(userId, permissions);
	}

	public List getActions(List permissions) throws SystemException {
		List actions = new ArrayList();

		Iterator itr = permissions.iterator();

		while (itr.hasNext()) {
			Permission permission = (Permission)itr.next();

			actions.add(permission.getActionId());
		}

		return actions;
	}

	public List getGroupPermissions(String groupId, String resourceId)
		throws SystemException {

		return PermissionFinder.findByG_R(groupId, resourceId);
	}

	public List getOrgGroupPermissions(
			String organizationId, String groupId, String resourceId)
		throws SystemException {

		return PermissionFinder.findByO_G_R(
			organizationId, groupId, resourceId);
	}

	public List getPermissions(
			String companyId, String[] actionIds, String resourceId)
		throws PortalException, SystemException {

		List permissions = new ArrayList();

		for (int i = 0; i < actionIds.length; i++) {
			Permission permission = PermissionUtil.fetchByA_R(
				actionIds[i], resourceId);
			
			if (permission == null) {
				permission = addPermission(companyId, actionIds[i], resourceId);
			}

			permissions.add(permission);
		}

		return permissions;
	}

	public List getUserPermissions(String userId, String resourceId)
		throws SystemException {

		return PermissionFinder.findByU_R(userId, resourceId);
	}

	public boolean hasGroupPermission(
			String groupId, String actionId, String resourceId)
		throws PortalException, SystemException {

		Permission permission = null;

		try {
			permission = PermissionUtil.findByA_R(actionId, resourceId);
		}
		catch (NoSuchPermissionException nspe) {

			// Return false if there is no permission based on the given action
			// id and resource id

			return false;
		}

		return GroupUtil.containsPermission(
			groupId, permission.getPermissionId());
	}

	public boolean hasRolePermission(
			String roleId, String companyId, String name, String typeId,
			String scope, String actionId)
		throws PortalException, SystemException {

		Iterator itr = ResourceUtil.findByC_N_T_S(
			companyId, name, typeId, scope).iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();

			try {
				Permission permission = PermissionUtil.findByA_R(
					actionId, resource.getResourceId());

				if (RoleUtil.containsPermission(
						roleId, permission.getPermissionId())) {

					return true;
				}
			}
			catch (NoSuchPermissionException nspe) {
			}
		}

		return false;
	}

	public boolean hasRolePermission(
			String roleId, String companyId, String name, String typeId,
			String scope, String primKey, String actionId)
		throws PortalException, SystemException {

		try {
			Resource resource = ResourceUtil.findByC_N_T_S_P(
				companyId, name, typeId, scope, primKey);

			Permission permission = PermissionUtil.findByA_R(
				actionId, resource.getResourceId());

			return RoleUtil.containsPermission(
				roleId, permission.getPermissionId());
		}
		catch (NoSuchPermissionException nspe) {
		}
		catch (NoSuchResourceException nsre) {
		}

		return false;
	}

	public boolean hasUserPermission(
			String userId, String actionId, String resourceId)
		throws PortalException, SystemException {

		Permission permission = null;

		try {
			permission = PermissionUtil.findByA_R(actionId, resourceId);
		}
		catch (NoSuchPermissionException nspe) {

			// Return false if there is no permission based on the given action
			// id and resource id

			return false;
		}

		return UserUtil.containsPermission(
			userId, permission.getPermissionId());
	}

	public boolean hasUserPermissions(
			String userId, String groupId, String actionId,
			String[] resourceIds, PermissionCheckerBag permissionCheckerBag)
		throws PortalException, SystemException {

		long start = 0;

		if (_log.isDebugEnabled()) {
			start = System.currentTimeMillis();
		}

		// Return false if there is no resources

		if ((Validator.isNull(actionId)) || (resourceIds == null) ||
			(resourceIds.length == 0)) {

			return false;
		}

		List permissions = PermissionFinder.findByA_R(actionId, resourceIds);

		// Return false if there are no permissions

		if (permissions.size() == 0) {
			return false;
		}

		// Record logs with the first resource id

		String resourceId = resourceIds[0];

		start = logHasUserPermissions(userId, actionId, resourceId, start, 1);

		// Is the user directly connected to one of the permissions?

		if (PermissionFinder.countByUsersPermissions(permissions, userId) > 0) {
			return true;
		}

		start = logHasUserPermissions(userId, actionId, resourceId, start, 2);

		// If we are checking permissions an object that belongs to a community,
		// then it's only necessary to check the group that represents the
		// community and not all the groups that the user belongs to. This is so
		// because an object cannot belong to more than one community.

		List userGroups = new ArrayList();
		//List userGroups = UserUtil.getGroups(userId);

		if (Validator.isNotNull(groupId)) {
			if (permissionCheckerBag.hasUserGroup(userId, groupId)) {
				userGroups.add(GroupUtil.findByPrimaryKey(groupId));
			}
		}

		List userOrgs = permissionCheckerBag.getUserOrgs();
		List userOrgGroups = permissionCheckerBag.getUserOrgGroups();
		List userUserGroupGroups =
			permissionCheckerBag.getUserUserGroupGroups();

		List groups = new ArrayList(
			userGroups.size() + userOrgGroups.size() +
				userUserGroupGroups.size());

		groups.addAll(userGroups);
		groups.addAll(userOrgGroups);
		groups.addAll(userUserGroupGroups);

		start = logHasUserPermissions(userId, actionId, resourceId, start, 3);

		// Check the organization and community intersection table. Break out of
		// this method if the user has one of the permissions set at the
		// intersection because that takes priority.

		if (checkOrgGroupPermission(userOrgs, userGroups, permissions)) {
			return true;
		}

		start = logHasUserPermissions(userId, actionId, resourceId, start, 4);

		// Is the user associated with groups or organizations that are directly
		// connected to one of the permissions?

		if (groups.size() > 0) {
			if (PermissionFinder.countByGroupsPermissions(
					permissions, groups) > 0) {

				return true;
			}
		}

		start = logHasUserPermissions(userId, actionId, resourceId, start, 5);

		// Is the user connected to one of the permissions via user, group, or
		// organization roles?

		if (PermissionFinder.countByUsersRoles(permissions, userId) > 0) {
			return true;
		}

		if (groups.size() > 0) {
			if (PermissionFinder.countByGroupsRoles(permissions, groups) > 0) {
				return true;
			}
		}

		start = logHasUserPermissions(userId, actionId, resourceId, start, 6);

		return false;
	}

	public void setGroupPermissions(
			String groupId, String[] actionIds, String resourceId)
		throws PortalException, SystemException {

		Group group = GroupUtil.findByPrimaryKey(groupId);

		Iterator itr = PermissionFinder.findByG_R(
			groupId, resourceId).iterator();

		while (itr.hasNext()) {
			Permission permission = (Permission)itr.next();

			GroupUtil.removePermission(groupId, permission);
		}

		List permissions = getPermissions(
			group.getCompanyId(), actionIds, resourceId);

		GroupUtil.addPermissions(groupId, permissions);
	}

	public void setGroupPermissions(
			String className, String classPK, String groupId,
			String[] actionIds, String resourceId)
		throws PortalException, SystemException {

		String associatedGroupId = null;

		if (className.equals(Organization.class.getName())) {
			Organization organization =
				OrganizationUtil.findByPrimaryKey(classPK);

			OrgGroupPermissionFinder.removeByO_G_R(
				classPK, groupId, resourceId);

			associatedGroupId = organization.getGroup().getGroupId();
		}
		else if (className.equals(UserGroup.class.getName())) {
			UserGroup userGroup = UserGroupUtil.findByPrimaryKey(classPK);

			associatedGroupId = userGroup.getGroup().getGroupId();
		}

		setGroupPermissions(associatedGroupId, actionIds, resourceId);
	}

	public void setOrgGroupPermissions(
			String organizationId, String groupId, String[] actionIds,
			String resourceId)
		throws PortalException, SystemException {

		Organization organization =
			OrganizationUtil.findByPrimaryKey(organizationId);

		String orgGroupId = organization.getGroup().getGroupId();

		Iterator itr = PermissionUtil.findByResourceId(resourceId).iterator();

		while (itr.hasNext()) {
			Permission permission = (Permission)itr.next();

			GroupUtil.removePermission(orgGroupId, permission);
		}

		itr = getPermissions(
			organization.getCompanyId(), actionIds, resourceId).iterator();

		OrgGroupPermissionFinder.removeByO_G_R(
			organizationId, groupId, resourceId);

		while (itr.hasNext()) {
			Permission permission = (Permission)itr.next();

			OrgGroupPermissionPK pk = new OrgGroupPermissionPK(
				organizationId, groupId, permission.getPermissionId());

			OrgGroupPermission orgGroupPermission =
				OrgGroupPermissionUtil.create(pk);

			OrgGroupPermissionUtil.update(orgGroupPermission);
		}
	}

	public void setRolePermission(
			String roleId, String companyId, String name, String typeId,
			String scope, String primKey, String actionId)
		throws PortalException, SystemException {

		if (scope.equals(Resource.SCOPE_COMPANY)) {

			// Remove group permission

			unsetRolePermissions(
				roleId, companyId, name, typeId, Resource.SCOPE_GROUP,
				actionId);
		}
		else if (scope.equals(Resource.SCOPE_GROUP)) {

			// Remove company permission

			unsetRolePermissions(
				roleId, companyId, name, typeId, Resource.SCOPE_COMPANY,
				actionId);
		}
		else if (scope.equals(Resource.SCOPE_INDIVIDUAL)) {
			throw new NoSuchPermissionException();
		}

		Resource resource = ResourceLocalServiceUtil.addResource(
			companyId, name, typeId, scope, primKey);

		Permission permission = null;

		try {
			permission = PermissionUtil.findByA_R(
				actionId, resource.getResourceId());
		}
		catch (NoSuchPermissionException nspe) {
			String permissionId = Long.toString(CounterServiceUtil.increment(
				Permission.class.getName()));

			permission = PermissionUtil.create(permissionId);

			permission.setCompanyId(companyId);
			permission.setActionId(actionId);
			permission.setResourceId(resource.getResourceId());

			PermissionUtil.update(permission);
		}

		RoleUtil.addPermission(roleId, permission);
	}

	public void setUserPermissions(
			String userId, String[] actionIds, String resourceId)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		List permissions = PermissionFinder.findByU_R(userId, resourceId);

		UserUtil.removePermissions(userId, permissions);

		permissions = getPermissions(
			user.getCompanyId(), actionIds, resourceId);

		UserUtil.addPermissions(userId, permissions);
	}

	public void unsetRolePermission(
			String roleId, String companyId, String name, String typeId,
			String scope, String primKey, String actionId)
		throws PortalException, SystemException {

		try {
			Resource resource = ResourceUtil.findByC_N_T_S_P(
				companyId, name, typeId, scope, primKey);

			Permission permission = PermissionUtil.findByA_R(
				actionId, resource.getResourceId());

			RoleUtil.removePermission(roleId, permission);
		}
		catch (NoSuchPermissionException nspe) {
		}
		catch (NoSuchResourceException nsre) {
		}
	}

	public void unsetRolePermissions(
			String roleId, String companyId, String name, String typeId,
			String scope, String actionId)
		throws PortalException, SystemException {

		Iterator itr = ResourceUtil.findByC_N_T_S(
			companyId, name, typeId, scope).iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();

			try {
				Permission permission = PermissionUtil.findByA_R(
					actionId, resource.getResourceId());

				RoleUtil.removePermission(roleId, permission);
			}
			catch (NoSuchPermissionException nspe) {
			}
		}
	}

	public void unsetUserPermissions(
			String userId, String[] actionIds, String resourceId)
		throws PortalException, SystemException {

		List permissions = PermissionFinder.findByU_A_R(
			userId, actionIds, resourceId);

		UserUtil.removePermissions(userId, permissions);
	}

	protected boolean checkOrgGroupPermission(
			List organizations, List groups, List permissions)
		throws PortalException, SystemException {

		for (int i = 0; i < permissions.size(); i++) {
			Permission permission = (Permission)permissions.get(i);

			if (checkOrgGroupPermission(organizations, groups, permission)) {
				return true;
			}
		}

		return false;
	}

	protected boolean checkOrgGroupPermission(
			List organizations, List groups, Permission permission)
		throws PortalException, SystemException {

		// Do not check for an OrgGroupPermission intersection unless there is
		// at least one organization and one group to check

		if ((organizations.size() == 0) || (groups.size() == 0)) {
			return false;
		}

		// Do not check unless the OrgGroupPermission intersection contains at
		// least one permission

		List orgGroupPermissions = OrgGroupPermissionUtil.findByPermissionId(
			permission.getPermissionId());

		if (orgGroupPermissions.size() == 0) {
			return false;
		}

		Iterator itr = orgGroupPermissions.iterator();

		while (itr.hasNext()) {
			OrgGroupPermission orgGroupPermission =
				(OrgGroupPermission)itr.next();

			if (orgGroupPermission.containsOrganization(organizations) &&
				orgGroupPermission.containsGroup(groups)) {

				return true;
			}
		}

		// Throw an exception so that we do not continue checking permissions.
		// The user has a specific permission given in the OrgGroupPermission
		// intersection that prohibits him from going further.

		throw new NoSuchPermissionException(
			"User has a permission in OrgGroupPermission that does not match");
	}

	protected long logHasUserPermissions(
		String userId, String actionId, String resourceId, long start,
		int block) {

		if (!_log.isDebugEnabled()) {
			return 0;
		}

		long end = System.currentTimeMillis();

		_log.debug(
			"Checking user permission block " + block + " for " + userId + " " +
				actionId + " " + resourceId + " takes " + (end - start) +
					" ms");

		return end;
	}

	private static Log _log =
		LogFactory.getLog(PermissionLocalServiceImpl.class);

}