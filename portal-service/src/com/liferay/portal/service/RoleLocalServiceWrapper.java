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
 * <a href="RoleLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link RoleLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RoleLocalService
 * @generated
 */
public class RoleLocalServiceWrapper implements RoleLocalService {
	public RoleLocalServiceWrapper(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	public com.liferay.portal.model.Role addRole(
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.SystemException {
		return _roleLocalService.addRole(role);
	}

	public com.liferay.portal.model.Role createRole(long roleId) {
		return _roleLocalService.createRole(roleId);
	}

	public void deleteRole(long roleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_roleLocalService.deleteRole(roleId);
	}

	public void deleteRole(com.liferay.portal.model.Role role)
		throws com.liferay.portal.SystemException {
		_roleLocalService.deleteRole(role);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _roleLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _roleLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Role getRole(long roleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleLocalService.getRole(roleId);
	}

	public java.util.List<com.liferay.portal.model.Role> getRoles(int start,
		int end) throws com.liferay.portal.SystemException {
		return _roleLocalService.getRoles(start, end);
	}

	public int getRolesCount() throws com.liferay.portal.SystemException {
		return _roleLocalService.getRolesCount();
	}

	public com.liferay.portal.model.Role updateRole(
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.SystemException {
		return _roleLocalService.updateRole(role);
	}

	public com.liferay.portal.model.Role updateRole(
		com.liferay.portal.model.Role role, boolean merge)
		throws com.liferay.portal.SystemException {
		return _roleLocalService.updateRole(role, merge);
	}

	public com.liferay.portal.model.Role addRole(long userId, long companyId,
		java.lang.String name,
		java.util.Map<java.util.Locale, String> titleMap,
		java.lang.String description, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleLocalService.addRole(userId, companyId, name, titleMap,
			description, type);
	}

	public com.liferay.portal.model.Role addRole(long userId, long companyId,
		java.lang.String name,
		java.util.Map<java.util.Locale, String> titleMap,
		java.lang.String description, int type, java.lang.String className,
		long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleLocalService.addRole(userId, companyId, name, titleMap,
			description, type, className, classPK);
	}

	public void addUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_roleLocalService.addUserRoles(userId, roleIds);
	}

	public void checkSystemRoles(long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_roleLocalService.checkSystemRoles(companyId);
	}

	public com.liferay.portal.model.Role getGroupRole(long companyId,
		long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleLocalService.getGroupRole(companyId, groupId);
	}

	public java.util.List<com.liferay.portal.model.Role> getGroupRoles(
		long groupId) throws com.liferay.portal.SystemException {
		return _roleLocalService.getGroupRoles(groupId);
	}

	public java.util.Map<String, java.util.List<String>> getResourceRoles(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey) throws com.liferay.portal.SystemException {
		return _roleLocalService.getResourceRoles(companyId, name, scope,
			primKey);
	}

	public com.liferay.portal.model.Role getRole(long companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleLocalService.getRole(companyId, name);
	}

	public java.util.List<com.liferay.portal.model.Role> getRoles(
		long companyId) throws com.liferay.portal.SystemException {
		return _roleLocalService.getRoles(companyId);
	}

	public java.util.List<com.liferay.portal.model.Role> getRoles(
		long[] roleIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleLocalService.getRoles(roleIds);
	}

	public java.util.List<com.liferay.portal.model.Role> getRoles(int type,
		java.lang.String subtype) throws com.liferay.portal.SystemException {
		return _roleLocalService.getRoles(type, subtype);
	}

	public java.util.List<com.liferay.portal.model.Role> getSubtypeRoles(
		java.lang.String subtype) throws com.liferay.portal.SystemException {
		return _roleLocalService.getSubtypeRoles(subtype);
	}

	public int getSubtypeRolesCount(java.lang.String subtype)
		throws com.liferay.portal.SystemException {
		return _roleLocalService.getSubtypeRolesCount(subtype);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserGroupGroupRoles(
		long userId, long groupId) throws com.liferay.portal.SystemException {
		return _roleLocalService.getUserGroupGroupRoles(userId, groupId);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserGroupRoles(
		long userId, long groupId) throws com.liferay.portal.SystemException {
		return _roleLocalService.getUserGroupRoles(userId, groupId);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, long groupId) throws com.liferay.portal.SystemException {
		return _roleLocalService.getUserRelatedRoles(userId, groupId);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, long[] groupIds) throws com.liferay.portal.SystemException {
		return _roleLocalService.getUserRelatedRoles(userId, groupIds);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException {
		return _roleLocalService.getUserRelatedRoles(userId, groups);
	}

	public java.util.List<com.liferay.portal.model.Role> getUserRoles(
		long userId) throws com.liferay.portal.SystemException {
		return _roleLocalService.getUserRoles(userId);
	}

	public boolean hasUserRole(long userId, long roleId)
		throws com.liferay.portal.SystemException {
		return _roleLocalService.hasUserRole(userId, roleId);
	}

	public boolean hasUserRole(long userId, long companyId,
		java.lang.String name, boolean inherited)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleLocalService.hasUserRole(userId, companyId, name, inherited);
	}

	public boolean hasUserRoles(long userId, long companyId,
		java.lang.String[] names, boolean inherited)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleLocalService.hasUserRoles(userId, companyId, names,
			inherited);
	}

	public java.util.List<com.liferay.portal.model.Role> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _roleLocalService.search(companyId, name, description, type,
			start, end, obc);
	}

	public java.util.List<com.liferay.portal.model.Role> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer type, java.util.LinkedHashMap<String, Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _roleLocalService.search(companyId, name, description, type,
			params, start, end, obc);
	}

	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer type)
		throws com.liferay.portal.SystemException {
		return _roleLocalService.searchCount(companyId, name, description, type);
	}

	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer type,
		java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.SystemException {
		return _roleLocalService.searchCount(companyId, name, description,
			type, params);
	}

	public void setUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_roleLocalService.setUserRoles(userId, roleIds);
	}

	public void unsetUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_roleLocalService.unsetUserRoles(userId, roleIds);
	}

	public com.liferay.portal.model.Role updateRole(long roleId,
		java.lang.String name,
		java.util.Map<java.util.Locale, String> titleMap,
		java.lang.String description, java.lang.String subtype)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _roleLocalService.updateRole(roleId, name, titleMap,
			description, subtype);
	}

	public RoleLocalService getWrappedRoleLocalService() {
		return _roleLocalService;
	}

	private RoleLocalService _roleLocalService;
}