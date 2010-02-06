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

package com.liferay.portal.upgrade.v4_4_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeUUID.java.html"><b><i>View Source</i></b></a>
 *
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