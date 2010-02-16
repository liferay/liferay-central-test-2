/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.journal.model.JournalFeed;

/**
 * <a href="JournalFeedPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalFeedPersistenceImpl
 * @see       JournalFeedUtil
 * @generated
 */
public interface JournalFeedPersistence extends BasePersistence<JournalFeed> {
	public void cacheResult(
		com.liferay.portlet.journal.model.JournalFeed journalFeed);

	public void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalFeed> journalFeeds);

	public com.liferay.portlet.journal.model.JournalFeed create(long id);

	public com.liferay.portlet.journal.model.JournalFeed remove(long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFeedException;

	public com.liferay.portlet.journal.model.JournalFeed updateImpl(
		com.liferay.portlet.journal.model.JournalFeed journalFeed, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalFeed findByPrimaryKey(
		long id)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFeedException;

	public com.liferay.portlet.journal.model.JournalFeed fetchByPrimaryKey(
		long id) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalFeed findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFeedException;

	public com.liferay.portlet.journal.model.JournalFeed findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFeedException;

	public com.liferay.portlet.journal.model.JournalFeed[] findByUuid_PrevAndNext(
		long id, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFeedException;

	public com.liferay.portlet.journal.model.JournalFeed findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFeedException;

	public com.liferay.portlet.journal.model.JournalFeed fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalFeed fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalFeed findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFeedException;

	public com.liferay.portlet.journal.model.JournalFeed findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFeedException;

	public com.liferay.portlet.journal.model.JournalFeed[] findByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFeedException;

	public com.liferay.portlet.journal.model.JournalFeed findByG_F(
		long groupId, java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFeedException;

	public com.liferay.portlet.journal.model.JournalFeed fetchByG_F(
		long groupId, java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalFeed fetchByG_F(
		long groupId, java.lang.String feedId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFeedException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_F(long groupId, java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchFeedException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_F(long groupId, java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}