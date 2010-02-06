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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.social.model.SocialRequest;

/**
 * <a href="SocialRequestPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRequestPersistenceImpl
 * @see       SocialRequestUtil
 * @generated
 */
public interface SocialRequestPersistence extends BasePersistence<SocialRequest> {
	public void cacheResult(
		com.liferay.portlet.social.model.SocialRequest socialRequest);

	public void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialRequest> socialRequests);

	public com.liferay.portlet.social.model.SocialRequest create(long requestId);

	public com.liferay.portlet.social.model.SocialRequest remove(long requestId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest updateImpl(
		com.liferay.portlet.social.model.SocialRequest socialRequest,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.social.model.SocialRequest findByPrimaryKey(
		long requestId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest fetchByPrimaryKey(
		long requestId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.social.model.SocialRequest findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest[] findByUuid_PrevAndNext(
		long requestId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.social.model.SocialRequest fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.social.model.SocialRequest findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest[] findByCompanyId_PrevAndNext(
		long requestId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByUserId(
		long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.social.model.SocialRequest findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest[] findByUserId_PrevAndNext(
		long requestId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByReceiverUserId(
		long receiverUserId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByReceiverUserId(
		long receiverUserId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByReceiverUserId(
		long receiverUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.social.model.SocialRequest findByReceiverUserId_First(
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest findByReceiverUserId_Last(
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest[] findByReceiverUserId_PrevAndNext(
		long requestId, long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByU_S(
		long userId, int status) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByU_S(
		long userId, int status, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByU_S(
		long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.social.model.SocialRequest findByU_S_First(
		long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest findByU_S_Last(
		long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest[] findByU_S_PrevAndNext(
		long requestId, long userId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByR_S(
		long receiverUserId, int status)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByR_S(
		long receiverUserId, int status, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByR_S(
		long receiverUserId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.social.model.SocialRequest findByR_S_First(
		long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest findByR_S_Last(
		long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest[] findByR_S_PrevAndNext(
		long requestId, long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest findByU_C_C_T_R(
		long userId, long classNameId, long classPK, int type,
		long receiverUserId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest fetchByU_C_C_T_R(
		long userId, long classNameId, long classPK, int type,
		long receiverUserId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.social.model.SocialRequest fetchByU_C_C_T_R(
		long userId, long classNameId, long classPK, int type,
		long receiverUserId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByU_C_C_T_S(
		long userId, long classNameId, long classPK, int type, int status)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByU_C_C_T_S(
		long userId, long classNameId, long classPK, int type, int status,
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByU_C_C_T_S(
		long userId, long classNameId, long classPK, int type, int status,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.social.model.SocialRequest findByU_C_C_T_S_First(
		long userId, long classNameId, long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest findByU_C_C_T_S_Last(
		long userId, long classNameId, long classPK, int type, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest[] findByU_C_C_T_S_PrevAndNext(
		long requestId, long userId, long classNameId, long classPK, int type,
		int status, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByC_C_T_R_S(
		long classNameId, long classPK, int type, long receiverUserId,
		int status) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByC_C_T_R_S(
		long classNameId, long classPK, int type, long receiverUserId,
		int status, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findByC_C_T_R_S(
		long classNameId, long classPK, int type, long receiverUserId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.social.model.SocialRequest findByC_C_T_R_S_First(
		long classNameId, long classPK, int type, long receiverUserId,
		int status, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest findByC_C_T_R_S_Last(
		long classNameId, long classPK, int type, long receiverUserId,
		int status, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public com.liferay.portlet.social.model.SocialRequest[] findByC_C_T_R_S_PrevAndNext(
		long requestId, long classNameId, long classPK, int type,
		long receiverUserId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.social.model.SocialRequest> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public void removeByReceiverUserId(long receiverUserId)
		throws com.liferay.portal.SystemException;

	public void removeByU_S(long userId, int status)
		throws com.liferay.portal.SystemException;

	public void removeByR_S(long receiverUserId, int status)
		throws com.liferay.portal.SystemException;

	public void removeByU_C_C_T_R(long userId, long classNameId, long classPK,
		int type, long receiverUserId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchRequestException;

	public void removeByU_C_C_T_S(long userId, long classNameId, long classPK,
		int type, int status) throws com.liferay.portal.SystemException;

	public void removeByC_C_T_R_S(long classNameId, long classPK, int type,
		long receiverUserId, int status)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public int countByReceiverUserId(long receiverUserId)
		throws com.liferay.portal.SystemException;

	public int countByU_S(long userId, int status)
		throws com.liferay.portal.SystemException;

	public int countByR_S(long receiverUserId, int status)
		throws com.liferay.portal.SystemException;

	public int countByU_C_C_T_R(long userId, long classNameId, long classPK,
		int type, long receiverUserId)
		throws com.liferay.portal.SystemException;

	public int countByU_C_C_T_S(long userId, long classNameId, long classPK,
		int type, int status) throws com.liferay.portal.SystemException;

	public int countByC_C_T_R_S(long classNameId, long classPK, int type,
		long receiverUserId, int status)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}