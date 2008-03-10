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

package com.liferay.portlet.tasks.service.persistence;

/**
 * <a href="TasksReviewUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TasksReviewUtil {
	public static com.liferay.portlet.tasks.model.TasksReview create(
		long reviewId) {
		return getPersistence().create(reviewId);
	}

	public static com.liferay.portlet.tasks.model.TasksReview remove(
		long reviewId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().remove(reviewId);
	}

	public static com.liferay.portlet.tasks.model.TasksReview remove(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(tasksReview);
	}

	public static com.liferay.portlet.tasks.model.TasksReview update(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(tasksReview);
	}

	public static com.liferay.portlet.tasks.model.TasksReview update(
		com.liferay.portlet.tasks.model.TasksReview tasksReview, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(tasksReview, merge);
	}

	public static com.liferay.portlet.tasks.model.TasksReview updateImpl(
		com.liferay.portlet.tasks.model.TasksReview tasksReview, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(tasksReview, merge);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByPrimaryKey(
		long reviewId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByPrimaryKey(reviewId);
	}

	public static com.liferay.portlet.tasks.model.TasksReview fetchByPrimaryKey(
		long reviewId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(reviewId);
	}

	public static java.util.List findByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List findByUserId(long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List findByUserId(long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview[] findByUserId_PrevAndNext(
		long reviewId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByUserId_PrevAndNext(reviewId, userId, obc);
	}

	public static java.util.List findByProposalId(long proposalId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByProposalId(proposalId);
	}

	public static java.util.List findByProposalId(long proposalId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByProposalId(proposalId, begin, end);
	}

	public static java.util.List findByProposalId(long proposalId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByProposalId(proposalId, begin, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByProposalId_First(
		long proposalId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByProposalId_First(proposalId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByProposalId_Last(
		long proposalId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByProposalId_Last(proposalId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview[] findByProposalId_PrevAndNext(
		long reviewId, long proposalId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByProposalId_PrevAndNext(reviewId, proposalId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByU_P(
		long userId, long proposalId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByU_P(userId, proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksReview fetchByU_P(
		long userId, long proposalId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByU_P(userId, proposalId);
	}

	public static java.util.List findByP_S(long proposalId, int stage)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_S(proposalId, stage);
	}

	public static java.util.List findByP_S(long proposalId, int stage,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByP_S(proposalId, stage, begin, end);
	}

	public static java.util.List findByP_S(long proposalId, int stage,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByP_S(proposalId, stage, begin, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_First(
		long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByP_S_First(proposalId, stage, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_Last(
		long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence().findByP_S_Last(proposalId, stage, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview[] findByP_S_PrevAndNext(
		long reviewId, long proposalId, int stage,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_PrevAndNext(reviewId, proposalId, stage, obc);
	}

	public static java.util.List findByP_S_C(long proposalId, int stage,
		boolean completed) throws com.liferay.portal.SystemException {
		return getPersistence().findByP_S_C(proposalId, stage, completed);
	}

	public static java.util.List findByP_S_C(long proposalId, int stage,
		boolean completed, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_S_C(proposalId, stage, completed, begin, end);
	}

	public static java.util.List findByP_S_C(long proposalId, int stage,
		boolean completed, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_S_C(proposalId, stage, completed, begin, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_C_First(
		long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_First(proposalId, stage, completed, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_C_Last(
		long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_Last(proposalId, stage, completed, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview[] findByP_S_C_PrevAndNext(
		long reviewId, long proposalId, int stage, boolean completed,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_PrevAndNext(reviewId, proposalId, stage,
			completed, obc);
	}

	public static java.util.List findByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_S_C_R(proposalId, stage, completed, rejected);
	}

	public static java.util.List findByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_S_C_R(proposalId, stage, completed, rejected,
			begin, end);
	}

	public static java.util.List findByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByP_S_C_R(proposalId, stage, completed, rejected,
			begin, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_C_R_First(
		long proposalId, int stage, boolean completed, boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_R_First(proposalId, stage, completed, rejected,
			obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview findByP_S_C_R_Last(
		long proposalId, int stage, boolean completed, boolean rejected,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_R_Last(proposalId, stage, completed, rejected,
			obc);
	}

	public static com.liferay.portlet.tasks.model.TasksReview[] findByP_S_C_R_PrevAndNext(
		long reviewId, long proposalId, int stage, boolean completed,
		boolean rejected, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		return getPersistence()
				   .findByP_S_C_R_PrevAndNext(reviewId, proposalId, stage,
			completed, rejected, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByProposalId(long proposalId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByProposalId(proposalId);
	}

	public static void removeByU_P(long userId, long proposalId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchReviewException {
		getPersistence().removeByU_P(userId, proposalId);
	}

	public static void removeByP_S(long proposalId, int stage)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByP_S(proposalId, stage);
	}

	public static void removeByP_S_C(long proposalId, int stage,
		boolean completed) throws com.liferay.portal.SystemException {
		getPersistence().removeByP_S_C(proposalId, stage, completed);
	}

	public static void removeByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByP_S_C_R(proposalId, stage, completed, rejected);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByProposalId(long proposalId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByProposalId(proposalId);
	}

	public static int countByU_P(long userId, long proposalId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU_P(userId, proposalId);
	}

	public static int countByP_S(long proposalId, int stage)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByP_S(proposalId, stage);
	}

	public static int countByP_S_C(long proposalId, int stage, boolean completed)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByP_S_C(proposalId, stage, completed);
	}

	public static int countByP_S_C_R(long proposalId, int stage,
		boolean completed, boolean rejected)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .countByP_S_C_R(proposalId, stage, completed, rejected);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static TasksReviewPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(TasksReviewPersistence persistence) {
		_persistence = persistence;
	}

	private static TasksReviewUtil _getUtil() {
		if (_util == null) {
			_util = (TasksReviewUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = TasksReviewUtil.class.getName();
	private static TasksReviewUtil _util;
	private TasksReviewPersistence _persistence;
}