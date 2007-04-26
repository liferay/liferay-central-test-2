/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.DuplicateRoleException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredRoleException;
import com.liferay.portal.RoleNameException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.base.RoleLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.service.persistence.RoleFinder;
import com.liferay.portal.service.persistence.RoleUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.Validator;

import java.util.List;
import java.util.Map;

/**
 * <a href="RoleLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RoleLocalServiceImpl extends RoleLocalServiceBaseImpl {

	public Role addRole(long userId, String companyId, String name, int type)
		throws PortalException, SystemException {

		return addRole(userId, companyId, name, type, null, null);
	}

	public Role addRole(
			long userId, String companyId, String name, int type,
			String className, String classPK)
		throws PortalException, SystemException {

		// Role

		validate(null, companyId, name);

		String roleId = String.valueOf(CounterLocalServiceUtil.increment(
			Role.class.getName()));

		Role role = RoleUtil.create(roleId);

		role.setCompanyId(companyId);
		role.setClassName(className);
		role.setClassPK(classPK);
		role.setName(name);
		role.setType(type);

		RoleUtil.update(role);

		// Resources

		if (userId > 0) {
			ResourceLocalServiceUtil.addResources(
				companyId, 0, userId, Role.class.getName(),
				role.getPrimaryKey().toString(), false, false, false);
		}

		return role;
	}

	public void checkSystemRoles(String companyId)
		throws PortalException, SystemException {

		// Regular roles

		String[] systemRoles = PortalUtil.getSystemRoles();

		for (int i = 0; i < systemRoles.length; i++) {
			try {
				RoleFinder.findByC_N(companyId, systemRoles[i]);
			}
			catch (NoSuchRoleException nsre) {
				addRole(0, companyId, systemRoles[i], RoleImpl.TYPE_REGULAR);
			}
		}

		// Community roles

		String[] systemCommunityRoles = PortalUtil.getSystemCommunityRoles();

		for (int i = 0; i < systemCommunityRoles.length; i++) {
			try {
				RoleFinder.findByC_N(companyId, systemCommunityRoles[i]);
			}
			catch (NoSuchRoleException nsre) {
				Role role = addRole(
					0, companyId, systemCommunityRoles[i],
					RoleImpl.TYPE_COMMUNITY);

				if (systemCommunityRoles[i].equals(RoleImpl.COMMUNITY_OWNER)) {
					List actions = ResourceActionsUtil.getModelResourceActions(
						Group.class.getName());

					PermissionLocalServiceUtil.setRolePermissions(
						role.getRoleId(), role.getCompanyId(),
						Group.class.getName(),
						ResourceImpl.SCOPE_GROUP_TEMPLATE,
						String.valueOf(GroupImpl.DEFAULT_PARENT_GROUP_ID),
						(String[])actions.toArray(new String[0]));
				}
				else if (systemCommunityRoles[i].equals(
							RoleImpl.COMMUNITY_ADMINISTRATOR)) {

					String[] actionIds = new String[] {
						ActionKeys.ASSIGN_USERS, ActionKeys.MANAGE_LAYOUTS,
						ActionKeys.UPDATE
					};

					PermissionLocalServiceUtil.setRolePermissions(
						role.getRoleId(), role.getCompanyId(),
						Group.class.getName(),
						ResourceImpl.SCOPE_GROUP_TEMPLATE,
						String.valueOf(GroupImpl.DEFAULT_PARENT_GROUP_ID),
						actionIds);
				}
			}
		}
	}

	public void deleteRole(String roleId)
		throws PortalException, SystemException {

		Role role = RoleUtil.findByPrimaryKey(roleId);

		if (PortalUtil.isSystemRole(role.getName())) {
			throw new RequiredRoleException();
		}

		// Resources

		if (Validator.isNull(role.getClassName()) &&
			Validator.isNull(role.getClassPK())) {

			ResourceLocalServiceUtil.deleteResource(
				role.getCompanyId(), Role.class.getName(),
				ResourceImpl.SCOPE_INDIVIDUAL, role.getPrimaryKey().toString());
		}

		if (role.getType() == RoleImpl.TYPE_COMMUNITY) {
			UserGroupRoleLocalServiceUtil.deleteUserGroupRolesByRoleId(
				role.getRoleId());
		}

		// Role

		RoleUtil.remove(roleId);
	}

	public Role getGroupRole(String companyId, long groupId)
		throws PortalException, SystemException {

		return RoleUtil.findByC_C_C(
			companyId, Group.class.getName(), String.valueOf(groupId));
	}

	public List getGroupRoles(long groupId)
		throws PortalException, SystemException {

		return GroupUtil.getRoles(groupId);
	}

	public Map getResourceRoles(
			String companyId, String name, String scope, String primKey)
		throws SystemException {

		return RoleFinder.findByC_N_S_P(companyId, name, scope, primKey);
	}

	public Role getRole(String roleId) throws PortalException, SystemException {
		return RoleUtil.findByPrimaryKey(roleId);
	}

	public Role getRole(String companyId, String name)
		throws PortalException, SystemException {

		return RoleFinder.findByC_N(companyId, name);
	}

	public List getUserGroupRoles(long userId, long groupId)
		throws SystemException {

		return RoleFinder.findByUserGroupRole(userId, groupId);
	}

	public List getUserRelatedRoles(long userId, long groupId)
		throws SystemException {

		return RoleFinder.findByU_G(userId, groupId);
	}

	public List getUserRelatedRoles(long userId, long[] groupIds)
		throws SystemException {

		return RoleFinder.findByU_G(userId, groupIds);
	}

	public List getUserRelatedRoles(long userId, List groups)
		throws SystemException {

		return RoleFinder.findByU_G(userId, groups);
	}

	public List getUserRoles(long userId)
		throws PortalException, SystemException {

		return UserUtil.getRoles(userId);
	}

	public boolean hasUserRole(long userId, String companyId, String name)
		throws PortalException, SystemException {

		Role role = RoleFinder.findByC_N(companyId, name);

		return UserUtil.containsRole(userId, role.getRoleId());
	}

	public boolean hasUserRoles(long userId, String companyId, String[] names)
		throws PortalException, SystemException {

		for (int i = 0; i < names.length; i++) {
			if (hasUserRole(userId, companyId, names[i])) {
				return true;
			}
		}

		return false;
	}

	public List search(
			String companyId, String name, String description, Integer type,
			int begin, int end)
		throws SystemException {

		return RoleFinder.findByC_N_D_T(
			companyId, name, description, type, begin, end);
	}

	public int searchCount(
			String companyId, String name, String description, Integer type)
		throws SystemException {

		return RoleFinder.countByC_N_D_T(companyId, name, description, type);
	}

	public void setUserRoles(long userId, String[] roleIds)
		throws PortalException, SystemException {

		UserUtil.setRoles(userId, roleIds);
	}

	public Role updateRole(String roleId, String name)
		throws PortalException, SystemException {

		Role role = RoleUtil.findByPrimaryKey(roleId);

		validate(roleId, role.getCompanyId(), name);

		if (PortalUtil.isSystemRole(role.getName())) {
			throw new RequiredRoleException();
		}

		role.setName(name);

		RoleUtil.update(role);

		return role;
	}

	protected void validate(String roleId, String companyId, String name)
		throws PortalException, SystemException {

		if ((Validator.isNull(name)) || (Validator.isNumber(name)) ||
			(name.indexOf(StringPool.COMMA) != -1) ||
			(name.indexOf(StringPool.STAR) != -1)) {

			throw new RoleNameException();
		}

		try {
			Role role = RoleFinder.findByC_N(companyId, name);

			if ((roleId == null) || !role.getRoleId().equals(roleId)) {
				throw new DuplicateRoleException();
			}
		}
		catch (NoSuchRoleException nsge) {
		}
	}

}