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

package com.liferay.portal.service;


/**
 * <a href="TeamLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link TeamLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TeamLocalService
 * @generated
 */
public class TeamLocalServiceWrapper implements TeamLocalService {
	public TeamLocalServiceWrapper(TeamLocalService teamLocalService) {
		_teamLocalService = teamLocalService;
	}

	public com.liferay.portal.model.Team addTeam(
		com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.addTeam(team);
	}

	public com.liferay.portal.model.Team createTeam(long teamId) {
		return _teamLocalService.createTeam(teamId);
	}

	public void deleteTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_teamLocalService.deleteTeam(teamId);
	}

	public void deleteTeam(com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		_teamLocalService.deleteTeam(team);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Team getTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.getTeam(teamId);
	}

	public java.util.List<com.liferay.portal.model.Team> getTeams(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.getTeams(start, end);
	}

	public int getTeamsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.getTeamsCount();
	}

	public com.liferay.portal.model.Team updateTeam(
		com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.updateTeam(team);
	}

	public com.liferay.portal.model.Team updateTeam(
		com.liferay.portal.model.Team team, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.updateTeam(team, merge);
	}

	public com.liferay.portal.model.Team addTeam(long userId, long groupId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.addTeam(userId, groupId, name, description);
	}

	public void deleteTeams(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_teamLocalService.deleteTeams(groupId);
	}

	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.getUserTeams(userId);
	}

	public java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.getUserTeams(userId, groupId);
	}

	public boolean hasUserTeam(long userId, long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.hasUserTeam(userId, teamId);
	}

	public java.util.List<com.liferay.portal.model.Team> search(long groupId,
		java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.search(groupId, name, description, params,
			start, end, obc);
	}

	public int searchCount(long groupId, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.searchCount(groupId, name, description, params);
	}

	public com.liferay.portal.model.Team updateTeam(long teamId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _teamLocalService.updateTeam(teamId, name, description);
	}

	public TeamLocalService getWrappedTeamLocalService() {
		return _teamLocalService;
	}

	private TeamLocalService _teamLocalService;
}