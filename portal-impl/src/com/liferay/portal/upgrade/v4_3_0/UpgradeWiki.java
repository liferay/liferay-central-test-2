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
import com.liferay.portal.kernel.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.WikiNodeTable;
import com.liferay.portal.upgrade.v4_3_0.util.WikiPageIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.WikiPageResourcePrimKeyUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.WikiPageTable;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeWiki extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// WikiNode

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"nodeId", true);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			WikiNodeTable.TABLE_NAME, WikiNodeTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(WikiNodeTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(WikiNodeTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper nodeIdMapper = upgradePKColumn.getValueMapper();

		AvailableMappersUtil.setWikiNodeIdMapper(nodeIdMapper);

		UpgradeColumn upgradeNodeIdColumn = new SwapUpgradeColumnImpl(
			"nodeId", nodeIdMapper);

		// WikiPage

		UpgradeColumn upgradeTitleColumn = new TempUpgradeColumnImpl("title");

		WikiPageIdUpgradeColumnImpl upgradePageIdColumn =
			new WikiPageIdUpgradeColumnImpl(
				upgradeNodeIdColumn, upgradeTitleColumn);

		UpgradeColumn upgradePageResourcePrimKeyColumn =
			new WikiPageResourcePrimKeyUpgradeColumnImpl(
				upgradePageIdColumn);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			WikiPageTable.TABLE_NAME, WikiPageTable.TABLE_COLUMNS,
			upgradeNodeIdColumn, upgradeTitleColumn, upgradePageIdColumn,
			upgradePageResourcePrimKeyColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(WikiPageTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(WikiPageTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper pageIdMapper = upgradePageIdColumn.getValueMapper();

		AvailableMappersUtil.setWikiPageIdMapper(pageIdMapper);
	}

}