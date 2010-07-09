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

/**
 * @author Brian Wing Shun Chan
 */
public class TasksProposalFinderUtil {
	public static int countByG_U(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByG_U(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByG_U(groupId, userId, start, end);
	}

	public static TasksProposalFinder getFinder() {
		if (_finder == null) {
			_finder = (TasksProposalFinder)PortalBeanLocatorUtil.locate(TasksProposalFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(TasksProposalFinder finder) {
		_finder = finder;
	}

	private static TasksProposalFinder _finder;
}