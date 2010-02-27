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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="TasksProposalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link TasksProposalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksProposalService
 * @generated
 */
public class TasksProposalServiceUtil {
	public static com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long groupId, java.lang.String className, java.lang.String classPK,
		java.lang.String name, java.lang.String description, long reviewUserId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addProposal(groupId, className, classPK, name, description,
			reviewUserId, addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long groupId, java.lang.String className, java.lang.String classPK,
		java.lang.String name, java.lang.String description, long reviewUserId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addProposal(groupId, className, classPK, name, description,
			reviewUserId, communityPermissions, guestPermissions);
	}

	public static void deleteProposal(long proposalId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteProposal(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal updateProposal(
		long proposalId, java.lang.String description, int dueDateMonth,
		int dueDateDay, int dueDateYear, int dueDateHour, int dueDateMinute)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateProposal(proposalId, description, dueDateMonth,
			dueDateDay, dueDateYear, dueDateHour, dueDateMinute);
	}

	public static TasksProposalService getService() {
		if (_service == null) {
			_service = (TasksProposalService)PortalBeanLocatorUtil.locate(TasksProposalService.class.getName());
		}

		return _service;
	}

	public void setService(TasksProposalService service) {
		_service = service;
	}

	private static TasksProposalService _service;
}