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
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.UserGroupImpl;
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
 * <a href="UpgradeUserGroup.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeUserGroup extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeUserGroup();
			_upgradeResource();
			_upgradeCounter();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeCounter() throws Exception {
		CounterLocalServiceUtil.reset(UserGroup.class.getName());
	}

	private void _upgradeResource() throws Exception {
		ResourceUtil.upgradePrimKey(
			_userGroupIdMapper, UserGroup.class.getName());
	}

	private void _upgradeUserGroup() throws Exception {

		// UserGroup

		PKUpgradeColumnImpl pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			UserGroupImpl.TABLE_NAME, UserGroupImpl.TABLE_COLUMNS,
			pkUpgradeColumn);

		upgradeTable.updateTable();

		_userGroupIdMapper = pkUpgradeColumn.getValueMapper();

		UpgradeColumn upgradeUserGroupIdColumn =
			new SwapUpgradeColumnImpl("userGroupId", _userGroupIdMapper);

		// Groups_UserGroups

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_USERGROUPS, _COLUMNS_GROUPS_USERGROUPS,
			upgradeUserGroupIdColumn);

		upgradeTable.updateTable();

		// Users_UserGroups

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_USERGROUPS, _COLUMNS_USERS_USERGROUPS,
			upgradeUserGroupIdColumn);

		upgradeTable.updateTable();
	}

	private ValueMapper _userGroupIdMapper;

	private static final String _TABLE_GROUPS_USERGROUPS = "Groups_UserGroups";

	private static final String _TABLE_USERS_USERGROUPS = "Users_UserGroups";

	private static final Object[][] _COLUMNS_GROUPS_USERGROUPS = {
		{"groupId", new Integer(Types.BIGINT)},
		{"userGroupId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_USERGROUPS = {
		{"userId", new Integer(Types.BIGINT)},
		{"userGroupId", new Integer(Types.BIGINT)}
	};

	private static Log _log = LogFactory.getLog(UpgradeGroup.class);

}