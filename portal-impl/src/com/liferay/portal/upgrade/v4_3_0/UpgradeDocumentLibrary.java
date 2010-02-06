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
 * <a href="UpgradeDocumentLibrary.java.html"><b><i>View Source</i></b></a>
 *
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

		upgradeTable.updateTable();

		ValueMapper fileEntryIdMapper = fileEntryIdColumn.getValueMapper();

		AvailableMappersUtil.setDLFileEntryIdMapper(fileEntryIdMapper);

		// DLFileRank

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileRankTable.TABLE_NAME, DLFileRankTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("fileRankId", false),
			upgradeUserIdColumn, upgradeFolderIdColumn);

		upgradeTable.setCreateSQL(DLFileRankTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// DLFileShortcut

		upgradePKColumn = new PKUpgradeColumnImpl("fileShortcutId", true);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileShortcutTable.TABLE_NAME, DLFileShortcutTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeUserIdColumn, upgradeFolderIdColumn,
			upgradeToFolderIdColumn);

		upgradeTable.setCreateSQL(DLFileShortcutTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper fileShortcutIdMapper = upgradePKColumn.getValueMapper();

		AvailableMappersUtil.setDLFileShortcutIdMapper(fileShortcutIdMapper);

		// DLFileVersion

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			DLFileVersionTable.TABLE_NAME, DLFileVersionTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("fileVersionId", false),
			upgradeUserIdColumn, upgradeFolderIdColumn);

		upgradeTable.setCreateSQL(DLFileVersionTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();
	}

}