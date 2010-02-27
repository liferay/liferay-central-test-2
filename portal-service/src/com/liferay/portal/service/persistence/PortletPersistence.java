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

import com.liferay.portal.model.Portlet;

/**
 * <a href="PortletPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPersistenceImpl
 * @see       PortletUtil
 * @generated
 */
public interface PortletPersistence extends BasePersistence<Portlet> {
	public void cacheResult(com.liferay.portal.model.Portlet portlet);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Portlet> portlets);

	public com.liferay.portal.model.Portlet create(long id);

	public com.liferay.portal.model.Portlet remove(long id)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Portlet updateImpl(
		com.liferay.portal.model.Portlet portlet, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Portlet findByPrimaryKey(long id)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Portlet fetchByPrimaryKey(long id)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Portlet findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Portlet findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Portlet[] findByCompanyId_PrevAndNext(
		long id, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Portlet findByC_P(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Portlet fetchByC_P(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Portlet fetchByC_P(long companyId,
		java.lang.String portletId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Portlet> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Portlet> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Portlet> findAll(int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_P(long companyId, java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_P(long companyId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}