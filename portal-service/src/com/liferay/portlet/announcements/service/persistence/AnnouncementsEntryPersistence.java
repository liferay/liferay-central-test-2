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

import com.liferay.portlet.announcements.model.AnnouncementsEntry;

/**
 * <a href="AnnouncementsEntryPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsEntryPersistenceImpl
 * @see       AnnouncementsEntryUtil
 * @generated
 */
public interface AnnouncementsEntryPersistence extends BasePersistence<AnnouncementsEntry> {
	public void cacheResult(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry);

	public void cacheResult(
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> announcementsEntries);

	public com.liferay.portlet.announcements.model.AnnouncementsEntry create(
		long entryId);

	public com.liferay.portlet.announcements.model.AnnouncementsEntry remove(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementsEntry announcementsEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry[] findByUuid_PrevAndNext(
		long entryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry[] findByUserId_PrevAndNext(
		long entryId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry[] findByC_C_PrevAndNext(
		long entryId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByC_C_A(
		long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByC_C_A(
		long classNameId, long classPK, boolean alert, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findByC_C_A(
		long classNameId, long classPK, boolean alert, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry findByC_C_A_First(
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry findByC_C_A_Last(
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public com.liferay.portlet.announcements.model.AnnouncementsEntry[] findByC_C_A_PrevAndNext(
		long entryId, long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.announcements.NoSuchEntryException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsEntry> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_C_A(long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C_A(long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}