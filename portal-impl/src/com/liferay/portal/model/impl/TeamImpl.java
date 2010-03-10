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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.service.RoleLocalServiceUtil;

/**
 * <a href="TeamImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TeamImpl extends TeamModelImpl implements Team {

	public TeamImpl() {
	}

	public Role getRole() {
		Role role = null;

		try {
			role = RoleLocalServiceUtil.getTeamRole(
				getCompanyId(), getTeamId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return role;
	}

	private static Log _log = LogFactoryUtil.getLog(TeamImpl.class);

}