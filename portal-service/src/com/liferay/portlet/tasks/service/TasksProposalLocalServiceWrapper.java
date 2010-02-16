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

package com.liferay.portlet.tasks.service;


/**
 * <a href="TasksProposalLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link TasksProposalLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksProposalLocalService
 * @generated
 */
public class TasksProposalLocalServiceWrapper
	implements TasksProposalLocalService {
	public TasksProposalLocalServiceWrapper(
		TasksProposalLocalService tasksProposalLocalService) {
		_tasksProposalLocalService = tasksProposalLocalService;
	}

	public com.liferay.portlet.tasks.model.TasksProposal addTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.addTasksProposal(tasksProposal);
	}

	public com.liferay.portlet.tasks.model.TasksProposal createTasksProposal(
		long proposalId) {
		return _tasksProposalLocalService.createTasksProposal(proposalId);
	}

	public void deleteTasksProposal(long proposalId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.deleteTasksProposal(proposalId);
	}

	public void deleteTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.deleteTasksProposal(tasksProposal);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.tasks.model.TasksProposal getTasksProposal(
		long proposalId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getTasksProposal(proposalId);
	}

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> getTasksProposals(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getTasksProposals(start, end);
	}

	public int getTasksProposalsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getTasksProposalsCount();
	}

	public com.liferay.portlet.tasks.model.TasksProposal updateTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.updateTasksProposal(tasksProposal);
	}

	public com.liferay.portlet.tasks.model.TasksProposal updateTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.updateTasksProposal(tasksProposal,
			merge);
	}

	public com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long userId, long groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String name,
		java.lang.String description, long reviewUserId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.addProposal(userId, groupId,
			className, classPK, name, description, reviewUserId,
			addCommunityPermissions, addGuestPermissions);
	}

	public com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long userId, long groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String name,
		java.lang.String description, long reviewUserId,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.addProposal(userId, groupId,
			className, classPK, name, description, reviewUserId,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long userId, long groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String name,
		java.lang.String description, long reviewUserId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.addProposal(userId, groupId,
			className, classPK, name, description, reviewUserId,
			communityPermissions, guestPermissions);
	}

	public void addProposalResources(long proposalId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.addProposalResources(proposalId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addProposalResources(long proposalId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.addProposalResources(proposalId,
			communityPermissions, guestPermissions);
	}

	public void addProposalResources(
		com.liferay.portlet.tasks.model.TasksProposal proposal,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.addProposalResources(proposal,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addProposalResources(
		com.liferay.portlet.tasks.model.TasksProposal proposal,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.addProposalResources(proposal,
			communityPermissions, guestPermissions);
	}

	public void deleteProposal(long proposalId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.deleteProposal(proposalId);
	}

	public void deleteProposal(long classNameId, java.lang.String classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.deleteProposal(classNameId, classPK);
	}

	public void deleteProposal(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.deleteProposal(className, classPK);
	}

	public void deleteProposal(
		com.liferay.portlet.tasks.model.TasksProposal proposal)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.deleteProposal(proposal);
	}

	public void deleteProposals(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.deleteProposals(groupId);
	}

	public com.liferay.portlet.tasks.model.TasksProposal getProposal(
		long proposalId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getProposal(proposalId);
	}

	public com.liferay.portlet.tasks.model.TasksProposal getProposal(
		long classNameId, java.lang.String classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getProposal(classNameId, classPK);
	}

	public com.liferay.portlet.tasks.model.TasksProposal getProposal(
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getProposal(className, classPK);
	}

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> getProposals(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getProposals(groupId, start, end);
	}

	public int getProposalsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getProposalsCount(groupId);
	}

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> getReviewProposals(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getReviewProposals(groupId, userId,
			start, end);
	}

	public int getReviewProposalsCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getReviewProposalsCount(groupId,
			userId);
	}

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> getUserProposals(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getUserProposals(groupId, userId,
			start, end);
	}

	public int getUserProposalsCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getUserProposalsCount(groupId, userId);
	}

	public com.liferay.portlet.tasks.model.TasksProposal updateProposal(
		long userId, long proposalId, java.lang.String description,
		int dueDateMonth, int dueDateDay, int dueDateYear, int dueDateHour,
		int dueDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.updateProposal(userId, proposalId,
			description, dueDateMonth, dueDateDay, dueDateYear, dueDateHour,
			dueDateMinute);
	}

	public TasksProposalLocalService getWrappedTasksProposalLocalService() {
		return _tasksProposalLocalService;
	}

	private TasksProposalLocalService _tasksProposalLocalService;
}