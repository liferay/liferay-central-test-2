/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

/**
 * <a href="ResourcePermissionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface ResourcePermissionPersistence extends BasePersistence {
	public void cacheResult(
		com.liferay.portal.model.ResourcePermission resourcePermission);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.ResourcePermission> resourcePermissions);

	public com.liferay.portal.model.ResourcePermission create(
		long resourcePermissionId);

	public com.liferay.portal.model.ResourcePermission remove(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission remove(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(ResourcePermission resourcePermission, boolean merge)</code>.
	 */
	public com.liferay.portal.model.ResourcePermission update(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        resourcePermission the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when resourcePermission is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portal.model.ResourcePermission update(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission updateImpl(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission findByPrimaryKey(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission fetchByPrimaryKey(
		long resourcePermissionId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ResourcePermission> findByResourceId(
		long resourceId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ResourcePermission> findByResourceId(
		long resourceId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ResourcePermission> findByResourceId(
		long resourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission findByResourceId_First(
		long resourceId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission findByResourceId_Last(
		long resourceId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission[] findByResourceId_PrevAndNext(
		long resourcePermissionId, long resourceId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission findByRoleId_First(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission findByRoleId_Last(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission[] findByRoleId_PrevAndNext(
		long resourcePermissionId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission findByR_R(
		long resourceId, long roleId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission fetchByR_R(
		long resourceId, long roleId) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ResourcePermission fetchByR_R(
		long resourceId, long roleId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ResourcePermission> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByResourceId(long resourceId)
		throws com.liferay.portal.SystemException;

	public void removeByRoleId(long roleId)
		throws com.liferay.portal.SystemException;

	public void removeByR_R(long resourceId, long roleId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByResourceId(long resourceId)
		throws com.liferay.portal.SystemException;

	public int countByRoleId(long roleId)
		throws com.liferay.portal.SystemException;

	public int countByR_R(long resourceId, long roleId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}