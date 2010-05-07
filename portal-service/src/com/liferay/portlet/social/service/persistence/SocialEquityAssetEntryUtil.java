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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.social.model.SocialEquityAssetEntry;

import java.util.List;

/**
 * <a href="SocialEquityAssetEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityAssetEntryPersistence
 * @see       SocialEquityAssetEntryPersistenceImpl
 * @generated
 */
public class SocialEquityAssetEntryUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(SocialEquityAssetEntry)
	 */
	public static void clearCache(SocialEquityAssetEntry socialEquityAssetEntry) {
		getPersistence().clearCache(socialEquityAssetEntry);
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
	public static List<SocialEquityAssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialEquityAssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SocialEquityAssetEntry remove(
		SocialEquityAssetEntry socialEquityAssetEntry)
		throws SystemException {
		return getPersistence().remove(socialEquityAssetEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SocialEquityAssetEntry update(
		SocialEquityAssetEntry socialEquityAssetEntry, boolean merge)
		throws SystemException {
		return getPersistence().update(socialEquityAssetEntry, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry) {
		getPersistence().cacheResult(socialEquityAssetEntry);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> socialEquityAssetEntries) {
		getPersistence().cacheResult(socialEquityAssetEntries);
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry create(
		long equityAssetEntryId) {
		return getPersistence().create(equityAssetEntryId);
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry remove(
		long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityAssetEntryException {
		return getPersistence().remove(equityAssetEntryId);
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry updateImpl(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(socialEquityAssetEntry, merge);
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry findByPrimaryKey(
		long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityAssetEntryException {
		return getPersistence().findByPrimaryKey(equityAssetEntryId);
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry fetchByPrimaryKey(
		long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(equityAssetEntryId);
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry findByAssetEntryId(
		long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityAssetEntryException {
		return getPersistence().findByAssetEntryId(assetEntryId);
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry fetchByAssetEntryId(
		long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByAssetEntryId(assetEntryId);
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry fetchByAssetEntryId(
		long assetEntryId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByAssetEntryId(assetEntryId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByAssetEntryId(long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityAssetEntryException {
		getPersistence().removeByAssetEntryId(assetEntryId);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByAssetEntryId(long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByAssetEntryId(assetEntryId);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialEquityAssetEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialEquityAssetEntryPersistence)PortalBeanLocatorUtil.locate(SocialEquityAssetEntryPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(SocialEquityAssetEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static SocialEquityAssetEntryPersistence _persistence;
}