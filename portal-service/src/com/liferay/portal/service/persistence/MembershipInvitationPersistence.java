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

/**
 * <a href="MembershipInvitationPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface MembershipInvitationPersistence extends BasePersistence {
	public void cacheResult(
		com.liferay.portal.model.MembershipInvitation membershipInvitation);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.MembershipInvitation> membershipInvitations);

	public com.liferay.portal.model.MembershipInvitation create(
		long membershipRequestId);

	public com.liferay.portal.model.MembershipInvitation remove(
		long membershipRequestId)
		throws com.liferay.portal.NoSuchMembershipInvitationException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipInvitation remove(
		com.liferay.portal.model.MembershipInvitation membershipInvitation)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(MembershipInvitation membershipInvitation, boolean merge)</code>.
	 */
	public com.liferay.portal.model.MembershipInvitation update(
		com.liferay.portal.model.MembershipInvitation membershipInvitation)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        membershipInvitation the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when membershipInvitation is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portal.model.MembershipInvitation update(
		com.liferay.portal.model.MembershipInvitation membershipInvitation,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipInvitation updateImpl(
		com.liferay.portal.model.MembershipInvitation membershipInvitation,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipInvitation findByPrimaryKey(
		long membershipRequestId)
		throws com.liferay.portal.NoSuchMembershipInvitationException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipInvitation fetchByPrimaryKey(
		long membershipRequestId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipInvitation> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipInvitation> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipInvitation> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipInvitation findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipInvitationException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipInvitation findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipInvitationException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipInvitation[] findByGroupId_PrevAndNext(
		long membershipRequestId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipInvitationException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipInvitation findByKey(
		java.lang.String key)
		throws com.liferay.portal.NoSuchMembershipInvitationException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipInvitation fetchByKey(
		java.lang.String key) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipInvitation> findByUserId(
		long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipInvitation> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipInvitation> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipInvitation findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipInvitationException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipInvitation findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipInvitationException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.MembershipInvitation[] findByUserId_PrevAndNext(
		long membershipRequestId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchMembershipInvitationException,
			com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipInvitation> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipInvitation> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.MembershipInvitation> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByKey(java.lang.String key)
		throws com.liferay.portal.NoSuchMembershipInvitationException,
			com.liferay.portal.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByKey(java.lang.String key)
		throws com.liferay.portal.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}