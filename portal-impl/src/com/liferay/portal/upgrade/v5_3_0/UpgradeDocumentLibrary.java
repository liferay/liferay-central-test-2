/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v5_3_0;

import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.upgrade.v5_3_0.util.DLFileEntryNameUpgradeColumnImpl;
import com.liferay.portal.upgrade.v5_3_0.util.DLFileEntryTitleUpgradeColumnImpl;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Date;

/**
 * <a href="UpgradeDocumentLibrary.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Alexander Chow
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

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

				addFileVersion(userId, groupId, folderId, name, version, size);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		// DLFileEntry

		UpgradeColumn nameColumn = new DLFileEntryNameUpgradeColumnImpl("name");
		UpgradeColumn titleColumn = new DLFileEntryTitleUpgradeColumnImpl(
			nameColumn, "title");

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileEntryImpl.TABLE_NAME, DLFileEntryImpl.TABLE_COLUMNS,
			nameColumn, titleColumn);

		upgradeTable.updateTable();

		// DLFileRank

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileRankImpl.TABLE_NAME, DLFileRankImpl.TABLE_COLUMNS,
			nameColumn);

		upgradeTable.updateTable();

		// DLFileShortcut

		UpgradeColumn toNameColumn = new DLFileEntryNameUpgradeColumnImpl(
			"toName");

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileShortcutImpl.TABLE_NAME, DLFileShortcutImpl.TABLE_COLUMNS,
			toNameColumn);

		upgradeTable.updateTable();

		// DLFileVersion

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileVersionImpl.TABLE_NAME, DLFileVersionImpl.TABLE_COLUMNS,
			nameColumn);

		upgradeTable.updateTable();
	}

	protected void addFileVersion(
		long userId, long groupId, long folderId, String fileName,
		double version, int size) {

		try {
			User user = UserLocalServiceUtil.getUser(userId);
			Date now = new Date();

			long fileVersionId = increment();

			DLFileVersion fileVersion = new DLFileVersionImpl();

			fileVersion.setNew(true);
			fileVersion.setPrimaryKey(fileVersionId);
			fileVersion.setGroupId(groupId);
			fileVersion.setCompanyId(user.getCompanyId());
			fileVersion.setUserId(user.getUserId());
			fileVersion.setUserName(user.getFullName());
			fileVersion.setCreateDate(now);
			fileVersion.setFolderId(folderId);
			fileVersion.setName(fileName);
			fileVersion.setVersion(version);
			fileVersion.setSize(size);
			fileVersion.setStatus(StatusConstants.APPROVED);
			fileVersion.setStatusByUserId(user.getUserId());
			fileVersion.setStatusByUserName(user.getFullName());
			fileVersion.setStatusDate(now);

			DLFileVersionLocalServiceUtil.addDLFileVersion(fileVersion);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Version " + version + " for " + fileName +
						" was not created: " + e.getMessage());
			}
		}
	}

	private static Log _log =
		LogFactoryUtil.getLog(UpgradeDocumentLibrary.class);

}