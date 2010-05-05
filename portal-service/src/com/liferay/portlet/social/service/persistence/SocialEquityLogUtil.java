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

import com.liferay.portlet.social.model.SocialEquityLog;

import java.util.List;

/**
 * <a href="SocialEquityLogUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityLogPersistence
 * @see       SocialEquityLogPersistenceImpl
 * @generated
 */
public class SocialEquityLogUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
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
	public static List<SocialEquityLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialEquityLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SocialEquityLog remove(SocialEquityLog socialEquityLog)
		throws SystemException {
		return getPersistence().remove(socialEquityLog);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SocialEquityLog update(SocialEquityLog socialEquityLog,
		boolean merge) throws SystemException {
		return getPersistence().update(socialEquityLog, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog) {
		getPersistence().cacheResult(socialEquityLog);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityLog> socialEquityLogs) {
		getPersistence().cacheResult(socialEquityLogs);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog create(
		long equityLogId) {
		return getPersistence().create(equityLogId);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog remove(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence().remove(equityLogId);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog updateImpl(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(socialEquityLog, merge);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByPrimaryKey(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence().findByPrimaryKey(equityLogId);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog fetchByPrimaryKey(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(equityLogId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_A(
		long assetEntryId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByAEI_A(assetEntryId, active);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_A(
		long assetEntryId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByAEI_A(assetEntryId, active, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_A(
		long assetEntryId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_A(assetEntryId, active, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_A_First(
		long assetEntryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_A_First(assetEntryId, active, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_A_Last(
		long assetEntryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_A_Last(assetEntryId, active, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_A_PrevAndNext(
		long equityLogId, long assetEntryId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_A_PrevAndNext(equityLogId, assetEntryId, active,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AEI_A_A(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AEI_A_A(userId, assetEntryId, actionId, active);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AEI_A_A(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AEI_A_A(userId, assetEntryId, actionId, active,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AEI_A_A(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AEI_A_A(userId, assetEntryId, actionId, active,
			start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByU_AEI_A_A_First(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AEI_A_A_First(userId, assetEntryId, actionId,
			active, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByU_AEI_A_A_Last(
		long userId, long assetEntryId, java.lang.String actionId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AEI_A_A_Last(userId, assetEntryId, actionId,
			active, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog[] findByU_AEI_A_A_PrevAndNext(
		long equityLogId, long userId, long assetEntryId,
		java.lang.String actionId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AEI_A_A_PrevAndNext(equityLogId, userId,
			assetEntryId, actionId, active, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByAEI_A(long assetEntryId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByAEI_A(assetEntryId, active);
	}

	public static void removeByU_AEI_A_A(long userId, long assetEntryId,
		java.lang.String actionId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByU_AEI_A_A(userId, assetEntryId, actionId, active);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByAEI_A(long assetEntryId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByAEI_A(assetEntryId, active);
	}

	public static int countByU_AEI_A_A(long userId, long assetEntryId,
		java.lang.String actionId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByU_AEI_A_A(userId, assetEntryId, actionId, active);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialEquityLogPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialEquityLogPersistence)PortalBeanLocatorUtil.locate(SocialEquityLogPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(SocialEquityLogPersistence persistence) {
		_persistence = persistence;
	}

	private static SocialEquityLogPersistence _persistence;
}