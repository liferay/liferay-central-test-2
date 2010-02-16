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

import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * <a href="ResourcePermissionLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * com.liferay.portal.service.impl.ResourcePermissionLocalServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePermissionLocalServiceUtil
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ResourcePermissionLocalService {
	public com.liferay.portal.model.ResourcePermission addResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourcePermission createResourcePermission(
		long resourcePermissionId);

	public void deleteResourcePermission(long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.ResourcePermission getResourcePermission(
		long resourcePermissionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.ResourcePermission> getResourcePermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getResourcePermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourcePermission updateResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ResourcePermission updateResourcePermission(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<String> getAvailableResourcePermissionActionIds(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey, long roleId, java.util.List<String> actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getResourcePermissionsCount(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.ResourcePermission> getRoleResourcePermissions(
		long roleId, int[] scopes, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasActionId(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		com.liferay.portal.model.ResourceAction resourceAction);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasScopeResourcePermission(long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void mergePermissions(long fromRoleId, long toRoleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void reassignPermissions(long resourcePermissionId, long toRoleId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeResourcePermission(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeResourcePermissions(long companyId,
		java.lang.String name, int scope, long roleId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void setResourcePermissions(long companyId, java.lang.String name,
		int scope, java.lang.String primKey, long roleId,
		java.lang.String[] actionIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}