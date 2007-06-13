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
import com.liferay.portal.model.impl.EmailAddressImpl;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.PhoneImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.model.impl.SubscriptionImpl;
import com.liferay.portal.model.impl.WebsiteImpl;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SkipUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.v4_3_0.util.ClassNameUpgradeColumnImpl;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionImpl;
import com.liferay.portlet.ratings.model.impl.RatingsEntryImpl;
import com.liferay.portlet.ratings.model.impl.RatingsStatsImpl;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeClassName.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeClassName extends UpgradeProcess {

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

		// Address

		UpgradeColumn upgradeCompanyIdColumn = new SkipUpgradeColumnImpl(
			"companyId", new Integer(Types.VARCHAR));

		UpgradeColumn upgradeUserIdColumn = new SkipUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR));

		UpgradeColumn upgradeClassPKColumn = new SkipUpgradeColumnImpl(
			"classPK", new Integer(Types.VARCHAR));

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			AddressImpl.TABLE_NAME, AddressImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl(), upgradeCompanyIdColumn,
			upgradeUserIdColumn, new ClassNameUpgradeColumnImpl(),
			upgradeClassPKColumn);

		upgradeTable.updateTable();

		// EmailAddress

		upgradeTable = new DefaultUpgradeTableImpl(
			EmailAddressImpl.TABLE_NAME, EmailAddressImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl(), upgradeCompanyIdColumn,
			upgradeUserIdColumn, new ClassNameUpgradeColumnImpl(),
			upgradeClassPKColumn);

		upgradeTable.updateTable();

		// Group

		upgradeTable = new DefaultUpgradeTableImpl(
			GroupImpl.TABLE_NAME, GroupImpl.TABLE_COLUMNS,
			upgradeCompanyIdColumn, new ClassNameUpgradeColumnImpl(),
			upgradeClassPKColumn);

		upgradeTable.updateTable();

		// MBDiscussion

		upgradeTable = new DefaultUpgradeTableImpl(
			MBDiscussionImpl.TABLE_NAME, MBDiscussionImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl(), new ClassNameUpgradeColumnImpl(),
			upgradeClassPKColumn);

		upgradeTable.updateTable();

		// Phone

		upgradeTable = new DefaultUpgradeTableImpl(
			PhoneImpl.TABLE_NAME, PhoneImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl(), upgradeCompanyIdColumn,
			upgradeUserIdColumn, new ClassNameUpgradeColumnImpl(),
			upgradeClassPKColumn);

		upgradeTable.updateTable();

		// RatingsEntry

		upgradeTable = new DefaultUpgradeTableImpl(
			RatingsEntryImpl.TABLE_NAME, RatingsEntryImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl(), upgradeCompanyIdColumn,
			upgradeUserIdColumn, new ClassNameUpgradeColumnImpl(),
			upgradeClassPKColumn);

		upgradeTable.updateTable();

		// RatingsStats

		upgradeTable = new DefaultUpgradeTableImpl(
			RatingsStatsImpl.TABLE_NAME, RatingsStatsImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl(), new ClassNameUpgradeColumnImpl(),
			upgradeClassPKColumn);

		upgradeTable.updateTable();

		// Role

		upgradeTable = new DefaultUpgradeTableImpl(
			RoleImpl.TABLE_NAME, RoleImpl.TABLE_COLUMNS,
			upgradeCompanyIdColumn, new ClassNameUpgradeColumnImpl(),
			upgradeClassPKColumn);

		upgradeTable.updateTable();

		// Subscription

		upgradeTable = new DefaultUpgradeTableImpl(
			SubscriptionImpl.TABLE_NAME, SubscriptionImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl(), upgradeCompanyIdColumn,
			upgradeUserIdColumn, new ClassNameUpgradeColumnImpl(),
			upgradeClassPKColumn);

		upgradeTable.updateTable();

		// Website

		upgradeTable = new DefaultUpgradeTableImpl(
			WebsiteImpl.TABLE_NAME, WebsiteImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl(), upgradeCompanyIdColumn,
			upgradeUserIdColumn, new ClassNameUpgradeColumnImpl(),
			upgradeClassPKColumn);

		upgradeTable.updateTable();
	}

	private static Log _log = LogFactory.getLog(UpgradeClassName.class);

}