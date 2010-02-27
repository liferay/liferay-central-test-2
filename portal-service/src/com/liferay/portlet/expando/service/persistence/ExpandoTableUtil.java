/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.expando.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.expando.model.ExpandoTable;

import java.util.List;

/**
 * <a href="ExpandoTableUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ExpandoTablePersistence
 * @see       ExpandoTablePersistenceImpl
 * @generated
 */
public class ExpandoTableUtil {
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
	public static ExpandoTable remove(ExpandoTable expandoTable)
		throws SystemException {
		return getPersistence().remove(expandoTable);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ExpandoTable update(ExpandoTable expandoTable, boolean merge)
		throws SystemException {
		return getPersistence().update(expandoTable, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable) {
		getPersistence().cacheResult(expandoTable);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.expando.model.ExpandoTable> expandoTables) {
		getPersistence().cacheResult(expandoTables);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable create(
		long tableId) {
		return getPersistence().create(tableId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable remove(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence().remove(tableId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable updateImpl(
		com.liferay.portlet.expando.model.ExpandoTable expandoTable,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(expandoTable, merge);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable findByPrimaryKey(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence().findByPrimaryKey(tableId);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable fetchByPrimaryKey(
		long tableId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(tableId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(companyId, classNameId);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(companyId, classNameId, start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findByC_C(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C(companyId, classNameId, start, end, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable findByC_C_First(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence().findByC_C_First(companyId, classNameId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable findByC_C_Last(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence().findByC_C_Last(companyId, classNameId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable[] findByC_C_PrevAndNext(
		long tableId, long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence()
				   .findByC_C_PrevAndNext(tableId, companyId, classNameId, obc);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable findByC_C_N(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		return getPersistence().findByC_C_N(companyId, classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C_N(companyId, classNameId, name);
	}

	public static com.liferay.portlet.expando.model.ExpandoTable fetchByC_C_N(
		long companyId, long classNameId, java.lang.String name,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_N(companyId, classNameId, name, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoTable> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByC_C(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C(companyId, classNameId);
	}

	public static void removeByC_C_N(long companyId, long classNameId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.expando.NoSuchTableException {
		getPersistence().removeByC_C_N(companyId, classNameId, name);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByC_C(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(companyId, classNameId);
	}

	public static int countByC_C_N(long companyId, long classNameId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C_N(companyId, classNameId, name);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ExpandoTablePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ExpandoTablePersistence)PortalBeanLocatorUtil.locate(ExpandoTablePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ExpandoTablePersistence persistence) {
		_persistence = persistence;
	}

	private static ExpandoTablePersistence _persistence;
}