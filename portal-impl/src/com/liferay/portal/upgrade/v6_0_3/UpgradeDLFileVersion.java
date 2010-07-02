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
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeDLFileVersion.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 */
public class UpgradeDLFileVersion extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select folderId, groupId, name, extension, title, " +
					"description, extraSettings from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long folderId = rs.getLong("folderId");
				long groupId = rs.getLong("groupId");
				String name = rs.getString("name");
				String extension = rs.getString("extension");
				String title = rs.getString("title");
				String description = rs.getString("description");
				String extraSettings = rs.getString("extraSettings");

				long fileVersionId = getLatestDLFileVersionId(
					folderId, groupId, name);

				runSQL(
					"update DLFileVersion set extension = '" + extension +
						"', title = '" + title + "', description = '" +
						description + "',  extraSettings = '" + extraSettings
						+ "' where fileVersionId = " + fileVersionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getLatestDLFileVersionId(long folderId, long groupId,
			String name)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long fileVersionId = 0;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select fileVersionId from DLFileVersion where folderId = ? " +
					"and groupId = ? and name = ? order by version desc");

			ps.setLong(1, folderId);
			ps.setLong(2, groupId);
			ps.setString(3, name);

			rs = ps.executeQuery();

			while (rs.next()) {
				fileVersionId = rs.getLong("fileVersionId");

				break;
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return fileVersionId;
	}

}