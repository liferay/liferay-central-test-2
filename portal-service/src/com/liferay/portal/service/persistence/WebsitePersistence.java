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

import com.liferay.portal.model.Website;

/**
 * <a href="WebsitePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WebsitePersistenceImpl
 * @see       WebsiteUtil
 * @generated
 */
public interface WebsitePersistence extends BasePersistence<Website> {
	public void cacheResult(com.liferay.portal.model.Website website);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Website> websites);

	public com.liferay.portal.model.Website create(long websiteId);

	public com.liferay.portal.model.Website remove(long websiteId)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website updateImpl(
		com.liferay.portal.model.Website website, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website findByPrimaryKey(long websiteId)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website fetchByPrimaryKey(long websiteId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website[] findByCompanyId_PrevAndNext(
		long websiteId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website[] findByUserId_PrevAndNext(
		long websiteId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByC_C(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByC_C(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByC_C(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website findByC_C_First(long companyId,
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website findByC_C_Last(long companyId,
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website[] findByC_C_PrevAndNext(
		long websiteId, long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByC_C_C(
		long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website findByC_C_C_First(long companyId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website findByC_C_C_Last(long companyId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website[] findByC_C_C_PrevAndNext(
		long websiteId, long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website findByC_C_C_P_First(
		long companyId, long classNameId, long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website findByC_C_C_P_Last(long companyId,
		long classNameId, long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Website[] findByC_C_C_P_PrevAndNext(
		long websiteId, long companyId, long classNameId, long classPK,
		boolean primary, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWebsiteException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Website> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_C(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}