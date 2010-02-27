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

import com.liferay.portal.model.LayoutSetPrototype;

/**
 * <a href="LayoutSetPrototypePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetPrototypePersistenceImpl
 * @see       LayoutSetPrototypeUtil
 * @generated
 */
public interface LayoutSetPrototypePersistence extends BasePersistence<LayoutSetPrototype> {
	public void cacheResult(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutSetPrototype> layoutSetPrototypes);

	public com.liferay.portal.model.LayoutSetPrototype create(
		long layoutSetPrototypeId);

	public com.liferay.portal.model.LayoutSetPrototype remove(
		long layoutSetPrototypeId)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSetPrototype updateImpl(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSetPrototype findByPrimaryKey(
		long layoutSetPrototypeId)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSetPrototype fetchByPrimaryKey(
		long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSetPrototype> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSetPrototype> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSetPrototype> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSetPrototype findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSetPrototype findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSetPrototype[] findByCompanyId_PrevAndNext(
		long layoutSetPrototypeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSetPrototype> findByC_A(
		long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSetPrototype> findByC_A(
		long companyId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSetPrototype> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSetPrototype findByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSetPrototype findByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutSetPrototype[] findByC_A_PrevAndNext(
		long layoutSetPrototypeId, long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSetPrototype> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSetPrototype> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.LayoutSetPrototype> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_A(long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_A(long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}