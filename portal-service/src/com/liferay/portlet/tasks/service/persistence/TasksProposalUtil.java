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

package com.liferay.portlet.tasks.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.tasks.model.TasksProposal;

import java.util.List;

/**
 * <a href="TasksProposalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksProposalPersistence
 * @see       TasksProposalPersistenceImpl
 * @generated
 */
public class TasksProposalUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<TasksProposal> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<TasksProposal> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static TasksProposal remove(TasksProposal tasksProposal)
		throws SystemException {
		return getPersistence().remove(tasksProposal);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static TasksProposal update(TasksProposal tasksProposal,
		boolean merge) throws SystemException {
		return getPersistence().update(tasksProposal, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal) {
		getPersistence().cacheResult(tasksProposal);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.tasks.model.TasksProposal> tasksProposals) {
		getPersistence().cacheResult(tasksProposals);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal create(
		long proposalId) {
		return getPersistence().create(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal remove(
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().remove(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal updateImpl(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(tasksProposal, merge);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByPrimaryKey(
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByPrimaryKey(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal fetchByPrimaryKey(
		long proposalId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(proposalId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal[] findByGroupId_PrevAndNext(
		long proposalId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(proposalId, groupId,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_U(groupId, userId, start, end, orderByComparator);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence()
				   .findByG_U_First(groupId, userId, orderByComparator);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence()
				   .findByG_U_Last(groupId, userId, orderByComparator);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal[] findByG_U_PrevAndNext(
		long proposalId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence()
				   .findByG_U_PrevAndNext(proposalId, groupId, userId,
			orderByComparator);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByC_C(
		long classNameId, java.lang.String classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal fetchByC_C(
		long classNameId, java.lang.String classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal fetchByC_C(
		long classNameId, java.lang.String classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_U(groupId, userId);
	}

	public static void removeByC_C(long classNameId, java.lang.String classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_U(groupId, userId);
	}

	public static int countByC_C(long classNameId, java.lang.String classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static TasksProposalPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (TasksProposalPersistence)PortalBeanLocatorUtil.locate(TasksProposalPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(TasksProposalPersistence persistence) {
		_persistence = persistence;
	}

	private static TasksProposalPersistence _persistence;
}