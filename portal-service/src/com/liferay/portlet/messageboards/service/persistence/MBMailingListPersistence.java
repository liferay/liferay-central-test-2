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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.model.MBMailingList;

/**
 * <a href="MBMailingListPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMailingListPersistenceImpl
 * @see       MBMailingListUtil
 * @generated
 */
public interface MBMailingListPersistence extends BasePersistence<MBMailingList> {
	public void cacheResult(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList);

	public void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> mbMailingLists);

	public com.liferay.portlet.messageboards.model.MBMailingList create(
		long mailingListId);

	public com.liferay.portlet.messageboards.model.MBMailingList remove(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public com.liferay.portlet.messageboards.model.MBMailingList updateImpl(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMailingList findByPrimaryKey(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public com.liferay.portlet.messageboards.model.MBMailingList fetchByPrimaryKey(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMailingList findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public com.liferay.portlet.messageboards.model.MBMailingList findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public com.liferay.portlet.messageboards.model.MBMailingList[] findByUuid_PrevAndNext(
		long mailingListId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public com.liferay.portlet.messageboards.model.MBMailingList findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public com.liferay.portlet.messageboards.model.MBMailingList fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMailingList fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByActive(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMailingList findByActive_First(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public com.liferay.portlet.messageboards.model.MBMailingList findByActive_Last(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public com.liferay.portlet.messageboards.model.MBMailingList[] findByActive_PrevAndNext(
		long mailingListId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public com.liferay.portlet.messageboards.model.MBMailingList findByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public com.liferay.portlet.messageboards.model.MBMailingList fetchByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMailingList fetchByG_C(
		long groupId, long categoryId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public void removeByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}