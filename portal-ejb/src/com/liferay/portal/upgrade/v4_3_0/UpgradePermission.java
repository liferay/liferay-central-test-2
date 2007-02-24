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

import com.liferay.portal.model.impl.PermissionImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradePermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class UpgradePermission extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradePermission();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradePermission() throws Exception {

		// Resource

		PKUpgradeColumnImpl pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			ResourceImpl.TABLE_NAME, ResourceImpl.TABLE_COLUMNS,
			pkUpgradeColumn);

		upgradeTable.updateTable();

		// Permission

		ValueMapper resourcePKMapper = pkUpgradeColumn.getValueMapper();

		pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		upgradeTable = new DefaultUpgradeTableImpl(
			PermissionImpl.TABLE_NAME, PermissionImpl.TABLE_COLUMNS,
			pkUpgradeColumn,
			new SwapUpgradeColumnImpl("resourceId", resourcePKMapper));

		upgradeTable.updateTable();

		ValueMapper permissionPKMapper = pkUpgradeColumn.getValueMapper();

		// Groups_Permissions

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_PERMISSIONS, _COLUMNS_GROUP_PERMISSIONS,
			new SwapUpgradeColumnImpl("permissionId", permissionPKMapper));

		upgradeTable.updateTable();

		// Roles_Permissions

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_ROLES_PERMISSIONS, _COLUMNS_ROLES_PERMISSIONS,
			new SwapUpgradeColumnImpl("permissionId", permissionPKMapper));

		upgradeTable.updateTable();

		// Users_Permissions

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_PERMISSIONS, _COLUMNS_USERS_PERMISSIONS,
			new SwapUpgradeColumnImpl("permissionId", permissionPKMapper));

		upgradeTable.updateTable();

		// OrgGroupPermission

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_ORG_GROUP_PERMISSION, _COLUMNS_ORG_GROUP_PERMISSION,
			new SwapUpgradeColumnImpl("permissionId", permissionPKMapper));

		upgradeTable.updateTable();
	}

	private static final String _TABLE_GROUPS_PERMISSIONS =
		"Groups_Permissions";

	private static final String _TABLE_ROLES_PERMISSIONS =
		"Roles_Permissions";

	private static final String _TABLE_USERS_PERMISSIONS =
		"Users_Permissions";

	private static final String _TABLE_ORG_GROUP_PERMISSION =
		"OrgGroupPermission";

	private static final Object[][] _COLUMNS_GROUP_PERMISSIONS = {
		{"groupId", new Integer(Types.BIGINT)},
		{"permissionId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_ROLES_PERMISSIONS = {
		{"roleId", new Integer(Types.VARCHAR)},
		{"permissionId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_PERMISSIONS = {
		{"userId", new Integer(Types.VARCHAR)},
		{"permissionId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_ORG_GROUP_PERMISSION = {
		{"organizationId", new Integer(Types.VARCHAR)},
		{"groupId", new Integer(Types.BIGINT)},
		{"permissionId", new Integer(Types.BIGINT)}
	};

	private static Log _log = LogFactory.getLog(UpgradePermission.class);

}