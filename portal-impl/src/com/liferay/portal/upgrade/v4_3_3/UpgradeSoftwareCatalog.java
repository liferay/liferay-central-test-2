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

package com.liferay.portal.upgrade.v4_3_3;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.v4_3_3.util.SCFrameworkVersionTable;
import com.liferay.portal.upgrade.v4_3_3.util.SCLicenseTable;
import com.liferay.portal.upgrade.v4_3_3.util.SCProductEntryTable;
import com.liferay.portal.upgrade.v4_3_3.util.SCProductVersionTable;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeSoftwareCatalog extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// SCFrameworkVersion

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SCFrameworkVersionTable.TABLE_NAME,
			SCFrameworkVersionTable.TABLE_COLUMNS);

		upgradeTable.setCreateSQL(SCFrameworkVersionTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// SCLicense

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SCLicenseTable.TABLE_NAME, SCLicenseTable.TABLE_COLUMNS);

		upgradeTable.setCreateSQL(SCLicenseTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// SCProductEntry

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SCProductEntryTable.TABLE_NAME, SCProductEntryTable.TABLE_COLUMNS);

		upgradeTable.setCreateSQL(SCProductEntryTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// SCProductVersion

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			SCProductVersionTable.TABLE_NAME,
			SCProductVersionTable.TABLE_COLUMNS);

		upgradeTable.setCreateSQL(SCProductVersionTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();
	}

}