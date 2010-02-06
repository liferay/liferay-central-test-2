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
 * <a href="UserGroupLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserGroupLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupLocalService
 * @generated
 */
public class UserGroupLocalServiceWrapper implements UserGroupLocalService {
	public UserGroupLocalServiceWrapper(
		UserGroupLocalService userGroupLocalService) {
		_userGroupLocalService = userGroupLocalService;
	}

	public com.liferay.portal.model.UserGroup addUserGroup(
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.SystemException {
		return _userGroupLocalService.addUserGroup(userGroup);
	}

	public com.liferay.portal.model.UserGroup createUserGroup(long userGroupId) {
		return _userGroupLocalService.createUserGroup(userGroupId);
	}

	public void deleteUserGroup(long userGroupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_userGroupLocalService.deleteUserGroup(userGroupId);
	}

	public void deleteUserGroup(com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.SystemException {
		_userGroupLocalService.deleteUserGroup(userGroup);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _userGroupLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _userGroupLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.UserGroup getUserGroup(long userGroupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _userGroupLocalService.getUserGroup(userGroupId);
	}

	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		int start, int end) throws com.liferay.portal.SystemException {
		return _userGroupLocalService.getUserGroups(start, end);
	}

	public int getUserGroupsCount() throws com.liferay.portal.SystemException {
		return _userGroupLocalService.getUserGroupsCount();
	}

	public com.liferay.portal.model.UserGroup updateUserGroup(
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.SystemException {
		return _userGroupLocalService.updateUserGroup(userGroup);
	}

	public com.liferay.portal.model.UserGroup updateUserGroup(
		com.liferay.portal.model.UserGroup userGroup, boolean merge)
		throws com.liferay.portal.SystemException {
		return _userGroupLocalService.updateUserGroup(userGroup, merge);
	}

	public void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws com.liferay.portal.SystemException {
		_userGroupLocalService.addGroupUserGroups(groupId, userGroupIds);
	}

	public com.liferay.portal.model.UserGroup addUserGroup(long userId,
		long companyId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _userGroupLocalService.addUserGroup(userId, companyId, name,
			description);
	}

	public void clearUserUserGroups(long userId)
		throws com.liferay.portal.SystemException {
		_userGroupLocalService.clearUserUserGroups(userId);
	}

	public void copyUserGroupLayouts(long userGroupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_userGroupLocalService.copyUserGroupLayouts(userGroupId, userIds);
	}

	public void copyUserGroupLayouts(long[] userGroupIds, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_userGroupLocalService.copyUserGroupLayouts(userGroupIds, userId);
	}

	public void copyUserGroupLayouts(long userGroupId, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_userGroupLocalService.copyUserGroupLayouts(userGroupId, userId);
	}

	public com.liferay.portal.model.UserGroup getUserGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _userGroupLocalService.getUserGroup(companyId, name);
	}

	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long companyId) throws com.liferay.portal.SystemException {
		return _userGroupLocalService.getUserGroups(companyId);
	}

	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long[] userGroupIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _userGroupLocalService.getUserGroups(userGroupIds);
	}

	public java.util.List<com.liferay.portal.model.UserGroup> getUserUserGroups(
		long userId) throws com.liferay.portal.SystemException {
		return _userGroupLocalService.getUserUserGroups(userId);
	}

	public boolean hasGroupUserGroup(long groupId, long userGroupId)
		throws com.liferay.portal.SystemException {
		return _userGroupLocalService.hasGroupUserGroup(groupId, userGroupId);
	}

	public java.util.List<com.liferay.portal.model.UserGroup> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return _userGroupLocalService.search(companyId, name, description,
			params, start, end, obc);
	}

	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.SystemException {
		return _userGroupLocalService.searchCount(companyId, name, description,
			params);
	}

	public void setUserUserGroups(long userId, long[] userGroupIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_userGroupLocalService.setUserUserGroups(userId, userGroupIds);
	}

	public void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws com.liferay.portal.SystemException {
		_userGroupLocalService.unsetGroupUserGroups(groupId, userGroupIds);
	}

	public com.liferay.portal.model.UserGroup updateUserGroup(long companyId,
		long userGroupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _userGroupLocalService.updateUserGroup(companyId, userGroupId,
			name, description);
	}

	public UserGroupLocalService getWrappedUserGroupLocalService() {
		return _userGroupLocalService;
	}

	private UserGroupLocalService _userGroupLocalService;
}