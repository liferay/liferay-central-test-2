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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.tasks.NoSuchProposalException;
import com.liferay.portlet.tasks.ProposalDueDateException;
import com.liferay.portlet.tasks.TasksActivityKeys;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.service.base.TasksProposalLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * <a href="TasksProposalLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TasksProposalLocalServiceImpl
	extends TasksProposalLocalServiceBaseImpl {

	public TasksProposal addProposal(
			long userId, long groupId, String name, String description,
			long classNameId, long classPK,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addProposal(
			userId, groupId, name, description, classNameId, classPK, null,
			Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public TasksProposal addProposal(
			long userId, long groupId, String name, String description,
			long classNameId, long classPK, long stageOneReviewerId,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addProposal(
			userId, groupId, name, description, classNameId, classPK,
			new Long(stageOneReviewerId),
			Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public TasksProposal addProposal(
			long userId, long groupId, String name, String description,
			long classNameId, long classPK,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addProposal(
			userId, groupId, name, description, classNameId, classPK, null,
			null, null, communityPermissions, guestPermissions);
	}

	public TasksProposal addProposal(
			long userId, long groupId, String name, String description,
			long classNameId, long classPK, long stageOneReviewerId,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addProposal(
			userId, groupId, name, description, classNameId, classPK,
			new Long(stageOneReviewerId), null, null, communityPermissions,
			guestPermissions);
	}

	public TasksProposal addProposal(
			long userId, long groupId, String name, String description,
			long classNameId, long classPK, Long stageOneReviewerId,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Proposal

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long id = counterLocalService.increment();

		TasksProposal proposal = tasksProposalPersistence.create(id);

		proposal.setGroupId(groupId);
		proposal.setCompanyId(user.getCompanyId());
		proposal.setUserId(user.getUserId());
		proposal.setUserName(user.getFullName());
		proposal.setCreateDate(now);
		proposal.setModifiedDate(now);
		proposal.setClassNameId(classNameId);
		proposal.setClassPK(classPK);
		proposal.setName(name);
		proposal.setDescription(description);

		proposal = tasksProposalPersistence.update(proposal);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addProposalResources(
				proposal, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addProposalResources(
				proposal, communityPermissions, guestPermissions);
		}

		// TasksReview

		if (stageOneReviewerId != null && stageOneReviewerId.longValue() > 0) {
			tasksReviewLocalService.addReview(
				stageOneReviewerId.longValue(), groupId, user.getUserId(),
				user.getFullName(), proposal.getProposalId(), 1, true, true);
		}

		// ActivityTracker

		activityTrackerLocalService.addActivityTracker(
			user.getUserId(), groupId, TasksProposal.class.getName(),
			proposal.getProposalId(), TasksActivityKeys.PROPOSE,
			StringPool.BLANK, 0);

		return proposal;
	}

	public void addProposalResources(
			long proposalId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		TasksProposal proposal =
			tasksProposalPersistence.findByPrimaryKey(proposalId);

		addProposalResources(
			proposal, addCommunityPermissions, addGuestPermissions);
	}

	public void addProposalResources(
			TasksProposal proposal, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			proposal.getCompanyId(), proposal.getGroupId(),
			proposal.getUserId(), TasksProposal.class.getName(),
			proposal.getProposalId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addProposalResources(
			long proposalId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		TasksProposal proposal =
			tasksProposalPersistence.findByPrimaryKey(proposalId);

		addProposalResources(
			proposal, communityPermissions, guestPermissions);
	}

	public void addProposalResources(
			TasksProposal proposal, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			proposal.getCompanyId(), proposal.getGroupId(),
			proposal.getUserId(), TasksProposal.class.getName(),
			proposal.getProposalId(), communityPermissions, guestPermissions);
	}

	public void deleteProposal(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteProposal(classNameId, classPK);
	}

	public void deleteProposal(long classNameId, long classPK)
		throws PortalException, SystemException {

		try {
			TasksProposal proposal = getProposal(classNameId, classPK);

			deleteProposal(proposal);
		}
		catch (NoSuchProposalException nspe) {
		}
	}

	public void deleteProposal(long proposalId)
		throws PortalException, SystemException {

		TasksProposal proposal =
			tasksProposalPersistence.findByPrimaryKey(proposalId);

		deleteProposal(proposal);
	}

	public void deleteProposal(TasksProposal proposal)
		throws PortalException, SystemException {

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			TasksProposal.class.getName(), proposal.getProposalId());

		// Activity Trackers

		activityTrackerLocalService.deleteActivityTrackers(
			TasksProposal.class.getName(), proposal.getProposalId());

		// Reviews

		tasksReviewLocalService.deleteReviews(proposal.getProposalId());

		// Proposal

		tasksProposalPersistence.remove(proposal.getProposalId());
	}

	public void deleteProposals(long companyId, long groupId)
		throws PortalException, SystemException {

		List proposals =
			tasksProposalPersistence.findByC_G(companyId, groupId);

		for (int i = 0; i < proposals.size(); i++) {
			deleteTasksProposal((TasksProposal)proposals.get(i));
		}
	}

	public TasksProposal getProposal(long proposalId)
		throws PortalException, SystemException {

		return tasksProposalPersistence.findByPrimaryKey(proposalId);
	}

	public TasksProposal getProposal(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getProposal(classNameId, classPK);
	}

	public TasksProposal getProposal(long classNameId, long classPK)
		throws PortalException, SystemException {

		return tasksProposalPersistence.findByC_C(classNameId, classPK);
	}

	public List getProposals() throws SystemException {
		return tasksProposalPersistence.findAll();
	}

	public List getProposals(long companyId, long groupId, int begin, int end)
		throws SystemException {

		return tasksProposalPersistence.findByC_G(companyId, groupId, begin, end);
	}

	public int getProposalsCount(long companyId, long groupId)
		throws SystemException {

		return tasksProposalPersistence.countByC_G(companyId, groupId);
	}

	public List getReviewersProposals(
			long companyId, long groupId, long reviewingUserId, int begin,
			int end, OrderByComparator obc)
		throws SystemException {

		return tasksProposalFinder.findByC_G_R(
			companyId, groupId, reviewingUserId, begin, end, obc);
	}

	public int getReviewersProposalsCount(
			long companyId, long groupId, long reviewingUserId)
		throws SystemException {

		return tasksProposalFinder.countByC_G_R(
			companyId, groupId, reviewingUserId);
	}

	public List getUsersProposals(
			long companyId, long groupId, long userId, int begin, int end)
		throws SystemException {

		return tasksProposalPersistence.findByC_G_U(
			companyId, groupId, userId, begin, end);
	}

	public int getUsersProposalsCount(long companyId, long groupId, long userId)
		throws SystemException {

		return tasksProposalPersistence.countByC_G_U(companyId, groupId, userId);
	}

	public List search(
			long companyId, long groupId, String keywords, int begin, int end,
			OrderByComparator obc)
		throws SystemException {

		return tasksProposalFinder.findByKeywords(
			companyId, groupId, keywords, begin, end, obc);
	}

	public List search(
			long companyId, long groupId, String name, String userName,
			boolean andOperator, int begin, int end, OrderByComparator obc)
		throws SystemException {

		return tasksProposalFinder.findByC_G_N_U(
			companyId, groupId, name, userName, andOperator, begin, end, obc);
	}

	public int searchCount(long companyId, long groupId, String keywords)
		throws SystemException {

		return tasksProposalFinder.countByKeywords(
			companyId, groupId, keywords);
	}

	public int searchCount(
			long companyId, long groupId, String name, String userName,
			boolean andOperator)
		throws SystemException {

		return tasksProposalFinder.countByC_G_N_U(
			companyId, groupId, name, userName, andOperator);
	}

	public TasksProposal updateProposal(
			long userId, long proposalId, String description, int dueDateMonth,
			int dueDateDay, int dueDateYear, int dueDateHour, int dueDateMinute)
		throws PortalException, SystemException {

		// Proposal

		User user = userPersistence.findByPrimaryKey(userId);

		Date dueDate = PortalUtil.getDate(
			dueDateMonth, dueDateDay, dueDateYear, dueDateHour, dueDateMinute,
			user.getTimeZone(), new ProposalDueDateException());

		TasksProposal proposal =
			tasksProposalPersistence.findByPrimaryKey(proposalId);

		proposal.setModifiedDate(new Date());
		proposal.setDescription(description);
		proposal.setDueDate(dueDate);

		tasksProposalPersistence.update(proposal);

		return proposal;
	}

}