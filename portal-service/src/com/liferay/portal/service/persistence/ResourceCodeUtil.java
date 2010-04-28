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
import com.liferay.portal.model.ResourceCode;

import java.util.List;

/**
 * <a href="ResourceCodeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceCodePersistence
 * @see       ResourceCodePersistenceImpl
 * @generated
 */
public class ResourceCodeUtil {
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
	public static List<ResourceCode> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ResourceCode> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static ResourceCode remove(ResourceCode resourceCode)
		throws SystemException {
		return getPersistence().remove(resourceCode);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ResourceCode update(ResourceCode resourceCode, boolean merge)
		throws SystemException {
		return getPersistence().update(resourceCode, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.ResourceCode resourceCode) {
		getPersistence().cacheResult(resourceCode);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ResourceCode> resourceCodes) {
		getPersistence().cacheResult(resourceCodes);
	}

	public static com.liferay.portal.model.ResourceCode create(long codeId) {
		return getPersistence().create(codeId);
	}

	public static com.liferay.portal.model.ResourceCode remove(long codeId)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(codeId);
	}

	public static com.liferay.portal.model.ResourceCode updateImpl(
		com.liferay.portal.model.ResourceCode resourceCode, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(resourceCode, merge);
	}

	public static com.liferay.portal.model.ResourceCode findByPrimaryKey(
		long codeId)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(codeId);
	}

	public static com.liferay.portal.model.ResourceCode fetchByPrimaryKey(
		long codeId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(codeId);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.ResourceCode findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	public static com.liferay.portal.model.ResourceCode findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	public static com.liferay.portal.model.ResourceCode[] findByCompanyId_PrevAndNext(
		long codeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(codeId, companyId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findByName(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByName(name);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findByName(
		java.lang.String name, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByName(name, start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findByName(
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByName(name, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.ResourceCode findByName_First(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByName_First(name, orderByComparator);
	}

	public static com.liferay.portal.model.ResourceCode findByName_Last(
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByName_Last(name, orderByComparator);
	}

	public static com.liferay.portal.model.ResourceCode[] findByName_PrevAndNext(
		long codeId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByName_PrevAndNext(codeId, name, orderByComparator);
	}

	public static com.liferay.portal.model.ResourceCode findByC_N_S(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N_S(companyId, name, scope);
	}

	public static com.liferay.portal.model.ResourceCode fetchByC_N_S(
		long companyId, java.lang.String name, int scope)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_N_S(companyId, name, scope);
	}

	public static com.liferay.portal.model.ResourceCode fetchByC_N_S(
		long companyId, java.lang.String name, int scope,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_N_S(companyId, name, scope, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.ResourceCode> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByName(name);
	}

	public static void removeByC_N_S(long companyId, java.lang.String name,
		int scope)
		throws com.liferay.portal.NoSuchResourceCodeException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_N_S(companyId, name, scope);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByName(java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByName(name);
	}

	public static int countByC_N_S(long companyId, java.lang.String name,
		int scope) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_N_S(companyId, name, scope);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ResourceCodePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ResourceCodePersistence)PortalBeanLocatorUtil.locate(ResourceCodePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ResourceCodePersistence persistence) {
		_persistence = persistence;
	}

	private static ResourceCodePersistence _persistence;
}