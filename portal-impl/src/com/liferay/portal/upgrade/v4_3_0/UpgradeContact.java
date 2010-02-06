/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.DefaultPKMapper;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.ContactIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ContactTable;
import com.liferay.portal.upgrade.v4_3_0.util.UserTable;

import java.sql.Types;

/**
 * <a href="UpgradeContact.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class UpgradeContact extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// Contact_

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"contactId", new Integer(Types.VARCHAR), true);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			ContactTable.TABLE_NAME, ContactTable.TABLE_COLUMNS,
			upgradePKColumn);

		upgradeTable.setCreateSQL(ContactTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper contactIdMapper = new DefaultPKMapper(
			upgradePKColumn.getValueMapper());

		AvailableMappersUtil.setContactIdMapper(contactIdMapper);

		// User_

		UpgradeColumn upgradeScreenNameColumn =
			new TempUpgradeColumnImpl("screenName");

		UpgradeColumn upgradeContactIdColumn = new ContactIdUpgradeColumnImpl(
			upgradeScreenNameColumn, contactIdMapper);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			UserTable.TABLE_NAME, UserTable.TABLE_COLUMNS,
			upgradeScreenNameColumn, upgradeContactIdColumn);

		upgradeTable.setCreateSQL(UserTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();
	}

}