/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultPKMapper;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeDocumentLibrary.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeDocumentLibrary extends UpgradeProcess {

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

		// DLFolder

		PKUpgradeColumnImpl pkUpgradeColumn = new PKUpgradeColumnImpl(
			"folderId", true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			DLFolderImpl.TABLE_NAME, DLFolderImpl.TABLE_COLUMNS,
			pkUpgradeColumn);

		upgradeTable.updateTable();

		ValueMapper folderIdMapper = new DefaultPKMapper(
			pkUpgradeColumn.getValueMapper());

		UpgradeColumn upgradeParentFolderIdColumn = new SwapUpgradeColumnImpl(
			"parentFolderId", folderIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			DLFolderImpl.TABLE_NAME, DLFolderImpl.TABLE_COLUMNS,
			upgradeParentFolderIdColumn);

		upgradeTable.updateTable();

		UpgradeColumn upgradeFolderIdColumn = new SwapUpgradeColumnImpl(
			"folderId", folderIdMapper);

		UpgradeColumn upgradeToFolderIdColumn = new SwapUpgradeColumnImpl(
			"toFolderId", folderIdMapper);

		// DLFileEntry

		pkUpgradeColumn = new PKUpgradeColumnImpl("fileEntryId", true);

		upgradeTable = new DefaultUpgradeTableImpl(
			DLFileEntryImpl.TABLE_NAME, DLFileEntryImpl.TABLE_COLUMNS,
			pkUpgradeColumn, upgradeFolderIdColumn);

		upgradeTable.updateTable();

		ValueMapper entryIdMapper = pkUpgradeColumn.getValueMapper();

		// DLFileRank

		upgradeTable = new DefaultUpgradeTableImpl(
			DLFileRankImpl.TABLE_NAME, DLFileRankImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("fileRankId", false),
			upgradeFolderIdColumn);

		upgradeTable.updateTable();

		// DLFileShortcut

		upgradeTable = new DefaultUpgradeTableImpl(
			DLFileShortcutImpl.TABLE_NAME, DLFileShortcutImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("fileShortcutId", false),
			upgradeFolderIdColumn, upgradeToFolderIdColumn);

		upgradeTable.updateTable();

		// DLFileVersion

		upgradeTable = new DefaultUpgradeTableImpl(
			DLFileVersionImpl.TABLE_NAME, DLFileVersionImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("fileVersionId", false),
			upgradeFolderIdColumn);

		upgradeTable.updateTable();

		// Resource

		//ResourceUtil.upgradePrimKey(
		//	folderIdMapper, DLFolder.class.getName());
		//ResourceUtil.upgradePrimKey(
		//	entryIdMapper, DLFileEntry.class.getName());

		// Schema

		runSQL(_UPGRADE_SCHEMA);
	}

	private static final String[] _UPGRADE_SCHEMA = {
		"alter table DLFileEntry drop primary key",
		"alter table DLFileEntry add primary key (fileEntryId)",

		"alter table DLFileRank drop primary key",
		"alter table DLFileRank add primary key (fileRankId)",

		"alter table DLFileVersion drop primary key",
		"alter table DLFileVersion add primary key (fileVersionId)"
	};

	private static Log _log = LogFactory.getLog(UpgradeDocumentLibrary.class);

}