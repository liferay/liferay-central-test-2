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
 * <a href="UpgradeDocumentLibrary.java.html"><b><i>View Source</i></b></a>
 *
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