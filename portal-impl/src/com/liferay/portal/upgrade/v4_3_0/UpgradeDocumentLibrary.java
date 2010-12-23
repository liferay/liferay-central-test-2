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

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.DefaultPKMapper;
import com.liferay.portal.kernel.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.DLFileEntryIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.DLFileEntryTable;
import com.liferay.portal.upgrade.v4_3_0.util.DLFileRankTable;
import com.liferay.portal.upgrade.v4_3_0.util.DLFileShortcutTable;
import com.liferay.portal.upgrade.v4_3_0.util.DLFileVersionTable;
import com.liferay.portal.upgrade.v4_3_0.util.DLFolderTable;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// DLFolder

		UpgradeColumn upgradeCompanyIdColumn = new SwapUpgradeColumnImpl(
			"companyId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getCompanyIdMapper());

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"folderId", true);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFolderTable.TABLE_NAME, DLFolderTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(DLFolderTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(DLFolderTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper folderIdMapper = new DefaultPKMapper(
			upgradePKColumn.getValueMapper());

		AvailableMappersUtil.setDLFolderIdMapper(folderIdMapper);

		UpgradeColumn upgradeParentFolderIdColumn = new SwapUpgradeColumnImpl(
			"parentFolderId", folderIdMapper);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFolderTable.TABLE_NAME, DLFolderTable.TABLE_COLUMNS,
			upgradeParentFolderIdColumn);

		upgradeTable.updateTable();

		UpgradeColumn upgradeFolderIdColumn = new SwapUpgradeColumnImpl(
			"folderId", folderIdMapper);

		UpgradeColumn upgradeToFolderIdColumn = new SwapUpgradeColumnImpl(
			"toFolderId", folderIdMapper);

		// DLFileEntry

		UpgradeColumn upgradeNameColumn = new TempUpgradeColumnImpl("name");

		PKUpgradeColumnImpl fileEntryIdColumn =
			new DLFileEntryIdUpgradeColumnImpl(
				upgradeCompanyIdColumn, upgradeFolderIdColumn,
				upgradeNameColumn);

		UpgradeColumn upgradeVersionUserIdColumn = new SwapUpgradeColumnImpl(
			"versionUserId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileEntryTable.TABLE_NAME, DLFileEntryTable.TABLE_COLUMNS,
			upgradeCompanyIdColumn, upgradeFolderIdColumn, upgradeNameColumn,
			fileEntryIdColumn, upgradeUserIdColumn, upgradeVersionUserIdColumn);

		upgradeTable.setCreateSQL(DLFileEntryTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(DLFileEntryTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper fileEntryIdMapper = fileEntryIdColumn.getValueMapper();

		AvailableMappersUtil.setDLFileEntryIdMapper(fileEntryIdMapper);

		// DLFileRank

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileRankTable.TABLE_NAME, DLFileRankTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("fileRankId", false),
			upgradeUserIdColumn, upgradeFolderIdColumn);

		upgradeTable.setCreateSQL(DLFileRankTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(DLFileRankTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// DLFileShortcut

		upgradePKColumn = new PKUpgradeColumnImpl("fileShortcutId", true);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileShortcutTable.TABLE_NAME, DLFileShortcutTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeUserIdColumn, upgradeFolderIdColumn,
			upgradeToFolderIdColumn);

		upgradeTable.setCreateSQL(DLFileShortcutTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(DLFileShortcutTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper fileShortcutIdMapper = upgradePKColumn.getValueMapper();

		AvailableMappersUtil.setDLFileShortcutIdMapper(fileShortcutIdMapper);

		// DLFileVersion

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileVersionTable.TABLE_NAME, DLFileVersionTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("fileVersionId", false),
			upgradeUserIdColumn, upgradeFolderIdColumn);

		upgradeTable.setCreateSQL(DLFileVersionTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(DLFileVersionTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();
	}

}