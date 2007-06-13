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

import com.liferay.portal.model.Contact;
import com.liferay.portal.model.impl.AddressImpl;
import com.liferay.portal.model.impl.ContactImpl;
import com.liferay.portal.model.impl.EmailAddressImpl;
import com.liferay.portal.model.impl.PhoneImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.WebsiteImpl;
import com.liferay.portal.tools.comparator.ColumnsComparator;
import com.liferay.portal.tools.util.DBUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultPKMapper;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.ClassPKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ContactIdUpgradeColumnImpl;

import java.sql.Types;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeContact.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeContact extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		// Sort the User_ table's columns to ensure the screenName column is
		// populated before the contactId column

		_columnsUser = (Object[][])UserImpl.TABLE_COLUMNS.clone();

		Arrays.sort(_columnsUser, new ColumnsComparator("screenName"));

		try {
			_upgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgrade() throws Exception {

		// Contact_

		PKUpgradeColumnImpl upgradeColumn = new PKUpgradeColumnImpl(
			0, new Integer(Types.VARCHAR), true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			ContactImpl.TABLE_NAME, ContactImpl.TABLE_COLUMNS, upgradeColumn);

		upgradeTable.updateTable();

		ValueMapper contactIdMapper = new DefaultPKMapper(
			upgradeColumn.getValueMapper());

		// Address

		TempUpgradeColumnImpl classNameIdColumn =
			new TempUpgradeColumnImpl("classNameId");

		UpgradeColumn upgradeClassPKColumn = new ClassPKUpgradeColumnImpl(
			classNameIdColumn, Contact.class.getName(), contactIdMapper, false);

		upgradeTable = new DefaultUpgradeTableImpl(
			AddressImpl.TABLE_NAME, AddressImpl.TABLE_COLUMNS,
			classNameIdColumn, upgradeClassPKColumn);

		upgradeTable.updateTable();

		// EmailAddress

		upgradeTable = new DefaultUpgradeTableImpl(
			EmailAddressImpl.TABLE_NAME, EmailAddressImpl.TABLE_COLUMNS,
			classNameIdColumn, upgradeClassPKColumn);

		upgradeTable.updateTable();

		// Phone

		upgradeTable = new DefaultUpgradeTableImpl(
			PhoneImpl.TABLE_NAME, PhoneImpl.TABLE_COLUMNS,
			classNameIdColumn, upgradeClassPKColumn);

		upgradeTable.updateTable();

		// User_

		TempUpgradeColumnImpl upgradeScreenNameColumn =
			new TempUpgradeColumnImpl("screenName");

		UpgradeColumn upgradeContactIdColumn = new ContactIdUpgradeColumnImpl(
			upgradeScreenNameColumn, contactIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			UserImpl.TABLE_NAME, _columnsUser, upgradeScreenNameColumn,
			upgradeContactIdColumn);

		upgradeTable.updateTable();

		// Website

		upgradeTable = new DefaultUpgradeTableImpl(
			WebsiteImpl.TABLE_NAME, WebsiteImpl.TABLE_COLUMNS,
			classNameIdColumn, upgradeClassPKColumn);

		upgradeTable.updateTable();

		// Schema

		DBUtil.getInstance().executeSQL(_UPGRADE_SCHEMA);
	}

	private static final String _UPGRADE_SCHEMA =
		"alter_column_type Contact_ contactId LONG";

	private static Log _log = LogFactory.getLog(UpgradeContact.class);

	private Object[][] _columnsUser;

}