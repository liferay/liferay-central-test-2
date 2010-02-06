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

package com.liferay.portal.upgrade.v5_2_3;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.v5_2_3.util.TagsAssetTable;
import com.liferay.portal.upgrade.v5_2_3.util.TagsPropertyTable;
import com.liferay.portal.upgrade.v5_2_3.util.TagsPropertyValueUpgradeColumnImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 * <a href="UpgradeTags.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Samuel Kong
 */
public class UpgradeTags extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type TagsAsset title VARCHAR(255) null");
		}
		catch (Exception e) {

			// TagsAsset

			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				TagsAssetTable.TABLE_NAME, TagsAssetTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(TagsAssetTable.TABLE_SQL_CREATE);

			upgradeTable.updateTable();
		}

		updateAssetViewCount();

		// TagsProperty

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			TagsPropertyTable.TABLE_NAME, TagsPropertyTable.TABLE_COLUMNS,
			new TagsPropertyValueUpgradeColumnImpl("value"));

		upgradeTable.setCreateSQL(TagsPropertyTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();
	}

	protected void updateAssetViewCount() throws Exception {
		updateAssetViewCount(
			BookmarksEntry.class.getName(), "BookmarksEntry", "entryId",
			"visits");

		updateAssetViewCount(
			DLFileEntry.class.getName(), "DLFileEntry", "fileEntryId",
			"readCount");
	}

	protected void updateAssetViewCount(
			String className, String tableName, String columnClassPK,
			String columnViewCount)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(className);

		StringBundler sb = new StringBundler(12);

		sb.append("update TagsAsset set viewCount = (select ");
		sb.append(tableName);
		sb.append(".");
		sb.append(columnViewCount);
		sb.append(" from ");
		sb.append(tableName);
		sb.append(" where TagsAsset.classPK = ");
		sb.append(tableName);
		sb.append(".");
		sb.append(columnClassPK);
		sb.append(") where TagsAsset.classNameId = ");
		sb.append(classNameId);

		runSQL(sb.toString());
	}

}