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

import com.liferay.portal.model.PortletPreferences;

/**
 * <a href="PortletPreferencesPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPreferencesPersistenceImpl
 * @see       PortletPreferencesUtil
 * @generated
 */
public interface PortletPreferencesPersistence extends BasePersistence<PortletPreferences> {
	public void cacheResult(
		com.liferay.portal.model.PortletPreferences portletPreferences);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.PortletPreferences> portletPreferenceses);

	public com.liferay.portal.model.PortletPreferences create(
		long portletPreferencesId);

	public com.liferay.portal.model.PortletPreferences remove(
		long portletPreferencesId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences updateImpl(
		com.liferay.portal.model.PortletPreferences portletPreferences,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByPrimaryKey(
		long portletPreferencesId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences fetchByPrimaryKey(
		long portletPreferencesId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByPlid_First(
		long plid, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByPlid_Last(
		long plid, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences[] findByPlid_PrevAndNext(
		long portletPreferencesId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByP_P_First(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByP_P_Last(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences[] findByP_P_PrevAndNext(
		long portletPreferencesId, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByO_O_P_First(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByO_O_P_Last(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences[] findByO_O_P_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByP_P(long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByO_O_P(long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByO_O_P_P(long ownerId, int ownerType, long plid,
		java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByP_P(long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByO_O_P(long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByO_O_P_P(long ownerId, int ownerType, long plid,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}