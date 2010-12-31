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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.v4_4_0.util.DLFileEntryTable;
import com.liferay.portal.upgrade.v4_4_0.util.DLFileEntryTitleColumnImpl;
import com.liferay.portal.upgrade.v4_4_0.util.DLFolderNameColumnImpl;
import com.liferay.portal.upgrade.v4_4_0.util.DLFolderTable;

import java.util.Set;

/**
 * @author Alexander Chow
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// DLFolder

		UpgradeColumn groupIdColumn = new TempUpgradeColumnImpl("groupId");

		UpgradeColumn parentFolderIdColumn = new TempUpgradeColumnImpl(
			"parentFolderId");

		DLFolderNameColumnImpl dlFolderNameColumn = new DLFolderNameColumnImpl(
			groupIdColumn, parentFolderIdColumn);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFolderTable.TABLE_NAME, DLFolderTable.TABLE_COLUMNS,
			groupIdColumn, parentFolderIdColumn, dlFolderNameColumn);

		upgradeTable.updateTable();

		Set<String> distinctNames = dlFolderNameColumn.getDistintNames();

		// DLFileEntry

		UpgradeColumn folderIdColumn = new TempUpgradeColumnImpl("folderId");

		UpgradeColumn nameColumn = new TempUpgradeColumnImpl("name");

		BaseUpgradeColumnImpl dlFileEntryTitleColumn =
			new DLFileEntryTitleColumnImpl(
				groupIdColumn, folderIdColumn, nameColumn, distinctNames);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileEntryTable.TABLE_NAME, DLFileEntryTable.TABLE_COLUMNS,
			folderIdColumn, nameColumn, dlFileEntryTitleColumn);

		upgradeTable.updateTable();
	}

}