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
import com.liferay.portal.upgrade.v4_3_0.util.IGFolderTable;
import com.liferay.portal.upgrade.v4_3_0.util.IGImageIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.IGImageTable;
import com.liferay.portal.upgrade.v4_3_0.util.IGLargeImageIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.IGSmallImageIdUpgradeColumnImpl;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeImageGallery extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// IGFolder

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
			IGFolderTable.TABLE_NAME, IGFolderTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(IGFolderTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(IGFolderTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper folderIdMapper = new DefaultPKMapper(
			upgradePKColumn.getValueMapper());

		AvailableMappersUtil.setIGFolderIdMapper(folderIdMapper);

		UpgradeColumn upgradeParentFolderIdColumn = new SwapUpgradeColumnImpl(
			"parentFolderId", folderIdMapper);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			IGFolderTable.TABLE_NAME, IGFolderTable.TABLE_COLUMNS,
			upgradeParentFolderIdColumn);

		upgradeTable.updateTable();

		UpgradeColumn upgradeFolderIdColumn = new SwapUpgradeColumnImpl(
			"folderId", folderIdMapper);

		// IGImage

		PKUpgradeColumnImpl upgradeImageIdColumn =
			new IGImageIdUpgradeColumnImpl(upgradeCompanyIdColumn);

		UpgradeColumn upgradeSmallImageIdColumn =
			new IGSmallImageIdUpgradeColumnImpl(
				upgradeCompanyIdColumn, upgradeImageIdColumn,
				AvailableMappersUtil.getImageIdMapper());

		UpgradeColumn upgradeLargeImageIdColumn =
			new IGLargeImageIdUpgradeColumnImpl(
				upgradeCompanyIdColumn, upgradeImageIdColumn,
				AvailableMappersUtil.getImageIdMapper());

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			IGImageTable.TABLE_NAME, IGImageTable.TABLE_COLUMNS,
			upgradeCompanyIdColumn, upgradeImageIdColumn, upgradeUserIdColumn,
			upgradeFolderIdColumn, upgradeSmallImageIdColumn,
			upgradeLargeImageIdColumn);

		upgradeTable.setCreateSQL(IGImageTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(IGImageTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper imageIdMapper = upgradeImageIdColumn.getValueMapper();

		AvailableMappersUtil.setIGImageIdMapper(imageIdMapper);
	}

}