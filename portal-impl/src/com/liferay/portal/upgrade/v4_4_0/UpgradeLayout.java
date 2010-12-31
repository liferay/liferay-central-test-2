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

package com.liferay.portal.upgrade.v4_4_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jorge Ferrer
 */
public class UpgradeLayout extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select plid, typeSettings from Layout where type_ = " +
					"'link_to_layout'");

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");
				String typeSettings = rs.getString("typeSettings");

				UnicodeProperties typeSettingsProperties =
					new UnicodeProperties(true);

				typeSettingsProperties.load(typeSettings);

				long linkToPlid = GetterUtil.getLong(
					typeSettingsProperties.getProperty("linkToPlid"));

				if (linkToPlid > 0) {
					Object[] layout = getLayout(linkToPlid);

					if (layout != null) {
						long groupId = (Long)layout[0];
						boolean privateLayout = (Boolean)layout[1];
						long layoutId = (Long)layout[2];

						typeSettingsProperties.remove("linkToPlid");
						typeSettingsProperties.put(
							"groupId", String.valueOf(groupId));
						typeSettingsProperties.put(
							"privateLayout", String.valueOf(privateLayout));
						typeSettingsProperties.put(
							"linkToLayoutId", String.valueOf(layoutId));
					}
				}

				updateTypeSettings(plid, typeSettingsProperties.toString());
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
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
				boolean privateLayout = rs.getBoolean("privateLayout");
				long layoutId = rs.getLong("layoutId");

				layout = new Object[] {groupId, privateLayout, layoutId};
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return layout;
	}

	protected void updateTypeSettings(long plid, String typeSettings)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update Layout set typeSettings = ? where plid = " + plid);

			ps.setString(1, typeSettings);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private static final String _GET_LAYOUT =
		"select * from Layout where plid = ?";

}