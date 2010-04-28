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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.LayoutPrototype;

import java.util.List;

/**
 * <a href="LayoutPrototypeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutPrototypePersistence
 * @see       LayoutPrototypePersistenceImpl
 * @generated
 */
public class LayoutPrototypeUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutPrototype> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutPrototype> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static LayoutPrototype remove(LayoutPrototype layoutPrototype)
		throws SystemException {
		return getPersistence().remove(layoutPrototype);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static LayoutPrototype update(LayoutPrototype layoutPrototype,
		boolean merge) throws SystemException {
		return getPersistence().update(layoutPrototype, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.LayoutPrototype layoutPrototype) {
		getPersistence().cacheResult(layoutPrototype);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutPrototype> layoutPrototypes) {
		getPersistence().cacheResult(layoutPrototypes);
	}

	public static com.liferay.portal.model.LayoutPrototype create(
		long layoutPrototypeId) {
		return getPersistence().create(layoutPrototypeId);
	}

	public static com.liferay.portal.model.LayoutPrototype remove(
		long layoutPrototypeId)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(layoutPrototypeId);
	}

	public static com.liferay.portal.model.LayoutPrototype updateImpl(
		com.liferay.portal.model.LayoutPrototype layoutPrototype, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(layoutPrototype, merge);
	}

	public static com.liferay.portal.model.LayoutPrototype findByPrimaryKey(
		long layoutPrototypeId)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(layoutPrototypeId);
	}

	public static com.liferay.portal.model.LayoutPrototype fetchByPrimaryKey(
		long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(layoutPrototypeId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.LayoutPrototype findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	public static com.liferay.portal.model.LayoutPrototype findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	public static com.liferay.portal.model.LayoutPrototype[] findByCompanyId_PrevAndNext(
		long layoutPrototypeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(layoutPrototypeId, companyId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findByC_A(
		long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_A(companyId, active);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findByC_A(
		long companyId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_A(companyId, active, start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_A(companyId, active, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.LayoutPrototype findByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_A_First(companyId, active, orderByComparator);
	}

	public static com.liferay.portal.model.LayoutPrototype findByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_A_Last(companyId, active, orderByComparator);
	}

	public static com.liferay.portal.model.LayoutPrototype[] findByC_A_PrevAndNext(
		long layoutPrototypeId, long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_A_PrevAndNext(layoutPrototypeId, companyId, active,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_A(long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_A(companyId, active);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_A(long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_A(companyId, active);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LayoutPrototypePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutPrototypePersistence)PortalBeanLocatorUtil.locate(LayoutPrototypePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(LayoutPrototypePersistence persistence) {
		_persistence = persistence;
	}

	private static LayoutPrototypePersistence _persistence;
}