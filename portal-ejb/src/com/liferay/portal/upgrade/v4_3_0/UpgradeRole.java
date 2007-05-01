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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.impl.OrgGroupRoleImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.ResourceUtil;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeRole.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeRole extends UpgradeProcess {

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

		// Role

		PKUpgradeColumnImpl pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			RoleImpl.TABLE_NAME, RoleImpl.TABLE_COLUMNS, pkUpgradeColumn);

		upgradeTable.updateTable();

		ValueMapper roleIdMapper = pkUpgradeColumn.getValueMapper();

		UpgradeColumn upgradeRoleIdColumn = new SwapUpgradeColumnImpl(
			"roleId", roleIdMapper);

		// Groups_Roles

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_ROLES, _COLUMNS_GROUPS_ROLES, upgradeRoleIdColumn);

		upgradeTable.updateTable();

		// OrgGroupRole

		upgradeTable = new DefaultUpgradeTableImpl(
			OrgGroupRoleImpl.TABLE_NAME, OrgGroupRoleImpl.TABLE_COLUMNS,
			upgradeRoleIdColumn);

		// Roles_Permissions

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_ROLES_PERMISSIONS, _COLUMNS_ROLES_PERMISSIONS,
			upgradeRoleIdColumn);

		upgradeTable.updateTable();

		// Users_Roles

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_ROLES, _COLUMNS_USERS_ROLES, upgradeRoleIdColumn);

		upgradeTable.updateTable();

		// Resource

		ResourceUtil.upgradePrimKey(roleIdMapper, Role.class.getName());

		// Counter

		CounterLocalServiceUtil.reset(Role.class.getName());
	}

	private static final String _TABLE_GROUPS_ROLES = "Groups_Roles";

	private static final String _TABLE_ROLES_PERMISSIONS = "Roles_Permissions";

	private static final String _TABLE_USERS_ROLES = "Users_Roles";

	private static final Object[][] _COLUMNS_GROUPS_ROLES = {
		{"groupId", new Integer(Types.BIGINT)},
		{"roleId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_ROLES_PERMISSIONS = {
		{"roleId", new Integer(Types.BIGINT)},
		{"permissionId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_ROLES = {
		{"userId", new Integer(Types.BIGINT)},
		{"roleId", new Integer(Types.BIGINT)}
	};

	private static Log _log = LogFactory.getLog(UpgradeGroup.class);

}