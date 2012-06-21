/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v5_1_6;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.DateUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.v5_1_6.util.SocialActivityTable;
import com.liferay.portal.upgrade.v5_1_6.util.SocialRelationTable;
import com.liferay.portal.upgrade.v5_1_6.util.SocialRequestTable;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeSocial extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {

		// SocialActivity

		UpgradeColumn createDateColumn = new DateUpgradeColumnImpl(
			"createDate");
		UpgradeColumn modifiedDateColumn = new DateUpgradeColumnImpl(
			"modifiedDate");

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SocialActivityTable.TABLE_NAME, SocialActivityTable.TABLE_COLUMNS,
			createDateColumn);

		upgradeTable.setCreateSQL(SocialActivityTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(SocialActivityTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// SocialRelation

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SocialRelationTable.TABLE_NAME, SocialRelationTable.TABLE_COLUMNS,
			createDateColumn);

		upgradeTable.setCreateSQL(SocialRelationTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(SocialRelationTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// SocialRequest

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SocialRequestTable.TABLE_NAME, SocialRequestTable.TABLE_COLUMNS,
			createDateColumn, modifiedDateColumn);

		upgradeTable.setCreateSQL(SocialRequestTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(SocialRequestTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();
	}

}