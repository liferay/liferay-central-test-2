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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.social.model.SocialEquityLog;

import java.util.List;

/**
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
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(SocialEquityLog socialEquityLog) {
		getPersistence().clearCache(socialEquityLog);
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
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialEquityLog> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static SocialEquityLog update(SocialEquityLog socialEquityLog,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(socialEquityLog, merge, serviceContext);
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

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AD_AI_A_T(
		long assetEntryId, int actionDate, java.lang.String actionId,
		boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_AD_AI_A_T(assetEntryId, actionDate, actionId,
			active, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AD_AI_A_T(
		long assetEntryId, int actionDate, java.lang.String actionId,
		boolean active, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_AD_AI_A_T(assetEntryId, actionDate, actionId,
			active, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AD_AI_A_T(
		long assetEntryId, int actionDate, java.lang.String actionId,
		boolean active, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_AD_AI_A_T(assetEntryId, actionDate, actionId,
			active, type, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_AD_AI_A_T_First(
		long assetEntryId, int actionDate, java.lang.String actionId,
		boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_AD_AI_A_T_First(assetEntryId, actionDate,
			actionId, active, type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_AD_AI_A_T_Last(
		long assetEntryId, int actionDate, java.lang.String actionId,
		boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_AD_AI_A_T_Last(assetEntryId, actionDate,
			actionId, active, type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_AD_AI_A_T_PrevAndNext(
		long equityLogId, long assetEntryId, int actionDate,
		java.lang.String actionId, boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_AD_AI_A_T_PrevAndNext(equityLogId, assetEntryId,
			actionDate, actionId, active, type, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AI_A_T(
		long assetEntryId, java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_AI_A_T(assetEntryId, actionId, active, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AI_A_T(
		long assetEntryId, java.lang.String actionId, boolean active, int type,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_AI_A_T(assetEntryId, actionId, active, type,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AI_A_T(
		long assetEntryId, java.lang.String actionId, boolean active, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_AI_A_T(assetEntryId, actionId, active, type,
			start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_AI_A_T_First(
		long assetEntryId, java.lang.String actionId, boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_AI_A_T_First(assetEntryId, actionId, active,
			type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_AI_A_T_Last(
		long assetEntryId, java.lang.String actionId, boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_AI_A_T_Last(assetEntryId, actionId, active, type,
			orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_AI_A_T_PrevAndNext(
		long equityLogId, long assetEntryId, java.lang.String actionId,
		boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_AI_A_T_PrevAndNext(equityLogId, assetEntryId,
			actionId, active, type, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_T_A(
		long assetEntryId, int type, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByAEI_T_A(assetEntryId, type, active);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_T_A(
		long assetEntryId, int type, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_T_A(assetEntryId, type, active, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_T_A(
		long assetEntryId, int type, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByAEI_T_A(assetEntryId, type, active, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_T_A_First(
		long assetEntryId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_T_A_First(assetEntryId, type, active,
			orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByAEI_T_A_Last(
		long assetEntryId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_T_A_Last(assetEntryId, type, active,
			orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_T_A_PrevAndNext(
		long equityLogId, long assetEntryId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByAEI_T_A_PrevAndNext(equityLogId, assetEntryId, type,
			active, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AD_AI_A_T(
		long userId, int actionDate, java.lang.String actionId, boolean active,
		int type) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AD_AI_A_T(userId, actionDate, actionId, active, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AD_AI_A_T(
		long userId, int actionDate, java.lang.String actionId, boolean active,
		int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AD_AI_A_T(userId, actionDate, actionId, active,
			type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AD_AI_A_T(
		long userId, int actionDate, java.lang.String actionId, boolean active,
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AD_AI_A_T(userId, actionDate, actionId, active,
			type, start, end, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByU_AD_AI_A_T_First(
		long userId, int actionDate, java.lang.String actionId, boolean active,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AD_AI_A_T_First(userId, actionDate, actionId,
			active, type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByU_AD_AI_A_T_Last(
		long userId, int actionDate, java.lang.String actionId, boolean active,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AD_AI_A_T_Last(userId, actionDate, actionId,
			active, type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog[] findByU_AD_AI_A_T_PrevAndNext(
		long equityLogId, long userId, int actionDate,
		java.lang.String actionId, boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AD_AI_A_T_PrevAndNext(equityLogId, userId,
			actionDate, actionId, active, type, orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AI_A_T(
		long userId, java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByU_AI_A_T(userId, actionId, active, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AI_A_T(
		long userId, java.lang.String actionId, boolean active, int type,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AI_A_T(userId, actionId, active, type, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AI_A_T(
		long userId, java.lang.String actionId, boolean active, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByU_AI_A_T(userId, actionId, active, type, start, end,
			orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByU_AI_A_T_First(
		long userId, java.lang.String actionId, boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AI_A_T_First(userId, actionId, active, type,
			orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByU_AI_A_T_Last(
		long userId, java.lang.String actionId, boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AI_A_T_Last(userId, actionId, active, type,
			orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog[] findByU_AI_A_T_PrevAndNext(
		long equityLogId, long userId, java.lang.String actionId,
		boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AI_A_T_PrevAndNext(equityLogId, userId, actionId,
			active, type, orderByComparator);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog findByU_AEI_AD_AI_A_T(
		long userId, long assetEntryId, int actionDate,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		return getPersistence()
				   .findByU_AEI_AD_AI_A_T(userId, assetEntryId, actionDate,
			actionId, active, type);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog fetchByU_AEI_AD_AI_A_T(
		long userId, long assetEntryId, int actionDate,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_AEI_AD_AI_A_T(userId, assetEntryId, actionDate,
			actionId, active, type);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog fetchByU_AEI_AD_AI_A_T(
		long userId, long assetEntryId, int actionDate,
		java.lang.String actionId, boolean active, int type,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByU_AEI_AD_AI_A_T(userId, assetEntryId, actionDate,
			actionId, active, type, retrieveFromCache);
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

	public static void removeByAEI_AD_AI_A_T(long assetEntryId, int actionDate,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByAEI_AD_AI_A_T(assetEntryId, actionDate, actionId, active,
			type);
	}

	public static void removeByAEI_AI_A_T(long assetEntryId,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByAEI_AI_A_T(assetEntryId, actionId, active, type);
	}

	public static void removeByAEI_T_A(long assetEntryId, int type,
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByAEI_T_A(assetEntryId, type, active);
	}

	public static void removeByU_AD_AI_A_T(long userId, int actionDate,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByU_AD_AI_A_T(userId, actionDate, actionId, active, type);
	}

	public static void removeByU_AI_A_T(long userId, java.lang.String actionId,
		boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByU_AI_A_T(userId, actionId, active, type);
	}

	public static void removeByU_AEI_AD_AI_A_T(long userId, long assetEntryId,
		int actionDate, java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException {
		getPersistence()
			.removeByU_AEI_AD_AI_A_T(userId, assetEntryId, actionDate,
			actionId, active, type);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByAEI_AD_AI_A_T(long assetEntryId, int actionDate,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByAEI_AD_AI_A_T(assetEntryId, actionDate, actionId,
			active, type);
	}

	public static int countByAEI_AI_A_T(long assetEntryId,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByAEI_AI_A_T(assetEntryId, actionId, active, type);
	}

	public static int countByAEI_T_A(long assetEntryId, int type, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByAEI_T_A(assetEntryId, type, active);
	}

	public static int countByU_AD_AI_A_T(long userId, int actionDate,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByU_AD_AI_A_T(userId, actionDate, actionId, active,
			type);
	}

	public static int countByU_AI_A_T(long userId, java.lang.String actionId,
		boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByU_AI_A_T(userId, actionId, active, type);
	}

	public static int countByU_AEI_AD_AI_A_T(long userId, long assetEntryId,
		int actionDate, java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByU_AEI_AD_AI_A_T(userId, assetEntryId, actionDate,
			actionId, active, type);
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