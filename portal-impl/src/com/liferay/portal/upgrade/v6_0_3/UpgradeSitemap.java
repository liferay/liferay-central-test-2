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

package com.liferay.portal.upgrade.v6_0_3;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
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
 * @author Julio Camarero
 */
public class UpgradeSitemap
	extends com.liferay.portal.upgrade.v5_1_0.UpgradeSitemap {

	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferencesImpl preferences =
			PortletPreferencesSerializer.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		long rootLayoutId = GetterUtil.getLong(
			preferences.getValue("root-layout-id", StringPool.BLANK));

		if (rootLayoutId > 0) {
			String uuid = getLayoutUuid(plid, rootLayoutId);

			if (uuid != null) {
				preferences.setValue(
					"root-layout-uuid", uuid);
			}

			preferences.reset("root-layout-id");
		}

		return PortletPreferencesSerializer.toXML(preferences);
	}

	protected String getLayoutUuid(long plid, long layoutId) throws Exception {
		String uuid = null;

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

				ps = con.prepareStatement(_GET_UUID);

				ps.setLong(1, groupId);
				ps.setLong(2, layoutId);
				ps.setBoolean(3, privateLayout);

				rs = ps.executeQuery();

				rs.next();

				uuid = rs.getString("uuid_");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return uuid;
	}

	private static final String _GET_LAYOUT =
		"select * from Layout where plid = ?";

	private static final String _GET_UUID =
		"select uuid_ from Layout where groupId = ? AND layoutId = ? " +
			"AND privateLayout = ?";

}