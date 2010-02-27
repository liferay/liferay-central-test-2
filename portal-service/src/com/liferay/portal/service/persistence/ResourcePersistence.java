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

import com.liferay.portal.model.Resource;

/**
 * <a href="ResourcePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePersistenceImpl
 * @see       ResourceUtil
 * @generated
 */
public interface ResourcePersistence extends BasePersistence<Resource> {
	public void cacheResult(com.liferay.portal.model.Resource resource);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Resource> resources);

	public com.liferay.portal.model.Resource create(long resourceId);

	public com.liferay.portal.model.Resource remove(long resourceId)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Resource updateImpl(
		com.liferay.portal.model.Resource resource, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Resource findByPrimaryKey(long resourceId)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Resource fetchByPrimaryKey(long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Resource> findByCodeId(
		long codeId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Resource> findByCodeId(
		long codeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Resource> findByCodeId(
		long codeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Resource findByCodeId_First(long codeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Resource findByCodeId_Last(long codeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Resource[] findByCodeId_PrevAndNext(
		long resourceId, long codeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Resource findByC_P(long codeId,
		java.lang.String primKey)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Resource fetchByC_P(long codeId,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Resource fetchByC_P(long codeId,
		java.lang.String primKey, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Resource> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Resource> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Resource> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCodeId(long codeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_P(long codeId, java.lang.String primKey)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCodeId(long codeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_P(long codeId, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}