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
import com.liferay.portal.model.ListType;

import java.util.List;

/**
 * <a href="ListTypeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ListTypePersistence
 * @see       ListTypePersistenceImpl
 * @generated
 */
public class ListTypeUtil {
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
	public static ListType remove(ListType listType) throws SystemException {
		return getPersistence().remove(listType);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ListType update(ListType listType, boolean merge)
		throws SystemException {
		return getPersistence().update(listType, merge);
	}

	public static void cacheResult(com.liferay.portal.model.ListType listType) {
		getPersistence().cacheResult(listType);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ListType> listTypes) {
		getPersistence().cacheResult(listTypes);
	}

	public static com.liferay.portal.model.ListType create(int listTypeId) {
		return getPersistence().create(listTypeId);
	}

	public static com.liferay.portal.model.ListType remove(int listTypeId)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(listTypeId);
	}

	public static com.liferay.portal.model.ListType updateImpl(
		com.liferay.portal.model.ListType listType, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(listType, merge);
	}

	public static com.liferay.portal.model.ListType findByPrimaryKey(
		int listTypeId)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(listTypeId);
	}

	public static com.liferay.portal.model.ListType fetchByPrimaryKey(
		int listTypeId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(listTypeId);
	}

	public static java.util.List<com.liferay.portal.model.ListType> findByType(
		java.lang.String type) throws com.liferay.portal.SystemException {
		return getPersistence().findByType(type);
	}

	public static java.util.List<com.liferay.portal.model.ListType> findByType(
		java.lang.String type, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByType(type, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ListType> findByType(
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByType(type, start, end, obc);
	}

	public static com.liferay.portal.model.ListType findByType_First(
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByType_First(type, obc);
	}

	public static com.liferay.portal.model.ListType findByType_Last(
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByType_Last(type, obc);
	}

	public static com.liferay.portal.model.ListType[] findByType_PrevAndNext(
		int listTypeId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchListTypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByType_PrevAndNext(listTypeId, type, obc);
	}

	public static java.util.List<com.liferay.portal.model.ListType> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ListType> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.ListType> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByType(java.lang.String type)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByType(type);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByType(java.lang.String type)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByType(type);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ListTypePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ListTypePersistence)PortalBeanLocatorUtil.locate(ListTypePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ListTypePersistence persistence) {
		_persistence = persistence;
	}

	private static ListTypePersistence _persistence;
}