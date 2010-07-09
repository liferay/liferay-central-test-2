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

package com.liferay.portal.upgrade;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public abstract class BaseUpgradePortletPreferences extends UpgradeProcess {

	protected void deletePortletPreferences(long portletPreferencesId)
		throws Exception {

		runSQL(
			"delete from PortletPreferences where portletPreferencesId = " +
				portletPreferencesId);
	}

	protected void doUpgrade() throws Exception {
		updatePortletPreferences();
	}

	protected Object[] getLayout(long plid) throws Exception {
		Object[] layout = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_LAYOUT);

			ps.setLong(1, plid);

			rs = ps.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				boolean privateLayout = rs.getBoolean("privateLayout");
				long layoutId = rs.getLong("layoutId");

				layout = new Object[] {
					groupId, companyId, privateLayout, layoutId};
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return layout;
	}

	protected String getLayoutUuid(long plid, long layoutId) throws Exception {
		Object[] layout = getLayout(plid);

		if (layout == null) {
			return null;
		}

		String uuid = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_LAYOUT_UUID);

			long groupId = (Long)layout[0];
			boolean privateLayout = (Boolean)layout[2];

			ps.setLong(1, groupId);
			ps.setBoolean(2, privateLayout);
			ps.setLong(3, layoutId);

			rs = ps.executeQuery();

			if (rs.next()) {
				uuid = rs.getString("uuid_");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return uuid;
	}

	protected abstract String getUpdatePortletPreferencesWhereClause();

	protected void updatePortletPreferences() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select portletPreferencesId, ownerId, ownerType, plid, " +
					"portletId, preferences from PortletPreferences where " +
						getUpdatePortletPreferencesWhereClause());

			rs = ps.executeQuery();

			while (rs.next()) {
				long portletPreferencesId = rs.getLong("portletPreferencesId");
				long ownerId = rs.getLong("ownerId");
				int ownerType = rs.getInt("ownerType");
				long plid = rs.getLong("plid");
				String portletId = rs.getString("portletId");
				String preferences = rs.getString("preferences");

				Object[] layout = getLayout(plid);

				if (layout != null) {
					long companyId = (Long)layout[1];

					String newPreferences = upgradePreferences(
						companyId, ownerId, ownerType, plid, portletId,
						preferences);

					updatePortletPreferences(
						portletPreferencesId, newPreferences);
				}
				else {
					deletePortletPreferences(portletPreferencesId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updatePortletPreferences(
			long portletPreferencesId, String preferences)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update PortletPreferences set preferences = ? where " +
					"portletPreferencesId = " + portletPreferencesId);

			ps.setString(1, preferences);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected abstract String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception;

	private static final String _GET_LAYOUT =
		"select * from Layout where plid = ?";

	private static final String _GET_LAYOUT_UUID =
		"select uuid_ from Layout where groupId = ? AND privateLayout = ? " +
			"AND layoutId = ?";

}