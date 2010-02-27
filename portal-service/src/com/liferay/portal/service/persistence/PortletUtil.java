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
import com.liferay.portal.model.Portlet;

import java.util.List;

/**
 * <a href="PortletUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPersistence
 * @see       PortletPersistenceImpl
 * @generated
 */
public class PortletUtil {
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
	public static Portlet remove(Portlet portlet) throws SystemException {
		return getPersistence().remove(portlet);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Portlet update(Portlet portlet, boolean merge)
		throws SystemException {
		return getPersistence().update(portlet, merge);
	}

	public static void cacheResult(com.liferay.portal.model.Portlet portlet) {
		getPersistence().cacheResult(portlet);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Portlet> portlets) {
		getPersistence().cacheResult(portlets);
	}

	public static com.liferay.portal.model.Portlet create(long id) {
		return getPersistence().create(id);
	}

	public static com.liferay.portal.model.Portlet remove(long id)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(id);
	}

	public static com.liferay.portal.model.Portlet updateImpl(
		com.liferay.portal.model.Portlet portlet, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(portlet, merge);
	}

	public static com.liferay.portal.model.Portlet findByPrimaryKey(long id)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(id);
	}

	public static com.liferay.portal.model.Portlet fetchByPrimaryKey(long id)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(id);
	}

	public static java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.Portlet findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Portlet findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Portlet[] findByCompanyId_PrevAndNext(
		long id, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(id, companyId, obc);
	}

	public static com.liferay.portal.model.Portlet findByC_P(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_P(companyId, portletId);
	}

	public static com.liferay.portal.model.Portlet fetchByC_P(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_P(companyId, portletId);
	}

	public static com.liferay.portal.model.Portlet fetchByC_P(long companyId,
		java.lang.String portletId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_P(companyId, portletId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Portlet> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Portlet> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Portlet> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_P(long companyId, java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_P(companyId, portletId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_P(long companyId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_P(companyId, portletId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PortletPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PortletPersistence)PortalBeanLocatorUtil.locate(PortletPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(PortletPersistence persistence) {
		_persistence = persistence;
	}

	private static PortletPersistence _persistence;
}