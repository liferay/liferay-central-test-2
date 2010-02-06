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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.MembershipRequest;

/**
 * <a href="MembershipRequestPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MembershipRequestPersistenceImpl
 * @see       MembershipRequestUtil
 * @generated
 */
public interface MembershipRequestPersistence extends BasePersistence<MembershipRequest> {
	public void cacheResult(
		com.liferay.portal.model.MembershipRequest membershipRequest);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.MembershipRequest> membershipRequests);

	public com.liferay.portal.model.MembershipRequest create(
		long membershipRequestId);

	public com.liferay.portal.model.MembershipRequest remove(
		long membershipRequestId)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipRequest updateImpl(
		com.liferay.portal.model.MembershipRequest membershipRequest,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipRequest findByPrimaryKey(
		long membershipRequestId)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipRequest fetchByPrimaryKey(
		long membershipRequestId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipRequest> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipRequest> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipRequest> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipRequest findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipRequest findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipRequest[] findByGroupId_PrevAndNext(
		long membershipRequestId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipRequest> findByUserId(
		long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipRequest> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipRequest> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipRequest findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipRequest findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipRequest[] findByUserId_PrevAndNext(
		long membershipRequestId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipRequest> findByG_S(
		long groupId, int statusId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipRequest> findByG_S(
		long groupId, int statusId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipRequest> findByG_S(
		long groupId, int statusId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipRequest findByG_S_First(
		long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipRequest findByG_S_Last(
		long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipRequest[] findByG_S_PrevAndNext(
		long membershipRequestId, long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipRequest> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipRequest> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipRequest> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public void removeByG_S(long groupId, int statusId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public int countByG_S(long groupId, int statusId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}