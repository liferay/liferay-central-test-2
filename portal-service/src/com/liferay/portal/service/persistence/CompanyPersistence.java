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

import com.liferay.portal.model.Company;

/**
 * <a href="CompanyPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CompanyPersistenceImpl
 * @see       CompanyUtil
 * @generated
 */
public interface CompanyPersistence extends BasePersistence<Company> {
	public void cacheResult(com.liferay.portal.model.Company company);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Company> companies);

	public com.liferay.portal.model.Company create(long companyId);

	public com.liferay.portal.model.Company remove(long companyId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company updateImpl(
		com.liferay.portal.model.Company company, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findByPrimaryKey(long companyId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByPrimaryKey(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findByWebId(java.lang.String webId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByWebId(java.lang.String webId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByWebId(
		java.lang.String webId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByVirtualHost(
		java.lang.String virtualHost, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findByMx(java.lang.String mx)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByMx(java.lang.String mx)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByMx(java.lang.String mx,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findByLogoId(long logoId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByLogoId(long logoId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company fetchByLogoId(long logoId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findBySystem_First(boolean system,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company findBySystem_Last(boolean system,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company[] findBySystem_PrevAndNext(
		long companyId, boolean system,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Company> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Company> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Company> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByWebId(java.lang.String webId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByMx(java.lang.String mx)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeByLogoId(long logoId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeBySystem(boolean system)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByWebId(java.lang.String webId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByMx(java.lang.String mx)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByLogoId(long logoId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countBySystem(boolean system)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}