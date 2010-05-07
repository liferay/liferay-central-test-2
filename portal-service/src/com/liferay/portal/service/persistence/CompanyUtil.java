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
import com.liferay.portal.model.Company;

import java.util.List;

/**
 * <a href="CompanyUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CompanyPersistence
 * @see       CompanyPersistenceImpl
 * @generated
 */
public class CompanyUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(Company)
	 */
	public static void clearCache(Company company) {
		getPersistence().clearCache(company);
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
	public static List<Company> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Company> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static Company remove(Company company) throws SystemException {
		return getPersistence().remove(company);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Company update(Company company, boolean merge)
		throws SystemException {
		return getPersistence().update(company, merge);
	}

	public static void cacheResult(com.liferay.portal.model.Company company) {
		getPersistence().cacheResult(company);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Company> companies) {
		getPersistence().cacheResult(companies);
	}

	public static com.liferay.portal.model.Company create(long companyId) {
		return getPersistence().create(companyId);
	}

	public static com.liferay.portal.model.Company remove(long companyId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(companyId);
	}

	public static com.liferay.portal.model.Company updateImpl(
		com.liferay.portal.model.Company company, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(company, merge);
	}

	public static com.liferay.portal.model.Company findByPrimaryKey(
		long companyId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(companyId);
	}

	public static com.liferay.portal.model.Company fetchByPrimaryKey(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(companyId);
	}

	public static com.liferay.portal.model.Company findByWebId(
		java.lang.String webId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByWebId(webId);
	}

	public static com.liferay.portal.model.Company fetchByWebId(
		java.lang.String webId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByWebId(webId);
	}

	public static com.liferay.portal.model.Company fetchByWebId(
		java.lang.String webId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByWebId(webId, retrieveFromCache);
	}

	public static com.liferay.portal.model.Company findByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByVirtualHost(virtualHost);
	}

	public static com.liferay.portal.model.Company fetchByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByVirtualHost(virtualHost);
	}

	public static com.liferay.portal.model.Company fetchByVirtualHost(
		java.lang.String virtualHost, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByVirtualHost(virtualHost, retrieveFromCache);
	}

	public static com.liferay.portal.model.Company findByMx(java.lang.String mx)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByMx(mx);
	}

	public static com.liferay.portal.model.Company fetchByMx(
		java.lang.String mx)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByMx(mx);
	}

	public static com.liferay.portal.model.Company fetchByMx(
		java.lang.String mx, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByMx(mx, retrieveFromCache);
	}

	public static com.liferay.portal.model.Company findByLogoId(long logoId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLogoId(logoId);
	}

	public static com.liferay.portal.model.Company fetchByLogoId(long logoId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByLogoId(logoId);
	}

	public static com.liferay.portal.model.Company fetchByLogoId(long logoId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByLogoId(logoId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySystem(system);
	}

	public static java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySystem(system, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBySystem(system, start, end, orderByComparator);
	}

	public static com.liferay.portal.model.Company findBySystem_First(
		boolean system,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySystem_First(system, orderByComparator);
	}

	public static com.liferay.portal.model.Company findBySystem_Last(
		boolean system,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findBySystem_Last(system, orderByComparator);
	}

	public static com.liferay.portal.model.Company[] findBySystem_PrevAndNext(
		long companyId, boolean system,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findBySystem_PrevAndNext(companyId, system,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portal.model.Company> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Company> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Company> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByWebId(java.lang.String webId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByWebId(webId);
	}

	public static void removeByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByVirtualHost(virtualHost);
	}

	public static void removeByMx(java.lang.String mx)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByMx(mx);
	}

	public static void removeByLogoId(long logoId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByLogoId(logoId);
	}

	public static void removeBySystem(boolean system)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeBySystem(system);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByWebId(java.lang.String webId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByWebId(webId);
	}

	public static int countByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByVirtualHost(virtualHost);
	}

	public static int countByMx(java.lang.String mx)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByMx(mx);
	}

	public static int countByLogoId(long logoId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByLogoId(logoId);
	}

	public static int countBySystem(boolean system)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countBySystem(system);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static CompanyPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CompanyPersistence)PortalBeanLocatorUtil.locate(CompanyPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(CompanyPersistence persistence) {
		_persistence = persistence;
	}

	private static CompanyPersistence _persistence;
}