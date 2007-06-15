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

import com.liferay.portal.tools.util.DBUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeMappingTables.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeMappingTables extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgrade() throws Exception {

		// Mappings

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeOrganizationIdColumn = new SwapUpgradeColumnImpl(
			"organizationId", AvailableMappersUtil.getOrganizationIdMapper());

		UpgradeColumn upgradeRoleIdColumn = new SwapUpgradeColumnImpl(
			"roleId", AvailableMappersUtil.getRoleIdMapper());

		UpgradeColumn upgradeUserGroupIdColumn = new SwapUpgradeColumnImpl(
			"userGroupId", AvailableMappersUtil.getUserGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		// Groups_Orgs

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_ORGS, _COLUMNS_GROUPS_ORGS, upgradeGroupIdColumn,
			upgradeOrganizationIdColumn);

		upgradeTable.updateTable();

		// Groups_Roles

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_ROLES, _COLUMNS_GROUPS_ROLES,
			upgradeGroupIdColumn, upgradeRoleIdColumn);

		upgradeTable.updateTable();

		// Groups_UserGroups

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_USERGROUPS, _COLUMNS_GROUPS_USERGROUPS,
			upgradeGroupIdColumn, upgradeUserGroupIdColumn);

		upgradeTable.updateTable();

		// Roles_Permissions

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_ROLES_PERMISSIONS, _COLUMNS_ROLES_PERMISSIONS,
			upgradeRoleIdColumn);

		upgradeTable.updateTable();

		// Users_Groups

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_GROUPS, _COLUMNS_USERS_GROUPS, upgradeUserIdColumn,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// Users_Orgs

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_ORGS, _COLUMNS_USERS_ORGS, upgradeUserIdColumn,
			upgradeOrganizationIdColumn);

		upgradeTable.updateTable();

		// Users_Permissions

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_PERMISSIONS, _COLUMNS_USERS_PERMISSIONS,
			upgradeUserIdColumn);

		upgradeTable.updateTable();

		// Users_Roles

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_ROLES, _COLUMNS_USERS_ROLES, upgradeUserIdColumn,
			upgradeRoleIdColumn);

		upgradeTable.updateTable();

		// Users_UserGroups

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_USERGROUPS, _COLUMNS_USERS_USERGROUPS,
			upgradeUserIdColumn, upgradeUserGroupIdColumn);

		upgradeTable.updateTable();

		// Schema

		DBUtil.getInstance().executeSQL(_UPGRADE_SCHEMA);
	}

	private static final String _TABLE_GROUPS_ORGS = "Groups_Orgs";

	private static final String _TABLE_GROUPS_ROLES = "Groups_Roles";

	private static final String _TABLE_GROUPS_USERGROUPS = "Groups_UserGroups";

	private static final String _TABLE_ROLES_PERMISSIONS = "Roles_Permissions";

	private static final String _TABLE_USERS_GROUPS = "Users_Groups";

	private static final String _TABLE_USERS_ORGS = "Users_Orgs";

	private static final String _TABLE_USERS_PERMISSIONS = "Users_Permissions";

	private static final String _TABLE_USERS_ROLES = "Users_Roles";

	private static final String _TABLE_USERS_USERGROUPS = "Users_UserGroups";

	private static final Object[][] _COLUMNS_GROUPS_ORGS = {
		{"groupId", new Integer(Types.BIGINT)},
		{"organizationId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_GROUPS_ROLES = {
		{"groupId", new Integer(Types.BIGINT)},
		{"roleId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_GROUPS_USERGROUPS = {
		{"groupId", new Integer(Types.BIGINT)},
		{"userGroupId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_ROLES_PERMISSIONS = {
		{"roleId", new Integer(Types.BIGINT)},
		{"permissionId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_GROUPS = {
		{"userId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_ORGS = {
		{"userId", new Integer(Types.BIGINT)},
		{"organizationId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_PERMISSIONS = {
		{"userId", new Integer(Types.BIGINT)},
		{"permissionId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_ROLES = {
		{"userId", new Integer(Types.BIGINT)},
		{"roleId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_USERGROUPS = {
		{"userId", new Integer(Types.BIGINT)},
		{"userGroupId", new Integer(Types.BIGINT)}
	};

	private static final String[] _UPGRADE_SCHEMA = {
		"alter_column_type Users_Groups userId LONG",
		"alter_column_type Users_Orgs userId LONG",
		"alter_column_type Users_Permissions userId LONG",
		"alter_column_type Users_Roles userId LONG",
		"alter_column_type Users_UserGroups userId LONG"
	};

	private static Log _log = LogFactory.getLog(UpgradeAddress.class);

}