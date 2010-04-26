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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.model.SocialEquityHistory;

/**
 * <a href="SocialEquityHistoryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityHistoryPersistenceImpl
 * @see       SocialEquityHistoryUtil
 * @generated
 */
public interface SocialEquityHistoryPersistence extends BasePersistence<SocialEquityHistory> {
	public void cacheResult(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory);

	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> socialEquityHistories);

	public com.liferay.portlet.social.model.SocialEquityHistory create(
		long equityHistoryId);

	public com.liferay.portlet.social.model.SocialEquityHistory remove(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityHistoryException;

	public com.liferay.portlet.social.model.SocialEquityHistory updateImpl(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquityHistory findByPrimaryKey(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityHistoryException;

	public com.liferay.portlet.social.model.SocialEquityHistory fetchByPrimaryKey(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}