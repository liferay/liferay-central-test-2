/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.tasks.service;

/**
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

	/**
	* Adds the tasks proposal to the database. Also notifies the appropriate model listeners.
	*
	* @param tasksProposal the tasks proposal to add
	* @return the tasks proposal that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal addTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.addTasksProposal(tasksProposal);
	}

	/**
	* Creates a new tasks proposal with the primary key. Does not add the tasks proposal to the database.
	*
	* @param proposalId the primary key for the new tasks proposal
	* @return the new tasks proposal
	*/
	public com.liferay.portlet.tasks.model.TasksProposal createTasksProposal(
		long proposalId) {
		return _tasksProposalLocalService.createTasksProposal(proposalId);
	}

	/**
	* Deletes the tasks proposal with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param proposalId the primary key of the tasks proposal to delete
	* @throws PortalException if a tasks proposal with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteTasksProposal(long proposalId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.deleteTasksProposal(proposalId);
	}

	/**
	* Deletes the tasks proposal from the database. Also notifies the appropriate model listeners.
	*
	* @param tasksProposal the tasks proposal to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.kernel.exception.SystemException {
		_tasksProposalLocalService.deleteTasksProposal(tasksProposal);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the tasks proposal with the primary key.
	*
	* @param proposalId the primary key of the tasks proposal to get
	* @return the tasks proposal
	* @throws PortalException if a tasks proposal with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal getTasksProposal(
		long proposalId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getTasksProposal(proposalId);
	}

	/**
	* Gets a range of all the tasks proposals.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of tasks proposals to return
	* @param end the upper bound of the range of tasks proposals to return (not inclusive)
	* @return the range of tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> getTasksProposals(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getTasksProposals(start, end);
	}

	/**
	* Gets the number of tasks proposals.
	*
	* @return the number of tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public int getTasksProposalsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.getTasksProposalsCount();
	}

	/**
	* Updates the tasks proposal in the database. Also notifies the appropriate model listeners.
	*
	* @param tasksProposal the tasks proposal to update
	* @return the tasks proposal that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal updateTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _tasksProposalLocalService.updateTasksProposal(tasksProposal);
	}

	/**
	* Updates the tasks proposal in the database. Also notifies the appropriate model listeners.
	*
	* @param tasksProposal the tasks proposal to update
	* @param merge whether to merge the tasks proposal with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the tasks proposal that was updated
	* @throws SystemException if a system exception occurred
	*/
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