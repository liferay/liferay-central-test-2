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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="TasksProposalLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link TasksProposalLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksProposalLocalService
 * @generated
 */
public class TasksProposalLocalServiceUtil {
	public static com.liferay.portlet.tasks.model.TasksProposal addTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.SystemException {
		return getService().addTasksProposal(tasksProposal);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal createTasksProposal(
		long proposalId) {
		return getService().createTasksProposal(proposalId);
	}

	public static void deleteTasksProposal(long proposalId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteTasksProposal(proposalId);
	}

	public static void deleteTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.SystemException {
		getService().deleteTasksProposal(tasksProposal);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal getTasksProposal(
		long proposalId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getTasksProposal(proposalId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> getTasksProposals(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getTasksProposals(start, end);
	}

	public static int getTasksProposalsCount()
		throws com.liferay.portal.SystemException {
		return getService().getTasksProposalsCount();
	}

	public static com.liferay.portlet.tasks.model.TasksProposal updateTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.SystemException {
		return getService().updateTasksProposal(tasksProposal);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal updateTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateTasksProposal(tasksProposal, merge);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long userId, long groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String name,
		java.lang.String description, long reviewUserId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addProposal(userId, groupId, className, classPK, name,
			description, reviewUserId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long userId, long groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String name,
		java.lang.String description, long reviewUserId,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addProposal(userId, groupId, className, classPK, name,
			description, reviewUserId, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long userId, long groupId, java.lang.String className,
		java.lang.String classPK, java.lang.String name,
		java.lang.String description, long reviewUserId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addProposal(userId, groupId, className, classPK, name,
			description, reviewUserId, communityPermissions, guestPermissions);
	}

	public static void addProposalResources(long proposalId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addProposalResources(proposalId, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addProposalResources(long proposalId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addProposalResources(proposalId, communityPermissions,
			guestPermissions);
	}

	public static void addProposalResources(
		com.liferay.portlet.tasks.model.TasksProposal proposal,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addProposalResources(proposal, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addProposalResources(
		com.liferay.portlet.tasks.model.TasksProposal proposal,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addProposalResources(proposal, communityPermissions,
			guestPermissions);
	}

	public static void deleteProposal(long proposalId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteProposal(proposalId);
	}

	public static void deleteProposal(long classNameId, java.lang.String classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteProposal(classNameId, classPK);
	}

	public static void deleteProposal(java.lang.String className,
		java.lang.String classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteProposal(className, classPK);
	}

	public static void deleteProposal(
		com.liferay.portlet.tasks.model.TasksProposal proposal)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteProposal(proposal);
	}

	public static void deleteProposals(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteProposals(groupId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal getProposal(
		long proposalId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getProposal(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal getProposal(
		long classNameId, java.lang.String classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getProposal(classNameId, classPK);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal getProposal(
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getProposal(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> getProposals(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getProposals(groupId, start, end);
	}

	public static int getProposalsCount(long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getProposalsCount(groupId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> getReviewProposals(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getReviewProposals(groupId, userId, start, end);
	}

	public static int getReviewProposalsCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getService().getReviewProposalsCount(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> getUserProposals(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getUserProposals(groupId, userId, start, end);
	}

	public static int getUserProposalsCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getService().getUserProposalsCount(groupId, userId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal updateProposal(
		long userId, long proposalId, java.lang.String description,
		int dueDateMonth, int dueDateDay, int dueDateYear, int dueDateHour,
		int dueDateMinute)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateProposal(userId, proposalId, description,
			dueDateMonth, dueDateDay, dueDateYear, dueDateHour, dueDateMinute);
	}

	public static TasksProposalLocalService getService() {
		if (_service == null) {
			_service = (TasksProposalLocalService)PortalBeanLocatorUtil.locate(TasksProposalLocalService.class.getName());
		}

		return _service;
	}

	public void setService(TasksProposalLocalService service) {
		_service = service;
	}

	private static TasksProposalLocalService _service;
}