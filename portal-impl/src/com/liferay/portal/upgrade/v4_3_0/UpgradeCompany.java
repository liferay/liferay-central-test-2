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
import com.liferay.portal.model.Account;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.tools.util.DBUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeCompany.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 * @author  Brian Wing Shun Chan
 *
 */
public class UpgradeCompany extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private String _getUpdateSQL(
		String tableName, long companyId, String webId) {

		String updateSQL =
			"update " + tableName + " set companyId = '" + companyId +
				"' where companyId = '" + webId + "'";

		if (_log.isDebugEnabled()) {
			_log.debug(updateSQL);
		}

		return updateSQL;
	}

	private String[] _getWebIds() throws Exception {
		List webIds = new ArrayList();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_GET_WEB_IDS);

			rs = ps.executeQuery();

			while (rs.next()) {
				String companyId = rs.getString("companyId");

				webIds.add(companyId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return (String[])webIds.toArray(new String[0]);
	}

	private void _upgrade() throws Exception {
		DBUtil dbUtil = DBUtil.getInstance();

		String[] webIds = _getWebIds();

		for (int i = 0; i < webIds.length; i++) {
			String webId = webIds[i];

			_upgradeWebId(webId);
		}

		for (int i = 0; i < _TABLES.length; i++) {
			String sql = "alter_column_type " + _TABLES[i] + " companyId LONG";

			if (_log.isDebugEnabled()) {
				_log.debug(sql);
			}

			dbUtil.executeSQL(sql);
		}

		dbUtil.executeSQL("alter_column_type Account_ accountId LONG");
	}

	private void _upgradeWebId(String webId) throws Exception {
		DBUtil dbUtil = DBUtil.getInstance();

		long companyId = CounterLocalServiceUtil.increment();

		for (int j = 0; j < _TABLES.length; j++) {
			dbUtil.executeSQL(_getUpdateSQL(_TABLES[j], companyId, webId));
		}

		long accountId = CounterLocalServiceUtil.increment();

		dbUtil.executeSQL(
			"update Account_ set accountId = '" + accountId +
				"', companyId = '" + companyId + "' where accountId = '" +
					webId + "'");

		dbUtil.executeSQL(
			"update Address set classPK = '" + accountId +
				"' where classNameId = '" + Account.class.getName() +
					"' and classPK = '" + webId + "'");

		dbUtil.executeSQL(
			"update Company set accountId = " + accountId + " where webId = '" +
				webId + "'");

		dbUtil.executeSQL("alter_column_type Company companyId LONG");

		dbUtil.executeSQL(
			"update Contact_ set companyId = '" + companyId +
				"', accountId = " + accountId + " where contactId = '" + webId +
					".default'");

		dbUtil.executeSQL(
			"update Contact_ set accountId = '" + accountId +
				"' where accountId = '" + webId + "'");

		dbUtil.executeSQL(
			"update EmailAddress set classPK = '" + accountId +
				"' where classNameId = '" + Account.class.getName() +
					"' and classPK = '" + webId + "'");

		dbUtil.executeSQL(
			"update Phone set classPK = '" + accountId +
				"' where classNameId = '" + Account.class.getName() +
					"' and classPK = '" + webId + "'");

		dbUtil.executeSQL(
			"update User_ set companyId = '" + companyId +
				"', defaultUser = TRUE where userId = '" + webId + ".default'");

		dbUtil.executeSQL(
			"update Website set classPK = '" + accountId +
				"' where classNameId = '" + Account.class.getName() +
					"' and classPK = '" + webId + "'");
	}

	private static final String _GET_WEB_IDS = "select companyId from Company";

	private static final String[] _TABLES = new String[] {
		"Account_", "Address", "BlogsCategory", "BlogsEntry", "BookmarksEntry",
		"BookmarksFolder", "CalEvent", "Company", "Contact_", "DLFileEntry",
		"DLFileRank", "DLFileShortcut", "DLFileVersion", "DLFolder",
		"EmailAddress", "Group_", "IGFolder", "IGImage", "JournalArticle",
		"JournalContentSearch", "JournalStructure", "JournalTemplate", "Layout",
		"LayoutSet", "MBCategory", "MBMessage", "Organization_", "Permission_",
		"Phone", "PollsQuestion", "Portlet", "RatingsEntry", "Resource_",
		"Role_", "ShoppingCart", "ShoppingCategory", "ShoppingCoupon",
		"ShoppingItem", "ShoppingOrder", "Subscription", "UserGroup", "User_",
		"UserTracker", "Website", "WikiNode", "WikiPage"
	};

	private static Log _log = LogFactory.getLog(UpgradeCompany.class);

}