/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * <a href="ActivityTrackerPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface ActivityTrackerPersistence {
	public com.liferay.portal.model.ActivityTracker create(
		long activityTrackerId);

	public com.liferay.portal.model.ActivityTracker remove(
		long activityTrackerId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public com.liferay.portal.model.ActivityTracker remove(
		com.liferay.portal.model.ActivityTracker activityTracker)
		throws com.liferay.portal.SystemException;

	/**
	 * @deprecated Use <code>update(ActivityTracker activityTracker, boolean merge)</code>.
	 */
	public com.liferay.portal.model.ActivityTracker update(
		com.liferay.portal.model.ActivityTracker activityTracker)
		throws com.liferay.portal.SystemException;

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        activityTracker the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when activityTracker is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public com.liferay.portal.model.ActivityTracker update(
		com.liferay.portal.model.ActivityTracker activityTracker, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ActivityTracker updateImpl(
		com.liferay.portal.model.ActivityTracker activityTracker, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ActivityTracker findByPrimaryKey(
		long activityTrackerId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public com.liferay.portal.model.ActivityTracker fetchByPrimaryKey(
		long activityTrackerId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByGroupId(
		long groupId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByGroupId(
		long groupId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ActivityTracker findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public com.liferay.portal.model.ActivityTracker findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public com.liferay.portal.model.ActivityTracker[] findByGroupId_PrevAndNext(
		long activityTrackerId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByCompanyId(
		long companyId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByCompanyId(
		long companyId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ActivityTracker findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public com.liferay.portal.model.ActivityTracker findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public com.liferay.portal.model.ActivityTracker[] findByCompanyId_PrevAndNext(
		long activityTrackerId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByUserId(
		long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByUserId(
		long userId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByUserId(
		long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ActivityTracker findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public com.liferay.portal.model.ActivityTracker findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public com.liferay.portal.model.ActivityTracker[] findByUserId_PrevAndNext(
		long activityTrackerId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByReceiverUserId(
		long receiverUserId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByReceiverUserId(
		long receiverUserId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByReceiverUserId(
		long receiverUserId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ActivityTracker findByReceiverUserId_First(
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public com.liferay.portal.model.ActivityTracker findByReceiverUserId_Last(
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public com.liferay.portal.model.ActivityTracker[] findByReceiverUserId_PrevAndNext(
		long activityTrackerId, long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByC_C(
		long classNameId, long classPK, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findByC_C(
		long classNameId, long classPK, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.ActivityTracker findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public com.liferay.portal.model.ActivityTracker findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public com.liferay.portal.model.ActivityTracker[] findByC_C_PrevAndNext(
		long activityTrackerId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchActivityTrackerException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findAll(
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.ActivityTracker> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public void removeByReceiverUserId(long receiverUserId)
		throws com.liferay.portal.SystemException;

	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public int countByReceiverUserId(long receiverUserId)
		throws com.liferay.portal.SystemException;

	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}