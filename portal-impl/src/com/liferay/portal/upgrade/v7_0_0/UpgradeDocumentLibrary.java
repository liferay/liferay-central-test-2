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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.upgrade.v7_0_0.util.DLFileEntryTable;
import com.liferay.portal.upgrade.v7_0_0.util.DLFileVersionTable;
import com.liferay.portlet.documentlibrary.util.DLAppUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Michael Young
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		// DLFileEntry

		try {
			runSQL("alter table DLFileEntry add fileName VARCHAR(255) null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DLFileEntryTable.TABLE_NAME, DLFileEntryTable.TABLE_COLUMNS,
				DLFileEntryTable.TABLE_SQL_CREATE,
				DLFileEntryTable.TABLE_SQL_ADD_INDEXES);
		}

		updateFileEntryFileNames();

		// DLFileVersion

		try {
			runSQL("alter table DLFileVersion add fileName VARCHAR(255) null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				DLFileVersionTable.TABLE_NAME, DLFileVersionTable.TABLE_COLUMNS,
				DLFileVersionTable.TABLE_SQL_CREATE,
				DLFileVersionTable.TABLE_SQL_ADD_INDEXES);
		}

		updateFileVersionFileNames();
	}

	protected void updateFileEntryFileName(long fileEntryId, String fileName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DLFileEntry set fileName = ? where fileEntryId = ?");

			ps.setString(1, fileName);
			ps.setLong(2, fileEntryId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateFileEntryFileNames() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select fileEntryId, extension, title, from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileEntryId = rs.getLong("fileEntryId");
				String extension = GetterUtil.getString(
					rs.getString("extension"));
				String title = GetterUtil.getString(rs.getString("title"));

				String fileName = DLAppUtil.getSanitizedFileName(
					title, extension);

				updateFileEntryFileName(fileEntryId, fileName);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateFileVersionFileName(
			long fileVersionId, String fileName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"update DLFileVersion set fileName = ? where " +
					"fileVersionId = ?");

			ps.setString(1, fileName);
			ps.setLong(2, fileVersionId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void updateFileVersionFileNames() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select fileVersionId, extension, title, from DLFileVersion");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileVersionId = rs.getLong("fileVersionId");
				String extension = GetterUtil.getString(
					rs.getString("extension"));
				String title = GetterUtil.getString(rs.getString("title"));

				String fileName = DLAppUtil.getSanitizedFileName(
					title, extension);

				updateFileVersionFileName(fileVersionId, fileName);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}