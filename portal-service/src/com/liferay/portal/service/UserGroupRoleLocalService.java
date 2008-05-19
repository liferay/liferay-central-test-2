/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * <a href="UserGroupRoleLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portal.service.impl.UserGroupRoleLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.UserGroupRoleLocalServiceFactory
 * @see com.liferay.portal.service.UserGroupRoleLocalServiceUtil
 *
 */
public interface UserGroupRoleLocalService {
	public com.liferay.portal.model.UserGroupRole addUserGroupRole(
		com.liferay.portal.model.UserGroupRole userGroupRole)
		throws com.liferay.portal.SystemException;

	public void deleteUserGroupRole(
		com.liferay.portal.service.persistence.UserGroupRolePK userGroupRolePK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteUserGroupRole(
		com.liferay.portal.model.UserGroupRole userGroupRole)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portal.model.UserGroupRole> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupRole> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupRole updateUserGroupRole(
		com.liferay.portal.model.UserGroupRole userGroupRole)
		throws com.liferay.portal.SystemException;

	public void addUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void addUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteUserGroupRoles(long userId, long[] groupIds)
		throws com.liferay.portal.SystemException;

	public void deleteUserGroupRoles(long[] userIds, long groupId)
		throws com.liferay.portal.SystemException;

	public void deleteUserGroupRoles(long userId, long groupId, long[] roleIds)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteUserGroupRoles(long[] userIds, long groupId, long roleId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteUserGroupRolesByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void deleteUserGroupRolesByRoleId(long roleId)
		throws com.liferay.portal.SystemException;

	public void deleteUserGroupRolesByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupRole> getUserGroupRoles(
		long userId, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public java.util.List<com.liferay.portal.model.UserGroupRole> getUserGroupRolesByGroupAndRole(
		long groupId, long roleId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public boolean hasUserGroupRole(long userId, long groupId, long roleId)
		throws com.liferay.portal.SystemException;

	public boolean hasUserGroupRole(long userId, long groupId,
		java.lang.String roleName)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;
}