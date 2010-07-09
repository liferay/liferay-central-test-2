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

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.DefaultPKMapper;
import com.liferay.portal.kernel.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.ClassNameIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ClassPKContainer;
import com.liferay.portal.upgrade.v4_3_0.util.ClassPKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.GroupNameUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.GroupTable;
import com.liferay.portal.upgrade.v4_3_0.util.LayoutOwnerIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.LayoutPlidUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.LayoutSetTable;
import com.liferay.portal.upgrade.v4_3_0.util.LayoutTable;
import com.liferay.portal.upgrade.v4_3_0.util.OrgGroupPermissionTable;
import com.liferay.portal.upgrade.v4_3_0.util.OrgGroupRoleTable;
import com.liferay.portal.util.PortalUtil;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class UpgradeGroup extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// Group_

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"groupId", true);

		ClassNameIdUpgradeColumnImpl classNameIdColumn =
			new ClassNameIdUpgradeColumnImpl();

		Map<Long, ClassPKContainer> classPKContainers =
			new HashMap<Long, ClassPKContainer>();

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(Organization.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getOrganizationIdMapper(), true));

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(User.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getUserIdMapper(), false));

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(UserGroup.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getUserGroupIdMapper(), true));

		UpgradeColumn upgradeClassPKColumn = new ClassPKUpgradeColumnImpl(
			classNameIdColumn, classPKContainers);

		UpgradeColumn upgradeNameColumn = new GroupNameUpgradeColumnImpl(
			upgradePKColumn, upgradeClassPKColumn);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			GroupTable.TABLE_NAME, GroupTable.TABLE_COLUMNS, upgradePKColumn,
			classNameIdColumn, upgradeClassPKColumn, upgradeNameColumn);

		upgradeTable.setCreateSQL(GroupTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper groupIdMapper = new DefaultPKMapper(
			upgradePKColumn.getValueMapper());

		AvailableMappersUtil.setGroupIdMapper(groupIdMapper);

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", groupIdMapper);

		// Layout

		UpgradeColumn upgradeLayoutOwnerIdColumn =
			new TempUpgradeColumnImpl("ownerId");

		LayoutOwnerIdUpgradeColumnImpl upgradeLayoutOwnerIdGroupIdColumn =
			new LayoutOwnerIdUpgradeColumnImpl(
				"groupId", upgradeLayoutOwnerIdColumn, groupIdMapper);

		LayoutOwnerIdUpgradeColumnImpl upgradeLayoutOwnerIdPrivateLayoutColumn =
			new LayoutOwnerIdUpgradeColumnImpl(
				"privateLayout", upgradeLayoutOwnerIdColumn, groupIdMapper);

		UpgradeColumn upgradeLayoutIdColumn =
			new TempUpgradeColumnImpl("layoutId");

		PKUpgradeColumnImpl upgradeLayoutPlidColumn =
			new LayoutPlidUpgradeColumnImpl(
				upgradeLayoutOwnerIdColumn, upgradeLayoutOwnerIdGroupIdColumn,
				upgradeLayoutOwnerIdPrivateLayoutColumn, upgradeLayoutIdColumn);

		Object[][] layoutColumns1 = {{"ownerId", new Integer(Types.VARCHAR)}};
		Object[][] layoutColumns2 = LayoutTable.TABLE_COLUMNS.clone();

		Object[][] layoutColumns = ArrayUtil.append(
			layoutColumns1, layoutColumns2);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			LayoutTable.TABLE_NAME, layoutColumns, upgradeLayoutOwnerIdColumn,
			upgradeLayoutOwnerIdGroupIdColumn,
			upgradeLayoutOwnerIdPrivateLayoutColumn, upgradeLayoutIdColumn,
			upgradeLayoutPlidColumn);

		String createSQL = LayoutTable.TABLE_SQL_CREATE;

		createSQL =
			createSQL.substring(0, createSQL.length() - 1) +
				",ownerId VARCHAR(75) null)";

		upgradeTable.setCreateSQL(createSQL);

		upgradeTable.updateTable();

		ValueMapper layoutPlidMapper = upgradeLayoutPlidColumn.getValueMapper();

		AvailableMappersUtil.setLayoutPlidMapper(layoutPlidMapper);

		// LayoutSet

		Object[][] layoutSetColumns1 =
			{{"ownerId", new Integer(Types.VARCHAR)}};
		Object[][] layoutSetColumns2 = LayoutSetTable.TABLE_COLUMNS.clone();

		Object[][] layoutSetColumns = ArrayUtil.append(
			layoutSetColumns1, layoutSetColumns2);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			LayoutSetTable.TABLE_NAME, layoutSetColumns,
			new PKUpgradeColumnImpl("layoutSetId", false),
			upgradeGroupIdColumn);

		createSQL = LayoutSetTable.TABLE_SQL_CREATE;

		createSQL =
			createSQL.substring(0, createSQL.length() - 1) +
				",ownerId VARCHAR(75) null)";

		upgradeTable.setCreateSQL(createSQL);

		upgradeTable.updateTable();

		// OrgGroupPermission

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			OrgGroupPermissionTable.TABLE_NAME,
			OrgGroupPermissionTable.TABLE_COLUMNS, upgradeGroupIdColumn);

		upgradeTable.setCreateSQL(OrgGroupPermissionTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// OrgGroupRole

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			OrgGroupRoleTable.TABLE_NAME, OrgGroupRoleTable.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.setCreateSQL(OrgGroupRoleTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		// Schema

		runSQL(_UPGRADE_SCHEMA);
	}

	private static final String[] _UPGRADE_SCHEMA = {
		"alter table Layout drop column ownerId",

		"alter table LayoutSet drop column ownerId"
	};

}