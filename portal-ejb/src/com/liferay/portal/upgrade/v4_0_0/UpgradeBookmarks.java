/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.upgrade.v4_0_0;

import com.liferay.portal.model.Group;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.util.Constants;
import com.liferay.portlet.bookmarks.service.spring.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.spring.BookmarksFolderLocalServiceUtil;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeBookmarks.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class UpgradeBookmarks extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeFolder();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeEntry(String folderId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_ENTRY);

			ps.setString(1, folderId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String entryId = rs.getString("entryId");

				boolean addCommunityPermissions = true;
				boolean addGuestPermissions = true;

				_log.debug("Upgrading entry " + entryId);

				BookmarksEntryLocalServiceUtil.addEntryResources(
					folderId, entryId, addCommunityPermissions,
					addGuestPermissions);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private void _upgradeFolder() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection(Constants.DATA_SOURCE);

			ps = con.prepareStatement(_UPGRADE_FOLDER);

			ps.setString(1, Group.DEFAULT_PARENT_GROUP_ID);

			rs = ps.executeQuery();

			while (rs.next()) {
				String folderId = rs.getString("folderId");

				boolean addCommunityPermissions = true;
				boolean addGuestPermissions = true;

				_log.debug("Upgrading folder " + folderId);

				BookmarksFolderLocalServiceUtil.addFolderResources(
					folderId, addCommunityPermissions, addGuestPermissions);

				_upgradeEntry(folderId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	public static final String _UPGRADE_ENTRY =
		"SELECT * FROM BookmarksEntry WHERE folderId = ?";

	public static final String _UPGRADE_FOLDER =
		"SELECT * FROM BookmarksFolder WHERE groupId != ?";

	private static Log _log = LogFactory.getLog(UpgradeBookmarks.class);

}