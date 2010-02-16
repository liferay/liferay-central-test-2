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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.model.MBDiscussion;

/**
 * <a href="MBDiscussionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBDiscussionPersistenceImpl
 * @see       MBDiscussionUtil
 * @generated
 */
public interface MBDiscussionPersistence extends BasePersistence<MBDiscussion> {
	public void cacheResult(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion);

	public void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> mbDiscussions);

	public com.liferay.portlet.messageboards.model.MBDiscussion create(
		long discussionId);

	public com.liferay.portlet.messageboards.model.MBDiscussion remove(
		long discussionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	public com.liferay.portlet.messageboards.model.MBDiscussion updateImpl(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBDiscussion findByPrimaryKey(
		long discussionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	public com.liferay.portlet.messageboards.model.MBDiscussion fetchByPrimaryKey(
		long discussionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findByClassNameId(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBDiscussion findByClassNameId_First(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	public com.liferay.portlet.messageboards.model.MBDiscussion findByClassNameId_Last(
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	public com.liferay.portlet.messageboards.model.MBDiscussion[] findByClassNameId_PrevAndNext(
		long discussionId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	public com.liferay.portlet.messageboards.model.MBDiscussion findByThreadId(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	public com.liferay.portlet.messageboards.model.MBDiscussion fetchByThreadId(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBDiscussion fetchByThreadId(
		long threadId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBDiscussion findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	public com.liferay.portlet.messageboards.model.MBDiscussion fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBDiscussion fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}