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

package com.liferay.portlet.announcements.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.announcements.model.AnnouncementsFlag;

/**
 * <a href="AnnouncementsFlagPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsFlagPersistenceImpl
 * @see       AnnouncementsFlagUtil
 * @generated
 */
public interface AnnouncementsFlagPersistence extends BasePersistence<AnnouncementsFlag> {
	public void cacheResult(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag);

	public void cacheResult(
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> announcementsFlags);

	public com.liferay.portlet.announcements.model.AnnouncementsFlag create(
		long flagId);

	public com.liferay.portlet.announcements.model.AnnouncementsFlag remove(
		long flagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException;

	public com.liferay.portlet.announcements.model.AnnouncementsFlag updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsFlag findByPrimaryKey(
		long flagId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException;

	public com.liferay.portlet.announcements.model.AnnouncementsFlag fetchByPrimaryKey(
		long flagId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findByEntryId(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findByEntryId(
		long entryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findByEntryId(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsFlag findByEntryId_First(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException;

	public com.liferay.portlet.announcements.model.AnnouncementsFlag findByEntryId_Last(
		long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException;

	public com.liferay.portlet.announcements.model.AnnouncementsFlag[] findByEntryId_PrevAndNext(
		long flagId, long entryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException;

	public com.liferay.portlet.announcements.model.AnnouncementsFlag findByU_E_V(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException;

	public com.liferay.portlet.announcements.model.AnnouncementsFlag fetchByU_E_V(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsFlag fetchByU_E_V(
		long userId, long entryId, int value, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByEntryId(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByU_E_V(long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchFlagException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByEntryId(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByU_E_V(long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}