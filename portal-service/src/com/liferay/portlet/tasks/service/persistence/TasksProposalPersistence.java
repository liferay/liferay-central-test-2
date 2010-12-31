/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tasks.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.tasks.model.TasksProposal;

/**
 * The persistence interface for the tasks proposal service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TasksProposalPersistenceImpl
 * @see TasksProposalUtil
 * @generated
 */
public interface TasksProposalPersistence extends BasePersistence<TasksProposal> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TasksProposalUtil} to access the tasks proposal persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the tasks proposal in the entity cache if it is enabled.
	*
	* @param tasksProposal the tasks proposal to cache
	*/
	public void cacheResult(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal);

	/**
	* Caches the tasks proposals in the entity cache if it is enabled.
	*
	* @param tasksProposals the tasks proposals to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.tasks.model.TasksProposal> tasksProposals);

	/**
	* Creates a new tasks proposal with the primary key. Does not add the tasks proposal to the database.
	*
	* @param proposalId the primary key for the new tasks proposal
	* @return the new tasks proposal
	*/
	public com.liferay.portlet.tasks.model.TasksProposal create(long proposalId);

	/**
	* Removes the tasks proposal with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param proposalId the primary key of the tasks proposal to remove
	* @return the tasks proposal that was removed
	* @throws com.liferay.portlet.tasks.NoSuchProposalException if a tasks proposal with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal remove(long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal updateImpl(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the tasks proposal with the primary key or throws a {@link com.liferay.portlet.tasks.NoSuchProposalException} if it could not be found.
	*
	* @param proposalId the primary key of the tasks proposal to find
	* @return the tasks proposal
	* @throws com.liferay.portlet.tasks.NoSuchProposalException if a tasks proposal with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal findByPrimaryKey(
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	/**
	* Finds the tasks proposal with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param proposalId the primary key of the tasks proposal to find
	* @return the tasks proposal, or <code>null</code> if a tasks proposal with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal fetchByPrimaryKey(
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the tasks proposals where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the tasks proposals where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of tasks proposals to return
	* @param end the upper bound of the range of tasks proposals to return (not inclusive)
	* @return the range of matching tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the tasks proposals where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of tasks proposals to return
	* @param end the upper bound of the range of tasks proposals to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first tasks proposal in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching tasks proposal
	* @throws com.liferay.portlet.tasks.NoSuchProposalException if a matching tasks proposal could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	/**
	* Finds the last tasks proposal in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching tasks proposal
	* @throws com.liferay.portlet.tasks.NoSuchProposalException if a matching tasks proposal could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	/**
	* Finds the tasks proposals before and after the current tasks proposal in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the primary key of the current tasks proposal
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next tasks proposal
	* @throws com.liferay.portlet.tasks.NoSuchProposalException if a tasks proposal with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal[] findByGroupId_PrevAndNext(
		long proposalId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	/**
	* Filters by the user's permissions and finds all the tasks proposals where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching tasks proposals that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the tasks proposals where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of tasks proposals to return
	* @param end the upper bound of the range of tasks proposals to return (not inclusive)
	* @return the range of matching tasks proposals that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the tasks proposals where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of tasks proposals to return
	* @param end the upper bound of the range of tasks proposals to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching tasks proposals that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the tasks proposals where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the matching tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the tasks proposals where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @param start the lower bound of the range of tasks proposals to return
	* @param end the upper bound of the range of tasks proposals to return (not inclusive)
	* @return the range of matching tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the tasks proposals where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @param start the lower bound of the range of tasks proposals to return
	* @param end the upper bound of the range of tasks proposals to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first tasks proposal in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching tasks proposal
	* @throws com.liferay.portlet.tasks.NoSuchProposalException if a matching tasks proposal could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	/**
	* Finds the last tasks proposal in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching tasks proposal
	* @throws com.liferay.portlet.tasks.NoSuchProposalException if a matching tasks proposal could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	/**
	* Finds the tasks proposals before and after the current tasks proposal in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param proposalId the primary key of the current tasks proposal
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next tasks proposal
	* @throws com.liferay.portlet.tasks.NoSuchProposalException if a tasks proposal with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal[] findByG_U_PrevAndNext(
		long proposalId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	/**
	* Filters by the user's permissions and finds all the tasks proposals where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the matching tasks proposals that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> filterFindByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds a range of all the tasks proposals where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @param start the lower bound of the range of tasks proposals to return
	* @param end the upper bound of the range of tasks proposals to return (not inclusive)
	* @return the range of matching tasks proposals that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> filterFindByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and finds an ordered range of all the tasks proposals where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @param start the lower bound of the range of tasks proposals to return
	* @param end the upper bound of the range of tasks proposals to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching tasks proposals that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> filterFindByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the tasks proposal where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.tasks.NoSuchProposalException} if it could not be found.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching tasks proposal
	* @throws com.liferay.portlet.tasks.NoSuchProposalException if a matching tasks proposal could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal findByC_C(
		long classNameId, java.lang.String classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	/**
	* Finds the tasks proposal where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching tasks proposal, or <code>null</code> if a matching tasks proposal could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal fetchByC_C(
		long classNameId, java.lang.String classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the tasks proposal where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching tasks proposal, or <code>null</code> if a matching tasks proposal could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.tasks.model.TasksProposal fetchByC_C(
		long classNameId, java.lang.String classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the tasks proposals.
	*
	* @return the tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the tasks proposals.
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
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the tasks proposals.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of tasks proposals to return
	* @param end the upper bound of the range of tasks proposals to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the tasks proposals where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the tasks proposals where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the tasks proposal where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C(long classNameId, java.lang.String classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	/**
	* Removes all the tasks proposals from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the tasks proposals where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the tasks proposals where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching tasks proposals that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the tasks proposals where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the number of matching tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Filters by the user's permissions and counts all the tasks proposals where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param userId the user ID to search with
	* @return the number of matching tasks proposals that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public int filterCountByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the tasks proposals where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the number of matching tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, java.lang.String classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the tasks proposals.
	*
	* @return the number of tasks proposals
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}