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
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.RoleTable;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeRole extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// Role

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"roleId", true);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			RoleTable.TABLE_NAME, RoleTable.TABLE_COLUMNS, upgradePKColumn);

		upgradeTable.setCreateSQL(RoleTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(RoleTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper roleIdMapper = upgradePKColumn.getValueMapper();

		AvailableMappersUtil.setRoleIdMapper(roleIdMapper);
	}

}