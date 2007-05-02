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
 * <a href="GroupLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is <code>com.liferay.portal.service.impl.GroupLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be accessed
 * from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.GroupServiceFactory
 * @see com.liferay.portal.service.GroupServiceUtil
 *
 */
public interface GroupLocalService {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Group addGroup(long userId,
		java.lang.String className, long classPK, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String friendlyURL, boolean active)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Group addGroup(long userId,
		java.lang.String className, long classPK, long liveGroupId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String friendlyURL, boolean active)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void addUserGroups(long userId, long[] groupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkSystemGroups(long companyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteGroup(long groupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Group getFriendlyURLGroup(long companyId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Group getGroup(long groupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Group getGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Group getOrganizationGroup(long companyId,
		long organizationId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getOrganizationsGroups(java.util.List organizations)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getRoleGroups(long roleId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Group getStagingGroup(long liveGroupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Group getUserGroup(long companyId,
		long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Group getUserGroupGroup(long companyId,
		long userGroupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getUserGroupsGroups(java.util.List userGroups)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasRoleGroup(long roleId, long groupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasUserGroup(long userId, long groupId)
		throws com.liferay.portal.SystemException;

	public java.util.List search(long companyId, java.lang.String name,
		java.lang.String description, java.util.LinkedHashMap params,
		int begin, int end) throws com.liferay.portal.SystemException;

	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.util.LinkedHashMap params)
		throws com.liferay.portal.SystemException;

	public void setRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void setUserGroups(long userId, long[] groupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void unsetUserGroups(long userId, long[] groupIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String friendlyURL, boolean active)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;
}