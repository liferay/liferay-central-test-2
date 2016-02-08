/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.calendar.upgrade.v1_0_3;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Adam Brandizzi
 */
public class UpgradeCalendarResource extends UpgradeProcess {

	public UpgradeCalendarResource(
		ClassNameLocalService classNameLocalService,
		CompanyLocalService companyLocaService,
		UserLocalService userLocalService) {

		_classNameLocalService = classNameLocalService;
		_companyLocaService = companyLocaService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeCalendarResourceUserIds();
	}

	protected long getCompanyAdminUserId(Company company)
		throws PortalException {

		Role role = RoleLocalServiceUtil.getRole(
			company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		long[] userIds = UserLocalServiceUtil.getRoleUserIds(role.getRoleId());

		return userIds[0];
	}

	protected void updateCalendarUserId(long calendarId, long userId)
		throws SQLException {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update Calendar set userId = ? where calendarId = ?");

			ps.setLong(1, userId);
			ps.setLong(2, calendarId);

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void updateCalendarUserIds(
			long groupClassNameId, long defaultUserId, long adminUserId)
		throws SQLException {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select Calendar.calendarId from Calendar join " +
					"CalendarResource where CalendarResource.classNameId = " +
						"? and CalendarResource.userId = ?");

			ps.setLong(1, groupClassNameId);
			ps.setLong(2, defaultUserId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long calendarId = rs.getLong(1);

				updateCalendarUserId(calendarId, adminUserId);
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	protected void upgradeCalendarResourceUserId(
			long groupClassNameId, long defaultUserId, long companyAdminUserId)
		throws SQLException {

		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(
				"update CalendarResource set userId = ? where userId = ? and " +
					"classNameId = ?");

			ps.setLong(1, companyAdminUserId);
			ps.setLong(2, defaultUserId);
			ps.setLong(3, groupClassNameId);

			ps.execute();
		}
		finally {
			DataAccess.cleanUp(ps);
		}
	}

	protected void upgradeCalendarResourceUserIds()
		throws PortalException, SQLException {

		for (Company company : _companyLocaService.getCompanies()) {
			long classNameId = _classNameLocalService.getClassNameId(
				Group.class);
			long defaultUserId = _userLocalService.getDefaultUserId(
				company.getCompanyId());
			long companyAdminUserId = getCompanyAdminUserId(company);

			upgradeCalendarResourceUserId(
				classNameId, defaultUserId, companyAdminUserId);

			updateCalendarUserIds(
				classNameId, defaultUserId, companyAdminUserId);
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final CompanyLocalService _companyLocaService;
	private final UserLocalService _userLocalService;

}