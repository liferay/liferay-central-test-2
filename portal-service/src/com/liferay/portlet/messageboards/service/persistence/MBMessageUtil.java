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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.messageboards.model.MBMessage;

import java.util.List;

/**
 * <a href="MBMessageUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessagePersistence
 * @see       MBMessagePersistenceImpl
 * @generated
 */
public class MBMessageUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static MBMessage remove(MBMessage mbMessage)
		throws SystemException {
		return getPersistence().remove(mbMessage);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static MBMessage update(MBMessage mbMessage, boolean merge)
		throws SystemException {
		return getPersistence().update(mbMessage, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage) {
		getPersistence().cacheResult(mbMessage);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBMessage> mbMessages) {
		getPersistence().cacheResult(mbMessages);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage create(
		long messageId) {
		return getPersistence().create(messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage remove(
		long messageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().remove(messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage updateImpl(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mbMessage, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByPrimaryKey(
		long messageId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByPrimaryKey(messageId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage fetchByPrimaryKey(
		long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(messageId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByUuid_PrevAndNext(
		long messageId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByUuid_PrevAndNext(messageId, uuid, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByGroupId_PrevAndNext(
		long messageId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(messageId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByCompanyId_PrevAndNext(
		long messageId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(messageId, companyId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadId(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByThreadId(threadId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadId(
		long threadId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByThreadId(threadId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadId(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByThreadId(threadId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByThreadId_First(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByThreadId_First(threadId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByThreadId_Last(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByThreadId_Last(threadId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByThreadId_PrevAndNext(
		long messageId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByThreadId_PrevAndNext(messageId, threadId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadReplies(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByThreadReplies(threadId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadReplies(
		long threadId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByThreadReplies(threadId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByThreadReplies(
		long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByThreadReplies(threadId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByThreadReplies_First(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByThreadReplies_First(threadId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByThreadReplies_Last(
		long threadId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByThreadReplies_Last(threadId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByThreadReplies_PrevAndNext(
		long messageId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByThreadReplies_PrevAndNext(messageId, threadId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByG_U_First(groupId, userId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByG_U_Last(groupId, userId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByG_U_PrevAndNext(
		long messageId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_U_PrevAndNext(messageId, groupId, userId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, categoryId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C(
		long groupId, long categoryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, categoryId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C(
		long groupId, long categoryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, categoryId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_C_First(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByG_C_First(groupId, categoryId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_C_Last(
		long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByG_C_Last(groupId, categoryId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByG_C_PrevAndNext(
		long messageId, long groupId, long categoryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_PrevAndNext(messageId, groupId, categoryId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_S(
		long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_S(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_S(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_S(groupId, status, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_S_First(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByG_S_First(groupId, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_S_Last(
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByG_S_Last(groupId, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByG_S_PrevAndNext(
		long messageId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_S_PrevAndNext(messageId, groupId, status, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_S(
		long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_S(companyId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_S(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_S(companyId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_S(companyId, status, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByC_S_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByC_S_First(companyId, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByC_S_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByC_S_Last(companyId, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByC_S_PrevAndNext(
		long messageId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_S_PrevAndNext(messageId, companyId, status, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByC_C_First(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByC_C_Last(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByC_C_PrevAndNext(
		long messageId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_C_PrevAndNext(messageId, classNameId, classPK, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_P(
		long threadId, long parentMessageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_P(threadId, parentMessageId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_P(
		long threadId, long parentMessageId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_P(threadId, parentMessageId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_P(
		long threadId, long parentMessageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByT_P(threadId, parentMessageId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByT_P_First(
		long threadId, long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByT_P_First(threadId, parentMessageId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByT_P_Last(
		long threadId, long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByT_P_Last(threadId, parentMessageId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByT_P_PrevAndNext(
		long messageId, long threadId, long parentMessageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByT_P_PrevAndNext(messageId, threadId, parentMessageId,
			obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_S(
		long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_S(threadId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_S(
		long threadId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_S(threadId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByT_S(
		long threadId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByT_S(threadId, status, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByT_S_First(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByT_S_First(threadId, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByT_S_Last(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByT_S_Last(threadId, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByT_S_PrevAndNext(
		long messageId, long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByT_S_PrevAndNext(messageId, threadId, status, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByTR_S(
		long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTR_S(threadId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByTR_S(
		long threadId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTR_S(threadId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByTR_S(
		long threadId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTR_S(threadId, status, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByTR_S_First(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByTR_S_First(threadId, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByTR_S_Last(
		long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByTR_S_Last(threadId, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByTR_S_PrevAndNext(
		long messageId, long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByTR_S_PrevAndNext(messageId, threadId, status, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_U_S(
		long groupId, long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U_S(groupId, userId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_U_S(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U_S(groupId, userId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_U_S(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U_S(groupId, userId, status, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_U_S_First(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByG_U_S_First(groupId, userId, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_U_S_Last(
		long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence().findByG_U_S_Last(groupId, userId, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByG_U_S_PrevAndNext(
		long messageId, long groupId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_U_S_PrevAndNext(messageId, groupId, userId, status,
			obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_T(
		long groupId, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_T(groupId, categoryId, threadId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_T(
		long groupId, long categoryId, long threadId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_T(groupId, categoryId, threadId, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_T(
		long groupId, long categoryId, long threadId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_T(groupId, categoryId, threadId, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_C_T_First(
		long groupId, long categoryId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_First(groupId, categoryId, threadId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_C_T_Last(
		long groupId, long categoryId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_Last(groupId, categoryId, threadId, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByG_C_T_PrevAndNext(
		long messageId, long groupId, long categoryId, long threadId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_PrevAndNext(messageId, groupId, categoryId,
			threadId, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_S(
		long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_S(groupId, categoryId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_S(groupId, categoryId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_S(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_S(groupId, categoryId, status, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_C_S_First(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_S_First(groupId, categoryId, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_C_S_Last(
		long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_S_Last(groupId, categoryId, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByG_C_S_PrevAndNext(
		long messageId, long groupId, long categoryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_S_PrevAndNext(messageId, groupId, categoryId,
			status, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C_S(
		long classNameId, long classPK, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C_S(classNameId, classPK, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C_S(
		long classNameId, long classPK, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_S(classNameId, classPK, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByC_C_S(
		long classNameId, long classPK, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C_S(classNameId, classPK, status, start, end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByC_C_S_First(
		long classNameId, long classPK, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_C_S_First(classNameId, classPK, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByC_C_S_Last(
		long classNameId, long classPK, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_C_S_Last(classNameId, classPK, status, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByC_C_S_PrevAndNext(
		long messageId, long classNameId, long classPK, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByC_C_S_PrevAndNext(messageId, classNameId, classPK,
			status, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_T_S(
		long groupId, long categoryId, long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_T_S(groupId, categoryId, threadId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_T_S(
		long groupId, long categoryId, long threadId, int status, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_T_S(groupId, categoryId, threadId, status, start,
			end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findByG_C_T_S(
		long groupId, long categoryId, long threadId, int status, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_T_S(groupId, categoryId, threadId, status, start,
			end, obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_C_T_S_First(
		long groupId, long categoryId, long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_S_First(groupId, categoryId, threadId, status,
			obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage findByG_C_T_S_Last(
		long groupId, long categoryId, long threadId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_S_Last(groupId, categoryId, threadId, status,
			obc);
	}

	public static com.liferay.portlet.messageboards.model.MBMessage[] findByG_C_T_S_PrevAndNext(
		long messageId, long groupId, long categoryId, long threadId,
		int status, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		return getPersistence()
				   .findByG_C_T_S_PrevAndNext(messageId, groupId, categoryId,
			threadId, status, obc);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessage> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMessageException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByThreadId(threadId);
	}

	public static void removeByThreadReplies(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByThreadReplies(threadId);
	}

	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_U(groupId, userId);
	}

	public static void removeByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C(groupId, categoryId);
	}

	public static void removeByG_S(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_S(groupId, status);
	}

	public static void removeByC_S(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_S(companyId, status);
	}

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeByT_P(long threadId, long parentMessageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByT_P(threadId, parentMessageId);
	}

	public static void removeByT_S(long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByT_S(threadId, status);
	}

	public static void removeByTR_S(long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByTR_S(threadId, status);
	}

	public static void removeByG_U_S(long groupId, long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_U_S(groupId, userId, status);
	}

	public static void removeByG_C_T(long groupId, long categoryId,
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_T(groupId, categoryId, threadId);
	}

	public static void removeByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_S(groupId, categoryId, status);
	}

	public static void removeByC_C_S(long classNameId, long classPK, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C_S(classNameId, classPK, status);
	}

	public static void removeByG_C_T_S(long groupId, long categoryId,
		long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_T_S(groupId, categoryId, threadId, status);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByThreadId(threadId);
	}

	public static int countByThreadReplies(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByThreadReplies(threadId);
	}

	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	public static int countByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C(groupId, categoryId);
	}

	public static int countByG_S(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_S(groupId, status);
	}

	public static int countByC_S(long companyId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_S(companyId, status);
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countByT_P(long threadId, long parentMessageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_P(threadId, parentMessageId);
	}

	public static int countByT_S(long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_S(threadId, status);
	}

	public static int countByTR_S(long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByTR_S(threadId, status);
	}

	public static int countByG_U_S(long groupId, long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U_S(groupId, userId, status);
	}

	public static int countByG_C_T(long groupId, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_T(groupId, categoryId, threadId);
	}

	public static int countByG_C_S(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_S(groupId, categoryId, status);
	}

	public static int countByC_C_S(long classNameId, long classPK, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C_S(classNameId, classPK, status);
	}

	public static int countByG_C_T_S(long groupId, long categoryId,
		long threadId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_C_T_S(groupId, categoryId, threadId, status);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MBMessagePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MBMessagePersistence)PortalBeanLocatorUtil.locate(MBMessagePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(MBMessagePersistence persistence) {
		_persistence = persistence;
	}

	private static MBMessagePersistence _persistence;
}