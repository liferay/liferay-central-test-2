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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="RoleLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link RoleLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RoleLocalService
 * @generated
 */
public class RoleLocalServiceUtil {
	public static com.liferay.portal.model.Role addRole(
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addRole(role);
	}

	public static com.liferay.portal.model.Role createRole(long roleId) {
		return getService().createRole(roleId);
	}

	public static void deleteRole(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRole(roleId);
	}

	public static void deleteRole(com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRole(role);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Role getRole(long roleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRole(roleId);
	}

	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoles(start, end);
	}

	public static int getRolesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRolesCount();
	}

	public static com.liferay.portal.model.Role updateRole(
		com.liferay.portal.model.Role role)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRole(role);
	}

	public static com.liferay.portal.model.Role updateRole(
		com.liferay.portal.model.Role role, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRole(role, merge);
	}

	public static com.liferay.portal.model.Role addRole(long userId,
		long companyId, java.lang.String name,
		java.util.Map<java.util.Locale, String> titleMap,
		java.lang.String description, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRole(userId, companyId, name, titleMap, description, type);
	}

	public static com.liferay.portal.model.Role addRole(long userId,
		long companyId, java.lang.String name,
		java.util.Map<java.util.Locale, String> titleMap,
		java.lang.String description, int type, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRole(userId, companyId, name, titleMap, description,
			type, className, classPK);
	}

	public static void addUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addUserRoles(userId, roleIds);
	}

	public static void checkSystemRoles(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkSystemRoles(companyId);
	}

	public static com.liferay.portal.model.Role getGroupRole(long companyId,
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupRole(companyId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.Role> getGroupRoles(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupRoles(groupId);
	}

	public static java.util.Map<String, java.util.List<String>> getResourceRoles(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getResourceRoles(companyId, name, scope, primKey);
	}

	public static com.liferay.portal.model.Role getRole(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRole(companyId, name);
	}

	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoles(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoles(roleIds);
	}

	public static java.util.List<com.liferay.portal.model.Role> getRoles(
		int type, java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoles(type, subtype);
	}

	public static java.util.List<com.liferay.portal.model.Role> getSubtypeRoles(
		java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubtypeRoles(subtype);
	}

	public static int getSubtypeRolesCount(java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSubtypeRolesCount(subtype);
	}

	public static java.util.List<com.liferay.portal.model.Role> getUserGroupGroupRoles(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupGroupRoles(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.Role> getUserGroupRoles(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupRoles(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserRelatedRoles(userId, groupId);
	}

	public static java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserRelatedRoles(userId, groupIds);
	}

	public static java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		long userId, java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserRelatedRoles(userId, groups);
	}

	public static java.util.List<com.liferay.portal.model.Role> getUserRoles(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserRoles(userId);
	}

	public static boolean hasUserRole(long userId, long roleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserRole(userId, roleId);
	}

	public static boolean hasUserRole(long userId, long companyId,
		java.lang.String name, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserRole(userId, companyId, name, inherited);
	}

	public static boolean hasUserRoles(long userId, long companyId,
		java.lang.String[] names, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserRoles(userId, companyId, names, inherited);
	}

	public static java.util.List<com.liferay.portal.model.Role> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, name, description, type, start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.Role> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer type, java.util.LinkedHashMap<String, Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, name, description, type, params, start,
			end, obc);
	}

	public static int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().searchCount(companyId, name, description, type);
	}

	public static int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer type,
		java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, name, description, type, params);
	}

	public static void setUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().setUserRoles(userId, roleIds);
	}

	public static void unsetUserRoles(long userId, long[] roleIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unsetUserRoles(userId, roleIds);
	}

	public static com.liferay.portal.model.Role updateRole(long roleId,
		java.lang.String name,
		java.util.Map<java.util.Locale, String> titleMap,
		java.lang.String description, java.lang.String subtype)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateRole(roleId, name, titleMap, description, subtype);
	}

	public static RoleLocalService getService() {
		if (_service == null) {
			_service = (RoleLocalService)PortalBeanLocatorUtil.locate(RoleLocalService.class.getName());
		}

		return _service;
	}

	public void setService(RoleLocalService service) {
		_service = service;
	}

	private static RoleLocalService _service;
}