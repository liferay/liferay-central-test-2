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

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.expando.model.ExpandoRow;

import java.util.List;

/**
 * <a href="ExpandoRowUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoRowPersistence
 * @see       ExpandoRowPersistenceImpl
 * @generated
 */
public class ExpandoRowUtil {
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
	public static ExpandoRow remove(ExpandoRow expandoRow)
		throws SystemException {
		return getPersistence().remove(expandoRow);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ExpandoRow update(ExpandoRow expandoRow, boolean merge)
		throws SystemException {
		return getPersistence().update(expandoRow, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow) {
		getPersistence().cacheResult(expandoRow);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoRow> expandoRows) {
		getPersistence().cacheResult(expandoRows);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow create(
		long rowId) {
		return getPersistence().create(rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow remove(
		long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().remove(rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow updateImpl(
		com.liferay.portlet.expando.model.ExpandoRow expandoRow, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(expandoRow, merge);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow findByPrimaryKey(
		long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().findByPrimaryKey(rowId);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow fetchByPrimaryKey(
		long rowId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(rowId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findByTableId(
		long tableId) throws com.liferay.portal.SystemException {
		return getPersistence().findByTableId(tableId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findByTableId(
		long tableId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTableId(tableId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTableId(tableId, start, end, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow findByTableId_First(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().findByTableId_First(tableId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow findByTableId_Last(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().findByTableId_Last(tableId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow[] findByTableId_PrevAndNext(
		long rowId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().findByTableId_PrevAndNext(rowId, tableId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow findByT_C(
		long tableId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchRowException {
		return getPersistence().findByT_C(tableId, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow fetchByT_C(
		long tableId, long classPK) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByT_C(tableId, classPK);
	}

	public static com.liferay.portlet.expando.model.ExpandoRow fetchByT_C(
		long tableId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByT_C(tableId, classPK, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoRow> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByTableId(long tableId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByTableId(tableId);
	}

	public static void removeByT_C(long tableId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchRowException {
		getPersistence().removeByT_C(tableId, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByTableId(long tableId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByTableId(tableId);
	}

	public static int countByT_C(long tableId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByT_C(tableId, classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ExpandoRowPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ExpandoRowPersistence)PortalBeanLocatorUtil.locate(ExpandoRowPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ExpandoRowPersistence persistence) {
		_persistence = persistence;
	}

	private static ExpandoRowPersistence _persistence;
}