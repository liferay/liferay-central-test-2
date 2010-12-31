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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeUUID extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		updateTable("BlogsEntry", "entryId");
		updateTable("BookmarksEntry", "entryId");
		updateTable("BookmarksFolder", "folderId");
		updateTable("DLFileEntry", "fileEntryId");
		updateTable("DLFileShortcut", "fileShortcutId");
		updateTable("DLFolder", "folderId");
		updateTable("CalEvent", "eventId");
		updateTable("IGFolder", "folderId");
		updateTable("IGImage", "imageId");
		updateTable("JournalArticle", "id_");
		updateTable("JournalStructure", "id_");
		updateTable("JournalTemplate", "id_");
		updateTable("MBCategory", "categoryId");
		updateTable("MBMessage", "messageId");
		updateTable("PollsChoice", "choiceId");
		updateTable("PollsQuestion", "questionId");
		updateTable("User_", "userId");
		updateTable("WikiNode", "nodeId");
		updateTable("WikiPage", "pageId");
	}

	protected void updateTable(String tableName, String pkColName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select " + pkColName + " from " + tableName +
					" where uuid_ IS NULL or uuid_ = ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long pkColValue = rs.getLong(pkColName);

				String uuid = PortalUUIDUtil.generate();

				runSQL(
					"update " + tableName + " set uuid_ = '" + uuid +
						"' where " + pkColName + " = " + pkColValue);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}