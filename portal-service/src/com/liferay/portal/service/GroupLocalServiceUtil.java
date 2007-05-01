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
 * <a href="GroupLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portal.service.GroupLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.GroupLocalServiceFactory</code> is responsible
 * for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.GroupLocalService
 * @see com.liferay.portal.service.GroupLocalServiceFactory
 *
 */
public class GroupLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portal.model.Group addGroup(long userId,
		java.lang.String className, java.lang.String classPK,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String friendlyURL, boolean active)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.addGroup(userId, className, classPK, name,
			description, type, friendlyURL, active);
	}

	public static com.liferay.portal.model.Group addGroup(long userId,
		java.lang.String className, java.lang.String classPK, long liveGroupId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String friendlyURL, boolean active)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.addGroup(userId, className, classPK,
			liveGroupId, name, description, type, friendlyURL, active);
	}

	public static void addRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();
		groupLocalService.addRoleGroups(roleId, groupIds);
	}

	public static void addUserGroups(long userId, long[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();
		groupLocalService.addUserGroups(userId, groupIds);
	}

	public static void checkSystemGroups(long companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();
		groupLocalService.checkSystemGroups(companyId);
	}

	public static void deleteGroup(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();
		groupLocalService.deleteGroup(groupId);
	}

	public static com.liferay.portal.model.Group getFriendlyURLGroup(
		long companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.getFriendlyURLGroup(companyId, friendlyURL);
	}

	public static com.liferay.portal.model.Group getGroup(long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.getGroup(groupId);
	}

	public static com.liferay.portal.model.Group getGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.getGroup(companyId, name);
	}

	public static com.liferay.portal.model.Group getOrganizationGroup(
		long companyId, java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.getOrganizationGroup(companyId, organizationId);
	}

	public static java.util.List getOrganizationsGroups(
		java.util.List organizations)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.getOrganizationsGroups(organizations);
	}

	public static java.util.List getRoleGroups(long roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.getRoleGroups(roleId);
	}

	public static com.liferay.portal.model.Group getStagingGroup(
		long liveGroupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.getStagingGroup(liveGroupId);
	}

	public static com.liferay.portal.model.Group getUserGroup(long companyId,
		long userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.getUserGroup(companyId, userId);
	}

	public static com.liferay.portal.model.Group getUserGroupGroup(
		long companyId, long userGroupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.getUserGroupGroup(companyId, userGroupId);
	}

	public static java.util.List getUserGroupsGroups(java.util.List userGroups)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.getUserGroupsGroups(userGroups);
	}

	public static boolean hasRoleGroup(long roleId, long groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.hasRoleGroup(roleId, groupId);
	}

	public static boolean hasUserGroup(long userId, long groupId)
		throws com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.hasUserGroup(userId, groupId);
	}

	public static java.util.List search(long companyId, java.lang.String name,
		java.lang.String description, java.util.LinkedHashMap params,
		int begin, int end) throws com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.search(companyId, name, description, params,
			begin, end);
	}

	public static int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.util.LinkedHashMap params)
		throws com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.searchCount(companyId, name, description,
			params);
	}

	public static void setRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();
		groupLocalService.setRoleGroups(roleId, groupIds);
	}

	public static void setUserGroups(long userId, long[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();
		groupLocalService.setUserGroups(userId, groupIds);
	}

	public static void unsetRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();
		groupLocalService.unsetRoleGroups(roleId, groupIds);
	}

	public static void unsetUserGroups(long userId, long[] groupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();
		groupLocalService.unsetUserGroups(userId, groupIds);
	}

	public static com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String friendlyURL, boolean active)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		GroupLocalService groupLocalService = GroupLocalServiceFactory.getService();

		return groupLocalService.updateGroup(groupId, name, description, type,
			friendlyURL, active);
	}
}