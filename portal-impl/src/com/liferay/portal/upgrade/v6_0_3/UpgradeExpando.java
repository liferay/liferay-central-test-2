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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeExpando.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 */
public class UpgradeExpando extends UpgradeDLFileVersion {

	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select fileEntryId, folderId, groupId, name from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long fileEntryId = rs.getLong("fileEntryId");
				long folderId = rs.getLong("folderId");
				long groupId = rs.getLong("groupId");
				String name = rs.getString("name");

				long fileVersionId = getLatestDLFileVersionId(
					folderId, groupId, name);

				runSQL(
					"update ExpandoRow set classPK = " + fileVersionId +
						 " where classPK = " + fileEntryId);

				runSQL(
					"update ExpandoValue set classPK = " + fileVersionId +
						 " where classPK = " + fileEntryId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}