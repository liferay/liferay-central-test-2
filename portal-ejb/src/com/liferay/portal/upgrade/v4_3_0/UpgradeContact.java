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

import com.liferay.portal.model.impl.AddressImpl;
import com.liferay.portal.model.impl.ContactImpl;
import com.liferay.portal.model.impl.EmailAddressImpl;
import com.liferay.portal.model.impl.PhoneImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.WebsiteImpl;
import com.liferay.portal.tools.util.DBUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.ContactIdUpgradeColumnImpl;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeContact.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class UpgradeContact extends UpgradeProcess {

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

		// Contact_

		PKUpgradeColumnImpl upgradeColumn =
			new PKUpgradeColumnImpl(0, new Integer(Types.VARCHAR), true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			ContactImpl.TABLE_NAME, ContactImpl.TABLE_COLUMNS, upgradeColumn);

		upgradeTable.updateTable();

		ValueMapper contactIdMapper = upgradeColumn.getValueMapper();

		TempUpgradeColumnImpl upgradeUserIdColumn = new TempUpgradeColumnImpl(
			"userId");

		UpgradeColumn upgradeContactIdColumn = new ContactIdUpgradeColumnImpl(
			"contactId", upgradeUserIdColumn, contactIdMapper);

		TempUpgradeColumnImpl upgradeClassNameColumn =
			new TempUpgradeColumnImpl("className");

		UpgradeColumn upgradeClassPKColumn = new ContactIdUpgradeColumnImpl(
			"classPK", upgradeClassNameColumn, contactIdMapper);

		// User_

		upgradeTable = new DefaultUpgradeTableImpl(
			UserImpl.TABLE_NAME, UserImpl.TABLE_COLUMNS,
			upgradeUserIdColumn, upgradeContactIdColumn);

		upgradeTable.updateTable();

		// Address

		upgradeTable = new DefaultUpgradeTableImpl(
			AddressImpl.TABLE_NAME, AddressImpl.TABLE_COLUMNS,
			upgradeClassNameColumn, upgradeClassPKColumn);

		upgradeTable.updateTable();

		// EmailAddress

		upgradeTable = new DefaultUpgradeTableImpl(
			EmailAddressImpl.TABLE_NAME, EmailAddressImpl.TABLE_COLUMNS,
			upgradeClassNameColumn, upgradeClassPKColumn);

		upgradeTable.updateTable();

		// Phone

		upgradeTable = new DefaultUpgradeTableImpl(
			PhoneImpl.TABLE_NAME, PhoneImpl.TABLE_COLUMNS,
			upgradeClassNameColumn, upgradeClassPKColumn);

		upgradeTable.updateTable();

		// Website

		upgradeTable = new DefaultUpgradeTableImpl(
			WebsiteImpl.TABLE_NAME, WebsiteImpl.TABLE_COLUMNS,
			upgradeClassNameColumn, upgradeClassPKColumn);

		upgradeTable.updateTable();

		// Schema

		DBUtil.getInstance().executeSQL(_UPGRADE_SCHEMA);
	}

	private static final String _UPGRADE_SCHEMA =
		"alter_column_type Contact_ contactId LONG";

	private static Log _log = LogFactory.getLog(UpgradeContact.class);

}