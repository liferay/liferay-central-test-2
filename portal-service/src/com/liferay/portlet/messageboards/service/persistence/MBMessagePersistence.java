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

import com.liferay.portlet.messageboards.model.MBMessage;

/**
 * <a href="MBMessagePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessagePersistenceImpl
 * @see       MBMessageUtil
 * @generated
 */
public interface MBMessagePersistence extends BasePersistence<MBMessage> {
	public void cacheResult(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage);

	public void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBMessage> mbMessages);

	public com.liferay.portlet.messageboards.model.MBMessage create(
		long messageId);

	public com.liferay.portlet.messageboards.model.MBMessage remove(
		long messageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage updateImpl(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByPrimaryKey(
		long messageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage fetchByPrimaryKey(
		long messageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByUuid_PrevAndNext(
		long messageId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByGroupId_PrevAndNext(
		long messageId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByCompanyId_PrevAndNext(
		long messageId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadId(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadId(
		long threadId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByThreadId_First(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByThreadId_Last(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByThreadId_PrevAndNext(
		long messageId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadReplies(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadReplies(
		long threadId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadReplies(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByThreadReplies_First(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByThreadReplies_Last(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByThreadReplies_PrevAndNext(
		long messageId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByG_U_PrevAndNext(
		long messageId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_C_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_C_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByG_C_PrevAndNext(
		long messageId, long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_S(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_S(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByG_S_PrevAndNext(
		long messageId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_S(
		long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_S(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByC_S_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByC_S_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByC_S_PrevAndNext(
		long messageId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByC_C_PrevAndNext(
		long messageId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_P(
		long threadId, long parentMessageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_P(
		long threadId, long parentMessageId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_P(
		long threadId, long parentMessageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByT_P_First(
		long threadId, long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByT_P_Last(
		long threadId, long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByT_P_PrevAndNext(
		long messageId, long threadId, long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_S(
		long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_S(
		long threadId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_S(
		long threadId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByT_S_First(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByT_S_Last(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByT_S_PrevAndNext(
		long messageId, long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByTR_S(
		long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByTR_S(
		long threadId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByTR_S(
		long threadId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByTR_S_First(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByTR_S_Last(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByTR_S_PrevAndNext(
		long messageId, long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_U_S(
		long groupId, long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_U_S(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_U_S(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_U_S_First(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_U_S_Last(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByG_U_S_PrevAndNext(
		long messageId, long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_T(
		long groupId, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_T(
		long groupId, long categoryId, long threadId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_T(
		long groupId, long categoryId, long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_C_T_First(
		long groupId, long categoryId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_C_T_Last(
		long groupId, long categoryId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByG_C_T_PrevAndNext(
		long messageId, long groupId, long categoryId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_S(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_C_S_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_C_S_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByG_C_S_PrevAndNext(
		long messageId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C_S(
		long classNameId, long classPK, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C_S(
		long classNameId, long classPK, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C_S(
		long classNameId, long classPK, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByC_C_S_First(
		long classNameId, long classPK, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByC_C_S_Last(
		long classNameId, long classPK, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByC_C_S_PrevAndNext(
		long messageId, long classNameId, long classPK, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_T_S(
		long groupId, long categoryId, long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_T_S(
		long groupId, long categoryId, long threadId, int status, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_T_S(
		long groupId, long categoryId, long threadId, int status, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_C_T_S_First(
		long groupId, long categoryId, long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage findByG_C_T_S_Last(
		long groupId, long categoryId, long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public com.liferay.portlet.messageboards.model.MBMessage[] findByG_C_T_S_PrevAndNext(
		long messageId, long groupId, long categoryId, long threadId,
		int status, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByThreadReplies(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_S(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_S(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByT_P(long threadId, long parentMessageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByT_S(long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByTR_S(long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_U_S(long groupId, long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_C_T(long groupId, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_C_S(long classNameId, long classPK, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_C_T_S(long groupId, long categoryId, long threadId,
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByThreadReplies(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_S(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_S(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByT_P(long threadId, long parentMessageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByT_S(long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByTR_S(long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_U_S(long groupId, long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_C_T(long groupId, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_C_S(long classNameId, long classPK, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_C_T_S(long groupId, long categoryId, long threadId,
		int status) throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}