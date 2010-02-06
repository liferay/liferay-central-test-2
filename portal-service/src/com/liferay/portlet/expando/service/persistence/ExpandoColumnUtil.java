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

import com.liferay.portlet.expando.model.ExpandoColumn;

import java.util.List;

/**
 * <a href="ExpandoColumnUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoColumnPersistence
 * @see       ExpandoColumnPersistenceImpl
 * @generated
 */
public class ExpandoColumnUtil {
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
	public static ExpandoColumn remove(ExpandoColumn expandoColumn)
		throws SystemException {
		return getPersistence().remove(expandoColumn);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ExpandoColumn update(ExpandoColumn expandoColumn,
		boolean merge) throws SystemException {
		return getPersistence().update(expandoColumn, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn) {
		getPersistence().cacheResult(expandoColumn);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> expandoColumns) {
		getPersistence().cacheResult(expandoColumns);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn create(
		long columnId) {
		return getPersistence().create(columnId);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn remove(
		long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().remove(columnId);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateImpl(
		com.liferay.portlet.expando.model.ExpandoColumn expandoColumn,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(expandoColumn, merge);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn findByPrimaryKey(
		long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByPrimaryKey(columnId);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn fetchByPrimaryKey(
		long columnId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(columnId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByTableId(
		long tableId) throws com.liferay.portal.SystemException {
		return getPersistence().findByTableId(tableId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByTableId(
		long tableId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTableId(tableId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findByTableId(
		long tableId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByTableId(tableId, start, end, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn findByTableId_First(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByTableId_First(tableId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn findByTableId_Last(
		long tableId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByTableId_Last(tableId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn[] findByTableId_PrevAndNext(
		long columnId, long tableId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByTableId_PrevAndNext(columnId, tableId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn findByT_N(
		long tableId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		return getPersistence().findByT_N(tableId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn fetchByT_N(
		long tableId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByT_N(tableId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn fetchByT_N(
		long tableId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByT_N(tableId, name, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByTableId(long tableId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByTableId(tableId);
	}

	public static void removeByT_N(long tableId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.expando.NoSuchColumnException {
		getPersistence().removeByT_N(tableId, name);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByTableId(long tableId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByTableId(tableId);
	}

	public static int countByT_N(long tableId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByT_N(tableId, name);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ExpandoColumnPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ExpandoColumnPersistence)PortalBeanLocatorUtil.locate(ExpandoColumnPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ExpandoColumnPersistence persistence) {
		_persistence = persistence;
	}

	private static ExpandoColumnPersistence _persistence;
}