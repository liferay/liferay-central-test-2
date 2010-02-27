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

package com.liferay.portal.upgrade.v5_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeSitemap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class UpgradeSitemap extends UpgradeProcess {

	protected void deletePortletPreferences(long portletPreferencesId)
		throws Exception {

		runSQL(
			"delete from PortletPreferences where portletPreferencesId = " +
				portletPreferencesId);
	}

	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select portletPreferencesId, ownerId, ownerType, plid, " +
					"portletId, preferences from PortletPreferences where " +
						"portletId like '85_%'");

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
					long companyId = (Long)layout[0];

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
				long companyId = rs.getLong("companyId");
				long layoutId = rs.getLong("layoutId");

				layout = new Object[] {companyId, layoutId};
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return layout;
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

	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferencesImpl preferences =
			PortletPreferencesSerializer.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		long rootPlid = GetterUtil.getLong(
			preferences.getValue("root-plid", StringPool.BLANK));

		if (rootPlid > 0) {
			Object[] layout = getLayout(rootPlid);

			if (layout != null) {
				long layoutId = (Long)layout[1];

				preferences.setValue(
					"root-layout-id", String.valueOf(layoutId));
			}
		}

		preferences.setValue("root-plid", null);

		return PortletPreferencesSerializer.toXML(preferences);
	}

	private static final String _GET_LAYOUT =
		"select * from Layout where plid = ?";

}