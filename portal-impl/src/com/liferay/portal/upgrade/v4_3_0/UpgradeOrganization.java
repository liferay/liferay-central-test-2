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
import com.liferay.portal.upgrade.v4_3_0.util.OrgGroupPermissionTable;
import com.liferay.portal.upgrade.v4_3_0.util.OrgLaborTable;
import com.liferay.portal.upgrade.v4_3_0.util.OrganizationTable;
import com.liferay.portal.upgrade.v4_3_0.util.ValueMapperUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeOrganization extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// Organization

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"organizationId", true);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			OrganizationTable.TABLE_NAME, OrganizationTable.TABLE_COLUMNS,
			upgradePKColumn);

		upgradeTable.setCreateSQL(OrganizationTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(OrganizationTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper organizationIdMapper = new DefaultPKMapper(
			upgradePKColumn.getValueMapper());

		AvailableMappersUtil.setOrganizationIdMapper(organizationIdMapper);

		UpgradeColumn upgradeParentOrganizationIdColumn =
			new SwapUpgradeColumnImpl(
				"parentOrganizationId", organizationIdMapper);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			OrganizationTable.TABLE_NAME, OrganizationTable.TABLE_COLUMNS,
			upgradeParentOrganizationIdColumn);

		upgradeTable.updateTable();

		UpgradeColumn upgradeOrganizationIdColumn = new SwapUpgradeColumnImpl(
			"organizationId", organizationIdMapper);

		// OrgGroupPermission

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			OrgGroupPermissionTable.TABLE_NAME,
			OrgGroupPermissionTable.TABLE_COLUMNS, upgradeOrganizationIdColumn);

		upgradeTable.setCreateSQL(OrgGroupPermissionTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(
			OrgGroupPermissionTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// OrgLabor

		upgradePKColumn = new PKUpgradeColumnImpl("orgLaborId", true);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			OrgLaborTable.TABLE_NAME, OrgLaborTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeOrganizationIdColumn);

		upgradeTable.setCreateSQL(OrgLaborTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(OrgLaborTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapperUtil.persist(
			upgradePKColumn.getValueMapper(), "org-labor-id");
	}

}