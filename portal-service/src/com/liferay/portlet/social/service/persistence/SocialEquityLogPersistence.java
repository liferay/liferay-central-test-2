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

import com.liferay.portlet.social.model.SocialEquityLog;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityLogPersistenceImpl
 * @see       SocialEquityLogUtil
 * @generated
 */
public interface SocialEquityLogPersistence extends BasePersistence<SocialEquityLog> {
	public void cacheResult(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog);

	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityLog> socialEquityLogs);

	public com.liferay.portlet.social.model.SocialEquityLog create(
		long equityLogId);

	public com.liferay.portlet.social.model.SocialEquityLog remove(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog updateImpl(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquityLog findByPrimaryKey(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog fetchByPrimaryKey(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AD_AI_A_T(
		long assetEntryId, int actionDate, java.lang.String actionId,
		boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AD_AI_A_T(
		long assetEntryId, int actionDate, java.lang.String actionId,
		boolean active, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AD_AI_A_T(
		long assetEntryId, int actionDate, java.lang.String actionId,
		boolean active, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquityLog findByAEI_AD_AI_A_T_First(
		long assetEntryId, int actionDate, java.lang.String actionId,
		boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog findByAEI_AD_AI_A_T_Last(
		long assetEntryId, int actionDate, java.lang.String actionId,
		boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_AD_AI_A_T_PrevAndNext(
		long equityLogId, long assetEntryId, int actionDate,
		java.lang.String actionId, boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AI_A_T(
		long assetEntryId, java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AI_A_T(
		long assetEntryId, java.lang.String actionId, boolean active, int type,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_AI_A_T(
		long assetEntryId, java.lang.String actionId, boolean active, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquityLog findByAEI_AI_A_T_First(
		long assetEntryId, java.lang.String actionId, boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog findByAEI_AI_A_T_Last(
		long assetEntryId, java.lang.String actionId, boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_AI_A_T_PrevAndNext(
		long equityLogId, long assetEntryId, java.lang.String actionId,
		boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_T_A(
		long assetEntryId, int type, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_T_A(
		long assetEntryId, int type, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByAEI_T_A(
		long assetEntryId, int type, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquityLog findByAEI_T_A_First(
		long assetEntryId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog findByAEI_T_A_Last(
		long assetEntryId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog[] findByAEI_T_A_PrevAndNext(
		long equityLogId, long assetEntryId, int type, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AD_AI_A_T(
		long userId, int actionDate, java.lang.String actionId, boolean active,
		int type) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AD_AI_A_T(
		long userId, int actionDate, java.lang.String actionId, boolean active,
		int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AD_AI_A_T(
		long userId, int actionDate, java.lang.String actionId, boolean active,
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquityLog findByU_AD_AI_A_T_First(
		long userId, int actionDate, java.lang.String actionId, boolean active,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog findByU_AD_AI_A_T_Last(
		long userId, int actionDate, java.lang.String actionId, boolean active,
		int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog[] findByU_AD_AI_A_T_PrevAndNext(
		long equityLogId, long userId, int actionDate,
		java.lang.String actionId, boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AI_A_T(
		long userId, java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AI_A_T(
		long userId, java.lang.String actionId, boolean active, int type,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findByU_AI_A_T(
		long userId, java.lang.String actionId, boolean active, int type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquityLog findByU_AI_A_T_First(
		long userId, java.lang.String actionId, boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog findByU_AI_A_T_Last(
		long userId, java.lang.String actionId, boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog[] findByU_AI_A_T_PrevAndNext(
		long equityLogId, long userId, java.lang.String actionId,
		boolean active, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog findByU_AEI_AD_AI_A_T(
		long userId, long assetEntryId, int actionDate,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public com.liferay.portlet.social.model.SocialEquityLog fetchByU_AEI_AD_AI_A_T(
		long userId, long assetEntryId, int actionDate,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquityLog fetchByU_AEI_AD_AI_A_T(
		long userId, long assetEntryId, int actionDate,
		java.lang.String actionId, boolean active, int type,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquityLog> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByAEI_AD_AI_A_T(long assetEntryId, int actionDate,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByAEI_AI_A_T(long assetEntryId,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByAEI_T_A(long assetEntryId, int type, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByU_AD_AI_A_T(long userId, int actionDate,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByU_AI_A_T(long userId, java.lang.String actionId,
		boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByU_AEI_AD_AI_A_T(long userId, long assetEntryId,
		int actionDate, java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityLogException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByAEI_AD_AI_A_T(long assetEntryId, int actionDate,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByAEI_AI_A_T(long assetEntryId, java.lang.String actionId,
		boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByAEI_T_A(long assetEntryId, int type, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByU_AD_AI_A_T(long userId, int actionDate,
		java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByU_AI_A_T(long userId, java.lang.String actionId,
		boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByU_AEI_AD_AI_A_T(long userId, long assetEntryId,
		int actionDate, java.lang.String actionId, boolean active, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}