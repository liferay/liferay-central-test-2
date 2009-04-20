/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v5_2_3;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.UpgradeTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeUser.java.html"><b><i>View Source</i></b></a>
 *
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
		if (isSupportsAlterColumnName()) {
			runSQL("alter_column_type User_ greeting VARCHAR(255) null");
		}
		else {

			// User_

			UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
				UserImpl.TABLE_NAME, UserImpl.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(UserImpl.TABLE_SQL_CREATE);

			upgradeTable.updateTable();
		}

		if (isSupportsUpdateWithInnerJoin()) {
			StringBuilder sb = new StringBuilder();

			sb.append("update User_ inner join Contact_ on ");
			sb.append("Contact_.contactId = User_.contactId set ");
			sb.append("User_.firstName = Contact_.firstName, ");
			sb.append("User_.middleName = Contact_.middleName, ");
			sb.append("User_.lastName = Contact_.lastName, ");
			sb.append("User_.jobTitle = Contact_.jobTitle");

			runSQL(sb.toString());
		}
		else {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				con = DataAccess.getConnection();

				ps = con.prepareStatement(
					"select contactId, firstName, middleName, lastName, " +
						"jobTitle from Contact_");

				rs = ps.executeQuery();

				while (rs.next()) {
					long contactId = rs.getLong("contactId");
					String firstName = rs.getString("firstName");
					String middleName = rs.getString("middleName");
					String lastName = rs.getString("lastName");
					String jobTitle = rs.getString("jobTitle");

					ps = con.prepareStatement(
						"update User_ set firstName = ?, middleName = ?, " +
							"lastName = ?, jobTitle = ? where contactId = ?");

					ps.setString(1, firstName);
					ps.setString(2, middleName);
					ps.setString(3, lastName);
					ps.setString(4, jobTitle);
					ps.setLong(5, contactId);

					ps.executeUpdate();

					ps.close();
				}
			}
			finally {
				DataAccess.cleanUp(con, ps, rs);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeUser.class);

}