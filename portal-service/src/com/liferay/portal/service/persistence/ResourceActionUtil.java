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
 * <a href="ResourceActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ResourceActionUtil {
	public static void cacheResult(
		com.liferay.portal.model.ResourceAction resourceAction) {
		getPersistence().cacheResult(resourceAction);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ResourceAction> resourceActions) {
		getPersistence().cacheResult(resourceActions);
	}

	public static com.liferay.portal.model.ResourceAction create(
		long resourceActionId) {
		return getPersistence().create(resourceActionId);
	}

	public static com.liferay.portal.model.ResourceAction remove(
		long resourceActionId)
		throws com.liferay.portal.NoSuchResourceActionException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(resourceActionId);
	}

	public static com.liferay.portal.model.ResourceAction remove(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(resourceAction);
	}

	/**
	 * @deprecated Use <code>update(ResourceAction resourceAction, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.ResourceAction update(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(resourceAction);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        resourceAction the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when resourceAction is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portal.model.ResourceAction update(
		com.liferay.portal.model.ResourceAction resourceAction, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(resourceAction, merge);
	}

	public static com.liferay.portal.model.ResourceAction updateImpl(
		com.liferay.portal.model.ResourceAction resourceAction, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(resourceAction, merge);
	}

	public static com.liferay.portal.model.ResourceAction findByPrimaryKey(
		long resourceActionId)
		throws com.liferay.portal.NoSuchResourceActionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(resourceActionId);
	}

	public static com.liferay.portal.model.ResourceAction fetchByPrimaryKey(
		long resourceActionId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(resourceActionId);
	}

	public static java.util.List<com.liferay.portal.model.ResourceAction> findByName(
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getPersistence().findByName(name);
	}

	public static java.util.List<com.liferay.portal.model.ResourceAction> findByName(
		java.lang.String name, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByName(name, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourceAction> findByName(
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByName(name, start, end, obc);
	}

	public static com.liferay.portal.model.ResourceAction findByName_First(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceActionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByName_First(name, obc);
	}

	public static com.liferay.portal.model.ResourceAction findByName_Last(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceActionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByName_Last(name, obc);
	}

	public static com.liferay.portal.model.ResourceAction[] findByName_PrevAndNext(
		long resourceActionId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceActionException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByName_PrevAndNext(resourceActionId, name, obc);
	}

	public static com.liferay.portal.model.ResourceAction findByN_A(
		java.lang.String name, java.lang.String actionId)
		throws com.liferay.portal.NoSuchResourceActionException,
			com.liferay.portal.SystemException {
		return getPersistence().findByN_A(name, actionId);
	}

	public static com.liferay.portal.model.ResourceAction fetchByN_A(
		java.lang.String name, java.lang.String actionId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByN_A(name, actionId);
	}

	public static com.liferay.portal.model.ResourceAction fetchByN_A(
		java.lang.String name, java.lang.String actionId,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByN_A(name, actionId, retrieveFromCache);
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

	public static java.util.List<com.liferay.portal.model.ResourceAction> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ResourceAction> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourceAction> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByName(java.lang.String name)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByName(name);
	}

	public static void removeByN_A(java.lang.String name,
		java.lang.String actionId)
		throws com.liferay.portal.NoSuchResourceActionException,
			com.liferay.portal.SystemException {
		getPersistence().removeByN_A(name, actionId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByName(java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByName(name);
	}

	public static int countByN_A(java.lang.String name,
		java.lang.String actionId) throws com.liferay.portal.SystemException {
		return getPersistence().countByN_A(name, actionId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ResourceActionPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(ResourceActionPersistence persistence) {
		_persistence = persistence;
	}

	private static ResourceActionPersistence _persistence;
}