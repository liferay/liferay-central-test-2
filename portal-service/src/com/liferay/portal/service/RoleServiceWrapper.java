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

package com.liferay.portal.service;


/**
 * <a href="RoleServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link RoleService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RoleService
 * @generated
 */
public class RoleServiceWrapper implements RoleService {
	public RoleServiceWrapper(RoleService roleService) {
		_roleService = roleService;
	}

	public com.liferay.portal.model.Role addRole(java.lang.String name,
		java.util.Map<java.util.Locale, String> titleMap,
		java.lang.String description, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleService.addRole(name, titleMap, description, type);
	}

	public void addUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_roleService.addUserRoles(userId, roleIds);
	}

	public void deleteRole(long roleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_roleService.deleteRole(roleId);
	}

	public com.liferay.portal.model.Role getGroupRole(long companyId,
		long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleService.getGroupRole(companyId, groupId);
	}

	public java.util.List<com.liferay.portal.model.Role> getGroupRoles(
		long groupId) throws com.liferay.portal.SystemException {
		return _roleService.getGroupRoles(groupId);
	}

	public com.liferay.portal.model.Role getRole(long roleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleService.getRole(roleId);
	}

	public com.liferay.portal.model.Role getRole(long companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleService.getRole(companyId, name);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserGroupGroupRoles(
		long userId, long groupId) throws com.liferay.portal.SystemException {
		return _roleService.getUserGroupGroupRoles(userId, groupId);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserGroupRoles(
		long userId, long groupId) throws com.liferay.portal.SystemException {
		return _roleService.getUserGroupRoles(userId, groupId);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException {
		return _roleService.getUserRelatedRoles(userId, groups);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserRoles(
		long userId) throws com.liferay.portal.SystemException {
		return _roleService.getUserRoles(userId);
	}

	public boolean hasUserRole(long userId, long companyId,
		java.lang.String name, boolean inherited)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleService.hasUserRole(userId, companyId, name, inherited);
	}

	public boolean hasUserRoles(long userId, long companyId,
		java.lang.String[] names, boolean inherited)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleService.hasUserRoles(userId, companyId, names, inherited);
	}

	public void unsetUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_roleService.unsetUserRoles(userId, roleIds);
	}

	public com.liferay.portal.model.Role updateRole(long roleId,
		java.lang.String name,
		java.util.Map<java.util.Locale, String> titleMap,
		java.lang.String description, java.lang.String subtype)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleService.updateRole(roleId, name, titleMap, description,
			subtype);
	}

	public RoleService getWrappedRoleService() {
		return _roleService;
	}

	private RoleService _roleService;
}