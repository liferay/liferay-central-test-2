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
 * <a href="UserGroupServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link UserGroupService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupService
 * @generated
 */
public class UserGroupServiceWrapper implements UserGroupService {
	public UserGroupServiceWrapper(UserGroupService userGroupService) {
		_userGroupService = userGroupService;
	}

	public void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_userGroupService.addGroupUserGroups(groupId, userGroupIds);
	}

	public com.liferay.portal.model.UserGroup addUserGroup(
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _userGroupService.addUserGroup(name, description);
	}

	public void deleteUserGroup(long userGroupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_userGroupService.deleteUserGroup(userGroupId);
	}

	public com.liferay.portal.model.UserGroup getUserGroup(long userGroupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _userGroupService.getUserGroup(userGroupId);
	}

	public com.liferay.portal.model.UserGroup getUserGroup(
		java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _userGroupService.getUserGroup(name);
	}

	public java.util.List<com.liferay.portal.model.UserGroup> getUserUserGroups(
		long userId) throws com.liferay.portal.SystemException {
		return _userGroupService.getUserUserGroups(userId);
	}

	public void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_userGroupService.unsetGroupUserGroups(groupId, userGroupIds);
	}

	public com.liferay.portal.model.UserGroup updateUserGroup(
		long userGroupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _userGroupService.updateUserGroup(userGroupId, name, description);
	}

	public UserGroupService getWrappedUserGroupService() {
		return _userGroupService;
	}

	private UserGroupService _userGroupService;
}