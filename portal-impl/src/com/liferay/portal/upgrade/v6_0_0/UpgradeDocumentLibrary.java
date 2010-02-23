/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileEntryNameUpgradeColumnImpl;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileEntryTable;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileEntryTitleUpgradeColumnImpl;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileEntryVersionUpgradeColumnImpl;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileRankTable;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileShortcutTable;
import com.liferay.portal.upgrade.v6_0_0.util.DLFileVersionTable;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * <a href="UpgradeDocumentLibrary.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Alexander Chow
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
			con = DataAccess.getConnection();

			StringBundler sb = new StringBundler(5);

			sb.append("insert into DLFileVersion (fileVersionId, groupId, ");
			sb.append("companyId, userId, userName, createDate, folderId, ");
			sb.append("name, version, size_, status, statusByUserId, ");
			sb.append("statusByUserName, statusDate) values (?, ?, ?, ?, ?, ");
			sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?)");

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
			ps.setDouble(9, version);
			ps.setInt(10, size);
			ps.setInt(11, StatusConstants.APPROVED);
			ps.setLong(12, userId);
			ps.setString(13, userName);
			ps.setTimestamp(14, now);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

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

				String portletId = PortletKeys.DOCUMENT_LIBRARY;
				long repositoryId = folderId;

				if (repositoryId ==
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

					repositoryId = groupId;
				}

				String newName = DLFileEntryNameUpgradeColumnImpl.getNewName(
					name);

				if (!newName.equals(name)) {
					DLServiceUtil.updateFile(
						companyId, portletId, groupId, repositoryId, name,
						newName, false);
				}

				addFileVersion(
					groupId, companyId, userId, userName, folderId, name,
					version, size);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		// DLFileEntry

		UpgradeColumn nameColumn = new DLFileEntryNameUpgradeColumnImpl("name");
		UpgradeColumn titleColumn = new DLFileEntryTitleUpgradeColumnImpl(
			nameColumn, "title");
		UpgradeColumn versionColumn = new DLFileEntryVersionUpgradeColumnImpl(
			"version");

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileEntryTable.TABLE_NAME, DLFileEntryTable.TABLE_COLUMNS,
			nameColumn, titleColumn, versionColumn);

		upgradeTable.setCreateSQL(DLFileEntryTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// DLFileRank

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileRankTable.TABLE_NAME, DLFileRankTable.TABLE_COLUMNS,
			nameColumn);

		upgradeTable.setCreateSQL(DLFileRankTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// DLFileShortcut

		UpgradeColumn toNameColumn = new DLFileEntryNameUpgradeColumnImpl(
			"toName");

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileShortcutTable.TABLE_NAME, DLFileShortcutTable.TABLE_COLUMNS,
			toNameColumn);

		upgradeTable.setCreateSQL(DLFileShortcutTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// DLFileVersion

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileVersionTable.TABLE_NAME, DLFileVersionTable.TABLE_COLUMNS,
			nameColumn, versionColumn);

		upgradeTable.setCreateSQL(DLFileVersionTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();
	}

}