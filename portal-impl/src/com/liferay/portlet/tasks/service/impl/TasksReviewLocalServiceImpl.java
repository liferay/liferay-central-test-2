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

package com.liferay.portlet.tasks.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.tasks.NoSuchReviewException;
import com.liferay.portlet.tasks.TasksActivityKeys;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.model.TasksReview;
import com.liferay.portlet.tasks.service.base.TasksReviewLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

/**
 * <a href="TasksReviewLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TasksReviewLocalServiceImpl extends TasksReviewLocalServiceBaseImpl {

	public TasksReview addReview(
			long userId, long groupId, long assigningUserId,
			String assigningUserName, long proposalId, int stage,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addReview(
			userId, groupId, assigningUserId, assigningUserName, proposalId,
			stage, new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public TasksReview addReview(
			long userId, long groupId, long assigningUserId,
			String assigningUserName, long proposalId, int stage,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addReview(
			userId, groupId, assigningUserId, assigningUserName, proposalId,
			stage, null, null, communityPermissions, guestPermissions);
	}

	public TasksReview addReview(
			long userId, long groupId, long assigningUserId,
			String assigningUserName, long proposalId, int stage,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Review

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long id = counterLocalService.increment();

		TasksReview review = tasksReviewPersistence.create(id);

		review.setGroupId(groupId);
		review.setCompanyId(user.getCompanyId());
		review.setUserId(user.getUserId());
		review.setUserName(user.getFullName());
		review.setCreateDate(now);
		review.setModifiedDate(now);
		review.setAssigningUserId(assigningUserId);
		review.setAssigningUserName(assigningUserName);
		review.setProposalId(proposalId);
		review.setStage(stage);
		review.setCompleted(false);
		review.setRejected(false);

		tasksReviewPersistence.update(review);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addReviewResources(
				review, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addReviewResources(
				review, communityPermissions, guestPermissions);
		}

		// ActivityTracker

		JSONObject extraData = new JSONObject();

		extraData.put("stage", review.getStage());
		extraData.put("completed", review.getCompleted());
		extraData.put("rejected", review.getRejected());

		activityTrackerLocalService.addActivityTracker(
			assigningUserId, groupId, TasksProposal.class.getName(),
			review.getProposalId(), TasksActivityKeys.ASSIGN,
			extraData.toString(), user.getUserId());

		return review;
	}

	public void addReviewResources(
			long proposalId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		TasksReview review =
			tasksReviewPersistence.findByPrimaryKey(proposalId);

		addReviewResources(
			review, addCommunityPermissions, addGuestPermissions);
	}

	public void addReviewResources(
			TasksReview review, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			review.getCompanyId(), review.getGroupId(),
			review.getUserId(), TasksProposal.class.getName(),
			review.getProposalId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addReviewResources(
			long proposalId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		TasksReview review =
			tasksReviewPersistence.findByPrimaryKey(proposalId);

		addReviewResources(
				review, communityPermissions, guestPermissions);
	}

	public void addReviewResources(
			TasksReview review, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			review.getCompanyId(), review.getGroupId(),
			review.getUserId(), TasksReview.class.getName(),
			review.getProposalId(), communityPermissions, guestPermissions);
	}

	public TasksReview rejectReview(
			long userId, long proposalId, int stage, boolean rejected)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		TasksReview review =
			tasksReviewPersistence.findByP_U(proposalId, userId);

		review.setModifiedDate(now);
		review.setCompleted(true);
		review.setRejected(rejected);

		tasksReviewPersistence.update(review, true);

		// ActivityTracker

		JSONObject extraData = new JSONObject();

		extraData.put("stage", review.getStage());
		extraData.put("completed", review.getCompleted());
		extraData.put("rejected", review.getRejected());

		activityTrackerLocalService.addActivityTracker(
			review.getUserId(), review.getGroupId(),
			TasksProposal.class.getName(), review.getProposalId(),
			TasksActivityKeys.REVIEW, extraData.toString(),
			review.getAssigningUserId());

		return review;
	}

	public void deleteReview(long reviewId)
		throws PortalException, SystemException {

		tasksReviewPersistence.remove(reviewId);
	}

	public void deleteReviews(long proposalId)
		throws PortalException, SystemException {

		List reviews = tasksReviewPersistence.findByProposalId(proposalId);

		for (int i = 0; i < reviews.size(); i++) {
			TasksReview review = (TasksReview)reviews.get(i);

			tasksReviewPersistence.remove(review.getReviewId());
		}
	}

	public TasksReview getReview(long reviewId)
		throws PortalException, SystemException {

		return tasksReviewPersistence.findByPrimaryKey(reviewId);
	}

	public TasksReview getReview(long proposalId, long userId)
		throws PortalException, SystemException {

		return tasksReviewPersistence.findByP_U(proposalId, userId);
	}

	public List getReviews(long proposalId)
		throws PortalException, SystemException {

		return tasksReviewPersistence.findByProposalId(proposalId);
	}

	public List getReviews(long proposalId, int stage)
		throws PortalException, SystemException {

		return tasksReviewPersistence.findByP_S(proposalId, stage);
	}

	public List getReviews(long proposalId, int stage, boolean completed)
		throws PortalException, SystemException {

		return tasksReviewPersistence.findByP_S_C(proposalId, stage, completed);
	}

	public List getReviews(long proposalId, int stage, int begin, int end)
		throws PortalException, SystemException {

		return tasksReviewPersistence.findByP_S(proposalId, stage, begin, end);
	}

	public List getReviews(
			long proposalId, int stage, boolean completed, boolean rejected)
		throws PortalException, SystemException {

		return tasksReviewPersistence.findByP_S_C_R(
			proposalId, stage, completed, rejected);
	}

	public List getReviews(
			long proposalId, int stage, boolean completed, int begin, int end)
		throws PortalException, SystemException {

		return tasksReviewPersistence.findByP_S_C(
			proposalId, stage, completed, begin, end);
	}

	public List getReviews(
			long proposalId, int stage, boolean completed, boolean rejected,
			int begin, int end)
		throws PortalException, SystemException {

		return tasksReviewPersistence.findByP_S_C_R(
			proposalId, stage, completed, rejected, begin, end);
	}

	public int getReviewsCount(long proposalId)
		throws PortalException, SystemException {

		return tasksReviewPersistence.countByProposalId(proposalId);
	}

	public int getReviewsCount(long proposalId, int stage)
		throws PortalException, SystemException {

		return tasksReviewPersistence.countByP_S(proposalId, stage);
	}

	public int getReviewsCount(long proposalId, int stage, boolean completed)
		throws PortalException, SystemException {

		return tasksReviewPersistence.countByP_S_C(
			proposalId, stage, completed);
	}

	public int getReviewsCount(
			long proposalId, int stage, boolean completed, boolean rejected)
		throws PortalException, SystemException {

		return tasksReviewPersistence.countByP_S_C_R(
			proposalId, stage, completed, rejected);
	}

	public void updateReviewers(
			long userId, long groupId, long proposalId, int stage,
			long[] reviewerIds, long[] removeReviewerIds)
		throws PortalException, SystemException {

		User assigningUser = userLocalService.getUserById(userId);

		for (int i = 0; i < removeReviewerIds.length; i++) {
			try {
				tasksReviewPersistence.removeByP_U(
					proposalId, removeReviewerIds[i]);
			}
			catch (NoSuchReviewException nsre) {
			}
		}

		for (int i = 0; i < reviewerIds.length; i++) {
			try {
				tasksReviewPersistence.findByP_U(proposalId, reviewerIds[i]);
			}
			catch (Exception e) {
				User assignedUser =
					userLocalService.getUserById(reviewerIds[i]);

				addReview(
					assignedUser.getUserId(), groupId,
					assigningUser.getUserId(), assigningUser.getFullName(),
					proposalId, stage, true, true);
			}
		}
	}

}