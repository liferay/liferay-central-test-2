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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.blogs.model.BlogsStatsUser;

/**
 * <a href="BlogsStatsUserPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsStatsUserPersistenceImpl
 * @see       BlogsStatsUserUtil
 * @generated
 */
public interface BlogsStatsUserPersistence extends BasePersistence<BlogsStatsUser> {
	public void cacheResult(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser);

	public void cacheResult(
		java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> blogsStatsUsers);

	public com.liferay.portlet.blogs.model.BlogsStatsUser create(
		long statsUserId);

	public com.liferay.portlet.blogs.model.BlogsStatsUser remove(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser updateImpl(
		com.liferay.portlet.blogs.model.BlogsStatsUser blogsStatsUser,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser findByPrimaryKey(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser fetchByPrimaryKey(
		long statsUserId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser[] findByGroupId_PrevAndNext(
		long statsUserId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser[] findByUserId_PrevAndNext(
		long statsUserId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser fetchByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser fetchByG_U(
		long groupId, long userId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByG_E(
		long groupId, int entryCount)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByG_E(
		long groupId, int entryCount, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByG_E(
		long groupId, int entryCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser findByG_E_First(
		long groupId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser findByG_E_Last(
		long groupId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser[] findByG_E_PrevAndNext(
		long statsUserId, long groupId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByC_E(
		long companyId, int entryCount)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByC_E(
		long companyId, int entryCount, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findByC_E(
		long companyId, int entryCount, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser findByC_E_First(
		long companyId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser findByC_E_Last(
		long companyId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public com.liferay.portlet.blogs.model.BlogsStatsUser[] findByC_E_PrevAndNext(
		long statsUserId, long companyId, int entryCount,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.blogs.model.BlogsStatsUser> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.blogs.NoSuchStatsUserException;

	public void removeByG_E(long groupId, int entryCount)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_E(long companyId, int entryCount)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_E(long groupId, int entryCount)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_E(long companyId, int entryCount)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}