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

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.DefaultPKMapper;
import com.liferay.portal.kernel.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.BookmarksEntryTable;
import com.liferay.portal.upgrade.v4_3_0.util.BookmarksFolderTable;

import java.sql.Types;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class UpgradeBookmarks extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// BookmarksFolder

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"folderId", true);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			BookmarksFolderTable.TABLE_NAME, BookmarksFolderTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(BookmarksFolderTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(BookmarksFolderTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper folderIdMapper = new DefaultPKMapper(
			upgradePKColumn.getValueMapper());

		AvailableMappersUtil.setBookmarksFolderIdMapper(folderIdMapper);

		UpgradeColumn upgradeParentFolderIdColumn = new SwapUpgradeColumnImpl(
			"parentFolderId", folderIdMapper);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			BookmarksFolderTable.TABLE_NAME, BookmarksFolderTable.TABLE_COLUMNS,
			upgradeParentFolderIdColumn);

		upgradeTable.updateTable();

		UpgradeColumn upgradeFolderIdColumn = new SwapUpgradeColumnImpl(
			"folderId", folderIdMapper);

		// BookmarksEntry

		upgradePKColumn = new PKUpgradeColumnImpl("entryId", true);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			BookmarksEntryTable.TABLE_NAME, BookmarksEntryTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeFolderIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(BookmarksEntryTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(BookmarksEntryTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper entryIdMapper = upgradePKColumn.getValueMapper();

		AvailableMappersUtil.setBookmarksEntryIdMapper(entryIdMapper);
	}

}