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

import com.liferay.portlet.social.model.SocialEquityAssetEntry;

/**
 * <a href="SocialEquityAssetEntryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityAssetEntryPersistenceImpl
 * @see       SocialEquityAssetEntryUtil
 * @generated
 */
public interface SocialEquityAssetEntryPersistence extends BasePersistence<SocialEquityAssetEntry> {
	public void cacheResult(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry);

	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> socialEquityAssetEntries);

	public com.liferay.portlet.social.model.SocialEquityAssetEntry create(
		long equityAssetEntryId);

	public com.liferay.portlet.social.model.SocialEquityAssetEntry remove(
		long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityAssetEntryException;

	public com.liferay.portlet.social.model.SocialEquityAssetEntry updateImpl(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquityAssetEntry findByPrimaryKey(
		long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityAssetEntryException;

	public com.liferay.portlet.social.model.SocialEquityAssetEntry fetchByPrimaryKey(
		long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquityAssetEntry findByAssetEntryId(
		long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityAssetEntryException;

	public com.liferay.portlet.social.model.SocialEquityAssetEntry fetchByAssetEntryId(
		long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquityAssetEntry fetchByAssetEntryId(
		long assetEntryId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByAssetEntryId(long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityAssetEntryException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByAssetEntryId(long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}