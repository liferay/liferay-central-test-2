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

import com.liferay.mail.model.CyrusUser;
import com.liferay.mail.model.CyrusVirtual;
import com.liferay.portal.model.impl.AccountImpl;
import com.liferay.portal.model.impl.ContactImpl;
import com.liferay.portal.model.impl.PasswordTrackerImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultPKMapper;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.UserPortraitIdUpgradeColumnImpl;
import com.liferay.portlet.ratings.model.impl.RatingsEntryImpl;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeUser.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeUser extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void doUpgrade() throws Exception {

		// User_

		PKUpgradeColumnImpl pkUpgradeColumn =
			new PKUpgradeColumnImpl("userId", new Integer(Types.VARCHAR), true);

		UpgradeColumn upgradeUserPortraitIdColumn =
			new UserPortraitIdUpgradeColumnImpl(
				pkUpgradeColumn, AvailableMappersUtil.getImageIdMapper());

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			UserImpl.TABLE_NAME, UserImpl.TABLE_COLUMNS, pkUpgradeColumn,
			upgradeUserPortraitIdColumn);

		upgradeTable.updateTable();

		ValueMapper userIdMapper = new DefaultPKMapper(
			pkUpgradeColumn.getValueMapper());

		AvailableMappersUtil.setUserIdMapper(userIdMapper);

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR), userIdMapper);

		// Account

		upgradeTable = new DefaultUpgradeTableImpl(
			AccountImpl.TABLE_NAME, AccountImpl.TABLE_COLUMNS,
			upgradeUserIdColumn);

		upgradeTable.updateTable();

		// Contact

		UpgradeColumn upgradeContactIdColumn = new TempUpgradeColumnImpl(
			"contactId", new Integer(Types.VARCHAR));

		upgradeTable = new DefaultUpgradeTableImpl(
			ContactImpl.TABLE_NAME, ContactImpl.TABLE_COLUMNS,
			upgradeContactIdColumn, upgradeUserIdColumn);

		upgradeTable.updateTable();

		// CyrusUser

		upgradeTable = new DefaultUpgradeTableImpl(
			CyrusUser.TABLE_NAME, CyrusUser.TABLE_COLUMNS, upgradeUserIdColumn);

		upgradeTable.updateTable();

		// CyrusVirtual

		upgradeTable = new DefaultUpgradeTableImpl(
			CyrusVirtual.TABLE_NAME, CyrusVirtual.TABLE_COLUMNS,
			upgradeUserIdColumn);

		upgradeTable.updateTable();

		// PasswordTracker

		upgradeTable = new DefaultUpgradeTableImpl(
			PasswordTrackerImpl.TABLE_NAME, PasswordTrackerImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("passwordTrackerId", false),
			upgradeUserIdColumn);

		upgradeTable.updateTable();

		// RatingsEntry

		UpgradeColumn skipUpgradeClassNameIdColumn = new TempUpgradeColumnImpl(
			"classNameId", new Integer(Types.VARCHAR));

		UpgradeColumn skipUpgradeClassPKColumn = new TempUpgradeColumnImpl(
			"classPK", new Integer(Types.VARCHAR));

		upgradeTable = new DefaultUpgradeTableImpl(
			RatingsEntryImpl.TABLE_NAME, RatingsEntryImpl.TABLE_COLUMNS,
			upgradeUserIdColumn, skipUpgradeClassNameIdColumn,
			skipUpgradeClassPKColumn);

		upgradeTable.updateTable();

		// Schema

		for (int i = 0; i < _TABLES.length; i++) {
			String sql = "alter_column_type " + _TABLES[i] + " userId LONG";

			if (_log.isDebugEnabled()) {
				_log.debug(sql);
			}

			runSQL(sql);
		}
	}

	private static final String[] _TABLES = new String[] {
		"Account_", "Contact_", "CyrusUser", "CyrusVirtual", "PasswordTracker",
		"RatingsEntry", "User_",
	};

	private static Log _log = LogFactory.getLog(UpgradeUser.class);

}