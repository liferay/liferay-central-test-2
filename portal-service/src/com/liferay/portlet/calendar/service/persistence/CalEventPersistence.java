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

package com.liferay.portlet.calendar.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.calendar.model.CalEvent;

/**
 * <a href="CalEventPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CalEventPersistenceImpl
 * @see       CalEventUtil
 * @generated
 */
public interface CalEventPersistence extends BasePersistence<CalEvent> {
	public void cacheResult(
		com.liferay.portlet.calendar.model.CalEvent calEvent);

	public void cacheResult(
		java.util.List<com.liferay.portlet.calendar.model.CalEvent> calEvents);

	public com.liferay.portlet.calendar.model.CalEvent create(long eventId);

	public com.liferay.portlet.calendar.model.CalEvent remove(long eventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent updateImpl(
		com.liferay.portlet.calendar.model.CalEvent calEvent, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.calendar.model.CalEvent findByPrimaryKey(
		long eventId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent fetchByPrimaryKey(
		long eventId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.calendar.model.CalEvent findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent[] findByUuid_PrevAndNext(
		long eventId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.calendar.model.CalEvent fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.calendar.model.CalEvent findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent[] findByCompanyId_PrevAndNext(
		long eventId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.calendar.model.CalEvent findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent[] findByGroupId_PrevAndNext(
		long eventId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByRemindBy(
		int remindBy)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByRemindBy(
		int remindBy, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByRemindBy(
		int remindBy, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.calendar.model.CalEvent findByRemindBy_First(
		int remindBy, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent findByRemindBy_Last(
		int remindBy, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent[] findByRemindBy_PrevAndNext(
		long eventId, int remindBy,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_T(
		long groupId, java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.calendar.model.CalEvent findByG_T_First(
		long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent findByG_T_Last(
		long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent[] findByG_T_PrevAndNext(
		long eventId, long groupId, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_R(
		long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_R(
		long groupId, boolean repeating, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findByG_R(
		long groupId, boolean repeating, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.calendar.model.CalEvent findByG_R_First(
		long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent findByG_R_Last(
		long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public com.liferay.portlet.calendar.model.CalEvent[] findByG_R_PrevAndNext(
		long eventId, long groupId, boolean repeating,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.calendar.model.CalEvent> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.calendar.NoSuchEventException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByRemindBy(int remindBy)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_T(long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_R(long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByRemindBy(int remindBy)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_T(long groupId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_R(long groupId, boolean repeating)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}