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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan Fernández
 */
public class UpgradeAdminPortlets extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
			updateAccessInControlPanelPermission_5(
				PortletKeys.BLOGS, PortletKeys.BLOGS_ADMIN);

			updateAccessInControlPanelPermission_5(
				PortletKeys.MESSAGE_BOARDS, PortletKeys.MESSAGE_BOARDS_ADMIN);
		}
		else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
			updateAccessInControlPanelPermission_6(
				PortletKeys.BLOGS, PortletKeys.BLOGS_ADMIN);

			updateAccessInControlPanelPermission_6(
				PortletKeys.MESSAGE_BOARDS, PortletKeys.MESSAGE_BOARDS_ADMIN);
		}
	}

	protected void addResource(
			long resourceId, String codeId, long primKey)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into Resource_ (resourceId, codeId, primKey) " +
					"values (?, ?, ?)");

			ps.setLong(1, resourceId);
			ps.setString(2, codeId);
			ps.setLong(3, primKey);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected Long[] addResourceIds(long companyId, String name)
		throws Exception {

		Long[] resourceIds = new Long[2];
		long resourceId = 0;
		long codeId = 0;

		// Company Scope

		codeId = increment();

		runSQL(
			"insert into ResourceCode (codeId, companyId, name, scope) values" +
				" (" + codeId + ", " + companyId + ", " + name +
				", " + ResourceConstants.SCOPE_COMPANY + ")");

		resourceId = increment();

		addResource(resourceId, String.valueOf(codeId), companyId);
		resourceIds[0] = resourceId;

		// Individual Scope

		codeId = increment();

		runSQL(
			"insert into ResourceCode (codeId, companyId, name, scope) values" +
				" (" + codeId + ", " + companyId + ", " + name +
				", " + ResourceConstants.SCOPE_INDIVIDUAL + ")");

		resourceId = increment();

		long controlPanelGroupId = getControlPanelGroupId();

		addResource(resourceId, String.valueOf(codeId), controlPanelGroupId);
		resourceIds[1] = resourceId;

		return resourceIds;
	}

	protected void addResourcePermission(
			long resourcePermissionId, long companyId, String name, long scope,
			long primKey, long roleId, long actionIds)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into ResourcePermission (resourcePermissionId, " +
					"companyId, name, scope, primKey, roleId, actionIds) " +
					"values (?, ?, ?, ?, ?, ?, ?)");

			ps.setLong(1, resourcePermissionId);
			ps.setLong(2, companyId);
			ps.setString(3, name);
			ps.setLong(4, scope);
			ps.setLong(5, primKey);
			ps.setLong(6, roleId);
			ps.setLong(7, actionIds);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected long getBitwiseValue(String name, String actionId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long bitwiseValue = 0;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select bitwiseValue from ResourceAction where name= ? " +
					"and actionId=?");

			ps.setString(1, name);
			ps.setString(2, actionId);

			rs = ps.executeQuery();

			if (rs.next()) {
				bitwiseValue = rs.getLong("bitwiseValue");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return bitwiseValue;
	}

	protected long getControlPanelGroupId() throws Exception {

		long groupId = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select groupId from Group_ where name= \"" +
					GroupConstants.CONTROL_PANEL + "\"");

			rs = ps.executeQuery();

			if (rs.next()) {
				groupId = rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return groupId;
	}

	protected Long[] getOldResourceIds(String portletFrom)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select Permission_.resourceId from Resource_ inner join " +
					"ResourceCode inner join Permission_ on " +
					"ResourceCode.codeId = Resource_.codeId and " +
					"ResourceCode.name=\"" + portletFrom +
					"\" and Permission_.actionId=\"" +
					ActionKeys.ACCESS_IN_CONTROL_PANEL +
					"\" and Permission_.resourceId = Resource_.resourceId;");

			rs = ps.executeQuery();

			List<Long> resourceIds = new ArrayList<Long>();

			while (rs.next()) {
				resourceIds.add(rs.getLong("resourceId"));
			}

			return resourceIds.toArray(new Long[resourceIds.size()]);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateAccessInControlPanelPermission_5(
			String portletFrom, String portletTo)
		throws Exception {

		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		if (companyIds.length == 0) {
			return;
		}

		for (int i = 0; i < companyIds.length; i++) {
			long companyId = companyIds[i];
			Long[] newResourceIds = addResourceIds(companyId, portletTo);
			Long[] oldResourceIds = getOldResourceIds(portletFrom);

			updatePermission(oldResourceIds, newResourceIds);
		}
	}

	protected void updateAccessInControlPanelPermission_6(
			String portletFrom, String portletTo)
		throws Exception {

		long bitwiseValue = getBitwiseValue(
			portletFrom, ActionKeys.ACCESS_IN_CONTROL_PANEL);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from ResourcePermission where name = ?");

			ps.setString(1, portletFrom);

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePermissionId = rs.getLong("resourcePermissionId");
				long actionIds = rs.getLong("actionIds");

				if ((actionIds & bitwiseValue) != 0) {
					actionIds = actionIds & (~bitwiseValue);

					runSQL(
						"update ResourcePermission set actionIds = '" +
							actionIds + "' where resourcePermissionId = " +
								resourcePermissionId);

					resourcePermissionId = increment();

					long companyId = rs.getLong("companyId");
					long scope = rs.getLong("scope");
					long primKey = rs.getLong("primKey");
					long roleId = rs.getLong("roleId");

					actionIds = rs.getLong("actionIds");
					actionIds |= bitwiseValue;

					addResourcePermission(
						resourcePermissionId, companyId, portletTo, scope,
						primKey, roleId, actionIds);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updatePermission(
			Long[] oldResourceIds, Long[] newResourceIds)
		throws Exception {

		for (int i = 0; i < oldResourceIds.length; i++) {
			runSQL(
				"update Permission_ set resourceId = '" + newResourceIds[i] +
					"' where resourceId = " + oldResourceIds[i]);
		}
	}

}