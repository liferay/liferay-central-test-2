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
 * <a href="UserGroupLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserGroupLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();

		return userGroupLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();

		return userGroupLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static void addGroupUserGroups(long groupId,
		java.lang.String[] userGroupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();
		userGroupLocalService.addGroupUserGroups(groupId, userGroupIds);
	}

	public static com.liferay.portal.model.UserGroup addUserGroup(
		java.lang.String userId, java.lang.String companyId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();

		return userGroupLocalService.addUserGroup(userId, companyId, name,
			description);
	}

	public static void deleteUserGroup(java.lang.String userGroupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();
		userGroupLocalService.deleteUserGroup(userGroupId);
	}

	public static com.liferay.portal.model.UserGroup getUserGroup(
		java.lang.String userGroupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();

		return userGroupLocalService.getUserGroup(userGroupId);
	}

	public static com.liferay.portal.model.UserGroup getUserGroup(
		java.lang.String companyId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();

		return userGroupLocalService.getUserGroup(companyId, name);
	}

	public static java.util.List getUserUserGroups(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();

		return userGroupLocalService.getUserUserGroups(userId);
	}

	public static boolean hasGroupUserGroup(long groupId,
		java.lang.String userGroupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();

		return userGroupLocalService.hasGroupUserGroup(groupId, userGroupId);
	}

	public static java.util.List search(java.lang.String companyId,
		java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap params, int begin, int end)
		throws com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();

		return userGroupLocalService.search(companyId, name, description,
			params, begin, end);
	}

	public static int searchCount(java.lang.String companyId,
		java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap params)
		throws com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();

		return userGroupLocalService.searchCount(companyId, name, description,
			params);
	}

	public static void unsetGroupUserGroups(long groupId,
		java.lang.String[] userGroupIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();
		userGroupLocalService.unsetGroupUserGroups(groupId, userGroupIds);
	}

	public static com.liferay.portal.model.UserGroup updateUserGroup(
		java.lang.String companyId, java.lang.String userGroupId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		UserGroupLocalService userGroupLocalService = UserGroupLocalServiceFactory.getService();

		return userGroupLocalService.updateUserGroup(companyId, userGroupId,
			name, description);
	}
}