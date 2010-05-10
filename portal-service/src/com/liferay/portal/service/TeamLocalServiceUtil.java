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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="TeamLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link TeamLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TeamLocalService
 * @generated
 */
public class TeamLocalServiceUtil {
	public static com.liferay.portal.model.Team addTeam(
		com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addTeam(team);
	}

	public static com.liferay.portal.model.Team createTeam(long teamId) {
		return getService().createTeam(teamId);
	}

	public static void deleteTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTeam(teamId);
	}

	public static void deleteTeam(com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTeam(team);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.Team getTeam(long teamId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTeam(teamId);
	}

	public static java.util.List<com.liferay.portal.model.Team> getTeams(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTeams(start, end);
	}

	public static int getTeamsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTeamsCount();
	}

	public static com.liferay.portal.model.Team updateTeam(
		com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTeam(team);
	}

	public static com.liferay.portal.model.Team updateTeam(
		com.liferay.portal.model.Team team, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTeam(team, merge);
	}

	public static com.liferay.portal.model.Team addTeam(long userId,
		long groupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addTeam(userId, groupId, name, description);
	}

	public static void deleteTeams(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTeams(groupId);
	}

	public static java.util.List<com.liferay.portal.model.Team> getGroupTeams(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupTeams(groupId);
	}

	public static java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTeams(userId);
	}

	public static java.util.List<com.liferay.portal.model.Team> getUserTeams(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserTeams(userId, groupId);
	}

	public static boolean hasUserTeam(long userId, long teamId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserTeam(userId, teamId);
	}

	public static java.util.List<com.liferay.portal.model.Team> search(
		long groupId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(groupId, name, description, params, start, end, obc);
	}

	public static int searchCount(long groupId, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().searchCount(groupId, name, description, params);
	}

	public static com.liferay.portal.model.Team updateTeam(long teamId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTeam(teamId, name, description);
	}

	public static TeamLocalService getService() {
		if (_service == null) {
			_service = (TeamLocalService)PortalBeanLocatorUtil.locate(TeamLocalService.class.getName());
		}

		return _service;
	}

	public void setService(TeamLocalService service) {
		_service = service;
	}

	private static TeamLocalService _service;
}