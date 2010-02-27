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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.asset.model.AssetTagProperty;

/**
 * <a href="AssetTagPropertyPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTagPropertyPersistenceImpl
 * @see       AssetTagPropertyUtil
 * @generated
 */
public interface AssetTagPropertyPersistence extends BasePersistence<AssetTagProperty> {
	public void cacheResult(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty);

	public void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> assetTagProperties);

	public com.liferay.portlet.asset.model.AssetTagProperty create(
		long tagPropertyId);

	public com.liferay.portlet.asset.model.AssetTagProperty remove(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public com.liferay.portlet.asset.model.AssetTagProperty updateImpl(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetTagProperty findByPrimaryKey(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public com.liferay.portlet.asset.model.AssetTagProperty fetchByPrimaryKey(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetTagProperty findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public com.liferay.portlet.asset.model.AssetTagProperty findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public com.liferay.portlet.asset.model.AssetTagProperty[] findByCompanyId_PrevAndNext(
		long tagPropertyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByTagId(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByTagId(
		long tagId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByTagId(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetTagProperty findByTagId_First(
		long tagId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public com.liferay.portlet.asset.model.AssetTagProperty findByTagId_Last(
		long tagId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public com.liferay.portlet.asset.model.AssetTagProperty[] findByTagId_PrevAndNext(
		long tagPropertyId, long tagId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByC_K(
		long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetTagProperty findByC_K_First(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public com.liferay.portlet.asset.model.AssetTagProperty findByC_K_Last(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public com.liferay.portlet.asset.model.AssetTagProperty[] findByC_K_PrevAndNext(
		long tagPropertyId, long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public com.liferay.portlet.asset.model.AssetTagProperty findByT_K(
		long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public com.liferay.portlet.asset.model.AssetTagProperty fetchByT_K(
		long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.asset.model.AssetTagProperty fetchByT_K(
		long tagId, java.lang.String key, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByT_K(long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByT_K(long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}