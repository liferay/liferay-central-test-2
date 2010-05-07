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
import com.liferay.portal.model.ClassName;

import java.util.List;

/**
 * <a href="ClassNameUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClassNamePersistence
 * @see       ClassNamePersistenceImpl
 * @generated
 */
public class ClassNameUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(ClassName)
	 */
	public static void clearCache(ClassName className) {
		getPersistence().clearCache(className);
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
	public static List<ClassName> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ClassName> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static ClassName remove(ClassName className)
		throws SystemException {
		return getPersistence().remove(className);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ClassName update(ClassName className, boolean merge)
		throws SystemException {
		return getPersistence().update(className, merge);
	}

	public static void cacheResult(com.liferay.portal.model.ClassName className) {
		getPersistence().cacheResult(className);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ClassName> classNames) {
		getPersistence().cacheResult(classNames);
	}

	public static com.liferay.portal.model.ClassName create(long classNameId) {
		return getPersistence().create(classNameId);
	}

	public static com.liferay.portal.model.ClassName remove(long classNameId)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(classNameId);
	}

	public static com.liferay.portal.model.ClassName updateImpl(
		com.liferay.portal.model.ClassName className, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(className, merge);
	}

	public static com.liferay.portal.model.ClassName findByPrimaryKey(
		long classNameId)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(classNameId);
	}

	public static com.liferay.portal.model.ClassName fetchByPrimaryKey(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(classNameId);
	}

	public static com.liferay.portal.model.ClassName findByValue(
		java.lang.String value)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByValue(value);
	}

	public static com.liferay.portal.model.ClassName fetchByValue(
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByValue(value);
	}

	public static com.liferay.portal.model.ClassName fetchByValue(
		java.lang.String value, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByValue(value, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.ClassName> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ClassName> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.ClassName> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByValue(java.lang.String value)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByValue(value);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByValue(java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByValue(value);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ClassNamePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ClassNamePersistence)PortalBeanLocatorUtil.locate(ClassNamePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ClassNamePersistence persistence) {
		_persistence = persistence;
	}

	private static ClassNamePersistence _persistence;
}