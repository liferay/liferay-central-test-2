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
 * <a href="ResourcePermissionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourcePermissionUtil {
	public static void cacheResult(
		com.liferay.portal.model.ResourcePermission resourcePermission) {
		getPersistence().cacheResult(resourcePermission);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ResourcePermission> resourcePermissions) {
		getPersistence().cacheResult(resourcePermissions);
	}

	public static com.liferay.portal.model.ResourcePermission create(
		long resourcePermissionId) {
		return getPersistence().create(resourcePermissionId);
	}

	public static com.liferay.portal.model.ResourcePermission remove(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(resourcePermissionId);
	}

	public static com.liferay.portal.model.ResourcePermission remove(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(resourcePermission);
	}

	/**
	 * @deprecated Use <code>update(ResourcePermission resourcePermission, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.ResourcePermission update(
		com.liferay.portal.model.ResourcePermission resourcePermission)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(resourcePermission);
	}

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
	public static com.liferay.portal.model.ResourcePermission update(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(resourcePermission, merge);
	}

	public static com.liferay.portal.model.ResourcePermission updateImpl(
		com.liferay.portal.model.ResourcePermission resourcePermission,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(resourcePermission, merge);
	}

	public static com.liferay.portal.model.ResourcePermission findByPrimaryKey(
		long resourcePermissionId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(resourcePermissionId);
	}

	public static com.liferay.portal.model.ResourcePermission fetchByPrimaryKey(
		long resourcePermissionId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(resourcePermissionId);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByResourceId(
		long resourceId) throws com.liferay.portal.SystemException {
		return getPersistence().findByResourceId(resourceId);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByResourceId(
		long resourceId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByResourceId(resourceId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByResourceId(
		long resourceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByResourceId(resourceId, start, end, obc);
	}

	public static com.liferay.portal.model.ResourcePermission findByResourceId_First(
		long resourceId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByResourceId_First(resourceId, obc);
	}

	public static com.liferay.portal.model.ResourcePermission findByResourceId_Last(
		long resourceId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByResourceId_Last(resourceId, obc);
	}

	public static com.liferay.portal.model.ResourcePermission[] findByResourceId_PrevAndNext(
		long resourcePermissionId, long resourceId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByResourceId_PrevAndNext(resourcePermissionId,
			resourceId, obc);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId) throws com.liferay.portal.SystemException {
		return getPersistence().findByRoleId(roleId);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByRoleId(roleId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByRoleId(roleId, start, end, obc);
	}

	public static com.liferay.portal.model.ResourcePermission findByRoleId_First(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByRoleId_First(roleId, obc);
	}

	public static com.liferay.portal.model.ResourcePermission findByRoleId_Last(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByRoleId_Last(roleId, obc);
	}

	public static com.liferay.portal.model.ResourcePermission[] findByRoleId_PrevAndNext(
		long resourcePermissionId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByRoleId_PrevAndNext(resourcePermissionId, roleId, obc);
	}

	public static com.liferay.portal.model.ResourcePermission findByR_R(
		long resourceId, long roleId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByR_R(resourceId, roleId);
	}

	public static com.liferay.portal.model.ResourcePermission fetchByR_R(
		long resourceId, long roleId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByR_R(resourceId, roleId);
	}

	public static com.liferay.portal.model.ResourcePermission fetchByR_R(
		long resourceId, long roleId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByR_R(resourceId, roleId, retrieveFromCache);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourcePermission> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByResourceId(long resourceId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByResourceId(resourceId);
	}

	public static void removeByRoleId(long roleId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByRoleId(roleId);
	}

	public static void removeByR_R(long resourceId, long roleId)
		throws com.liferay.portal.NoSuchResourcePermissionException,
			com.liferay.portal.SystemException {
		getPersistence().removeByR_R(resourceId, roleId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByResourceId(long resourceId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByResourceId(resourceId);
	}

	public static int countByRoleId(long roleId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByRoleId(roleId);
	}

	public static int countByR_R(long resourceId, long roleId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByR_R(resourceId, roleId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ResourcePermissionPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(ResourcePermissionPersistence persistence) {
		_persistence = persistence;
	}

	private static ResourcePermissionPersistence _persistence;
}