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

package com.liferay.portal.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.model.ResourceAction;

import java.util.List;

/**
 * <a href="ResourceActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceActionPersistence
 * @see       ResourceActionPersistenceImpl
 * @generated
 */
public class ResourceActionUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static ResourceAction remove(ResourceAction resourceAction)
		throws SystemException {
		return getPersistence().remove(resourceAction);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ResourceAction update(ResourceAction resourceAction,
		boolean merge) throws SystemException {
		return getPersistence().update(resourceAction, merge);
	}

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
		if (_persistence == null) {
			_persistence = (ResourceActionPersistence)PortalBeanLocatorUtil.locate(ResourceActionPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ResourceActionPersistence persistence) {
		_persistence = persistence;
	}

	private static ResourceActionPersistence _persistence;
}