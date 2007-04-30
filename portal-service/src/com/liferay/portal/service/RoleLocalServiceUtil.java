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

package com.liferay.portal.service;

/**
 * <a href="RoleLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.RoleLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.RoleLocalServiceFactory</code> is responsible
 * for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.RoleLocalService
 * @see com.liferay.portal.service.RoleLocalServiceFactory
 *
 */
public class RoleLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portal.model.Role addRole(long userId,
		long companyId, java.lang.String name, int type)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.addRole(userId, companyId, name, type);
	}

	public static com.liferay.portal.model.Role addRole(long userId,
		long companyId, java.lang.String name, int type,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.addRole(userId, companyId, name, type,
			className, classPK);
	}

	public static void checkSystemRoles(long companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();
		roleLocalService.checkSystemRoles(companyId);
	}

	public static void deleteRole(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();
		roleLocalService.deleteRole(roleId);
	}

	public static com.liferay.portal.model.Role getGroupRole(long companyId,
		long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.getGroupRole(companyId, groupId);
	}

	public static java.util.List getGroupRoles(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.getGroupRoles(groupId);
	}

	public static java.util.Map getResourceRoles(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.getResourceRoles(companyId, name, scope, primKey);
	}

	public static com.liferay.portal.model.Role getRole(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.getRole(roleId);
	}

	public static com.liferay.portal.model.Role getRole(long companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.getRole(companyId, name);
	}

	public static java.util.List getUserGroupRoles(long userId, long groupId)
		throws com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.getUserGroupRoles(userId, groupId);
	}

	public static java.util.List getUserRelatedRoles(long userId, long groupId)
		throws com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.getUserRelatedRoles(userId, groupId);
	}

	public static java.util.List getUserRelatedRoles(long userId,
		long[] groupIds) throws com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.getUserRelatedRoles(userId, groupIds);
	}

	public static java.util.List getUserRelatedRoles(long userId,
		java.util.List groups) throws com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.getUserRelatedRoles(userId, groups);
	}

	public static java.util.List getUserRoles(long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.getUserRoles(userId);
	}

	public static boolean hasUserRole(long userId, long companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.hasUserRole(userId, companyId, name);
	}

	public static boolean hasUserRoles(long userId, long companyId,
		java.lang.String[] names)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.hasUserRoles(userId, companyId, names);
	}

	public static java.util.List search(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer type, int begin, int end)
		throws com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.search(companyId, name, description, type,
			begin, end);
	}

	public static int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer type)
		throws com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.searchCount(companyId, name, description, type);
	}

	public static void setUserRoles(long userId, java.lang.String[] roleIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();
		roleLocalService.setUserRoles(userId, roleIds);
	}

	public static com.liferay.portal.model.Role updateRole(
		java.lang.String roleId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		RoleLocalService roleLocalService = RoleLocalServiceFactory.getService();

		return roleLocalService.updateRole(roleId, name);
	}
}