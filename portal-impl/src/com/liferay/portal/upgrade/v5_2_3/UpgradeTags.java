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

package com.liferay.portal.upgrade.v5_2_3;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.v5_2_3.util.TagsPropertyValueUpgradeColumnImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.tags.model.impl.TagsAssetImpl;
import com.liferay.portlet.tags.model.impl.TagsPropertyImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * <a href="UpgradeTags.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Samuel Kong
 *
 */
public class UpgradeTags extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void doUpgrade() throws Exception {
		if (isSupportsAlterColumnName()) {
			runSQL("alter_column_type TagsAsset title VARCHAR(255) null");
		}
		else {

			// TagsAsset

			UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
				TagsAssetImpl.TABLE_NAME, TagsAssetImpl.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(TagsAssetImpl.TABLE_SQL_CREATE);

			upgradeTable.updateTable();
		}

		updateViewCount();

		// TagsProperty

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			TagsPropertyImpl.TABLE_NAME, TagsPropertyImpl.TABLE_COLUMNS,
			new TagsPropertyValueUpgradeColumnImpl("value"));

		upgradeTable.setCreateSQL(TagsPropertyImpl.TABLE_SQL_CREATE);

		upgradeTable.updateTable();
	}

	protected void updateViewCount() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			long bookmarksEntryClassNameId = PortalUtil.getClassNameId(
				BookmarksEntry.class.getName());

			ps = con.prepareStatement(
				"update TagsAsset inner join BookmarksEntry on " +
					"TagsAsset.classPK = BookmarksEntry.entryId set " +
						"TagsAsset.viewCount = BookmarksEntry.visits WHERE " +
							"TagsAsset.classNameId = " +
								bookmarksEntryClassNameId);

			ps.executeUpdate();

			long dlFileEntryClassNameId = PortalUtil.getClassNameId(
				DLFileEntry.class.getName());

			ps = con.prepareStatement(
				"update TagsAsset inner join DLFileEntry on " +
					"TagsAsset.classPK = DLFileEntry.fileEntryId set " +
						"TagsAsset.viewCount = DLFileEntry.readCount WHERE " +
							"TagsAsset.classNameId = " +
								dlFileEntryClassNameId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeTags.class);

}