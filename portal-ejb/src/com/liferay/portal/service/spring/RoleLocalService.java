/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.service.spring;

/**
 * <a href="RoleLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public interface RoleLocalService {
	public com.liferay.portal.model.Role addRole(java.lang.String userId,
		java.lang.String companyId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role addRole(java.lang.String userId,
		java.lang.String companyId, java.lang.String name,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void checkSystemRoles(java.lang.String companyId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public void deleteRole(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role getGroupRole(
		java.lang.String companyId, java.lang.String groupId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role getRole(java.lang.String roleId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role getRole(java.lang.String companyId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public java.util.List getUserRoles(java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public boolean hasUserRole(java.lang.String userId,
		java.lang.String companyId, java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public boolean hasUserRoles(java.lang.String userId,
		java.lang.String companyId, java.lang.String[] names)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public java.util.List search(java.lang.String companyId,
		java.lang.String name, int begin, int end)
		throws com.liferay.portal.SystemException;

	public int searchCount(java.lang.String companyId, java.lang.String name)
		throws com.liferay.portal.SystemException;

	public void setUserRoles(java.lang.String userId, java.lang.String[] roleIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role updateRole(java.lang.String roleId,
		java.lang.String name)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException;
}