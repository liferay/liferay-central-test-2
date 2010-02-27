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

import com.liferay.portal.model.OrgLabor;

/**
 * <a href="OrgLaborPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgLaborPersistenceImpl
 * @see       OrgLaborUtil
 * @generated
 */
public interface OrgLaborPersistence extends BasePersistence<OrgLabor> {
	public void cacheResult(com.liferay.portal.model.OrgLabor orgLabor);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors);

	public com.liferay.portal.model.OrgLabor create(long orgLaborId);

	public com.liferay.portal.model.OrgLabor remove(long orgLaborId)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgLabor updateImpl(
		com.liferay.portal.model.OrgLabor orgLabor, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgLabor findByPrimaryKey(long orgLaborId)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgLabor fetchByPrimaryKey(long orgLaborId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgLabor> findByOrganizationId(
		long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgLabor> findByOrganizationId(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgLabor> findByOrganizationId(
		long organizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgLabor findByOrganizationId_First(
		long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgLabor findByOrganizationId_Last(
		long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgLabor[] findByOrganizationId_PrevAndNext(
		long orgLaborId, long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgLabor> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgLabor> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgLabor> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByOrganizationId(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByOrganizationId(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}