/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v5_2_8;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	protected void addFileVersion(
			long groupId, long companyId, long userId, String userName,
			long folderId, String name, double version, int size)
		throws Exception {

		Timestamp now = new Timestamp(System.currentTimeMillis());

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			StringBundler sb = new StringBundler(4);

			sb.append("insert into DLFileVersion (fileVersionId, groupId, ");
			sb.append("companyId, userId, userName, createDate, folderId, ");
			sb.append("name, description, version, size_) values (?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?)");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setLong(1, increment());
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, now);
			ps.setLong(7, folderId);
			ps.setString(8, name);
			ps.setString(9, StringPool.BLANK);
			ps.setDouble(10, version);
			ps.setInt(11, size);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from DLFileEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long groupId = rs.getLong("groupId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				long folderId = rs.getLong("folderId");
				String name = rs.getString("name");
				double version = rs.getDouble("version");
				int size = rs.getInt("size_");

				addFileVersion(
					groupId, companyId, userId, userName, folderId, name,
					version, size);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}