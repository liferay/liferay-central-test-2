/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

public class MembershipRequestUtil {
	public static void cacheResult(
		com.liferay.portal.model.MembershipRequest membershipRequest) {
		getPersistence().cacheResult(membershipRequest);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.MembershipRequest> membershipRequests) {
		getPersistence().cacheResult(membershipRequests);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portal.model.MembershipRequest create(
		long membershipRequestId) {
		return getPersistence().create(membershipRequestId);
	}

	public static com.liferay.portal.model.MembershipRequest remove(
		long membershipRequestId)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(membershipRequestId);
	}

	public static com.liferay.portal.model.MembershipRequest remove(
		com.liferay.portal.model.MembershipRequest membershipRequest)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(membershipRequest);
	}

	/**
	 * @deprecated Use <code>update(MembershipRequest membershipRequest, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.MembershipRequest update(
		com.liferay.portal.model.MembershipRequest membershipRequest)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(membershipRequest);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        membershipRequest the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when membershipRequest is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portal.model.MembershipRequest update(
		com.liferay.portal.model.MembershipRequest membershipRequest,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(membershipRequest, merge);
	}

	public static com.liferay.portal.model.MembershipRequest updateImpl(
		com.liferay.portal.model.MembershipRequest membershipRequest,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(membershipRequest, merge);
	}

	public static com.liferay.portal.model.MembershipRequest findByPrimaryKey(
		long membershipRequestId)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(membershipRequestId);
	}

	public static com.liferay.portal.model.MembershipRequest fetchByPrimaryKey(
		long membershipRequestId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(membershipRequestId);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest[] findByGroupId_PrevAndNext(
		long membershipRequestId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(membershipRequestId, groupId, obc);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByUserId(
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, start, end, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest[] findByUserId_PrevAndNext(
		long membershipRequestId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByUserId_PrevAndNext(membershipRequestId, userId, obc);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByG_S(
		long groupId, int statusId) throws com.liferay.portal.SystemException {
		return getPersistence().findByG_S(groupId, statusId);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByG_S(
		long groupId, int statusId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_S(groupId, statusId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findByG_S(
		long groupId, int statusId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_S(groupId, statusId, start, end, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByG_S_First(
		long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException {
		return getPersistence().findByG_S_First(groupId, statusId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest findByG_S_Last(
		long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException {
		return getPersistence().findByG_S_Last(groupId, statusId, obc);
	}

	public static com.liferay.portal.model.MembershipRequest[] findByG_S_PrevAndNext(
		long membershipRequestId, long groupId, int statusId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipRequestException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_S_PrevAndNext(membershipRequestId, groupId,
			statusId, obc);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByG_S(long groupId, int statusId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_S(groupId, statusId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByG_S(long groupId, int statusId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_S(groupId, statusId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static MembershipRequestPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(MembershipRequestPersistence persistence) {
		_persistence = persistence;
	}

	private static MembershipRequestPersistence _persistence;
}