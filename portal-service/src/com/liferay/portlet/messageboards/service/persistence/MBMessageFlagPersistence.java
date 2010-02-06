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

import com.liferay.portlet.messageboards.model.MBMessageFlag;

/**
 * <a href="MBMessageFlagPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageFlagPersistenceImpl
 * @see       MBMessageFlagUtil
 * @generated
 */
public interface MBMessageFlagPersistence extends BasePersistence<MBMessageFlag> {
	public void cacheResult(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag);

	public void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> mbMessageFlags);

	public com.liferay.portlet.messageboards.model.MBMessageFlag create(
		long messageFlagId);

	public com.liferay.portlet.messageboards.model.MBMessageFlag remove(
		long messageFlagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag updateImpl(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByPrimaryKey(
		long messageFlagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag fetchByPrimaryKey(
		long messageFlagId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByUserId(
		long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag[] findByUserId_PrevAndNext(
		long messageFlagId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByThreadId(
		long threadId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByThreadId(
		long threadId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByThreadId_First(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByThreadId_Last(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag[] findByThreadId_PrevAndNext(
		long messageFlagId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByMessageId(
		long messageId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByMessageId(
		long messageId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByMessageId(
		long messageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByMessageId_First(
		long messageId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByMessageId_Last(
		long messageId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag[] findByMessageId_PrevAndNext(
		long messageFlagId, long messageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByT_F(
		long threadId, int flag) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByT_F(
		long threadId, int flag, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByT_F(
		long threadId, int flag, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByT_F_First(
		long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByT_F_Last(
		long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag[] findByT_F_PrevAndNext(
		long messageFlagId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByM_F(
		long messageId, int flag) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByM_F(
		long messageId, int flag, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByM_F(
		long messageId, int flag, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByM_F_First(
		long messageId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByM_F_Last(
		long messageId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag[] findByM_F_PrevAndNext(
		long messageFlagId, long messageId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByU_T_F(
		long userId, long threadId, int flag)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByU_T_F(
		long userId, long threadId, int flag, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findByU_T_F(
		long userId, long threadId, int flag, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByU_T_F_First(
		long userId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByU_T_F_Last(
		long userId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag[] findByU_T_F_PrevAndNext(
		long messageFlagId, long userId, long threadId, int flag,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag findByU_M_F(
		long userId, long messageId, int flag)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag fetchByU_M_F(
		long userId, long messageId, int flag)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag fetchByU_M_F(
		long userId, long messageId, int flag, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public void removeByThreadId(long threadId)
		throws com.liferay.portal.SystemException;

	public void removeByMessageId(long messageId)
		throws com.liferay.portal.SystemException;

	public void removeByT_F(long threadId, int flag)
		throws com.liferay.portal.SystemException;

	public void removeByM_F(long messageId, int flag)
		throws com.liferay.portal.SystemException;

	public void removeByU_T_F(long userId, long threadId, int flag)
		throws com.liferay.portal.SystemException;

	public void removeByU_M_F(long userId, long messageId, int flag)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageFlagException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public int countByThreadId(long threadId)
		throws com.liferay.portal.SystemException;

	public int countByMessageId(long messageId)
		throws com.liferay.portal.SystemException;

	public int countByT_F(long threadId, int flag)
		throws com.liferay.portal.SystemException;

	public int countByM_F(long messageId, int flag)
		throws com.liferay.portal.SystemException;

	public int countByU_T_F(long userId, long threadId, int flag)
		throws com.liferay.portal.SystemException;

	public int countByU_M_F(long userId, long messageId, int flag)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}