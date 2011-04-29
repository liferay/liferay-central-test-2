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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Terry Jia
 */
public class UpgradeExpando extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		updateExpandColumn();
	}

	protected void updateExpandColumn() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select typeSettings, columnId from ExpandoColumn where " +
					"typeSettings like '%selection%'");

			rs = ps.executeQuery();

			while (rs.next()) {
				String typeSettings = rs.getString("typeSettings");

				typeSettings = typeSettings.replace(
					"selection=1", "display-type=selection-list");
				typeSettings = typeSettings.replace(
					"selection=0", "display-type=text-box");

				runSQL(
					"update ExpandoColumn set typeSettings = '" + typeSettings +
						"' where columnId = " + rs.getLong("columnId"));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}