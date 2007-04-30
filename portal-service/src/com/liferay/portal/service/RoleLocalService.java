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
 * <a href="RoleLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is <code>com.liferay.portal.service.impl.RoleLocalServiceImpl</code>.
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
 * @see com.liferay.portal.service.RoleServiceFactory
 * @see com.liferay.portal.service.RoleServiceUtil
 *
 */
public interface RoleLocalService {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role addRole(long userId, long companyId,
		java.lang.String name, int type)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Role addRole(long userId, long companyId,
		java.lang.String name, int type, java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void checkSystemRoles(long companyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteRole(java.lang.String roleId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Role getGroupRole(long companyId,
		long groupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getGroupRoles(long groupId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.Map getResourceRoles(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role getRole(java.lang.String roleId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Role getRole(long companyId,
		java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getUserGroupRoles(long userId, long groupId)
		throws com.liferay.portal.SystemException;

	public java.util.List getUserRelatedRoles(long userId, long groupId)
		throws com.liferay.portal.SystemException;

	public java.util.List getUserRelatedRoles(long userId, long[] groupIds)
		throws com.liferay.portal.SystemException;

	public java.util.List getUserRelatedRoles(long userId, java.util.List groups)
		throws com.liferay.portal.SystemException;

	public java.util.List getUserRoles(long userId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasUserRole(long userId, long companyId,
		java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean hasUserRoles(long userId, long companyId,
		java.lang.String[] names)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List search(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer type, int begin, int end)
		throws com.liferay.portal.SystemException;

	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer type)
		throws com.liferay.portal.SystemException;

	public void setUserRoles(long userId, java.lang.String[] roleIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.Role updateRole(java.lang.String roleId,
		java.lang.String name)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;
}