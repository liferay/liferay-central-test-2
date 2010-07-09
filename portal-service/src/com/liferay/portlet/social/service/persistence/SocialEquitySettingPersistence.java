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

import com.liferay.portlet.social.model.SocialEquitySetting;

/**
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquitySettingPersistenceImpl
 * @see       SocialEquitySettingUtil
 * @generated
 */
public interface SocialEquitySettingPersistence extends BasePersistence<SocialEquitySetting> {
	public void cacheResult(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting);

	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> socialEquitySettings);

	public com.liferay.portlet.social.model.SocialEquitySetting create(
		long equitySettingId);

	public com.liferay.portlet.social.model.SocialEquitySetting remove(
		long equitySettingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	public com.liferay.portlet.social.model.SocialEquitySetting updateImpl(
		com.liferay.portlet.social.model.SocialEquitySetting socialEquitySetting,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquitySetting findByPrimaryKey(
		long equitySettingId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	public com.liferay.portlet.social.model.SocialEquitySetting fetchByPrimaryKey(
		long equitySettingId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findByG_C_A(
		long groupId, long classNameId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findByG_C_A(
		long groupId, long classNameId, java.lang.String actionId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findByG_C_A(
		long groupId, long classNameId, java.lang.String actionId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquitySetting findByG_C_A_First(
		long groupId, long classNameId, java.lang.String actionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	public com.liferay.portlet.social.model.SocialEquitySetting findByG_C_A_Last(
		long groupId, long classNameId, java.lang.String actionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	public com.liferay.portlet.social.model.SocialEquitySetting[] findByG_C_A_PrevAndNext(
		long equitySettingId, long groupId, long classNameId,
		java.lang.String actionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	public com.liferay.portlet.social.model.SocialEquitySetting findByG_C_A_T(
		long groupId, long classNameId, java.lang.String actionId, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	public com.liferay.portlet.social.model.SocialEquitySetting fetchByG_C_A_T(
		long groupId, long classNameId, java.lang.String actionId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.social.model.SocialEquitySetting fetchByG_C_A_T(
		long groupId, long classNameId, java.lang.String actionId, int type,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialEquitySetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_C_A(long groupId, long classNameId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_C_A_T(long groupId, long classNameId,
		java.lang.String actionId, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquitySettingException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_C_A(long groupId, long classNameId,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_C_A_T(long groupId, long classNameId,
		java.lang.String actionId, int type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}