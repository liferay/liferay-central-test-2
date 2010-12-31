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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Team;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.TeamServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.TeamPermissionUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class TeamServiceImpl extends TeamServiceBaseImpl {

	public Team addTeam(
			long groupId, String name, String description)
		throws PortalException, SystemException {

		GroupPermissionUtil.check(
			getPermissionChecker(), groupId, ActionKeys.MANAGE_TEAMS);

		return teamLocalService.addTeam(
			getUserId(), groupId, name, description);
	}

	public void deleteTeam(long teamId)
		throws PortalException, SystemException {

		TeamPermissionUtil.check(
			getPermissionChecker(), teamId, ActionKeys.DELETE);

		teamLocalService.deleteTeam(teamId);
	}

	public Team updateTeam(long teamId, String name, String description)
		throws PortalException, SystemException {

		TeamPermissionUtil.check(
			getPermissionChecker(), teamId, ActionKeys.UPDATE);

		return teamLocalService.updateTeam(teamId, name, description);
	}

}