/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.LongPKUpgradeTableImpl;
import com.liferay.portal.upgrade.util.MapUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeTable;
import com.liferay.portal.upgrade.util.UpgradeTable;

import java.sql.Types;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradePermissions.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class UpgradePermissions extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradePermission();
			_upgradeResource();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradePermission() throws Exception {

		// Handle permissions table

		PKUpgradeTable upgradeTable = new LongPKUpgradeTableImpl(
			_TABLE_PERMISSION, _COLUMNS_PERMISSION, true);

		upgradeTable.updateTable();

		Map permissionIdMap = upgradeTable.getPKMap();

		// Handle mapping tables

		UpgradeTable groupMap = new MapUpgradeTableImpl(
			_TABLE_GROUPS_PERMISSIONS, _COLUMNS_GROUP_PERMISSIONS,
			permissionIdMap);

		groupMap.updateTable();

		UpgradeTable roleMap = new MapUpgradeTableImpl(
			_TABLE_ROLES_PERMISSIONS, _COLUMNS_ROLES_PERMISSIONS,
			permissionIdMap);

		roleMap.updateTable();

		UpgradeTable userMap = new MapUpgradeTableImpl(
			_TABLE_USERS_PERMISSIONS, _COLUMNS_USERS_PERMISSIONS,
			permissionIdMap);

		userMap.updateTable();

		UpgradeTable orgGroupMap = new MapUpgradeTableImpl(
			_TABLE_ORG_GROUP_PERMISSION, _COLUMNS_ORG_GROUP_PERMISSION,
			permissionIdMap);

		orgGroupMap.updateTable();
	}

	private void _upgradeResource() throws Exception {
		PKUpgradeTable upgradeTable = new LongPKUpgradeTableImpl(
			_TABLE_RESOURCE, _COLUMNS_RESOURCE, true);

		upgradeTable.updateTable();

		Map resourceIdMap = upgradeTable.getPKMap();

		Iterator itr = resourceIdMap.keySet().iterator();

		while (itr.hasNext()) {
			Long oldId = (Long)itr.next();
			Long newId = (Long)resourceIdMap.get(oldId);

			PermissionLocalServiceUtil.updateResourceId(
				oldId.longValue(), newId.longValue());
		}
	}

	private static final String _TABLE_PERMISSION = "Permission_";

	private static final String _TABLE_RESOURCE = "Resource_";

	private static final String _TABLE_GROUPS_PERMISSIONS =
		"Groups_Permissions";

	private static final String _TABLE_ROLES_PERMISSIONS =
		"Roles_Permissions";

	private static final String _TABLE_USERS_PERMISSIONS =
		"Users_Permissions";

	private static final String _TABLE_ORG_GROUP_PERMISSION =
		"OrgGroupPermission";

	private static final Object[][] _COLUMNS_PERMISSION = {
		{"permissionId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.VARCHAR)},
		{"actionId", new Integer(Types.VARCHAR)},
		{"resourceId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_RESOURCE = {
		{"resourceId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.VARCHAR)},
		{"name", new Integer(Types.VARCHAR)},
		{"typeId", new Integer(Types.VARCHAR)},
		{"scope", new Integer(Types.VARCHAR)},
		{"primKey", new Integer(Types.VARCHAR)}
	};

	private static final Object[][] _COLUMNS_GROUP_PERMISSIONS = {
		{"permissionId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_ROLES_PERMISSIONS = {
		{"permissionId", new Integer(Types.BIGINT)},
		{"roleId", new Integer(Types.VARCHAR)}
	};

	private static final Object[][] _COLUMNS_USERS_PERMISSIONS = {
		{"permissionId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.VARCHAR)}
	};

	private static final Object[][] _COLUMNS_ORG_GROUP_PERMISSION = {
		{"permissionId", new Integer(Types.BIGINT)},
		{"organizationId", new Integer(Types.VARCHAR)},
		{"groupId", new Integer(Types.BIGINT)}
	};

	private static Log _log = LogFactory.getLog(UpgradePermissions.class);

}